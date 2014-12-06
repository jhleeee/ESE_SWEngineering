package client.gui;

import java.awt.*;
import java.awt.event.*; 

import javax.swing.*;


public class LoginView extends Frame implements ActionListener, Runnable
{
	 DTO dto = new DTO();//DTO 객체 생성
	 JTextField in_ID; //아이디
	 JPasswordField in_PW; //비밀번호
	 JButton login; //로그인 버튼
	 ImageIcon im;
	 String win = "0";
	 String lose = "0";
 
	 public LoginView(String str) 
	 {
		 
		  super(str);
		  init();
		 
		  this.setLocation(100, 100); //프레임 시작위치
		  super.setVisible(true); //실제로 프레임을 화면에 출력
		  super.setSize(300, 150); //프레임의 처음 크기
		  super.setResizable(true); //프레임 사이즈 조절
	 
	 }
 
	 public void init() 
	 {
		  JPanel p = new JPanel(); 
		  
		  JLabel id = new JLabel("아이디");
		  id.setBounds(10, 10, 80, 25);
		  
		  this.addWindowListener(new WindowAdapter() 
			 { 
				 // x를 눌렀을때 종료
				 public void windowClosing(WindowEvent e)
				 {
					 System.exit(0);
				 }
			 });
		  
		  JLabel password = new JLabel("비밀번호");
		  password.setBounds(10, 40, 80, 25);
		  
		  im = new ImageIcon("log.jpg");
		  login = new JButton("",im);
		  login.setBounds(100, 80, 80, 25);
		  login.addActionListener(this); //버튼이벤트 
		  
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
		 LoginView exam = new LoginView("로그인");
	 }
 
	 public void run() 
	 {
		 //스레드 정의부분
	 }
	 
	 public void actionPerformed(ActionEvent e) 
	 { 
		 //액션이벤트
		 Object obj = e.getSource();
 
		 //로그인 버튼 눌렀을 떄
		 if ((JButton) obj == login)
		 {
			 dto.setId(in_ID.getText()); //입력된 아이디를 가져와 dto에 저장
			 dto.setPassword(in_PW.getText());  //입력된 비밀번호를 가져와 dto에 저장
			 dto.setWin(win.toString());
			 dto.setLose(lose.toString());
			 try 
			 {
				 insertDAO.create(dto);  //dto를 DAO에 넘겨준다.
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