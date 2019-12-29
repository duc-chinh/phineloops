package fr.dauphine.javaavance.phineloops.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame ;
import javax.swing.JOptionPane;

import fr.dauphine.javaavance.phineloops.controller.MyActionListener;
import fr.dauphine.javaavance.phineloops.controller.MyListener;
import fr.dauphine.javaavance.phineloops.model.Grid;

public class Gui extends JFrame 
{

	private GridDisplay leveldisplay;
	private Grid level;
	private MyListener mymouse;
	private JButton solveButton;

	private MyActionListener myclick;
	public Gui(Grid level)
	{
		this.level = level;
		mymouse = new MyListener(this);
		myclick= new MyActionListener(this);
		setName("PhineLoops Game");
		leveldisplay = new GridDisplay(this.level, this);
		//SOLVE BUTTON
		this.solveButton = new JButton("SOLVE");
		this.solveButton.addActionListener(myclick);
		add(leveldisplay, BorderLayout.CENTER);
		add(solveButton, BorderLayout.EAST);
		setSize(level.getWidth() * 114 + 70, level.getHeight() * 114 + 30);
		setLocation(0, 0);
		addMouseListener(mymouse);
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				System.exit(0);
			}
		});

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
		JOptionPane.showMessageDialog(this, "You found the solution!",
				"PhineLoops Game - Congratulations!", JOptionPane.INFORMATION_MESSAGE);
		return;
	}
}