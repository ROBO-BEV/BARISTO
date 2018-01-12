'' =================================================================================================
''
''   File....... jm_rfid_demo.spin
''   Purpose.... Parallax Serial RFID Reader demo for the Propeller Activity Board. 
''               Use pins 16 and 17 as they have 3.9K resistors inline to prevent too much current 
''               into the pins from the 5V RFID device. Make sure that the header power jumpers are 
''               set to 5V (default), and to move the power switch to position 2 to power the headers. 
''   Author..... Jon "JonnyMac" McPhalen
''               Copyright (c) 2014 Jon McPhalen
''               -- see below for terms of use
''   E-mail..... jon@jonmcphalen.com
''   Started.... 23 SEP 2014
''   Updated.... 03 OCT 2014
''
'' =================================================================================================


con { timing }

  _clkmode = xtal1 + pll16x
  _xinfreq = 5_000_000                                          ' use 5MHz crystal

  CLK_FREQ = ((_clkmode - xtal1) >> 6) * _xinfreq               ' system freq as a constant
  MS_001   = CLK_FREQ / 1_000                                   ' ticks in 1ms
  US_001   = CLK_FREQ / 1_000_000                               ' ticks in 1us


con { io pins }

  RX1     = 31                                                  ' programming / terminal
  TX1     = 30
  
  SDA     = 29                                                  ' eeprom / i2c
  SCL     = 28

  RFID_RX = 17                                                  ' RFID on PAB
  RFID_EN = 16
  

con

  #0, LSBFIRST, MSBFIRST
  

con { pst formatting }

   #1, HOME, GOTOXY, #8, BKSP, TAB, LF, CLREOL, CLRDN, CR
  #14, GOTOX, GOTOY, CLS


obj

  term : "fullduplexserial" 
  rfid : "fullduplexserial"
  

var
          

pub main | idx

  setup                                                         ' start program objects

  repeat 
    repeat 2
      term.tx(CR)
    term.str(string("Present tag:", CR))
    
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
      term.str(string("-- Unknown tag"))

    pause(3000) 
    

pub setup

'' Setup IO and objects for application

  term.start(RX1, TX1, %0000, 115_200)                  ' terminal via programming port                 
  rfid.start(RFID_RX, RFID_RX, %1100, 2400)             ' open-drain serial for RFID                         


con

  { --------- }
  {  R F I D  }
  { --------- }


con

  LAST_TAG = 3                                                  ' tag #s are 0..LAST_TAG
  

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
'' -- returns tag index (0..LAST_TAG) if found
'' -- returns -1 if tag not found

  repeat tidx from 0 to LAST_TAG                                ' loop through known tags
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