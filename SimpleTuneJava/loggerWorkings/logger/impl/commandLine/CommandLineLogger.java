package logger.impl.commandLine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import st.comm.CommAccessManager;

import logger.dataManager.LoggerManager;
import logger.dataSupplier.interfaces.DataSupplier;
import logger.dataSupplier.interfaces.DataSupplierListener;
import logger.interfaces.LoggingAttribute;
import logger.interfaces.LoggingSwitch;

public class CommandLineLogger implements DataSupplierListener, Runnable{
	
	private Log logger = LogFactory.getLog(getClass());
	
	
	private LinkedList<LoggingAttribute> attributeList = new LinkedList<LoggingAttribute>();
	
	private int attributeCount = 0;
	
	private BufferedWriter out = null;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    
	private LoggerManager loggerManager = null;
	
	public CommandLineLogger(){
		System.out.println(this.getDataSupplierListenerName()+": [Instantiated]");
		Thread tempThread = new Thread(this);
		tempThread.start();
	}
	
	public void setLoggingManager(LoggerManager loggerManager){
		this.loggerManager = loggerManager;
	}
	
	private void printCommPortUsageReport() {
		System.out.println("Comm Port Usage");
		
		Vector<String> availableCommPortNames = CommAccessManager.getInstance().getAvailableCommPortNames();
		Iterator<String> iterator = availableCommPortNames.iterator();
		int counter = 0;
		while(iterator.hasNext()){
			String next = iterator.next();
			System.out.println("["+counter+"] : "+next +"  Is in use?: ["+CommAccessManager.getInstance().isCommPortAssigned(next)+"]");
			counter++;
		}
		
	}

	private void printDataSuppliers() {
		String[] dataSupplierNameList = this.loggerManager.getDataSupplierNameList();
		
		for(int i = 0; i < dataSupplierNameList.length; i++){
			System.out.println("["+i+"] : "+dataSupplierNameList[i]);
		}
	}

	private void printUsage(){
		System.out.println(this.getDataSupplierListenerName()+": [Usage is as follows]");
		System.out.println(" -- Print this help menu                  > h");
		System.out.println(" -- List comm ports and their usage state > p");
		System.out.println(" -- List available data suppliers         > d");
		System.out.println(" -- Assign comm port to data supplier     > a");
		System.out.println(" -- Start (GO) logging data supplier      > g");
		System.out.println(" -- Stop logging data supplier            > s");
		System.out.println(" -- Quit program                          > q");
		
	}

	public String getDataSupplierListenerName() {
		return "Command Line Logger";
	}

	public void run(){
		this.startDataSupplierListener();
	}
	
	private void startDataSupplierListener() {
		System.out.println(this.getDataSupplierListenerName()+": [Thread Started]");
		
		// Inform user on usage
		printUsage();
		
		// Start processing loop
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String commandLineValue = "";
		try {
			while(!(commandLineValue = br.readLine()).equalsIgnoreCase("test quit")){
			
				// Print out the help menu
				if(commandLineValue.equals("q")){
					System.out.println("Exiting.");
					System.exit(0);
				}
				
				// Print out the help menu
				if(commandLineValue.equals("h")){
					this.printUsage();
				}

				// Print out comm port usage report
				if(commandLineValue.equals("p")){
					this.printCommPortUsageReport();
				}
				
				// Print out a list of all the available data providers
				if(commandLineValue.equals("d")){
					this.printDataSuppliers();
				}
				
				// Start logging
				if(commandLineValue.equals("g")){
					System.out.println("All data sources with assigned comm ports have started logging.");
					
					// Define file to log to
					try {
				        String datetime = dateFormat.format(new java.util.Date());
						out = new BufferedWriter(new FileWriter("ST_"+datetime+".csv", true));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					this.loggerManager.startLoggingAll();
				}
				// Stop logging
				if(commandLineValue.equals("s")){
					System.out.println("All data sources with assigned comm ports have stopped logging.");
					
					this.loggerManager.stopLoggingAll();
					
					out.close();
				}
				// Assign a comm port to a data supplier
				if(commandLineValue.equals("a")){
					
					System.out.println("Please enter the number of data supplier of interest to assing comm port to.");
					this.printDataSuppliers();
					String dataSupplierIndex = br.readLine();
					int dataSupplierIndexInt = 0;
					
					// Can we parse a number from the user entered value?
					boolean toContinue = true;
					try{
						dataSupplierIndexInt = Integer.parseInt(dataSupplierIndex);
					}catch(Exception e){
						System.out.println("Can't parse index selection from: "+dataSupplierIndex);
						toContinue = false;
					}
					
					// Make sure the data supplier index returns something useful
					DataSupplier dataSupplierByIndex = null;
					if(toContinue){
						dataSupplierByIndex = this.loggerManager.getDataSupplierByIndex(dataSupplierIndexInt);
						if(dataSupplierByIndex == null){
							System.out.println("No data supplier with index: "+dataSupplierIndexInt);
							toContinue = false;
						}
					}
					
					// We now have a valid selected data supplier, move onto port assignment.
					if(toContinue){
						System.out.println("Please enter the number of available comm port to assign.");
						this.printCommPortUsageReport();
						String commIndex = br.readLine();
						int commPortIndex = 0;
						
						boolean toContinue2 = true;
						try{
							commPortIndex = Integer.parseInt(commIndex);
						}catch(Exception e){
							System.out.println("Can't parse integer from: "+commIndex);
							commPortIndex = -1;
						}
						
						
						if(dataSupplierByIndex == null){
							System.out.println("No data supplier found with index: "+dataSupplierIndexInt);
							toContinue2 = false;
						}
						
						boolean assignCommPort = true;
						if(toContinue2){
							assignCommPort = CommAccessManager.getInstance().assignCommPort(commPortIndex, dataSupplierByIndex);
							
							if(assignCommPort){
								System.out.println("Great Success!!!");
							}else{
								System.out.println("Error assigning comm port.");
							}
						}else{
							System.out.println("Error assigning comm port.");
						}
					
					}else{
						System.out.println("Error assigning comm port.");
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Thank you, come again!!!");
		System.exit(0);
	}

	public void newDataAvailable(Double newData, LoggingAttribute loggingAttribute) {
		this.attributeCount++;
		if(this.attributeCount == this.attributeList.size()){
			this.printDataLine();
			this.attributeCount = 0;
		}
		
	}

	public void attributeAutomaticallyAdded(LoggingAttribute loggingAttribute) {
		attributeList.add(loggingAttribute);
	}
	
	private void printDataLine(){
		Iterator<LoggingAttribute> iterator = this.attributeList.iterator();
		
		String dataLine = "";
		int counter = 0;
		
		while(iterator.hasNext()){
			counter++;
			
			LoggingAttribute next = iterator.next();
			
			if(counter == this.attributeList.size()){
				dataLine += next.getValue();
			}else{
				dataLine += next.getValue() + " , ";
			}
			
		}
		
		try {
			out.write(dataLine+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("**** :"+dataLine);
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void newDataAvailable(LinkedList<LoggingAttribute> attributesUpdated) {
		// TODO Auto-generated method stub
		
	}

	public void attributesAdded(LinkedList<LoggingAttribute> loggingAttribute) {
		// TODO Auto-generated method stub
		
	}

	public void newSwitchStateDataAvailable(
			LinkedList<LoggingSwitch> switchesUpdated) {
		// TODO Auto-generated method stub
		
	}

	public void attributesRemoved(LinkedList<LoggingAttribute> loggingAttribuets) {
		// TODO Auto-generated method stub
		
	}

	public void loggingStateChange(boolean isLogging) {
		// TODO Auto-generated method stub
		
	}

}
