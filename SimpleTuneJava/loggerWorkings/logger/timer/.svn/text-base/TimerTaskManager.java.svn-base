package logger.timer;

import java.util.TimerTask;
import java.util.Vector;

import logger.timer.tasks.CommTask;
import logger.timer.tasks.interfaces.CommTaskInterface;

public class TimerTaskManager {
	private static TimerTaskManager instance = null;
	private Vector<TimerTask> taskList = new Vector<TimerTask>();
	
	private TimerTaskManager(){
		
		// Add all tasks here:
		// Tasks need to start themselves on instantiation
		
		// Task to update GUI comm port list
		//taskList.add(new CommTask(DataLoggerGUIController.getInstance()));
		
	}
	
	public static TimerTaskManager getInstance(){
		if(TimerTaskManager.instance == null){
			TimerTaskManager.instance = new TimerTaskManager();
		}
		return TimerTaskManager.instance;
	}
	
	public void addCommTaskListener(CommTaskInterface commTaskImpl){
		this.taskList.add(new CommTask(commTaskImpl));
	}
	
}
