package edu.unm.cs.venture;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the Overlord object.
 * 
 * @author zach@cs.unm.edu
 */
public class TestOverlord {
	
	private Overlord overlord = Overlord.getInstance();
	
	@BeforeClass
	public static final void constructModel() {
		boolean[][] collectionToScanners = null;
		boolean[][] switchingNetwork = null;
		int[][] nodeProperties = null;
		
		// Try to load the model files
		try {
			collectionToScanners = Parser.parseFileForBool(new File("src/model/CollectionToScanners.csv"), 55, 55);
			switchingNetwork = Parser.parseFileForBool(new File("src/model/SwitchingNetwork.csv"), 25, 25);
			nodeProperties = Parser.parseFileForInt(new File("src/model/NodeProperties.csv"), 6, 80);
		}
		catch (FileNotFoundException e) {
			fail("File not found: " + e.getMessage());
		}
		catch (IOException e) {
			fail("Could not open file: " + e.getMessage());
		}
		
		Overlord.getInstance().constructModel(nodeProperties, collectionToScanners, switchingNetwork);
	}

	/**
	 * Test method for {@link edu.unm.cs.venture.Overlord#getInstance()}.
	 * 
	 * Ensure that the class properly returns an instance when asked for one,
	 * and that it always returns the first generated instance.
	 */
	@Test
	public final void testGetInstance() {
		
		// Generate the singleton instances
		Overlord testOverlord1 = Overlord.getInstance();
		Overlord testOverlord2 = Overlord.getInstance();
		
		// Test if we got Overlords back
		assertNotNull("Overlord did not return an instance.", testOverlord1);
		assertNotNull("Overlord did not return an instance on second call", testOverlord1);
		
		// Test if we get the same instance back on the second time
		assertSame("Overlord did not return the same Overlord instance.", testOverlord1, testOverlord2);
	}
	
	
	/**
	 * Test to see if the model is being generated and linked correctly.
	 */
	@Test
	public final void testConstructModel() {
		
		// Ensure that node 11 (index 10) is connected to all 5 scanners
		Node node11 = overlord.getAllNodes().get(10);
		assertSame("Node 11's first link is not to Scanner 1 (SCAN5)",
				node11.getOutputEdges().get(0).getOutputNode(),
				overlord.getAllNodes().get(50));
		assertSame("Node 11's second link is not to Scanner 2 (SCAN6)",
				node11.getOutputEdges().get(1).getOutputNode(),
				overlord.getAllNodes().get(51));
		assertSame("Node 11's third link is not to Scanner 3 (SCAN7)",
				node11.getOutputEdges().get(2).getOutputNode(),
				overlord.getAllNodes().get(52));
		assertSame("Node 11's fourth link is not to Scanner 4 (SCAN8)",
				node11.getOutputEdges().get(3).getOutputNode(),
				overlord.getAllNodes().get(53));
		assertSame("Node 11's fifth link is not to Scanner 5 (SCAN9)",
				node11.getOutputEdges().get(4).getOutputNode(),
				overlord.getAllNodes().get(54));
	}
	
	@Test
	public final void testGetGates() {
		
	}
	
	
	@Test
	public final void scenarioBagsOnEdge() {
		
		Bag curBag;
		Edge edge = overlord.getAllEdges().get(400);
		
		// Add 5 bags
		for (int i = 0; i < 5; i++) {
			curBag = new Bag(true, null, null, 0);
			edge.addBag(curBag);
			edge.onTick(2);
		}
		
		// Tick 5 times
		for (int i = 0; i < 5; i++) {
			edge.onTick(2);
			
			System.err.println(edge.getBags());
		}
	}
}
