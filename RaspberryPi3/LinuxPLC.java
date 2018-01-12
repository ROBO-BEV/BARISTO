/**
 * @file LinuxPLC.java 
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@infinifill.com
 * @updated  11 JAN 2018
 * 
 * @version 0.1
 * @brief Manage a two way connection between Linux and a PLC 
 * @link https://wiki.ubuntu.com/ARM/RaspberryPi
 * @link http://velocio.net/ace/
 *
 * @section DESCRIPTION
 *
 * Class to make and manage connection between any Linux PC and the 
 * Velocio Programmable Logic Controller (PLC) 
 */
 
import STATUS_CODES;

public class LinuxPLC {
 
  private int serialPortNumber;   //Serial port number currently in use 
  private int velocioModelNumber; //Two PLC are currently supported
  private int linuxDistribution;  //Two Linux distributions are currently supported
  private boolean LOCAL_DEBUG_ON = true; //Toogle debug print statement for this object
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
   * @brief Default LinuxPLC object constructor 
   *
   * @parm NONE
   * @section DESCRIPTION
   *
   * Initalized serial port number to -1 and assumes an 
   * ACE 1450 PLC is connected to a Raspberry Pi 3 B+ running Ubunti 16.04
   * 
   */
  public LinuxPLC(){
    serialPortNumber = -1;
    velocioModelNumber = ACE_1450;
    linuxDistribution = UBUNTU_16_04_LTS_CLASSIC;    
  }//END LinuxPLC() DEFAULT CONSTRUCTOR

  /**
   * @brief Three parameter LinuxPLC object constructor 
   *
   * @parm portNum Serial port would like to use (Falls back to 1 on failure)
   * @parm plcModelNum Velocio PLC you are communicating with 
   * @parm linuxDistro Linux distribution currently on Raspberry Pi 3 B+
   *
   * @section DESCRIPTION
   *
   * TO-DO: ???
   */
  public LinuxPLC(int portNum, double plcModelNum, double linuxDistro){
    serialPortNumber = portNum;
    velocioModelNumber = plcModelNum;
    linuxDistribution = linuxDistro;    

    if(LOCAL_DEBUG_ON) System.out.println("The port number you selected it not available, using port number 1.");
    serialPortNumber = 1; //


  }//END LinuxPLC() DEFAULT CONSTRUCTOR


  public int GetSerialPortNUmber(){
    return this.serialPortNumber;
  }

  public static void UnitTest1(String[] args){
    System.out.println("Hello world!");
    
    MakeSerialConnection(0);
    
    UpdatePLC(1.0);
  }
  
  
  /**
   * @brief Initialize two-way serial connection between Linux and PLC
   * @parm portNum ID number for serial port you want to create
   *
   * @return 1 if no connections errors and ERROR_CODE_? otherwise
   */
  public int MakeSerialConnection(int portNum){
    //USB is a serial communication path
    
      
      return ERROR_CODE_NO_USB_DEVICE_CONNECTED; 
    
    return 1; //No errors connection made
  }// END MakeSerialConnection() FUNCTION
    
  /**
   * @brief Update software running on the Veloicio 1450 PLC
   * @parm newVerNum Version number of new software to install
   *
   * @return 1 if update was sucessful errors and ERROR_CODE_? otherwise
   */
 public int UpdatePLC(double newVerNum){
   
   int updatePLC_ErrorCode = 0;
   
   if(newVerNum == 0.1){
   
   }
   else if(newVerNum == 0.2){
   
   }
   else{
   
   }//END if-elseif-else BLOCK
 }//END UpdatePLC() FUNCTION
 
   if(updatePLC_ErrorCode == 0) return 1;
   else return updatePLC_ErrorCode;
 }//END Update() FUNCTION
 
 
 
 private CheckVersionNumber(double newVerNum){
 
 
   return ERROR_CODE_OLD_VERSION_NUMBER;
 }//END CheckVersionNumber() FUNCTION
 
}//END LinuxPLC CLASS