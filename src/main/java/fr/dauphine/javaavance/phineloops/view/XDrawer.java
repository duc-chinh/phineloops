package fr.dauphine.javaavance.phineloops.view;

import java.awt.Graphics;

import fr.dauphine.javaavance.phineloops.model.X;

/**
 * The XDrawer class defines the 4-sides piece to be displayed on the GUI.
 * @see fr.dauphine.javaavance.phineloops.model.X
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class XDrawer extends Drawer 
{
	public XDrawer(X x) 
	{
		super(x);
	}
	
	@Override
	public void draw(Graphics g) 
	{
		super.draw(g);
		return;
	}
}
