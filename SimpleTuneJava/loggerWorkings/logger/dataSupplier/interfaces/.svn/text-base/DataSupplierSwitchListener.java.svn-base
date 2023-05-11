package logger.dataSupplier.interfaces;

import java.util.LinkedList;

import logger.interfaces.LoggingSwitch;

public interface DataSupplierSwitchListener {

	/**
	 * Called when new switch data is available from the data supplier.
	 * 
	 * @param switchesUpdated
	 */
	public void newSwitchStateDataAvailable(LinkedList<LoggingSwitch> switchesUpdated);
	
	/**
	 * Add set of available switches
	 * @param switches
	 */
	public void switchesAdded(LinkedList<LoggingSwitch> switches);
}
