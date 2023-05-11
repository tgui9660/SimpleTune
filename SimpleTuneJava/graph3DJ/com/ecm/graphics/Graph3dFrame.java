package com.ecm.graphics;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main Graph3d
 * 
 * @author botman
 *
 */
public class Graph3dFrame extends Frame{

	private JPanel drawingPanel = null;
	private String title = null;
	
	public Graph3dFrame(JPanel drawingPanel, String title){
		this.drawingPanel = drawingPanel;
		this.title = title;
		init();
	}
	
	public void init(){
		//this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle(title);
		this.add(drawingPanel);
		
		//this.getContentPane().add(drawingPanel,java.awt.BorderLayout.CENTER);
		this.pack();
	}
	
}
