package com.ecm.graphics.render.flatFaceRender;

import java.awt.Font;

import com.ecm.graphics.tools.ColorTable;
import com.sun.j3d.utils.geometry.NormalGenerator;
import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.Text2D;

/**
 * Class draws a 2D surface visible from either side.
 * 
 * @author botman
 *
 */
public class Plane2d extends Shape3D {
	private Color3f selectedColor = ColorTable.getSelectedColor();
	private  Point3f A = null;
	private  Point3f B = null;
	private  Point3f C = null;
	private  Point3f D = null;
	
	private boolean isValuePlane = false;
	
	private Appearance app;
	
	private int xCoord = 0;
	private int zCoord = 0;
	
	private Color3f color;
	private Color3f oldColor;
	
	public float theValue;
	private double oldValue;
	private boolean isTwoSided = false;
	
	public Plane2d(Point3f A, Point3f B, Point3f C, Point3f D, Color3f color, boolean isValuePlane, int xCoord, int zCoord, float theValue, boolean isTwoSided) {
		this.isValuePlane = isValuePlane;
		this.color = color;
		this.theValue = theValue;
		this.oldValue = theValue;
		
		//Save points, might use em in the future
		this.A= A;
		this.B= B;
		this.C= C;
		this.D= D;
		
		this.xCoord = xCoord;
		this.zCoord = zCoord;
		this.isTwoSided = isTwoSided;
		
		renderPlane(A, B, C, D);
	}


	/**
	 * Render a 2d plane
	 * 
	 * @param A
	 * @param B
	 * @param C
	 * @param D
	 */
	private void renderPlane(Point3f A, Point3f B, Point3f C, Point3f D) {
		Point3f[] pts = null;
		int[] stripCounts = null;
		int[] contourCount = null;
		
		if(this.isTwoSided){
			pts = new Point3f[8];
				
			 // front
			pts[0] = C;
			pts[1] = D;
			pts[2] = A;
			pts[3] = B;
			
			// back
			pts[4] = C;
			pts[5] = B;
			pts[6] = A;
			pts[7] = D;
				
			stripCounts = new int[2];
			stripCounts[0] = 4;
			stripCounts[1] = 4;
			
			contourCount = new int[2];
			contourCount[0] = 1;
			contourCount[1] = 1;
		}else{
			pts = new Point3f[4];
			
			 // front
			pts[0] = C;
			pts[1] = D;
			pts[2] = A;
			pts[3] = B;
				
			stripCounts = new int[1];
			stripCounts[0] = 4;
			
			contourCount = new int[1];
			contourCount[0] = 1;
		}
		

		GeometryInfo gInf = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);

		gInf.setCoordinates(pts);
		gInf.setStripCounts(stripCounts);
		gInf.setContourCounts(contourCount);

		NormalGenerator ng = new NormalGenerator();
		ng.setCreaseAngle((float) Math.toRadians(30));
		ng.generateNormals(gInf);

		this.setGeometry(gInf.getGeometryArray());
		
		app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes(color,ColoringAttributes.SHADE_FLAT);
		app.setColoringAttributes(ca);
		app.setCapability (Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		
		this.setAppearance(app);
	}
	
	public void setSelected(boolean value){
		if(value == true){
			this.setEditColoring();
		}else{
			this.setValueColoring();
		}
	}
	
	/**
	 * Color to set when a selected face
	 *
	 */
	private void setEditColoring(){
		ColoringAttributes ca = new ColoringAttributes(selectedColor,ColoringAttributes.SHADE_FLAT);
		app.setColoringAttributes(ca);
	}

	/**
	 * Color shade representing face value
	 *
	 */
	private void setValueColoring(){ColoringAttributes ca = new ColoringAttributes(ColorTable.getColor(this.theValue),ColoringAttributes.SHADE_FLAT);
		app.setColoringAttributes(ca);
	}
	
	public boolean isValuePlane() {
		return isValuePlane;
	}
	
	public int getXCoord(){
		return this.xCoord;
	}
	
	public int getZCoord(){
		return this.zCoord;
	}
}
