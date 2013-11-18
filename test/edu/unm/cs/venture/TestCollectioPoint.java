package edu.unm.cs.venture;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestCollectioPoint {



	@BeforeClass
	public static final void initialize(){
		Overlord overlord = Overlord.getInstance();
		boolean[][] collectionToScanners = null;
		boolean[][] concourseToGates = null;
		boolean[][] switchingNetwork = null;
		int[][] nodeProperties = null;

		// Try to load the model files
		try {
			collectionToScanners = Parser.parseFileForBool(new File("src/model/CollectionToScanners.csv"), 55, 55);
			concourseToGates = Parser.parseFileForBool(new File("src/model/ConcourseToGates.csv"), 31, 31);
			switchingNetwork = Parser.parseFileForBool(new File("src/model/SwitchingNetwork.csv"), 25, 25);
			nodeProperties = Parser.parseFileForInt(new File("src/model/NodeProperties.csv"), 6, 80);
		}
		catch (FileNotFoundException e) {
			fail("File not found: " + e.getMessage());
		}
		catch (IOException e) {
			fail("Could not open file: " + e.getMessage());
		}

		overlord.constructModel(nodeProperties, collectionToScanners, switchingNetwork);

	}


	@Test
	public void testGetInputEdges() {
		for(Node s : Overlord.getInstance().getAllNodes()){
			if(s instanceof CollectionPoint){
				assertNull(s.getInputEdges());
			}
		}
	}

	@Test
	public void testGetOutputEdges() {
		for(Node s : Overlord.getInstance().getAllNodes()){
			if(s instanceof CollectionPoint){
				assertNotNull(s.getOutputEdges());
			}
		}
	}
	
	
	
	@Test
	public void testAllNotNull() {
		
		for(Node s : Overlord.getInstance().getAllNodes()){
			if(s instanceof CollectionPoint){
				
				assertNotNull(s.getCoords());
				assertNotNull(s.getID());
				assertNotNull(s.getPos());
				assertNotNull(s.getClass());
			}
		}
		
		
		
		
	}











	/*
	@Test
	public void testCollectionPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnTick() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBags() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateBag() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsAvailable() {
		fail("Not yet implemented");
	}

	@Test
	public void testGiveBag() {
		fail("Not yet implemented");
	}


	@Test
	public void testEnable() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisable() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPos() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddInputEdge() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddOutputEdge() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetID() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEnabled() {
		fail("Not yet implemented");
	}
	 */

}
