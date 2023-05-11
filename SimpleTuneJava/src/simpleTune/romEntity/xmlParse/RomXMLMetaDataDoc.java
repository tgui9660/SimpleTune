package simpleTune.romEntity.xmlParse;

import java.util.HashMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.romEntity.xmlParse.romXMLData.TableXML;

/**
 * This is an overall collection of data specific to a passed ROM CalID.
 * Used by the Rom Tuning Entity to build a tree hierarchy. 
 * Table data is organized by category.
 * 
 * @author emorgan
 *
 */
public class RomXMLMetaDataDoc {
	private Log logger = LogFactory.getLog(getClass());
	// TableXML with ROMID table data.
	private TableXML romID;
	
	// <category, <tablename, theTable> >
	private TreeMap<String,TreeMap<String, TableXML>> categoryMap = new TreeMap<String,TreeMap<String, TableXML>>();
	
	// Unique number associated with this ROM opening.
	// Ensures that same ROM can be opened multiple times
	private int uniqueIdentifier;
	
	// Associated file system path to ROM
	private String fileSystemPath;
	
	// Defined basemap name
	private String basMapName;
	
	// Just some helper fields 
	private String tempBaseMapName;
	private String calID;
	
	// Following two used during the processing of the XML
	public String getTempBaseMapName() {
		return tempBaseMapName;
	}

	public void setTempBaseMapName(String baseMapName) {
		this.tempBaseMapName = baseMapName;
	}

	/**
	 * First time calling this is the only one that takes.
	 * 
	 * @param romID
	 */
	public void AddRomIDTable(TableXML romID){
		if(romID == null){
			this.romID = romID;
		}
	}
	
	/**
	 * Puts a map into a category.
	 * 
	 * Please note that most category data is held within the base data.
	 * 
	 * @param tableName
	 * @param category
	 * @param theTable
	 */
	public void AddDataTable(String tableName, String category, TableXML theTable){
		if(category ==  null){
			TableXML getBaseTableData = RomXMLDataManager.getInstance().GetBaseTableData(this.basMapName, tableName);
			if(getBaseTableData != null){
				category = getBaseTableData.getCategory();
			}
			
			if(category == null){
				category = "misc";
			}
		}
		
		if(categoryMap.get(category.toLowerCase()) == null){
			categoryMap.put(category.toLowerCase(), new TreeMap<String, TableXML>());
		}
		//logger.info("Adding : "+category+" : "+tableName);
		
		categoryMap.get(category.toLowerCase()).put(tableName.toLowerCase(), theTable);
	}
	
	public TableXML GetRomID(){
		return this.romID;
	}
	
	public TreeMap<String,TreeMap<String, TableXML>> GetCategoryTableMap(){
		return this.categoryMap;
	}

	public String getCalID() {
		return calID;
	}

	public void setCalID(String calID) {
		this.calID = calID;
	}

	public String getBasMapName() {
		return basMapName;
	}

	public void setBasMapName(String basMapName) {
		this.basMapName = basMapName;
	}

	public int getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(int uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getFileSystemPath() {
		return fileSystemPath;
	}

	public void setFileSystemPath(String fileSystemPath) {
		this.fileSystemPath = fileSystemPath;
	}
}
