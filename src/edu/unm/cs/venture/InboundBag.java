package edu.unm.cs.venture;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class InboundBag extends Bag{



	/** The amount of time that passes in a single tick. */
	public static final int TICK_TIME = 10;

	/** The unique ID of this piece of baggage. */
	private int id;

	/** The current accumulated time of this bag. */
	private int time;

	private boolean legal;

	/** The collection point this node starts at. */
	private InboundGate startNode;

	private int bagClaim;
	

	/** The history of the path this bag has taken. */
	private final LinkedList<Object> pathTaken;


	/**
	 * Constructor for the InboundBag
	 * @param startNode the InboundGate that the inbound bag is generated at
	 * @param bagClaim int 0-2, selects which bag claim the bag goes to.
	 */
	public InboundBag(InboundGate startNode, int bagClaim) {
		super(true, null, null, bagClaim);
		this.id = super.getUID();
		this.startNode = startNode;
		this.pathTaken = new LinkedList<Object>();
		this.bagClaim = bagClaim;
	}


	

	/**
	 * Advances the cumulative time of this bag on a tick by the tick time.
	 */
	@Override
	public void onTick(Iterator<Map.Entry<String, Bag>> bagIterator){
		
		time += TICK_TIME;
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



	public int getID() {
		return id;
	}


	public int getTime() {
		return time;
	}

	
	
	public InboundGate getInboundStartNode() {
		return startNode;
	}
	
	public LinkedList<Object> getPathTaken() {
		return pathTaken;
	}

	public void addVisited(Object obj){
		pathTaken.add(obj);
	}
	
	public int getBagClaim(){
		return bagClaim;
	}

	
}
