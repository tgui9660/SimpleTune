package simpleTune.gui.compare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.etable.EInternalFrame;
import simpleTune.gui.interfaces.TuningEntity;

public class CompareJFrame extends JFrame {
	
	public CompareJFrame(){
		this.setTitle("Compare and Copy Tuning Groups");
		this.setSize(500, 600);
		//this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		this.add(new CompareJPanel(), BorderLayout.CENTER);
	}

}
