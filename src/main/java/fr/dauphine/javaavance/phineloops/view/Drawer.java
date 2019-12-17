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
	private BufferedImage img;
	public Drawer(Piece p) {
		// TODO Auto-generated constructor stub
		this.p=p;
		try {
			img = ImageIO.read(new File("ImagesPieces/"+p.getNumber()+".png"));
		} catch (IOException e) {
		}

	}

	public void draw(Graphics g) {
		AffineTransform transformer = new AffineTransform();
		transformer.rotate(((90*p.getOrientation())*Math.PI)/180, img.getWidth()/2, img.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(transformer,AffineTransformOp.TYPE_BILINEAR);
		BufferedImage im= op.filter(img, null);
		g.drawImage(im, p.getX()*100, p.getY()*100,100,100, null);
	}
}