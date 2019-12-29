package fr.dauphine.javaavance.phineloops.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.dauphine.javaavance.phineloops.view.Gui;

/**
 * The MyListener method handles the mouse's actions and movements on the GUI.
 * @param g Graphic User Interface of the game
 * @see fr.dauphine.javaavance.phineloops.view.Gui
 * @see java.awt.event.MouseAdapter
 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class MyListener extends MouseAdapter
{
	private Gui g;

	public MyListener(Gui g)
	{
		this.g = g;
	}
	
	/**
	 * This method the action to be made when the user clicks on the interface. It will mainly
	 * rotate the piece which has been clicked, refresh the display and verify if the grid was
	 * solved.
	 * 
	 * @see java.awt.event.MouseEvent
	 * @see fr.dauphine.javaavance.phineloops.view.Gui
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		for(int y = 0; y < g.getLevel().getHeight(); y++)
		{
			for(int x = 0; x < g.getLevel().getWidth(); x++)
			{
				int posX = g.getLevel().getPiece(x, y).getX() * 114;
				int posY = g.getLevel().getPiece(x, y).getY() * 114;
				if(e.getX() > posX && e.getX() < (posX + 114) && e.getY() > (posY + 30) && e.getY() < (posY + 144))
					g.getLevel().getPiece(x, y).rotate();
			}
		}
		g.getLeveldisplay().repaint();
		
		if(g.getLevel().check()) g.getLevel().endGame(g);
		
		return;
	}
}