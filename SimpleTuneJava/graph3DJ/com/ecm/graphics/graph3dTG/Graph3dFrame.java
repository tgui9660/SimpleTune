package com.ecm.graphics.graph3dTG;

import java.awt.Font;
import java.awt.Shape;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Vector;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.ecm.graphics.data.GraphData;

public class Graph3dFrame extends TransformGroup{
	private Color3f black = new Color3f(0, 0, 0);
	private Color3f red = new Color3f(1, 0, 0);
	private int xWidth;
	private int zDepth;
	private float fudgedScaleYMin;
	private float fudgedScaleYMax;
	private String xAxes;
	private String yAxes;
	private String zAxes;
	private double[] xAxesVals;
	private double[] zAxesVals;
	private FontExtrusion myExtrude;
	private Shape extrusionShape;
	
	public Graph3dFrame(float fudgedScaleYMin, float fudgedScaleYMax, double[] xAxesVals, double[] zAxesVals, String xAxes, String yAxes, String zAxes){
		this.xWidth = GraphData.getXWidth();
		this.zDepth = GraphData.getZDepth();
		this.fudgedScaleYMax = fudgedScaleYMax;
		this.fudgedScaleYMin = fudgedScaleYMin;
		this.zAxes = zAxes;
		this.yAxes = yAxes;
		this.xAxes = xAxes;
		this.xAxesVals = xAxesVals;
		this.zAxesVals = zAxesVals;
		
		//Allow rotate etc.
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		

		//use a customized FontExtrusion object to control the depth of the text
	    double X1 = 0;
	    double Y1 = 0;
	    double X2 = 0.01;
	    double Y2 = 0;
	    extrusionShape = new java.awt.geom.Line2D.Double(X1, Y1, X2, Y2);
	    myExtrude = new FontExtrusion(extrusionShape);
	    
		
		this.addChild(buildGrid());
		this.addChild(buildAxes());
		this.addChild(buildLabels());
	}

	
	
	/**
	 * Generate 3d text.
	 * 
	 * @param textValue
	 * @return
	 */
	private Shape3D getText(String textValue, int size){
		Appearance app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes(red,ColoringAttributes.SHADE_FLAT);
		app.setColoringAttributes(ca);
		
		Font myFont = new Font("Arial",Font.BOLD, size);
		Font3D myFont3D = new Font3D(myFont,myExtrude);
		Text3D myText3D = new Text3D(myFont3D, textValue);
		Shape3D myShape3D = new Shape3D(myText3D, app);
		
		return myShape3D;
	}
	
	/**
	 * Build up labels
	 * 
	 * @return
	 */
	private TransformGroup buildLabels(){
		Transform3D finalTransform = new Transform3D();
		finalTransform.setScale(0.6);
		
		TransformGroup labelTransformGroup = new TransformGroup();
		
		Shape3D xLabel3D = getText(xAxes, 2);
		Shape3D yLabel3D = getText(yAxes, 2);
		Shape3D zLabel3D = getText(zAxes, 2);
		
		//Build up the xLabel
		Transform3D myTransform3DX = new Transform3D();
		myTransform3DX.setTranslation(new Vector3f(this.xWidth/4, this.fudgedScaleYMax+2, -this.zDepth));
		myTransform3DX.mul(finalTransform);
		TransformGroup xTransformGroup = new TransformGroup(myTransform3DX);
		xTransformGroup.addChild(xLabel3D);

		//Build up the yLabel
		Transform3D myTransform3DY = new Transform3D();
		myTransform3DY.setTranslation(new Vector3f(1.0f, this.fudgedScaleYMin, 0.0f));
		myTransform3DY.mul(finalTransform);
		TransformGroup yTransformGroup = new TransformGroup(myTransform3DY);
		yTransformGroup.addChild(yLabel3D);

		//Build up the zLabel
		Transform3D myTransform3DZ = new Transform3D();
		myTransform3DZ.rotY(-Math.PI/2.0d);
		myTransform3DZ.setTranslation(new Vector3f(this.xWidth, this.fudgedScaleYMax+2, -3*this.zDepth/4));
		myTransform3DZ.mul(finalTransform);
		TransformGroup zTransformGroup = new TransformGroup(myTransform3DZ);
		zTransformGroup.addChild(zLabel3D);
		
		
		//Build minor x label values
		for(int i = 0; i < this.xWidth; i++){
			
			BigDecimal roundfinalPrice = new BigDecimal(this.xAxesVals[i]).setScale(2,BigDecimal.ROUND_HALF_UP);
			Double doublePrice= new Double(roundfinalPrice.doubleValue()); 
			Shape3D minorXLabel = getText(doublePrice.toString(), 1);
			//System.out.println("Minor value:"+doublePrice.toString());
			
			float position = (float)(i);
			
			Transform3D xTrans3D = new Transform3D();
			xTrans3D.setTranslation(new Vector3f(position, this.fudgedScaleYMax+1, -this.zDepth));
			xTrans3D.mul(finalTransform);
			xTrans3D.setScale(0.3);
			
			TransformGroup xTrans3DGroup = new TransformGroup(xTrans3D);
			xTrans3DGroup.addChild(minorXLabel);
			labelTransformGroup.addChild(xTrans3DGroup);
		}
		
		//Build minor y label values
		for(int i = 0; i < this.zDepth; i++){
			Shape3D minorXLabel = getText(this.zAxesVals[i]+"", 1);
			
			float position = -(this.zDepth-(float)(i));
			
			Transform3D xTrans3D = new Transform3D();
			xTrans3D.rotY(-Math.PI/2.0d);
			xTrans3D.setTranslation(new Vector3f(this.xWidth, this.fudgedScaleYMax+1, position));
			xTrans3D.mul(finalTransform);
			xTrans3D.setScale(0.3);
			
			TransformGroup xTrans3DGroup = new TransformGroup(xTrans3D);
			xTrans3DGroup.addChild(minorXLabel);
			labelTransformGroup.addChild(xTrans3DGroup);
		}
		
		//Add labels to labels tansform group
		labelTransformGroup.addChild(xTransformGroup);
		labelTransformGroup.addChild(yTransformGroup);
		labelTransformGroup.addChild(zTransformGroup);
		
		
		return labelTransformGroup;
	}

	/**
	 * Build axes lines
	 * 
	 * @return
	 */
	private TransformGroup buildAxes() {
		float maxHeight = fudgedScaleYMax+1;
		
		Vector myShapes = new Vector();
		Appearance app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes(black,ColoringAttributes.SHADE_FLAT);
		app.setColoringAttributes(ca);

		Point3f[] lineStart = new Point3f[3];
		Point3f[] lineEnded = new Point3f[3];

		lineStart[0] = new Point3f(xWidth, 0.0f, -zDepth);
		lineEnded[0] = new Point3f(xWidth,maxHeight, -zDepth);

		lineStart[1] = new Point3f(xWidth,maxHeight, -zDepth);
		lineEnded[1] = new Point3f(0.0f,maxHeight, -zDepth);
		
		lineStart[2] = new Point3f(xWidth,maxHeight, -zDepth);
		lineEnded[2] = new Point3f(xWidth,maxHeight, 0.0f);
		
		
		for (int i = 0; i < 3; i++) {
			Point3f[] plaPts = new Point3f[2];
			plaPts[0] = lineStart[i];
			plaPts[1] = lineEnded[i];

			LineArray pla = new LineArray(2, LineArray.COORDINATES);
			pla.setCoordinates(0, plaPts);
			Shape3D plShape = new Shape3D(pla, app);
			myShapes.add(plShape);
		}

		// Build up the transform group
		TransformGroup axesTransformGroup = new TransformGroup();
		Iterator childNodeInterator = myShapes.iterator();
		while (childNodeInterator.hasNext()) {
			Shape3D myshape3d = (Shape3D) childNodeInterator.next();
			axesTransformGroup.addChild(myshape3d);
		}

		return axesTransformGroup;
	}
	
	/**
	 * Method builds base grid lines.
	 * 
	 */
	private TransformGroup buildGrid() {
		// Draw base grid
		// Points to build the graph up

		Vector myShapes = new Vector();
		Appearance app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes(black,ColoringAttributes.SHADE_FLAT);
		app.setColoringAttributes(ca);

		int totalLineNumber = (xWidth + zDepth + 2)*2;
		
		Point3f[] lineStart = new Point3f[totalLineNumber];
		Point3f[] lineEnded = new Point3f[totalLineNumber];

		int totalCount = 0;
		for (int i = 0; i < xWidth + 1; i++) {
			lineStart[i] = new Point3f((float) i, this.fudgedScaleYMin, 0.0f);
			lineEnded[i] = new Point3f((float) i, this.fudgedScaleYMin, -(float) zDepth);
			
			totalCount++;
		}
		
		for (int i = xWidth + 1; i < (xWidth + 1)*2; i++) {
			lineStart[i] = new Point3f((float) i - (xWidth+1), this.fudgedScaleYMin, -(float) zDepth);
			lineEnded[i] = new Point3f((float) i - (xWidth+1), (float)(this.fudgedScaleYMax + 1.5), -(float) zDepth);
			
			totalCount++;
		}
		
		for (int i = totalCount; i < (xWidth + 1)*2 + zDepth +1; i++) {
			lineStart[i] = new Point3f(0.0f, this.fudgedScaleYMin, -(((xWidth + 1)*2 + zDepth)-(float)i ));
			lineEnded[i] = new Point3f((float) xWidth, this.fudgedScaleYMin, -(((xWidth + 1)*2 + zDepth)-(float)i ));
			
			totalCount++;
		}
		
		for (int i = totalCount; i < (xWidth + 1)*2 + (zDepth +1)*2; i++) {
			lineStart[i] = new Point3f((float) xWidth, this.fudgedScaleYMin, -(((xWidth + 1)*2 + (zDepth +1)*2) -(float) i)+1);
			lineEnded[i] = new Point3f((float) xWidth, (float)(this.fudgedScaleYMax + 1.5), -(((xWidth + 1)*2 + (zDepth +1)*2) -(float) i)+1);
			
			totalCount++;
		}
		
		
		// Add lines to vector of shapes
		for (int i = 0; i < totalCount; i++) {

			Point3f[] plaPts = new Point3f[2];
			plaPts[0] = lineStart[i];
			plaPts[1] = lineEnded[i];

			LineArray pla = new LineArray(2, LineArray.COORDINATES);
			pla.setCoordinates(0, plaPts);
			Shape3D plShape = new Shape3D(pla, app);
			myShapes.add(plShape);

		}

		// Build up the transform group
		TransformGroup gridTransformGroup = new TransformGroup();
		Iterator childNodeInterator = myShapes.iterator();
		while (childNodeInterator.hasNext()) {
			Shape3D myshape3d = (Shape3D) childNodeInterator.next();
			gridTransformGroup.addChild(myshape3d);
		}

		return gridTransformGroup;
	}
}
