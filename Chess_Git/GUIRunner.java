import java.util.ArrayList;
import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class GUIRunner implements Serializable
{
	private static final long serialVersionUID = -5502586734068739286L;
	private Location selectedPiece, promotedPiece;
	private BoardState board;
	private GUI gui;
	private boolean isWhiteTurn;
	private boolean highlight;
	private boolean perpetualFlip;
	private ArrayList<Integer> undoMoves;
	private ArrayList<Integer> wasPieceTaken;
	private ArrayList<Location> moves;
	
	
	
	public void setVisible()
	{
		gui.setVisible(true);
		newGame();
	}
	
	public void newGame()
	{
	
		isWhiteTurn = true;
		undoMoves = new ArrayList<Integer>();
		wasPieceTaken = new ArrayList<Integer>();
		gui.newGame();
	}
	
	public boolean getTurn()
	{
		return isWhiteTurn;
	}
	
	
}