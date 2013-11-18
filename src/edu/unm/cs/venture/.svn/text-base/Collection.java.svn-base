package edu.unm.cs.venture;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class Collection implements Node {
	
	private static int UID = 0;
	
	private Dimension position;
	private String id;
	private boolean curState;
	private LinkedList<InboundBag> curBag;
	private LinkedList<Edge> inputEdges;
	private LinkedList<Edge> outputEdges;
	
	
	/**
	 * Constructor for the Gate object
	 * @param position 
	 * @param concourse the int value of the concourse the Gate is associated with
	 */
	public Collection(Dimension position, int concourse){
		this.id = "Coll"+((concourse*5)+getUID());
		this.position = position;
		this.curState = true;
		
		curBag = new LinkedList<InboundBag>();
		inputEdges = new LinkedList<Edge>();
		outputEdges = new LinkedList<Edge>();

	}
	
	/** Generates a unique ID for a Gate. */
	public static int getUID() {
		return (UID++)%5;
	}

	@Override
	public void onTick(int phase) {
		if(!curBag.isEmpty()){
		}
		if(phase == 1){
			if(curBag.isEmpty()){
				acceptBag();
			}
		}
		else{
			if(!curBag.isEmpty()){
				giveBag();
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 * Contains no Bags, only InboundBags, so returns null
	 */
	@Override
	public List<Bag> getBags() {
		return null;
	}
	
	/**
	 * Returns a list of InboundBags
	 * @return curBag, list of InboundBags
	 */
	public List<InboundBag> getInboundBags(){
		return curBag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Edge> getInputEdges() {
		return inputEdges;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInputEdge(Edge edge) {
		inputEdges.add(edge);
		
	}

	/**
	 * {@inheritDoc}
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
		
		if(outputEdges.get(curBag.peek().getBagClaim()).getBags().peekLast() == null){
			curBag.peek().addVisited(outputEdges.get(curBag.peek().getBagClaim()));
			outputEdges.get(curBag.peek().getBagClaim()).addInboundBag(curBag.poll());
			
		}
		
	}
	
	/**
	 * 
	 */
	public void acceptBag(){
		//sets original values to be unmachable, as to be able to check if they were actually changed
		int acceptedEdge = Integer.MAX_VALUE;
		int longestTime = 0;

		//keeps track of which edge the highes priority bag is on
		int counter = 0;

		//goes through all input edges, checks their first bags, and accepts the one
		//with the lowest time 
		for(Edge e: inputEdges){
			InboundBag bag = (InboundBag)e.getBags().peek();
			if(bag == null){
				counter++;
				continue;
			}
			if(bag.getTime() > longestTime){
				longestTime = bag.getTime();
				acceptedEdge = counter;
			}

			counter++;			
		}

		//if no edges had any bags to give, return
		if(acceptedEdge == Integer.MAX_VALUE){
			return;
		}

		//take the bag with the highest priority and put it in curBag
		//then set its place on its old edge to null

		curBag.add(inputEdges.get(acceptedEdge).giveInboundBag());

		curBag.peekLast().addVisited(this);	

	}

	/**
	 * {@inheritDoc}
	 * This returns nothing because this node has no coordinates
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
	 * This node cannot be disabled, so calling this method does nothing
	 */
	@Override
	public void disable() {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		return curState;
	}

	@Override
	public String getID() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disposeBag(Bag bag) {
		Overlord.getInstance().disposeBag(Integer.toString(bag.getID()));
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
		// TODO Auto-generated method stub
		
	}
}
