import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -5825115473793035743L;
	private GUIRunner runner;	
	private ChessBoard board;
	private PromotionPopup promotion;
	private GameOverPopup gameOver;
	private VerifyExitPopup verify;
	private Flipped isFlipped;

	
	public GUI(BoardState boardState, GUIRunner runner)
	{
		super("G Chess");
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension boardSize = new Dimension(482,482);
		Dimension pgnPanelSize = new Dimension(160,461);
		Dimension southSize = new Dimension(712, 41);
		Dimension statusBarSize = new Dimension(712, 27);
		Dimension toolBarSize = new Dimension(70,482);

		this.runner = runner;
		
		isFlipped = new Flipped();
		setLayout(new BorderLayout());
		setResizable(false);
		JPanel south = new JPanel();
		south.setPreferredSize(southSize);
		south.setLayout(new BoxLayout(south, BoxLayout.PAGE_AXIS));
		JPanel southTop = new JPanel();
		southTop.setOpaque(true);
		southTop.setBackground(new Color(51,102,255));
		JPanel southBottom = new JPanel();
		southBottom.setOpaque(true);
		southBottom.setBackground(new Color(51,102,255));

		
		south.add(southTop);
		south.add(southBottom);
		add(south, BorderLayout.SOUTH);
	
	
		pack();
		setLocation(screen.width/2-getSize().width/2, screen.height/2-getSize().height/2);
		repaint();
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	public ChessBoard getChessBoard()
	{
		return board;
	}
	
	public Flipped getIsFlipped()
	{
		return isFlipped;
	}

	public Location processLocation(Location loc)
	{
		if(!isFlipped.getFlip())
			return loc;
		else
			return new Location(7-loc.getRow(),7-loc.getCol());
	}
	public boolean getFlipped()
	{
		return isFlipped.getFlip();
	}
	public void verifyExit()
	{
		verify = new VerifyExitPopup(this);
		setEnabled(false);
		verify.setVisible(true);
	}
	
	public void gameOver(int outcome)
	{
		if(outcome==1)
			gameOver = new GameOverPopup(this, " White Win");
		else if(outcome==-1)
			gameOver = new GameOverPopup(this, " Black Win");
		else
			gameOver = new GameOverPopup(this, " Draw");
		setEnabled(false);
		gameOver.setVisible(true);
	}
	
	public void newGame()
	{
		setEnabled(true);
		if(gameOver!=null)
			gameOver.dispose();

}