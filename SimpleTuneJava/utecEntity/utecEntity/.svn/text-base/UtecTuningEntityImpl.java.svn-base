package utecEntity;


import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import simpleTune.general.tools.DataTools;
import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.interfaces.TuningEntity;
import simpleTune.gui.interfaces.TuningEntityListener;
import simpleTune.gui.tree.ETreeNode;
import simpleTune.romEntity.xmlParse.RomXMLDataManager;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import logger.utec.commInterface.UtecInterface;
import logger.utec.gui.JutecToolBar;
import logger.utec.gui.mapTabs.UtecDataManager;

public class UtecTuningEntityImpl implements TuningEntity{
	private Log logger = LogFactory.getLog(getClass());
	
	// Misc items
	private String currentPort = null;
	private int fileChosen;
	private JFileChooser fileChooser =  new JFileChooser();

	
	private Vector<JMenu> jMenuItems = new Vector<JMenu>();
	
	// Menu Items
	public JMenuItem saveItem = new JMenuItem("Save Log");
	public JMenuItem saveMapItem = new JMenuItem("Save Map To File");
	public JMenuItem resetUtec = new JMenuItem("Force Utec Reset");
	public JMenuItem startLogging = new JMenuItem("Start Logging");
	public JMenuItem closePort = new JMenuItem("Close Port");
	public JMenuItem loadMapOne = new JMenuItem("Load Map #1");
	public JMenuItem loadMapTwo = new JMenuItem("Load Map #2");
	public JMenuItem loadMapThree = new JMenuItem("Load Map #3");
	public JMenuItem loadMapFour = new JMenuItem("Load Map #4");
	public JMenuItem loadMapFive = new JMenuItem("Load Map #5");

	public JMenuItem saveMapOne = new JMenuItem("Save To Map #1");
	public JMenuItem saveMapTwo = new JMenuItem("Save To Map #2");
	public JMenuItem saveMapThree = new JMenuItem("Save To Map #3");
	public JMenuItem saveMapFour = new JMenuItem("Save To Map #4");
	public JMenuItem saveMapFive = new JMenuItem("Save To Map #5");
	
	private TuningEntityListener theTEL = null;
	
	public UtecTuningEntityImpl(){
		
		// Build GUI elements
		initJMenu();
	}
	
	private void initJMenu(){

		// *********************************************
		// Add a menu item for basic application actions
		// *********************************************
		// Define the menu system
		JMenu fileMenu = new JMenu("File");
		saveItem.addActionListener(this);
		saveMapItem.addActionListener(this);
		fileMenu.add(saveItem);
		fileMenu.add(saveMapItem);
		jMenuItems.add(fileMenu);
		
		// ******************************************
		// Add menu item to coordinate Utec operation
		// ******************************************
		JMenu actionMenu = new JMenu("Actions");
		this.resetUtec.addActionListener(this);
		this.startLogging.addActionListener(this);
		this.closePort.addActionListener(this);
		actionMenu.add(this.resetUtec);
		actionMenu.add(this.startLogging);
		actionMenu.add(this.closePort);
		jMenuItems.add(actionMenu);
		
		// ****************************************
		// Add menu item to pull maps from the utec
		// ****************************************
		JMenu getMapsMenu = new JMenu("Load Map");
		loadMapOne.addActionListener(this);
		loadMapTwo.addActionListener(this);
		loadMapThree.addActionListener(this);
		loadMapFour.addActionListener(this);
		loadMapFive.addActionListener(this);
		getMapsMenu.add(loadMapOne);
		getMapsMenu.add(loadMapTwo);
		getMapsMenu.add(loadMapThree);
		getMapsMenu.add(loadMapFour);
		getMapsMenu.add(loadMapFive);
		jMenuItems.add(getMapsMenu);
		

		// ****************************************
		// Add menu item to save maps to the utec
		// ****************************************
		JMenu setMapsMenu = new JMenu("Save Map");
		saveMapOne.addActionListener(this);
		saveMapTwo.addActionListener(this);
		saveMapThree.addActionListener(this);
		saveMapFour.addActionListener(this);
		saveMapFive.addActionListener(this);
		setMapsMenu.add(saveMapOne);
		setMapsMenu.add(saveMapTwo);
		setMapsMenu.add(saveMapThree);
		setMapsMenu.add(saveMapFour);
		setMapsMenu.add(saveMapFive);
		jMenuItems.add(setMapsMenu);

		// ***************************************
		// Add a menu item for comm port selection
		// ***************************************
		JMenu portsMenu = new JMenu("Select Port");

		// Gather list of ports from interface
		Vector portsVector = UtecInterface.getPortsVector();

		Iterator portsIterator = portsVector.iterator();
		int counter = 0;
		while (portsIterator.hasNext()) {
			counter++;
			Object o = portsIterator.next();
			String theName = (String) o;
			JMenuItem item = new JMenuItem(theName);
			item.setName(theName);
			item.addActionListener(this);
			portsMenu.add(item);
		}
		jMenuItems.add(portsMenu);
	}
	
	public void removeTuningGroup(String tuningGroup){
		logger.info("Removing tuning group:"+tuningGroup);
		
		UtecDataManager.removeTuningGroup(tuningGroup);
	}
	
	public Object[][] getTableData(String tableGroup, String tableIdentifier) {
		logger.info("Get tableGroup:"+tableGroup);
		logger.info("Get tableIdentifier:"+tableIdentifier);
		
		Object[][] data = null;
		
		UtecMapData utecMap = UtecMapManager.getInstance().getUtecMap(tableGroup);
		if(tableIdentifier.equalsIgnoreCase("Fueling")){
			data = utecMap.getFuelMap();
		}
		else if(tableIdentifier.equalsIgnoreCase("Boost")){
			data = utecMap.getBoostMap();
		}
		else if(tableIdentifier.equalsIgnoreCase("Timing")){
			data = utecMap.getTimingMap();
		}
		else if(tableIdentifier.equalsIgnoreCase("Name")){
			data = new String[1][1];
			data[0][0] = utecMap.getMapName();
		}
		else if(tableIdentifier.equalsIgnoreCase("Comment")){
			data = new String[1][1];
			data[0][0] = utecMap.getMapComment();
		}
		else{
			logger.info("Returning an empty data set.");
			data = new Double[0][0];
		}
		
		return data;
	}

	public void init(TuningEntityListener theTEL) {
		this.theTEL = theTEL;

		// Initialise tree
		// ETreeNode root = new ETreeNode("UTEC: No map selected....", new TableMetaData(TableMetaData.CATEGORY,0.0,0.0,new Object[0], null, null, false,"","","", this));
		
		
		// Inform main GUI of initial tree
		//this.theTEL.addNewTuningGroup(root);
	}

	public String getName() {
		return "UTEC Tuning Entity";
	}

	public Vector<JMenu> getMenuItems() {
		
		return jMenuItems;
	}


	/**
	 * Implements actionPerformed
	 * 
	 * Action listeners for buttons/menus that throw them
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();

			// Open Port
			if (cmd.equals("New")) {
				logger.info("New action occuring");
			}

			// Close Port
			else if (cmd.equals("Open")) {
				logger.info("Open action occuring");
			}

			// Start Capture
			else if (cmd.equals("Save Log")) {
				String saveFileName = null;
				logger.info("Save action occuring");
				fileChosen = fileChooser.showSaveDialog(null);
				if (fileChosen == JFileChooser.APPROVE_OPTION) {
					saveFileName = fileChooser.getSelectedFile().getPath();
					// selectedFile = fileChooser.getSelectedFile();
					try {
						File file = new File(saveFileName);
						FileWriter out = new FileWriter(file);
						//out.write(bottomPanel.totalLog);
						out.close();
						//bottomPanel.totalLog = "";
					} catch (IOException e2) {
						System.out
								.println("Couldn't save file " + saveFileName);
						e2.printStackTrace();
					}
				}

			}

			else if (cmd.equals("Save Map To File")) {
				logger.info("Saving map to file.");

				if (UtecDataManager.getCurrentMapData() != null) {

					String saveFileName = null;
					logger.info("Save map now.");
					fileChosen = fileChooser.showSaveDialog(null);
					if (fileChosen == JFileChooser.APPROVE_OPTION) {
						saveFileName = fileChooser.getSelectedFile().getPath();
						UtecDataManager.getCurrentMapData().writeMapToFile(saveFileName);

					}
				} else {
					logger.info("Map is null.");
				}
			}

			else if (cmd.equals("Load Map #1")) {
				logger.info("Starting to get map 1");
				UtecInterface.pullMapData(1);
			}

			else if (cmd.equals("Load Map #2")) {
				logger.info("Starting to get map 2");
				UtecInterface.pullMapData(2);
			}

			else if (cmd.equals("Load Map #3")) {
				logger.info("Starting to get map 3");
				UtecInterface.pullMapData(3);
			}

			else if (cmd.equals("Load Map #4")) {
				logger.info("Starting to get map 4");
				UtecInterface.pullMapData(4);
			}

			else if (cmd.equals("Load Map #5")) {
				logger.info("Starting to get map 5");
				UtecInterface.pullMapData(5);
			}
			
			else if (cmd.equals("Save To Map #1")) {
				logger.info("Starting to save map #1");
				Iterator mapIterator = UtecDataManager.getAllMaps().iterator();
				while(mapIterator.hasNext()){
					UtecMapData mapData = (UtecMapData)mapIterator.next();
					if(mapData.getMapName().equals(ApplicationStateManager.getSelectedTuningGroup())){
						UtecInterface.sendMapData(1, mapData);
					}
				}
			}

			else if (cmd.equals("Save To Map #2")) {
				logger.info("Starting to save map #2");
				Iterator mapIterator = UtecDataManager.getAllMaps().iterator();
				while(mapIterator.hasNext()){
					UtecMapData mapData = (UtecMapData)mapIterator.next();
					if(mapData.getMapName().equals(ApplicationStateManager.getSelectedTuningGroup())){
						UtecInterface.sendMapData(2, mapData);
					}
				}
			}

			else if (cmd.equals("Save To Map #3")) {
				logger.info("Starting to save map #3");
				Iterator mapIterator = UtecDataManager.getAllMaps().iterator();
				while(mapIterator.hasNext()){
					UtecMapData mapData = (UtecMapData)mapIterator.next();
					if(mapData.getMapName().equals(ApplicationStateManager.getSelectedTuningGroup())){
						UtecInterface.sendMapData(3, mapData);
					}
				}
			}

			else if (cmd.equals("Save To Map #4")) {
				logger.info("Starting to save map #4");
				Iterator mapIterator = UtecDataManager.getAllMaps().iterator();
				while(mapIterator.hasNext()){
					UtecMapData mapData = (UtecMapData)mapIterator.next();
					if(mapData.getMapName().equals(ApplicationStateManager.getSelectedTuningGroup())){
						UtecInterface.sendMapData(4, mapData);
					}
				}
			}

			else if (cmd.equals("Save To Map #5")) {
				logger.info("Starting to save map #5");
				Iterator mapIterator = UtecDataManager.getAllMaps().iterator();
				while(mapIterator.hasNext()){
					UtecMapData mapData = (UtecMapData)mapIterator.next();
					if(mapData.getMapName().equals(ApplicationStateManager.getSelectedTuningGroup())){
						UtecInterface.sendMapData(5, mapData);
					}
				}
			}
			
			else if(cmd.equals("Force Utec Reset")){
				logger.info("Resetting the Utec");
				UtecInterface.resetUtec();
			}
			
			else if(cmd.equals("Start Logging")){
				logger.info("Kicking off the logging.");
				UtecInterface.startLoggerDataFlow();
			}
			
			else if(cmd.equals("Close Port")){
				logger.info("Closing access to the currently opened port (if any).");
				UtecInterface.closeConnection();
			}
			

			// Only non explicitly defined actions are those generated by ports.
			// Since an arbitrary machine could have any number of serial ports
			// its impossible to hard code choices based on menu items generated
			// on the fly.
			// Must pull the calling object and interrogate
			else {
				JMenuItem theItem = (JMenuItem) e.getSource();
				String portChoice = theItem.getName();
				logger.info("Port chosen: " + portChoice);
				currentPort = portChoice;
				UtecInterface.setPortChoice(currentPort);
				UtecInterface.openConnection();
			}
	}

	public JToolBar getToolBar() {
		return new JutecToolBar(this.theTEL, this);
	}

	/*
	public void saveTableData(String tableGroup, String tableIdentifier, Object[][] data) {
		logger.info("utec save data requested:"+tableIdentifier);
		
		Iterator mapIterate = UtecDataManager.getAllMaps().iterator();
		while(mapIterate.hasNext()){
			UtecMapData mapData = (UtecMapData)mapIterate.next();
			String[] split = tableIdentifier.split(";");
			String mapType = split[0];
			String tableName = split[1];
			if(mapData.getMapName().equals(tableName)){
				if(mapType.equals("Fuel")){
					logger.info("UTE: Fuel Set");
					mapData.setFuelMap(convertObjToDouble(data));
				}
				else if(mapType.equals("Boost")){
					logger.info("UTE: Boost Set");
					mapData.setBoostMap(convertObjToDouble(data));
				}
				else if(mapType.equals("Timing")){
					logger.info("UTE: Timing Set");
					mapData.setTimingMap(convertObjToDouble(data));
				}
				else if(mapType.equals("MapName")){
					logger.info("UTE: MapName Set");
					mapData.setMapName((String)data[0][0]);
				}
			}
		}
	}
	*/
	/**
	 * Helper method for when dealing with fuel timing and boost tables
	 * @param objData
	 * @return
	 */
	private Double[][] convertObjToDouble(Object[][] objData){
		int length = objData.length;
		int width = objData[0].length;
		
		Double[][] newData = new Double[length][width];
		
		for(int i = 0 ; i < length ; i++){
			for(int j = 0 ; j < width ; j++){
				newData[i][j] = (Double)objData[i][j];
			}
		}
		
		return newData;
	}

	public void notifySystemExit() {
		logger.info("Saving map to file.");

    	String tuningGroup = ApplicationStateManager.getSelectedTuningGroup();
    	int mapChangeCount = 0;//this.theTEL.getMapChangeCount(ApplicationStateManager.getCurrentTuningEntity(),tuningGroup);
    	logger.info("Number of maps changed: "+mapChangeCount);
    	int returnValue = 0;
    	if(mapChangeCount > 0){
    		returnValue = JOptionPane.showConfirmDialog(null, "Tuning Group contains changes, save before continuing?", "Warning", JOptionPane.YES_NO_OPTION);
    		
    		if(returnValue == 0){
    			this.theTEL.saveMaps();
    			
                // Kick off the saving file to disk
    			String temp = ApplicationStateManager.getSelectedTuningGroup();
    			UtecMapData mapData = null;
            	Iterator mapIterate = UtecDataManager.getAllMaps().iterator();
            	while(mapIterate.hasNext()){
            		mapData = (UtecMapData)mapIterate.next();
            		if(mapData.getMapName().equals(temp)){
            			break;
            		}
            	}
            	
            	String saveFileName = null;
				fileChosen = fileChooser.showSaveDialog(null);
				if (fileChosen == JFileChooser.APPROVE_OPTION) {
					saveFileName = fileChooser.getSelectedFile().getPath();
					mapData.writeMapToFile(saveFileName);
				}
				
            	this.theTEL.removeTuningGroup(tuningGroup);
            	
    		}else if(returnValue == 1){
    			this.theTEL.removeTuningGroup(tuningGroup);
    		}
    	}
    	
    	UtecInterface.closeConnection();
		this.theTEL.readyForExit();
	}

	public String[] getFileSuffixes() {
		String[] fileSuffixes = new String[1];
		fileSuffixes[0] = "txt";
		
		return fileSuffixes;
	}

	public void openFile(String fullPath) {
		logger.info("GUI Called for file open :"+fullPath);
		
		// Pull the short filename
		String shortFileName = DataTools.parseShortFileName(fullPath);
		
		// Get associated Map data
		String uniqueID = UtecMapManager.getInstance().openUtecMap(fullPath);
		
		// Pull the map created to build the rest of the nodes below the root node
		UtecMapData utecMap = UtecMapManager.getInstance().getUtecMap(uniqueID);
		
		// Build up the tree
		ETreeNode root = new ETreeNode("UTEC : ["+utecMap.getMapName()+" - "+utecMap.getMapComment()+"] : ", new TableMetaData(getFileSuffixes()[0], TableMetaData.MAP_SET_ROOT,0.0,0.0,new Object[0],null,null,false,"","", "", "", uniqueID, null,null,null,"",this, "", "", "", "", ""));

		// Category fuel
		String fuelTableName = "Fueling";
		TableMetaData fuelMetaData = new TableMetaData();
		fuelMetaData.setSuffix(getFileSuffixes()[0]);
		fuelMetaData.setDimensions(TableMetaData.DATA_3D);
		fuelMetaData.setMinValue(-20.0);
		fuelMetaData.setMaxValue(20.0);
		fuelMetaData.setIgnoredValues(new Object[0]);
		fuelMetaData.setColumnLabels(utecMap.getXAxisData());
		fuelMetaData.setRowLabels(utecMap.getYAxisData());
		fuelMetaData.setInvertedColoring(false);
		fuelMetaData.setTableName(fuelTableName);
		fuelMetaData.setXAxisLabel("Load");
		fuelMetaData.setYAxisLabel("RPM");
		fuelMetaData.setTableIdentifier(fuelTableName);
		fuelMetaData.setTableGroup(uniqueID);
		fuelMetaData.setParentTuningEntity(this);
		fuelMetaData.setDataFormat("#.#");
		fuelMetaData.setXFormat("#.#");
		fuelMetaData.setYFormat("#.#");
		fuelMetaData.setDescription("");
		fuelMetaData.setMainTableUnits("% Fuel Offsets");
		fuelMetaData.setXAxisUnits("%");
		fuelMetaData.setYAxisUnits("");
		fuelMetaData.setShortFileName(shortFileName);
		fuelMetaData.setMainTableName(fuelTableName);
		
		ETreeNode fuelDataNode = new ETreeNode(fuelTableName, fuelMetaData);
		
		// Category boost
		String boostTableName = "Boost";
		TableMetaData boostMetaData = new TableMetaData();
		boostMetaData.setSuffix(getFileSuffixes()[0]);
		boostMetaData.setDimensions(TableMetaData.DATA_3D);
		boostMetaData.setMinValue(-20.0);
		boostMetaData.setMaxValue(20.0);
		boostMetaData.setIgnoredValues(new Object[0]);
		boostMetaData.setColumnLabels(utecMap.getXAxisData());
		boostMetaData.setRowLabels(utecMap.getYAxisData());
		boostMetaData.setInvertedColoring(false);
		boostMetaData.setTableName(boostTableName);
		boostMetaData.setXAxisLabel("Load");
		boostMetaData.setYAxisLabel("RPM");
		boostMetaData.setTableIdentifier(boostTableName);
		boostMetaData.setTableGroup(uniqueID);
		boostMetaData.setParentTuningEntity(this);
		boostMetaData.setDataFormat("#.#");
		boostMetaData.setXFormat("#.#");
		boostMetaData.setYFormat("#.#");
		boostMetaData.setDescription("");
		boostMetaData.setMainTableUnits("N/A");
		boostMetaData.setXAxisUnits("%");
		boostMetaData.setYAxisUnits("");
		boostMetaData.setShortFileName(shortFileName);
		boostMetaData.setMainTableName(boostTableName);
		
		ETreeNode boostDataNode = new ETreeNode(boostTableName, boostMetaData);
		
		// Category timing
		String timingTableName = "Timing";
		TableMetaData timingMetaData = new TableMetaData();
		timingMetaData.setSuffix(getFileSuffixes()[0]);
		timingMetaData.setDimensions(TableMetaData.DATA_3D);
		timingMetaData.setMinValue(-20.0);
		timingMetaData.setMaxValue(20.0);
		timingMetaData.setIgnoredValues(new Object[0]);
		timingMetaData.setColumnLabels(utecMap.getXAxisData());
		timingMetaData.setRowLabels(utecMap.getYAxisData());
		timingMetaData.setInvertedColoring(false);
		timingMetaData.setTableName(timingTableName);
		timingMetaData.setXAxisLabel("Load");
		timingMetaData.setYAxisLabel("RPM");
		timingMetaData.setTableIdentifier(timingTableName);
		timingMetaData.setTableGroup(uniqueID);
		timingMetaData.setParentTuningEntity(this);
		timingMetaData.setDataFormat("#.#");
		timingMetaData.setXFormat("#.#");
		timingMetaData.setYFormat("#.#");
		timingMetaData.setDescription("");
		timingMetaData.setMainTableUnits("Degrees BTDC");
		timingMetaData.setXAxisUnits("%");
		timingMetaData.setYAxisUnits("");
		timingMetaData.setShortFileName(shortFileName);
		timingMetaData.setMainTableName(timingTableName);
		
		ETreeNode timingDataNode = new ETreeNode(timingTableName, timingMetaData);
		
		// Category map name
		String mapTableName = "Name";
		TableMetaData mapMetaData = new TableMetaData();
		mapMetaData.setSuffix(getFileSuffixes()[0]);
		mapMetaData.setDimensions(TableMetaData.DATA_1D);
		mapMetaData.setMinValue(-20.0);
		mapMetaData.setMaxValue(20.0);
		mapMetaData.setIgnoredValues(new Object[0]);
		mapMetaData.setColumnLabels(utecMap.getXAxisData());
		mapMetaData.setRowLabels(utecMap.getYAxisData());
		mapMetaData.setInvertedColoring(false);
		mapMetaData.setTableName(mapTableName);
		mapMetaData.setXAxisLabel("Load");
		mapMetaData.setYAxisLabel("RPM");
		mapMetaData.setTableIdentifier(mapTableName);
		mapMetaData.setTableGroup(uniqueID);
		mapMetaData.setParentTuningEntity(this);
		mapMetaData.setDataFormat("#.#");
		mapMetaData.setXFormat("#.#");
		mapMetaData.setYFormat("#.#");
		mapMetaData.setDescription("");
		mapMetaData.setMainTableUnits("N/A");
		mapMetaData.setXAxisUnits("%");
		mapMetaData.setYAxisUnits("");
		mapMetaData.setShortFileName(shortFileName);
		mapMetaData.setMainTableName(mapTableName);
		
		ETreeNode mapDataNode = new ETreeNode(mapTableName, mapMetaData);
		
		// Category map name
		String commentTableName = "Comment";
		TableMetaData commentMetaData = new TableMetaData();
		commentMetaData.setSuffix(getFileSuffixes()[0]);
		commentMetaData.setDimensions(TableMetaData.DATA_1D);
		commentMetaData.setMinValue(-20.0);
		commentMetaData.setMaxValue(20.0);
		commentMetaData.setIgnoredValues(new Object[0]);
		commentMetaData.setColumnLabels(utecMap.getXAxisData());
		commentMetaData.setRowLabels(utecMap.getYAxisData());
		commentMetaData.setInvertedColoring(false);
		commentMetaData.setTableName(commentTableName);
		commentMetaData.setXAxisLabel("Load");
		commentMetaData.setYAxisLabel("RPM");
		commentMetaData.setTableIdentifier(commentTableName);
		commentMetaData.setTableGroup(uniqueID);
		commentMetaData.setParentTuningEntity(this);
		commentMetaData.setDataFormat("#.#");
		commentMetaData.setXFormat("#.#");
		commentMetaData.setYFormat("#.#");
		commentMetaData.setDescription("");
		commentMetaData.setMainTableUnits("N/A");
		commentMetaData.setXAxisUnits("%");
		commentMetaData.setYAxisUnits("");
		commentMetaData.setShortFileName(shortFileName);
		commentMetaData.setMainTableName(commentTableName);
		
		ETreeNode commentDataNode = new ETreeNode(commentTableName, commentMetaData);
		
		// Build up the final tree structure
		root.add(mapDataNode);
		root.add(commentDataNode);
		root.add(fuelDataNode);
		root.add(boostDataNode);
		root.add(timingDataNode);
		
		// Call the passed tuning entity listener with the corresponding map structure
		this.theTEL.addNewTuningGroup(root);
	}

	public void saveFile(String tableGroup, String path) {
		logger.info("Saving tableGroup :"+tableGroup);
		logger.info("Saving path :"+path);

		// Pull the relevant utec map
		UtecMapData utecMap = UtecMapManager.getInstance().getUtecMap(tableGroup);
		
		// Write file to disk
		utecMap.writeMapToFile(path);
	}

	public void setCurrentTuningGroup(String tuningGroup) {
		logger.info("Setting Current tuningGroup :"+tuningGroup);
	}

	public void saveTableData(String tableGroup, String tableIdentifier, Object[][] data, int dataTableType) {
		logger.info("Save Table Data");
		logger.info("  - tableGroup  :"+tableGroup);
		logger.info("  - tableIdentifier  :"+tableIdentifier);
		logger.info("  - dataTableType  :"+dataTableType);
		
		// Pull the relevant utec map
		UtecMapData utecMap = UtecMapManager.getInstance().getUtecMap(tableGroup);
		
		// Saving data
		if(tableIdentifier.toLowerCase().equals("boost")){
			utecMap.setBoostMap(DataTools.convertObjToDouble(data));
		}
		else if(tableIdentifier.toLowerCase().equals("fueling")){
			utecMap.setFuelMap(DataTools.convertObjToDouble(data));
		}
		else if(tableIdentifier.toLowerCase().equals("timing")){
			utecMap.setTimingMap(DataTools.convertObjToDouble(data));
		}
		else if(tableIdentifier.toLowerCase().equals("name")){
			utecMap.setMapName((String)data[0][0]);
		}
		else if(tableIdentifier.toLowerCase().equals("comment")){
			utecMap.setMapComment((String)data[0][0]);
		}
	}

}
