/**
 * @file     ParallaxHardware.h
 * @author   Blaze Sanders (@ROBO_BEV)TM
 * @email    blaze@robobev.com
 * @updated  07 APRIL 2018
 *
 * @version 0.1
 * @brief Manage a two way connection between Linux distro and Parallax hardware
 *
 * @section DESCRIPTION
 *
 *
 */

#ifndef PARALLAX_HARDWARE_H
#define PARALLAX_HARDWARE_H

#if defined(__cplusplus)
extern "C" {
#endif

#include "BARISTO.h"
#include "ping.h"          //Control the PING ultrasonic
#include "simpletools.h"   //TODO: ???

#include <stdio.h> //Like <iostream> but smaller file size
//TODO FIND SOURCE ONLINE #include <cassert> //Used in UnitTest for functional testing
//TODO FIND SOURCE ONLINE #include <chrono>  //Used for high accuracy (microsecond) timing of events


//Parallax Part Number Constants
#define DEFAULT_NUM_OF_PARALLAX_PARTS 4;       //THREE ultrasonic sensors & ONE RFID transciever
#define NUM_OF_PING_SENSORS  3;	               //Number of PING sensors in BARISTO coffee kiosk
#define MAX_MUN_OF_PING_SENSORS 10;            //Max number of PINGs a Pi 3 can control to resoltuion of 5 mm TODO  FIND MAX WITH TESTING
#define NUM_OF_RFID_TRANSCIEVERS 1;	       //Number of RFID scanners in BARISTO coffee kiosk
#define MAX_MUN_OF_RFID_TRANSCIEVERS 3;        //Max number of RFIDs a Pi 3 can control  with a delay on less then 200 ms TODO FIND MAX WITH TESETING
#define RFID_TX_RX_PN28140 28140;              //www.parallax.com/product/28140
#define ULTRASONIC_SENSOR_PN28015 28015;       //www.parallax.com/product/28015
#define RFID_TAG_PN28142 28142;                //www.parallax.com/product/28142
#define UNSUPPORTED_PARALLAX_PART_NUMBER -1;   //

//Global Variables
extern bool DEBUG_STATEMENTS_ON;


typedef struct ParallaxHardwareStruct
{
  unsigned int verisonNumber;         //0.1, 1.1, 2,6,
  unsigned int parallaxPartNumber;    //TO-DO: Two Parallax RFID hardware modules and one Parallax ultrasonic sensor are currently supported
} ParallaxHardware_t;


//PUBLIC Function Prototypes

/**
 * @brief Default ParallaxHardware object/struct constructor
 *
 * @parm NONE
 *
 * @section DESCRIPTION
 *
 * Assumes that one ultrasonic sensor P/N 28140 is connected Parallax Proppeller chip
 *
 */
void InitializeParallaxHardware();

/**
 * @brief Three parameter ParallaxHardware object/struct constructor
 *
 * @parm verNum TODO
 * @parm pPartNum Parallax hardware part you are communicating with
 *
 * @section DESCRIPTION
 *
 * TO-DO: ???
 */
void InitializeParallaxHardware(int verNum, int pPartNum);

/**
 * @brief Setup communication between PC and RFID module installed in kiosk
 *
 * @param bootMode Select mode kiosk should boot up in
 * @param verNum High level hardware kiosk version number 
 * @parm parrallaxPartNumber Part number for RFID module installed in kiosk
 *
 * @section DESCRIPTION
 *
 * TO-DO: ???
 */
int IntializeRFID(int bootMode, double verNum, int parrallaxPartNumber);

/**
 * @brief Setup communication between PC and ultrasonic module installed in kiosk
 *
 * @param bootMode Select mode kiosk should boot up in
 * @param verNum High level hardware kiosk version number
 * @parm parrallaxPartNumber Part number for ultrasonic module installed in kiosk
 * @parm numOfSensors Number of ultrasonic modules installed in kiosk
 *
 * @section DESCRIPTION
 *
 * TO-DO: ???
 */
int IntializeUltrasonicSensors(int bootMode, double verNum, int parrallaxPartNumber, int numOfSensors);


/** TODO
 *
 */
int ScanForRFIDTag();

/**
 * @brief Perform check on kioks software version and debug print statement settings
 *
 * @param bootMode Select mode kiosk should boot up in
 * @param verNum High level hardware kiosk version number
 *
 * @section DESCRIPTION
 *
 * TO-DO: ???
 */
int BootUpChecks(int bootMode, double verNum);

//PRIVATE Function Prototypes
//TODO:

/**
 * @brief Get the distance in cm to closet object to PING sensor
 *
 * @param numOfSensors Number of physical PING sensors you need measurements from
 * @param units Units (M = Metric meters or I = Imperial Inches) of measured distance
 * @parmn signalPins[] The physical BCM pin each PING sensors data/signal is connected to
 * @section DESCRIPTION
 *
 * Resolution of 5 mm is possible on the Pi 3 with this code.
 * More powerful devices such as the NVIDIA TX2 will have an even
 * smaller measurement resolution.
 *
 * @return Distances to the closet object visble to each PING sensor
 */
int GetDistance(int numOfSensors, int signalPins[], char units);

#endif //__cplusplus
#endif //PARALLAX_HARDWARE_H
