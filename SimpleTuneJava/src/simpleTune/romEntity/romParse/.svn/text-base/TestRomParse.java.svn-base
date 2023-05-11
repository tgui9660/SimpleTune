package simpleTune.romEntity.romParse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestRomParse {
	private static Log logger = LogFactory.getLog(TestRomParse.class);
	
	public static void main(String[] args){
		/*
		logger.info("Test 16 bit");
		RomImage testImage1 = new RomImage("testData/16bit.hex");
		testImage1.getRomCalID();
		logger.info("-----------------");
		
		
		logger.info("Test 32 bit small");
		RomImage testImage2 = new RomImage("testData/32bitsmall.hex");
		testImage2.getRomCalID();
		0x56D50
		logger.info("-----------------");
		*/
		
		/*
		 * 0xC0F84
		 */
		logger.info("Test 32 bit large");
		RomImage testImage3 = new RomImage("testData/32bitsmall.hex");
		testImage3.getRomCalID();
		//double[] dataUint16 = testImage3.getDataUint16(0x56D50, 112, false, "(x-760)*0.01933677");
		//for(int i = 0; i < dataUint16.length; i++){
		//	System.out.print(dataUint16[i] +",  ");
		//}
		logger.info("");
		
		//testImage3.getDataUint8(0x56D50, 20, "(x-760)*0.01933677");
		//testImage3.getDataUint32(0x56D50, 20, false, "(x-760)*0.01933677");
		//testImage3.getDataFloat(0x56D50, 20, false, "(x-760)*0.01933677");
		logger.info("-----------------");
	}
}
