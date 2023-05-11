package logger.dataSupplier.impl.ssm;


import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;

import logger.dataManager.LoggerManager;
import logger.interfaces.LoggingAttribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nfunk.jep.JEP;

/**
 * 
 * External classes use this class to control the flow of data
 * 
 * Class coordinates the extraction of many ECU parameters at once.
 * 
 * Lots of interaction between SSMDataQueryTimerTask and boolean flags, read carefully!
 * 
 * @author botman
 *
 */
public class SSMDataQueryManager implements SSMQueryResultListener{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private static SSMDataQueryManager instance = null;
	
	// Current active attributes
	private LinkedList<LoggingAttribute> activeAttributes = new LinkedList<LoggingAttribute>();
	
	// Attributes pending to be made active. Can't just add an attribute while an ECU query is being formed.
	private LinkedList<LoggingAttribute> addAttributes = new LinkedList<LoggingAttribute>();
	
	// Attributes pending to be made inactive. Can't just remove an attribute while an ECU query is being formed.
	private LinkedList<LoggingAttribute> removeAttributes = new LinkedList<LoggingAttribute>();
	
	//Expression parser
	private JEP jep = new JEP();
	
	// ECU result from a query contains a header which is the mimic of the query sent, which is stored in the following.
	// Also, justSentQueryByteCount, a nice to have
	//private byte[] justSentQuery = null;
	//private int justSentQueryByteCount = 0;
	
	// Since a number of steps occur to get a result from the ECU, this flag is set to true/false to control whether or not data is flowing
	// Set by stop and start logging methods
	//
	private boolean pullData = false;
	
	// Setup the logging timer that attempts to just start the current ecu query
	int delay = 1000;   // delay for 1 sec.
    int period = 1500;  // repeat every 1.5 sec.
    Timer timer = new Timer();
    
    // Might not need this, but this boolean tells whether or not a pull data is waiting on a query
    // result. Stops the timer thread from successfully calling pullData and filling the query queue
    private boolean pullDataIsBusy = false;
    
    /**
     * Singleton code.
     * @return
     */
	public static SSMDataQueryManager getInstance(){
		if(SSMDataQueryManager.instance ==  null){
			SSMDataQueryManager.instance = new SSMDataQueryManager();
		}
		return SSMDataQueryManager.instance;
	}
	private SSMDataQueryManager(){
		timer.schedule(new SSMDataQueryTimerTask(this), delay, period);
	}
	
	/**
	 * Adds an attribute from the total query
	 * @param loggingAttribute
	 */
	public void addLoggingAttribute(SSMLoggingAttributeImpl loggingAttribute){
		System.out.println("SSMQueryManager ADDING logging attribute: "+loggingAttribute.getName());
		
		if(!this.addAttributes.contains(loggingAttribute)){
			// Add to pending list of attribute to add to query
			this.addAttributes.add(loggingAttribute);
			
			// If we just added an attribute for the first time, start logging again.
			if(this.addAttributes.size() == 1){
				//this.pullData();
			}
			
		}

	}
	
	/**
	 * Removes an attribute to the total query
	 * 
	 * @param loggingAttribute
	 */
	public void removeLoggingAttribute(SSMLoggingAttributeImpl loggingAttribute){
		
		// Not much use if name is empty
		if(loggingAttribute.getName().length() > 0){
			System.out.println("SSMQueryManager REMOVING logging attribute: "+loggingAttribute.getName());
		}
		
		if(!this.removeAttributes.contains(loggingAttribute)){
			// Add to pending list of attribute to remove from query
			this.removeAttributes.add(loggingAttribute);
		}
		
	}
	
	/**
	 * Externally called to stop data pull
	 */
	public void stopLogging(){
		System.out.println("SSMQueryManager STOP LOGGING");
		
		// While logging we dont need any info from ECU init
		SSMECUInit.getInstance().setActive(true);
		
		// Set logging flag
		this.pullData = false;
		
	}
	
	/**
	 * Externally called to initiate data pull
	 */
	public void startLogging(){
		System.out.println("SSMQueryManager START LOGGING");
		SSMECUInit.getInstance().setActive(false);
		
		// Set logging flag
		// Logger timer will then kick off pullData
		this.pullData = true;
		
	}

	/**
	 * Queries ECU with live attributes in queries LinkedList
	 */
	public void pullData() {
		
		// Pull dota only if...
		// - pulldata flag is set
		// - if we have data in either active or add attributes
		// - pullData is waiting on a ECU return
		if(this.pullDataIsBusy || !this.pullData || (this.activeAttributes.size() == 0 && this.addAttributes.size() == 0)){
			//System.out.println("!!!!!!!! SSMDATAMANAGER : NOT Pulling data.");
			return;
		}
		
		// We're working here!!! Timer Task back off, ok?!!!
		this.pullDataIsBusy = true;
		
		// Look for any attributes that need to be removed from the master query
		if(this.removeAttributes.size() > 0){
			Iterator<LoggingAttribute> removeIterator = this.removeAttributes.iterator();
			while(removeIterator.hasNext()){
				LoggingAttribute next = removeIterator.next();
				this.activeAttributes.remove(next);
				System.out.println("Removed attribute: "+next.getName());
			}
			this.removeAttributes.clear();
		}
		
		
		// Look for any new attributes to add to the master query
		if(this.addAttributes.size() > 0){
			Iterator<LoggingAttribute> addIterator = this.addAttributes.iterator();
			while(addIterator.hasNext()){
				LoggingAttribute next = addIterator.next();
				this.activeAttributes.add(next);
				System.out.println("Adding attribute: "+next.getName());
			}
			this.addAttributes.remove();
		}


		// Make sure there are attributes selected to query on.
		// If no attributes, do nothing.
		// When an attribute is added next, the pullData() will be called.
		if(this.activeAttributes.size() == 0){
			this.pullDataIsBusy = false;
			return;
		}
		
		// We're guaranteed to be pulling data at this point :-)
		//System.out.println("!!!!!!!! SSMDATAMANAGER : Pulling data!");
		
    	// Build raw ECU query bytes
		// base address + any count of ecubits in an array
    	LinkedList<Byte> data = new LinkedList<Byte>();
    	Iterator<LoggingAttribute> iterator = this.activeAttributes.iterator();
    	while(iterator.hasNext()){
    		SSMLoggingAttributeImpl loggingAttribute = (SSMLoggingAttributeImpl)iterator.next();
    		Integer addressLength = loggingAttribute.getAddressLength();
    		Integer address = loggingAttribute.getAddress();
    		
    		for(int i = 0; i < addressLength; i++){
    			Integer calcedAddress = address + i;
    			data.add(calcedAddress.byteValue());
    		}
    	}
		
		// Build new query object
		SSMQuery theQuery = new SSMQuery(SSMQueryBuilder.ADDRESS_LIST_READ_MEMORY, data, true, this);
		
		// Place command on query queue
		SSMQueryQueue.getInstance().registerSSMQuery(theQuery);
		
	}
	
	
	public void SSMQueryResult(SSMQuery data) {
		//System.out.println("SSM Data Manager got result count: "+data.getResult().size());
		
		// If truncated or too long result, don't return results
		if(data.getStatus() == SSMQuery.MALFORMED_RESPONSE){
			System.out.println("!!!!!! SSMQueryManager : Failed query attempt, packet returned malformed.");

			this.pullDataIsBusy = false;
			
			this.pullData();
		}else if(data.getStatus() == SSMQuery.ECU_CONNECTION_FAILED){
			this.pullDataIsBusy = false;
			return;
		}
		

		// Quick output of result
		System.out.println("SSM Query Manager Got result from ECU Query.");
		System.out.println("\n----------------------\nDATA RECEIVED FROM ECU\n----------------------");
		Iterator<Byte> iterator = data.getResult().iterator();
		while(iterator.hasNext()){
			Byte next = iterator.next();
			System.out.println("--->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF));
		}
		System.out.println("\n----------------------\n");
		
		
		// What extracted data do we have?
		System.out.println("\n----------------------\nRAW DATA EXTRACTED FROM RESULT\n----------------------");
		Iterator<Byte> iterator2 = data.getRawDataResultBytes().iterator();
		while(iterator2.hasNext()){
			Byte next = iterator2.next();
			System.out.println("--->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF));
		}
		System.out.println("\n----------------------\n");
		
		
		// Iterate through result and call attributes with new data
		System.out.println("Iterating through returned data set.");
		Iterator<LoggingAttribute> attrIterator = this.activeAttributes.iterator();
		
		// Position in received data stream
		int position = 0;  
		
		// Iterate through ordered list of attributes and pull data
		// This is needed as an ecu query might include many attributes (rpm, maf, etc..)
		while(attrIterator.hasNext()){
			SSMLoggingAttributeImpl attr = (SSMLoggingAttributeImpl)attrIterator.next();
			System.out.println("Processing : "+ attr.getName());
			
			// Pull ecuBit count, default to 1 if needed
			Integer ecuBit = attr.getAddressLength();
			if(ecuBit == null || ecuBit < 1){
				ecuBit = 1;
			}
			
			// Raw value pulled from response before conversion is applied
			short rawValue = -1;
			
			// Temp storage of bytes used to build up the raw value above
			byte[] tempBytes = new byte[ecuBit];
			
			// Push data into temp structure
			for(int i = 0; i < ecuBit; i++){
				tempBytes[i] = data.getRawDataResultBytes().get(i + position);	
				System.out.println(" GOT: "+Integer.toHexString((int)tempBytes[i]&0xFF));
			}
			
			// Update position in result data byte array
			position += ecuBit;
			
			// Pull the raw data now
			if(ecuBit > 1){
				ByteBuffer bb = ByteBuffer.allocate(ecuBit);
				bb.put(tempBytes);
				rawValue = bb.getShort(0);
			}else{
				ByteBuffer bb = ByteBuffer.allocate(2);
				byte[] bytes = new byte[2];
				bytes[1] = tempBytes[0];
				bytes[0] = new Byte("00000000");
				
				bb.put(bytes);
				rawValue = bb.getShort(0);
			}
			
			// Apply conversion
			String expression = attr.getExpression();
			jep.addVariable("x", rawValue);
			jep.parseExpression(expression); 
			double value = jep.getValue();
			System.out.println("Expression: "+expression);
			System.out.println("Raw value: " + rawValue);
			System.out.println("Calced value: " + value);
			
			// Place new value into attribute
			attr.setValue(value);
		}
		
		// Let the logging manager know new data is ready
		LoggerManager.getInstance().newAttributeDataAvailable(this.activeAttributes);
		
		// Not so busy now, allow the timer task to successfully launch pulldata()
		this.pullDataIsBusy = false;
		
		// Kick off next round, go as fast as possible ignoring timed event calls to pullData
		// from the timer task.
		this.pullData();
	}

}

