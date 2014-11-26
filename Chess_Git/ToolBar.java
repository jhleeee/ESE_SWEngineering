
import java.awt.*;

import javax.swing.*;

public class ToolBar extends JPanel
{
	private static final long serialVersionUID = 956862821780246300L;

	public ToolBar(GUI listener, Dimension size)
	{
		setPreferredSize(size);
		setOpaque(true);
		setBackground(new Color(51,102,255));
		
		JPanel title = new JPanel();
		title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		title.setLayout(new BorderLayout());
		title.setPreferredSize(new Dimension(size.width-5, 60));
		title.setOpaque(true);
		title.setBackground(new Color(46,184,0));
		
		JLabel title1 = new JLabel("TOOL", JLabel.CENTER);
		title1.setPreferredSize(new Dimension(size.width-5, 30));
		title1.setFont(new Font("Courier", title1.getFont().getStyle(), 20));
		title1.setOpaque(true);
		title1.setBackground(Color.BLACK);
		
		JLabel title2 = new JLabel("BAR", JLabel.CENTER);
		title2.setFont(new Font("Courier", title2.getFont().getStyle(), 20));
		title2.setPreferredSize(new Dimension(size.width-5, 30));
		title.add(title1, BorderLayout.NORTH);
		title.add(title2, BorderLayout.SOUTH);
		
		JButton flip = new JButton("FLIP");
		flip.setPreferredSize(new Dimension(size.width-5, 60));
		flip.setBorder(BorderFactory.createRaisedBevelBorder());
		flip.setBackground(Color.WHITE);
		flip.setToolTipText("Turn Board 180 Degrees");
		
		JButton undo = new JButton("UNDO");
		undo.setPreferredSize(new Dimension(size.width-5, 60));
		undo.setBorder(BorderFactory.createRaisedBevelBorder());
		undo.setBackground(Color.WHITE);
		undo.setToolTipText("Undo Move");
		
		JButton highlight = new JButton("HIGHLIGHT");
		highlight.setPreferredSize(new Dimension(size.width-5, 60));
		highlight.setBorder(BorderFactory.createRaisedBevelBorder());
		highlight.setBackground(Color.WHITE);
		highlight.setToolTipText("Toggle Move Highlighting");
		
		JButton newGameButton = new JButton("NEW");
		newGameButton.setPreferredSize(new Dimension(size.width-5, 60));
		newGameButton.setBorder(BorderFactory.createRaisedBevelBorder());
		newGameButton.setBackground(Color.WHITE);
		newGameButton.setToolTipText("New Game");
		
		JButton exitButton = new JButton("EXIT");
		exitButton.setPreferredSize(new Dimension(size.width-5, 60));
		exitButton.setBorder(BorderFactory.createRaisedBevelBorder());
		exitButton.setBackground(Color.WHITE);
		exitButton.setToolTipText("Exit G Chess");
		
		flip.setActionCommand("-20");
		undo.setActionCommand("-21");
		highlight.setActionCommand("-22");
		newGameButton.setActionCommand("-10");
		exitButton.setActionCommand("-11");
		
		flip.addActionListener(listener);
		undo.addActionListener(listener);
		highlight.addActionListener(listener);
		newGameButton.addActionListener(listener);
		exitButton.addActionListener(listener);
		
		add(title);
		
		
		add(flip);
		add(undo);
		add(highlight);
		add(newGameButton);
		add(exitButton);
	}
}
