package fr.dauphine.javaavance.phineloops.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.dauphine.javaavance.phineloops.model.Empty;
import fr.dauphine.javaavance.phineloops.view.Gui;

public class MyListener extends MouseAdapter{
	private Gui g;

	public MyListener( Gui g) {
		this.g=g;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(int i=0;i<g.getLevel().getWidth();i++) {
			for(int j=0;j<g.getLevel().getHeight();j++) {
				int posX = g.getLevel().getPiece(i,j).getX()*100;
				int posY = g.getLevel().getPiece(i,j).getY()*100;
				if(e.getX()>=posX && e.getX()<= posX+100 && e.getY()>=posY && e.getY()<= posY+100) {
					if (g.getLevel().getPiece(i, j) instanceof Empty) {
						System.out.println("Empty");
						return;
					}
					if(g.getLevel().getPiece(i, j).getOrientation()!= g.getLevel().getPiece(i, j).getOrientationsMax()) {
						g.getLevel().getPiece(i, j).setOrientation(g.getLevel().getPiece(i, j).getOrientation()+1);
					}else {
						g.getLevel().getPiece(i, j).setOrientation(0);
					}
				}
			}
		}
		g.getLeveldisplay().repaint();
	}

}