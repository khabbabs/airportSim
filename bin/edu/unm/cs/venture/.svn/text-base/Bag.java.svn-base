package edu.unm.cs.venture;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


/**
 * An object representing a single piece of luggage.
 * 
 * @author zach@cs.unm.edu
 * @author rbshaw5@gmail.com
 * @author khabbabs@unm.edu
 * @author kzelle@unm.edu
 */
public class Bag {
	
	/** The next UID to give out. */
	private static int UID = 0;
	
	/** The amount of time that passes in a single tick. */
	public static final int TICK_TIME = 1;

	/** The unique ID of this piece of baggage. */
	private int id;
	
	/** The current accumulated time of this bag. */
	private int time;
	
	private boolean legal;
	
	/** The collection point this node starts at. */
	private CollectionPoint startNode;
	
	/** The final gate this bag should be routed to. */
	private Node endNode;
	
	/** defines which gate its going to in the concourse */
	private int destinationGate;
	
	/** The history of the path this bag has taken. */
	private final LinkedList<Object> pathTaken;
	
	/**
	 * A list of nodes that this bag has already traveled through as well as
	 * nodes that are globally blacklisted.
	 */
	private final LinkedList<Node> blackListedNodes = new LinkedList<Node>();
	
	
	/**
	 * Constructor for the Bag class
	 * @param legal boolean whether the bag is allowed to pass through scanner or not
	 * @param startNode CollectionPoint where the bag begins its journey
	 * @param endNode Concourse where the bag is destine to go
	 * @param gate int which gate the bag is ultimately traveling to
	 */
	public Bag(boolean legal, CollectionPoint startNode, Node endNode, int gate) {
		this.id = getUID();
		this.legal = legal;
		this.startNode = startNode;
		this.endNode = endNode;
		this.destinationGate = gate;
		this.pathTaken = new LinkedList<Object>();
	}
	
	
	/** Generates a unique ID for a bag. */
	public static int getUID() {
		return UID++;
	}
	
	/**
	 * Advances the cumulative time of this bag on a tick by the tick time.
	 * 
	 * @param The iterator used in Tick to iterate over the bags. Used if the bag
	 * needs to remove itself from the master list whilst iterating over it.
	 */
	public void onTick(Iterator<Map.Entry<String, Bag>> bagIterator) {
		time += TICK_TIME;
		if(time > (900)){
			Overlord.getInstance().getDisposedBags().put(Integer.toString(getID()), this);
			bagIterator.remove();
			if(pathTaken.peekLast() instanceof Node){
				Node node = (Node)pathTaken.peekLast();
				node.disposeBag(this);
			}
			else if(pathTaken.peekLast() instanceof Edge){
				Edge edge = (Edge)pathTaken.peekLast();
		//this was not there. show ron 		 
				edge.disposeBag(this);
				
			}
			else{
				System.err.println("BAG PATHTAKEN ON INSTANCEOF SOMETHING WEIRD");
			}
		}
	}
	
	
	/**
	 * Adds a node to this bags blacklist.
	 * 
	 * @param node The node to be added to the list.
	 */
	public void addToBlackList(Node node) {
		blackListedNodes.add(node);
	}
	
	
	/**
	 * Clears all of the nodes in this bag's blacklist.
	 */
	public void clearBlackList() {
		blackListedNodes.clear();
	}
	
	
	/**
	 * Returns whether this bag is legal or not. If it's legal, it is allowed
	 * to pass through the scanner.
	 * 
	 * @return true if the bag can pass, false if YOU SHALL NOT PASS!
	 */
	public boolean onScan() {
		return legal;
	}
	
	
	// *************** GETTERS ***************
	
	// TODO: Comment these
	
	/**
	 * Gets the bag's blacklist.
	 * 
	 * @return the blacklist.
	 */
	public LinkedList<Node> getBlackList() {
		return blackListedNodes;
	}
	
	
	public int getID() {
		return id;
	}
	
	
	public int getTime() {
		return time;
	}
	
	
	public CollectionPoint getStartNode() {
		return startNode;
	}
	
	
	public Node getEndNode() {
		return endNode;
	}
	
	public int getDestinationGate(){
		return destinationGate;
	}
	
	public LinkedList<Object> getPathTaken() {
		return pathTaken;
	}
	
	public void addVisited(Object obj){
		pathTaken.add(obj);
	}
}
