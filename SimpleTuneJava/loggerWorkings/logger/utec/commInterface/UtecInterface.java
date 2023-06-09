/*
 * Created on May 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package logger.utec.commInterface;


import gnu.io.CommPortIdentifier;

import java.util.Enumeration;
import java.util.Vector;

import logger.utec.commEvent.UtecTimerTaskManager;
import logger.utec.gui.mapTabs.UtecDataManager;
import logger.utec.properties.UtecProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.ApplicationStateManager;
import utec.comm.SerialConnectionException;
import utec.comm.UtecSerialConnectionManager;
import utecEntity.UtecMapData;

/**
 * @author emorgan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UtecInterface{
	private static Log logger = LogFactory.getLog(UtecInterface.class);
	
	//Store string vector of known system comm ports
	private static Vector portChoices =  listPortChoices();
	//private static UtecSerialListener serialEventListener = new UtecSerialListener();
	

	/**
	 * Initial setup of the class
	 *
	 */
	public static void init(){
		UtecSerialConnectionManager.init();
	}

	/**
	 * Method sends string data to the utec
	 * @param mapData
	 */
	public static void sendDataToUtec(StringBuffer commandData){
		UtecTimerTaskManager.execute(commandData);
	}
	
	/**
	 * Transmit a map to the utec
	 * 
	 * @param mapNumber
	 * @param listener
	 */
	public static void sendMapData(int mapNumber, UtecMapData mapData) {
		
		// Sanity check
		if (mapNumber < 1 || mapNumber > 5) {
			System.err.println("UtecInterface, Map selection out of range:"+mapNumber);
			return;
		}
		
		StringBuffer mapDataStringBuffer = mapData.getUpdatedMap();
		
		String[] commandList = UtecProperties.getProperties("utec.startMapUpload");
		if(commandList == null){
			System.err.println("UtecInterface, Command string in properties file for utec.startMapUpload not found.");
			return;
		}
		
		resetUtec();
		logger.info("UtecInterface, sending map:" + mapNumber);
		
		// Iterate through command string
		int starCounter = 0;
		for(int i = 0; i < commandList.length ; i++){
			if(commandList[i].equalsIgnoreCase("*")){
				if(starCounter == 0){
					// Select map
					
					if (mapNumber == 1) {
						UtecTimerTaskManager.execute(33);
						logger.info("Requested Map 1");
					}
					if (mapNumber == 2) {
						UtecTimerTaskManager.execute(64);
						logger.info("Requested Map 2");
					}
					if (mapNumber == 3) {
						UtecTimerTaskManager.execute(35);
						logger.info("Requested Map 3");
					}
					if (mapNumber == 4) {
						UtecTimerTaskManager.execute(36);
						logger.info("Requested Map 4");
					}
					if (mapNumber == 5) {
						UtecTimerTaskManager.execute(37);
						logger.info("Requested Map 5");
					}
				}else if(starCounter == 1){
					logger.info("UtecInterface, Sending map data to the UTEC");
					UtecTimerTaskManager.execute(mapDataStringBuffer);
					
				}else{
					System.err.println("No operation supported for properties value '*'");
				}
				
				starCounter++;
			}else{
				// Send parsed command to the utec
				UtecTimerTaskManager.execute(Integer.parseInt(commandList[i]));
			}
		}
	}
	

	/**
	 * Get UTEC to send logger data data
	 * 
	 */
	public static void startLoggerDataFlow() {
		logger.info("Starting data flow from UTEC");
		
		String[] commandList = UtecProperties.getProperties("utec.startLogging");
		if(commandList == null){
			System.err.println("Command string in properties file for utec.startLogging not found.");
			return;
		}
		
		resetUtec();
		for(int i = 0; i < commandList.length ; i++){
			// Send parsed command to the utec
			UtecTimerTaskManager.execute(Integer.parseInt(commandList[i]));
		}
	}

	/**
	 * Reset UTEC to main screen
	 * 
	 */
	public static void resetUtec() {
		logger.info("Utec reset called.");

		String[] commandList = UtecProperties.getProperties("utec.resetUtec");
		if(commandList == null){
			System.err.println("Command string in properties file for utec.resetUtec not found.");
			return;
		}
		
		for(int i = 0; i < commandList.length ; i++){
			// Send parsed command to the utec
			UtecTimerTaskManager.execute(Integer.parseInt(commandList[i]));
		}
	}

	/**
	 * Get map data from map number passed in
	 * 
	 * @param mapNumber
	 */
	
	public static void pullMapData(int mapNumber) {
		// Sanity check
		if (mapNumber < 1 || mapNumber > 5) {
			System.err.println("UI Map selection out of range.");
			return;
		}
		
		String[] commandList = UtecProperties.getProperties("utec.startMapDownload");
		if(commandList == null){
			System.err.println("UI Command string in properties file for utec.startMapDownload not found.");
			return;
		}
		
		resetUtec();
		logger.info("UI, getting map:" + mapNumber);
		
		// Iterate through command string
		int starCounter = 0;
		for(int i = 0; i < commandList.length ; i++){
			if(commandList[i].equalsIgnoreCase("*")){
				if(starCounter == 0){
					// Select map
					UtecDataManager.setExpectingMap(true);
					if (mapNumber == 1) {
						UtecTimerTaskManager.execute(33);
						logger.info("Requested Map 1");
					}
					if (mapNumber == 2) {
						UtecTimerTaskManager.execute(64);
						logger.info("Requested Map 2");
					}
					if (mapNumber == 3) {
						UtecTimerTaskManager.execute(35);
						logger.info("Requested Map 3");
					}
					if (mapNumber == 4) {
						UtecTimerTaskManager.execute(36);
						logger.info("Requested Map 4");
					}
					if (mapNumber == 5) {
						UtecTimerTaskManager.execute(37);
						logger.info("Requested Map 5");
					}
				}else if(starCounter == 1){

					// Make data class receptive to map transfer
					
					
				}else{
					System.err.println(" UI No operation supported for properties value '*'");
				}
				
				starCounter++;
			}else{
				// Send parsed command to the utec
				UtecTimerTaskManager.execute(Integer.parseInt(commandList[i]));
			}
		}
	}

	
	/**
	 * Get a list of all the ports available
	 * @return
	 */
	public static Vector getPortsVector(){
		return portChoices;
	}
	
	/**
	 * Get name of currently used port
	 * @return
	 */
	public static String getPortChoiceUsed(){
		return UtecSerialConnectionManager.parameters.getPortName();
	}

	
	/**
	 * Pass a single command char to the UTEC
	 * @param charValue
	 */
	public static void sendCommandToUtec(int charValue){
		UtecTimerTaskManager.execute(charValue);
	}
	
	/**
	 * PRIVATE
	 * 
	 * Open a port as per names defined in get port names method
	 * 
	 * @param portName
	 */
	 public static void openConnection(){
		 if(UtecSerialConnectionManager.isOpen()){
			 logger.info("Port is already open.");
			 return;
		 }
		 
	 	
	 	//Attempt to make connection
	 	try{
	 		UtecSerialConnectionManager.openConnection();
	 	}catch(SerialConnectionException e){
	 		System.err.println("Error opening serial port connection");
	 		e.printStackTrace();
	 		return;
	 	}
	 	
	 }
	
	/**
	 * Close connection to the currently opened port
	 *
	 */
	public static void closeConnection(){
		UtecSerialConnectionManager.closeConnection();	
	}
	
	/**
	 * Open selected port.
	 * @param port
	 */
	public static void setPortChoice(String port){
		UtecSerialConnectionManager.closeConnection();
		UtecSerialConnectionManager.parameters.setPortName(port);
		// openConnection();
	}
	
	/**
	 * Method returns a vector of all available serial ports found
	 * 
	 * @return
	 */
	private static Vector listPortChoices() {
		Vector theChoices = new Vector();
		CommPortIdentifier portId;
		
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		if (!en.hasMoreElements()) {
			System.err.println("No Valid ports found, check Java installation");
		}
			
		//Iterate through the ports
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier) en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				logger.info("Port found on system: "+portId.getName());
				theChoices.addElement(portId.getName());
			}
		}
		return theChoices;
	}
}