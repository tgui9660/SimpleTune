package simpleTune.gui.data;


import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.tree.TreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.SimpleTuneGUI;
import simpleTune.gui.etable.EInternalFrame;
import simpleTune.gui.interfaces.TuningEntity;
import simpleTune.gui.tree.ETreeNode;

public class ApplicationStateManager {
	private static Log logger = LogFactory.getLog(ApplicationStateManager.class);
	public static int MAIN_TABLE_DATA = 0;
	public static int XAXIS_TABLE_DATA = 1;
	public static int YAXIS_TABLE_DATA = 2;
	public static int SWITCH_TABLE_DATA = 3;
	public static int STRING_TABLE_DATA = 4;
	
	public static final int USER_LEVEL_1 = 1;
	public static final int USER_LEVEL_2 = 2;
	public static final int USER_LEVEL_3 = 3;
	public static final int USER_LEVEL_4 = 4;
	public static final int USER_LEVEL_5 = 5;
	
	private static Vector<TuningEntity> tuningEntities = new Vector<TuningEntity>();
	private static TuningEntity currentTuningEntity;
	private static int currentUserLevel = ApplicationStateManager.USER_LEVEL_1;
	
	private static SimpleTuneGUI stInstance = null;
	
	//private static String selectedTuningGroup = "";
	
	private static String currentTableGroup = "";
	private static String currentTableIdentifier = "";
	
	private static int openTuningGroupCount = 0;
	
	private static Vector<String> tuningGroups = new Vector<String>();
	
	// Dirty, yup, global var used for recursive tree walking
	private static Vector<String> tableList = new Vector<String>();
	
	
	public static void incOpenTuningGroupCount(String tuningGroup){
		openTuningGroupCount++;
		tuningGroups.add(tuningGroup);
	}
	
	public static void decOpenTuningGroupCount(String tuningGroup){
		if(openTuningGroupCount == 0){
			return;
		}
		openTuningGroupCount--;
		tuningGroups.remove(tuningGroup);
	}
	
	public static int getOpenTuningGroupCount(){
		return openTuningGroupCount;
	}
	
	public static Vector<TuningEntity> getTuningEntities() {
		return tuningEntities;
	}

	public static void addTuningEntity(TuningEntity tuningEntity) {
		tuningEntities.add(tuningEntity);
	}
	
	
	public static int getTuningEntityCount(){
		return tuningEntities.size();
	}
	
	public static void notifyTuningEntitiesOfClose(){
		Iterator<TuningEntity> iterator = tuningEntities.iterator();
		while(iterator.hasNext()){
			TuningEntity tuningEntity  = iterator.next();
			tuningEntity.notifySystemExit();
		}
	}
	
	public static TuningEntity getTuningEntity(String fileSuffix){

		Iterator<TuningEntity> iterator = tuningEntities.iterator();
		while(iterator.hasNext()){
			TuningEntity tuningEntity  = iterator.next();
			String[] fileSuffixes = tuningEntity.getFileSuffixes();
			for(int i=0; i < fileSuffixes.length; i++){
				if(fileSuffixes[i].equalsIgnoreCase(fileSuffix)){
					return tuningEntity;
				}
			}
		}
		
		return null;
	}
	
	public static int getCurrentUserLevel() {
		return currentUserLevel;
	}

	public static void setCurrentUserLevel(int currentUserLevel) {
		ApplicationStateManager.currentUserLevel = currentUserLevel;
	}
	
	public static SimpleTuneGUI getSTInstance() {
		return stInstance;
	}

	public static void setSTInstance(SimpleTuneGUI stInstance) {
		ApplicationStateManager.stInstance = stInstance;
	}

	public static void setCurrentTableIdentifier(String currentTableIdentifier){
		ApplicationStateManager.currentTableIdentifier = currentTableIdentifier;
	}
	
	public static String getCurrentTableIdentifier(){
		return currentTableIdentifier;
	}
	
	public static void setSelectedTuningGroup(String selectedTuningGroup){
		logger.info("Setting selected tuning group :"+selectedTuningGroup);
		
		if(selectedTuningGroup == null || selectedTuningGroup.length() == 0){
			return;
		}
		
		// Set ST Gui title based on table group
		ApplicationStateManager.getSTInstance().addTuningGroupNameToTitle(selectedTuningGroup);
		
		ApplicationStateManager.currentTableGroup = selectedTuningGroup;
	}
	
	public static String getSelectedTuningGroup() {
		return currentTableGroup;
	}

	public static Vector<String> getOpenTuningGroups(){
		return tuningGroups;
	}
	
	public static TuningEntity getTGTuningEntity(String tuningGroup){
		TableMetaData tableMetaData = ApplicationStateManager.getTGTableMetaData(tuningGroup);
		return ApplicationStateManager.getTuningEntity(tableMetaData.getSuffix());
	}
	public static TableMetaData getTGTableMetaData(String tuningGroup){
		return ApplicationStateManager.stInstance.getNode(tuningGroup).getTableMetaData();
	}
	public static TableMetaData getTGTableMetaData(String tableIdentifier, String tuningGroup){
		logger.info("Searching for tableMetaData for   identifier:"+tableIdentifier+"   tuningGroup:"+tuningGroup);
		ETreeNode node = ApplicationStateManager.stInstance.getNode(tuningGroup);
		return getTableMetaData(tableIdentifier, node);
	}
	public static Vector<String> getTableList(String tuningGroup){
		ETreeNode node = ApplicationStateManager.stInstance.getNode(tuningGroup);
		
		// Recursively gather all leaf children
		ApplicationStateManager.tableList = new Vector<String>();
		walkTree((ETreeNode)node);
		
		return ApplicationStateManager.tableList;
	}
	
	private static TableMetaData getTableMetaData(String tableIdentifier, ETreeNode tree){
		Enumeration<ETreeNode> depthFirstEnumeration = tree.depthFirstEnumeration();
		
		while(depthFirstEnumeration.hasMoreElements()){
			ETreeNode nextElement = depthFirstEnumeration.nextElement();
			
			//logger.info("Comparing  "+tableIdentifier +"  :to:  "+nextElement.getTableMetaData().getTableIdentifier());
			if(nextElement.isLeaf()){
				if(nextElement.getTableMetaData().getTableIdentifier().equalsIgnoreCase(tableIdentifier)){
					logger.info("Match Found!");
					return nextElement.getTableMetaData();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Heler method to gather all leaf nodes that represent tables
	 * @param tree
	 */
	private static void walkTree(ETreeNode tree){
		Enumeration<ETreeNode> children = tree.children();;
		
		while(children.hasMoreElements()){
			ETreeNode nextElement = children.nextElement();
			
			if(nextElement.isLeaf()){
				ApplicationStateManager.tableList.add(nextElement.getTableMetaData().getTableIdentifier());
			}else{
				walkTree(nextElement);
			}
		}
	}
	
	public static void refresh(){
		logger.info("Refreshing entire application.");
		ApplicationStateManager.stInstance.refreshEInternalFrames();
	}
	
	public static EInternalFrame getExistingEInternalFrame(TableMetaData tableMetaData){
		JInternalFrame[] allFrames = ApplicationStateManager.stInstance.getRightDesktop().getAllFrames();
		
		for(int i = 0; i < allFrames.length ; i++){
			EInternalFrame frame = (EInternalFrame)allFrames[i];
			TableMetaData tableMetaDataFrame = frame.getTableMetaData();
			if(tableMetaDataFrame.equals(tableMetaData)){
				return frame;
			}
		}
		
		return null;
	}
	
	/*
	public static void setSelectedTuningGroup(String selectedTuningGroup) {
		//logger.info("Application state manager setting tuning group: "+selectedTuningGroup);
		ApplicationStateManager.selectedTuningGroup = selectedTuningGroup;
		//ApplicationStateManager.getCurrentTuningEntity().setCurrentTuningGroup(selectedTuningGroup);
	}
	*/
}
