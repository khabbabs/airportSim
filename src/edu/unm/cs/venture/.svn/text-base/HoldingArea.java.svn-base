package edu.unm.cs.venture;

import java.util.LinkedList;

public class HoldingArea {
	
	LinkedList<Bag> holdingBags;
	
	private static HoldingArea instance;
	
	/**
	 * Singleton method, always returns the same instance of a class so other classes can acces it
	 * @return instance of HoladingArea so its methods can be called
	 */
	public static HoldingArea getInstance() {
		return instance == null ? instance = new HoldingArea() : instance;
	}
	
	/**
	 * Is called every time the Overlord ticks
	 * Disposes all bags in the holding area and moves them to statistics
	 */
	public void onTick(){
		while(!holdingBags.isEmpty()){
			disposeBag(holdingBags.poll());
		}
	}
	
	/**
	 * Constructor for the class
	 */
	public HoldingArea(){
		holdingBags = new LinkedList<Bag>();
	}
	
	/**
	 * Adds a Bag to the holding area
	 * @param bag the Bag to be held
	 */
	public void holdBag(Bag bag){
		holdingBags.add(bag);
		bag.addVisited(this);
	}
	
	/**
	 * Sends a bag to statistics to be disposed of
	 * @param bag the Bag to be disposed
	 */
	private void disposeBag(Bag bag){
		Statistics.getInstance().addIncompleteBag(bag);
	}
	
}
