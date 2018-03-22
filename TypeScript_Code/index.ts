/**
 * @module   SensorTemplate
 * @file     index.ts
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@robobev.com
 * @updated  22 MAR 2018
 *
 * @version 0.1
 * @brief PING ultrasonic sensor for ROBO BEV BARISTO coffee kiosk
 *
 * @link https://www.robobev.com
 *
 * @section DESCRIPTION
 *
 * Yes we are programming a coffee robot in JAVA..script TEST
 *
 */

//4th Law Typings module, to ensure consistency and decrease the probabiliby of errors 
import * as Typings from "@4th-law/typings-public"
import * as config from "config"
import * as rpio from "rpio"

/**
 * @param {NProfile.ECategory} _category - tells us if this is a sensor or actuator
  * @param {NProfile.EType} _type - tells us what kind of sensor this is (optical, mechanical, etc. see @4th-law/typings)
  * @param {string} _make - tells us the manufacturer of the particular sensor this profile is for
  * @param {string} _model - tells us the model of the sensor in question
  * @param {string} _id - tells us the product id for the sensor this profile is for
  * @param {string} _version - the version of this profile, in vX.X.X format
  * @param {number} _sampleInterval - the number of miliseconds between updates to _lastDataPoint
  * @param {NData.ISensor} _lastDataPoint - the last data point sampled by this profile
  * @param {object} _specs - any information specific to this profile, such as the resolution of a camera
  */
export default class SensorTemplate extends Typings.Sensor {

  protected _category = "SENSOR" as NProfile.ECategory
  protected _type = "[insert type]" as NProfile.EType
  protected _make: string
  protected _model: string
  protected _id: string
  protected _version: string
  //TO-DO: _sampleInterval should be _samplePeriod, needs to be changed in typings
  protected _sampleInterval: number
  protected _lastDataPoint: NData.ISensor
  protected _specs: object


  /*
   * @breif Calculate distance in millimeters from a PING ultrasonic sensor
   *
   * @section DESCRIPTION
   * 
   * ??? BARISTO has three PING ultrasonic sesnors to calulate the x, y, and z location of the users smart cup
   *
   * @param pin Raspberry Pi B+ pin INPUT & OUTPUT pin name constant - defaults to GPIO1 / P13
   *
   * @see Input pins default to LOW before first attempted read state
   *
   * @return NOTHING 
   */ 
  public constructor(pin = GPIO1) {
    super()

    this._category = "SENSOR" as NProfile.ECategory
    this._type = "ACOUSTIC" as NProfile.EType
    this._make = "Parallax"
    this._model = "PING"
    this._id = "28015"
    this._version = "v0.1.0"
    this._sampleInterval = config.get("profile.sampleInterval")
    //??? this._sampleFrequency = 100    //Units Hz?
    //??? this._lastDataPoint: any
    this._specs = {}

    const GPIOpins = new PING_28015()
    rpio.open(pin, rpio.INPUT, rpio.PULL_DOWN)  //Calls rpio.init([options]) automatically 
  
    //Print pin status and distance every 1000 ms
    setInterval(() => {
      if(DEBUG_STATEMENTS_ON){
        pinState = GPIOpins.readInputButtonState(pin)
        console.log('Input pin P' + pin + ' is currently set to ' + (pinState ? 'HIGH' : 'LOW'));

        //???
       
      }
    }, 1000) 

  }//END CONSTRUCTOR
}//END CLASS

const p = new Profile()

