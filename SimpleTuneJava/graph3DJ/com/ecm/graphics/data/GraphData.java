package com.ecm.graphics.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import com.ecm.graphics.render.flatFaceRender.Plane2d;
import com.ecm.graphics.tools.ColorTable;
import com.ecm.graphics.tools.FitData;

/**
 * Please note, general rule here is that datacells have their temp values modified to eventually
 * be what the main cell value should be, then temp values are applied. This allows for multiple
 * pass cell data editing. This also allows for future data cell rendering patterns to avoid mass
 * memory usage.
 * 
 * In short.
 * 1) Calc cells new value
 * 2) Apply value
 * 
 * @author botman
 *
 */
public class GraphData {

	// Data change types
	public static int NEW = 0;

	public static int REPLACE = 1;

	public static int INCRIMENT = 2;

	public static Vector GraphDataListeners = new Vector();

	private static GraphDataCell[][] graphData = null;

	private static int xWidth;

	private static int zDepth;

	private static double maxY;

	private static double minY;
	
	private static Vector scratchPad = new Vector();

	private static GraphDataCellCoordinate lastSelectedCellCoord = null;
	private static GraphDataCellCoordinate diagSelectOne = null;
	private static GraphDataCellCoordinate diagSelectTwo = null;
	private static GraphDataCellCoordinate diagSelectOld = null;
	
	/**
	 * Follows 3d convention, z vector is into monitor
	 * 
	 * @param x
	 * @param z
	 */
	public static void InitGraphData(Vector points, double aminY, double amaxY) {
		//Remove any old listeners
		GraphDataListeners = new Vector();
		
		
		//Remove old state information
		lastSelectedCellCoord = null;
		diagSelectOne = null;
		diagSelectTwo = null;
		diagSelectOld = null;
		
		//Store new min and max allowed value
		maxY = amaxY;
		minY = aminY;
		
		//Define xWidth, zDepth
		getDimensions(points);
		
		// Initialize the 2d storage array
		graphData = new GraphDataCell[xWidth][zDepth];

		//Populate the value array
		populateValueArray(points);
		
		
		// Initialize the color table since this is where we know the valid min
		// and max values
		ColorTable.initColorTable(minY, maxY);
		
		//Clear any old selected entities from scratch pad
		scratchPad.clear();
	}
	
	/**
	 * Return coordinates of single selected cell, otherwise return null
	 * 
	 * @return
	 */
	public static GraphDataCellCoordinate getSingleCount(){
		int counter = 0;
		GraphDataCell tempCell = null;
		
		int x = 0;
		int z = 0;
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				tempCell = graphData[j][i];
				if (tempCell.getSelected()) {
					counter++;
					x = tempCell.getX();
					z = tempCell.getZ();
				}
			}
		}
		
		if(counter == 1){
			return new GraphDataCellCoordinate(x, z);
		}
		
		return null;
	}
	
	/**
	 * Save selected items to the scratch pad
	 *
	 */
	public static void copySelectedToScratchPad(){
		
		
		//Clear scratch before each use
		scratchPad.clear();
		
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				GraphDataCell dataCell = graphData[j][i];
				if (dataCell.getSelected()) {
					scratchPad.add(dataCell);
				}
			}
		}
		
	}
	
	/**
	 * Paste selected items starting at upper left coord of passed x and z
	 * 
	 * @param x
	 * @param z
	 */
	public static void pasteAtCoord(){
		//Get single new cell that defines paste to upper left coordinate
		GraphDataCellCoordinate newULcoord = GraphData.getSingleCount();
		if(newULcoord == null){
			return;
		}
		
		//Get upper left coord of cell in scratch pad
		GraphDataCell copyGDC = getUpperLeftCoordCell();
		
		//Caculate the translation for each scratchpad cell
		int xDiff = newULcoord.getX() - copyGDC.getX();
		int zDiff = newULcoord.getZ() - copyGDC.getZ();
		
		
		Iterator iterate = scratchPad.iterator();
		while(iterate.hasNext()){
			GraphDataCell tempCell = (GraphDataCell)iterate.next();
			tempCell.setSelected(false);
			
			int tempx = tempCell.getX();
			int tempz = tempCell.getZ();
			
			int newx = tempx + xDiff;
			int newz = tempz + zDiff;
			
			if((newx < xWidth)&&(newx >= 0) && (newz >= 0) && (newz < zDepth)){
				graphData[newx][newz].setTempValue(tempCell.getValue());
				graphData[newx][newz].setSelected(true);
			}
		}
		
		
		applyDataCellChanges(GraphData.REPLACE);
	}


	/**
	 * Find minimum value in the data set
	 * 
	 * @return
	 */
	public static float getMinValue() {
		float minValue = 20.0f;
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				float tempValue = (graphData[j][i]).getValue();
				if (tempValue < minValue) {
					minValue = tempValue;
				}
			}
		}

		return minValue;
	}

	/**
	 * Return the max value in the dataset.
	 * 
	 * @return
	 */
	public static float getMaxValue() {
		float maxValue = 0.0f;
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				float tempValue = (graphData[j][i]).getValue();
				if (tempValue > maxValue) {
					maxValue = tempValue;
				}
			}
		}

		return maxValue;
	}

	/**
	 * Get value at the passed coordinates
	 * 
	 * @param x
	 * @param z
	 * @return
	 */
	public static float getCellValue(int x, int z) {
		return (graphData[x][z]).getValue();
	}

	/**
	 * Return 2D array of all listeners
	 * 
	 * @param x
	 * @param z
	 * @return
	 */
	public static Vector[][] getAllListeners(int x, int z) {
		Vector[][] listenerVectorsArray = new Vector[xWidth][zDepth];

		// Process row by row in z depth direction
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {

				Vector listeners = (graphData[x][z]).getListeners();
				listenerVectorsArray[j][i] = listeners;
			}
		}

		return listenerVectorsArray;
	}

	/**
	 * Add new data to a cell.
	 * 
	 * @param x
	 * @param z
	 * @param value
	 */
	public static void changeCellValue(float value, int type) {
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				GraphDataCell dataCell = graphData[j][i];
				if (dataCell.getSelected()) {
					// Update value
					dataCell.updateValue(value, type);

					Iterator graphDataIterators = GraphDataListeners.iterator();
					while (graphDataIterators.hasNext()) {
						GraphDataListener gdl = (GraphDataListener) graphDataIterators.next();
						gdl.newGraphData(dataCell.getX(), dataCell.getZ(), dataCell.getValue());
					}
				}
			}
		}

	}
	
	/**
	 * Set selected state of DataCell at x / z value
	 * 
	 * @param x
	 * @param z
	 * @param value
	 */
	public static void setSelected(int x, int z, boolean value) {
		lastSelectedCellCoord = new GraphDataCellCoordinate(x, z);
		
		(graphData[x][z]).setSelected(value);

		Iterator graphDataIterators = GraphDataListeners.iterator();
		while (graphDataIterators.hasNext()) {
			GraphDataListener gdl = (GraphDataListener) graphDataIterators
					.next();
			gdl.selectStateChange(x, z, (graphData[x][z]).getSelected());
		}
	}
	
	
	/**
	 * 
	 * 
	 * @param x
	 * @param z
	 */
	public static void flipSelectedValue(GraphDataCellCoordinate gcc){
		
		(graphData[gcc.getX()][gcc.getZ()]).flipSelected();
		
		if((graphData[gcc.getX()][gcc.getZ()]).getSelected()){
			lastSelectedCellCoord = new GraphDataCellCoordinate(gcc.getX(), gcc.getZ());
		}
		
		
		Iterator graphDataIterators = GraphDataListeners.iterator();
		while (graphDataIterators.hasNext()) {
			GraphDataListener gdl = (GraphDataListener) graphDataIterators
					.next();
			gdl.selectStateChange(gcc.getX(), gcc.getZ(), (graphData[gcc.getX()][gcc.getZ()]).getSelected());
		}
	}
	
	

	/**
	 * Undo recent value changes
	 *
	 */
	public static void undoValueChange() {
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				GraphDataCell dataCell = graphData[j][i];
				if (dataCell.getSelected()) {
					dataCell.setTempValue(dataCell.getOldValue());
					
					notifyListeners(dataCell.getX(), dataCell.getZ(), dataCell.getOldValue());
				}
			}
		}
		
		//Apply graphical changes
		applyDataCellChanges(GraphData.REPLACE);
	}

	/**
	 * Set selected value to true on all cells
	 *
	 */
	public static void selectAllCells(){
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				GraphDataCell dataCell = graphData[j][i];

				dataCell.setSelected(true);
			}
		}
	}
	
	/**
	 * Set selected value to false on all cells
	 *
	 */
	public static void deSelectAllCells(){
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				GraphDataCell dataCell = graphData[j][i];

				dataCell.setSelected(false);
			}
		}
	}

	/**
	 * Method delegates saving of smoothed X Data values
	 * 
	 */
	public static void smoothXData() {
		smoothDataXDirection();
		applyDataCellChanges(GraphData.REPLACE);
	}
	
	/**
	 * Method delegates saving of smoothed Z Data values
	 *
	 */
	public static void smoothZData() {
		smoothDataZDirection();
		applyDataCellChanges(GraphData.REPLACE);
	}
	

	/**
	 * Smooth in both directions
	 *
	 */
	public static void totalSmooth(){
		smoothDataXDirection();
		smoothDataZDirection();
		applyDataCellChanges(GraphData.REPLACE);
	}

	/**
	 * Sets a line of cells to selected false
	 * 
	 * @param coordOne
	 * @param coordTwo
	 */
	public static void setLineSelectedFalse(GraphDataCellCoordinate coordOne, GraphDataCellCoordinate coordTwo) {
		int xOne = coordOne.getX();
		int zOne = coordOne.getZ();
		
		int xTwo = coordTwo.getX();
		int zTwo = coordTwo.getZ();
		
		if (xOne == xTwo) {

			int zMin = Math.min(zOne, zTwo);
			int zMax = Math.max(zOne, zTwo);

			for (int i = zMin; i < (zMax + 1); i++) {
				GraphData.setSelected(xOne, i, false);
			}
		}
		if (zOne == zTwo) {
			int xMin = Math.min(xOne, xTwo);
			int xMax = Math.max(xOne, xTwo);

			for (int i = xMin; i < (xMax + 1); i++) {
				GraphData.setSelected(i, zOne, false);
			}
		}
	}
	
	/**
	 * Returns the number of selected items
	 * 
	 * @return
	 */
	public static int getSelectedCount(){

		int counter = 0;
		
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				GraphDataCell dataCell = graphData[j][i];
				if (dataCell.getSelected()) {
					counter++;
				}
			}
		}
		
		return counter;
	}
	
	/**
	 * Draws a square based on selected corners
	 * 
	 * @param diagSelectTwo
	 */
	public static void drawRectDiagonalSelection(){
		
		//Set old area to selected false
		drawRectDiagWithState(false, GraphData.getDiagSelectOne(), GraphData.getDiagSelectOld());

		//Set new area to selected true
		drawRectDiagWithState(true, GraphData.getDiagSelectOne(), GraphData.getDiagSelectTwo());
	}
	
	/**
	 * Helper method for above. Sets diagonally bounded area to not selected.
	 * 
	 * @param value
	 * @param one
	 * @param two
	 */
	private static void drawRectDiagWithState(boolean value, GraphDataCellCoordinate one, GraphDataCellCoordinate two) {
		//Ensure that there is an initially selected corner for the selected area
		if(one != null && two != null){
			int xOne = one.getX();
			int zOne = one.getZ();
			
			
			int xTwo = two.getX();
			int zTwo = two.getZ();
			
			//Recalc diagonal values for easier processing
			int tempX = Math.min(xOne, xTwo);
			int tempZ = Math.min(zOne, zTwo);
			int xWidth = Math.abs(xTwo - xOne);
			int zHeight = Math.abs(zTwo - zOne);

			// Add selected items based on diagonal coordinates
			if (xOne == xTwo && zOne == zTwo) {
			} else {
				for (int i = tempZ; i < (tempZ + zHeight + 1); i++) {
					for (int j = tempX; j < (tempX + xWidth + 1); j++) {
						GraphData.setSelected(j, i, value);
					}
				}
			}
		}
	}
	
	// ***********************************************
	// Private helper methods
	// ***********************************************

	/**
	 * Populate the values array
	 * 
	 * @param points
	 * @return
	 */
	private static void populateValueArray(Vector points) {
		Iterator pointIterate = points.iterator();
		int counter = 0;
		while (pointIterate.hasNext()) {
			float[] line = (float[]) pointIterate.next();

			for (int i = 0; i < line.length; i++) {
				graphData[i][counter] = new GraphDataCell(line[i], i, counter);
			}

			counter++;
		}
	}

	/**
	 * Method sets up and tests dimensions of data passed in.
	 * 
	 * @param points
	 */
	private static void getDimensions(Vector points) {
		Iterator pointsIterator = points.iterator();
		int iterateCounter = 0;
		while (pointsIterator.hasNext()) {

			iterateCounter++;

			float[] line = (float[]) pointsIterator.next();

			if (line == null || line.length == 0) {
				System.err.println("Points table lacks data.");
				System.exit(0);
			}

			int lineLength = line.length;

			if (iterateCounter > 1) {
				if (lineLength != xWidth) {
					System.err.println("Dimensionality does not hold, lines of unequal length data.");
					System.exit(0);
				}
			}
			xWidth = lineLength;

			zDepth = iterateCounter;
		}
	}
	
	
	
	private static double getPointDistance(GraphDataCellCoordinate a, GraphDataCellCoordinate b) {
		return Math.sqrt(Math.pow(Math.abs(a.getX() - b.getX()), 2.0) + Math.pow(Math.abs(a.getZ() - b.getZ()), 2.0));
	}
	
	
	/**
	 * Used for copy and paste function
	 * 
	 */
	private static GraphDataCell getUpperLeftCoordCell(){
		//Upper left is min x and max z value
		int x = xWidth;
		int z = 0;
		
		Iterator scratchIterate = scratchPad.iterator();
		while(scratchIterate.hasNext()){
			GraphDataCell tempData = (GraphDataCell)scratchIterate.next();
			
			if(tempData.getX()<x){
				x = tempData.getX();
			}
			
			if(tempData.getZ() > z){
				z = tempData.getZ();
			}
		}
		
		return graphData[x][z];
	}
	
	/**
	 * Method notifies listeners of changes to passed cell
	 * 
	 */
	//private static void notifyListeners(GraphDataCell dataCell) {
	private static void notifyListeners(int x, int z, float value) {
		// Update the listeners
		Iterator graphDataIterators = GraphDataListeners.iterator();
		while (graphDataIterators.hasNext()) {
			GraphDataListener gdl = (GraphDataListener) graphDataIterators.next();
			gdl.newGraphData(x, z,value);
		}
	}
	
	
	
	/**
	 * Helper method to run through all selected cells and apply changes made
	 * 
	 * @param type
	 */
	private static void applyDataCellChanges(int type) {
		for (int i = 0; i < zDepth; i++) {
			for (int j = 0; j < xWidth; j++) {
				GraphDataCell dataCell = graphData[j][i];

				// Cell must be selected
				if (dataCell.getSelected()) {
					dataCell.applyTempValue(type);
				}
			}
		}
	}
	
	
	/**
	 * Method smooths data in x direction where possible
	 * 
	 */
	private static void smoothDataXDirection() {
		// Gather cells in the X direction
		for (int i = 0; i < zDepth; i++) {

			LinkedList smoothXCells = new LinkedList();
			for (int j = 0; j < xWidth; j++) {
				GraphDataCell dataCell = graphData[j][i];

				// Cell must be selected
				if (dataCell.getSelected()) {
					if (smoothXCells.isEmpty()) {
						smoothXCells.add(dataCell);
					} else {
						GraphDataCell lastCell = (GraphDataCell) smoothXCells.getLast();

						// Is previous cell part of chain?
						if (lastCell.getX() == (dataCell.getX() - 1)) {
							smoothXCells.add(dataCell);
						} else {
							// If chain has more than 2 elements then smooth
							// Then remove from chain
							if (smoothXCells.size() > 2) {
								smoothListDataX(smoothXCells);
							}
							// Else remove elements from chain
							else {
								smoothXCells.clear();
							}
						}

					}
				}

				// Final processing if at end of loop
				if (j == (xWidth - 1)) {
					if (smoothXCells.size() > 2) {
						smoothListDataX(smoothXCells);
					}
					smoothXCells.clear();
				}

			}
		}
	}

	/**
	 * Smooth data in the Z direction
	 * 
	 */
	public static void smoothDataZDirection() {
		// Gather cells in the X direction
		for (int i = 0; i < xWidth; i++) {

			LinkedList smoothYCells = new LinkedList();
			for (int j = 0; j < zDepth; j++) {
				GraphDataCell dataCell = graphData[i][j];

				// Cell must be selected
				if (dataCell.getSelected()) {
					if (smoothYCells.isEmpty()) {
						smoothYCells.add(dataCell);
					} else {
						GraphDataCell lastCell = (GraphDataCell) smoothYCells
								.getLast();

						// Is previous cell part of chain?
						if (lastCell.getZ() == (dataCell.getZ() - 1)) {
							smoothYCells.add(dataCell);
						} else {
							// If chain has more than 2 elements then smooth
							// Then remove from chain
							if (smoothYCells.size() > 2) {
								smoothListDataY(smoothYCells);
							}
							// Else remove elements from chain
							else {
								smoothYCells.clear();
							}
						}

					}
				}

				// Final processing if at end of loop
				if (j == (zDepth - 1)) {
					if (smoothYCells.size() > 2) {
						smoothListDataY(smoothYCells);
					}
					smoothYCells.clear();
				}

			}
		}
	}

	/**
	 * Actually smooth the values
	 * 
	 * @param smoothXCells
	 */
	private static void smoothListDataY(LinkedList smoothXCells) {
		Iterator smoothIterate = smoothXCells.iterator();
		double[] xVals = new double[smoothXCells.size()];
		double[] yVals = new double[smoothXCells.size()];
		int counter = 0;
		while (smoothIterate.hasNext()) {
			GraphDataCell tempCell = (GraphDataCell) smoothIterate.next();
			xVals[counter] = tempCell.getZ();
			yVals[counter] = tempCell.getTempValue();
			counter++;
		}

		FitData.init(xVals, yVals);
		smoothIterate = smoothXCells.iterator();
		while (smoothIterate.hasNext()) {
			GraphDataCell tempCell = (GraphDataCell) smoothIterate.next();
			double xValue = tempCell.getZ();
			double smoothYValue = FitData.getSmoothYValue(xValue);
			tempCell.setTempValue((float) smoothYValue);

			// Update the listeners
			Iterator graphDataIterators = GraphDataListeners.iterator();
			while (graphDataIterators.hasNext()) {
				GraphDataListener gdl = (GraphDataListener) graphDataIterators.next();
				gdl.newGraphData(tempCell.getX(), tempCell.getZ(),(float) smoothYValue);
			}
		}

		// Done processing, clear selected cells
		smoothXCells.clear();
	}

	/**
	 * Actually smooth the values
	 * 
	 * @param smoothXCells
	 */
	private static void smoothListDataX(LinkedList smoothXCells) {
		Iterator smoothIterate = smoothXCells.iterator();
		double[] xVals = new double[smoothXCells.size()];
		double[] yVals = new double[smoothXCells.size()];
		int counter = 0;
		while (smoothIterate.hasNext()) {
			GraphDataCell tempCell = (GraphDataCell) smoothIterate.next();
			xVals[counter] = tempCell.getX();
			yVals[counter] = tempCell.getTempValue();
			counter++;
		}

		FitData.init(xVals, yVals);
		smoothIterate = smoothXCells.iterator();
		while (smoothIterate.hasNext()) {
			GraphDataCell tempCell = (GraphDataCell) smoothIterate.next();
			double xValue = tempCell.getX();
			double smoothYValue = FitData.getSmoothYValue(xValue);
			tempCell.setTempValue((float) smoothYValue);

			// Update the listeners
			Iterator graphDataIterators = GraphDataListeners.iterator();
			while (graphDataIterators.hasNext()) {
				GraphDataListener gdl = (GraphDataListener) graphDataIterators
						.next();
				gdl.newGraphData(tempCell.getX(), tempCell.getZ(),
						(float) smoothYValue);
			}
		}

		// Done processing, clear selected cells
		smoothXCells.clear();
	}

	public static double getMaxY() {
		return maxY;
	}

	public static double getMinY() {
		return minY;
	}
	
	public static double getHeight() {
		return Math.abs(maxY) + Math.abs(minY);
	}
	
	public static void addGraphDataListener(GraphDataListener gdl) {
		GraphDataListeners.add(gdl);
	}
	
	public static void addGraphDataCellListener(int x, int z,
			GraphDataCellListener listener) {
		(graphData[x][z]).addDataListener(listener);
	}
	
	public static GraphDataCellCoordinate getLastSelectedCoord(){
		return lastSelectedCellCoord;
	}

	public static int getXWidth() {
		return xWidth;
	}

	public static int getZDepth() {
		return zDepth;
	}
	
	public static GraphDataCellCoordinate getDiagSelectOne(){
		return diagSelectOne;
	}
	
	public static GraphDataCellCoordinate getDiagSelectTwo(){
		return diagSelectTwo;
	}
	
	public static void setDiagSelectOne(GraphDataCellCoordinate one){
		if(diagSelectOne == null){
			diagSelectOld = one;
			diagSelectTwo = one;
		}
		diagSelectOne = one;
	}
	
	public static void setDiagSelectTwo(GraphDataCellCoordinate passedCoord){
		if(passedCoord == diagSelectTwo){
			return;
		}
		
		diagSelectOld = diagSelectTwo;
		
		if(diagSelectTwo == null){
			diagSelectOld = passedCoord;
		}
		
		if(passedCoord == null){
			diagSelectOld = null;
		}
		diagSelectTwo = passedCoord;
	}

	public static GraphDataCellCoordinate getDiagSelectOld() {
		return diagSelectOld;
	}

}
