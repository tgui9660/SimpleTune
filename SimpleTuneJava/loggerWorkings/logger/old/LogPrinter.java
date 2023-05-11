package logger.old;

import logger.interfaces.AttributeListener;
import logger.interfaces.LoggingAttribute;

public class LogPrinter implements AttributeListener{

	private static LogPrinter instance = null;
	
	private LogPrinter(){
		
	}
	
	public static LogPrinter getInstance(){
		if(LogPrinter.instance == null){
			LogPrinter.instance = new LogPrinter();
		}
		
		return LogPrinter.instance;
	}
	
	public void newData(LoggingAttribute loggingAttribute, Double newData) {
		// TODO Auto-generated method stub
		
	}

}
