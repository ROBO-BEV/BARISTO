'' @file    Ping_Demo.spin
'' @author  Chris Savage & Jeff Martin & Blaze Sanders
'' @created 08 MAY 2006 
'' @updated 10 MARCH 2018
'' @email   founders@robobev.com
'' 
'' @version 1.4
''
'' @brief BARISTO Parallax PING ultrasonic distance driver program  
''
'' @section DESCRIPTION
''
'' This program defines the BARISTO ultrasonic distance sensor state machine. 
'' It should be run at 180 MHz in its own COG/CPU to give best User Experience (UX) 
'' 
'' Version 1.4 - Updated March 10, 2018 by Blaze Sanders to define the BARISTO state machine 
'' Version 1.3 - Updated March 1, 2018 by Blaze Sanders to compile on Pi 3 Raspbian Stretch OS
'' Version 1.2 - Updated March 26, 2008 by Jeff Martin to use updated Debug_LCD
'' 
'' See end of file for terms of use

CON

  {Propeller Timing Constants}
  _clkmode = xtal1 + pll16x                ' Use crystal and multiple by 16 to get 80 MHz CPU
  _xinfreq = 5_000_000                     ' Use 5MHz crystal

  {Ultrasonic Distance Sensor Hardware Constant(s)} 
  PING_Z_AXIS_PIN = 20                            ' I/O Pin For PING)))
  PING_X_AXIS_PIN = 21                            ' I/O Pin For PING)))
  PING_Y_AXIS_PIN = 22                            ' I/O Pin For PING)))

  {Linux OS Pin contstant}
  ULTRASONICS_READY_TO_DISPENSE_PIN = 23 
  
  {Serial Terminal Formatting Constants} 
  #1, HOME, GOTOXY 
  #8, BKSP, TAB, LF, CLREOL, CLRDN, CR     'CR = Carriage Return
  #14, GOTOX, GOTOY, CLS
  #30, TXpin, RXpin
  TERM_BAUD_RATE = 115200
  DEBUG_STATEMENTS_ON = 1

  {Distance Constants in millimeters}
  DISTANCE_TO_PLATFORM =  200              
  DISANCE_TO_EMPTY_SMART_CUP = 180
  DISTANCE_TO_FULL_SMART_CUP = 50
  DISTANCE_TO_SMART_CUP_WITH_LID_ON = 45
   
VAR

  long  mmRangeZaxis, mmRangeYaxis, mmRangeXaxis
  byte  ultrasonicsReadyToDispense
    
OBJ

  LCD  : "Debug_LCD"
  Ping : "Ping"
  Term : "FullDuplexSerial"
  
PUB Main

  {Setup PING harware and terminal software}
  Term.start(RXpin, TXpin, %0000, 115_200)              ' Setup terminal connection pin and baud rate
  Term.str(string("Starting BARISTO PING sensors", CR))
  DIRA[ULTRASONICS_READY_TO_DISPENSE_PIN]~~             ' Make GPIO an output pin
  OUTA[ULTRASONICS_READY_TO_DISPENSE_PIN]~              ' Initialize output pin LOW
  ultrasonicsReadyToDispense = 0x00                     ' Intialize all three ultrasonic sensors to NOT READY (0x0000_0XYZ) 

  repeat                                                ' Repeat forever in one of eigth Propeller COGs / CPUs
    Term.str(string("Z axis PING))) SENT"))                                               
    mmRangeZaxis := Ping.Millimeters(PING_Z_AXIS_PIN)   ' Send ultrasonic pulse and get distance in millimeters
    Term.dec(mmRangeZaxis)				' Print distance in decimal format
    Term.str(string(".0 millimeters"))                 
    Term.str(string("Z axis PING))) RECIEVED")) 

    Term.str(string("X axis PING))) SENT"))                                               
    mmRangeXaxis := Ping.Millimeters(PING_X_AXIS_PIN)   ' Send ultrasonic pulse and get distance in millimeters
    Term.dec(mmRangeXaxis)				' Print distance in decimal format
    Term.str(string(".0 millimeters"))                 
    Term.str(string("X axis PING))) RECIEVED")) 

    Term.str(string("Y axis PING))) SENT"))                                               
    mmRangeYaxis := Ping.Millimeters(PING_Y_AXIS_PIN)   ' Send ultrasonic pulse and get distance in millimeters
    Term.dec(mmRangeYaxis)				' Print distance in decimal format
    Term.str(string(".0 millimeters"))                 
    Term.str(string("Y axis PING))) RECIEVED")) 


    IF mmRange <= DISTANCE_TO_SMART_CUP_WITH_LID_ON
      Term.str(string("Your lid is still on my young apprentice, pleasse remove."))
      waitcnt(clkfreq*2 + cnt)                           ' Pause for 2 seconds
    ELSEIF mmRangeZaxis <= DISTANCE_TO_PLATFORM          ' A smart or random object is present on platform
      ultrasonicsReadyToDispense = ultrasonicsReadyToDispense OR 0b0000_0001
      IF mmRangeZaxis <= DISTANCE_TO_FULL_SMART_CUP      ' Cup is full of liquid
        OUTA[ULTRASONICS_READY_FOR_DISPENSE_PIN]~        ' Make READY pin LOW                           
        IF DEBUG_STATEMENTS_ON 
          Term.str(string("sCup 1 is full. Please remove from pad and screw on lid"))        
          ultrasonicsReadyToDispense = ultrasonicsReadyToDispense AND 0b0000_1110
      ELSEIF mmRangeZaxis <= DISTANCE_TO_EMPTY_SMART_CUP ' Cup is preseent but not full of liquid
        IF MIN_Y_POSITION <= mmRangeYaxis AND mmRangeYaxis <= MAX_Y_POSITION     
          IF DEBUG_STATEMENTS_ON 
            Term.str(string("sCup 1 is centered front to back."))
            ultrasonicsReadyToDispense = ultrasonicsReadyToDispense OR 0b0000_0010
        ELSEIF mmRangeYaxis < MIN_Y_POSITION
          IF DEBUG_STATEMENTS_ON 
            Term.str(string("Cup NOT centered, please move cup towards you."))
        ELSE
          IF DEBUG_STATEMENTS_ON 
            Term.str(string("Cup NOT centered, please move cup away from you."))

        IF MIN_X_POSITION <= mmRangeXaxis AND mmRangeXaxis <= MAX_X_POSITION     
          IF DEBUG_STATEMENTS_ON 
            Term.str(string("sCup 1 is centered left to right."))
            ultrasonicsReadyToDispense = ultrasonicsReadyToDispense OR 0b0000_0100
        ELSEIF mmRangeXaxis < MIN_X_POSITION
          IF DEBUG_STATEMENTS_ON 
            Term.str(string("Cup NOT centered, please move cup to your left."))
        ELSE
          IF DEBUG_STATEMENTS_ON 
            Term.str(string("Cup NOT centered, please move cup to your right."))


     IF ultrasonicsReadyToDispense AND 0b0000_0111 == 0x07
       OUTA[ULTRASONICS_READY_FOR_DISPENSE_PIN]~~       ' Make READY pin HIGH                   
       IF DEBUG_STATEMENTS_ON 
          Term.str(string("Filling cupp: "))
          Term.dec((mmRange/DISTANCE_TO_EMPTY_SMART_CUP) - DISTANCE_TO_FULL_SMART_CUP)
          Term.str(string("% full", CR))
          waitcnt(clkfreq/2 + cnt)                     ' Pause 0.5 second to slow down print statements
      
    waitcnt(clkfreq / 10 + cnt)                          ' Pause 1/10 Second to reduce COG/CPU load
    
{{
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                                   TERMS OF USE: MIT License                                                  │                                                            
├──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤
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
└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
}}        
