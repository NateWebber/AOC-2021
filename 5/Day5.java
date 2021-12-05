import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 5 - 12/05/2021
* Problems 1 and 2
*/

public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        File inFile = new File("/home/nate/personal/advent2021/5/in.txt");
        Scanner fileReader = new Scanner(inFile);

        ArrayList<Tile> dangerList = new ArrayList<>();

        int[][] tiles = new int[1000][1000];

        while (fileReader.hasNextLine()) {

            parseLine(fileReader.nextLine(), tiles, dangerList);

        }

        fileReader.close();

        System.out.println(dangerList.size());
    }

    static void parseLine(String s, int[][] tiles, ArrayList<Tile> dangerList) {
        // System.out.printf("looking at line: %s\n", s);

        // all these lines are just dividing up the strings into the values we need
        String[] firstSplit = s.split(" ", -1);
        String[] firstStringCoords = firstSplit[0].split(",", -1);
        String[] secondStringCoords = firstSplit[2].split(",", -1);
        int firstX = Integer.parseInt(firstStringCoords[0]);
        int firstY = Integer.parseInt(firstStringCoords[1]);
        int secondX = Integer.parseInt(secondStringCoords[0]);
        int secondY = Integer.parseInt(secondStringCoords[1]);

        // System.out.printf("determined line goes from [%d, %d] to [%d, %d]\n", firstX,
        // firstY, secondX, secondY);

        /*
         * Next we have to increment all the appropriate positions in the master array
         * The way we determine what tiles are included in a line is largely the same
         * for all 4 directions, only slight adjustments are needed
         */

        // horizontal line
        if (firstY == secondY) {
            // these checks determine the specific direction the line moves
            if (firstX < secondX) {
                for (int i = firstX; i <= secondX; i++) {
                    // System.out.printf("adding tile %d %d\n", i, firstY);
                    tiles[i][firstY] += 1; // increment the position in the array, since the line has crossed it
                    // check for a dangerous tile
                    if (tiles[i][firstY] > 1) {
                        // System.out.printf("Danger!\n");
                        Tile dTile = new Tile(i, firstY);
                        boolean matchFound = false;
                        // if the dangerous tile is already know to us, we don't care about it
                        for (Tile t : dangerList) {
                            if (t.matchesTile(dTile)) {
                                // System.out.printf("tile already known as dangerous\n");
                                matchFound = true;
                            }
                        }
                        // this was a new dangerous tile!
                        if (!matchFound)
                            dangerList.add(dTile);
                    }
                }
            } else {
                for (int i = firstX; i >= secondX; i--) {
                    // System.out.printf("adding tile %d %d\n", i, firstY);
                    tiles[i][firstY] += 1;
                    if (tiles[i][firstY] > 1) {
                        // System.out.printf("Danger!\n");
                        Tile dTile = new Tile(i, firstY);
                        boolean matchFound = false;
                        for (Tile t : dangerList) {
                            if (t.matchesTile(dTile)) {
                                // System.out.printf("tile already known as dangerous\n");
                                matchFound = true;
                            }
                        }
                        if (!matchFound)
                            dangerList.add(dTile);
                    }
                }
            }
        }
        // vertical line
        else if (firstX == secondX) {
            if (firstY < secondY) {
                for (int i = firstY; i <= secondY; i++) {
                    // System.out.printf("adding tile %d %d\n", firstX, i);
                    tiles[firstX][i] += 1;
                    if (tiles[firstX][i] > 1) {
                        // System.out.printf("Danger!\n");
                        Tile dTile = new Tile(firstX, i);
                        boolean matchFound = false;
                        for (Tile t : dangerList) {
                            if (t.matchesTile(dTile)) {
                                // System.out.printf("tile already known as dangerous\n");
                                matchFound = true;
                            }
                        }
                        if (!matchFound)
                            dangerList.add(dTile);
                    }
                }
            } else {
                for (int i = firstY; i >= secondY; i--) {
                    // System.out.printf("adding tile %d %d\n", firstX, i);
                    tiles[firstX][i] += 1;
                    if (tiles[firstX][i] > 1) {
                        // System.out.printf("Danger!\n");
                        Tile dTile = new Tile(firstX, i);
                        boolean matchFound = false;
                        for (Tile t : dangerList) {
                            if (t.matchesTile(dTile)) {
                                // System.out.printf("tile already known as dangerous\n");
                                matchFound = true;
                            }
                        }
                        if (!matchFound)
                            dangerList.add(dTile);
                    }
                }
            }
        }
        /*
         * here be ̶d̶r̶a̶g̶o̶n̶s̶ diagonal lines
         * In the case of diagonal lines, we know that neither of the coordinates will
         * match
         * This means that the line could travel in any of 4 directions, up and right,
         * down and right, up and left, or down and left
         * We figure that out first by two layers of if statements comparing the
         * coordinates
         * Then, since we know diagonals will only be at 45 degree angles, stepping
         * along the lines isn't too hard
         */
        else {
            // moving right
            if (firstX < secondX) {
                // moving down and right
                if (firstY < secondY) {
                    for (int i = firstX, j = firstY; i <= secondX && j <= secondY; i++, j++) {
                        // System.out.printf("adding tile %d %d\n", firstX, i);
                        tiles[i][j] += 1;
                        if (tiles[i][j] > 1) {
                            // System.out.printf("Danger!\n");
                            Tile dTile = new Tile(i, j);
                            boolean matchFound = false;
                            for (Tile t : dangerList) {
                                if (t.matchesTile(dTile)) {
                                    // System.out.printf("tile already known as dangerous\n");
                                    matchFound = true;
                                }
                            }
                            if (!matchFound)
                                dangerList.add(dTile);
                        }
                    }
                }
                // moving up and right
                else {
                    for (int i = firstX, j = firstY; i <= secondX && j >= secondY; i++, j--) {
                        // System.out.printf("adding tile %d %d\n", firstX, i);
                        tiles[i][j] += 1;
                        if (tiles[i][j] > 1) {
                            // System.out.printf("Danger!\n");
                            Tile dTile = new Tile(i, j);
                            boolean matchFound = false;
                            for (Tile t : dangerList) {
                                if (t.matchesTile(dTile)) {
                                    // System.out.printf("tile already known as dangerous\n");
                                    matchFound = true;
                                }
                            }
                            if (!matchFound)
                                dangerList.add(dTile);
                        }
                    }
                }
            }
            // moving left
            else {
                // moving down and left
                if (firstY < secondY) {
                    for (int i = firstX, j = firstY; i >= secondX && j <= secondY; i--, j++) {
                        // System.out.printf("adding tile %d %d\n", firstX, i);
                        tiles[i][j] += 1;
                        if (tiles[i][j] > 1) {
                            // System.out.printf("Danger!\n");
                            Tile dTile = new Tile(i, j);
                            boolean matchFound = false;
                            for (Tile t : dangerList) {
                                if (t.matchesTile(dTile)) {
                                    // System.out.printf("tile already known as dangerous\n");
                                    matchFound = true;
                                }
                            }
                            if (!matchFound)
                                dangerList.add(dTile);
                        }
                    }
                }
                // moving up and left
                else {
                    for (int i = firstX, j = firstY; i >= secondX && j >= secondY; i--, j--) {
                        // System.out.printf("adding tile %d %d\n", firstX, i);
                        tiles[i][j] += 1;
                        if (tiles[i][j] > 1) {
                            // System.out.printf("Danger!\n");
                            Tile dTile = new Tile(i, j);
                            boolean matchFound = false;
                            for (Tile t : dangerList) {
                                if (t.matchesTile(dTile)) {
                                    // System.out.printf("tile already known as dangerous\n");
                                    matchFound = true;
                                }
                            }
                            if (!matchFound)
                                dangerList.add(dTile);
                        }
                    }
                }
            }

        }
    }

    static class Tile {
        private int x;
        private int y;

        public int[] getCoords() {
            return new int[] { this.x, this.y };
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public boolean matchesTile(Tile t) {
            return (this.x == t.getX()) && (this.y == t.getY());
        }

        public Tile(int nX, int nY) {
            this.x = nX;
            this.y = nY;
        }

        public void printTile() {
            System.out.printf("[%d, %d]\n", this.x, this.y);
        }
    }
}
