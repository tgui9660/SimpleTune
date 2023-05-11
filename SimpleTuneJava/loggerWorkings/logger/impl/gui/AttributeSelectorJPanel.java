package logger.impl.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import logger.impl.gui.layout.BetterFlowLayout;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AttributeSelectorJPanel extends JPanel{

	private Log logger = LogFactory.getLog(getClass());
	
	GridBagConstraints c = new GridBagConstraints();
	
	int count = 0;
	
	private static int CELL_HEIGHT = 12;
	private static int CELL_WIDTH = 10;
	
	public AttributeSelectorJPanel(){
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// As many rows as needed and one column
		GridLayout definedLayout = new GridLayout(0,1);
		definedLayout.setVgap(2);
		//this.setLayout(definedLayout);
		
		
		//this.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.gridx = 0;
		c.ipady = CELL_HEIGHT;
		//c.ipadx = CELL_WIDTH;
		c.weighty = 0;
		//c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		
		//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//this.setLayout(new BetterFlowLayout(FlowLayout.CENTER, 1, 1));
		
		this.setLayout(new MigLayout());
	}
	
	public void addAttributeGUI(AttributeControl gui){
		logger.info("Adding Component to :"+count);
		c.gridy = count;
		//this.add(gui, c);
		count++;
		//c.weighty = 20;
		
		//this.add(gui);
		
		this.add(gui, "wrap");
	}
	
}
