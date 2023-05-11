package logger.dataSupplier.impl.ssm;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSMECUInitTimerTask extends TimerTask{
	
	private Log logger = LogFactory.getLog(getClass());
	

	private SSMECUInit ecuInit = null;
	
	public SSMECUInitTimerTask(SSMECUInit ecuInit){
		System.out.println("ECU INIT TIMER INSTANTIATED");
		this.ecuInit = ecuInit;
	}
	
	public void run() {
		System.out.println("ECU INIT TIMER TICK!!!! ---- ");
		this.ecuInit.startECUInitQuery();
	}

}
