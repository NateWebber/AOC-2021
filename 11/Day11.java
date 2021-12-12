import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 11 - 12/11/2021
* Problems 1 and 2
*/

public class Day11{
	public static void main(String[] args) throws FileNotFoundException{
		File inFile = new File("/home/nate/dev/personal/AOC-2021/11/in.txt");
		Scanner reader = new Scanner(inFile);

		ArrayList<String> inputLines = new ArrayList<>();

		while (reader.hasNextLine())
			inputLines.add(reader.nextLine());

		int lineCount = inputLines.size();
		int lineLength = inputLines.get(0).length();

		int[][] grid = new int[lineCount][lineLength];
		boolean[][] flashGrid = new boolean[lineCount][lineLength];

		clearFlashGrid(flashGrid);
		
		//populate the grid from input
		for (int y = 0; y < lineCount; y++){
			String inLine = inputLines.get(y);
			for (int x = 0; x < lineLength; x++){
				char c = inLine.charAt(x);
				grid[x][y] = Character.getNumericValue(c);
			}
		}
		int finalSum = 0;
		for (int i = 0; i < 500; i++){
			//System.out.printf("Step %d: Sum = %d\n", i, finalSum);
			//printGrid(grid);
			incrementGrid(grid);
			finalSum += doFlashes(grid, flashGrid);
			if (allFlashed(flashGrid))
				System.out.println("Problem 2: " + i);
			resetFlashed(grid, flashGrid);
			clearFlashGrid(flashGrid);
		}
		System.out.println("Problem 1: " + finalSum);
		reader.close();
	}

	static void clearFlashGrid(boolean[][] flashGrid){
		for(int y = 0; y < flashGrid[0].length; y++)
			for(int x = 0; x < flashGrid[0].length; x++)
				flashGrid[x][y] = false;
	}

	static void incrementGrid(int[][] grid){
		for(int y = 0; y < grid[0].length; y++)
			for(int x = 0; x < grid[0].length; x++)
					grid[x][y] += 1;
	}

	static int doFlashes(int[][] grid, boolean[][] flashGrid){
		int sum = 0;
		for(int y = 0; y < grid.length; y++){
			for(int x = 0; x < grid[0].length; x++){
				sum += tryFlash(grid, flashGrid, x, y);
			}
		}
		return sum;
	}

	static int tryFlash(int[][] grid, boolean[][] flashGrid, int x, int y){
		int lineCount = grid.length;
		int lineLength = grid[0].length;
		int sum = 0;
		if ((!flashGrid[x][y]) && (grid[x][y] > 9)){
			flashGrid[x][y] = true;
			sum += 1;
			//up
			if (y > 0){
				grid[x][y - 1] += 1;
				sum += tryFlash(grid, flashGrid, x, y - 1);
			}
			//down
			if (y < lineCount - 1){
				grid[x][y + 1] += 1;
				sum += tryFlash(grid, flashGrid, x, y + 1);
			}
			//left
			if (x > 0){
				grid[x - 1][y] += 1;
				sum += tryFlash(grid, flashGrid, x - 1, y);
			}
			//right
			if (x < lineLength - 1){
				grid[x + 1][y] += 1;
				sum += tryFlash(grid, flashGrid, x + 1, y);
			}
			//up + left
			if (y > 0 && x > 0){
				grid[x - 1][y - 1] += 1;
				sum += tryFlash(grid, flashGrid, x - 1, y - 1);
			}
			//up + right
			if (y > 0 && x < lineLength - 1){
				grid[x + 1][y - 1] += 1;
				sum += tryFlash(grid, flashGrid, x + 1, y - 1);
			}
			//down + left
			if (y < lineCount - 1 && x > 0){
				grid[x - 1][y + 1] += 1;
				sum += tryFlash(grid, flashGrid, x - 1, y + 1);
			}
			//down + right
			if (y < lineCount - 1 && x < lineLength - 1){
				grid[x + 1][y + 1] += 1;
				sum += tryFlash(grid, flashGrid, x + 1, y + 1);
			}
		}
		return sum;
	}

	static void resetFlashed(int[][] grid, boolean[][] flashGrid){
		for (int y = 0; y < grid.length; y++){
			for (int x = 0; x < grid[0].length; x++){
				if (flashGrid[x][y])
					grid[x][y] = 0;
			}
		}
	}

	static void printGrid(int[][] grid){
		for (int y = 0; y < grid.length; y++){
			System.out.println("");
			for (int x = 0; x < grid[0].length; x++)
				System.out.print(grid[x][y]);
		}
		System.out.println("");
	}

	static boolean allFlashed(boolean[][] flashGrid){
		for (int y = 0; y < flashGrid.length; y++)
			for (int x = 0; x < flashGrid[0].length; x++)
				if (!flashGrid[x][y])
					return false;
		return true;
	}
}
		