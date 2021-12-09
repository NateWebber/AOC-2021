import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 9 - 12/9/2021
* Problems 1 and 2
*/

/*
* Don't do recursion kids
*/
public class Day9 {
	// the main function is just parsing the input and calling the functions that
	// solve the problems
	public static void main(String[] args) throws FileNotFoundException {
		File inFile = new File("/home/nate/personal/advent2021/9/in.txt");
		Scanner reader = new Scanner(inFile);

		ArrayList<String> inLines = new ArrayList<>();

		while (reader.hasNextLine())
			inLines.add(reader.nextLine());

		int lineLength = inLines.get(0).length();
		int lineCount = inLines.size();

		int[][] grid = new int[lineCount][lineLength];

		for (int i = 0; i < lineCount; i++) {
			String currLine = inLines.get(i);
			// System.out.println("parsing line: " + currLine);
			for (int j = 0; j < lineLength; j++) {
				// System.out.println("parsing char: " + currLine.charAt(j));
				grid[i][j] = Character.getNumericValue(currLine.charAt(j));
			}
		}

		int[] solutions = solveProblems(grid, lineCount, lineLength);

		System.out.printf("Problem 1: %d\n", solutions[0]);

		System.out.printf("Problem 2: %d\n", solutions[1]);

		reader.close();
	}

	/*
	 * solveProblems() solves problem one in its entireity by locating low points
	 * and adding them to the sum variable
	 * Whenever we find a low point, we add to the basinList with findBasin(), which
	 * begins recursive enumeration of the basins
	 */
	static int[] solveProblems(int[][] grid, int lineCount, int lineLength) {
		int[] solutions = new int[2];

		int sum = 0; // sum of low point danger values
		ArrayList<Integer> basinList = new ArrayList<>(); // will store the sizes of the basins
		/*
		 * As we step through the entire grid, there are assorted if statements to
		 * figure out what part of the grid we're in
		 * This is because we don't want to run off the end of the grid (e.g. if we're
		 * in the top left corner, we only go right and down)
		 * The basic idea is the same though. Determine a low point by checking its
		 * neighbors to see if it's less than all of them
		 */
		for (int i = 0; i < lineCount; i++) {
			for (int j = 0; j < lineLength; j++) {
				// System.out.println("i, j: " + i + " " + j);
				int currInt = grid[i][j];
				// top left corner
				if (i == 0 && j == 0) {
					// System.out.println("should be here");
					if ((grid[i + 1][j] > currInt) && (grid[i][j + 1] > currInt)) {
						// System.out.printf("low point: %d %d: %d\n", i, j, currInt);
						sum += (currInt + 1);
						// System.out.printf("current sum: %d\n", sum);
						basinList.add(findBasin(grid, i, j, lineCount, lineLength));
						continue;
					}
					continue;
				}
				// top right corner
				if (i == 0 && j == (lineLength - 1)) {
					if ((grid[i + 1][j] > currInt) && (grid[i][j - 1] > currInt)) {
						// System.out.printf("low point: %d %d: %d\n", i, j, currInt);
						sum += (currInt + 1);
						// System.out.printf("current sum: %d\n", sum);
						basinList.add(findBasin(grid, i, j, lineCount, lineLength));
						continue;
					}
					continue;
				}
				// bottom left corner
				if (i == (lineCount - 1) && j == 0) {
					if ((grid[i - 1][j] > currInt) && (grid[i][j + 1] > currInt)) {
						// System.out.printf("low point: %d %d: %d\n", i, j, currInt);
						sum += (currInt + 1);
						// System.out.printf("current sum: %d\n", sum);
						basinList.add(findBasin(grid, i, j, lineCount, lineLength));
						continue;
					}
					continue;
				}
				// bottom right corner
				if (i == (lineCount - 1) && j == (lineLength - 1)) {
					if ((grid[i - 1][j] > currInt) && (grid[i][j - 1] > currInt)) {
						// System.out.printf("low point: %d %d: %d\n", i, j, currInt);
						sum += (currInt + 1);
						// System.out.printf("current sum: %d\n", sum);
						basinList.add(findBasin(grid, i, j, lineCount, lineLength));
						continue;
					}
					continue;
				}
				// top row
				if (i == 0) {
					if ((grid[i + 1][j] > currInt) && (grid[i][j - 1] > currInt)
							&& (grid[i][j + 1] > currInt)) {
						// System.out.printf("low point: %d %d: %d\n", i, j, currInt);
						sum += (currInt + 1);
						// System.out.printf("current sum: %d\n", sum);
						basinList.add(findBasin(grid, i, j, lineCount, lineLength));
						continue;
					}
					continue;
				}
				// bottom row
				if (i == (lineCount - 1)) {
					if ((grid[i - 1][j] > currInt) && (grid[i][j - 1] > currInt)
							&& (grid[i][j + 1] > currInt)) {
						// System.out.printf("low point: %d %d: %d\n", i, j, currInt);
						sum += (currInt + 1);
						// System.out.printf("current sum: %d\n", sum);
						basinList.add(findBasin(grid, i, j, lineCount, lineLength));
						continue;
					}
					continue;
				}

				// left side
				if (j == 0) {
					if ((grid[i + 1][j] > currInt) && (grid[i - 1][j] > currInt)
							&& (grid[i][j + 1] > currInt)) {
						// System.out.printf("low point: %d %d: %d\n", i, j, currInt);
						sum += (currInt + 1);
						// System.out.printf("current sum: %d\n", sum);
						basinList.add(findBasin(grid, i, j, lineCount, lineLength));
						continue;
					}
					continue;
				}

				// right side
				if (j == (lineLength - 1)) {
					if ((grid[i + 1][j] > currInt) && (grid[i - 1][j] > currInt)
							&& (grid[i][j - 1] > currInt)) {

						// System.out.printf("low point: %d %d: %d\n", i, j, currInt);
						sum += (currInt + 1);
						// System.out.printf("current sum: %d\n", sum);
						basinList.add(findBasin(grid, i, j, lineCount, lineLength));
						continue;
					}
					continue;
				}

				if ((grid[i - 1][j] > currInt) && (grid[i + 1][j] > currInt) && (grid[i][j - 1] > currInt)
						&& (grid[i][j + 1] > currInt)) {
					// System.out.printf("low point: %d %d: %d\n", i, j, currInt);
					sum += (currInt + 1);
					// System.out.printf("current sum: %d\n", sum);
					basinList.add(findBasin(grid, i, j, lineCount, lineLength));
					continue;
				}
			}
		}
		Collections.sort(basinList, Collections.reverseOrder()); // put basin sizes into descending order
		solutions[0] = sum;
		solutions[1] = (basinList.get(0) * basinList.get(1) * basinList.get(2)); // multiply the 3 largest basins

		return solutions;
	}

	/*
	 * findBasin() is small. It sets up and starts the recursive process of
	 * enumerating a basin
	 * We technically only care how big a basin is, but we still have to keep track
	 * of which specific tiles are in there so we don't accidentally count tiles
	 * more than once
	 */
	static int findBasin(int[][] grid, int i, int j, int lineCount, int lineLength) {
		ArrayList<Coords> coordsList = new ArrayList<>(); // this is the list that will be modified during recurison
		// coordsList.add(new Coords(i, j));
		basinRecursion(grid, i, j, coordsList, lineCount, lineLength); // start recursion
		return coordsList.size(); // we only care about how big a basin is
	}

	/*
	 * If we enter basinRecursion(), we can guarantee that the current tile is valid
	 * First we check if we've already been to this tile, because in that case we
	 * can just stop right away
	 * Otherwise, we add the current tile to the list, and like in findSolutions(),
	 * based on what part of the grid we're on, we check the neighbors and call
	 * basinRecursion() again
	 */
	static void basinRecursion(int[][] grid, int i, int j, ArrayList<Coords> coordsList, int lineCount,
			int lineLength) {
		// already here
		if (containsCoords(coordsList, new Coords(i, j))) {
			return;
		}
		int currInt = grid[i][j];
		coordsList.add(new Coords(i, j));

		// top left corner
		if (i == 0 && j == 0) {
			// go right
			if (!(grid[i][j + 1] < currInt || grid[i][j + 1] == 9)) {
				basinRecursion(grid, i, j + 1, coordsList, lineCount, lineLength);
			}
			// go down
			if (!(grid[i + 1][j] < currInt || grid[i + 1][j] == 9)) {
				basinRecursion(grid, i + 1, j, coordsList, lineCount, lineLength);
			}
			return;
		}
		// top right corner
		if (i == 0 && j == (lineLength - 1)) {
			// go down
			if (!(grid[i + 1][j] < currInt || grid[i + 1][j] == 9)) {
				basinRecursion(grid, i + 1, j, coordsList, lineCount, lineLength);
			}
			// go left
			if (!(grid[i][j - 1] < currInt || grid[i][j - 1] == 9)) {
				basinRecursion(grid, i, j - 1, coordsList, lineCount, lineLength);
			}
			return;
		}
		// bottom left corner
		if (i == (lineCount - 1) && j == 0) {
			// go up
			if (!(grid[i - 1][j] < currInt || grid[i - 1][j] == 9)) {
				basinRecursion(grid, i - 1, j, coordsList, lineCount, lineLength);
			}
			// go right
			if (!(grid[i][j + 1] < currInt || grid[i][j + 1] == 9)) {
				basinRecursion(grid, i, j + 1, coordsList, lineCount, lineLength);
			}
			return;
		}
		// bottom right corner
		if (i == (lineCount - 1) && j == (lineLength - 1)) {
			// go left
			if (!(grid[i][j - 1] < currInt || grid[i][j - 1] == 9)) {
				basinRecursion(grid, i, j - 1, coordsList, lineCount, lineLength);
			}
			// go up
			if (!(grid[i - 1][j] < currInt || grid[i - 1][j] == 9)) {
				basinRecursion(grid, i - 1, j, coordsList, lineCount, lineLength);
			}
			return;
		}
		// top row
		if (i == 0) {
			// go right
			if (!(grid[i][j + 1] < currInt || grid[i][j + 1] == 9)) {
				basinRecursion(grid, i, j + 1, coordsList, lineCount, lineLength);
			}
			// go left
			if (!(grid[i][j - 1] < currInt || grid[i][j - 1] == 9)) {
				basinRecursion(grid, i, j - 1, coordsList, lineCount, lineLength);
			}
			// go down
			if (!(grid[i + 1][j] < currInt || grid[i + 1][j] == 9)) {
				basinRecursion(grid, i + 1, j, coordsList, lineCount, lineLength);
			}
			return;

		}
		// bottom row
		if (i == (lineCount - 1)) {
			// go right
			if (!(grid[i][j + 1] < currInt || grid[i][j + 1] == 9)) {
				basinRecursion(grid, i, j + 1, coordsList, lineCount, lineLength);
			}
			// go up
			if (!(grid[i - 1][j] < currInt || grid[i - 1][j] == 9)) {
				basinRecursion(grid, i - 1, j, coordsList, lineCount, lineLength);
			}
			// go left
			if (!(grid[i][j - 1] < currInt || grid[i][j - 1] == 9)) {
				basinRecursion(grid, i, j - 1, coordsList, lineCount, lineLength);
			}
			return;

		}

		// left side
		if (j == 0) {
			// go right
			if (!(grid[i][j + 1] < currInt || grid[i][j + 1] == 9)) {
				basinRecursion(grid, i, j + 1, coordsList, lineCount, lineLength);
			}
			// go down
			if (!(grid[i + 1][j] < currInt || grid[i + 1][j] == 9)) {
				basinRecursion(grid, i + 1, j, coordsList, lineCount, lineLength);
			}
			// go up
			if (!(grid[i - 1][j] < currInt || grid[i - 1][j] == 9)) {
				basinRecursion(grid, i - 1, j, coordsList, lineCount, lineLength);
			}
			return;

		}

		// right side
		if (j == (lineLength - 1)) {
			// go down
			if (!(grid[i + 1][j] < currInt || grid[i + 1][j] == 9)) {
				basinRecursion(grid, i + 1, j, coordsList, lineCount, lineLength);
			}
			// go left
			if (!(grid[i][j - 1] < currInt || grid[i][j - 1] == 9)) {
				basinRecursion(grid, i, j - 1, coordsList, lineCount, lineLength);
			}
			// go up
			if (!(grid[i - 1][j] < currInt || grid[i - 1][j] == 9)) {
				basinRecursion(grid, i - 1, j, coordsList, lineCount, lineLength);
			}
			return;

		}
		// none of the above
		// go down
		if (!(grid[i + 1][j] < currInt || grid[i + 1][j] == 9)) {
			basinRecursion(grid, i + 1, j, coordsList, lineCount, lineLength);
		}
		// go right
		if (!(grid[i][j + 1] < currInt || grid[i][j + 1] == 9)) {
			basinRecursion(grid, i, j + 1, coordsList, lineCount, lineLength);
		}
		// go up
		if (!(grid[i - 1][j] < currInt || grid[i - 1][j] == 9)) {
			basinRecursion(grid, i - 1, j, coordsList, lineCount, lineLength);
		}
		// go left
		if (!(grid[i][j - 1] < currInt || grid[i][j - 1] == 9)) {
			basinRecursion(grid, i, j - 1, coordsList, lineCount, lineLength);
		}
	}

	/*
	 * Quick helper function to see if a list of Coords has the specific Coords 'c'
	 * in it
	 */
	static boolean containsCoords(ArrayList<Coords> list, Coords c) {
		for (Coords x : list) {
			if (x.matchesOther(c))
				return true;
		}
		return false;
	}

	/*
	 * The Coords class is just a convenient container for a set of x, y coordinates
	 */
	static class Coords {
		private int x, y;

		public Coords(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}

		public boolean matchesOther(Coords c) {
			return ((this.x == c.getX()) && (this.y == c.getY()));
		}

	}
}
