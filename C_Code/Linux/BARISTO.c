/**
 * @file     BARISTO.c
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@robobev.com
 * @created  03 MAR 2018
 * @updated  07 APRIL 2018
 *
 * @version 0.1
 *
 * @brief Main driver program for the robotic BARISTO coffee kiosk
 *
 * @link https://www.robobev.com
 * @link http://pubs.opengroup.org/onlinepubs/7908799/xsh/unistd.h.html
 *
 * @section DESCRIPTION
 *
 * MIT license with lots of code easter eggs. Have fun!
 *
 * Compiled using "g++ BARISTO.c -std=c++11 -o RUN_BARISTO"
 */

//Robotic Beverage Technology Inc specific header files
#include "BARISTO.h"
//#include "ParallaxHardware.h"

//SimpleIDE specific header files to flash the Parallax Propeller microcontroller
#include "ping.h"          // Control the PING ultrasonic
#include "simpletools.h"   //TODO ???

#include <cassert>   //Used in UnitTest for functional testing
#include <chrono>    //High accuracy microsecond timing in unit test
#include <unistd.h>  //Standard constant and types like usleep(), close(), and pause()

using namespace std;

#define NUM_OF_ERROR_CODE 50

//Global Variables
int errorCodes[NUM_OF_ERROR_CODE];	//Runtime traceback error array
int errorNum = 0;      			//Internally used index for errorCode array

int main(int argc, char *argv[]){
  printf("Starting %s\n", argv[0]);
  UnitTest();

  //TODO:

  return 0;
}

//See BARISTO.h for further documentation on the following PUBLIC funstions:

bool UnitTest(){

  assert(true);
  assert(!false);

  time_t timer;
  time(&timer);
  struct tm UTC_minus_8_Time;
  int START_OF_YEAR_EPOCH = 1900; //Start of Unix Network Time Protocol epoch. This code will roll over At 06:28:1$

  auto start = std::chrono::high_resolution_clock::now(); //Start timer on this line of code

  usleep(1000000);				//Wait 1 second

  int PING_SENSOR_1_SIG_PIN = 17;		//Pi 3 BCM 17 = GPIO 0
  int cmDist = -1;
  for(int loopNum=0; loopNum<10; loopNum++){
    int cmDist = ping_cm(PING_SENSOR_1_SIG_PIN);//Get cm distance from Ping)))
    printf("cmDist = %d\n", cmDist);            //Display distance
    sleep(0.5);                               	//Wait 1/2 second
  }//END FOR LOOP

  return true;
}//END UnitTest() FUNCTION

//See BARISTO.h for further documentation on the following PRIVATE funstions:


/*
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
*/
