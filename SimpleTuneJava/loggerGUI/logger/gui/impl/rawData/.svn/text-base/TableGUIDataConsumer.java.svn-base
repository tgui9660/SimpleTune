package logger.gui.impl.rawData;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.dataManager.LoggerManager;
import logger.gui.control.DataConsumerGUIController;
import logger.gui.interfaces.DataConsumerGUI;
import logger.gui.interfaces.StatusListener;
import logger.interfaces.LoggingAttribute;

/**
 * Class implements GUI to display RAW data.
 * 
 * @author Eric Morgan
 */
public class TableGUIDataConsumer extends JPanel implements StatusListener, DataConsumerGUI{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private JPanel panel3;
	private JPanel panel4;
	private JLabel statusJLabel;
	private JPanel panel5;
	private JLabel calIDJLabel;
	private JPanel panel6;
	private JLabel rateJLabel;
	private JPanel panel1;
	private JPanel panel7;
	private JToggleButton connectJButton;
	private JToggleButton logJButton;
	private JButton resetJButton;
	private JMenuBar loggerJMenuBar;
	private JMenu commJMenu;
	private JMenuItem testJMenuItem;
	private JSplitPane splitPane1;
	private JTabbedPane tabbedPane1;
	private JScrollPane scrollPane2;
	private JTable table3;
	private JScrollPane scrollPane3;
	private JTable table4;
	private JScrollPane scrollPane1;
	private JTable table2;
	private JScrollPane scrollPane4;
	private JTable table1;
	
	private String NAME = "Raw Data Logger";
	private LoggerManager loggerManager = null;
	private DataConsumerGUIController controller = null;
	
	private AttributeJTable attributeJTable = null;
	private RawDataJTable dataJTable = null;
	private SwitchJTable switchJTable = null;
	
	public TableGUIDataConsumer(DataConsumerGUIController controller, AttributeJTable attributeJTable, RawDataJTable rawDataJTable, SwitchJTable switchJTable) {
		this.attributeJTable = attributeJTable;
		this.dataJTable = rawDataJTable;
		this.controller = controller;
		this.switchJTable = switchJTable;
		
		initComponents();
		init();
	}

	public String getLoggerGUIName(){
		return this.NAME;
	}
	
	
	private void init(){
		this.loggerManager = LoggerManager.getInstance();
		//this.loggerManager.registerDataSupplierListenerAll(this.attributeJTable);
		//this.loggerManager.registerDataSupplierListener(this.attributeJTable);
	}
	
	public void StatusMessageReceived(int type, String message) {
		//System.out.println("GUI message: "+message);
		
		if(type == StatusListener.MESSAGE_TYPE_STATUS){
			this.statusJLabel.setText("Status: "+message);
		}
		
		if(type == StatusListener.MESSAGE_TPYE_QUERY_RATE){
			this.rateJLabel.setText("Rate: "+message);
		}
		
	}
	
	// **************************
	// Data listener methods here
	// **************************
	/**
	 * Call back to add attributes
	 */
	public void attributeAutomaticallyAdded(LoggingAttribute loggingAttribute) {
		System.out.println(" ++ Attribute added: "+loggingAttribute.getName());
	}

	/**
	 * Need a way to make labels and whatnot
	 */
	public String getDataSupplierListenerName() {
		System.out.println(" ++ Name requested: "+this.NAME);
		return this.NAME;
	}

	/**
	 * Called when new data is available for a particular logging attribute.
	 * I might need to change this as logging attributes could have their own
	 * listeners.
	 */
	public void newDataAvailable(Double newData, LoggingAttribute loggingAttribute) {
		System.out.println(" ++ NEW DATA: "+newData);
	}
	
	/**
	 * Control logging, start, stop, init, connect.. whatever I decide to call it that is.
	 */
	public void setLoggingManager(LoggerManager loggerManager){
		System.out.println(" ++ Got Logging Manager");
		this.loggerManager = loggerManager;
		
		this.controller.setLoggerManager(this.loggerManager);
	}
	
	// *********************************
	// GUI INTERFACE IMPLEMENTED METHODS
	// *********************************
	public void addCommPort(String commPortName) {
		System.out.println("Adding comm port to GUI: "+commPortName);
		this.getCommJMenu().add(commPortName);
	}


	public boolean getIsConnected() {
		return this.getConnectJButton().isSelected();
	}


	public boolean getIsLogging() {
		return this.getLogJButton().isSelected();
	}


	public void removeCommPort(String commPortName) {
		JMenuItem item = new JMenuItem(commPortName);
		this.getCommJMenu().remove(item);
	}
	

	public void addActionListener(ActionListener listener) {
		logger.info("Adding action listener to GUI :"+listener.getClass().getName());
		
		this.getResetJButton().addActionListener(listener);
		this.getConnectJButton().addActionListener(listener);
		this.getLogJButton().addActionListener(listener);
		
		this.getResetJButton().firePropertyChange("test", true, false);
	}
	
	// ******************
	// GENERATED GUI CODE
	// ******************
	private void initComponents() {
		panel3 = new JPanel();
		panel4 = new JPanel();
		statusJLabel = new JLabel();
		panel5 = new JPanel();
		calIDJLabel = new JLabel();
		panel6 = new JPanel();
		rateJLabel = new JLabel();
		panel1 = new JPanel();
		panel7 = new JPanel();
		connectJButton = new JToggleButton();
		connectJButton.setActionCommand("CONNECT:"+this.getLoggerGUIName());
		logJButton = new JToggleButton();
		logJButton.setActionCommand("LOG:"+this.getLoggerGUIName());
		resetJButton = new JButton();
		resetJButton.setActionCommand("RESET:"+this.getLoggerGUIName());
		loggerJMenuBar = new JMenuBar();
		commJMenu = new JMenu();
		testJMenuItem = new JMenuItem();
		splitPane1 = new JSplitPane();
		tabbedPane1 = new JTabbedPane();
		scrollPane2 = new JScrollPane();
		table3 = new JTable();
		scrollPane3 = new JScrollPane();
		table4 = new JTable();
		scrollPane1 = new JScrollPane();
		table2 = new JTable();
		scrollPane4 = new JScrollPane();
		table1 = new JTable();

		//======== this ========

		setLayout(new BorderLayout());

		//======== panel3 ========
		{
			panel3.setBorder(null);
			panel3.setLayout(new GridBagLayout());
			((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {194, 82, 80, 0};
			((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
			((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//======== panel4 ========
			{
				panel4.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				panel4.setLayout(new BorderLayout());

				//---- statusJLabel ----
				statusJLabel.setText("Status: ");
				panel4.add(statusJLabel, BorderLayout.CENTER);
			}
			panel3.add(panel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//======== panel5 ========
			{
				panel5.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				panel5.setLayout(new BorderLayout());

				//---- calIDJLabel ----
				calIDJLabel.setText("CAL ID:");
				panel5.add(calIDJLabel, BorderLayout.CENTER);
			}
			panel3.add(panel5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//======== panel6 ========
			{
				panel6.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				panel6.setLayout(new BorderLayout());

				//---- rateJLabel ----
				rateJLabel.setText("Rate:");
				panel6.add(rateJLabel, BorderLayout.CENTER);
			}
			panel3.add(panel6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		add(panel3, BorderLayout.SOUTH);

		//======== panel1 ========
		{
			panel1.setLayout(new BorderLayout());

			//======== panel7 ========
			{
				panel7.setLayout(new GridBagLayout());
				((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
				((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
				((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//---- connectJButton ----
				connectJButton.setText("Connect");
				panel7.add(connectJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- logJButton ----
				logJButton.setText("Log");
				panel7.add(logJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- resetJButton ----
				resetJButton.setText("Reset Data");
				panel7.add(resetJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			panel1.add(panel7, BorderLayout.CENTER);

			//======== loggerJMenuBar ========
			{

				//======== commJMenu ========
				{
					commJMenu.setText("Comm Ports");

				}
				loggerJMenuBar.add(commJMenu);
			}
			panel1.add(loggerJMenuBar, BorderLayout.NORTH);
		}
		add(panel1, BorderLayout.NORTH);

		//======== splitPane1 ========
		{

			//======== tabbedPane1 ========
			{

				//======== scrollPane2 ========
				{
					//scrollPane2.setViewportView(table3);
					
					this.scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					this.scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					scrollPane2.setViewportView(this.attributeJTable);
					
					//TableJPanel tableJPanel = new TableJPanel();
					//scrollPane2.setColumnHeaderView(tableJPanel.getTableHeader());
					//scrollPane2.setViewportView(new TableJPanel());
					
				}
				tabbedPane1.addTab("Parameters", scrollPane2);


				//======== scrollPane3 ========
				{
					scrollPane3.setViewportView(this.switchJTable);
				}
				tabbedPane1.addTab("Switches", scrollPane3);


				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(table2);
				}
				tabbedPane1.addTab("Sensors", scrollPane1);

			}
			splitPane1.setLeftComponent(tabbedPane1);

			//======== scrollPane4 ========
			{
				scrollPane4.setViewportView(dataJTable);
			}
			splitPane1.setRightComponent(scrollPane4);
		}
		add(splitPane1, BorderLayout.CENTER);
	}


	
	// ***************************
	// General getters and setters
	// ***************************
	public JPanel getPanel3() {
		return panel3;
	}

	public void setPanel3(JPanel panel3) {
		this.panel3 = panel3;
	}

	public JPanel getPanel4() {
		return panel4;
	}

	public void setPanel4(JPanel panel4) {
		this.panel4 = panel4;
	}

	public JLabel getStatusJLabel() {
		return statusJLabel;
	}

	public void setStatusJLabel(JLabel statusJLabel) {
		this.statusJLabel = statusJLabel;
	}

	public JPanel getPanel5() {
		return panel5;
	}

	public void setPanel5(JPanel panel5) {
		this.panel5 = panel5;
	}

	public JLabel getCalIDJLabel() {
		return calIDJLabel;
	}

	public void setCalIDJLabel(JLabel calIDJLabel) {
		this.calIDJLabel = calIDJLabel;
	}

	public JPanel getPanel6() {
		return panel6;
	}

	public void setPanel6(JPanel panel6) {
		this.panel6 = panel6;
	}

	public JLabel getRateJLabel() {
		return rateJLabel;
	}

	public void setRateJLabel(JLabel rateJLabel) {
		this.rateJLabel = rateJLabel;
	}

	public JPanel getPanel1() {
		return panel1;
	}

	public void setPanel1(JPanel panel1) {
		this.panel1 = panel1;
	}

	public JPanel getPanel7() {
		return panel7;
	}

	public void setPanel7(JPanel panel7) {
		this.panel7 = panel7;
	}

	public JToggleButton getConnectJButton() {
		return connectJButton;
	}

	public void setConnectJButton(JToggleButton connectJButton) {
		this.connectJButton = connectJButton;
	}

	public JToggleButton getLogJButton() {
		return logJButton;
	}

	public void setLogJButton(JToggleButton logJButton) {
		this.logJButton = logJButton;
	}

	public JButton getResetJButton() {
		return resetJButton;
	}

	public void setResetJButton(JButton resetJButton) {
		this.resetJButton = resetJButton;
	}

	public JMenuBar getLoggerJMenuBar() {
		return loggerJMenuBar;
	}

	public void setLoggerJMenuBar(JMenuBar loggerJMenuBar) {
		this.loggerJMenuBar = loggerJMenuBar;
	}

	public JMenu getCommJMenu() {
		return commJMenu;
	}

	public void setCommJMenu(JMenu commJMenu) {
		this.commJMenu = commJMenu;
	}

	public JMenuItem getTestJMenuItem() {
		return testJMenuItem;
	}

	public void setTestJMenuItem(JMenuItem testJMenuItem) {
		this.testJMenuItem = testJMenuItem;
	}

	public JSplitPane getSplitPane1() {
		return splitPane1;
	}

	public void setSplitPane1(JSplitPane splitPane1) {
		this.splitPane1 = splitPane1;
	}

	public JTabbedPane getTabbedPane1() {
		return tabbedPane1;
	}

	public void setTabbedPane1(JTabbedPane tabbedPane1) {
		this.tabbedPane1 = tabbedPane1;
	}

	public JScrollPane getScrollPane2() {
		return scrollPane2;
	}

	public void setScrollPane2(JScrollPane scrollPane2) {
		this.scrollPane2 = scrollPane2;
	}

	public JTable getTable3() {
		return table3;
	}

	public void setTable3(JTable table3) {
		this.table3 = table3;
	}

	public JScrollPane getScrollPane3() {
		return scrollPane3;
	}

	public void setScrollPane3(JScrollPane scrollPane3) {
		this.scrollPane3 = scrollPane3;
	}

	public JTable getTable4() {
		return table4;
	}

	public void setTable4(JTable table4) {
		this.table4 = table4;
	}

	public JScrollPane getScrollPane1() {
		return scrollPane1;
	}

	public void setScrollPane1(JScrollPane scrollPane1) {
		this.scrollPane1 = scrollPane1;
	}

	public JTable getTable2() {
		return table2;
	}

	public void setTable2(JTable table2) {
		this.table2 = table2;
	}

	public JScrollPane getScrollPane4() {
		return scrollPane4;
	}

	public void setScrollPane4(JScrollPane scrollPane4) {
		this.scrollPane4 = scrollPane4;
	}

	public JTable getTable1() {
		return table1;
	}

	public void setTable1(JTable table1) {
		this.table1 = table1;
	}
}

