package logger.dataManager;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.dataSupplier.interfaces.DataSupplier;
import logger.dataSupplier.interfaces.DataSupplierListener;
import logger.dataSupplier.interfaces.DataSupplierSwitchListener;
import logger.interfaces.LoggingAttribute;
import logger.interfaces.LoggingSwitch;

public class LoggerManager {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private static LoggerManager instance = null;
	
	// Various registered entities
	private LinkedList<DataSupplier> dataSuppliers = new LinkedList<DataSupplier>();
	private LinkedList<DataSupplierListener> dataSupplierListeners = new LinkedList<DataSupplierListener>();
	
	// Not sure this will ever be needed, but maybe
	private LinkedList<DataSupplierSwitchListener> switchListeners = new LinkedList<DataSupplierSwitchListener>();

	/**
	 * Singleton code.
	 * @return
	 */
	public static LoggerManager getInstance(){
		if(LoggerManager.instance == null){
			LoggerManager.instance = new LoggerManager();
		}
		
		return LoggerManager.instance;
	}
	private LoggerManager(){
	}
	
	
	/**
	 * Calls all registered data suppliers and tells them to start supplying data.
	 * This can occur even if no attributes are enabled for logging.
	 */
	public void startLoggingAll(){		
		logger.info("Start the data flow by starting data suppliers.");
		
		Iterator<DataSupplier> iterator = this.dataSuppliers.iterator();
		while(iterator.hasNext()){
			DataSupplier next = iterator.next();
			next.startLogging();
		}

		// Inform listeners of logging state change
		this.notifyListenersOfLoggingState(true);
	}
	
	/**
	 * Calls all registered data suppliers and tells them to stop supplying data.
	 */
	public void stopLoggingAll(){		
		logger.info("Stop the data flow by stopping data suppliers.");
		Iterator<DataSupplier> iterator = this.dataSuppliers.iterator();
		while(iterator.hasNext()){
			DataSupplier next = iterator.next();
			next.stopLogging();
		}
		
		// Inform listeners of logging state change
		this.notifyListenersOfLoggingState(false);
	}
	
	/**
	 * Method tells listeners whether or not logging is occurring.
	 * 
	 * @param isEnabled
	 */
	public void notifyListenersOfLoggingState(boolean isEnabled){
		logger.info("Informing listeners that logging enabled is :"+isEnabled);
		
		Iterator<DataSupplierListener> iterator = this.dataSupplierListeners.iterator();
		while(iterator.hasNext()){
			DataSupplierListener next = iterator.next();
			next.loggingStateChange(isEnabled);
		}
		
	}
	
	/**
	 * Nice to have method that returns a data supplier by its index value
	 * 
	 * @param index
	 * @return
	 */
	public DataSupplier getDataSupplierByIndex(int index){
		if(index < 0 || index >= this.dataSuppliers.size()){
			return null;
		}
		return this.dataSuppliers.get(index);
	}
	
	/**
	 * Nice to have method for GUIs.
	 * Gets a list of the names of all registered data suppliers.
	 * 
	 * @return
	 */
	public String[] getDataSupplierNameList(){
		
		Iterator<DataSupplier> iterator = this.dataSuppliers.iterator();
		int dataSupplierCount = this.dataSuppliers.size();
		
		String[] nameList = new String[dataSupplierCount];
		
		int counter = 0;
		while(iterator.hasNext()){
			nameList[counter] = iterator.next().getDataSupplierName();
			counter++;
		}
		
		return nameList;
	}
	
	/**
	 * Called by the various data suppliers when they have new attribute data to "supply".
	 * 
	 * @param newData
	 */
	public void newAttributeDataAvailable(LinkedList<LoggingAttribute> newData){
		Iterator<DataSupplierListener> iterator = this.dataSupplierListeners.iterator();
		while(iterator.hasNext()) {
			DataSupplierListener next = iterator.next();
			next.newDataAvailable(newData);
		}
	}

	/**
	 * Called by various data suppliers when they have new switch data available.
	 * 
	 * @param newSwitchData
	 */
	public void newSwitchDataAvailable(LinkedList<LoggingSwitch> newSwitchData){
		Iterator<DataSupplierSwitchListener> iterator = this.switchListeners.iterator();
		while(iterator.hasNext()){
			DataSupplierSwitchListener next = iterator.next();
			next.newSwitchStateDataAvailable(newSwitchData);
		}
	}

	/**
	 * Remove attributes
	 * 
	 * @param delAttr
	 */
	public void removeAttributes(LinkedList<LoggingAttribute> delAttr){
		// Make sure all attributes are disabled
		Iterator<LoggingAttribute> dels = delAttr.iterator();
		while(dels.hasNext()){
			LoggingAttribute next = dels.next();
			next.setActive(false);
		}
		
		// Notify listeners of removal
		Iterator<DataSupplierListener> iterator = this.dataSupplierListeners.iterator();
		while(iterator.hasNext()){
			DataSupplierListener next = iterator.next();
			next.attributesRemoved(delAttr);
		}
	}
	
	/**
	 * 
	 * @param dataSupplierListener
	 */
	public void registerDataSupplierListener(DataSupplierListener dataSupplierListener){
		dataSupplierListeners.add(dataSupplierListener);
		
		// Add initial set of attributes from all the suppliers
		Iterator<DataSupplier> iterator = this.dataSuppliers.iterator();
		while(iterator.hasNext()){
			DataSupplier next = iterator.next();
			LinkedList<LoggingAttribute> loggingAttributes = next.getLoggingAttributes();
			dataSupplierListener.attributesAdded(loggingAttributes);
		}
		
	}
	
	
	/**
	 * Add a supplier to the list of suppliers
	 * @param dataSupplier
	 */
	public void registerDataSupplier(DataSupplier dataSupplier){
		logger.info("Registered Data Source: "+dataSupplier.getDataSupplierName());
		this.dataSuppliers.add(dataSupplier);
		
	}
	
	/**
	 * Method walks all attributes and enables them. Good for testing.
	 */
	public void enableAllAttributes(){
		Iterator<DataSupplier> iterator = this.dataSuppliers.iterator();
		while(iterator.hasNext()){
			DataSupplier next = iterator.next();
			
			LinkedList<LoggingAttribute> loggingAttributes = next.getLoggingAttributes();
			Iterator<LoggingAttribute> iterator2 = loggingAttributes.iterator();
			
			while(iterator2.hasNext()){
				LoggingAttribute next2 = iterator2.next();
				next2.setActive(true);
			}
		}
	}
	
	/**
	 * Should be called before logging data. Might not get all attributes otherwise.
	 */
	public void initAllDataSuppliers(){
		Iterator<DataSupplier> iterator = this.dataSuppliers.iterator();
		while(iterator.hasNext()){
			DataSupplier next = iterator.next();
			next.intializeConnections();
		}
	}
	
	/**
	 * Add a switch listener
	 * @param listener
	 */
	public void registerDataSupplierSwitchListener(DataSupplierSwitchListener listener){
		this.switchListeners.add(listener);
		
		// Add initial set of attributes from all the suppliers
		Iterator<DataSupplier> iterator = this.dataSuppliers.iterator();
		while(iterator.hasNext()){
			DataSupplier next = iterator.next();
			LinkedList<LoggingSwitch> switches = next.getSwitches();
			listener.switchesAdded(switches);
		}
	}
}
