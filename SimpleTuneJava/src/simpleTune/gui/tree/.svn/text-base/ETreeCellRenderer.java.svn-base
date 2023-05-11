package simpleTune.gui.tree;


import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class ETreeCellRenderer implements TreeCellRenderer{
	private Log logger = LogFactory.getLog(getClass());
    DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();
    
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
    	
    	Component returnValue = null;
    	
    	if(value != null && value instanceof ETreeNode){
    		
    		ETreeNode eTreeNode = (ETreeNode)value;
    		
    		JPanel namedJPanel = new JPanel(new GridLayout(1, 1));
            namedJPanel.setBorder(createLineBorder(Color.LIGHT_GRAY));
            JLabel nodeName = new JLabel("");
            namedJPanel.setBackground(Color.LIGHT_GRAY);
    		
            // Define appropriate ICON to use for node
    		if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.DATA_1D){
    			nodeName = new JLabel(eTreeNode.getNodeName() + " ", new ImageIcon("./graphics/treeNodeImages/1d.gif"), JLabel.LEFT);
    		}else if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.DATA_2D){
    			nodeName = new JLabel(eTreeNode.getNodeName() + " ", new ImageIcon("./graphics/treeNodeImages/2d.gif"), JLabel.LEFT);
    		}else if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.DATA_3D){
    			nodeName = new JLabel(eTreeNode.getNodeName() + " ", new ImageIcon("./graphics/treeNodeImages/3d.gif"), JLabel.LEFT);
    		}else if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.SWITCH){
    			nodeName = new JLabel(eTreeNode.getNodeName() + " ", new ImageIcon("./graphics/treeNodeImages/switch.gif"), JLabel.LEFT);
    			//logger.info("USING switch image.");
    		}else if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.CATEGORY){
    			nodeName = new JLabel(eTreeNode.getNodeName() + " ", new ImageIcon("./graphics/treeNodeImages/folder.png"), JLabel.LEFT);
    			//logger.info("USING switch image.");
    		}else if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.RESERVED_ROOT){
    			nodeName = new JLabel(eTreeNode.getNodeName() + " ", new ImageIcon("./graphics/SimpleTune.png"), JLabel.LEFT);
    			//logger.info("USING switch image.");
    		}else{
    			//logger.info("Can't decide which node image to use in tree.");
    		}
    		
    		Color notSelectedBackground = new Color(127, 237, 127);
    		Color notSelectedBorder = new Color(59, 164, 59);
    		

    		Color selectedBackground = new Color(117, 136, 253);
    		Color selectedBorder = new Color(59, 74, 164);
    		
    		if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.MAP_SET_ROOT){
    			nodeName = new JLabel(eTreeNode.getNodeName(), new ImageIcon("./graphics/treeNodeImages/rom.gif"), JLabel.LEFT);
    			nodeName.setFont(new Font("LucidaBrightRegular", Font.PLAIN, 11));
                namedJPanel.add(nodeName);

                if (selected) {
                    namedJPanel.setBackground(selectedBackground);
                    namedJPanel.setBorder(createLineBorder(selectedBorder));

                } else {
                    namedJPanel.setBorder(createLineBorder(notSelectedBorder));
                    namedJPanel.setBackground(notSelectedBackground);
                }

                namedJPanel.setPreferredSize(new Dimension(tree.getParent().getWidth()+2000, 35));
                namedJPanel.setMaximumSize(new Dimension(tree.getParent().getWidth()+2000, 35));
                namedJPanel.setEnabled(tree.isEnabled());
                returnValue = namedJPanel;
    			
    		}
    		else if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.RESERVED_ROOT){
    			nodeName.setFont(new Font("LucidaBrightRegular", Font.BOLD, 14));
                namedJPanel.add(nodeName);
                returnValue = namedJPanel;
    		}
    		else if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.CATEGORY){
    			nodeName.setFont(new Font("LucidaBrightRegular", Font.PLAIN, 11));
                namedJPanel.add(nodeName);
                returnValue = namedJPanel;
    		}
    		else if(eTreeNode.getTableMetaData().getNodeType() == TableMetaData.DATA_3D || eTreeNode.getTableMetaData().getNodeType() == TableMetaData.DATA_2D || eTreeNode.getTableMetaData().getNodeType() == TableMetaData.DATA_1D || eTreeNode.getTableMetaData().getNodeType() == TableMetaData.SWITCH){
    		
    			
                namedJPanel.add(nodeName);
                nodeName.setFont(new Font("LucidaBrightRegular", Font.PLAIN, 11));

                
                if (selected) {
                    namedJPanel.setBackground(selectedBackground);
                    namedJPanel.setBorder(createLineBorder(selectedBorder));

                }
                namedJPanel.setPreferredSize(new Dimension(tree.getParent().getWidth()+2000, 20));
                namedJPanel.setMaximumSize(new Dimension(tree.getParent().getWidth()+2000, 20));
                /*
                else {
                    namedJPanel.setBorder(createLineBorder(notSelectedBorder));
                    namedJPanel.setBackground(notSelectedBackground);
                }
				*/
                if (eTreeNode.getUserLevel() == 5) {
                    nodeName.setForeground(new Color(255, 150, 150));
                } 
                
                else if (eTreeNode.getUserLevel() > ApplicationStateManager.getCurrentUserLevel()) {
                    //tableName.setForeground(new Color(185, 185, 185));
                	//nodeName.setFont(new Font("Tahoma", Font.ITALIC, 11));

                }

                returnValue = namedJPanel;
    		}else{
    			namedJPanel.setBackground(Color.LIGHT_GRAY);
    		}
    	}
    	
    	else{
    		returnValue = defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            returnValue.setBackground(Color.LIGHT_GRAY);
    	}
    	
    	
        if (returnValue == null) {
            returnValue = defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            returnValue.setBackground(Color.LIGHT_GRAY);
        }
    	
		return returnValue;
	}
}
