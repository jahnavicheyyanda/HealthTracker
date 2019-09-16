Health tracker app is developed in order to control the prototype of auscultation device in a convenient way. Transmission and communication of the data and control
commands between device and smartphone is implemented wireless via Bluetooth. The app allows visualization of audio signals and saving them along with patient data. 

## On Raspberrypi side:
To accept the connections coming from android phone we are creating a bluetooth server application on the raspberrypi. The python code can be found here  

https://github.com/zecergin/HealthTracker/blob/master/raspibtsrv.py  

 PyBluez library currently supports two types of BluetoothSocket objects: RFCOMM and L2CAP. We create a Bluetooth socket using the RFCOM protocol.  The RFCOMM socket  is created by passing RFCOMM as an argument to the BluetoothSocket constructor. An UUID is advertised so any client knowing the UUID may connect. When the socket receives a command from the client, the audio file is transferred using Obexftp Service.  
 
## The Android Application:
 
### MainPage: 
Class to manage the first screen of application. It has the connection with AppInfo for pop-up information, MainActivity to manage Bluetooth and FilesPage for listview of records. 

### MainActivity: 
Turning the bluetooth on/off, opening the mobile device to discover by other devices, discovering the devices with Bluetooth, pairing and connecting to chosen devices are managed in this class. 

### PatientData: 
Class to keep the patient data which is entered by user. 

### PatientDataEdit: 
Class to keep the changed data by the user. Changes will be reflected to the audio files title in SD card. 

### PlaybackThread: 
Class for reading and playing the received audio file. 

### RecordWave: 
Class for signal visualization with the help of PlaybackThread and WaveformView. 
 
