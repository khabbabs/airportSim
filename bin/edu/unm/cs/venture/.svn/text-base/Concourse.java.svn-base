package edu.unm.cs.venture;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Concourse  implements Node {
	
	private static int UID = 0;
	private boolean curState;
	private Dimension coordinates;
	private Dimension position;
	private String id;
	private LinkedList<Edge> inputEdges;
	private LinkedList<Edge> outputEdges;
	private LinkedList<Edge> gateGroupEdges;
	private ArrayList<Gate> allGates;
	private LinkedList<Bag> curBag;
	
	
	
	
	public Concourse(Dimension coordinates, Dimension position){
		curState = true;
		this.coordinates = coordinates;
		this.position = position;
		this.id = "Conc"+getUID();
		inputEdges = new LinkedList<Edge>();
		outputEdges = new LinkedList<Edge>();
		gateGroupEdges = new LinkedList<Edge>();
		allGates = new ArrayList<Gate>();
		curBag = new LinkedList<Bag>();
	}
	
	/** Generates a unique ID for a Concourse. */
	private static int getUID() {
		return UID++;
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
	
	
	public void constructConcourse() {
		
		// Working variables
		Dimension relativePos;
		Edge curEdge;
		GateGroup curGroup;
		Gate curGate;
		Collection curColl;
		InboundGate curIGate;
		BagClaim curBagClaim;
		
		// Generate and link gate groups
		for (int i = 0; i < 5; i++) {
			// Find the relative position of this gate group
			relativePos = new Dimension();
			relativePos.height = this.position.height + 15;
			relativePos.width = this.position.width - 20 + 10 * i;
			
			// Generate and position the gate group
			curGroup = new GateGroup(relativePos, Integer.parseInt(this.id.split("Conc")[1]));
			curEdge = new Edge(this, curGroup);
			this.addOutputEdge(curEdge);
			curGroup.addInputEdge(curEdge);
			this.addGateGroupEdge(curEdge);
			
			// Add the group and its input edge to the master lists
			Overlord.getInstance().getAllNodes().add(curGroup);
			Overlord.getInstance().getAllEdges().add(curEdge);
			
			// Generate and position the collections area
			curColl = new Collection(new Dimension(relativePos.width, relativePos.height + 25),
					Integer.parseInt(this.id.split("Conc")[1]));
			
			// Add the collection area to the master list
			Overlord.getInstance().getAllNodes().add(curColl);
			
			// Generate and link gates
			for (int j = 0; j < 5; j++) {
				// Find the relative position of this gate
				relativePos = new Dimension();
				relativePos.height = curGroup.getPos().height + 15;
				relativePos.width = curGroup.getPos().width - 4 + 2 * j;
				
				// Create the gate
				curGate = new Gate(relativePos, Integer.parseInt(this.id.split("Conc")[1]));
				
				// Link the gate back to the gate group
				curEdge = new Edge(curGroup, curGate);
				curGroup.addOutputEdge(curEdge);
				curGate.addInputEdge(curEdge);
				allGates.add(curGate);
				
				// Add the gate and GG link to the master list
				Overlord.getInstance().getAllNodes().add(curGate);
				Overlord.getInstance().getAllEdges().add(curEdge);
				
				// Create the "inbound network" gate
				curIGate = new InboundGate(relativePos, Integer.parseInt(this.id.split("Conc")[1]));
				
				// Link this gate to the collections area
				curEdge = new Edge(curIGate, curColl);
				curIGate.addOutputEdge(curEdge);
				curColl.addInputEdge(curEdge);
				
				// Add the gate and GG link to the master list
				Overlord.getInstance().getAllNodes().add(curIGate);
				Overlord.getInstance().getAllEdges().add(curEdge);
			}
			
			// Add three bag claims
			for (int k = 0; k < 3; k++) {
				// Find the relative positions of the bag claims
				relativePos = new Dimension();
				relativePos.height = curGroup.getPos().height + 30;
				relativePos.width = curGroup.getPos().width - 2 + 2 * k;
				
				
				curBagClaim = new BagClaim(new Dimension(relativePos.width, relativePos.height),
						Integer.parseInt(this.id.split("Conc")[1]));
				
				// Link this claim to the collection
				curEdge = new Edge(curColl, curBagClaim);
				curColl.addOutputEdge(curEdge);
				curBagClaim.addInputEdge(curEdge);

				// Add this claim and its edge to the master list
				Overlord.getInstance().getAllNodes().add(curBagClaim);
				Overlord.getInstance().getAllEdges().add(curEdge);
			}
		}
	}
	

	@Override
	public List<Bag> getBags() {		
		return curBag;
	}

	@Override
	public List<Edge> getInputEdges() {		
		return this.inputEdges;
	}

	@Override
	public List<Edge> getOutputEdges() {		
		return this.outputEdges;
	}

	@Override
	public void giveBag() {
		if(curBag.peek().getEndNode().getCoords() == this.getCoords()){
			int gateGroup = curBag.peek().getDestinationGate() / 5;
			Edge edge = gateGroupEdges.get(gateGroup);
			curBag.peek().addVisited(edge);
			edge.addBag(curBag.poll());
		}
			
		else{
			Edge edge = whichEdge();
			if(edge != null){
				curBag.peek().addVisited(edge);
				edge.addBag(curBag.poll());
			}
		}
	}

	@Override
	public Dimension getCoords() {
		
		return this.coordinates;
	}

	@Override
	public void enable() {
		this.curState=true;
		
	}

	@Override
	public void disable() {
		
		
	}

	@Override
	public Dimension getPos() {
		return this.position;
	}

	@Override
	public void addInputEdge(Edge edge) {
		inputEdges.add(edge);
	}

	@Override
	public void addOutputEdge(Edge edge) {
		outputEdges.add(edge);
	}
	
	/**
	 * Adds an edge to the list gateGroupEdges
	 * @param edge the Edge to be added
	 */
	public void addGateGroupEdge(Edge edge){
		gateGroupEdges.add(edge);
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public boolean isEnabled() {
		return curState;
	}
	
	public ArrayList<Gate> getGates(){
		return allGates;
	}
	
	
	/**
	 * Determines which edge to send the current bag to
	 * @return Edge to which the current bag is sent to
	 */
	private Edge whichEdge(){
		
		//convert the destination coordinates to a Point for distance formula reasons
		Dimension coords = curBag.peek().getEndNode().getCoords();
		Point destPoint = new Point();
		destPoint.x = coords.width;
		destPoint.y = coords.height;
		
		//create arays of Points and doubles to keep track of distance information
		Point[] possibleNodes = new Point[outputEdges.size()];
		double[] distances = new double[outputEdges.size()];
		
		
		//counter keeps track of which value in the array the arrays are at
		int counter = 0;
		for(Edge e:outputEdges){
			possibleNodes[counter] = new Point();
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
			//does not allow if edge is full or if edge goes to a gate group
			//or ifedge is disabled or if output node is on blacklist
			if((outputEdges.get(i).getBags().peekLast() != null) || (gateGroupEdges.indexOf(outputEdges.get(i)) != -1)
			   || !outputEdges.get(i).getState() 
			   ||curBag.peek().getBlackList().indexOf(outputEdges.get(i).getOutputNode()) != -1){
				continue;
			}
			else if(distances[i] < shortestDistance){
				if(outputEdges.get(i).getBags().peekLast() == null){
					correctIndex = i;
					shortestDistance = distances[i];
				}				
			}
		}
		//return the edge which leads to the node with the shortest distance
		//to the destination node
		if(correctIndex < Integer.MAX_VALUE){
			return outputEdges.get(correctIndex);
		}
		//return null if there is nowhere to go (nowhere to hide!)
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
			else if(e.getBags().peek().getTime() > longestTime){
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
		if(curBag.peekLast() == null){
			System.err.println("acceptedEdge = " + acceptedEdge);
		}
		curBag.peekLast().addVisited(this);	
		
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


