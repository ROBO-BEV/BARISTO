#!/usr/bin/env python

__author__ =  "Blaze Sanders"
__email__ =   "founders@robobev.com"
__company__ = "Robotic Beverage Technologies Inc"
__status__ =  "Development"
__date__ =    "Late Updated: 2018-03-08"
__doc__ =     "Control PowerPoint presentation programmaticaly with keyboard"

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
			print("Large caffe mocha with sugar coming up")
			for timer in range(10):
				keyboard.type(abs(10-timer))
				keyboard.type("...")
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
