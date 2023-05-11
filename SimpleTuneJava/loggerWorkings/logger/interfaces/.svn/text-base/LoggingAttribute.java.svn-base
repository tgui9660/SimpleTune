package logger.interfaces;

import java.util.HashMap;

public interface LoggingAttribute {
	public static int TYPE_KEYED_ATTRIBUTE = 0;
	public static int TYPE_BASE_ATTRIBUTE = 1;
	
	
	public int getType();
	public void setType(int type);
	
	/**
	 * Allow for conversions of data. example: HashMap<psi, x/33>
	 * @param conversions
	 */
	public void setConversions(HashMap<String, String> conversions);
	public HashMap<String, String> getConversions();
 
	/**
	 * Current expression to convert raw data to user friendly data
	 * @param expression
	 */
	public void setExpression(String expression);
	public String getExpression();
	
	/**
	 * Units of measure. PSI, COWS, F
	 * @return
	 */
	public String getUnits();
	public void setUnits(String units);
	
	/**
	 * Get the value contained by this attribute.
	 * 
	 * @return
	 */
	public Double getValue();
	public void setValue(Double newValue);
	
	/**
	 * Upper bound of attribute
	 */
	public Double getMin();
	public void setMin(Double min);
	
	/**
	 * Lower bound of attribute
	 */
	public Double getMax();
	public void setMax(Double max);
	
	/**
	 * Precision of data to represent. In format such as ##.####
	 */
	public String getPrecision();
	public void setPrecision(String precision);
	
	/**
	 * Name of the attribute. Keep short as possible.
	 * @return
	 */
	public String getName();
	public void setName(String name);
	
	/**
	 * GUI element that listens to this attribute
	 * @param listener
	 */
	// public void addListener(DataSupplierListener listener);
	
	/**
	 * Defines whether or not list
	 * @param isActive
	 */
	public void setActive(Boolean isActive);
	// public void setNewData(Double newData);
	
	/**
	 * 
	 * @return
	 */
	public String getID();
	public void setID(String id);
	
	/**
	 * Tell me a little something about this attribute.
	 * @return
	 */
	public String getDescription();
	public void setDescription(String description);
	

}
