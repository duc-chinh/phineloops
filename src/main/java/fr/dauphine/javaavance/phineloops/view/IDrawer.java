package fr.dauphine.javaavance.phineloops.view;

import java.awt.Graphics;

import fr.dauphine.javaavance.phineloops.model.I;

/**
 * The IDrawer class defines the 2-sides piece to be displayed on the GUI.
 * @see fr.dauphine.javaavance.phineloops.model.I
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class IDrawer extends Drawer
{
	public IDrawer(I i) 
	{
		super(i);
	}
	
	@Override
	public void draw(Graphics g) 
	{
		super.draw(g);
		return;
	}
}
