package logger.gui.impl.csvData;

import java.awt.BorderLayout;
import java.util.LinkedList;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.gui.impl.base.DataConsumerGUIBase;
import logger.interfaces.LoggingAttribute;

/**
 * Class aggregates all CSV files together in an easy GUI
 * 
 * @author emorgan
 *
 */
public class CSVDataConsumer extends DataConsumerGUIBase{

	private static final long serialVersionUID = 7449127324984583023L;

	private Log logger = LogFactory.getLog(getClass());
	
	private String NAME = "CSV Logs";
	
	private JSplitPane splitPane = new JSplitPane();
	private DataLinesJTable linesJTable = new DataLinesJTable();
	private FileJTable fileJTable = new FileJTable();
	private JScrollPane leftScroll = new JScrollPane();
	private JScrollPane rightScroll = new JScrollPane();
	
	
	public CSVDataConsumer(){
		super();
		logger.debug("Instantiated.");
		init();
	}
	
	public void init(){
		this.setName(this.NAME);
		
		// Simple Layout Manager
		setLayout(new BorderLayout());
		
		// Build up viewports
		this.leftScroll.setViewportView(fileJTable);
		this.rightScroll.setViewportView(linesJTable);
		this.splitPane.setLeftComponent(leftScroll);
		this.splitPane.setRightComponent(rightScroll);
		
		// Add to main JPanel
		this.add(splitPane, BorderLayout.CENTER);
	}

	@Override
	public void attributesAdded(LinkedList<LoggingAttribute> loggingAttribute) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributesRemoved(LinkedList<LoggingAttribute> loggingAttribuets) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return this.NAME;
	}

	@Override
	public void loggingStateChange(boolean isLogging) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newDataAvailable(LinkedList<LoggingAttribute> attributesUpdated) {
		// Add data to current CSV
		this.linesJTable.newDataAvailable(attributesUpdated);
	
		// Attempt to scroll down with the addition of new data
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JScrollBar scrollBar = rightScroll.getVerticalScrollBar();
				scrollBar.setValue(scrollBar.getMaximum());
			}
		});
	}
}
