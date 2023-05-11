package simpleTune.romEntity.xmlParse.romXMLData;

public class TableAxisXML {
	private String type;
	private String name;
	private String storagetype;
	private String endian;
	private String logparam;
	private boolean beforeRam = false;
	
	private TableScalingXML scalingData;
	private long storageaddress;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStoragetype() {
		return storagetype;
	}
	public void setStoragetype(String storagetype) {
		this.storagetype = storagetype;
	}
	public String getEndian() {
		return endian;
	}
	public void setEndian(String endian) {
		this.endian = endian;
	}
	public String getLogparam() {
		return logparam;
	}
	public void setLogparam(String logparam) {
		this.logparam = logparam;
	}
	public TableScalingXML getScalingData() {
		return scalingData;
	}
	public void setScalingData(TableScalingXML scalingData) {
		//logger.info("Setting the scaling data.");
		this.scalingData = scalingData;
	}
	public long getStorageaddress() {
		return storageaddress;
	}
	public void setStorageaddress(long storageaddress) {
		this.storageaddress = storageaddress;
	}
	public boolean isBeforeRam() {
		return beforeRam;
	}
	public void setBeforeRam(boolean beforeRam) {
		this.beforeRam = beforeRam;
	}
}
