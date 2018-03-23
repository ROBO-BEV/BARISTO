import * as Typings from "@4th-law/typings"
import * as rpio from "rpio"
//import * as mathjs from "mathjs" DONT NEED THIS AT LUMENORA

const DEBUG_STATEMENTS_ON = true;  //Toggle console debug messages on and off

//Pin value constants
const LOW =  0   
const HIGH = 1
const UNDEFINED = -1
//TO-DO: const PWM_TWO_PERCENT to PWM_HUNDRED_PERCENT in two percent steps

//Button state constants
const PRESSED = 1
const NOT_PRESSED = 0
const HELD = 2

//Ping Ultrasonic Sensor constants
const INVALID_STATE = -1
const PING_SENT = 0
const PING_RECIEVED = 1
const MM_SOUND_MSL_CONSTANT = 22600       //TO-DO: Figure out why?

//Pin direction constants
const INPUT_PIN = 0
const OUTPUT_PIN = 1
//TO-DO: const PWM 2

//Raspberry Pi B+ refernce pin constants as defined in ???rc.local script???
const NUM_GPIO_PINS = 8                   //Outputs: GPO0 to GPO3 Inputs: GPI0 to GPI3
const MAX_NUM_A_OR_B_PLUS_GPIO_PINS = 40  //Pins 1 to 40 on Raspberry Pi A+ or B+ 
const MAX_NUM_A_OR_B_GPIO_PINS = 26       //Pins 1 to 26 on Raspberry Pi A or B 
const NUM_OUTPUT_PINS = 4                 //This software instance of Raspberry Pi can have up to four output pins
const NUM_INPUT_PINS = 4                  //This software instance of Raspberry Pi can have up to four input pins
const NUM_INPUT_OUTPUT_PINS = 3           //This software instance of Raspberry Pi can have up to two combined input & outpins pins

const GPI1 = 1                            //Raspberry Pi Connector J? P? 
const GPI2 = 2                            //Raspberry Pi Connector J? P? 
const GPI3 = 3                            //Raspberry Pi Connector J? P? 
const GPI4 = 4                            //Raspberry Pi Connector J? P? 
const GPO1 = 5                            //Raspberry Pi Connector J? P? 
const GPO2 = 6                            //Raspberry Pi Connector J? P? 
const GPO3 = 7                            //Raspberry Pi Connector J? P? 
const GPO4 = 8                            //Raspberry Pi Connector J? P? 
const GPIO1 = 9                           //Raspberry Pi Connector J? P? 
const GPIO2 = 10                          //Raspberry Pi Connector J? P? 
const GPIO3 = 11                          //Raspberry Pi Connector J? P? 


//??? Whys is this code both in index.ts and PING_28015.ts  
export default class PING_28015 extends Typings.Sensor {
  protected _category = "SENSOR" as NProfile.ECategory; // sets where the information is coming from/going to (Sensor or Actuator)
  protected _type = "ELECTRICAL" as NProfile.EType;     // indicates what type of Sensor/Actuator the information is from/for
  protected _make = "Parallax";                         // indicates the manufacturer of the Sensor/Actuator
  protected _model = "PING";                            // indicates the model type (e.g. make: Ford, model: F150)
  protected _id = "28015";                         // indicates the specific product ID (like a car's VIN)
  protected _version = "v1.0.0";                        // indicates the 4th Law profile version
  protected _sampleFrequency = 600;                     // sets the rate at which data is retrieved or sent (optional parameter)
  protected _lastDataPoint: any;

  /*
   * @see index.ts
   */ 
  constructor(pin = GPIO1){
    super();
    console.log("PING 28015 constructor()")
  }//END ONE PARAMETER CONSTRUCTOR

  
  /** TO-DO: CHANGE pin and return type to Uint8Array https://developer.mozilla.org/en-US/docs/Web/JavaScript/Typed_arrays
   * @brief Calculate the distance to the closet object to the PING ultracsonic sensor
   * 
   * @note Interrupts aren't supported by the underlying hardware, so events may be missed during the 1ms poll window.  The best we can do is to print the current state after a event is detected.
   *
   * @param pin Raspberry Pi B+ combine INPUT & OUTPUT pin name constant (i.e. GPIO1, GPIO2, GPIO3) 
   * 
   * @return Distance to closet object to resolution of 5 mm
   */
  public GetDistanceMM(pin = GPIO1): number{
    //Toggle SIG pin high to start measurement process
    rpio.write(pin, rpio.HIGH)     
    rpio.usleep(1)                              //=usleep(73) Community benchmarks suggest that the cost for usleep() is 72 microseconds on raspi-3
    rpio.write(pin, rpio.LOW)  

    //Switch SIG pin from output pint to input pin
    rpio.open(pin, rpio.INPUT, rpio.PULL_DOWN)  //Calls rpio.init([options]) automatically

    //Start waiting for echo bounce back
    var echoTime = rpio.poll(pin, PollPING)     //Is this going to read fast enough to get 5 mm resoltuion
    while(echoTime != INVALID_STATE)
      rpio.usleep(1)                            //Pause to reduce CPU load
    return (echoTime * MM_SOUND_MSL_CONTSTANT)  //Varying depending on air density (MSL = Mean Sea Level)
  }
    

  /** TO-DO: CHANGE pin and return type to Uint8Array https://developer.mozilla.org/en-US/docs/Web/JavaScript/Typed_arrays
   * @brief Calculate the distance to the closet object to the PING ultracsonic sensor
   * 
   * @note Interrupts aren't supported by the underlying hardware, so events may be missed during the 1ms poll window.  The best we can do is to print the current state after a event is detected.
   *
   * @param pin Raspberry Pi B+ combine INPUT & OUTPUT pin name constant (i.e. GPIO1, GPIO2, GPIO3) 
   * 
   * @return Distance to closet object to resolution of 5 mm
   */

  private PollPING(pin = GPOI1, state = PING_SENT): number{  
    if(state == PING_SENT)
      //TO-DO: Start system timer not dependent on function scope
      return INVALID_STATE
    if(state == PING_RECIEVED)
      //TO-DO: Stop system timer not dependent on function scope
      return pingElaspsedTime  
  }


  /** TO-DO: CHANGE pin and return type to Uint8Array https://developer.mozilla.org/en-US/docs/Web/JavaScript/Typed_arrays
   * @brief Read the currect logic level (HIGH or LOW) on an button press.
   * 
   * @note Interrupts aren't supported by the underlying hardware, so events may be missed during the 1ms poll window.  The best we can do is to print the current state after a event is detected.
   *
   * @param pin Raspberry Pi B+ pin name constant (i.e. GPI0, GPO3, etc.) 
   * 
   * @return Logic level of button connected to a GPIO input pin
   */
  public readInputButtonState(pin: number): number{
     //var pinState2 = rpio.poll(pin, rpio.read(pin)); TO-DO: THIS LINE MAY REPLACE ALL CODE BELOW :)

     var pinState = rpio.read(pin); 
     if(this._lastDataPoint == LOW && pinState == HIGH){
       //TO_DO: RESET ??? this._lastDataPoint == LOW; //
       return PRESSED; //Button being pressed  
     }
     else if(this._lastDataPoint == HIGH && pinState == HIGH){
       return HELD; //Button being held down
     }
     else{
       return NOT_PRESSED; //Button NOT being pressed or held down
     }
  }//END readInputButtonState() FUNCTION


 /*
  * @brief Print the logic levels of all four GPIO input pins and all three combined GPIO input & output pins
  * 
  * @param NONE
  * 
  * @return NOTHING
  */
  public displayAllInputPins(): void{
     if(DEBUG_STATEMENTS_ON){
       for(var i:number = GPI1; i<=NUM_INPUT_PINS; i++){
         console.log('Input pin P' + i + ' is currently set to ' + (this.readInputButtonState(i) ? 'HIGH' : 'LOW'));
       }//END FOR LOOP

       for(var i:number = GPIO1; i<=NUM_INPUT_OUTPUT_PINS; i++){
         console.log('Input/Output pin P' + i + ' is currently set to ' + (this.readInputButtonState(i) ? 'HIGH' : 'LOW'));
       }//END FOR LOOP

     }//END IF
  }//END displayAllInputPins() FUNCTION


}//END CLASS
