package fr.dauphine.javaavance.phineloops.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import fr.dauphine.javaavance.phineloops.view.Gui;

/**
 * The Grid class is the grid of the game.
 * @param grid A double array of Piece objets
 * @param height Height of the grid
 * @param width Width of the grid
 * @see Piece
 * 
 * @author Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 */
public class Grid
{
	private Piece[][] grid;
	private int height;
	private int width;

	public Grid(int height, int width)
	{
		this.height = height;
		this.width = width;
		grid = new Piece[this.height][this.width];
	}
	
	/**
	 * This getter returns a Piece object from a specific coordinate on the grid.
	 * @param x
	 * @param y
	 * @return Piece One piece of the grid on (x, y)
	 */
	public Piece getPiece(int x, int y)
	{
		return grid[y][x];
	}

	/**
	 * This getter returns the grid's height.
	 * @return int
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * This getter returns the grid's width.
	 * @return int
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * This method checks if a piece on (x, y) is compatible with its left and upper neighbor on the grid.
	 * @see Piece
	 * @param x
	 * @param y
	 * @return boolean If this piece is compatible or not
	 */
	private boolean isValid(int x, int y)
	{
		Piece piece = grid[y][x];
		// checks if the piece is compatible when it's near the border of the grid
		if((piece.getX() == 0 && piece.getConnection(Orientation.WEST) != null)
				|| (piece.getX() == width - 1 && piece.getConnection(Orientation.EAST) != null)
				|| (piece.getY() == 0 && piece.getConnection(Orientation.NORTH) != null)
				|| (piece.getY() == height - 1 && piece.getConnection(Orientation.SOUTH) != null))
			return false;
		if(piece.getX() > 0) // checks if the piece is compatible with the left neighbor
		{
			Boolean b = grid[piece.getY()][piece.getX() - 1].getConnection(Orientation.EAST);
			if((b != null && piece.getConnection(Orientation.WEST) == null)
					|| (b == null && piece.getConnection(Orientation.WEST) != null))
				return false;
		}
		if(piece.getY() > 0) // checks if the piece is compatible with the upper neighbor
		{
			Boolean b = grid[piece.getY() - 1][piece.getX()].getConnection(Orientation.SOUTH);
			if((b != null && piece.getConnection(Orientation.NORTH) == null)
					|| (b == null && piece.getConnection(Orientation.NORTH) != null))
				return false;
		}
		return true;
	}
	
	/**
	 * This method generates a random solvable grid with a given size.
	 * @see Piece
	 * @see java.util.Random
	 * @param height Height of the grid
	 * @param width Width of the grid
	 * @return Grid A solvable grid
	 */
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
				} while(!g.isValid(x, y)); // generates a random piece until it fits
			}
		}

		for(int y = 0; y < height; y++) // randomizes the pieces' orientation
		{
			for(int x = 0; x < width; x++)
				g.grid[y][x].setRandomOrientation();
		}

		return g;
	}
	
	/**
	 * This method generates a Grid object based on the file read.
	 * @see java.io.BufferedReader
	 * @see java.io.FileReader
	 * @see java.io.IOException
	 * @param inputFile Name of the input file
	 * @return Grid The file's grid
	 */
	public static Grid generateGridWithFile(String inputFile)
	{
		Grid g = null;
		BufferedReader b_in = null;
		FileReader f_in = null;
		String line;
		try 
		{
			f_in = new FileReader(inputFile);
			b_in = new BufferedReader(f_in);
			// parses the file and generates the grid based on the informations written in the file
			line = b_in.readLine();
			int height = Integer.parseInt(line);
			line = b_in.readLine();
			int width = Integer.parseInt(line);
			g = new Grid(height,width);
			int x = 0, y = 0;
			while((line = b_in.readLine()) != null)
			{
				int numberPiece = Character.getNumericValue(line.charAt(0));
				int numberOrientation = Character.getNumericValue(line.charAt(2));
				if(numberPiece == 0) g.grid[y][x] = new Empty(x, y);
				else if(numberPiece == 1) g.grid[y][x] = new OneConnection(numberOrientation, x, y);
				else if(numberPiece == 2) g.grid[y][x] = new I(numberOrientation, x, y);
				else if(numberPiece == 3) g.grid[y][x] = new T(numberOrientation, x, y);
				else if(numberPiece == 4) g.grid[y][x] = new X(x,y);
				else if(numberPiece == 5) g.grid[y][x] = new L(numberOrientation, x, y);
				if(x == width-1){ x = 0; y++; }
				else{ x++; }
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
				if(b_in != null) b_in.close();
				if(f_in != null) f_in.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}

		return g;
	}

	/**
	 * This method checks if the grid is solved or not.
	 * @return boolean If the grid is solved
	 */
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
	
	/**
	 * This method uses the Choco Solver package, which helps solving Constraint Satisfaction Problems (CSP).
	 * So it creates a model and defines constraints to solve a grid.
	 * @see org.chocosolver.solver.Model;
	 * @see org.chocosolver.solver.constraints.Constraint;
	 * @see org.chocosolver.solver.variables.IntVar;
	 * @return boolean If a solution was found or not
	 */
	public boolean solve() 
	{
		Model model = new Model("PhineLoops Solver");
		IntVar[][] orientationP = new IntVar[height][width];
		for(int i = 0; i < height; i++) // defines the range of possible orientations of a piece
		{
			for(int j = 0; j < width; j++) 
			{
				if(grid[i][j].getNumber() == 0 || grid[i][j].getNumber() == 4)
					orientationP[i][j] = model.intVar("ORIENTATION: (" + i + "," + j + ")", 0);
				else
					orientationP[i][j] = model.intVar("ORIENTATION: (" + i + "," + j + ")", 0, grid[i][j].getOrientationsMax()-1);
			}
		}
		
		int neighbor, n;
		String op1, op2;
		neighbor = n = 0;
		op1 = op2 = "";

		for(int i = 0; i < height; i++) 
		{
			for(int j = 0; j < width; j++) 
			{
				int pieceNumber = grid[i][j].getNumber();
				Constraint[] c = new Constraint[4];
				Constraint [] c1 = new Constraint[4];
				// for each square, define an array of square which will have as size the number of sides that can be connected
				// for each square, initialize values to make connections between the current piece and its neighbors
				if(pieceNumber != 0) 
				{
					if(i != 0) // define constraints with the upper neighbor
					{
						neighbor = grid[i-1][j].getNumber();
						if(pieceNumber == 5) 
						{
							if(neighbor == 0) 
							{
								c[0] = model.member(orientationP[i][j], new int[] {1, 2});
								c1[0] = c[0];
							}
							else if(neighbor == 1) 
							{
								c[0] = model.and(model.member(orientationP[i][j], new int[] {0, 3}),
										model.arithm(orientationP[i-1][j], "=", 2));
								c1[0] = model.and(model.member(orientationP[i][j], new int[] {1, 2}),
										model.arithm(orientationP[i-1][j], "!=", 2));
							}
							else if(neighbor == 2) 
							{
								c[0] = model.and(model.member(orientationP[i][j], new int[] {0, 3}),
										model.arithm(orientationP[i-1][j], "=", 0));
								c1[0] = model.and(model.member(orientationP[i][j], new int[] {1, 2}),
										model.arithm(orientationP[i-1][j], "!=", 0));
							}
							else if(neighbor == 3) 
							{
								c[0] = model.and(model.member(orientationP[i][j], new int[] {0, 3}),
										model.arithm(orientationP[i-1][j], "!=", 0));
								c1[0] = model.and(model.member(orientationP[i][j], new int[] {1, 2}),
										model.arithm(orientationP[i-1][j], "=", 0));
							}
							else if(neighbor == 4) 
							{
								c[0] = model.member(orientationP[i][j], new int[] {0, 3});
								c1[0] = c[0];
							}
							else 
							{
								c[0] = model.and(model.member(orientationP[i][j], new int[] {0, 3}),
										model.member(orientationP[i-1][j], new int[] {1, 2}));
								c1[0] = model.and(model.member(orientationP[i][j], new int[] {1, 2}),
										model.member(orientationP[i-1][j], new int[]{0, 3}));
							}
						}
						else 
						{
							if(pieceNumber == 1 ||pieceNumber == 2 || pieceNumber == 4)
							{ 
								n = 0; op1 = "!="; op2 = "="; 
							}
							else if(pieceNumber == 3) 
							{
								n = 2; op1 = "="; op2 = "!=";
							}
							if(neighbor == 0)
							{
								c[0] = model.arithm(orientationP[i][j], op1, n);
								c1[0] = c[0];
							}
							else if(neighbor == 1) 
							{
								c[0] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i-1][j], "=", 2));
								c1[0] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i-1][j], "!=", 2));
							}
							else if(neighbor == 2)	
							{
								c[0] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i-1][j], "=", 0));
								c1[0] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i-1][j], "!=", 0));
							}
							else if(neighbor == 3) 
							{
								c[0] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i-1][j], "!=", 0));
								c1[0] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i-1][j], "=", 0));
							}
							else if(neighbor == 4)
							{
								c[0] = model.arithm(orientationP[i][j], op2, n);
								c1[0] = c[0];
							}
							else 
							{
								c[0] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.member(orientationP[i-1][j], new int[] {1, 2}));
								c1[0] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.member(orientationP[i-1][j], new int[] {0, 3}));
							}
						}
					}
					else 
					{
						if(pieceNumber == 1 || neighbor == 4) c[0] = model.arithm(orientationP[i][j], "!=", 0);
						else if(pieceNumber == 2) c[0] = model.arithm(orientationP[i][j], "=", 1);
						else if(pieceNumber == 3) c[0] = model.arithm(orientationP[i][j], "=", 2);
						else c[0] = model.member(orientationP[i][j], new int[] {1, 2});
						c1[0] = c[0];
					}

					if(i != height-1) // define constraints with the lower neighbor
					{
						neighbor = grid[i+1][j].getNumber();
						if(pieceNumber == 5) 
						{
							if(neighbor == 0) 
							{
								c[2] = model.member(orientationP[i][j], new int[] {0, 3});
								c1[2] = c[2];
							}
							else if(neighbor == 1 || neighbor == 2) 
							{
								c[2] = model.and(model.member(orientationP[i][j], new int[] {1, 2}),
										model.arithm(orientationP[i+1][j], "=", 0));
								c1[2] = model.and(model.member(orientationP[i][j], new int[] {0, 3}),
										model.arithm(orientationP[i+1][j], "!=", 0));
							}
							else if(neighbor == 4)
							{
								c[2] = model.member(orientationP[i][j], new int[] {1, 2});
								c1[2] = c[2];
							}
							else if(neighbor == 3) 
							{
								c[2] = model.and(model.member(orientationP[i][j], new int[] {1, 2}),
										model.arithm(orientationP[i+1][j], "!=", 2));
								c1[2] = model.and(model.member(orientationP[i][j], new int[] {0, 3}),
										model.arithm(orientationP[i+1][j], "=", 2));
							}
							else 
							{
								c[2] = model.and(model.member(orientationP[i][j], new int[] {1, 2}),
										model.member(orientationP[i+1][j], new int[] {0, 3}));
								c1[2] = model.and(model.member(orientationP[i][j], new int[] {0, 3}),
										model.member(orientationP[i+1][j], new int[] {1, 2}));
							}
						}
						else 
						{
							if(pieceNumber == 1){ n = 2; op1 = "!="; op2 = "="; }
							else if(pieceNumber==2 || pieceNumber == 4){ n = 0; op1 = "!="; op2 = "="; }
							else if(pieceNumber == 3){ n = 0; op1 = "="; op2 = "!="; }
						
							if(neighbor == 0) 
							{
								c[2] = model.arithm(orientationP[i][j], op1, n);
								c1[2] = c[2];
							}
							else if(neighbor == 1 || neighbor == 2) 
							{
								c[2] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i+1][j], "=", 0));
								c1[2] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i+1][j], "!=", 0));
							}
							else if(neighbor == 3) 
							{
								c[2] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i+1][j], "!=", 2));
								c1[2] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i+1][j], "=", 2));
							}
							else if(neighbor == 4)
							{
								c[2] = model.arithm(orientationP[i][j], op2, n);
								c1[2] = c[2];
							}
							else 
							{
								c[2] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.member(orientationP[i+1][j], new int[] {0, 3}));
								c1[2] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.member(orientationP[i+1][j], new int[ ]{1, 2}));
							}
						}
					}
					else 
					{
						if(pieceNumber == 1) c[2] = model.arithm(orientationP[i][j], "!=", 2);
						else if(pieceNumber == 2) c[2] = model.arithm(orientationP[i][j], "=", 1);
						else if(pieceNumber == 3) c[2] = model.arithm(orientationP[i][j], "=", 0);
						else if(pieceNumber == 4) c[2] = model.arithm(orientationP[i][j], "!=", 0);
						else c[2] = model.member(orientationP[i][j], new int[] {0, 3});
						c1[2] = c[2];
					}

					if(j != 0) // define constraints with the left neighbor
					{
						neighbor = grid[i][j-1].getNumber();
						
						if(pieceNumber == 5) 
						{
							if(neighbor == 0) 
							{
								c[3] = model.member(orientationP[i][j], new int[] {0, 1});
								c1[3] = c[3];
							}
							else if(neighbor == 1 || neighbor == 2) 
							{
								c[3] = model.and(model.member(orientationP[i][j], new int[] {2, 3}),
										model.arithm(orientationP[i][j-1], "=", 1));
								c1[3] = model.and(model.member(orientationP[i][j], new int[] {0, 1}),
										model.arithm(orientationP[i][j-1], "!=", 1));
							}
							else if(neighbor == 3)
							{
								c[3] = model.and(model.member(orientationP[i][j], new int[] {2, 3}),
										model.arithm(orientationP[i][j-1], "!=", 3));
								c1[3] = model.and(model.member(orientationP[i][j], new int[] {0, 1}),
										model.arithm(orientationP[i][j-1], "=", 3));
							}
							else if(neighbor == 4) 
							{
								c[3] = model.member(orientationP[i][j], new int[] {2, 3});
								c1[3] = c[3];
							}
							else 
							{
								c[3] = model.and(model.member(orientationP[i][j], new int[] {2, 3}),
										model.member(orientationP[i][j-1], new int[] {0, 1}));
								c1[3] = model.and(model.member(orientationP[i][j], new int[] {0, 1}),
										model.member(orientationP[i][j-1], new int[] {2, 3}));
							}
						}
						else 
						{
							if(pieceNumber == 1){ n = 3; op1 = "!="; op2 = "="; }
							else if(pieceNumber == 2){ n = 1; op1 = "!="; op2 = "="; }
							else if(pieceNumber == 3){ n = 1; op1 = "="; op2 = "!="; }
							else if(pieceNumber == 4){ n = 0; op1 = "!="; op2 = "="; }
	
							if(neighbor == 0) 
							{
								c[3] = model.arithm(orientationP[i][j], op1, n);
								c1[3] = c[3];
							}
							else if(neighbor == 1 || neighbor == 2) 
							{
								c[3] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i][j-1], "=", 1));
								c1[3] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i][j-1], "!=", 1));
							}
							else if(neighbor == 3)
							{
								c[3] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i][j-1], "!=", 3));
								c1[3] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i][j-1], "=", 3));
							}
							else if(neighbor == 4)
							{
								c[3] = model.arithm(orientationP[i][j], op2, n);
								c1[3] = c[3];
							}
							else
							{
								c[3] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.member(orientationP[i][j-1], new int[] {0, 1}));
								c1[3] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.member(orientationP[i][j-1], new int[] {2, 3}));
							} 
						}
					}
					else 
					{
						if(pieceNumber == 1) c[3] = model.arithm(orientationP[i][j], "!=", 3);
						else if(pieceNumber == 2) c[3] = model.arithm(orientationP[i][j], "=", 0);
						else if(pieceNumber == 3) c[3] = model.arithm(orientationP[i][j], "=", 1);
						else if(pieceNumber == 4) c[3] = model.arithm(orientationP[i][j], "!=", 0);
						else if(pieceNumber == 5) c[3] = model.member(orientationP[i][j], new int[] {0, 1});
						c1[3] = c[3];
					}
					
					if(j != width-1) // define constraints with the right neighbor
					{
						neighbor = grid[i][j+1].getNumber();
						
						if(pieceNumber == 5) 
						{
							if(neighbor == 0)
							{
								c[1] = model.member(orientationP[i][j], new int[] {2, 3});
								c1[1] = c[1];
							}
							else if(neighbor == 1)
							{
								c[1] = model.and(model.member(orientationP[i][j], new int[] {0, 1}),
										model.arithm(orientationP[i][j+1], "=", 3));
								c1[1] = model.and(model.member(orientationP[i][j], new int[] {2, 3}),
										model.arithm(orientationP[i][j+1], "!=", 3));
							}
							else if(neighbor == 2)
							{
								c[1] = model.and(model.member(orientationP[i][j], new int[] {0, 1}),
										model.arithm(orientationP[i][j+1], "=", 1));
								c1[1] = model.and(model.member(orientationP[i][j], new int[] {2, 3}),
										model.arithm(orientationP[i][j+1], "!=", 1));
							}
							else if(neighbor == 3)
							{
								c[1] = model.and(model.member(orientationP[i][j], new int[] {0, 1}),
										model.arithm(orientationP[i][j+1], "!=", 1));
								c1[1] = model.and(model.member(orientationP[i][j], new int[] {2, 3}),
										model.arithm(orientationP[i][j+1], "=", 1));
							}
							else if(neighbor == 4)
							{
								c[1] = model.member(orientationP[i][j], new int[] {0, 1});
								c1[1] = c[1];
							}
							else
							{
								c[1] = model.and(model.member(orientationP[i][j], new int[] {0, 1}),
										model.member(orientationP[i][j+1], new int[] {2, 3}));
								c1[1] = model.and(model.member(orientationP[i][j], new int[] {2, 3}),
										model.member(orientationP[i][j+1], new int[] {0, 1}));
							}
						}
						else 
						{
							if(pieceNumber == 1 || pieceNumber == 2){ n = 1; op1 = "!="; op2 = "="; }
							else if(pieceNumber == 3){ n = 3; op1 = "="; op2 = "!="; }
							else if(pieceNumber == 4){ n = 0; op1 = "!="; op2 = "="; }
							
							if(neighbor == 0)
							{
								c[1] = model.arithm(orientationP[i][j], op1, n);
								c1[1] = c[1];
							}
							else if(neighbor == 1)
							{
								c[1] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i][j+1], "=", 3));
								c1[1] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i][j+1], "!=", 3));
							}
							else if(neighbor == 2)
							{
								c[1] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i][j+1], "=", 1));
								c1[1] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i][j+1], "!=", 1));
							}
							else if(neighbor == 3) 
							{
								c[1] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.arithm(orientationP[i][j+1], "!=", 1));
								c1[1] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.arithm(orientationP[i][j+1], "=", 1));
							}
							else if(neighbor == 4)
							{
								c[1] = model.arithm(orientationP[i][j], op2, n);
								c1[1] = c[1];
							}
							else
							{
								c[1] = model.and(model.arithm(orientationP[i][j], op2, n),
										model.member(orientationP[i][j+1], new int[] {2, 3}));
								c1[1] = model.and(model.arithm(orientationP[i][j], op1, n),
										model.member(orientationP[i][j+1], new int[] {0, 1}));
							} 
						}
					}
					else 
					{
						if(pieceNumber == 1) c[1] = model.arithm(orientationP[i][j], "!=", 1);
						else if(pieceNumber == 2) c[1] = model.arithm(orientationP[i][j], "=", 0);
						else if(pieceNumber == 3) c[1] = model.arithm(orientationP[i][j], "=", 3);
						else if(pieceNumber == 4) c[1] = model.arithm(orientationP[i][j], "!=", 0);
						else c[1] = model.member(orientationP[i][j], new int[] {2, 3});
						c1[1] = c[1];
					}
					model.and(model.or(c[0], c1[0]), model.or(c[1], c1[1]), model.or(c[2], c1[2]), model.or(c[3], c1[3])).post();
				}
			}
		}
		
		boolean solved = model.getSolver().solve(); // find a solution
		if(solved) // if found, refreshes the grid
		{
			for(int i = 0; i < height; i++) 
			{
				for(int j = 0; j < width; j++) 
				{
					if(grid[i][j].getNumber() != 0)
						grid[i][j].setOrientation(orientationP[i][j].getValue());
				}
			}
		}
		
		return solved;
	}

	/**
	 * This method stores the grid in a new file.
	 * @see import java.io.BufferedWriter
	 * @see java.io.FileWriter
	 * @see java.io.IOException
	 * @param outputFile Name of the output file
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

	/**
	 * This method prints the grid in the console.
	 * @see Piece
	 */
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

	/**
	 * This method is launched when a solution was found, then it shows an information message and stops
	 * the program.
	 * @param g Graphic user interface of the game
	 */
	public void endGame(Gui g)
	{
		g.showMessage();
		System.exit(0);
		
		return;
	}
}