package logger.impl.gui;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;



public class LoggingTestExec {
	private static Logger logger = Logger.getLogger(LoggingTestExec.class);
	
	public static void main(String[] args){
		
		// Setting up the logging
		PatternLayout layout = new PatternLayout("[%d][%c{1}]%p - %m%n");
		ConsoleAppender appender = new ConsoleAppender();
		appender.setLayout(layout);
		logger.setLevel(Level.INFO);
		appender.activateOptions();
		BasicConfigurator.configure(appender);
		
		// Define main logging entity. Placed in JPanel to maximize usage in future
		LoggingEntityGUI loggingEntityGUI = new LoggingEntityGUI();
		
		// Build up surrounding frame
		JFrame testFrame = new JFrame("SimpleTune Logger");
		testFrame.getContentPane().add(loggingEntityGUI);
		//testFrame.add(loggingEntityGUI);
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		testFrame.setVisible(true);
	}
}
