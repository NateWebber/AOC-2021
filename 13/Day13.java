import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 13 - 12/16/2021
* Problems 1 and 2
*/

public class Day13 {
	public static void main(String[] args) throws FileNotFoundException {
		File inFile = new File("/home/nate/personal/advent2021/13/in.txt");
		Scanner reader = new Scanner(inFile);

		/* INPUT PARSING */
		ArrayList<String> coordLines = new ArrayList<>();
		ArrayList<String> instructionLines = new ArrayList<>();
		boolean coordsParsed = false;

		while (reader.hasNextLine()) {
			String nLine = reader.nextLine();
			if (nLine.equals("")) {
				coordsParsed = true;
			} else if (!coordsParsed)
				coordLines.add(nLine);
			else
				instructionLines.add(nLine);

		}

		/*
		 * System.out.println("Coord Lines:");
		 * for (String s : coordLines)
		 * System.out.println(s);
		 * System.out.println("Instruction Lines:");
		 * for (String s : instructionLines)
		 * System.out.println(s);
		 */

		reader.close();
		/* END INPUT PARSING */

		char[][] grid = setupGrid(coordLines);

		// printGrid(grid);

		for (int i = 0; i < instructionLines.size(); i++){
			String instruction = instructionLines.get(i);
			boolean vertFold = (instruction.charAt(11) == 'y');
			if (vertFold)
				grid = foldVertical(grid, Integer.parseInt(instruction.substring(13)));
			else
				grid = foldHorizontal(grid, Integer.parseInt(instruction.substring(13)));
			System.out.printf("Dot total after %d fold(s): %d\n", i + 1, countDots(grid));
		}
		
		printGrid(grid);

	}

	static char[][] setupGrid(ArrayList<String> coordLines) {
		/* GRID SETUP */
		int maxX = 0;
		int maxY = 0;
		ArrayList<int[]> coordsList = new ArrayList<>();

		for (String s : coordLines) {
			int[] coords = Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray();
			coordsList.add(coords);
			if (coords[0] > maxX)
				maxX = coords[0];
			if (coords[1] > maxY)
				maxY = coords[1];
		}

		// grid is 0 indexed, so we need one extra space to account for it
		maxX += 1;
		maxY += 1;

		// System.out.printf("Making grid with maxX = %d maxY = %d\n", maxX, maxY);
		char[][] grid = new char[maxY][maxX];

		for (int y = 0; y < maxY; y++)
			for (int x = 0; x < maxX; x++)
				grid[y][x] = '.';

		for (int[] coords : coordsList) {
			grid[coords[1]][coords[0]] = '#';
		}

		return grid;
	}

	static void printGrid(char[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			System.out.println("");
			for (int x = 0; x < grid[0].length; x++) {
				System.out.print(grid[y][x] + " ");
			}
		}
	}

	static int countDots(char[][] grid) {
		int sum = 0;
		for (int y = 0; y < grid.length; y++)
			for (int x = 0; x < grid[0].length; x++)
				if (grid[y][x] == '#')
					sum += 1;
		return sum;
	}

	static char[][] foldVertical(char[][] grid, int line) {
		// assumes we've been given a "fold along x = ?" line
		// NB: We always fold left

		int newY = (grid.length - (grid.length - line));

		char[][] newGrid = new char[newY][grid[0].length];

		// first copy the unchanged contents (i.e. the top portion of the grid)
		for (int y = 0; y < newY; y++)
			for (int x = 0; x < grid[0].length; x++)
				newGrid[y][x] = grid[y][x];

		for (int y = line + 1; y < grid.length; y++) {
			int offset = 2 * (y - line);
			for (int x = 0; x < grid[0].length; x++)
				if (grid[y][x] == '#')
					newGrid[y - offset][x] = '#';
		}

		return newGrid;
	}

	static char[][] foldHorizontal(char[][] grid, int line) {
		// assumes we've been given a "fold along x = ?" line
		// NB: We always fold left

		int newX = (grid[0].length - (grid[0].length - line));

		char[][] newGrid = new char[grid.length][newX];

		// first copy the unchanged contents (i.e. the top portion of the grid)
		for (int y = 0; y < grid.length; y++)
			for (int x = 0; x < newX; x++)
				newGrid[y][x] = grid[y][x];

		for (int x = line + 1; x < grid[0].length; x++) {
			int offset = 2 * (x - line);
			for (int y = 0; y < grid.length; y++)
				if (grid[y][x] == '#')
					newGrid[y][x - offset] = '#';
		}

		return newGrid;
	}
}
