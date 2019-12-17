package fr.dauphine.javaavance.phineloops.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.dauphine.javaavance.phineloops.view.Gui;

public class MyListener extends MouseAdapter{
	private Gui g;
	
	public MyListener( Gui g) {
		this.g=g;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
	}
	
}