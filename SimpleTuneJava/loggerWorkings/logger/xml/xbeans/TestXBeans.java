package logger.xml.xbeans;

import java.io.File;
import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import noNamespace.LoggerDocument;
import noNamespace.EcuDocument.Ecu;
import noNamespace.EcuparamDocument.Ecuparam;
import noNamespace.EcuparamsDocument.Ecuparams;
import noNamespace.LoggerDocument.Logger;
import noNamespace.ParameterDocument.Parameter;
import noNamespace.ParametersDocument.Parameters;
import noNamespace.ProtocolDocument.Protocol;
import noNamespace.ProtocolsDocument.Protocols;
import noNamespace.SwitchDocument.Switch;
import noNamespace.SwitchesDocument.Switches;

public class TestXBeans {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File loggerFile = new File("defs/logger.xml");
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
		
		Logger logger = doc.getLogger();
		Protocols protocols = logger.getProtocols();
		Protocol protocol = protocols.getProtocol();
		String id = protocol.getId();
		System.out.println("ID: "+id);
		
		
		Parameters parameters = protocol.getParameters();
		Parameter[] parameterArray = parameters.getParameterArray();
		for(int i = 0; i < parameterArray.length; i++){
			Parameter par = parameterArray[i];
			System.out.println("SSM Param: "+par.getName().getStringValue());
			String id2 = par.getId();
			System.out.println("- - ID: "+id2);
		}
		
		Ecuparams ecuparams = protocol.getEcuparams();
		Ecuparam[] ecuparamArray = ecuparams.getEcuparamArray();
		for(int i = 0; i < ecuparamArray.length; i++){
			Ecuparam ecu = ecuparamArray[i];
			String name = ecu.getName().getStringValue();
			String id2 = ecu.getId();
			//System.out.println("ECU Param: "+name);
			//System.out.println("- - ID: "+id2);
			//System.out.println("-----------------");
			//System.out.println("Supported ECU calids:");
			Ecu[] ecuArray = ecu.getEcuArray();
			for(int j = 0; j < ecuArray.length; j++){
				Ecu theEcu = ecuArray[j];
				String id3 = theEcu.getId();
				//System.out.println("- - model: "+id3);
			}
		}
		
		Switches switches = protocol.getSwitches();
		Switch[] switchArray = switches.getSwitchArray();
		for(int i = 0; i < switchArray.length; i++){
			Switch aSwitch = switchArray[i];
			//aSwitch.g
		}
	}

}
