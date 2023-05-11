import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 * Class attempts to pull RPM data via SSM
 * 
 * @author Eric Morgan, eric.c.morgan@gmail.com
 *
 * FREE, USE AS YOU PLEASE
 */
public class SSMQueryTest{

	// Data from UTEC
	private static OutputStream outputToECUStream;

	// Data to UTEC
	private static InputStream inputFromECUStream;
	
	// Defines named comm port
	private static CommPortIdentifier portId;

	// Serial port being accessed
	private static SerialPort sPort;
	
	// Engine speed
	// Address: 0x00000E & 0x00000F
	// 16bit value
	// P0x0E high byte
	// P0x0F low byte
	// Conversion: divide by 4 to get RPM
	// SSM Command: 0xA8
	// A8 PP A1 A1 A1 A2 A2 A2 A3 A3 A3
	// 4800 bps n, 8, 1

	public static void main(String[] args){
		
		// Setup comm connection
		SSMQueryTest.setupCommConnection();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
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
		
		
		SSMQueryTest.sendCommand(bytesToSend);
		
	}
	
	public static void setupCommConnection() {
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
				
				try {
					sPort = (SerialPort) portId.open("SSM_DEMO", 2000);
					sPort.disableReceiveTimeout();
					sPort.setRTS(false);
					sPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
					sPort.setSerialPortParams(4800, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					
					inputFromECUStream = sPort.getInputStream();
					outputToECUStream = sPort.getOutputStream();
					
					sPort.addEventListener(new DataListener(inputFromECUStream));
				} catch (PortInUseException e) {
					e.printStackTrace();
				} catch (UnsupportedCommOperationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TooManyListenersException e) {
					e.printStackTrace();
				}
				
				
				
			}
		}
	}
	
	public static void sendCommand(byte[] commandBytes) {
		
		 if(sPort == null){ System.err.println("No Port Selected, therefore no interraction with Utec happening.");
		 	return; 
		 }

		try {

			outputToECUStream.write(commandBytes);
			
			System.out.println("Sending data to ECU :"+commandBytes);
		} catch (IOException e) {
			System.err.println("Can't send data to ECU : " + commandBytes);
			e.getMessage();
		}
	}

	public static InputStream getInputStream(){
		return SSMQueryTest.inputFromECUStream;
	}
	
	
	public static SerialPort getSPort() {
		return sPort;
	}

}
