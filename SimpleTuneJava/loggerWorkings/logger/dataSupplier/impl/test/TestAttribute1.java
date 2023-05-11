package logger.dataSupplier.impl.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import logger.dataSupplier.interfaces.DataSupplierListener;
import logger.interfaces.LoggingAttribute;

public class TestAttribute1 implements LoggingAttribute{
	private Vector<DataSupplierListener> listenerVector = new Vector<DataSupplierListener>();
	private boolean isActive = false;
	private Double value = -1000.0;
	
	public void addListener(DataSupplierListener listener) {
		this.listenerVector.add(listener);
	}

	public Double getMax() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getMin() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "TestAttribute1";
	}

	public String getPrecision() {
		// TODO Auto-generated method stub
		return "#.##";
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setNewData(Double newData) {
		this.value = newData;
		
		if(this.isActive){
			
			Iterator<DataSupplierListener> iterator = this.listenerVector.iterator();
			
			while(iterator.hasNext()){
				DataSupplierListener next = iterator.next();
				//next.newDataAvailable(newData, this);
			}
		}
	}

	public Double getValue() {
		return this.value;
	}

	public void setMax(Double max) {
		// TODO Auto-generated method stub
		
	}

	public void setMin(Double min) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	public void setPrecision(String precision) {
		// TODO Auto-generated method stub
		
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDescription(String description) {
		// TODO Auto-generated method stub
		
	}

	public void setID(String id) {
		// TODO Auto-generated method stub
		
	}

	public String getUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUnits(String units) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(Double newValue) {
		// TODO Auto-generated method stub
		
	}

	public HashMap<String, String> getConversions() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConversions(HashMap<String, String> conversions) {
		// TODO Auto-generated method stub
		
	}

	public void setExpression(String expression) {
		// TODO Auto-generated method stub
		
	}

	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setType(int type) {
		// TODO Auto-generated method stub
		
	}

}
