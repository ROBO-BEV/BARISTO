'' @file    Ping_Demo.spin
'' @author  Chris Savage & Jeff Martin & Blaze Sanders
'' @created 08 MAY 2006 
'' @updated 01 MARCH 2018
'' @email   founders@robobev.com
'' 
'' @version 1.3 
''
'' @brief Demo driver program to test the Parallax PING ultrasonic distance sensor   
''
'' @section DESCRIPTION
''
'' This demo prints to both the Parallax Serial Terminal and a Parallax 4X20 Serial LCD
'' 
'' Version 1.3 - Updated March 1, 2018 by Blaze Sanders to compile on Pi 3 Raspbian Stretch OS
'' Version 1.2 - Updated March 26, 2008 by Jeff Martin to use updated Debug_LCD
'' 
'' See end of file for terms of use

CON

  _clkmode = xtal1 + pll16x
  _xinfreq = 5_000_000

  {Ultrasonic Distance Sensor Hardware Constant(s)} 
  PING_Pin = 20                                         ' I/O Pin For PING)))
  
  {LCD Hardware Constants} 
  LCD_Pin = 1                                           ' I/O Pin For LCD
  LCD_Baud = 19_200                                     ' LCD Baud Rate
  LCD_Lines = 4                                         ' Parallax 4X20 Serial LCD (#27979)
                                                        

  {LCD and Serial Terminal Formatting Constants} 
  #1, HOME, GOTOXY 
  #8, BKSP, TAB, LF, CLREOL, CLRDN, CR                  'CR = Carriage Return
  #14, GOTOX, GOTOY, CLS
  #30, TXpin, RXpin
  TERM_BAUD_RATE = 115200

VAR

  long  range

    
OBJ

  LCD  : "Debug_LCD"
  Ping : "Ping"
  Term : "FullDuplexSerial"

  
PUB Main

  {Setup LCD harware and terminal software}
  Term.start(RXpin, TXpin, %0000, 115_200)              ' Setup terminal via programming port 
  LCD.init(LCD_Pin, LCD_Baud, LCD_Lines)                ' Initialize LCD Object
  LCD.cursor(0)                                         ' Turn Off Cursor
  LCD.backlight(true)                                   ' Turn On Backlight   
  LCD.cls                                               ' Clear Display
                                                        
                                                                                                            
  LCD.str( string("PING)))  Demo", CR, CR, "Inches      -", CR, "Centimeters -"))
  Term.str(string("PING)))  Demo", CR, CR, "Inches      -", CR, "Centimeters -", CR))
  
  repeat                                                ' Repeat Forever
    Term.str(string("TEST BEFORE PING"))                                               
    'LCD.gotoxy(15, 2)                                  ' Position Cursor
    range := Ping.Inches(PING_Pin)                      ' Get Range In Inches
    Term.str(string("TEST AFTER PING")) 
    'LCD.decx(range, 2)                                  ' Print Inches
    'LCD.str(string(".0 "))                              ' Pad For Clarity
    Term.dec(range)
    Term.str(string(".0 inches"))                              ' Pad For Clarity
    'LCD.gotoxy(14, 3)                                   ' Position Cursor
    range := Ping.Millimeters(PING_Pin)                 ' Get Range In Millimeters
    'LCD.decf(range / 10, 3)                             ' Print Whole Part
    'LCD.putc(".")                                       ' Print Decimal Point
    'LCD.decx(range // 10, 1)                            ' Print Fractional Part
    waitcnt(clkfreq / 10 + cnt)                         ' Pause 1/10 Second
    
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