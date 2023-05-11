/*
 * Created by JFormDesigner on Mon Feb 16 10:37:19 EST 2009
 */

package com.ecm.test;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Eric Morgan
 */
public class LogTest extends JPanel {
	public LogTest() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Eric Morgan
		panel3 = new JPanel();
		panel4 = new JPanel();
		statusJLabel = new JLabel();
		panel5 = new JPanel();
		calIDJLabel = new JLabel();
		panel6 = new JPanel();
		rateJLabel = new JLabel();
		panel1 = new JPanel();
		panel7 = new JPanel();
		connectJButton = new JToggleButton();
		logJButton = new JToggleButton();
		resetJButton = new JButton();
		loggerJMenuBar = new JMenuBar();
		commJMenu = new JMenu();
		testJMenuItem = new JMenuItem();
		splitPane1 = new JSplitPane();
		tabbedPane1 = new JTabbedPane();
		scrollPane2 = new JScrollPane();
		table3 = new JTable();
		scrollPane3 = new JScrollPane();
		table4 = new JTable();
		scrollPane1 = new JScrollPane();
		table2 = new JTable();
		scrollPane4 = new JScrollPane();
		table1 = new JTable();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new BorderLayout());

		//======== panel3 ========
		{
			panel3.setBorder(null);
			panel3.setLayout(new GridBagLayout());
			((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {194, 82, 80, 0};
			((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
			((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//======== panel4 ========
			{
				panel4.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				panel4.setLayout(new BorderLayout());

				//---- statusJLabel ----
				statusJLabel.setText("Status: ");
				panel4.add(statusJLabel, BorderLayout.CENTER);
			}
			panel3.add(panel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//======== panel5 ========
			{
				panel5.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				panel5.setLayout(new BorderLayout());

				//---- calIDJLabel ----
				calIDJLabel.setText("CAL ID:");
				panel5.add(calIDJLabel, BorderLayout.CENTER);
			}
			panel3.add(panel5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//======== panel6 ========
			{
				panel6.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				panel6.setLayout(new BorderLayout());

				//---- rateJLabel ----
				rateJLabel.setText("Rate:");
				panel6.add(rateJLabel, BorderLayout.CENTER);
			}
			panel3.add(panel6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		add(panel3, BorderLayout.SOUTH);

		//======== panel1 ========
		{
			panel1.setLayout(new BorderLayout());

			//======== panel7 ========
			{
				panel7.setLayout(new GridBagLayout());
				((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
				((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
				((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//---- connectJButton ----
				connectJButton.setText("Connect");
				panel7.add(connectJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- logJButton ----
				logJButton.setText("Log");
				panel7.add(logJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- resetJButton ----
				resetJButton.setText("Reset");
				panel7.add(resetJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			panel1.add(panel7, BorderLayout.CENTER);

			//======== loggerJMenuBar ========
			{

				//======== commJMenu ========
				{
					commJMenu.setText("Comm Ports");

					//---- testJMenuItem ----
					testJMenuItem.setText("Item 1");
					commJMenu.add(testJMenuItem);
				}
				loggerJMenuBar.add(commJMenu);
			}
			panel1.add(loggerJMenuBar, BorderLayout.NORTH);
		}
		add(panel1, BorderLayout.NORTH);

		//======== splitPane1 ========
		{

			//======== tabbedPane1 ========
			{

				//======== scrollPane2 ========
				{
					scrollPane2.setViewportView(table3);
				}
				tabbedPane1.addTab("Parameters", scrollPane2);


				//======== scrollPane3 ========
				{
					scrollPane3.setViewportView(table4);
				}
				tabbedPane1.addTab("Switches", scrollPane3);


				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(table2);
				}
				tabbedPane1.addTab("Sensors", scrollPane1);

			}
			splitPane1.setLeftComponent(tabbedPane1);

			//======== scrollPane4 ========
			{
				scrollPane4.setViewportView(table1);
			}
			splitPane1.setRightComponent(scrollPane4);
		}
		add(splitPane1, BorderLayout.CENTER);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Eric Morgan
	private JPanel panel3;
	private JPanel panel4;
	private JLabel statusJLabel;
	private JPanel panel5;
	private JLabel calIDJLabel;
	private JPanel panel6;
	private JLabel rateJLabel;
	private JPanel panel1;
	private JPanel panel7;
	private JToggleButton connectJButton;
	private JToggleButton logJButton;
	private JButton resetJButton;
	private JMenuBar loggerJMenuBar;
	private JMenu commJMenu;
	private JMenuItem testJMenuItem;
	private JSplitPane splitPane1;
	private JTabbedPane tabbedPane1;
	private JScrollPane scrollPane2;
	private JTable table3;
	private JScrollPane scrollPane3;
	private JTable table4;
	private JScrollPane scrollPane1;
	private JTable table2;
	private JScrollPane scrollPane4;
	private JTable table1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
