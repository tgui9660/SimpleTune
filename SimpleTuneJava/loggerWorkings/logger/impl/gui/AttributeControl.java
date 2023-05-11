package logger.impl.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logger.interfaces.LoggingAttribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AttributeControl extends JPanel{
	private Log logger = LogFactory.getLog(getClass());
	
	public AttributeControl(LoggingAttribute loggingAttribute){
		this.setLayout(new BorderLayout());
		
		this.add(new JCheckBox(), BorderLayout.WEST);
		this.add(new JLabel(loggingAttribute.getName()), BorderLayout.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		//this.setSize(new Dimension(0, 22));
		//this.setMaximumSize(new Dimension(0, 12));
		this.setPreferredSize(new Dimension(150, 20));
	}
}
