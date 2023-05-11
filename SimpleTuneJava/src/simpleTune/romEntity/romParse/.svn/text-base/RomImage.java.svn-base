package simpleTune.romEntity.romParse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nfunk.jep.JEP;

import simpleTune.romEntity.xmlParse.RomXMLDataManager;
import simpleTune.romEntity.xmlParse.romXMLData.TableXML;

public class RomImage {
	private Log logger = LogFactory.getLog(getClass());
	private int addressOffset;
	private String theRomPath;
	private long fileSize;
	private byte[] bytes;
	private JEP jep = new JEP(); //Expression parser.
	
	public RomImage(String romPath){
		logger.info("Attempting to open rom : " + romPath);

		this.theRomPath = romPath;

		File aFile = new File(this.theRomPath);
		this.fileSize = aFile.length();
		
		logger.info("ROM Size : "+this.fileSize);
		
		InputStream is = null;
		
		try {
			is = new FileInputStream(aFile);
		} catch (FileNotFoundException e) {
			System.err.println("Can't open ROM Image:"+this.theRomPath);
			e.printStackTrace();
		}

        if (this.fileSize > Integer.MAX_VALUE) {
            System.err.println("ROM File Size too large to open : "+this.theRomPath);
        }

        // Create the byte array to hold the data
        bytes = new byte[(int)this.fileSize];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        try {
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			    offset += numRead;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
        	System.err.println("Can't read all bytes of ROM : "+this.theRomPath);
        }

        // Close the input stream and return bytes
        try {
			is.close();
		} catch (IOException e) {
			System.err.println("Can't close ROM stream : "+this.theRomPath);
			e.printStackTrace();
		}
		
		// Because of different ROM sizes, there might be an offset.
		this.addressOffset = 0;
		if(this.fileSize == 163840){
			this.addressOffset = -32768;
		}
		
		// Prepare the expression parser
		jep.addVariable("x", 0);
		
		// Everything ok here.
		logger.info("Opened ROM just fine!");
	}
	
	/**
	 * Pull the CalID from the ROM.
	 * 
	 * @return
	 */
	public String getRomCalID(){
		HashMap<String,HashMap<String,TableXML>> mapData = RomXMLDataManager.getInstance().GetAllROMMapData();
		
		//HashMap<String,HashMap<String,TableXML>> mapData = RomXMLDataManager.getInstance().GetAllBaseMapData();
		Set<String> keySet = mapData.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String next = iterator.next();
			HashMap<String,TableXML> hashMap = mapData.get(next);
			
			TableXML tableXML = hashMap.get("romid");
			//logger.info("-----------------------------");
			//logger.info("CAL ID:"+tableXML.getXmlid());
			//logger.info("ECU ID:"+tableXML.getEcuid());
			//logger.info("Internal Address:"+tableXML.getInternalidaddress());
			
			int parseInt = Integer.parseInt(tableXML.getInternalidaddress(), 16);
			if(getCalIDAtAddress(parseInt).equalsIgnoreCase(tableXML.getXmlid())){
				logger.info("CAL ID FOUND! :"+tableXML.getXmlid());
				return tableXML.getXmlid().toLowerCase();
			}
		}

		// Case where no base is found
		return null;
	}
	
	private String getCalIDAtAddress(int offset){
		char[] charArray = new char[8];
		
		// Case of 32 bit rom
		//int offset = 0x2000;
		
		// Case of 16 bit rom
		//if(this.fileSize < 200000){
		//	offset = 0x200;
		//}
		
		if(this.fileSize < offset){
			logger.info("Requested CALID Address is larger than ROM.");
			return "";
		}
		
		// Read bytes into char array
		for(int i = 0; i < 8; i++){
			charArray[i] = (char)(this.bytes[offset + i]);
		}

		String result = new String(charArray);
		logger.info("ROM CalID query attempt value :"+result);
		
		return result;
	}
	
	/**
	 * Changes the ROM calID
	 * 
	 * @param newCalID
	 */
	/*
	public void setRomCalID(String newCalID){
		logger.info("Setting ROM CalID :"+newCalID);
		
		// Pull individual chars
		char[] charArray = newCalID.toCharArray();
		
		// Case of 32 bit rom
		int offset = 0x2000;
		
		// Case of 16 bit rom
		if(this.fileSize < 200000){
			offset = 0x200;
		}
		
		// Read bytes into ROM
		for(int i = 0; i < 8; i++){
			this.bytes[offset + i] = (byte)charArray[i];
		}
	}
	*/
	
	public Double[][] getDataUint8(int baseAddress, String expression, int sizex, int sizey, boolean beforeRam){
		// Detect empty expression
		if(expression == null){
			logger.info("Expression getting float data is NULL. Not Good.");
			expression = "x";
		}
		
		// Define the number of elements we're looking for
		int count = 0;
		if(sizex > 0 && sizey > 0){
			count  = sizex*sizey;
		}else if(sizex > 0){
			count = sizex;
			sizey = 1;
		}else if(sizey > 0){
			count = sizey;
			sizex = 1;
		}else{
			return null;
		}

		// Define 2D array to hold data
		Double[][] toBereturned = new Double[sizey][sizex];
		
		double[] returnValues = new double[count];

		// Apply any needed offset.
		if(!beforeRam){
			baseAddress = calcBaseAddressOffset(baseAddress);
		}
		
		ByteBuffer bb = ByteBuffer.allocate(2);
		
		for(int i = 0; i < count ; i++){
			
			byte[] bytes = new byte[2];
			
			bytes[1] = this.bytes[baseAddress + i];
			bytes[0] = new Byte("00000000");
			
			bb.put(bytes);
			int intValue = bb.getShort(0);
			double value = (double)intValue;
			
			jep.addVariable("x", value);
			jep.parseExpression(expression);
			value = (double)jep.getValue();
			
			returnValues[i] = value;
			
			// Clear the buffer
			bb.clear();
		}

		// Translate found data into passed in dimensions of 2D array.
		int counter = 0;
		for(int i = 0; i < sizey; i++){
			for(int j = 0; j < sizex; j++){
				toBereturned[i][j] = returnValues[counter];
				counter ++;
			}
		}
		
		return toBereturned;
	}
	
	/**
	 * Return an array of data from the ROM
	 * Expression will modify the out going data.
	 * 
	 * @param baseAddress
	 * @param count
	 * @param expression
	 * @return
	 */
	public Double[][] getDataUint8_OLD(int baseAddress, String expression, int sizex, int sizey, boolean beforeRam){
		// Detect empty expression
		if(expression == null){
			expression = "x";
		}
		
		/*
		logger.info("baseAddress :"+baseAddress);
		logger.info("expression :"+expression);
		logger.info("sizex :"+sizex);
		logger.info("sizey :"+sizey);
		*/
		
		// Define the number of elements we're looking for
		int count = 0;
		if(sizex > 0 && sizey > 0){
			count  = sizex*sizey;
		}else if(sizex > 0){
			count = sizex;
			sizey = 1;
		}else if(sizey > 0){
			count = sizey;
			sizex = 1;
		}else{
			return null;
		}
		
		// Define 2D array to hold data
		Double[][] toBereturned = new Double[sizey][sizex];
		//logger.info("sizex: "+sizex);
		//logger.info("sizey: "+sizey);
		
		// Temp storage of gathered data
		Double[] gatheredData = new Double[count];
		
		// Apply any needed offset.
		if(!beforeRam){
			baseAddress = calcBaseAddressOffset(baseAddress);
		}
		
		// Gather data
		for(int i = 0; i < count; i++){
			int byteOne = (0x000000FF & (int)this.bytes[baseAddress + i]);
			
			char tempChar = (char) (byteOne);
			
			jep.addVariable("x", (int)tempChar);
			jep.parseExpression(expression);
			gatheredData[i] = jep.getValue();
		}
		
		// Translate found data into passed in dimensions of 2D array.
		int counter = 0;
		for(int i = 0; i < sizey; i++){
			for(int j = 0; j < sizex; j++){
				toBereturned[i][j] = gatheredData[counter];
				counter ++;
			}
		}
		
		return toBereturned;
	}
	
	/**
	 * Saves data unit8 to the ROM.
	 * @param data
	 * @param baseAddress
	 * @param expression
	 */
	public void setDataUint8(Double[] data, int baseAddress, String expression, boolean beforeRam){
		/*
		logger.info("RomImage saving uint8");
		logger.info("--------- baseAddress:"+baseAddress);
		logger.info("--------- expression:"+expression);
		
		for(int i = 0; i < data.length; i++){
			System.out.print(data[i]+" , ");
		}
		logger.info("");
		*/
		
		// Detect empty expression
		if(expression == null){
			expression = "x";
		}
		
		// Define the number of elements we're looking for
		int count = data.length;
		
		// Apply any needed offset.
		if(!beforeRam){
			baseAddress = calcBaseAddressOffset(baseAddress);
		}
		
		// Gather data
		for(int i = 0; i < count; i++){
			jep.addVariable("x", data[i]);
			jep.parseExpression(expression);
			Double tempValue = jep.getValue();
			this.bytes[baseAddress + i] = tempValue.byteValue();
		}
	}
	
	/**
	 * Return an array of data from the ROM.
	 * Expression will modify the out going data.
	 * 
	 * @param baseAddress
	 * @param count
	 * @param isLittleEndian
	 * @param expression
	 * @return
	 */
	public Double[][] getDataUint16(int baseAddress, boolean isLittleEndian, String expression, int sizex, int sizey, boolean beforeRam){
		
		// Detect empty expression
		if(expression == null){
			expression = "x";
		}
		
		// Define the number of elements we're looking for
		int count = 0;
		if(sizex > 0 && sizey > 0){
			count  = sizex*sizey;
		}else if(sizex > 0){
			count = sizex;
			sizey = 1;
		}else if(sizey > 0){
			count = sizey;
			sizex = 1;
		}else{
			return null;
		}

		// Define 2D array to hold data
		Double[][] toBereturned = new Double[sizey][sizex];
		
		double[] gatheredData = new double[count];

		// Apply any needed offset.
		if(!beforeRam){
			baseAddress = calcBaseAddressOffset(baseAddress);
		}
		
		for(int i = 0; i < count*2 ; i += 2){
			int byteOne = (0x000000FF & (int)this.bytes[baseAddress + i]);
			int byteTwo = (0x000000FF & (int)this.bytes[baseAddress + i + 1]);
			
			char tempChar = (char) (byteOne << 8 | byteTwo);
			
			// Now swap the byte order if big endian
			if(isLittleEndian){
				tempChar = (char) (byteTwo << 8 | byteOne);
			}
			
			//logger.info("Pulled ROM value :"+(int)tempChar);
			
			jep.addVariable("x", (int)tempChar);
			jep.parseExpression(expression);
			gatheredData[i/2] = jep.getValue();
		}

		// Translate found data into passed in dimensions of 2D array.
		int counter = 0;
		for(int i = 0; i < sizey; i++){
			for(int j = 0; j < sizex; j++){
				toBereturned[i][j] = gatheredData[counter];
				counter ++;
			}
		}
		
		return toBereturned;
	}
	
	public void setDataUint16(Double[] data, int baseAddress, boolean isLittleEndian, String expression, boolean beforeRam){
		/*
		logger.info("RomImage saving uint16");
		logger.info("--------- baseAddress:"+baseAddress);
		logger.info("--------- expression:"+expression);
		
		for(int i = 0; i < data.length; i++){
			System.out.print(data[i]+" , ");
		}
		logger.info("");
		*/
		
		// Detect empty expression
		if(expression == null){
			expression = "x";
		}
		
		// Define the number of elements we're looking for
		int count = data.length;

		// Apply any needed offset.
		if(!beforeRam){
			baseAddress = calcBaseAddressOffset(baseAddress);
		}
		
		// Place bytes back into ROM
		for(int i = 0; i < count ; i ++){
			jep.addVariable("x", data[i]);
			jep.parseExpression(expression);
			Double value = jep.getValue();
			if(value < 0){
				value = 0.0;
			}
			
			int intValue = value.intValue();
			
			//System.out.print(intValue+" , ");
			
			ByteBuffer buf = ByteBuffer.allocate(4);
			buf.putInt(0, intValue);
			
			byte byteHigh = buf.get(3);
			byte byteLow = buf.get(2);
			
			if(isLittleEndian){
				this.bytes[baseAddress + i*2] = byteHigh;
				this.bytes[baseAddress + i*2 + 1] = byteLow;
			}else{
				this.bytes[baseAddress + i*2] = byteLow;
				this.bytes[baseAddress + i*2 + 1] = byteHigh;
			}
		}
	}
	
	/**
	 * Get int data at base address.
	 * 
	 * @param baseAddress
	 * @param count
	 * @param isLittleEndian
	 * @param expression
	 * @return
	 */
	public Double[][] getDataUint32(int baseAddress, boolean isLittleEndian, String expression, int sizex, int sizey, boolean beforeRam){
		// Detect empty expression
		if(expression == null){
			expression = "x";
		}
		
		// Define the number of elements we're looking for
		int count = 0;
		if(sizex > 0 && sizey > 0){
			count  = sizex*sizey;
		}else if(sizex > 0){
			count = sizex;
			sizey = 1;
		}else if(sizey > 0){
			count = sizey;
			sizex = 1;
		}else{
			return null;
		}

		// Define 2D array to hold data
		Double[][] toBereturned = new Double[sizey][sizex];
		
		double[] returnValues = new double[count];

		// Apply any needed offset.
		if(!beforeRam){
			baseAddress = calcBaseAddressOffset(baseAddress);
		}
		
		for(int i = 0; i < count*4 ; i += 4){
			int byteOne = (0x000000FF & (int)this.bytes[baseAddress + i]);
			int byteTwo = (0x000000FF & (int)this.bytes[baseAddress + i + 1]);
			int byteThree = (0x000000FF & (int)this.bytes[baseAddress + i + 2]);
			int byteFour = (0x000000FF & (int)this.bytes[baseAddress + i + 3]);
			
			long tempChar = (long) (((byteOne << 24 ) | (byteTwo << 16) | (byteThree << 8) | byteFour) & 0xFFFFFFFFL);
			
			// Now swap the byte order if big endian
			if(isLittleEndian){
				tempChar = (long) (((byteFour << 24 ) | (byteThree << 16) | (byteTwo << 8) | byteOne) & 0xFFFFFFFFL);
			}
			
			jep.addVariable("x", tempChar);
			jep.parseExpression(expression);
			returnValues[i/4] = jep.getValue();
		}
		

		// Translate found data into passed in dimensions of 2D array.
		int counter = 0;
		for(int i = 0; i < sizey; i++){
			for(int j = 0; j < sizex; j++){
				toBereturned[i][j] = returnValues[counter];
				counter ++;
			}
		}
		
		return toBereturned;
	}
	
	public void setDataUint32(Double[] data, int baseAddress, boolean isLittleEndian, String expression, boolean beforeRam){
		/*
		logger.info("RomImage saving uint32");
		logger.info("--------- baseAddress:"+baseAddress);
		logger.info("--------- expression:"+expression);
		
		for(int i = 0; i < data.length; i++){
			System.out.print(data[i]+" , ");
		}
		logger.info("");
		*/
		
		// Detect empty expression
		if(expression == null){
			expression = "x";
		}
		
		// Define the number of elements we're looking for
		int count = data.length;

		// Apply any needed offset.
		if(!beforeRam){
			baseAddress = calcBaseAddressOffset(baseAddress);
		}
		
		// Place bytes back into ROM
		for(int i = 0; i < count ; i ++){
			jep.addVariable("x", data[i]);
			jep.parseExpression(expression);
			Double value = jep.getValue();
			int intValue = value.intValue();
			
			ByteBuffer buf = ByteBuffer.allocate(4);
			buf.putInt(0, intValue);
			
			byte byteOne = buf.get(3);
			byte byteTwo = buf.get(2);
			byte byteThree = buf.get(1);
			byte byteFour = buf.get(0);
			
			if(isLittleEndian){
				this.bytes[baseAddress + i*4] = byteOne;
				this.bytes[baseAddress + i*4 + 1] = byteTwo;
				this.bytes[baseAddress + i*4 + 2] = byteThree;
				this.bytes[baseAddress + i*4 + 3] = byteFour;
			}else{
				this.bytes[baseAddress + i*4] = byteFour;
				this.bytes[baseAddress + i*4 + 1] = byteThree;
				this.bytes[baseAddress + i*4 + 2] = byteTwo;
				this.bytes[baseAddress + i*4 + 3] = byteOne;
			}
		}
	}
	
	/**
	 * Get float data at base address
	 * 
	 * @param baseAddress
	 * @param count
	 * @param isLittleEndian
	 * @param expression
	 * @return
	 */
	public Double[][] getDataFloat(int baseAddress, boolean isLittleEndian, String expression, int sizex, int sizey, boolean beforeRam){
		// Detect empty expression
		if(expression == null){
			logger.info("Expression getting float data is NULL. Not Good.");
			expression = "x";
		}
		
		// Define the number of elements we're looking for
		int count = 0;
		if(sizex > 0 && sizey > 0){
			count  = sizex*sizey;
		}else if(sizex > 0){
			count = sizex;
			sizey = 1;
		}else if(sizey > 0){
			count = sizey;
			sizex = 1;
		}else{
			return null;
		}

		// Define 2D array to hold data
		Double[][] toBereturned = new Double[sizey][sizex];
		
		double[] returnValues = new double[count];

		// Apply any needed offset.
		if(!beforeRam){
			baseAddress = calcBaseAddressOffset(baseAddress);
		}
		
		ByteBuffer bb = ByteBuffer.allocate(4);
		
		for(int i = 0; i < count*4 ; i += 4){
			
			byte[] bytes = new byte[4];
			
			
			if(!isLittleEndian){
				bytes[0] = this.bytes[baseAddress + i];
				bytes[1] = this.bytes[baseAddress + i + 1];
				bytes[2] = this.bytes[baseAddress + i + 2];
				bytes[3] = this.bytes[baseAddress + i + 3];
			}else{
				bytes[3] = this.bytes[baseAddress + i];
				bytes[2] = this.bytes[baseAddress + i + 1];
				bytes[1] = this.bytes[baseAddress + i + 2];
				bytes[0] = this.bytes[baseAddress + i + 3];
			}
			
			bb.put(bytes);
			float floatValue = bb.getFloat(0); 
			double value = (double)floatValue;
			
			jep.addVariable("x", value);
			jep.parseExpression(expression);
			value = (double)jep.getValue();
			
			returnValues[i/4] = value;
			
			// Clear the buffer
			bb.clear();
		}

		// Translate found data into passed in dimensions of 2D array.
		int counter = 0;
		for(int i = 0; i < sizey; i++){
			for(int j = 0; j < sizex; j++){
				toBereturned[i][j] = returnValues[counter];
				counter ++;
			}
		}
		
		return toBereturned;
	}
	
	public void setDataFloat(Double[] data, int baseAddress, boolean isLittleEndian, String expression, boolean beforeRam){
		/*
		logger.info("RomImage saving float");
		logger.info("--------- baseAddress:"+baseAddress);
		logger.info("--------- expression:"+expression);
		
		for(int i = 0; i < data.length; i++){
			System.out.print(data[i]+" , ");
		}
		logger.info("");
		*/
		
		// Detect empty expression
		if(expression == null){
			expression = "x";
		}
		
		// Define the number of elements we're looking for
		int count = data.length;

		// Apply any needed offset.
		if(!beforeRam){
			baseAddress = calcBaseAddressOffset(baseAddress);
		}
		
		// Place bytes back into ROM
		for(int i = 0; i < count ; i ++){
			jep.addVariable("x", data[i]);
			jep.parseExpression(expression);
			Double value = jep.getValue();
			float floatValue = value.floatValue();
			
			
			ByteBuffer buf = ByteBuffer.allocate(4);
			buf.putFloat(0, floatValue);
			
			byte byteOne = buf.get(3);
			byte byteTwo = buf.get(2);
			byte byteThree = buf.get(1);
			byte byteFour = buf.get(0);
			
			if(isLittleEndian){
				this.bytes[baseAddress + i*4] = byteOne;
				this.bytes[baseAddress + i*4 + 1] = byteTwo;
				this.bytes[baseAddress + i*4 + 2] = byteThree;
				this.bytes[baseAddress + i*4 + 3] = byteFour;
			}else{
				this.bytes[baseAddress + i*4] = byteFour;
				this.bytes[baseAddress + i*4 + 1] = byteThree;
				this.bytes[baseAddress + i*4 + 2] = byteTwo;
				this.bytes[baseAddress + i*4 + 3] = byteOne;
			}
		}
	}

	public byte[] getBytes() {
		return bytes;
	}
	
	/**
	 * Logical base address calculations. Hosts sanity checks.
	 * @param baseAddress
	 * @return
	 */
	private int calcBaseAddressOffset(int baseAddress) {
		if(baseAddress + this.addressOffset > 0){
			baseAddress =  baseAddress + this.addressOffset;
			//logger.debug("New base address is :"+baseAddress);
		}else{
			//logger.error("Possible  major issue! baseAddress + offset < 0. WARNING! Keep address :"+baseAddress);
		}
		return baseAddress;
	}
}
