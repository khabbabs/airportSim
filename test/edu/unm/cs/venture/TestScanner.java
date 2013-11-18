package edu.unm.cs.venture;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestScanner {

	Dimension testDim1 = new Dimension(1,1);
	Dimension testDim2 = new Dimension(2,2);
	Scanner testScan = new Scanner(testDim1, testDim2);
	CollectionPoint testNode = new CollectionPoint(testDim1,testDim2);
	boolean DEBUG_PRINT=false; 

	Edge testInputEdge = new Edge(testNode,testScan);
	Edge testOutputEdge = new Edge(testScan,testNode);


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
	public void testScanner() {

		assertEquals(testScan.getPos(),testDim2);
	}


	/*
	@Test
	public void testOnTick() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBags() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testGetInputEdges() {
		if(DEBUG_PRINT){System.out.println("Testing all output edges for TestScanner.java");}
		for(Node s : Overlord.getInstance().getAllNodes() ){
			if(s instanceof Scanner){
				if(DEBUG_PRINT){System.out.println(s.getID()+" has ");}
				for(Edge e : s.getInputEdges()){
					if(DEBUG_PRINT){System.out.println("edge: "+e.getID()+"connected to "+e.getInputNode().getID());}
					assertTrue(s.getID() == e.getOutputNode().getID());
				}
			}
		}
	}

	@Test
	public void testGetOutputEdges() {
		
		if(DEBUG_PRINT){System.out.println("Testing all output edges for TestSwitchingHub.java");}
		for(Node s : Overlord.getInstance().getAllNodes() ){
			if(s instanceof SwitchingHub){
				if(DEBUG_PRINT){System.out.println(s.getID()+" has ");}
				for(Edge e : s.getOutputEdges()){
					if(DEBUG_PRINT){System.out.println("edge: "+e.getID()+"connected to "+e.getInputNode().getID());}
					assertTrue(s.getID() == e.getInputNode().getID());
				}
			}
		}
	}
/*
	@Test
	public void testGiveBag() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCoords() {
		assertEquals(testScan.getCoords(),testDim1);
	}
	/*
	@Test
	public void testEnable() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisable() {
		fail("Not yet implemented");
	}*/
	 
	@Test
	public void testGetPos() {
		assertEquals(testScan.getPos(),testDim2);
	}
/*
	@Test
	public void testAddInputEdge() {
		
		
	}

	@Test
	public void testAddOutputEdge() {
		
	}
	
	@Test
	public void testGetID() {
		fail("Not yet implemented");
	}*/

}
