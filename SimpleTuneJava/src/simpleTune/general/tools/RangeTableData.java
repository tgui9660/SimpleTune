package simpleTune.general.tools;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RangeTableData {
	private Log logger = LogFactory.getLog(getClass());
	
	private int rowCount;
	private int colCount;
	private Vector<DataTuple> input;
	private Double[][] xAxisValues;
	private Double[][] yAxisValues;
	private Double[][] tableValues;
	
	public RangeTableData(Vector<DataTuple> input, int rowCount, int colCount){
		// Save argument data
		this.rowCount = rowCount;
		this.colCount = colCount;
		this.input = input;
		
		// Pull all data bounds for every dimension
		Double XMax = DataTupleTools.getMaxTuple(input, DataTuple.FIRST);
		Double XMin = DataTupleTools.getMinTuple(input, DataTuple.FIRST);
		
		Double YMax = DataTupleTools.getMaxTuple(input, DataTuple.SECOND);
		Double YMin = DataTupleTools.getMinTuple(input, DataTuple.SECOND);
		
		Double ZMax = DataTupleTools.getMaxTuple(input, DataTuple.THIRD);
		Double ZMin = DataTupleTools.getMinTuple(input, DataTuple.THIRD);
		
		// Define x axis values
		xAxisValues = new Double[0][colCount - 1];
		Double chunkSize = (XMax - XMin)/(colCount -1);
		
		for(int i = 0; i < colCount-1; i++){
			xAxisValues[0][i] = XMin += chunkSize;
		}
		
		// Define y axis values
		yAxisValues = new Double[rowCount - 1][0];
		chunkSize = (YMax - YMin)/(rowCount - 1);
		
		for(int i = 0; i < rowCount-1; i++){
			yAxisValues[i][0] = YMin += chunkSize;
		}
		
		// Define main table dimensions
		this.tableValues = new Double[rowCount - 1][rowCount - 1];
		
		// Iterate through input
		Iterator<DataTuple> name = input.iterator();
		while(name.hasNext()){
			DataTuple tuple = name.next();
		}
	}
	
	
	public Double[][] getDoubleData(){
		return null;
	}
}
