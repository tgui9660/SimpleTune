package simpleTune.general.tools;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataTuple {
	private Log logger = LogFactory.getLog(getClass());
	
	public static int FIRST = 0;
	public static int SECOND = 1;
	public static int THIRD = 2;
	
	
	
	Double valueOne;
	Double valueTwo;
	Double valueThree;
	
	public DataTuple(Double valueOne, Double valueTwo, Double valueThree){
		this.valueOne = valueOne;
		this.valueTwo = valueTwo;
		this.valueThree = valueThree;
		
		//logger.debug("Data Tuple Created (X):"+valueOne + ":    (Y):"+valueTwo+":    (Z):"+valueThree+":");
	}
	
	public Object getTupleofIndex(int tupleIndex){
		if(tupleIndex == DataTuple.FIRST){
			return this.valueOne;
		}
		if(tupleIndex == DataTuple.SECOND){
			return this.valueTwo;
		}
		if(tupleIndex == DataTuple.THIRD){
			return this.valueThree;
		}
		
		return null;
	}
	
	public Object getValueOne() {
		return valueOne;
	}

	public Object getValueTwo() {
		return valueTwo;
	}

	public Object getValueThree() {
		return valueThree;
	}
	
}
