package fr.dauphine.javaavance.phineloops.view;

import java.awt.Graphics;

import fr.dauphine.javaavance.phineloops.model.T;

/**
 * The TDrawer class defines the 3-sides piece to be displayed on the GUI.
 * @see fr.dauphine.javaavance.phineloops.model.T
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class TDrawer extends Drawer
{
	public TDrawer(T t) 
	{
		super(t);
	}
	
	@Override
	public void draw(Graphics g) 
	{
		super.draw(g);
		return;
	}
}
