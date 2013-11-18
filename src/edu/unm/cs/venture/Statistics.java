package edu.unm.cs.venture;

public class Statistics {


	private static Statistics instance;
	
	// keeps track of # of bags at a given time bin 15 is over 15 mins
	private volatile int[] times;
    
    private volatile int bagsCompleted = 0;
    private volatile int bagsIncomplete = 0;
    
	private volatile int illegalBags = 0;
    private volatile int totalBags = 0;
    private volatile int totalTime = 0;
    



	public static Statistics getInstance() {
		return instance == null ? instance = new Statistics() : instance;
	}

	/**
	 * Private constructor for a singleton class
	 */
	private Statistics(){
		times = new int[16];	
	}
	
	/**
	 * Add Bag that made it through the system
	 * @param bag Bag that made it.
	 * Must have the eye of the tiger
	 */
	public void addLegalBag(Bag bag){
		times[bag.getTime()/60]++;
		totalBags++;
		bagsCompleted++;
		totalTime += bag.getTime();
		
	}
	
	/**
	 * Add bag to Statistics that didn't make it through the system
	 * @param bag Bag that didn't make it
	 * Poor little guy must have just gotten tired
	 */
	public void addIncompleteBag(Bag bag){
		
		if(!bag.onScan()){
			illegalBags++;
		}
		else{
			totalTime += bag.getTime();
			totalBags++;
			bagsIncomplete++;
		}
	}	
	
	/**
	 * Gets the percentage of Bags that are illegal
	 * @return String, a percentage rate of how many bags are illegal
	 */
	public String getIllegalRate(){
		double rate = (illegalBags+totalBags) == 0 ? 0 : (double)illegalBags / ((double)illegalBags + totalBags);
		rate *= 100; // convert to percent
		return String.format("%.2f", rate);
	}
	
	/**
	 * Gets the percentage of Bags that are incomplete
	 * @return String, a percentage rate of how many bags are incomplete
	 */
	public String getIncompleteRate(){
		double rate = (bagsIncomplete+totalBags) == 0 ? 0 : (double)bagsIncomplete / ((double)bagsIncomplete + totalBags);
		rate *= 100; // convert to percent
		return String.format("%.2f", rate);
	}
	
	/**
	 * Gets the average time of the bags going through the system
	 * @return a double, average time in seconds 
	 */
	public double getAverageTime(){
		return totalBags == 0 ? 0 : totalTime / totalBags;
	}
	
	
	
	public void reset(){
		bagsCompleted = 0;
	    bagsIncomplete = 0;
	    
		illegalBags = 0;
	    totalBags = 0;
	    totalTime = 0;
	}
	
	/*  AUTO GEN */

	public int[] getTimes() {
		return times;
	}
	
	public int getBagsCompleted() {
		return bagsCompleted;
	}

	public int getBagsIncomplete() {
		return bagsIncomplete;
	}

	public int getTotalBags() {
		return totalBags;
	}

	public int getTotalTime() {
		return totalTime;
	}
	
	public int getIllegalBags() {
		return illegalBags;
	}
	
}
