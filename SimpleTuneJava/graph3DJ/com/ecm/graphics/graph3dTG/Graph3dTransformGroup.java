package com.ecm.graphics.graph3dTG;

import java.awt.Font;
import java.awt.Shape;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.Iterator;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
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
import com.ecm.graphics.render.flatFaceRender.Graph3dFlatFaceRender;
import com.ecm.graphics.render.flatFaceRender.Graph3dInterpolatedRender;
import com.ecm.graphics.render.flatFaceRender.Graph3dSmoothRender;
import com.ecm.graphics.render.flatFaceRender.Plane2d;
import com.ecm.graphics.render.flatFaceRender.ValuePlane;
import com.ecm.graphics.tools.ColorTable;


/**
 * 
 * @author botman
 * 
 */
public class Graph3dTransformGroup extends TransformGroup{
	private Color3f red = new Color3f(1, 0, 0);
	private Color3f green = new Color3f(0, 1, 0);
	private Color3f white = new Color3f(1, 1, 1);
	private Color3f black = new Color3f(0, 0, 0);
	private Color3f labelColor = new Color3f(1, 0, 0);
	
	private int xWidth = 0;
	private int zDepth = 0;
	private float fudgedScaleYMax;
	private float fudgedScaleYMin;
	private float percentScale;
	private ValuePlane[][] faces;
	
	private String xAxes = "";
	private String yAxes = "";
	private String zAxes = "";
	
	private double[] xAxesVals;
	private double[] zAxesVals;
	

	// Modified data listeners
	public Vector modifiedDataListeners = new Vector();
	
	private LinkedHashMap tempFaceHash = new LinkedHashMap();
	
	public Graph3dTransformGroup(){

		
		// Allow rotate etc.
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		this.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	}
	
	public void Init(double graphMinY, double graphMaxY, double[] xAxesVals, double[] zAxesVals, String xAxes, String yAxes, String zAxes, TransformGroup renderTG) {
		
		this.xAxesVals = xAxesVals;
		this.zAxesVals = zAxesVals;
		
		this.xAxes = xAxes;
		this.yAxes = yAxes;
		this.zAxes = zAxes;

		this.xWidth = GraphData.getXWidth();
		this.zDepth = GraphData.getZDepth();

		percentScale = (float)((((xWidth + zDepth)/2))/GraphData.getHeight())/4;
		fudgedScaleYMax = (float)GraphData.getMaxY()*percentScale;
		fudgedScaleYMin = (float)GraphData.getMinY()*percentScale;
		
		
		// **************************
		// Add all the children
		// **************************

		// Add the base grid
		this.removeAllChildren();
		
		
		Transform3D myTransform3D = new Transform3D();

		//Set position in center of screen
		float scale = (float)1.35/((GraphData.getXWidth()+GraphData.getZDepth())/2);
		float maxY = scale * this.getMaxCalc3dWorldHeight();
		float maxX = scale * GraphData.getXWidth();
		float maxZ = scale * GraphData.getZDepth();
		myTransform3D.setScale(scale);
		myTransform3D.setTranslation(new Vector3f(-maxX / 2, -maxY / 2,maxZ / 2));
		TransformGroup trans3dtg = new TransformGroup(myTransform3D);
		trans3dtg.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		
		BranchGroup totalBG = new BranchGroup();
		totalBG.setCapability(BranchGroup.ALLOW_DETACH);
		
		trans3dtg.addChild(renderTG);
		trans3dtg.addChild(new Graph3dFrame(fudgedScaleYMin, fudgedScaleYMax, xAxesVals,zAxesVals, xAxes, yAxes, zAxes));
		
		totalBG.addChild(trans3dtg);
		
		this.addChild(totalBG);
		
	}

	

	// *******************************
	// Misc getters and setters
	// *******************************

	public int getXWidth() {
		return xWidth;
	}

	public int getZDepth() {
		return zDepth;
	}

	public float getMaxCalc3dWorldHeight() {
		return fudgedScaleYMax;
	}

	public String getXAxes() {
		return xAxes;
	}

	public void setXAxes(String axes) {
		xAxes = axes;
	}

	public String getYAxes() {
		return yAxes;
	}

	public void setYAxes(String axes) {
		yAxes = axes;
	}

	public String getZAxes() {
		return zAxes;
	}

	public void setZAxes(String axes) {
		zAxes = axes;
	}
	
	public ValuePlane getValuePlane(int xCoord, int zCoord){
		return faces[xCoord][zCoord];
	}
}

