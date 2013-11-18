package edu.unm.cs.venture;

import java.util.LinkedList;

/**
 * A link between two nodes. Could take the form of a conveyor belt, or be more
 * abstract, such as people carrying bags or a cart of bags.
 * 
 * @author zach@cs.unm.edu
 * @author rbshaw5@gmail.com
 * @author khabbabs@unm.edu
 * @author kzelle@unm.edu
 */
public class Edge {
	
	/** Standard capacity for any given edge. */
	private static final int STD_CAPACITY = 6;
	
	/** Next ID to assign. */
	private static int UID = 0;

	/** The unique ID of this edge. */
	private String id;
	
	/** The current bags on this edge as a queue. */
	private final LinkedList<Bag> curBags = new LinkedList<Bag>();
	
	private boolean curState;
	
	/** The source node of this edge. */
	private Node inputNode;
	
	/** The end node of this edge. */
	private Node outputNode;
	
	/** The amount of bags that can be on this edge at once. */
	private final int capacity;
	
	
	
	/**
	 * Constructs an edge with a unique ID, an input node, an output node,
	 * and a capacity.
	 * 
	 * @param id A unique ID for this edge.
	 * @param inputNode The starting node that this edge is connected to.
	 * @param outputNode The ending node that this edge is connected to.
	 * @param capacity The amount of bags that can be on this edge at once.
	 */
	public Edge(Node inputNode, Node outputNode) {
		this.id = "Edge" + getUID();
		this.inputNode = inputNode;
		this.outputNode = outputNode;
		this.curState = true;
		
		
		// If specific types of nodes are set as the input/output, set a non-
		// standard capacity. Otherwise, set it to the static STD_CAPACITY
		// value specified in the Edge class.
		
		// Collection Point -> Scanner
		if (this.inputNode.getClass() == CollectionPoint.class) {
			capacity = 20;
		}
		else {
			capacity = STD_CAPACITY;
		}
		
		while(curBags.size() < capacity){
			curBags.add(null);
		}
	}
	
	/**
	 * Getter for UID, a unique ID
	 * @return int unique id number
	 */
	private static int getUID() {
		return UID++;
	}
	
	/**
	 * Executes one "unit" (tick) of simulation for this object.
	 * 
	 * @param phase Simulation per object is divided into two phases, 0 and 1.
	 */
	public void onTick(int phase) {
		if(phase == 2){
			if(curBags.peek() == null){
				moveBags();
			}
		}
	}
	
	
	/**
	 * Advances the bags on this edge by one unit.
	 * WARNING: This method removes the first bag. Only call if this bag is unnecessary
	 * or null
	 */
	private void moveBags() {
		curBags.poll();
		curBags.add(null);
	}
	
	/**
	 * Enables the current edge
	 */
	public void enable() {
		this.curState = true;
		while(curBags.size() < capacity){
			curBags.add(null);
		}
	}
	
	/**
	 * Disables the current edge
	 */
	public void disable() {
		this.curState = false;
		//removes all the bags from the edge and moves them to the holding area
		//since they are "lost"
		
		while(!curBags.isEmpty()){
			if(curBags.peek() == null){
				curBags.poll();
				continue;
			}
			Overlord.getInstance().disposeBag(Integer.toString(curBags.peek().getID()));
			HoldingArea.getInstance().holdBag(curBags.poll());
		}
		
		inputNode.shouldDisable();
	}
	
	/**
	 * Gives out the first bag on the edge to the node requesting it
	 * Also changes the spot to a null value
	 * 
	 * @return Bag, the first bag on the edge
	 */
	public Bag giveBag(){
		Bag bag = curBags.peek();
		curBags.set(0, null);
		return bag;
	}
	
	/**
	 * Gives out the first bag on the edge to the node requesting it
	 * Also changes the spot to a null value
	 * 
	 * @return InboundBag, the first bag on the edge
	 */
	public InboundBag giveInboundBag(){
		InboundBag bag = (InboundBag)curBags.peek();
		curBags.set(0, null);
		return bag;
		
	}
	
	
	/**
	 * Replaces the last bag with the provided bag
	 * WARNING: This replaces the last bag. Be sure to check to make sure
	 * the last bag is null before replacing it. It is the responsibility of the nod
	 * to check for this, this method does not check to see if it is overwriting
	 * another Bag
	 * 
	 * @param bag the Bag to be placed into the last spot on the edge
	 */
	public void addBag(Bag bag){
		curBags.set(capacity - 1, bag);
	}
	
	/**
	 * Replaces the last bag with the provided bag
	 * WARNING: This replaces the last bag. Be sure to check to make sure
	 * the last bag is null before replacing it. It is the responsibility of the nod
	 * to check for this, this method does not check to see if it is overwriting
	 * another Bag
	 * 
	 * @param bag the InboundBag to be placed into the last spot on the edge
	 */
	public void addInboundBag(InboundBag bag){
		curBags.set(capacity - 1, bag);
	}
	
	/**
	 * Removes a bag from the edge that has gone over the time limit
	 * @param bag
	 */
	public void disposeBag(Bag bag){
		HoldingArea.getInstance().holdBag(bag);
		curBags.set(curBags.indexOf(bag), null);
	}
	
	public String toString() {
		return id;
	}
	
	// *************** GETTERS ***************
	
	// TODO: Comment these
	// TODO: Probably don't need all of these
	
	public String getID() {
		return id;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public Node getInputNode() {
		return inputNode;
	}
	
	public Node getOutputNode() {
		return outputNode;
	}
	
	public LinkedList<Bag> getBags() {
		return curBags;
	}
	
	/**
	 * Getter for curState
	 * @return boolean, true if the edge is enabled
	 */
	public boolean getState(){
		return curState;
	}
	
	// *************** SETTERS ***************
	
	public void setInputNode(Node node) {
		inputNode = node;
	}
	
	public void setOutputNode(Node node) {
		outputNode = node;
	}
}
