package fr.dauphine.javaavance.phineloops; 

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.view.Gui;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * The PhineLoops program implements the "Infinite Loops" game, available on mobile devices via the Apple Store
 * and the Google Play Store. It has a level generator, a level solver and a level checker. The program is
 * launched based on the command line entered by the user.
 * @param inputFile Name of the input file
 * @param outputFile Name of the output file
 * @param width Width of the grid
 * @param height Height of the grid
 * @param maxcc Maximum number of connected spaces. Unused
 * 
 * @author Bruno Negrevergne, Taoufiq Kounaidi, Léa Ong, Duc-Chinh Pham
 * @version 2.1
 * @since 2019-12-29
 */
public class Main
{
	private static String inputFile = null;  
	private static String outputFile = null;
	private static Integer width = -1;
	private static Integer height = -1;
	@SuppressWarnings("unused")
	private static Integer maxcc = -1;
	
	/**
	 * This method generates a grid and is stored in a file.
	 * @see fr.dauphine.javaavance.phineloops.model.Grid
	 * @param width Width of the grid
	 * @param height Height of the grid
	 * @param outputFile Name of the output file
	 */
	private static void generate(int width, int height, String outputFile)
	{
		Grid g = Grid.generateGrid(width, height);
		g.generateFile(outputFile);
		
		return;
	}
	
	/**
	 * This method opens a grid from a file and tries to solve it. If one solution is solved, it will be
	 * stored in another file.
	 * @see fr.dauphine.javaavance.phineloops.model.Grid
	 * @param inputFile Name of the input file containing the grid to solve
	 * @param outputFile Name of the output file containing the solved grid
	 * @return boolean This returns if the grid was solved or not
	 */
	private static boolean solve(String inputFile, String outputFile)
	{
		Grid g = Grid.generateGridWithFile(inputFile);
		boolean solved = g.solve();
		if(solved) g.generateFile(outputFile);
		
		return solved;
	}
	
	/**
	 * This method opens a grid from a file and verifies if it is a solved one or not.
	 * @see fr.dauphine.javaavance.phineloops.model.Grid
	 * @param inputFile Name of the input file containing the grid to be verified
	 * @return boolean This returns if the grid is solved or not
	 */
	private static boolean check(String inputFile)
	{
		Grid g = Grid.generateGridWithFile(inputFile);
		
		return g.check(); 
	}
	
	/**
	 * The main method launches the program based on the command line entered by the user. Many options are
	 * available, which are managed with the Apache Commons CLI package.
	 * @see org.apache.commons.cli.CommandLine
	 * @see org.apache.commons.cli.CommandLineParser
 	 * @see org.apache.commons.cli.DefaultParser
 	 * @see org.apache.commons.cli.HelpFormatter
 	 * @see org.apache.commons.cli.Options
 	 * @see org.apache.commons.cli.ParseException
	 * @param args
	 */
	public static void main(String[] args)
	{
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;

		options.addOption("g", "generate ", true, "Generate a grid of size height x width.");
		options.addOption("c", "check", true, "Check whether the grid in <arg> is solved.");

		options.addOption("s", "solve", true, "Solve the grid stored in <arg>.");   
		options.addOption("o", "output", true, "Store the generated or solved grid in <arg>. (Use only with --generate and --solve.)");
		options.addOption("t", "threads", true, "Maximum number of solver threads. (Use only with --solve.)");
		options.addOption("x", "nbcc", true, "Maximum number of connected components. (Use only with --generate.)");
		options.addOption("G", "gui", false, "Run with the graphic user interface.");
		options.addOption("h", "help", false, "Display this help");

		try
		{
			cmd = parser.parse(options, args);         
		}
		catch (ParseException e)
		{
			System.err.println("Error: invalid command line format.");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("phineloops", options);
			System.exit(1);
		}       

		try
		{    
			if(cmd.hasOption("g")) // grid generator
			{
				System.out.println("Running phineloops generator.");
				String[] gridformat = cmd.getOptionValue("g").split("x");
				width = Integer.parseInt(gridformat[1]);
				height = Integer.parseInt(gridformat[0]); 
				if(!cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");
				outputFile = cmd.getOptionValue("o");
				if(cmd.hasOption("G")) 
				{
					Grid g = Grid.generateGrid(height, width);
					g.generateFile(outputFile);
					new Gui(g);
				}
				else 
				{
					generate(width, height, outputFile);  
					System.exit(0); // exit with success   
				}
			}
			else if(cmd.hasOption("s")) // grid solver
			{
				System.out.println("Running phineloops solver.");
				inputFile = cmd.getOptionValue("s");
				if(!cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");      
				outputFile = cmd.getOptionValue("o");
				if(cmd.hasOption("G")) 
				{
					Grid g = Grid.generateGridWithFile(inputFile);
					g.solve();
					g.generateFile(outputFile);
					new Gui(g);
				}
				else 
				{
					boolean solved = solve(inputFile, outputFile); 
					System.out.println("SOLVED: " + solved);   
					System.exit(0); // exit with success   
				}
			}
			else if(cmd.hasOption("c")) // guid checker
			{
				System.out.println("Running phineloops checker.");
				inputFile = cmd.getOptionValue("c");
				boolean solved = check(inputFile); 
				if(cmd.hasOption("G")) 
				{
					new Gui(Grid.generateGridWithFile(inputFile));
				}
				else 
				{
					System.out.println("SOLVED: " + solved);  
					System.exit(0); // exit with success   
				}
			}
			else 
			{
				throw new ParseException("You must specify at least one of the following options: -generate -check -solve ");           
			}
		} 
		catch(ParseException e) 
		{
			System.err.println("Error parsing commandline : " + e.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("phineloops", options);         
			System.exit(1); // exit with error      
		}
		
		return;
	}
}
