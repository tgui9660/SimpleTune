package logger.interfaces.impl;

import java.util.HashMap;
import logger.interfaces.LoggingAttribute;

public class LoggingAttributeImpl implements LoggingAttribute{
	
	// Getter and setter values
	private boolean isActive = false;
	private Double value = -1000.0;
	private Double max = -1000.0;
	private Double min = -1000.0;
	private String name = "";
	private String precision = "#.##";
	private String id = "";
	private String description = "";
	private String units = "";
	
	private HashMap<String, String> conversions = new HashMap<String, String>();
	private String currentExpression = "x";
	
	private int type = LoggingAttribute.TYPE_BASE_ATTRIBUTE;
	
	// ************************
	// GETTERS AND SETTERS HERE
	// ************************
	public String getExpression() {
		return currentExpression;
	}
	public void setExpression(String expression) {
		this.currentExpression = expression;
	}
	
	public Double getValue() {
		return this.value;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}
	
	public Double getMax() {
		return this.max;
	}

	public Double getMin() {
		return this.min;
	}

	public String getName() {
		return this.name;
	}

	public String getPrecision() {
		return this.precision;
	}

	public void setActive(Boolean isActive) {
		//System.out.println(this.getName()+" Logging attribute:"+isActive);
		this.isActive = isActive;
	}

	public boolean getActive(){
		return this.isActive;
	}
	
	public String getID() {
		return this.id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnits() {
		return this.units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public void setValue(Double newValue) {
		this.value = newValue;
	}
	public HashMap<String, String> getConversions() {
		return this.conversions;
	}
	public void setConversions(HashMap<String, String> conversions) {
		this.conversions = conversions;
	}
	public int getType() {
		return this.type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
