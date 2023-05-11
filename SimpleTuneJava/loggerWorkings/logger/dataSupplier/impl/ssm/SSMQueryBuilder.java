package logger.dataSupplier.impl.ssm;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class builds up the raw byte commands to send to ECU based on passed attribute addresses.
 * 
 * This is a helper class. Also contains useful command utilities.
 * 
 * @author botman
 *
 */
public class SSMQueryBuilder {
	
	private Log logger = LogFactory.getLog(getClass());
	

	// Singleton Instance
	private static SSMQueryBuilder ssmQueryBuilder = null;
	
	// Some interactions require that a padding byte of 0x00 is added
	public static boolean DATA_PADDED = true;
	public static boolean DATA_NOT_PADDED = false;
    
    // Memory access types
    public static byte BLOCK_READ_MEMORY = (byte)0xA0;
    public static byte ADDRESS_LIST_READ_MEMORY = (byte)0xA8;
    public static byte BLOCK_WRITE_MEMORY = (byte)0xB0;
    public static byte ADDRESS_LIST_WRITE_MEMORY = (byte)0xB8;
    public static byte ECU_INIT = (byte)0xBF;
	

	/**
	 * Singleton code.
	 * 
	 * @return
	 */
	public static SSMQueryBuilder getInstance(){
		if(SSMQueryBuilder.ssmQueryBuilder == null){
			SSMQueryBuilder.ssmQueryBuilder = new SSMQueryBuilder();
		}
		
		return SSMQueryBuilder.ssmQueryBuilder;
	}
	private SSMQueryBuilder(){
		
	}

	/**
	 * Returns the ECU Init bytes
	 * @return
	 */
	public byte[] getECUInitByteSequence(){
		byte[] commands = new byte[6];
		commands[0] = (byte)0x80; // Every SSM command starts with this
		commands[1] = (byte)0x10; // Destination subaru ecu
		commands[2] = (byte)0xF0; // Source diagnostic tool
		commands[3] = (byte)(1); // Data size plus command plus command and other bytes
		commands[4] = SSMQueryBuilder.ECU_INIT; // SSM command to send, read memory
		commands[5] = this.getCheckSum(commands);
		
		return commands;
	}
	
	/**
	 * Method generates a query based on the passed parameters and data.
	 * 
	 * @param type
	 * @param data
	 * @param isPadding
	 * @return
	 */
	public byte[] getCommandByteSequence(byte type, LinkedList<Byte> data, boolean isPadding){
		//System.out.println("Padding is :"+isPadding);
		
		byte[] commands = null;
		
		// Byte count first includes:....
		//
		// Every SSM command starts with; 0x80
		// Destination subaru ecu; 0x10
		// Source diagnostic tool; 0xF0
		// Size of data, byte count after this byte, not including checksum
		// SSM Command to send, ie "type" in this case
		// Data Bytes
		// End of byte command set is the checksum
		
		int headerByteCount = 5;
		int dataByteCount = 1; // Used for setting one of the packet attributes "data size"
		
		// Add space for data
		// 3 bytes per address queried
		dataByteCount = 3 * data.size();
		
		// Alter command dimensions
		if(isPadding){
			dataByteCount++; 
		}
		
		// Add one more byte for the checksum
		dataByteCount++;
		
		// Lets see some of the dimensions gathered
		/*
		System.out.println("-------------------------");
		System.out.println("Header Byte Count : "+headerByteCount);
		System.out.println("Data Byte Count   : "+dataByteCount);
		System.out.println("-------------------------");
		*/
		
		// Total command size combines and pre and post byte counts
		commands = new byte[headerByteCount + dataByteCount];
		
		// Setup ECU query header
		commands[0] = (byte)0x80; // Every SSM command starts with this
		commands[1] = (byte)0x10; // Destination subaru ecu
		commands[2] = (byte)0xF0; // Source diagnostic tool
		commands[3] = (byte)(dataByteCount); // Data size plus command plus command and other bytes
		commands[4] = type; // SSM command to send, read memory
		
		
		// Add padding byte if needed
		if(isPadding){
			// Padding
			commands[5] = (byte)0x00; 
			
			// Move up one as data addition is keyed on the header size
			// NOTE, not a true rep of header size
			headerByteCount++;
		}
		
		
		// Add data to command
		if(data != null){
			Iterator<Byte> iterator = data.iterator();
			
			int counter = 0;
			while(iterator.hasNext()){
				Byte next = iterator.next();
				
				//System.out.println("Adding Passed Data : "+next);
				
				ByteBuffer buf = ByteBuffer.allocate(4);
				buf.putInt(0, next);
				
				byte[] array2 = buf.array();
				
				for(int i = 0; i < 3; i++){
					commands[headerByteCount + counter] = array2[i+1];
					counter++;
				}
			}
		}

		
		// Add the checksum
		commands[commands.length - 1] = getCheckSum(commands);
		

		/*
		System.out.println("-------------------------");
		System.out.println("FINAL ECU QUERY");
		System.out.println("-------------------------");
		*/
		
		/*
		// Final sanity test output
		for(int i = 0; i < commands.length; i++){
			System.out.println(" =====> "+Integer.toHexString((int)commands[i]&0xFF).toUpperCase());
		}
		*/
		
		return commands;
	}
	
	/**
	 * Calculate the checksum in the generated ECU query
	 * @param bytesToSend
	 * @return
	 */
	private byte getCheckSum(byte[] bytesToSend){
		long sumValue = 0;
		for(int i = 0; i < bytesToSend.length -1; i++){
			sumValue += (int)bytesToSend[i]&0xFF;
			//System.out.println("##### :"+(bytesToSend[i]&0xFF)+" <> "+sumValue + " <> " + Integer.toHexString((int)bytesToSend[i]&0xFF));
		}
		//System.out.println("Checksum found :"+(byte)(sumValue&0xFF));
		
		return (byte)(sumValue&0xFF);
	}
	
	/**
	 * Extracts the raw data from a query by comparing the input "query" to the "result".
	 * Note, the result of a query to the ECU has a header that contains the initial query.
	 * 
	 * @param query
	 * @param result
	 * @return
	 */
	public LinkedList<Byte> getRawData(byte[] query, LinkedList<Byte> result){
		System.out.println("QUERY BUILDER RAW DATA EXTRACTOR");
		
		Iterator<Byte> iterator = result.iterator();
		int counter = 0;
		LinkedList<Byte> extractedTotalData = new LinkedList<Byte>();
		while(iterator.hasNext()){
			Byte next = iterator.next();
			System.out.println("--->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF));
			
			if(query == null){
				System.out.println("NULL");
			}
			
			// Remove echoed query in response
			if(counter >= query.length + 5 && counter < result.size()){
				extractedTotalData.add(next);
			}
			counter++;
		}
		System.out.println("\n----------------------\n");
		
		
		return extractedTotalData;
	}
}
