package simpleTune.gui.etable;

import java.text.DecimalFormat;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ETableRowModel extends AbstractTableModel{
	private Log logger = LogFactory.getLog(getClass());
	
	private int length;
	private Object[] labels;
	private int counter = 0;
	private DecimalFormat formatter;
	 
	public ETableRowModel(int length, Object[] labelsSet, String labelFormat){
		this.length = length;
		this.labels = labelsSet;
		
		if(this.labels == null){
			logger.info("Null row label set found.");
		}
		
		// Perform decimal formatting if this is an instance of double
		if(this.labels != null && this.labels[0] instanceof Double){
			if(labelFormat == null || labelFormat.length() == 0){
				this.formatter = new DecimalFormat("#");
			}else{
				this.formatter = new DecimalFormat(labelFormat);
			}
		}

		
	}
	
	public int getRowCount() {
		return  length;
	}

    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	return true;
    }
    
	public int getColumnCount() {
		return length;
	}

	public Object getValueAt(int arg0, int arg1) {
		if(this.labels == null){
			return  arg0;
		}
		
		if(this.labels[0] instanceof Double){
	    	String output = formatter.format(this.labels[arg0]);
	    	return Double.parseDouble(output);
		}

		return this.labels[arg0];
	}
	
	public String getColumnName(int col){
		
		if(this.labels == null){
			return counter++ + "";
		}
		
		return this.labels[col]+"";
	}
	
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	if(this.labels[0] instanceof Double){
    		try{
        		Double changedValue = Double.parseDouble((String)aValue);
        		
        		this.labels[rowIndex] = changedValue;
        	}catch(Exception e){
        	}
    	}else{
    		this.labels[rowIndex] = aValue;
    	}
    	
    }

}
