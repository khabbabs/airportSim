package edu.unm.cs.venture;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
* 
* Baggage is generated at these points with 
* random and/or user-controlled properties. 
* Sends baggage to appropriate scanner.
* 
* @author zach@cs.unm.edu
* @author rbshaw5@gmail.com
* @author khabbabs@unm.edu
* @author kzelle@unm.edu
*/
public class CollectionPoint implements Node {
	private static int UID = 0;
	private boolean curState;
	private Dimension coordinates;
	private Dimension position;
	private String id;
	private LinkedList<Edge> outputEdges;
	private LinkedList<Bag> curBag;
	
	
	public CollectionPoint(Dimension coordinates, Dimension position){
		
		curState = true;
		this.coordinates = coordinates;
		this.position = position;
		this.id = "CP"+getUID();
		outputEdges = new LinkedList<Edge>();
		curBag = new LinkedList<Bag>();
	}
	
	/** Generates a unique ID for a collectionPoint. */
	private static int getUID() {
		return UID++;
	}
	
	
	
	@Override
	public void onTick(int phase) {
		if(phase == 1){
			return;
		}
		else{
			if(!curBag.isEmpty()){
				giveBag();
			}
		}
		
	}
	
	
	/**
	 * Determines which edge to send the current bag to
	 * @return Edge to which the current bag is sent to
	 */
	private Edge whichEdge(){
		
		//convert the destination coordinates to a Point for distance formula reasons
		Dimension coords = curBag.peek().getEndNode().getCoords();
		Point destPoint = new Point();
		destPoint.x = (int) coords.getWidth();
		destPoint.y = (int) coords.getHeight();
		
		//create arays of Points and doubles to keep track of distance information
		Point[] possibleNodes = new Point[outputEdges.size()];
		for(int i=0; i<outputEdges.size(); i++){
			possibleNodes[i] = new Point();
		}
		double[] distances = new double[outputEdges.size()];
		
		//counter keeps track of which value in the array the arrays are at
		int counter = 0;
		for(Edge e:outputEdges){
			Dimension possibleCoords = e.getOutputNode().getCoords();
			possibleNodes[counter].x = possibleCoords.width;
			possibleNodes[counter].y = possibleCoords.height;
			
			distances[counter] = destPoint.distance(possibleNodes[counter]);
			counter++;
		}
		
		//initially set variables to unmatchable values so we know if they've been changed
		int correctIndex = Integer.MAX_VALUE;
		double shortestDistance = Double.MAX_VALUE;
		
		//figure out the shortest distance
		for(int i=0; i<outputEdges.size(); i++){
			//Does not allow edge if it is full
			if(outputEdges.get(i).getBags().peekLast() != null || !outputEdges.get(i).getState()){
				continue;
			}
			if(distances[i] < shortestDistance){
				if(outputEdges.get(i).getBags().peekLast() == null){
					correctIndex = i;
					shortestDistance = distances[i];
				}				
			}
		}
		//return the edge which leads to the node with the shortest diastance
		//to the destination node
		if(correctIndex < Integer.MAX_VALUE){
			return outputEdges.get(correctIndex);
		}
		else return null;
		
	}

	@Override
	public List<Bag> getBags() {
		// TODO Auto-generated method stub
		return null;
	}
// DOES NOT EXIST FOR THIS CLASS. LOOK AT THE OBJECT MODEL
	@Override
	public List<Edge> getInputEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Edge> getOutputEdges() {
		return this.outputEdges;
	}
	
	/**
	 * Adds a bag to the curBag, a list of the point's current bags
	 * @param bag the bag to be added
	 */
	public boolean generateBag(Bag bag){
		if(curBag.isEmpty()){
			curBag.add(bag);
			bag.addVisited(this);
			return true;
		}
		else return false;
			
		
	}
	
	/**
	 * Checks if the collection point is available to generate a bag
	 * @return a boolean, true if it can generate false if not
	 */
	public boolean isAvailable(){
		return curBag.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void giveBag() {
		Edge edge = whichEdge();
		if(edge != null){
			curBag.peek().addVisited(edge);
			edge.addBag(curBag.poll());
		}
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getCoords() {
		return this.coordinates;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enable() {
		this.curState = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disable() {
		this.curState = false;
		while(!curBag.isEmpty()){
			Overlord.getInstance().disposeBag(Integer.toString(curBag.peek().getID()));
			HoldingArea.getInstance().holdBag(curBag.poll());
		}
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
	public void addInputEdge(Edge edge) {
		// Invalid operation
		throw new UnsupportedOperationException("Collection points do not have input edges.");
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
	public String getID() {
		return id;
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
	 */
	@Override
	public void shouldDisable(){
		boolean shouldDisable = true;
		//If there are any valid edges, don't disable
		for(Edge e: outputEdges){
			if(e.getState()){
				shouldDisable = false;
			}
		}
		
		//if there are no valid edges, self-disable
		if(shouldDisable){
			this.disable();
		}
		
	}

}
