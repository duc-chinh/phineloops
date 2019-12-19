package fr.dauphine.javaavance.phineloops.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.dauphine.javaavance.phineloops.controller.MyListener;
import fr.dauphine.javaavance.phineloops.model.Grid;

public class Gui
{
	private JFrame frame;
	private GridDisplay leveldisplay;
	private Grid level;
	private MyListener mymouse;
	private JButton solveButton;
	private JButton generateButton;
	
	public Gui(Grid level)
	{
		this.level = level;
		this.mymouse = new MyListener(this);
		this.frame = new JFrame("PhineLoops Game");
		this.leveldisplay = new GridDisplay(this.level, this);
		//SOLVE BUTTON
		this.solveButton = new JButton("SOLVE");
		this.solveButton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				System.out.println("add methode solve here for interface");
			}
		});
		frame.add(leveldisplay, BorderLayout.CENTER);
		frame.add(solveButton, BorderLayout.EAST);
		frame.setSize(level.getWidth() * 114 + 70, level.getHeight() * 114 + 30);
		frame.setLocation(0, 0);
		frame.addMouseListener(mymouse);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public Grid getLevel()
	{
		return level;
	}
	
	public GridDisplay getLeveldisplay()
	{
		return leveldisplay;
	}
	
	public void showMessage()
	{
		JOptionPane.showMessageDialog(frame, "You found the solution!",
				"PhineLoops Game - Congratulations!", JOptionPane.INFORMATION_MESSAGE);
		return;
	}
}