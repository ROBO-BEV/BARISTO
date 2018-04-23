/*
 * Small modifications for using text_t interface by Steve Denson.
 * Super-simple text I/O for PropGCC, stripped of all stdio overhead.
 * Copyright (c) 2012, Ted Stefanik. Concept inspired by:
 *
 *     very simple printf, adapted from one written by me [Eric Smith]
 *     for the MiNT OS long ago
 *     placed in the public domain
 *       - Eric Smith
 *     Propeller specific adaptations
 *     Copyright (c) 2011 Parallax, Inc.
 *     Written by Eric R. Smith, Total Spectrum Software Inc.
 *
 * MIT licensed (see terms at end of file)
 */
#include <ctype.h>
#include "simpletext.h"

char* _safe_gets(text_t *text, char* origBuf, int count)
{
  char* buf = origBuf;
  while (count-- > 0)
  {
      int ch = text->rxChar(text);

      if (ch == 8 || ch == 127)
      {
          if (buf > origBuf)
          {
              text->txChar(text, '\010');
              text->txChar(text, ' ');
              text->txChar(text, '\010');
              count += 1;
              buf--;
          }
          count += 1;
          continue;
      }

      text->txChar(text, ch);
      if (ch == '\r')
          text->txChar(text, '\n');

      if (ch == '\r' || ch == '\n')
          break;

      *(buf++) = ch;
  }
  *buf = 0;

  return (origBuf);
}


/* +--------------------------------------------------------------------
 * |  TERMS OF USE: MIT License
 * +--------------------------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * +--------------------------------------------------------------------
 */
