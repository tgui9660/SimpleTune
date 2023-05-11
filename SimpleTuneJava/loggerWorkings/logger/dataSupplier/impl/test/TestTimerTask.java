package logger.dataSupplier.impl.test;

import java.util.TimerTask;

public class TestTimerTask extends TimerTask{
	private TestDataSupplier ds = null;
	
	public TestTimerTask(TestDataSupplier ds){
		this.ds = ds;
	}
	
	@Override
	public void run() {
		ds.newTestData();
	}

}
