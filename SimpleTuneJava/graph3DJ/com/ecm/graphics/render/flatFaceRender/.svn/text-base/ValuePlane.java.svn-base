package com.ecm.graphics.render.flatFaceRender;

import java.awt.Font;
import java.awt.Shape;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Vector;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.ecm.graphics.data.GraphData;
import com.ecm.graphics.data.GraphDataCellListener;
import com.ecm.graphics.graph3dTG.Graph3dTransformGroup;
import com.ecm.graphics.tools.ColorTable;
import com.sun.j3d.utils.geometry.Text2D;

public class ValuePlane extends TransformGroup implements GraphDataCellListener{

	private Color3f white = new Color3f(1, 1, 1);
	private Color3f black = new Color3f(0, 0, 0);
	
	private Text3D myText3D = null;
	private float theValue = 0.0f;
	private float oldValue = 0.0f;
	private Plane2d plane2d;
	
	private Point3f A;
	private Point3f B;
	private Point3f C;
	private Point3f D;
	
	private int xCoord = 0;
	private int zCoord = 0;
	private float fudgeValue = 0;
	private TransformGroup planeTransformGroup = new TransformGroup();
	
	private DataChanger vpi;
	
	private BranchGroup wallBG = null;
	private BranchGroup textBG = null;
	
	private float translationSave = 0.0f;
	
	
	/**
	 * Constructor 
	 * 
	 * @param A
	 * @param B
	 * @param C
	 * @param D
	 * @param theValue
	 * @param maxY
	 * @param xCoord
	 * @param zCoord
	 * @param fudgeValue
	 * @param vpi
	 */
	public ValuePlane(Point3f A, Point3f B, Point3f C, Point3f D, float theValue, int xCoord, int zCoord, float fudgeValue, DataChanger vpi){
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
		
		this.xCoord = xCoord;
		this.zCoord = zCoord;
		
		this.fudgeValue = fudgeValue;
		this.vpi = vpi;
		
	    double X1 = 0;
	    double Y1 = 0;
	    double X2 = 0.01;
	    double Y2 = 0;
	    
	    
	  
	    this.theValue = theValue;
		
		//Plane 
		plane2d = new Plane2d(A, B, C, D, ColorTable.getColor(theValue), true, xCoord, zCoord, theValue, false);
		
		float xPosition = A.x;
		float zPosition = (A.z + C.z)/2;
		float yPosition = A.y;
		
		
		//this.translateY = Math.abs(this.minY);
		
		textBG = getTextBG(this.theValue);
		
		//Setup final transform group
		planeTransformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		planeTransformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		planeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		planeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		planeTransformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		planeTransformGroup.addChild(plane2d);
		planeTransformGroup.addChild(textBG);
		planeTransformGroup.addChild(getRing());
		
		//Set final transform to adjust for height
		this.modifyHeightInternal(theValue);
		
		//Add everything to "this" transform group
		this.addChild(planeTransformGroup);
	}
	
	/**
	 * Method returns text graphics object for passed value
	 * @param textValue
	 * @return
	 */
	private BranchGroup getTextBG(double textValue){
		BigDecimal roundfinalPrice = new BigDecimal(textValue).setScale(2,BigDecimal.ROUND_HALF_UP);
		Double doublePrice= new Double(roundfinalPrice.doubleValue()); 
		textValue = doublePrice.doubleValue();
		
		
		//Build new branchGroup to contain text
		BranchGroup textBG = new BranchGroup();
		
		//Allow branchGroup to be removed from parent node
		textBG.setCapability(BranchGroup.ALLOW_DETACH);
		
		
	    //Set the thickness of the text
		double X1 = 0;
	    double Y1 = 0;
	    double X2 = 0.01;
	    double Y2 = 0;
	    Shape extrusionShape = new java.awt.geom.Line2D.Double(X1, Y1, X2, Y2);
		FontExtrusion myExtrude = new FontExtrusion(extrusionShape);
		
		Appearance app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes(black,ColoringAttributes.SHADE_FLAT);
		app.setColoringAttributes(ca);
		
		
		//Build new 3d text object
		Font myFont = new Font("Arial",Font.BOLD, 1);
		Font3D myFont3D = new Font3D(myFont,myExtrude);
		myText3D = new Text3D(myFont3D, textValue+"");
		Shape3D myShape3D = new Shape3D(myText3D, app);
		
		
		//Move text into position, rotate, lay flat, position over right face
		float xPosition = A.x;
		float zPosition = (A.z + C.z)/2;
		float yPosition = A.y;
		
		TransformGroup myshapeTransformGroup = new TransformGroup();
		Transform3D myTransform3D = new Transform3D();
		myTransform3D.rotX(-Math.PI/2.0d);
		myTransform3D.setTranslation(new Vector3f(xPosition, .02f, zPosition));
		myTransform3D.setScale(0.35);
		myshapeTransformGroup.setTransform(myTransform3D);
		myshapeTransformGroup.addChild(myShape3D);
		
		textBG.addChild(myshapeTransformGroup);
		
		return textBG;
	}
	
	/**
	 * Method draws white bounding lines on face
	 * @return
	 */
	private TransformGroup getRing(){
		
		Vector myShapes = new Vector();
		Appearance app = new Appearance();
		LineAttributes la = new LineAttributes();
	    la.setLineWidth(2.0f);
	    app.setLineAttributes(la);
		ColoringAttributes ca = new ColoringAttributes(black,ColoringAttributes.SHADE_FLAT);
		app.setColoringAttributes(ca);

		Point3f[] lineStart = new Point3f[4];
		Point3f[] lineEnded = new Point3f[4];

		lineStart[0] = new Point3f(A);
		lineEnded[0] = new Point3f(B);

		lineStart[1] = new Point3f(B);
		lineEnded[1] = new Point3f(C);
		
		lineStart[2] = new Point3f(C);
		lineEnded[2] = new Point3f(D);
		
		lineStart[3] = new Point3f(D);
		lineEnded[3] = new Point3f(A);
		
		
		for (int i = 0; i < 4; i++) {

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

	public Point3f getA() {
		return A;
	}

	public Point3f getB() {
		return B;
	}

	public Point3f getC() {
		return C;
	}

	public Point3f getD() {
		return D;
	}

	public float getTheValue() {
		return theValue;
	}
	
	public Plane2d getPlane2d(){
		return this.plane2d;
	}
	
	
	/**
	 * Modify height, total count is number of elements to be changed in this
	 * height update batch, allows performance tuning by calling wall updates after
	 * all the faces are complete.
	 */
	public void modifyHeight(float value, int count){
		//Save off old height value
		float tempValue = value;
		this.oldValue = this.theValue;
		this.theValue = value;
		
		//Remove old 3d text object
		this.planeTransformGroup.removeChild(this.textBG);
		
		//Save off new text object for future removal
		textBG = this.getTextBG(value);
		
		
		Transform3D planeTransform = new Transform3D();
		this.translationSave = (theValue/(float)GraphData.getMaxY())*fudgeValue;
		planeTransform.setTranslation(new Vector3f(0.0f, this.translationSave, 0.0f));
		this.planeTransformGroup.setTransform(planeTransform);
		this.planeTransformGroup.addChild(textBG);
		
		
		// Call up to graph3dtransformgroup to update the walls of the faces
		vpi.modifyHeight(this.xCoord, this.zCoord, this.theValue, count);
		
	}

	/**
	 * Undo height change
	 */
	public void undoHeightChange(int totalCount){
		modifyHeight(this.oldValue, totalCount);
	}
	
	/**
	 * Internal use only height modification method
	 * 
	 * @param value
	 */
	private void modifyHeightInternal(float value){
		
		//Save off the old value, or 0 in this case
		this.oldValue = this.theValue;
		this.theValue = value;
		
		//Save off translation value for later possible undo
		this.translationSave = (theValue/(float)GraphData.getMaxY())*fudgeValue;
		
		//Define transform3d to apply
		Transform3D planeTransform = new Transform3D();
		planeTransform.setTranslation(new Vector3f(0.0f,this.translationSave , 0.0f));
		planeTransformGroup.setTransform(planeTransform);
	}
	
	
	public float getModifiedYValue(){
		return (theValue/(float)GraphData.getMaxY())*fudgeValue;
	}
	
	
	public float getOldValue(){
		return this.oldValue;
	}
	
	public void modifyHeight(int x, int z, float value, int count){
		
	}

	public BranchGroup getWallBG() {
		return wallBG;
	}

	public void setWallBG(BranchGroup wallBG) {
		this.wallBG = wallBG;
	}
	
	
	public void graphDataChanged(float value, int count, int type){
		this.modifyHeight(value, count);
		this.plane2d.theValue = value;
		
	}
	
	public void selectStateChanged(boolean value){
		this.plane2d.setSelected(value);
	}
}
