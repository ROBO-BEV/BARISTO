/**
 * @file     BARISTO.c
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@robobev.com
 * @created  03 MAR 2018
 * @updated  27 APRIL 2018
 *
 * @version 0.1
 *
 * @brief Main driver program for the BARISTO robotic coffee kiosk
 *
 * @link https://www.robobev.com
 *
 * @section DESCRIPTION
 *
 * MIT license with lots of code easter eggs. Have fun!
 *
 */

//Robotic Beverage Technology Inc hardware specific header files
#include "BARISTO.h"
#include "RFID.h"
//TODO FIND SOURCE ONLINE #include <chrono.h>    //High accuracy microsecond timing in unit test

//SimpleIDE specific header files to flash the Parallax Propeller microcontroller
#include "simpletools.h"  //TODO ???
#include "ping.h"         //Control the PING ultrasonic or LASER distance sensor
#include "rfidser.h"      //TODO MOVE TO BARISTO.h Control Serial RFID Card Reader.
#include "mstimer.h"      //TODO High accuracy millisecond timing in unit test
#include "simplei2c.h"    //Easy to use I2C library to communicate with sensors 

//Standard C Libraries  
#include <unistd.h>       //Standard constant and types like usleep(), close(), and pause()
#include <stdlib.h>       //Used to convert Strings to Integers and TODO 
//TODO FIND SOURCE ONLINE #include <cassert>
 
int main(int argc, char *argv[])
{
  int errorCode[MAX_ERROR_CODES];     //Runtime traceback error array  
  int errorNum = 0;                   //Internally used index for errorCode array
  i2c *eeBus;                         //I2C bus ID
  rfidser *RFID_Transceiver;          //RFID transciever 

  printf("Starting %s\n", argv[0]);   //Command line argument zero is the command name
  int bootUpMode = atoi(argv[1]);
  switch(bootUpMode)
  {
    case 0: //TESTING_MODE
      printf("IN TESTING MODE");
      return UnitTest1(RFID_Transceiver);
      return UnitTest2();
      break;
    case 1: //FIELD_MODE
      printf("IN FIELD MODE");
      
      break;
    case 2: //PRODUCTION_MODE
      printf("IN PRODUCTION MODE");
      
      break;
    default: 
      printf("WTF MATE. FIRE THE MISSILES!"); 
  }//END SWITCH   
  
  return OK;
}

//See BARISTO.h for further documentation on the following PUBLIC funstions:

int UnitTest1(rfidser *RFID_Transceiver){
  
  RFID_Transceiver = rfid_open(MCU_SERIAL_PIN, RFID_ENABLE_PIN);
  char *tag =  rfid_get(RFID_Transceiver, 200);    //Attempt to scan RFID every 200 ms
  
  if(!strcmp(tag, "timed out"))             // Timed out?
    if(DEBGUG_STATEMENTS_ON) print("No smart cup RFID tag scanned");
  else if(!strcmp(tag, "70006E0299"))       // Round card ID match?
    if(DEBGUG_STATEMENTS_ON) print("Good Morning Rosie");
  else if(!strcmp(tag, "0200822A14"))       // Rectangle card ID match?
    if(DEBGUG_STATEMENTS_ON) print("Good Morning Blaze");
  else                                      // No matches?
    print("New RFID tag with ID #%s scanned. \n", tag);               //   print ID.
        
  return OK;
}//END UnitTest1() FUNCTION

int UnitTest2(){

  time_t timer;
  time(&timer);
  struct tm UTC_minus_8_Time;
  int START_OF_YEAR_EPOCH = 1900; //Start of Unix Network Time Protocol epoch. This code will roll over At 06:28:1$

  //TODO AFTER <Chrono> #included auto start = std::chrono::high_resolution_clock::now(); //Start timer on this line of code

  usleep(1000000);				//Wait 1 second

  int cmDist[3] = {UNDEFINED, UNDEFINED, UNDEFINED};
  for(int loopNum=0; loopNum<10; loopNum++){
    for(int axisNum=0; axisNum<3; axisNum++){
      cmDist[axisNum] = ping_cm(PING_SENSOR_0_SIG_PIN+axisNum);     //Get cm distance from Ping)))
      printf("cmDist#%d = %d\n", axisNum, cmDist[axisNum]);         //Display distance
    }//END INNER FOR LOOP      
    sleep(500);                               	                     //Wait 1/2 second
  }//END OUTER FOR LOOP
}//END UnitTest2() FUNCTION




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
