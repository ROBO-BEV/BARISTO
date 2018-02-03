/**
 * @file     VelocioPLC.java 
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@infinifill.com
 * @updated  20 JAN 2018
 * 
 * @version 0.1
 * @brief Manage a two way connection between Linux and a PLC 
 * @link https://wiki.ubuntu.com/ARM/RaspberryPi
 * @link http://velocio.net/ace/
 * @link http://fazecast.github.io/jSerialComm/
 *
 * @section DESCRIPTION
 *
 * Class to make and manage connection between any Linux PC and the 
 * Velocio Programmable Logic Controller (PLC) 
 */
//TO-DO: import jSerialComm.*; 
//http://fazecast.github.io/jSerialComm/
//https://stackoverflow.com/questions/34644569/how-to-import-external-jars-in-sublime-text-2

public class VelocioPLC {
 
  private int serialPortNumber;       //Serial port number currently in use 
  private SerialPort serialPort0;     //jSerialCom Serial Port object
  private InputStream in;             //Standard Java InputStream/OutputStream interface
  private double velocioModelNumber;  //Two PLC are currently supported
  private double linuxDistribution;   //Two Linux distributions are currently supported
  
  //Velocio PLC Constants 
  public static final double ACE_1450 = 1450.0;
  public static final double BRANCH_1486v10 = 1486.10;
  
  /**
   * @brief Default VelocioPLC object constructor 
   *
   * @parm NONE
   * @section DESCRIPTION
   *
   * Initalized serial port number to 0 and assumes an 
   * ACE 1450 PLC is connected to a Raspberry Pi 3 B+ running Ubunti 16.04
   * 
   */
  public VelocioPLC(){
    serialPortNumber = 0;
    serialPort0 = SerialPort.getCommPorts("/dev/ttyS0");
    serialPort0.openPort();
    //Decrease CPU load by allowing OS to block until a value is recieved
    serialPort0.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0); 
    in = serialPort0.getInputStream();

    velocioModelNumber = ACE_1450;
    linuxDistribution = InfiniFill.UBUNTU_16_04_LTS_CLASSIC;    
  }//END VelocioPLC() DEFAULT CONSTRUCTOR
  
  /**
   * @brief Override finalize() to create destructors workaround.
   *
   * @parm NONE
   * @section DESCRIPTION
   *
   * TO-DO: ???
   * 
   */
  public void finalize(){
    serialPort0.closePort();     
    in.close();
  }//END finalize() FUNCTION 

  /**
   * @brief Three parameter VelocioPLC object constructor 
   *
   * @parm portNum Serial port you would like to use (Falls back to 1 on failure)
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

    //TO-DO: if(InfiniFill.DEBUG_STATEMENTS_ON) System.out.println("The port number you selected it not available, using port number 1.");
    //TO-DO: serialPortNumber = 1;

  }//END VelocioPLC() DEFAULT CONSTRUCTOR
  
  public int IntializeDisplay(int bootMode, double verNum){
   
   return InfiniFill.OK; //Intialization OK
  }
  
  public int IntializeValves(int bootMode, double verNum){
    
    return InfiniFill.OK; //Intialization OK
  }
  
  public int IntializePumps(int bootMode, double verNum){
    
    return InfiniFill.OK; //Intialization OK  
  }
   
  public int IntializeHeaters(int bootMode, double verNum){
   
    return InfiniFill.OK; //Intialization OK 
  }
  
  public int IntializeCoffeeMachine(int bootMode, double verNum){
    return InfiniFill.OK; //Intialization OK 
  }

  public int GetSerialPortNUmber(){
    return this.serialPortNumber;
  }

  public static void UnitTest1(String[] args){
    System.out.println("Hello world!");
    
    //TO-DO: CreateFullDuplexSerialConnection(0);
    
    //TO-DO: UpdatePLC(1.0);
  }
  
  
  /**
   * @brief Initialize two-way serial connection
   * @parm portNum ID number for serial port you want to create
   *
   * @return 1 if no connections errors and ERROR_CODE_? otherwise
   */
  public int ConfigureFullDuplexSerialConnection(int portNum, int baudRate, int newDataBits, int newStopBits, int newParity){
    //USB is a serial communication path
    for(int i = 0; i < MAX_SERIAL_PORTS; i++){
     if
    }//END FOR LOOP


    switch(portNum){
      case 0:
      break;
      case 1:
      break;
      default:
    }//END switch
      
    //TO-DO: return InfiniFill.ERROR_CODE_NO_USB_DEVICE_CONNECTED;   between Linux and PLC
    
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
 
}//END VelocioPLC CLASS