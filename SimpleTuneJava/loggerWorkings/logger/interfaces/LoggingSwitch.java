package logger.interfaces;

public interface LoggingSwitch {
	
	public boolean isEnabled();
	public void setEnabled(boolean enabled);
	
	public String getDescription();
	public void setDescription(String description);
	
	public String getName();
	public void setName(String name);
	
	public String getID();
	public void setID(String ID);
}
