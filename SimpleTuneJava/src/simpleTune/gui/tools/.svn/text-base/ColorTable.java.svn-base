package simpleTune.gui.tools;

import java.awt.Color;

import javax.vecmath.Color3f;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
 
/**
 * A table which maps values to colors
 * 
 */
public class ColorTable {
	private Log logger = LogFactory.getLog(getClass());
   
	// Use of floats to ensure max data resolution
	private static float min;
    private static float max;
    
    
    /**
     * 
     * @param minA
     * @param maxA
     */
    public static void initColorTable(float minA, float maxA) {
        min = minA;
        max = maxA;
    }
 
   
    /**
     * Gets the color for the given value if the value is smaller than the
     * minimal value specified the minimal Color is returned if the value is
     * bigger than the maximal value specified the maximal Color is returned
     * 
     * @param value
     *            value to get Color for
     * @return Color for the given value
     */
    public static Color3f getColor(float value) {
    	
    	float range = max - min;
    	float length = value - min;
    	float percent = length/range;
    	
    	Color maxColor = new Color(255, 102, 102);
        Color minColor = new Color(153, 153, 255);
    	
        float[] minColorHSB = new float[3];
        float[] maxColorHSB = new float[3];

        Color.RGBtoHSB(minColor.getRed(),
                       minColor.getGreen(),
                       minColor.getBlue(),
                       minColorHSB);

        Color.RGBtoHSB(maxColor.getRed(),
                       maxColor.getGreen(),
                       maxColor.getBlue(),
                       maxColorHSB);

        float h = minColorHSB[0] + (maxColorHSB[0] - minColorHSB[0]) * percent;
        float s = minColorHSB[1] + (maxColorHSB[1] - minColorHSB[1]) * percent;
        float b = minColorHSB[2] + (maxColorHSB[2] - minColorHSB[2]) * percent;

        Color newColor = Color.getHSBColor(h, s, b);
        
        return ColorToColor3f(newColor);
    }
    
    private static Color3f ColorToColor3f(Color theColor){
    	float x = convertToFloat(theColor.getRed());
    	float y = convertToFloat(theColor.getGreen());
    	float z = convertToFloat(theColor.getBlue());
    	
    	Color3f returnColor3f = new Color3f(x,y,z);
    		
    	return returnColor3f;
    }
    
    private static Color Color3fToColor(Color3f theColor){
    	int x = convertToInt(theColor.x);
    	int y = convertToInt(theColor.y);
    	int z = convertToInt(theColor.z);
    	
    	Color returnColor3f = new Color(x,y,z);
    		
    	return returnColor3f;
    }
    
    private static int convertToInt(float value){
    	int retValue = (int)(255*value);
    	return retValue;
    }
    
    private static float convertToFloat(float value){
    	float retValue = value/255;
    	return retValue;
    }
    
    /**
     * Returns the cell select Color
     * @return
     */
    public static Color3f getSelectedColor(){
    	return ColorToColor3f(new Color(204, 204, 204));
    }
}
