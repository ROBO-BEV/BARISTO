'' SPECIAL VERSION FOR TESTING DUE TO VARIANCES IN SENSORS
'' WTM 01/22/2016
''
''******************************************
''*  Title: DHTnn_Test for DHT11_Object    *
''*  Modified from DHT21_Demo -            *
''*  Walter T. Mosscrop 2014               *
''*  Author: Gregg Erickson  2012          *
''*  See MIT License for Related Copyright *
''*  See end of file and objects for .     *
''*  related copyrights and terms of use   *
''*  This object draws upon code from other*
''*  OBEX objects such as servoinput.spin  *
''*  and DHT C++  from Adafruit Industries *
''*                                        *
''*  The object reads the temperature and  *
''*  humidity from an DHT11 Sensor         *
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
''******************************************



CON

  _clkmode = xtal1 + pll16x   'Set clock speed and mode
  _xinfreq = 5_000_000
VAR

long ReadStatus      ' Status of available data
                     ' -1 = updating values, try again
                     '  0 = bad data (parity error)
                     '  1 = good data
long Temperature     ' Calculated temperature in degrees C as float
long Humidity        ' Calculated humidity in % relative humidity as float

OBJ

  serial  : "FullDuplexSerial"
  DHT     : "DHTnn_Object"
  f       : "FloatMath"
  fp      : "FloatString"

Pub Main

serial.start(31, 30, 0, 38_400)     ' Initialize Serial Communication to Serial Terminal
fp.SetPrecision(4)
'              pin, device (11 or 22), temperature address, humidity address, read status address
DHT.StartDHTnn(0  , 11               , @Temperature       , @Humidity       , @ReadStatus) 

repeat
    readStatus := -1

    DHT.DHTnn_Read 

    repeat until ReadStatus => 0

    if ReadStatus == 1
      temperature := f.FMul(temperature, 9.0)    ' Convert °C to °F
      temperature := f.FDiv(temperature, 5.0)
      temperature := f.FAdd(temperature, 32.0)
      
      serial.str(fp.FloatToString(temperature))
      serial.str(@fahr)
      serial.str(@space)

      serial.str(fp.FloatToString(humidity))
      serial.str(@percent)

      serial.str(@crlf)

    else
      serial.str(@invalid)
      serial.str(@crlf)

Dat
                                                
Space    byte " ",0           ' Strings for use in printing
Percent  byte " %",0
Fahr     byte " F",0
Valid    byte " Read OK",0
Invalid  byte " Read Error",0
crlf     byte $0A, $0D, 0

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
