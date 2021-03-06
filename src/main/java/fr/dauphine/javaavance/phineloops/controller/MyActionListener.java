package fr.dauphine.javaavance.phineloops.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import fr.dauphine.javaavance.phineloops.view.Gui;

/**
 * The MyActionListener class is associated to the "SOLVE" button on the GUI. It allows the user
 * to solve the grid generated by clicking on it, it it is solvable.
 * @param g Graphic User Interface of the game
 * @see fr.dauphine.javaavance.phineloops.view.Gui
 * @see java.awt.event.ActionListener
 *
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class MyActionListener implements ActionListener
{
	Gui g;
	
	public MyActionListener(Gui g) 
	{
		this.g = g;
	}
	
	/**
	 * This method launches the solving algorithm when the button is clicked. If a solution is found,
	 * it will be displayed on the interface, else an information message will pop.
	 * @see java.awt.event.ActionEvent
	 * @see javax.swing.JOptionPane
	 * @see fr.dauphine.javaavance.phineloops.view.Gui
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		g.getLevel().solve();
		if(g.getLevel().solve())
			g.getLeveldisplay().repaint();
		else
			JOptionPane.showMessageDialog(g, "No solution for this level!",
					"PhineLoops Game - Sorry!", JOptionPane.INFORMATION_MESSAGE);
		
		return;
	}
}
