package fr.dauphine.javaavance.phineloops.model;

public class Empty extends Piece
{
	public Empty(int x, int y)
	{
		super(0, 0, x, y);
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
