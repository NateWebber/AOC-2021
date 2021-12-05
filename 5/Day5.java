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

//the biggest O there ever was
public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        File inFile = new File("/home/nate/personal/advent2021/5/in.txt");
        Scanner fileReader = new Scanner(inFile);

        ArrayList<Tile> tilesList = new ArrayList<>();
        /*
         * String testLine = fileReader.nextLine();
         * String[] testSplit = testLine.split(" ", -1);
         * for (String s : testSplit)
         * System.out.println(s);
         * String[] secondSplit = testSplit[0].split(",", -1);
         * for (String s : secondSplit)
         * System.out.println(s);
         */

        while (fileReader.hasNextLine()) {

            ArrayList<Tile> newTiles = parseLine(fileReader.nextLine());
            /*
             * for (Tile t : newTiles)
             * t.printTile();
             */
            for (Tile t : newTiles) {
                boolean matched = false;
                for (Tile x : tilesList) {
                    if (x.matchesTile(t)) {
                        x.incrementCount();
                        matched = true;
                    }
                }
                if (!matched) {
                    tilesList.add(t);
                }
            }
        }
        int dangerCount = 0;
        for (Tile t : tilesList) {
            if (t.getCount() > 1) {
                dangerCount += 1;
            }
        }
        System.out.println(dangerCount);
    }

    static ArrayList<Tile> parseLine(String s) {
        ArrayList<Tile> returnList = new ArrayList<>();
        // System.out.printf("looking at line: %s\n", s);
        String[] firstSplit = s.split(" ", -1);
        String[] firstStringCoords = firstSplit[0].split(",", -1);
        String[] secondStringCoords = firstSplit[2].split(",", -1);
        int firstX = Integer.parseInt(firstStringCoords[0]);
        int firstY = Integer.parseInt(firstStringCoords[1]);
        int secondX = Integer.parseInt(secondStringCoords[0]);
        int secondY = Integer.parseInt(secondStringCoords[1]);

        // System.out.printf("determined line goes from [%d, %d] to [%d, %d]\n", firstX,
        // firstY, secondX, secondY);
        // horizontal line
        if (firstY == secondY) {
            if (firstX < secondX) {
                for (int i = firstX; i <= secondX; i++) {
                    // System.out.printf("adding tile %d %d\n", i, firstY);
                    returnList.add(new Tile(i, firstY));
                }
            } else {
                for (int i = firstX; i >= secondX; i--) {
                    // System.out.printf("adding tile %d %d\n", i, firstY);
                    returnList.add(new Tile(i, firstY));
                }
            }
        }
        // vertical line
        else if (firstX == secondX) {
            if (firstY < secondY) {
                for (int i = firstY; i <= secondY; i++) {
                    // System.out.printf("adding tile %d %d\n", firstX, i);
                    returnList.add(new Tile(firstX, i));
                }
            } else {
                for (int i = firstY; i >= secondY; i--) {
                    // System.out.printf("adding tile %d %d\n", firstX, i);
                    returnList.add(new Tile(firstX, i));
                }
            }
        }
        return returnList;
    }

    static class Tile {
        private int x;
        private int y;
        private int count;

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
            this.count = 1;
        }

        public void incrementCount() {
            this.count += 1;
        }

        public int getCount() {
            return this.count;
        }

        public void printTile() {
            System.out.printf("[%d, %d]\n", this.x, this.y);
        }
    }
}
