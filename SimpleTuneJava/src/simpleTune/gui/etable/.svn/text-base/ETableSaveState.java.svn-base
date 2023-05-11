package simpleTune.gui.etable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public class ETableSaveState {
	private Log logger = LogFactory.getLog(getClass());
    private Object[][] internalData;
	//private String name;
	public ETableSaveState(Object[][] data){
		//this.name = name;
		int width = data.length;
		int height = data[0].length;
		
		//LOGGER.debug("Dimensions:  w:"+ width+"   h:"+height);
		this.internalData = new Object[width][height];
		
		for(int i = 0; i < width; i ++){
			for(int j=0; j < height; j++){
				Object tempData = data[i][j];
				this.internalData[i][j] = tempData;
			}
		}
		
		logger.debug("Sample: "+this.internalData[0][0]);
	}
	
	public Object[][] getData(){
		return this.internalData;
	}
	
	/*
	public String getName(){
		return name;
	}
	*/
}
