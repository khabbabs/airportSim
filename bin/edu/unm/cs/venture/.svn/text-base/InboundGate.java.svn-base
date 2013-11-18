package edu.unm.cs.venture;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class InboundGate implements Node{

	private static int UID = 0;
	
	private Dimension position;
	private String id;
	private boolean curState;
	private LinkedList<Bag> curBag;
	private LinkedList<Edge> outputEdges;
	
	
	/**
	 * Constructor for the Gate object
	 * @param position 
	 * @param concourse the int value of the concourse the Gate is associated with
	 */
	public InboundGate(Dimension position, int concourse){
		this.id = "InboundGate"+getUID();
		this.position = position;
		this.curState = true;
		
		curBag = new LinkedList<Bag>();
		outputEdges = new LinkedList<Edge>();

	}
	
	/** Generates a unique ID for a Gate. */
	public static int getUID() {
		return UID++;
	}

	/**
	 * {@inheritDoc}
	 * @param phase of the tick
	 */
	@Override
	public void onTick(int phase) {
		if(phase == 2){
			if(!curBag.isEmpty()){
				giveBag();
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 * @return curBag, the list of current bags at the inbound gate
	 */
	@Override
	public List<Bag> getBags() {
		return curBag;
	}

	/**
	 * {@inheritDoc}
	 * @return null, inbound gates have no input edges
	 */
	@Override
	public List<Edge> getInputEdges() {
	
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @param edge
	 * This method doesn't do anything, inbound gates have no input edges
	 */
	@Override
	public void addInputEdge(Edge edge) {
	
		
	}

	/**
	 * {@inheritDoc}
	 * @return outputEdges , a List of edges that are outgoing from this node
	 */
	@Override
	public List<Edge> getOutputEdges() {
		return outputEdges;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOutputEdge(Edge edge) {
		outputEdges.add(edge);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void giveBag() {
		Edge edge = outputEdges.get(0);
		curBag.peek().addVisited(edge);
		edge.addBag(curBag.poll());
		
	}

	/**
	 * {@inheritDoc}
	 * This method returns nothing, because inbound gates have no coordinates
	 */
	@Override
	public Dimension getCoords() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPos() {
		return position;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enable() {
		curState = true;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disable() {
		curState = false;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		return curState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getID() {
		return id;
	}
	
	/**
	 * Adds a bag to the curBag, a list of the point's current bags
	 * @param bag the bag to be added
	 */
	public boolean generateBag(InboundBag bag){
		if(curBag.isEmpty()){
			curBag.add(bag);
			bag.addVisited(this);
			return true;
		}
		else return false;	
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disposeBag(Bag bag) {
		HoldingArea.getInstance().holdBag(curBag.remove(curBag.indexOf(bag)));
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return id;
	}

	@Override
	public void shouldDisable() {
	
	}
}
