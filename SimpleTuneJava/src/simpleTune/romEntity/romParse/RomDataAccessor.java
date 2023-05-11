package simpleTune.romEntity.romParse;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.romEntity.xmlParse.RomXMLDataManager;
import simpleTune.romEntity.xmlParse.RomXMLMetaDataDoc;
import simpleTune.romEntity.xmlParse.romXMLData.StateXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableAxisXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableScalingXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableXML;

public class RomDataAccessor {
	private Log logger = LogFactory.getLog(getClass());
	
	private static RomDataAccessor romDataAccessor;
	private RomDataAccessor(){
		
	}
	
	public static RomDataAccessor getInstance(){
		if(RomDataAccessor.romDataAccessor == null){
			RomDataAccessor.romDataAccessor = new RomDataAccessor();
		}
		return RomDataAccessor.romDataAccessor;
	}
	
	public void saveDataToROM(String tableGroup, String tableIdentifier, Object[][] data, int tableType){
		logger.info("ROM Data Accessor saving data,  tableGroup:"+tableGroup+"   tableIdentifier:"+tableIdentifier+"   tableType:"+tableType);
		
		// Parse needed data from the identifier String
		String[] split = tableGroup.split(";");
		int uniqueIdentifier = Integer.parseInt(split[0]);
		String fileSystemPath = split[1];

		String[] split2 = tableIdentifier.split(";");
		String calID = split2[0];
		String baseName = split2[1];
		String tableName = split2[2];
		
		int baseAddress = 0;
		String endian = null;
		String storagetype = null;
		String expression = null;
		boolean beforeRam = false;
		
		// Get needed meta data to pull data from the ROM
		if(tableType == ApplicationStateManager.MAIN_TABLE_DATA){
			TableXML getROMTableData = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName);
			baseAddress = (int)getROMTableData.getStorageaddress();
			
			TableXML getBaseTableData = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName);
			endian = getBaseTableData.getEndian();
			storagetype = getBaseTableData.getStoragetype();
			expression = getBaseTableData.getScaling().getTo_byte();
			beforeRam = getBaseTableData.isBeforeRam();
		}else if(tableType == ApplicationStateManager.XAXIS_TABLE_DATA){
			TableAxisXML xAxisDataXML = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).getXAxis();
			baseAddress = (int)xAxisDataXML.getStorageaddress();
			
			TableAxisXML axis = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName).getXAxis();
			endian = axis.getEndian();
			storagetype = axis.getStoragetype();
			expression = axis.getScalingData().getTo_byte();
			beforeRam = axis.isBeforeRam();
		}else if(tableType == ApplicationStateManager.YAXIS_TABLE_DATA){
			TableAxisXML yAxisDataXML = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).getYAxis();
			baseAddress = (int)yAxisDataXML.getStorageaddress();
			
			
			TableAxisXML axis = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName).getYAxis();
			endian = axis.getEndian();
			storagetype = axis.getStoragetype();
			expression = axis.getScalingData().getTo_byte();
			beforeRam = axis.isBeforeRam();
		}else if(tableType == ApplicationStateManager.SWITCH_TABLE_DATA){
			logger.info("RomDataAccessor Switch table found.");
			TableXML getROMTableData = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName);
			baseAddress = (int)getROMTableData.getStorageaddress();
			
			TableXML getBaseTableData = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName);
			if(getBaseTableData != null){
				endian = getBaseTableData.getEndian();
				storagetype = getBaseTableData.getStoragetype();
				TableScalingXML scaling = getROMTableData.getScaling();
				if(scaling != null){
					expression = scaling.getTo_byte();
				}
				beforeRam = getBaseTableData.isBeforeRam();
			}else{
				endian = getROMTableData.getEndian();
				
				storagetype = getROMTableData.getStoragetype();
				
				TableScalingXML scaling = getROMTableData.getScaling();
				if(scaling != null){
					expression = scaling.getTo_byte();
				}
			}
			
			logger.info("******* fileSystemPath :"+fileSystemPath);
			logger.info("******* calID :"+calID);
			logger.info("******* baseName :"+baseName);
			logger.info("******* tableName :"+tableName);
			logger.info("******* baseAddress :"+baseAddress);
			logger.info("******* beforeRam :"+beforeRam);
			
			logger.info("Switch data to store:"+data[0][0]);
			Vector<StateXML> states = null;
			if(getBaseTableData == null){
				states = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).getStates();
			}else{
				states = getBaseTableData.getStates();
			}
			
			Iterator<StateXML> iterator = states.iterator();
			while(iterator.hasNext()){
				StateXML next = iterator.next();
				String stateName = next.getName();
				String stateData = next.getData();
				
				if((Boolean)data[0][0] && stateName.equalsIgnoreCase("on")){
					logger.info("Attempting to save switch true.");
					logger.info("Switch data to apply :"+stateData);

					// Parse out the bytes from the "data"
					String[] splitBytes = stateData.split("\\s+");
					
					// Define structure to save data
					Double[] returnData = new Double[splitBytes.length];
					
					for(int i = 0; i < splitBytes.length; i++){
						returnData[i] = Integer.parseInt(splitBytes[i], 16) + 0.0;
					}
					
					RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).setDataUint8(returnData, baseAddress, "x", beforeRam);
				}
				
				if(!(Boolean)data[0][0] && stateName.equalsIgnoreCase("off")){
					logger.info("Attempting to save switch false.");
					logger.info("Switch data to apply :"+stateData);

					// Parse out the bytes from the "data"
					String[] splitBytes = stateData.split("\\s+");
					
					// Define structure to save data
					Double[] returnData = new Double[splitBytes.length];
					
					for(int i = 0; i < splitBytes.length; i++){
						returnData[i] = Integer.parseInt(splitBytes[i], 16) + 0.0;
					}
					
					RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).setDataUint8(returnData, baseAddress, "x", beforeRam);
				}
			}
			
			return;
		}else{
			logger.info("RomDataAccessor: SEVERE ERROR: Can't identify table type with INT id: "+tableType);
			return;
		}
		
		logger.info("******* fileSystemPath :"+fileSystemPath);
		logger.info("******* calID :"+calID);
		logger.info("******* baseName :"+baseName);
		logger.info("******* tableName :"+tableName);
		logger.info("******* baseAddress :"+baseAddress);
		logger.info("******* endian :"+endian);
		logger.info("******* storagetype :"+storagetype);
		logger.info("******* expression :"+expression);
		
		// Define endian boolean
		boolean isLittleEndian = true;
		if(endian.equalsIgnoreCase("big")){
			isLittleEndian = false;
		}
		
		// Since data is received as an array of Objects.......
		Double[] convertedData = new Double[data.length * data[0].length];
		int counter = 0;
		if(data[0][0] instanceof Double){
			for(int i = 0; i < data.length; i++){
				for(int j = 0; j < data[0].length; j++){
					//System.out.print((Double)data[i][j] + " , ");
					convertedData[counter] = (Double)data[i][j];
					counter++;
				}
			}
		}else{
			logger.info("RomDataAcessor can't save non Double data.");
			return;
		}
	
		// Query the ROM image manager for the ROM and Set data
		if(storagetype.equalsIgnoreCase("uint8")){
			RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).setDataUint8(convertedData, baseAddress, expression, beforeRam);
		}
		else if(storagetype.equalsIgnoreCase("uint16")){
			RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).setDataUint16(convertedData, baseAddress, isLittleEndian, expression, beforeRam);
		}
		else if(storagetype.equalsIgnoreCase("unit32")){
			RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).setDataUint32(convertedData, baseAddress, isLittleEndian, expression, beforeRam);
		}
		else if(storagetype.equalsIgnoreCase("float")){
			RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).setDataFloat(convertedData, baseAddress, isLittleEndian, expression, beforeRam);
		}
		else{
			logger.info("RomDataAccessor: Requested rom table data storage type (like, uint32) does not exist: "+storagetype);
		}
	}
	
	public boolean getSwitchValue(String tableGroup, String tableIdentifier){
		boolean returnValue = false;
		
		// Parse needed data from the identifier String
		String[] split = tableGroup.split(";");
		String[] split2 = tableIdentifier.split(";");
		int uniqueIdentifier = Integer.parseInt(split[0]);
		String fileSystemPath = split[1];
		String calID = split2[0];
		String baseName = split2[1];
		String tableName = split2[2];
		boolean beforeRam = false;
		
		int baseAddress = (int)RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).getStorageaddress();
		//logger.info("Base address: "+baseAddress);
		//logger.info("Base name: "+baseName);
		//logger.info("Table name: "+tableName);
		
		Vector<StateXML> states = null;
		TableXML getBaseTableData = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName);
		if(getBaseTableData == null){
			states = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).getStates();
		}else{
			states = getBaseTableData.getStates();
			beforeRam = getBaseTableData.isBeforeRam();
		}

		if(beforeRam != true){
			beforeRam = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).isBeforeRam();
		}
		
		
		//logger.info("State count:"+states.size());
		
		int trueCount = 0;
		int falseCount = 0;
		
		Iterator<StateXML> iterator = states.iterator();
		while(iterator.hasNext()){
			StateXML next = iterator.next();
			String name = next.getName();
			String data = next.getData();
			
			// Parse out the bytes from the "data"
			String[] splitBytes = data.split("\\s+");

			// Output test
			//logger.info("Name:"+name+"   Data:"+data);
			
			// Pull the bytes from the ROM
			Double[][] romData  = RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).getDataUint8(baseAddress, "x", splitBytes.length, 1, beforeRam);

			boolean isMatch = true;
			
			// Compare bytes, return true if they are the same sequence
			for(int i = 0; i < splitBytes.length; i++){
				int value = Integer.parseInt(splitBytes[i], 16);
				String one = Integer.toHexString(value);
				String two = Integer.toHexString((romData[0][i].intValue()));
				
				//logger.info("Compare : "+one + " , "+ two);
				
				if(!one.equalsIgnoreCase(two)){
					isMatch = false;
				}
			}
			
			// If we have a byte match, return true or false if this is "on" or "off"
			if(isMatch){
				logger.info("Match found!");
				if(name.equalsIgnoreCase("on")){
					//logger.info("On found");
					returnValue = true;
					trueCount++;
				}else if(name.equalsIgnoreCase("off")){
					//logger.info("Off found");
					returnValue = false;
					falseCount++;
				}
			}
			
		}
		
		// If fasle + true count are the same, then it must be enabled, right?
		// This exists because switch data for 
		if(trueCount == falseCount && falseCount >0){
			//logger.info("ROM Data Accessor: checksum must be enabled.");
			returnValue = true;
		}else if(trueCount == falseCount && falseCount <= 0){
			//logger.info("ROM Data Accessor: checksum must be disabled.");
			returnValue = false;
		}
		
		//logger.info("Switch value returned :"+returnValue);
		return returnValue;
	}
	
	public Double[][] getTableData(String tableGroup, String tableIdentifier){
		logger.info("ROM Data Accessor getting data,  tableGroup:"+tableGroup+"   tableIdentifier:"+tableIdentifier);
		// Parse needed data from the identifier String
		String[] split = tableGroup.split(";");
		String[] split2 = tableIdentifier.split(";");
		int uniqueIdentifier = Integer.parseInt(split[0]);
		String fileSystemPath = split[1];
		String calID = split2[0];
		String baseName = split2[1];
		String tableName = split2[2];
		
		// Get needed meta data to pull data from the ROM
		int baseAddress = (int)RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).getStorageaddress();
		String endian = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName).getEndian();
		String storagetype = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName).getStoragetype();
		String expression = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName).getScaling().getExpression();
		boolean beforeRam = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName).isBeforeRam();
		if(beforeRam != true){
			beforeRam = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).isBeforeRam();
		}
		
		
		logger.info("+++++ fileSystemPath :"+fileSystemPath);
		logger.info("+++++ calID :"+calID);
		logger.info("+++++ baseName :"+baseName);
		logger.info("+++++ tableName :"+tableName);
		logger.info("+++++ baseAddress :"+baseAddress);
		logger.info("+++++ endian :"+endian);
		logger.info("+++++ storagetype :"+storagetype);
		logger.info("+++++ expression :"+expression);
		logger.info("+++++ beforeRam :"+beforeRam);
		
		// Pull the most relevant table dimension data
		int sizex = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).getSizex();
		if(sizex == -1){
			sizex = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName).getSizex();
		}
		
		int sizey = RomXMLDataManager.getInstance().GetROMTableData(calID, tableName).getSizey();
		if(sizey == -1){
			sizey = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName).getSizey();
		}
		
		/*
		// Flip data on side for 2d data sets for ease of reading in GUI
		if((sizex == 1 || sizex == -1) && sizey > 1){
			sizex = sizey;
			sizey = 1;
		}
		*/
		
		// Define endian boolean
		boolean isLittleEndian = true;
		if(endian.equalsIgnoreCase("big")){
			isLittleEndian = false;
		}
	
		// Query the ROM image manager for the ROM and get data
		//logger.info("RDA: Storage Type: "+storagetype);
		Double[][] romData = null;
		if(storagetype.equalsIgnoreCase("uint8")){
			romData  = RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).getDataUint8(baseAddress, expression, sizex, sizey, beforeRam);
		}else if(storagetype.equalsIgnoreCase("uint16")){
			romData = RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).getDataUint16(baseAddress, isLittleEndian, expression, sizex, sizey, beforeRam);
		}else if(storagetype.equalsIgnoreCase("unit32")){
			romData = RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).getDataUint32(baseAddress, isLittleEndian, expression, sizex, sizey, beforeRam);
		}else if(storagetype.equalsIgnoreCase("float")){
			romData = RomImageManager.getInstance().getROMImage(uniqueIdentifier, fileSystemPath).getDataFloat(baseAddress, isLittleEndian, expression, sizex, sizey, beforeRam);
		}else{
			logger.info("RTEI: Requested rom table data storage type does not exist: "+storagetype);
		}
		
		return romData;
	}
	
	public Double[][] getXAxisData(RomXMLMetaDataDoc romMetaData, String tableName){
		TableAxisXML axis2 = RomXMLDataManager.getInstance().GetROMTableData(romMetaData.getCalID(), tableName).getXAxis();
		
		if(axis2 == null){
			return null;
		}
		
		long baseAddress = axis2.getStorageaddress();
		
		// First attempt to get size data from the specific XML rom info, then the base data
		int sizex = RomXMLDataManager.getInstance().GetROMTableData(romMetaData.getCalID(), tableName).getSizex();
		if(sizex == -1){
			sizex = RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getSizex();
		}
		
		
		boolean isLittleEndian = true;
		if(RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getEndian().equalsIgnoreCase("big")){
			isLittleEndian = false;
		}
		TableXML getBaseTableData = RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName);
		TableAxisXML axis = getBaseTableData.getXAxis();
		String expression = axis.getScalingData().getExpression();
		boolean beforeRam = RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).isBeforeRam();
		if(beforeRam != true){
			beforeRam = RomXMLDataManager.getInstance().GetROMTableData(romMetaData.getCalID(), tableName).isBeforeRam();
		}
		
		Double[][] xAxisRawData = null;
		if(RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getXAxis().getStoragetype().equalsIgnoreCase("uint16")){
			xAxisRawData = RomImageManager.getInstance().getROMImage(romMetaData.getUniqueIdentifier(), romMetaData.getFileSystemPath()).getDataUint16((int)baseAddress, isLittleEndian, expression, sizex, 1, beforeRam);
		}else if(RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getXAxis().getStoragetype().equalsIgnoreCase("uint8")){
			xAxisRawData = RomImageManager.getInstance().getROMImage(romMetaData.getUniqueIdentifier(), romMetaData.getFileSystemPath()).getDataUint8((int)baseAddress, expression, sizex, 1, beforeRam);
		}else if(RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getXAxis().getStoragetype().equalsIgnoreCase("float")){
			xAxisRawData = RomImageManager.getInstance().getROMImage(romMetaData.getUniqueIdentifier(), romMetaData.getFileSystemPath()).getDataFloat((int)baseAddress, isLittleEndian, expression, sizex, 1, beforeRam);
		}
		
		/*
		Double[] returnValues = new Double[xAxisRawData[0].length];
		for(int i = 0; i < xAxisRawData[0].length; i++){
			returnValues[i] = xAxisRawData[0][i];
		}
		*/
		return xAxisRawData;
	}
	
	public Double[][] getYAxisData(RomXMLMetaDataDoc romMetaData, String tableName){
		//logger.info("Tablename : " + tableName);
		TableAxisXML axis2 = RomXMLDataManager.getInstance().GetROMTableData(romMetaData.getCalID(), tableName).getYAxis();
		
		if(axis2 == null){
			return null;
		}
		
		long baseAddress = axis2.getStorageaddress();

		// First attempt to get size data from the specific XML rom info, then the base data
		int sizey = RomXMLDataManager.getInstance().GetROMTableData(romMetaData.getCalID(), tableName).getSizey();
		if(sizey == -1){
			sizey = RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getSizey();
		}
		
		boolean isLittleEndian = true;
		if(RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getEndian().equalsIgnoreCase("big")){
			isLittleEndian = false;
		}
		TableXML getBaseTableData = RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName);
		TableAxisXML axis = getBaseTableData.getYAxis();
		String expression = axis.getScalingData().getExpression();
		boolean beforeRam = RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).isBeforeRam();
		if(beforeRam != true){
			beforeRam = RomXMLDataManager.getInstance().GetROMTableData(romMetaData.getCalID(), tableName).isBeforeRam();
		}
		
		
		Double[][] xAxisRawData = null;
		if(RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getYAxis().getStoragetype().equalsIgnoreCase("uint16")){
			xAxisRawData = RomImageManager.getInstance().getROMImage(romMetaData.getUniqueIdentifier(), romMetaData.getFileSystemPath()).getDataUint16((int)baseAddress, isLittleEndian, expression, 1, sizey, beforeRam);
		}else if(RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getYAxis().getStoragetype().equalsIgnoreCase("uint8")){
			xAxisRawData = RomImageManager.getInstance().getROMImage(romMetaData.getUniqueIdentifier(), romMetaData.getFileSystemPath()).getDataUint8((int)baseAddress, expression, 1, sizey, beforeRam);
		}else if(RomXMLDataManager.getInstance().GetBaseTableData(romMetaData.getBasMapName(), tableName).getYAxis().getStoragetype().equalsIgnoreCase("float")){
			xAxisRawData = RomImageManager.getInstance().getROMImage(romMetaData.getUniqueIdentifier(), romMetaData.getFileSystemPath()).getDataFloat((int)baseAddress, isLittleEndian, expression, 1, sizey, beforeRam);
		}
		
		/*
		Double[] returnValues = new Double[xAxisRawData[0].length];
		for(int i = 0; i < xAxisRawData[0].length; i++){
			returnValues[i] = xAxisRawData[0][i];
		}
		*/
		
		return xAxisRawData;
	}
}
