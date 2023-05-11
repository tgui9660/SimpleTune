package logger.dataSupplier.impl.ssm.ecuInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EcuInit {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private static int SSM_OFFSET = 0;
	private byte[] initBytes = null;
	
	public EcuInit(byte[] initBytes){
		this.initBytes = initBytes;
	}
	
	/**
	 * Based on the ecuinit value, returns true/false on availability of SSM attribute
	 * 
	 * @param ecuByte
	 * @param ecuBit
	 * @return
	 */
	public boolean getSSMAvailability(int ecuByte, int ecuBit){
		boolean isAvailable = false;
		
		// Pull the byte of interest
		int byteValue = this.initBytes[ecuByte - 1];
		
		// Pull the bit value from the byte by applying the bit mask derived from the ecuBit
		int bitValue = byteValue & 2^(ecuBit - 1);
		
		if(bitValue > 0){
			isAvailable = true;
		}
		
		return isAvailable;
	}
	
}
