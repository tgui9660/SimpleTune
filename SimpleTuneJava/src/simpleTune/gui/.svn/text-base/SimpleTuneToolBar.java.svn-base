package simpleTune.gui;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.interfaces.TuningEntity;

public class SimpleTuneToolBar extends JToolBar implements ActionListener{
	private Log logger = LogFactory.getLog(getClass());
	private int fileChosen;
	private JFileChooser fileChooser =  new JFileChooser();
	
    private JButton openImage = new JButton(new ImageIcon("./graphics/open.png"));
    private JButton saveImage = new JButton(new ImageIcon("./graphics/save.png"));
    private JButton closeImage = new JButton(new ImageIcon("./graphics/close.png"));
	
    public SimpleTuneToolBar(){
    	
        this.setFloatable(false);
        this.add(openImage);
        this.add(saveImage);
        this.add(closeImage);
        
        // Set action listener
        openImage.addActionListener(this);
        saveImage.addActionListener(this);
        closeImage.addActionListener(this);
        
        // Set tool tips
        openImage.setToolTipText("Open Tuning Group");
        saveImage.setToolTipText("Save Tuning Group");
        closeImage.setToolTipText("Close Tuning Group");
        
        // What color should we force the background to?
        //this.setBackground(Color.WHITE);
        
        // Set initial button state
        this.openImage.setEnabled(true);
        this.saveImage.setEnabled(true);
        this.closeImage.setEnabled(true);
        
        // Set up file chooser
        this.fileChooser.addChoosableFileFilter(new SimpleTuneFileFilter());
    }
    
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openImage) {
			logger.info("Attempting to open file.");

			String path = null;
			fileChosen = fileChooser.showOpenDialog(null);
			if (fileChosen == JFileChooser.APPROVE_OPTION) {
				// Get path to selected ROM
				path = fileChooser.getSelectedFile().getPath();
				
				String[] split = path.split("\\.");
				String fileSuffix = split[split.length - 1];
				
				Vector<TuningEntity> tuningEntities = ApplicationStateManager.getTuningEntities();
				Iterator<TuningEntity> entityIterator = tuningEntities.iterator();
				while(entityIterator.hasNext()){
					TuningEntity entity = entityIterator.next();
					String[] fileSuffixes = entity.getFileSuffixes();
					if(fileSuffixes != null){
						for(int i = 0; i < fileSuffixes.length; i++){
							String aSuffix = fileSuffixes[i];
							if(aSuffix.equalsIgnoreCase(fileSuffix)){
								entity.openFile(path);
							}
						}
					}
				}
			}
		}
		if (e.getSource() == saveImage) {
			saveTuningGoup();
		}
		if (e.getSource() == closeImage) {
			// Cant close something that doesn't exist
			if(ApplicationStateManager.getOpenTuningGroupCount() == 0){
				return;
			}
			
			logger.info("Open tuning group count :"+ApplicationStateManager.getOpenTuningGroupCount());
			logger.info("Attempting to close ROM image :"+ApplicationStateManager.getSelectedTuningGroup());
			
			int changedMapCount = SimpleTuneGUI.getInstance().getChangedMapCount();
			logger.info("Changed map count :"+changedMapCount);
			
			String selectedTuningGroup = ApplicationStateManager.getSelectedTuningGroup();
			logger.info("SelectedTuningGroup :"+selectedTuningGroup);
			
			// If there is no tuning group in scope, then do nothing!
			if(selectedTuningGroup.length() > 0){
				int userOption = -1;
				if(changedMapCount == 0){
					userOption = closeCanelDialog();
					if(userOption == 1){
						// Do nothing
					}else if(userOption == 0){
						// Close and don't save
						SimpleTuneGUI.getInstance().removeTuningGroup(ApplicationStateManager.getSelectedTuningGroup());
					}
				}else{
					userOption = saveCloseCanelDialog();
					if(userOption == 2){
						// Do nothing
					}else if(userOption == 1){
						// Close and don't save
						SimpleTuneGUI.getInstance().removeTuningGroup(ApplicationStateManager.getSelectedTuningGroup());
					}else if(userOption == 0){
						// Close and save tuning group
						saveTuningGoup();
						SimpleTuneGUI.getInstance().removeTuningGroup(ApplicationStateManager.getSelectedTuningGroup());
					}
				}
				
				logger.info("User Value  :"+userOption);
			}
		}	
	}

	private int saveCloseCanelDialog() {
		//Custom button text
		Object[] options = {"Close and Save",
		                    "Close and DONT Save",
		                    "Cancel"};
		int n = JOptionPane.showOptionDialog(this,
		    "Close selected Tuning Group with unsaved changes?",
		    "Close Tuning Group",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.INFORMATION_MESSAGE,
		    null,
		    options,
		    options[2]);
		return n;
	}
	
	private int closeCanelDialog() {
		//Custom button text
		Object[] options = {"Close",
		                    "Cancel"};
		int n = JOptionPane.showOptionDialog(this,
		    "Close selected Tuning Group?",
		    "Close Tuning Group",
		    JOptionPane.OK_CANCEL_OPTION,
		    JOptionPane.INFORMATION_MESSAGE,
		    null,
		    options,
		    options[1]);
		return n;
	}
	
	private void saveTuningGoup() {
		// Nothing to save if nothing is open, or no tuning group in scope
		if(ApplicationStateManager.getOpenTuningGroupCount() == 0 || ApplicationStateManager.getSelectedTuningGroup().length() == 0){
			return;
		}
		
		String openFileName = null;
		fileChosen = fileChooser.showSaveDialog(null);
		if (fileChosen == JFileChooser.APPROVE_OPTION) {
			// Get path to selected ROM
			openFileName = fileChooser.getSelectedFile().getPath();
			
			// Push changed data in tables to the appropriate tuning entity
			int numberTablesSaved = ApplicationStateManager.getSTInstance().saveMaps();
			logger.info("Number of changed maps pushed to entities :"+numberTablesSaved);
			
			/*
			if(numberTablesSaved > 0){
				// Actually write files to disk
				String[] split = openFileName.split("\\.");
				TuningEntity tuningEntity = ApplicationStateManager.getTuningEntity(split[split.length - 1]);
				tuningEntity.saveFile(ApplicationStateManager.getSelectedTuningGroup(), openFileName);
			}*/
			
			
			// Actually write files to disk
			String[] split = openFileName.split("\\.");
			TuningEntity tuningEntity = ApplicationStateManager.getTuningEntity(split[split.length - 1]);
			tuningEntity.saveFile(ApplicationStateManager.getSelectedTuningGroup(), openFileName);
		}
	}

}
