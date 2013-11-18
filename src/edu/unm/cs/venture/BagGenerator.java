package edu.unm.cs.venture;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles bag generation for the whole system
 * @author Ronald (wraith55@gmail.com)
 * @author Zach (zach@cs.unm.edu)
 * @author Khabbab (khabbabs@unm.edu)
 * @author Kellen (kzelle@unm.edu)
 *
 */
public class BagGenerator {

	private ArrayList<CollectionPoint> collectionPoints;
	private ArrayList<Concourse> endPoints;
	private int size;
	private int rate;
	private int inboundRate;
	private int legalChance;
	
	private static Random generator;
	
	/**
	 * The constructor for the bag generator object
	 * 
	 * @param collectionPoints array list of collection points
	 * @param endPoints array list of concourses
	 * @param legalChance int between 0 and 99 which represents the chance of it being legal
	 * @param rate the number of bags generated per tick
	 */
	public BagGenerator(ArrayList<CollectionPoint> collectionPoints, ArrayList<Concourse> endPoints, int legalChance, int rate){
		this.collectionPoints = collectionPoints;
		this.size = collectionPoints.size();		
		this.rate = rate;
		this.endPoints = endPoints;
		generator = new Random(5);
		this.legalChance = legalChance;
		
		this.inboundRate = rate;
		
		
	}
	
	/**
	 * Runs on every tick from the overlord
	 */
	public void onTick(){
		
		for(int i = 0; i < rate; i++){
			
			Bag bag = randomBag();
			Overlord.getInstance().getAllBags().put(Integer.toString(bag.getID()), bag);
			
			if(!bag.getStartNode().generateBag(bag)){
				Overlord.getInstance().getAllBags().remove(Integer.toString(bag.getID()));
			}
		}
		
		for(int i = 0; i < inboundRate; i++){
			InboundBag inBag = randomInBag();
			
			
			Overlord.getInstance().getAllBags().put(Integer.toString(inBag.getID()), inBag);
			
			if(!inBag.getInboundStartNode().generateBag(inBag)){
				Overlord.getInstance().getAllBags().remove(Integer.toString(inBag.getID()));
			}
			
		}
	}

	
	/**
	 * Sets the rate of the bag generation
	 * 
	 * @param rate number of bags generated per tick
	 */
	public void setRate(int rate){
		this.rate = rate;
	}
	
	/**
	 * Gets the rate for the bag generation
	 * 
	 * @return rate, the number of bags generated per tick
	 */
	public int getRate(){
		return rate;
	}
	
	/**
	 * sets the legal chance, the chance of a bag being legal/illegal
	 * 
	 * @param legalChance an int 0-99, which represents the percent chance
	 * of a bag being legal
	 */
	public void setLegalChance(int legalChance){
		this.legalChance = legalChance;
	}
	
	
	/**
	 * Gets the legal chance, the chance of a bag being legal/illegal
	 * 
	 * @return legalChance, an int 0-99 which represents the percent chance
	 * of a bag being legal
	 */
	public int getLegalChance(){
		return legalChance;
	}
	
	/**
	 * Generates a random bag using parameters provided from the constructor
	 * 
	 * @return a Bag, fully generated with start point and end point
	 */
	public Bag randomBag(){
		
		
		//random collection point, or start point
		int randomCP = generator.nextInt(size);
		while(!collectionPoints.get(randomCP).isEnabled()){
			//System.out.println("Collection point");
			randomCP = generator.nextInt(size);
		}	
		
		//random concourse, or endpoint
		int randomConcourse = generator.nextInt(5);
		Concourse concourse = endPoints.get(randomConcourse);
		
		//random gate 
		int randomGate = generator.nextInt(25);
		while(!concourse.getGates().get(randomGate).isEnabled()){
			System.out.println("concourse");
			randomGate = generator.nextInt(25);
		}
		
		//determines legality of the bag randomly based on legal chance
		boolean isLegal = (generator.nextInt(100) <= legalChance);
		
		
		Bag bag = new Bag(isLegal, collectionPoints.get(randomCP), endPoints.get(randomConcourse), randomGate);
		
		return bag;
	}
	
	
	public InboundBag randomInBag(){
		
		int randomStart = generator.nextInt(125);
		InboundGate gate = Overlord.getInstance().getInboundGates().get(randomStart);
		
		
		//random gate 
		int randomClaim = generator.nextInt(3);
		

		InboundBag bag = new InboundBag(gate, randomClaim);

		return bag;
	}

	
}
