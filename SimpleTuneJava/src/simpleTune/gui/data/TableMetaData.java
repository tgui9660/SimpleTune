package simpleTune.gui.data;


import java.awt.Dimension;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.interfaces.TuningEntity;

public class TableMetaData {
	private Log logger = LogFactory.getLog(getClass());
	// Type of table
	public static final int DATA_1D = 0;
	public static final int DATA_2D = 1;
	public static final int DATA_3D = 3;
	public static final int MAP_SET_ROOT = 4;
	public static final int RESERVED_ROOT = 5;
	public static final int CATEGORY = 6;
	public static final int SWITCH = 7;
	
	// **********************
	// Constructor set values
	// **********************
	
	// Table data related
	private double maxValue;
	private double minValue;
	private Object[] ignoredValues;
	private boolean isInvertedColoring;
	private String dataFormat;
	private String xFormat;
	private String yFormat;
	
	// Table labels etc
	private String tableName;
	private Object[][] columnLabels;
	private Object[][] rowLabels;
	private String xAxisLabel;
	private String yAxisLabel;
	private String tableIdentifier;
	private String tableGroup;
	private String description;
	private String suffix;

	
	// Is this a table1d, table2d, table3d
	private int dimensions;
	
	// What tuning entity should table described by this meta data refer to?
	private TuningEntity parentTuningEntity;
	
	// Optional setters
	private DecimalFormat formatter = new DecimalFormat( "#.0" );
	private String mainTableUnits = "";
	private String xAxisUnits = "";
	private String yAxisUnits = "";
	private String shortFileName = "";
	private String mainTableName = "";
	
	public TableMetaData(){
		
	}
	
	public Dimension getDimension(){
		int width = 1;
		int height = 1;
		
		if(this.columnLabels != null){
			width = this.columnLabels.length;
		}
		
		if(this.rowLabels != null){
			height = this.rowLabels.length;
		}
		
		return new Dimension(width, height);
	}
	
	public TableMetaData(String suffix, int dimensions, double minValue, double maxValue, Object[] ignoredValues, Object[][] columnLabels, Object[][] rowLabels, boolean isInvertedColoring, String tableName, String xAxisLabel, String yAxisLabel, String tableIdentifier, String tableGroup, String dataFormat, String xFormat, String yFormat, String description, TuningEntity parentTuningEntity, String xAxisUnits, String yAxisUnits, String mainTableUnits, String shortFileName, String mainTableName) {
		this.suffix = suffix;
		this.dimensions = dimensions;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.ignoredValues = ignoredValues;
		this.columnLabels = columnLabels;
		this.rowLabels = rowLabels;
		this.isInvertedColoring = isInvertedColoring;
		this.tableName = tableName;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
		this.tableIdentifier = tableIdentifier;
		this.tableGroup = tableGroup;
		this.parentTuningEntity = parentTuningEntity;	
		this.dataFormat = dataFormat;
		this.xFormat = xFormat;
		this.yFormat = yFormat;
		this.description = description;
		this.mainTableUnits = mainTableUnits;
		this.xAxisUnits = xAxisUnits;
		this.yAxisUnits = yAxisUnits;
		this.shortFileName = shortFileName;
		this.mainTableName = mainTableName;
	}

	public Object[] getIgnoredValues() {
		return ignoredValues;
	}

	public boolean isInvertedColoring() {
		return isInvertedColoring;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public String getTableName() {
		return tableName;
	}

	public int getNodeType() {
		return dimensions;
	}

	public String getTableIdentifier() {
		return tableIdentifier;
	}

	public Object[][] getColumnLabels() {
		return columnLabels;
	}

	public Object[][] getRowLabels() {
		return rowLabels;
	}

	public TuningEntity getParentTuningEntity() {
		return parentTuningEntity;
	}

	public String getTableGroup() {
		return tableGroup;
	}

	
	// Getters and setters for not so commonly used items, not included in constructor
	
	public DecimalFormat getFormatter() {
		return formatter;
	}

	public void setFormatter(DecimalFormat formatter) {
		this.formatter = formatter;
	}

	public String getXAxisLabel() {
		return xAxisLabel;
	}

	public String getYAxisLabel() {
		return yAxisLabel;
	}

	/*
	public static Dimension getData1DDimension() {
		return Data1DDimension;
	}

	public static Dimension getData2DDimension() {
		return Data2DDimension;
	}

	public static Dimension getData3DDimension() {
		return Data3DDimension;
	}
	*/
	
	public String getDataFormat() {
		return dataFormat;
	}

	public String getXAxisFormat() {
		return xFormat;
	}

	public String getYAxisFormat() {
		return yFormat;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getMainTableUnits() {
		return mainTableUnits;
	}

	public void setMainTableUnits(String mainTableUnits) {
		this.mainTableUnits = mainTableUnits;
	}

	public String getXAxisUnits() {
		return xAxisUnits;
	}

	public void setXAxisUnits(String axisUnits) {
		xAxisUnits = axisUnits;
	}

	public String getYAxisUnits() {
		return yAxisUnits;
	}

	public void setYAxisUnits(String axisUnits) {
		yAxisUnits = axisUnits;
	}

	public String getShortFileName() {
		return shortFileName;
	}

	public void setShortFileName(String shortFileName) {
		this.shortFileName = shortFileName;
	}

	public String getMainTableName() {
		return mainTableName;
	}

	public void setMainTableName(String mainTableName) {
		this.mainTableName = mainTableName;
	}

	public int getDimensions() {
		return dimensions;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public String getXFormat() {
		return xFormat;
	}

	public void setXFormat(String format) {
		xFormat = format;
	}

	public String getYFormat() {
		return yFormat;
	}

	public void setYFormat(String format) {
		yFormat = format;
	}

	public void setColumnLabels(Object[][] columnLabels) {
		this.columnLabels = columnLabels;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public void setIgnoredValues(Object[] ignoredValues) {
		this.ignoredValues = ignoredValues;
	}

	public void setInvertedColoring(boolean isInvertedColoring) {
		this.isInvertedColoring = isInvertedColoring;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public void setParentTuningEntity(TuningEntity parentTuningEntity) {
		this.parentTuningEntity = parentTuningEntity;
	}

	public void setRowLabels(Object[][] rowLabels) {
		this.rowLabels = rowLabels;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setTableGroup(String tableGroup) {
		this.tableGroup = tableGroup;
	}

	public void setTableIdentifier(String tableIdentifier) {
		this.tableIdentifier = tableIdentifier;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setXAxisLabel(String axisLabel) {
		xAxisLabel = axisLabel;
	}

	public void setYAxisLabel(String axisLabel) {
		yAxisLabel = axisLabel;
	}
	
	public boolean equals(TableMetaData compare){
		
		if(this.tableName == compare.getTableName() &&
				this.tableGroup == compare.getTableGroup() &&
				this.tableIdentifier == compare.getTableIdentifier()){
			return true;
		}
		
		return false;
	}
}
