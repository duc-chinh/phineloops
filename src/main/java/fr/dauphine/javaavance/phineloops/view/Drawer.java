package fr.dauphine.javaavance.phineloops.view;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import fr.dauphine.javaavance.phineloops.model.Piece;

/**
 * The Drawer class defines a piece's image to be displayed on the GUI.
 * @see fr.dauphine.javaavance.phineloops.model.Piece
 * @see java.awt.image.BufferedImage
 * @param p Piece of the grid
 * @param img Image of the piece
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 *
 */
public abstract class Drawer 
{
	public Piece p;
	private BufferedImage img;
	
	public Drawer(Piece p) 
	{
		this.p = p;
		try 
		{
			img = ImageIO.read(new File("ImagesPieces/" + p.getNumber() + ".png"));
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method displays the piece on the GUI.
	 * @see java.awt.Graphics
	 * @see java.awt.geom.AffineTransform
	 * @see java.awt.image.AffineTransformOp
	 * @see java.awt.image.BufferedImage
	 * @param g
	 */
	public void draw(Graphics g)
	{
		AffineTransform transformer = new AffineTransform();
		transformer.rotate(((90 * p.getOrientation()) * Math.PI)/180, img.getWidth()/2, img.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(transformer, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage im = op.filter(img, null);
		g.drawImage(im, p.getX()*im.getWidth(), p.getY()*im.getHeight(), null);
		
		return;
	}
}