package simpleTune.gui.tree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.interfaces.TuningEntity;

import javax.swing.JTree;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ETree extends JTree implements MouseListener {
	private Log logger = LogFactory.getLog(getClass());
	
    public ETree(ETreeNode treeRootNode){
		super(treeRootNode);
		
		// Define tree attributes
		UIManager.put( "Tree.hash", Color.BLACK );
		
		setCellRenderer(new ETreeCellRenderer());
		setRootVisible(true);
        setRowHeight(0);
        addMouseListener(this);
        setFont(new Font("LucidaBrightRegular", Font.PLAIN, 11));
	}

	public void mouseClicked(MouseEvent e) {
		if(e == null){
			return;
		}
		if(getPathForLocation(e.getX(), e.getY()) == null){
			return;
		}
		
		Object selectedObject = getPathForLocation(e.getX(), e.getY()).getLastPathComponent();
		
		// Null selection occurs when no tree row is selected
		if(selectedObject == null){
			return;
		}
		
		if(selectedObject instanceof ETreeNode){
			// Get the node in scope
			ETreeNode theNode = (ETreeNode)selectedObject;

			// Set table group in scope
			String tableGroup = theNode.getTableMetaData().getTableGroup();
			if(tableGroup != null && tableGroup != ""){
				ApplicationStateManager.setSelectedTuningGroup(tableGroup);
			}
			
			// If this is a table that contains data, then open it in the right pane in an internal frame
			if(theNode.getTableMetaData().getNodeType() == TableMetaData.DATA_1D || theNode.getTableMetaData().getNodeType() == TableMetaData.DATA_2D || theNode.getTableMetaData().getNodeType() == TableMetaData.DATA_3D|| theNode.getTableMetaData().getNodeType() == TableMetaData.SWITCH){
				logger.debug("ETree Table data:"+theNode.getTableMetaData().getTableIdentifier());
					
				TuningEntity tuningEntity = ApplicationStateManager.getTuningEntity(theNode.getTableMetaData().getSuffix());
				
				if(tuningEntity ==  null){
					logger.info("Can't find an associated tuning entity with file suffix :"+theNode.getTableMetaData().getSuffix());
					return;
				}
				
				Object[][] tableData = tuningEntity.getTableData(theNode.getTableMetaData().getTableGroup(), theNode.getTableMetaData().getTableIdentifier());
				
				if(tableData == null){
					logger.info("Serious error, no table data found for given identifier >"+theNode.getTableMetaData().getTableIdentifier()+"<");
					return;
				}
				
				logger.debug("ETree size:"+tableData.length);
				ApplicationStateManager.getSTInstance().displayInternalFrameTable(tableData, theNode.getTableMetaData(), true);
			}
			
		}
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
