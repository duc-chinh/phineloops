package fr.dauphine.javaavance.phineloops.view;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import fr.dauphine.javaavance.phineloops.model.*;

public class GridWindow extends JPanel{
	
	private Grid grid;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("This is your Grid");
		frame.setSize(new Dimension(500,500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Random r = new Random();
		Grid grid = Grid.generateGrid(r.nextInt(20), r.nextInt(20));
		GridWindow gridwindow = new GridWindow(grid);
		frame.add(gridwindow);
		frame.setVisible(true);
	}
	
	public GridWindow(Grid grid) {
		this.grid=grid;
		this.setBackground(Color.red);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for (int i=0; i <= grid.getHeight() ;i++) {
			for (int j=0; j <= grid.getWidth() ; j++) {
				g.drawRect(j*20, i*20, 20, 20);
			}
		}
	}
}
