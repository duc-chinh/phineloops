package fr.dauphine.javaavance.phineloops.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found or Error open file");
		}
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
				else if(numberPiece == 4) g.grid[y][x] = new X(numberOrientation, y);
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

	/*
	public boolean solve(int x, int y)
	{
		boolean solved = false;

		for(int i = 0; i < grid[y][x].getOrientationsMax(); i++)
		{
			grid[y][x].setOrientation(i);
			System.out.println("=== " + grid[y][x].getNumber() + " - " + grid[y][x].getOrientation() + " ===");
			if(isValid(x, y))
			{
				if(x == width - 1)
				{
					if(y == height - 1)
					{
						solved = true;
						break;
					}
					else
						solved = solve(0, y + 1);
				}
				else
					solved = solve(x + 1, y);
			}
			if(solved) break;
		}

		return solved;
	}
	 */
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

	public static void main(String[] args)
	{
		/* Generator test*/
		/*Grid g = Grid.generateGrid(5,5);
		g.generateFile("niveau1.txt");
		g.printGrid();
		new Gui(g);*/


		/*Check test*/
		Grid grid = Grid.generateGridWithFile("instances/public/grid_8x8_dist.0_vflip.false_hflip.false_messedup.false_id.3.dat");
		System.out.println("SOLVED:"+grid.check());
		new Gui(grid);

	}
}