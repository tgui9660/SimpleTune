package utecEntity;

import java.util.HashMap;


public class UtecMapManager {
	private static UtecMapManager instance =  null;
	private static HashMap<String, UtecMapData> mapList = new HashMap<String, UtecMapData>();
	private static int mapCounter = 0;
	
	private UtecMapManager(){
		
	}
	
	public static UtecMapManager getInstance(){
		if(instance == null){
			UtecMapManager.instance = new UtecMapManager();
		}
		
		return UtecMapManager.instance;
	}
	
	/**
	 * Opens a utec map file and returns the unique identifier attached to it.
	 * 
	 * @param fullPath
	 * @return
	 */
	public String openUtecMap(String fullPath){
		// Inc right away, want to ensure that we have uniqe key data
		UtecMapManager.mapCounter++;
		
		// Define the unique identifier
		String uniqueID = UtecMapManager.mapCounter + ";" + fullPath;
		
		// Add to master list
		UtecMapManager.mapList.put(uniqueID, new UtecMapData(fullPath));
		
		return uniqueID;
	}
	
	/**
	 * Returns a parsed UTEC map based on the passed unique key
	 * @param uniqueID
	 * @return
	 */
	public UtecMapData getUtecMap(String uniqueID){
		return UtecMapManager.mapList.get(uniqueID);
	}
	
}
