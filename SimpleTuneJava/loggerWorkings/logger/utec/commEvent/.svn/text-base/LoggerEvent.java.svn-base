/*
 * Created on May 28, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package logger.utec.commEvent;



import logger.utec.gui.mapTabs.UtecDataManager;
import logger.utec.properties.UtecProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utecEntity.UtecMapData;


/**
 * @author emorgan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LoggerEvent {
	private Log logger = LogFactory.getLog(getClass());
	private String UtecBuffer = null;
	private String[] data = new String[6];
	private double[] doubleData = null; //new double[6];
	private boolean isMapData = false;
	private UtecMapData mapData = null;
	private boolean isValidData = true;
	
	public LoggerEvent(String buffer){
		
		this.UtecBuffer = buffer;
		this.setLoggerData();
	}
	
	private void setLoggerData(){
		
		logger.info("LoggerEvent:"+UtecBuffer+":");
		
		data = UtecBuffer.split(",");
		
		// Count the "," to ensure this is a line of logging data
		logger.info("LoggerEvent: Checking data length");
		if(data.length < 4){
			this.isValidData = false;
			logger.info("LoggerEvent: Too short returning, not valid data.");
			return;
		}
		logger.info("LoggerEvent: Data length ok:"+data.length);
		
		doubleData = new double[data.length];
		
		logger.info("LoggerEvent: Building double array.");
		for(int i = 0; i < data.length; i++){
			String theData = data[i];
			theData = theData.trim();
			if(theData.startsWith(">")){
				theData = "25.5";
			}
			if(theData.startsWith("--")){
				theData = "0.0";
			}
			if(theData.startsWith("ECU")){
				theData = "0.0";
			}
			
			try{
				doubleData[i] = Double.parseDouble(theData);
			}catch (NumberFormatException e) {
				logger.info("LoggerEvent: Not valid data. Number error in commevent :"+theData+":");
				for(int k=0;k<theData.length();k++){
					logger.info("-  LoggerEvent int values *****:"+(int)theData.charAt(k)+":");
				}
				this.isValidData = false;
				return;
	        }
		}
		
		logger.info("LoggerEvent: Setting data manager afr, psi and knock data.");
		UtecDataManager.setAfrData(doubleData[Integer.parseInt(UtecProperties.getProperties("utec.afrIndex")[0])]);
		UtecDataManager.setPsiData(doubleData[Integer.parseInt(UtecProperties.getProperties("utec.psiIndex")[0])]);
		UtecDataManager.setKnockData(doubleData[Integer.parseInt(UtecProperties.getProperties("utec.knockIndex")[0])]);
		logger.info("******* Logger event DONE ok***********");
	}




	public boolean isMapData() {
		return isMapData;
	}


	public void setMapData(boolean isMapData) {
		this.isMapData = isMapData;
	}


	public UtecMapData getMapData() {
		return mapData;
	}


	public void setMapData(UtecMapData mapData) {
		this.mapData = mapData;
	}


	public String[] getData() {
		return data;
	}


	public void setData(String[] data) {
		this.data = data;
	}


	public double[] getDoubleData() {
		return doubleData;
	}


	public void setDoubleData(double[] doubleData) {
		this.doubleData = doubleData;
	}


	public String getUtecBuffer() {
		return UtecBuffer;
	}


	private void setUtecBuffer(String utecBuffer) {
		UtecBuffer = utecBuffer;
	}


	public boolean isValidData() {
		return isValidData;
	}
}
