package edu.unm.cs.venture;

import static org.junit.Assert.*;

import java.awt.Dimension;

import org.junit.Test;

public class TestConcourse {

	Dimension dim = new Dimension(0, 0);
	
	@Test
	public final void testConcourse() {
		Concourse concourse = new Concourse(dim, dim);
		
		// Display the output nodes of the concourse
		System.err.println("Concourse " + concourse.getID() + " has output nodes: ");
		for (Edge outputEdge : concourse.getOutputEdges()) {
			System.err.print(outputEdge.getOutputNode().getID() + ": ");
			
			// Display attached gates to each group
			for (Edge outputEdge2 : outputEdge.getOutputNode().getOutputEdges()) {
				System.err.print(outputEdge2.getOutputNode().getID() + ", ");
			}
			
			System.err.println();
		}
	}
}
