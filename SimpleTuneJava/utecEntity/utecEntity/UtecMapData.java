package utecEntity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtecMapData {
	private Log logger = LogFactory.getLog(getClass());
	
	private StringBuffer rawMapData = new StringBuffer();

	private Double[][] fuelMap = new Double[40][11];
	private Double[][] timingMap = new Double[40][11];
	private Double[][] boostMap = new Double[40][11];

	private String mapName = "";
	private String mapComment = "";
	
	private double[] tempStorage = new double[440];
	
	private String stringChecksumHexValue = "";
	
	public void addRawData(int byteData) {
		rawMapData.append(byteData);
	}
	
	public void replaceRawData(StringBuffer newDataBuffer){
		this.rawMapData = newDataBuffer;
		logger.info("---------- Unclean data. ------------");
		logger.info(this.rawMapData);
		logger.info("---------- Unclean data. ------------");
	}
	/**
	 * Generic constructor
	 *
	 */
	public UtecMapData() {
	}
	
	/**
	 * Constructor takes a file location to import
	 * @param fileLocation
	 */
	public UtecMapData(String fileLocation) {
		importFileData(fileLocation);
	}


	/**
	 * Import UTEC maps from a file
	 * @param fileLocation
	 */
	public void importFileData(String fileLocation) {
		rawMapData = new StringBuffer();
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(fileLocation);
			br = new BufferedReader(fr);

			String record = new String();
			try {
				while ((record = br.readLine()) != null) {
					this.rawMapData.append(record+"\r");
					// logger.info(record);
				}
			} catch (IOException e) {
				logger.error(e);
			}

		} catch (FileNotFoundException e) {
			logger.error(e);
		}

		populateMapDataStructures();
	}

	public void populateMapDataStructures() {
		
		// Functionality as the method names suggest
		logger.info("Starting to populated map data.");
		cleanUpMapData();
		logger.info("Cleaned up map ok.");
		logger.info("---------- Clean data. ------------");
		logger.info(this.rawMapData);
		logger.info("---------- Clean data. ------------");
		populateMapName();
		logger.info("Populated map name ok.");
		populateMapComment();
		logger.info("Populated map comment ok.");
		populateFuelMapData();
		logger.info("Populated Fuel data ok.");
		populateTimingMapData();
		logger.info("Populated Timing data ok.");
		populateBoostMapData();
		logger.info("Populated Boost data ok.");
		calculateChecksum();
		logger.info("Populated Checksum ok.");
	}
	 
	/**
	 * Return map based on current data.
	 * @return
	 */
	public StringBuffer getUpdatedMap(){
		calculateChecksum();
		
		StringBuffer totalFile = new StringBuffer("");
		
		totalFile.append("[START][MAPGROUP1][MAPGROUP]\r");
		totalFile.append("Map Name:-["+this.mapName+"]\r");
		totalFile.append("Map Comments:-["+this.mapComment+"]\r");
		
		totalFile.append("Fuel Map\r");
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				String temp = this.fuelMap[i][j]+"";
				if(temp.endsWith(".0")){
					temp = temp.substring(0, temp.length()-2);
				}
				String number = "["+temp+"]";
				int count = 9-number.length();
				String spaces = "";
				for(int k=0 ; k<count ; k++){
					spaces += " ";
				}
				
				number = spaces+number;
				totalFile.append(number);
			}
			totalFile.append("\r");
		}
		totalFile.append("\r");
		
		totalFile.append("Timing Map\r");
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				String temp = this.timingMap[i][j]+"";
				if(temp.endsWith(".0")){
					temp = temp.substring(0, temp.length()-2);
				}
				String number = "["+temp+"]";
				int count = 9-number.length();
				String spaces = "";
				for(int k=0 ; k<count ; k++){
					spaces += " ";
				}
				
				number = spaces+number;
				totalFile.append(number);
			}
			totalFile.append("\r");
		}
		totalFile.append("\r");
		

		totalFile.append("Boost Map\r");
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				String temp = this.boostMap[i][j]+"";
				if(temp.endsWith(".0")){
					temp = temp.substring(0, temp.length()-2);
				}
				String number = "["+temp+"]";
				int count = 9-number.length();
				String spaces = "";
				for(int k=0 ; k<count ; k++){
					spaces += " ";
				}
				
				number = spaces+number;
				totalFile.append(number);
			}
			totalFile.append("\r");
		}
		
		totalFile.append("[END][MAPGROUP]["+this.stringChecksumHexValue.toUpperCase()+"][EOF]");
		return totalFile;
	}
	
	public void calculateChecksum(){
		
		int mapChecksumValue = 0;
		char[] charArray = this.mapName.toCharArray();
		for(int i=0 ; i<charArray.length ; i++){
			int charValue = charArray[i];
			mapChecksumValue += charValue;
		}
		//logger.info("Map name Checksum:"+mapChecksumValue);
		
		int mapCommentChecksumValue = 0;
		charArray = this.mapComment.toCharArray();
		for(int i=0 ; i<charArray.length ; i++){
			int charValue = charArray[i];
			mapCommentChecksumValue += charValue;
		}
		//logger.info("Map comment Checksum:"+mapCommentChecksumValue);
		
		int fuelChecksum = 0; 
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				fuelChecksum += (this.fuelMap[i][j]*10 + 1000 + 1);
			}
		}
		//logger.info("Fuel Checksum:"+fuelChecksum);
		
		int timingChecksum = 0; 
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				timingChecksum += (this.timingMap[i][j]*10 + 1000 + 1);
			}
		}
		//logger.info("Timing Checksum:"+timingChecksum);
		
		int boostChecksum = 0; 
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				boostChecksum += (this.boostMap[i][j]*100 + 1);
			}
		}
		//logger.info("Boost Checksum:"+boostChecksum);
		
		int totalChecksum = mapChecksumValue+mapCommentChecksumValue+fuelChecksum+timingChecksum+boostChecksum;
		//logger.info("Total decimal checksum:"+totalChecksum);
		String hexString = Integer.toHexString(totalChecksum);
		//logger.info("Total hex checksum:"+hexString);
		
		hexString = hexString.substring(hexString.length()-4, hexString.length());
		hexString = 0 + hexString;
		totalChecksum = Integer.parseInt(hexString, 16);
		
		this.stringChecksumHexValue = hexString;
		logger.info("Total hex checksum:"+Integer.toHexString(totalChecksum));
	}
	
	public void populateBoostMapData(){
		int fuelStart = rawMapData.indexOf("Boost Map")+10;
		int fuelEnd = rawMapData.indexOf("[END]") - 1;
		String singleRow = rawMapData.substring(fuelStart, fuelEnd);
		
		String numericalValue = "";
		String[] split = singleRow.split(" *");
		int counter = 0;
		boolean makeDouble = false;
		for(int i=0; i<split.length; i++){
			String tempString = split[i];
			
			if(tempString.equals("[")){
				makeDouble = true;
				numericalValue = "";
			}
			else if(tempString.equals("]")){
				makeDouble = false;
				double parsedDouble = Double.parseDouble(numericalValue);
				this.tempStorage[counter] = parsedDouble;
				counter++;
			}
			else{
				if(makeDouble == true){
					numericalValue+=tempString;
				}
			}
		}
		
		// Move temp stored data into appropriate double array
		counter = 0;
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				this.boostMap[i][j] = this.tempStorage[counter];
				counter++;
			}
		}
		
		// Test print of data
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				//logger.info("Boost Output: "+ this.boostMap[j][i]);
			}
		}
		
	}
	
	public void populateTimingMapData(){
		int fuelStart = rawMapData.indexOf("Timing Map")+10;
		int fuelEnd = rawMapData.indexOf("Boost Map") - 2;
		String singleRow = rawMapData.substring(fuelStart, fuelEnd);
		
		String numericalValue = "";
		String[] split = singleRow.split(" *");
		int counter = 0;
		boolean makeDouble = false;
		for(int i=0; i<split.length; i++){
			String tempString = split[i];
			
			if(tempString.equals("[")){
				makeDouble = true;
				numericalValue = "";
			}
			else if(tempString.equals("]")){
				makeDouble = false;
				double parsedDouble = Double.parseDouble(numericalValue);
				this.tempStorage[counter] = parsedDouble;
				counter++;
			}
			else{
				if(makeDouble == true){
					numericalValue+=tempString;
				}
			}
		}
		
		// Move temp stored data into appropriate double array
		counter = 0;
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				this.timingMap[i][j] = this.tempStorage[counter];
				counter++;
			}
		}
		
		// Test print of data
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				//logger.info("Timing Output: "+ this.timingMap[j][i]);
			}
		}
		
	}
	
	public void populateFuelMapData(){
		int fuelStart = rawMapData.indexOf("Fuel Map")+10;
		int fuelEnd = rawMapData.indexOf("Timing Map") - 2;
		String singleRow = rawMapData.substring(fuelStart, fuelEnd);
		
		String numericalValue = "";
		String[] split = singleRow.split(" *");
		int counter = 0;
		boolean makeDouble = false;
		for(int i=0; i<split.length; i++){
			String tempString = split[i];
			
			if(tempString.equals("[")){
				makeDouble = true;
				numericalValue = "";
			}
			else if(tempString.equals("]")){
				makeDouble = false;
				double parsedDouble = Double.parseDouble(numericalValue);
				this.tempStorage[counter] = parsedDouble;
				counter++;
			}
			else{
				if(makeDouble == true){
					numericalValue+=tempString;
				}
			}
		}
		
		// Move temp stored data into appropriate double array
		counter = 0;
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				this.fuelMap[i][j] = this.tempStorage[counter];
				counter++;
			}
		}
		
		// Test print of data
		for(int i=0 ; i<40 ; i++){
			for(int j=0 ; j<11 ; j++){
				//logger.info("Fuel Output: "+ this.fuelMap[j][i]);
			}
		}
		
	}
	
	public void populateMapComment(){
		int start = rawMapData.indexOf("Map Comments:-[")+15;
		int stop = rawMapData.indexOf("Fuel Map") - 2;
		this.mapComment = rawMapData.substring(start, stop);
		logger.info("Map comment:"+mapComment+":");
	}
	
	public void populateMapName(){
		logger.info("Populating map name.");
		int start = rawMapData.indexOf("Map Name:-[")+11;
		int stop = rawMapData.indexOf("Map Comments") - 2;
		
		logger.info("Start:"+start);
		logger.info("Stop:"+stop);
		
		this.mapName = rawMapData.substring(start, stop);
		logger.info("Map name:"+mapName+":");
	}

	/**
	 * Remove possible cruft from map file
	 *
	 */
	public void cleanUpMapData() {
		int start = rawMapData.indexOf("[START]");
		int stop = rawMapData.indexOf("[EOF]") + 5;
		int size = rawMapData.length();

		if (stop != size) {
			rawMapData.delete(0, start);
			stop = rawMapData.indexOf("[EOF]") + 5;
			rawMapData.delete(stop, size);
		} else {
			//logger.info("Nothing to update.");
			//logger.info(rawMapData);
		}
	}
	
	/**
	 * Write method overwrites file of same name.
	 * @param entirePath
	 */
	public void writeMapToFile(String entirePath){
		
		// Overwrite file if it exists already
		File tempFile = new File(entirePath);
		boolean exists = (tempFile).exists();
	    if (exists) {
	    	tempFile.delete();
	    } 
		tempFile = null;
		
		// Write data to new file
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(entirePath));
	        out.write(this.getUpdatedMap().toString());
	        out.close();
	    } catch (IOException e) {
	    }
	}
	
	// *************************
	// Misc getters and setters.
	// *************************

	public Double[][] getBoostMap() {
		return boostMap;
	}

	public void setBoostMap(Double[][] boostMap) {
		logger.info("Replacing Boost Data");
		this.boostMap = boostMap;
	}

	public Double[][] getFuelMap() {
		return fuelMap;
	}

	public void setFuelMap(Double[][] fuelMap) {
		logger.info("Replacing Fuel Data");
		this.fuelMap = fuelMap;
	}

	public Double[][] getTimingMap() {
		return timingMap;
	}

	public void setTimingMap(Double[][] timingMap) {
		logger.info("Replacing Timing Data");
		this.timingMap = timingMap;
	}

	public String getMapComment() {
		return mapComment;
	}
	
	public void setMapComment(String mapComment){
		logger.info("Replacing Map Comment");
		this.mapComment = mapComment;
	}

	public String getMapName() {
		return mapName;
	}
	
	public void setMapName(String newMapName){
		logger.info("Replacing Map Name");
		this.mapName = newMapName;
	}
	
	public void setFuelMapValue(int row, int col, double value){
		this.fuelMap[col][row] = value;
	}
	
	public void setBoostMapValue(int row, int col, double value){
		this.boostMap[col][row] = value;
	}
	
	public void setTimingMapValue(int row, int col, double value){
		this.timingMap[col][row] = value;
	}
	
	public Object[][] getYAxisData(){
		String[][] values = new String[40][1];
		for(int i = 0; i < values.length; i++){
			values[i][0] = i*250+500 + "";
		}
		return values;
	}
	
	public Object[][] getXAxisData(){
		String[][] values = new String[1][11];
		for(int i = 0; i < values.length; i++){
			values[i][i] = i*10.0 + "";
		}
		return values;
	}
}