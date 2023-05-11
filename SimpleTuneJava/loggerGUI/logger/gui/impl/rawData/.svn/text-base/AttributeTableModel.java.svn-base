package logger.gui.impl.rawData;


import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.interfaces.LoggingAttribute;

class AttributeTableModel extends AbstractTableModel {
	
	private Log logger = LogFactory.getLog(getClass());
	
	boolean DEBUG = false;
	private List<Object> tableValues = new Vector<Object>();
	private String[] columnNames = {"Select", "Parameters", "Units"};
    private RawDataJTable dataJTable = null;
	
	public void setDataJTable(RawDataJTable dataJTable){
		this.dataJTable = dataJTable;
	}
	
	public AttributeTableModel(){
		
	}
    
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
    	return object.get(col + 1);
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
	public boolean isCellEditable(int row, int column) {
		if (column == 0 || column == 2) {
			return true;
		}
		return false;
	}
	
	public Vector<String> getConversions(int row){
		LinkedList<Object> object = (LinkedList<Object>) tableValues.get(row);
		LoggingAttribute attr = (LoggingAttribute)object.get(0);
		HashMap<String,String> conversions = attr.getConversions();
		Vector<String> values = new Vector<String>();
		Iterator<String> iterator = conversions.keySet().iterator();
		while(iterator.hasNext()){
			String next = iterator.next();
			values.add(next);
		}
		return values;
	}

	public LoggingAttribute getLoggingAttribute(int row){
		LinkedList<Object> object = (LinkedList<Object>) tableValues.get(row);
		return (LoggingAttribute)object.get(0);
	}
	
	private void addAttribute(LoggingAttribute attribute) {
		LinkedList<Object> newLine = new LinkedList<Object>();
		newLine.add(attribute);
		newLine.add(new Boolean(false));
		newLine.add(attribute.getName());
		newLine.add(attribute.getUnits());	
		
		this.tableValues.add(newLine);

		//this.fireTableRowsInserted(0,0);
	}
	
	public void addAttributes(LinkedList<LoggingAttribute> loggingAttribute) {
		
		Iterator<LoggingAttribute> iterator = loggingAttribute.iterator();
		while(iterator.hasNext()){
			LoggingAttribute next = iterator.next();
			this.addAttribute(next);
		}
		
		Collections.sort(this.tableValues, new AttributeTableModelSortComparator());

		this.fireTableRowsInserted(0,0);
	}
	
	public void removeAttributes(LinkedList<LoggingAttribute> attrs){
		Iterator<LoggingAttribute> iterator = attrs.iterator();
		while(iterator.hasNext()){
			LoggingAttribute next = iterator.next();
			this.removeAttribute(next);
		}
		this.fireTableDataChanged();
	}
	
	private void removeAttribute(LoggingAttribute attr){
		Iterator<Object> iterator = this.tableValues.iterator();
		int index = 0;
		while(iterator.hasNext()){
			LinkedList<Object> next = (LinkedList<Object>)iterator.next();
			LoggingAttribute found = (LoggingAttribute)next.get(0);
			if(attr == found){
				break;
			}
			index++;
		}
		this.tableValues.remove(index);
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
	
        
    	LinkedList<Object> object = (LinkedList<Object>) tableValues.get(row);
		object.set(col+1, value);
		this.tableValues.set(row, object);
        fireTableCellUpdated(row, col);
        
        if(value instanceof Boolean && ((Boolean)value) == false){
        	//System.out.println("Turn OFF row: "+ row);
        	
        	this.dataJTable.removeAttribute((LoggingAttribute)object.get(0));
        }
        if(value instanceof Boolean && ((Boolean)value) == true){
        	//System.out.println("Turn ON row: "+ row);
        	
        	this.dataJTable.addAttribute((LoggingAttribute)object.get(0));
        }
        
        
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
