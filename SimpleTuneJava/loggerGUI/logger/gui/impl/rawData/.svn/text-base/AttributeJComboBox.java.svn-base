package logger.gui.impl.rawData;

import java.util.Vector;

import javax.swing.JComboBox;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.interfaces.LoggingAttribute;

public class AttributeJComboBox extends JComboBox{
	
	private Log logger = LogFactory.getLog(getClass());
	

	private LoggingAttribute attr = null;
	
	public AttributeJComboBox(Vector<String> values){
		super(values);
	}
	
	public void setLoggingAttribute(LoggingAttribute attr){
		this.attr = attr;
	}
	
	public LoggingAttribute getLoggingAttribute(){
		return this.attr;
	}
}
