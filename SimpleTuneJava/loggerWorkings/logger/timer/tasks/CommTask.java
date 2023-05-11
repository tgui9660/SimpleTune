package logger.timer.tasks;

import java.util.Timer;
import java.util.TimerTask;

import logger.timer.tasks.interfaces.CommTaskInterface;

public class CommTask extends TimerTask{
	private CommTaskInterface commTask = null;
	private Timer timer = new Timer();
	private int delay = 5000;
	private int period = 4000;
	
	
	public CommTask(CommTaskInterface guiController){
		this.commTask = guiController;

		this.startMe();
	    
	}
	
	// Called by the timer
	public void run() {
		this.commTask.updateCommPorts();
	}
	
	
	// ##################
	// ST Control Methods
	// ##################
	public void startMe(){
		timer.schedule(this, this.delay, this.period);
	}
	
	public void stopMe(){
		this.cancel();
	}

}
