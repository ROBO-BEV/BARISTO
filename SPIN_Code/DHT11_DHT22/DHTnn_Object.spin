
''******************************************
''*  Title: DHTnn_Object                   *
''*  Modified from DHT21_Object -          *
''*  Walter T. Mosscrop 2016               *
''*  Original Author: Gregg Erickson  2012 *
''*  See MIT License for Related Copyright *
''*  See end of file and objects for .     *
''*  related copyrights and terms of use   *
''*  This object draws upon code from other*
''*  OBEX objects such as servoinput.spin  *
''*  and DHT C++  from Adafruit Industries *
''*                                        *
''*  This object reads the temperature and *
''*  humidity from an DHT11 or DHT22       *
''*  using a unique 1-wire serial protocol *
''*  with 5 byte packets where 0s are 26uS *
''*  long and 1s are 70uS.                 *
''*                                        *
''*  The object automatically returns the  *
''*  temperature and humidiy to variables  *
''*  memory every few seconds as Deg C and *
''*  relative percent respectively. It also*
''*  return an error byte where true means *
''*  the data received had correct parity  *
''*                                        *
''*  The read loop has been reworked to    *
''*  stabilize the timing and reduce the   *
''*  number of invalid reads due to timing *
''*  issues                                *
''******************************************
{
   VccRed (Power)
                │
               10K (Pull-Up Resistor)
                │
   Prop Pin Yellow (Data)


   VSSBlack (Ground)

}
CON

  MAX_SAMPLES = 40 ' 40 one-bit samples 

VAR

long APin,Device                  
long MeasurementStack[50]
long clkpermicro, clkminpulse
long measurementCog, measuring
long MeasurementValues[MAX_SAMPLES]
long temperaturePtr, humidityPtr, statusPtr

OBJ
  f       : "FloatMath"

PUB StartDHTnn(devicePin,DeviceModel,TempPtr,HumPtr,StatPtr) : result

  APin := devicePin
  Device := DeviceModel

  measurementCog := -1
  
{{
    This method lauches the Measuring code in a new cog
    to read the DHTnn autonomously with variables updated
    every few seconds
}}

  MeasurementInit(APin, @measuring, @MeasurementValues)

  temperaturePtr := TempPtr
  humidityPtr := HumPtr
  statusPtr := StatPtr

PUB Stop
{{
   stop cog if in use
}}

    if measurementCog => 0
      cogstop(measurementCog)

Pub DHTnn_Read | Data[5], Temp, Humid, ByteCount,BitCount,Pulsewidth,Parity,ptr,tr,hr
{{
    This method reads the DHTnn device autonomously with variables located at
    the pointers updated every few seconds
}}
                      'Apin is data line in from DHTnn
                      'Data[5] bytes received in order from DHTnn
                      'DATA = upper 8 bits of RH data
                      '     + lower 8 bits of RH data
                      '     + upper 8 bits of temperature data
                      '     + lower 8 bits of temperature data
                      '     + 8 bits check-sum (equals other 4 bytes added)

                      'ByteCount   - Index counter for data Bytes
                      'BitCount    - Index counter for bits within data bytes
                      'Pulsewidth  - Width of bits received from DHTnn, 26uS = 0, 70S=1, in clock ticks
                      'parity      - Boolean - parity for data from DHTnn
                      'clkpermicro - number of clock ticks per microsecond
                      'clkminpulse - the number of clock ticks for a pulsewidth value to be considered a 1

  clkpermicro := clkfreq / 1_000_000
  clkminpulse := clkpermicro * 48 ' Minimum 48 us 'high' pulse

  waitcnt(clkfreq*2+cnt)                ' Pause to allow DHTnn to stabilize

  DIRA[Apin]~~                          ' Set selected Pin to output
  OUTA[Apin]~~                          ' Pull Up selected Pin to start sequence

  Repeat ByteCount from 0 to 4
    Data[ByteCount]:=0                  ' Clear Data of Each Byte Before Input
  
  waitcnt(clkfreq/4+cnt)                ' Pause to allow DHTnn to stabilize

  ' Send a low to the DHTnn to request data

  DIRA[Apin]~~                          ' Set selected Pin to output
  OUTA[Apin]~                           ' Pull Down selected Pin for 500 uS to Request Data

  if device == 11
    waitcnt((clkpermicro * 20_000) + cnt) ' hold 20ms
  else
    waitcnt(clkfreq / 2000 + cnt)       ' Pause for 500uS

  OUTA[Apin]~~                          ' Return Pin to High
  DIRA[Apin]~                           ' Set Pin to Input and Release Control to DHTnn
  
  ' DHTnn reponds with a ready signal (80uS low, 80uS high, 40 uS low, before data)

  waitpne(|<Apin,|<Apin,0)              ' Wait for low, high, low sequence
  
  waitpeq(|<Apin,|<Apin,0)
  
  waitpne(|<Apin,|<Apin,0)

  MeasurementStart
  
  ' DHTnn will send 40 high bits where 0 is 26uS and 1 is 70uS

  repeat until measuring == 0
 
  ptr := 0
  
  Repeat ByteCount from 0 to 4          ' Store Data in 5 Bytes
    Repeat BitCount from 7 to 0         ' Receive Data by Bit, MSB to LSB
      pulsewidth := LONG[@MeasurementValues][ptr++]
      if pulsewidth > clkminpulse       ' If Pulse > min pulse then bit is 1 else 0 
        Data[ByteCount]|=|<BitCount     ' Store the bit in byte
  
  ' Check Parity
  parity := ((data[0]+data[1]+data[2]+data[3])& $ff)==(data[4]) 'Last byte equals sum of first four bytes
  
  ' Calculate Temperature

  if parity
    if (Device == 11)
      tr := data[2] & $7f               ' Pull from data2, mask MSB Bit
    else
      tr := data[2]                     ' Pull from data2
    
    tr <<= 8                            ' Put into upper byte
    tr += data[3]                       ' Add lower byte

    Temp := f.FFloat(tr)

    if (Device == 11)
      Temp := f.FDiv(Temp, 256.0)       ' Convert to DHT11 value
    else
      Temp := f.FMul(Temp, 0.1)         ' Convert to DHT22 value    
  
  ' Calculate Humidity

  if parity
    Humid:=data[0]                      ' Pull from data0
    Humid<<=8                           ' Put into upper byte
    Humid+=data[1]                      ' Add lower byte

    Humid := f.FFloat(Humid)

    if (Device == 11)
      Humid := f.FDiv(Humid, 256.0)     ' Convert to DHT11 value
    else
      Humid := f.FMul(Humid, 0.1)       ' Convert to DHT22 value    

  ' Return values to addresses provided in pointers
  
  if parity
    Long[temperaturePtr] := temp        ' Temperature
    Long[humidityPtr] := humid          ' Humidity
    Long[statusPtr] := 1                ' Parity ok
  else
    Long[statusPtr]:= 0                 ' Error

' Cog relies on these arguments, do not remove
pub MeasurementInit(pAPin, pMeasuringStatus, pMeasurementValues) 
 
  measurementCog := cognew(@MeasureCog, @pAPin)

pub MeasurementStart
  Long[statusPtr] := -1                  ' We're updating values    

  LONGFILL(@MeasurementValues, MAX_SAMPLES, 0)

  measuring := 1

DAT
        ORG 0

MeasureCog        
        mov             p1, par                         ' Get data pointer
        rdlong          APinNum, p1                     ' Get APin number

        add             p1, #4                          ' Get next pointer
        
        rdlong          measuringStatusPtr, p1          ' Get pointer to measuring value

        add             p1, #4                          ' Get next pointer
        rdlong          arrayPtrSave, p1                ' Get monitor array pointer

        mov             APinMask, #1                    ' Get pin mask
        shl             APinMask, APinNum 

        ' Used for timing pulses

        movi            ctra, #%11111                   ' Setup counter (always)
        mov             frqa, #1                        ' One count per tick

        ' Used for timeout in case of device failure
        
        movi            ctrb, #%11111                   ' Setup counter (always)
        mov             frqb, #1                        ' One count per tick

wait
        rdlong          t1, measuringStatusPtr wz       ' Check "measuring" value
   if_z jmp             #wait                           ' Wait until "measuring" value nonzero

        ' Prepare for measurement
   
        mov             arrayPtr, arrayPtrSave          ' Set up array pointer

        mov             t2, #0                          ' Reset counter
        mov             phsb, #0                        ' Reset timeout
        
measure

' Handle low-to-high (pulse gap)

        mov             t1, #0 wc                       ' Reset carry for waitpeq

        waitpeq         APinMask, APinMask              ' Wait for high state

        mov             t1, phsb                        ' Have we timed out?
        cmp             t1, timeoutTicks wz, wc         
  if_a  jmp             #done                           ' Yes, set done & start over

        mov             phsa, #0                        ' Reset counter

' Time high-to-low (pulse)

        waitpne         APinMask, APinMask              ' Wait for low state
        mov             t1, phsa                        ' Save counter value in t1, can't write directly to hub
        wrlong          t1, arrayPtr                    ' Save duration value
        add             arrayPtr, #4                    ' Set up for next array value

        mov             t1, phsb                        ' Have we timed out?
        cmp             t1, timeoutTicks wz, wc         
  if_a  jmp             #done                           ' Yes, set done & start over

        add             t2, #1                          ' Count values
        cmp             maxSample, t2 wz                ' Have we reached the limit?
  if_ne jmp             #measure                        ' No, back for more

done    mov             t1, #0                          ' Yes, set status value to zero
        wrlong          t1, measuringStatusPtr          ' Update status value

        jmp             #wait                           ' We're done monitoring
        
APinNum            long              0
maxSample          long              MAX_SAMPLES
measuringStatusPtr long              0
arrayPtr           long              0 
arrayPtrSave       long              0

timeoutTicks       long              5 * 80_000_000 ' Assumes 80 MHz clock; 5 seconds

APinMask      long              0

p1            long              0

t1            long              0
t2            long              0

{{

┌┐
│                                                   TERMS OF USE: MIT License                                                  │
├┤
│Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation    │
│files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,    │
│modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software│
│is furnished to do so, subject to the following conditions:                                                                   │
│                                                                                                                              │
│The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.│
│                                                                                                                              │
│THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE          │
│WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR         │
│COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,   │
│ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.                         │
└┘
}}
