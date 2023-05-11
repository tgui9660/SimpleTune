package logger.dataSupplier.impl.ssm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.interfaces.impl.LoggingAttributeImpl;

public class SSMLoggingAttributeImpl extends LoggingAttributeImpl{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private Integer ecuByteIndex = null;
	private Integer ecuBit = null;
	private Integer address = null;
	private Integer addressLength = null;
	

	
	// *******************
	// GETTERS AND SETTERS
	// *******************
	public void setActive(Boolean isActive) {
		//System.out.println(super.getName()+" Logging attribute:"+isActive);
		
		if(isActive){
			SSMDataQueryManager.getInstance().addLoggingAttribute(this);
		}else{
			SSMDataQueryManager.getInstance().removeLoggingAttribute(this);
		}
		
		super.setActive(isActive);
	}
	
	public Integer getEcuByteIndex() {
		return ecuByteIndex;
	}
	public void setEcuByteIndex(Integer ecuByteIndex) {
		this.ecuByteIndex = ecuByteIndex;
	}
	public Integer getEcuBit() {
		return ecuBit;
	}
	public void setEcuBit(Integer ecuBit) {
		this.ecuBit = ecuBit;
	}
	public Integer getAddress() {
		return address;
	}
	public void setAddress(Integer address) {
		this.address = address;
	}
	public Integer getAddressLength() {
		return addressLength;
	}
	public void setAddressLength(Integer addressLength) {
		this.addressLength = addressLength;
	}

}
