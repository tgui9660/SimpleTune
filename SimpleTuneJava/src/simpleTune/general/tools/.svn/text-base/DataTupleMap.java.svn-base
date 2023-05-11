package simpleTune.general.tools;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataTupleMap {
	private Log logger = LogFactory.getLog(getClass());
	private Vector<DataTuple> tupleVector = null;
	private Double[][] xValues = null;
	private Double[][] yValues = null;
	
	public DataTupleMap(Vector<DataTuple> tupleVector, Double[][] xValues, Double[][] yValues){
		this.tupleVector = tupleVector;
		this.xValues = xValues;
		this.yValues = yValues;
	}
	
	public Double[][] getData(){
		Double[][] dataSet = new Double[yValues.length][xValues[0].length];
		
		for(int i = 0; i < dataSet.length; i++){
			for(int j = 0; j < dataSet[0].length; j++){
				//logger.info("Setting value i:"+i +"  j:"+j+"  value:"+0.0);
				dataSet[i][j] = 0.0;
			}
			
		}
		
		Double[] yTemp = new Double[yValues.length];
		for(int i = 0; i < yValues.length; i++){
			yTemp[i] = yValues[i][0];
			logger.info("yValue >"+yTemp[i]);
		}
		
		Double[] xTemp = new Double[xValues[0].length];
		for(int i = 0; i < xValues[0].length; i++){
			xTemp[i] = xValues[0][i];
			logger.info("xValue >"+xTemp[i]);
		}
		
		Iterator<DataTuple> iterator = tupleVector.iterator();
		while(iterator.hasNext()){
			DataTuple next = iterator.next();
			Double xValue = (Double)next.getValueOne();
			Double yValue = (Double)next.getValueTwo();
			Double zValue = (Double)next.getValueThree();

			//logger.info("X :"+xValue);
			//logger.info("Y :"+yValue);
			//logger.info("Z :"+zValue);
			
			int yBucketIndex = getBucketIndex(yValue, yTemp);
			int xBucketIndex = getBucketIndex(xValue, xTemp);
			//logger.info("y index :"+yBucketIndex);
			//logger.info("x index :"+xBucketIndex);
			
			Double currentCellValue = dataSet[yBucketIndex][xBucketIndex];
			if(currentCellValue != 0.0){
				//zValue = (currentCellValue + zValue)/2;
			}

			dataSet[yBucketIndex][xBucketIndex] = zValue;
		}
		
		return dataSet;
	}
	
	private int getBucketIndex(Double value, Double[] list){
		int bucket = 0;
		Double listValueOne = null;
		Double listValueTwo = null;
		for(int i = 0; i < (list.length - 1); i++){
			listValueOne = list[i];
			listValueTwo = list[i+1];
			
			if(i == 0 && value < listValueOne){
				bucket = i;
				break;
			}
			
			else if(value >= listValueOne && value < listValueTwo){
				Double sizeOne = value - listValueOne;
				Double sizeTwo = listValueTwo - value;
				
				if(sizeOne > sizeTwo){
					bucket = i+1;
					break;
				}else{
					bucket = i;
					break;
				}
			}
			else if((i + 1) == list.length - 1){
				bucket = i+1;
				break;
			}
			
			
		}
		
		logger.info("Value :"+value+"  listValueOne:"+listValueOne+"  listValueTwo:"+listValueTwo);
		logger.info("Bucket Found:"+bucket);
		logger.info("");
		return bucket;
	}
}
