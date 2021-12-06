import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 6 - 12/06/2021
* Problems 1-2
*/

/*
* "And what are you, reader, but a Loose-Fish and a Fast-Fish, too?"
*  Herman Melville, Moby-Dick (or, the Whale)
*/
public class Day6 {
    /*
     * Today's solution is surprisingly elegant (is that a self-burn?)
     * I initially wrote this spending way too much memory, and promptly ran out
     * come Problem 2
     * There was clearly no way around the exponential space complexity, so with the
     * conclusion that I simply couldn't represent the fish in memory, I needed a
     * new tactic
     * I eventually realized that all I really need to do is keep track of how many
     * fish are of a given age
     * Then I can just move these total groups down the line, and when they spawn I
     * just add more to the total of fish at age 8, and move the spawning fish
     * themselves back up to age 6
     */
    public static void main(String[] args) throws FileNotFoundException {
        File inFile = new File("/home/nate/personal/advent2021/6/in.txt");

        Scanner reader = new Scanner(inFile);

        String[] fishStrings = reader.nextLine().split(",");

        long[] fishArray = new long[9]; // indexed by age

        // initial population
        for (String s : fishStrings)
            fishArray[Integer.parseInt(s)] += 1;

        long sum = 0;
        for (long f : fishArray)
            sum += f;

        // System.out.println("initial sum: " + sum);

        for (int i = 0; i < 256; i++) {
            long spawningFish = fishArray[0]; // this many fish will be spawning

            // age the population: time waits for no fish
            for (int j = 1; j < 9; j++) {
                fishArray[j - 1] = fishArray[j];
            }

            // the spawning fish go back to age 6
            fishArray[6] += spawningFish;

            // and the new set of fish are born at age 8
            fishArray[8] = spawningFish;

            // update the running fish total
            sum += spawningFish;

        }
        System.out.println(sum);
        reader.close();
    }

}