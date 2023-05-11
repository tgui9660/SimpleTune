package com.ecm.graphics.render.flatFaceRender;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;

import com.ecm.graphics.data.GraphData;
import com.ecm.graphics.tools.ColorTable;
import com.ecm.graphics.tools.FitData;

public class Graph3dInterpolatedRender extends TransformGroup implements DataChanger{
	private int xWidth;
	private int zDepth;
	private float fudgedScaleYMax;
	private int totalFineWidth;
	private double[][] fineValues;
	private double resolution = 0.25;
	
	public Graph3dInterpolatedRender(float fudgedScaleYMax){
		this.xWidth = GraphData.getXWidth();
		this.zDepth = GraphData.getZDepth();
		this.fudgedScaleYMax = fudgedScaleYMax;
		
		System.out.println("hit 1");
		//Allow rotate etc.
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		//Store all values
		double[][] allValues = new double[xWidth][zDepth];
		
		
		//Add all the faces
		for(int i = 0; i < xWidth - 1; i++){
			for(int j = 0; j < zDepth - 1; j++){
				allValues[i][j] = GraphData.getCellValue(i, j);
			}
		}
		System.out.println("hit 2");
		totalFineWidth = xWidth*4;
		
		double[] xVals = new double[xWidth];
		double[] yVals = new double[xWidth];
		

		double[] xValsFine = new double[totalFineWidth];
		double[] yValsFine = new double[totalFineWidth];
		fineValues = new double[totalFineWidth][zDepth];
		
		for(int i = 0; i < zDepth - 1; i++){
			for(int j = 0; j < xWidth - 1; j++){
				xVals[j] = j;
				yVals[j] = allValues[j][i];
				System.out.println("Values:"+i+"   >"+allValues[j][i]);
			}
			
			FitData.init(xVals, yVals);
			

			for(int k = 0; k < xWidth - 1; k++){
				double xCount = 0;
				while(xCount < 1){
					double Xnew = k + xCount;
					
					double smoothed = FitData.getSmoothYValue(Xnew);
					System.out.println(Xnew+": >"+smoothed);
					
					fineValues[k][i] = smoothed;
					
					xCount += resolution;
					
				}
				System.out.println("-------------------------");
			}
		}
		
		System.out.println("hit 3 ");
		for(int i = 0; i < zDepth - 1; i++){
			for(int j = 0; j < this.totalFineWidth - 1/resolution; j++){
				this.addChild(getValueFace(j, i));
			}
		}
		System.out.println("hit 4");
	}
	

	/**
	 * Return a plane at the x/z position
	 * Helper method
	 * 
	 * @param x
	 * @param z
	 * @return
	 */
	private Plane2d getValueFace(int x, int z) {
		float theValue1 = (float)fineValues[x][z];
		float theValue2 = (float)fineValues[x+1][z];
		float theValue3 = (float)fineValues[x+1][z+1];
		float theValue4 = (float)fineValues[x][z+1];
		
		System.out.println(x+" ::::::  "+z);
		float x1 = (float)(x)/4;
		float x2 = (float)(x1 + resolution);
		
		System.out.println(x1 + ",,,,,,,,"+x2);
		
		Point3f A = new Point3f(x1    , getTransValue(theValue1), -z);
		Point3f B = new Point3f(x2, getTransValue(theValue2), -z );
		Point3f C = new Point3f(x2, getTransValue(theValue3), -z - 1);
		Point3f D = new Point3f(x1    , getTransValue(theValue4), -z - 1);
		
		Plane2d plane2d = new Plane2d(A, B, C, D, ColorTable.getColor(theValue1), false, x, z, theValue1, false);
		
		

		return plane2d;
	}
	
	private float getTransValue(float inValue){
		return (inValue/(float)GraphData.getMaxY())*fudgedScaleYMax;
	}
	
	
	public void modifyHeight(int x, int z, float value, int totalCount){
		
	}
}
