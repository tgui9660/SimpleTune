package logger.gui.impl.csvData;

import javax.swing.JTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileJTable extends JTable{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private FileJTableModel model = new FileJTableModel();
	
	public FileJTable(){
		this.setModel(this.model);
	}
}
