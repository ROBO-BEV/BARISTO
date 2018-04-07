/**
 * @file     BARISTO.c
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@robobev.com
 * @updated  07 APRIL 2018
 *
 * @version 0.1
 *
 * @brief Main driver program for the Baristo coffee kiosk
 *
 * @link https://www.robobev.com
 *
 * @section DESCRIPTION
 *
 *
 */

#include <stdio.h> //Smaller then iostream file size wise

//Parallax Propeller specific functions
#include <propeller.h>


//Global Variables
private static int errorCodes[];                     //Runtime traceback error array
private static int errorNum = 0;                     //Internally used index for errorCode array

//Quick on-off toggle for debug statements
public static boolean DEBGUG_STATEMENTS_ON = false;

//Error Code Constants
public static int MAX_ERROR_CODES = 50;                      //TODO: DETERMINE THIS NUMBER
public static int NONE = 0;                                  //No errors present
public static int ERROR_CODE_NO_USB_DEVICE_CONNECTED = -1;   //No USB (aka serial) devices connected
public static int ERROR_CODE_OLD_VERSION_NUMBER = -2;        //Kiosk is not running the latest update
public static int ERROR_CODE_VERSION_NOT_RELEASED = -3;      //Code running is not public yet, HACKER!
public static int ERROR_CODE_NO_SERIAL_CONNECTION_MADE = -4; //TODO: MAYBE THE SAME AS -1 (NO_USB_DEVICE_CONNECTED)

//Operating System (OS) Constants
public static double RESIN_2_12_0_REV_1 = 2.12;
public static double WINDOWS_10_PRO_V1709 = 10.1709;
public static double UBUNTU_16_04_LTS_CLASSIC = 16.4;
public static double RASPBERRY_STRETCH_2017_11 = 2017.11
//TO-DO: public static double LINUX_FOR_TEGRA_1234.56 = 1234.5
//TO-DO: public static double UBUNTU_SNAPPY = 1.1

//Amazon Device Constants
public static int ECHO =  0;
public static int ECHO_DOT_1 = 1;
public static int ECHO_DOT_2 = 2;
//TODO: Other supported Amazon devices? Move these constants to AmazonHardware.ts 

//Kiosk Constants
public static double CURRENT_KIOSK_HW_VERSION = 0.1;
public static double LOWEST_SUPPORTED_KIOSK_HW_VERSION = 0.1;
public static int PRODUCTION = 2;
public static int FIELD = 1;
public static int TESTING = 0;
public static int OK = 0;
public static int SERIAL_PORT_0 = 0;
public static int SERIAL_PORT_1 = 1;
public static int SERIAL_PORT_2 = 2;
public static int SERIAL_PORT_3 = 3;
public static int SERIAL_PORT_4 = 4;

public static int DO_OR_DO_NOT_THERE_IS_NO_TRY = -42;


int Main(){


  return 0;
}


bool UnitTest(){

  return true;
}
