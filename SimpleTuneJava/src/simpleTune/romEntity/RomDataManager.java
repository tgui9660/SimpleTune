package simpleTune.romEntity;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.general.tools.DataTools;
import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.interfaces.TuningEntity;
import simpleTune.gui.tree.ETreeNode;
import simpleTune.romEntity.romParse.RomDataAccessor;
import simpleTune.romEntity.xmlParse.RomXMLDataManager;
import simpleTune.romEntity.xmlParse.RomXMLMetaDataDoc;
import simpleTune.romEntity.xmlParse.romXMLData.TableAxisXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableScalingXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableStaticScalingXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableXML;

public class RomDataManager {
	private static RomDataManager romDataManager;
	private Log logger = LogFactory.getLog(getClass());
	private String tuningGroupInScope = null;
	
	private RomDataManager(){
		
	}
	
	public static RomDataManager getInstance(){
		if(RomDataManager.romDataManager == null){
			RomDataManager.romDataManager = new RomDataManager();
		}
		
		return RomDataManager.romDataManager;
	}
	

	public void BuildMapDataTreeNode(RomXMLMetaDataDoc mapData, TuningEntity parentTuningEntity) {
		// Get the file extension
		String fileSystemPath = mapData.getFileSystemPath();
		String[] split = fileSystemPath.split("\\.");
		String suffix = split[1];
		

		// Pull the short fileName
		String shortFileName = DataTools.parseShortFileName(fileSystemPath);
		
		// Initialize tree
		ETreeNode root = new ETreeNode("ROM : "+mapData.getCalID().toUpperCase()+" : ["+mapData.getUniqueIdentifier()+"] : "+mapData.getFileSystemPath(), new TableMetaData(suffix, TableMetaData.MAP_SET_ROOT,0.0,0.0,new Object[0],null,null,false,"","", "", "", mapData.getUniqueIdentifier()+";"+mapData.getFileSystemPath(), null,null,null,"",parentTuningEntity, "", "", "", "", ""));
		TreeMap<String,TreeMap<String,TableXML>> categoryTableMap = mapData.GetCategoryTableMap();
		Set<String> keySet = categoryTableMap.keySet();
		Iterator<String> iterator = keySet.iterator();

		// Iterate through sets of categories
		while(iterator.hasNext()){
			String nextCategoryKey = iterator.next();
			ETreeNode categoryNode = new ETreeNode(nextCategoryKey, new TableMetaData(suffix, TableMetaData.CATEGORY,0.0,0.0,new Object[0],null,null,false,"","", "", "", mapData.getUniqueIdentifier()+":"+mapData.getFileSystemPath(), null,null,null,"",parentTuningEntity, "", "", "", "", ""));
			
			TreeMap<String,TableXML> tableMap = categoryTableMap.get(nextCategoryKey);
			Set<String> tableKeySet = tableMap.keySet();
			Iterator<String> tableIterator = tableKeySet.iterator();
			//logger.info(tableKeySet.size() +" : "+nextCategoryKey);
			while(tableIterator.hasNext()){
				String tableName = tableIterator.next();
				TableXML tableXML = tableMap.get(tableName);
				int simpleTuneTYPE = tableXML.getSimpleTuneTYPE();
				
				TableXML baseTableData = RomXMLDataManager.getInstance().GetBaseTableData(mapData.getBasMapName(), tableXML.getName());
				
				// Pull table name
				String baseTableName = mapData.getBasMapName();
				if(baseTableData != null){
					baseTableName = baseTableData.getName();
				}
				
				// Pull the table type
				// Case of table data definition
				if(simpleTuneTYPE == RomXMLDataManager.TableData){
					// If null, there is no base table data and must look up in romtabledata
					if(baseTableData == null){
						baseTableData = RomXMLDataManager.getInstance().GetROMTableData(mapData.getCalID(), tableName);
					}
					simpleTuneTYPE = baseTableData.getSimpleTuneTYPE();
				}
				
				// Gather table data
				String dataFormat = "";
				TableScalingXML scaling = baseTableData.getScaling();
				String mainTableUnits = "";
				if(scaling != null){
					dataFormat = scaling.getFormat();
					mainTableUnits = scaling.getUnits();
				}
				String description = baseTableData.getDescription();
				
				
				// Gather axis data
				Object[][] xAxisData = RomDataAccessor.getInstance().getXAxisData(mapData, tableName);				
				Object[][] yAxisData = RomDataAccessor.getInstance().getYAxisData(mapData, tableName);

				
				// Gather all axis data
				String xAxisName = "";
				String xFormat = "";
				String xAxisUnits = "";
				TableAxisXML xAxisXML = baseTableData.getXAxis();
				if(xAxisXML != null){
					xAxisName = xAxisXML.getName();
					xFormat = baseTableData.getXAxis().getScalingData().getFormat();
					xAxisUnits = baseTableData.getXAxis().getScalingData().getUnits();
				}
				
				String yAxisName = "";
				String yFormat = "";
				String yAxisUnits = "";
				TableAxisXML yAxisXML = baseTableData.getYAxis();
				if(yAxisXML != null){
					yAxisName = yAxisXML.getName();
					yFormat = baseTableData.getYAxis().getScalingData().getFormat();
					yAxisUnits = baseTableData.getYAxis().getScalingData().getUnits();
				}
				if(baseTableData.getStaticScaling() != null){
					TableStaticScalingXML staticScaling = baseTableData.getStaticScaling();
					yAxisName = staticScaling.getName();
					// TODO Not quite right, placed on X axis to make readable
					//xAxisName = staticScaling.getName();
					
				}
				
				/*
				TableAxisXML xAxis = baseTableData.getXAxis();
				if(xAxis != null){
					xAxisName = xAxis.getName();
				}
				
				TableAxisXML yAxis = baseTableData.getYAxis();
				if(yAxis != null){
					yAxisName = yAxis.getName();
				}
				*/
				
				// Search for possible static scaling data as defined in ecu_def.xml
				TableStaticScalingXML staticScaling = baseTableData.getStaticScaling();
				if(staticScaling != null){
					Vector<String> data = staticScaling.getData();
					Iterator<String> dataIterator = data.iterator();
					yAxisData = new Object[data.size()][1];
					int counter = 0;
					while(dataIterator.hasNext()){
						String next = dataIterator.next();
						yAxisData[counter][0] = next;
						counter++;
					}
				}
				
				
				// Add the appropriate nodes based on table types
				if(simpleTuneTYPE == RomXMLDataManager.Table3D){
					categoryNode.add(new ETreeNode(tableXML.getName(), new TableMetaData(suffix, TableMetaData.DATA_3D, -15.0, 50.0, new Object[0] ,xAxisData, yAxisData, false, tableName , xAxisName, yAxisName, mapData.getCalID()+";"+mapData.getBasMapName()+";"+tableXML.getName() , mapData.getUniqueIdentifier()+";"+mapData.getFileSystemPath(),dataFormat,xFormat,yFormat, description, parentTuningEntity, xAxisUnits, yAxisUnits, mainTableUnits, shortFileName, baseTableName)));	
				}
				else if(simpleTuneTYPE == RomXMLDataManager.Table2D){
					categoryNode.add(new ETreeNode(tableXML.getName(), new TableMetaData(suffix, TableMetaData.DATA_2D, -10.0, 60.0, new Object[0] ,xAxisData,yAxisData, false, tableName , xAxisName, yAxisName, mapData.getCalID()+";"+mapData.getBasMapName()+";"+tableXML.getName() , mapData.getUniqueIdentifier()+";"+mapData.getFileSystemPath(),dataFormat,null,null,description, parentTuningEntity, xAxisUnits, yAxisUnits, mainTableUnits, shortFileName, baseTableName)));	
				}
				else if(simpleTuneTYPE == RomXMLDataManager.Table1D){
					categoryNode.add(new ETreeNode(tableXML.getName(), new TableMetaData(suffix, TableMetaData.DATA_1D, -10.0, 60.0, new Object[0] ,null,null, false, tableName , xAxisName, yAxisName, mapData.getCalID()+";"+mapData.getBasMapName()+";"+tableXML.getName() , mapData.getUniqueIdentifier()+";"+mapData.getFileSystemPath(),dataFormat,null,null,description, parentTuningEntity, xAxisUnits, yAxisUnits, mainTableUnits, shortFileName, baseTableName)));	
				}
				else if(simpleTuneTYPE == RomXMLDataManager.TableSwitch){
					categoryNode.add(new ETreeNode(tableXML.getName(), new TableMetaData(suffix, TableMetaData.SWITCH, -10.0, 60.0, new Object[0] ,null,null, false, tableName , xAxisName, yAxisName, mapData.getCalID()+";"+mapData.getBasMapName()+";"+tableXML.getName() , mapData.getUniqueIdentifier()+";"+mapData.getFileSystemPath(),"",null,null,description, parentTuningEntity, xAxisUnits, yAxisUnits, mainTableUnits, shortFileName, baseTableName)));	
				}
				else{
					logger.info("RomDataManager unsupported table type; "+simpleTuneTYPE);
					categoryNode.add(new ETreeNode(tableXML.getName(), new TableMetaData(suffix, TableMetaData.SWITCH, -10.0, 60.0, new Object[0] ,null,null, false, tableName , xAxisName, yAxisName, mapData.getCalID()+";"+mapData.getBasMapName()+";"+tableXML.getName() , mapData.getUniqueIdentifier()+";"+mapData.getFileSystemPath(),"",null,null,description, parentTuningEntity, xAxisUnits, yAxisUnits, mainTableUnits, shortFileName, baseTableName)));	
					/**	<table type="Switch" name="Checksum Fix" storageaddress="0x7FB80" category="Checksum Fix" sizey="12">
	    				<description>Click the 'enabled' check box to fix the checksum issue.</description>
	     				<state name="on" data="00 00 00 00 00 00 00 00 5A A5 A5 5A" />
          				<state name="off" data="00 00 00 00 00 00 00 00 5A A5 A5 5A" />	
						</table>**/
				}
				
				
			}
			
			root.add(categoryNode);
		}
		
		ApplicationStateManager.getSTInstance().addNewTuningGroup(root);
	}

	public String getTuningGroupInScope() {
		return tuningGroupInScope;
	}

	public void setTuningGroupInScope(String tuningGroupInScope) {
		this.tuningGroupInScope = tuningGroupInScope;
	}
	
}
