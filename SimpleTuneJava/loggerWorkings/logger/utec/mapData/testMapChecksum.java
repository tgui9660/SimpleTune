package logger.utec.mapData;

import utecEntity.UtecMapData;

public class testMapChecksum {
	
	public static void main(String[] args){
		//logger.info("Testing checksum");
		UtecMapData testMapData = new UtecMapData();
		testMapData.importFileData("c:\\stistage1olf.TXT");
		
	}
}
