/**
 * @file     ParallaxHardware.java
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@infinifill.com
 * @updated  14 JAN 2018
 *
 * @version 0.1
 * @brief Manage a two way connection between Linux and prefined Parallax hardware
 *
 * @section DESCRIPTION
 *
 * Yes we are programming a coffee robot in JAVA...
 */
public class ParallaxHardware {

  private int serialPortNumber;       //Serial port number currently in use 
  private double parallaxModelNumber; //Two Parallax pieces of hardware are currently supported
  private double linuxDistribution;   //Two Linux distributions are currently supported

  //Parallax Constants
  //TO-DO: public static final int RFID_TX_RX1234 = 1234;
  //TO-DO: public static final int ULTRASONIC_SENSOR_1234 = 1234;
  //TO-DO: public static final int RFID_TAG_1234 = 1234;

  /**
   * @brief Default ParallaxHardware object constructor 
   *
   * @parm NONE
   * @section DESCRIPTION
   *
   * TO-DO:
   * 
   */
  public ParallaxHardware(){
    serialPortNumber = -1;
    parallaxModelNumber = -1;
    linuxDistribution = InfiniFill.UBUNTU_16_04_LTS_CLASSIC;    
  }


  /**
   * @brief Three parameter ParallaxHardware object constructor 
   *
   * @parm portNum Serial port you would like to use (Falls back to 1 on failure)
   * @parm pModelNum Parallax hardware part you are communicating with 
   * @parm linuxDistro Linux distribution currently on Raspberry Pi 3 B+
   *
   * @section DESCRIPTION
   *
   * TO-DO: ???
   */
  public ParallaxHardware(int portNum, double pModelNum, double linuxDistro){
    serialPortNumber = portNum;
    parallaxModelNumber = pModelNum;
    linuxDistribution = linuxDistro;    

    //TO-DO: if(InfiniFill.DEBUG_STATEMENTS_ON) System.out.println("The port number you selected it not available, using port number 1.");
    //TO-DO: serialPortNumber = 1;

  }//END VelocioPLC() DEFAULT CONSTRUCTOR

  /**
   * @brief Creat a two-way serial connection 
   * @parm portNum ID number for serial port you want to create
   *
   * @return 1 if no connections errors and ERROR_CODE_? otherwise
   */
  public int CreateFullDuplexSerialConnection(int portNum){
    //USB is a serial communication path
    
      
    //TO-DO: return InfiniFill.ERROR_CODE_NO_SERIAL_CONNECTION_MADE; 
    
    return InfiniFill.OK; //No errors connection made
  }// END MakeSerialConnection() FUNCTION

  public int IntializeRFID(int bootMode, double verNum){
  
     //TO-DO: return InfiniFill.ERROR_CODE_??? 
    
    return InfiniFill.OK; //No errors connection made
  }//END IntializeRFID() FUNCTION


  public int IntializeUltrasonicSensor(int bootMode, double verNum){

     //TO-DO: return InfiniFill.ERROR_CODE_??? 
    
    return InfiniFill.OK; //No errors connection made
  }//END IntializeUltrasonicSensor FUNCTION


  public int ScanForRFIDTag(){
  	String tag = "0xFFFFFFFF";
    //TO-DO: tag = ReadTag(); 
    //TO-DO: SearchTagDatabase(tag);

    //TO-DO: return InfiniFill.ERROR_CODE_??? 
    
    return InfiniFill.OK; //No errors connection made
  }//END ScanRFID() FUNCTION  

}//END ParallaxHardware CLASS