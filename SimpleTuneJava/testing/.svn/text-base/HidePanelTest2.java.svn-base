
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HidePanelTest2 {
    JFrame frame;
    JPanel left, right;
    JSplitPane pane;
    int lastDividerLocation = -1;

    int dimensionValue = 300;
    
    public static void main(String[] args) {
    	HidePanelTest2 demo = new HidePanelTest2();
        demo.makeFrame();
        demo.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        demo.frame.show();
    }

    public void toggleFrameSize(){
    	System.out.println("Toggle Value");
    	
    	if(frame.getWidth() == dimensionValue){
    		frame.setSize(dimensionValue*2, dimensionValue);
    	}else{
    		frame.setSize(dimensionValue, dimensionValue);
    	}
    }

    public JFrame makeFrame() {
        frame = new JFrame();
        
        // Create a horizontal split pane.
        pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        left = new JPanel();
        left.setBackground(Color.red);
        pane.setLeftComponent(left);
        right = new JPanel();
        right.setBackground(Color.green);
        pane.setRightComponent(right);

        JButton test = new JButton("Toggle");
        test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//frame.setSize(dimensionValue*2, dimensionValue);
            	
                Container c = frame.getContentPane();
                c.remove(pane);
                c.remove(left);
                c.remove(right);
                pane.setLeftComponent(left);
                pane.setRightComponent(right);
                c.add(pane, BorderLayout.CENTER);
                lastDividerLocation = dimensionValue;
                pane.setDividerLocation(dimensionValue);
                c.validate();
                c.repaint();
                
                
            }
        });
        JButton showleft = new JButton("Left");
        showleft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//frame.setSize(dimensionValue, dimensionValue);
                Container c = frame.getContentPane();
                if (pane.isShowing()) {
                    lastDividerLocation = pane.getDividerLocation();
                }
                c.remove(pane);
                c.remove(left);
                c.remove(right);
                c.add(left, BorderLayout.CENTER);
                c.validate();
                c.repaint();
                
            }
        });
        JButton showright = new JButton("Right");
        showright.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//frame.setSize(dimensionValue, dimensionValue);
                Container c = frame.getContentPane();
                if (pane.isShowing()) {
                    lastDividerLocation = pane.getDividerLocation();
                }
                c.remove(pane);
                c.remove(left);
                c.remove(right);
                c.add(right, BorderLayout.CENTER);
                c.validate();
                c.repaint();
            }
        });
        JButton showboth = new JButton("Both");
        showboth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//frame.setSize(dimensionValue, dimensionValue);
                Container c = frame.getContentPane();
                c.remove(pane);
                c.remove(left);
                c.remove(right);
                pane.setLeftComponent(left);
                pane.setRightComponent(right);
                c.add(pane, BorderLayout.CENTER);
                if (lastDividerLocation >= 0) {
                    pane.setDividerLocation(lastDividerLocation);
                }
                c.validate();
                c.repaint();
            }
        });

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridBagLayout());
        buttons.add(test);
        buttons.add(showleft);
        buttons.add(showright);
        buttons.add(showboth);
        frame.getContentPane().add(buttons, BorderLayout.NORTH);

        pane.setPreferredSize(new Dimension(dimensionValue, dimensionValue));
        frame.setSize(dimensionValue, dimensionValue);
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.pack();
        pane.setDividerLocation(0.5);
        
        return frame;
    }

}