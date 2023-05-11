package simpleTune.gui.etable.dataJPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.etable.ETable;
import simpleTune.gui.etable.dataJPanel.interfaces.DataJPanelInterface;

public class DataJPanelSwitch extends JPanel implements DataJPanelInterface, ActionListener{
	private Log logger = LogFactory.getLog(getClass());
	private TableMetaData tableMetaData;
	private JCheckBox onButton;
	private JCheckBox offButton;
	boolean initialSwitchValue;
	boolean userSwitchValue;
	
	public boolean isUserSwitchValue() {
		return userSwitchValue;
	}

	public DataJPanelSwitch(TableMetaData tableMetaData, boolean switchValue){
		logger.info("Switch frame initial value :"+switchValue);
		
		this.tableMetaData = tableMetaData;
		this.initialSwitchValue = switchValue;
		this.userSwitchValue = switchValue;
		
		this.setLayout(new BorderLayout());
		
		// Define ON button
		onButton = new JCheckBox("Enabled");
		onButton.setEnabled(true);
		onButton.setActionCommand("on");
		onButton.addActionListener(this);
		
		
		// Define OFF button
		offButton = new JCheckBox("Disabled");
		offButton.setEnabled(true);
		offButton.setActionCommand("off");
		offButton.addActionListener(this);
		
		if(switchValue){
			onButton.setSelected(true);
			offButton.setSelected(false);
		}else{
			onButton.setSelected(false);
			offButton.setSelected(true);
		}
		
		this.add(onButton, BorderLayout.WEST);
		this.add(offButton, BorderLayout.EAST);
	}
	
	public void copyEntireTable() {
		// TODO Auto-generated method stub
		
	}

	public void copySelectedTableData() {
		// TODO Auto-generated method stub
		
	}

	public boolean dataChanged() {
		if(this.initialSwitchValue == this.userSwitchValue){
			return false;
		}else{
			return true;
		}
	}

	public Object[][] getData() {
		Boolean[][] returnValues = new Boolean[1][1];
		returnValues[0][0] = this.userSwitchValue;
		return returnValues;
	}

	public JToolBar getToolBar() {
		// TODO Auto-generated method stub
		return new JToolBar();
	}

	public JMenuBar getMenuBar() {
		// TODO Auto-generated method stub
		return new JMenuBar();
	}

	public void pasteTableData() {
		// TODO Auto-generated method stub
		
	}

	public void replaceMainData(Object[][] newData) {
		Boolean newValue = (Boolean)(newData[0][0]);
		this.userSwitchValue = newValue;
	}

	public void refresh(){
		this.setSwitchValue(this.userSwitchValue);
	}
	
	public void replaceXAxisData(Object[][] newData) {
		// TODO Auto-generated method stub
		
	}

	public void replaceYAxisData(Object[][] newData) {
		// TODO Auto-generated method stub
		
	}
	
	public void revertDataState() {
		// TODO Auto-generated method stub
		
	}

	public void saveDataState() {
		// TODO Auto-generated method stub
		
	}

	public void setClosed(boolean value) {
		// TODO Auto-generated method stub
		
	}

	public Dimension getFrameSize() {
		// TODO Auto-generated method stub
		return new Dimension(300, 200);
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equalsIgnoreCase("on")){
			this.setSwitchValue(true);
		}else if(actionCommand.equalsIgnoreCase("off")){
			this.setSwitchValue(false);
		}
	}
	
	public void setSwitchValue(boolean value){
		if(value){
			this.offButton.setSelected(false);
			this.onButton.setSelected(true);
			this.userSwitchValue = true;
		}else{
			this.offButton.setSelected(true);
			this.onButton.setSelected(false);
			this.userSwitchValue = false;
		}
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
		logger.info(" **** SWITCH Equals Test");
		
		// Same type?
		if(!(dataJPanel instanceof DataJPanelSwitch)){
			return false;
		}
		
		Object[][] data1 = this.getData();
		Object[][] data2 = dataJPanel.getData();
		
		// Dimension check
		if(data1.length != data2.length || data1[0].length != data2[0].length){
			return false;
		}
		
		// Raw data check
		if(this.isUserSwitchValue() != ((DataJPanelSwitch)dataJPanel).isUserSwitchValue()){
			logger.info(" **** SWITCH Data Different.");
			return false;
		}
		
		logger.info(" **** SWITCH Data Same.");
		// Pass all checks, everything ok
		return true;
	}
}
