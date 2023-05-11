package com.ecm.os;
import javax.vecmath.*;
import javax.media.j3d.*;


//Draws the black grid on top of 3D plot surface
public class Grid extends Shape3D {

    private static Color3f gridColor = new Color3f(.2f, .2f, .2f );
 
    public Grid( QuadArray quad ){       
        //Grid attributes
        LineAttributes la = new LineAttributes();
        la.setLineAntialiasingEnable(true);
        Appearance app = new Appearance();
        PolygonAttributes pa1 = new PolygonAttributes();
        pa1.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        pa1.setCullFace(PolygonAttributes.CULL_NONE);
        pa1.setPolygonOffset(-1000f);
        app.setPolygonAttributes(pa1);
        app.setLineAttributes(la);        
        
        ColoringAttributes ca = new ColoringAttributes(gridColor,ColoringAttributes.NICEST);
        app.setColoringAttributes(ca);
        setAppearance(app);
        
	setGeometry(quad);       
    }   
}