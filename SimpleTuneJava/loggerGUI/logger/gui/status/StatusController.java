package logger.gui.status;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.gui.interfaces.StatusListener;

public class StatusController {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private static StatusController statusController = null;
	private Vector<StatusListener> listeners = new Vector<StatusListener>();
	
	private StatusController(){
		
	}
	
	public static StatusController getInstance(){
		if(StatusController.statusController == null){
			StatusController.statusController = new StatusController();
		}
		
		return StatusController.statusController;
	}
	
	public void addStatusListener(StatusListener listener){
		listeners.add(listener);
	}
	
	public void addStatusMessage(int type, String message){
		Iterator<StatusListener> iterator = this.listeners.iterator();
		while(iterator.hasNext()){
			StatusListener next = iterator.next();
			next.StatusMessageReceived(type, message);
		}
	}
}
