package logger.utec.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtecProperties {
	private static Properties properties = new Properties();
	private Log logger = LogFactory.getLog(getClass());
	private static boolean init = false;
	private static String propertiesLocation = "utec/utec.properties";
	
	public static String[] getProperties(String name){
		if(init == false){
			init();
		}
		
		
		String propertyString = properties.getProperty(name);
		String[] values = propertyString.split(",");
		
		
		return values;
	}
	
	private static void init(){
		try {
			properties.load(new FileInputStream(propertiesLocation));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		init = true;
	}
}
