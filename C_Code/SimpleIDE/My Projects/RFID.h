/**
 * @file     RFID.h
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@robobev.com
 * @created  27 APRIL 2018
 * @updated  27 APRIL 2018
 *
 * @version 0.1
 *
 * @brief RFID tag data management for the BARISTO coffee kiosk
 *
 * @link https://www.robobev.com
 *
 * @section DESCRIPTION
 *
 * MIT license with lots of code easter eggs. Have fun!
 */

#ifndef RFID_H
#define RFID_H

//SimpleIDE specific header files to flash the Parallax Propeller microcontroller
#include "rfidser.h"      //Contrrol Serial RFID Transceiver
#include "fdserial.h"


//Global Constants
extern int DATA_KIOSK_UPDATE_RADIUS = 16; //kilometers


/**
 * @cond
 * Defines rfidser interface struct
 * 9 contiguous ints + buffers
 */
typedef struct tag_struct
{
    double databaseID; //Worldwide ROBO BEV ID (Country Code.Verison)
    double latitude;   //Minus = South & Positive = North
    double longitude;  //Minus = East & Positive = West

    int  rx_pin;    /* recieve pin */
    int  tx_pin;    /* transmit pin */
    int  mode;      /* interface mode */
    int  ticks;     /* clkfreq / baud */
    char *buffptr;  /* pointer to rx buffer */
    char *idstr;
    int  en;
} tag_t;

/**
 * @brief Encrypt password for transimission over the internet
     *
     * @section DESCRIPTION
     *
     * TODO
     *
     * @return  TRUE if password was sucessfully encrypted, and  FALSE otherwise
     */
     bool EncryptPassword();

    /**
     * @brief Compare encrypted password to password store in user database
     *
     * @section DESCRIPTION
     *
     * TODO
     *
     * @return  TRUE if password was sucessfully encrypted, and  FALSE otherwise
     */
    bool VerfifyPassword();

#endif 
