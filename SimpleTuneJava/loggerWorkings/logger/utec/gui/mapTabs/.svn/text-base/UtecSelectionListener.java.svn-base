package logger.utec.gui.mapTabs;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtecSelectionListener implements ListSelectionListener{
	private JTable parentTable = null;
	private Log logger = LogFactory.getLog(getClass());
	public UtecSelectionListener(JTable parentTable){
		this.parentTable = parentTable;
	}
	public void valueChanged(ListSelectionEvent event) {
		//logger.info("1: "+ event.getFirstIndex()+"     2: "+event.getLastIndex());
		
		int selRow[] = parentTable.getSelectedRows();
		int selCol[] = parentTable.getSelectedColumns();
		
		
		for(int i = 0; i < selRow.length; i++){
			//logger.info("Row Value: "+selRow[i]);
		}
		
		for(int i = 0; i < selCol.length; i++){
			//logger.info("Col Value: "+selCol[i]);
		}
		
		//logger.info("---------------------------");
		Object[] selectedCells = new Object[selRow.length * selCol.length];
		
		
	}

}
