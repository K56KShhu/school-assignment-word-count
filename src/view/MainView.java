package view;


import service.WordCount;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.*;

/**
 * @author zkyyo
 * @since 2018-09-08 17:30
 **/

public class MainView {

    private List<String> count;
    private StringBuilder sb;

    public MainView(List<String> count, StringBuilder sb) {
        this.count = count;
        this.sb = sb;
    }

    public void draw() {
        JFrame f = new JFrame("统计");
        f.setSize(1100, 618);
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
        ta.setText(sb.toString());
        ta.setLineWrap(true);
        f.add(ta);

        final JFileChooser fc = new JFileChooser();
        JButton bOpen = new JButton("打开文件");
        f.add(bOpen);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        bOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(f);
                File file = fc.getSelectedFile();
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    WordCount wc = new WordCount();
                    try {
                        wc.solution(new File(file.getAbsolutePath()));
                        wc.getCountResult().add("字符数: " + wc.getTotalCharacters());
                        wc.getCountResult().add("单词数: " + wc.getTotalWords());
                        wc.getCountResult().add("行数: " + wc.getTotalLines());
                        wc.getCountResult().add("");
                        wc.getCountResult().add("空行数: " + wc.getTotalLines());
                        wc.getCountResult().add("代码行: " + wc.getCodeLine());
                        wc.getCountResult().add("注释行数: " + wc.getCommentLine());
                        new MainView(wc.getCountResult(), wc.getText()).draw();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
