package fr.dauphine.javaavance.phineloops.model;

import fr.dauphine.javaavance.phineloops.view.XDrawer;

/**
 * The X class is a Piece corresponding to the 4-sides piece.
 * @see Piece
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class X extends Piece
{
	public X(int x, int y)
	{
		super(4, 0, 0, x, y);
		super.drawer = new XDrawer(this);
	}
	
	@Override
	public void setRandomOrientation()
	{
		return;
	}
	
	@Override
	protected void setConnections()
	{
		super.setConnection(Orientation.NORTH, false);
		super.setConnection(Orientation.SOUTH, false);
		super.setConnection(Orientation.WEST, false);
		super.setConnection(Orientation.EAST, false);
		
		return;
	}
	
	@Override
	public String toString()
	{
		return "╋";
	}
}
