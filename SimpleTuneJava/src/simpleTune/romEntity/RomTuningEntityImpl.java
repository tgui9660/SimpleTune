package simpleTune.romEntity;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import simpleTune.gui.interfaces.TuningEntity;
import simpleTune.gui.interfaces.TuningEntityListener;
import simpleTune.romEntity.romParse.RomDataAccessor;
import simpleTune.romEntity.romParse.RomImageManager;
import simpleTune.romEntity.xmlParse.RomXMLDataManager;
import simpleTune.romEntity.xmlParse.RomXMLMetaDataDoc;
import simpleTune.romEntity.xmlParse.romXMLData.TableXML;

public class RomTuningEntityImpl implements TuningEntity{
	private Log logger = LogFactory.getLog(getClass());
	
	private Vector<JMenu> jMenuItems = new Vector<JMenu>();
	
	// Menu Items
	public JMenuItem openRomItem = new JMenuItem("Open ROM");
	public JMenuItem closeRomItem = new JMenuItem("Close ROM");
	public JMenuItem saveRomItem = new JMenuItem("Save ROM");
	
	private TuningEntityListener theTEL;
	
	public RomTuningEntityImpl(){
		// Parse the passed XML file
		// TODO do not hardcode
		RomXMLDataManager.getInstance().OpenXMlDef("defs/ecu_defs.xml");
		
		initJMenu();
	}
	
	private void initJMenu(){

		logger.info("Init menu items.");
		
		// *********************************************
		// Add a menu item for basic application actions
		// *********************************************
		// Define the menu system
		JMenu fileMenu = new JMenu("File");
		openRomItem.addActionListener(this);
		closeRomItem.addActionListener(this);
		saveRomItem.addActionListener(this);
		fileMenu.add(openRomItem);
		fileMenu.add(closeRomItem);
		fileMenu.add(saveRomItem);
		jMenuItems.add(fileMenu);
	}
	
	
	public Vector<JMenu> getMenuItems() {
		logger.info("Getting menu items.");
		return jMenuItems;
	}

	public String getName() {
		return "ROM Tuning Entity";
	}

	/**
	 * Example tableIdentifier.
	 * 
	 * 1|/home/emorgan/16bit.hex|a4tc101l|boost|Target Boost
	 * 
	 * Calls the appropriate data accessing method.
	 */
	//@Override
	public Object[][] getTableData(String tableGroup, String tableIdentifier) {
		String[] split = tableIdentifier.split(";");
		String baseName = split[1];
		String tableName = split[2];
		
		logger.info("TableGroup >"+tableGroup+"<");
		logger.info("TableIdentifier >"+tableIdentifier+"<");
		logger.info("Basename >"+baseName+"<");
		logger.info("TableName >"+tableName+"<");
		
		
		TableXML getBaseTableData = RomXMLDataManager.getInstance().GetBaseTableData(baseName, tableName);
		
		String type = "";
		if(getBaseTableData == null){
			logger.info("RTEI: Strange null base table found. ECM, remember to fix checksum hack, dude!");
			type = "switch";
		}else{
			type = getBaseTableData.getType();
		}
		
		
		
		if(type.equalsIgnoreCase("switch")){
			Boolean[][] returnValue = new Boolean[1][1];
			returnValue[0][0] = RomDataAccessor.getInstance().getSwitchValue(tableGroup, tableIdentifier);
			
			return returnValue;
		}else{
			return RomDataAccessor.getInstance().getTableData(tableGroup, tableIdentifier);
		}
	}

	public JToolBar getToolBar() {
		return new RomToolBar(this.theTEL, this);
	}

	public void init(TuningEntityListener listener) {
		this.theTEL = listener;
	}

	public void notifySystemExit() {
		this.theTEL.readyForExit();
	}

	public void removeTuningGroup(String tuningGroup) {
		logger.info("RTE removing tuning group:"+tuningGroup);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Open ROM")) {
			logger.info("Open ROM action occuring");
		}
		
		if (cmd.equals("Close ROM")) {
			logger.info("Close ROM action occuring");
		}
		
		if (cmd.equals("Save ROM")) {
			logger.info("Save ROM action occuring");
		}
	}

	public void setCurrentTuningGroup(String tuningGroup) {
		RomDataManager.getInstance().setTuningGroupInScope(tuningGroup);
	}

	public String[] getFileSuffixes() {
		return new String[]{"hex", "rom"};
	}

	public void openFile(String path) {
		logger.info("RTE requested file be open :"+path);
		
		// Get the ROM Image manager to open up the ROM Image
		int uniqueNumber = RomImageManager.getInstance().openROMImage(path);
		
		// Pull the CALID of the ROM
		String romCalID = RomImageManager.getInstance().getROMImage(uniqueNumber, path).getRomCalID();
		
		// No ROM found
		if(romCalID == null || romCalID.length() == 0){
			logger.error("No ROM Found based on XML Def listed ROMs");
			return;
		}
		
		// Pull XML table data based on CALID gathered.
		RomXMLMetaDataDoc getROMTables = RomXMLDataManager.getInstance().GetROMTables(romCalID);
		
		// Used for opening a ROM multiple times. A unique number + path is the key associated with this instance
		getROMTables.setUniqueIdentifier(uniqueNumber);
		getROMTables.setFileSystemPath(path);
		
		// Build up tree nodes for left pane
		RomDataManager.getInstance().BuildMapDataTreeNode(getROMTables, this);

	}


	public void saveTableData(String tableGroup, String tableIdentifier, Object[][] data, int dataTableType) {
		RomDataAccessor.getInstance().saveDataToROM(tableGroup, tableIdentifier, data, dataTableType);
	}
	
	public void saveFile(String tableGroup, String path) {
		//logger.info("Rom tuning entity saving file :"+path);
		//logger.info("Rom tuning entity tableGroup :"+tableGroup);
		RomImageManager.getInstance().saveRomImage(tableGroup, path);
	}

}
