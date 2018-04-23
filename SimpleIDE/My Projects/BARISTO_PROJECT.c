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
 *
 * @section DESCRIPTION
 *
 * MIT license with lots of code easter eggs. Have fun!
 *
 * Compiled using "g++ BARISTO.c -std=c++11 -o RUN_BARISTO"
 */

//Robotic Beverage Technology Inc specific header files
#include "BARISTO.h"

//SimpleIDE specific header files to flash the Parallax Propeller microcontroller
#include "simpletools.h"   //TODO ???
#include "ping.h"          //Control the PING ultrasonic or LASER distance sensor
#include "fdserial.h"      //Contrrol Serial RFID Card Reader.
#include "mstimer.h"       //TODO High accuracy millisecond timing in unit test
#include "simplei2c.h"     //

//#include <assert.h>   //Used in UnitTest for functional testing
//TODO FIND SOURCE ONLINE #include <chrono.h>    //High accuracy microsecond timing in unit test

#include <unistd.h>  //Standard constant and types like usleep(), close(), and pause()

//Global Variables
extern bool DEBGUG_STATEMENTS_ON = true;  //Quick ON-OFF Toggle of Debug Statements
extern int MAX_ERROR_CODES = 10;          //Traceback error array size
extern int errorNum = 0;      			         //Internally used index for errorCode array
i2c *eeBus;                               // I2C bus ID \
rfidser *RFID_Scanner;

//Global Constants
static int PRODUCTION = 2;  //volatile?

 
int main(int argc, char *argv[])
{
  int errorCode[MAX_ERROR_CODES];        //Runtime traceback error array  
  printf("Starting %s\n", argv[0]);      //Command line argument zero is the command name
  int bootUpMode = int(argv[1]);
  switch(bootUpMode)
  {
    case 0:
      printf("IN TESTING MODE");
      int testOK = UnitTest();
      break;
    case 1:
      printf("IN FIELD MODE");
      
      break;
    case PRODUCTION:
      printf("IN PRODUCTION MODE");
      
      break;
    default: 
      printf("WTF MATE. FIRE THE MISSILES!"); 
  }//END SWITCH    
  

  //TODO:

  return 0;
}

//See BARISTO.h for further documentation on the following PUBLIC funstions:

int UnitTest(){
/*
  assert(true);
  assert(!false);

  time_t timer;
  time(&timer);
  struct tm UTC_minus_8_Time;
  int START_OF_YEAR_EPOCH = 1900; //Start of Unix Network Time Protocol epoch. This code will roll over At 06:28:1$

  //TODO AFTER <Chrono> #included auto start = std::chrono::high_resolution_clock::now(); //Start timer on this line of code

  usleep(1000000);				//Wait 1 second
*/
  int PING_SENSOR_1_SIG_PIN = 17;		//Pi 3 BCM 17 = GPIO 0
  int cmDist = -1;
  for(int loopNum=0; loopNum<10; loopNum++){
    int cmDist = ping_cm(PING_SENSOR_1_SIG_PIN);//Get cm distance from Ping)))
    printf("cmDist = %d\n", cmDist);            //Display distance
    sleep(0.5);                               	//Wait 1/2 second
  }//END FOR LOOP


  int RFID_1_SERIAL_PIN = ??;
  int tag = -1;
  
  RFID_Scanner = rfid_open(MCU_SERIAL_PIN_0, RFID_1_ENABLE_PIN)
  
  return 1;
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
