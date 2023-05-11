package com.ecm.graphics.test;

import com.ecm.graphics.tools.FitData;

public class TestFitData {
	static double[] x_values = {-3.0, -2.0, -1.5, 0.0, 1.0, 2.3, 3.0, 4.0, 5.5}; 
	//static double[] y_values = {2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
	//static double[] y_values = {-2.0,-2.0,-2.0,-2.0,-2.0,-2.0,-2.0,-2.0,-2.0};
	//static double[] y_values = {-2.0, 0.0, 2.0, 3.0, 2.0, 5.0, 7.0, 10.0, 14.0};
	static double[] y_values = {-15, -10,  -7, -5, -4, -5, -7, -10, -15};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FitData.init(x_values, y_values);
		
		for(int i = 0; i < x_values.length; i++){
			double yValue = FitData.getSmoothYValue(x_values[i]);
			System.out.println("Calculated and smoothed y value: "+yValue);
		}
	}

}
