package fr.dauphine.javaavance.phineloops.view;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.dauphine.javaavance.phineloops.model.Piece;

public abstract class Drawer {
	public Piece p;
	
	public Drawer(Piece p) {
		// TODO Auto-generated constructor stub
		this.p=p;
	}
	public void draw(Graphics g) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("ImagesPieces/"+p.getNumber()+".png"));
		} catch (IOException e) {
		}

		BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		AffineTransform transformer = new AffineTransform();
		transformer.rotate(90*p.getOrientation()* Math.PI / 180.0, img.getWidth()/2, img.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(transformer,AffineTransformOp.TYPE_BILINEAR);
		img = op.filter(img, null);
		g.drawImage(img, p.getX()*100, p.getY()*100,100,100, null);
	}
}