package edu.unm.cs.venture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * This object parses csv files to return data actually relevant to the airport.
 * 
 * 
 * @author zach@cs.unm.edu
 * @author rbshaw5@gmail.com
 * @author khabbabs@unm.edu
 * @author kzelle@unm.edu
 */
public class Parser {	
	
	File collectionToScanners; 
	File concourseToGates;
	File switchingNetwork;
	File nodeProperties;
	
	
	/**
	 * Parses through a csv file and converts it to a boolean array
	 * @param file csv file that contains information on how the nodes are connected
	 * @return an array full of the information from the spreadsheet
	 * @throws IOException if csv file is incorrect, or has invalid information
	 * @throws FileNotFoundException if the file is null
	 */
	public static boolean[][] parseFileForBool(File file, int sizeX, int sizeY) throws IOException, FileNotFoundException{
		
		BufferedReader buff;
		try{
			buff = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found!");
			throw new FileNotFoundException();
		}
		String[][] collectionString = new String[sizeY][sizeX];
		
		for(int i=0; i<sizeY; i++){
			collectionString[i] = buff.readLine().split(",");
		}
				
		boolean[][] collections = stringToBoolean(collectionString);
		
		return collections;
	}
	
	/**
	 * Parses a csv file and returns an int array with the relevant information
	 * @param file a csv file containing integer information
	 * @return an array with the information from the csv file
	 * @throws IOException if the csv file is formatted correctly
	 */
	public static int[][] parseFileForInt(File file, int sizeX, int sizeY) throws IOException{
		BufferedReader buff;
		try{
			buff = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found!");
			throw new FileNotFoundException();
		}
		//creates the string array so that the bufferedreader can put the information somewhere
		String[][] stringArray = new String[sizeY][sizeX];
		//the string array is then converted to an int array
		int[][] intArray = new int[sizeY][sizeX];
		
		
		for(int i=0; i<sizeY; i++){
			stringArray[i] = buff.readLine().split(",");
		}
		
		for(int i=0; i<sizeY; i++){
			for(int j=0; j<sizeX; j++){
				intArray[i][j] = Integer.parseInt(stringArray[i][j]);
			}
		}
		
		return intArray;
		
	}
	
	
	
	/**
	 * Takes in a 2D array of strings and converts it to to booleans 
	 * @param array 2-dimensional array of strings parsed from a cvs file
	 * @return 2-dimensional array of booleans converted from the strings
	 */
	private static boolean[][] stringToBoolean(String[][] array){
		boolean[][] boolArray = new boolean[array[0].length][array.length];
		for(int i=0; i<array[0].length; i++){
			for(int j=0; j<array.length; j++){
				boolArray[i][j] = (Integer.parseInt(array[i][j]) == 1);				
			}
		}
		return boolArray;
	}
	
	
	
	
	
}
