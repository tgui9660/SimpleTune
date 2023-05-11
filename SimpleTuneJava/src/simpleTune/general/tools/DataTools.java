package simpleTune.general.tools;

public class DataTools {
	/**
	 * Helper method for when dealing with fuel timing and boost tables
	 * @param objData
	 * @return
	 */
	public static Double[][] convertObjToDouble(Object[][] objData){
		int length = objData.length;
		int width = objData[0].length;
		
		Double[][] newData = new Double[length][width];
		
		for(int i = 0 ; i < length ; i++){
			for(int j = 0 ; j < width ; j++){
				newData[i][j] = (Double)objData[i][j];
			}
		}
		
		return newData;
	}

	public static String parseShortFileName(String path){
		String property = System.getProperty("os.name");
		String[] pathSplit = null;
		if(property.toLowerCase().contains("window")){
			pathSplit = path.split("\\\\");
		}else{
			pathSplit = path.split("/");
		}
		 
		return pathSplit[pathSplit.length - 1];
	}
}
