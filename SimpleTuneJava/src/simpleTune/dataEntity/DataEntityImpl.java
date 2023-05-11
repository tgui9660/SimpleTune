package simpleTune.dataEntity;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JToolBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.general.tools.DataTuple;
import simpleTune.general.tools.DataTupleMap;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.interfaces.TuningEntity;
import simpleTune.gui.interfaces.TuningEntityListener;
import simpleTune.gui.tree.ETreeNode;

public class DataEntityImpl implements TuningEntity{

	private Log logger = LogFactory.getLog(getClass());
	
	private Vector<DataTuple> values = new Vector<DataTuple>();
	private TuningEntityListener theTel;
	private DataTupleMap timingMap = null;
	
	public String[] getFileSuffixes() {
		return new String[]{"csv"};
	}

	public Vector<JMenu> getMenuItems() {
		logger.info("Get Menu Items");
		return null;
	}

	public String getName() {
		return "Data Parser";
	}

	public Object[][] getTableData(String tableGroup, String tableIdentifier) {
		logger.info("Get Table Data tableGroup :"+tableGroup);
		logger.info("Get Table Data tableIdentifier :"+tableIdentifier);
		
		return timingMap.getData();
	}

	public JToolBar getToolBar() {
		logger.info("Get Tool Bar");
		return null;
	}

	public void init(TuningEntityListener listener) {
		this.theTel = listener;
	}

	public void notifySystemExit() {
		this.theTel.readyForExit();
	}

	public void openFile(String path) {
		logger.info("Requesting file open :"+path);
		
		// Open the file
	    FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			logger.error(e);
		}
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine = null;
	    
	    // First pull the log header of csv
	    String header = null;
	    try {
	    	header = br.readLine();
		} catch (IOException e1) {
			logger.error(e1);
		}
	    
		// Get needed indexes
		int rpmIndex = this.getAttributeIndex(header, "rpm");
		int timingIndex = this.getAttributeIndex(header, "ignition total timing");
		int loadIndex = this.getAttributeIndex(header, "load");
		
		logger.info("RPM index :"+ rpmIndex);
		logger.info("TIMING index :"+ timingIndex);
		logger.info("LOAD index :"+ loadIndex);
		
		// Sanity check
		if(rpmIndex == -1 || timingIndex == -1 || loadIndex == -1){
			logger.error("Invalid set of logging parameters.");
			return;
		}
		
		// Build data bucket
		this.values = new Vector<DataTuple>();
		
	    //Read File Line By Line add to vector after parsing out tuple (x/y/z) data points
	    try {
			while ((strLine = br.readLine()) != null){
			  //logger.debug(strLine);
			  values.add(new DataTuple(getCSVValue(strLine, loadIndex), getCSVValue(strLine, rpmIndex), getCSVValue(strLine, timingIndex)));
			}
		} catch (IOException e) {
			logger.error(e);
		}
		this.timingMap = new DataTupleMap(values, this.getXAxisData(), this.getYAxisData());
		
		// Category timing
		String timingTableName = "Timing";
		TableMetaData timingMetaData = new TableMetaData();
		timingMetaData.setSuffix(getFileSuffixes()[0]);
		timingMetaData.setDimensions(TableMetaData.DATA_3D);
		timingMetaData.setMinValue(0.0);
		timingMetaData.setMaxValue(60.0);
		timingMetaData.setIgnoredValues(new Object[0]);
		timingMetaData.setColumnLabels(this.getXAxisData());
		timingMetaData.setRowLabels(this.getYAxisData());
		timingMetaData.setInvertedColoring(false);
		timingMetaData.setTableName(timingTableName);
		timingMetaData.setXAxisLabel("Load");
		timingMetaData.setYAxisLabel("RPM");
		timingMetaData.setTableIdentifier(path+";timing");
		timingMetaData.setTableGroup(path);
		timingMetaData.setParentTuningEntity(this);
		timingMetaData.setDataFormat("#.#");
		timingMetaData.setXFormat("#.#");
		timingMetaData.setYFormat("#.#");
		timingMetaData.setDescription("");
		timingMetaData.setMainTableUnits("Degrees BTDC");
		timingMetaData.setXAxisUnits("%");
		timingMetaData.setYAxisUnits("");
		timingMetaData.setShortFileName(path);
		timingMetaData.setMainTableName(timingTableName);
		
		ETreeNode timingDataNode = new ETreeNode(timingTableName, timingMetaData);
		
		
		
		// Build up the tree
		ETreeNode root = new ETreeNode("CSV : ["+path+"] : ", new TableMetaData(getFileSuffixes()[0], TableMetaData.MAP_SET_ROOT,0.0,0.0,new Object[0],null,null,false,"","", "", "", path, null,null,null,"",this, "", "", "", "", ""));

		root.add(timingDataNode);
		
		// Call the passed tuning entity listener with the corresponding map structure
		this.theTel.addNewTuningGroup(root);
	}
	
	public Double[][] getYAxisData(){
		double startValue = 0;
		double inc = 425;
		Double[][] values = new Double[18][1];
		for(int i = 0; i < values.length; i++){
			values[i][0] = startValue + i*inc;
		}
		return values;
	}
	
	public Double[][] getXAxisData(){
		double startValue = 0;
		double inc = .27;
		Double[][] values = new Double[1][15];
		for(int i = 0; i < values[0].length; i++){
			values[0][i] = startValue + i*inc;
		}
		return values;
	}
	
	private Double getCSVValue(String csvLine, int index){
		String[] strings = this.parseValues(csvLine);
		double parseDouble = Double.parseDouble(strings[index]);
		
		return parseDouble;
	}
	
	private int getAttributeIndex(String csvLine, String value){
		int index = -1;
		String[] strings = this.parseValues(csvLine);
		
		for(int i = 0; i < strings.length; i++){
			if(strings[i].toLowerCase().contains(value.toLowerCase())){
				index = i;
				break;
			}
		}
		
		return index;
	}
	
	private String[] parseValues(String csvLine){
		String[] theValues = csvLine.split(",");
		
		return theValues;
	}

	public void removeTuningGroup(String tuningGroup) {
		logger.info("Remove Tuning Group :"+tuningGroup);
		
	}

	public void saveFile(String tableGroup, String path) {
		logger.info("Save File");
		logger.info(" - tableGroup :"+tableGroup);
		logger.info(" - path :"+path);
	}

	public void saveTableData(String tableGroup, String tableIdentifier, Object[][] data, int dataTableType) {
		logger.info("Save Table Data File");
		logger.info(" - tableGroup :"+tableGroup);
		logger.info(" - tableIdentifier :"+tableIdentifier);
		logger.info(" - dataTableType :"+dataTableType);
	}

	public void setCurrentTuningGroup(String tuningGroup) {
		logger.info("Set Current Tuning Group :"+tuningGroup);
	}

	public void actionPerformed(ActionEvent arg0) {
		logger.info("Action Occured");
	}

}
