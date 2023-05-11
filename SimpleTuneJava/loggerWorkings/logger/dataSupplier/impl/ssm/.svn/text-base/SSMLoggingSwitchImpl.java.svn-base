package logger.dataSupplier.impl.ssm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.interfaces.impl.LoggingSwitchImpl;

public class SSMLoggingSwitchImpl extends LoggingSwitchImpl{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private Integer ecuByte = null;
	private Integer ecuBit = null;
	private SSMLoggingSwitchListener listener = null;
	
	// Override super class enabled to tell listener to look for us, or not
	public void setEnabled(boolean enabled) {
		// Set the enabled value
		super.setEnabled(enabled);
		
		// Inform listeners
		this.listener.loggingSwitchStateChange(this);
	}
	
	
	public void  setListener(SSMLoggingSwitchListener listener){
		this.listener = listener;
	}
	public Integer getEcuByte() {
		return ecuByte;
	}
	public void setEcuByte(Integer ecuByte) {
		this.ecuByte = ecuByte;
	}
	public Integer getEcuBit() {
		return ecuBit;
	}
	public void setEcuBit(Integer ecuBit) {
		this.ecuBit = ecuBit;
	}
}
