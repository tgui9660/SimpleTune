package logger.dataSupplier.impl.ssm;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Encompasses all bits of information needed to complete an ECU query
 *
 * @author emorgan
 *
 */
public class SSMQuery {
	
	private Log logger = LogFactory.getLog(getClass());
	
	// Query status
	public static int OK = 0;
	public static int ECU_CONNECTION_FAILED = 1;
	public static int MALFORMED_RESPONSE = 2;
	
	// Raw Bytes of query to be sent to ECU
	private byte[] query = null;
	
	// Result Bytes from the ECU query
	private LinkedList<Byte> result = null;
	
	// Listener that generated this request
	// Used to route message back
	private SSMQueryResultListener listener = null;
	
	// Query data with no headers or checksums
	private LinkedList<Byte> rawDataQueryBytes = null;
	
	// What kind of ECU query is this? As defined in static vars in Query Builder
	private byte messageType = -1;
	
	// Do we pad the query?
	private boolean paddedValue = false;

	// Result data with no headers or checksums
	private LinkedList<Byte> rawDataResultBytes = new LinkedList<Byte>();

	// Get query status
	private int status = -1; // Default for healthy

	// Set the number of users to define this. Used for when this is selected by the user from
	// the gui, or pulled in as part of a multi query construct.
	private int userCount = 0;
	

	
	/**
	 * Special constructor for ECUInit queries
	 * 
	 * @param listener
	 */
	public SSMQuery(SSMQueryResultListener listener){
		this.query = SSMQueryBuilder.getInstance().getECUInitByteSequence();
		this.listener = listener;
	}

	/**
	 * Generic constructor for any type of query
	 * 
	 * @param messageType
	 * @param rawDataBytes
	 * @param paddedValue
	 * @param listener
	 */
	public SSMQuery(byte messageType, LinkedList<Byte> rawDataBytes, boolean paddedValue, SSMQueryResultListener listener){
		this.messageType = messageType;
		this.paddedValue = paddedValue;
		this.rawDataQueryBytes = rawDataBytes;
		this.listener = listener;
		
		
		// Generate the raw query
		this.query = SSMQueryBuilder.getInstance().getCommandByteSequence(this.messageType, this.rawDataQueryBytes, this.paddedValue);
		
	}
	
	
	/**
	 * Helper class that bit masks per passed byte and bit positions and indexed result bye. 
	 * 
	 * @param ecuByte
	 * @param ecuBit
	 * @return
	 */
	public boolean isObjectEnabled(int ecuByte, int ecuBit){
		
		// Pull the byte of interest
		byte byteValue = rawDataResultBytes.get(ecuByte);
		
		// Output some metrics of interest
		int tempInt = ((int)byteValue&0xFF);
		System.out.println(" -- Init byte : "+ecuByte+"   bit : "+ecuBit+"   rawValue : "+tempInt);
		
		// Flag to be eventually returned
		boolean isAvailable = false;
		
		// Pull the bit value from the byte by applying the bit mask derived from the ecuBit
		Double pow = Math.pow(2, ecuBit);
		Short powerValue = pow.shortValue();
		System.out.println(" -- ECUBIT ^ :"+powerValue);
		int bitValue = byteValue & powerValue.byteValue();
		System.out.println(" -- BitValue : "+bitValue);
		
		// Is this a hit?!
		if(bitValue != 0){
			isAvailable = true;
		}
		
		System.out.println(" -- Available : "+isAvailable);
		
		return isAvailable;
	}
	
	/**
	 * Get string with raw data bytes from result
	 * @return
	 */
	public String getDataBytesResultPrintout(){
		String result = "";
		result += "-----------------------------------\n";
		result += "-------RESULT DATA BYTES ONLY------\n";
		result += "-----------------------------------\n";
		result += "-- [Listener : "+this.listener.getClass().getName()+"]\n";
		Iterator<Byte> iterator = this.rawDataResultBytes.iterator();
		int counter = 0;
		int total = 0;
		while(iterator.hasNext()){
			Byte next = iterator.next();
			total += ((int)next&0xFF);
			result += counter+" : --->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF)+"\n";
			counter++;
		}
		result += "Total Byte Sum   : "+total+"\n";
		result += "Total Byte Count : "+counter+"\n";
		result += "-----------------------------------\n";
		
		return result;
	}
	
	/**
	 * Get Helpful result printout
	 * @return
	 */
	public String getFullQueryResultPrintout(){
		String result = "";
		result += "-----------------------------------\n";
		result += "-----FULL RESULT BYTES FROM ECU----\n";
		result += "-----------------------------------\n";
		result += "-- [Listener : "+this.listener.getClass().getName()+"]\n";
		
		Iterator<Byte> iterator = this.result.iterator();
		int counter = 0;
		int total = 0;
		while(iterator.hasNext()){
			Byte next = iterator.next();
			total += ((int)next&0xFF);
			result += counter+" : --->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF)+"\n";
			counter++;
		}
		result += "Total Byte Sum   : "+total+"\n";
		result += "Total Byte Count : "+counter+"\n";
		result += "-----------------------------------\n";
		
		return result;
	}
	
	public LinkedList<Byte>getFullQueryBytes(){
		LinkedList<Byte> values = new LinkedList<Byte>();
		for(int i = 0; i < this.query.length; i++){
			values.add(this.query[i]);
		}
		return values;
	}
	
	public String getFullQueryPrintout(){
		String result = "";
		result += "-----------------------------------\n";
		result += "------FULL QUERY BYTES TO ECU------\n";
		result += "-----------------------------------\n";
		result += "-- [Listener : "+this.listener.getClass().getName()+"]\n";
		Iterator<Byte> iterator = this.getFullQueryBytes().iterator();
		int counter = 0;
		int total = 0;
		while(iterator.hasNext()){
			Byte next = iterator.next();
			total += ((int)next&0xFF);
			result += counter+" : --->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF)+"\n";
			counter++;
		}
		result += "Total Byte Sum   : "+total+"\n";
		result += "Total Byte Count : "+counter+"\n";
		result += "-----------------------------------\n";
		
		return result;
	}
	
	public String getRawQueryDataPrintout(){
		String result = "";
		result += "-----------------------------------\n";
		result += "------RAW QUERY BYTES TO ECU------\n";
		result += "-----------------------------------\n";
		result += "-- [Listener : "+this.listener.getClass().getName()+"]\n";
		Iterator<Byte> iterator = this.rawDataQueryBytes.iterator();
		int counter = 0;
		int total = 0;
		while(iterator.hasNext()){
			Byte next = iterator.next();
			total += ((int)next&0xFF);
			result += counter+" : --->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF)+"\n";
			counter++;
		}
		result += "Total Byte Sum   : "+total+"\n";
		result += "Total Byte Count : "+counter+"\n";
		result += "-----------------------------------\n";
		
		return result;
	}
	
	/**
	 * Once the overseeing body deems the result in this query is correct, let the listener know.
	 */
	public void resultReadyForListener(){
		
		// Ouput the result
		System.out.println(this.getFullQueryResultPrintout());
		
		// Call listener with result
		this.listener.SSMQueryResult(this);
	}
	
	/**
	 * Is the result of the query sane?
	 * If not, you might want to ignore it.
	 * @return
	 */
	public boolean isHealthy(){
		if(this.result == null){
			return false;
		}
		
		if(result.size() <= this.query.length){
			return false;
		}
		
		if(result.size() > (this.query.length + 5 + this.rawDataQueryBytes.size() + 1)){
			return false;
		}
		
		// Sanity check
		if(this.rawDataResultBytes.size() == 0){
			logger.error("No raw data bytes in result. Probably an echoed query result.");
			return false;
		}
		
		return true;
	}
	

	/**
	 * Pull raw data from query
	 * 
	 * Removes:
	 * - repeated query header
	 * - 
	 * 
	 * @param result
	 * @param query
	 * @return
	 */
	private LinkedList<Byte> getDataFromQuery(LinkedList<Byte> result, LinkedList<Byte> query){
		if(result.size() == query.size()){
			return new LinkedList<Byte>();
		}
		
		LinkedList<Byte> finished = new LinkedList<Byte>();
		
		Iterator<Byte> iterator = result.iterator();
		int counter = 0;
		while(iterator.hasNext()) {
			Byte next = iterator.next();
			
			// Add everything but echoed header
			if(counter >= query.size()){
				finished.add(next);
			}
			
			counter++;
		}
			
		// Remove leading result header
		int headerSize = 4;
		if(this.paddedValue){
			headerSize++;
		}
		for(int i = 0; i < headerSize; i++){
			finished.remove(0);
		}
		
		// Remove trailing checksum from result
		finished.remove(finished.size() - 1);
		
		return finished;
	}
	
	/**
	 * Helper method, calls:LinkedList<Byte> getDataFromQuery(LinkedList<Byte> result, LinkedList<Byte> query)
	 * @param result
	 * @param query
	 * @return
	 */
	private LinkedList<Byte> getDataFromQuery(LinkedList<Byte> result, byte[] query){
		LinkedList<Byte> finished = new LinkedList<Byte>();
		
		for(int i = 0; i < query.length; i++){
			finished.add(query[i]);
		}
		
		return this.getDataFromQuery(result, finished);
	}
	
	
	// *******************
	// Getters and setters
	// *******************
	public int getStatus() {
		if(this.status == SSMQuery.OK){
			if(!this.isHealthy()){
				this.status = SSMQuery.MALFORMED_RESPONSE;
			}
		}
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public SSMQueryResultListener getListener() {
		return listener;
	}
	public byte[] getQuery() {
		System.out.println(this.getFullQueryPrintout());
		return query;
	}
	public LinkedList<Byte> getResult() {
		return result;
	}
	public LinkedList<Byte> getRawDataResultBytes() {
		return rawDataResultBytes;
	}
	public void setResult(LinkedList<Byte> result) {
		//System.out.println("SSM Query set result, count: "+result.size());
		
		// Extract return data bytes
		this.rawDataResultBytes = this.getDataFromQuery(result, this.query);
		
		// Save off full result
		this.result = result;
	}
	public void userCountInc(){
		this.userCount++;
	}
	public void userCountDec(){
		this.userCount--;
	}
	public int getUserCount(){
		return this.userCount;
	}
	public LinkedList<Byte> getRawQueryBytes(){
		return this.rawDataQueryBytes;
	}
}
