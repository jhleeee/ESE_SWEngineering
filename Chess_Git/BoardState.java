import java.util.ArrayList;
import java.io.*;

@SuppressWarnings("serial")
public class BoardState implements Serializable
{
	private ChessPiece[][] boardState;
	private boolean isWhiteTurn;
	private int savedStateQueue;
	
	public BoardState()
	{
		
	}
	
	public ChessPiece[][] getState()
	{
		return boardState;
	}
	
}