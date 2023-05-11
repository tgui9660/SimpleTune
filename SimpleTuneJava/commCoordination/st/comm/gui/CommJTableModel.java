package st.comm.gui;

import javax.swing.table.AbstractTableModel;

import logger.dataManager.LoggerManager;

public class CommJTableModel extends AbstractTableModel{
	private String[] dataSupplierNameList = LoggerManager.getInstance().getDataSupplierNameList();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
