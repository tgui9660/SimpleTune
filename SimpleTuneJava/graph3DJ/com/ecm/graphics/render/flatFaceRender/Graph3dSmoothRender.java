package com.ecm.graphics.render.flatFaceRender;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;

import com.ecm.graphics.data.GraphData;
import com.ecm.graphics.tools.ColorTable;

public class Graph3dSmoothRender extends TransformGroup implements DataChanger{
	private int xWidth;
	private int zDepth;
	private float fudgedScaleYMax;
	
	public Graph3dSmoothRender(float fudgedScaleYMax){
		this.xWidth = GraphData.getXWidth();
		this.zDepth = GraphData.getZDepth();
		this.fudgedScaleYMax = fudgedScaleYMax;
		

		//Allow rotate etc.
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		
		//Add all the faces
		for(int i = 0; i < xWidth - 1; i++){
			for(int j = 0; j < zDepth - 1; j++){
				this.addChild(getValueFace(i, j));
			}
		}
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
		float theValue1 = GraphData.getCellValue(x, z);
		float theValue2 = GraphData.getCellValue(x+1, z);
		float theValue3 = GraphData.getCellValue(x+1, z+1);
		float theValue4 = GraphData.getCellValue(x, z+1);
		
		Point3f A = new Point3f(x +  .5f, getTransValue(theValue1), -z -  .5f);
		Point3f B = new Point3f(x + 1.5f, getTransValue(theValue2), -z -  .5f);
		Point3f C = new Point3f(x + 1.5f, getTransValue(theValue3), -z - 1.5f);
		Point3f D = new Point3f(x +  .5f, getTransValue(theValue4), -z - 1.5f);
		
		Plane2d plane2d = new Plane2d(A, B, C, D, ColorTable.getColor(theValue1), false, x, z, theValue1, false);
		
		

		return plane2d;
	}
	
	private float getTransValue(float inValue){
		return (inValue/(float)GraphData.getMaxY())*fudgedScaleYMax;
	}
	
	
	public void modifyHeight(int x, int z, float value, int totalCount){
		
	}
}
