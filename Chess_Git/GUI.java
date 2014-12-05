/*
G Chess version 1.0
Copyright (c) 2010 Gary Menezes

Copyright Notice
  You may use the accompanying code under the following conditions:
  You may:
    1. Use this code for non-commercial, educational, and personal purposes.
    2. Redistribute this code *as is* along with included copyright notices.
  You may not:
    1. Use this code for any commercial purpose.
    2. Create any derivative works for redistribution.
*/
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
	
	private StatusBar statusBar;
	
	private PGNDisplay pgnPanel;
		
	public GUI(BoardState boardState, GUIRunner runner)
	{
		super("G Chess");
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();//���� �ػ�
		Dimension boardSize = new Dimension(700,700);//���������
	//	Dimension pgnPanelSize = new Dimension(160,461);//�����丮â ����Ʈ
	//	Dimension southSize = new Dimension(712, 41); // �ϴ� ������
	//	Dimension statusBarSize = new Dimension(782, 27);//���� ��ǥâ ������
		Dimension toolBarSize = new Dimension(300,700);//��ɹ�ư ������

		this.runner = runner;
		
		isFlipped = new Flipped();// false �ʱ�ȭ
		
		//"this" Main Frame***********************************************************
		setLayout(new BorderLayout());//ĭ ������ ���̾ƿ�
		setResizable(false);
		//****************************************************************************
		
		//South Panel*****************************************************************//�ϴܺ� ����
//		JPanel south = new JPanel();
//		south.setPreferredSize(southSize);//�ϴܺ� ������
//		south.setLayout(new BoxLayout(south, BoxLayout.PAGE_AXIS));
//		JPanel southTop = new JPanel();
//		southTop.setOpaque(true);
//		southTop.setBackground(new Color(51,102,0));
//		JPanel southBottom = new JPanel();
//		southBottom.setOpaque(true);
//		southBottom.setBackground(new Color(51,2,255));
		//****************************************************************************

		//Status Bar******************************************************************
//		statusBar = new StatusBar(statusBarSize);//���¹� ������
//		southBottom.add(statusBar);// �ϴܺιٴڿ� ���¹� �߰�
		//****************************************************************************
		
//		south.add(southTop);//�ϴܺο� �����߰�
//		south.add(southBottom);//�ϴܺο� �ٴ��߰�
		//add(south, BorderLayout.SOUTH);

		//PGN Panel*******************************************************************		
//		pgnPanel = new PGNDisplay(pgnPanelSize);//�����丮 â �߰�
//		add(pgnPanel,BorderLayout.WEST);
		//****************************************************************************

		//Button Panel****************************************************************
		ToolBar toolBar = new ToolBar(this, toolBarSize);
		add(toolBar,BorderLayout.EAST);
		//****************************************************************************

		//Game Board******************************************************************
		board = new ChessBoard(boardState, this, boardSize);
		updateBoard(boardState);	//���� ������Ʈ	
		add(board,BorderLayout.CENTER);//���� ����߰�
		//****************************************************************************

		//Menu Bar********************************************************************
		Menu menuBar = new Menu(this);//��� �޴��� 
		setJMenuBar(menuBar);
		//****************************************************************************

		pack();
		setLocation(screen.width/2-getSize().width/2, screen.height/2-getSize().height/2);// ȭ�� �߾ӿ� ����
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
	public StatusBar getStatusBar()
	{
		return statusBar;
	}
	public PGNDisplay getPgnPanel()
	{
		return pgnPanel;
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
		//clearPGN();
		//updateStatusBar("New Game!", true);//���ӽ����� �˸��� �κ�
		flipBoard();
		flipBoard();
	}
	public void updatePGN(Location start, Location end)
	{
		//pgnPanel.updatePGN(start, end, runner.getTurn());
	}
	
	public void backPGN()
	{
		//pgnPanel.backPGN(runner.getTurn());
	}
	
	public void clearPGN()
	{
		pgnPanel.clearPGN();
	}
	
	public void promotion()
	{
		setEnabled(false);
		promotion = new PromotionPopup(this);
		promotion.setVisible(true);
	}
	
	public void endPromotion()
	{
		setEnabled(true);
		promotion.dispose();
	}
	
	public void enableSide(boolean turn)
	{
		board.enableSide(turn);
	}
	
	public void flipBoard()
	{
		isFlipped.flip();
		board.flipBoard();
	}
	
	public void resetBorders()
	{
		board.resetBorders();
	}
	
	public void resetBackground()
	{
		board.resetBackground();
	}
	
	public void enable(Location loc, boolean highlight)
	{
		board.enable(loc, highlight);
	}
	
	public void selected(Location loc)
	{
		board.selected(loc);
	}
	
	public void dynamicUpdateStatusBar(JButton button)
	{
		for(int y=0; y<board.getSide(); y++)
			for(int x=0; x<board.getSide(); x++)
				if(button==board.getButton(y,x))
					updateStatusBar(""+processLocation(new Location(y,x)), false);
	}
	
	public void updateStatusBar(String output, boolean isAlert)
	{
		//statusBar.update(output, isAlert);
	}
	
	public void updateBoard(BoardState boardState)
	{
		board.updateBoard(boardState);
	}
	
	public void actionPerformed(ActionEvent event)//��ưŬ���� �۵�
	{
		int command = Integer.parseInt(event.getActionCommand());//Ŭ���� ��ư- ���忡���� ��ǥ��
		if(command >= 0)
		{
			//ü���������� Ŭ���̺�Ʈ ó��
			int x = command%10;
			int y = command/10;
			
			Location loc = processLocation(new Location(y,x)); //LOC ��ǥ����
			runner.processOne(loc);
		}
		else
			//��ɹ�ư���� Ŭ�� �̺�Ʈ ó��
			runner.processTwo(command);
	}
}