package fr.dauphine.javaavance.phineloops.view;

import javax.swing.JFrame;
import fr.dauphine.javaavance.phineloops.model.Grid;

public class Gui {
	private JFrame frame;
	private GridDisplay leveldisplay;
	private Grid level;
	
	public Gui(Grid level) {
		this.level=level;
		this.frame= new JFrame("PhineLoops Game");
		this.leveldisplay= new GridDisplay(this.level,this);
		frame.add(leveldisplay);
		frame.setSize(level.getWidth()*100,level.getHeight()*103);
		frame.setLocation(0,0);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
}