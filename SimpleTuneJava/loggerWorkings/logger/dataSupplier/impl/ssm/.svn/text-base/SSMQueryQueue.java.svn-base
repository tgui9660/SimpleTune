package logger.dataSupplier.impl.ssm;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class queues and executes all SSM queries.
 * 
 * This is the ECU facing code. Called by the manager for periodic attribute updates. Also called
 * by ECU init code to detect supported SSM calls. Also used by memory reading and writing code
 * for RAMTune. EVENTUALLY ;-) TODO Make it so!
 * 
 * @author emorgan
 *
 */
public class SSMQueryQueue {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private static SSMQueryQueue instance = null;
	
	// Queue actively being pulled from
	private LinkedList<SSMQuery> activeQueue  = new LinkedList<SSMQuery>();
	
	// Needed as concurrent modification of runningQueue is not allowed
	private LinkedList<SSMQuery> pendingQueue  = new LinkedList<SSMQuery>();
	
	
	/**
	 * Singleton code.
	 * 
	 * @return
	 */
	public static SSMQueryQueue getInstance(){
		if(SSMQueryQueue.instance == null){
			SSMQueryQueue.instance = new SSMQueryQueue();
		}
		
		return SSMQueryQueue.instance;
		
	}
	private SSMQueryQueue(){
	}
	
	/**
	 * Place a query on the queue
	 * @param command
	 * @param listener
	 */
	public void registerSSMQuery(SSMQuery query){
		
		// Place request on pending queue
		pendingQueue.add(query);
		
		// Kick off queue if already not running
		this.processQueue();
	}
	
	/**
	 * Called by SSMQueryExecute when a result is ready.
	 * @param result
	 */
	public void ssmQueryResult(SSMQuery result){
		
		// Call current listener with results
		// TODO, some logging here maybe?
		result.resultReadyForListener();
		
		// Kick off next query
		this.processQueue();
	}
	
	/**
	 * Kicks off the processing
	 */
	private void processQueue(){
		// Add any pending requests to the active queue
		Iterator<SSMQuery> iterator = this.pendingQueue.iterator();
		while(iterator.hasNext()){
			SSMQuery next = iterator.next();
			this.activeQueue.add(next);
		}
		this.pendingQueue.clear();
		
		// Kick off any remaining objects in active queue
		if(this.activeQueue.size() > 0){
			// Pull the next item off the queue
			SSMQuery removeFirst = this.activeQueue.remove(0);
			
			// Execute query and wait for response back at ssmQueryResult() method.
			SSMQueryExecute.getInstance().executeECUQuery(removeFirst);
		}

	}
	
}
