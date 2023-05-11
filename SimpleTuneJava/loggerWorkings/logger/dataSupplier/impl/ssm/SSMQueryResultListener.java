package logger.dataSupplier.impl.ssm;

import java.util.LinkedList;

/**
 * Call back interface to receive results from an ECU query.
 * 
 * Needed because ECU queries are asynchronous.
 * 
 * @author emorgan
 *
 */
public interface SSMQueryResultListener {
	public void SSMQueryResult(SSMQuery data);
}
