package simpleTune.gui.interfaces;

import javax.swing.JMenu;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.util.Vector;


public interface TuningEntity extends ActionListener{
	
	// Return name of tuning entity
	public String getName();
	
	// Return all the menu items relevant to tuning entity
	public Vector<JMenu> getMenuItems();
	
	// Return the toolbar
	public JToolBar getToolBar();
	
	// Return object data based on passed table identifier
	public Object[][] getTableData(String tableGroup, String tableIdentifier);
	
	// Remove tuning group
	public void removeTuningGroup(String tuningGroup);
	
	// Push back modified data to the tuning entity
	// Data table type is used to define axis information
	public void saveTableData(String tableGroup, String tableIdentifier, Object[][] data, int dataTableType);
	
	// Sets the tuning group in scope. Good for saving files.
	public void setCurrentTuningGroup(String tuningGroup);
	
	// Returns a list of valid file suffixes a tuning entity is known to use.
	// For example, the ROM tuning entity uses .hex and .rom.
	// Note: Do NOT include the "."
	// Note: Case insensitive.
	public String[] getFileSuffixes();
	
	// Called by gui when a user requests a file is open, with a suffix that matches one provided.
	public void openFile(String path);
	
	// Called by gui when a user requests that an open file be saved
	public void saveFile(String tableGroup, String path);
	
	// Control methods
	public void init(TuningEntityListener listener);
	
	// Notify of system exit. Tuning entity must reply to parent GUI (Tuning Entity Listener) that it is in fact
	// ready for shutdown once all its needed steps are taken.
	public void notifySystemExit();
}
