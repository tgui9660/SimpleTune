package simpleTune.romEntity.romParse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RomImageManager {
	private Log logger = LogFactory.getLog(getClass());
	private static RomImageManager romImageManager;
	private int counter = 0;
	//private String FILE_SUFFIX = "_DANGER";
	private String FILE_SUFFIX = "";
	
	// Keyed on number + ROM file system path
	// Ensures that we can open multiple ROMs at the same time
	private HashMap<String, RomImage> romMap = new HashMap<String, RomImage>();
	
	private RomImageManager(){
		
	}
	
	public static RomImageManager getInstance(){
		if(RomImageManager.romImageManager == null){
			RomImageManager.romImageManager = new RomImageManager();
		}
		
		return romImageManager;
	}
	
	public int openROMImage(String romPathLocation){
		// Up counter to ensure unique name
		counter++;
		
		// Add new ROM to map
		romMap.put(counter+":"+romPathLocation, new RomImage(romPathLocation));
		
		// Return the int used to generate a unique hashmap key
		return counter;
	}
	
	public RomImage getROMImage(String tableGroup){
		return romMap.get(tableGroup);
	}
	
	public RomImage getROMImage(int uniqueNumber, String romPathLocation){
		return romMap.get(uniqueNumber+":"+romPathLocation);
	}
	
	/**
	 * Used to save a ROM to disk.
	 * 
	 * Negotiates overwriting roms etc.
	 * 
	 * @param romIDPath
	 * @param newPath
	 */
	public void saveRomImage(String romIDPath, String newPath){
		logger.info("R I M saving rom :"+romIDPath + "    newPath:"+newPath);
		
		// Pull data from tuning group path
		String[] split = romIDPath.split(";");
		int uniqueNumber = Integer.parseInt(split[0]);
		String romPathLocation = split[1];
		
		// Query for image.
		RomImage image = this.getROMImage(uniqueNumber, romPathLocation);
		
		// Quick error check
		if(image == null){
			System.err.println("Major error, cant find rom to save :"+romIDPath);
			return;
		}
		
		// For the time being, only allow NEW files to be written.
		//if(!romPathLocation.equalsIgnoreCase(newPath)){
		if(true){
			String[] pathSplit = newPath.split("\\.");
			newPath = pathSplit[0] + FILE_SUFFIX +"." + pathSplit[1];
			
			logger.info("Writing NEW file to disk :"+newPath);
			
			// Pull bytes that make up the ROM
			byte[] bytes = image.getBytes();
			
			// Define file to write
			File newFile = new File(newPath);
			
			// Open file stream.
			FileOutputStream outPutStream = null;
			try {
				outPutStream = new FileOutputStream(newFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			// To disk now
			try {
				outPutStream.write(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			// Cleanup a little here
			try {
				outPutStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}else{
			
			logger.info("WHOA!!! No writing over files till I'm sure all is kosher.");
		}
		
	}
}
