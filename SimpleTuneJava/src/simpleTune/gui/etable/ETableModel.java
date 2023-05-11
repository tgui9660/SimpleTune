package simpleTune.gui.etable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.TableMetaData;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.Locale;

public class ETableModel extends AbstractTableModel {
	private Log logger = LogFactory.getLog(getClass());
    private Object[][] data;
    private TableMetaData tableMetaData;
    private DecimalFormat formatter;
    private ETable parentETable;
    
    public ETableModel(TableMetaData metaData, Object[][] initialData, ETable parentETable) {
        this.tableMetaData = metaData;
        this.data = initialData;
        this.parentETable = parentETable;
        
        // Set formatter local
        Locale.setDefault(Locale.US);
        
        // If this is a main data table, apply format
        if(this.tableMetaData.getDataFormat() == null){
        	this.formatter = new DecimalFormat("#");
        }else{
        	this.formatter = new DecimalFormat(this.tableMetaData.getDataFormat());
        }
        
        // If this is an axis table, then apply the appropriate format
        if(this.parentETable.isXAxis){
        	logger.info("Is X AXIS");
        	logger.info("AXIS format: " + this.tableMetaData.getXAxisFormat());
        	String axisFormat = this.tableMetaData.getXAxisFormat();
        	if(axisFormat != null){
        		this.formatter = new DecimalFormat(axisFormat);
        	}
        }
        if(this.parentETable.isYAxis){
        	logger.info("Is Y AXIS");
        	logger.info("AXIS format: " + this.tableMetaData.getYAxisFormat());
        	String axisFormat = this.tableMetaData.getYAxisFormat();
        	if(axisFormat != null){
        		this.formatter = new DecimalFormat(axisFormat);
        	}
        }
    }

    public int getColumnCount() {
    	return this.data[0].length;
    }

    public int getRowCount() {
    	return this.data.length;
    }

    public Object getValueAt(int row, int col) {
    	if(data[row][col] instanceof Double || data[row][col] instanceof Integer ||data[row][col] instanceof Float){
    		
    		String output = formatter.format(data[row][col]);
            
    		if(Double.isNaN((Double)data[row][col])){
    			return -9999;
    		}
    		
    		//logger.info(data[row][col] + " : "+output+":");
    		
        	return Double.parseDouble(output);
    	}
    	
    	// Else this must be a string value, no formatting of numbers obviously needed
    	return data[row][col];
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public Double[][] getDoubleData(){
    	Double[][] doubleData = new Double[data.length][data[0].length];
    	for(int i = 0; i < data.length; i++){
    		for(int j = 0; j < data[0].length; j++){
    			if(!(data[i][j] instanceof String)){
    				doubleData[i][j] = (Double)data[i][j];
    			}else{
    				doubleData[i][j] = -1.0;
    			}
    		}
    	}
    	
    	return doubleData;
    }

    /**
     * Called when a user double clicks on a table to edit a cell
     * 
     * Right now, only entry mechanism to kick off data i
     */
    public void setValueAt(Object value, int row, int col) {
    	logger.info("Setting value :"+value+"  row:"+row+"   col:"+col);
    	Double[][] oldData = new Double[data.length][data[0].length];
    	// Save off the old data for possible future use
    	for(int i = 0; i < data.length; i++){
    		for(int j = 0; j < data[0].length; j++){
    			if(!(data[i][j] instanceof String)){
    				oldData[i][j] = (Double)data[i][j];
    			}else{
    				oldData[i][j] = -1.0;
    			}
    		}
    	}

    	
    	// Insert in the new data
        if (value instanceof String) {
            try {
                double temp = Double.parseDouble((String) value);
                data[row][col] = temp;
            } catch (NumberFormatException e) {
            }
        } else if (value instanceof Double) {
            String tempString = formatter.format(value);
            Double parsedValue = Double.parseDouble(tempString);
            
            // If the data really hasn't changed, then return.
            if(parsedValue == data[row][col]){
            	return;
            }
            
            //data[row][col] = parsedValue;
            data[row][col] = value;
        }

        // Detect scale mode setting
    	if((this.parentETable.isXAxis || this.parentETable.isYAxis ) && this.parentETable.dataJPanel.getIsScaleActive() && !(data[row][col] instanceof String)){
    		logger.info("ETableModel value changed thats pertinent to scale mode.");
    		
    		ETable mainETable = this.parentETable.dataJPanel.getMainETable();
    		
    		if(this.parentETable.isXAxis){
    			Double[] colData = new Double[data[row].length];
    			
    			//logger.info("ETableMode col data:");
    			for(int i = 0; i < data[row].length; i++){
    				colData[i] = (Double)oldData[row][i];
    				//logger.info(" --- :"+colData[i]);
    			}
    			
    			mainETable.scaleXAxis(col, (Double)data[row][col], colData);
    		}
    		
    		if(this.parentETable.isYAxis){
    			Double[] rowData = new Double[data.length];
    			
    			//logger.info("ETableMode row data:");
    			for(int i = 0; i < data.length; i++){
    				rowData[i] = (Double)oldData[i][col];
    				//logger.info(" --- :"+rowData[i]);
    			}
    			
    			mainETable.scaleYAxis(row, (Double)data[row][col], rowData);
    		}
    	}
        
        this.parentETable.setHasDataChanged(true);
        //this.parentETable.saveState(this.getDoubleData());
        this.fireTableDataChanged();
    }

    public void setDoubleData(int row, int col, double value) {
        //String tempString = formatter.format(value);
        //this.data[row][col] = Double.parseDouble(tempString);
    	this.data[row][col] = value;
    }


    public void replaceData(Double[][] newData) {

    	logger.debug("Replacing Data!");
    	
        int width = newData.length;
        int height = newData[0].length;

        //logger.debug("---------------------");
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //double tempData = data[i][j];
                //String tempString = formatter.format(tempData);
                //this.data[i][j] = Double.parseDouble(tempString);
            	//this.data[i][j] = newData[i][j];
            	this.setDoubleData(i, j, newData[i][j]);
            	//System.out.print(newData[i][j] + ", ");
            }
            //System.out.println(" ");
        }
        //logger.debug("---------------------");
        //this.refresh();
        
    	
    	
        //this.copyData(newData);
        
        
        //this.data = newData;
        
        this.fireTableDataChanged();
        
        // This is bad to use as it resizes the inner JTable
        //this.fireTableStructureChanged();
        
        //this.fireAllChangedIterative();
    }
    
    private void fireAllChangedIterative(){
        int width = data.length;
        int height = data[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.fireTableCellUpdated(i, j);
            }
        }
    }
    
    /**
     * ARG Why????
     * <p/>
     * Seem to be getting some pass by reference issues.
     * @param data
     */
    /*
    private void copyData(Double[][] data) {
        int width = data.length;
        int height = data[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //double tempData = data[i][j];
                //String tempString = formatter.format(tempData);
                //this.data[i][j] = Double.parseDouble(tempString);
            	//this.data[i][j] = data[i][j];
            	this.setDoubleData(i, j, data[i][j]);
            }
        }
    }
	*/
    
    public Object[][] getData() {
        return data;
    }

    public void refresh() {
    	//logger.info("Refreshing tableModel");
        this.fireTableDataChanged();
	}
	
}