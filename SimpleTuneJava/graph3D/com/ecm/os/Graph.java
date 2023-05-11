package com.ecm.os;
import javax.vecmath.*;
import javax.media.j3d.*;
import java.util.*;

//Computes the plot shape
public class Graph extends Shape3D {
    
    private static Color3f shapeColor = new Color3f(0f, .6f, .6f );
    private static Color3f black = new Color3f(0f, 0f, 0f );
    private static Color3f white = new Color3f(1f, 1f, 1f );
    private static Color3f white1 = new Color3f(.1f, .1f, .1f );
    private static Color3f white2 = new Color3f(.8f, .8f, .8f );

    private QuadArray quad;
    private Material material;
    private Graph3D parent;
    
    public Graph( Graph3D parent, GraphData graphData ){
        this.parent = parent;
	
        //Graph attributes
        Appearance app = new Appearance();
        PolygonAttributes pa = new PolygonAttributes();
        pa.setBackFaceNormalFlip(true);
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);
        app.setPolygonAttributes(pa);
        material = new Material(shapeColor,black,white1,white2,30.0f);
    	material.setLightingEnable(true);
        material.setCapability(Material.ALLOW_COMPONENT_READ);
        material.setCapability(Material.ALLOW_COMPONENT_WRITE);

        app.setMaterial(material);
    	
    	TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.NONE,0f);
        app.setTransparencyAttributes(ta);
        setAppearance(app);
        
        int rows = graphData.getRows();
        Double data[][] = graphData.getData();
        
        //Make shape quads from data points
        int numVerts = (rows)*(rows)*4;
	quad = new QuadArray(numVerts, QuadArray.COORDINATES | QuadArray.NORMALS );
        Point3f[] verts = new Point3f[numVerts];
        int i,j;
        
        int count = 0;
        float fi,fj;
 
        int ii=0;
        int jj=0;
        float rw = (float)rows;
        for(i=0; i<rows; i++) {
          for(j=0; j<rows; j++) {
            ii = i+1;
            jj = j+1;
            if( ii >= rows) ii = i;
            if( jj >= rows) jj = j;
            fi = (float)i/(float)rows;
            fj = (float)(j-rw/2f)/(float)(rows);
	    
            verts[count] = new Point3f( fj, data[i][j].floatValue(), fi);
            count++;
            
            fi = (float)(i+1)/(float)rows;
            fj = (float)(j-rw/2f)/(float)(rows);
	    	  
            verts[count] = new Point3f( fj, data[ii][j].floatValue(), fi);
            count++;
            
            fi = (float)(i+1)/(float)rows;
            fj = (float)(j+1-rw/2f)/(float)(rows);
	    	   
            verts[count] = new Point3f( fj, data[ii][jj].floatValue(), fi);
            count++;
            
            fi = (float)i/(float)rows;
            fj = (float)(j+1-rw/2f)/(float)(rows);
	    	   
            verts[count] = new Point3f( fj, data[i][jj].floatValue(), fi);
            count++;
          }
        }
	
	quad.setCoordinates(0, verts);
        
	Point3f [] pts = new Point3f[4];
	Vector3f normal = new Vector3f();
	Vector3f v1 = new Vector3f();
	Vector3f v2 = new Vector3f();
	
	//compute normals for lighting
	for (i = 0; i < 4; i++) pts[i] = new Point3f();
	int numG = rows*rows;	  
 	for (int face = 0; face<numG ; face++) {
	        quad.getCoordinates(face*4, pts);
	        v1.sub(pts[1], pts[0]);
	        v2.sub(pts[2], pts[0]);
	        normal.cross(v1, v2);
	        normal.normalize();
	        for (i = 0; i < 4; i++) quad.setNormal((face * 4 + i), normal);	        
	}    
	setGeometry(quad);
     }
	
     public QuadArray getQuads(){
	    return quad;
     }
	
	   
    /**Turn data plot on/off */
    public void setGraphOn( boolean b){
        //If off, change all its colors so that they match background color. This wiil allow for
        //nice wire frame appearance.   
        if( !b ){
          Color3f bc = parent.getBackgroundColor();
          material.setAmbientColor(bc);
          material.setEmissiveColor(bc);
          material.setDiffuseColor(bc);
          material.setSpecularColor(bc);
        } else {
          material.setAmbientColor(shapeColor);
          material.setEmissiveColor(black);
          material.setDiffuseColor(white1);
          material.setSpecularColor(white2);
        }
    
    }

 }
        
