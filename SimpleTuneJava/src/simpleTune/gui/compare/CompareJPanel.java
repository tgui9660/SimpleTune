package simpleTune.gui.compare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.SimpleTuneGUI;
import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.etable.EInternalFrame;
import simpleTune.gui.interfaces.TuningEntity;

public class CompareJPanel extends JPanel implements ActionListener, ListSelectionListener{

	private Vector<String> openTuningGroups = null;
	private Object[] tgListArray = null;
	
	private JComboBox tgListOne = null;
	private JComboBox tgListTwo = null;
	
	private JButton compareButton = new JButton("Compare");
	private JButton selectAllButton = new JButton("Select All");
	private JButton commitButton = new JButton("Commit");
	
	private String selectedTGOne = "";
	private String selectedTGTwo = "";
	
	private Vector<String> tableListOne = new Vector<String>();
	private Vector<String> tableListTwo = new Vector<String>();
	
	private JTable compareJTable = new JTable();
	
	private JLabel countJLabel = new JLabel("Table Match Count:0");
	private Log logger = LogFactory.getLog(getClass());
	
	private CompareTableModel tableModel = new CompareTableModel();
	
	// ############ GLOBAL VARS, MESSY YES!!! ###########
	// Current selected table
	private String currentSelectedTable = "";
	
	public CompareJPanel(){
		this.initGui();
	}
	
	private void initGui() {
		
		this.setLayout(new BorderLayout());

		// Pull down lists & initial state
		openTuningGroups = ApplicationStateManager.getOpenTuningGroups();
		tgListArray = openTuningGroups.toArray();
		
		tgListOne = new JComboBox(tgListArray);
		tgListOne.setSelectedIndex(0);
		tgListOne.addActionListener(this);
		this.selectedTGOne = (String)tgListArray[0];
		this.tableListOne = ApplicationStateManager.getTableList(this.selectedTGOne);
		
		tgListTwo = new JComboBox(tgListArray);
		tgListTwo.setSelectedIndex(tgListArray.length-1);
		tgListTwo.addActionListener(this);
		this.selectedTGTwo = (String)tgListArray[tgListArray.length-1];
		this.tableListTwo = ApplicationStateManager.getTableList(this.selectedTGTwo);
		
		
		//Lay out the pull down lists
		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		listPane.add(Box.createVerticalGlue());
		//listPane.add(Box.createHorizontalGlue());
		listPane.add(new JLabel("Base Tuning Group"));
		listPane.add(tgListOne);
		listPane.add(Box.createRigidArea(new Dimension(0, 20)));
		listPane.add(new JLabel("Comparison Tuning Group"));
		listPane.add(tgListTwo);
		listPane.add(Box.createRigidArea(new Dimension(0, 20)));
		listPane.add(this.countJLabel);
		
		// Layout the buttons
		this.compareButton.addActionListener(this);
		this.selectAllButton.addActionListener(this);
		this.commitButton.addActionListener(this);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(this.compareButton);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonPane.add(this.selectAllButton);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonPane.add(this.commitButton);

		// Build up JTable to hold table comparisons
		this.compareJTable = new JTable(tableModel);
		this.compareJTable.setColumnSelectionAllowed(true);

		// Not available for all java versions
		//this.compareJTable.setFillsViewportHeight(true);
		
		TableColumnModel columnModel = this.compareJTable.getColumnModel();
	    
		TableColumn tc = columnModel.getColumn(0);
	    tc.setPreferredWidth(50);
	    tc.setMaxWidth(50);
	    
		TableColumn tc2 = columnModel.getColumn(1);
	    tc2.setPreferredWidth(this.getWidth() - 80);
	    
	    JScrollPane scrollPane = new JScrollPane(this.compareJTable);
	    
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    
		// Setup the table double click and selection functionality
		this.compareJTable.getSelectionModel().addListSelectionListener(this);
		
		this.compareJTable.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2){
					// Open up both tables on table row double click
					ApplicationStateManager.getSTInstance().displayInternalFrameTable(selectedTGOne, tableModel.getBasePath(currentSelectedTable), true);
					ApplicationStateManager.getSTInstance().displayInternalFrameTable(selectedTGTwo, tableModel.getComparePath(currentSelectedTable), true);
				}
			}
		} );
	    
	    
		this.add(listPane, BorderLayout.NORTH);
		this.add(buttonPane, BorderLayout.SOUTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
        
		if(e.getSource().equals(this.tgListOne)){
			logger.info("List One Hit");
			
			String newSelection = (String)((JComboBox)(e.getSource())).getSelectedItem();
			
			logger.info(":"+newSelection+":  :"+this.selectedTGOne+":");
			
			if(!newSelection.equals(this.selectedTGOne)){
				logger.info("Updating count");
				this.getTableModel().clearTable();
				this.selectedTGOne = newSelection;
				this.tableListOne = ApplicationStateManager.getTableList(this.selectedTGOne);
				this.updateCountJLabel(0);
			}
		}
		
		if(e.getSource().equals(this.tgListTwo)){
			logger.info("List Two Hit");
			
			String newSelection = (String)((JComboBox)(e.getSource())).getSelectedItem();
			
			logger.info(":"+newSelection+":  :"+this.selectedTGTwo+":");
			
			if(!newSelection.equals(this.selectedTGTwo)){
				logger.info("Updating count");
				this.getTableModel().clearTable();
				this.selectedTGTwo = newSelection;
				this.tableListTwo = ApplicationStateManager.getTableList(this.selectedTGTwo);
				this.updateCountJLabel(0);
			}
		}
		
		if(e.getSource().equals(this.compareButton)){
			logger.info("Comparing maps");
			this.tableModel.clearTable();
			buildUpCompareTable();
		}
		
		if(e.getSource().equals(this.selectAllButton)){
			logger.info("Selecting all maps");
			selectAll();
		}
		if(e.getSource().equals(this.commitButton)){
			logger.info("Commiting all changes");
			commitChanges();
		}
	}
	
	private void selectAll(){
		this.tableModel.selectAll();
	}

	private void commitChanges(){
		logger.info("Gathering list of changed tables & paths.");
		
		Vector<String> changeTables = this.tableModel.getChangeTables();
		Iterator<String> iterator3 = changeTables.iterator();
		
		logger.info("Found this many paths:"+changeTables.size());
		
		while(iterator3.hasNext()){
			
			String tableName = iterator3.next();
			
			logger.info("Copying table :"+tableName+" >"+this.tableModel.getBasePath(tableName)+"  >"+this.tableModel.getComparePath(tableName));
			
			TableMetaData tableMetaDataOne = ApplicationStateManager.getTGTableMetaData(this.tableModel.getBasePath(tableName), this.selectedTGOne);
			TableMetaData tableMetaDataTwo = ApplicationStateManager.getTGTableMetaData(this.tableModel.getComparePath(tableName), this.selectedTGTwo);
			
			TuningEntity tuningEntity = ApplicationStateManager.getTGTuningEntity(this.selectedTGOne);
			Object[][] tableDataOne = tuningEntity.getTableData(this.selectedTGOne, this.tableModel.getBasePath(tableName));
			
			TuningEntity tuningEntity2 = ApplicationStateManager.getTGTuningEntity(this.selectedTGTwo);
			Object[][] tableDataTwo = tuningEntity2.getTableData(this.selectedTGTwo, this.tableModel.getComparePath(tableName));
			
			EInternalFrame frameOne = ApplicationStateManager.getExistingEInternalFrame(tableMetaDataOne);
			EInternalFrame frameTwo = ApplicationStateManager.getExistingEInternalFrame(tableMetaDataTwo);
			
			if(frameOne == null){
				frameOne = new EInternalFrame(tableMetaDataOne, tableDataOne);
			}
			if(frameTwo == null){
				frameTwo = new EInternalFrame(tableMetaDataTwo, tableDataTwo);
			}
			
			frameOne.copy(frameTwo);
			//frameOne.saveDataToParentTuningEntity();
			//frameOne.refresh();
		}
		
		// Refresh all internal open frames
		//ApplicationStateManager.refresh();
	}
	
	private void buildUpCompareTable(){
		if(this.tableListOne.size() > 0 && this.tableListTwo.size() > 0){
			Iterator<String> iterator = this.tableListOne.iterator();
			CompareTableModel model = this.getTableModel();
			int matchCount = 0;
			while(iterator.hasNext()){
				
				String next = iterator.next();
				String[] split = next.split(";");
				String one = split[split.length - 1];
				
				Iterator<String> iterator2 = tableListTwo.iterator();
				while(iterator2.hasNext()){
					String next2 = iterator2.next();
					
					String[] split2 = next2.split(";");
					String two = split2[split2.length - 1];
					//logger.info("Comparing "+one+"   :   "+two);
					
					// Make sure that the table names match
					if(one.equals(two)){
						logger.info("NEXT >" + next + "   :   NEXT2 >"+next2);
						logger.info("TG1 >"+this.selectedTGOne+"   :    TG2 >"+this.selectedTGTwo);
						TableMetaData tableMetaDataOne = ApplicationStateManager.getTGTableMetaData(next, this.selectedTGOne);
						TableMetaData tableMetaDataTwo = ApplicationStateManager.getTGTableMetaData(next2, this.selectedTGTwo);
						
						if(tableMetaDataOne == null){
							logger.error("tableMetaDataOne is NULL");
							return;
						}
						if(tableMetaDataTwo == null){
							logger.error("tableMetaDataTwo is NULL");
							return;
						}
						
						logger.info("Dimension 1 >" + tableMetaDataOne.getDimension().getWidth() +"   :   "+tableMetaDataOne.getDimension().getHeight()+ "   :   Dimension 2 >"+tableMetaDataTwo.getDimension().getWidth()+"  :  "+tableMetaDataTwo.getDimension().getHeight());
						
						TuningEntity tuningEntity = ApplicationStateManager.getTGTuningEntity(this.selectedTGOne);
						Object[][] tableDataOne = tuningEntity.getTableData(this.selectedTGOne, next);
						
						TuningEntity tuningEntity2 = ApplicationStateManager.getTGTuningEntity(this.selectedTGTwo);
						Object[][] tableDataTwo = tuningEntity2.getTableData(this.selectedTGTwo, next2);
						
						// Node types and table dimensions 5x6 ex: must be the same
						if((tableMetaDataOne.getNodeType() == tableMetaDataTwo.getNodeType()) && 
								tableMetaDataOne.getDimension().equals(tableMetaDataTwo.getDimension()) &&
								tableDataOne.length == tableDataTwo.length && 
								tableDataOne[0].length == tableDataTwo[0].length){
							
							EInternalFrame frameOne = ApplicationStateManager.getExistingEInternalFrame(tableMetaDataOne);
							EInternalFrame frameTwo = ApplicationStateManager.getExistingEInternalFrame(tableMetaDataTwo);
							
							if(frameOne == null){
								frameOne = new EInternalFrame(tableMetaDataOne, tableDataOne);
							}
							if(frameTwo == null){
								frameTwo = new EInternalFrame(tableMetaDataTwo, tableDataTwo);
							}
							
							if(!frameOne.equals(frameTwo)){
								logger.info(" **** DIFFERENT Data!");
								matchCount++;
								
								// Pull raw table name
								String[] split3 = next.split(";");
								String tableName = split[split.length - 1];
								
								model.addRow(tableName, next, next2);
								
								break;
							}else{
								
							}
						}
					}
				}
			}
			
			this.updateCountJLabel(matchCount);
		}
	}
	
	private CompareTableModel getTableModel(){
		return (CompareTableModel)this.compareJTable.getModel();
	}
	
	private void updateCountJLabel(int count){
		this.countJLabel.setText("Table Match Count:"+count);
	}

	public void valueChanged(ListSelectionEvent event) {
        
 		// See if this is a valid table selection
		if( event.getSource() == this.compareJTable.getSelectionModel() && event.getFirstIndex() >= 0 )
		{
			// Get the data model for this table
			CompareTableModel model = (CompareTableModel)this.compareJTable.getModel();

			if(this.compareJTable.getSelectedRow() > -1 &&
					this.compareJTable.getSelectedColumn() > -1 &&
					model.getValueAt(this.compareJTable.getSelectedRow(), this.compareJTable.getSelectedColumn() ) instanceof String){
				// Determine the selected item
				this.currentSelectedTable = (String)model.getValueAt(this.compareJTable.getSelectedRow(), this.compareJTable.getSelectedColumn() );	
			}
			
		}
	}
	
}
