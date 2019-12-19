package fr.dauphine.javaavance.phineloops.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.dauphine.javaavance.phineloops.model.Empty;
import fr.dauphine.javaavance.phineloops.view.Gui;

public class MyListener extends MouseAdapter
{
	private Gui g;

	public MyListener(Gui g)
	{
		this.g = g;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		for(int i = 0;i < g.getLevel().getWidth(); i++)
		{
			for(int j = 0; j < g.getLevel().getHeight(); j++)
			{
				int posX = g.getLevel().getPiece(i, j).getX() * 114;
				int posY = g.getLevel().getPiece(i, j).getY() * 114;
				if(e.getX() > posX && e.getX() < (posX + 114) 
						&& e.getY() > (posY + 30) && e.getY() < (posY + 144))
				{
					g.getLevel().getPiece(i, j).rotate();
				}
			}
		}
		g.getLeveldisplay().repaint();
		if(g.getLevel().check()) g.getLevel().endGame(g);
	}
}