package logger.utec.commEvent;


import java.util.Timer;

import logger.utec.properties.UtecProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UtecTimerTaskExecute implements UtecTimerTaskListener{
	private Log logger = LogFactory.getLog(getClass());
	private final Timer timer = new Timer();
	private int delay = Integer.parseInt(UtecProperties.getProperties("utec.commandTransmissionPauseMS")[0]);
	private int period = Integer.parseInt(UtecProperties.getProperties("utec.dataTransmissionPauseMS")[0]);
	private UtecTimerTask utecTimerTask = new UtecTimerTask( this);
	
	public UtecTimerTaskExecute(String data){
		utecTimerTask.setData(data);
		timer.schedule(utecTimerTask, delay, period);
	}
	
	public void utecCommTimerCompleted() {
		// Ensure that the manager knows we are done
		UtecTimerTaskManager.operationComplete();
		
		timer.cancel();
	}
}
