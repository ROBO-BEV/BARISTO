Robotic Beverage Technologies Inc Baristo Kiosk Software 

Author: Blaze Sanders Email: founders@robobev.com Skype: blaze.sanders Twitter: @BlazeDSanders

Baristo kiosks are self-contained autonomous cashless coffee kiosks, with a prepaid coffee  cup and smartphone app. Check out https://www.robobev.com for more details in the coming months.

This Git repo holds code for multiple CPUs that reside inside the Baristo Kiosk. It's broken down into the following five directories (***JAVA_Code, PYTHON_Code, PLC_Code, SPIN_Code***). All three CPU must be running for Baristo kiosk to be fully functional and serve coffee. Please use https://google.github.io/styleguide/javaguide.html as the coding standard :)

***
PYTHON_Code: 

@link ???

To run the code in PYTHON_Code directory complete the following steps:
1. Download and open PYTHON_Code directory onto a Raspberry Pi 3 B+
2. ???
3. ???
4. ???

***

JAVA_Code: Highest level code which was designed to run on a Raspberry Pi 3 B+, NVIVIDA TX2, and/or a Microsoft Surface Book 2016. With the help of the JAVA Virtual Machine other platforms will be supported in the future or may work now with a little hardware reconfiguration. Yes we selected JAVA beacuse its a coffee robot :)

@link https://www.raspberrypi.org/products/raspberry-pi-3-model-b/

@link https://www.microsoft.com/en-us/surface/devices/surface-book/overview 

@link https://developer.nvidia.com/embedded/buy/jetson-tx2

@link https://www.ics.com/blog/getting-started-mraa-raspberry-pi

@link https://github.com/horst3180/arc-theme/issues/63

@link https://www.microsoft.com/en-us/store/p/ubuntu/9nblggh4msv6?rtc=1

Ubuntu on Windows allows one to use Ubuntu Terminal and run Ubuntu command line utilities including bas, ssh, git, apt-get, etc
Location of Ubuntu home directoty on the Windows file system
C:\Users\"user name"\AppData\Local\Packages\CanonicalGroupLimited.UbuntuonWindows_79rhkp1fndgsc\LocalS$

checking for pcre-config... no
configure: error: in `/home/pi/Documents/IoT/swig':
configure: error: 
        Cannot find pcre-config script from PCRE (Perl Compatible Regular Expressions)
        library package. This dependency is needed for configure to complete,
        Either:
        - Install the PCRE developer package on your system (preferred approach).
        - Download the PCRE source tarball, build and install on your system
          as you would for any package built from source distribution.
        - Use the Tools/pcre-build.sh script to build PCRE just for SWIG to statically
          link against. Run 'Tools/pcre-build.sh --help' for instructions.
          (quite easy and does not require privileges to install PCRE on your system)
        - Use configure --without-pcre to disable regular expressions support in SWIG
          (not recommended).

javac -cp ~/Documents/IoT/mraa/build/src/java/mraa.jar:. Example.java


To run the code in JAVA_Code directory complete the following steps:
1. In a Linux or Ubuntu on Windows terminal, run the command "javac Baristo.java" to compile all JAVA code (Need help? https://goo.gl/aED8Dw)
2. Next run the command "java Baristo" to start kiosk JAVA software running.  
3. Enter ternimal input as prompted from the EngineeringTerminal Class and have fun.

***

PLC_Code: 

@link  http://velocio.net/embedded-plcs/

To run the code in PLC_Code directory complete the following steps:
1. Download and open the Velocio vBuilder application.
2. File > Open (Ctrl+O) the Baristo.vio file.
3. Tool > Program to File (??) to program the PLC.
4. Press the green RUN button (F5) to start kiosk PLC software running.

***

SPIN_Code: 

@link https://www.parallax.com/product/32810

To run the code in SPIN_Code directory complete the following steps:
1. Download and open the Parallax Propeller Tool / IDE.
2. File > Open (Ctrl+O) the Baristo.SPIN file
3. Run > Compile Current > Load EEPROM (F11) to programm EEPROM and start kiosk SPIN software running. 
