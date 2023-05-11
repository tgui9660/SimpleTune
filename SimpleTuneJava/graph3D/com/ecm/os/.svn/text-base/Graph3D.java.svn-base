package com.ecm.os;
import javax.swing.*;
import javax.vecmath.*;
import javax.media.j3d.*;
import java.util.*;

import com.sun.j3d.exp.swing.JCanvas3D;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.universe.*;

import java.awt.*;

public class Graph3D extends JCanvas3D
{
    private BoundingSphere bounds;
    private BranchGroup objRoot;        //root branch group
    private BranchGroup lightRoot;      //light attributes
    private BranchGroup boundaryBox;    //translucent boundary box
    private Graph graph;              	//3D shape
    private Grid grid;               	//wire grid
    private Axis axis;               	//holds axis and labels
    private TransformGroup labelTrans;  //transform group for z axis labels
    private TransformGroup objTrans;    //overall transformation group
    private Transform3D t3d;            //overall transformation 
    private Background bg;              //background color
    private double scale = .8;          //initial scale
    private Point3f lightPosition = new Point3f(.5f,.5f,1f);  	  //spotlight position
    private Color3f shapeColor = new Color3f(0f, .6f, .6f );      //plot color
    private Color3f backgroundColor = new Color3f(.6f,.6f,.6f);   //plot background color
    private PointLight p1;              //main spotlight 
    private PointLight p2;              //secondary spotlight
    private boolean shapeOff = false;   //shape displayed or not
    private GraphData graphData;
    private ArrayList labels;
    
    
    public Graph3D(){
        //super(SimpleUniverse.getPreferredConfiguration());
        init(new GraphData());
    }
    
    public Graph3D(GraphData graphData ){
        //super(SimpleUniverse.getPreferredConfiguration());
        init(graphData);
    }
    
    //Draws 3D graph
    public Graph3D( GraphicsConfiguration config, GraphData graphData )
    {
          //super(config);
          
          init(graphData);
    }

	private void init(GraphData graphData) {
		this.graphData = graphData;       
		      BranchGroup scene = createSceneGraph();
		      //SimpleUniverse u = new SimpleUniverse(this);
		      
		      SimpleUniverse u = new SimpleUniverse();
		      u.getViewingPlatform().setNominalViewingTransform();
		      u.addBranchGraph(scene);
	}
    
    /** Turn perspective on/off */
    public void setPerspectiveOn( boolean b ){
    	/*
        View view = getView();
	if( b) view.setProjectionPolicy( View.PERSPECTIVE_PROJECTION );
        else   view.setProjectionPolicy( View.PARALLEL_PROJECTION );
        */
    }
    
    /** Turn plot data on/off */
    public void setGraphOn( boolean b ){
	if( !b ){
          lightRoot.detach();
        } else {
          objRoot.addChild(lightRoot);
          p1.setColor(shapeColor);
          p2.setColor(shapeColor);
        }
        shapeOff = b;
        graph.setGraphOn(b);
    }
    
    /** Turn spotlights on/off */
    public void setLightOn( boolean b ){
        if( b ) {
          p1.setColor(new Color3f(1f,1f,1f));
          p2.setColor(new Color3f(1f,1f,1f));
        } else {
          p1.setColor(shapeColor);
          p2.setColor(shapeColor);
        }
    }
    
    public Color3f getBackgroundColor(){
	return backgroundColor;
    }
    
    /**Change the plot data color */
    public void setShapeColor(Color3f c){
        shapeColor = c;
        p1.setColor(c);
        p2.setColor(c);
    }
    
    public ArrayList getLabels(){
        return labels;
    }
    
        
    /** Creates lighting for the plot.  Use 2 pointlights and ambient light
        for best looking display.
    */
    private void makeLight(){
      lightRoot = new BranchGroup(); 
      lightRoot.setCapability(BranchGroup.ALLOW_DETACH);
      
      //main spotlight light
      p1 = new PointLight(shapeColor,lightPosition,new Point3f(.2f,.2f,.2f));
      p1.setCapability(PointLight.ALLOW_COLOR_WRITE);
      p1.setInfluencingBounds(bounds);     
      lightRoot.addChild(p1);
             
      //light to brighten up whole plot, secondary light
      p2 = new PointLight(shapeColor,new Point3f(0f,0f,5f),
                          new Point3f(.05f,.05f,.05f) );
      p2.setCapability(PointLight.ALLOW_COLOR_WRITE);
      p2.setInfluencingBounds(bounds);
      lightRoot.addChild(p2);
                                                        
      //ambient light
      AmbientLight al = new AmbientLight( new Color3f(1f,1f,1f) );
      al.setInfluencingBounds(bounds);
      lightRoot.addChild(al);
      objRoot.addChild(lightRoot);            
    }
    
    /** Create the Java3D basic sceneGraph and all necessary BranchGroups */
    private BranchGroup createSceneGraph() {
      // Create the root of the branch graph
      objRoot = new BranchGroup();
      objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
      objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
    
      bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100);
    
      // Set up the background
      bg = new Background(backgroundColor);
      bg.setCapability(Background.ALLOW_COLOR_WRITE);
      bg.setCapability(Background.ALLOW_COLOR_READ);
      bg.setApplicationBounds(bounds);
      objRoot.addChild(bg);

      // Create a TransformGroup to scale all objects so they
      // appear in the scene.
      objTrans = new TransformGroup();
      objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
      objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      t3d = new Transform3D();
      t3d.setScale(scale);
      t3d.setTranslation(new Vector3f(0f,-.2f, 0f));
      objTrans.setTransform(t3d);
      objRoot.addChild(objTrans);
    
      //light branch
      lightRoot = new BranchGroup();
        
      // Create the rotate behavior node
      MouseRotate behavior = new MouseRotate(objTrans);
      objTrans.addChild(behavior);
      behavior.setSchedulingBounds(bounds);

      // Create the zoom behavior node
      MouseZoom behavior2 = new MouseZoom(objTrans);
      objTrans.addChild(behavior2);
      behavior2.setSchedulingBounds(bounds);

      // Create the translate behavior node
      MouseTranslate behavior3 = new MouseTranslate(objTrans);
      objTrans.addChild(behavior3);
      behavior3.setSchedulingBounds(bounds);
           
      graph = new Graph(this, graphData);
      grid = new Grid( graph.getQuads() );
      axis = new Axis();
      boundaryBox = new BoundaryBox();
      
      //Ad objects to graph
      objTrans.addChild(graph);
      objTrans.addChild(grid);
      objTrans.addChild(axis);
      objTrans.addChild(boundaryBox);
       
      //scale all labels so that they aren't so big
      labelTrans = new TransformGroup();     
      Transform3D label3d = new Transform3D();
      label3d.setScale(.05);
      labelTrans.setTransform(label3d);
      objTrans.addChild(labelTrans);
  
      Labels label = new Labels(axis,graphData);
      labels = label.makeAxisLabels();
      for(int i=0; i<labels.size(); i++) labelTrans.addChild( (Shape3D)labels.get(i) );
      labelTrans.addChild( label.makeLabelsZ() );
      
      makeLight();
      
      // Let Java 3D perform optimizations on this scene graph.
      objRoot.compile();
      
      return objRoot;
    }

}

