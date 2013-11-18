package edu.unm.cs.venture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestGUI {

	public static void main(String[] args) {
		
		// Initialize the model to its base configuration
		initModel();
		
		/* *******************************************************************
		 * TESTING AND MODEL-MANIPULATING CODE GOES HERE
		 * *******************************************************************/
		Overlord overlord = Overlord.getInstance();
		
		Bag curBag;
		Edge edge = overlord.getAllEdges().get(400);
		
		// Add 5 bags
		for (int i = 0; i < 5; i++) {
			// Create the bag and put it in the master list and the edge's list
			curBag = new Bag(true, null, null, 0);
			overlord.getAllBags().put(Integer.toString(curBag.getID()), curBag);
			edge.addBag(curBag);
			
			// Add the current edge to the bag's own history list
			curBag.addVisited(edge);
			edge.onTick(2);
		}
		
		System.err.println(edge.getBags());
		
		/* *******************************************************************
		 * END TESTING CODE
		 * *******************************************************************/
		
		// Starts the GUI
		// NOTE: Make sure that the FALSE flag is set in airports main method
		// NOTE: or this will break badly.
		Airport.main(null);
	}
	
	/**
	 * Pulls in the necessary data files and constructs the model in Overlord.
	 */
	private static void initModel() {
		// Get instance of the Overlord
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
