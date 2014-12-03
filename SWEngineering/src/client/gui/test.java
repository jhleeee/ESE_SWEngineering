package client.gui;



import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class test {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        for (int i = 0; i < 10; i++) {
            panel.add(new JButton("Hello-" + i));
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(50, 30, 300, 50);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 400));
        contentPane.add(scrollPane);
        frame.setContentPane(contentPane);
        
        JButton btnNewButton = new JButton("New button");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton.setBackground(new Color(153, 153, 204));
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setOpaque( true );
        btnNewButton.setBounds(156, 224, 97, 23);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("New button");
        btnNewButton_1.setBackground(new Color(204, 102, 102));
        btnNewButton_1.setContentAreaFilled(false);
        btnNewButton_1.setOpaque( true );
        btnNewButton_1.setBounds(118, 301, 97, 23);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("New button");
        btnNewButton_2.setBackground(new Color(153, 204, 153));
        btnNewButton_2.setBounds(42, 136, 97, 23);
        btnNewButton_2.setContentAreaFilled(false);
        btnNewButton_2.setOpaque( true );
        contentPane.add(btnNewButton_2);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}