package simpleTune.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.dataEntity.DataEntityImpl;
import simpleTune.gui.compare.CompareJFrame;
import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.desktop.EDesktopPane;
import simpleTune.gui.etable.EInternalFrame;
import simpleTune.gui.interfaces.TuningEntity;
import simpleTune.gui.interfaces.TuningEntityListener;
import simpleTune.gui.tree.ETree;
import simpleTune.gui.tree.ETreeNode;
import simpleTune.romEntity.RomTuningEntityImpl;
import st.comm.gui.CommJFrame;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.Vector;

import logger.gui.control.LoggerGUIManager;
import logger.gui.interfaces.DataConsumerGUI;

public class SimpleTuneGUI extends JFrame implements WindowListener, ActionListener,
		TreeSelectionListener, TuningEntityListener {
	private Log logger = LogFactory.getLog(getClass());
	//private final String ST_VERSION = "SimpleTune v1.0 8/1/09 {Eric@SimpleTune.org} FREE Not for Sale";

	private final String ST_VERSION = "SimpleTune v1.0a 5/11/23 {Eric@ECMorgan.net} FREE Not for Sale";
	
	private JPanel mainJPanel = new JPanel();

	private JMenuBar jMenuBar = new JMenuBar();
	private JMenu tuningEntitiesJMenu = new JMenu("Tuning Entities Deployed");
	private JMenu toolsJMenu = new JMenu("Tools");
	
	private JSplitPane splitPane = new JSplitPane();
	private EDesktopPane rightDesktopPane = new EDesktopPane();

	private ETreeNode rootNode = new ETreeNode("SimpleTune", new TableMetaData(null,
			TableMetaData.RESERVED_ROOT, 0.0, 0.0, new Object[0], null, null,
			false, "", "", "", "", "",null,null,null, "", null, "", "", "", "", ""));
	public ETreeNode getRootNode() {
		return rootNode;
	}
	
	public EDesktopPane getRightDesktop(){
		return this.rightDesktopPane;
	}
	
	private ETree leftJTree = new ETree(rootNode);

	private boolean newTree = true;

	private JScrollPane scrollpane;
	
	private int closeCount = 0;
	
	private static int DIVIDER_WIDTH = 325;
	private static int FRAME_WIDTH = 1024;
	private static int FRAME_HEIGHT = 768;
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	private CommJFrame commJFrame = new CommJFrame();
	
	private SimpleTuneGUI() {
		// Ensure that the end user agrees with the terms
		initSplashScreen();
		
		// Define which tuning entities are available.
		initData();

		// Initialize the GUI elements.
		initGui();
		
		// Try to cover our bases on mac os x
		try {
		    Class.forName("com.apple.eawt.Application").newInstance();

		    // When instantiated, handles mac osx command-q fast quits
		    //MacOSXCommandQuitApplication quit = new MacOSXCommandQuitApplication(this);
		} catch ( ClassNotFoundException e) {
			logger.info("Not on Mac OSX");
		} catch (InstantiationException e) {
			logger.info("Not on Mac OSX");
		} catch (IllegalAccessException e) {
			logger.info("Not on Mac OSX");
		}
		
		
	}
	
	private void initSplashScreen(){
		//Custom button text
		Object[] options = {"I Agree",
		                    "I Do NOT Agree"};
		
		int n = JOptionPane.showOptionDialog(this,
		    "Before using SimpleTune you must agree to all points in the License.txt",
		    "License agreement and terms of use.",
		    JOptionPane.YES_NO_OPTION,
		    JOptionPane.INFORMATION_MESSAGE,
		    null,
		    options,
		    options[1]);
		
		// User selected close
		if(n != 0){
			System.exit(0);
		}
	}

	public static SimpleTuneGUI getInstance() {
		if (ApplicationStateManager.getSTInstance() == null) {
			ApplicationStateManager.setSTInstance(new SimpleTuneGUI());
		}

		return ApplicationStateManager.getSTInstance();
	}

	private void initData() {
		// Add supported tuning entities
		// As new tuning entities are developed, add them here
		
		DataEntityImpl dtei = new DataEntityImpl();
		dtei.init(this);
		ApplicationStateManager.addTuningEntity(dtei);
		
		//UtecTuningEntityImpl utei = new UtecTuningEntityImpl();
		//utei.init(this);
		//ApplicationStateManager.addTuningEntity(utei);
		
		
		RomTuningEntityImpl rtei = new RomTuningEntityImpl();
		rtei.init(this);
		ApplicationStateManager.addTuningEntity(rtei);
		
	}

	private void initGui() {
		logger.info("Initializing GUI.");
		
		// Set look and feel for basic items
		try {
			if(System.getProperty("os.name").toLowerCase().contains("window")){
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}
			else if(System.getProperty("os.name").toLowerCase().contains("linux")){
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}
			else if(System.getProperty("os.name").toLowerCase().contains("mac")){
				
			}
			else{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set the frame icon
		Image img = Toolkit.getDefaultToolkit().getImage("graphics/SimpleTune.png");
		setIconImage(img);

		// Set frame title
		this.setTitle(this.ST_VERSION);
		
		// Set main JFrame size
		// Ensure that when program starts that ST opens to maximize entire screen.
		// Also ensure that when minimized, screen size defaults to a reasonable resolution. 
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		// This class implements its own windows closing methods. Duh!!! ;-)
		this.addWindowListener(this);

		// Setup JMenu
		Iterator tuningEntities = ApplicationStateManager.getTuningEntities().iterator();
		while (tuningEntities.hasNext()) {
			TuningEntity theTuningEntity = (TuningEntity) tuningEntities.next();
			JMenuItem tempItem = new JMenuItem(theTuningEntity.getName());
			tempItem.addActionListener(this);
			tuningEntitiesJMenu.add(tempItem);
		}

		this.jMenuBar.add(this.tuningEntitiesJMenu);
		this.setLayout(new BorderLayout());
		this.setJMenuBar(this.jMenuBar);

		
		JMenuItem compareItem = new JMenuItem("Compare/Copy TGs");
		compareItem.addActionListener(this);
		this.toolsJMenu.add(compareItem);
		
		JMenuItem commItem = new JMenuItem("Comm Management");
		commItem.addActionListener(this);
		this.toolsJMenu.add(commItem);
		
		this.jMenuBar.add(this.toolsJMenu);
		
		// Setup desktop pane
		rightDesktopPane.setBackground(Color.LIGHT_GRAY);

		leftJTree.setBackground(Color.LIGHT_GRAY);
		this.scrollpane = new JScrollPane(leftJTree);
		
		// Setup split pane
		splitPane.setDividerLocation(DIVIDER_WIDTH);
		splitPane.setLeftComponent(this.scrollpane);
		splitPane.setRightComponent(rightDesktopPane);
		splitPane.setDividerSize(5);
		
		// Setup main JPanel
		mainJPanel.setLayout(new BorderLayout());
		mainJPanel.add(splitPane, BorderLayout.CENTER);
		mainJPanel.add(new SimpleTuneToolBar(), BorderLayout.NORTH);
		
		// Add everything to TabbedPane
		this.tabbedPane.add(mainJPanel, "Map Editor");
		//this.tabbedPane.add(new CompareJPanel(), "Compare/Copy");
		//this.tabbedPane.add(new LoggingEntityGUI());
		
		/**
		 * Logger Specific
		 * Init GUI Controller
		 */
		Iterator<DataConsumerGUI> iterator = LoggerGUIManager.getInstance().getDataConsumerGUIs().iterator();
		while(iterator.hasNext()){
			DataConsumerGUI next = iterator.next();
			this.tabbedPane.add((JPanel)next, next.getLoggerGUIName());
		}
		
		this.add(this.tabbedPane);
		
		// Define close action
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
	}

	public void rebuildJMenuBar(Vector<JMenu> items) {
		Iterator iterator = items.iterator();

		this.jMenuBar.removeAll();

		while (iterator.hasNext()) {
			JMenu tempMenu = (JMenu) iterator.next();
			jMenuBar.add(tempMenu);
		}

		jMenuBar.add(this.tuningEntitiesJMenu);

		this.jMenuBar.revalidate();
	}

	public void valueChanged(TreeSelectionEvent arg0) {

		logger.debug("Tree Node selected.");

	}

	public void addTuningGroupNameToTitle(String titleAppend) {
		this.setTitle(this.ST_VERSION + ": " + titleAppend);
	}

	/**
	 * Tuning group is a collection of maps and parameters, ala a ROM or a UTEC
	 * Map file
	 * 
	 */
	public void addNewTuningGroup(ETreeNode newTreeModel) {
		logger.debug("test: " + this.newTree);

		int childCount = this.rootNode.getChildCount();
		String newTuningGroup = newTreeModel.getTableMetaData().getTableGroup();

		logger.debug("Children:" + childCount + "  :" + newTuningGroup);
		for (int i = 0; i < childCount; i++) {
			ETreeNode tempNode = (ETreeNode) this.rootNode.getChildAt(i);
			if (tempNode.getTableMetaData().getTableGroup().equals(newTuningGroup)) {
				logger.error("Can't open same ROM / Map file 2x");
				return;
			}
		}

		if (this.newTree == true) {
			this.newTree = false;
			this.rootNode.removeAllChildren();
		}

		this.rootNode.add(newTreeModel);
		this.leftJTree.updateUI();
		this.splitPane.repaint();
		
		// Up count of open maps
		ApplicationStateManager.incOpenTuningGroupCount(newTuningGroup);
	}

	public ETreeNode getNode(String tuningGroup){
		int childCount = this.rootNode.getChildCount();
		
		for (int i = 0; i < childCount; i++) {
			ETreeNode tempNode = (ETreeNode) this.rootNode.getChildAt(i);
			if (tempNode.getTableMetaData().getTableGroup().equals(tuningGroup)) {
				//ApplicationStateManager.setSelectedTuningGroup("No Tuning Group Selected.");
				return tempNode;
			}
		}
		return null;
	}
	/**
	 * Removes a tuning group from the GUI
	 * 
	 */
	public void removeTuningGroup(String tableGroup) {
		int childCount = this.rootNode.getChildCount();
		logger.info("Root Node Children Count :"+childCount);
		
		int removedCount = 0;
		
		for (int i = 0; i < childCount; i++) {
			ETreeNode tempNode = (ETreeNode) this.rootNode.getChildAt(i);
			if (tempNode.getTableMetaData().getTableGroup().equals(tableGroup)) {
				//ApplicationStateManager.setSelectedTuningGroup("No Tuning Group Selected.");
				removedCount++;
				this.rootNode.remove(i);
			}
		}
		
		logger.info("Removed this count of maps from tree :"+removedCount);
		
		this.addTuningGroupNameToTitle("");
		this.leftJTree.updateUI();
		this.splitPane.repaint();

		// Clean up
		this.rightDesktopPane.removeInternalFrames(tableGroup);

		// Clean up on tuning entity sides
		Iterator tuningEntites = ApplicationStateManager.getTuningEntities().iterator();

		while (tuningEntites.hasNext()) {
			TuningEntity theTuningEntity = (TuningEntity) tuningEntites.next();
			theTuningEntity.removeTuningGroup(tableGroup);
		}

		// Decrement open map count
		ApplicationStateManager.decOpenTuningGroupCount(tableGroup);
		
	}

	public void displayInternalFrameTable(Object[][] data, TableMetaData tableMetaData, boolean togglePresentFramesVisibility) {
		this.rightDesktopPane.add(data, tableMetaData, togglePresentFramesVisibility);
	}

	public void displayInternalFrameTable(String tableGroup, String tableIdentifier, boolean togglePresentFramesVisibility) {
		TableMetaData tableMetaData = ApplicationStateManager.getTGTableMetaData(tableIdentifier, tableGroup);
		
		TuningEntity tuningEntity = ApplicationStateManager.getTGTuningEntity(tableGroup);
		Object[][] tableData = tuningEntity.getTableData(tableGroup, tableIdentifier);
		
		displayInternalFrameTable(tableData, tableMetaData, togglePresentFramesVisibility);
	}
	
	public void removeInternalFrame(EInternalFrame frame) {
		this.rightDesktopPane.remove(frame);
	}

	public void setNewToolBar(JToolBar theToolBar) {
		//this.add(theToolBar, BorderLayout.NORTH);
		logger.info("Attempting to set tuning entity defined toolbar, but feature not available.");
	}

	/*
	 * Helper method that returns the number of maps that have had their data changed.
	 */
	public int getMapChangeCount(TuningEntity tuningEntity, String tableGroup) {
		JInternalFrame[] allFrames = this.rightDesktopPane.getAllFrames();
		int number = 0;
		for (int i = 0; i < allFrames.length; i++) {
			EInternalFrame eInternalFrame = (EInternalFrame) allFrames[i];

			if (eInternalFrame.getTableMetaData().getTableGroup().equals(
					tableGroup)) {
				if (eInternalFrame.dataChanged()) {
					number++;
				}
			}
		}

		return number;

	}

	/*
	 * Method walks through all opened JInternalFrames in right pane. If an InternFrame claims its 
	 * data has been changed, the tuning entity parent is notified.
	 */
	public int saveMaps() {
		JInternalFrame[] allFrames = this.rightDesktopPane.getAllFrames();
		String selectedTableGroup = ApplicationStateManager.getSelectedTuningGroup();
		
		int counter = 0;
		for (int i = 0; i < allFrames.length; i++) {
			EInternalFrame eInternalFrame = (EInternalFrame) allFrames[i];

			logger.debug("Current table :"+eInternalFrame.getTableMetaData().getTableName());
			logger.debug("Frame Table Group value :"+eInternalFrame.getTableMetaData().getTableGroup());
			logger.debug("Application state selected Table Group value :"+selectedTableGroup);
			
			if (eInternalFrame.getTableMetaData().getTableGroup().equals(selectedTableGroup)) {
				logger.debug("Has data changed? :"+eInternalFrame.dataChanged());
				if (eInternalFrame.dataChanged()) {
					logger.debug("Saving to parent tuning entity now.");
					counter++;
					eInternalFrame.saveDataToParentTuningEntity();
				}
			}
		}
		return counter;
	}
	
	/**
	 * Number of internal frames of current seleted tuning group that have changed data in them.
	 * @return
	 */
	public int getChangedMapCount(){
		JInternalFrame[] allFrames = this.rightDesktopPane.getAllFrames();
		String selectedTableGroup = ApplicationStateManager.getSelectedTuningGroup();
		
		int counter = 0;
		for (int i = 0; i < allFrames.length; i++) {
			EInternalFrame eInternalFrame = (EInternalFrame) allFrames[i];

			if (eInternalFrame.getTableMetaData().getTableGroup().equals(selectedTableGroup)) {
				if (eInternalFrame.dataChanged()) {
					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * Getter that returns the title of SimpleTune
	 * @return
	 */
	public String getSimpleTuneTitle() {
		return ST_VERSION;
	}

	/*
	 * Tuning entity in scope will call this when it is ready to exit. Some entities might need to cleanup connections or save files.
	 */
	public void readyForExit() {
		logger.info("SimpleTune is now exiting as per tuning entity notification.");
		this.closeCount++;
		
		if(this.closeCount == ApplicationStateManager.getTuningEntityCount()){
			logger.info("All tuning entites responded, now gui can exit.");
			
			System.exit(0);
		}
		
	}

	
	
	// **************************************************************
	// Methods pertaining to the WindowListener this class implements
	// **************************************************************

	/**
	 * Captures menu actions.
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equalsIgnoreCase("compare/copy tgs")){
			// Do nothing if there are no opened tuning groups
			if(ApplicationStateManager.getOpenTuningGroups().size() < 2){
				return;
			}
			
			CompareJFrame compare = new CompareJFrame();
			compare.setVisible(true);
		}
		
		if(e.getActionCommand().equalsIgnoreCase("Comm Management")){
			commJFrame.setVisible(true);
		}
		
		/*
		if (e.getActionCommand().equalsIgnoreCase("UTEC Tuning Entity")) {
			String theCommand = e.getActionCommand();

			ApplicationStateManager.setCurrentTuningEntity(theCommand, this);
		}
		*/
		//String theCommand = e.getActionCommand();

		//ApplicationStateManager.setCurrentTuningEntity(theCommand, this);
		//logger.info("Node selected.");
	}

	public void windowActivated(WindowEvent e) {
		logger.info("Window Activated.");
	}

	public void windowClosed(WindowEvent e) {
		logger.info("Window Closed.");
	}

	public void windowClosing(WindowEvent e) {
		logger.info("Preparing SimpleTune for exit.");

		stQuitApplicationDialog();
		
	}

	public void stQuitApplicationDialog() {
		// If there are still open maps, ask user if they really want to continue
		if(ApplicationStateManager.getOpenTuningGroupCount() > 0){
			//Custom button text
			Object[] options = {"Exit SimpleTune",
			                    "Cancel"};
			
			int n = JOptionPane.showOptionDialog(this,
			    "Tuning Groups are open and may be unsaved. Exit SimpleTune?",
			    "Exit SimpleTune",
			    JOptionPane.OK_CANCEL_OPTION,
			    JOptionPane.INFORMATION_MESSAGE,
			    null,
			    options,
			    options[1]);
			
			// User selected close
			if(n == 0){
				logger.info("User decided to exit.");
				// Ensure that all tuning entities know that their closing.
				ApplicationStateManager.notifyTuningEntitiesOfClose();
			}
		}else{
			// Ensure that all tuning entities know that their closing.
			ApplicationStateManager.notifyTuningEntitiesOfClose();
		}
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		logger.info("Window Deactivated.");
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		logger.info("Window Deiconified.");
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		logger.info("Window Iconified.");
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		logger.info("Window Opened.");
	}

	public void refreshEInternalFrames(){
		this.rightDesktopPane.refreshAllEInternalFrames();
	}
}
