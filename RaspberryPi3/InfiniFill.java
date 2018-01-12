/**
 * @file InfiniFill.java
 * @author     Blaze Sanders (@ROBO_BEV)
 * @email       blaze@infinifill.com
 * @updated  10 JAN 2018
 *
 * @version 0.1
 * @brief Main driver program for the InfiniFill coffee kiosk
 *
 * @section DESCRIPTION
 *
 * Yes we are programming a coffee robot in JAVA...
 */
public class InfiniFill {
 
/**
 * @brief Program starts running from here 
  * @param args[] Command line program input arguments
 */
 public static void main (String[] args){
   boolean programErrors = false;
 
   System.out.println("InfiniFill kiosk starting up.");
   //TO-DO: CongfigureInfiniFill();
   System.out.println("InfiniFill kiosk is now ready.");
   
   do {
     System.out.println("Good Morning, Jane. Please place your cup on the pad.");
     //TO-DO: errorCode = ScanRFID();
     System.out.println("One large coffee with light cream coming right up.");
    //TO-DO: switch(errorCode) break;
  }while (!INFININFILL_ERROR.PROGRAM_CRASH); //END WHILE LOOP
 
 }//END main() FUNCTION
 
 //Error Code Constants
 public static final int ERROR_CODE_NO_USB_DEVICE_CONNECTED = -1;
 public static final int ERROR_CODE_OLD_VERSION_NUMBER = -2;

 //Linux Distribution Constants 
 public static final double UBUNTU_16_04_LTS_CLASSIC = 16.4; 
 public static final double RASPBERRY_STRETCH_2017_11 = 2017.11; //

 //Velocio PLC Constants 
 public static final double ACE_1450 = 1450.0;
 public static final double BRANCH_1486v10 = 1486.10

 
 /**
  * @brief Intialize all the hardware subsystem inside the InfiniFill kiosk
  * @param bootMode Select mode kiosk shall start in
  * @param verNum High level kiosk version number of hardware being used
  * 
  * @section DESCRIPTION
  *
  * Possible start up modes include: PRODUCTION, FIELD, and TESTING
  * See PLM ???? to verfy that want parts are inside each high level kiosk version
  */
 private void ConfigureInfiniFil(int bootMode, int verNum){
   
   LinuxPLC.IntializeDisplay(bootMode, verNum);
   LinuxPLC.IntializeValves(bootMode, verNum);
   LinuxPLC.IntializePumps(bootMode, verNum);
   LinuxPLC.IntializeHeaters(bootMode, verNum);
   LinuxPLC.IntializeCoffeeMachine(bootMode, verNum);
   //TO-DO: ParallaxHardware.IntializeRFID(bootMode, verNum);
   //TO-DO: ParallaxHardware.IntializeUltrasonicSensor(bootMode, verNum);
   //TO-DO: AmazonHardware.IntializeAudio(bootMode, verNum); 
 }//END ConfigureInfiniFill() FUNCTION

}//END InfiniFill CLASS