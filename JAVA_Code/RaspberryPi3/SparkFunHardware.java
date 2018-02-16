/**
 * @file     SparkFunHardware.java
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    founders@robobev.com
 * @updated  15 FEB 2018
 *
 * @version 0.1
 * @brief Manage a two way connection between a CPU with serial ports and predefined SparkFun hardware
 *
 * @section DESCRIPTION
 *
 */
public class SparkFunHardware {

  private int serialPortNumber;      //Serial port number currently in use 
  private int sparkFunPartNumber;    //TO-DO: Two Parallax RFID hardware modules and one Parallax ultrasonic sensor are currently supported
  private double operatingSystem;    //Two Linux distributions and one Windows OS are currently supported

  //SparkFun Part Number Constants
  public static int DEFAULT_NUM_OF_SPARKFUN_PARTS = 1;           //THREE ultrasonic sensors & ONE RFID transciever
  public static final int RFID_TX_RX_PN09963 = 09963;            //TO-DO: Add link
  public static final int RFID_TAG_PN1234 = 1234;                //TO-DO: Find rectangular card
  /**
   * @brief Default SparkFun Hardware object constructor 
   *
   * @parm NONE
   * @section DESCRIPTION
   *
   * Constructor assumes that ??? P/N ??? is connected to a PC running 
   * Window 10 Pro version 1709 on Serial Port #0
   * 
   */
  public SparkFunHardware(){
    serialPortNumber = 0;

    int sparkfunParts[] = new int[DEFAULT_NUM_OF_SPARKFUN_PARTS];
    sparkfunParts[0] = RFID_TX_RX_PN09963;


    operatingSystem = Baristo.WINDOWS_10_PRO_V1709; 
    
    for(int i = 0; i < DEFAULT_NUM_OF_SPARKFUN_PARTS; i++){
      SparkFunHardware(serialPortNumber++, sparkfunParts[i], operatingSystem); 
    }//END FOR LOOP
      

  }//END SparkFunHardware() DEFAULT CONSTRUCTOR


  /**
   * @brief Three parameter SparkFun Hardware object constructor 
   *
   * @parm portNum Serial port you would like to use (Falls back to 0 on failure)
   * @parm sModelNum SparkFun hardware part you are communicating with 
   * @parm opSys High level OS currently running
   *
   * @section DESCRIPTION
   *
   * TO-DO: ???
   */
  public SparkFunHardware(int portNum, int sPartNum, double opSys){
    serialPortNumber = portNum;
    sparkfunPartNumber = sPartNum;
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

     //TO-DO: return InfiniFill.ERROR_CODE_??? 
    
    return Baristo.OK; //No errors connection made
  }//END IntializeUltrasonicSensor FUNCTION


  public int ScanForRFIDTag(){
  	String tag = "0xFFFFFFFF";
    //TO-DO: tag = ReadTag(); 
    //TO-DO: SearchTagDatabase(tag);

    //TO-DO: return InfiniFill.ERROR_CODE_??? 
    
    return Baristo.OK; //No errors connection made
  }//END ScanRFID() FUNCTION  


  public int DetermineCupPosition(){

  }//END DetermineCupPosition() FUNCTION

}//END ParallaxHardware CLASS