
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class HidePanelTest {
    JFrame frame;
    JPanel left, right;
    JSplitPane pane;
    int lastDividerLocation = -1;

    public static void main(String[] args) {
    	HidePanelTest demo = new HidePanelTest();
        demo.makeFrame();
        demo.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        demo.frame.show();
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

        JButton showleft = new JButton("Left");
        showleft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
        buttons.add(showleft);
        buttons.add(showright);
        buttons.add(showboth);
        frame.getContentPane().add(buttons, BorderLayout.NORTH);

        pane.setPreferredSize(new Dimension(400, 300));
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.pack();
        pane.setDividerLocation(0.5);
        return frame;
    }

}