package com.ecm.graphics;

import com.ecm.graphics.data.GraphData;
import com.ecm.graphics.data.GraphDataCellCoordinate;
import com.ecm.graphics.graph3dTG.Graph3dTransformGroup;
import com.ecm.graphics.render.flatFaceRender.Graph3dFlatFaceRender;
import com.ecm.graphics.render.flatFaceRender.Graph3dSmoothRender;
import com.ecm.graphics.render.flatFaceRender.Plane2d;
import com.sun.j3d.exp.swing.JCanvas3D;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.image.TextureLoader;

import javax.media.j3d.*;
import javax.swing.JPanel;
import javax.vecmath.*;

import java.util.Vector;
import java.util.Iterator;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class Graph3dJPanel extends JPanel implements KeyListener, MouseListener{

	// General 3d graph bits and parts
	private SimpleUniverse univ = null;
	private BranchGroup scene = null;
	private PickCanvas pickCanvas = null;
	//private Canvas3D canvas3d = null;
	private JCanvas3D jCanvas3D = null;
	//private BranchGroup contentBranchGroup = null;
	
	// Save keymodification state
	private boolean shiftModified = false;
	private boolean altModified = false;
	private boolean ctrlModified = false;

	// Selected faces
	//private Vector selectedFaces = new Vector();
	private Graph3dTransformGroup graph3dTransform = new Graph3dTransformGroup();

	// Track diagonal of mass selection
	private int xDiag1 = -1;
	private int zDiag1 = -1;
	private int xDiag2 = -1;
	private int zDiag2 = -1;
	private int oldXDiag2 = -1;
	private int oldZDiag2 = -1;

	// Modified value being build up
	private String newValue = "";
	
	// Saved constructor values
	private Vector values;
	private Vector oldValues;
	private double[] xLabels;
	private double[] zLabels;
	private String xLabel;
	private String yLabel;
	private String zLabel;
	
	private BoundingSphere bounds;
	private int frameWidth = 800;
	private int frameHeight = 600;
	
	private double maxX;
	private double maxY;
	
	private int renderKeyCode = 112;
	
	//Track mouse click count
	private int clickCount = 0;
	
	/**
	 * Graph 3d Constructor.
	 * 
	 * All fields must be used when instantiating. No NULL data!
	 * 
	 * @param values
	 * @param xLabels
	 * @param zLabels
	 * @param xLabel
	 * @param yLabel
	 * @param zLabel
	 */
	public void Init(Vector values, double minY, double maxY, double[] xLabels, double[] zLabels, String xLabel, String yLabel, String zLabel) {
		
		if(this.values != null){
			this.oldValues = this.values;
		}else{
			GraphData.InitGraphData(values, minY, maxY);
			this.oldValues = values;
		}
		this.values = values;
		this.xLabels = xLabels;
		this.zLabels = zLabels;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.zLabel = zLabel;
		this.maxX = minY;
		this.maxY = maxY;
		
		
		
		if(this.values.equals(this.oldValues)){
			//System.out.println("Same render set.");
		}else{
			//First initialize the GraphData
			GraphData.InitGraphData(values, minY, maxY);
		}
		
		//Define renderer
		int xWidth = GraphData.getXWidth();
		int zDepth = GraphData.getZDepth();

		float percentScale = (float)((((xWidth + zDepth)/2))/GraphData.getHeight())/4;
		float fudgedScaleYMax = (float)GraphData.getMaxY()*percentScale;
		
		
		TransformGroup renderTG = null;
		if(this.renderKeyCode == 112){
			renderTG = new Graph3dFlatFaceRender( fudgedScaleYMax);
		}
		if(this.renderKeyCode == 113){
			renderTG = new Graph3dSmoothRender( fudgedScaleYMax);
		}
		
		
		//Build 3d scape
		graph3dTransform.Init(this.maxX, this.maxY, this.xLabels, this.zLabels, this.xLabel, this.yLabel, this.zLabel, renderTG);
		
		
	}
	
	

	public Graph3dJPanel(){
		//Define JFrame attributes
		this.setLayout(new java.awt.BorderLayout());
		this.setPreferredSize(new java.awt.Dimension(frameWidth, frameHeight));
		
		//Initialize components
		this.initComponents();
	}
	
	/**
	 * Initialize all the 3d related objects
	 *
	 */
	public void initComponents() {
		
		
		
		// Create Canvas3D and SimpleUniverse; add canvas to drawing panel
		//Get the preferred graphics configuration for the default screen
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		//canvas3d = new Canvas3D(config);
		jCanvas3D = new JCanvas3D();
		jCanvas3D.setResizeMode(JCanvas3D.RESIZE_IMMEDIATELY);
		jCanvas3D.addMouseListener(this);
		jCanvas3D.addKeyListener(this);
		

		//Create simple universe with view branch
		//univ = new SimpleUniverse(canvas3d);
		
		//Dimension dim = new Dimension(100,100);
		//canvas3d.setPreferredSize(dim);
		//canvas3d.setSize(dim);
		jCanvas3D.setSize(100, 100);
		Canvas3D offscreenCanvas3D = jCanvas3D.getOffscreenCanvas3D();
	//	offscreenCanvas3D.setSize(100, 100);
		univ = new SimpleUniverse(offscreenCanvas3D);

		// add mouse behaviors to the ViewingPlatform
		//ViewingPlatform viewingPlatform = univ.getViewingPlatform();
		// add orbit behavior to the ViewingPlatform
		/*
		OrbitBehavior orbit = new OrbitBehavior(canvas3d, OrbitBehavior.REVERSE_ALL);
		bounds = new BoundingSphere(new Point3d(0.0f, 0.0f, 0.0f), 100.0);
		// BoundingSphere bounds = new BoundingSphere();
		orbit.setSchedulingBounds(bounds);
		viewingPlatform.setViewPlatformBehavior(orbit);
	*/
		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed.
		univ.getViewingPlatform().setNominalViewingTransform();

		// Ensure at least 5 msec per frame (i.e., < 200Hz)
		univ.getViewer().getView().setMinimumFrameCycleTime(5);

		//Test code, might need to turn on for OpenGL Nvidia rendering in windows because of Nvidia driver error
		univ.getViewer().getView().setSceneAntialiasingEnable(false);
		
		// Create the content branch and add it to the universe
		scene = createSceneGraph();
		//pickCanvas = new PickCanvas(canvas3d, scene);
		//pickCanvas.setMode(PickCanvas.BOUNDS);
		univ.addBranchGraph(scene);
		
		

		//Add canvas to JPanel
		this.add(jCanvas3D, java.awt.BorderLayout.CENTER);
	}
	
	
	/**
	 * As method name suggests, this method creates the scene graph
	 * 
	 * @return
	 */
	public BranchGroup createSceneGraph() {
		BranchGroup contentBranchGroup = getGraphBG(graph3dTransform);
		
		//Allow to be removed from the parent
		contentBranchGroup.setCapability(BranchGroup.ALLOW_DETACH);
		contentBranchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		contentBranchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		contentBranchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		
		return contentBranchGroup;
	}

	private BranchGroup getGraphBG(Graph3dTransformGroup graph3dTransform) {
		

		Transform3D myTransform3D = new Transform3D();
		myTransform3D.rotX(Math.PI/4.0d);
		TransformGroup graphTransformGroup = new TransformGroup(myTransform3D);
		graphTransformGroup.addChild(graph3dTransform);
		
		//Set background color to white
		Color3f bgColor = new Color3f(1, 1, 1);
		String separator = System.getProperty("file.separator");
		String imageLocation = "graphics"+separator+"3d_Background.jpg";
		
		boolean exists = (new File(imageLocation)).exists();
		Background bgNode = null;
		if(exists){
			TextureLoader backgroundTexture = new TextureLoader(imageLocation, null);
			bgNode = new Background(backgroundTexture.getImage());
			bgNode.setImageScaleMode(Background.SCALE_FIT_ALL);
		}else{
			bgNode = new Background(bgColor);
		}
		bgNode.setApplicationBounds(bounds);
		
		BranchGroup graphBG = new BranchGroup();
		graphBG.addChild(graphTransformGroup);
		graphBG.addChild(bgNode);
		graphBG.setCapability(BranchGroup.ALLOW_DETACH);
		graphBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		graphBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		
		
		BranchGroup contentBranchGroup = new BranchGroup();
		contentBranchGroup.addChild(graphBG);
		return contentBranchGroup;
	}

	// *********************************************************
	// Process key events
	// *********************************************************

	public void keyTyped(KeyEvent evt) {
	}

	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
		int modified = evt.getModifiers();
		
		//System.out.println("Key code:"+key);
		
		// Print out the license and readme info
		if(key == 76){
			System.err.println(" README\n\nThis is the initial test release of the graph 3d editing project.\nNo warranty,  no help if I don't care to. Not yet that is.... \nThis test graph util may only be used within the realms of the Engiuity program.\nThis program (and all included files) may not be modified or used for commercial purposes.\nThis program may not be distributed by any other means besides that of the Enginuity main website.\nI can be contacted @ eric.c.morgan@gmail.com concerning this sofware.");
			System.out.println(" README\n\nThis is the initial test release of the graph 3d editing project.\nNo warranty, no source, no help if I don't care to. Not yet that is.... \nThis test graph util may only be used within the realms of the Engiuity program.\nThis program (and all included files) may not be modified or used for commercial purposes.\nThis program may not be distributed by any other means besides that of the Enginuity main website.\nI can be contacted @ eric.c.morgan@gmail.com concerning this sofware.");
		}
		

		// Flag shift modifier application
		if (modified == evt.SHIFT_MASK) {
			shiftModified = true;
			
			processShiftModification(key);
		}
		
		//Rect selection
		if (modified == evt.ALT_MASK) {
			this.altModified = true;
		}
		
		if (modified == evt.CTRL_MASK) {
			this.ctrlModified = true;
			// Detect only number key presses
			if((key < 58 && key > 47)||key == 46 || key == 45){
				char value = evt.getKeyChar();
				this.newValue = this.newValue + value;
			}
			
			// Detect r key, replace selected face heights to entered number
			if(key == 82){
				if(this.newValue != ""){
					double newDoubleValue = Double.parseDouble(this.newValue);
					GraphData.changeCellValue((float)newDoubleValue, GraphData.REPLACE);
				}
				
				this.newValue = "";
			}
			
 
			// Detect i key, increment face value
			if(key == 73){
				if(this.newValue != ""){
					double newDoubleValue = Double.parseDouble(this.newValue);
					GraphData.changeCellValue((float)newDoubleValue, GraphData.INCRIMENT);
				}
				this.newValue = "";
			}
			
			//Detect 'a' key to set all to selected
			if(key == 65){
				GraphData.selectAllCells();
			}
			
			//Detect 'v' key to attempt paste
			if(key == 86){
				GraphData.pasteAtCoord();
			}
			
			//Detect 'c' key for copying selected items to scratch pad
			if(key == 67){
				GraphData.copySelectedToScratchPad();
			}
		}
		
		// Detect 'u' key press, undo height change of selected faces
		if(key == 85){
			GraphData.undoValueChange();
		}
		
		//Detect '<' smooth data where possible in the x direction
		if(key == 44){
			GraphData.smoothXData();
		}

		//Detect '>' smooth data where possible in the Z direction
		if(key == 46){
			GraphData.smoothZData();
		}
		
		//Detect '/' smooth data where possible in the y & x directions, ala full smooth
		if(key == 47){
			GraphData.totalSmooth();
		}
		
		//Detect smooth render
		if(key == 112){
			if(this.renderKeyCode != 112){
				this.renderKeyCode = 112;
				this.Init(values, maxX, maxY, xLabels, zLabels, xLabel, yLabel, zLabel);
			}
		}
		
		//Detect face render
		if(key == 113){
			if(this.renderKeyCode != 113){
				this.renderKeyCode = 113;
				this.Init(values, maxX, maxY, xLabels, zLabels, xLabel, yLabel, zLabel);
			}
		}
	}

	/**
	 * Given that a shift modifier is applied, handle as needed.
	 * 
	 * @param key
	 */
	private void processShiftModification(int key) {
		int xModify = 0;
		int zModify = 0;

		//Define translation direction
		if (key == KeyEvent.VK_LEFT) {
			xModify--;
		} else if (key == KeyEvent.VK_RIGHT) {
			xModify++;
		} else if (key == KeyEvent.VK_UP) {
			zModify++;
		} else if (key == KeyEvent.VK_DOWN) {
			zModify--;
		}

		// Can only start arrow selection if there is at least one face already
		if (GraphData.getSelectedCount() > 0 && (xModify != 0 || zModify != 0)) {
			int xNew = GraphData.getDiagSelectTwo().getX() + xModify;
			int zNew = GraphData.getDiagSelectTwo().getZ() + zModify;
			
			if(xNew < GraphData.getXWidth() && zNew < GraphData.getZDepth() && xNew >= 0 && zNew >= 0){
				GraphDataCellCoordinate newCoord = new GraphDataCellCoordinate(xNew, zNew);

				GraphData.setDiagSelectTwo(newCoord);
				GraphData.drawRectDiagonalSelection();
			}
		
		}
	}
	
	public void keyReleased(KeyEvent evt) {
		int keycode = evt.getKeyCode();
		if (keycode == 16) {
			shiftModified = false;

			// Set to no diagonal selection
			this.xDiag1 = -1;
			this.zDiag1 = -1;
			this.xDiag2 = -1;
			this.zDiag2 = -1;
			
			GraphData.setDiagSelectOne(null);
			GraphData.setDiagSelectTwo(null);
		}
		if (keycode == 17) {
			this.ctrlModified = false;
			this.newValue = "";
		}
		
		if (keycode == 18) {
			this.altModified = false;
			
			
		}
	}

	// *********************************************************
	// Mouse event listeners
	// *********************************************************
	
	public void mousePressed(MouseEvent evt) {
	}
	public void mouseEntered(MouseEvent evt) {
	}
	public void mouseExited(MouseEvent evt) {
	}
	public void mouseReleased(MouseEvent evt) {
	} 
	
	/**
	 * Listen for mouse events
	 */
	public void mouseClicked(MouseEvent e)

	{
		this.clickCount = e.getClickCount();
		
		if (e.getClickCount() == 1) {
			
			// Set to no diagonal selection
			this.xDiag1 = -1;
			this.zDiag1 = -1;
			this.xDiag2 = -1;
			this.zDiag2 = -1;
			this.oldXDiag2 = -1;
			this.oldZDiag2 = -1;
			
			pickCanvas.setShapeLocation(e);

			PickResult result = pickCanvas.pickClosest();

			if (result == null) {
			} else {

				Shape3D s = (Shape3D) result.getNode(PickResult.SHAPE3D);

				if (s != null) {
					if (s.getClass().getName().contains("Plane2d")) {
						Plane2d thePlane = (Plane2d) s;
						if (thePlane.isValuePlane()) {

							
							// Remove all selected faces from the faces vector
							// if no shift modifier is applied
							if (shiftModified == false) {
								GraphData.deSelectAllCells();
							}
							
							//Flip selected state
							GraphDataCellCoordinate tempCoord =  new GraphDataCellCoordinate(thePlane.getXCoord(), thePlane.getZCoord());
							GraphData.flipSelectedValue(tempCoord);
							
							//Set to first diag select
							if(GraphData.getDiagSelectOne() == null){
								GraphData.setDiagSelectOne(GraphData.getLastSelectedCoord());
							}
						}
					}
				}
			}
		}
		if (e.getClickCount() > 1) {
			if(this.shiftModified){
				if(GraphData.getDiagSelectOne() != null){
					GraphData.setDiagSelectTwo(GraphData.getLastSelectedCoord());
					GraphData.drawRectDiagonalSelection();
				}
			}
		}
	}
	
	// ****************************************************************
	// Method to remove graph branchgroup when new graph is loaded
	// Fixes memory leak
	// ****************************************************************
	public void shutdownCleanup(){
		/*
		if(canvas3d != null){
			
			System.out.println("Clean up of graph called.");
			
			this.pickCanvas = null;
			
			this.univ.getLocale().removeBranchGraph(scene);
			com.sun.j3d.utils.universe.Viewer.clearViewerMap();
			
			//Attemp to get around memory leak
			BranchGroup emptyGroup = new BranchGroup();
			emptyGroup.setCapability(BranchGroup.ALLOW_DETACH);
			this.univ.getLocale().addBranchGraph(emptyGroup);
			this.univ.getLocale().removeBranchGraph(emptyGroup);
			emptyGroup = null;
			 
			
			
			com.sun.j3d.utils.universe.Viewer.clearViewerMap();
			this.univ.removeAllLocales();
			this.univ.cleanup();
			
			//(this) is a JPanel, removing canvas component
			this.remove(canvas3d);
		}
		
		System.gc();
		*/
	}
	
	/*
	public void addNewGraphBG(Vector values, double minY, double maxY, double[] xLabels, double[] zLabels, String xLabel, String yLabel, String zLabel){
		this.values = values;
		this.xLabels = xLabels;
		this.zLabels = zLabels;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.zLabel = zLabel;
		

		//First initialize the GraphData
		GraphData.InitGraphData(values, minY, maxY);
		
		this.graph3dTransform = new Graph3dTransformGroup(values, maxX, maxY, xLabels, zLabels, xLabel, yLabel, zLabel);
		
		this.initComponents();
	}
	*/
}
