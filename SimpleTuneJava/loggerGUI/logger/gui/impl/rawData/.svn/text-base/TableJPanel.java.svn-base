package logger.gui.impl.rawData;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TableJPanel extends JPanel{
	
	private Log logger = LogFactory.getLog(getClass());
	
	private JTable table = null;
	
	
	public TableJPanel(){
		// Set layout
		//this.setLayout(new BorderLayout());
		
		
		GridLayout layout = new GridLayout(0,1);
		this.setLayout(layout);
		this.add(this.getNewInternalJPanel());
		this.add(this.getNewInternalJPanel());
		this.add(this.getNewInternalJPanel());
		this.add(this.getNewInternalJPanel());
		
		
		/*
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createHorizontalGlue());
		this.add(Box.createVerticalGlue());
		this.add(this.getNewInternalJPanel());
		
		this.add(Box.createHorizontalGlue());
		this.add(Box.createVerticalGlue());
		this.add(this.getNewInternalJPanel());
		*/
	}
	
	public JTableHeader getTableHeader(){
		return this.table.getTableHeader();
	}
	
	private JPanel getNewInternalJPanel(){
		JPanel panel = new JPanel();
		
		// Set layout
		panel.setLayout(new BorderLayout());
		
		// Define table
		JTable jTable = new JTable();
		//jTable.setModel(new AttributeTableModel());
		
		
		Object[][] data = {
			    {"Mary", "Campione",
			     "Snowboarding", new Integer(5), new Boolean(false)},
			    {"Alison", "Huml",
			     "Rowing", new Integer(3), new Boolean(true)},
			    {"Kathy", "Walrath",
			     "Knitting", new Integer(2), new Boolean(false)},
			    {"Sharon", "Zakhour",
			     "Speed reading", new Integer(20), new Boolean(true)},
			    {"Philip", "Milne",
			     "Pool", new Integer(10), new Boolean(false)}
			};
		String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

		table = new JTable(data, columnNames);
		
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
		panel.add(table, BorderLayout.CENTER);
		
		return panel;
	}
}
