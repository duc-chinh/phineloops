package fr.dauphine.javaavance.phineloops.view;

import java.awt.Graphics;
import javax.swing.JPanel;
import fr.dauphine.javaavance.phineloops.model.Grid;

/**
 * The GridDisplay class draws the entire grid on the GUI.
 * @see javax.swing.JPanel
 * @see fr.dauphine.javaavance.phineloops.model.Grid
 * @param serialVersionUID Default serial number
 * @param level Grid
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class GridDisplay extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Grid level;
	
	public GridDisplay(Grid level, Gui g)
	{
		this.level = level;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		for(int y = 0; y < level.getWidth(); y++)
		{
			for(int x = 0; x < level.getHeight(); x++)
				level.getPiece(x, y).drawer.draw(g);
		}
		return;
	}
}