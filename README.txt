*****************************************************************************
Confused? Talk to us on discord! (YMMV):
https://discord.gg/jRkQF4U
*****************************************************************************

#ARMS
Version 0.1

-----------------------------------------------------------------------------
README CONTENTS:
1. Starting an Android Emulator
2. Installation and Use of ARMS software
3. Installation of Retail Inventory Database

-----------------------------------------------------------------------------
1. Starting an Android Emulator

Required Programs for Emulation:

	Android Studio with latest platform tools update:
		https://developer.android.com/studio/index.html
		https://developer.android.com/studio/releases/platform-tools.html

First, launch Android Studio. Select the option for "Start a new Android Studio project." 
Create a new blank project and add an empty activity. From the new window, hold shift and press F10. 
In the new window, click on the button  that says "Create New Virtual Device." 
ARMS can be run from any of the availble Android devices, but for sake of simplicity, 
we will use the Nexus 5X option. For the system image, select Nougat (Android 7.1.1). 
Once your device has been created, select it and click on the "OK" button to launch the device.

-----------------------------------------------------------------------------
2. Installation and Use of ARMS software

The included file "ARMS.apk" can be installed on the emulator by dragging the file and dropping it onto the
emulated phone. The application will automatically be installed. To access the newly installed application, 
click and drag the mouse upwards from the bottom of the screen. From the list of available apps, find "ARMS"
and click on it once.

You will then be greeted by a login screen. For the sake of our demonstration, you may log in by leaving the username
and password fields blank. To start using the Barcode Scanner, click on the image of the barcode. If Android
asks for permission to use the camera, select "Allow." Because the emulator does not have any access to 
physical camera hardware, you will not be able to directly scan a barcode. 

-----------------------------------------------------------------------------
3. Installation of Retail Inventory Database

//TODO: Make a script to install the database
//TODO: Populate the database with at least one item to scan

NOTE: FUll database integration is still a work in progress, so as of right now, it is not functional enough for demonstration

