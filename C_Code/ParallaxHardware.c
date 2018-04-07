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

#include "ParallaxHardware.h"

void InitializeParallaxHardware(){
   serialPortNumber = 0;

   parallaxPartNumber = UNSUPPORTED_PARALLAX_PART_NUMBER;
   int parallaxParts[] = new int[DEFAULT_NUM_OF_PARALLAX_PARTS];

   for (int i = 0; i < DEFAULT_NUM_OF_PARALLAX_PARTS; i++){
     parallaxParts[i] = UNSUPPORTED_PARALLAX_PART_NUMBER;
   }//END FOR LOOP

   parallaxParts[0] = RFID_TX_RX_PN28140;
   parallaxParts[1] = ULTRASONIC_SENSOR_PN28015;
   parallaxParts[2] = ULTRASONIC_SENSOR_PN28015;
   parallaxParts[3] = ULTRASONIC_SENSOR_PN28015;

   operatingSystem = BARISTO.WINDOWS_10_PRO_V1709;

   for(int i = 0; i < DEFAULT_NUM_OF_PARALLAX_PARTS; i++){
     parallaxPartNumber = parallaxParts[i];
     ParallaxHardware(serialPortNumber++, parallaxPartNumber, operatingSystem);
   }//END FOR LOOP
}//END InitializeParallaxHardware()


void InitializeParallaxHardware(int portNum, int pPartNum, double opSys){
  serialPortNumber = portNum;
  parallaxPartNumber = pPartNum;
  operatingSystem = opSys;

  //TO-DO: if(Baristo.DEBUG_STATEMENTS_ON) System.out.println("The port number you selected it not available, using port number 1.");
  //TO-DO: serialPortNumber = 0;

}//END THREE PARAMETER ParallaxHardware(INT, INT, DOUBLE) CONSTRUCTOR

public int CreateFullDuplexSerialConnection(int portNum){
  //USB is a serial communication path
  //TO-DO: return Baristo.ERROR_CODE_NO_SERIAL_CONNECTION_MADE;

  return BARISTO.OK; //No errors connection made
}// END MakeSerialConnection() FUNCTION

int IntializeRFID(int bootMode, double verNum, int parrallaxPartNumber){

  return BARISTO.OK; //Intialization OK
}


int IntializeUltrasonicSensors(int bootMode, double verNum, int parrallaxPartNumber, int numOfSensors){

  BootUpChecks(bootMode, verNum);


  switch(parrallaxPartNumber){
    case 28015:

      break;
    default;
      if(Baristo.DEBUG_STATEMENTS_ON) System.out.println("You attempted to use an unsupported Parallax ultrasonic sensor.");
      return UNSUPPORTED_PARALLAX_PART_NUMBER;
  }//END SWTICH STATEMENT

  return BARISTO.OK; //No errors connection made
}//END IntializeUltrasonicSensor FUNCTION


int ScanForRFIDTag(){
  String tag = "0xFFFFFFFF";
  //TO-DO: tag = ReadTag();
  //TO-DO: SearchTagDatabase(tag);
  //TO-DO: return InfiniFill.ERROR_CODE_???

  return BARISTO.OK; //No errors connection made
}//END ScanRFID() FUNCTION

int BootUpChecks(int bootMode, double verNum){

  if (bootMode == Baristo.PRODUCTION) Baristo.DEBUG_STATEMENTS_ON = false;
  else BARSITO.DEBUG_STATEMENTS_ON = true;
    if (verNum > Baristo.CURRENT_KIOSK_HW_VERSION){
      if(Baristo.DEBUG_STATEMENTS_ON){
        System.out.println("You attempted to run non-public and unsupported software.");
        System.out.println("50 is coming for you :)");
      }
    }
    else if (verNum < Baristo.LOWEST_SUPPORTED_KIOSK_HW_VERSION){
      if(DEBUG_STATEMENTS_ON){
        System.out.println("You attempted to run old unsupported software.");
        System.out.println("Please download newest open source code from https://github.com/ROBO-BEV/Baristo.");
      }
      return ERROR_CODE_OLD_VERSION_NUMBER;
  }
}//END BootUpChecks() FUNCTION


int GetDistance(){
  int cmDist = -1;
  for (int i = 0; i < NUM_OF_PING_SENSORS; i++){
    ping_cm(hardwarePin);                 // Get cm distance from Ping)))
  }//END FOR LOOP
  print("cmDist = %d\n", cmDist);           // Display distance
  pause(200);
}//END  GetDistance

