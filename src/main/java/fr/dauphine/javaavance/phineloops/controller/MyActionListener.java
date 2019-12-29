package fr.dauphine.javaavance.phineloops.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import fr.dauphine.javaavance.phineloops.view.Gui;

public class MyActionListener  implements ActionListener{
	Gui g;
	public MyActionListener(Gui g) {
		// TODO Auto-generated constructor stub
		this.g=g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		g.getLevel().solve();
		if(g.getLevel().solve()) g.getLeveldisplay().repaint();
		else {
			JOptionPane.showMessageDialog(g, "No solution for this level!",
					"PhineLoops Game - Sorry!", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
