package view;


import service.Solution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * @author zkyyo
 * @since 2018-09-08 17:30
 **/

public class MainView {

    private List<String> count;
    private Solution s;

    public MainView(Solution s) {
        List<String> count = new ArrayList<>();
        count.add("字符数: " + s.getTotalCharacters());
        count.add("单词数: " + s.getTotalWords());
        count.add("行数: " + s.getTotalLines());
        count.add("");
        count.add("空行数: " + s.getBlankLine());
        count.add("注释行数: " + s.getCommentLine());
        count.add("代码行数: " + s.getCodeLine());
        this.count = count;
        this.s = s;
    }

    public void draw() {
        JFrame f = new JFrame("统计");
        f.setSize(1000, 618);
        f.setLocation(200, 200);
        f.setLayout(new FlowLayout());


        int y = 25;
        for (String s : count) {
            JLabel l = new JLabel(s);
            l.setBounds(100, y += 50, 560, 100);
            l.setFont(new java.awt.Font("Dialog", Font.PLAIN, 30));
            f.add(l);
        }

        JTextArea ta = new JTextArea();
        ta.setPreferredSize(new Dimension(800, 450));
        ta.setText(s.getSb().toString());
        //设置自动换行
        ta.setLineWrap(true);
        f.add(ta);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
