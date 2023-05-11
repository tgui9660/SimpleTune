package com.ecm.os;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.universe.*;

//Example of running Graph3D inside of a standalone JFrame
//http://java.sys-con.com/node/216381?page=0,1
public class Standalone extends JFrame implements ActionListener
{
	private Graph3D graph3d;
	private JCheckBox perspectiveCheckbox = new JCheckBox("Perspective On");
	private JCheckBox fillCheckbox = new JCheckBox("Fill On");
	
	public Standalone()
	{
          setTitle("Graph3D");
	    Border lineBorder = BorderFactory.createLineBorder(Color.black);
	    
	    //file to plot
	    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            GraphData graphData = new GraphData("./testData/plot3D.dat");
            graph3d = new Graph3D( config, graphData );
            
            getContentPane().setLayout( new BorderLayout() );
            
	    
            
            getContentPane().add( graph3d, BorderLayout.CENTER );
	    
	    JPanel optionPanel = new JPanel();
	    optionPanel.setBorder( lineBorder );
	    optionPanel.setLayout( new FlowLayout());
	    getContentPane().add( optionPanel, BorderLayout.SOUTH );
	    
	    optionPanel.add( perspectiveCheckbox );
	    optionPanel.add( fillCheckbox );
	    perspectiveCheckbox.setSelected( true );
	    fillCheckbox.setSelected( true );
	    
	    fillCheckbox.addActionListener( this );
	    perspectiveCheckbox.addActionListener( this );
	    
	}
	
	//GUI Event Handlers
	public void actionPerformed( ActionEvent evt ){
		JCheckBox source = (JCheckBox)evt.getSource();
		if( source == perspectiveCheckbox ){
	          graph3d.setPerspectiveOn( source.isSelected() );
		} else if( source == fillCheckbox ){
	          graph3d.setGraphOn( source.isSelected() );
		}		
	}
	
	public static void main( String args[] ){
		Standalone standalone = new Standalone();
		standalone.setBounds(0,0, 500,500);
		standalone.setVisible(true);
	}

	
}
