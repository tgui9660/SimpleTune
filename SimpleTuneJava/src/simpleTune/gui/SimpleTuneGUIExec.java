package simpleTune.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class SimpleTuneGUIExec {
	private static Logger logger = Logger.getLogger(SimpleTuneGUIExec.class);

	public static void main(String[] args){
		
		// ***************************
		// Set log lines look and feel
		// ***************************
		PatternLayout layout = new PatternLayout("[%d][%c{1}]%p - %m%n");
		
		
		// USED FOR WHEN CODING, ALL INFO TO CONSOLE
		
		ConsoleAppender appender = new ConsoleAppender();
		appender.setLayout(layout);
		logger.setLevel(Level.INFO);
		
		
		// USED FOR WHEN PRODUCING DISTRIBUTION
		/*
		FileAppender appender = new FileAppender();
		appender.setFile("SimpleTune.log");
		appender.setLayout(layout);
		appender.setAppend(true);
		*/
		
		/*
		FileAppender appender = new FileAppender();
		appender.setFile("SimpleTune.log");
		appender.setLayout(layout);
		appender.setAppend(false);
		logger.setLevel(Level.ERROR);
		*/
		
		// Needs to be called
		appender.activateOptions();
		BasicConfigurator.configure(appender);
		
		
		
		// *************************************************************
		// Place holder code for when I decide to get fancy with the GUI
		// *************************************************************
	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	    	logger.error("Supported look and feel not found.", e);
	    }
	    catch (ClassNotFoundException e) {
	    	logger.error("Supported look and feel not found.", e);
	    }
	    catch (InstantiationException e) {
	    	logger.error("Supported look and feel not found.", e);
	    }
	    catch (IllegalAccessException e) {
	    	logger.error("Supported look and feel not found.", e);
	    }

		
	    // **********************
	    // MAKE, GUI, GO, NOW!!!!
	    // **********************
		SimpleTuneGUI.getInstance().setVisible(true);
	}
}
