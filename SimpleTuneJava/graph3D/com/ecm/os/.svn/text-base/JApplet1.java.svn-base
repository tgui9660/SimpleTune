package com.ecm.os;
/*
	A basic extension of the javax.swing.JApplet class
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.universe.*;


//Example of running Graph3D from an Applet
public class JApplet1 extends JApplet implements ActionListener
{
	private Graph3D graph3d;
	private JCheckBox perspectiveCheckbox = new JCheckBox("Perspective On");
	private JCheckBox fillCheckbox = new JCheckBox("Fill On");
	
	public void init()
	{
	    Border lineBorder = BorderFactory.createLineBorder(Color.black);
	    
	    //file to plot
            GraphData graphData = new GraphData("plot3D.dat");
            getContentPane().setLayout( new BorderLayout() );
            GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
	    
	    //graph3d = new Graph3D(config, graphData);
	    //graph3d = new Graph3D(graphData);
            
            graph3d = new Graph3D();
	    
        getContentPane().add( graph3d, BorderLayout.CENTER );
	    
	    JPanel optionPanel = new JPanel();
	    optionPanel.setBorder( lineBorder );
	    optionPanel.setLayout( new FlowLayout());
	    getContentPane().add( optionPanel, BorderLayout.SOUTH );
	    
	    optionPanel.add( perspectiveCheckbox );
	    optionPanel.add( fillCheckbox );
	    perspectiveCheckbox.setSelected(true);
	    fillCheckbox.setSelected(true);
	    
	    fillCheckbox.addActionListener( this );
	    perspectiveCheckbox.addActionListener( this );

	}
	
	//GUI Event Handler
	public void actionPerformed( ActionEvent evt ){
		JCheckBox source = (JCheckBox)evt.getSource();
		if( source == perspectiveCheckbox ){
	          graph3d.setPerspectiveOn( source.isSelected() );
		} else if( source == fillCheckbox ){
	          graph3d.setGraphOn( source.isSelected() );

		}
		
	}

	
}
