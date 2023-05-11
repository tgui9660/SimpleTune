package st.comm.gui;

import javax.swing.JPanel;

import logger.dataManager.LoggerManager;

public class CommJPanel extends JPanel{

	public CommJPanel(){
		String[] dataSupplierNameList = LoggerManager.getInstance().getDataSupplierNameList();
		
	}
}
