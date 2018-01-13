/**
 * @file     InfiniFill.java
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@infinifill.com
 * @updated  13 JAN 2018
 *
 * @version 0.1
 * @brief Main driver program for the InfiniFill coffee kiosk
 *
 * @section DESCRIPTION
 *
 * Yes we are programming a coffee robot in JAVA...
 */
public class InfiniFill {
 
public static boolean DEBGUG_STATEMENTS_ON = true;

/**
 * @brief Program starts running from here 
 * @param args[] Command line program input arguments
 */
 public static void main (String[] args){
   boolean programErrors = false;
   String inputArguments[] = {"2", "2.2"};

   System.out.println("InfiniFill kiosk starting up.");
   InfiniFillTerminal.GetUserInput(inputArguments);
   CongfigureInfiniFill(Integer.parseInt(inputArguments[0]), Double.parseDouble(inputArguments[1]));
   System.out.println("InfiniFill kiosk is now ready.");
   
   do {
     System.out.println("Good Morning, Jane. Please place your cup on the pad.");
     //TO-DO: errorCode = ScanRFID();
     System.out.println("One large coffee with light cream coming right up.");
     //TO-DO: switch(errorCode) break;
   }while (!programErrors); //END WHILE LOOP
 
 }//END main() FUNCTION
 
 //Error Code Constants
 public static final int ERROR_CODE_NO_USB_DEVICE_CONNECTED = -1;
 public static final int ERROR_CODE_OLD_VERSION_NUMBER = -2;      
 public static final int ERROR_CODE_VERSION_NOT_RELEASED = -3;     //Code is too new

 //Linux Distribution Constants 
 public static final double UBUNTU_16_04_LTS_CLASSIC = 16.4; 
 public static final double RASPBERRY_STRETCH_2017_11 = 2017.11; //

 //Velocio PLC Constants 
 public static final double ACE_1450 = 1450.0;
 public static final double BRANCH_1486v10 = 1486.10;

 //Kiosk Constants
 public static final double CURRENT_KIOSK_HW_VERSION = 0.1;

 
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
 private static void CongfigureInfiniFill(int bootMode, double verNum){
   //TO-DO: LinuxPLC kiosk0 = new Kiosk;
   //TO-DO: kisk0.addSystem
   VelocioPLC kisk0PLC = new VelocioPLC();
   kisk0PLC.IntializeDisplay(bootMode, verNum);
   kisk0PLC.IntializeValves(bootMode, verNum);
   kisk0PLC.IntializePumps(bootMode, verNum);
   kisk0PLC.IntializeHeaters(bootMode, verNum);
   kisk0PLC.IntializeCoffeeMachine(bootMode, verNum);
   //TO-DO: ParallaxHardware.IntializeRFID(bootMode, verNum);
   //TO-DO: ParallaxHardware.IntializeUltrasonicSensor(bootMode, verNum);
   //TO-DO: AmazonHardware.IntializeAudio(bootMode, verNum); 
   //TO-DO: OnSemiConductorHardware.IntializeCamera(bootMode, verNum); www.boofcv.org
 }//END ConfigureInfiniFill() FUNCTION

}//END InfiniFill CLASS