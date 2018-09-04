from tkinter import *

window0 = Tk()
window0.geometry('960x540')

#tk.iconbitmap(default='ROBO_BEV_LOGO.ico')
window0.title("BARISTO")

photo = PhotoImage(file="Page1.png")
widget = Label(window0, image=photo)
widget.photo = photo

widget = Label(window0, text="10", fg="white", font=("Source Sans Pro",50))
#widget = Label(window0, text="9", fg="white")

widget.pack()

window0.mainloop()

