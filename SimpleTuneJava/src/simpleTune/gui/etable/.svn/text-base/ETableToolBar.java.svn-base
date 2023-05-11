package simpleTune.gui.etable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.TableMetaData;
import simpleTune.gui.etable.dataJPanel.interfaces.DataJPanelInterface;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

public class ETableToolBar extends JToolBar implements MouseListener, ItemListener, ActionListener {
	private Log logger = LogFactory.getLog(getClass());
	private JButton incrementFine = new JButton(new ImageIcon("./graphics/frameImages/add.png"));
	private JButton decrementFine = new JButton(new ImageIcon("./graphics/frameImages/subtract.png"));
	private JButton incrementCoarse = new JButton(new ImageIcon("./graphics/frameImages/add.png"));
	private JButton decrementCoarse = new JButton(new ImageIcon("./graphics/frameImages/subtract.png"));
	//private JButton enable3d = new JButton(new ImageIcon("./graphics/frameImages/3d_render.png"));
	private JButton smooth = new JButton(new ImageIcon("./graphics/frameImages/smooth.png"));
	private JToggleButton scale = new JToggleButton(new ImageIcon("./graphics/frameImages/scale.png"));
	private JButton setValue = new JButton(new ImageIcon("./graphics/frameImages/equals.png"));
	private JButton multiply = new JButton(new ImageIcon("./graphics/frameImages/multiply.png"));
	private JButton undo = new JButton(new ImageIcon("./graphics/frameImages/undo.png"));
	private JButton redo = new JButton(new ImageIcon("./graphics/frameImages/redo.png"));
	private JFormattedTextField incrementByFine = new JFormattedTextField(new DecimalFormat("#.####"));
	private JFormattedTextField incrementByCoarse = new JFormattedTextField(new DecimalFormat("#.####"));
	private JFormattedTextField setValueText = new JFormattedTextField(new DecimalFormat("#.####"));
	private JComboBox scaleSelection = new JComboBox();
	private JCheckBox overlayLog = new JCheckBox("Overlay Log");
	private JButton clearOverlay = new JButton("Clear Overlay");
	private JLabel liveDataValue = new JLabel();

	//private ETable eTable;
	private  DataJPanelInterface dataJPanel;
	
	private boolean scaleToggleBoolean = false;
	
	public ETableToolBar(TableMetaData tableMetaData, DataJPanelInterface dataJPanel) {
		//this.eTable = eTable;
		this.dataJPanel = dataJPanel;
		
		this.setFloatable(false);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel finePanel = new JPanel();
		finePanel.add(incrementFine);
		finePanel.add(decrementFine);
		finePanel.add(incrementByFine);
		this.add(finePanel);

		JPanel coarsePanel = new JPanel();
		coarsePanel.add(incrementCoarse);
		coarsePanel.add(decrementCoarse);
		coarsePanel.add(incrementByCoarse);
		this.add(coarsePanel);

		JPanel setValuePanel = new JPanel();
		setValuePanel.add(setValue);
		setValuePanel.add(multiply);
		setValuePanel.add(setValueText);
		this.add(setValuePanel);

		// Only add the 3d button if table includes 3d data
		if (tableMetaData.getNodeType() == TableMetaData.DATA_3D) {
			//this.add(enable3d);
		}

		this.add(smooth);
		this.add(scale);
		this.add(redo);
		this.add(undo);
		
		// this.add(scaleSelection);
		/*
		if (table.isLiveDataSupported()) {
			JPanel liveDataPanel = new JPanel();
			liveDataPanel.add(overlayLog);
			liveDataPanel.add(clearOverlay);
			// liveDataPanel.add(liveDataValue);
			this.add(liveDataPanel);
		}
		*/
		incrementFine.setPreferredSize(new Dimension(22, 22));
		//incrementFine.setBorder(createLineBorder(new Color(150, 150, 150), 1));
		decrementFine.setPreferredSize(new Dimension(22, 22));
		//decrementFine.setBorder(createLineBorder(new Color(150, 150, 150), 1));
		incrementCoarse.setPreferredSize(new Dimension(22, 22));
		//incrementCoarse
		//		.setBorder(createLineBorder(new Color(150, 150, 150), 1));
		decrementCoarse.setPreferredSize(new Dimension(22, 22));
		//decrementCoarse
		//		.setBorder(createLineBorder(new Color(150, 150, 150), 1));
		//enable3d.setPreferredSize(new Dimension(33, 33));
		//enable3d.setBorder(createLineBorder(new Color(150, 150, 150), 1));
		smooth.setPreferredSize(new Dimension(22, 22));
		scale.setPreferredSize(new Dimension(22, 22));
		scale.setSelectedIcon(new ImageIcon("./graphics/frameImages/scale_on.png"));
		redo.setPreferredSize(new Dimension(22, 22));
		undo.setPreferredSize(new Dimension(22, 22));
		//smooth.setBorder(createLineBorder(new Color(150, 150, 150), 1));
		setValue.setPreferredSize(new Dimension(22, 22));
		//setValue.setBorder(createLineBorder(new Color(150, 150, 150), 1));
		multiply.setPreferredSize(new Dimension(22, 22));
		//multiply.setBorder(createLineBorder(new Color(150, 150, 150), 1));
		scaleSelection.setPreferredSize(new Dimension(22, 22));
		scaleSelection.setFont(new Font("Tahoma", Font.PLAIN, 11));
		clearOverlay.setPreferredSize(new Dimension(22, 22));
		//clearOverlay.setBorder(createLineBorder(new Color(150, 150, 150), 1));

		incrementByFine.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		incrementByFine.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
		incrementByFine.setPreferredSize(new Dimension(45, 23));
		incrementByCoarse.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		incrementByCoarse.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
		incrementByCoarse.setPreferredSize(new Dimension(45, 23));
		setValueText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		setValueText.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
		setValueText.setPreferredSize(new Dimension(45, 23));

		incrementFine.setToolTipText("Increment Value (Fine)");
		decrementFine.setToolTipText("Decrement Value (Fine)");
		incrementCoarse.setToolTipText("Increment Value (Coarse)");
		decrementCoarse.setToolTipText("Decrement Value (Coarse)");
		//enable3d.setToolTipText("Render data in 3d");
		smooth.setToolTipText("Smooth data");
		redo.setToolTipText("Redo Map Changes");
		undo.setToolTipText("Undo Map Changes");
		scale.setToolTipText("Toggle Scale Mode");
		setValue.setToolTipText("Set Absolute Value");
		setValueText.setToolTipText("Set Absolute Value");
		incrementByFine.setToolTipText("Fine Value Adjustment");
		incrementByCoarse.setToolTipText("Coarse Value Adjustment");
		multiply.setToolTipText("Multiply Value");
		overlayLog.setToolTipText("Enable Overlay Of Real Time Log Data");
		clearOverlay.setToolTipText("Clear Log Data Overlay Highlights");

		incrementFine.addMouseListener(this);
		decrementFine.addMouseListener(this);
		incrementCoarse.addMouseListener(this);
		decrementCoarse.addMouseListener(this);
		//enable3d.addMouseListener(this);
		smooth.addMouseListener(this);
		scale.addMouseListener(this);
		redo.addMouseListener(this);
		undo.addMouseListener(this);
		setValue.addMouseListener(this);
		multiply.addMouseListener(this);
		scaleSelection.addItemListener(this);
		overlayLog.addItemListener(this);
		clearOverlay.addActionListener(this);

	
		// TODO Implement further
		try {
			incrementByFine.setValue(Math.abs(0.1));
			incrementByCoarse.setValue(Math.abs(1.0));
			this.setValueText.setValue(Math.abs(1.0));
		} catch (Exception ex) {
			// scaling units haven't been added yet -- no problem
		}
		
		
		// key binding actions
		Action enterAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				//getTable().requestFocus();
				//setValue();
			}
		};

		// set input mapping
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);

		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

		im.put(enter, "enterAction");
		getActionMap().put(im.get(enter), enterAction);

		incrementFine.getInputMap().put(enter, "enterAction");
		decrementFine.getInputMap().put(enter, "enterAction");
		incrementCoarse.getInputMap().put(enter, "enterAction");
		decrementCoarse.getInputMap().put(enter, "enterAction");
		incrementByFine.getInputMap().put(enter, "enterAction");
		incrementByCoarse.getInputMap().put(enter, "enterAction");
		setValueText.getInputMap().put(enter, "enterAction");
		setValue.getInputMap().put(enter, "enterAction");
		incrementFine.getInputMap().put(enter, "enterAction");
		
		//setScales(table.getScales());
	}

	public void mouseClicked(MouseEvent e) {
		 	if (e.getSource() == incrementCoarse) {
	           //eTable.changeSelectedCells(Double.parseDouble(String.valueOf(incrementByCoarse.getValue())), ETable.INCREMENT);
		 		ETable currentETable = this.dataJPanel.getCurrentETableInScope();
		 		if(currentETable != null){
		 			currentETable.changeSelectedCells(Double.parseDouble(String.valueOf(incrementByCoarse.getValue())), ETable.INCREMENT);
		 		}
		 		
	        }
		 	
		 	else if (e.getSource() == decrementCoarse) {
	            //eTable.changeSelectedCells(Double.parseDouble(String.valueOf(incrementByCoarse.getValue())), ETable.DECREMENT);
		 		ETable currentETable = this.dataJPanel.getCurrentETableInScope();
		 		if(currentETable != null){
		 			currentETable.changeSelectedCells(Double.parseDouble(String.valueOf(incrementByCoarse.getValue())), ETable.DECREMENT);
		 		}
	        } 
		 	
		 	/*
		 	else if (e.getSource() == enable3d) {
	            // TODO Implement
	        	LOGGER.debug("Implement Enable 3d");
	        } 
		 	*/
		 	
		 	else if (e.getSource() == smooth) {
		 		//eTable.changeSelectedCells(0.0, ETable.SMOOTH);
		 		ETable currentETableInScope = this.dataJPanel.getCurrentETableInScope();
		 		if(currentETableInScope != null){
		 			currentETableInScope.changeSelectedCells(0.0, ETable.SMOOTH);
		 		}
	        } 
		 	
		 	else if (e.getSource() == undo) {
		 		logger.info("Undo action.");
		 		ETable currentETableInScope = this.dataJPanel.getCurrentETableInScope();
		 		currentETableInScope.savedStateUndo();
	        } 
		 	
		 	else if (e.getSource() == redo) {
		 		logger.info("Redo action.");
		 		ETable currentETableInScope = this.dataJPanel.getCurrentETableInScope();
		 		currentETableInScope.savedStateRedo();
	        } 
		 	
		 	else if (e.getSource() == scale) {
		 		// Keep a local copy of whether or not the toggle button is "on" or not.
		 		if(this.scaleToggleBoolean){
		 			this.scaleToggleBoolean = false;
		 		}else{
		 			this.scaleToggleBoolean = true;
		 		}
		 		
		 		// Notify parent data jpanel of scale value
		 		this.dataJPanel.setIsScaleActive(this.scaleToggleBoolean);
	        } 
		 	
		 	else if (e.getSource() == incrementFine) {
	            //eTable.changeSelectedCells(Double.parseDouble(String.valueOf(incrementByFine.getValue())), ETable.INCREMENT);
		 		ETable currentETableInScope = this.dataJPanel.getCurrentETableInScope();
		 		if(currentETableInScope != null){
		 			currentETableInScope.changeSelectedCells(Double.parseDouble(String.valueOf(incrementByFine.getValue())), ETable.INCREMENT);
		 		}
	        } 
		 	
		 	else if (e.getSource() == decrementFine) {
	            //eTable.changeSelectedCells(Double.parseDouble(String.valueOf(incrementByFine.getValue())), ETable.DECREMENT);
		 		ETable currentETableInScope = this.dataJPanel.getCurrentETableInScope();
		 		if(currentETableInScope != null){
		 			currentETableInScope.changeSelectedCells(Double.parseDouble(String.valueOf(incrementByFine.getValue())), ETable.DECREMENT);
		 		}
	        } 
		 	
		 	else if (e.getSource() == multiply) {
	            //eTable.changeSelectedCells(Double.parseDouble(setValueText.getText()), ETable.MULTIPLY);
		 		ETable currentETableInScope = this.dataJPanel.getCurrentETableInScope();
		 		if(currentETableInScope != null){
		 			currentETableInScope.changeSelectedCells(Double.parseDouble(setValueText.getText()), ETable.MULTIPLY);
		 		}
	        } 
		 	
		 	else if (e.getSource() == setValue) {
	            //eTable.changeSelectedCells(Double.parseDouble(setValueText.getText()), ETable.SET);
		 		ETable currentETableInScope = this.dataJPanel.getCurrentETableInScope();
		 		if(currentETableInScope != null){
		 			currentETableInScope.changeSelectedCells(Double.parseDouble(setValueText.getText()), ETable.SET);
		 		}
	        }

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
