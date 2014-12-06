package client.gui;

import java.awt.*;
import java.awt.event.*; 

import javax.swing.*;


public class LoginView extends Frame implements ActionListener, Runnable
{
	 DTO dto = new DTO();//DTO ��ü ����
	 JTextField in_ID; //���̵�
	 JPasswordField in_PW; //��й�ȣ
	 JButton login; //�α��� ��ư
	 ImageIcon im;
	 String win = "0";
	 String lose = "0";
 
	 public LoginView(String str) 
	 {
		 
		  super(str);
		  init();
		 
		  this.setLocation(100, 100); //������ ������ġ
		  super.setVisible(true); //������ �������� ȭ�鿡 ���
		  super.setSize(300, 150); //�������� ó�� ũ��
		  super.setResizable(true); //������ ������ ����
	 
	 }
 
	 public void init() 
	 {
		  JPanel p = new JPanel(); 
		  
		  JLabel id = new JLabel("���̵�");
		  id.setBounds(10, 10, 80, 25);
		  
		  this.addWindowListener(new WindowAdapter() 
			 { 
				 // x�� �������� ����
				 public void windowClosing(WindowEvent e)
				 {
					 System.exit(0);
				 }
			 });
		  
		  JLabel password = new JLabel("��й�ȣ");
		  password.setBounds(10, 40, 80, 25);
		  
		  im = new ImageIcon("log.jpg");
		  login = new JButton("",im);
		  login.setBounds(100, 80, 80, 25);
		  login.addActionListener(this); //��ư�̺�Ʈ 
		  
		  in_ID = new JTextField(20);
		  in_ID.setBounds(100, 10, 160, 25);
		  
		  in_PW = new JPasswordField(20);
		  in_PW.setBounds(100, 40, 160, 25);
		  
		  p.setLayout(null);
		  p.add(id);
		  p.add(password);
		  p.add(login);
		  p.add(in_PW);
		  p.add(in_ID);
		  
		  add(p);
		  
	 }

	 public static void main(String[] args) 
	 {
		 LoginView exam = new LoginView("�α���");
	 }
 
	 public void run() 
	 {
		 //������ ���Ǻκ�
	 }
	 
	 public void actionPerformed(ActionEvent e) 
	 { 
		 //�׼��̺�Ʈ
		 Object obj = e.getSource();
 
		 //�α��� ��ư ������ ��
		 if ((JButton) obj == login)
		 {
			 dto.setId(in_ID.getText()); //�Էµ� ���̵� ������ dto�� ����
			 dto.setPassword(in_PW.getText());  //�Էµ� ��й�ȣ�� ������ dto�� ����
			 dto.setWin(win.toString());
			 dto.setLose(lose.toString());
			 try 
			 {
				 insertDAO.create(dto);  //dto�� DAO�� �Ѱ��ش�.
				 DataComfirm.create(dto);
			 } 
			 catch (Exception e1) 
			 {
				 // TODO Auto-generated catch block
				 e1.printStackTrace();
			 } 

		 }
	 }
}