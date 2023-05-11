package simpleTune.gui.etable;


import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.vecmath.Color3f;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.general.tools.DataTools;
import simpleTune.gui.tools.ColorTable;

import java.awt.Color;
import java.awt.Component;

public class ETableCellRenderer extends DefaultTableCellRenderer{
	private Log logger = LogFactory.getLog(getClass());
	
	private double min;
	private double max;
	private Object[] ignoredValues;
	private boolean isInvertedColoring;
	private ETable theETableParent;
	
	public ETableCellRenderer(double min, double max, Object[] ignoredValues, boolean isInvertedColoring, ETable theETable){
		this.theETableParent = theETable;
		
		this.min = min;
		this.max = max;
		this.ignoredValues = ignoredValues;
		this.isInvertedColoring = isInvertedColoring;
		this.setHorizontalAlignment(CENTER);
	}
	
	/**
	 * Set min and max values currently found in table
	 * @param min
	 * @param max
	 */
	public void setScale(double min, double max){
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Called when table needs cell rendering information. Cell logic on color values goes here.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		
		if(isSelected){
			cell.setBackground(new Color(65, 105, 225));
			this.theETableParent.isSelectedETable();
		}else{
			if(value instanceof Double){
				
				// Reset the color scale if needed, keeps away the crazy colors
				Double[][] newData = DataTools.convertObjToDouble(this.theETableParent.getData());
				
		        int width = newData.length;
		        int height = newData[0].length;

		        // Set initial min and max values that we can be reasonably assured will be eclipsed
		        double min = 10000;
		        double max = -10000;
		        
		        // Iterate through entire table looking for max and min values
		        for (int i = 0; i < width; i++) {
		            for (int j = 0; j < height; j++) {
		            	if(newData[i][j] < min){
		            		min = newData[i][j];
		            	}
		            	
		            	if(newData[i][j] > max){
		            		max = newData[i][j];
		            	}
		            }
		        }
				this.setScale(min, max);
				
				// If there is a range of values, scale all cell colors.
				if((max - min) > 0){
					ColorTable.initColorTable((float)min,(float) max);
					if(this.isInvertedColoring){
						ColorTable.initColorTable((float)max, (float)min);
					}
					Color3f theColor = ColorTable.getColor(((Double)value).floatValue());
					cell.setBackground(new Color(theColor.x, theColor.y, theColor.z));
					
					// If out of range color cell red
					if((Double)value < min || (Double)value > max){
						//cell.setBackground(Color.RED);
					}
				}else{
					// If there is no difference in cell values, set to a nice basic blue color
					cell.setBackground(new Color(30, 144, 255));
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
