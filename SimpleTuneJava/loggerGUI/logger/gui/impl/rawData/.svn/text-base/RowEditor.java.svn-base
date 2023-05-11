package logger.gui.impl.rawData;

import java.awt.Component;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class RowEditor extends JComboBox implements TableCellEditor{
	
	private Log logger = LogFactory.getLog(getClass());
	
	String [] evenItems = {"cat","rat","bat"};
	String [] oddItems = {"dog","hog","frog"};
	boolean isOdd = true;
 
	protected transient Vector listeners;
	protected transient String ov;
	protected transient boolean editing;
 
	RowEditor()
	{
		for(int i = 0; i<oddItems.length; i++)addItem(oddItems[i]);
		listeners = new Vector();
	}
 
	public Component getTableCellEditorComponent(JTable table, Object value,
		boolean isSelected, int row, int col)
	{
		boolean odd = 1 == (row%2);
		int i;
		if(odd != isOdd)
		{
			removeAllItems();
			if(odd)for(i = 0; i<oddItems.length; i++)addItem(oddItems[i]);
			else for(i = 0; i<evenItems.length; i++)addItem(evenItems[i]);
		}
		isOdd = odd;
		return this;
	}
	public Object getCellEditorValue(){return getSelectedItem();}
 
	public boolean isCellEditable(EventObject eo){return true;}
	public boolean shouldSelectCell(EventObject eo){return true;}
	public boolean stopCellEditing()
	{
		fireEditingStopped();
		editing = false;
		return true;
	}
 
	public void cancelCellEditing()
	{
		fireEditingCanceled();
		editing = false;
	}
 
 
	public void addCellEditorListener(CellEditorListener cel)
	{listeners.addElement(cel);}
 
	public void removeCellEditorListener(CellEditorListener cel)
	{listeners.removeElement(cel);}
 
	protected void fireEditingCanceled()
	{
		ChangeEvent ce = new ChangeEvent(this);
		for(int i = listeners.size()-1; i>= 0; i--)
		{((CellEditorListener)listeners.elementAt(i)).editingCanceled(ce);}
	}
	protected void fireEditingStopped()
	{
		ChangeEvent ce = new ChangeEvent(this);
		for(int i = listeners.size()-1; i>= 0; i--)
		{((CellEditorListener)listeners.elementAt(i)).editingStopped(ce);}
	}
}

