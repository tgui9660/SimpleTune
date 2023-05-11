package logger.gui.impl.rawData;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.interfaces.LoggingSwitch;


public class SwitchTableModel extends AbstractTableModel{
	
	private Log logger = LogFactory.getLog(getClass());
	

	boolean DEBUG = false;
	private List<Object> tableValues = new Vector<Object>();
	private String[] columnNames = {"Select", "Switch"};
    private RawDataJTable dataJTable = null;
	
	public void setDataJTable(RawDataJTable dataJTable){
		this.dataJTable = dataJTable;
	}
	
	public SwitchTableModel(){
		
	}
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
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
		if (column == 0) {
			return true;
		}
		return false;
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
	
    	// Set new value in table
    	LinkedList<Object> object = (LinkedList<Object>) tableValues.get(row);
		object.set(col+1, value);
		this.tableValues.set(row, object);
        fireTableCellUpdated(row, col);
        
        // Call switch with new value
    	LoggingSwitch theSwitch = (LoggingSwitch)object.get(0);
    	theSwitch.setEnabled((Boolean)value);
    	
        // Ouput debug data if called for
        if (DEBUG) {
            printDebugData();
        }
    }
    
	private void addSwitch(LoggingSwitch newSwitch) {
		LinkedList<Object> newLine = new LinkedList<Object>();
		newLine.add(newSwitch);
		newLine.add(new Boolean(false));
		newLine.add(newSwitch.getName());
		
		this.tableValues.add(newLine);

		//this.fireTableRowsInserted(0,0);
	}
	
	public void addSwitches(LinkedList<LoggingSwitch> loggingSwitches) {
		
		Iterator<LoggingSwitch> iterator = loggingSwitches.iterator();
		while(iterator.hasNext()){
			LoggingSwitch next = iterator.next();
			this.addSwitch(next);
		}
		
		Collections.sort(this.tableValues, new AttributeTableModelSortComparator());

		this.fireTableRowsInserted(0,0);
	}
	
	/**
	 * Quick look into the tables values.
	 */
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
