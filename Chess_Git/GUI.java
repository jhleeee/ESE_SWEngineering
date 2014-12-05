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
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();//현재 해상도
		Dimension boardSize = new Dimension(700,700);//보드사이즈
	//	Dimension pgnPanelSize = new Dimension(160,461);//히스토리창 사이트
	//	Dimension southSize = new Dimension(712, 41); // 하단 사이즈
	//	Dimension statusBarSize = new Dimension(782, 27);//말의 좌표창 사이즈
		Dimension toolBarSize = new Dimension(300,700);//기능버튼 사이즈

		this.runner = runner;
		
		isFlipped = new Flipped();// false 초기화
		
		//"this" Main Frame***********************************************************
		setLayout(new BorderLayout());//칸 나누는 레이아웃
		setResizable(false);
		//****************************************************************************
		
		//South Panel*****************************************************************//하단부 제작
//		JPanel south = new JPanel();
//		south.setPreferredSize(southSize);//하단부 사이즈
//		south.setLayout(new BoxLayout(south, BoxLayout.PAGE_AXIS));
//		JPanel southTop = new JPanel();
//		southTop.setOpaque(true);
//		southTop.setBackground(new Color(51,102,0));
//		JPanel southBottom = new JPanel();
//		southBottom.setOpaque(true);
//		southBottom.setBackground(new Color(51,2,255));
		//****************************************************************************

		//Status Bar******************************************************************
//		statusBar = new StatusBar(statusBarSize);//상태바 사이즈
//		southBottom.add(statusBar);// 하단부바닥에 상태바 추가
		//****************************************************************************
		
//		south.add(southTop);//하단부에 위쪽추가
//		south.add(southBottom);//하단부에 바닥추가
		//add(south, BorderLayout.SOUTH);

		//PGN Panel*******************************************************************		
//		pgnPanel = new PGNDisplay(pgnPanelSize);//히스토리 창 추가
//		add(pgnPanel,BorderLayout.WEST);
		//****************************************************************************

		//Button Panel****************************************************************
		ToolBar toolBar = new ToolBar(this, toolBarSize);
		add(toolBar,BorderLayout.EAST);
		//****************************************************************************

		//Game Board******************************************************************
		board = new ChessBoard(boardState, this, boardSize);
		updateBoard(boardState);	//보드 업데이트	
		add(board,BorderLayout.CENTER);//보드 가운데추가
		//****************************************************************************

		//Menu Bar********************************************************************
		Menu menuBar = new Menu(this);//상단 메뉴바 
		setJMenuBar(menuBar);
		//****************************************************************************

		pack();
		setLocation(screen.width/2-getSize().width/2, screen.height/2-getSize().height/2);// 화면 중앙에 띄우기
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
		//updateStatusBar("New Game!", true);//게임시작을 알리는 부분
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
	
	public void actionPerformed(ActionEvent event)//버튼클릭시 작동
	{
		int command = Integer.parseInt(event.getActionCommand());//클릭한 버튼- 보드에서의 좌표값
		if(command >= 0)
		{
			//체스보드위의 클릭이벤트 처리
			int x = command%10;
			int y = command/10;
			
			Location loc = processLocation(new Location(y,x)); //LOC 좌표저장
			runner.processOne(loc);
		}
		else
			//기능버튼들의 클릭 이벤트 처리
			runner.processTwo(command);
	}
}