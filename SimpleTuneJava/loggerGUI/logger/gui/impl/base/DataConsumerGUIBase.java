package logger.gui.impl.base;

import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.dataManager.LoggerManager;
import logger.dataSupplier.interfaces.DataSupplierListener;
import logger.gui.control.DataConsumerGUIController;
import logger.gui.interfaces.DataConsumerGUI;
import logger.gui.interfaces.StatusListener;
import logger.interfaces.LoggingAttribute;

/**
 * An easy to use abstract class to build data consumer GUIs on
 * @author emorgan
 *
 */
public abstract class DataConsumerGUIBase extends JPanel implements DataSupplierListener, DataConsumerGUI, StatusListener{


	
	private Log logger = LogFactory.getLog(getClass());
	
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 3719724512607570060L;
	
	public DataConsumerGUIBase(){
		// Automagically add to GUI for user
		DataConsumerGUIController.getInstance().addDataConsumer(this);
		
		// Make sure we get data
		LoggerManager.getInstance().registerDataSupplierListener(this);
	}
	
	// **********************************************************************
	// Not sure the following will be of interest to most data consumers GUIs
	// **********************************************************************
	private boolean isLogging = false;
	private boolean isConnected = false;

	public boolean getIsConnected() {
		return this.isConnected;
	}

	public boolean getIsLogging() {
		return this.isLogging;
	}
	
	
	// ************************************************************************
	// These both rely on the abstract implemented method to make things easier
	// ************************************************************************
	public String getDataSupplierListenerName(){
		return this.getName();
	}
	
	public String getLoggerGUIName() {
		return this.getName();
	}
	

	// *****************************************************************
	// For general GUI data listeners, we don't care about the following
	// *****************************************************************
	public void addActionListener(ActionListener listener) {
		// Don't care
	}

	public void addCommPort(String commPortName) {
		// Don't care
	}

	public void removeCommPort(String commPortName) {
		// Don't care
	}

	public void StatusMessageReceived(int type, String message){
		// Don't care
	}
	
	// ****************
	// Abstract methods
	// ****************
	
	/**
	 * New data pushed to this listener
	 */
	public abstract void newDataAvailable(LinkedList<LoggingAttribute> attributesUpdated);
	
	/**
	 * Notifies whether or not the main GUI or a physical switch has initiated logging of data
	 */
	public abstract void loggingStateChange(boolean isLogging);
	
	/**
	 * Returns the name of the data consumer GUI being implemented
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * New data attributes added that the lower level logging code will be listening to.
	 */
	public abstract void attributesAdded(LinkedList<LoggingAttribute> loggingAttribute);

	/**
	 * Attributes no longer being listened to by lower level logging code.
	 */
	public abstract void attributesRemoved(LinkedList<LoggingAttribute> loggingAttribuets);

	

}
