package com.ecm.graphics.exampleCode;

public interface LoggerInterface {
	//A place to register to listen for new logger information
	public void addLoggerListener(LoggerListener loggerListener, String loggerName);
	
	//Names of available loggers (AFR, KNOCK, Accel, etc), same name as used in above method.
	public String[] getNames();
	
	//Axes name values
	public String getXName(); //Left - right
	public String getYName(); //Up - down
	public String getZName(); //Into monitor
	
	//Logger dimension information
	public double getMaxXValue();
	public double getMaxYValue();
	public double getMaxZValue();
	
	//Logger state commands
	public void initLogger();
	public void stopLogger();
	public void startLogger();
	public void pauseLogger();
	public void resetLogger();
	
}
