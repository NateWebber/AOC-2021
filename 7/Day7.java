import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 7 - 12/07/2021
* Problems 1-2
*/

/*
* "I rush! Naught's an obstacle, naught's an angle to the iron way!"
* Herman Melville, Moby Dick (or, the Whale)
*/

/*
* The naive implementation worked and then didn't stop working
* I was trying to write this one as fast as possible, so I just accepted that
*/
public class Day7 {
    public static void main(String[] args) throws FileNotFoundException {
        File inFile = new File("/home/nate/personal/advent2021/7/in.txt");

        Scanner reader = new Scanner(inFile);

        String inLine = reader.nextLine();

        String[] inStrings = inLine.split(",");

        Integer[] crabs = new Integer[inStrings.length];

        for (int i = 0; i < inStrings.length; i++)
            crabs[i] = Integer.parseInt(inStrings[i]);

        ArrayList<Integer> totals = new ArrayList<Integer>(); // list of total costs

        /*
         * for every possible horizontal position (logically, it will be between 0 and
         * the largest position; it wouldn't make sense to have them move all in the
         * same direction)
         */
        for (int i = 0; i <= Collections.max(Arrays.asList(crabs)); i++) {
            int totalCost = 0; // cost for this position

            // for each crab
            for (Integer c : crabs) {
                int diff = (Math.abs(i - c)); // distance the crab must travel
                for (int j = 1; j <= diff; j++)
                    totalCost += j; // for each step of the distance, add the step
            }
            totals.add(totalCost);
        }

        System.out.println(Collections.min(totals)); // print the smallest of the total costs

        reader.close();
    }
}