package fr.dauphine.javaavance.phineloops.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.dauphine.javaavance.phineloops.controller.MyActionListener;
import fr.dauphine.javaavance.phineloops.controller.MyListener;
import fr.dauphine.javaavance.phineloops.model.Grid;

/**
 * The Gui class is the PhineLoops program's graphic user interface.
 * @see javax.swing.JFrame
 * @see GridDisplay
 * @see fr.dauphine.javaavance.phineloops.model.Grid
 * @see fr.dauphine.javaavance.phineloops.controller.MyListener
 * @see javax.swing.JButton
 * @see fr.dauphine.javaavance.phineloops.controller.MyActionListener
 * @param serialVersionUID Default serial number
 * @param levelDisplay Grid's drawer
 * @param Grid Grid
 * @param myMouse Mouse listener
 * @param solveButton SOLVE button
 * @param myClick Mouse clicks' listener
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class Gui extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private GridDisplay levelDisplay;
	private Grid level;
	private MyListener myMouse;
	private JButton solveButton;
	private MyActionListener myClick;
	
	public Gui(Grid level)
	{
		this.level = level;
		myMouse = new MyListener(this);
		myClick = new MyActionListener(this);
		setName("PhineLoops Game");
		levelDisplay = new GridDisplay(this.level, this);
		//SOLVE BUTTON
		this.solveButton = new JButton("SOLVE");
		this.solveButton.addActionListener(myClick);
		add(levelDisplay, BorderLayout.CENTER);
		add(solveButton, BorderLayout.EAST);
		setSize(level.getWidth() * 114 + 70, level.getHeight() * 114 + 30);
		setLocation(0, 0);
		addMouseListener(myMouse);
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) 
			{
				System.exit(0);
			}
		});

	}
	
	/**
	 * This getter returns the grid.
	 * @return level
	 */
	public Grid getLevel()
	{
		return level;
	}
	
	/**
	 * This getter returns the grid's drawer.
	 * @return levelDisplay
	 */
	public GridDisplay getLeveldisplay()
	{
		return levelDisplay;
	}

	/**
	 * This method displays an information message when a solution was found.
	 */
	public void showMessage()
	{
		JOptionPane.showMessageDialog(this, "You found the solution!",
				"PhineLoops Game - Congratulations!", JOptionPane.INFORMATION_MESSAGE);
		return;
	}
}