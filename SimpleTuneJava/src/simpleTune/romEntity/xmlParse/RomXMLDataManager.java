package simpleTune.romEntity.xmlParse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.romEntity.xmlParse.romXMLData.TableXML;

/**
 * Class stores all table data concerning specific ROM models and their base type information.
 * @author emorgan
 *
 */
public class RomXMLDataManager {
	private Log logger = LogFactory.getLog(getClass());
	public static final int TableRomID = 0;
	public static final int Table3D = 1;
	public static final int Table2D = 2;
	public static final int Table1D = 3;
	public static final int TableSwitch = 4;
	public static final int TableData = 5;
	
	/**
	 * Nested structures of the hashmaps
	 * 
	 * (CalID - (TableName - Table) )
	 */
	 
	//private FastMap romROMMap3D = new FastMap();
	//private FastMap romBaseMap3D = new FastMap();
	
	private HashMap<String, HashMap<String, TableXML>> romROMMap3D = new HashMap<String, HashMap<String, TableXML>>();
	private HashMap<String, HashMap<String, TableXML>> romBaseMap3D = new HashMap<String, HashMap<String, TableXML>>();
	
	
	public static HashMap<String, HashMap<String, TableXML>> GetAllBaseMapData(){
		return RomXMLDataManager.getInstance().romBaseMap3D;
	}
	
	public static HashMap<String, HashMap<String, TableXML>> GetAllROMMapData(){
		return RomXMLDataManager.getInstance().romROMMap3D;
	}
	
	/**
	 * Searches for table data for the passed calID and tableName.
	 * 
	 * @param CalID
	 * @param tableName
	 * @return
	 */
	public TableXML GetROMTableData(String CalID, String tableName){
		String baseMapName = GetROMIDTable(CalID).getBase();
		
		TableXML tableXML = this.romROMMap3D.get(CalID.toLowerCase()).get(tableName.toLowerCase());
		
		//FastMap object = (FastMap)this.romROMMap3D.get(CalID.toLowerCase());
		//TableXML tableXML = (TableXML)object.get(tableName.toLowerCase());
		
		
		HashMap<String,TableXML> hashMap = this.romBaseMap3D.get(baseMapName);
		//FastMap hashMap = (FastMap)this.romBaseMap3D.get(baseMapName);
		
		
		if(tableXML == null && hashMap == null){
			return GetROMTableData(baseMapName, tableName);
		}else if(tableXML == null && hashMap != null){
			logger.info("Serious ROM Map error for calID:"+CalID+"  tableName:"+tableName);
			return null;
		}
		
		return tableXML;	
	}
	
	/**
	 * Finds the ROMID table data for a passed ROM Calid
	 * 
	 * @param CalID
	 * @return
	 */
	public TableXML GetROMIDTable(String CalID){
		
		HashMap<String,TableXML> hashMap = this.romROMMap3D.get(CalID.toLowerCase());
		//FastMap hashMap = (FastMap)this.romROMMap3D.get(CalID.toLowerCase());
		
		
		if(hashMap == null){
			logger.info("ROM Map for key not found :"+CalID.toLowerCase());
			return null;
		}
		//logger.info("Found :"+CalID.toLowerCase());
		
		Set<String> keySet = hashMap.keySet();
		//j2me.util.Set keySet = hashMap.keySet();
		
		
		Iterator<String> iterator = keySet.iterator();
		//j2me.util.Iterator iterator = keySet.iterator();
		while(iterator.hasNext()){
			String next = (String)iterator.next();
			TableXML tableXML = (TableXML)hashMap.get(next);

			// ROM ID Data
			if(tableXML.getSimpleTuneTYPE() == 0){
				return tableXML;
			}
		}
		return null;
	}
	
	private static RomXMLDataManager romXMLDataManager = null;
	
	private RomXMLDataManager(){
	}
	
	public TableXML GetBaseTableData(String baseName, String tableName){
		return romBaseMap3D.get(baseName.toLowerCase()).get(tableName.toLowerCase());
		
		//FastMap temp = (FastMap)romBaseMap3D.get(baseName.toLowerCase());
		//TableXML tempXML = (TableXML)temp.get(tableName.toLowerCase());
		
		//return tempXML;
	}
	
	public static RomXMLDataManager getInstance(){
		if(romXMLDataManager == null){
			romXMLDataManager = new RomXMLDataManager();
		}
		return romXMLDataManager;
	}
	
	public void AddTable(String name, String tableName, TableXML theTable, boolean isBaseTable){
		if(isBaseTable){
			this.AddBaseTable(name, tableName, theTable);
		}else{
			this.AddROMTable(name, tableName, theTable);
		}
	}
	
	private void AddROMTable(String calID, String tableName, TableXML theTable){
		if(romROMMap3D.get(calID.toLowerCase()) == null){
			this.romROMMap3D.put(calID.toLowerCase(), new HashMap<String, TableXML>());
			//this.romROMMap3D.put(calID.toLowerCase(), new FastMap());
			
			//logger.info("Adding CALID:"+calID.toLowerCase());
		}
		//a4tc101l
		
		//if(calID.equalsIgnoreCase("a4tc101l")){
		//	logger.info("----table :>"+tableName+"<:");
		//}
		//if(tableName.equalsIgnoreCase("romid")){
		//	logger.info("ROMID FOUND! :"+calID.toLowerCase());
		//}
		
		this.romROMMap3D.get(calID.toLowerCase()).put(tableName.toLowerCase(), theTable);
		//((FastMap)(this.romROMMap3D.get(calID.toLowerCase()))).put(tableName.toLowerCase(), theTable);
	}
	
	private void AddBaseTable(String baseName, String tableName, TableXML theTable){
		if(romBaseMap3D.get(baseName.toLowerCase()) == null){
			this.romBaseMap3D.put(baseName.toLowerCase(), new HashMap<String, TableXML>());
			//this.romBaseMap3D.put(baseName.toLowerCase(), new FastMap());
			
			
			//logger.info("Adding Base :"+baseName.toLowerCase());
		}
		
		this.romBaseMap3D.get(baseName.toLowerCase()).put(tableName.toLowerCase(), theTable);
		//((FastMap)(this.romBaseMap3D.get(baseName.toLowerCase()))).put(tableName.toLowerCase(), theTable);
	}
	
	/**
	 * Removes previous entries, and opens defined XML def file.
	 * 
	 * @param fileLocationPath
	 */
	public void OpenXMlDef(String fileLocationPath){
		romROMMap3D.clear();
		romBaseMap3D.clear();
		
		ECUDefXMLParser.ParseXMLDefFile(fileLocationPath);
	}
	
	/**
	 * Finds the basemapname for a passed CALID.
	 * This is needed, as some roms refer to other rom data, not base maps.
	 * Must follow the chain of basemap references until a real basmap "example: 32bitbase" is found.
	 * 
	 * @param CalID
	 * @return
	 */
	private String GetBaseMapName(String CalID){
		String baseMapName = "";
		HashMap<String,TableXML> hashMap = this.romROMMap3D.get(CalID.toLowerCase());
		//FastMap hashMap = (FastMap)this.romROMMap3D.get(CalID.toLowerCase());
		
		if(hashMap == null){
			logger.info("ROM Map for key not found :"+CalID.toLowerCase());
			return null;
		}
		//logger.info("Found :"+CalID.toLowerCase());
		
		Set<String> keySet = hashMap.keySet();
		//j2me.util.Set keySet = hashMap.keySet();
		 
		 
		 
		 
		Iterator<String> iterator = keySet.iterator();
		//j2me.util.Iterator iterator = keySet.iterator();
		while(iterator.hasNext()){
			String next = (String)iterator.next();
			TableXML tableXML = (TableXML)hashMap.get(next);

			// ROM ID Data
			if(tableXML.getSimpleTuneTYPE() == 0){
				//logger.info("A ************************** ROM ID BASE TYPE :>"+tableXML.getBase()+"<:");
				baseMapName = tableXML.getBase();
				if(romBaseMap3D.get(baseMapName.toLowerCase()) == null){
					return GetBaseMapName(baseMapName);
				}else{
					return baseMapName;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Returns a report with all meta data concerning given ROM calid as found in ecu_defs.
	 * Used by the GUI to build up the tree nodes and to define unique identifiers used by
	 * the RomManager to key ROM images.
	 * 
	 * @param CalID
	 * @return
	 */
	public RomXMLMetaDataDoc GetROMTables(String CalID){
		logger.info("Searching for :"+CalID);
		//logger.info("BaseMap is:"+GetBaseMapName(CalID));
		
		if(CalID == null || CalID.length() == 0){
			return null;
		}
		
		RomXMLMetaDataDoc romXMLDoc = new RomXMLMetaDataDoc();
		romXMLDoc.setCalID(CalID.toLowerCase());
		romXMLDoc.setBasMapName(GetBaseMapName(CalID));
		
		String baseMapName = "";
		HashMap<String,TableXML> hashMap = this.romROMMap3D.get(CalID.toLowerCase());
		//FastMap hashMap = (FastMap)this.romROMMap3D.get(CalID.toLowerCase());
		
		if(hashMap == null){
			logger.info("ROM Map for key not found :"+CalID.toLowerCase());
		}
		logger.info("Found :"+CalID.toLowerCase());
		
		Set<String> keySet = hashMap.keySet();
		//j2me.util.Set keySet = hashMap.keySet();
		
		
		
		Iterator<String> iterator = keySet.iterator();
		//j2me.util.Iterator iterator = keySet.iterator();
		
		
		
		while(iterator.hasNext()){
			String next = (String)iterator.next();
			TableXML tableXML = (TableXML)hashMap.get(next);

			// ROM ID Data
			if(tableXML.getSimpleTuneTYPE() == 0){
				//logger.info("A ************************** ROM ID BASE TYPE :>"+tableXML.getBase()+"<:");
				baseMapName = tableXML.getBase();
				romXMLDoc.setTempBaseMapName(baseMapName.toLowerCase());
			}else{
				romXMLDoc.AddDataTable(tableXML.getName(), tableXML.getCategory(), tableXML);
			}
			
			//logger.info(next+" : "+tableXML.getSimpleTuneTYPE()+" : "+tableXML.getName());
		}
		
		// If basemapname "32bitbase etc" is not found, then we have a link to another ROM and must follow chain
		// until a known base is found.
		int counter = 0;
		while(counter < 4 && romBaseMap3D.get(romXMLDoc.getTempBaseMapName().toLowerCase()) == null){
			romXMLDoc = GetNestedTables(romXMLDoc);
			counter++;
		}
		
		return romXMLDoc;
	}
	
	/**
	 * Returns base table name
	 * 
	 * @param CalID
	 * @return
	 */
	private RomXMLMetaDataDoc GetNestedTables(RomXMLMetaDataDoc romXMLDoc){
		String baseMapName = "";
		
		HashMap<String,TableXML> hashMap = this.romROMMap3D.get(romXMLDoc.getTempBaseMapName().toLowerCase());
		//FastMap hashMap = (FastMap)this.romROMMap3D.get(romXMLDoc.getTempBaseMapName().toLowerCase());
		
		
		if(hashMap == null){
			logger.info("ROM Map for key not found :"+romXMLDoc.getTempBaseMapName().toLowerCase());
		}
		
		//logger.info("Found :"+romXMLDoc.getBaseMapName().toLowerCase());
		
		Set<String> keySet = hashMap.keySet();
		//j2me.util.Set keySet = hashMap.keySet();
		
		
		
		Iterator<String> iterator = keySet.iterator();
		//j2me.util.Iterator iterator = keySet.iterator();
		
		
		while(iterator.hasNext()){
			String next = (String)iterator.next();
			TableXML tableXML = (TableXML)hashMap.get(next);

			// ROM ID Data
			if(tableXML.getSimpleTuneTYPE() == 0){
				//logger.info("B ************************** ROM ID BASE TYPE :>"+tableXML.getBase()+"<:");
				baseMapName = tableXML.getBase();
				romXMLDoc.setTempBaseMapName(baseMapName);
			}else{
				romXMLDoc.AddDataTable(tableXML.getName(), tableXML.getCategory(), tableXML);
			}
			
			//logger.info(next+" : "+tableXML.getSimpleTuneTYPE()+" : "+tableXML.getName());
		}
		
		return romXMLDoc;
	}
}
