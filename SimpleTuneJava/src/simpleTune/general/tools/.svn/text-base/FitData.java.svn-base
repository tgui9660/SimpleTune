package simpleTune.general.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import Jama.Matrix;

public class FitData {
	private static Log logger = LogFactory.getLog(FitData.class);
	static double[] x_values;
	static double[] y_values;
	
	static double a; // Min x value

	static double b; // Max x value

	static int n = 10; // Iterations

	static int m = 0; // Number of x values

	static double[][] A_array;

	static double[][] B_array;

	static double[] results;
	
	/**
	 * Find LAST index value that contains a value in passed array that is smaller than the passed value.
	 * 
	 * If no such index exists, return -1.
	 * 
	 * @param value
	 * @param valueRange
	 * @return
	 */
	private static int findNextLowestIndex(Double value, Double[] valueRange){
		int savedIndex = -1;
		
		for(int i = 0; i < valueRange.length; i++){
			Double theValue = valueRange[i];
			if(theValue < value){
				savedIndex = i;
			}
		}
		
		return savedIndex;
	}

	/**
	 * Find FIRST index value that contains a value greater than the passed value.
	 * 
	 * @param value
	 * @param valueRange
	 * @return
	 */
	private static int findNextGreatestIndex(Double value, Double[] valueRange){
		int savedIndex = -1;
		
		for(int i = 0; i < valueRange.length; i++){
			Double theValue = valueRange[i];
			if(theValue > value){
				return i;
			}
		}
		
		return savedIndex;
	}
	
	public static Double[][] getYAxisScale(Double[][] intialData, int rowIndex, double newRowValue, Double[] oldRowValues){
		int width = intialData[0].length;
		int height = oldRowValues.length;
		
		//logger.info("Smooth width :"+width);
		//logger.info("Smooth height :"+height);
		//logger.info("Row Index :"+rowIndex);
		//logger.info("New Row Value :"+newRowValue);
		
		int lastLowestIndex = FitData.findNextLowestIndex(newRowValue, oldRowValues);
		int firstGreatestIndex = FitData.findNextGreatestIndex(newRowValue, oldRowValues);
		
		//logger.info("Last Lowest Index : "+lastLowestIndex);
		//logger.info("First Greatest Index : "+firstGreatestIndex);
		
		
		double[] rowValues = new double[height];
		Double[][] returnData = new Double[height][width];
		
		for(int j = 0; j < width; j++){
			// Pull row of data
			for(int i = 0; i < height; i++){
				rowValues[i] = intialData[i][j];
				//System.out.print(" , "+rowValues[i]);
			}
			//logger.info("");
			
			if(firstGreatestIndex == -1){
				rowValues[rowIndex] = rowValues[width - 1];
			}
			else if(lastLowestIndex == -1){
				rowValues[rowIndex] = rowValues[0];
			}
			else{
				double lowIndexValue = oldRowValues[lastLowestIndex];
				double highIndexValue = oldRowValues[firstGreatestIndex];
			
				double majorIndexDifference = highIndexValue - lowIndexValue;
				double minorIndexDifference = newRowValue - lowIndexValue;
				
				double percentage = minorIndexDifference / majorIndexDifference;
				
				double lowValue = rowValues[lastLowestIndex];
				double highValue = rowValues[firstGreatestIndex];
				double difference = highValue - lowValue;
				
				double additive = difference * percentage;
				double newValue = lowValue + additive;
				
				rowValues[rowIndex] = newValue;
			}
			
			// Put row of data back into return data set
			for(int i = 0; i < oldRowValues.length; i++){
				rowValues[i] = returnData[i][j] = rowValues[i];
				//System.out.print(" , "+rowValues[i]);
			}
		}
		
		
		return returnData;
	}
	
	public static Double[][] getXAxisScale(Double[][] intialData, int colIndex, double newColValue, Double[] oldColumnValues){
		int width = oldColumnValues.length;
		int height = intialData.length;
		
		//logger.info("Smooth width :"+width);
		//logger.info("Smooth height :"+height);
		//logger.info("Col Index :"+colIndex);
		//logger.info("New Col Value :"+newColValue);
		
		int lastLowestIndex = FitData.findNextLowestIndex(newColValue, oldColumnValues);
		int firstGreatestIndex = FitData.findNextGreatestIndex(newColValue, oldColumnValues);
		
		//logger.info("Last Lowest Index : "+lastLowestIndex);
		//logger.info("First Greatest Index : "+firstGreatestIndex);
		
		
		double[] rowValues = new double[width];
		Double[][] returnData = new Double[height][width];
		
		for(int j = 0; j < height; j++){
			// Pull row of data
			for(int i = 0; i < oldColumnValues.length; i++){
				rowValues[i] = intialData[j][i];
				//System.out.print(" , "+rowValues[i]);
			}
			//logger.info("");
			
			if(firstGreatestIndex == -1){
				rowValues[colIndex] = rowValues[width - 1];
			}
			else if(lastLowestIndex == -1){
				rowValues[colIndex] = rowValues[0];
			}
			else{
				double lowIndexValue = oldColumnValues[lastLowestIndex];
				double highIndexValue = oldColumnValues[firstGreatestIndex];
			
				double majorIndexDifference = highIndexValue - lowIndexValue;
				double minorIndexDifference = newColValue - lowIndexValue;
				
				double percentage = minorIndexDifference / majorIndexDifference;
				
				double lowValue = rowValues[lastLowestIndex];
				double highValue = rowValues[firstGreatestIndex];
				double difference = highValue - lowValue;
				
				double additive = difference * percentage;
				double newValue = lowValue + additive;
				
				rowValues[colIndex] = newValue;
			}
			
			// Put row of data back into return data set
			for(int i = 0; i < oldColumnValues.length; i++){
				rowValues[i] = returnData[j][i] = rowValues[i];
				//System.out.print(" , "+rowValues[i]);
			}
		}
		
		
		return returnData;
	}
	
	public static Double[][] getXAxisScaleInterpolated(Double[][] intialData, int colIndex, double newColValue, Double[] oldColumnValues){
		int width = oldColumnValues.length;
		int height = intialData.length;
		
		if(FitData.getMaxValue(oldColumnValues) < newColValue){
			newColValue = FitData.getMaxValue(oldColumnValues);
		}
		
		logger.info("Smooth width :"+width);
		logger.info("Smooth height :"+height);
		logger.info("Col Index :"+colIndex);
		logger.info("New Col Value :"+newColValue);
		
		Double[][] returnData = new Double[height][width];
		
		double[] tempXValues = new double[oldColumnValues.length];
		for(int i = 0; i < oldColumnValues.length; i++){
			tempXValues[i] = oldColumnValues[i];
		}
		
		double[] tempYValues = new double[width];
		
		// Row pass
		for(int j = 0; j < height; j++){
			for(int i = 0; i < oldColumnValues.length; i++){
				tempYValues[i] = intialData[j][i];
				logger.info("Y Value --- : "+tempYValues[i]);
			}
			init(tempXValues, tempYValues);
			for(int i = 0; i < oldColumnValues.length; i++){
				if(i == colIndex){
					double newData = getSmoothYValue(newColValue);
					logger.info("OLD Scaled Data :"+intialData[j][i]);
					logger.info("NEW Scaled Data :"+newData);
					returnData[j][i] = newData;
				}else{
					returnData[j][i] = intialData[j][i];
				}
			}
		}
		
		return returnData;
	}
	
	
	public static Double[][] getYAxisScaleInterpolated(Double[][] intialData, int rowIndex, double newRowValue, Double[] oldRowValues){
		int width = intialData[0].length;
		int height = oldRowValues.length;
		
		
		if(FitData.getMaxValue(oldRowValues) < newRowValue){
			newRowValue = FitData.getMaxValue(oldRowValues);
		}
		
		logger.info("Smooth width :"+width);
		logger.info("Smooth height :"+height);
		logger.info("Row Index :"+rowIndex);
		logger.info("New Row Value :"+newRowValue);

		
		Double[][] returnData = new Double[height][width];
		
		double[] tempXValues = new double[oldRowValues.length];
		for(int i = 0; i < oldRowValues.length; i++){
			tempXValues[i] = oldRowValues[i];
		}
		
		double[] tempYValues = new double[height];
		
		// Col pass
		for(int j = 0; j < width; j++){
			for(int i = 0; i < height; i++){
				tempYValues[i] = intialData[i][j];
				logger.info("Y Value --- : "+tempYValues[i]);
			}
			init(tempXValues, tempYValues);
			for(int i = 0; i < oldRowValues.length; i++){
				if(i == rowIndex){
					double newData = getSmoothYValue(newRowValue);
					logger.info("OLD Scaled Data :"+intialData[i][j]);
					logger.info("NEW Scaled Data :"+newData);
					returnData[i][j] = newData;
				}else{
					returnData[i][j] = intialData[i][j];
				}
			}
		}
		
		return returnData;
	}
	
	/**
	 * Do a full smooth on passed double data
	 * @param intialData
	 * @return
	 */
	public static Double[][] getFullSmooth(Double[][] intialData){
		int width = intialData.length;
		int height = intialData[0].length;
		
		logger.info("Smooth width :"+width);
		logger.info("Smooth height :"+height);
		
		
		Double[][] returnData = new Double[width][height];
		
		double[] tempXValues = new double[width];
		for(int i = 0; i < width; i++){
			tempXValues[i] = i;
		}
		
		double[] tempYValues = new double[width];
		
		
		// Ensure proper dimension
		if(width > 3){
			// Row pass
			for(int j = 0; j < height; j++){
				for(int i = 0; i < width; i++){
					tempYValues[i] = intialData[i][j];
				}
				init(tempXValues, tempYValues);
				for(int i = 0; i < width; i++){
					returnData[i][j] = getSmoothYValue(i);
				}
			}
		}else{
			// Just copying over initial data to returned data since no row smoothing pass was made to
			// populate the returnData[][]
			for(int j = 0; j < height; j++){
				for(int i = 0; i < width; i++){
					returnData[i][j] = intialData[i][j];
				}
			}
		}

		if(height > 3){
			// Column Pass
			tempXValues = new double[height];
			for(int i = 0; i < height; i++){
				tempXValues[i] = i;
			}
			
			tempYValues = new double[height];
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					tempYValues[j] = returnData[i][j];
				}
				init(tempXValues, tempYValues);
				for(int j = 0; j < height; j++){
					returnData[i][j] = getSmoothYValue(j);
				}
			}
		}

		
		return returnData;
	}
	
	
	/**
	 * Initialize smoother with passed data
	 * 
	 * @param xVals
	 * @param yVals
	 */
	public static void init(double[] xVals, double[] yVals) {
		x_values = xVals;
		y_values = yVals;

		a = getMinValue(x_values);
		b = getMaxValue(x_values);

		m = x_values.length - 1;

		// Guess at a valid value of n
		n = (int) (m / 4);
		if (n == 0) {
			n = 1;
		}

		A_array = new double[n + 1][n + 1];
		B_array = new double[n + 1][1];

		// Build (n+1)x(n+1) array of coefficients
		for (int i = 0; i <= n; i++) {

			for (int j = 0; j <= n; j++) {

				double kValue = (evalChebyshev(getZSubK(0), i))
						* (evalChebyshev(getZSubK(0), j));
				for (int k = 1; k <= m; k++) {
					kValue += (evalChebyshev(getZSubK(k), i))
							* (evalChebyshev(getZSubK(k), j));
				}
				A_array[j][i] = kValue;
			}
		}
		// Build nx1 array of values
		for (int i = 0; i <= n; i++) {
			double kValue = y_values[0] * (evalChebyshev(getZSubK(0), i));

			for (int k = 1; k <= m; k++) {
				kValue += y_values[k] * (evalChebyshev(getZSubK(k), i));
			}
			B_array[i][0] = kValue;
		}
		// Solve for coeffs
		Matrix alpha_matrix = new Matrix(A_array);
		Matrix beta_matrix = new Matrix(B_array);
		Matrix resultMatrix = alpha_matrix.solve(beta_matrix);

		// Get coeff results
		results = resultMatrix.getColumnPackedCopy();
		
	}

	/**
	 * Get smoothed y value for passed x value
	 * 
	 * @param xValue
	 * @return
	 */
	public static double getSmoothYValue(double xValue) {
		double interimValue = getScaledXValue(xValue);

		double yValue = evalChebyshev(interimValue, 0) * results[0];
		for (int j = 1; j <= n; j++) {
			yValue += evalChebyshev(interimValue, j) * results[j];
		}
		
		return yValue;
	}

	/**
	 * Gets scaled x value in range [-1, 1]
	 * 
	 * @param value
	 * @return
	 */
	private static double getScaledXValue(double value) {
		double returnValue = (2 * value - a - b) / (b - a);
		return returnValue;
	}

	/**
	 * Evaluates the passed value for the indexed value
	 * 
	 * Recursive method shown for fun.
	 * 
	 * @param value
	 * @param index
	 * @return
	 */
	private static double evalChebyshev(double value, int index) {
		double returnValue = Math.cos(index * Math.acos(value));
		return returnValue;
	}

	/**
	 * Return the Z sub K value for the given max and min x values
	 * 
	 * @param index
	 * @return
	 */
	private static double getZSubK(int index) {
		double returnValue = (2 * x_values[index] - a - b) / (b - a);
		return returnValue;
	}

	/**
	 * Get max value in passed array
	 * 
	 * @param valueArray
	 * @return
	 */
	private static double getMaxValue(double[] valueArray) {
		double returnValue = valueArray[0];

		for (int i = 1; i < valueArray.length; i++) {
			double temp = valueArray[i];
			if (temp > returnValue) {
				returnValue = temp;
			}
		}

		return returnValue;
	}
	
	/**
	 * Double version
	 * 
	 * @param valueArray
	 * @return
	 */
	private static Double getMaxValue(Double[] valueArray) {
		double returnValue = valueArray[0];

		for (int i = 1; i < valueArray.length; i++) {
			double temp = valueArray[i];
			if (temp > returnValue) {
				returnValue = temp;
			}
		}

		return returnValue;
	}

	/**
	 * Get min value in passed array
	 * 
	 * @param valueArray
	 * @return
	 */
	private static double getMinValue(double[] valueArray) {
		double returnValue = valueArray[0];

		for (int i = 1; i < valueArray.length; i++) {
			double temp = valueArray[i];
			if (temp < returnValue) {
				returnValue = temp;
			}
		}

		return returnValue;
	}
	
	/**
	 * Double version
	 * 
	 * @param valueArray
	 * @return
	 */
	private static Double getMinValue(Double[] valueArray) {
		double returnValue = valueArray[0];

		for (int i = 1; i < valueArray.length; i++) {
			double temp = valueArray[i];
			if (temp < returnValue) {
				returnValue = temp;
			}
		}

		return returnValue;
	}
}
