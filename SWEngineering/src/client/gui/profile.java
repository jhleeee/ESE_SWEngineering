package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class profile extends JFrame
{
	
	public profile(String str)
	{
		super(str);
		
		JPanel p = new JPanel();
		
		this.addWindowListener(new WindowAdapter() 
		 { 
			 // x를 눌렀을때 종료
			 public void windowClosing(WindowEvent e)
			 {
				 System.exit(0);
			 }
		 });
		
		JLabel id = new JLabel("ID");
		id.setBounds(24,90,80,25);
		
		JTextField out_id = new JTextField(20);       // 아이디
		out_id.setBounds(100,90,160,25);	
		
		JLabel win = new JLabel("승");
		win.setBounds(24,130,80,25);
		
		JTextField out_win = new JTextField(20); // 승
		out_win.setBounds(100,130,160,25);
		
		JLabel lose = new JLabel("패");
		lose.setBounds(24,170,80,25);
		
		JTextField out_lose = new JTextField(20); // 패
		out_lose.setBounds(100,170,160,25);
		
		p.setLayout(null);
		p.add(id);
		p.add(win);
		p.add(lose);
		p.add(out_id);
		p.add(out_win);
		p.add(out_lose);
		add(p);
		
		this.setLocation(300, 300);
		this.setVisible(true);
		this.setSize(300,400);
		this.setResizable(true);
	}
	public static void main(String[] args) 
	{
		 profile exam1 = new profile("프로필");
	}

}