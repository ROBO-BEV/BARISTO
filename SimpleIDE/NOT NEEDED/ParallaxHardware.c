/**
 * @file     ParallaxHardware.c
 * @author   Blaze Sanders (@ROBO_BEV)TM
 * @email    blaze@robobev.com
 * @updated  07 APRIL 2018
 *
 * @version 0.1
 * @brief Manage a two way connection between Linux distro and Parallax hardware
 *
 * @section DESCRIPTION
 *
 * Compiled driver ABOVE code using "g++ DRIVER_NAME.c ParallaxHardware.c -std=c++11 -o APP_NAME"
 */

#include "BARISTO.h"
#include "ParallaxHardware.h"
#include <unistd.h>             //Standard constant and types like usleep(), close(), and pause()

void InitializeParallaxHardware(){
   ParallaxHardware->parallaxPartNumber = UNSUPPORTED_PARALLAX_PART_NUMBER;
   int parallaxParts[] = new int[DEFAULT_NUM_OF_PARALLAX_PARTS];

   for (int i = 0; i < DEFAULT_NUM_OF_PARALLAX_PARTS; i++){
     parallaxParts[i] = UNSUPPORTED_PARALLAX_PART_NUMBER;
   }//END FOR LOOP

   parallaxParts[0] = RFID_TX_RX_PN28140;
   parallaxParts[1] = ULTRASONIC_SENSOR_PN28015;
   parallaxParts[2] = ULTRASONIC_SENSOR_PN28015;
   parallaxParts[3] = ULTRASONIC_SENSOR_PN28015;

   for(int i = 0; i < DEFAULT_NUM_OF_PARALLAX_PARTS; i++){
     parallaxPartNumber = parallaxParts[i];
     InitializeParallaxHardware(ParallaxHardware->parallaxPartNumber);
   }//END FOR LOOP
}//END InitializeParallaxHardware()


void InitializeParallaxHardware(int verNum, int pPartNum){
  ParallaxHardware->parallaxPartNumber = pPartNum;

}//END TWO PARAMETER ParallaxHardware(INT, INT) CONSTRUCTOR

int IntializeRFID(int bootMode, double verNum, int parrallaxPartNumber){

  return BARISTO.OK; //Intialization OK
}


int IntializeUltrasonicSensors(int bootMode, double verNum, int parrallaxPartNumber, int numOfSensors){

  BootUpChecks(bootMode, verNum);


  switch(parrallaxPartNumber){
    case 28015:

      break;
    default;
      if(BARISTO.DEBUG_STATEMENTS_ON) printf("You attempted to use an unsupported Parallax ultrasonic sensor.");
      return UNSUPPORTED_PARALLAX_PART_NUMBER;
  }//END SWTICH STATEMENT

  return BARISTO.OK; //No errors connection made
}//END IntializeUltrasonicSensor FUNCTION


int ScanForRFIDTag(){
  //TODO char[] *tag = "0xFFFFFFFF";
  //TODO tag = ReadTag();
  //TODO SearchTagDatabase(tag);
  //TODO return BARISTO.ERROR_CODE_???

  return BARISTO.OK; //No errors connection made
}//END ScanRFID() FUNCTION

int BootUpChecks(int bootMode, double verNum){

  if (bootMode == BARISTO->PRODUCTION) BARISTO->DEBUG_STATEMENTS_ON = false;
  else BARSITO.DEBUG_STATEMENTS_ON = true;
    if (verNum > BARISTO.CURRENT_KIOSK_HW_VERSION){
      if(BARISTO.DEBUG_STATEMENTS_ON){
        printf("You attempted to run non-public and unsupported software.\n");
        printf("50 cent is coming for you :)");
      }
    }
    else if (verNum < BARISTO.LOWEST_SUPPORTED_KIOSK_HW_VERSION){
      if(DEBUG_STATEMENTS_ON){
        printf("You attempted to run old unsupported software.");
        printf("Please download newest open source code from https:\/\/github.com/ROBO-BEV/BARISTO");
      }
      return ERROR_CODE_OLD_VERSION_NUMBER;
  }
}//END BootUpChecks() FUNCTION


int[] GetDistance(int numOfSensors, int[] signalPins, char units){

  int distance = new int[MAX_NUM_OF_PING_SENSORS];
  switch(units){
    case 'M':		//Fall through capital M so that both cases are catch
    case 'm':
      for (int i = 0; i < numOfSensors; i++){
        distance[i] = ping_cm(signalPins[i]);        // Get cm distance from PINGs connection to defined GPIO pins
      }//END FOR LOOP
      if(DEBUG_STATEMENTS_ON) printf("Distance measured by PING sensor#%d = %d centimeters \n", i, distance);
      break;
    case 'I':		//Fall through capital M so that both cases are catch
    case 'i':
      //TODO COPY CODE ABOVE FOR INCHES
      break;
    default:
      assert(false); print("GetDistance() function was passed an invalid UNITS parameter");
  }//END SWITCH

  return distance;

}//END  GetDistance

