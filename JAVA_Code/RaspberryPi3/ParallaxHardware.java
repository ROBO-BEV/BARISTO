/**
 * @file     ParallaxHardware.java
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@infinifill.com
 * @updated  06 FEB 2018
 *
 * @version 0.1
 * @brief Manage a two way connection between Linux and predefined Parallax hardware
 *
 * @section DESCRIPTION
 *
 * Yes we are programming a coffee robot in JAVA...
 */

import Baristo.;

public class ParallaxHardware {

  private int serialPortNumber;      //Serial port number currently in use 
  private int parallaxPartNumber;    //TO-DO: Two Parallax RFID hardware modules and one Parallax ultrasonic sensor are currently supported
  private double operatingSystem;    //Two Linux distributions and one Windows OS are currently supported

  //Parallax Part Number Constants
  public static int DEFAULT_NUM_OF_PARALLAX_PARTS = 4;           //THREE ultrasonic sensors & ONE RFID transciever
  public static final int RFID_TX_RX_PN28140 = 28140;            //www.parallax.com/product/28140
  public static final int ULTRASONIC_SENSOR_PN28015 = 28015;    //www.parallax.com/product/28015
  public static final int RFID_TAG_PN28142 = 28142;              //www.parallax.com/product/28142
  public static final int UNSUPPORTED_PARALLAX_PART_NUMBER = -420;
  /**
   * @brief Default ParallaxHardware object constructor 
   *
   * @parm NONE
   * @section DESCRIPTION
   *
   * Constructor assumes that ultrasonic sensor P/N 28140 is connected to a PC running 
   * Window 10 Pro version 1709 on Serial Port #0
   * 
   */
  public ParallaxHardware(){
    serialPortNumber = 0;

    parallaxPartNumber = -1;
    int parallaxParts[] = new int[DEFAULT_NUM_OF_PARALLAX_PARTS];
    parallaxParts[0] = RFID_TX_RX_PN28140;
    parallaxParts[1] = ULTRASONIC_SENSOR_PN28015;
    parallaxParts[2] = ULTRASONIC_SENSOR_PN28015;
    parallaxParts[3] = ULTRASONIC_SENSOR_PN28015;

    operatingSystem = Baristo.WINDOWS_10_PRO_V1709; 
    
    for(int i = 0; i < DEFAULT_NUM_OF_PARALLAX_PARTS; i++){
      parallaxPartNumber = parallaxParts[i];
      ParallaxHardware(serialPortNumber++, parallaxPartNumber, operatingSystem); 
    }//END FOR LOOP
      

  }//END ParallaxHardware() DEFAULT CONSTRUCTOR


  /**
   * @brief Three parameter ParallaxHardware object constructor 
   *
   * @parm portNum Serial port you would like to use (Falls back to 0 on failure)
   * @parm pModelNum Parallax hardware part you are communicating with 
   * @parm opSys High level OS currently running
   *
   * @section DESCRIPTION
   *
   * TO-DO: ???
   */
  public ParallaxHardware(int portNum, int pPartNum, double opSys){
    serialPortNumber = portNum;
    parallaxPartNumber = pPartNum;
    operatingSystem = opSys;    

    //TO-DO: if(Baristo.DEBUG_STATEMENTS_ON) System.out.println("The port number you selected it not available, using port number 1.");
    //TO-DO: serialPortNumber = 0;

  }//END THREE PARAMETER ParallaxHardware(INT, INT, DOUBLE) CONSTRUCTOR

  /**
   * @brief Creat a two-way serial connection 
   * @parm portNum ID number for serial port you want to create
   *
   * @return 1 if no connections errors and ERROR_CODE_? otherwise
   */
  public int CreateFullDuplexSerialConnection(int portNum){
    //USB is a serial communication path
    
      
    //TO-DO: return Baristo.ERROR_CODE_NO_SERIAL_CONNECTION_MADE; 
    
    return Baristo.OK; //No errors connection made
  }// END MakeSerialConnection() FUNCTION

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
  public int IntializeRFID(int bootMode, double verNum, int parrallaxPartNumber){
   
   return Baristo.OK; //Intialization OK
  }
  
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
  public int IntializeUltrasonicSensors(int bootMode, double verNum, int parrallaxPartNumber, int numOfSensors){
     
    BootUpChecks(bootMode, verNum);
      
    
    switch(parrallaxPartNumber){
      case 28015:
        
        break;   
      default:
        if(Baristo.DEBUG_STATEMENTS_ON) System.out.println("You attempted to use an unsupported Parallax ultrasonic sensor.");
        return UNSUPPORTED_PARALLAX_PART_NUMBER;

    }//END SWTICH STATEMENT
     
    return Baristo.OK; //No errors connection made
  }//END IntializeUltrasonicSensor FUNCTION


  public int ScanForRFIDTag(){
    String tag = "0xFFFFFFFF";
    //TO-DO: tag = ReadTag(); 
    //TO-DO: SearchTagDatabase(tag);

    //TO-DO: return InfiniFill.ERROR_CODE_??? 
    
    return Baristo.OK; //No errors connection made
  }//END ScanRFID() FUNCTION  

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
  private int BootUpChecks(int bootMode, double verNum){
    
    if (bootMode == Baristo.PRODUCTION) Baristo.DEBUG_STATEMENTS_ON = false;
    else Baristo.DEBUG_STATEMENTS_ON = true;  

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
     

}//END ParallaxHardware CLASS