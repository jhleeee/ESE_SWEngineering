package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class profile extends JFrame
{
	
	public profile()
	{
		JPanel p = new JPanel();
		JLabel id = new JLabel("아이디");
		id.setBounds(10,10,80,25);
		
		this.addWindowListener(new WindowAdapter() 
		 { 
			 // x를 눌렀을때 종료
			 public void windowClosing(WindowEvent e)
			 {
				 System.exit(0);
			 }
		 });
		
		this.setLocation(300, 300);
		this.setVisible(true);
		this.setSize(300,400);
		this.setResizable(true);
		
		JLabel password = new JLabel("비밀번호");
		password.setBounds(10,40,80,25);
		
		JTextField out_id = new JTextField(20);       // 아이디
		out_id.setBounds(100,10,160,25);
		
		JTextField out_password = new JTextField(20); // 패스워드
		out_password.setBounds(100,40,160,25);
		
		p.add(id);
		p.add(password);
		p.add(out_id);
		p.add(out_password);
		  		
	}
	public static void main(String[] args) 
	{
		 new profile();
	}

}
