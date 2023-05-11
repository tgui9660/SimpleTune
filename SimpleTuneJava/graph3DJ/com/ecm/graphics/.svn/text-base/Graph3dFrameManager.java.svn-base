package com.ecm.graphics;

import java.awt.Frame;
import java.util.Vector;
import com.ecm.graphics.listeners.Window3dListener;

/**
 * Becuase of strange memory leaks in the Java3d code, this class keeps end users
 * from creating and destroying 3d scene graph instances. Instead each new graph is
 * just the initial graph plus a new graph3dtransform group with new 3d entities.
 * 
 * @author botman
 *
 */
public class Graph3dFrameManager{
	private static Frame graph3dJFrame = null;
	private static Graph3dJPanel graph3dJPanel = new Graph3dJPanel();
	
	/**
	 * Opens new graph3d JFrame with supplied values.
	 * 
	 * @param values
	 * @param xLabels
	 * @param zLabels
	 * @param xLabel
	 * @param yLabel
	 * @param zLabel
	 */
	public static void openGraph3dFrame(Vector values, double minY, double maxY, double[] xLabels, double[] zLabels, String xLabel, String yLabel, String zLabel, String frameTitle){
		//Define new Graph3d Jpanel
		graph3dJPanel.Init(values, minY, maxY, xLabels, zLabels, xLabel, yLabel, zLabel);
		
		//Build new JFrame
		if(graph3dJFrame == null){
			graph3dJFrame = new Graph3dFrame(graph3dJPanel, frameTitle);
			graph3dJFrame.addWindowListener(new Window3dListener());
			graph3dJFrame.setSize(400,400);
		}
		
		
		//Show JFrame
		graph3dJFrame.setVisible(true);
		
		
	}
	
	/**
	 * Closes JFrame and removed child 3d nodes from Main transform group
	 *
	 */
	public static void closeGraph3dFrame(){
		if(graph3dJFrame != null){
			graph3dJFrame.setVisible(false);
		}
		
		
		System.gc();
	}
	
}
