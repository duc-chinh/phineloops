package fr.dauphine.javaavance.phineloops.view;

import java.awt.Graphics;
import javax.swing.JPanel;
import fr.dauphine.javaavance.phineloops.model.Grid;

public class GridDisplay  extends JPanel{
	private Grid level;
	
	public GridDisplay(Grid level, Gui g) {
		// TODO Auto-generated constructor stub
		this.level=level;
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		for(int i=0;i<level.getWidth();i++) {
			for(int j=0;j<level.getHeight();j++) {
				level.getPiece(i,j).drawer.draw(g);
			}
		}
	}
}