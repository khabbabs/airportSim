package edu.unm.cs.venture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TestAirport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	     initModel();
		Airport.main(null);

	}
	
	private static void initModel() {
		
		
		ArrayList<CollectionPoint> allCp = new ArrayList<CollectionPoint>();
		ArrayList<Concourse> allCon = new ArrayList<Concourse>();
		// Get instance of the Overlord
		Overlord overlord = Overlord.getInstance();
		
		// Adjacency matrices and node properties
		boolean[][] collectionToScanners = null;
		boolean[][] switchingNetwork = null;
		int[][] nodeProperties = null;
		
		// Try to load the model files
		try {
			collectionToScanners = Parser.parseFileForBool(new File("src/model/CollectionToScanners.csv"), 55, 55);
			switchingNetwork = Parser.parseFileForBool(new File("src/model/SwitchingNetwork.csv"), 25, 25);
			nodeProperties = Parser.parseFileForInt(new File("src/model/NodeProperties.csv"), 6, 80);
		}
		catch (FileNotFoundException e) {
			System.err.println("Could not find file! Exception msg: " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println("Could not load file! Exception msg: " + e.getMessage());
		}
		
		// Generate the model
		overlord.constructModel(nodeProperties, collectionToScanners, switchingNetwork);
		
		
		for(CollectionPoint c : overlord.getCollectionPoints()){
	    	 allCp.add(c);
	     }
		for(Concourse c : overlord.getConcourses()){
	    	 allCon.add(c);
	     }
		/*
		Bag testBag = new Bag(true,allCp.get(0),allCon.get(3),20);
		allCp.get(0).generateBag(testBag);
		overlord.getAllBags().put(Integer.toString(testBag.getID()), testBag);
		
		
		
		Bag testBag2 = new Bag(true,allCp.get(1),allCon.get(0),2);
		allCp.get(1).generateBag(testBag2);
		overlord.getAllBags().put(Integer.toString(testBag2.getID()), testBag2);
		*/
		
		Random rand1 = new Random();
		

		for(int i =0;i<50;i++){
			Bag tempBag = new Bag(true,allCp.get(i),allCon.get(rand1.nextInt(5)),rand1.nextInt(25));
			allCp.get(i).generateBag(tempBag);
			overlord.getAllBags().put(Integer.toString(tempBag.getID()), tempBag);
		}


		/*
		Bag testBag3 = new Bag(true,allCp.get(0),allCon.get(3),20);
		allCp.get(0).generateBag(testBag3);
		overlord.getAllBags().put(Integer.toString(testBag3.getID()), testBag3);
		*/
		
	   ArrayList<Bag> bagsToTest = new ArrayList<Bag>();
	   
	   System.out.println();
	   //rand1.nextInt(allCp.size());

	   Random rand2 = new Random();
	   //rand2.nextInt(allCon.size());

	   
	   Random rand3 = new Random();
	   //rand3.nextInt(25);
	   
	   /*
		for(int i=0;i<50;i++){
			bagsToTest.add(new Bag(true,allCp.get(i),allCon.get(rand2.nextInt(allCon.size())),rand3.nextInt(25)));
		}
		
		for(Bag b : bagsToTest){
			
			
			for(CollectionPoint p : allCp){
				if(p==b.getStartNode()){
					p.generateBag(b);
				}
			}
			
			
			
			overlord.getAllBags().put(Integer.toString(b.getID()), b);
		}*/
		
		
		
		
		 
		/*while(i!=0){
			//testBag.onTick();
			 overlord.tick();
		     //testBag.onTick();
		     e = (Edge)testBag.getPathTaken().peekLast();
			//System.out.println(i+": "+testBag.getPathTaken().peekLast());
		    //System.out.println(i+": "+e.getID());
			//i--;
		}*/
		
		
		
		
		
		/*for(Edge b : overlord.getAllEdges()){
			//System.out.println("tits");
			if(b.getID().equals("Edge115")){
				System.out.println("edge 115 outputs to : "+b.getOutputNode());
			}
		}*/
		
		/*for(Edge p : allCp.get(0).getOutputEdges()){
		System.out.println(p.getID()+"is connected to "+ p.getOutputNode().getID());
		}*/
		
		
		
		//BagGenerator BagGen = new BagGenerator(allCp,allCon,90,5); 
	}
	
	
	

}
