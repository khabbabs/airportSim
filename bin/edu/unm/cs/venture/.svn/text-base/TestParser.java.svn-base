package edu.unm.cs.venture;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestParser {

	@Test
	public void testIntParser(){
		
		File intFile = null;
		
		
		intFile = new File("src/model/TestIntParser.csv");
		assertNotNull("DID NOT LOAD", intFile);
		
		int[][] testArray = null;
		try{
			testArray = Parser.parseFileForInt(intFile, 3, 3);
		}
		catch(IOException e){
			fail("DID NOT PARSE CORRECTLY");
		}
		int counter = 1;
		//goes through every data and compares it to the correct value
		for(int i=0; i<3; i++){			
			for(int j=0; j<3; j++){
				System.out.print(testArray[i][j] + " ");
				assertEquals(testArray[i][j], counter);
				counter++;
			}
			System.out.println();
		}
		
		
	}
	
	@Test
	public void testBoolParser(){
		
		File boolFile = null;
		boolFile = new File("src/model/TestBoolParser.csv");
		
		boolean[][] testArray = null;
		
		try{
			testArray = Parser.parseFileForBool(boolFile, 2, 2);
		}
		catch(IOException e){
			fail("DID NOT PARSE CORRECTLY");
		}
		
		
		for(int i=0; i<2; i++){
			for(int j=0; j<2; j++){
				System.out.print(testArray[i][j] + " ");
				//assertEquals(testArray[i][j], i);
				
			}
			System.out.println();
				
		}
		
		
		
	}

}
