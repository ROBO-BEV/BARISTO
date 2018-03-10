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
	if GPIO.input(POLLY_LISTEN_PIN) == HIGH:
	#print("STARTING WORMY PYTHON GAME. HAVE FUN :)")
	#from subprocess import check_call
	#check_call("python ~/python_games/wormy.py",shell=True)

	switch (audioClipNum):
		case 1:
			if DEBUG_STATEMENTS_ON: print("Hello, Is this your first time with us?")
			#AWS.Polly("Hello, Is this your first time with us?")
		case 2:
			if DEBUG_STATEMENTS_ON: 
				print("Place your smart cup on the black pad.")
				print("OR")
				print("Tell me the last four digits of your ohone number.")
		case 3:
			if DEBUG_STATEMENTS_ON: print("Good morning, Rosie.")
		case 4:
			if DEBUG_STATEMENTS_ON: 
				print("Large caffe mocha with sugar coming up")
				range(timer 13 to 1)
						keyboard.type(timer)
								keyboard.type("...")
		case 5:
			if DEBUG_STATEMENTS_ON: print("Your coffee is ready, Have a great day!")
		case 6:
			if DEBUG_STATEMENTS_ON: print("You said your number was 555-555-5555 correct?")
		case 7:
			if DEBUG_STATEMENTS_ON: 
				print("Your phone number is not registered with BARISTO.")
				print("Please say your full phone number again.")
		case 8:
			if DEBUG_STATEMENTS_ON: 
				print("I still can not find your phone number.")
				print("Please scan the QR code below to download BARISTO from the Google Play store and register with us.")
