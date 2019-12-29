package fr.dauphine.javaavance.phineloops.view;

import java.awt.Graphics;

import fr.dauphine.javaavance.phineloops.model.Empty;

/**
 * The EmptyDrawer class defines the Empty piece to be displayed on the GUI.
 * @see fr.dauphine.javaavance.phineloops.model.Empty
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class EmptyDrawer extends Drawer 
{
	public EmptyDrawer(Empty empty) 
	{
		super(empty);
	}
	
	@Override
	public void draw(Graphics g) 
	{
		super.draw(g);
		return;
	}
}
