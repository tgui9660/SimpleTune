
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import logger.xml.defInstance.Address;
import logger.xml.defInstance.Conversion;
import logger.xml.defInstance.Conversions;
import logger.xml.defInstance.Logger;
import logger.xml.defInstance.Parameter;
import logger.xml.defInstance.Parameters;
import logger.xml.defInstance.Protocol;
import logger.xml.defInstance.Protocols;


/** 
  * This shows how to use JAXB to unmarshal an xml file
  * Then display the information from the content tree
  */

public class LoggerXMLParseTest {
	static Protocol next = null;
	
    public static void main (String args[]) {
        try {
            JAXBContext jc = JAXBContext.newInstance("logger.xml.defInstance");
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Logger loggerInstance = (Logger)unmarshaller.unmarshal(new File( "logger/logger.xml"));
            Protocols protocols = loggerInstance.getProtocols();
            Iterator<Protocol> iterator = protocols.getProtocol().iterator();
            while(iterator.hasNext()){
            	next = iterator.next();
            	String id = next.getId();
            	System.out.println("ID :"+id);
            } 
            
           
    		Parameters parameters = next.getParameters();
    		List<Parameter> parameter = parameters.getParameter();
    		Iterator<Parameter> iterator2 = parameter.iterator();
    		while(iterator2.hasNext()){
    			Parameter next = iterator2.next();
    			List<Object> addressOrConversionsOrDepends = next.getAddressOrConversionsOrDepends();
    			Iterator<Object> iterator3 = addressOrConversionsOrDepends.iterator();
    			while(iterator3.hasNext()){
    				Object next2 = iterator3.next();
    				System.out.println("---- :"+next2.getClass().getCanonicalName());
    				if(next2 instanceof Address){
    					System.out.println("------------ :"+((Address)next2).getvalue());
    					String length = ((Address)next2).getLength();
    					System.out.println("------------ L:"+length);
    				}
    				if(next2 instanceof Conversions){
    					List<Conversion> conversion = ((Conversions)next2).getConversion();
    					Iterator<Conversion> iterator4 = conversion.iterator();
    					while(iterator4.hasNext()){
    						Conversion next3 = iterator4.next();
    						System.out.println("------------ :"+next3.getExpr());
    					}
    					
    				}
    			}
    				
    		}

       }catch (Exception e ) {
         e.printStackTrace();
       }
    }
}
