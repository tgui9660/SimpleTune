package logger.gui.impl.rawData;

import java.util.LinkedList;

import javax.swing.JTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.dataSupplier.interfaces.DataSupplierSwitchListener;
import logger.interfaces.LoggingSwitch;

public class SwitchJTable extends JTable implements DataSupplierSwitchListener{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private SwitchTableModel model = new SwitchTableModel();
	
	public SwitchJTable(){
		this.setModel(this.model);
		this.getColumnModel().getColumn(0).setMaxWidth(50);
	}

	public void newSwitchStateDataAvailable(LinkedList<LoggingSwitch> switchesUpdated) {
		System.out.println("Switch JTABLE new switch update received.");
	}

	public void switchesAdded(LinkedList<LoggingSwitch> loggingSwitches) {
		//System.out.println("Switch JTABLE got switch count: "+loggingSwitches.size());
		this.model.addSwitches(loggingSwitches);
	}
}
