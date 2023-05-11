package logger.utec.commInterface;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;

import logger.utec.gui.mapTabs.UtecDataManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utec.comm.UtecSerialConnectionManager;

public class UtecSerialListener implements SerialPortEventListener {
	private static Log logger = LogFactory.getLog(UtecSerialListener.class);
	private static UtecSerialListener instance = null;
	private static String totalData = "";
	private static boolean isRegistered = false;
	
	public static UtecSerialListener getInstance(){
		if(instance == null){
			instance = new UtecSerialListener();
		}
		logger.info("Seria listener instance query.");
		return instance;
	}
	
	private UtecSerialListener() {
		totalData = "";
		logger.info("Serial listener was instantiated.");
	}

	public void serialEvent(SerialPortEvent e) {
		
		int newData = 1;
		switch (e.getEventType()) {
		case SerialPortEvent.DATA_AVAILABLE:

			// Append new output to buffer
			while (newData != -1) {
				try {
					newData = UtecSerialConnectionManager.getInputFromUtecStream().read();

					// Invalid data
					if (newData == -1) {
						//totalData = "";
						//System.err.println("Invalid data at UtecSerialListener, breaking while loop.");
						break;
					}
					
					// Dont append new lines \r or \n
					if(newData == 13 || newData == 10){
						//Dont append newlines or carriage returns
					}else{
						totalData += (char) newData;
					}

					// Output all data received
					//System.out.print((char)newData);
					
				} catch (IOException ex) {
					System.err.println(ex);
					return;
				}
				
				// New line of data available now
				//if((this.totalData.indexOf("\r") != 1) || (this.totalData.indexOf("\n") != 1)){
				if(newData == 13){
					//logger.info("USL Newline:"+newData);
					String tempData = totalData;
					totalData = "";
					//logger.info("USL totalData:"+tempData+":");
					UtecDataManager.setSerialData(tempData);
					
				}
				
			}// End while loop
		}
	}

	public static boolean isRegistered() {
		return isRegistered;
	}

	public static void setRegistered(boolean isRegistered) {
		UtecSerialListener.isRegistered = isRegistered;
	}
}
