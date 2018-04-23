/*
 * @file oledc_copy.c
 *
 * @author Matthew Matz
 *
 * @version 0.9
 *
 * @copyright Copyright (C) Parallax, Inc. 2016.  See end of file for
 * terms of use (MIT License).
 *
 * @brief 0.96-inch RGB OLED display driver component, see oledc_h. for documentation.
 *
 * @detail Please submit bug reports, suggestions, and improvements to
 * this code to editor@parallax.com.
 */

#include "oledc.h"

char TFTROTATION;

void oledc_copy(int x0, int y0, int w, int h, int x2, int y2) 
{
  while(oledc_screenLock());  
  oledc_screenLockSet();

  int x1 = x0 + w - 1;
  int y1 = y0 + h - 1;

  // check rotation, move pixel around if necessary
  switch (TFTROTATION) {
    case 1:
      gfx_swap(x0, y0);
      gfx_swap(x1, y1);
      x0 = TFTWIDTH - x0 - 1;
      x1 = TFTWIDTH - x1 - 1;
      x2 = TFTWIDTH - x2 - 1;
      break;
    case 2:
      x0 = TFTWIDTH - x0 - 1;
      y0 = TFTHEIGHT - y0 - 1;
      x1 = TFTWIDTH - x1 - 1;
      y1 = TFTHEIGHT - y1 - 1;
      x2 = TFTWIDTH - x2 - 1;
      y2 = TFTHEIGHT - y2 - 1;
      gfx_swap(x0, x1);
      gfx_swap(y0, y1);
      break;
    case 3:
      gfx_swap(x0, y0);
      gfx_swap(x1, y1);
      y0 = TFTHEIGHT - y0 - 1;
      y1 = TFTHEIGHT - y1 - 1;
      y2 = TFTHEIGHT - y2 - 1;
      break;
  }

  // Boundary check
  if ((y0 >= TFTHEIGHT) && (y1 >= TFTHEIGHT))
    return;
  if ((x0 >= TFTWIDTH) && (x1 >= TFTWIDTH))
    return;
  if (x0 >= TFTWIDTH)
    x0 = TFTWIDTH - 1;
  if (y0 >= TFTHEIGHT)
    y0 = TFTHEIGHT - 1;
  if (x1 >= TFTWIDTH)
    x1 = TFTWIDTH - 1;
  if (y1 >= TFTHEIGHT)
    y1 = TFTHEIGHT - 1;
  if (x2 >= TFTWIDTH)
    x2 = TFTWIDTH - 1;
  if (y2 >= TFTHEIGHT)
    y2 = TFTHEIGHT - 1;

  oledc_writeCommand(SSD1331_CMD_COPY, 0);
  oledc_writeCommand(x0, 0);
  oledc_writeCommand(y0, 0);
  oledc_writeCommand(x1, 0);
  oledc_writeCommand(y1, 0);
  oledc_writeCommand(x2, 0);
  oledc_writeCommand(y2, 0);

  int _tMark = CNT + (CLKFREQ / 2000);
  while(_tMark > CNT);                          // Wait for system clock target

  oledc_screenLockClr();
}

// Parts of this file are from the Adafruit GFX arduino library

/***************************************************
  This is a library for the 0.96" 16-bit Color OLED with SSD1331 driver chip
  Pick one up today in the adafruit shop!
  ------> http://www.adafruit.com/products/684
  These displays use SPI to communicate, 4 or 5 pins are required to
  interface
  Adafruit invests time and resources providing this open source code,
  please support Adafruit and open-source hardware by purchasing
  products from Adafruit!
  Written by Limor Fried/Ladyada for Adafruit Industries.
  BSD license, all text above must be included in any redistribution
 ****************************************************/

/**
 * TERMS OF USE: MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */
