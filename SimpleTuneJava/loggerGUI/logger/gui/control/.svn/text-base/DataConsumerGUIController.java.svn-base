package logger.gui.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import simpleTune.gui.SimpleTuneGUIExec;
import st.comm.CommAccessManager;

import logger.dataManager.LoggerManager;
import logger.gui.interfaces.DataConsumerGUI;
import logger.gui.interfaces.StatusListener;
import logger.gui.status.StatusController;
import logger.timer.TimerTaskManager;
import logger.timer.tasks.interfaces.CommTaskInterface;

/**
 * All GUI actions pass through here in attempt to separate GUI from control code. :ActionListener
 * 
 * Schedules period updates like available comm port listings. :CommTaskInterface
 * 
 * @author botman
 *
 */
public class DataConsumerGUIController implements ActionListener{

	private Logger logger = Logger.getLogger(getClass());
	
	private Vector<DataConsumerGUI> dataConsumers = new Vector<DataConsumerGUI>();
	private Vector<String> commPortNames = new Vector<String>();
	private LoggerManager loggerManager = null;
	
	
	private static DataConsumerGUIController instance = null;
	
	public static DataConsumerGUIController getInstance(){
		if(DataConsumerGUIController.instance == null){
			DataConsumerGUIController.instance = new DataConsumerGUIController();
			
			// Add to needed tasks
			//TimerTaskManager.getInstance().addCommTaskListener(DataConsumerGUIController.instance);
			
			// Get instance of the logger manager
			// This is the low level logging controller that starts/stops logging etc
			DataConsumerGUIController.instance.loggerManager = LoggerManager.getInstance();
		}
		return DataConsumerGUIController.instance;
	}
	
	private DataConsumerGUIController(){
	}
	
	
	public void addDataConsumer(DataConsumerGUI dataConsumer){
		logger.debug("Added Data Consumer GUI : "+dataConsumer.getLoggerGUIName());
		
		// Listen for gui events created by user
		dataConsumer.addActionListener(this);
		
		// Add GUI to master list of consumers
		this.dataConsumers.add(dataConsumer);

		// Register GUI to listener and display system messages to USER
		StatusController.getInstance().addStatusListener((StatusListener)dataConsumer);
	}


	/**
	 * Set the logger manager once received by the GUI
	 * This is dirty, but needed since I try to keep GUI control 
	 * methods separate from GUI code as much as possible. 
	 *
	 * @param loggerManager
	 */
	public void setLoggerManager(LoggerManager loggerManager){
		this.loggerManager = loggerManager;
	}
	
	/**
	 * Returns a logger GUI by name
	 * If none found by name, null is returned.
	 * @param name
	 * @return
	 */
	public DataConsumerGUI getConsumer(String name){
		Iterator<DataConsumerGUI> iterator = this.dataConsumers.iterator();
		while(iterator.hasNext()){
			DataConsumerGUI next = iterator.next();
			if(next.getLoggerGUIName().equalsIgnoreCase(name)){
				return next;
			}
		}
		return null;
	}
	
	public Vector<DataConsumerGUI> getAllConsumers(){
		return this.dataConsumers;
	}
	
	// ###########################
	// Externally initiated events
	// ###########################
	public void updateCommPorts(){
		System.out.println("Updating comm values.");
		
		Vector<String> availableCommPortNames = CommAccessManager.getInstance().getAvailableCommPortNames();
		
		Iterator<String> iterator = availableCommPortNames.iterator();
		while(iterator.hasNext()){
			String next = iterator.next();
			//System.out.println("Processing: "+next);
			if(!this.commPortNames.contains(next)){
				// Add to master list of commports
				this.commPortNames.add(next);
				
				// Update all GUIs with new Comm Port
				Iterator<DataConsumerGUI> iterator2 = this.dataConsumers.iterator();
				while(iterator2.hasNext()){
					DataConsumerGUI next2 = iterator2.next();
					next2.addCommPort(next);
				}
			}
		}
		
		Iterator<String> iterator2 = this.commPortNames.iterator();
		while(iterator2.hasNext()){
			String next = iterator2.next();
			if(!availableCommPortNames.contains(next)){
				// Update all GUIs with new Comm Port
				Iterator<DataConsumerGUI> iterator3 = this.dataConsumers.iterator();
				while(iterator3.hasNext()){
					DataConsumerGUI next2 = iterator3.next();
					next2.removeCommPort(next);
				}
			}
		}
	}
	
	public void setCommValues(Vector<String> commValues){
		System.out.println("Comm ports updated");
		
		
	}
	
	
	// ###################
	// ACTION EVENTS BELOW
	// ###################
	/**
	 * All GUI event action commands follow
	 * ACTION:NAME
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println("ACTION OCCURRED: "+e.getActionCommand());
		
		if(e.getActionCommand().toLowerCase().contains("reset")){
			this.handleReset();
		}
		
		else if(e.getActionCommand().toLowerCase().contains("connect")){
			this.handleConnect(e.getActionCommand().split(":")[1]);

		}
		
		else if(e.getActionCommand().toLowerCase().contains("log")){
			this.handleLog(e.getActionCommand().split(":")[1]);
		}
	}

	// ***************************
	// METHODS CONTROLLING ACTIONS
	// ***************************
	private void handleLog(String loggerName){
		boolean selected = this.getConsumer(loggerName).getIsLogging();
		
		System.out.println("LOG ATTEMPT: "+selected);
		
		if(selected){
			// LOG data start
			StatusController.getInstance().addStatusMessage(StatusListener.MESSAGE_TYPE_STATUS,"Starting data logging.");
			this.loggerManager.startLoggingAll();
		}else{
			// LOG data stop
			StatusController.getInstance().addStatusMessage(StatusListener.MESSAGE_TYPE_STATUS,"Stopping data logging.");
			this.loggerManager.stopLoggingAll();
		}
		
	}
	
	private void handleConnect(String loggerName){
		boolean selected = this.getConsumer(loggerName).getIsConnected();
		
		System.out.println("CONNECT ATTEMPT: "+selected);
		
		if(selected){
			// Connect all data suppliers to their respective ECUs
			StatusController.getInstance().addStatusMessage(StatusListener.MESSAGE_TYPE_STATUS, "Attempting Supplier Connections");
			this.loggerManager.initAllDataSuppliers();
		}else{
			// Disconnect from all ECUs
			StatusController.getInstance().addStatusMessage(StatusListener.MESSAGE_TYPE_STATUS,"Removing Supplier Connection");
		}
		
	}
	
	private void handleReset(){
		System.out.println("RESET ATTEMPT");
		LoggerGUIManager.getInstance().getDataJTable().resetData();
		
		// Reset Table Data
		StatusController.getInstance().addStatusMessage(StatusListener.MESSAGE_TYPE_STATUS,"Resetting data");
	}

}
