package logger.gui.impl.rawData;

import java.util.Comparator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AttributeTableModelSortComparator implements Comparator{
	
	private Log logger = LogFactory.getLog(getClass());
	
	public int compare(Object o1, Object o2) {
		LinkedList<Object> one = (LinkedList<Object>)o1;
		LinkedList<Object> two = (LinkedList<Object>)o2;
		
		
		String stringOne = (String)one.get(2);
		String stringTwo = (String)two.get(2);
		
		int compareToIgnoreCase = stringOne.compareToIgnoreCase(stringTwo);
		
		if(compareToIgnoreCase < 0) {
			return -1;
		} 
		else if(compareToIgnoreCase == 0) {
			return 0;
		}
		else {
			return 1;
		}
	}

}
