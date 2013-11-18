package edu.unm.cs.venture;

import static org.junit.Assert.*;

import java.awt.Dimension;

import org.junit.Test;

public class TestBag {

	CollectionPoint p = new CollectionPoint(new Dimension(1,1), new Dimension(2,2));
	Node n = new SwitchingHub(new Dimension(3,3), new Dimension(4,4));
	Bag b = new Bag(true,p,n,1);
	
	
	@Test
	public void testBag() {
		 assertTrue(b.getClass()!=null);
	}

	@Test
	public void testGetUID() {
		 assertTrue(b.getID()==1);
	}

	@Test
	public void testAddToBlackList() {
		b.addToBlackList(new Scanner(new Dimension(1,1), new Dimension(2,2)));
		
	}


	@Test
	public void testOnScan() {
		assertTrue(b.onScan());
	}


	@Test
	public void testGetStartNode() {
		assertTrue(b.getStartNode() instanceof CollectionPoint);
	}

	@Test
	public void testGetEndNode() {
		assertTrue(b.getEndNode() instanceof SwitchingHub);
	}

	@Test
	public void testGetDestinationGate() {
		assertTrue(b.getDestinationGate()==1);
	}

	

}
