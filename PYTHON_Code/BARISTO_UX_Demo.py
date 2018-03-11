#!/usr/bin/env python

__author__ =  "Blaze Sanders"
__email__ =   "founders@robobev.com"
__company__ = "Robotic Beverage Technologies Inc"
__status__ =  "Development"
__date__ =    "Late Updated: 2018-03-10"
__doc__ =     "Control PowerPoint presentation programmaticaly with keyboard"

# @link https://docs.python.org/3/library/subprocess.html#replacing-os-popen-os-popen2-os-popen3
# @link http://codeandlife.com/2012/07/29/arduino-and-raspberry-pi-serial-communication/
# @link https://sourceforge.net/p/raspberry-gpio-python/wiki/Inputs/

# Useful system jazz
import sys, time, traceback, argparse

# Allow control of GPIO pins on Raspberry Pi 3 and Pi Zero (i.e. I2C, SPI, TTL)
import RPi.GPIO as GPIO

# Allow use of both serial ports on the Pi 3 /ttyS0 nad /AAMM
#import serial

# Allow control of all keyboard keys
#import pynput.keyboard
#from pynput.keyboard import Key, Controller

# Allow BASH command to be run  inside  Python 2.7 code like this file
import subprocess
from  subprocess import Popen, PIPE

# Allow demo to talk
import BARISTO_Text2Speech

PRODUCT_MODE = "PRODUCT"            # Final product configuration
FIELD_MODE  = "FIELD"				# Non-Techanical repair person configuration
TESTING_MODE = "TEST"               # Internal developer configuration
DEBUG_STATEMENTS_ON = True      	# Toogle debug statements on and off for this python file

#Pin value constants
LOW =  0
HIGH = 1
UNDEFINED = -1
#TO-DO: PWM_TWO_PERCENT to PWM_HUNDRED_PERCENT in two percent steps

#Button state constants
PRESSED = 0
NOT_PRESSED = 1
HELD = 2

#Pin direction constants
INPUT_PIN = 0
OUTPUT_PIN = 1
#TO-DO: const PWM 2

#Raspberry Pi B+ refernce pin constants as defined in ???rc.local script???
NUM_GPIO_PINS = 8                  	#Outputs: GPO0 to GPO3 Inputs: GPI0 to GPI3
MAX_NUM_A_OR_B_PLUS_GPIO_PINS = 40 	#Pins 1 to 40 on Raspberry Pi A+ or B+ or ZERO W
MAX_NUM_A_OR_B_GPIO_PINS = 26      	#Pins 1 to 26 on Raspberry Pi A or B
NUM_OUTPUT_PINS = 4                	#This software instance of Raspberry Pi can have up to four output pins
NUM_INPUT_PINS = 4                 	#This software instance of Raspberry Pi can have up to four input pins

#Hardware connected to the Raspberry Pi 3
RFID_READ_PIN = 22            		#Raspberry Pi Connector J8 BCM 22 =  P15
EMIC_SIN_PIN = 23					#Raspberry Pi Connector J8 BCM 23 = P16
EMIC_SOUT_PIN = 24					#Raspberry Pi Connector J8 BCM 24 = P18
COFFEE_READY_PIN = 24		   		#Raspberry Pi Connector J8 BCM 24 =  P17
POLLY_LISTEN_PIN = 25		   		#Raspberry Pi Connector J8 BCM 25 =  P22
EMIC_SPEAK_PIN = 27	   				#Raspberry Pi Connector J8 BCM 27 =  P12

#UART pins in BCM mode are: 14, 15 /dev/ttyAMA0

#Useful time.sleep() constants
USER_DELAY_TIME  = 0.7				#Delay keyboard presses by 700 ms
CPU_LOAD_DELAY = 0.02				#Delay 20 ms to reduce CPU load from reaching 100% during looping

#Random constant for EMIC2 hardware and Amazon Web Service (AWS) Polly API
EMIC2 = 222
POLLY = 333

#Pre-recorded .mp3 audio file
HELLO_AUDIO = 1						#"Hello, Is this your first time with us?"
PLACE_AUDIO = 2						#TO-DO:
GOOD_AUDIO = 3						#TO-DO:
COFFEE_AUDIO = 4						#TO-DO:
READY_AUDIO = 5						#TO-DO:
NUMBER_AUDIO = 6					#TO-DO:
NOT_REGISTERED_AUDIO = 7				#TO-DO:
DOWNLOAD_AUDIO = 8					#TO-DO: DELETE?

# Create a command line parser
parser = argparse.ArgumentParser(prog = "BARISTO V1", description = __doc__, add_help=True)
parser.add_argument("-i", "--piIP_Address", type=str, default="192.168.0.134", help="IPv4 address of the Mars Pi.")
parser.add_argument("-r", "--rx_Socket", type=int, default=30000, help="UDP port / socket number for connected Ethernet device.")
parser.add_argument("-s", "--tx_Socket", type=int, default=30100, help="UDP port / socket number for connected Ethernet device.")
parser.add_argument("-u", "--unit", type=str, default= FIELD_MODE, choices=[TESTING_MODE, FIELD_MODE, PRODUCT_MODE], help="Select boot up mode for BARISTO kiosk.")
parser.add_argument("-t", "--trace", type=int, default=0, help="Program trace level.")
parser.add_argument("-f", "--filename", type=str, default="sampleData.txt", help="File to be transmitted.") #cam0.0.jpeg
parser.add_argument("-l", "--loop", type=int, default=0, help="Set to 1 to loop file.") 
args = parser.parse_args()

##
#  @brief Transition PowerPoint to the next slide using the right arrow key
#
#  @param NOTHING
#
#  @return NOTHING
def transitionToNextSlide():
	# Press and release right arrow key
	keyboard.press('Key.right')
	keyboard.release('Key.right')

##
#  @brief Transition PowerPoint to the previous slide using the left arrow key
#
#  @param NOTHING
#
#  @return NOTHING
def transitionToPreviousSlide():
	# Press and release left arrow key
	keyboard.press('Key.left')
	keyboard.release('Key.left')

##
#  @brief Generic text to speech function call
#
#  @param interfaceName Either EMIC 2 or AWS Polly
#
#  @param audioClipNum Audio clip to play from speaker
#
#  @return NOTHING
def text2Speech(interfaceName, audioClipNum):
	if (interfacename == EMIC2):
		BARISTO_Text2Speech.emic2Interface(audioClipNum)
	if (interfaceName == POLLY):
		BARISTO_Text2Speech.awsPollyInterface(audioClipNum)

##
#  @brief Calls into V0.1 hardware and terminal demo
#
#  @param interfaceName Either EMIC 2 or AWS Polly
#
#  @return NOTHING
#def emic2Interface(serialPort, caption):
def emic2Interface(serialPort, audioClipNum):
	if (audioClipNum == HELLO_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Hello, Is this your first time with us?")
  			#data = np.array([0xFF, 0xA1, 0xB5])
  			for  i in range (8): #0 to 7
  		  		#GPIO.output(EMIC_SIN_PIN, data[i])
  		  		serialPort.write("Hello World")
  		  		serialPort.flush()
  				GPIO.output(EMIC_SPEAK_PIN, HIGH)
	if (audioClipNum == PLACE_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Place your smart cup on the black pad.")
			print("OR")
			print("Tell me the last four digits of your ohone number.")
	if (audioClipNum == GOOD_AUDIO):
		if DEBUG_STATEMENTS_ON: print("Good morning, Rosie.")
	if (audioClipNum == COFFEE_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Large caffe mocha with sugar coming up")
			for timer in range(10):
				keyboard.type(abs(10-timer))
				keyboard.type("...")
	if (audioClipNum == READY_AUDIO):
		if DEBUG_STATEMENTS_ON: print("Your coffee is ready, Have a great day!")
	if (audioClipNum == NUMBER_AUDIO):
		if DEBUG_STATEMENTS_ON: print("You said your number ended in 5555 correct?")
	if (audioClipNum == NOT_REGISTERED_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Your phone number is not registered with BARISTO.")
			print("Please scan the QR code below to download BARISTO from the Google Play store and register with us.")
##
#  @brief Configure GPIO pin on a Pi 3 using BCM pin mode
#
#  @param NONE
#
#  @return NOTHING
def setupGPIO():
	GPIO.setmode(GPIO.BCM)  #e.g. Raspberry Pi Connector J8 BCM 22 =  P15
	GPIO.setup(RFID_READ_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)
	GPIO.setup(COFFEE_READY_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)
	GPIO.setup(POLLY_LISTEN_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)
	GPIO.setup(EMIC_SPEAK_PIN, GPIO.OUT)
	#https://spellfoundry.com/2016/05/29/configuring-gpio-serial-port-raspbian-jessie-including-pi-3/

	#Falling edge active LOW pins with 20 ms software debounce
	GPIO.add_event_detect(RFID_READ_PIN, GPIO.FALLING, callback=my_callback, bouncetime=20)
	GPIO.add_event_detect(COFFEE_READY_PIN, GPIO.FALLING, callback=my_callback, bouncetime=20)
	GPIO.add_event_detect(POLLY_LISTEN_PIN, GPIO.FALLING, callback=my_callback, bouncetime=20)

##
#  @brief Read the currect logic level (HIGH or LOW) on an button press.
#
#  @note Interrupts aren't supported by the underlying hardware, so events may be missed during the 1ms poll window.  The best we can do is to print the current state after a event is detected.
#
#  @param pin Raspberry Pi B+ pin name constant (i.e. GPI0, GPO3, etc.)
#
#  @return Logic level of button connected to a GPIO input pin
def readInputButtonState(pin):
	pinState = GPIO.input(pin)
	if lastSelectButtonState == LOW and pinState == HIGH:
	 	#lastSelectButtonState == LOW; //
		return PRESSED     #Button being pressed
	elif lastSelectButtonState == HIGH and pinState == HIGH:
		return HELD        #Button being held down
	else:
		return NOT_PRESSED #Button NOT being pressed or held down
##
#  @brief Print the logic levels of all four GPIO input pins
#
#  @param NONE
#
# @return NOTHING
def displayAllInputPins():
	if DEBUG_STATEMENTS_ON:
		for i in range(1,NUM_INPUT_PINS+1):
			print('Input pin P' + i + ' is currently set to ' + readInputButtonState(i))


if __name__ == "__main__":

	keyboard = Controller()
	keyboard.type('ROBO BEV BARISTO demo starting now...')

	#TO_DO: serialPort1 = configureSerialPort()
	setupGPIO()

	try:

		while True:
			for i in range(2): #Repeat "Hello, Is this your first time with us?"  twice
				text2Speech(EMIC2, HELLO_AUDIO)
				text2Speech(EMIC2, PLACE_AUDIO)
				time.sleep(1500)

			while not GPIO.event_detected(RFID_READ_PIN):	#Loop until active LOW pin falls
				time.sleep(CPU_LOAD_DELAY)
				#if ( loopCounter > CPU_LOAD_DELAY * 1000) RFID was not read
				if  (loopCounter > 10000):
					text2Speech(EMIC2, NUMBER_AUDIO)
					keyboard.type('Yes')
					text2Speech(EMIC2, NOT_REGISTERED_AUDIO)
			transitionToNextSlide()
			text2Speech(EMIC2, GOOD_AUDIO)
			text2Speech(EMIC2, LARGE_AUDIO)

			while GPIO.input(COFFEE_READY_PIN) == HIGH:		#Loop until active LOW pin falls
				time.sleep(CPU_LOAD_DELAY)
			transitionToNextSlide()
			text2Speech(EMIC2, READY_AUDIO)

			time.sleep(CPU_LOAD_DELAY)

	except KeyboardInterrupt:
		print "\n**Keyboard Interrupt Detected**\n"
		GPIO.remove_event_detect(RFID_READ_PIN)     #TO-DO: May be useful for all pins some where in code :)
		GPIO.cleanup()

	except:
		traceback.print_exc()
