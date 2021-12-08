import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 8 - 12/8/2021
* Problems 1 and 2
*/

/*
* This solution is dedicated to the 7-segment clock in Wegmans 1400
* Without the time I have spent intently pondering it, this solution would surely have been harder to come by
*/

/*
* I tried to approach this problem from a logical perspective, almost like building a proof
* Basically we determine what each segment is based only on information that we can guarantee we have before attempting that determination
* The exact reasoning is detailed in determineNumbers()
*/
public class Day8 {
	/*
	 * Nothing vey interesting in the main method, mostly just function calls
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File inFile = new File("/home/nate/personal/advent2021/8/in.txt");
		Scanner reader = new Scanner(inFile);

		ArrayList<String> inputLines = new ArrayList<>();

		while (reader.hasNextLine())
			inputLines.add(reader.nextLine());

		printStringList(inputLines);

		int sum = finalSum(inputLines);
		System.out.println(sum);
		reader.close();
	}

	/*
	 * Fortunately, Scanner.next() makes parsing the input for this problem pretty
	 * easy
	 * I put the tokens all into the same list, since I know that the first 10 will
	 * be the digits, and the final 4 will be the display
	 * I just throw out the "|" token, it's kind of a red herring really
	 */
	static ArrayList<String> parseInputLine(String inLine) {
		Scanner lineReader = new Scanner(inLine);

		ArrayList<String> retList = new ArrayList<>();

		while (lineReader.hasNext()) {
			String s = lineReader.next();
			if (s.equals("|")) // throw this one out
				continue;
			else {
				/*
				 * This is a small trick to make comparison easier
				 * Even if we correctly determine which string represents which digit, it won't
				 * necessarily match one of the displayed strings
				 * e.g. "abc" might represent 7, but the display might have "bca" (which would
				 * be 7)
				 * But if we sort them all alphanetically, we can guarantee that it actually
				 * will match, making our lives much easier in the long run
				 */
				char[] cArr = s.toCharArray();
				Arrays.sort(cArr);
				retList.add(String.valueOf(cArr));
			}
		}
		// System.out.println("parsed line:");
		// printStringList(retList);
		lineReader.close();
		return retList;
	}

	/*
	 * This function determines the final sum used for Problem 2
	 * Line by line, we use other functions to determine the "code" on the display
	 * Then we add it to the final sum, and when we're done with the input, we
	 * return it
	 */
	static int finalSum(ArrayList<String> inputLines) {
		int sum = 0; // return variable
		// for every input line
		for (String inLine : inputLines) {
			String code = ""; // this is what the 4 digit display reads
			ArrayList<String> parsedLine = parseInputLine(inLine); // parse the input line into the list of 14 tokens
			String[] positions = determineNumbers(parsedLine); // using the first 10 tokens, figure out which string
																// represents which digit
			// for reach of the "display" tokens
			for (int i = 10; i < 14; i++) {
				// for each of the (now decoded) representations
				for (int j = 0; j < 10; j++) {
					// if this display token matches this particular representation
					if (positions[j].equals(parsedLine.get(i))) {
						code += Integer.toString(j); // append that number to the code
						break;
					}
				}
			}
			// System.out.println("code: " + code);
			sum += Integer.parseInt(code); // turn the completed code into an int and add it to the sum
		}
		return sum;
	}

	/*
	 * This function is the big one, so to speak
	 * If I wrote out my entire process, it'd be a whole paragraph, so I'll comment
	 * my logic at each step of the way
	 * The short version, though, is that given the "freebie" digits (1, 4, 7, and
	 * 8), gradually use process of elimination to figure out the other ones
	 * Trying to figure out exactly which letter corresponds to each segment seemed
	 * like begging for confusion, so my logic instead only considers digits in
	 * their entireity
	 */
	static String[] determineNumbers(ArrayList<String> parsedLine) {
		String[] retArr = new String[10];
		/*
		 * There are a lot of loops in this function, but since they're all of a fixed,
		 * small length, it's really not as expensive as it may look
		 * First, we find the representations for the "freebie" digits (1, 4, 7, 8)
		 * using their unique length
		 */
		for (int i = 0; i < 10; i++) {
			String currToken = parsedLine.get(i);
			// //System.out.println(currToken);
			switch (currToken.length()) {
				case 2:
					if (retArr[1] == null) {
						// System.out.printf("found 1!: %s\n", currToken);
						retArr[1] = currToken;
					}
					break;
				case 3:
					if (retArr[7] == null) {
						// System.out.printf("found 7!: %s\n", currToken);
						retArr[7] = currToken;
					}
					break;
				case 4:
					if (retArr[4] == null) {
						// System.out.printf("found 4!: %s\n", currToken);
						retArr[4] = currToken;
					}
					break;
				case 7:
					if (retArr[8] == null) {
						// System.out.printf("found 8!: %s\n", currToken);
						retArr[8] = currToken;
					}
					break;
				default:
					break;
			}
		}
		/*
		 * Determine 3
		 * There are 3 digit of length 5 (i.e. have 5 segments lit): 2, 3, and 5
		 * Of these digits, only 3 will have both of the segments in 1 (which we already
		 * know) lit, so we can figure it out by checking for that
		 */
		char[] oneArr = retArr[1].toCharArray();
		for (int i = 0; i < 10; i++) {
			String currToken = parsedLine.get(i);
			if ((currToken.length() == 5) && (currToken.indexOf(oneArr[0]) != -1)
					&& (currToken.indexOf(oneArr[1]) != -1)) {
				// System.out.printf("found 3!: %s\n", currToken);
				retArr[3] = currToken;
				break;
			}
		}
		/*
		 * Determine 6
		 * Kind of like 3 above, there are 3 digits of length 6: 0, 6, and 9
		 * Of these digits, 6 is the only one that does NOT contain both segments in 1,
		 * so we check for that
		 */
		for (int i = 0; i < 10; i++) {
			String currToken = parsedLine.get(i);
			if ((currToken.length() == 6)
					&& !((currToken.indexOf(oneArr[0]) != -1) && (currToken.indexOf(oneArr[1]) != -1))) {
				// System.out.printf("found 6!: %s\n", currToken);
				retArr[6] = currToken;
				break;
			}
		}
		/*
		 * Determine 9
		 * 9 is the only digit of length 6 that contains the entireity of 4 (which we
		 * already know) within it, so check for that
		 */
		char[] fourArr = retArr[4].toCharArray();
		for (int i = 0; i < 10; i++) {
			String currToken = parsedLine.get(i);
			if ((currToken.length() == 6) && (currToken.indexOf(fourArr[0]) != -1)
					&& (currToken.indexOf(fourArr[1]) != -1) && (currToken.indexOf(fourArr[2]) != -1)
					&& (currToken.indexOf(fourArr[3]) != -1) && !(currToken.equals(retArr[6]))) {
				// System.out.printf("found 9!: %s\n", currToken);
				retArr[9] = currToken;
				break;
			}
		}
		/*
		 * Determine 0
		 * By now we have found 6 and 9, so 0 is the only remaining digit of length 6
		 */
		for (int i = 0; i < 10; i++) {
			String currToken = parsedLine.get(i);
			if ((currToken.length() == 6) && !(currToken.equals(retArr[6])) && !(currToken.equals(retArr[9]))) {
				// System.out.printf("found 0!: %s\n", currToken);
				retArr[0] = currToken;
				break;
			}
		}
		/*
		 * Determine 5
		 * We already found 3, so the two remaining digits of length 5 are 5 and 2
		 * We've also already found 9, and 5 is entirely contained within 9, whereas 2
		 * is not
		 * So, we can find 5 by checking if 9 contains all of its segments
		 */
		String nineString = retArr[9];
		for (int i = 0; i < 10; i++) {
			String currToken = parsedLine.get(i);
			char[] currArr = currToken.toCharArray();
			if ((currToken.length() == 5) && (nineString.indexOf(currArr[0]) != -1)
					&& (nineString.indexOf(currArr[1]) != -1) && (nineString.indexOf(currArr[2]) != -1)
					&& (nineString.indexOf(currArr[3]) != -1) && (nineString.indexOf(currArr[4]) != -1)
					&& !(currToken.equals(retArr[3]))) {
				// System.out.printf("found 5!: %s\n", currToken);
				retArr[5] = currToken;
				break;
			}
		}
		/*
		 * Determine 2
		 * Finally, 2 is the only length 5 digit remaining, so we find it here
		 */
		for (int i = 0; i < 10; i++) {
			String currToken = parsedLine.get(i);
			if ((currToken.length() == 5) && !(retArr[5].equals(currToken)) && !(currToken.equals(retArr[6]))
					&& !(currToken.equals(retArr[3]))) {
				// System.out.printf("found 2!: %s\n", currToken);
				retArr[2] = currToken;
				break;
			}
		}
		// final check for completion
		for (String s : retArr) {
			/*
			 * If this happens, we didn't find the string for one or more of the digits
			 * This should never happen
			 */
			if (s == null)
				System.out.println("ERROR DETERMINING DIGITS, CRASH LIKELY IMMINENT");
		}
		return retArr;
	}

	/*
	 * This function is only useful for debugging
	 */
	static void printStringList(ArrayList<String> l) {
		for (String s : l) {
			System.out.println(s);
		}
	}
}
