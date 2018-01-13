/**
 * @file     VelocioPLC.java 
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@infinifill.com
 * @updated  12 JAN 2018
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
public class VelocioPLC {
 
  private int serialPortNumber;       //Serial port number currently in use 
  private double velocioModelNumber;  //Two PLC are currently supported
  private double linuxDistribution;      //Two Linux distributions are currently supported
  
  public boolean DEBUG_STATEMENTS_ON = true; //Toogle debug print statements

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
  public VelocioPLC(){
    serialPortNumber = -1;
    velocioModelNumber = InfiniFill.ACE_1450;
    linuxDistribution = InfiniFill.UBUNTU_16_04_LTS_CLASSIC;    
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
  public VelocioPLC(int portNum, double plcModelNum, double linuxDistro){
    serialPortNumber = portNum;
    velocioModelNumber = plcModelNum;
    linuxDistribution = linuxDistro;    

    if(DEBUG_STATEMENTS_ON) System.out.println("The port number you selected it not available, using port number 1.");
    serialPortNumber = 1;

  }//END LinuxPLC() DEFAULT CONSTRUCTOR
  
  public int IntializeDisplay(int bootMode, double verNum){
   
   return 0; //Intialization OK
  }
  
  public int IntializeValves(int bootMode, double verNum){
    
    return 0; //Intialization OK
  }
  
  public int IntializePumps(int bootMode, double verNum){
    
    return 0; //Intialization OK  
  }
   
  public int IntializeHeaters(int bootMode, double verNum){
   
    return 0; //Intialization OK 
  }
  
  public int IntializeCoffeeMachine(int bootMode, double verNum){
    return 0; //Intialization OK 
  }

  public int GetSerialPortNUmber(){
    return this.serialPortNumber;
  }

  public static void UnitTest1(String[] args){
    System.out.println("Hello world!");
    
    //TO-DO: MakeSerialConnection(0);
    
    //TO-DO: UpdatePLC(1.0);
  }
  
  
  /**
   * @brief Initialize two-way serial connection between Linux and PLC
   * @parm portNum ID number for serial port you want to create
   *
   * @return 1 if no connections errors and ERROR_CODE_? otherwise
   */
  public int MakeSerialConnection(int portNum){
    //USB is a serial communication path
    
      
    //TO-DO: return InfiniFill.ERROR_CODE_NO_USB_DEVICE_CONNECTED; 
    
    return 1; //No errors connection made
  }// END MakeSerialConnection() FUNCTION
    
  /**
   * @brief Update software running on the Veloicio 1450 PLC
   * @parm newVerNum Version number of new software to install
   *
   * @return 1 if update was sucessful errors and ERROR_CODE_? otherwise
   */
 public int UpdatePLC(double newVerNum){
   
   boolean updateError = false;
   int updateErrorCode = 0;

   if(!ValidVersionNumber(newVerNum)) updateError = true;
   if(newVerNum == 0.1 && !updateError){
   
   }
   else if(newVerNum == 0.2 && !updateError){
   
   }
   else{
     updateErrorCode = InfiniFill.ERROR_CODE_VERSION_NOT_RELEASED;
   }//END if-elseif-else BLOCK

   if(!updateError) return 1; 
   else return updateErrorCode; 

 }//END UpdatePLC() FUNCTION
 
 private boolean ValidVersionNumber(double newVerNum){
   //TO-DO: InfiniFill.ERROR_CODE_OLD_VERSION_NUMBER;
   return true;
 }//END CheckVersionNumber() FUNCTION
 
}//END LinuxPLC CLASS