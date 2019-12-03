package fr.dauphine.javaavance.phineloops.model;

import java.util.HashMap;
import java.util.Random;

public abstract class Piece
{
	private final int number;
	private final int orientationsMax;
	private int orientation;
	private int x;
	private int y;
	private HashMap<Orientation, Boolean> connections;

	public Piece(int number, int orientationsMax, int orientation, int x, int y)
	{
		this.number = number;
		this.orientationsMax = orientationsMax;
		this.orientation = orientation;
		this.x = x;
		this.y = y;
		connections = new HashMap<Orientation, Boolean>(4);
		setConnections();
	}
	
	protected abstract void setConnections();
	
	public int getNumber()
	{
		return number;
	}
	
	public int getOrientationsMax()
	{
		return orientationsMax;
	}

	public int getOrientation()
	{
		return orientation;
	}

	public void setOrientation(int orientation)
	{
		this.orientation = orientation;
		return;
	}
	
	public void setRandomOrientation()
	{
		Random r = new Random();
		orientation = r.nextInt(4);
		setConnections();
		return;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Boolean getConnection(Orientation o)
	{
		return connections.get(o);
	}
	
	public void setConnection(Orientation o, Boolean b)
	{
		if(!connections.containsKey(o)) connections.put(o, b);
		else connections.replace(o, b);
		return;
	}

	public void rotate()
	{
		if(orientation == orientationsMax) orientation = 0;
		else orientation++;
		setConnections();
		
		return;
	}
	
	public boolean isFullyConnected()
	{
		return ((connections.get(Orientation.NORTH) == null || connections.get(Orientation.NORTH))
				&& (connections.get(Orientation.SOUTH) == null || connections.get(Orientation.SOUTH))
				&& (connections.get(Orientation.WEST) == null || connections.get(Orientation.WEST))
				&& (connections.get(Orientation.EAST) == null || connections.get(Orientation.EAST)));
	}
	
	public void compare(Orientation o, Piece p)
	{
		Boolean neighborValue = null;
		Orientation toCompare = null;
		if(o == Orientation.NORTH) toCompare = Orientation.SOUTH;
		else if(o == Orientation.SOUTH) toCompare = Orientation.NORTH;
		else if(o == Orientation.WEST) toCompare = Orientation.EAST;
		else if(o == Orientation.EAST) toCompare = Orientation.WEST;
		
		neighborValue = p.getConnection(toCompare);
		if(neighborValue != null && !neighborValue)
		{
			this.setConnection(o, true);
			p.setConnection(toCompare, true);
		}
		
		return;
	}
}
