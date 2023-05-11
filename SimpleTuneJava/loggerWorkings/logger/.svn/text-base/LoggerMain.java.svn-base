package logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import st.comm.CommAccessManager;
import logger.dataManager.LoggerManager;
import logger.dataSupplier.impl.test.TestDataSupplier;
import logger.impl.commandLine.CommandLineLogger;

public class LoggerMain{
	private Log logger = LogFactory.getLog(getClass());
	public static void main(String[] args) {
		
		// Init the comm access manager
		CommAccessManager.getInstance().init();
	
		
		// Register data suppliers
		LoggerManager.getInstance().registerDataSupplier(new TestDataSupplier());
		
		
		// Register data source listeners
		LoggerManager.getInstance().registerDataSupplierListener(new CommandLineLogger());
		
	}
}
