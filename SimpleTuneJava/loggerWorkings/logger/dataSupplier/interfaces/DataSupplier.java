package logger.dataSupplier.interfaces;

import java.util.LinkedList;

import logger.interfaces.LoggingAttribute;
import logger.interfaces.LoggingSwitch;

public interface DataSupplier {
	
	
	/**
	 * Return the set of EXTERNAL switches this data supplier can detect state of and act upon.
	 * 
	 * @return
	 */
	public LinkedList<LoggingSwitch> getSwitches();
	
	/**
	 * Called before logging data starts to properly form all connections.
	 */
	public void intializeConnections();
	
	/**
	 * Tells supplier to use specific comm port
	 * @param commPortName
	 */
	public void setCommPort(String commPortName);
	
	/**
	 * Tell data supplier to give up its comm port right now
	 */
	public void relinquishCurrentCommPortNow();
	
	/**
	 * Return the name of the data supplier
	 * @return
	 */
	public String getDataSupplierName();
	
	/**
	 * Get all the associated logging attributes tied to this logging entity.
	 * 
	 * May be an empty set if data supplier needs to at some point determine the set of 
	 * available attributes if keyed to a particular model car.
	 * @return
	 */
	public LinkedList<LoggingAttribute> getLoggingAttributes();
	
	/**
	 * Starts the logging of all attributes
	 * 
	 * Used mainly for testing
	 */
	public void startLogging();
	
	/**
	 * Stops the logging of all attributes
	 *
	 */
	public void stopLogging();
	
}
