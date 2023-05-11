package logger.dataSupplier.interfaces;

import java.util.LinkedList;

import logger.interfaces.LoggingAttribute;

public interface DataSupplierListener{
	/**
	 * Returns name of this data supplier listener.
	 * Used in GUI, make it cute and unique
	 */
	public String getDataSupplierListenerName();
	
	/**
	 * Called when a batch of data is updated.
	 * 
	 * @param newData
	 * @param loggingAttribute
	 */
	public void newDataAvailable(LinkedList<LoggingAttribute> attributesUpdated);
	
	/**
	 * Called when programmatically assigning an attribute to a logger. Some data suppliers
	 * need comm access to determine what set of logging attributes they support.
	 * 
	 * @param loggingAttribute
	 */
	public void attributesAdded(LinkedList<LoggingAttribute> loggingAttribute);
	
	/**
	 * Called when a data supplier either shuts down, or deems some attributes unavailable
	 * 
	 * @param loggingAttribuets
	 */
	public void attributesRemoved(LinkedList<LoggingAttribute> loggingAttribuets);
	
	/**
	 * Data suppliers can kick off logging. This method lets listeners know whats going on.
	 * 
	 * @param isLogging
	 */
	public void loggingStateChange(boolean isLogging);
}
