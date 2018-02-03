/**
 * @file     Baristo.java
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@infinifill.com
 * @updated  03 FEB 2018
 *
 * @version 0.1
 * @brief Main driver program for the Baristo coffee kiosk
 *
 * @section DESCRIPTION
 *
 * Yes we are programming a coffee robot in JAVA...
 */

public class Baristo {
 
public static boolean DEBGUG_STATEMENTS_ON = true;

/**
 * @brief Program starts running from here 
 * @param args[] Command line program input arguments
 */
 public static void main (String[] args){
   boolean programError = false;
   String inputArguments[] = {"2", "0.1"};  //TO-DO REPLACE WITH STRING OR NUMERICAL CONSTANTS
   boolean personPresent = false;

   System.out.println("Baristo kiosk starting up.");
   inputArguments = InfiniFillTerminal.GetUserInput();
   CongfigureBaristo(Integer.parseInt(inputArguments[0]), Double.parseDouble(inputArguments[1]));
   System.out.println("Baristo kiosk is now ready.");
   
   do {
     //TO-DO: personPresent = OnSemiConductorHardware.ScanForPersonPresent();
     //TO-DO: 
     //System.out.println("Good Morning, Jane. Please place your cup on the pad.");
     //TO-DO: errorCode = ParallaxHardware.ScanForRFIDTag();
     //TO-DO: errorCode = ParallaxHardware.ScanForCupPresent();
     //System.out.println("One large coffee with light cream coming right up.");
     //TO-DO: switch(errorCode) break;
     //TO-DO: System.gc(); //Ask garbage collector to run and reclaim "free" JVM memory maybe
   }while (!programError); //END WHILE LOOP


 
 }//END main() FUNCTION
 
 //Error Code Constants
 public static final int ERROR_CODE_NO_USB_DEVICE_CONNECTED = -1;   //No USB (aka serial) devices connected
 public static final int ERROR_CODE_OLD_VERSION_NUMBER = -2;        //Kiosk is not running the latest update
 public static final int ERROR_CODE_VERSION_NOT_RELEASED = -3;      //Code running is not public yet ?:)
 public static final int ERROR_CODE_NO_SERIAL_CONNECTION_MADE = -4  //TO-DO: SAME AS -1 ABOVE MAYBE

 //Linux Distribution Constants 
 public static final double UBUNTU_16_04_LTS_CLASSIC = 16.4; 
 public static final double RASPBERRY_STRETCH_2017_11 = 2017.11; 
 //TO-DO: public static final double UBUNTU_SNAPPY = 1.1

 //Amazon Device Constants
 public static final int ECHO = 0;
 public static final int ECHO_DOT_1 = 1;
 public static final int ECHO_DOT_2 = 2;
 //TO-DO: Other supported Amazon devices

 //Kiosk Constants
 public static final double CURRENT_KIOSK_HW_VERSION = 0.1;
 public static final int PRODUCTION = 2;
 public static final int FIELD = 1;
 public static final int TESTING = 0;
 public static final int OK = 1;
 public static final int DO_OR_DO_NOT_THERE_IS_NO_TRY = -999;

 /**
  * @brief Intialize all the hardware subsystems inside the Baristo kiosk
  *
  * @param bootMode Select mode kiosk should boot up in
  * @param verNum High level hardware kiosk version number 
  *
  * @section DESCRIPTION
  *
  * Possible start up modes include: PRODUCTION, FIELD, and TESTING
  * See Aligni Product Lifetime Management (PLM) software P/N ???-??????-01 to verify which parts 
  * are inside a high level hardware kiosk version number.
  *
  * The order of the intialization functions are important. All CPUs and RFID transicever must boot 
  * before any sensor or user input / output subsystem.
  */
 private static void CongfigureBaristo(int bootMode, double verNum){
                                                                    
                                                                    //CPU #1 the Raspberry Pi
   VelocioPLC kioskPLC = new VelocioPLC();                          //CPU #2
   ParallaxHardware kioskRFID = new ParallaxHardware();             //RFID #1 transciever      
   ParallaxHardware kioskUltrasonicSensor = new ParallaxHardware(); //Sensor #1                 
   int PARRALAX_PART_NUMBER = -1;                                     //Initalize parameter to pass to ParallaxHardware objects

   //Critical Subsystems
   kioskPLC.IntializeBatteries(bootMode, verNum);
   kioskPLC.IntializeSolarPanels(bootMode, verNum);
   PARRALAX_PART_NUMBER = 999; //TO-DO DETERMINE RFID PART NUMBER
   kioskRFID.IntializeRFID(bootMode, verNum, PARRALAX_PART_NUMBER);

   //Coffee Subsystems
   //Check feedstock levels (e.g water, coffee bean), test linear actuators, and test water heaters, pumps, & valves
   kioskPLC.IntializeFeedstock(bootMode, verNum);                  
   kioskPLC.IntializeHeaters(bootMode, verNum);
   kioskPLC.IntializeValves(bootMode, verNum);
   kioskPLC.IntializePumps(bootMode, verNum);
   kioskPLC.IntializeLinearActuators(bootMode, verNum);
   

   //User Input/Output Subsystems
   //Load splash screen depending on boot mode and test display  
   kioskPLC.IntializeDisplay(bootMode, verNum);

   //Sensor Subsystems
   //Test and turn on cameras, distance sensors, audio input & output hardware devices
   //Load QR code software and cache some user profiles for possible offline vending
   int numOfCameras = 4;
   int ON_SEMI_PART_NUMBER = 999; //TO-DO DETERMINE CAMERA PART NUMBER
   int numOfUltrasonicSensors = 2;
   int PARRALAX_PART_NUMBER = 888; //TO-DO DETERMINE SENSOR PART NUMBER
   int AMAZON_DEVICE_NAME = ECHO_DOT_2;
   //TO-DO: OnSemiConductorHardware.IntializeCameras(bootMode, verNum, numOfCameras, ON_SEMI_PART_NUMBER); //TO-DO  BASED OFF www.boofcv.org
   //TO-DO: InitializeQRCodeScanner(bootmode, verNum) //TO-DO CONNECT TO AWS BACK END (DEEP LENS OR DATANBASE)
   kioskUltrasonicSensor.IntializeUltrasonicSensor(bootMode, verNum, PARRALAX_PART_NUMBER);
   //TO-DO: AmazonHardware.IntializeAudio(bootMode, verNum, AmazonDeviceName); 

 }//END ConfigureBaristo() FUNCTION
 
}//END Baristo CLASS