package fr.dauphine.javaavance.phineloops.model;

public class X extends Piece
{
	public X(int x, int y)
	{
		super(4, 0, x, y);
	}
	
	@Override
	public void rotate()
	{
		return;
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
