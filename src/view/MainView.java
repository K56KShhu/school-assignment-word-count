package view;


import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author zkyyo
 * @since 2018-09-08 17:30
 **/

public class MainView {
    public void draw(String[] strings) {
        JFrame f = new JFrame("统计");
        f.setSize(1000, 618);
        f.setLocation(200, 200);
        f.setLayout(null);
        int y = 50;
        for (String s : strings) {
            JLabel l = new JLabel(s);
            l.setBounds(100, y++, 560, 100);
            l.setFont(new java.awt.Font("Dialog", Font.PLAIN, 30));
            f.add(l);
        }
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);
    }
}
