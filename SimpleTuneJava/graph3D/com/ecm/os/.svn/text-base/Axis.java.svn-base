package com.ecm.os;
import javax.swing.*;
import javax.vecmath.*;
import javax.media.j3d.*;

//Draws the axis & lines for translucent bounding box
public class Axis extends Shape3D {
    
    private static Color3f axisColor = new Color3f(1f, 1f, 0f );

    private float verts[] = {    -.5f,1f,0f,    -.5f,0f,0f,
                                 -.5f,0f,1f,     .5f,0f,1f,
                                 -.55f,.5f,0f,   .5f,.5f,0f,
                                 -.55f,1f,0f,    .50f,1f,0f,
                                 -.5f,0f,0f,    -.5f,0f,1.05f,
                                  0f,0f,0f,      0f,0f,1.05f,
                                  .5f,0f,1.05f,  .5f,0f,0f,
                                 -.55f,0f,.5f,   .50f,0f,.5f,
                                 -.55f,0f,1f,    .50f,0f,1f,
                                  0f, 0f, 0f,    0f, 1f, 0f,
                                  0.5f, 0f, 0f,  0.5f, 1f, 0f,
                                 -.55f, 0f, 0f,  .5f, 0f, 0f,
                                 .5f, .5f, 0f,   .5f, .5f, 1f,
                                 .5f, 1f, 0f,    .5f, 1f, 1f,
                                 .5f, 1f, 1f,    .5f, 0f, 1f,
                                 .5f, 1f, .5f,   .5f, 0f, .5f };
    
    
    public Axis(){
        
        //Make Axis
        LineAttributes la1 = new LineAttributes();
        la1.setLineWidth(1.5f);
        Appearance app = new Appearance();
        app.setLineAttributes(la1);
        
        ColoringAttributes ca = new ColoringAttributes(axisColor,ColoringAttributes.FASTEST);
        app.setColoringAttributes(ca);
        setAppearance(app);
        LineArray lines = new LineArray(verts.length,LineArray.COORDINATES);
        Point3f[] lineVerts = new Point3f[verts.length/3];
        
        int cnt = 0;
        for(int i=0; i<verts.length; i+=3){
            lineVerts[cnt] = new Point3f(verts[i], verts[i+1], verts[i+2]);
            cnt++;
        }
        
        lines.setCoordinates(0,lineVerts);       
	setGeometry(lines);
    }
	
    /**Return axis color */
    public Color3f getAxisColor(){
        return axisColor;
    }
    
}
