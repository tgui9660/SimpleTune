package logger.dataSupplier.impl.ssm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.dataManager.LoggerManager;
import logger.interfaces.LoggingSwitch;

/**
 * Class periodically queries switch states via SSM.
 * 
 * If the defogger switches is enabled in the GUI and the SSM
 * query shows the defogger switch is depressed, then start 
 * the logging process.
 * 
 * @author emorgan
 *
 */
public class SSMLoggingSwitchManager implements SSMLoggingSwitchListener, SSMQueryResultListener{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private static SSMLoggingSwitchManager instance = null;
	
	// These 3 are needed to prevent concurrent modification of vectors
	private LinkedList<SSMLoggingSwitchImpl> pendingRemoveSwitches = new LinkedList<SSMLoggingSwitchImpl>();
	private LinkedList<SSMLoggingSwitchImpl> pendingAddSwitches = new LinkedList<SSMLoggingSwitchImpl>();
	private LinkedList<SSMLoggingSwitchImpl> activeSwitches = new LinkedList<SSMLoggingSwitchImpl>();
	
	// Want to ensure nothing is added to switches list while waiting for ECU response, could mess up data parsing!
	private LinkedList<Integer> oldSwitchesBytes = new LinkedList<Integer>();
	
	// Setup the logging timer that attempts to just start the current ecu query
	int delay = 1000;   // delay for 1 sec.
    int period = 1500;  // repeat every 1.5 sec.
    Timer timer = new Timer();
	
	public static SSMLoggingSwitchManager getInstance(){
		if(SSMLoggingSwitchManager.instance == null){
			SSMLoggingSwitchManager.instance = new SSMLoggingSwitchManager();
		}
		return SSMLoggingSwitchManager.instance;
	}
	private SSMLoggingSwitchManager(){
		timer.schedule(new SSMLoggingSwitchTimerTask(this), delay, period);
	}
	
	
	public void pullSwitchData(){
		
		// Removed pending switches
		Iterator<SSMLoggingSwitchImpl> iterator = this.pendingRemoveSwitches.iterator();
		while(iterator.hasNext()){
			SSMLoggingSwitchImpl next = iterator.next();
			this.activeSwitches.remove(next);
		}
		this.pendingRemoveSwitches.clear();
		
		// Add pending switches
		Iterator<SSMLoggingSwitchImpl> iterator2 = this.pendingAddSwitches.iterator();
		while(iterator2.hasNext()){
			SSMLoggingSwitchImpl next = iterator2.next();
			this.activeSwitches.add(next);
		}
		this.pendingAddSwitches.clear();
		
		// Only do work if there are switches to work upon
		if(this.activeSwitches.size() > 0){
			
			// Build up list of bytes to query on
			// Save off query bytes for result use
			this.oldSwitchesBytes.clear();
			Iterator<SSMLoggingSwitchImpl> iterator3 = this.activeSwitches.iterator();
			LinkedList<Byte> data = new LinkedList<Byte>();
			while(iterator3.hasNext()){
				SSMLoggingSwitchImpl next = iterator3.next();
				Integer ecuByte = next.getEcuByte();
				
				// Make sure the byte doesn't already exist in query
				Iterator<Byte> iterator4 = data.iterator();
				boolean match = false;
				while(iterator4.hasNext()){
					Byte next2 = iterator4.next();
					if(next2.intValue() == ecuByte.intValue()){
						match = true;
						break;
					}
				}
				
				// If unique, add to list
				if(!match){
					data.add(ecuByte.byteValue());
					this.oldSwitchesBytes.add(ecuByte);
				}
				
			}
			
			
			// Build up query
			SSMQuery theQuery = new SSMQuery(SSMQueryBuilder.ADDRESS_LIST_READ_MEMORY, data, SSMQueryBuilder.DATA_PADDED, this);
			
			System.out.println(theQuery.getRawQueryDataPrintout());
			
			// Place query on queue
			SSMQueryQueue.getInstance().registerSSMQuery(theQuery);
		}

		
	}
	

	/**
	 * Listen for switch state changes from GUI interaction.
	 * Switches can go from active to inactive.
	 * Add or remove from active list as needed
	 */
	public void loggingSwitchStateChange(SSMLoggingSwitchImpl theSwitch) {
		System.out.println("Switch state change: "+theSwitch.getName() + "  value: "+theSwitch.isEnabled());
		
		if(theSwitch.isEnabled()){
			this.pendingAddSwitches.add(theSwitch);
		}else{
			this.pendingRemoveSwitches.add(theSwitch);
			
			// If switch is only switch selected, then stop the logging FAST
			if(this.activeSwitches.size() == 1){
				LoggerManager.getInstance().stopLoggingAll();
			}
		}
	}
	
	
	/**
	 * Listen for query results
	 * 
	 */
	public void SSMQueryResult(SSMQuery ssmQuery) {
		if(!ssmQuery.isHealthy()){
			logger.error("Malformed query response.");
			return;
		}
		
		
		LinkedList<LoggingSwitch> switches = SSMDataSupplier.getInstance().getSwitches();
		Iterator<LoggingSwitch> iterator = switches.iterator();
		while(iterator.hasNext()){
			LoggingSwitch next = iterator.next();
			
			// Only want to deal with specific switch type
			if(next instanceof SSMLoggingSwitchImpl){
				SSMLoggingSwitchImpl theSwitch = (SSMLoggingSwitchImpl)next;
				
				// Is switch enabled by user in GUI?
				// If so, see if its enabled physically in car
				if(next.isEnabled()){
					System.out.println("********** GUI USER SWITCH ON: "+next.getName());
					byte byteValue = theSwitch.getEcuByte().byteValue();
					
					// Get index of result byte by looking at query index
					int indexOf = ssmQuery.getRawQueryBytes().indexOf(byteValue);
					System.out.println(" -- Index found : "+indexOf);
					
					// With this mask and target byte bit, is this switch "on"?
					boolean objectEnabled = ssmQuery.isObjectEnabled(0, theSwitch.getEcuBit());
					
					// If on, start logging, if not stop logging
					if(objectEnabled){
						LoggerManager.getInstance().startLoggingAll();
					}else{
						LoggerManager.getInstance().stopLoggingAll();
					}
					
				}
			}
		}
	}
	
}
