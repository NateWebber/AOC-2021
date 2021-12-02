import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 1 - 12/01/2021
* Problem 1
*/
class d1p1{
    public static void main(String[] args) throws FileNotFoundException{
        File inFile = new File("/home/nate/personal/advent2021/1/input.txt");
        Scanner fileScanner = new Scanner(inFile);

        ArrayList<Integer> inputList = new ArrayList<>();

        while (fileScanner.hasNextLine()){
            inputList.add(Integer.parseInt(fileScanner.nextLine()));
        }
        int sum = 0;
        for (int i = 1; i < 2000; i++){
            if (inputList.get(i) > inputList.get(i - 1))
                sum += 1;
        }
        System.out.println(sum);
        fileScanner.close();
    }
}