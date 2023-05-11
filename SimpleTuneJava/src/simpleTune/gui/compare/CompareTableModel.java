package simpleTune.gui.compare;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

class CompareTableModel extends AbstractTableModel {
	boolean DEBUG = true;
	private Vector tableValues = new Vector();
    private String[] columnNames = {"Copy", "Dimensionally Equivalent Tables With Different Data"};
    
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
        Vector object = (Vector) tableValues.get(row);
		return object.get(col);
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

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public void selectAll(){
    	Iterator iterator = this.tableValues.iterator();
    	while(iterator.hasNext()){
    		((Vector)iterator.next()).set(0, new Boolean(true));
    	}
    	this.fireTableDataChanged();
    }
    
    public Vector<String> getChangeTables(){
    	Vector<String> vector = new Vector<String>();
    	
    	Iterator iterator = this.tableValues.iterator();
    	while(iterator.hasNext()){
    		Vector next = (Vector)iterator.next();
    		Boolean value = (Boolean)next.get(0);
    		if(value){
    			String path = (String)next.get(1);
    			vector.add(path);
    		}
    	}
    	
    	return vector;
    }

    public String getComparePath(String tableName){
    	Iterator iterator = this.tableValues.iterator();
    	while(iterator.hasNext()){
    		Vector next = (Vector)iterator.next();
    		
    		if(((String)next.get(1)).equals(tableName)){
    			return (String)next.get(3);
    		}
    	}
    	return null;
    }
    
    public String getBasePath(String tableName){
    	Iterator iterator = this.tableValues.iterator();
    	while(iterator.hasNext()){
    		Vector next = (Vector)iterator.next();
    		
    		if(((String)next.get(1)).equals(tableName)){
    			return (String)next.get(2);
    		}
    	}
    	return null;
    }
    
	public boolean isCellEditable(int row, int column) {
		if (column == 0) {
			return true;
		}
		return false;
	}

	public void addRow(String tableName, String path1, String path2) {
		Vector newLine = new Vector();
		newLine.add(new Boolean(false));
		newLine.add(tableName);
		newLine.add(path1); // Used later
		newLine.add(path2); // Used later
		
		this.tableValues.add(newLine);
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
       /*
    	if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }
	*/
        
    	Vector object = (Vector) tableValues.get(row);
		//Object object2 = object.get(col);
		
		object.set(col, value);
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
            //System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                //System.out.print("  " + data[i][j]);
            	//System.out.print("  " + this.getValueAt(i, j));
            	
            }
            //System.out.println();
        }
        //System.out.println("--------------------------");
    }
}
