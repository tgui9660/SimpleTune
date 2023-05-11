package logger.interfaces.impl;

import logger.interfaces.LoggingSwitch;

public class LoggingSwitchImpl implements LoggingSwitch{

	private String name = "";
	private String id = "";
	private String description = "";
	private boolean enabled = false;
	
	public String getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setID(String ID) {
		this.id = ID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
