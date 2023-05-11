package logger.gui.exec;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.gui.control.DataConsumerGUIController;
import logger.gui.control.LoggerGUIManager;
import logger.gui.impl.rawData.TableGUIDataConsumer;

public class LoggerGUIExec {
	
	private Log logger = LogFactory.getLog(getClass());
	
	public static void main(String[] args){

		// Add all GUIs
		//DataConsumerGUIController.getInstance().addDataConsumer(new TableGUIDataConsumer());
		LoggerGUIManager.getInstance();
		
		// Add GUI to a JFrame for testing
		JFrame mainJFrame = new JFrame();
		JTabbedPane tab = new JTabbedPane();
		tab.add("Raw Data Logger", (JPanel)DataConsumerGUIController.getInstance().getConsumer("Raw Data Logger"));
		//tab.add("CSV Logger", (JPanel)DataConsumerGUIController.getInstance().getConsumer("CSV Logger"));
		
		mainJFrame.add(tab);
		mainJFrame.setSize(1024, 768);
		mainJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		

		// Define close action
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Gotta see it, right?
		mainJFrame.setVisible(true);
	}
}
