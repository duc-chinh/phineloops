package fr.dauphine.javaavance.phineloops.view;

import java.awt.Graphics;

import fr.dauphine.javaavance.phineloops.model.OneConnection;

/**
 * The OneConnectionDrawer class defines the 1-side piece to be displayed on the GUI.
 * @see fr.dauphine.javaavance.phineloops.model.OneConnection
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class OneConnectionDrawer extends Drawer 
{
	public OneConnectionDrawer(OneConnection oneConnection) 
	{
		super(oneConnection);
	}
	
	@Override
	public void draw(Graphics g) 
	{
		super.draw(g);
		return;
	}
}
