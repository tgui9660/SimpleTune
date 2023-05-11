package logger.dataSupplier.impl.ssm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.dataManager.LoggerManager;
import logger.interfaces.LoggingAttribute;
import logger.interfaces.LoggingSwitch;

/**
 * Periodically pings the ECU for switch status information.
 * - CALID
 * - SWITCHES ACTIVE
 * - Applicable Attributes based on CALID (see logger.xml, parameters section)
 * 
 * @author emorgan
 *
 */
public class SSMECUInit implements SSMQueryResultListener{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private static SSMECUInit instance = null;
	
	
	// Store the current ROM calid
	String calID = "";
	
	// Periodic thread settings
	int delay = 1000;   // delay 
    int period = 5000;  // repeat 
    Timer timer = new Timer();
    
    private boolean isActive = true;
    
    //private SSMDataSupplier supplier = null;
    
	/**
	 * Singleton code
	 * @return
	 */
	public static SSMECUInit getInstance(){
		if(SSMECUInit.instance == null){
			SSMECUInit.instance = new SSMECUInit();
		}
		
		return SSMECUInit.instance;
	}
	private SSMECUInit(){
		//timer.schedule(new SSMECUInitTimerTask(this), delay, period);
		timer.scheduleAtFixedRate(new SSMECUInitTimerTask(this), delay, period);
	}
	
	
	/**
	 * Kicks off an ECU query into 
	 *  - which switches are ready
	 *  - calid of car
	 *  - what SSM parameters are available
	 *  
	 *  Called by the timer task periodically
	 */
	public void startECUInitQuery(){
		// Place query on queue and register this class as the listener for results
		if(this.isActive){
			System.out.println("SSMECUInit : Calling for ECU Init status");
			SSMQueryQueue.getInstance().registerSSMQuery(new SSMQuery(this));
		}

	}
	
	/**
	 * Toggled by start and stop logging to maximize the query throughput.
	 * 
	 * TODO, Maybe removed, can't decide if I want app to try to connect to multiple cars
	 * without a restart. No reason to really. As of now, restart for new cars is needed.
	 * 
	 * @param value
	 */
	public void setActive(boolean value){
		//this.isActive = value;
	}
	
	/**
	 * Called after the ECU Init query is completed.
	 */
	public void SSMQueryResult(SSMQuery ssmQuery) {
		//Sanity check
		if(ssmQuery.getStatus() == SSMQuery.MALFORMED_RESPONSE){
			// Fire off a retry ASAP!
			this.startECUInitQuery();
			
			// Return and wait for the callback.
			return;
		}else if(ssmQuery.getStatus() == SSMQuery.ECU_CONNECTION_FAILED){
			return;
		}
	
		// Output the results
		
		System.out.println("ECUINIT Values ---------------------------");
		System.out.println(ssmQuery.getFullQueryResultPrintout());
		System.out.println(ssmQuery.getDataBytesResultPrintout());
		System.out.println("ECUINIT Values ---------------------------");
		
		
		// *****************
		// Parse CALID value
		// *****************
		this.calID = "";
		for(int i = 0; i < 5; i++){
			Byte byte1 = ssmQuery.getResult().get(i+14);
			char tempChar = (char)(byte1.byteValue());
			String hexString = Integer.toHexString(tempChar).toUpperCase();
			if(hexString.length() == 1){
				hexString = "0"+hexString;
			}
			this.calID = this.calID +hexString;
		}
		logger.info("****** CALID: "+this.calID);
		
		
		// *******************************************************************
		// Iterate through all attributes and build list of ones to be removed
		// *******************************************************************
		LinkedList<LoggingAttribute> loggingAttributes = SSMDataSupplier.getInstance().getLoggingAttributes();
		Iterator<LoggingAttribute> iterator = loggingAttributes.iterator();
		LinkedList<LoggingAttribute> toBeRemoved = new LinkedList<LoggingAttribute>();
		
		//System.out.println("SSM ECU Init raw data result bytes size : "+rawDataResultBytes.size());
		
		while(iterator.hasNext()){
			SSMLoggingAttributeImpl next = (SSMLoggingAttributeImpl)iterator.next();
			
			
			// Look at only those that are NOT calculated values
			if(next.getEcuByteIndex() != null){
				Integer ecuBit = next.getEcuBit();
				Integer ecuByteIndex = next.getEcuByteIndex();
				
				boolean attributeEnabled = ssmQuery.isObjectEnabled(ecuByteIndex + 1, ecuBit);
				if(!attributeEnabled){
					toBeRemoved.add((LoggingAttribute)next);
				}
			}else{
				//System.out.println("SSM ECU Init calculated value found : "+next.getName());
			}

		}
		
		
		// *****************************
		// Call GUI to remove attributes
		// *****************************
		LoggerManager.getInstance().removeAttributes(toBeRemoved);
		
		// We are DONE!! No more ECU init logging for now
		this.isActive = false;
	}

	
	/**
	 * Get the ECU call ID as parsed from the ECU init query.
	 * @return
	 */
	public String getCALID(){
		return this.calID;
	}
}
