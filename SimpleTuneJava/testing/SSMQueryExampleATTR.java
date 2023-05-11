import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TooManyListenersException;

import logger.dataSupplier.impl.ssm.SSMQueryBuilder;

public class SSMQueryExampleATTR {

    // Data from ECU
    private static OutputStream outputECUStream;

    // Data to ECU
    private static InputStream inputFromECUStream;

    // Defines named comm port
    private static CommPortIdentifier commPortIdentifier;

    // Serial port being accessed
    private static SerialPort serialPort;

    // Define state of defined comm port, open or closed.
    private static boolean open = false;

    // Set to whatever is your comm port as printed out by the comm port listing method
    private static String commPortChoice = "/dev/tty.usbserial-TX45b7tf";
    
    // Pause between byte reads
    private static int BYTE_READ_PAUSE = 0;
    private static int INITIAL_PAUSE = 10;
    
    // Define comm port connection attributes
    private static int BAUD = 4800;
    private static int DATABITS = SerialPort.DATABITS_8;
    private static int STOPBITS = SerialPort.STOPBITS_1;
    private static int PARITY = SerialPort.PARITY_NONE;
    private static int FLOWCONTROL = SerialPort.FLOWCONTROL_NONE;
    private static boolean RTS = false;
    
    //DIRTY
    private static boolean isRPM = true;
    
    public static void main(String[] args){
          
    	// What ports are available?
    	printOutCommPortList();
    	
    	// Make our comm port selection based on the list printed out.
    	boolean success = bindCommPort(commPortChoice);
    	
    	// If comm port binding failed, exit.
    	if(!success){
    		System.out.println("Issues during execution, exiting.");
    		System.exit(0);
    	}
    	
    	// Open the serial port connection.
    	openConnection();
	
    	// Sleep a little to ensure the port is really ready.
		try {
			Thread.sleep(INITIAL_PAUSE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Finally send compiled ECU query
		
		try {
			// TEST PULLING RPM
			byte[] dataToSend = {(byte)0x00,(byte)0x00,(byte)0x0E,(byte)0x00,(byte)0x00,(byte)0x0F}; 
			//sendECUCommand(SSMQueryBuilder.getInstance().getCommandByteSequence(SSMQueryBuilder.ADDRESS_LIST_READ_MEMORY, dataToSend));
			Thread.currentThread().sleep(60);
			
			// TEST PULLING ECU INIT
			isRPM = false;
			//sendECUCommand(SSMQueryBuilder.getInstance().getCommandByteSequence(SSMQueryBuilder.ECU_INIT));
			//sendECUCommand(bytesToSend);
			//Thread.currentThread().sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void printOutCommPortList(){
    	Enumeration<CommPortIdentifier> en = CommPortIdentifier.getPortIdentifiers();
		if (!en.hasMoreElements()) {
			System.out.println("No Valid ports found, check Java installation");
			System.out.println("Exiting now.");
		}
			
		//Iterate through the ports
		while (en.hasMoreElements()) {
			CommPortIdentifier chosenCommPort = en.nextElement();
			System.out.println("Serial found on system :"+chosenCommPort.getName());
		}
    }
    
    public static boolean bindCommPort(String commPortName){
    	try {
			commPortIdentifier = CommPortIdentifier.getPortIdentifier(commPortName);
		} catch (NoSuchPortException e) {
			System.out.println("No port available by passed name :"+commPortName);
			return false;
		}
		
		return true;
    }

    public static void sendECUCommand(byte[] charValue) {

        if (serialPort == null) {
            System.err.println("No Port Selected, therefore no interraction with ECU happening.");
            return;
        }

        try {
            outputECUStream.write(charValue);

            System.out.println("Sending query to the ECU:" + charValue);
        } catch (IOException e) {
            System.err.println("Can't send char command to ECU: " + charValue);
            e.getMessage();
        }
    }

    public static void openConnection() {
        System.out.println("Opening connection now.");
        
        // Set the parameters of the connection.
   		try {
			serialPort = (SerialPort) commPortIdentifier.open("SSM_DEMO", 2000);
			serialPort.disableReceiveTimeout();
			serialPort.setRTS(RTS);
			serialPort.setFlowControlMode(FLOWCONTROL);
			serialPort.setSerialPortParams(BAUD, DATABITS, STOPBITS, PARITY);
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}

        // Open the input and output streams for the connection. If they won't
        // open, close the port before throwing an exception.
        try {
            outputECUStream = serialPort.getOutputStream();
            inputFromECUStream = serialPort.getInputStream();
        } catch (IOException e) {
            System.err.println("Error opening IO streams");
            serialPort.close();
        }

        // Add this object as an event listener for the serial port.
        try {
            serialPort.addEventListener(new ECUDataListener(inputFromECUStream));

        } catch (TooManyListenersException e) {
            System.err.println("Too Many listeners");
            serialPort.close();
        }

        // Set notifyOnDataAvailable to true to allow event driven input.
        serialPort.notifyOnDataAvailable(true);

        // Set notifyOnBreakInterrup to allow event driven break handling.
        serialPort.notifyOnBreakInterrupt(true);

        // Set receive timeout to allow breaking out of polling loop during
        // input handling.
        try {
            serialPort.enableReceiveTimeout(30);
        } catch (UnsupportedCommOperationException e) {
            System.err.println("Time Out");
        }

        open = true;
        System.out.println("Port opened with success.");
    }

    public static void closeConnection() {
        // If port is already closed just return.
        if (!open) {
            System.err.println("Attempting to close an already closed port.");
            return;
        }
        System.out.println("Closing connection to the currently targeted port");

        // Check to make sure sPort has reference to avoid a NPE.
        if (serialPort != null) {
            try {
                // close the i/o streams.
                outputECUStream.close();
                inputFromECUStream.close();
            } catch (IOException e) {
                System.err.println(e);
            }

            // Close the port.
            serialPort.close();
        }

        open = false;
    }
    

    /**
     * Private class to listen for responses from the ECU.
     * 
     * @author emorgan
     *
     */
    private static class ECUDataListener  implements SerialPortEventListener{
    	
    	private LinkedList<Byte> commands = new LinkedList<Byte>();
    	private InputStream in = null;
    	
    	// Pass in the input stream used to get data from the ECU
    	public ECUDataListener(InputStream in){
    		this.in = in;
    	}
    	
    	/**
    	 * Part of the SerialPortEventListener set of methods.
    	 */
    	public void serialEvent(SerialPortEvent e) {
    		System.out.println("Got Data, Event type :"+e.getEventType());
    		System.out.println("Wanted data tpye :"+SerialPortEvent.DATA_AVAILABLE);
    		
    		int newData = 1;
    		
    		switch (e.getEventType()) {
    		case SerialPortEvent.DATA_AVAILABLE:
    			while (newData != -1) {
    				// Pause between pulls, yes dirty
    				try {
						Thread.currentThread().sleep(BYTE_READ_PAUSE);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
    				
    				// You can read a block of bytes if needed, this implementation 
					// reads a byte one at a time from the inputstream
    				try {
    					
    					newData = in.read();
    					
    					if(newData != -1){
    						commands.add((byte)newData);
    					}
    					
    				} catch (IOException ex) {
    					System.err.println(ex);
    					return;
    				}
    				
    			}
    			
    			
    			System.out.println("\n----------------------\nDATA RECEIVED FROM ECU\n----------------------");
    			Iterator<Byte> iterator = commands.iterator();
    			System.out.println("Printing out this many bytes :"+commands.size());
    			int counter = 0;
    			while(iterator.hasNext()){
    				counter++;
    				Byte next = iterator.next();
    				byte[] byteArray = new byte[1];
    				byteArray[0] = next;
    				BigInteger temp = new BigInteger(byteArray);
    				//System.out.println(counter + "  --->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF)+" <> "+temp.toString(2));
    				System.out.println(counter + "  --->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF)+" <> "+Integer.toString(next, 2));
    			}
    		
    			if(isRPM){
    				// Extract the RPM data
        			byte firstByte = commands.get(commands.size() - 3);
        			byte secondByte = commands.get(commands.size() - 2);
        			
        			System.out.println("First Byte :"+Integer.toHexString((int)firstByte&0xFF));
        			System.out.println("Second Byte :"+Integer.toHexString((int)secondByte&0xFF));
        			
        			
        			// Combine 2 result bytes into a short value
        			ByteBuffer bb = ByteBuffer.allocate(2);
        			byte[] bytes = new byte[2];
        			bytes[0] = firstByte;
        			bytes[1] = secondByte;
        			bb.put(bytes);
        			short rpmShort = bb.getShort(0);
        			System.out.println("Raw short value :"+ rpmShort);
        			System.out.println("\n\nRPM VALUE FOUND:"+rpmShort/4);
    			}
    			
    			
    			// Clean up now
    			SSMQueryExampleATTR.closeConnection();
    		}

    	}
    }
}
