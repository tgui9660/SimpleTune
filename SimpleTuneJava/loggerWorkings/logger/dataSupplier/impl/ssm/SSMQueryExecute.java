package logger.dataSupplier.impl.ssm;

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
import java.util.LinkedList;
import java.util.TooManyListenersException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simple class that queries the ECU with a passed command byte sequence.
 * 
 * Only used by the query queue.
 * 
 * @author emorgan
 *
 */
public class SSMQueryExecute implements SerialPortEventListener{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private static SSMQueryExecute instance = null;
	
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
    
    // Pause between byte reads on ECU response
    private static int BYTE_READ_PAUSE = 0;
    
    // Pause between ECU responses
    private static int COMMAND_REPEAT_PAUSE = 0;
    
    // **************************************
    // Define comm port connection attributes
    // **************************************
    private static int BAUD = 4800;
    private static int DATABITS = SerialPort.DATABITS_8;
    private static int STOPBITS = SerialPort.STOPBITS_1;
    private static int PARITY = SerialPort.PARITY_NONE;
    private static int FLOWCONTROL = SerialPort.FLOWCONTROL_NONE;
    private static boolean RTS = false;
    
    private SSMQuery pendingQuery = null;
    
	/**
	 * Singleton code.
	 * @return
	 */
	public static SSMQueryExecute getInstance(){
		if(SSMQueryExecute.instance == null){
			SSMQueryExecute.instance = new SSMQueryExecute();
		}
		
		return SSMQueryExecute.instance;
	}
	private SSMQueryExecute(){
		// Make our comm port selection based on the list printed out.
    	boolean success = bindCommPort(commPortChoice);
    	
    	// If comm port binding failed, exit.
    	if(!success){
    		System.out.println("There were problems biding comm port :"+commPortChoice);
    		return;
    	}
    	
		this.openConnection();
	}

	
    public boolean bindCommPort(String commPortName){
    	try {
			commPortIdentifier = CommPortIdentifier.getPortIdentifier(commPortName);
		} catch (NoSuchPortException e) {
			System.out.println("No port available by passed name :"+commPortName);
			return false;
		}
		
		return true;
    }
    
	/**
	 * Send the query to the ECU. If there is an issue, the user passed
	 * query will be echoed back.
	 * 
	 * @param ecuQuery
	 */
    public void executeECUQuery(SSMQuery query) {
    	// Save off query for when we get a result 
    	this.pendingQuery = query;
    	
    	query.getQuery();
    	
        if (serialPort == null) {
            System.err.println("\nNo Port Selected, therefore no interraction with ECU happening.\n");
            
            // Echo back the request with connection failed status
            query.setStatus(SSMQuery.ECU_CONNECTION_FAILED);
            query.setResult(query.getFullQueryBytes());
            SSMQueryQueue.getInstance().ssmQueryResult(query);
            
            return;
        }

        try {
        	// Write query to ecu stream
        	/*
        	for(int i = 0; i < ecuQuery.length; i++){
        		byte commandPiece = ecuQuery[i];
        		outputECUStream.write(commandPiece);
        		try {
        			Thread.sleep(1);
        		} catch (InterruptedException e1) {
        			e1.printStackTrace();
        		}	
        	}
        	*/
        	
        	// Write query all at once
        	outputECUStream.write(query.getQuery());
            
            //System.out.println("Sending query to the ECU:" + query.getQuery());
            for(int i = 0; i < query.getQuery().length; i++){
            	//System.out.println(" =====> "+Integer.toHexString((int)ecuQuery[i]&0xFF).toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("Can't send char command to ECU: " + query.getQuery());
            e.getMessage();
        }
    }

    public void openConnection() {
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
            serialPort.addEventListener(this);

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
        
        // Pause between before attempting to connect to ensure the port is ready
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

    }

    public void closeConnection() {
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
    
	public void serialEvent(SerialPortEvent e) {
		System.out.println("SSM Query Execute got data type :"+e.getEventType());
		//System.out.println("Wanted E data tpye :"+SerialPortEvent.DATA_AVAILABLE);
		
		/*
		try {
			Thread.sleep(0);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		*/
		
		/*
		try {
			int available = inputFromECUStream.available();
			System.out.println(" AVAILABLE: "+ available);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
		int newData = 1;
		LinkedList<Byte> receivedData = new LinkedList<Byte>();
		
		if(e.getEventType() == SerialPortEvent.DATA_AVAILABLE){
			System.out.println("SSM Query Execute reading response bytes.");
			while (newData != -1) {
				// Pause between pulls, yes dirty
				
				/*
				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				*/
				
				// You can read a block of bytes if needed, this implementation 
				// reads a byte one at a time from the inputstream
				try {
					newData = inputFromECUStream.read();
					
					if(newData != -1){
						receivedData.add((byte)newData);
					}
					
				} catch (IOException ex) {
					System.err.println(ex);
					//return;
				}
			}
		}
		
		/*
		try {
			Thread.sleep(250);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		*
		*/
		
		
		/*
		try {
			int available = inputFromECUStream.available();
			System.out.println(" 2 AVAILABLE: "+ available);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
		// Pause between pulls, yes dirty
		/*
		try {
			Thread.sleep(250);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		*/
		
		// Save result in query
		this.pendingQuery.setResult(receivedData);
		
		//System.out.println("SSM Query Execute about to call: SSM Query Queue with result");
		
		// Call SSMQueryQueu with result
		SSMQueryQueue.getInstance().ssmQueryResult(this.pendingQuery);
	}
}
