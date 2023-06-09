package st.comm;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import logger.dataSupplier.interfaces.DataSupplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Coordinate Comm access
 * 
 * @author emorgan
 *
 */
public class CommAccessManager {
	private Log logger = LogFactory.getLog(getClass());
	private static CommAccessManager instance = null;
	private HashMap<String, DataSupplier> commPortsInUseList = new HashMap<String, DataSupplier>();
	
	private CommAccessManager(){
		
	}
	
	public static CommAccessManager getInstance(){
		if(CommAccessManager.instance == null){
			CommAccessManager.instance = new CommAccessManager();
		}
		
		return CommAccessManager.instance;
	}
	
	public boolean isCommPortAssigned(String commPortName){
		if(commPortsInUseList.get(commPortName) != null){
			return true;
		}
		
		return false;
	}
	
	public String getCommPortAssignedToDataSupplier(DataSupplier dataSupplier){
		Set<String> keySet = commPortsInUseList.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String commPortName = iterator.next();
			String dataSupplierName = commPortsInUseList.get(commPortName).getDataSupplierName();
			if(dataSupplier.getDataSupplierName().equals(dataSupplierName)){
				return commPortName;
			}
		}
		
		return null;
	}
	
	public boolean assignCommPort(String commPortName, DataSupplier dataSupplier){
		Vector<String> availableCommPortNames = CommAccessManager.getInstance().getAvailableCommPortNames();
		
		Iterator<String> iterator = availableCommPortNames.iterator();
		while(iterator.hasNext()){
			String next = iterator.next();
			if(next.equalsIgnoreCase(commPortName)){
				int indexOf = CommAccessManager.getInstance().getAvailableCommPortNames().indexOf(next);
				
				return this.assignCommPort(indexOf, dataSupplier);
			}
		}
		
		return false;
	}
	
	public boolean assignCommPort(int commPortIndex, DataSupplier dataSupplier){
		
		String commPortName = CommAccessManager.getInstance().getAvailableCommPortNames().get(commPortIndex);
		
		// If comm port is in use, return false, impossible operation
		if(CommAccessManager.getInstance().isCommPortAssigned(commPortName)){
			System.out.println("Comm Port already assigned and in use.");
			logger.info("Comm Port already assigned and in use.");
			return false;
		}
		
		// If data supplier is already assigned to a comm port, force it to give its current port up
		String commPortAssignedToDataSupplier = getCommPortAssignedToDataSupplier(dataSupplier);
		if(commPortAssignedToDataSupplier != null){
			dataSupplier.relinquishCurrentCommPortNow();
			commPortsInUseList.remove(commPortAssignedToDataSupplier);
		}
		
		dataSupplier.setCommPort(commPortName);
		commPortsInUseList.put(commPortName, dataSupplier);
		
		return true;
	}
	
	public Vector<String> getAvailableCommPortNames() {
		Vector<String> theChoices = new Vector<String>();
		CommPortIdentifier portId;
		
		Enumeration<CommPortIdentifier> en = CommPortIdentifier.getPortIdentifiers();
		if (!en.hasMoreElements()) {
			System.out.println("No Valid ports found, check Java installation");
			System.out.println("Exiting now.");
		}
			
		//Iterate through the ports
		while (en.hasMoreElements()) {
			portId = en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				//System.out.println("Port found on system: "+portId.getName());
				theChoices.addElement(portId.getName());
			}
		}
		return theChoices;
	}
	
	public void init(){
		getAvailableCommPortNames();
	}
}
