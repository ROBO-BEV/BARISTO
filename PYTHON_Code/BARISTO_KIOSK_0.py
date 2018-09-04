from tkinter import *

class BARISTO_KIOSK_0:

	def  create_audio_speaker_button_frame(audioFrame):

		photo = PhotoImage(file="AudioSpeaker.png")
		widget = Label(window0, image=photo)
		widget.photo = photo

		self.audioButton = Button(audioFrame, photo, bg="black", command=self.speak_page1)

		print("Audio Speaker Button Frame Function Test")

	def  create_timer_frame(timerFrame):
		timerFrame = Frame(master)	#Create timer frame
		widget = Label(window0, text="10", fg="white", font=("Source Sans Pro", 40))
		timerFrame.pack() 		#Make timer frame visible
		#widget = Label(window0, text="9", fg="white")
		print("Timer Frame Function Test")


	def __init__(self, master):
		window0.geometry('960x540')	#Set window size to ???

		#tk.iconbitmap(default='ROBO_BEV_LOGO.ico')
		window0.title("BARISTO")

		photo = PhotoImage(file="Page1.png")
		widget = Label(window0, image=photo)
		widget.photo = photo
		widget.pack()			#Make photo widget visible

	#if __name__ == "__main__":
		audioFrame = Frame(master)
		create_timer_frame(audioFrame)
		audioFrame.pack()

window0 = Tk()

app = BARISTO_KIOSK_0(window0)

window0.mainloop()

window0.destory() #optional
