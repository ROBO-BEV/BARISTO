/*
 * @file SimpleTerm_close.c
 * SimpleTerm close is rarely used.
 *
 * Copyright (c) 2013, Parallax Inc.
 * Written by Steve Denson
 */
#include "serial.h"

extern HUBDATA terminal *dport_ptr;

void simpleterm_close()
{
  extern text_t *dport_ptr;
  if(!dport_ptr)
    return;
  serial_close(dport_ptr);
  dport_ptr = 0;
}

terminal *simpleterm_reopen(int rxpin, int txpin, int mode, int baud)
{
  simpleterm_close();
  dport_ptr = serial_open(rxpin, txpin, mode, baud);
  return dport_ptr;
}

/*
+--------------------------------------------------------------------
| TERMS OF USE: MIT License
+--------------------------------------------------------------------
Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files
(the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge,
publish, distribute, sublicense, and/or sell copies of the Software,
and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
+--------------------------------------------------------------------
*/

