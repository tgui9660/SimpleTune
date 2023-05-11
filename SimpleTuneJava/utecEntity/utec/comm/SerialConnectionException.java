/*
 * Class defines an exception to be thrown in the event
 * of serial connection troubles
 */

package utec.comm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SerialConnectionException extends Exception {
	private Log logger = LogFactory.getLog(getClass());
    /**
     * Constructs a <code>SerialConnectionException</code>
     * with the specified detail message.
     *
     * @param   s   the detail message.
     */
    public SerialConnectionException(String str) {
        super(str);
    }

    /**
     * Constructs a <code>SerialConnectionException</code>
     * with no detail message.
     */
    public SerialConnectionException() {
        super();
    }
}
