#!/usr/bin/env python

__author__ =  "Blaze Sanders"
__email__ =   "blaze@robobev.com"
__company__ = "Robotic Beverage Technologies Inc"
__status__ =  "Development"
__date__ =    "Late Updated: 2018-04-02"
__doc__ =     "Play predefine text using EMIC2 hardware or AWS Polly interface"

# Useful system jazz
import sys, time, traceback, argparse

# Allow control of all keyboard keys
import pynput.keyboard
from pynput.keyboard import Key, Controller


#Pre-recorded .mp3 audio file
HELLO_AUDIO = 1                                         #"Hello, Is this your first time with us?"
PLACE_AUDIO = 2                                         #TO-DO:
GOOD_AUDIO = 3                                          #TO-DO:
COFFEE_AUDIO = 4                                                #TO-DO:
READY_AUDIO = 5                                         #TO-DO:
NUMBER_AUDIO = 6                                        #TO-DO:
NOT_REGISTERED_AUDIO = 7                                #TO-DO:
DOWNLOAD_AUDIO = 8                                      #TO-DO: DELETE?

DEBUG_STATEMENTS_ON = True    #Toogle debug statements on and off for this python file



## TO-DO, TO-DO, TO-DO DEBUG print should not be copied and pasted between the emic2 and AWS Polly functions
#  @brief  Plays predefined text stored on Pi
#
#  @param audioClipNum Audio clip to play from speaker
#
#  @return NOTHING
def emic2Interface(audioClipNum):
	#if GPIO.input(POLLY_LISTEN_PIN) == HIGH:
	#print("STARTING WORMY PYTHON GAME. HAVE FUN :)")
	#from subprocess import check_call
	#check_call("python ~/python_games/wormy.py",shell=True)

	if (audioClipNum == HELLO_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Hello, Is this your first time using a BARISTO kiosk? \n\n")
			#EMIC2.Say("Hello, Is this your first time with us?")
	if (audioClipNum == PLACE_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Place your smart cup on the black pad.")
			print("OR")
			print("Tell me the last four digits of your phone number. \n\n")
	if (audioClipNum == GOOD_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Good morning, Rosie. \n\n")
	if (audioClipNum == COFFEE_AUDIO):
		if DEBUG_STATEMENTS_ON:
			keyboard = Controller()
			print("Large caffe mocha with milk coming up \n\n")
			for timer in range(10):
				keyboard.type(str(abs(10-timer)))
				keyboard.type("...")
				time.sleep(1)
	if (audioClipNum == READY_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Your coffee is ready, Have a great day! \n\n")
	if (audioClipNum == NUMBER_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("You said your number was 555-555-5555 correct? \n\n")
	if (audioClipNum == NOT_REGISTERED_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Your phone number is not registered with BARISTO. \n")
			print("Please scan the QR code below to download BARISTO from the Google Play store and register with us. \n\n")


## TO-DO, TO-DO, TO-DO
#  @brief Calls into AWS Polly API
#
#  @param audioClipNum Audio clip to play from speaker
#
#  @return NOTHING
def awsPollyInterface(audioClipNum):
	#if GPIO.input(POLLY_LISTEN_PIN) == HIGH:
	#print("STARTING WORMY PYTHON GAME. HAVE FUN :)")
	#from subprocess import check_call
	#check_call("python ~/python_games/wormy.py",shell=True)

	if (audioClipNum == HELLO_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Hello, Is this your first time with us?")
			#AWS.Polly("Hello, Is this your first time with us?")
	if (audioClipNum == PLACE_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Place your smart cup on the black pad.")
			print("OR")
			print("Tell me the last four digits of your ohone number.")
	if (audioClipNum == GOOD_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Good morning, Rosie.")
	if (audioClipNum == COFFEE_AUDIO):
		if DEBUG_STATEMENTS_ON:
			keyboard = Controller()
			print("Large caffe mocha with sugar coming up")
			for timer in range(10):
				keyboard.type(str(abs(10-timer)))
				keyboard.type("...")
				time.sleep(1)
	if (audioClipNum == READY_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Your coffee is ready, Have a great day!")
	if (audioClipNum == NUMBER_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("You said your number was 555-555-5555 correct?")
	if (audioClipNum == NOT_REGISTERED_AUDIO):
		if DEBUG_STATEMENTS_ON:
			print("Your phone number is not registered with BARISTO.")
			print("Please scan the QR code below to download BARISTO from the Google Play store and register with us.")
