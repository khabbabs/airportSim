package edu.unm.cs.venture;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class Gate implements Node {

	private static int UID = 0;
	private LinkedList<Edge> inputEdges;
	private Dimension position;
	private String id;
	private boolean curState;
	private LinkedList<Bag> curBags;

	/**
	 * Constructor for the Gate object
	 * @param position 
	 * @param concourse the int value of the concourse the Gate is associated with
	 */
	public Gate(Dimension position, int concourse){
		this.id = "Gate"+((concourse*25)+getUID());
		this.position = position;
		this.curState = true;
		inputEdges = new LinkedList<Edge>();
		curBags = new LinkedList<Bag>();

	}
	
	/** Generates a unique ID for a Gate. */
	public static int getUID() {
		return (UID++)%25;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTick(int phase) {
		if(phase == 1){
			acceptBag();
			
		}
		else{
			while(!curBags.isEmpty()){
				giveBag();
			}
		}
		
		

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Bag> getBags() {
		return curBags;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Edge> getInputEdges() {

		return this.inputEdges;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Edge> getOutputEdges() {		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void giveBag() {
		Overlord.getInstance().disposeBag(Integer.toString(curBags.peek().getID()));
		curBags.peek().addVisited(Statistics.getInstance());
		Statistics.getInstance().addLegalBag(curBags.poll());
	}
	
	/**
	 * {@inheritDoc}
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
		
		return this.position;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enable() {
		this.curState = true;
		for(Edge e : inputEdges){
			e.enable();
		}

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disable() {
		this.curState = false;

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
	public void addOutputEdge(Edge edge) {
		// Invalid operation
		//throw new UnsupportedOperationException("Gates have no output edges");
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getID() {
		return id;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		return this.curState;
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
			curBags.add(inputEdges.get(0).getBags().peek());
			
			//adds this object to the bag's visited list
			curBags.peekLast().addVisited(this);
			
			//sets the edge's last space to null
			inputEdges.get(0).getBags().set(0, null);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disposeBag(Bag bag) {
		HoldingArea.getInstance().holdBag(curBags.remove(curBags.indexOf(bag)));
		
	}
	
	@Override
	public String toString() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 * This class can only be disable manually, so this method does nothing
	 */
	@Override
	public void shouldDisable() {
		
		
	}
	
}
