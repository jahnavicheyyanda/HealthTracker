#!/usr/bin/python*
import os, struct, sys
import obexftp
from bluetooth import *

def main():
   
    # Make device visible
    os.system("hciconfig hci0 piscan")
    
    # Create a new server socket using RFCOMM protocol
    server_sock = BluetoothSocket(RFCOMM)
    
    # Bind to any port
    server_sock.bind(("", PORT_ANY))
    
    # Start listening
    server_sock.listen(1)

    # Get the port the server socket is listening
    port = server_sock.getsockname()[1]

    # The service UUID to advertise
    uuid = "00001101-0000-1000-8000-00805f9b34fb"

    # Start advertising the service
    advertise_service(server_sock, "RaspiBtSrv",
                       service_id=uuid,
                       service_classes=[uuid, SERIAL_PORT_CLASS],
                       profiles=[SERIAL_PORT_PROFILE])
    
    
    

    # Main Bluetooth server loop
    while True:

        print ("Waiting for connection on RFCOMM channel %d" % port)

        try:
            client_sock = None

            # This will block until we get a new connection
            client_sock, client_info = server_sock.accept()
            print ("Accepted connection from ", client_info)
        address = client_info[0]
        dev = client_info[0]
            channel = obexftp.browsebt(dev, obexftp.PUSH)
            print(channel)          
            
            while True:
            # Read the data sent by the client
                data = client_sock.recv(1024)
                if len(data) == 0:
                    break
                    print ("Received")

            # Handle the request
                if data == "1":
                    command = "obexftp --nopath --noconn --uuid none --bluetooth "+address+" --channel "+str(channel)+" -c /mnt/Health_tracker_transfer -p /home/pi/Bloxton/Test.wav"
            os.system(command)
                                  
                 

        except IOError:
            pass

        except KeyboardInterrupt:
            if client_sock is not None:
                client_sock.close()
               
            server_sock.close()

            print ("Server going down")
            break
        

main()


        

    

