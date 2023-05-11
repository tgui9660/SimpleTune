package logger.utec.gui.mapTabs;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.vecmath.Color3f;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.tools.ColorTable;

import java.awt.Color;
import java.awt.Component;

public class UtecTableCellRenderer extends DefaultTableCellRenderer{
	private double min;
	private double max;
	private Object[] ignoredValues;
	private boolean isInvertedColoring;
	private Log logger = LogFactory.getLog(getClass());
	public UtecTableCellRenderer(double min, double max, Object[] ignoredValues, boolean isInvertedColoring){
		this.min = min;
		this.max = max;
		this.ignoredValues = ignoredValues;
		this.isInvertedColoring = isInvertedColoring;
	}
	
	/**
	 * Called when table needs cell rendering information. Cell logic on color values goes here.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		
		if(isSelected){
			cell.setBackground(Color.BLUE);
		}else{
			if(value instanceof Double){
				ColorTable.initColorTable((float)min, (float)max);
				if(this.isInvertedColoring){
					ColorTable.initColorTable((float)max, (float)min);
				}
				Color3f theColor = ColorTable.getColor((Float)value);
				cell.setBackground(new Color(theColor.x, theColor.y, theColor.z));
				
				// If out of range color cell red
				if((Double)value < min || (Double)value > max){
					cell.setBackground(Color.RED);
				}
			}
			

			// Iterate through the ignored values, paint them gray
			for(int i = 0; i < ignoredValues.length; i++){
				
				// Double ignored values
				if((value instanceof Double) && (ignoredValues[i] instanceof Double)){
					Double doubleValue = (Double)value;
					Double ignoredValue = (Double)ignoredValues[i];
					
					if((doubleValue - ignoredValue) == 0){
						cell.setBackground(Color.GRAY);
					}
				}
				
				// Maybe add string value detection as needed
			}
		}
		
		
		
		
		return cell;
	}
}
