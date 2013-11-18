package edu.unm.cs.venture;

import java.awt.Dimension;
import java.util.List;


/**
 * Represents a node-type object such as a switching hub or a scanner.
 * 
 * @author zach@cs.unm.edu
 * @author rbshaw5@gmail.com
 * @author khabbabs@unm.edu
 * @author kzelle@unm.edu
 */
public interface Node {

	/**
	 * Executes one "unit" (tick) of simulation for this object.
	 * 
	 * @param phase Simulation per object is divided into two phases, 0 and 1.
	 */
	public void onTick(int phase);
	
	
	/**
	 * Gets a list of bags contained in the node.
	 * 
	 * @return A list of bag objects that the node contains.
	 */
	public List<Bag> getBags();
	
	
	/**
	 * Returns a list of edges leading to this node.
	 * 
	 * @return a List of edges leading to this node.
	 */
	public List<Edge> getInputEdges();
	
	
	/**
	 * Adds an edge to the input edge list of this node.
	 * 
	 * @param edge The edge to add to the list.
	 */
	public void addInputEdge(Edge edge);
	
	
	/**
	 * Returns a list of edges coming from this node.
	 * 
	 * @return a List of edges coming from this node.
	 */
	public List<Edge> getOutputEdges();
	
	
	/**
	 * Adds an edge to the output edge list of this node.
	 * 
	 * @param edge The edge to add to the list.
	 */
	public void addOutputEdge(Edge edge);
	
	
	/**
	 * Sends a bag to a connected edge, which is determined based on case-
	 * specific information (where it is in the model).
	 */
	public void giveBag();
	
	
	/**
	 * Returns the Cartesian coordinates of this node relative to other
	 * nodes. Note this is the model coordinates, not GUI coordinates.
	 * 
	 * @return an ordered pair representing relative coordinates.
	 */
	public Dimension getCoords();
	
	/**
	 * returns the position of the element according to its location
	 * on the GUI
	 * @return an ordered pair representing relative positions. 
	 */
	public Dimension getPos();
	
	
	/**
	 * Enables the node.
	 */
	public void enable();
	
	
	/**
	 * Disables the node.
	 */
	public void disable();
	
	/**
	 * Checks if the node is currently enabled
	 * @return true if enabled, false if not
	 */
	public boolean isEnabled();
	
	/**
	 * Returns the nodes ID.
	 * @return ID.
	 */
	public String getID();
	
	/**
	 * Disposes a bag that is over the time limit
	 * @param bag the Bag to be disposed
	 */
	public void disposeBag(Bag bag);
	
	/**
	 * Is called if an output edge is disbaled
	 * If the edge has no valid outputs, it is disabled as well 
	 */
	public void shouldDisable();
	
}
