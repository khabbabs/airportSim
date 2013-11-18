package edu.unm.cs.venture;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SwitchingHub implements Node{
	
	private static int UID = 0;
	private boolean curState;
	private Dimension coordinates;
	private Dimension position;
	private String id;
	private LinkedList<Bag> curBag;
	private LinkedList<Edge> inputEdges;
	private LinkedList<Edge> outputEdges;
		

	/**
	 * Constructor for the switching hub object
	 * @param coordinates Dimension used for algorithm purposes
	 * @param position Dimension used for graphics purposes
	 */
	public SwitchingHub(Dimension coordinates, Dimension position){
		this.curState = true;
		this.coordinates = coordinates;
		this.position = position;
		this.id = "SH"+getUID();
		inputEdges = new LinkedList<Edge>();
		outputEdges = new LinkedList<Edge>();
		curBag = new LinkedList<Bag>();
	}
	
	/** Generates a unique ID for a bag. */
	public static int getUID() {
		return UID++;
	}
	
	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Bag> getBags() {
		return this.curBag;
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
		return this.outputEdges;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void giveBag() {
		Edge edge = whichEdge();
		if(edge != null){
			curBag.peek().addVisited(edge);
			curBag.peek().addToBlackList(this);
			if(curBag.peek().getBlackList().size() > 10){
				curBag.peek().getBlackList().remove();
			}
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
		//removes all bags in the hub and sends them to the holding area
		while(!curBag.isEmpty()){
			Overlord.getInstance().disposeBag(Integer.toString(curBag.peek().getID()));
			HoldingArea.getInstance().holdBag(curBag.poll());
		}
		//disables all incoming edges
		for(Edge e : inputEdges){
			e.disable();			
			
		}
	}
	
	
	/**
	 * Is called if an output edge is disbaled
	 * If the edge has no valid outputs, it is disabled as well 
	 */
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
		inputEdges.add(edge);
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
	 * Getter for the current state
	 * @return boolean which tells if the node is enabled if true
	 */
	public boolean getState(){
		return curState;
	}
	
	
	/**
	 * Determines which edge to send the current bag to
	 * @return Edge to which the current bag is sent to
	 */
	private Edge whichEdge(){
		
		Random rand = new Random();
		
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
			possibleNodes[counter].x =  possibleCoords.width;
			possibleNodes[counter].y =  possibleCoords.height;
			
			distances[counter] = destPoint.distance(possibleNodes[counter]);
			counter++;
		}
		
		//initially set variables to unmatchable values so we know if they've been changed
		int correctIndex = Integer.MAX_VALUE;
		double shortestDistance = Double.MAX_VALUE;
		
		//figure out the shortest distance
		for(int i=0; i<outputEdges.size(); i++){
			//Does not allow edge if it is full
			if(outputEdges.get(i).getBags().peekLast() != null || !outputEdges.get(i).getState()
					|| curBag.peek().getBlackList().indexOf(outputEdges.get(i).getOutputNode()) != -1){
				continue;
			}
			if(distances[i] < shortestDistance){
				if(outputEdges.get(i).getBags().peekLast() == null){
					correctIndex = i;
					shortestDistance = distances[i];
				}				
			}
			else if(distances[i] == shortestDistance){
				if(outputEdges.get(i).getBags().peekLast() == null){
					if(rand.nextInt(2) == 1){
						correctIndex = i;
						shortestDistance = distances[i];
					}
				}
			}
		}
		//return the edge which leads to the node with the shortest diastance
		//to the destination node
		if(correctIndex < Integer.MAX_VALUE){
			return outputEdges.get(correctIndex);
		}
		else{
			curBag.peek().clearBlackList();
			return null;
		}
		
	}
	
	/**
	 * Checks first bag of all incoming edges an accepts the one with the highest time
	 * as it is the highest priority
	 */
	private void acceptBag(){
		//sets original values to be unmachable, as to be able to check if they were actually changed
		int acceptedEdge = Integer.MAX_VALUE;
		int longestTime = 0;
		
		//keeps track of which edge the highes priority bag is on
		int counter = 0;
		
		//goes through all input edges, checks their first bags, and accepts the one
		//with the lowest time 
		for(Edge e: inputEdges){
			if(e.getBags().peek() == null){
				counter++;
				continue;
			}
			if(e.getBags().peek().getTime() > longestTime){
				longestTime = e.getBags().peek().getTime();
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
	
		curBag.add(inputEdges.get(acceptedEdge).giveBag());
	
		curBag.peekLast().addVisited(this.getClass());	
		
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
	
}
