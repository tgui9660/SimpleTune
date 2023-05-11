package logger.gui.control;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import st.comm.CommAccessManager;

import logger.dataManager.LoggerManager;
import logger.dataSupplier.impl.ssm.SSMDataSupplier;
import logger.gui.impl.csvData.CSVDataConsumer;
import logger.gui.impl.rawData.AttributeJTable;
import logger.gui.impl.rawData.RawDataJTable;
import logger.gui.impl.rawData.SwitchJTable;
import logger.gui.impl.rawData.TableGUIDataConsumer;
import logger.gui.interfaces.DataConsumerGUI;

/**
 * Used by external classes to access logger parts.
 * 
 * At this point, only SimpleTuneGUI and test logger classes
 * access this class.
 * 
 * I don't like this class, working on abstract classes to automatically handle registration with 
 * various parts of the system
 * 
 * @author emorgan
 *
 */
public class LoggerGUIManager {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private static LoggerGUIManager loggerManager = null;
	private RawDataJTable dataJTable = new RawDataJTable();
	private AttributeJTable attributeJTable = new AttributeJTable();
	private SwitchJTable switchJTable = new SwitchJTable();
	
	public RawDataJTable getDataJTable() {
		return dataJTable;
	}

	public AttributeJTable getAttributeJTable() {
		return attributeJTable;
	}

	/**
	 * Singleton code.
	 * @return
	 */
	public static LoggerGUIManager getInstance(){
		if(LoggerGUIManager.loggerManager == null){
			LoggerGUIManager.loggerManager = new LoggerGUIManager();
		}
		
		return LoggerGUIManager.loggerManager;
	}
	private LoggerGUIManager(){
		init();
	}
	
	/**
	 * Setup and tie all parts together.
	 * 
	 */
	private void init(){
		// ***************************************************
		// Tie attribute table and the raw data table together
		// as attribute table calls raw data table to display
		// selected logging attributes (psi/rpm/etc)
		// ***************************************************
		this.attributeJTable.setDataJTables(this.dataJTable);
		
		
		// ****************************
		// Init the comm access manager
		// ****************************
		CommAccessManager.getInstance().init();
		
		
		// ***********************
		// Register data suppliers
		// ***********************
		// - Test supplier
		//LoggerManager.getInstance().registerDataSupplier(new TestDataSupplier());
		LoggerManager.getInstance().registerDataSupplier(SSMDataSupplier.getInstance());
		
		
		// *********************************
		// Add logger gui data consumers
		// Register here
		// *********************************
		// Raw Data GUI, and then its tie back to its specific controlling class.
		TableGUIDataConsumer tableGUIDataConsumer = new TableGUIDataConsumer(DataConsumerGUIController.getInstance(), this.attributeJTable, this.dataJTable, this.switchJTable);
		DataConsumerGUIController.getInstance().addDataConsumer(tableGUIDataConsumer);

		CSVDataConsumer csvConsumer = new CSVDataConsumer();
		//DataConsumerGUIController.getInstance().addDataConsumer(csvConsumer);
		
		// ******************************
		// Register data source listeners
		// ******************************
		// - GUI Listener
		// registerDataSupplierListener to soley register
		//LoggerManager.getInstance().registerDataSupplierListener(csvConsumer);
		LoggerManager.getInstance().registerDataSupplierListener(this.attributeJTable);
		LoggerManager.getInstance().registerDataSupplierSwitchListener(this.switchJTable);
		
	}
	
	public Vector<DataConsumerGUI> getDataConsumerGUIs(){
		return DataConsumerGUIController.getInstance().getAllConsumers();
	}
}
