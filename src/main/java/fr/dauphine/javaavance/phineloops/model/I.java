package fr.dauphine.javaavance.phineloops.model;

import java.util.Random;
import fr.dauphine.javaavance.phineloops.view.IDrawer;

/**
 * The I class is a Piece corresponding to the 2-sides piece.
 * @see Piece
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class I extends Piece
{
	public I(int orientation, int x, int y)
	{
		super(2, 2, orientation, x, y);
		super.drawer = new IDrawer(this);
	}
	
	@Override
	public void setRandomOrientation()
	{
		Random r = new Random();
		super.setOrientation(r.nextInt(2));
		setConnections();
		return;
	}
	
	@Override
	protected void setConnections()
	{
		int orientation = super.getOrientation();
		if(orientation == 0)
		{
			super.setConnection(Orientation.NORTH, false);
			super.setConnection(Orientation.SOUTH, false);
			super.setConnection(Orientation.WEST, null);
			super.setConnection(Orientation.EAST, null);
		}
		else if(orientation == 1)
		{
			super.setConnection(Orientation.NORTH, null);
			super.setConnection(Orientation.SOUTH, null);
			super.setConnection(Orientation.WEST, false);
			super.setConnection(Orientation.EAST, false);
		}
		
		return;
	}
	
	@Override
	public String toString()
	{
		int orientation = super.getOrientation();
		if(orientation == 0) return "┃";
		else return "━";
	}
}
