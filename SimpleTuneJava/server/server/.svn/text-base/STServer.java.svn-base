package server;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import server.database.DatabaseController;
import simpleTune.gui.SimpleTuneGUI;
import simpleTune.gui.SimpleTuneGUIExec;
import simpleTune.romEntity.romParse.RomImage;

public class STServer {
	
	private static Logger logger = Logger.getLogger(STServer.class);
	
	public static void main(String[] args){

		PatternLayout layout = new PatternLayout("[%d][%c{1}]%p - %m%n");
		/*
		ConsoleAppender appender = new ConsoleAppender();
		appender.setLayout(layout);
		*/
		
		/*
		FileAppender appender = new FileAppender();
		appender.setFile("SimpleTune.log");
		appender.setLayout(layout);
		appender.setAppend(true);
		*/
		
		ConsoleAppender appender = new ConsoleAppender();
		appender.setLayout(layout);
		
		// Needs to be called
		appender.activateOptions();
		
		
		BasicConfigurator.configure(appender);
		
		logger.setLevel(Level.INFO);
		
		
		//DatabaseController.getInstance().tesetROMInsert();
		//DatabaseController.getInstance().testTableQuery();
		
		RomImage image = new RomImage("/home/botman/Desktop/32bitlarge.hex");
		
		DatabaseController.getInstance().insertROM("Eric", image.getRomCalID(), image.getBytes());
		
	}
}
