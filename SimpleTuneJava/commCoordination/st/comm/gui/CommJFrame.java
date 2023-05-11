package st.comm.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class CommJFrame extends JFrame{
	private final String TITLE = "Comm Manager";
	
	public CommJFrame(){
		this.setTitle(this.TITLE);
		this.setSize(500, 600);
		this.setLayout(new BorderLayout());
		this.add(new CommJPanel());
	}
}
