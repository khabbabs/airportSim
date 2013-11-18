package edu.unm.cs.venture;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class GateGroup implements Node {
	
	private static int UID = 0;
	private LinkedList<Edge> inputEdges;
	private LinkedList<Edge> outputEdges;
	private Dimension position;
	private String id;
	private LinkedList<Bag> curBag;
	
	public GateGroup(Dimension position, int concourse){
		this.id = "GG"+((concourse*5)+getUID());
		this.position = position;
		inputEdges = new LinkedList<Edge>();
		outputEdges = new LinkedList<Edge>();
		curBag = new LinkedList<Bag>();
	}
	
	/** Generates a unique ID for a GateGroup. */
	public static int getUID() {
		return (UID++)%5;
	}
	
	
	

	@Override
	public void onTick(int phase) {
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

	@Override
	public List<Bag> getBags() {
		return this.curBag;
	}

	@Override
	public List<Edge> getInputEdges() {
		return this.inputEdges;
	}

	@Override
	public List<Edge> getOutputEdges() {
		return this.outputEdges;
	}

	/**
	 * Gives its bag to its only outgoing edge, if the edge is available to take a bag.
	 */
	@Override
	public void giveBag() {
		//if the last space on the only outgoing edge is free
		//There should be no reason it isn't free
		if(outputEdges.get(curBag.peek().getDestinationGate() % 5).getBags().peekLast() == null){
			//adds the edge its going to to the bag's visited list
			curBag.peek().addVisited(outputEdges.get(curBag.peek().getDestinationGate() % 5));
			
			//adds the bag to the edge
			outputEdges.get(curBag.peek().getDestinationGate() % 5).addBag(curBag.poll());
		}
		
	}
	
	/**
	 * Looks at the only input edge and checks to see if it has a bag
	 * at its last spot. If it does, it takes it and replaces that bag
	 * with a null value
	 */
	public void acceptBag(){
		//if the incoming edge is presenting a bag
		if(inputEdges.get(0).getBags().peek() != null){
			//add the presented bag to the current bag list
			curBag.add(inputEdges.get(0).getBags().peek());
			
			//adds this object to the bag's visited list
			curBag.peekLast().addVisited(this);
			
			//sets the edge's last space to null
			inputEdges.get(0).getBags().set(0, null);
		}
	}

	/**
	 * Returns arbitrary coordinates for purposes of not causing
	 * errors in distance calculations made in Concourse
	 * @return a Dimension, in this case it is arbitrary
	 */
	@Override
	public Dimension getCoords() {
		Dimension dim = new Dimension();
		dim.width = 100000;
		dim.height = 100000;
		return dim;
	}

	@Override
	public Dimension getPos() {
		return this.position;
	}

	@Override
	public void enable() {		
		
	}

	@Override
	public void disable() {		
		
	}

	@Override
	public void addInputEdge(Edge edge) {
		inputEdges.add(edge);
	}

	@Override
	public void addOutputEdge(Edge edge) {
		outputEdges.add(edge);
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public boolean isEnabled() {
		return true;
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

	/**
	 * {@inheritDoc}
	 * This class cannot be disabled, so this method does nothing
	 */
	@Override
	public void shouldDisable() {
		
		
	}

}
