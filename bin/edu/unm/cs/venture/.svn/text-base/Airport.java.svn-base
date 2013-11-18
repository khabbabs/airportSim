package edu.unm.cs.venture;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Airport {

	/** Launch the application. */
	public static void main(String[] args) {

		// Construct the model
		initModel();
		
		// Start the GUI in a separate thread
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphGUI gui = new GraphGUI();
					Overlord.getInstance().setGUI(gui); // Give Overlord a reference to this gui
					gui.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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