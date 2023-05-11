package logger.gui.impl.rawData;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RealDataJPanelTotal extends JPanel{
	
	private Log logger = LogFactory.getLog(getClass());
	

	public void addNewDataPanel(){
		this.setLayout(new BorderLayout());
		
		this.add(new JButton("Reset Data"), BorderLayout.NORTH);
		
		
		
		
		this.add(new JButton("Reset Data"), BorderLayout.NORTH);
		
		
	}
}
