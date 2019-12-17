package fr.dauphine.javaavance.phineloops.view;

import javax.swing.JFrame;

import fr.dauphine.javaavance.phineloops.controller.MyListener;
import fr.dauphine.javaavance.phineloops.model.Grid;

public class Gui {
	private JFrame frame;
	private GridDisplay leveldisplay;
	private Grid level;
	private MyListener mymouse;
	
	public Gui(Grid level) {
		this.level=level;
		this.mymouse= new MyListener(this);
		this.frame= new JFrame("PhineLoops Game");
		this.leveldisplay= new GridDisplay(this.level,this,mymouse);
		frame.add(leveldisplay);
		frame.setSize(level.getWidth()*100,level.getHeight()*105);
		frame.setLocation(0,0);
		frame.addMouseListener(mymouse);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
}