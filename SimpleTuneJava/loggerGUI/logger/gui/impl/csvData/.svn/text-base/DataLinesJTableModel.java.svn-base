package logger.gui.impl.csvData;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.interfaces.LoggingAttribute;

public class DataLinesJTableModel extends AbstractTableModel{
	
	private Log logger = LogFactory.getLog(getClass());
	
	//private LinkedList<String> columnNames = new LinkedList<String>();
	private LinkedList<LinkedList<Object>> tableValues = new LinkedList<LinkedList<Object>>();
	private LinkedList<LoggingAttribute> attributes = new LinkedList<LoggingAttribute>();
	
	private DecimalFormat formatter;
	
	public DataLinesJTableModel(){
		this.formatter = new DecimalFormat("##.##");
	}
	
	public int getColumnCount() {
		return this.attributes.size();
	}
	
    public String getColumnName(int col) {
    	LoggingAttribute loggingAttribute = attributes.get(col);
        return loggingAttribute.getName();
    }

	public int getRowCount() {
		return this.tableValues.size();
	}

	public Object getValueAt(int row, int col) {
    	LinkedList<Object> rowValue = tableValues.get(row);
    	
    	// Is a value being asked for outside the bounds of the ROW?
    	if(!(col < rowValue.size())){
    		return " -- ";
    	}
    	
    	// Is this a number?
    	Object temp = rowValue.get(col);
    	if(temp instanceof Double){
    		
    		if(Double.isNaN((Double)temp)){
    			return " -- ";
    		}
    		
    	   	String output = formatter.format((Double)temp);
    		
    		return Double.parseDouble(output);
    	}
 
    	return " -- ";
	}
	
	public void newDataAvailable(LinkedList<LoggingAttribute> attributesUpdated) {
		System.out.println("CSV: new data available count :"+attributesUpdated.size());
		
		// Initialize new row of data
		// Be stupid and just put 100 elements in it
		LinkedList<Object> newRow = new LinkedList<Object>();
		for(int i = 0; i < 100; i ++){
			newRow.add(" -- ");
		}
		
		// Iterate through the list of returned values
		boolean newColAdded = false;
		Iterator<LoggingAttribute> iterator = attributesUpdated.iterator();
		while(iterator.hasNext()){
			LoggingAttribute next = iterator.next();
			
			// New attribute, add to list
			if(!this.attributes.contains(next)){
				
				// Add to master list
				this.attributes.add(next);
				
				// Mark this addition
				newColAdded = true;
			}
			
			// Get index of attribute
			int indexOf = this.attributes.indexOf(next);
			
			// Put attribute value into proper row position
			System.out.println("CSV: add row value :"+next.getName() +"   value:"+next.getValue());
			newRow.add(indexOf, next.getValue());
		}
		
		// Put new row in table data
		this.tableValues.add(newRow);
		
		
		// Refresh only when needed
		if(newColAdded){
			
			// Maybe we added a column and changed the table structure
			this.fireTableStructureChanged();
		}

		
		// We added a new row of data for sure though
		this.fireTableDataChanged();
	}
}
