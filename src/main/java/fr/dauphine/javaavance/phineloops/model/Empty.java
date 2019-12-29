package fr.dauphine.javaavance.phineloops.model;

import fr.dauphine.javaavance.phineloops.view.EmptyDrawer;

/**
 * The Empty class is a Piece corresponding to an empty spot.
 * @see Piece
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class Empty extends Piece
{
	public Empty(int x, int y)
	{
		super(0, 0, 0, x, y);
		super.drawer = new EmptyDrawer(this);
	}
	
	@Override
	public void setRandomOrientation()
	{
		return;
	}
	
	@Override
	protected void setConnections()
	{
		super.setConnection(Orientation.NORTH, null);
		super.setConnection(Orientation.SOUTH, null);
		super.setConnection(Orientation.WEST, null);
		super.setConnection(Orientation.EAST, null);
		
		return;
	}
	
	@Override
	public String toString()
	{
		return " ";
	}
}
