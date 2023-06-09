package logger.utec.commEvent;


import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utec.comm.UtecSerialConnectionManager;

public class UtecTimerTask extends TimerTask{
	private Log logger = LogFactory.getLog(getClass());
	private UtecTimerTaskListener listener = null;
	private String data = "";
	private int counter = 0;
	private StringBuffer stringBuffer = null;
	
	public UtecTimerTask(UtecTimerTaskListener listener){
		this.listener = listener;
	}
	
	public void setData(String data){
		this.data = data;
		this.stringBuffer = new StringBuffer(data);
		//JutecGUI.getInstance().getJProgressBar().setMinimum(0);
		//JutecGUI.getInstance().getJProgressBar().setMaximum(data.length());
	}
	
	public void run(){
		char theChar = stringBuffer.charAt(counter);
		logger.info("->"+theChar+"<-  :"+(int)theChar+"");
		
		//Send the data to the Utec
		UtecSerialConnectionManager.sendCommandToUtec((int)theChar);
		
		counter++;
		//JutecGUI.getInstance().getJProgressBar().setValue(counter);
		
		// Kill the timer after a at the end of the string
		if(counter == data.length()){
			this.cancel();
			listener.utecCommTimerCompleted();
		}
	}
}
