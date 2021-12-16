import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
* Nate Webber
* Advent of Code 2021
* Day 12 - 12/12/2021
* Problems 1 and 2
*/

public class Day12 {
	public static void main(String[] args) throws FileNotFoundException {
		File inFile = new File("/home/nate/personal/advent2021/12/in.txt");
		Scanner reader = new Scanner(inFile);

		ArrayList<Cave> caveList = new ArrayList<>();
		while(reader.hasNextLine())
			parseInputLine(reader.nextLine(), caveList);

		/*printCaveList(caveList);

		for (Cave c : caveList)
			printCaveConnections(c);*/

		ArrayList<String> problem1Paths = getPaths(caveList, 1);

		ArrayList<String> problem2Paths = getPaths(caveList, 2);
		
		/*for (String s : problem2Paths)
			System.out.println(s);*/

		System.out.println("Problem 1: " + problem1Paths.size());

		System.out.println("Problem 2: " + problem2Paths.size());
		
		reader.close();
	}

	static ArrayList<String> getPaths(ArrayList<Cave> caves, int problem){
		ArrayList<String> retList = new ArrayList<>();
		Cave startCave = getCaveByName("start", caves);
		if (problem == 1){
			for (Cave c : startCave.getConnections()){
				String newPath = "start->";
				stepPath(startCave, c, newPath, retList);
			}
		}
		else{
			for (Cave c : startCave.getConnections()){
				String newPath = "start->";
				stepPathProblem2(startCave, c, newPath, retList);
			}
		}
		


		return retList;
	}

	static void stepPath(Cave start, Cave dest, String currPath, ArrayList<String> paths){
		if (dest.isSmall() && currPath.contains(dest.getName()))
			return;
		currPath += dest.getName();
		if (dest.getName().equals("end")){
			paths.add(currPath);
			return;
		}
		else{
			currPath += "->";
			for (Cave c : dest.getConnections()){
				String newPath = currPath;
				stepPath(dest, c, newPath, paths);
			}
		}
	}

	static void stepPathProblem2(Cave start, Cave dest, String currPath, ArrayList<String> paths){
		if (dest.getName().equals("start"))
			return;
		// small cave we've been to, and we've already repeated a small cave
		if (dest.isSmall() && currPath.contains(dest.getName()) && pathRepeatsSmallCave(currPath))
			return;
		currPath += dest.getName();
		if (dest.getName().equals("end")){
			paths.add(currPath);
			return;
		}
		else{
			currPath += "->";
			for (Cave c : dest.getConnections()){
				String newPath = currPath;
				stepPathProblem2(dest, c, newPath, paths);
			}
		}
	}

	static boolean pathRepeatsSmallCave(String path){
		ArrayList<String> pathList = new ArrayList<String>(Arrays.asList(path.split("->", -1)));
		pathList.remove(pathList.size() - 1);
		for (String s : pathList){
			if (Character.isLowerCase(s.charAt(0)) && ((path.split(s, -1).length - 1) > 1)){
				return true;
			}
		}
		return false;

	}

	static void parseInputLine(String line, ArrayList<Cave> caves) {
		String[] caveNames = line.split("-", -1);
		//System.out.printf("parsed cave names: %s, %s\n", caveNames[0], caveNames[1]);
		Cave cave1 = getCaveByName(caveNames[0], caves);
		if (cave1 == null) {
			//System.out.printf("Cave %s didn't exist yet, generating\n", caveNames[0]);
			cave1 = new Cave(caveNames[0], !(Character.isUpperCase(caveNames[0].charAt(0))));
			caves.add(cave1);
		}
		Cave cave2 = getCaveByName(caveNames[1], caves);
		if (cave2 == null) {
			//System.out.printf("Cave %s didn't exist yet, generating\n", caveNames[1]);
			cave2 = new Cave(caveNames[1], !(Character.isUpperCase(caveNames[1].charAt(0))));
			caves.add(cave2);
		}
		cave1.addConnection(cave2);
		cave2.addConnection(cave1);

	}

	static Cave getCaveByName(String n, ArrayList<Cave> caves) {
		for (Cave c : caves)
			if (c.getName().equals(n))
				return c;
		return null;
	}

	static void printCaveList(ArrayList<Cave> list) {
		System.out.println("");
		for (Cave c : list)
			System.out.print(c.getName() + " ");
		System.out.println("");
	}

	static void printCaveConnections(Cave c){
		System.out.printf("%s's (small = %b) connections:\n", c.getName(), c.isSmall());
		for (Cave x : c.getConnections())
			System.out.println(c.getName() + "->" + x.getName());
		System.out.println("");
	}
}


class Cave {
	String name;
	boolean small;
	boolean visited;
	ArrayList<Cave> connections;

	public Cave(String n, boolean s) {
		this.name = n;
		this.small = s;
		this.visited = false;
		this.connections = new ArrayList<Cave>();
	}

	public String getName() {
		return this.name;
	}

	public boolean isSmall(){
		return this.small;
	}

	public void setVisited(boolean b) {
		this.visited = b;
	}

	public boolean getVisited() {
		return this.visited;
	}

	public void addConnection(Cave other) {
		this.connections.add(other);
	}

	public ArrayList<Cave> getConnections(){
		return this.connections;
	}
}
