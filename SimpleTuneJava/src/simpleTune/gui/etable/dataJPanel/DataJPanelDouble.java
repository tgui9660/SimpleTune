package simpleTune.gui.etable.dataJPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import simpleTune.general.tools.DataTools;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.etable.ETable;
import simpleTune.gui.etable.ETableMenuBar;
import simpleTune.gui.etable.ETableSaveState;
import simpleTune.gui.etable.ETableToolBar;
import simpleTune.gui.etable.dataJPanel.interfaces.DataJPanelInterface;
import simpleTune.gui.etable.text.RotatedLabel;
import simpleTune.gui.tools.ClipBoardCopy;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

public class DataJPanelDouble extends JPanel implements DataJPanelInterface{
	private Log logger = LogFactory.getLog(getClass());
    private Stack<ETableSaveState> savedData = new Stack<ETableSaveState>();
	
	private TableMetaData tableMetaData;
	private ClipBoardCopy excelCopyMainTable;
	private ClipBoardCopy excelCopyXAxisTable;
	private ClipBoardCopy excelCopyYAxisTable;
	private ETableMenuBar eTableMenuBar = new ETableMenuBar(this);
	private ETableToolBar toolBar = null;
	private Double[][] newData = null;
	
	private boolean cols = true;
	private boolean rows = true;
	public boolean scaleSwitch = false;
	
	private int OSX_HEIGHT = 60;
	private int WINDOWS_HEIGHT = 20;
	private int LINUX_HEIGHT = 25;
	private int ESTIMATED_CELL_HEIGHT = 16;
	private int ESTIMATED_CELL_WIDTH = 35;
	private int ESTIMATED_COLS_HEIGHT = 14;
	
	private ETable currentETableInScope = null;
	
	// Contains a list of all the tables used within this data panel.
	// Possible inclusions at this point are:
	// Row Table
	// Col Table
	// Main Data Table
	//private Vector<ETable> tableVector = new Vector<ETable>();
	
	private ETable eTableMain = null;
	private ETable XAxisData = null;
	private ETable YAxisData = null;
	
	public DataJPanelDouble(TableMetaData tableMetaData, Object[][] data) {
		
		// Can't do nuthin without data
		if(data == null){
			logger.error("DataJPanel3DDouble: data passed into constructor is null.");
			return;
		}

		// TODO, maybe in the future expand to different data types
		if(data[0][0] instanceof Double){
			// Typical 2D & 3D data
			newData = (Double[][])data;
		}

		// Save initial data
		this.savedData.push(new ETableSaveState(newData));
		
		// Build up columns & rows
		// TODO very messy, clean up
		Object[][] columnLabels = tableMetaData.getColumnLabels();
		if(columnLabels == null){
			cols = false;
			columnLabels = new Double[1][data[0].length];
			for(int i = 0; i < data[0].length; i++){
				columnLabels[0][i] = 0.0;
			}
		}
		this.XAxisData = new ETable(tableMetaData, columnLabels, this, true, false);
		this.excelCopyXAxisTable = new ClipBoardCopy(XAxisData);
		//ETable columnTable = new ETable(tableMetaData, columnLabels, this);
		
		Object[][] rowLabels = tableMetaData.getRowLabels();
		if(rowLabels == null){
			rows = false;
			rowLabels = new Double[data.length][1];
			for(int i = 0; i < data.length; i++){
				rowLabels[i][0] = 0.0;
			}
		}
		this.YAxisData = new ETable(tableMetaData, rowLabels, this, false, true);
		this.excelCopyYAxisTable = new ClipBoardCopy(YAxisData);
		//ETable rowTable = new ETable
		
		// Get the table
		eTableMain = new ETable(tableMetaData, newData, this, false, false);
		eTableMain.setBackground(Color.WHITE);
        
		// For copying purposes
        this.excelCopyMainTable = new ClipBoardCopy(eTableMain);

        // Define toolbar to use
        this.toolBar = new ETableToolBar(tableMetaData, this);
        
        // Setup the scroll pane policy
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        if(rows){
            // Add the row table
            c.gridx = 0;
            c.gridy = 1;
            c.fill = GridBagConstraints.VERTICAL;
            c.weightx = 0;
            c.weighty = .1;
            c.insets = new Insets(0,0,0,5);
            tablePanel.add(this.YAxisData, c);
            //this.tableVector.add(rowTable);
        }

        if(cols){
            // Add the column table
            c.gridwidth = 5;
            c.gridx = 1;
            c.gridy = 0;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = .2;
            c.weighty = 0;
            c.insets = new Insets(0,0,5,0);
            tablePanel.add(this.XAxisData, c);
            //this.tableVector.add(columnTable);
        }

        
        // Add the table
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = .5;
        tablePanel.add(eTableMain, c);
        //this.tableVector.add(eTableMain);
        
        // Scroll pane for large things
		JScrollPane scrollPane = new JScrollPane(tablePanel);
		
		// Table Title
		JLabel titleJLabel = new JLabel(tableMetaData.getTableName().toUpperCase() +"  " + tableMetaData.getMainTableUnits());
		titleJLabel.setHorizontalAlignment(JLabel.CENTER);
		

		
		// Y Label
		String yAxisLabel = "";
		if(cols){
			//yAxisLabel = tableMetaData.getYAxisLabel() +"  " + tableMetaData.getYAxisUnits();
			yAxisLabel = tableMetaData.getYAxisUnits();
		}
		if(yAxisLabel == null){
			yAxisLabel = "";
		}
		RotatedLabel yAxisJLabel = new RotatedLabel(yAxisLabel);
		yAxisJLabel.setHorizontalAlignment(JLabel.CENTER);
		
		
		
		// X Label
		String xAxisLabel = tableMetaData.getXAxisLabel() +"  " + tableMetaData.getXAxisUnits();
		if(xAxisLabel == null){
			xAxisLabel = "";
		}
		JLabel xAxisJLabel = new JLabel(xAxisLabel);
		xAxisJLabel.setHorizontalAlignment(JLabel.CENTER);
		// if no X label, try the y one :)
		if(!cols){
			xAxisJLabel = new JLabel(tableMetaData.getYAxisLabel() +"  " + tableMetaData.getYAxisUnits());
			xAxisJLabel.setHorizontalAlignment(JLabel.LEFT);
		}
		

		// Main table Label
		String mainAxisLabel = tableMetaData.getMainTableUnits();
		if(mainAxisLabel == null){
			mainAxisLabel = "";
		}
		JLabel mainAxisJLabel = new JLabel(mainAxisLabel);
		mainAxisJLabel.setHorizontalAlignment(JLabel.CENTER);
		
		
		this.setLayout(new BorderLayout());
		this.add(xAxisJLabel, BorderLayout.NORTH);
		this.add(yAxisJLabel, BorderLayout.WEST);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(mainAxisJLabel, BorderLayout.SOUTH);
		
	}
	
	public void refresh(){
		if(this.eTableMain != null){
			logger.info("Refreshing Main Table");
			this.eTableMain.refresh();
			//this.eTableMain.repaint();
		}
		
		if(this.XAxisData != null){
			logger.info("Refreshing X Axis");
			this.XAxisData.refresh();
			//this.XAxisData.repaint();
		}
		
		if(this.YAxisData != null){
			logger.info("Refreshing Y Axis");
			this.YAxisData.refresh();
			//this.YAxisData.repaint();
		}
	}
	
	public ETable getCurrentETableInScope(){
		return this.currentETableInScope;
	}
	
	public void setCurrentETableInScope(ETable eTable){
		this.currentETableInScope = eTable;
		
		if(this.eTableMain != eTable){
			this.eTableMain.setNoCellsSelected();
		}
		
		if(this.XAxisData != eTable){
			this.XAxisData.setNoCellsSelected();
		}
		
		if(this.YAxisData != eTable){
			this.YAxisData.setNoCellsSelected();
		}
		
		/*
		Iterator<ETable> iterator = this.tableVector.iterator();
		while(iterator.hasNext()){
			ETable next = iterator.next();
			if(!next.equals(eTable)){
				next.setNoCellsSelected();
			}
		}
		*/
	}
	
	public JToolBar getToolBar(){
		return this.toolBar;
	}
	
	public JMenuBar getMenuBar(){
		return this.eTableMenuBar;
	}
	
	/**
	 * Check to see if data relevant to this frame has changed
	 * 
	 * @return
	 */
	public boolean dataChanged(){
		if(this.eTableMain.getHasDataChanged()){
			logger.info("Main Table Changed :");
			return true;
		}
		
		if(this.XAxisData.getHasDataChanged()){
			logger.info("X axis Table Changed :");
			return true;
		}
		
		if(this.YAxisData.getHasDataChanged()){
			logger.info("Y axis Table Changed :");
			return true;
		}
		
		/*
		Iterator<ETable> iterator = this.tableVector.iterator();
		while(iterator.hasNext()){
			ETable next = iterator.next();
			if(next.hasDataChanged()){
				return true;
			}
		}
		*/
		logger.info("NO Table Changed :");
		return false;
	}
	

	public boolean mainDataTableChange() {
		// TODO Auto-generated method stub
		return this.eTableMain.getHasDataChanged();
	}

	public boolean xAxisDataTableChange() {
		// TODO Auto-generated method stub
		return this.XAxisData.getHasDataChanged();
	}

	public boolean yAxisDataTableChange() {
		// TODO Auto-generated method stub
		return this.YAxisData.getHasDataChanged();
	}

	/**
	 * Helper method to compare data in two arrays. Must be a better way to do this.
	 * .equals and == does not work for some reason.
	 * @param data1
	 * @param data2
	 * @return
	 */
	private boolean compareMe(Double[][] data1, Object[][] data2){
		int width = data1.length;
		int height = data1[0].length;
		
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if((data1[i][j] - (Double)data2[i][j]) != 0){
					return false;
				}
			}
		}
		return true;
	}

	public void copySelectedTableData() {
		// TODO Auto-generated method stub
		
	}

	public void copyEntireTable() {
		// TODO Auto-generated method stub
		
	}

	public void pasteTableData() {
		// TODO Auto-generated method stub
		
	}

	public void setClosed(boolean value) {
		// TODO Auto-generated method stub
		
	}

	public void revertDataState() {
		// TODO Auto-generated method stub
		
	}

	public void saveDataState() {
		// TODO Auto-generated method stub
		
	}

	public void replaceMainData(Object[][] newData) {
		if(!(newData[0][0] instanceof Double)){
			return;
		}
		//logger.info("Replacing main table data now.");
		this.eTableMain.replaceAlltableData(DataTools.convertObjToDouble(newData));
	}

	public void replaceXAxisData(Object[][] newData) {
		if(!(newData[0][0] instanceof Double)){
			return;
		}
		
		this.XAxisData.replaceAlltableData(DataTools.convertObjToDouble(newData));
	}

	public void replaceYAxisData(Object[][] newData) {
		if(!(newData[0][0] instanceof Double)){
			return;
		}
		
		this.YAxisData.replaceAlltableData(DataTools.convertObjToDouble(newData));
	}
	
	public Object[][] getData() {	
		return newData;
	}
	
	public Object[][] getXAXisData() {
		if(this.XAxisData != null){
			return this.XAxisData.getData();
		}
		return null;
	}

	public Object[][] getYAxisData() {
		if(this.YAxisData != null){
			return this.YAxisData.getData();
		}
		return null;
	}

	public Dimension getFrameSize() {
		int width = newData[0].length*ESTIMATED_CELL_WIDTH;
		if(rows){
			width += 100;
		}
		
		int totalRowHeight = newData.length*ESTIMATED_CELL_HEIGHT;
		
		// If there is a column table, then add some space for it
		if(cols){
			totalRowHeight += this.ESTIMATED_COLS_HEIGHT;
		}
		
		if(System.getProperty("os.name").equalsIgnoreCase("linux")){
			totalRowHeight += this.LINUX_HEIGHT;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("mac")){
			totalRowHeight += this.OSX_HEIGHT;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("windows")){
			totalRowHeight += this.WINDOWS_HEIGHT;
		}else{
			totalRowHeight += 50;
		}

		
		return new Dimension(width, totalRowHeight);
	}

	public void setIsScaleActive(boolean isActive) {
		logger.info("DataJPanelDouble scaleMode is :" + isActive);
		this.scaleSwitch = isActive;
	}

	public boolean getIsScaleActive() {
		return this.scaleSwitch;
	}

	public ETable getMainETable() {
		return eTableMain;
	}

	public boolean equals(DataJPanelInterface dataJPanel) {
		logger.info(" **** DOUBLE Equals Test");
		
		// Same type?
		if(!(dataJPanel instanceof DataJPanelDouble)){
			return false;
		}
		
		Object[][] data1 = this.getData();
		Object[][] data2 = dataJPanel.getData();
		
		// Dimension check
		if(data1.length != data2.length || data1[0].length != data2[0].length){
			return false;
		}
		
		//logger.info("Dimensions 1 :  "+data1.length+"  :  "+data1[0].length);
		//logger.info("Dimensions 1 :  "+data2.length+"  :  "+data2[0].length);
		
		// Raw data check
		//logger.info("----------------------");
		for(int i = 0; i < data1.length; i++){
			for(int j = 0; j < data1[0].length; j++){
				//logger.info("----");
				//logger.info("Comparing "+data1[i][j]+" : "+data2[i][j]);
				if(!data1[i][j].equals(data2[i][j])){
					logger.info(" **** DOUBLE Data Different.");
					return false;
				}
			}
		}
		
		logger.info(" **** DOUBLE Data Same.");
		// Pass all checks, everything ok
		return true;
	}
}
