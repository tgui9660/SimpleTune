
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.Vector;

import utec.comm.SerialConnectionException;
import utec.comm.SerialParameters;


public class SSMQueryTest2 {


    // Parameters used to define serial connection
    public static SerialParameters parameters = new SerialParameters();

    // Data from UTEC
    private static OutputStream outputToUtecStream;

    // Data to UTEC
    private static InputStream inputFromUtecStream;

    // Defines named comport
    private static CommPortIdentifier portId;

    // Serial port being accessed
    private static SerialPort sPort;

    // Defin state of defined comport, open or closed.
    private static boolean open = false;

    // Listeners
    private static Vector portListeners = new Vector();

    public static void main(String[] args){
    	parameters.setBaudRate(4800);
    	parameters.setDatabits(SerialPort.DATABITS_8);
    	parameters.setStopbits(SerialPort.STOPBITS_1);
    	parameters.setParity(SerialPort.PARITY_NONE);
            
    	init();
    	
    	setCommPortName();
    	
    	try {
			openConnection();
		} catch (SerialConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Build up command byte
		byte[] bytesToSend = new byte[13];
		bytesToSend[0] = (byte)0x80; // Every SSM command starts with this
		bytesToSend[1] = (byte)0x10; // Destination subaru ecu
		bytesToSend[2] = (byte)0xF0; // Source diagnostic tool
		bytesToSend[3] = (byte)0x08; // Size
		bytesToSend[4] = (byte)0xA8; // SSM command to send, read memory
		bytesToSend[5] = (byte)0x00; // Padding
		bytesToSend[6] = (byte)0x00; // Start First RPM tuple
		bytesToSend[7] = (byte)0x00;
		bytesToSend[8] = (byte)0x0E; // End First RPM tuple
		bytesToSend[9] = (byte)0x00; // Start Second tuple
		bytesToSend[10] = (byte)0x00;
		bytesToSend[11] = (byte)0x0F; // End Second RPM tuple
		
		
		long sumValue = 0;
		for(int i = 0; i < bytesToSend.length - 1; i++){
			sumValue += (int)bytesToSend[i]&0xFF;
			System.out.println("##### :"+(bytesToSend[i]&0xFF)+" <> "+sumValue + " <> " + Integer.toHexString((int)bytesToSend[i]&0xFF));
		}
		bytesToSend[12] = (byte)(sumValue&0xFF);
		System.out.println("##### :CKSUM <>"+((int)bytesToSend[12]&0xFF) + " <> " + Integer.toHexString((int)bytesToSend[12]&0xFF));
		
		//System.out.println("Checksum 1 :"+bytesToSend[12]);
		//System.out.println("Checksum 2 :"+sumValue);
		//System.out.println("Checksum 3 :"+(sumValue&0xFF));
		//System.out.println("Checksum 4 :"+((int)bytesToSend[12]&0xFF));
		
		
		sendCommand(bytesToSend);
    }
    
    public static void setCommPortName(){
		Enumeration<CommPortIdentifier> en = CommPortIdentifier.getPortIdentifiers();
		if (!en.hasMoreElements()) {
			System.out.println("No Valid ports found, check Java installation");
			System.out.println("Exiting now.");
		}
			
		//Iterate through the ports
		while (en.hasMoreElements()) {
			CommPortIdentifier chosenCommPort = en.nextElement();
			System.out.println("USB Serial found on system: "+chosenCommPort.getName());
			
			if (chosenCommPort.getPortType() == CommPortIdentifier.PORT_SERIAL 
					&& chosenCommPort.getName().toLowerCase().contains("usb") 
					&& chosenCommPort.getName().toLowerCase().contains("tty") ) {
				
				System.out.println("Chosen port :"+chosenCommPort.getName());
				
				portId = chosenCommPort;
			}
		}
    }
    public static void init() {
        try {
        	setConnectionParameters();
        } catch (SerialConnectionException e) {
            System.out.println("Error initializing the connection parameters.");
            e.printStackTrace();
        }
    }

    /**
     * Method to write chars to the UTEC.
     *
     * @param charValue
     */
    public static void sendCommand(byte[] charValue) {

        if (sPort == null) {
            System.err.println("No Port Selected, therefore no interraction with Utec happening.");
            return;
        }

        try {
            outputToUtecStream.write(charValue);

            System.out.println("Sending char to the utec:" + charValue);
        } catch (IOException e) {
            System.err.println("Can't send char command to UTEC: " + charValue);
            e.getMessage();
        }
    }

    

    /**
     * Opens a connection to the defined serial port If attempt fails,
     * SerialConnectionException is thrown
     *
     * @throws SerialConnectionException
     */
    public static void openConnection() throws SerialConnectionException {
        System.out.println("Opening connection now.");

        // if(sPort == null){ System.err.println("No port selected or available to open."); return; }

        if (parameters == null) {
            System.err.println("No port selected or available to open.");
            return;
        }
        
        /*
        // Obtain a CommPortIdentifier object for the port you want to open.
        try {
            // System.out.println("PORT: "+parameters.getPortName());
            portId = CommPortIdentifier.getPortIdentifier(parameters.getPortName());
        } catch (NoSuchPortException e) {
            System.err.println("Can't get commport identifier");
            return;
            //throw new SerialConnectionException(e.getMessage());
        }
		*/
        
        // Open the port represented by the CommPortIdentifier object. Give
        // the open call a relatively long timeout of 30 seconds to allow
        // a different application to reliquish the port if the user
        // wants to.
        try {
        	System.out.println("Creating serial port.");
        	
            sPort = (SerialPort) portId.open("SerialDemo", 30000);
        } catch (PortInUseException e) {
            System.err.println("Can't open serial port");
            // throw new SerialConnectionException(e.getMessage());
        }

        // Set the parameters of the connection. If they won't set, close the
        // port before throwing an exception.
        try {
            setConnectionParameters();
        } catch (SerialConnectionException e) {
            System.err.println("Can't set serial port connection parameters");
            sPort.close();
            throw e;
        }

        // Open the input and output streams for the connection. If they won't
        // open, close the port before throwing an exception.
        try {
            outputToUtecStream = sPort.getOutputStream();
            inputFromUtecStream = sPort.getInputStream();
        } catch (IOException e) {
            System.err.println("Error opening IO streams");
            sPort.close();
            throw new SerialConnectionException("Error opening i/o streams");
        }

        // Create a new KeyHandler to respond to key strokes in the
        // messageAreaOut. Add the KeyHandler as a keyListener to the
        // messageAreaOut.
        // keyHandler = new KeyHandler(outputToUtecStream);
        // messageAreaOut.addKeyListener(keyHandler);

        // Add this object as an event listener for the serial port.
        try {

            // *****************************************************************
            // Test to ensure that the listener is not registered multiple times
            // *****************************************************************
            //if(!UtecSerialListener.isRegistered()){
            //	sPort.addEventListener(UtecSerialListener.getInstance());
            //	UtecSerialListener.setRegistered(true);
            //}
            sPort.addEventListener(new DataListener(inputFromUtecStream));

        } catch (TooManyListenersException e) {
            System.err.println("Too Many listeners");
            sPort.close();
            throw new SerialConnectionException("too many listeners added");
        }

        // Set notifyOnDataAvailable to true to allow event driven input.
        sPort.notifyOnDataAvailable(true);

        // Set notifyOnBreakInterrup to allow event driven break handling.
        sPort.notifyOnBreakInterrupt(true);

        // Set receive timeout to allow breaking out of polling loop during
        // input handling.
        try {
            sPort.enableReceiveTimeout(30);
        } catch (UnsupportedCommOperationException e) {
            System.err.println("Time Out");
        }

        // Add ownership listener to allow ownership event handling.
        // portId.addPortOwnershipListener(this);

        open = true;
        System.out.println("Port opened with success.");
    }

    /**
     * Sets the connection parameters to the setting in the parameters object.
     * If set fails return the parameters object to origional settings and throw
     * exception.
     */
    private static void setConnectionParameters() throws SerialConnectionException {

        if (sPort == null) {
            System.err.println("No port selected.");
            return;
        }
        // Save state of parameters before trying a set.
        int oldBaudRate = sPort.getBaudRate();
        int oldDatabits = sPort.getDataBits();
        int oldStopbits = sPort.getStopBits();
        int oldParity = sPort.getParity();
        int oldFlowControl = sPort.getFlowControlMode();

        // Set connection parameters, if set fails return parameters object
        // to original state.
        try {
            sPort.setSerialPortParams(parameters.getBaudRate(), parameters
                    .getDatabits(), parameters.getStopbits(), parameters
                    .getParity());
        } catch (UnsupportedCommOperationException e) {
            parameters.setBaudRate(oldBaudRate);
            parameters.setDatabits(oldDatabits);
            parameters.setStopbits(oldStopbits);
            parameters.setParity(oldParity);
            throw new SerialConnectionException("Unsupported parameter");
        }

        // Set flow control.
        try {
            sPort.setFlowControlMode(parameters.getFlowControlIn()
                    | parameters.getFlowControlOut());
        } catch (UnsupportedCommOperationException e) {
            throw new SerialConnectionException("Unsupported flow control");
        }
    }

    /**
     * Close the port and clean up associated elements.
     */
    public static void closeConnection() {
        // If port is alread closed just return.
        if (!open) {
            System.err.println("Attempting to close an already closed port.");
            return;
        }
        System.out.println("Closing connection to the currently targeted port");

        // Check to make sure sPort has reference to avoid a NPE.
        if (sPort != null) {
            try {
                // close the i/o streams.
                outputToUtecStream.close();
                inputFromUtecStream.close();
            } catch (IOException e) {
                System.err.println(e);
            }

            // Close the port.
            sPort.close();

            // Remove the ownership listener.
            // portId.removePortOwnershipListener(this);
        }

        open = false;
    }

    /**
     * Send a one second break signal.
     */
    private static void sendBreak() {
        sPort.sendBreak(1000);
    }

    /**
     * Set listener for serial events
     * @param serialListener
     */
    //public static void setSerialListener(UtecSerialListener serialListener) {
    //	UtecSerialConnectionManager.serialListener = serialListener;
    //}

    /**
     * Return whether or not the port is open
     *
     * @return
     */
    public static boolean isOpen() {
        return open;
    }

    public static InputStream getInputFromUtecStream() {
        return inputFromUtecStream;
    }

    public static OutputStream getOutputToUtecStream() {
        return outputToUtecStream;
    }

    public void serialEvent(SerialPortEvent arg0) {
        // TODO Auto-generated method stub

    }
}
