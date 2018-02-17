/**
 * @file     Baristo.java
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    founders@robobev.com
 * @updated  16 FEB 2018
 *
 * @version 0.1
 * @brief Main driver program for the Baristo coffee kiosk
 * 
 * @link https://RoboBev.com/subscribe
 * @link http://pi4j.com/install.html
 * @link https://github.com/Pi4J/pi4j
 *
 * @section DESCRIPTION
 *
 * Yes we are programming a coffee robot in JAVA..
 *
 * %%
 * Copyright (C) 2012 - 2017 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%.
 */

//Imports to use https://github.com/Pi4J/pi4j
import com.pi4j.io.gpio.*;
//import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
//import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.platform.*;
//import com.pi4j.platform.Platform;
//import com.pi4j.platform.PlatformAlreadyAssignedException;
//import com.pi4j.platform.PlatformManager;
import com.pi4j.io.i2c.*;
//import com.pi4j.io.i2c.I2CBus;
//import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

import java.io.IOException;
import java.util.*;
//import java.util.Arrays;
//import java.util.Scanner;
//import java.util.Date;

public class Baristo {
 
  //Global Variables
  private static int errorCodes[];                     //Runtime traceback error array
  private static int errorNum = 0;                     //Internally used index for errorCode array
  
  public static boolean DEBGUG_STATEMENTS_ON = false;  //Quick on-off toggle for debug statements

  
  //Error Code Constants
  public static final int MAX_ERROR_CODES = 50;                      //TO-DO: DETERMINE THIS NUMBER
  public static final int NONE = 0;                                  //No errors present
  public static final int ERROR_CODE_NO_USB_DEVICE_CONNECTED = -1;   //No USB (aka serial) devices connected
  public static final int ERROR_CODE_OLD_VERSION_NUMBER = -2;        //Kiosk is not running the latest update
  public static final int ERROR_CODE_VERSION_NOT_RELEASED = -3;      //Code running is not public yet ?:)
  public static final int ERROR_CODE_NO_SERIAL_CONNECTION_MADE = -4;  //TO-DO: SAME AS -1 ABOVE MAYBE

  //Operating System (OS) Constants
  public static final double WINDOWS_10_PRO_V1709 = 10.1709;  
  public static final double UBUNTU_16_04_LTS_CLASSIC = 16.4; 
  public static final double RASPBERRY_STRETCH_2017_11 = 2017.11; 
  //TO-DO: public static final double LINUX_FOR_TEGRA_1234.56 = 1234.56; 
  //TO-DO: public static final double UBUNTU_SNAPPY = 1.1 

  //Amazon Device Constants
  public static final int ECHO =  0;
  public static final int ECHO_DOT_1 = 1;
  public static final int ECHO_DOT_2 = 2;
  //TO-DO: Other supported Amazon devices and move this to AmazonHardware.java

  //Kiosk Constants
  public static final double CURRENT_KIOSK_HW_VERSION = 0.1;
  public static final double LOWEST_SUPPORTED_KIOSK_HW_VERSION = 0.1;
  public static final int PRODUCTION = 2;
  public static final int FIELD = 1;
  public static final int TESTING = 0;
  public static final int OK = 0;
  public static final int SERIAL_PORT_0 = 0;
  public static final int SERIAL_PORT_1 = 1;
  public static final int SERIAL_PORT_2 = 2;
  public static final int SERIAL_PORT_3 = 3;
  public static final int SERIAL_PORT_4 = 4; 

  public static final int DO_OR_DO_NOT_THERE_IS_NO_TRY = -42;
    
  /**
   * @brief Program starts running from here 
   * @param args[] Command line program input arguments
   */
  public static void main (String[] args){
   
    //Create General Purpose Input / Output (GPIO) controller
    final GpioController gpio = GpioFactory.getInstance();
    

    //TO-DO: PUT NEXT ???12??? LINES OF CODE INTO ITS OWN FUNCTION
    //Set GPIO Pin #1 (P1 = BCM?) as an input with internal pull up resistor enabled
    final GpioPinDigitalInput powerButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP);
    powerButton.setDebounce(20); //20 milliseconds
    

    final GpioPinDigitalOutput ultrasonicPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);



    //Create and register gpio pin listeners
    powerButton.addListener(new GpioPinListenerDigital() {
      @Override
      public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        //Display pin state on console
        System.out.println("[" + new Date() + "] --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
      }//END handleGpioPinDigitalStateChangeEvent() FUNCTION

    });


    //Power button is a Normallly Open (N.O.) button pulled HIGH
    while(powerButton.isHigh()){ 
      Thread.sleep(500); //Pause for 500 milliseconds 
    }//END WHILE LOOP

    System.out.println("Baristo kiosk starting up.");

    String inputArguments[] = {"2", "0.1"};  //TO-DO: REPLACE WITH STRING OR NUMERICAL CONSTANTS
   
    boolean personPresent = false;           //TO-DO: Computer Vision variable 
   
    //Variables to track the number of program errors during run time 
    boolean programError = false;                        
    errorCodes = new int[MAX_ERROR_CODES];                   

    inputArguments = EngineeringTerminal.GetUserInput();
    errorCodes[errorNum++] = CongfigureBaristo(Integer.parseInt(inputArguments[0]), Double.parseDouble(inputArguments[1]));

    System.out.println("Baristo kiosk is now ready.");
    
    do {
      //TO-DO: personPresent = OnSemiConductorHardware.ScanForPersonPresent();
      //TO-DO: https://aws.amazon.com/developers/getting-started/java/
      //System.out.println("Good Morning, Jane. Please place your cup on the pad.");
      //TO-DO: errorCode = ParallaxHardware.ScanForRFIDTag();
      //TO-DO: errorCode = ParallaxHardware.ScanForCupPresent();
      //System.out.println("One large coffee with light cream coming right up.");
      //TO-DO: switch(errorCode) break;
      //TO-DO: System.gc(); //Ask garbage collector to run and reclaim "free" JVM memory maybe
    }while (!programError); //END WHILE LOOP

}//END main() FUNCTION
 

  /**
   * @brief Intialize all the hardware subsystems inside the Baristo kiosk
   *
   * @param bootMode Select mode kiosk should boot up in
   * @param verNum High level hardware kiosk version number 
   *
   * @return Zero if no error, One otherwise
   * @section DESCRIPTION
   *
   * Possible start up modes include: PRODUCTION, FIELD, and TESTING
   * See Aligni Product Lifetime Management (PLM) software P/N ???-??????-01 to verify which parts 
   * are inside a high level hardware kiosk version number.
   *
   * The order of the intialization functions are important. All CPUs and RFID transicever must boot 
   * before any sensor or user input / output subsystem.
   */
  private static int CongfigureBaristo(int bootMode, double verNum){
                                                                
                                                                    //CPU #1 the Surface Book, NVIVDA TX2, or Raspberry Pi
    VelocioPLC kioskPLC = new VelocioPLC(SERIAL_PORT_0, VelocioPLC.ACE_1450, WINDOWS_10_PRO_V1709);                          //CPU #2
    ParallaxHardware kioskRFID = new ParallaxHardware(SERIAL_PORT_1, ParallaxHardware.RFID_TX_RX_PN28140, WINDOWS_10_PRO_V1709);             //RFID #1 transciever   
    int numOfUltrasonicSensors = 2;   
    ParallaxHardware kioskUltrasonicSensors = new ParallaxHardware(SERIAL_PORT_2, ParallaxHardware.ULTRASONIC_SENSOR_PN1234, WINDOWS_10_PRO_V1709); //Sensor #1                 
   

    //Critical Subsystems
    errorCodes[errorNum++] = kioskPLC.IntializeBatteries(bootMode, verNum);
    errorCodes[errorNum++] = kioskPLC.IntializeSolarPanels(bootMode, verNum);
    errorCodes[errorNum++] = kioskRFID.IntializeRFID(bootMode, verNum, ParallaxHardware.RFID_TX_RX_PN28140);

    //Coffee Subsystems
    //Check feedstock levels (e.g water, coffee bean), test linear actuators, and test water heaters, pumps, & valves
    kioskPLC.IntializeFeedstock(bootMode, verNum);                  
    kioskPLC.IntializeHeaters(bootMode, verNum);
    kioskPLC.IntializeValves(bootMode, verNum);
    kioskPLC.IntializePumps(bootMode, verNum);
    kioskPLC.IntializeLinearActuators(bootMode, verNum);
   

    //User Input/Output Subsystems
    //Load splash screen depending on boot mode and test display  
    //TO-DO: Remove now that we are using Surface Book kioskPLC.IntializeDisplay(bootMode, verNum);

    //Sensor Subsystems
    //Test and turn on cameras, distance sensors, audio input & output hardware devices
    //Load QR code software and cache some user profiles for possible offline vending
    int numOfCameras = 4;
    int ON_SEMI_PART_NUMBER = 999;         //TO-DO: Move this to OnSemiHardware.java


    //TO-DO: OnSemiConductorHardware.IntializeCameras(bootMode, verNum, numOfCameras, ON_SEMI_PART_NUMBER); //TO-DO  BASED OFF www.boofcv.org
    //TO-DO: InitializeQRCodeScanner(bootmode, verNum) //TO-DO CONNECT TO AWS BACK END (DEEP LENS OR DATANBASE)

    kioskUltrasonicSensors.IntializeUltrasonicSensors(bootMode, verNum, ParallaxHardware.ULTRASONIC_SENSOR_PN1234, numOfUltrasonicSensors);
   
    int AMAZON_DEVICE_NAME = ECHO_DOT_2;   //TO-DO: Move this to AmazonHardware.java
    //TO-DO: AmazonHardware.IntializeAudio(bootMode, verNum, AmazonDeviceName); 
   
    return OK;
 }//END ConfigureBaristo() FUNCTION
 
}//END Baristo CLASS