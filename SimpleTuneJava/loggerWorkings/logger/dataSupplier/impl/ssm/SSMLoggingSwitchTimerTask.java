package logger.dataSupplier.impl.ssm;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSMLoggingSwitchTimerTask extends TimerTask{
	
	private Log logger = LogFactory.getLog(getClass());
	


	private SSMLoggingSwitchManager manager = null;
	
	public SSMLoggingSwitchTimerTask(SSMLoggingSwitchManager manager){
		this.manager = manager;
	}
	
	public void run() {
		System.out.println("ECU SWITCH TIMER TICK!!!! ---- ");
		this.manager.pullSwitchData();
	}

}
