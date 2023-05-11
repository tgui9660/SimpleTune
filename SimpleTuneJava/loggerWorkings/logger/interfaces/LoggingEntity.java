package logger.interfaces;

import javax.swing.JMenu;

public interface LoggingEntity {
	
	/**
	 * Get all the associated logging attributes tied to this loggint entity.
	 * 
	 * @return
	 */
	public LoggingAttribute[] getLoggingAttributes();
	
	/**
	 * Returns the name of the logging entity.
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * Returns any needed specific menu items assocted with the logging entity.
	 * 
	 * @return
	 */
	public JMenu getJMenu();
	
}
