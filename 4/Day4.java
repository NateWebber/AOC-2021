import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 4 - 12/04/2021
* Problems 1 and 2
*/

public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        File inFile = new File("/home/nate/personal/advent2021/4/in.txt");
        Scanner fileScanner = new Scanner(inFile);

        // the first line of the input is all the called numbers
        String numbersLine = fileScanner.nextLine();

        // split numbers into a list of strings
        List<String> calledNumberStrings = Arrays.asList(numbersLine.split(",", -1));

        ArrayList<Integer> calledNumbers = new ArrayList<>();

        // convert into a list of ints
        for (String s : calledNumberStrings)
            calledNumbers.add(Integer.parseInt(s));

        ArrayList<BingoBoard> boards = new ArrayList<>();

        // populate boards list with boards using parseBoard() function
        while (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
            String[] newLines = new String[5];
            for (int i = 0; i < 5; i++) {
                newLines[i] = fileScanner.nextLine();
            }
            boards.add(parseBoard(newLines));
        }

        // print the answer to problem 2 to System.out
        problem1(boards, calledNumbers);

        // print the answer to problem 2 to System.out
        problem2(boards, calledNumbers);

        fileScanner.close();
    }

    /*
     * Problem 1 is relatively simple. We just iterate through the called numbers,
     * and the boards, until one of them wins
     * Most of the work is done by helper functions, so this one is quite small
     */
    public static void problem1(ArrayList<BingoBoard> boards, ArrayList<Integer> calledNumbers) {
        // for every called number
        for (int nextCall : calledNumbers) {
            // for all the boards
            for (BingoBoard b : boards) {
                b.mark(nextCall); // mark the board if it has the number
                // if we've found a winner, print and return
                if (b.checkForWin()) {
                    System.out.println("PART 1 WINNER! Board score is: " + b.getUnmarkedSum() * nextCall);
                    return;
                }
            }
        }
        System.out.println("No winners (this is bad)"); // we shouldn't get here
    }

    /*
     * Problem 2 was a bit more challenging. My approach was to prune boards from
     * the master list as they won
     * Since you can't modify a list while iterating it, this required a bit of
     * extra overhead, but nothing crazy
     * Eventually we find the sole remaining board that hasn't won, we keep going
     * until it wins, and we calculate its score
     */
    public static void problem2(ArrayList<BingoBoard> boards, ArrayList<Integer> calledNumbers) {
        BingoBoard worstBoard = null; // this board stinks
        for (int nextCall : calledNumbers) {
            ArrayList<BingoBoard> removeList = new ArrayList<>(); // parallel list used to track which boards to prune

            // mark the boards, and add winners to the pruning list
            for (BingoBoard b : boards) {
                b.mark(nextCall);
                if (b.checkForWin())
                    removeList.add(b);
            }

            // prune boards that have already won
            for (BingoBoard b : removeList)
                boards.remove(b);

            // if there's only one more board, it's the one we want. grab it and break out
            if (boards.size() == 1) {
                worstBoard = boards.get(0);
                break;
            }
        }

        // finish marking the worst board until it wins, and then print out its score
        for (int nextCall : calledNumbers) {
            worstBoard.mark(nextCall);
            if (worstBoard.checkForWin()) {
                System.out.println("PART 2 WINNER! (Loser?) Board score is: " + worstBoard.getUnmarkedSum() * nextCall);
                return;
            }
        }
        System.out.println("No winners (this is bad)"); // we shouldn't get here
    }

    /*
     * This function specifically expects an array of 5 strings (i.e. the lines in
     * the input that constitute a board)
     * It then parses these lines and returns a BingoBoard object with the correct
     * tiles
     */
    public static BingoBoard parseBoard(String[] lines) {
        BingoTile[][] newBoard = new BingoTile[5][5];
        // for each row
        for (int j = 0; j < 5; j++) {
            /*
             * If there's a single digit number first in the row, the space at the start
             * will mess up the parsing, so we get rid of it here
             */
            if (lines[j].charAt(0) == ' ')
                lines[j] = lines[j].substring(1);
            String[] intStrings = lines[j].split("\\s+"); // regex for arbitrary number of spaces
            BingoTile[] newRow = new BingoTile[5]; // set up a new row
            for (int i = 0; i < 5; i++) {
                BingoTile tile = new BingoTile(Integer.parseInt(intStrings[i])); // populate the new row with parsed
                                                                                 // ints
                newRow[i] = tile;
            }
            newBoard[j] = newRow; // add the new row into the wip board
        }
        return new BingoBoard(newBoard); // make the final BingoBoard object and return it
    }

    /*
     * BingoTiles only need two pieces of info: their number, and whether they're
     * marked
     * This simple class handles both of those in a convenient package
     */
    static class BingoTile {
        private int number;
        private boolean marked;

        public BingoTile(int i) {
            this.number = i;
            this.marked = false;
        }

        public void mark() {
            this.marked = true;
        }

        public int getNumber() {
            return this.number;
        }

        public boolean isMarked() {
            return this.marked;
        }
    }

    /*
     * BingoBoards have all the functionality we need for the context of the problem
     * You can mark them, check them for a win, and get the sum of their unmarked
     * squares
     */
    static class BingoBoard {
        private BingoTile[][] board;

        public BingoBoard(BingoTile[][] newBoard) {
            this.board = newBoard;
        }

        // if this board has a tile with number x, mark it
        public void mark(int x) {
            for (int i = 0; i < this.board.length; i++)
                for (int j = 0; j < this.board[0].length; j++)
                    if (this.board[i][j].getNumber() == x)
                        this.board[i][j].mark();
        }

        public boolean checkForWin() {
            // horizontal winner check
            for (int i = 0; i < this.board.length; i++) {
                boolean rowHasUnmarked = false;
                for (int j = 0; j < this.board[0].length; j++) {
                    if (!this.board[i][j].isMarked()) {
                        rowHasUnmarked = true;
                        break;
                    }
                }
                if (!rowHasUnmarked)
                    return true;

            }
            // vertical winner check
            for (int i = 0; i < this.board[0].length; i++) {
                boolean columnHasUnmarked = false;
                for (int j = 0; j < this.board.length; j++) {
                    if (!this.board[j][i].isMarked()) {
                        columnHasUnmarked = true;
                        break;
                    }
                }
                if (!columnHasUnmarked)
                    return true;

            }
            return false; // no winner :(
        }

        // sum up the values of all the unmarked tiles (needed to get board score)
        public int getUnmarkedSum() {
            int returnSum = 0;
            for (int i = 0; i < this.board.length; i++)
                for (int j = 0; j < this.board[0].length; j++)
                    if (!this.board[i][j].isMarked())
                        returnSum += this.board[i][j].getNumber();
            return returnSum;
        }

    }

}