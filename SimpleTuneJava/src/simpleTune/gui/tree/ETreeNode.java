package simpleTune.gui.tree;


import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;

public class ETreeNode extends DefaultMutableTreeNode{
	private Log logger = LogFactory.getLog(getClass());
	private int userLevel = ApplicationStateManager.USER_LEVEL_1;
	private String nodeName = "";
	
	private TableMetaData tableMetaData = null;
	
	public ETreeNode(String nodeName, TableMetaData tableMetaData){
		super(nodeName);
		this.nodeName = nodeName;
		this.tableMetaData = tableMetaData;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String mapName) {
		this.nodeName = mapName;
	}
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public TableMetaData getTableMetaData() {
		return tableMetaData;
	}
}
