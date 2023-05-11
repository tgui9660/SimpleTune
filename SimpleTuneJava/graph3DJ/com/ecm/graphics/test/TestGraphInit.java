package com.ecm.graphics.test;

import java.util.Vector;

import com.ecm.graphics.Graph3dFrameManager;
import com.ecm.graphics.data.GraphData;
import com.ecm.graphics.data.GraphDataListener;

public class TestGraphInit implements GraphDataListener{
	
	public void init(){

		Vector testVector = new Vector();
		for (int i = 0; i < 12; i++) {
			float[] testData = { 0.0f + i, 1.0f + i, 2.0f + i, 3.0f + i,
					-4.0f + i, -5.0f + i, -4.0f + i, -3.0f + i, 20.0f + i, 1.0f+i, 0.0f + i, 1.0f + i, 2.0f + i, 3.0f + i,
					4.0f + i, 5.0f + i};
			testVector.add(testData);
		}
		
		double[] testX = { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000 };
		double[] testZ = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 , 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40};

		Graph3dFrameManager.openGraph3dFrame(testVector, 0.0, 10.0,  testX, testZ, "X Label", "Graph Title", "Z Label", "test title");
		GraphData.addGraphDataListener(this);
	}
	
	public void newGraphData(int x, int z, float value) {
		
		//System.out.println("New data recieved at the client \n*********************");
		//System.out.println("X:"+x+" Z:"+z+" VALUE:"+value);
		//System.out.println("*********************");
	}
	
	public void selectStateChange(int x, int z, boolean value){
		//System.out.println("New state recieved at the client \n*********************");
		//System.out.println("X:"+x+" Z:"+z+" VALUE:"+value);
		//System.out.println("*********************");
	}
	
}
