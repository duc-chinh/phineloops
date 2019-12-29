package fr.dauphine.javaavance.phineloops.model;

import java.util.HashMap;
import java.util.Random;

import fr.dauphine.javaavance.phineloops.view.Drawer;

/**
 * The Piece class is a piece from the grid.
 * @param number Piece number (from 0 to 5)
 * @param orientationsMax Different possible orientations' number (from 0 to 4)
 * @param orientaiton Current orientation (from 0 to orientationsMax)
 * @param x
 * @param y
 * @param connections Connections of this piece's status: null if no connection, false if not connected yet, true if connected
 * @param drawer Associated image to be displayed on the GUI
 * @see java.util.HashMap
 * @see Orientation
 * @see fr.dauphine.javaavance.phineloops.view.Drawer
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public abstract class Piece
{
	private final int number;
	private final int orientationsMax;
	private int orientation;
	private int x;
	private int y;
	private HashMap<Orientation, Boolean> connections;
	public Drawer drawer;
	
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
	
	/**
	 * This method sets connections based on the piece and its orientation. To be defined
	 */
	protected abstract void setConnections();
	
	/**
	 * This getter returns the piece's number.
	 * @return int
	 */
	public int getNumber()
	{
		return number;
	}
	
	/**
	 * This getter returns the piece's number of possible orientations.
	 * @return int
	 */
	public int getOrientationsMax()
	{
		return orientationsMax;
	}

	/**
	 * This getter returns the piece's actual orientation.
	 * @return int
	 */
	public int getOrientation()
	{
		return orientation;
	}
	
	/**
	 * This setter changes the piece's orientation.
	 * @param orientation
	 */
	public void setOrientation(int orientation)
	{
		this.orientation = orientation;
		setConnections();
		return;
	}
	
	/**
	 * This method changes randomly the piece's orientation.
	 */
	public void setRandomOrientation()
	{
		Random r = new Random();
		orientation = r.nextInt(4);
		setConnections();
		return;
	}
	
	/**
	 * This getter returns x.
	 * @return int
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * This getter returns y.
	 * @return int
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * This getter returns the connection's status of the selected side.
	 * @param o Piece's side
	 * @return
	 */
	public Boolean getConnection(Orientation o)
	{
		return connections.get(o);
	}
	
	/**
	 * This setter changes the connection's status of the selected side.
	 * @param o Piece's side
	 * @param b Connection's status
	 */
	public void setConnection(Orientation o, Boolean b)
	{
		if(!connections.containsKey(o)) connections.put(o, b);
		else connections.replace(o, b);
		return;
	}
	
	/**
	 * This method increments the piece's orientation and goes back to 0 when it reaches orientationsMax.
	 */
	public void rotate()
	{
		orientation++;
		if(orientation >= orientationsMax) orientation = 0;
		setConnections();
		return;
	}
	/**
	 * Unused method
	public boolean compare(Orientation o, Piece p)
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
			return true;
		}
		else
			return false;
	}
	*/
}
