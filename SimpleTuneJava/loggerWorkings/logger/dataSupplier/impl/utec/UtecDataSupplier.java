package logger.dataSupplier.impl.utec;

import java.util.LinkedList;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.dataSupplier.interfaces.DataSupplier;
import logger.dataSupplier.interfaces.DataSupplierListener;
import logger.interfaces.AttributeListener;
import logger.interfaces.LoggingAttribute;
import logger.interfaces.LoggingSwitch;
import logger.utils.Parameter;

public class UtecDataSupplier implements DataSupplier{
	
	private Log logger = LogFactory.getLog(getClass());
	

	public void setCommPort(String commPortName) {
		// TODO Auto-generated method stub
		
	}

	public void init(DataSupplierListener dataSupplierListener) {
		// TODO Auto-generated method stub
		
	}

	public String getDataSupplierName() {
		return "UtecDataSupplier";
	}

	public void relinquishCurrentCommPortNow() {
		// TODO Auto-generated method stub
		
	}

	public LinkedList<LoggingAttribute> getLoggingAttributes() {
		LoggingAttribute[] loggingAttributes = new LoggingAttribute[2];
		
		loggingAttributes[0] = new UtecKnockLoggingAttribute();
		loggingAttributes[1] = new UtecPSILoggingAttribute();
		
		return null;
	}

	public void startLogging() {
		System.out.println("UtecDataSupplier: [logging start]");
	}

	public void stopLogging() {
		// TODO Auto-generated method stub
		
	}

	public void init(Vector<Parameter> supplierParameters) {
		// TODO Auto-generated method stub
		
	}

	public Vector<LoggingAttribute> newAttributesAvailable() {
		// TODO Auto-generated method stub
		return null;
	}

	public void intializeConnections() {
		// TODO Auto-generated method stub
		
	}

	public LinkedList<LoggingSwitch> getSwitches() {
		// TODO Auto-generated method stub
		return null;
	}

}
