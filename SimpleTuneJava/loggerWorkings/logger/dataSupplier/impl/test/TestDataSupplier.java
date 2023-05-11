package logger.dataSupplier.impl.test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import logger.dataSupplier.interfaces.DataSupplier;
import logger.interfaces.LoggingAttribute;
import logger.interfaces.LoggingSwitch;
import logger.utils.Parameter;

public class TestDataSupplier implements DataSupplier{
	private LinkedList<LoggingAttribute> attributes = new LinkedList<LoggingAttribute>();
	int delay = 1000;   // delay for 1 sec.
    int period = 500;  // repeat every .5 sec.
    Timer timer = new Timer();

    
	public TestDataSupplier(){
		attributes.add(new TestAttribute1());
		attributes.add(new TestAttribute2());
		attributes.add(new TestAttribute3());
		attributes.add(new TestAttribute4());
	}
	
	
	public String getDataSupplierName() {
		// TODO Auto-generated method stub
		return "TestDataSupplier";
	}

	
	public LinkedList<LoggingAttribute> getLoggingAttributes() {
		
		// TODO Auto-generated method stub
		return this.attributes;
	}

	
	public void relinquishCurrentCommPortNow() {
		// TODO Auto-generated method stub
		
	}

	
	public void setCommPort(String commPortName) {
		// TODO Auto-generated method stub
		
	}

	
	public void startLogging() {
		System.out.println("TestDataSupplier starting logging.");
		timer.schedule(new TestTimerTask(this), delay, period);
	}

	public void newTestData() {
		//System.out.println("TetstDataSupplier applying new test data to attributes.");
		Iterator<LoggingAttribute> iterator = this.attributes.iterator();
		while(iterator.hasNext()){
			LoggingAttribute next = iterator.next();
			Random generator = new Random();
			Double rand = generator.nextDouble() * 10;
			//next.setNewData(12.3 + rand	);
		}
	}

	
	public void stopLogging() {
		System.out.println("TestDataSupplier stopping logging.");
		this.timer.cancel();
		this.timer.purge();
		this.timer = new Timer();
	}


	public void init(Vector<Parameter> supplierParameters) {
		// TODO Auto-generated method stub
		
	}


	public Vector<LoggingAttribute> newAttributesAvailable() {
		// TODO Auto-generated method stub
		return null;
	}


	public void intializeConnections() {
		// TODO Auto-generated method stub
		
	}


	public LinkedList<LoggingSwitch> getSwitches() {
		// TODO Auto-generated method stub
		return null;
	}

}
