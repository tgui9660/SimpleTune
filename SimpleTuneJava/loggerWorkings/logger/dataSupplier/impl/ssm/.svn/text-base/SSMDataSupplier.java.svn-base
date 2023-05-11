package logger.dataSupplier.impl.ssm;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlException;

import noNamespace.LoggerDocument;
import noNamespace.AddressDocument.Address;
import noNamespace.ConversionDocument.Conversion;
import noNamespace.ConversionsDocument.Conversions;
import noNamespace.LoggerDocument.Logger;
import noNamespace.ParameterDocument.Parameter;
import noNamespace.ParametersDocument.Parameters;
import noNamespace.ProtocolDocument.Protocol;
import noNamespace.ProtocolsDocument.Protocols;
import noNamespace.SwitchDocument.Switch;
import noNamespace.SwitchesDocument.Switches;


import logger.dataSupplier.interfaces.DataSupplier;
import logger.interfaces.LoggingAttribute;
import logger.interfaces.LoggingSwitch;

public class SSMDataSupplier implements DataSupplier{
	
	private Log logger = LogFactory.getLog(getClass());
	

	// Not sure if I'll use this yet, but keeping just in case
	private String commPortString = "";
	
	// Main logger def location
	private String loggerDefPath = "defs/logger.xml";
	
	// List of all supported parameters
	private LinkedList<LoggingAttribute> ssmParameters = new LinkedList<LoggingAttribute>();
	
	// List of all supported switches
	private LinkedList<LoggingSwitch> switches = new LinkedList<LoggingSwitch>();
	
	private static SSMDataSupplier instance = null;
	private SSMDataSupplier(){
		init();
	}
	public static SSMDataSupplier getInstance(){
		if(SSMDataSupplier.instance == null){
			SSMDataSupplier.instance = new SSMDataSupplier();
		}
		
		return SSMDataSupplier.instance;
	}

	/**
	 * Setup needed data structures.
	 */
	public void init(){
		
		File loggerFile = new File(loggerDefPath);
		LoggerDocument doc = null;
		try {
			doc = LoggerDocument.Factory.parse(loggerFile);
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get base protocol node
		Logger logger = doc.getLogger();
		Protocols protocols = logger.getProtocols();
		Protocol protocol = protocols.getProtocol();
		
		// Gather parameters
		extractParametersFromXML(protocol);
		
		// Gather switches
		extractSwitchesFromXML(protocol);
		
		// Start ecuinit
		SSMECUInit.getInstance();

	}

	private void extractSwitchesFromXML(Protocol protocol) {
		Switches switches = protocol.getSwitches();
		Switch[] switchArray = switches.getSwitchArray();
		for(int i = 0; i < switchArray.length; i++){
			Switch xmlSwitch = switchArray[i];
			SSMLoggingSwitchImpl ssmSwitch = new SSMLoggingSwitchImpl();
			
			String name = xmlSwitch.getName().getStringValue();
			ssmSwitch.setName(name);
			
			String id = xmlSwitch.getId();
			ssmSwitch.setID(id);
			
			String ssmByte = xmlSwitch.getByte();
			if(ssmByte.contains("x")){
				ssmByte = (ssmByte.split("x"))[1];
			}
			ssmSwitch.setEcuByte(Integer.parseInt(ssmByte, 16));

			BigInteger bit = xmlSwitch.getBit();
			ssmSwitch.setEcuBit(bit.intValue());
			
			// Add listener to switch
			ssmSwitch.setListener(SSMLoggingSwitchManager.getInstance());
			
			// Add switch to list
			this.switches.add(ssmSwitch);
		}
	}

	private void extractParametersFromXML(Protocol protocol) {
		Parameters parameters = protocol.getParameters();
		Parameter[] parameterArray = parameters.getParameterArray();
		for(int i = 0; i < parameterArray.length; i++){
			Parameter par = parameterArray[i];
			//System.out.println("SSM Param: "+par.getName().getStringValue());
			String id2 = par.getId();
			//System.out.println("- - ID: "+id2);
			
			SSMLoggingAttributeImpl anAttribute = new SSMLoggingAttributeImpl();
			anAttribute.setActive(false);
			
			
			Address address = par.getAddress();
			if(address != null){
				String addressString = par.getAddress().getStringValue();
				if(addressString.contains("x")){
					addressString = (addressString.split("x"))[1];
				}
				anAttribute.setAddress(Integer.parseInt(addressString, 16));
			}

			
			if(par.getAddress() != null){
				BigInteger length = par.getAddress().getLength();
				if(length != null){
					anAttribute.setAddressLength(length.intValue());
				}else{
					anAttribute.setAddressLength(1);
				}
			}

			
			
			anAttribute.setDescription(par.getDesc().getStringValue());
			
			if(par.getEcubyteindex() != null){
				anAttribute.setEcuBit(par.getEcubit().intValue());
			}
			
			if(par.getEcubyteindex() != null){
				anAttribute.setEcuByteIndex(par.getEcubyteindex().intValue());
			}
			
			Conversions conversions = par.getConversions();
			if(conversions != null){
				Conversion aConversion = conversions.getConversionArray(0);
				if(aConversion != null){
					if(aConversion.getExpr() != null){
						String expression = aConversion.getExpr().getStringValue();
						//System.out.println("Expression; "+expression);
						anAttribute.setExpression(expression);
					}
				}
			}
			
			String units = "";
			String precision = "##.##";	
			Double min = -1.0;
			Double max = -1.0;
			HashMap<String, String> convMap = new HashMap<String, String>();
			
			try{
				Conversion[] conversionArray = par.getConversions().getConversionArray();
				for(int j = 0; j < conversionArray.length; j++){
					Conversion conv = conversionArray[j];
					String convUnits = conv.getUnits().getStringValue();
					String convExpression = conv.getExpr().getStringValue();
					convMap.put(convUnits, convExpression);
				}
				units = par.getConversions().getConversionArray(0).getUnits().getStringValue();
				precision = par.getConversions().getConversionArray(0).getFormat().toPlainString();
				min = par.getConversions().getConversionArray(0).getGaugeMin().doubleValue();
				max = par.getConversions().getConversionArray(0).getGaugeMax().doubleValue();
				
			}catch(Exception e){

			}
			
			anAttribute.setConversions(convMap);
			anAttribute.setID(par.getId());
			anAttribute.setMax(max);
			anAttribute.setMin(min);
			anAttribute.setName(par.getName().getStringValue());
			anAttribute.setPrecision(precision);
			anAttribute.setUnits(units);
			
			
			this.ssmParameters.add(anAttribute);
		}
	}
	
	
	public String getDataSupplierName() {
		return "SSM";
	}

	public LinkedList<LoggingAttribute> getLoggingAttributes() {
		return this.ssmParameters;
	}

	public void relinquishCurrentCommPortNow() {
		// TODO Auto-generated method stub
		
	}

	public void setCommPort(String commPortName) {
		System.out.println("Comm port name set: "+ commPortName);
		this.commPortString = commPortName;
	}

	public void startLogging() {
		//System.out.println(this.getDataSupplierName() + " :START Logging");
		SSMDataQueryManager.getInstance().startLogging();
	}

	public void stopLogging() {
		//System.out.println(this.getDataSupplierName() + " :STOP Logging");
		SSMDataQueryManager.getInstance().stopLogging();
	}

	public void init(Vector<logger.utils.Parameter> supplierParameters) {
		// TODO Auto-generated method stub
		
	}

	public Vector<LoggingAttribute> newAttributesAvailable() {
		// TODO Auto-generated method stub
		return null;
	}

	public void intializeConnections() {
		// TODO Auto-generated method stub
		
	}
	
	public LinkedList<LoggingSwitch> getSwitches() {
		return this.switches;
	}
}
