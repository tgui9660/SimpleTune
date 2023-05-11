package logger.gui.impl.rawData;


import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.interfaces.LoggingAttribute;

class RawDataTableModel extends AbstractTableModel {
	
	private Log logger = LogFactory.getLog(getClass());
	
	boolean DEBUG = false;
	private LinkedList<Object> tableValues = new LinkedList<Object>();
	private DecimalFormat formatter;
	
	public RawDataTableModel(){
		this.formatter = new DecimalFormat("##.##");
	}
    private String[] columnNames = {"Name", "Units", "MIN", "Current", "MAX"};
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
    	// System.out.println("Row Count :"+this.tableValues.size());
    	return tableValues.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
    	LinkedList<Object> object = (LinkedList<Object>) tableValues.get(row);
    	
    	Object temp = object.get(col+1);
    	if(temp instanceof Double){
    		
    		if(Double.isNaN((Double)temp)){
    			return " -- ";
    		}
    		
    		String output = formatter.format((Double)temp);
    		
    		return Double.parseDouble(output);
    		
    	}
    	return temp;
    }

    
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void addAttribute(LoggingAttribute attribute) {
		attribute.setActive(true);
		
		Iterator<Object> iterator = this.tableValues.iterator();
		boolean found = false;
		LinkedList<Object> rowValues = null;
		
		while(iterator.hasNext()){

			Object next = iterator.next();
			rowValues = (LinkedList<Object>)next;
			LoggingAttribute tableAttribute = (LoggingAttribute)rowValues.get(0);
			
			if(tableAttribute == attribute){
				found = true;
				break;
			}
			
		}
		
		if(!found){
			LinkedList<Object> newLine = new LinkedList<Object>();
			newLine.add(attribute);
			newLine.add(attribute.getName());
			newLine.add(attribute.getUnits());
			newLine.add(" -- ");
			newLine.add(" -- ");
			newLine.add(" -- ");
			
			this.tableValues.add(newLine);
			this.fireTableRowsInserted(0,0);
		}
		
	}
	
	public void removeAttribute(LoggingAttribute attribute) {
		attribute.setActive(false);
		
		Iterator<Object> iterator = this.tableValues.iterator();
		int counter = 0;
		LoggingAttribute object = null;
		
		while(iterator.hasNext()){
			LinkedList<Object> next = (LinkedList<Object>)iterator.next();
			object = (LoggingAttribute)next.get(0);
			
			if(object == attribute){
				break;
			}
			
			counter++;
		}

		iterator = null;
		
		/*
		System.out.println("Table size: "+this.tableValues.size());
		System.out.println(" -- counter: "+counter);
		*/
		
		// Catch array index issues
		if(this.tableValues.size() == counter){
			return;
		}
		if(counter > 0 || counter < this.tableValues.size()){
			this.tableValues.remove(counter);
			this.fireTableDataChanged();
		}

	}
	
	public void updateAttribute(Double newData, LoggingAttribute loggingAttribute){
		System.out.println("Updating :"+ loggingAttribute.getName());
		
		Iterator<Object> iterator = this.tableValues.iterator();
		int counter = 0;
		LinkedList<Object> rowValues = null;
		boolean found = false;
		
		while(iterator.hasNext()){

			Object next = iterator.next();
			rowValues = (LinkedList<Object>)next;
			if(rowValues == null){
				//System.out.println("NULL data found!!!");
			}
			
			LoggingAttribute tableAttribute = (LoggingAttribute)rowValues.get(0);
			//System.out.println(" --------- found name: "+tableAttribute.getName());
			
			if(tableAttribute == loggingAttribute){
				//System.out.println("Found logging attribute   counter:"+counter);
				found = true;
				break;
			}
			
			counter++;
		}
		
		iterator = null;

		if(found){
			//System.out.println("RowData type: "+rowValues.get(3).getClass());
			
			if(rowValues.get(3) instanceof Double){
				Double min = (Double)rowValues.get(3);
				Double max = (Double)rowValues.get(5);
				
				if(newData < min){
					min = newData;
				}
				
				if(newData > max){
					max = newData;
				}
				
				rowValues.set(3, min);
				rowValues.set(5, max);
			}else{
				rowValues.set(3, newData);
				rowValues.set(5, newData);
			}
			
			rowValues.set(4, newData);
			this.tableValues.set(counter, rowValues);
			this.fireTableDataChanged();	
		}
	}
	
	public void updateAttributeUnits(LoggingAttribute loggingAttribute){
		System.out.println("Updating :"+ loggingAttribute.getName());
		
		Iterator<Object> iterator = this.tableValues.iterator();
		int counter = 0;
		LinkedList<Object> rowValues = null;
		boolean found = false;
		
		while(iterator.hasNext()){

			Object next = iterator.next();
			rowValues = (LinkedList<Object>)next;
			if(rowValues == null){
				//System.out.println("NULL data found!!!");
			}
			
			LoggingAttribute tableAttribute = (LoggingAttribute)rowValues.get(0);
			//System.out.println(" --------- found name: "+tableAttribute.getName());
			
			if(tableAttribute == loggingAttribute){
				//System.out.println("Found logging attribute   counter:"+counter);
				found = true;
				break;
			}
			
			counter++;
		}
		
		iterator = null;

		if(found){
			
			rowValues.set(3, " -- ");
			rowValues.set(4, " -- ");
			rowValues.set(5, " -- ");
			
			rowValues.set(2, loggingAttribute.getUnits());
			
			this.tableValues.set(counter, rowValues);
			this.fireTableDataChanged();	
		}
	}
	
	public void updateAttributesData(LinkedList<LoggingAttribute> loggingAttributes){
		Iterator<LoggingAttribute> iterator = loggingAttributes.iterator();
		while(iterator.hasNext()){
			LoggingAttribute next = iterator.next();
			this.updateAttributeData(next);
		}
		this.fireTableDataChanged();
	}
	
	private void updateAttributeData(LoggingAttribute loggingAttribute){
		System.out.println("Updating :"+ loggingAttribute.getName());
		double newData = loggingAttribute.getValue();
		
		Iterator<Object> iterator = this.tableValues.iterator();
		int counter = 0;
		LinkedList<Object> rowValues = null;
		boolean found = false;
		
		while(iterator.hasNext()){

			Object next = iterator.next();
			rowValues = (LinkedList<Object>)next;
			if(rowValues == null){
				//System.out.println("NULL data found!!!");
			}
			
			LoggingAttribute tableAttribute = (LoggingAttribute)rowValues.get(0);
			//System.out.println(" --------- found name: "+tableAttribute.getName());
			
			if(tableAttribute == loggingAttribute){
				//System.out.println("Found logging attribute   counter:"+counter);
				found = true;
				break;
			}
			
			counter++;
		}
		
		iterator = null;

		if(found){
			//System.out.println("RowData type: "+rowValues.get(3).getClass());
			
			if(rowValues.get(3) instanceof Double){
				Double min = (Double)rowValues.get(3);
				Double max = (Double)rowValues.get(5);
				
				if(newData < min){
					min = newData;
				}
				
				if(newData > max){
					max = newData;
				}
				
				rowValues.set(3, min);
				rowValues.set(5, max);
			}else{
				rowValues.set(3, newData);
				rowValues.set(5, newData);
			}
			
			rowValues.set(4, newData);
			
			// Units can change based on combo box selection
			//rowValues.set(2, loggingAttribute.getUnits());
			
			this.tableValues.set(counter, rowValues);
			//this.fireTableDataChanged();	
		}
	}
	
	public void resetData(){
		System.out.println("RawDataTableMode: Resetting data");
		int count = this.tableValues.size();
		
		// Reset the GUI first, to be sure
		for(int i = 0; i < count; i++){
			Object object = this.tableValues.get(i);
			LinkedList<Object> rowValue = (LinkedList<Object>)object;
			rowValue.set(3," -- ");
			rowValue.set(4," -- ");
			rowValue.set(5," -- ");
			
			this.tableValues.set(i, rowValue);
		}
		
		Iterator<Object> iterator = this.tableValues.iterator();
		int counter = 0;
		LinkedList<Object> rowValues = null;
		boolean found = false;
		
		while(iterator.hasNext()){

			Object next = iterator.next();
			rowValues = (LinkedList<Object>)next;
			if(rowValues == null){
				//System.out.println("NULL data found!!!");
			}
			
			LoggingAttribute attr = (LoggingAttribute)rowValues.get(0);
			attr.setMin(10000.0);
			attr.setMax(-10000.0);
			//attr.setValue(0.0);
		}
		
		this.fireTableDataChanged();
	}
	
	public void clearTable() {
		this.tableValues.clear();
		this.fireTableDataChanged();
	}
	
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {   
    	if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }
	
        
    	LinkedList object = (LinkedList) tableValues.get(row);
		//Object object2 = object.get(col);
		
		object.set(col+1, value);
		this.tableValues.set(row, object);
		
        //data[row][col] = value;
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            //System.out.println("New value of data:");
            printDebugData();
        }
    }
	
	
    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                //System.out.print("  " + data[i][j]);
            	System.out.print("  " + this.getValueAt(i, j));
            	
            }
            System.out.println();
        }
      System.out.println("--------------------------");
    }
}
