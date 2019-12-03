package fr.dauphine.javaavance.phineloops.model;

public class T extends Piece
{
	public T(int orientation, int x, int y)
	{
		super(3, 4, orientation, x, y);
	}
	
	@Override
	protected void setConnections()
	{
		int orientation = super.getOrientation();
		if(orientation == 0)
		{
			super.setConnection(Orientation.NORTH, false);
			super.setConnection(Orientation.SOUTH, null);
			super.setConnection(Orientation.WEST, false);
			super.setConnection(Orientation.EAST, false);
		}
		else if(orientation == 1)
		{
			super.setConnection(Orientation.NORTH, false);
			super.setConnection(Orientation.SOUTH, false);
			super.setConnection(Orientation.WEST, null);
			super.setConnection(Orientation.EAST, false);
		}
		else if(orientation == 2)
		{
			super.setConnection(Orientation.NORTH, null);
			super.setConnection(Orientation.SOUTH, false);
			super.setConnection(Orientation.WEST, false);
			super.setConnection(Orientation.EAST, false);
		}
		else if(orientation == 3)
		{
			super.setConnection(Orientation.NORTH, false);
			super.setConnection(Orientation.SOUTH, false);
			super.setConnection(Orientation.WEST, false);
			super.setConnection(Orientation.EAST, null);
		}
		return;
	}
	
	@Override
	public String toString()
	{
		int orientation = super.getOrientation();
		if(orientation == 0) return "┻";
		else if(orientation == 1) return "┣";
		else if(orientation == 2) return "┳";
		else return "┫";
	}
}
