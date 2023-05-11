package simpleTune.gui.etable;


import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.ecm.graphics.Graph3dJPanel;
//import com.ecm.os.Graph3D;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.etable.dataJPanel.DataJPanel1DString;
import simpleTune.gui.etable.dataJPanel.DataJPanelDouble;
import simpleTune.gui.etable.dataJPanel.DataJPanelSwitch;
import simpleTune.gui.etable.dataJPanel.interfaces.DataJPanelInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Vector;

public class EInternalFrame extends JInternalFrame implements InternalFrameListener, ActionListener{
	private Stack<ETableSaveState> savedData = new Stack<ETableSaveState>();
	private TableMetaData tableMetaData;
	private DataJPanelInterface dataJPanel = null;
	public DataJPanelInterface getDataJPanel() {
		return dataJPanel;
	}
	private Log logger = LogFactory.getLog(getClass());
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JPanel descriptionJPanel = new JPanel();
	private boolean scaleToggleMode = false;
	
	public EInternalFrame(TableMetaData tableMetaData, Object[][] data){
		super(tableMetaData.getTableName().toUpperCase() + " - [" + tableMetaData.getShortFileName()+"]", true, true, true, true);
		
		// Save off very important table meta data
		this.tableMetaData = tableMetaData;
		
		// **************************************************
		// Pull the appropriate jpanel based on tableMetaData
		// **************************************************
		if(tableMetaData.getNodeType() == TableMetaData.DATA_1D){
			if(data[0][0] instanceof String){
				dataJPanel = new DataJPanel1DString(tableMetaData, data);
			}
			else if(data[0][0] instanceof Double){
				dataJPanel = new DataJPanelDouble(tableMetaData, data);
			}
		}else if(tableMetaData.getNodeType() == TableMetaData.DATA_2D){

			if(data[0][0] instanceof Double){
				dataJPanel = new DataJPanelDouble(tableMetaData, data);
			}
			
		}else if(tableMetaData.getNodeType() == TableMetaData.DATA_3D){
			
			if(data[0][0] instanceof Double){
				dataJPanel = new DataJPanelDouble(tableMetaData, data);
			}
			
		}else if(tableMetaData.getNodeType() == TableMetaData.SWITCH){
			logger.info("EInternalFrame starting new switch jpanel.");
			dataJPanel = new DataJPanelSwitch(tableMetaData, (Boolean)data[0][0]);
		}else{
			logger.error("No applicable DataJPanel found for table type code:"+tableMetaData.getNodeType());
			return;
		}
		

		
		// *****************************
		// Build up final internal frame
		// *****************************
		
		// Ensure we set the appropriate frame dimensions and pick frame icon
		Image img = null;
		if(tableMetaData.getNodeType() == TableMetaData.DATA_1D){
			img = Toolkit.getDefaultToolkit().getImage("./graphics/treeNodeImages/1d.gif");
		}else if(tableMetaData.getNodeType() == TableMetaData.DATA_2D){
			img = Toolkit.getDefaultToolkit().getImage("./graphics/treeNodeImages/2d.gif");
		}else if(tableMetaData.getNodeType() == TableMetaData.DATA_3D){
			img = Toolkit.getDefaultToolkit().getImage("./graphics/treeNodeImages/3d.gif");
		}else if(tableMetaData.getNodeType() == TableMetaData.SWITCH){
			img = Toolkit.getDefaultToolkit().getImage("./graphics/treeNodeImages/switch.gif");
		}else{
			img = Toolkit.getDefaultToolkit().getImage("graphics/SimpleTune.png");
		}
		
		// Setup the main frame attributes
		ImageIcon imgIcon = new ImageIcon(img);
		this.setFrameIcon(imgIcon);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		this.addInternalFrameListener(this);
		
		if(this.tableMetaData == null){
			logger.error("NULL TableMetaData!");
			return;
		}
		
		if(this.dataJPanel == null){
			logger.error("DataJPanel NULL!");
			return;
		}
		
		// Set the frame size
		if(this.dataJPanel.getFrameSize() != null && this.tableMetaData.getNodeType() == TableMetaData.DATA_1D || this.tableMetaData.getNodeType() == TableMetaData.DATA_2D || this.tableMetaData.getNodeType() == TableMetaData.DATA_3D){
			Dimension frameSize = this.dataJPanel.getFrameSize();
			int width = frameSize.width;
			int height = frameSize.height;
			
			if(height > 500){
				height = 500;
			}
			
			if(height < 75){
				height = 75;
			}

			if(width < 450){
				width = 450;
			}
			
			this.setSize(width+40, height+120);
		}else if(this.dataJPanel.getFrameSize() != null && this.tableMetaData.getNodeType() == TableMetaData.SWITCH){
			this.setSize(this.dataJPanel.getFrameSize());
		}
		
		// Define the description tab
		JTextArea metaTextArea = new JTextArea();
		metaTextArea.setWrapStyleWord(true);
		metaTextArea.setLineWrap(true);
		metaTextArea.setText(tableMetaData.getDescription());
		metaTextArea.setEditable(false);
		metaTextArea.setCaretPosition(0); // Set pane to scroll to top initially
		
		// Set size and initial positions
		JScrollPane descriptionScrollingArea = new JScrollPane(metaTextArea);
		if(tableMetaData.getNodeType() == TableMetaData.SWITCH){
			descriptionScrollingArea.setPreferredSize(new Dimension(this.getSize().width - 42, this.getSize().height - 85));
		}else{
			descriptionScrollingArea.setPreferredSize(new Dimension(this.getSize().width - 42, this.getSize().height - 150));
		}
		descriptionJPanel.add(descriptionScrollingArea, BorderLayout.CENTER);
		
		/*
		Graph3dJPanel graph3d = new Graph3dJPanel();
		Vector testVector = new Vector();
        for (int i = 0; i < 12; i++) {
                float[] testData = { 0.0f + i, 1.0f + i, 2.0f + i, 3.0f + i,
                                -4.0f + i, -5.0f + i, -4.0f + i, -3.0f + i, 20.0f + i, 1.0f+i, 0.0f + i, 1.0f + i, 2.0f + i, 3.0f + i,
                                4.0f + i, 5.0f + i};
                testVector.add(testData);
        }
        
        double[] testX = { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000 };
        double[] testZ = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 , 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40};


		graph3d.Init(testVector, -10, 10, testX, testZ, "X Label", "Y Label", "Z label");
		*/
		
		// Setup the tabbed panes.
		this.tabbedPane.addTab("Table Data", null, (JPanel)this.dataJPanel, "Data pertinent to table.");
		
		// Only add description if there is data
		if(tableMetaData.getDescription().length() != 0){
			this.tabbedPane.addTab("Description", null, this.descriptionJPanel, "Misc table data.");
		}
		
		/*
	    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        GraphData graphData = new GraphData("./testData/plot3D.dat");
        Graph3D graph3d = new Graph3D( config, graphData );
        */
		
		//this.tabbedPane.addTab("3D", null, graph3d, "3D Data Model.");
		//this.tabbedPane.addTab("3D", null, new Graph3D(), "3D Data Model.");
		//this.tabbedPane.setSize(this.getSize());
		

		// Put everything into this frame.
		//this.setJMenuBar(this.dataJPanel.getMenuBar());
		this.add(this.tabbedPane, BorderLayout.CENTER);
		this.add(this.dataJPanel.getToolBar(), BorderLayout.NORTH);
		
		// NO resizing.
		this.setResizable(false);
		this.setMaximizable(false);
	}
	
	/**
	 * Check to see if data relevant to this frame has changed.
	 * 
	 * Call down to the data level.
	 * 
	 * @return
	 */
	public boolean dataChanged(){
		return this.dataJPanel.dataChanged();
	}
	
	/**
	 * Called by main gui when a save action occurs
	 */
	public void saveDataToParentTuningEntity(){
		if(this.dataJPanel.getData() == null){
			logger.error("EInternalFrame data in cells is null.");
		}	
		logger.info("EInternalFrame saving :"+this.tableMetaData.getTableName());
		logger.info("---            X Axis :"+this.tableMetaData.getXAxisLabel());
		logger.info("---            Y Axis :"+this.tableMetaData.getYAxisLabel());
		
		// Save the main table data
		if(this.dataJPanel.mainDataTableChange()){
			logger.info("Saving Main Data");
			this.tableMetaData.getParentTuningEntity().saveTableData(this.tableMetaData.getTableGroup(), this.tableMetaData.getTableIdentifier(), this.dataJPanel.getData(), ApplicationStateManager.MAIN_TABLE_DATA);	
		}
		
		// Save the X Axis data if possible
		if(this.dataJPanel.getXAXisData() != null && this.dataJPanel.xAxisDataTableChange() && this.tableMetaData.getColumnLabels() != null){
			logger.info("Saving X Axis Data");
			this.tableMetaData.getParentTuningEntity().saveTableData(this.tableMetaData.getTableGroup(), this.tableMetaData.getTableIdentifier(), this.dataJPanel.getXAXisData(), ApplicationStateManager.XAXIS_TABLE_DATA);
		}
		
		// Save the Y Axis data if possible
		if(this.dataJPanel.getYAxisData() != null && this.dataJPanel.yAxisDataTableChange() && this.tableMetaData.getRowLabels() != null){
			logger.info("Saving Y Axis Data");
			this.tableMetaData.getParentTuningEntity().saveTableData(this.tableMetaData.getTableGroup(), this.tableMetaData.getTableIdentifier(), this.dataJPanel.getYAxisData(), ApplicationStateManager.YAXIS_TABLE_DATA);
		}
		
		// Case if the inner table is a switch
		if(tableMetaData.getNodeType() == TableMetaData.SWITCH && this.dataJPanel.dataChanged() == true){
			logger.info("EInternalFrame :Saving switch data.");
			this.tableMetaData.getParentTuningEntity().saveTableData(this.tableMetaData.getTableGroup(), this.tableMetaData.getTableIdentifier(), this.dataJPanel.getData(), ApplicationStateManager.SWITCH_TABLE_DATA);
		}
		
		// Case if the inner table is a 1d string
		if(tableMetaData.getNodeType() == TableMetaData.DATA_1D && this.dataJPanel.dataChanged() == true){
			logger.info("EInternalFrame :Saving 1D data String.");
			this.tableMetaData.getParentTuningEntity().saveTableData(this.tableMetaData.getTableGroup(), this.tableMetaData.getTableIdentifier(), this.dataJPanel.getData(), ApplicationStateManager.STRING_TABLE_DATA);
		}
		
	}
	
	public void saveDataState(){
		this.savedData.push(new ETableSaveState(this.dataJPanel.getData()));
	}
	
	public void revertDataState(){
		if(!this.savedData.isEmpty()){
			if(this.savedData.size() > 1){
				this.setTableData(this.savedData.pop().getData());
			}else if(savedData.size() == 1){
				this.setTableData(this.savedData.peek().getData());
			}
		}
	}
	
	
	public void setTableData(Object[][] data){
		this.dataJPanel.replaceMainData(data);
	}
	
	public Object[][] getTableData(){
		return this.dataJPanel.getData();
	}
	
	public void internalFrameOpened(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		//logger.info("OPEN!!!");
	}

	public void internalFrameClosing(InternalFrameEvent arg0) {
		this.setVisible(false);
		//logger.info("CLOSE!!!");
		//this.dispose();
	}

	public void internalFrameClosed(InternalFrameEvent arg0) {
	}

	public void internalFrameIconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void internalFrameDeiconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameActivated(InternalFrameEvent arg0) {
		//logger.info("SELECTED!!!");
		ApplicationStateManager.setSelectedTuningGroup(this.tableMetaData.getTableGroup());
	}

	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// logger.info("Action!!!");
	}

	
	public TableMetaData getTableMetaData() {
		return tableMetaData;
	}
	
	public void setVisible(boolean aFlag) {
		//logger.info("Visible toggle!!!");
		//TuningEntity currentTuningEntity = ApplicationStateManager.getCurrentTuningEntity();
		//String tableGroup = this.tableMetaData.getTableGroup();
		//currentTuningEntity.setCurrentTuningGroupInScope(tableGroup);
		super.setVisible(aFlag);
	}
	/*
	public ETable getETable() {
		return eTable;
	}

	public ClipBoardCopy getExcelCopy() {
		return excelCopy;
	}
	*/
	public boolean equals(EInternalFrame internalFrame){
		
		// Make sure the two tables are the same type 1D 2D 3D
		if(internalFrame.getTableMetaData().getDimensions() != this.getTableMetaData().getDimensions()){
			return false;
		}
		
		// Make sure the data contained is of the same dimensions. IE 2D arrays of same size.
		// Also checks to see if raw data is equal
		boolean equals = this.getDataJPanel().equals(internalFrame.getDataJPanel());
		
		return equals;
	}
	
	public void refresh(){
		logger.info("Refreshing frame.");
		this.dataJPanel.refresh();
	}
	
	public void copy(EInternalFrame other){
		logger.info("Copying enire ETable.");
		Object[][] data = other.getDataJPanel().getData();
		this.dataJPanel.replaceMainData(data);
		
		// Copy the X Axis data if possible
		if(this.dataJPanel.getXAXisData() != null){
			logger.info("Copy X Axis Data");
			Object[][] axisData = other.getDataJPanel().getXAXisData();
			this.dataJPanel.replaceXAxisData(axisData);
		}
		
		// Copy the Y Axis data if possible
		if(this.dataJPanel.getYAxisData() != null){
			logger.info("Copy Y Axis Data");
			Object[][] axisData = other.getDataJPanel().getYAxisData();
			this.dataJPanel.replaceYAxisData(axisData);
		}
		
		// Case if the inner table is a switch
		if(tableMetaData.getNodeType() == TableMetaData.SWITCH){
			logger.info("EInternalFrame :Copy switch data.");
			this.tableMetaData.getParentTuningEntity().saveTableData(this.tableMetaData.getTableGroup(), this.tableMetaData.getTableIdentifier(), this.dataJPanel.getData(), ApplicationStateManager.SWITCH_TABLE_DATA);
		}
		
		// Case if the inner table is a 1d string
		if(tableMetaData.getNodeType() == TableMetaData.DATA_1D){
			logger.info("EInternalFrame :Copy 1D data String.");
			this.tableMetaData.getParentTuningEntity().saveTableData(this.tableMetaData.getTableGroup(), this.tableMetaData.getTableIdentifier(), this.dataJPanel.getData(), ApplicationStateManager.STRING_TABLE_DATA);
		}
		
		this.saveDataToParentTuningEntity();
	}
}
