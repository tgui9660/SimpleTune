package simpleTune.gui.etable.dataJPanel;


import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.etable.ETable;
import simpleTune.gui.etable.dataJPanel.interfaces.DataJPanelInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class DataJPanel1DString extends JPanel implements DataJPanelInterface{
	private Log logger = LogFactory.getLog(getClass());
	private TableMetaData tableMetaData;
	private String initialStringValue;
	private JTextArea dataTextArea;
	
	private int FRAME_HEIGHT = 20;
	private int FRAME_WIDTH = 40;
	
	public DataJPanel1DString(TableMetaData tableMetaData, Object[][] data) {
		this.tableMetaData = tableMetaData;
		this.initialStringValue = (String)data[0][0];
		
		this.setLayout(new BorderLayout());
		
		dataTextArea = new JTextArea((String)data[0][0]);
		JScrollPane dataScrollPane = new JScrollPane(dataTextArea);
		dataScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dataScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.add(dataScrollPane, BorderLayout.CENTER);
	}
	
	public JToolBar getToolBar() {
		// TODO Auto-generated method stub
		return new JToolBar();
	}

	public JMenuBar getMenuBar() {
		// TODO Auto-generated method stub
		return new JMenuBar();
	}

	public boolean dataChanged() {
		// TODO Auto-generated method stub
		return this.initialStringValue.equals(this.dataTextArea.getText());
	}

	public void copySelectedTableData() {
		// TODO Auto-generated method stub
		
	}

	public void copyEntireTable() {
		// TODO Auto-generated method stub
		
	}

	public void pasteTableData() {
		// TODO Auto-generated method stub
		
	}

	public void setClosed(boolean value) {
		// TODO Auto-generated method stub
		
	}

	public void revertDataState() {
		// TODO Auto-generated method stub
		
	}

	public void saveDataState() {
		// TODO Auto-generated method stub
		
	}

	public void replaceMainData(Object[][] newData) {
		this.dataTextArea.setText((String)newData[0][0]);
	}
	
	public void refresh(){
		logger.info("Refresh called, not implemented.");
	}

	public void replaceXAxisData(Object[][] newData) {
		// TODO Auto-generated method stub
		
	}

	public void replaceYAxisData(Object[][] newData) {
		// TODO Auto-generated method stub
		
	}
	
	public Object[][] getData() {
		Object[][] temp = new Object[1][1];
		temp[0][0] = this.dataTextArea.getText();
		return temp;
	}

	public Dimension getFrameSize() {
		return new Dimension(this.FRAME_WIDTH, this.FRAME_HEIGHT);
	}

	public ETable getCurrentETableInScope() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCurrentETableInScope(ETable table) {
		// TODO Auto-generated method stub
		
	}

	public Object[][] getXAXisData() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[][] getYAxisData() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean mainDataTableChange() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean xAxisDataTableChange() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean yAxisDataTableChange() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setIsScaleActive(boolean isActive) {
		// TODO Auto-generated method stub
		
	}

	public boolean getIsScaleActive() {
		// TODO Auto-generated method stub
		return false;
	}

	public ETable getMainETable() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(DataJPanelInterface dataJPanel) {
		logger.info(" **** STRING Equals Test");
		
		// Same type?
		if(!(dataJPanel instanceof DataJPanel1DString)){
			return false;
		}
		
		Object[][] data1 = this.getData();
		Object[][] data2 = dataJPanel.getData();
		
		// Dimension check
		if(data1.length != data2.length || data1[0].length != data2[0].length){
			return false;
		}
		
		// Raw data check
		for(int i = 0; i < data1.length; i++){
			for(int j = 0; j < data1[0].length; i++){
				if(data1[i][j] != data2[i][j]){
					logger.info(" **** STRING Data Different.");
					return false;
				}
			}
		}
		
		logger.info(" **** STRING Data Same.");
		// Pass all checks, everything ok
		return true;
	}
}
