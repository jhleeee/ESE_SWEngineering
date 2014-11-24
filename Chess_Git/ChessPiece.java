import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public abstract class ChessPiece implements Serializable
{
	private boolean isWhite;
	
	private Location loc;
	
	public ChessPiece(boolean isWhite, Location loc)
	{
		this.isWhite = isWhite;
		this.loc = loc;
	}
	
	public void setLocation(Location loc)
	{
		this.loc = loc;
	}
	
	public Location getLocation()
	{
		return loc;
	}
	
	public boolean getColor()
	{
		return isWhite;
	}
	
	public abstract void draw(Graphics g);
	public abstract ArrayList<Location> getMoves(BoardState board);
	public abstract void moveTo(Location moveLoc);
	
	
	public String toString()
	{
		if(isWhite)
			return "White";
		else
			return "Black";
	}
}
