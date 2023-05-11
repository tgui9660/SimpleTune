package simpleTune.gui.etable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.general.tools.FitData;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.etable.dataJPanel.interfaces.DataJPanelInterface;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

public class ETable extends JTable{
	private Log logger = LogFactory.getLog(getClass());
	
    public static final int INCREMENT = 0;
	public static final int DECREMENT = 1;
	public static final int MULTIPLY = 2;
	public static final int SET = 3;
	public static final int SMOOTH = 4;
	
	private ETableModel theModel;
	private Vector tempSelectedCells = new Vector();
	private TableMetaData tableMetaData;
	private Font theFont = new Font("LucidaBrightRegular", Font.PLAIN, 10);
	private boolean hasDataChanged = false;
	public DataJPanelInterface dataJPanel;
	public boolean isXAxis = false;
	public boolean isYAxis = false;
	private Stack<Double[][]> savedStates = new Stack<Double[][]>();
	private Stack<Double[][]> redoStates = new Stack<Double[][]>();
	private ETableCellRenderer tableRenderer = null;
	
	public void savedStateUndo(){
		logger.debug("UNDO state selected");
		
		//Save current state
		Double[][] doubleData = this.theModel.getDoubleData();
		
		Double[][] pop = null;
		
		if(savedStates.size() > 1){

			pop = savedStates.pop();
			redoStates.push(pop);

		}else if(savedStates.size() == 1){
			pop = savedStates.peek();
		}
		
		if(pop != null){
			logger.debug("ETable savedStateUndo!");
			this.replaceAlltableData(pop);
		}
	}
	
	public void savedStateRedo(){
		logger.debug("REDO state selected");
		
		if(!redoStates.isEmpty()){
			Double[][] pop = redoStates.pop();
			savedStates.push(pop);
			if(pop != null){
				logger.debug("ETable savedStateRedo!");
				this.replaceAlltableData(pop);
			}
		}
	}
	
	public ETable(TableMetaData metaData, Object[][] data, DataJPanelInterface dataJPanel, boolean isXAxis, boolean isYAxis){
		this.isXAxis = isXAxis;
		this.isYAxis = isYAxis;
		
		this.dataJPanel = dataJPanel;
		this.theModel = new ETableModel(metaData, data, this);
		super.setModel(this.theModel);
		this.tableMetaData = metaData;
		this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.setCellSelectionEnabled(true);
		
		if(data[0][0] instanceof Double){
			this.tableRenderer = new ETableCellRenderer(getMinValue(data), getMaxValue(data), metaData.getIgnoredValues(), metaData.isInvertedColoring(), this);
			this.setDefaultRenderer(Object.class, tableRenderer);
		}
		
		this.setGridColor(Color.BLACK);
		this.setFont(theFont);
		this.setRowHeight(15);
		
		// Save the initial state
		logger.info("Saving initial state.");
		this.saveState(this.theModel.getDoubleData());
		
		// Get rid of stock column header
		this.getTableHeader().setVisible(false);
		this.setTableHeader(null);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Can't set the column width unless this is set.
		
		// Search table for longest value
		// Used to set table cell widths
		int maxCharCount = this.getMaxCharCount(data);
		int newWidth = maxCharCount*8;
		
		if(data[0].length == 1 && data.length > 1){
			for(int i = 0; i < data[0].length; i++){
				TableColumn column = this.getColumnModel().getColumn(i);
				
				//column.setMaxWidth(newWidth);
				column.setPreferredWidth(newWidth);
				column.setMinWidth(newWidth);
			}
		}else{
			for(int i = 0; i < data[0].length; i++){
				TableColumn column = this.getColumnModel().getColumn(i);
				
				//column.setMaxWidth(35);
				column.setPreferredWidth(35);
				column.setMinWidth(35);
				
			}
			
		}
		
		if(this.isYAxis){
			TableColumn column = this.getColumnModel().getColumn(0);
			
			column.setMinWidth(40);
			column.setMaxWidth(100);
			column.setPreferredWidth(maxCharCount*7);
		}
	}
	
	private int getMaxCharCount(Object[][] data){
		int maxValue = 0;
		
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[0].length ; j++){
				String testValue = data[i][j]+"";
				int testCount = testValue.length();
				if(maxValue < testCount){
					maxValue = testCount;
				}
			}
		}
		
		return maxValue;
	}
	
	private double getMinValue(Object[][] data){
		Double minValue = Double.MAX_VALUE;
		if(data[0][0] instanceof Double){
			for(int i = 0; i < data.length; i++){
				for(int j = 0; j < data[0].length ; j++){
					Double testValue = (Double)data[i][j];
					//logger.info("TestValue i:"+i +"  j:"+j+"  value:"+testValue);
					//slogger.info("TestValue :"+testValue);
					if(testValue < minValue){
						minValue = testValue;
					}
				}
			}
			
			return minValue;
		}
		return 0.0;
	}
	
	private double getMaxValue(Object[][] data){
		Double maxValue = Double.MIN_VALUE;
		
		if(data[0][0] instanceof Double){
			for(int i = 0; i < data.length; i++){
				for(int j = 0; j < data[0].length ; j++){
					Double testValue = (Double)data[i][j];
					if(testValue > maxValue){
						maxValue = testValue;
					}
				}
			}
			
			return maxValue;
		}
		return 0.0;
	}
	
	/**
	 * Zero based row and columns, inclusive
	 * 
	 * @param rowStart
	 * @param rowEnd
	 * @param colStart
	 * @param colEnd
	 */
	public void setSelectedQuadrilateral(int rowStart, int rowEnd, int colStart, int colEnd){
		for(int i = rowStart; i < rowEnd + 1; i++){
			for(int j = colStart; j < colEnd + 1; j++){
				this.changeSelection(i, j, false, true);
			}
		}
	}
	
	
	/**
	 * Set a cell as being selected.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public void setSelected(int rowIndex, int colIndex){

	    boolean toggle = false;
	    boolean extend = true;
	    //this.changeSelection(rowIndex, colIndex, toggle, extend);
	    //this.changeSelection(1, 1, toggle, extend);
	    //this.changeSelection(2, 2, toggle, extend);
	    //this.setSelectedQuadrilateral(1,3,4,6);
	    //this.addColumnSelectionInterval(3, 6);
	   // logger.info(" >"+ this.getSelectedColumnCount()+"  >"+this.getSelectedRowCount());
	    
	    //LOGGER.debug(" >"+ this.getSelectedColumnCount()+"  >"+this.getSelectedRowCount());
	}
	
	/**
	 * Increment cell values by passed double amount.
	 * @param amount
	 */
	public void changeSelectedCells(double amount, int type){
		Vector<Coordinate> tempCells = new Vector<Coordinate>();
		
		int rowStart = this.getSelectedRow();
		int rowEnd = this.getSelectionModel().getMaxSelectionIndex();
		
		int colStart = this.getSelectedColumn();
		int colEnd = this.getColumnModel().getSelectionModel().getMaxSelectionIndex();
		
		Double[][] tempSelectionData = new Double[colEnd - colStart+1][rowEnd - rowStart+1];
		Double[][] smoothData = new Double[colEnd - colStart+1][rowEnd - rowStart+1];
		
		// Get smoothed data from selection
		if(type == ETable.SMOOTH){
			
			if(colEnd == colStart && (rowEnd - rowStart) < 2){
				logger.info("Improper smooth selection dimensions 1.");
				return;
			}
			else if(rowEnd == rowStart && (colEnd - colStart) < 2){
				logger.info("Improper smooth selection dimensions 2.");
				return;
			}

			logger.info("Attempting to smooth data.");
			
			for(int i = rowStart; i <= rowEnd; i++){
				for(int j = colStart; j <= colEnd; j++){
					if(this.isCellSelected(i,j)){
						// The cell is selected
						Object value = theModel.getValueAt(i, j);
						
						if(value instanceof Double){
							tempSelectionData[j - colStart][i - rowStart] = (Double)value;
						}
					}
				}
			}
			
			// Smooth the data
			smoothData = FitData.getFullSmooth(tempSelectionData);
		}
		
		int counter = 0;
		for(int i = rowStart; i <= rowEnd; i++){
			for(int j = colStart; j <= colEnd; j++){
				if(this.isCellSelected(i,j)){
					counter++;
					tempCells.add(new Coordinate(i,j));
					// The cell is selected
					Object value = theModel.getValueAt(i, j);
					
					if(value instanceof Double){
						double temp = 0.0;
						if(type == ETable.INCREMENT){
							 temp = (Double)value + amount;
						}
						
						else if(type == ETable.DECREMENT){
							 temp = (Double)value - amount;
						}
						
						else if(type == ETable.MULTIPLY){
							 temp = (Double)value * amount;
						}
						
						else if(type == ETable.SET){
							 temp = amount;
						}
						
						else if(type == ETable.SMOOTH){
							temp = smoothData[j - colStart][i - rowStart];
						}
						
						//theModel.setValueAt(temp, i, j);
						theModel.setDoubleData(i,j,temp);
						this.setSelected(i, j);
					}
				}
			}
		}
		
		// Have we actually changed anything?
		// If not, that means we must work from a pre-selected set
		if(counter > 0){
			this.hasDataChanged = true;
			
		}
		
		if(counter == 0){
			Iterator tempIterate = this.tempSelectedCells.iterator();
			while(tempIterate.hasNext()){
				Coordinate tempCoord = (Coordinate)tempIterate.next();
				
				Object value = theModel.getValueAt(tempCoord.getX(), tempCoord.getY());
				
				if(value instanceof Double){
					double temp = 0.0;
					if(type == ETable.INCREMENT){
						 temp = (Double)value + amount;
					}
					
					else if(type == ETable.DECREMENT){
						 temp = (Double)value - amount;
					}
					
					else if(type == ETable.MULTIPLY){
						 temp = (Double)value * amount;
					}
					
					else if(type == ETable.SET){
						 temp = amount;
					}
					
					theModel.setDoubleData(tempCoord.getX(),tempCoord.getY(),temp);
				}
				//logger.info("ETable Data has changed.");
				this.hasDataChanged = true;
				
			}
		}else{
			this.tempSelectedCells = tempCells;
		}
		
		// Used to save a state snapshot
		if(this.hasDataChanged){
			// Copy changed data into the state vector
			this.saveState(this.theModel.getDoubleData());
			//this.savedStates.push(this.theModel.getDoubleData());
			//logger.info("ETable saving a state.");
		}
		
		theModel.refresh();
		
	}
	
	public void saveState(Double[][] data){
		logger.debug("Saving a state,  size : "+this.savedStates.size());
		this.savedStates.push(data);
	}
	
	/**
	 * Replace all table data with passed data.
	 * @param newData
	 */
	public void replaceAlltableData(Double[][] newData){
		((ETableModel)this.dataModel).replaceData(newData);
		this.setHasDataChanged(true);
	}

	public void refresh(){
		logger.debug("Refresh");
		
		((ETableModel)this.dataModel).refresh();
		this.updateUI();
		this.validate();
		this.repaint();
	}

	public ETableModel getTheModel() {
		return theModel;
	}
	
	private class Coordinate{
		private int x;
		private int y;
		
		public Coordinate(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public int getX(){
			return this.x;
		}
		
		public int getY(){
			return this.y;
		}
	}

	public TableMetaData getTableMetaData() {
		return tableMetaData;
	}
	
	public boolean getHasDataChanged(){
		return this.hasDataChanged;
	}
	
	public void setHasDataChanged(boolean newValue){
		this.hasDataChanged = newValue;
		//this.saveState(this.theModel.getDoubleData());
		this.refresh();
	}
	
	// Called by the cell renderer to notify that the table is selected and in scope
	public void isSelectedETable(){
		this.dataJPanel.setCurrentETableInScope(this);
	}
	
	// Clear out all selected cells
	public void setNoCellsSelected(){
		this.clearSelection();
	}
	
	/**
	 * Returns the current set of data that this ETable is dealing with.
	 * @return
	 */
	public Object[][] getData(){
		return this.theModel.getData();
	}
	
	
	public void scaleYAxis(int rowIndex, double newRowValue, Double[] rowValues){
		logger.info("ETable Scaling in Y Axis Mode");
		Object[][] data = this.getData();
		
    	Double[][] doubleData = new Double[data.length][data[0].length];
    	for(int i = 0; i < data.length; i++){
    		for(int j = 0; j < data[0].length; j++){
    			doubleData[i][j] = (Double)data[i][j];
    			//logger.info("... :"+doubleData[i][j]);
    		}
    		//logger.info("----");
    	}
    	
		Double[][] newData = FitData.getYAxisScale(doubleData, rowIndex, newRowValue, rowValues);
    	
		for(int i = 0; i < newData.length; i++){
    		for(int j = 0; j < newData[0].length; j++){
    			//logger.info("... :"+newData[i][j]);
    		}
    		//logger.info("----");
    	}
    	
		if(newData ==  null){
			logger.info("NULL");
		}
		this.replaceAlltableData(newData);
	}
	
	public void scaleXAxis(int colIndex, double newColValue, Double[] columnValues){
		logger.info("ETable Scaling in X Axis Mode");
		Object[][] data = this.getData();
		
    	Double[][] doubleData = new Double[data.length][data[0].length];
    	for(int i = 0; i < data.length; i++){
    		for(int j = 0; j < data[0].length; j++){
    			doubleData[i][j] = (Double)data[i][j];
    			//logger.info("... :"+doubleData[i][j]);
    		}
    		//logger.info("----");
    	}
    	
		Double[][] newData = FitData.getXAxisScale(doubleData, colIndex, newColValue, columnValues);
    	
		for(int i = 0; i < newData.length; i++){
    		for(int j = 0; j < newData[0].length; j++){
    			//logger.info("... :"+newData[i][j]);
    		}
    		//logger.info("----");
    	}
    	
		if(newData ==  null){
			//logger.info("NULL");
		}
		this.replaceAlltableData(newData);
	}
}
