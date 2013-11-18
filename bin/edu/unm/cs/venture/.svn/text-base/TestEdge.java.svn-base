package edu.unm.cs.venture;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestEdge {



	CollectionPoint testNodeCp =  new CollectionPoint(new Dimension(1,1), new Dimension(2,2));
	SwitchingHub testNodeSh = new SwitchingHub(new Dimension(3,3), new Dimension(4,4));
	Edge testEdge = new Edge(testNodeCp,testNodeSh);

	/*
	 * adds bag to the edge, then checks if the bag was added on correctly.
	 */
	@Test
	public void test() {


		testEdge.addBag(new Bag(true,testNodeCp,testNodeSh,1));
		assertTrue(testEdge.getCapacity() == 20);
		assertNotNull(testEdge.getBags().getLast());
	}
	/*
	 * adds a bag to the edge calls ontick 19 times to push the bag to the top of the list
	 * thats when testing of the giveBag method is possible.
	 * 
	 * checks onTick and giveBag
	 * 
	 */
	@Test
	public void testonTick(){



		testEdge.addBag(new Bag(true,testNodeCp,testNodeSh,1));



		for(int i=0;i<19;i++){
			testEdge.onTick(2);
		}
		assertNotNull(testEdge.giveBag());

	}





	// is not used yet.
	@BeforeClass
	public static final void initialize(){
		Overlord overlord = Overlord.getInstance();

		// Adjacency matrices and node properties
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
			System.err.println("Could not find file! Exception msg: " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println("Could not load file! Exception msg: " + e.getMessage());
		}

		// Generate the model
		overlord.constructModel(nodeProperties, collectionToScanners, switchingNetwork);
	}

}
