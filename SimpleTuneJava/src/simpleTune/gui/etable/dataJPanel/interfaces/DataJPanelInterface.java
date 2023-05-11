package simpleTune.gui.etable.dataJPanel.interfaces;

import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import simpleTune.gui.etable.ETable;

public interface DataJPanelInterface {
	public JToolBar getToolBar();
	
	public JMenuBar getMenuBar();
	
	public boolean dataChanged();
	
	public boolean mainDataTableChange();
	
	public boolean xAxisDataTableChange();
	
	public boolean yAxisDataTableChange();
	
	public void copySelectedTableData();
	
	public void copyEntireTable();
	
	public void pasteTableData();
	
	public void setClosed(boolean value);
	
	public void revertDataState();
	
	public void saveDataState();
	
	public void replaceMainData(Object[][] newData);
	
	public void replaceXAxisData(Object[][] newData);
	
	public void replaceYAxisData(Object[][] newData);
	
	public Dimension getFrameSize();
	
	public Object[][] getData();
	
	public Object[][] getXAXisData();
	
	public Object[][] getYAxisData();
	
	// If datajpanel uses multiple ETables, return whichever is in scope
	public ETable getCurrentETableInScope();
	
	// 
	public ETable getMainETable();
	
	public void setCurrentETableInScope(ETable eTable);
	
	public void setIsScaleActive(boolean isActive);
	
	public boolean getIsScaleActive();
	
	public boolean equals(DataJPanelInterface dataJPanel);
	
	public void refresh();
}
