package logger.utec.gui.mapTabs;


import java.util.Iterator;
import java.util.Vector;

import logger.utec.commEvent.LoggerDataListener;
import logger.utec.commEvent.UtecAFRListener;
import logger.utec.properties.UtecProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.interfaces.TuningEntity;
import simpleTune.gui.tree.ETreeNode;
import utecEntity.UtecMapData;

public class UtecDataManager {
	private static Log logger = LogFactory.getLog(UtecDataManager.class);
	private static UtecMapData currentMapData = null;
	private static UtecTableModel fuelListener = null;
	private static UtecTableModel timingListener = null;
	private static UtecTableModel boostListener = null;
	private static UtecAFRListener utecAFRListener = null;
	private static Vector<LoggerDataListener> loggerListener = new Vector<LoggerDataListener>();
	private static boolean isExpectingMap = false;
	
	private static String rawMapData = "";
	
	// Loggers
	private static int afrIndex = Integer.parseInt(UtecProperties.getProperties("utec.afrIndex")[0]);
	private static int psiIndex = Integer.parseInt(UtecProperties.getProperties("utec.psiIndex")[0]);
	private static int knockIndex = Integer.parseInt(UtecProperties.getProperties("utec.knockIndex")[0]);
	private static int loadIndex = Integer.parseInt(UtecProperties.getProperties("utec.loadIndex")[0]);
	
	// Set data values to initial state
	private static double afrData = 999.0;
	private static double psiData = 999.0;
	private static double knockData = 0.0;
	private static double loadData = 0.0;
	
	private static int lineCounter = 0;
	
	private static Vector<UtecMapData> allMaps = new Vector<UtecMapData>();
	
	public static void addMap(UtecMapData newUtecMap){
		allMaps.add(newUtecMap);
		//logger.info("UtecDataManager:"+ApplicationStateManager.getCurrentTuningEntity().getName());
		ETreeNode rootNode = null;//buildMapDataTreeNode(newUtecMap, ApplicationStateManager.getCurrentTuningEntity());
		ApplicationStateManager.getSTInstance().addNewTuningGroup(rootNode);
	}
	
	public static void setCurrentMap(UtecMapData newUtecMap){
		currentMapData = newUtecMap;
		
		// Call listeners
		logger.info("Calling map listeners.");
		if(fuelListener != null){
			fuelListener.replaceData(currentMapData.getFuelMap());
		}
		
		if(boostListener != null){
			boostListener.replaceData(currentMapData.getBoostMap());
		}
		
		if(timingListener != null){
			timingListener.replaceData(currentMapData.getTimingMap());
		}
		
		
		
		logger.info("Done calling map listeners.");
	}
	
	private static ETreeNode buildMapDataTreeNode(UtecMapData mapData, TuningEntity parentTuningEntity) {
		/*
		// Define columnLabels
		Double[] columnLabels = new Double[11];
		for(int i = 0; i < columnLabels.length ; i++){
			columnLabels[i] = (i * 10.0);
		}

		Double[] rowLabels = new Double[40];
		for(int i = 0; i < rowLabels.length ; i++){
			rowLabels[i] = i+0.0;
		}
		
		
		// Initialize tree
		ETreeNode root = new ETreeNode("UTEC:"+mapData.getMapName()+", "+mapData.getMapComment(), new TableMetaData(TableMetaData.MAP_SET_ROOT,0.0,0.0,new Object[0],null,null,false,"","", "", "", mapData.getMapName(),null,null,null,"", parentTuningEntity));
		
		ETreeNode mapName = new ETreeNode("Map Name", new TableMetaData(TableMetaData.DATA_1D, Double.parseDouble(UtecProperties.getProperties("utec.fuelMapMin")[0]), Double.parseDouble(UtecProperties.getProperties("utec.fuelMapMax")[0]), null,columnLabels,rowLabels, false, "Map Name" , "", "", "MapName:"+mapData.getMapName(),null,null,null, mapData.getMapName(),"", parentTuningEntity));
		
		
		
		Object[] ignored = {new Double(-100.0)};
		ETreeNode fuel = new ETreeNode("Fuel", new TableMetaData(TableMetaData.DATA_3D, Double.parseDouble(UtecProperties.getProperties("utec.fuelMapMin")[0]), Double.parseDouble(UtecProperties.getProperties("utec.fuelMapMax")[0]), ignored,columnLabels,rowLabels, false, "Fuel" , "Load", "RPM", "Fuel:"+mapData.getMapName(), mapData.getMapName(),null,null,null,"", parentTuningEntity));
		
		Object[] ignored2 = {new Double(-100.0)};
		ETreeNode timing = new ETreeNode("Timing", new TableMetaData(TableMetaData.DATA_3D, Double.parseDouble(UtecProperties.getProperties("utec.timingMapMin")[0]), Double.parseDouble(UtecProperties.getProperties("utec.timingMapMax")[0]), ignored,columnLabels,rowLabels, false, "Timing" , "Load", "RPM",  "Timing:"+mapData.getMapName(), mapData.getMapName(),null,null,null,"", parentTuningEntity));
		
		Object[] ignored3 = {new Double(-100.0)};
		ETreeNode boost = new ETreeNode("Boost", new TableMetaData(TableMetaData.DATA_3D, Double.parseDouble(UtecProperties.getProperties("utec.boostMapMin")[0]), Double.parseDouble(UtecProperties.getProperties("utec.boostMapMax")[0]), ignored, columnLabels,rowLabels,false, "Boost", "Load", "RPM", "Boost:"+mapData.getMapName(), mapData.getMapName(),null,null,null,"", parentTuningEntity));
		
		root.add(mapName);
		root.add(fuel);
		root.add(timing);
		root.add(boost);
		
		return root;
		*/
		return null;
	}
	
	/**
	 * Get serial data from a serial event
	 * 
	 * @param serialData
	 */
	public static void setSerialData(String serialData){
		
		if(isExpectingMap){
			lineCounter++;
			logger.info("Line:"+lineCounter);
			//logger.info("Map:"+serialData+":");
			rawMapData += serialData+"\n";
			
			// Detect End of Map
			if(lineCounter == 128){
				rawMapData += "[END][MAPGROUP][0B05E][EOF]";
				lineCounter = 0;
				logger.info("Map EOF");
				UtecMapData newMap = new UtecMapData();
				newMap.replaceRawData(new StringBuffer(rawMapData));
				newMap.populateMapDataStructures();
				
				
				// setCurrentMap(newMap);
				rawMapData = "";
				addMap(newMap);
				setExpectingMap(false);
			}
		}else{
			pullLoggerData(serialData);
		}
		
		
	}

	private static void pullLoggerData(String serialData) {
		String[] data = serialData.split(",");
		
		// Count the "," to ensure this is a line of logging data
		//logger.info("DM LoggerEvent: Checking data length");
		if(data.length < 4){
			
			return;
		}
		
		double[] doubleData = new double[data.length];
		
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
				for(int k=0;k<theData.length();k++){
					//logger.info("--  DM LoggerEvent int values *****:"+(int)theData.charAt(k)+":");
				}
				
				// **********************************
				// Call out to general data listeners
				// **********************************
				
	        }
		}
		
		// ********************************************************
		// If we make it this far we know we have valid logger data
		// ********************************************************
		UtecDataManager.notifyLoggerDataListeners(doubleData);
	}
	
	/**
	 * Helper method to dole out data to logger data listeners
	 * @param doubleData
	 */
	private static void notifyLoggerDataListeners(double[] doubleData){
		
		setAfrData(doubleData[afrIndex]);
		setPsiData(doubleData[psiIndex]);
		setKnockData(doubleData[knockIndex]);
		setLoadData(doubleData[loadIndex]);
		
		Iterator iterator = UtecDataManager.getLoggerListeners().iterator();
		while(iterator.hasNext()){
			LoggerDataListener loggerListener = (LoggerDataListener)iterator.next();
			loggerListener.getCommEvent(doubleData);
		}
	}
	
	
	public static void setBoostListener(UtecTableModel boostListener) {
		UtecDataManager.boostListener = boostListener;
	}


	public static void setFuelListener(UtecTableModel fuelListener) {
		UtecDataManager.fuelListener = fuelListener;
	}


	public static void setTimingListener(UtecTableModel timingListener) {
		UtecDataManager.timingListener = timingListener;
	}

	public static void setFuelMapValue(int row, int col, double value){
		if(currentMapData != null){
			currentMapData.setFuelMapValue(row, col, value);
		}
	}
	
	public static void setBoostMapValue(int row, int col, double value){
		if(currentMapData != null){
			currentMapData.setBoostMapValue(row, col, value);
		}
	}
	
	public static void setTimingMapValue(int row, int col, double value){
		if(currentMapData != null){
			currentMapData.setTimingMapValue(row, col, value);
		}
	}


	public static UtecMapData getCurrentMapData() {
		return currentMapData;
	}


	public static UtecAFRListener getUtecAFRListener() {
		return utecAFRListener;
	}


	public static void setUtecAFRListener(UtecAFRListener utecAFRListener) {
		UtecDataManager.utecAFRListener = utecAFRListener;
	}


	public static double getAfrData() {
		return afrData;
	}


	public static void setAfrData(double afrData) {
		UtecDataManager.afrData = afrData;
	}


	public static double getKnockData() {
		// Zero out knock data when pulled
		double temp = UtecDataManager.knockData;
		UtecDataManager.knockData = 0;
		return temp;
	}


	public static void setKnockData(double knockData) {
		// Save highest counted knock events
		if(knockData > UtecDataManager.knockData){
			UtecDataManager.knockData = knockData;
		}
	}


	public static double getLoadData() {
		return loadData;
	}


	public static void setLoadData(double loadData) {
		UtecDataManager.loadData = loadData;
	}
	
	
	public static double getPsiData() {
		return psiData;
	}


	public static void setPsiData(double psiData) {
		UtecDataManager.psiData = psiData;
	}

	public static Vector<LoggerDataListener> getLoggerListeners() {
		return loggerListener;
	}

	public static void addLoggerListener(LoggerDataListener loggerListener) {
		UtecDataManager.loggerListener.add(loggerListener);
	}
	
	public static void addMapDataListener(LoggerDataListener loggerListener) {
		UtecDataManager.loggerListener.add(loggerListener);
	}

	public static boolean isExpectingMap() {
		return isExpectingMap;
	}

	public static void setExpectingMap(boolean isExpectingMap) {
		UtecDataManager.isExpectingMap = isExpectingMap;
	}

	public static Vector<UtecMapData> getAllMaps() {
		return allMaps;
	}
	
	public static void removeTuningGroup(String tuningGroup){
		Iterator mapIterator = allMaps.iterator();
		UtecMapData newMapData = null;
		while(mapIterator.hasNext()){
			newMapData = (UtecMapData)mapIterator.next();
			if(newMapData.getMapName().equals(tuningGroup)){
				break;
			}
		}
		
		if(newMapData != null){
			allMaps.remove(newMapData);
		}
	}
}
