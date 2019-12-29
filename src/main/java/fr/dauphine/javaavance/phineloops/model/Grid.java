package fr.dauphine.javaavance.phineloops.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.loop.monitors.ISearchMonitor;
import org.chocosolver.solver.variables.IntVar;

import fr.dauphine.javaavance.phineloops.view.Gui;

public class Grid
{
	private Piece[][] grid;
	private int height;
	private int width;
	private int unconnectedSides;

	public Grid(int height, int width)
	{
		this.height = height;
		this.width = width;
		grid = new Piece[this.height][this.width];
		unconnectedSides = 0;
	}

	public Piece getPiece(int x, int y)
	{
		return grid[y][x];
	}

	public void setUnconnectedSides(int unconnectedSides)
	{
		this.unconnectedSides = unconnectedSides;
		return;
	}

	public int getUnconnectedSides()
	{
		return unconnectedSides;
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	private boolean isValid(int x, int y)
	{
		Piece piece = grid[y][x];
		if((piece.getX() == 0 && piece.getConnection(Orientation.WEST) != null)
				|| (piece.getX() == width - 1 && piece.getConnection(Orientation.EAST) != null)
				|| (piece.getY() == 0 && piece.getConnection(Orientation.NORTH) != null)
				|| (piece.getY() == height - 1 && piece.getConnection(Orientation.SOUTH) != null))
			return false;
		if(piece.getX() > 0)
		{
			Boolean b = grid[piece.getY()][piece.getX() - 1].getConnection(Orientation.EAST);
			if((b != null && piece.getConnection(Orientation.WEST) == null)
					|| (b == null && piece.getConnection(Orientation.WEST) != null))
				return false;
		}
		if(piece.getY() > 0)
		{
			Boolean b = grid[piece.getY() - 1][piece.getX()].getConnection(Orientation.SOUTH);
			if((b != null && piece.getConnection(Orientation.NORTH) == null)
					|| (b == null && piece.getConnection(Orientation.NORTH) != null))
				return false;
		}
		return true;
	}

	public static Grid generateGrid(int height, int width)
	{
		Grid g = new Grid(height, width);
		Random r = new Random();
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				g.grid[y][x] = null;
				do
				{
					int nb = r.nextInt(6);

					if(nb == 0) g.grid[y][x] = new Empty(x, y);
					else if(nb == 1) g.grid[y][x] = new OneConnection(0, x, y);
					else if(nb == 2) g.grid[y][x] = new I(0, x, y);
					else if(nb == 3) g.grid[y][x] = new T(0, x, y);
					else if(nb == 4) g.grid[y][x] = new X(x, y);
					else if(nb == 5) g.grid[y][x] = new L(0, x, y);
					g.grid[y][x].setRandomOrientation();
				} while(!g.isValid(x, y));
			}
		}

		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
				g.grid[y][x].setRandomOrientation();
		}

		return g;
	}

	public static Grid generateGridWithFile(String inputFile)
	{
		Grid g =null;
		BufferedReader b_in=null;
		FileReader f_in =null;
		String ligne;
		try 
		{
			f_in = new FileReader(inputFile);
			b_in= new BufferedReader(f_in);
			try {
				ligne =b_in.readLine();
				int height = Integer.parseInt(ligne);
				ligne= b_in.readLine();
				int width = Integer.parseInt(ligne);
				g= new Grid(height,width);
				int x =0 ,y =0;
				while((ligne=b_in.readLine())!=null)
				{
					int numberPiece =Character.getNumericValue(ligne.charAt(0));
					int numberOrientation =Character.getNumericValue(ligne.charAt(2));
					if(numberPiece == 0) g.grid[y][x] = new Empty(x, y);
					else if(numberPiece == 1) g.grid[y][x] = new OneConnection(numberOrientation, x, y);
					else if(numberPiece == 2) g.grid[y][x] = new I(numberOrientation, x, y);
					else if(numberPiece == 3) g.grid[y][x] = new T(numberOrientation, x, y);
					else if(numberPiece == 4) g.grid[y][x] = new X(x,y);
					else if(numberPiece == 5) g.grid[y][x] = new L(numberOrientation, x, y);
					if(x==width-1) {
						x=0;
						y++;
					}else {
						x++;
					}

				}
				b_in.close();
				f_in.close();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found or Error open file");
		}

		return g;
	}


	public boolean check()
	{
		boolean solved = true;
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				if(!isValid(x, y))
				{
					solved = false;
					break;
				}
			}
			if(!solved) break;
		}
		return solved;
	}

	public boolean solve() {
		Model model = new Model ("PhineLoop solver");
		//define a two dimensional array named for instance orientationP[][] for which each cell is of type IntVar for the orientation of the Piece
		IntVar [][] orientationP = new  IntVar [height][width];
		for(int i=0;i<height;i++) {
			for(int j =0; j<width;j++) {
				//specify the range of values orientation that can be in each square
				if(grid[i][j].getNumber()==0 || grid[i][j].getNumber()==4) orientationP[i][j]=model.intVar("orientation "+i+""+j,0);
				else orientationP[i][j]=model.intVar("orientation "+i+""+j,0,grid[i][j].getOrientationsMax()-1);
			}
		}
		int numberN=0;
		int n=0;
		String op1="";
		String op2="";

		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				int number =grid[i][j].getNumber();
				Constraint[] c = new Constraint[4];
				Constraint [] c1= new Constraint[4];
				//for each square define an array of square that is of size equal to the number of side can be connected
				//// initialize the values for each square connections with neighbor must be connected
				if(number!=0) {
					if(i != 0) {
						numberN = grid[i-1][j].getNumber();
						/*if(number==0) {
						if(numberN==0)c[0]=model.trueConstraint();
						else if(numberN==1) c[0]=model.arithm(orientationP[i-1][j],"!=",2);
						else if(numberN==2) c[0]=model.arithm(orientationP[i-1][j],"!=",0);
						else if(numberN==3) c[0]=model.arithm(orientationP[i-1][j],"=",0);
						else if(numberN==4) c[0]=model.falseConstraint();
						else c[0]=model.member(orientationP[i-1][j],new int[] {0,3});
						c1[0]=c[0];
					}else */if(number==5) {
						if(numberN==0) {c[0]=model.member(orientationP[i][j],new int[] {1,2});c1[0]=c[0];}
						else if(numberN==1) {c[0]=model.and(model.member(orientationP[i][j],new int[] {0,3}),model.arithm(orientationP[i-1][j],"=",2));c1[0]=model.and(model.member(orientationP[i][j],new int[] {1,2}),model.arithm(orientationP[i-1][j],"!=",2));}
						else if(numberN==2) {c[0]=model.and(model.member(orientationP[i][j],new int[] {0,3}),model.arithm(orientationP[i-1][j],"=",0));c1[0]=model.and(model.member(orientationP[i][j],new int[] {1,2}),model.arithm(orientationP[i-1][j],"!=",0));}
						else if(numberN==3) {c[0]=model.and(model.member(orientationP[i][j],new int[] {0,3}),model.arithm(orientationP[i-1][j],"!=",0));c1[0]=model.and(model.member(orientationP[i][j],new int[] {1,2}),model.arithm(orientationP[i-1][j],"=",0));}
						else if(numberN==4) {c[0]=model.member(orientationP[i][j],new int[] {0,3});c1[0]=c[0];}
						else {c[0]=model.and(model.member(orientationP[i][j],new int[] {0,3}),model.member(orientationP[i-1][j],new int[]{1,2}));c1[0]=model.and(model.member(orientationP[i][j],new int[] {1,2}),model.member(orientationP[i-1][j],new int[]{0,3}));}
					}else {
						if(number==1 ||number==2 ||number==4) {n=0;op1="!="; op2="=";}
						else if(number==3) {n=2;op1="=";op2="!=";}
						if(numberN==0 ){c[0]=model.arithm(orientationP[i][j],op1,n);c1[0]=c[0];}
						else if(numberN==3) {c[0]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i-1][j],"!=",0));c1[0]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i-1][j],"=",0));}
						else if(numberN==1) {c[0]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i-1][j],"=",2));c1[0]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i-1][j],"!=",2));}
						else if(numberN==2)	{c[0]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i-1][j],"=",0));c1[0]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i-1][j],"!=",0));}
						else if(numberN==4){c[0]=model.arithm(orientationP[i][j],op2,n);c1[0]=c[0];}
						else {c[0]=model.and(model.arithm(orientationP[i][j],op2,n),model.member(orientationP[i-1][j],new int[]{1,2}));c1[0]=model.and(model.arithm(orientationP[i][j],op1,n),model.member(orientationP[i-1][j],new int[]{0,3}));}
					}
					}else {
						/*if(number==0)c[0]=model.arithm(orientationP[i][j],"=",0);
					else */if(number==1||numberN==4) c[0]=model.arithm(orientationP[i][j],"!=",0);
					else if(number==2) c[0]=model.arithm(orientationP[i][j],"=",1);
					else if(number==3) c[0]=model.arithm(orientationP[i][j],"=",2);
					else c[0]=model.member(orientationP[i][j],new int [] {1,2});
					c1[0]=c[0];
					}

					if(i != height-1) {
						numberN = grid[i+1][j].getNumber();
						/*if(number==0) {
						if(numberN==0)c[2]=model.arithm(orientationP[i+1][j],"=",0);
						else if(numberN==1 ||numberN==2) c[2]=model.arithm(orientationP[i+1][j],"!=",0);
						else if(numberN==3) c[2]=model.arithm(orientationP[i+1][j],"=",2);
						else if(numberN==4) c[2]=model.falseConstraint();
						else c[2]=model.member(orientationP[i+1][j],new int[]{1,2});
						c1[2]=c[2];

					}else */if(number==5) {
						if(numberN==0) {c[2]=model.member(orientationP[i][j],new int[] {0,3});c1[2]=c[2];}
						else if(numberN==1 ||numberN==2 ) {c[2]=model.and(model.member(orientationP[i][j],new int[] {1,2}),model.arithm(orientationP[i+1][j],"=",0));c1[2]=model.and(model.member(orientationP[i][j],new int[] {0,3}),model.arithm(orientationP[i+1][j],"!=",0));}
						else if(numberN==4){c[2]=model.member(orientationP[i][j],new int[] {1,2});c1[2]=c[2];}
						else if(numberN==3) {c[2]=model.and(model.member(orientationP[i][j],new int[] {1,2}),model.arithm(orientationP[i+1][j],"!=",2));c1[2]=model.and(model.member(orientationP[i][j],new int[] {0,3}),model.arithm(orientationP[i+1][j],"=",2));}
						else {c[2]=model.and(model.member(orientationP[i][j],new int[] {1,2}),model.member(orientationP[i+1][j],new int[]{0,3}));c1[2]=model.and(model.member(orientationP[i][j],new int[] {0,3}),model.member(orientationP[i+1][j],new int[]{1,2}));}
					}else {
						if(number==1) {n=2;op1="!=";op2="=";}
						else if(number==2 ||number==4) {n=0;op1="!=";op2="=";}
						else if(number==3) {n=0;op1="=";op2="!=";}
						if(numberN==0) {c[2]=model.arithm(orientationP[i][j],op1,n);c1[2]=c[2];}
						else if(numberN==1 ||numberN==2) {c[2]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i+1][j],"=",0));c1[2]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i+1][j],"!=",0));}
						else if(numberN==4){c[2]=model.arithm(orientationP[i][j],op2,n);;c1[2]=c[2];}
						else if(numberN==3) {c[2]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i+1][j],"!=",2));c1[2]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i+1][j],"=",2));}
						else {c[2]=model.and(model.arithm(orientationP[i][j],op2,n),model.member(orientationP[i+1][j],new int[]{0,3}));c1[2]=model.and(model.arithm(orientationP[i][j],op1,n),model.member(orientationP[i+1][j],new int[]{1,2}));}
					}
					}else {
						if(/*number==0||*/number==3) c[2]=model.arithm(orientationP[i][j],"=",0);
						else if(number==1) c[2]=model.arithm(orientationP[i][j],"!=",2);
						else if(number==2) c[2]=model.arithm(orientationP[i][j],"=",1);
						else if(number==4) c[2]=model.arithm(orientationP[i][j],"!=",0);
						else c[2]=model.member(orientationP[i][j],new int [] {0,3});
						c1[2]=c[2];
					}

					if(j != 0) {
						numberN = grid[i][j-1].getNumber();
						/*if(number==0) {
						if(numberN==0)c[3]=model.arithm(orientationP[i][j-1],"=",0);
						else if(numberN==1 ||numberN==2) c[3]=model.arithm(orientationP[i][j-1],"!=",1);
						else if(numberN==3) c[3]=model.arithm(orientationP[i][j-1],"=",3);
						else if(numberN==4) c[3]=model.arithm(orientationP[i][j-1],"!=",0);
						else c[3]=model.member(orientationP[i][j-1],new int[]{2,3});
						c1[3]=c[3];

					}else*/ if(number==5) {
						if(numberN==0) {c[3]=model.member(orientationP[i][j],new int[] {0,1});c1[3]=c[3];}
						else if(numberN==1||numberN==2) {c[3]=model.and(model.member(orientationP[i][j],new int[] {2,3}),model.arithm(orientationP[i][j-1],"=",1));c1[3]=model.and(model.member(orientationP[i][j],new int[] {0,1}),model.arithm(orientationP[i][j-1],"!=",1));}
						else if(numberN==3){c[3]=model.and(model.member(orientationP[i][j],new int[] {2,3}),model.arithm(orientationP[i][j-1],"!=",3));c1[3]=model.and(model.member(orientationP[i][j],new int[] {0,1}),model.arithm(orientationP[i][j-1],"=",3));}
						else if(numberN==4) {c[3]=model.member(orientationP[i][j],new int[] {2,3});c1[3]=c[3];}
						else {c[3]=model.and(model.member(orientationP[i][j],new int[] {2,3}),model.member(orientationP[i][j-1],new int[]{0,1}));c1[3]=model.and(model.member(orientationP[i][j],new int[] {0,1}),model.member(orientationP[i][j-1],new int[]{2,3}));}
					}else {
						if(number==1) { n=3;;op1="!=";op2="=";}
						else if(number==2) {n=1;op1="!=";op2="=";}
						else if(number==3) {n=1;op1="=";op2="!=";}
						else if(number==4) {n=0;op1="!=";op2="=";}
						if(numberN==0) {c[3]=model.arithm(orientationP[i][j],op1,n);c1[3]=c[3];}
						else if(numberN==1||numberN==2) {c[3]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i][j-1],"=",1));c1[3]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i][j-1],"!=",1));}
						else if(numberN==3){c[3]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i][j-1],"!=",3));c1[3]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i][j-1],"=",3));}
						else if(numberN==4){c[3]=model.arithm(orientationP[i][j],op2,n);c1[3]=c[3];}
						else{c[3]=model.and(model.arithm(orientationP[i][j],op2,n),model.member(orientationP[i][j-1],new int[]{0,1}));c1[3]=model.and(model.arithm(orientationP[i][j],op1,n),model.member(orientationP[i][j-1],new int[]{2,3}));} 
					}
					}else {
						if(/*number==0 ||*/number==2) c[3]=model.arithm(orientationP[i][j],"=",0);
						else if(number==1) c[3]=model.arithm(orientationP[i][j],"!=",3);
						else if(number==3) c[3]=model.arithm(orientationP[i][j],"=",1);
						else if(number==4) c[3]=model.arithm(orientationP[i][j],"!=",0);
						else if(number==5) c[3]=model.member(orientationP[i][j],new int [] {0,1});
						c1[3]=c[3];
					}
					if(j != width-1) {
						numberN = grid[i][j+1].getNumber();
						/*if(number==0) {
						if(numberN==0) c[1]=model.arithm(orientationP[i][j+1],"=",0);
						else if(numberN==1) c[1]=model.arithm(orientationP[i][j+1],"!=",3);
						else if(numberN==2)  c[1]=model.arithm(orientationP[i][j+1],"!=",1);
						else if(numberN==3)  c[1]=model.arithm(orientationP[i][j+1],"=",1);
						else if(numberN==4) c[1]=model.arithm(orientationP[i][j+1],"!=",0);
						else c[1]=model.member(orientationP[i][j+1],new int[]{0,1});
						c1[1]=c[1];

					}else*/ if(number==5) {
						if(numberN==0){c[1]=model.member(orientationP[i][j],new int[] {2,3});c1[1]=c[1];}
						else if(numberN==1){c[1]=model.and(model.member(orientationP[i][j],new int[] {0,1}),model.arithm(orientationP[i][j+1],"=",3));c1[1]=model.and(model.member(orientationP[i][j],new int[] {2,3}),model.arithm(orientationP[i][j+1],"!=",3));}
						else if(numberN==2){c[1]=model.and(model.member(orientationP[i][j],new int[] {0,1}),model.arithm(orientationP[i][j+1],"=",1));c1[1]=model.and(model.member(orientationP[i][j],new int[] {2,3}),model.arithm(orientationP[i][j+1],"!=",1));}
						else if(numberN==3){c[1]=model.and(model.member(orientationP[i][j],new int[] {0,1}),model.arithm(orientationP[i][j+1],"!=",1));c1[1]=model.and(model.member(orientationP[i][j],new int[] {2,3}),model.arithm(orientationP[i][j+1],"=",1));}
						else if(numberN==4){c[1]=model.member(orientationP[i][j],new int[] {0,1});c1[1]=c[1];}
						else{c[1]=model.and(model.member(orientationP[i][j],new int[] {0,1}),model.member(orientationP[i][j+1],new int[]{2,3}));c1[1]=model.and(model.member(orientationP[i][j],new int[] {2,3}),model.member(orientationP[i][j+1],new int[]{0,1}));} 

					}else {
						if(number==1 || number==2) { n=1;op1="!=";op2="=";}
						else if(number==3) {n=3;op1="=";op2="!=";}
						else if(number==4){ n=0;op1="!=";op2="=";}
						if(numberN==0){c[1]=model.arithm(orientationP[i][j],op1,n);c1[1]=c[1];}
						else if(numberN==1){c[1]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i][j+1],"=",3));c1[1]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i][j+1],"!=",3));}
						else if(numberN==2){c[1]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i][j+1],"=",1));c1[1]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i][j+1],"!=",1));}
						else if(numberN==3) {c[1]=model.and(model.arithm(orientationP[i][j],op2,n),model.arithm(orientationP[i][j+1],"!=",1));c1[1]=model.and(model.arithm(orientationP[i][j],op1,n),model.arithm(orientationP[i][j+1],"=",1));}
						else if(numberN==4){c[1]=model.arithm(orientationP[i][j],op2,n);c1[1]=c[1];}
						else{c[1]=model.and(model.arithm(orientationP[i][j],op2,n),model.member(orientationP[i][j+1],new int[]{2,3}));c1[1]=model.and(model.arithm(orientationP[i][j],op1,n),model.member(orientationP[i][j+1],new int[]{0,1}));} 
					}
					}else {
						/*if(number==0) c[1]=model.arithm(orientationP[i][j],"=",0);
					else*/ if(number==1) c[1]=model.arithm(orientationP[i][j],"!=",1);
					else if(number==2) c[1]=model.arithm(orientationP[i][j],"=",0);
					else if(number==3) c[1]=model.arithm(orientationP[i][j],"=",3);
					else if(number==4) c[1]=model.arithm(orientationP[i][j],"!=",0);
					else c[1]=model.member(orientationP[i][j],new int [] {2,3});
					c1[1]=c[1];
					}

					model.and(model.or(c[0],c1[0]),model.or(c[1],c1[1]),model.or(c[2],c1[2]),model.or(c[3],c1[3])).post();
				}

			}
		}
		boolean solved=model.getSolver().solve();
		if(solved) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if(grid[i][j].getNumber()!=0) {
						grid[i][j].setOrientation(orientationP[i][j].getValue());
					}
				}
			}
		}
		return solved;
	}

	public void generateFile(String outputFile)
	{
		BufferedWriter b_out = null;
		FileWriter f_out = null;
		try
		{
			f_out = new FileWriter(outputFile);
			b_out = new BufferedWriter(f_out);

			b_out.write("" + height);
			b_out.newLine();
			b_out.write("" + width);
			b_out.newLine();
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					b_out.write(grid[y][x].getNumber() + " " + grid[y][x].getOrientation());
					b_out.newLine();
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(b_out != null) b_out.close();
				if(f_out != null) f_out.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}

		return;
	}


	public void printGrid()
	{
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				System.out.print(grid[y][x].toString());
			}
			System.out.println();
		}

		return;
	}

	public void endGame(Gui g)
	{
		g.showMessage();
		System.exit(0);
		return;
	}

	/*public static void main(String[] args)
	{
		
		//Solver test
		//Grid g = Grid.generateGridWithFile("instances/public/grid_8x8_dist.0_vflip.false_hflip.true_messedup.true_id.0.dat");
		Grid g = Grid.generateGridWithFile("test");
		g.printGrid();
		g.solve();
		g.printGrid();
		new Gui(g);

	}*/
}