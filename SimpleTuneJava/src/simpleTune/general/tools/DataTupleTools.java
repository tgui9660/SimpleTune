package simpleTune.general.tools;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataTupleTools {
	private static Log logger = LogFactory.getLog(DataTupleTools.class);
	
	
	
	
	// Data tools for tuples
	public static Double getMinTuple(Vector<DataTuple> dataSet, int tupleIndex){
		Double returnValue = -1.0;
		
		Iterator<DataTuple> name = dataSet.iterator();
		boolean first = true;
		while(name.hasNext()){
			DataTuple tuple = name.next();
			Object tupleValue = tuple.getTupleofIndex(tupleIndex);
			
			// Parse the tuple value
			Double parsedValue = null;
			if(tupleValue instanceof String){
				try{
					parsedValue = Double.parseDouble((String)tupleValue);
				}catch(Exception e){
					logger.error("Double parsed from string problems for :"+tupleValue);
				}
			}else{
				parsedValue = (Double)tupleValue;
			}
			
			if(first){
				returnValue = parsedValue;
				first = false;
			}else{
				if(parsedValue < returnValue){
					returnValue = parsedValue;
				}
			}
		}
		
		return returnValue;
	}
	
	public static Double getMaxTuple(Vector<DataTuple> dataSet, int tupleIndex){
		Double returnValue = -1.0;
		
		Iterator<DataTuple> name = dataSet.iterator();
		boolean first = true;
		while(name.hasNext()){
			DataTuple tuple = name.next();
			Object tupleValue = tuple.getTupleofIndex(tupleIndex);
			
			// Parse the tuple value
			Double parsedValue = null;
			if(tupleValue instanceof String){
				try{
					parsedValue = Double.parseDouble((String)tupleValue);
				}catch(Exception e){
					logger.error("Double parsed from string problems for :"+tupleValue);
				}
			}else{
				parsedValue = (Double)tupleValue;
			}
			
			if(first){
				returnValue = parsedValue;
				first = false;
			}else{
				if(parsedValue > returnValue){
					returnValue = parsedValue;
				}
			}
		}
		
		return returnValue;
	}
}
