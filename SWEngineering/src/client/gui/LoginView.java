import javax.swing.*;

public class LoginView {

	public static void main(String[] args) {
		JFrame frame = new JFrame("�α���");
		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);

		frame.setVisible(true);
	}

	private static void placeComponents(JPanel panel) 
	{
		panel.setLayout(null);

		JLabel IDLabel = new JLabel("���̵�");
		IDLabel.setBounds(10, 10, 80, 25);
		panel.add(IDLabel);

		JTextField IDText = new JTextField(20);
		IDText.setBounds(100, 10, 160, 25);
		panel.add(IDText);
		
		JLabel PasswordLabel = new JLabel("�н�����");
		PasswordLabel.setBounds(10, 40, 80, 25);
		panel.add(PasswordLabel);

		JPasswordField PasswordText = new JPasswordField(20);
		PasswordText.setBounds(100, 40, 160, 25);
		panel.add(PasswordText);

		JButton LoginButton = new JButton("�α���");
		LoginButton.setBounds(10, 80, 80, 25);
		panel.add(LoginButton);
		
		JButton CancleButton = new JButton("���");
		CancleButton.setBounds(180, 80, 80, 25);
		panel.add(CancleButton);
	}
}
