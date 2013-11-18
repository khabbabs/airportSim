package edu.unm.cs.venture;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class BagClaim implements Node{

	private static int UID = 0;
	
	private Dimension position;
	private String id;
	private boolean curState;
	private LinkedList<InboundBag> curBags;
	private LinkedList<Edge> inputEdges;
	private LinkedList<Edge> outputEdges;
	
	
	/**
	 * Constructor for the Gate object
	 * @param position 
	 * @param concourse the int value of the concourse the Gate is associated with
	 */
	public BagClaim(Dimension position, int concourse){
		this.id = "BagClaim"+getUID();
		this.position = position;
		this.curState = true;
		
		curBags = new LinkedList<InboundBag>();
		inputEdges = new LinkedList<Edge>();
		outputEdges = new LinkedList<Edge>();

	}
	
	/** Generates a unique ID for a Gate. */
	public static int getUID() {
		return UID++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTick(int phase) {
		if(phase == 1){
			if(curBags.isEmpty()){
				acceptBag();
			}
		}
		else{
			if(!curBags.isEmpty()){
				giveBag();
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Bag> getBags() {
		return null;
	}
	
	/**
	 * Accepts bag from its only input edge
	 */
	public void acceptBag(){
		if(inputEdges.get(0).getBags().peek() != null){
			curBags.add(inputEdges.get(0).giveInboundBag());
		}
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
		Overlord.getInstance().disposeBag(Integer.toString(curBags.poll().getID()));
		
	}

	/**
	 * {@inheritDoc}
	 * Returns nothing because Claims don't have coordinates
	 */
	@Override
	public Dimension getCoords() {
		// TODO Auto-generated method stub
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
		for(Edge e : inputEdges){
			e.enable();
		}
		
	}

	/**
	 * {@inheritDoc}
	 * BagClaims can't be disabled
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
		HoldingArea.getInstance().holdBag(curBags.remove(curBags.indexOf(bag)));
		
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
