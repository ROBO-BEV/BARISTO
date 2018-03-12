'' @file    RFIS_Main.spin
'' @author  Jon "JonnyMac" McPhalen & Blaze Sanders
'' @created 23 SEP 2014 
'' @updated 12 MARCH 2018
'' @email   jon@jonmcphalen.com
'' @email   founders@robobev.com
'' 
'' @section DESCRIPTION
'' 
'' Parallax Serial RFID main driver program for PROPELLER PROJECT BOARD USB Part Number 32810 REV D
'' Use pins 25 and 26 with inline 3.9K resistors to prevent too much current 
'' into the pins from the 5V RFID device.  
''
'' Version 1.1 - Updated March 12, 2018 by Blaze Sanders for PROPELLER PROJECT BOARD USB for ROBO BEV
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
  NUM_OF_TAGS = 3                                  ' Number of RFID tags stored on CPU running this code
 
OBJ

  Term : "FullDuplexSerial" 
  RFID : "FullDuplexSerial"
  
VAR
         
  byte  tagbuf[10]                                              'RFID tag buffer

PUB Main | idx

  Setup                                                         ' Initiliaze hardware

  repeat                                                        ' Loop forever in GOG/CPU for 
    Term.str(string("Please place your smart cup on the black pad.", CR))
    
    accept_tag(@tagbuf)                                         ' Wait for tag to be place above transciever
    
    Term.str(string("Scanned Tag ID: "))                        ' Display tag string
    repeat idx from 0 to 9
      Term.tx(byte[@tagbuf][idx])
    Term.tx(CR)
    
    idx := get_tag_id(@tagbuf)                                  ' Lookup tag
    
    if (idx => 0)                                               ' Print name linked to tag
      Term.str(string("Hello "))
      Term.str(@@Names[idx])
    else
      Term.str(string("-- I can not find your smart cup."))
      Term.str(string("-- Please scan QR code to download BARISTO from the Google Play store and register with us."))

    pause(500)                                                  'Pause 0.5 seconds to reduce GOG/CPU load
    
'' @brief Configure Parallax debug terminal and Parallax RFID transciever for use
''
'' @param NONE
''
'' @return NOTHING
''
PUB Setup

  Term.start(RXpin, TXpin, %0000, 115_200)              ' Terminal via programming port                 
  RFID.start(RFID_RX, RFID_RX, %1100, 2400)             ' Open-drain serial for RFID                      

'' @brief Enables RFID reader for a few milliseconds and read tag bytes (if available) into p_buf  
''
'' @param p_buf temp memory to hold tag ID as its read from the tag for ROBO employee for QR generation
''
'' @return NOTHING
''
PUB accept_tag(p_buf) | c, idx

  bytefill(p_buf, 0, 10)                                        ' Clear old data

  RFID.rxflush                                                  ' Clear recieve buffer
  
  low(RFID_EN)                                                  ' Enable active-low RFID reader

  repeat
    c := RFID.rx
  until (c == $0A)                                              ' Wait for $0A (LF)

  repeat 10                                                     ' Recieve 10 tag bytes
    byte[p_buf++] := rfid.rx

  input(RFID_EN)                                                ' Disable reader

'' @brief Compares tag data in ram (at p_buf) with known tags
''
'' @param p_buf 
''
'' @return tag index (0..NUM_OF_TAGS) if found, -1 if tag not found
''
PRI get_tag_id(p_buf) | tidx, p_check, bidx

  repeat tidx from 0 to NUM_OF_TAGS                             ' Loop through known tags TO-DO: How do I update as new user join???
    p_check := @@Tags[tidx]                                     ' Get hub address of tag being tested
    repeat bidx from 0 to 9                                     ' Loop through bytes in tag
      if (byte[p_buf][bidx] <> byte[p_check][bidx])             ' if byte mismatch
        quit                                                    ' abort this tag
    if (bidx == 10)                                             ' if all bytes matched
      return tidx                                               ' return this tag id
      
  return -1                                                     ' return not found

  
''User RFID tag data  
''TO-DO: Generate new data by using accept_tag function above
''TO-DO: Encyrpt for transmission over the internet
DAT

  Tag0        byte      "0415148F26"
  Tag1        byte      "0415148E0C"
  Tag2        byte      "041514AEA3"
  Tag3        byte      "041514A076"          
  Tag4        byte      "0415148F26"
  Tag5        byte      "0415148F26"
  Tag6        byte      "0415148F26"
  Tag7        byte      "0415148F26"
  Tag8        byte      "0415148F26"
  Tag9        byte      "0415148F26"
  Tag10       byte      "0415148F26"
  Tag11       byte      "0415148F26"
  Tag12       byte      "0415148F26"
  Tag13       byte      "0415148F26"
  Tag14       byte      "0415148F26"
  Tag15       byte      "0415148F26"
  Tag16       byte      "0415148F26"
  Tag17       byte      "0415148F26"
  Tag18       byte      "0415148F26"
  Tag19       byte      "0415148F26"
  Tag20       byte      "0415148F26"

  Tags        word      @Tag0, @Tag1, @Tag2, @Tag3, @Tag4, @Tag5, @Tag6, @Tag7, @Tag8, @Tag9, @Tag10, @Tag11, @Tag12, @Tag13, @Tag14, @Tag15, @Tag16, @Tag17, @Tag18, @Tag19, @Tag20
 

  Name0       byte      "Luke Skywalker", 0
  Name1       byte      "Princess Leia", 0
  Name2       byte      "Darth Vader", 0
  Name3       byte      "Obi Wan Kenobi", 0
  Name4       byte      "Anakin Skywalker", 0
  Name5       byte      "Yoda", 0
  Name6       byte      "R2-D2", 0
  Name7       byte      "C3PO", 0
  Name8       byte      "Roise", 0
  Name9       byte      "Blaze Sanders", 0
  Name10      byte      "Alex", 0
  Name11      byte      "Micah", 0
  Name12      byte      "Colin", 0
  Name13      byte      "Deepali", 0
  Name14      byte      "Elon Musk", 0
  Name15      byte      "Falcon Heavy", 0
  Name16      byte      "Tesla Roadster", 0
  Name17      byte      "Model S", 0
  Name18      byte      "Model 3", 0
  Name19      byte      "Model X", 0
  Name20      byte      "Model Y", 0

  Names       word      @Name0, @Name1, @Name2, @Name3, @Name4, @Name5, @Name6, @Name7, @Name8, @Name9, @Name10, @Name11, @Name12, @Name13, @Name14, @Name15, @Name16, @Name17, @Name18, @Name19, @Name20      


PRI pause(ms) | t

'' Delay program in milliseconds

  if (ms < 1)                                                   ' delay must be > 0
    return
  else
    t := cnt - 1776                                             ' sync with system counter
    repeat ms                                                   ' run delay
      waitcnt(t += MS_001)
    

PRI high(pin)

'' Makes pin output and high

  outa[pin] := 1
  dira[pin] := 1


PRI low(pin)

'' Makes pin output and low

  outa[pin] := 0
  dira[pin] := 1


PRI toggle(pin)

'' Toggles pin state

  !outa[pin]
  dira[pin] := 1


PRI input(pin)

'' Makes pin input and returns current state

  dira[pin] := 0

  return ina[pin]
  

{{Terms of Use: MIT License

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
