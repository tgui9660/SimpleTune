package logger.gui.impl.rawData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logger.dataSupplier.interfaces.DataSupplierListener;
import logger.gui.interfaces.StatusListener;
import logger.gui.status.StatusController;
import logger.interfaces.LoggingAttribute;
import logger.interfaces.LoggingSwitch;

public class AttributeJTable extends JTable implements DataSupplierListener, ActionListener{

	private Log logger = LogFactory.getLog(getClass());

	private String NAME = "AttributeTable";
	private RawDataJTable dataJTable = null;
	private AttributeTableModel model = new AttributeTableModel();

	// These variables are used to calculate the queries/sec metric
	private long oldTime = Calendar.getInstance().getTimeInMillis();
	private long newTime = Calendar.getInstance().getTimeInMillis();
	private int oldAttributeCount = 0;
	private int newAttributeCount = 0;


	public AttributeJTable(){
		this.setModel(model);
		this.getColumnModel().getColumn(0).setMaxWidth(50);
	}

	/**
	 * Used to enable different combo boxes in different rows
	 */
	public TableCellEditor getCellEditor(int row, int column){
		if(column == 2){
			AttributeJComboBox cb = new AttributeJComboBox(this.model.getConversions(row));
			cb.addActionListener(this);
			cb.setLoggingAttribute(this.model.getLoggingAttribute(row));
			return new DefaultCellEditor(cb);
		}
		return super.getCellEditor(row, column);
	}


	public String getDataSupplierListenerName() {
		return this.NAME;
	}


	public void setDataJTables(RawDataJTable dataJTable){
		this.dataJTable = dataJTable;
		this.model.setDataJTable(dataJTable);
	}


	public void newDataAvailable(LinkedList<LoggingAttribute> attributesUpdated) {
		System.out.println("AttributeJTable New data, count : "+attributesUpdated.size());

		// Update time and attribute metrics ASAP for more reasonable results
		this.oldTime = this.newTime;
		this.oldAttributeCount = this.newAttributeCount;
		this.newAttributeCount = attributesUpdated.size();
		this.newTime = Calendar.getInstance().getTimeInMillis();

		this.updateQueryRateMetric();

		this.dataJTable.updateAttributes(attributesUpdated);
	}

	public void attributesAdded(LinkedList<LoggingAttribute> loggingAttribute) {
		this.model.addAttributes(loggingAttribute);

		this.validate();
	}

	private void updateQueryRateMetric(){
		String message = "--";
		double deltaTimeMil = this.newTime - this.oldTime;
		double deltaTimeSec = deltaTimeMil/1000.0;

		System.out.println("deltaTimeSec : "+ deltaTimeSec);
		System.out.println("deltaTimeMil : "+ deltaTimeMil);
		System.out.println(" -- old : "+ this.newTime);
		System.out.println(" -- new : "+ this.oldTime);

		if(this.newAttributeCount != 0 && deltaTimeSec != 0){
			DecimalFormat formatter = new DecimalFormat("##.##");
			message = formatter.format(deltaTimeSec) + " Sec/Query ";
			message += formatter.format(1.0 / deltaTimeSec) + "Queries/Sec ";
		}

		StatusController.getInstance().addStatusMessage(StatusListener.MESSAGE_TPYE_QUERY_RATE, message);			
	}

	/**
	 * Listen for combo box selections and apply conversions and units
	 */
	public void actionPerformed(ActionEvent e) {
		AttributeJComboBox cb = (AttributeJComboBox)e.getSource();
		String units = (String)cb.getSelectedItem();
		System.out.println("Units: "+units);

		String expression = cb.getLoggingAttribute().getConversions().get(cb.getSelectedItem());
		System.out.println("Expression: "+expression);

		// Set new units and expression in attribute
		LoggingAttribute loggingAttribute = cb.getLoggingAttribute();
		loggingAttribute.setUnits(units);
		loggingAttribute.setExpression(expression);

		// Data table model must update this attribute to reflect unit changes
		// NOTE: On next data received the values will be recalculated with the new expression
		((RawDataTableModel)this.dataJTable.getModel()).updateAttributeUnits(loggingAttribute);
	}

	public void attributesRemoved(LinkedList<LoggingAttribute> loggingAttributes) {
		this.model.removeAttributes(loggingAttributes);
	}

	public void loggingStateChange(boolean isLogging) {
		// TODO Auto-generated method stub

	}

}
