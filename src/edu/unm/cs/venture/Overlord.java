package edu.unm.cs.venture;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This object control the simulation of the model. All of the user controls
 * will communicate with the model through this object, and this object
 * controls the main ticking loop.
 * 
 * @author zach@cs.unm.edu
 * @author rbshaw5@gmail.com
 * @author khabbabs@unm.edu
 * @author kzelle@unm.edu
 */
public class Overlord {
	
	/** List of all nodes in the model, in the order specified in the docs. */
	private ArrayList<Node> allNodes;
	
	/** List of all edges in the model in no explicit order. */
	private LinkedList<Edge> allEdges;
	
	/** Map of all bags in the system. */
	private HashMap<String, Bag> allBags;
	
	/** Map of all bags that have been removed from the main part of the system. */
	private HashMap<String, Bag> disposedBags;
	
	private ArrayList<CollectionPoint> collectionPoints;
	private ArrayList<Concourse> concourses;
	private ArrayList<InboundGate> inboundGates;
	
	/** The bag generator */
	BagGenerator bagGenerator;
	
	/** Flag that determines if bag generator should be running. */
	private boolean generateBags;

	/** The singleton instance of the Overlord object. */
	private static Overlord instance;
	
	/** The instance of the GUI object. */
	public GraphGUI gui;
	
	/** The main simulation loop "running" variable. */
	private boolean run;
	
	//private Time totalSimTime
	
	private LinkedList<Object> stateChanges;
	
	/** Tick length in milliseconds. */
	public static int TICK_LENGTH = 100;
		
	
	/** Returns the singleton instance of the Overlord. */
	public static Overlord getInstance() {
		return instance == null ? instance = new Overlord() : instance;
	}
	
	/** Private constructor for the Overlord. */
	private Overlord() {
		allNodes = new ArrayList<Node>();
		allEdges = new LinkedList<Edge>();
		allBags = new HashMap<String, Bag>();
		disposedBags = new HashMap<String, Bag>();
		stateChanges = new LinkedList<Object>();
		
		// Default running flag values
		run = false;
		generateBags = true;
	}
	
	
	/**
	 * Miniature node factory used for producing collection points, scanners,
	 * switching hubs, or concourse nodes. The type of node returned is based
	 * on the code provided, which are listed below:
	 * 
	 * 0 = collection point
	 * 1 = scanner
	 * 2 = switching hub
	 * 3 = concourse node
	 * 
	 * @param code The object code specifying which type of node to return
	 * @param coords Cartesian coordinates of the node
	 * @param pos Relative visual position of node for GUI
	 * 
	 * @return the specified type of node.
	 * 
	 * @throws IllegalArgumentException if coords or pos are null, or if an
	 * invalid code is supplied.
	 */
	private static Node getNode(int code, Dimension coords, Dimension pos) throws IllegalArgumentException {
		
		if (coords == null || pos == null) throw new IllegalArgumentException("Coords and pos must not be null.");
		
		switch(code) {
		// Collection Point
		case 0:
			return new CollectionPoint(coords, pos);
			
		// Scanner
		case 1:
			return new Scanner(coords, pos);
			
		// Switching Hub
		case 2:
			return new SwitchingHub(coords, pos);
			
		// Concourse
		case 3:
			return new Concourse(coords, pos);
			
		// Invalid code
		default:
			throw new IllegalArgumentException("Invalid code.");
		}
	}
	
	
	/**
	 * Constructs the airport model from node property lists and adjacency matrices.
	 * 
	 * @param nodeProps A list of all nodes represented in the adjacency matrices
	 * and their associated properties.
	 * 
	 * @param edgeMatrices Matricies representing linkage between nodes by edges.
	 */
	public void constructModel(int[][] nodeProps, boolean[][]... edgeMatrices) {
		
		Node curNode;
		Iterator<Node> nodeIterator;
		
		// STEP 1: Generate the nodes

		generateNodes(nodeProps);

		// STEP 2: Create the edges and link the nodes
		
		nodeIterator = allNodes.iterator();
		int indexOffset = 0;

		// For each edge matrix
		for (boolean[][] edgeMatrix : edgeMatrices) {

			// For each row in the edge matrix
			for (boolean[] edges : edgeMatrix) {

				// Get the next node, or throw an exception if there are no more
				if (nodeIterator.hasNext()) {
					curNode = nodeIterator.next();
				} else {
					throw new NoSuchElementException("Less nodes than rows of edges.");
				}
				
				generateEdges(curNode, edges, indexOffset);
			}
			
			// Advance the start index by the amount of nodes in the current edge matrix
			indexOffset += edgeMatrix.length;
		}
		
		
		// STEP 3: Join redundant nodes
		
		nodeIterator = allNodes.iterator();
		Node redundantNode;
		LinkedList<Node> nodesToDelete = new LinkedList<Node>();
		
		// For each row in the node properties CSV
		for (int[] properties : nodeProps) {

			curNode = nodeIterator.next();

			// If there is a join value
			if (properties[1] != 0) {
				
				redundantNode = allNodes.get(properties[1]);

				// For each of the current node's input edges
				for (Edge inputEdge : curNode.getInputEdges()) {

					// Link the redundant node to the current node's inputs
					redundantNode.addInputEdge(inputEdge);
					inputEdge.setOutputNode(redundantNode);
				}
				
				// Add the current node to the kill list to remove after
				// the joining process is done. This is done this way
				// to avoid concurrent modification problems due to use
				// of many enhanced for loops.
				nodesToDelete.add(curNode);
			}
		}
		
		// Delete the orphaned nodes
		for (Node node : nodesToDelete) {
			allNodes.remove(node);
		}
		
		// Set up the bag generator now that the rest of the nodes exist
		bagGenerator = new BagGenerator(getCollectionPoints(), getConcourses(), 90, 10);
	}
	
	
	/**
	 * Generate nodes based on the properties CSV and add them to the master
	 * node list.
	 * 
	 * @param nodeProps The properties CSV in array format.
	 */
	private void generateNodes(int[][] nodeProps) {
		
		// For each row in the node properties CSV
		for (int[] properties : nodeProps) {

			// Generate coordinates and position dimensions
			Dimension coords = new Dimension(properties[2], properties[3]);
			Dimension pos = new Dimension(properties[4], properties[5]);

			// Create node and add it to the master list.
			allNodes.add(getNode(properties[0], coords, pos));
		}
		
		// Construct the concourse sections procedurally
		for (Concourse conc : getConcourses()) {
			conc.constructConcourse();
		}
	}
	
	
	/**
	 * Generates the model's edges based on the adjacency matrices and links
	 * the nodes with them.
	 * 
	 * Note that the node properties file needs to "sync" up with the edge matrix
	 * files in that any duplicated edges in the adjacency matrices must be
	 * represented in the node properties file, and the order of the adjacency
	 * matrices must be correct as well.
	 * 
	 * @param node The current node being linked to other nodes.
	 * @param edges The row of edges in the proper adjacency matrix for the node.
	 * @param indexOffset Indicates where in the master node list the node being
	 * linked TO is. Because each adjacency matrix row refers to other nodes
	 * RELATIVE to the chunk of the model they represent, this offset will ensure
	 * the relative index used here will point towards the correct node.
	 */
	private void generateEdges(Node node, boolean[] edges, int indexOffset) {
		
		Edge edge;
		
		// For each adjacency entry in the row
		for (int i = 0; i < edges.length; i++) {
			
			// Link the nodes if adjacent (entry is true)
			if (edges[i]) {
				edge = new Edge(node, allNodes.get(indexOffset + i));
				node.addOutputEdge(edge);
				allNodes.get(indexOffset + i).addInputEdge(edge);
				allEdges.add(edge);
			}
		}
	}
	
	/* ******************************
	 * UTILITY
	 * ******************************/
	
	public void disposeBag(String ID) {
		disposedBags.put(ID, allBags.get(ID));
		allBags.remove(ID);
	}
	
	/* ******************************
	 * MAIN SIMULATION LOOP TICK
	 * ******************************/
	
	/** Advances the simulation by one step ("tick") */
	public synchronized void tick() {
		
		// Generate bags randomly
		if (generateBags) {
			bagGenerator.onTick();
		}
		
		// Execute any element disables/enables
		executeStateChanges();

		// Bag Phase 1
		Iterator<Map.Entry<String, Bag>> bagIterator = allBags.entrySet().iterator();
		Bag curBag = null;
		while (bagIterator.hasNext()) {
			curBag = bagIterator.next().getValue();
			curBag.onTick(bagIterator);
		}
		
		// Nodes Phase 1
		for (Node node : allNodes) {
			node.onTick(1);
		}
		
		// Edges Phase 1
		for (Edge edge : allEdges) {
			edge.onTick(2);
		}
		
		// Nodes Phase 2
		for (Node node : allNodes) {
			node.onTick(2);
		}
		
		// Holding Area
		HoldingArea.getInstance().onTick();
		
		// Update bag location on graph
		gui.updateBags();
		
		// Remove the temporary list of disposed bags for this tick
		disposedBags.clear();
	}
	
	
	/* ******************************
	 * GETTERS
	 * ******************************/
	
	/** @return Master list of all nodes in the model. */
	public ArrayList<Node> getAllNodes() {
		return allNodes;
	}
	
	/** @return Master list of all edges in the model. */
	public LinkedList<Edge> getAllEdges() {
		return allEdges;
	}
	
	/** @return Master map of all bags in the model. */
	public HashMap<String, Bag> getAllBags() {
		return allBags;
	}
	
	/** @return Master map of all disposed bags in the model. */
	public HashMap<String, Bag> getDisposedBags() {
		return disposedBags;
	}
	
	/** @return Whether the simulation is running or not. */
	public boolean getRun() {
		return run;
	}
	
	
	/** @return Sub-list of master list of all collection points in the model. */
	public ArrayList<CollectionPoint> getCollectionPoints(){
		
		if (collectionPoints == null) {
			collectionPoints = new ArrayList<CollectionPoint>();

			for (Node element : allNodes) {
				if (element.getClass() == CollectionPoint.class) {
					collectionPoints.add((CollectionPoint) element);
				}
			}
		}
		
		return collectionPoints;
	}
	
	/** @return Sub-list of master list of all concourses in the model. */
	public ArrayList<Concourse> getConcourses(){
		
		if (concourses == null) {
			concourses = new ArrayList<Concourse>();

			for (Node element : allNodes) {
				if (element.getClass() == Concourse.class) {
					concourses.add((Concourse) element);
				}
			}
		}
		
		return concourses;
	}
	
	/** @return Sub-list of master list of all inbound gates in the model. */
	public ArrayList<InboundGate> getInboundGates(){
		
		if (inboundGates == null) {
			inboundGates = new ArrayList<InboundGate>();

			for (Node element : allNodes) {
				if (element.getClass() == InboundGate.class) {
					inboundGates.add((InboundGate) element);
				}
			}
		}
		
		return inboundGates;
	}

	/* *************************
	 * SETTERS
	 * *************************/
	
	/** Sets the reference to the running GUI thread. */
	public void setGUI(GraphGUI gui) {
		this.gui = gui;
	}
	
	/** Toggles the bag generation */
	public void toggleBagGen() {
		generateBags = !generateBags;
	}
	
	/* *************************
	 * CONTROLLER METHODS
	 * *************************/
	
	/** Sets the main loop boolean to false. */
	public void setRun(boolean bool) {
		run = bool;
	}
	
	/**
	 * Schedules an element disable. This method ensures that any disables
	 * that the user enters are executed at the correct time during a tick,
	 * as simply flipping the flag whenever can break the system.
	 * 
	 * @param element The graph element to disable
	 */
	public void scheduleStateChange(Object element) {
		synchronized(stateChanges) {
			stateChanges.add(element);
		}
	}
	
	public void executeStateChanges() {
		synchronized(stateChanges) {
			for (Object element : stateChanges) {
				// Element is a node
				if (element instanceof Node) {
					Node node = (Node)element;

					// Toggle state
					if (node.isEnabled()) {
						node.disable();
					} else {
						node.enable();
					}
				}
				// Element is an edge
				else {
					Edge edge = (Edge)element;

					// Toggle state
					if (edge.getState()) {
						edge.disable();
					} else {
						edge.enable();
					}
				}
			}
			
			// Reflect all possible changes in GUI
			for (Node node : Overlord.getInstance().getAllNodes()) {
				gui.updateModel(node);
			}
			for (Edge edge : Overlord.getInstance().getAllEdges()) {
				gui.updateModel(edge);
			}
			
			// Reset state changes after executing
			stateChanges.clear();
		}
	}
	
	/**
	 * Task object that will start looping the simulation of the airport.
	 * @author zach
	 */
	protected class SimLooper extends TimerTask
	{
		/** Timer object this task belongs to. Used to top the task. */
		private Timer timer;
		
		
		/** Construct task with timer reference. */
		public SimLooper(Timer timer) {
			super();
			this.timer = timer;
		}
		
		/** {@inheritDoc} */
	    public void run()
	    {
	    	if (run) {
	    		//System.err.println("Simulating...");
	    		tick();
	    	} else {
	    		timer.cancel();
	    	}
	    }
	}
}
