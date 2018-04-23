/*
  dt_toEt.c
  
  Ported from Bob Belleville's date_time_epoch.spin from
  http://obex.parallax.com/object/191

  Copyright (c) Parallax Inc 2015. All rights MIT licensed;
                see end of file.
*/  

#include "simpletools.h"
#include "datetime.h"

//int dte_toETV(int y, int mo, int d,int h,int m, int s)
int dt_toEt(datetime dt)
{
  // create a epoch time value for the given date and time
  return ( (dte_toJD(dt.y,dt.mo,dt.d) - _epoch) * 86400 ) 
            + dte_toSPD(dt.h,dt.m,dt.s);
}

/*
  TERMS OF USE: MIT License
 
  Permission is hereby granted, free of charge, to any person obtaining a
  copy of this software and associated documentation files (the "Software"),
   to deal in the Software without restriction, including without limitation
  the rights to use, copy, modify, merge, publish, distribute, sublicense,
  and/or sell copies of the Software, and to permit persons to whom the
  Software is furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.
 
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
  THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
  DEALINGS IN THE SOFTWARE.
*/

