package logger.impl.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import logger.interfaces.LoggingAttribute;
import logger.interfaces.LoggingEntity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LoggingEntityGUI extends JPanel{
	private Log logger = LogFactory.getLog(getClass());
	private AttributeSelectorJPanel attributeSelectorJPanel = new AttributeSelectorJPanel();
	private AttributeLoggerJPanel attributeLoggerJPanel = new AttributeLoggerJPanel();
	private JScrollPane selectorScrollPane = new JScrollPane();
	
	private JSplitPane splitPane = new JSplitPane();

	private static int DIVIDER_WIDTH = 170;
	private static int DIVIDER_SIZE = 4;
	
	public LoggingEntityGUI(){
		this.setName("Logging");
		
		this.init();
	}
	
	/**
	 * Pulls all needed data from passed logging entity and adds to logging GUI.
	 * @param loggingEntity
	 */
	public void addLoggingEntity(LoggingEntity loggingEntity){
		// Pull list of available logging attributes
		LoggingAttribute[] loggingAttributes = loggingEntity.getLoggingAttributes();
		
		// Iterate through all the attributes and add to the GUI with wrapping Controlling GUIs
		for(int i = 0; i < loggingAttributes.length; i++){
			LoggingAttribute aLoggingAttribute = loggingAttributes[i];
			logger.info("Adding attribute :"+aLoggingAttribute.getName());
			
			AttributeControl newGUI = new AttributeControl(aLoggingAttribute);
			
			this.attributeSelectorJPanel.addAttributeGUI(newGUI);
		}
		
		// Refresh the GUI after all additions
		this.attributeSelectorJPanel.repaint();
		this.repaint();
	}
	
	/**
	 * Builds up the GUI.
	 *
	 */
	private void init(){
		selectorScrollPane.getViewport().add(attributeSelectorJPanel);
		selectorScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		selectorScrollPane.setPreferredSize(new Dimension(160, 0));
		
		// Setup split pane
		splitPane.setDividerLocation(DIVIDER_WIDTH);
		splitPane.setLeftComponent(selectorScrollPane);
		
		splitPane.setRightComponent(attributeLoggerJPanel);
		splitPane.setDividerSize(DIVIDER_SIZE);
		
		// Setup main JPanel
		this.setLayout(new BorderLayout());
		this.add(splitPane, BorderLayout.CENTER);
		
	}
	
}
