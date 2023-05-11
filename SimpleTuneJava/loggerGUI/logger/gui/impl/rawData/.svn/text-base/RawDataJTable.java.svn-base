package logger.gui.impl.rawData;

import java.util.LinkedList;

import javax.swing.JTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.interfaces.LoggingAttribute;

public class RawDataJTable extends JTable{

	private Log logger = LogFactory.getLog(getClass());

	private RawDataTableModel rawDataTable = new RawDataTableModel();

	public RawDataJTable(){
		this.setModel(this.rawDataTable);
	}

	public void addAttribute(LoggingAttribute loggingAttribute){
		this.rawDataTable.addAttribute(loggingAttribute);
	}

	public void removeAttribute(LoggingAttribute loggingAttribute){
		this.rawDataTable.removeAttribute(loggingAttribute);
	}

	public void updateAttributes(LinkedList<LoggingAttribute> attributes){
		this.rawDataTable.updateAttributesData(attributes);
	}

	public void resetData(){
		this.rawDataTable.resetData();
	}

}