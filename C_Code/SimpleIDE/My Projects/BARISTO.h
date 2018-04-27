/**
 * @file     BARISTO.h
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
 * @link htttps://www.upverter.com/??? TODO
 *
 * @section DESCRIPTION
 *
 * MIT license with lots of code easter eggs. Have fun!
 */

//Standard C libraries 
#include <stdio.h>    //Like <iostream> but smaller file size
#include <stdbool.h>  //Needed to use bool variables in C code

//SimpleIDE specific header files to flash the Parallax Propeller microcontroller
#include <propeller.h>

//Error Code Constants
#define NO_ERROR 0;                              //No errors present
#define ERROR_CODE_NO_USB_DEVICE_CONNECTED -1;   //No USB (aka serial) devices connected
#define ERROR_CODE_OLD_VERSION_NUMBER -2;        //Kiosk is not running the latest update
#define ERROR_CODE_VERSION_NOT_RELEASED -3;      //Code running is not public yet, HACKER!
#define ERROR_CODE_NO_SERIAL_CONNECTION_MADE -4; //TODO: MAYBE THE SAME AS -1 (NO_USB_DEVICE_CONNECTED)

//Operating System (OS) Constants
#define RESIN_2_12_0_REV_1 2.12;
#define WINDOWS_10_PRO_V1709 10.1709;
#define UBUNTU_16_04_LTS_CLASSIC 16.4;
#define RASPBERRY_STRETCH_2017_11 2017.11
#define LINUX_FOR_TEGRA_1234_56 1234.56
#define UBUNTU_SNAPPY 1.1

//Amazon Device Constants
#define ECHO 1;
#define ECHO_DOT_2 2;
//TODO: Other supported Amazon devices? Move these constants to AmazonHardware.ts

//Kiosk Constants
#define CURRENT_KIOSK_HW_VERSION 0.1;
#define LOWEST_SUPPORTED_KIOSK_HW_VERSION 0.1;
//#define PRODUCTION 2;
#define FIELD 1;
#define TESTING 0;
#define OK 1;
#define SERIAL_PORT_0 0;
#define SERIAL_PORT_1 1;
#define SERIAL_PORT_2 2;
#define SERIAL_PORT_3 3;

#define MCU_SERIAL_PIN_0 = 0;
#define MCU_SERIAL_PIN_1 = 1;
#define MCU_SERIAL_PIN_2 = 2;

#define RFID_1_ENABLE_PIN = 18;  //Parallax pin ???

#define DO_OR_DO_NOT_THERE_IS_NO_TRY -42;

int UnitTest();

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
