package logger.dataSupplier.impl.ssm;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSMDataQueryTimerTask extends TimerTask{
	
	private Log logger = LogFactory.getLog(getClass());
	

	private SSMDataQueryManager queryManager = null;
	
	public SSMDataQueryTimerTask(SSMDataQueryManager queryManager){
		this.queryManager = queryManager;
	}
	
	public void run() {
		System.out.println("SSM DATA QUERY TIMER TICK!!!! ---- ");
		//queryManager.pullData();
	}

}
