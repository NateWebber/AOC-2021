import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 10 - 12/10/2021
* Problems 1 and 2
*/


public class Day10 {
	public static void main(String[] args) throws FileNotFoundException {
		File inFile = new File("/home/nate/personal/advent2021/10/in.txt");
		Scanner reader = new Scanner(inFile);

		ArrayList<String> inLines = new ArrayList<>();

		ArrayList<String> incompleteLines = new ArrayList<>();

		ArrayList<Long> incompleteScores = new ArrayList<>();

		while (reader.hasNextLine())
			inLines.add(reader.nextLine());

		int sum = 0;

		for (String line : inLines) {
			int score = getErrorScore(line);
			switch (score) {
				case 0:
					break;
				case -1:
					// System.out.printf("incomplete: %s\n", line);
					incompleteLines.add(line);
					break;
				default:
					sum += score;

			}
		}

		System.out.println("Problem 1: " + sum);

		for (String line : incompleteLines) {
			String sequence = getFinishingSequence(line);
			// System.out.printf("complete sequence for %s is: %s\n", line, sequence);
			long score = getSequenceScore(sequence);
			incompleteScores.add(score);
		}

		Collections.sort(incompleteScores);

		System.out.println("Problem 2: " + incompleteScores.get(incompleteScores.size() / 2));

		reader.close();
	}

	static String getFinishingSequence(String line) {
		String retString = "";

		char[] lineArr = line.toCharArray();

		Deque<Character> charStack = new ArrayDeque<Character>();

		for (char c : lineArr) {
			switch (c) {
				case '(':
					charStack.push(')');
					break;
				case '[':
					charStack.push(']');
					break;
				case '{':
					charStack.push('}');
					break;
				case '<':
					charStack.push('>');
					break;
				default:
					charStack.pop();
					break;
			}
		}
		// System.out.println("STACK CONTENTS:");
		for (char c : charStack) {
			retString += c;
		}
		// System.out.println();

		return retString;
	}

	static int getErrorScore(String line) {
		// System.out.printf("looking at line: %s\n", line);
		/*
		 * int expectedParens = 0;
		 * int expectedBrackets = 0;
		 * int expectedCurlies = 0;
		 * int expectedArrows = 0;
		 */

		// char expectedChar = '-';

		char[] lineArr = line.toCharArray();

		Deque<Character> charStack = new ArrayDeque<Character>();

		for (char c : lineArr) {
			switch (c) {
				case '(':
					charStack.push(')');
					break;
				case '[':
					charStack.push(']');
					break;
				case '{':
					charStack.push('}');
					break;
				case '<':
					charStack.push('>');
					break;
				default:
					// System.out.printf("peeked and saw: %c\n", charStack.peek());
					if (!(charStack.peek() == c)) {
						// System.out.printf("unexpected %c!\n", c);
						switch (c) {
							case ')':
								return 3;
							case ']':
								return 57;
							case '}':
								return 1197;
							case '>':
								return 25137;
						}
					}
					charStack.pop();
			}
		}
		if (!(charStack.isEmpty()))
			return -1;
		return 0;

		/*
		 * for (char c : lineArr) {
		 * 
		 * System.out.printf("expected counts: %d %d %d %d\n", expectedParens,
		 * expectedBrackets, expectedCurlies, expectedArrows);
		 * }
		 */

	}

	static long getSequenceScore(String sequence) {
		long sum = 0;
		char[] cArr = sequence.toCharArray();
		for (char c : cArr) {
			sum *= 5;
			switch (c) {
				case ')':
					sum += 1;
					break;
				case ']':
					sum += 2;
					break;
				case '}':
					sum += 3;
					break;
				case '>':
					sum += 4;
					break;
			}
		}
		// System.out.printf("score for sequence %s: %dl\n", sequence, sum);
		return sum;
	}
}
