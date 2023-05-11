package simpleTune.romEntity;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.interfaces.TuningEntity;
import simpleTune.gui.interfaces.TuningEntityListener;
import simpleTune.romEntity.romParse.RomImageManager;
import simpleTune.romEntity.xmlParse.RomXMLDataManager;
import simpleTune.romEntity.xmlParse.RomXMLMetaDataDoc;

public class RomToolBar extends JToolBar implements ActionListener{
	private Log logger = LogFactory.getLog(getClass());
	private TuningEntityListener theTEL;
	private int fileChosen;
	private JFileChooser fileChooser =  new JFileChooser();
	private TuningEntity parentTuningEntity;
	
    private JButton openImage = new JButton(new ImageIcon("./graphics/open.png"));
    private JButton saveImage = new JButton(new ImageIcon("./graphics/save.png"));
    private JButton closeImage = new JButton(new ImageIcon("./graphics/close.png"));
	
    public RomToolBar(TuningEntityListener theTEL, TuningEntity parentTuningEntity){
    	this.theTEL = theTEL;
    	this.parentTuningEntity = parentTuningEntity;
    	
        this.setFloatable(false);
        this.add(openImage);
        this.add(saveImage);
        this.add(closeImage);
        
        // Set action listener
        openImage.addActionListener(this);
        saveImage.addActionListener(this);
        closeImage.addActionListener(this);
        
        // Set tool tips
        openImage.setToolTipText("Open ROM");
        saveImage.setToolTipText("Save ROM");
        closeImage.setToolTipText("Close ROM");
        
        this.setBackground(Color.WHITE);
        
        // Set initial button state
        this.openImage.setEnabled(true);
        this.saveImage.setEnabled(true);
        this.closeImage.setEnabled(true);
        
        // Set up file choser
        this.fileChooser.addChoosableFileFilter(new RomFileFilter());
    }
    
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openImage) {
			logger.info("Attempting to open ROM image.");

			String openFileName = null;
			fileChosen = fileChooser.showOpenDialog(null);
			if (fileChosen == JFileChooser.APPROVE_OPTION) {
				// Get path to selected ROM
				openFileName = fileChooser.getSelectedFile().getPath();
				
				// Get the ROM Image manager to open up the ROM Image
				int uniqueNumber = RomImageManager.getInstance().openROMImage(openFileName);
				
				// Pull the CALID of the ROM
				String romCalID = RomImageManager.getInstance().getROMImage(uniqueNumber, openFileName).getRomCalID();
				
				// Pull XML table data based on CALID gathered.
				RomXMLMetaDataDoc getROMTables = RomXMLDataManager.getInstance().GetROMTables(romCalID);
				
				// Used for opening a ROM multiple times. A unique number + path is the key associated with this instance
				getROMTables.setUniqueIdentifier(uniqueNumber);
				getROMTables.setFileSystemPath(openFileName);
				
				// Build up tree nodes for left pane
				RomDataManager.getInstance().BuildMapDataTreeNode(getROMTables, this.parentTuningEntity);
			}
		}
		if (e.getSource() == saveImage) {
			logger.info("Attempting to save ROM image.");
			
			String openFileName = null;
			fileChosen = fileChooser.showSaveDialog(null);
			if (fileChosen == JFileChooser.APPROVE_OPTION) {
				// Get path to selected ROM
				openFileName = fileChooser.getSelectedFile().getPath();
				
				// Inform ROM manager of save
				RomImageManager.getInstance().saveRomImage(RomDataManager.getInstance().getTuningGroupInScope(), openFileName);
			}
		}
		if (e.getSource() == closeImage) {
			logger.info("Attempting to close ROM image.");
		}	
		
	}

}
