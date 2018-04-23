#!/usr/bin/env python

__author__ =  "Blaze Sanders"
__email__ =   "blaze@robobev.com"
__company__ = "Robotic Beverage Technologies Inc"
__status__ =  "Development"
__date__ =    "Late Updated: 2018-04-06"
__doc__ =     "Compile all the code to run BARISTO coffee kiosk and start ./RUN_BASISTO app"

# @link https://docs.python.org/3/library/subprocess.html#replacing-os-popen-os-popen2-os-popen3

# Useful system jazz
import sys, time, traceback, argparse

# Allow control of all keyboard keys
import pynput.keyboard
from pynput.keyboard import Key, Controller

# Allow BASH command to be run  inside  Python 2.7 code like this file
import subprocess
from subprocess import Popen, PIPE
from subprocess import check_call

PRODUCT_MODE = "PRODUCT"        # Final product configuration
FIELD_MODE  = "FIELD"		# Non-Techanical repair person configuration
TESTING_MODE = "TESTING"        # Internal developer configuration
DEBUG_STATEMENTS_ON = True      # Toogle debug statements on and off for this python file

#Useful time.sleep() constants
USER_DELAY_TIME  = 0.7			#Delay keyboard presses by 700 ms
CPU_LOAD_DELAY = 0.02			#Delay 20 ms to reduce CPU load from reaching 100% during looping
DRAMATIC_DELAY = 3			#Delay 3 second to slow down UI in dramatic way
RFID_LOOP_DELAY = 185		        #Delay ??  TODO REMOVE AFTER RFID TRANSCIEVER IS WORKING

# Create a command line parser
parser = argparse.ArgumentParser(prog = "BARISTO V1", description = __doc__, add_help=True)
parser.add_argument("-i", "--piIP_Address", type=str, default="192.168.1.218", help="IPv4 address of the BARISTO CPU.")
parser.add_argument("-r", "--rx_Socket", type=int, default=30000, help="UDP port / socket number for connected Ethernet device.")
parser.add_argument("-s", "--tx_Socket", type=int, default=30100, help="UDP port / socket number for connected Ethernet device.")
parser.add_argument("-u", "--unit", type=str, default= FIELD_MODE, choices=[TESTING_MODE, FIELD_MODE, PRODUCT_MODE], help="Select boot up mode for BARISTO kiosk.")
parser.add_argument("-t", "--trace", type=int, default=0, help="Program trace level.")
parser.add_argument("-f", "--filename", type=str, default="BARISTOV1.zip", help="Firmware to be flashed.")
parser.add_argument("-l", "--loop", type=int, default=0, help="Set to 1 to loop this driver program.")
args = parser.parse_args()

##
#  @brief Transition PowerPoint to the next slide using the right arrow key
#
#  @param NOTHING
#
#  @return NOTHING
def transitionToNextSlide():
	# Press and release right arrow key
	keyboard.press(Key.right)
	keyboard.release(Key.right)

##
#  @brief Transition PowerPoint to the previous slide using the left arrow key
#
#  @param NOTHING
#
#  @return NOTHING
def transitionToPreviousSlide():
	# Press and release left arrow key
	keyboard.press(Key.left)
	keyboard.release(Key.left)

if __name__ == "__main__":

	keyboard = Controller()
	check_call("clear",shell=True) #Clear terminal

	keyboard.type('Compiling BARISTO.c now...')
	check_call("clear",shell=True)
	
        #Run g++ BARISTO.c -std=c++11 -o RUN_BARISTO bash command
	check_call("g++ BARISTO.c -std=c++11 -o RUN_BARISTO",shell=True)

	keyboard.type('Starting BARISTO kiosk now...')
	check_call("./RUN_BARISTO",shell=True)

	try:
		print "TRY"
 
	except KeyboardInterrupt:
		print "\n**Keyboard Interrupt Detected**\n"
		GPIO.remove_event_detect(RFID_READ_PIN)     #TO-DO: May be useful for all pins some where in code :)
		GPIO.cleanup()

	except:
		traceback.print_exc()
