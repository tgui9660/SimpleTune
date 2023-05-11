package com.ecm.graphics.exampleCode;

import Jama.Matrix;

public class FitTest {
	
	static double[] x_values = {-3.0, -2.0, -1.5, 0.0, 1.0, 2.3, 3.0, 4.0, 5.5}; 
	//static double[] y_values = {2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
	//static double[] y_values = {-2.0,-2.0,-2.0,-2.0,-2.0,-2.0,-2.0,-2.0,-2.0};
	//static double[] y_values = {-2.0, 0.0, 2.0, 3.0, 2.0, 5.0, 7.0, 10.0, 14.0};
	static double[] y_values = {-15, -10,  -7, -5, -4, -5, -7, -10, -15};
	
	
	static double a; //Min x value
	static double b; //Max x value
	
	static int n = 6; //Iterations
	static int m = 0; //Number of x values
	
	static double[][] A_array;
	static double[][] B_array;
	
	
	public static void main(String[] args){
		fit();
	}
	
	public static void fit(){
		a = getMinValue(x_values);
		System.out.println("Min Value is:"+a);
		
		b = getMaxValue(x_values);
		System.out.println("Max Value is:"+b);
		
		m = x_values.length - 1;
		
		//Guess at a valid value of n
		n = (int)(m/4);
		if(n == 0){
			n = 1;
		}
		System.out.println("N: "+n);
		
		A_array = new double[n + 1][n + 1];
		B_array = new double[n + 1][1];
		
		// Build (n+1)x(n+1) array of coefficients
		System.out.println("\nAlpha Array values\n-----------------");
		for(int i = 0; i <= n; i++){
			
			for(int j = 0; j <= n; j++){
				
				double kValue = (evalChebyshev(getZSubK(0), i)) * (evalChebyshev(getZSubK(0), j));
				for(int k = 1; k <= m; k++){
					kValue += (evalChebyshev(getZSubK(k), i)) * (evalChebyshev(getZSubK(k), j));
				}
				A_array[j][i] = kValue;
				System.out.print(kValue + "; ");
			}
			System.out.print("\n");
		}
		System.out.println("------------------");
		
		// Build nx1 array of values
		for(int i = 0; i <= n; i++){
			
			double kValue = y_values[0]*(evalChebyshev(getZSubK(0), i));
			
			for(int k = 1; k <= m; k++){
				kValue += y_values[k]*(evalChebyshev(getZSubK(k), i));
			}
			B_array[i][0] = kValue;
			System.out.println("Beta Array Value: "+kValue);
		}
		System.out.println("------------------");
		
		//Solve for coeffs
		Matrix alpha_matrix = new Matrix(A_array);
		Matrix beta_matrix = new Matrix (B_array);
		Matrix resultMatrix = alpha_matrix.solve (beta_matrix);
		
		//Get coeff results
		double results[] = resultMatrix.getColumnPackedCopy();
		//System.out.println("Length:"+results.length);
		
		
		for(int i = 0; i < results.length; i++){
			System.out.println("Coefficients:"+results[i]);
		}
		System.out.println("------------------");
		System.out.println("Y Values");
		
		// Print out all resulting y values for g(x), are we close?
		for(int i = 0; i < x_values.length; i++){
			double interimValue = getScaledXValue(x_values[i]);
			//System.out.println("Interim Value:"+interimValue+"for x value:"+x_values[i]);
			
			double tempValue = evalChebyshev(interimValue, 0)*results[0];
			double yValue = tempValue;
			//System.out.println("TempValue :"+tempValue+" Coeff being used: "+results[0]);
			for(int j = 1; j <= n; j++){
				tempValue = evalChebyshev(interimValue, j)*results[j];
				//System.out.println("TempValue :"+tempValue+" Coeff being used: "+results[j]);
				
				yValue += tempValue;
			}
			System.out.print(yValue+"; ");
		}
	}
	
	/**
	 * Gets scaled x value in range [-1, 1]
	 * 
	 * @param value
	 * @return
	 */
	private static double getScaledXValue(double value){
		double returnValue = (2*value - a - b)/(b - a);
		//System.out.println("Interim value is:"+returnValue);
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
	private static double evalChebyshev(double value, int index){
		//Recursive method below
		/*
		if(index == 0){
			return 1.0;
		}
		
		if(index == 1){
			return value;
		}
		
		double returnValue = (2*value*evalChebyshev(value, index - 1)) -  evalChebyshev(value, index - 2);
		*/
		
		
		double returnValue = Math.cos(index*Math.acos(value));
		//System.out.println("Index: "+ index+"   Value:"+value+" resolved to>"+returnValue);
		return returnValue;
	}
	
	
	/**
	 * Return the Z sub K value for the given max and min x values
	 * @param index
	 * @return
	 */
	private static double getZSubK(int index){
		double returnValue = (2*x_values[index] - a - b)/(b - a);
		//System.out.println("ZSUB K:"+returnValue);
		return returnValue;
	}
	
	/**
	 * Get max value in passed array
	 * 
	 * @param valueArray
	 * @return
	 */
	private static double getMaxValue(double[] valueArray){
		double returnValue = valueArray[0];
		
		for(int i = 1; i < valueArray.length; i++){
			double temp = valueArray[i];
			if(temp > returnValue){
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
	private static double getMinValue(double[] valueArray){
		double returnValue = valueArray[0];
		
		for(int i = 1; i < valueArray.length; i++){
			double temp = valueArray[i];
			if(temp < returnValue){
				returnValue = temp;
			}
		}
		
		return returnValue;
	}
}
