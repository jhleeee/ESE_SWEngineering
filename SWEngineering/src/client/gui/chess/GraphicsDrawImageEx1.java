package client.gui.chess;

import java.awt.*;
import javax.swing.*;

public class GraphicsDrawImageEx1 extends JFrame
{
	Container contentPane;
	GraphicsDrawImageEx1()
	{
		setTitle("test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane=getContentPane();
		MyPanel panel=new MyPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		setSize(300, 400);
		setVisible(true);
	}
	class MyPanel extends JPanel
	{
		ImageIcon icon=new ImageIcon("GChess v1.0/king.jpg");
		Image img=icon.getImage();
		
		public void paintComponet(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(img, 20, 20, this);
		}
	}
	public static void main(String[] args)
	{
		new GraphicsDrawImageEx1();
	}
}

