package simpleTune.romEntity.xmlParse;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import simpleTune.romEntity.xmlParse.romXMLData.RomIdXML;
import simpleTune.romEntity.xmlParse.romXMLData.StateXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableAxisXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableScalingXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableStaticScalingXML;
import simpleTune.romEntity.xmlParse.romXMLData.TableXML;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class ECUDefXMLParser {
	private static Log logger = LogFactory.getLog(ECUDefXMLParser.class);
	/**
	 * Main static method to parse an XML Def file
	 * @param fileNamePath
	 */
	public static void ParseXMLDefFile(String fileNamePath) {
		logger.info("Found XML Def :"+fileNamePath);
		
		DOMParser parser = new DOMParser();

		try {
			parser.parse(fileNamePath);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		DocumentImpl document = (DocumentImpl)parser.getDocument();

		NodeList nodes = document.getElementsByTagName("rom");

		//logger.info("Node count "+nodes.getLength());
		//String baseName = "";
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			String baseAttribute = element.getAttribute("base");
			String[] split = baseAttribute.split(":");
			if(split.length == 2){
				baseAttribute = split[1]; // CAL ID in xml is "CAL ID:XXXXXXXX", no bueno
			}
			
			boolean isBase = false;
			
			
			if(baseAttribute != null && baseAttribute.length() == 0){
				//logger.info("BASE found.\n----------------------");
				isBase = true;
			}else{
				//logger.info("ROM found.\n----------------------");
				
			}
			
			

			NodeList childNodes = element.getChildNodes();
			//logger.info("Child Nodes count :"+childNodes.getLength());
			
			String currentXMLID = "";
			
			for(int j = 0; j < childNodes.getLength(); j++){
				Node romChild = childNodes.item(j);

				// Save off attribute data for later use
				NamedNodeMap attributes = romChild.getAttributes();

				// **********
				// ROMID Data
				// **********
				if(romChild.getLocalName() != null && romChild.getLocalName().equalsIgnoreCase("romid")){
					
					currentXMLID = PullRomidData(romChild, isBase, baseAttribute);
					//baseName = PullRomidData(romChild, isBase, baseName);
					//PullRomidData(romChild, isBase, baseName);
				}

				// **********
				// TABLE Data
				// **********
				if(romChild.getLocalName() != null && romChild.getLocalName().equalsIgnoreCase("table")){
					PullTableData(romChild, attributes, isBase, currentXMLID);
				}

			}
		}
	}
	

	/**
	 * Pulls ID Data, returns base name.
	 * 
	 * @param romChild
	 * @param isBase
	 * @return
	 */
	private static String PullRomidData(Node romChild, boolean isBase, String baseName) {
		NodeList romIdChildNodes = romChild.getChildNodes();

		TableXML romMetaData = new TableXML();
		romMetaData.setSimpleTuneTYPE(RomXMLDataManager.TableRomID);
		romMetaData.setBase(baseName.toLowerCase());
		
		String xmlID = "";
		
		for(int k = 0; k < romIdChildNodes.getLength(); k++){
			Node item = romIdChildNodes.item(k);
			if(item.getNodeName().equalsIgnoreCase("xmlid")){
				//logger.info("xmlid   :"+item.getTextContent());
				xmlID = item.getTextContent();
				String[] split = xmlID.split(":");
				if(split.length == 2){
					xmlID = split[1]; // CAL ID in xml is "CAL ID:XXXXXXXX", no bueno
				}
				romMetaData.setXmlid(xmlID);
			}else if(item.getNodeName().equalsIgnoreCase("make")){
				//logger.info("make :"+item.getTextContent());
				romMetaData.setMake(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("model")){
				//logger.info("model :"+item.getTextContent());
				romMetaData.setModel(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("submodel")){
				//logger.info("submodel :"+item.getTextContent());
				romMetaData.setSubmodel(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("filesize")){
				//logger.info("filesize :"+item.getTextContent());
				romMetaData.setFilesize(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("internalidaddress")){
				//logger.info("internalidaddress :"+item.getTextContent());
				romMetaData.setInternalidaddress(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("internalidstring")){
				//logger.info("internalidstring :"+item.getTextContent());
				romMetaData.setInternalidstring(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("year")){
				//logger.info("year :"+item.getTextContent());
				romMetaData.setYear(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("market")){
				//logger.info("market :"+item.getTextContent());
				romMetaData.setMarket(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("transmission")){
				//logger.info("transmission :"+item.getTextContent());
				romMetaData.setTransmission(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("memmodel")){
				//logger.info("memmodel :"+item.getTextContent());
				romMetaData.setMemmodel(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("flashmethod")){
				//logger.info("flashmethod :"+item.getTextContent());
				romMetaData.setFlashmethod(item.getTextContent());
			}else if(item.getNodeName().equalsIgnoreCase("ecuid")){
				//logger.info("ecuid :"+item.getTextContent());
				romMetaData.setEcuid(item.getTextContent());
			}
		}
		
		//logger.debug("Metadata table added to base table data :"+xmlID);
		
		// Save table to table map
		RomXMLDataManager.getInstance().AddTable(xmlID, "romId", romMetaData, isBase);
		
		// Only return base if this is a new known base node
		//if(isBase){
		//	return newBaseName;
		//}
		
		return xmlID;
	}

	private static void PullTableData(Node romChild, NamedNodeMap attributes, boolean isBase, String baseName) {
		Node typeAttribute = attributes.getNamedItem("type");
		Node storageAddress = attributes.getNamedItem("storageaddress");
		
		if(typeAttribute !=null && typeAttribute.getNodeValue().equalsIgnoreCase("3D") && storageAddress == null){
			//logger.info("******** Table 3D Found :" + baseName + ":");
			
			String tableName = "";
			
			TableXML table3dXML = new TableXML();
			table3dXML.setSimpleTuneTYPE(RomXMLDataManager.Table3D);
			
			if(attributes.getNamedItem("category") != null){
				table3dXML.setCategory(attributes.getNamedItem("category").getNodeValue());
			}
			if(attributes.getNamedItem("endian") != null){
				table3dXML.setEndian(attributes.getNamedItem("endian").getNodeValue());
			}
			if(attributes.getNamedItem("logparam") != null){
				table3dXML.setLogparam(attributes.getNamedItem("logparam").getNodeValue());
			}
			if(attributes.getNamedItem("name") != null){
				table3dXML.setName(attributes.getNamedItem("name").getNodeValue());
				tableName = attributes.getNamedItem("name").getNodeValue();
			}
			if(attributes.getNamedItem("sizex") != null){
				table3dXML.setSizex(Integer.parseInt(attributes.getNamedItem("sizex").getNodeValue()));
			}
			if(attributes.getNamedItem("sizey") != null){
				table3dXML.setSizey(Integer.parseInt(attributes.getNamedItem("sizey").getNodeValue()));
			}
			if(attributes.getNamedItem("storagetype") != null){
				table3dXML.setStoragetype(attributes.getNamedItem("storagetype").getNodeValue());
			}
			if(attributes.getNamedItem("type") != null){
				table3dXML.setType(attributes.getNamedItem("type").getNodeValue());
			}
			if(attributes.getNamedItem("userlevel") != null){
				table3dXML.setUserlevel(attributes.getNamedItem("userlevel").getNodeValue());
			}
			if(attributes.getNamedItem("beforeram") != null){
				String nodeValue = attributes.getNamedItem("userlevel").getNodeValue();
				if(nodeValue.toLowerCase().equalsIgnoreCase("true")){
					table3dXML.setBeforeRam(true);
				}
			}
			
			NodeList table3DChildren = romChild.getChildNodes();
			for(int k = 0; k < table3DChildren.getLength(); k++){
				Node item = table3DChildren.item(k);
				NamedNodeMap table3DChildrenAttributes = item.getAttributes();
				
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("scaling")){
					//logger.info("------- Scaling Data");
					TableScalingXML tableScalingXML = PullScalingData(table3DChildrenAttributes);
					table3dXML.setScaling(tableScalingXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("table") && table3DChildrenAttributes.getNamedItem("type") != null && table3DChildrenAttributes.getNamedItem("type").getNodeValue().equalsIgnoreCase("X Axis")){
					//logger.info("------- X Axis");
					TableAxisXML tableAxisXML = PullTableAxisXML(table3DChildrenAttributes, item);
					table3dXML.setXAxis(tableAxisXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("table") && table3DChildrenAttributes.getNamedItem("type") != null && table3DChildrenAttributes.getNamedItem("type").getNodeValue().equalsIgnoreCase("Y Axis")){
					//logger.info("------- Y Axis");
					TableAxisXML tableAxisXML = PullTableAxisXML(table3DChildrenAttributes, item);
					table3dXML.setYAxis(tableAxisXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("description")){
					//logger.info("------- Description Data");
					String description = item.getTextContent();
					table3dXML.setDescription(description);
				}
			}
			
			// Save table to database of tables
			RomXMLDataManager.getInstance().AddTable(baseName, tableName, table3dXML, isBase);
		}
		
		else if(typeAttribute !=null && typeAttribute.getNodeValue().equalsIgnoreCase("2D") && storageAddress == null){
			//logger.info("******** 2D Table Found");
			
			String tableName = "";
			
			TableXML table2dXML = new TableXML();
			table2dXML.setSimpleTuneTYPE(RomXMLDataManager.Table2D);
			
			if(attributes.getNamedItem("category") != null){
				table2dXML.setCategory(attributes.getNamedItem("category").getNodeValue());
			}
			if(attributes.getNamedItem("endian") != null){
				table2dXML.setEndian(attributes.getNamedItem("endian").getNodeValue());
			}
			if(attributes.getNamedItem("logparam") != null){
				table2dXML.setLogparam(attributes.getNamedItem("logparam").getNodeValue());
			}
			if(attributes.getNamedItem("name") != null){
				table2dXML.setName(attributes.getNamedItem("name").getNodeValue());
				tableName = attributes.getNamedItem("name").getNodeValue();
			}
			if(attributes.getNamedItem("sizey") != null){
				table2dXML.setSizey(Integer.parseInt(attributes.getNamedItem("sizey").getNodeValue()));
			}
			if(attributes.getNamedItem("storagetype") != null){
				table2dXML.setStoragetype(attributes.getNamedItem("storagetype").getNodeValue());
			}
			if(attributes.getNamedItem("type") != null){
				table2dXML.setType(attributes.getNamedItem("type").getNodeValue());
			}
			if(attributes.getNamedItem("userlevel") != null){
				table2dXML.setUserlevel(attributes.getNamedItem("userlevel").getNodeValue());
			}
			if(attributes.getNamedItem("beforeram") != null){
				String nodeValue = attributes.getNamedItem("beforeram").getNodeValue();
				if(nodeValue.toLowerCase().equalsIgnoreCase("true")){
					table2dXML.setBeforeRam(true);
				}
			}
			
			NodeList table2DChildren = romChild.getChildNodes();
			for(int k = 0; k < table2DChildren.getLength(); k++){
				Node item = table2DChildren.item(k);
				NamedNodeMap table3DChildrenAttributes = item.getAttributes();
				
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("scaling")){
					//logger.info("------- Scaling Data");
					TableScalingXML tableScalingXML = PullScalingData(table3DChildrenAttributes);
					table2dXML.setScaling(tableScalingXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("table") && table3DChildrenAttributes.getNamedItem("type") != null && table3DChildrenAttributes.getNamedItem("type").getNodeValue().equalsIgnoreCase("Y Axis")){
					//logger.info("------- Y Axis");
					TableAxisXML tableAxisXML = PullTableAxisXML(table3DChildrenAttributes, item);
					table2dXML.setYAxis(tableAxisXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("table") && table3DChildrenAttributes.getNamedItem("type") != null && table3DChildrenAttributes.getNamedItem("type").getNodeValue().equalsIgnoreCase("Static Y Axis")){
					//logger.info("------- Static Y Axis Found :"+tableName);
					TableStaticScalingXML tableStaticScalingXML = PullStaticScaling(item, table3DChildrenAttributes);
					table2dXML.setStaticScaling(tableStaticScalingXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("description")){
					//logger.info("------- Description Data");
					String description = item.getTextContent();
					table2dXML.setDescription(description);
				}
			}
			
			// Save table to database of tables
			RomXMLDataManager.getInstance().AddTable(baseName, tableName, table2dXML, isBase);
		}
		
		else if(typeAttribute !=null && typeAttribute.getNodeValue().equalsIgnoreCase("1D") && storageAddress == null){
			//logger.info("******** 1D Table Found");
			
			String tableName = "";
			
			TableXML table1dXML = new TableXML();
			table1dXML.setSimpleTuneTYPE(RomXMLDataManager.Table1D);
			
			table1dXML.setType("1D");
			
			if(attributes.getNamedItem("name") != null){
				table1dXML.setName(attributes.getNamedItem("name").getNodeValue());
				tableName = attributes.getNamedItem("name").getNodeValue();
			}
			if(attributes.getNamedItem("category") != null){
				table1dXML.setCategory(attributes.getNamedItem("category").getNodeValue());
			}
			if(attributes.getNamedItem("storagetype") != null){
				table1dXML.setStoragetype(attributes.getNamedItem("storagetype").getNodeValue());
			}
			if(attributes.getNamedItem("endian") != null){
				table1dXML.setEndian(attributes.getNamedItem("endian").getNodeValue());
			}
			if(attributes.getNamedItem("sizey") != null){
				table1dXML.setSizey(Integer.parseInt(attributes.getNamedItem("sizey").getNodeValue()));
			}
			if(attributes.getNamedItem("userlevel") != null){
				table1dXML.setUserlevel(attributes.getNamedItem("userlevel").getNodeValue());
			}
			if(attributes.getNamedItem("beforeram") != null){
				String nodeValue = attributes.getNamedItem("beforeram").getNodeValue();
				if(nodeValue.toLowerCase().equalsIgnoreCase("true")){
					table1dXML.setBeforeRam(true);
				}
			}
			
			NodeList table1DChildren = romChild.getChildNodes();
			for(int k = 0; k < table1DChildren.getLength(); k++){
				Node item = table1DChildren.item(k);
				NamedNodeMap table1DChildrenAttributes = item.getAttributes();
				
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("scaling")){
					//logger.info("------- Scaling Data");
					TableScalingXML tableScalingXML = PullScalingData(table1DChildrenAttributes);
					table1dXML.setScaling(tableScalingXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("table") && table1DChildrenAttributes.getNamedItem("type") != null && table1DChildrenAttributes.getNamedItem("type").getNodeValue().equalsIgnoreCase("Y Axis")){
					//logger.info("------- Y Axis");
					TableAxisXML tableAxisXML = PullTableAxisXML(table1DChildrenAttributes, item);
					table1dXML.setYAxis(tableAxisXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("table") && table1DChildrenAttributes.getNamedItem("type") != null && table1DChildrenAttributes.getNamedItem("type").getNodeValue().equalsIgnoreCase("Static Y Axis")){
					//logger.info("------- Static Axis");
					TableStaticScalingXML tableStaticScalingXML = PullStaticScaling(item, table1DChildrenAttributes);
					table1dXML.setStaticScaling(tableStaticScalingXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("description")){
					//logger.info("------- Description Data");
					String description = item.getTextContent();
					table1dXML.setDescription(description);
				}
			}
			// Save table to database of tables
			RomXMLDataManager.getInstance().AddTable(baseName, tableName, table1dXML, isBase);
		}
		
		else if(typeAttribute !=null && typeAttribute.getNodeValue().equalsIgnoreCase("switch") && storageAddress == null){
			//logger.info("******** Switch Table Found");
			
			String tableName = "";
			
			TableXML switchXML = new TableXML();
			switchXML.setSimpleTuneTYPE(RomXMLDataManager.TableSwitch);
			
			switchXML.setType("Switch");
			
			if(attributes.getNamedItem("name") != null){
				switchXML.setName(attributes.getNamedItem("name").getNodeValue());
				tableName = attributes.getNamedItem("name").getNodeValue();
			}
			if(attributes.getNamedItem("category") != null){
				switchXML.setCategory(attributes.getNamedItem("category").getNodeValue());
			}
			if(attributes.getNamedItem("sizey") != null){
				switchXML.setSizey(Integer.parseInt(attributes.getNamedItem("sizey").getNodeValue()));
			}
			if(attributes.getNamedItem("storageaddress") != null){
				String hexString = attributes.getNamedItem("storageaddress").getNodeValue();
				String[] split = hexString.split("x");
				
				switchXML.setStorageaddress(Long.parseLong(split[1], 16));
			}
			if(attributes.getNamedItem("beforeram") != null){
				String nodeValue = attributes.getNamedItem("beforeram").getNodeValue();
				if(nodeValue.toLowerCase().equalsIgnoreCase("true")){
					switchXML.setBeforeRam(true);
				}
			}
			
			NodeList switchChildNodes = romChild.getChildNodes();
			for(int k = 0; k < switchChildNodes.getLength(); k++){
				Node item = switchChildNodes.item(k);
				
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("description")){
					//logger.info("------- Description Data");
					String description = item.getTextContent();
					switchXML.setDescription(description);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("state")){
					//logger.info("------- State Data");
					
					StateXML newState = new StateXML();
					
					NamedNodeMap stateAttributes = item.getAttributes();
					
					Node namedItem = stateAttributes.getNamedItem("name");
					if(namedItem != null){
						newState.setName(namedItem.getNodeValue());
					}

					Node dataItem = stateAttributes.getNamedItem("data");
					if(dataItem != null){
						newState.setData(dataItem.getNodeValue());
					}
					
					switchXML.addState(newState);
				}
			}

			// Save table to database of tables
			RomXMLDataManager.getInstance().AddTable(baseName, tableName, switchXML, isBase);
		}
		else if(storageAddress != null){
			//logger.info("Data table here, basename:"+baseName+":");
			String tableName = "";
			
			TableXML tableDataXML = new TableXML();
			tableDataXML.setSimpleTuneTYPE(RomXMLDataManager.TableData);
			
			tableDataXML.setType("Data");
			
			if(attributes.getNamedItem("category") != null){
				tableDataXML.setCategory(attributes.getNamedItem("category").getNodeValue());
			}
			if(attributes.getNamedItem("endian") != null){
				tableDataXML.setEndian(attributes.getNamedItem("endian").getNodeValue());
			}
			if(attributes.getNamedItem("logparam") != null){
				tableDataXML.setLogparam(attributes.getNamedItem("logparam").getNodeValue());
			}
			if(attributes.getNamedItem("name") != null){
				tableDataXML.setName(attributes.getNamedItem("name").getNodeValue());
				tableName = attributes.getNamedItem("name").getNodeValue();
				//logger.info("---->"+tableName);
				if(tableName.contains("hecksum")){
					//logger.info("Data table here, basename:"+baseName+":");
					//logger.info("table name ---->"+tableName);
					//logger.info("table type ---->"+typeAttribute.getNodeValue());
				}
			}
			if(attributes.getNamedItem("sizex") != null){
				tableDataXML.setSizex(Integer.parseInt(attributes.getNamedItem("sizex").getNodeValue()));
			}
			if(attributes.getNamedItem("sizey") != null){
				tableDataXML.setSizey(Integer.parseInt(attributes.getNamedItem("sizey").getNodeValue()));
			}
			if(attributes.getNamedItem("storagetype") != null){
				tableDataXML.setStoragetype(attributes.getNamedItem("storagetype").getNodeValue());
			}
			if(attributes.getNamedItem("type") != null){
				tableDataXML.setType(attributes.getNamedItem("type").getNodeValue());
			}
			if(attributes.getNamedItem("userlevel") != null){
				tableDataXML.setUserlevel(attributes.getNamedItem("userlevel").getNodeValue());
			}
			if(attributes.getNamedItem("storageaddress") != null){
				String hexString = attributes.getNamedItem("storageaddress").getNodeValue();
				String[] split = hexString.split("x");
				
				tableDataXML.setStorageaddress(Long.parseLong(split[1], 16));
			}
			if(attributes.getNamedItem("beforeram") != null){
				String nodeValue = attributes.getNamedItem("beforeram").getNodeValue();
				if(nodeValue.toLowerCase().equalsIgnoreCase("true")){
					tableDataXML.setBeforeRam(true);
				}
			}
			
			NodeList table3DChildren = romChild.getChildNodes();
			for(int k = 0; k < table3DChildren.getLength(); k++){
				Node item = table3DChildren.item(k);
				NamedNodeMap table3DChildrenAttributes = item.getAttributes();
				
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("scaling")){
					//logger.info("------- Scaling Data");
					TableScalingXML tableScalingXML = PullScalingData(table3DChildrenAttributes);
					tableDataXML.setScaling(tableScalingXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("table") && table3DChildrenAttributes.getNamedItem("type") != null && table3DChildrenAttributes.getNamedItem("type").getNodeValue().equalsIgnoreCase("X Axis")){
					//logger.info("------- X Axis");
					TableAxisXML tableAxisXML = PullTableAxisXML(table3DChildrenAttributes, item);
					tableDataXML.setXAxis(tableAxisXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("table") && table3DChildrenAttributes.getNamedItem("type") != null && table3DChildrenAttributes.getNamedItem("type").getNodeValue().equalsIgnoreCase("Y Axis")){
					//logger.info("------- Y Axis");
					TableAxisXML tableAxisXML = PullTableAxisXML(table3DChildrenAttributes, item);
					tableDataXML.setYAxis(tableAxisXML);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("description")){
					//logger.info("------- Description Data");
					String description = item.getTextContent();
					tableDataXML.setDescription(description);
				}
				if(item.getNodeName() != null && item.getNodeName().equalsIgnoreCase("state")){
					//logger.info("------- State Data");
					//tableDataXML.setSimpleTuneTYPE(RomXMLDataManager.TableSwitch);
					
					StateXML newState = new StateXML();
					
					NamedNodeMap stateAttributes = item.getAttributes();
					
					Node namedItem = stateAttributes.getNamedItem("name");
					if(namedItem != null){
						newState.setName(namedItem.getNodeValue());
					}

					Node dataItem = stateAttributes.getNamedItem("data");
					if(dataItem != null){
						newState.setData(dataItem.getNodeValue());
					}
					
					tableDataXML.addState(newState);
				}
			}
			
			// Save table to database of tables
			RomXMLDataManager.getInstance().AddTable(baseName, tableName, tableDataXML, isBase);
		}
	}

	private static TableStaticScalingXML PullStaticScaling(Node item, NamedNodeMap table3DChildrenAttributes) {
		TableStaticScalingXML tableStaticScalingXML = new TableStaticScalingXML();
		
		if(table3DChildrenAttributes.getNamedItem("type") != null){
			//logger.info("type :"+table3DChildrenAttributes.getNamedItem("type").getNodeValue());
			tableStaticScalingXML.setType(table3DChildrenAttributes.getNamedItem("type").getNodeValue());
		}
		if(table3DChildrenAttributes.getNamedItem("name") != null){
			//logger.info("name :"+table3DChildrenAttributes.getNamedItem("name").getNodeValue());
			tableStaticScalingXML.setName(table3DChildrenAttributes.getNamedItem("name").getNodeValue());
		}
		if(table3DChildrenAttributes.getNamedItem("sizey") != null){
			//logger.info("sizey :"+table3DChildrenAttributes.getNamedItem("sizey").getNodeValue());
			tableStaticScalingXML.setSizey(Integer.parseInt(table3DChildrenAttributes.getNamedItem("sizey").getNodeValue()));
		}
		if(table3DChildrenAttributes.getNamedItem("logparam") != null){
			//logger.info("logparam :"+table3DChildrenAttributes.getNamedItem("logparam").getNodeValue());
			tableStaticScalingXML.setType(table3DChildrenAttributes.getNamedItem("logparam").getNodeValue());
		}
		NodeList childNodes = item.getChildNodes();
		for(int p = 0; p < childNodes.getLength(); p++){
			Node dataItem = childNodes.item(p);
			if(dataItem.getNodeName() != null && dataItem.getNodeName().equalsIgnoreCase("data")){
				//logger.info("----- :"+dataItem.getTextContent());
				tableStaticScalingXML.addData(dataItem.getTextContent());
			}
		}
		return tableStaticScalingXML;
	}

	private static TableAxisXML PullTableAxisXML(NamedNodeMap table3DChildrenAttributes, Node item) {
		TableAxisXML tableAxisXML = new TableAxisXML();

		Node typeItem = table3DChildrenAttributes.getNamedItem("type");
		if(typeItem != null){
			tableAxisXML.setType(typeItem.getNodeValue());
		}
		
		Node nameItem = table3DChildrenAttributes.getNamedItem("name");
		if(nameItem != null){
			tableAxisXML.setName(nameItem.getNodeValue());
		}
		
		Node storagetypeItem = table3DChildrenAttributes.getNamedItem("storagetype");
		if(storagetypeItem != null){
			tableAxisXML.setStoragetype(storagetypeItem.getNodeValue());
		}
		
		Node endianItem = table3DChildrenAttributes.getNamedItem("endian");
		if(endianItem != null){
			tableAxisXML.setEndian(endianItem.getNodeValue());
		}

		Node logparamItem = table3DChildrenAttributes.getNamedItem("logparam");
		if(logparamItem != null){
			tableAxisXML.setLogparam(logparamItem.getNodeValue());
		}
		
		Node storageaddressItem = table3DChildrenAttributes.getNamedItem("storageaddress");
		if(storageaddressItem != null){
			String hexString = storageaddressItem.getNodeValue();
			String[] split = hexString.split("x");
			
			tableAxisXML.setStorageaddress(Long.parseLong(split[1], 16));
		}
		Node beforeRamItem = table3DChildrenAttributes.getNamedItem("beforeram");
		if(beforeRamItem != null){
			String nodeValue = beforeRamItem.getNodeValue();
			if(nodeValue.toLowerCase().equalsIgnoreCase("true")){
				tableAxisXML.setBeforeRam(true);
			}
		}

		
		// Gather the scaling information
		//Node firstChild = item.getFirstChild();
		//Node firstChild = typeItem.getFirstChild();
		//NamedNodeMap attributes = firstChild.getAttributes();
		NodeList childNodes = item.getChildNodes();
		int length = childNodes.getLength();
		if(length > 0){
			//logger.info("Pulling axis data.");
			
			
			//logger.info("Length :"+length);
			
			for(int i = 0; i < length ; i++){
				Node item2 = childNodes.item(i);
				String nodeName = item2.getNodeName();
				if(nodeName.equalsIgnoreCase("scaling")){
					NamedNodeMap attributes = item2.getAttributes();
					TableScalingXML scalingData = PullScalingData(attributes);
					tableAxisXML.setScalingData(scalingData);
				}
				//logger.info("nodeName :"+nodeName);
			}
		}

		/*
		if(attributes != null){
			logger.info("Pulling scaling data.");
			TableScalingXML scalingData = PullScalingData(attributes);
			tableAxisXML.setScalingData(scalingData);
		}
		*/
		return tableAxisXML;
	}

	private static TableScalingXML PullScalingData(NamedNodeMap table3DChildrenAttributes) {
		TableScalingXML tableScalingXML = new TableScalingXML();
		
		if(table3DChildrenAttributes.getNamedItem("units") != null){
			tableScalingXML.setUnits(table3DChildrenAttributes.getNamedItem("units").getNodeValue());
		}
		if(table3DChildrenAttributes.getNamedItem("expression") != null){
			tableScalingXML.setExpression(table3DChildrenAttributes.getNamedItem("expression").getNodeValue());
		}
		if(table3DChildrenAttributes.getNamedItem("to_byte") != null){
			tableScalingXML.setTo_byte(table3DChildrenAttributes.getNamedItem("to_byte").getNodeValue());
		}
		if(table3DChildrenAttributes.getNamedItem("format") != null){
			tableScalingXML.setFormat(table3DChildrenAttributes.getNamedItem("format").getNodeValue());
		}
		if(table3DChildrenAttributes.getNamedItem("fineincrement") != null){
			tableScalingXML.setFineincrement(Double.parseDouble(table3DChildrenAttributes.getNamedItem("fineincrement").getNodeValue()));
		}
		if(table3DChildrenAttributes.getNamedItem("coarseincrement") != null){
			tableScalingXML.setFineincrement(Double.parseDouble(table3DChildrenAttributes.getNamedItem("coarseincrement").getNodeValue()));
		}
		//logger.info(tableScalingXML);
		return tableScalingXML;
	}
}
