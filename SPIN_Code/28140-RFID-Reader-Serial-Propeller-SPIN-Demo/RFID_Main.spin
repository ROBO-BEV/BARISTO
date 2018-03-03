'' @file    RFIS_Main.spin
'' @author  Jon "JonnyMac" McPhalen & Blaze Sanders
'' @created 23 SEP 2014 
'' @updated 03 MARCH 2018
'' @email   jon@jonmcphalen.com
'' @email   founders@robobev.com
'' 
'' @section DESCRIPTION
'' 
'' Parallax Serial RFID main driver program for PROPELLER PROJECT BOARD USB Part Number 32810 REV D
'' Use pins 25 and 26 with inline 3.9K resistors to prevent too much current 
'' into the pins from the 5V RFID device.  
''
'' Version 1.1 - Updated March 3, 2018 by Blaze Sanders for PROPELLER PROJECT BOARD USB for ROBO BEV
'' Version 1.0 - Updated Oct 03, 2014 by Jon "JonnyMac" McPhalen 
'' 
'' See end of file for terms of use

CON 

  {Propeller Timing Constants}
  _clkmode = xtal1 + pll16x                        ' Use crystal and multiple by 16 to get 80 MHz CPU
  _xinfreq = 5_000_000                             ' use 5MHz crystal

  CLK_FREQ = ((_clkmode - xtal1) >> 6) * _xinfreq  ' Set system freq as a constant
  MS_001   = CLK_FREQ / 1_000                      ' Set CPU ticks in 1 ms as a constant
  US_001   = CLK_FREQ / 1_000_000                  ' Set CPU ticks in 1 us as a constant

  {I2C and Serial Terminal Communication Constants} 
  #0, LSBFIRST, MSBFIRST                           ' LITTLE or BIG endianness options
  #1, HOME, GOTOXY,                                ' Terminal cursor position constants
  #8, BKSP, TAB, LF, CLREOL, CLRDN, CR             ' Terminal formatting constants (CR = Carriage Return)
  #14, GOTOX, GOTOY, CLS                           ' Terminal cursor position and screen clearing constants
  #28, SCL, SDA, TXpin, RXpin                      ' Standard Propeller pin constants
  
  {RFID Module Constants}
  RFID_RX = 26                                     ' Serial data transmitted from RFID module to CPU on this pin
  RFID_EN = 25                                     ' ENABLE & DISABLE RFID module with this pin
 
OBJ

  term : "FullDuplexSerial" 
  rfid : "FullDuplexSerial"
  

VAR
          

PUB Main | idx

  Setup                                                         ' start program objects

  repeat 
    repeat 2
      term.tx(CR)
    term.str(string("Please place your smart cup on the black pad.", CR))
    
    accept_tag(@tagbuf)                                         ' wait for tag
    
    term.str(string("-- "))                                     ' display tag string
    repeat idx from 0 to 9
      term.tx(byte[@tagbuf][idx])
    term.tx(CR)
    
    idx := get_tag_id(@tagbuf)                                  ' lookup tag
    
    if (idx => 0)                                               ' display name for tag
      term.str(string("-- "))
      term.str(@@Names[idx])
    else
      term.str(string("-- I can not find your smart cup."))
      term.str(string("-- Please scan QR code to download BARISTO from the Google Play store and register with us."))

    pause(2000)                 'Pause 2.0 seconds
    

pub Setup

'' Setup IO and objects for application

  term.start(RXpin, TXpin, %0000, 115_200)                  ' terminal via programming port                 
  rfid.start(RFID_RX, RFID_RX, %1100, 2400)             ' open-drain serial for RFID                         


con

  { --------- }
  {  R F I D  }
  { --------- }


con

 NUM_OF_TAGS = 3                         ' Number of RFID tags stored on CPU running this code
  

var

  byte  tagbuf[10]                                              ' tag buffer
  

pub accept_tag(p_buf) | c, idx

'' Enables RFID reader for ms milliseconds
'' -- reads tag bytes (if available) into p_buf

  bytefill(p_buf, 0, 10)                                        ' clear old data

  rfid.rxflush                                                  ' clear rx buffer
  
  low(RFID_EN)                                                  ' enable reader

  repeat
    c := rfid.rx
  until (c == $0A)                                              ' wait for $0A (LF)

  repeat 10                                                     ' rx 10 tag bytes
    byte[p_buf++] := rfid.rx

  input(RFID_EN)                                                ' disable reader
  

pub get_tag_id(p_buf) | tidx, p_check, bidx

'' Compares tag data in ram (at p_buf) with known tags
'' -- returns tag index (0..NUM_OF_TAGS) if found
'' -- returns -1 if tag not found

  repeat tidx from 0 to NUM_OF_TAGS                             ' loop through known tags
    p_check := @@Tags[tidx]                                     ' get hub address of tag being tested
    repeat bidx from 0 to 9                                     ' loop through bytes in tag
      if (byte[p_buf][bidx] <> byte[p_check][bidx])             ' if byte mismatch
        quit                                                    ' abort this tag
    if (bidx == 10)                                             ' if all bytes matched
      return tidx                                               ' return this tag id
      
  return -1                                                     ' return not found

  
dat { tags data }

  Tag0        byte      "0415148F26"
  Tag1        byte      "0415148E0C"
  Tag2        byte      "041514AEA3"
  Tag3        byte      "041514A076"          

  Tags        word      @Tag0, @Tag1, @Tag2, @Tag3 


  Name0       byte      "Luke Skywalker", 0
  Name1       byte      "Princess Leia", 0
  Name2       byte      "Darth Vader", 0
  Name3       byte      "Obi Wan Kenobi", 0

  Names       word      @Name0, @Name1, @Name2, @Name3        


con

  { ------------- }
  {  B A S I C S  }
  { ------------- }


pub pause(ms) | t

'' Delay program in milliseconds

  if (ms < 1)                                                   ' delay must be > 0
    return
  else
    t := cnt - 1776                                             ' sync with system counter
    repeat ms                                                   ' run delay
      waitcnt(t += MS_001)
    

pub high(pin)

'' Makes pin output and high

  outa[pin] := 1
  dira[pin] := 1


pub low(pin)

'' Makes pin output and low

  outa[pin] := 0
  dira[pin] := 1


pub toggle(pin)

'' Toggles pin state

  !outa[pin]
  dira[pin] := 1


pub input(pin)

'' Makes pin input and returns current state

  dira[pin] := 0

  return ina[pin]
  

dat { license }

{{

  Terms of Use: MIT License

  Permission is hereby granted, free of charge, to any person obtaining a copy of this
  software and associated documentation files (the "Software"), to deal in the Software
  without restriction, including without limitation the rights to use, copy, modify,
  merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
  permit persons to whom the Software is furnished to do so, subject to the following
  conditions:

  The above copyright notice and this permission notice shall be included in all copies
  or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
  PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
  CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
  OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

}}  