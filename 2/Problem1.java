import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 2 - 12/02/2021
* Problem 1
*/

public class Problem1{
    public static void main(String[] args) throws FileNotFoundException{
        File inFile = new File("/home/nate/personal/advent2021/2/in.txt");
        Scanner fileScanner = new Scanner(inFile);
        int verSum = 0, horSum = 0;
        while (fileScanner.hasNextLine()){
            String nextLine = fileScanner.nextLine();
            switch (nextLine.charAt(0)){
                case 'f':
                    horSum += Integer.parseInt(nextLine.substring(8));
                    break;
                case 'u':
                    verSum -= Integer.parseInt(nextLine.substring(3));
                    break;
                case 'd':
                    verSum += Integer.parseInt(nextLine.substring(5));
                    break;
            }
        }
        System.out.println(horSum * verSum);
        fileScanner.close();
    }
}