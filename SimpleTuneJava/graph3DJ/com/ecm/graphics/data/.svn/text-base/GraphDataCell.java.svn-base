package com.ecm.graphics.data;

import java.util.Iterator;
import java.util.Vector;

public class GraphDataCell {
	private float theValue;

	private Vector graphDataListeners = new Vector();

	private boolean isSelected = false;

	private float oldValue;

	private int x;

	private int z;

	private float tempValue;

	/**
	 * Constructor
	 * 
	 * @param value
	 * @param x
	 * @param z
	 */
	public GraphDataCell(float value, int x, int z) {
		this.x = x;
		this.z = z;

		if (value < GraphData.getMinY()) {
			value = (float) GraphData.getMinY();
		}

		if (value > GraphData.getMaxY()) {
			value = (float) GraphData.getMaxY();
		}

		this.theValue = value;
		this.oldValue = value;
		this.tempValue = value;
	}


	/**
	 * Changes the value of this data cell
	 * 
	 * @param value
	 * @param type
	 */
	public void updateValue(float value, int type) {

		// Do nothing if same value is applied multiple times
		if (this.theValue == value) {
			return;
		}

		if (value < GraphData.getMinY()) {
			value = (float) GraphData.getMinY();
		}

		if (value > GraphData.getMaxY()) {
			value = (float) GraphData.getMaxY();
		}

		this.oldValue = this.theValue;

		if (type == GraphData.REPLACE) {
			this.theValue = value;
		}
		if (type == GraphData.INCRIMENT) {
			this.theValue += value;
		}

		this.tempValue = value;
		notifyListeners(type);
	}

	/**
	 * Call out to listeners because of state change
	 * 
	 * @param type
	 */
	private void notifyListeners(int type) {

		Iterator listenerIterate = graphDataListeners.iterator();
		while (listenerIterate.hasNext()) {
			GraphDataCellListener listener = (GraphDataCellListener) listenerIterate
					.next();
			listener.graphDataChanged(this.theValue, 1, type);
		}

	}

	/**
	 * Set selected state
	 * 
	 * @param value
	 */
	public void setSelected(boolean value) {
		this.isSelected = value;

		Iterator listenerIterate = graphDataListeners.iterator();
		while (listenerIterate.hasNext()) {
			GraphDataCellListener listener = (GraphDataCellListener) listenerIterate.next();
			listener.selectStateChanged(value);
		}
	}
	
	/**
	 * Swap selected state, uses above method
	 *
	 */
	public void flipSelected(){
		if(this.isSelected == true){
			this.setSelected(false);
		}else{
			this.setSelected(true);
		}
	}

	/**
	 * Takes temp value and applies it to the cell
	 * 
	 * @param type
	 */
	public void applyTempValue(int type) {
		this.updateValue(this.tempValue, type);
	}

	// *******************************
	// Misc helper getters and setters
	// *******************************
	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public float getOldValue() {
		return oldValue;
	}
	
	public float getTempValue() {
		return tempValue;
	}

	public void setTempValue(float tempValue) {
		this.tempValue = tempValue;
	}

	public GraphDataCell(GraphDataCellListener listener) {
		this.graphDataListeners.add(listener);
	}

	public void addDataListener(GraphDataCellListener listener) {
		this.graphDataListeners.add(listener);
	}
	
	public Vector getListeners() {
		return this.graphDataListeners;
	}

	public boolean getSelected() {
		return this.isSelected;
	}
	
	public float getValue() {
		return this.theValue;
	}

}
