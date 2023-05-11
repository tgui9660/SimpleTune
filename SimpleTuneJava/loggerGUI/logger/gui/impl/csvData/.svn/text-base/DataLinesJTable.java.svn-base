package logger.gui.impl.csvData;

import java.util.LinkedList;

import javax.swing.JTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.interfaces.LoggingAttribute;

public class DataLinesJTable extends JTable{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private DataLinesJTableModel model = new DataLinesJTableModel();
	
	public DataLinesJTable(){
		this.setModel(this.model);
	}
	
	public void newDataAvailable(LinkedList<LoggingAttribute> attributesUpdated) {
		this.model.newDataAvailable(attributesUpdated);
	}
}
