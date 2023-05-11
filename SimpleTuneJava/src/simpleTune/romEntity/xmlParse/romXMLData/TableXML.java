package simpleTune.romEntity.xmlParse.romXMLData;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TableXML {
	private int SimpleTuneTYPE;
	
	private Log logger = LogFactory.getLog(getClass());
	
	private String type;
	private String name;
	private String category;
	private String storagetype;
	private String endian;
	private int sizex = -1;
	private int sizey = -1;
	private String userlevel;
	private String logparam;
	private boolean beforeRam = false;
	
	private TableScalingXML scaling;
	private TableStaticScalingXML staticScaling;
	private TableAxisXML xAxis;
	private TableAxisXML yAxis;
	private String description;
	
	private Vector<StateXML> states = new Vector<StateXML>();
	
	private long storageaddress;
	
	public long getStorageaddress() {
		return storageaddress;
	}

	public void setStorageaddress(long storageaddress) {
		this.storageaddress = storageaddress;
	}

	public void addState(StateXML newState){
		this.states.add(newState);
	}
	
	public Vector<StateXML> getStates(){
		return this.states;
	}
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public int getSizex() {
		return sizex;
	}
	public void setSizex(int sizex) {
		this.sizex = sizex;
	}
	public int getSizey() {
		return sizey;
	}
	public void setSizey(int sizey) {
		this.sizey = sizey;
	}
	public String getUserlevel() {
		return userlevel;
	}
	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}
	public String getLogparam() {
		return logparam;
	}
	public void setLogparam(String logparam) {
		this.logparam = logparam;
	}
	
	public String toString(){
		String total = "";
		
		total += "type :"+ type +"\n";
		total += "name :"+ name +"\n";
		total += "category :"+ category +"\n";
		total += "storagetype :"+ storagetype +"\n";
		total += "endian :"+ endian +"\n";
		total += "sizex :"+ sizex +"\n";
		total += "sizey :"+ sizey +"\n";
		total += "userlevel :"+ userlevel +"\n";
		total += "logparam :"+ logparam +"\n";
		
		return total;
	}
	
	public TableScalingXML getScaling() {
		return scaling;
	}
	public void setScaling(TableScalingXML scaling) {
		this.scaling = scaling;
	}
	public TableAxisXML getXAxis() {
		return xAxis;
	}
	public void setXAxis(TableAxisXML axis) {
		xAxis = axis;
	}
	public TableAxisXML getYAxis() {
		return yAxis;
	}
	public void setYAxis(TableAxisXML axis) {
		yAxis = axis;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TableStaticScalingXML getStaticScaling() {
		return staticScaling;
	}
	public void setStaticScaling(TableStaticScalingXML staticScaling) {
		this.staticScaling = staticScaling;
	}

	public int getSimpleTuneTYPE() {
		return SimpleTuneTYPE;
	}

	public void setSimpleTuneTYPE(int simpleTuneTYPE) {
		SimpleTuneTYPE = simpleTuneTYPE;
	}
	
	
	// ROM ID Special Case
    private String xmlid;
    private String internalidaddress;
    private String internalidstring;
    private String ecuid;
    private String market;
    private String transmission;
    private String year;
    private String flashmethod;
    private String model;
    private String submodel;
    private String memmodel;
    private String filesize;
    private String make;
    private String base;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getXmlid() {
		return xmlid;
	}

	public void setXmlid(String xmlid) {
		this.xmlid = xmlid;
	}

	public String getInternalidaddress() {
		return internalidaddress;
	}

	public void setInternalidaddress(String internalidaddress) {
		this.internalidaddress = internalidaddress;
	}

	public String getInternalidstring() {
		return internalidstring;
	}

	public void setInternalidstring(String internalidstring) {
		this.internalidstring = internalidstring;
	}

	public String getEcuid() {
		return ecuid;
	}

	public void setEcuid(String ecuid) {
		this.ecuid = ecuid;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFlashmethod() {
		return flashmethod;
	}

	public void setFlashmethod(String flashmethod) {
		this.flashmethod = flashmethod;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSubmodel() {
		return submodel;
	}

	public void setSubmodel(String submodel) {
		this.submodel = submodel;
	}

	public String getMemmodel() {
		return memmodel;
	}

	public void setMemmodel(String memmodel) {
		this.memmodel = memmodel;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public boolean isBeforeRam() {
		return beforeRam;
	}

	public void setBeforeRam(boolean beforeRam) {
		this.beforeRam = beforeRam;
	}
}
