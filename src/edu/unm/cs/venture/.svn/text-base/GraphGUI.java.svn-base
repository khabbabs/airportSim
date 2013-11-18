package edu.unm.cs.venture;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import edu.unm.cs.venture.Overlord.SimLooper;

import net.miginfocom.swing.MigLayout;


public class GraphGUI {
	
	private JFrame frame;
	private Graph graph;
	private SpriteManager sManager;

	/** Construct the GUI object and initialize the elements on screen. */
	public GraphGUI() {
		// Make GraphStream use the advanced renderer
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		// Create the Swing GUI
		initialize();
		
		// High-quality graph settings
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
	}

	/**
	 * GUI elements initialization implementation. Good practice to keep this
	 * separate from the actual constructor.
	 */
	private void initialize() {
		/* *********************************
		 * MAIN FRAME
		 * *********************************/
		
		frame = new JFrame();
		frame.setBounds(50, 50, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[265, fill][grow, fill]", "[fill, grow]"));
		
		/* **********************************
		 * CONTROL PANEL
		 * **********************************/
		
		JPanel controlPanel = new JPanel(new MigLayout("flowy", "[fill, grow]", "[][][][][fill, grow]"));
		JScrollPane scrollControlView = new JScrollPane(controlPanel);
		
		/* **********************************
		 * SIMULATION PANEL
		 * **********************************/
		
		// Run simulation checkbox
		JCheckBox runSimChkBox = new JCheckBox(new AbstractAction("Run Simulation") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBox source = (JCheckBox)event.getSource();
				
				// If the checkbox is ticked, run the simulation
				if (source.isSelected()) {
					Overlord.getInstance().setRun(true);
					Timer timer = new Timer();
					SimLooper simLoop = Overlord.getInstance().new SimLooper(timer);
					timer.scheduleAtFixedRate(simLoop, 0, Overlord.TICK_LENGTH);
				}
				// If the checkbox is not ticked, stop the simulation
				else {
					Overlord.getInstance().setRun(false);
				}
			}
		});
		
		// Generate bags checkbox
		JCheckBox genBagsChkBox = new JCheckBox(new AbstractAction("Generate Bags") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				Overlord.getInstance().toggleBagGen();
			}
		});
		genBagsChkBox.setSelected(true); // Gen bags is checked by default
		
		// Bag generation rate slider
		JSlider rateSlider = new JSlider(0, 10, Overlord.getInstance().bagGenerator.getRate());
		rateSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				Overlord.getInstance().bagGenerator.setRate(source.getValue());
			}
		});
		
		//Turn on labels at major tick marks.
		rateSlider.setMajorTickSpacing(2);
		rateSlider.setMinorTickSpacing(1);
		rateSlider.setPaintTicks(true);
		rateSlider.setPaintLabels(true);
		rateSlider.setSnapToTicks(true);
		
		// Rate slider label
		JLabel lblRateSlider = new JLabel("Rate (bags/" + Bag.TICK_TIME + " second(s))", JLabel.CENTER);
		
		// Set up the panel
		JPanel simPanel = new JPanel(new MigLayout("flowy", "[fill, grow]" , "[][][][]"));
		simPanel.setBorder(BorderFactory.createTitledBorder("Simulation"));
		
		simPanel.add(runSimChkBox);
		simPanel.add(genBagsChkBox);
		simPanel.add(rateSlider);
		simPanel.add(lblRateSlider);
		
		/* ***************************************
		 * LABELS PANEL
		 * ***************************************/
		
		// Edge labels Checkbox
		JCheckBox labelEdgesChkBox = new JCheckBox(new AbstractAction("Edges") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				
				JCheckBox source = (JCheckBox)event.getSource();
				
				// Turn on labels
				if (source.isSelected()) {
					graph.addAttribute("ui.stylesheet", "edge {text-mode: normal;}");
				}
				// Turn off labels
				else {
					graph.addAttribute("ui.stylesheet", "edge {text-mode: hidden;}");
				}
			}
		});
		
		// Node labels Checkbox
		JCheckBox labelNodesChkBox = new JCheckBox(new AbstractAction("Nodes") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				
				JCheckBox source = (JCheckBox)event.getSource();
				
				// Turn on labels
				if (source.isSelected()) {
					graph.addAttribute("ui.stylesheet", "node {text-mode: normal;}");
				}
				// Turn off labels
				else {
					graph.addAttribute("ui.stylesheet", "node {text-mode: hidden;}");
				}
			}
		});
		
		// Node labels Checkbox
		JCheckBox labelBagsChkBox = new JCheckBox(new AbstractAction("Bags") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {

				JCheckBox source = (JCheckBox)event.getSource();

				// Turn on labels
				if (source.isSelected()) {
					graph.addAttribute("ui.stylesheet", "sprite {text-mode: normal;}");
				}
				// Turn off labels
				else {
					graph.addAttribute("ui.stylesheet", "sprite {text-mode: hidden;}");
				}
			}
		});
		
		// Set up the panel
		JPanel labelPanel = new JPanel(new MigLayout("flowy", "[fill, grow]" , "[][][]"));
		labelPanel.setBorder(BorderFactory.createTitledBorder("Labels"));
		
		labelPanel.add(labelEdgesChkBox);
		labelPanel.add(labelNodesChkBox);
		labelPanel.add(labelBagsChkBox);
		
		/* **************************************
		 * DISABLE PANEL
		 * **************************************/
		
		// Generate option lists for the combo boxes
		ArrayList<Node> allNodes = Overlord.getInstance().getAllNodes();
		LinkedList<Edge> allEdges = Overlord.getInstance().getAllEdges();
		
		// Starting/Ending points avoid displaying swaths of non-disablable elements
		int nodeBegin = 50; int nodeEnd = 70;   // end point is +1
		int edgeBegin = 600; int edgeEnd = 661; // end point is +1
		
		// String array of options
		Node[] nodeOptions = new Node[nodeEnd - nodeBegin];
		Edge[] edgeOptions = new Edge[edgeEnd - edgeBegin];
		
		// Node options
		for (int i = nodeBegin; i < nodeEnd; i++) {
			nodeOptions[i - nodeBegin] = allNodes.get(i);
		}
		
		// Edge options
		for (int i = edgeBegin; i < edgeEnd; i++) {
			edgeOptions[i - edgeBegin] = allEdges.get(i);
		}
		
		// Create the combo boxes
		final JComboBox nodeComboBox = new JComboBox(nodeOptions);
		final JComboBox edgeComboBox = new JComboBox(edgeOptions);
		
		// Node disable toggle button
		JButton nodeDisableButton = new JButton(new AbstractAction("Toggle Selected Node") {

			private static final long serialVersionUID = 1L;
			private Node selectedNode;
			
			@Override
			public void actionPerformed(ActionEvent event) {
				 selectedNode = (Node)nodeComboBox.getSelectedItem();

				// If the simulation is running, change state safely
				if (Overlord.getInstance().getRun()) {
					Overlord.getInstance().scheduleStateChange(selectedNode);
				}
				// Otherwise just update it right away
				else {
					if (selectedNode.isEnabled()) {
						selectedNode.disable();
					} else {
						selectedNode.enable();
					}
					
					// Reflect all possible changes in GUI
					for (Node node : Overlord.getInstance().getAllNodes()) {
						Overlord.getInstance().gui.updateModel(node);
					}
					for (Edge edge : Overlord.getInstance().getAllEdges()) {
						Overlord.getInstance().gui.updateModel(edge);
					}
				}
			}
		});
		
		// Edge disable toggle button
		JButton edgeDisableButton = new JButton(new AbstractAction("Toggle Selected Edge") {

			private static final long serialVersionUID = 1L;
			private Edge selectedEdge;

			@Override
			public void actionPerformed(ActionEvent event) {
				selectedEdge = (Edge)edgeComboBox.getSelectedItem();
				
				// If the simulation is running, change state safely
				if (Overlord.getInstance().getRun()) {
					Overlord.getInstance().scheduleStateChange(selectedEdge);
				}
				// Otherwise just update it right away
				else {
					if (selectedEdge.getState()) {
						selectedEdge.disable();
					} else {
						selectedEdge.enable();
					}
					
					// Reflect all possible changes in GUI
					for (Node node : Overlord.getInstance().getAllNodes()) {
						Overlord.getInstance().gui.updateModel(node);
					}
					for (Edge edge : Overlord.getInstance().getAllEdges()) {
						Overlord.getInstance().gui.updateModel(edge);
					}
				}
			}
		});
		
		// Set up the panel
		JPanel disablePanel = new JPanel(new MigLayout("flowy", "[fill, grow]" , "[][][][][]"));
		disablePanel.setBorder(BorderFactory.createTitledBorder("Disable Components"));
		
		disablePanel.add(nodeComboBox);
		disablePanel.add(nodeDisableButton);
		disablePanel.add(edgeComboBox);
		disablePanel.add(edgeDisableButton);
		
		/* ***************************************
		 * STATISTICS PANEL
		 * ***************************************/
		
		// Field name labels
		JLabel[] fieldNames = new JLabel[7];
		fieldNames[0] = new JLabel("Bags Completed:");
		fieldNames[1] = new JLabel("Bags Incomplete:");
		fieldNames[2] = new JLabel("Bags Incomplete %:");
		fieldNames[3] = new JLabel("Total Bags:");
		fieldNames[4] = new JLabel("Illegal Bags:");
		fieldNames[5] = new JLabel("Illegal Bag %:");
		fieldNames[6] = new JLabel("Average Time:");
		
		// Field labels
		final JLabel[] fields = new JLabel[fieldNames.length];
		for (int i=0;i<fields.length;i++) fields[i] = new JLabel("", JLabel.RIGHT);
		
		// Set up the panel
		JPanel statsPanel = new JPanel(new MigLayout("wrap", "[][fill, grow]" , "[][][][][][][][]"));
		statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
		
		for (int i=0;i<fields.length;i++) {
			statsPanel.add(fieldNames[i]);
			statsPanel.add(fields[i]);
		}
		
		// Add the reset button
		JButton clearStatsButton = new JButton(new AbstractAction("Reset Statistics") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Statistics.getInstance().reset();
			}
		});
		statsPanel.add(clearStatsButton, "span 2, grow");
		
		// Create a swing timer to constantly update statistics in sorta real time
		ActionListener updateStats = new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent evt) {
		        fields[0].setText(Integer.toString(Statistics.getInstance().getBagsCompleted()));
		        fields[1].setText(Integer.toString(Statistics.getInstance().getBagsIncomplete()));
		        fields[2].setText(Statistics.getInstance().getIncompleteRate());
		        fields[3].setText(Integer.toString(Statistics.getInstance().getTotalBags()));
		        fields[4].setText(Integer.toString(Statistics.getInstance().getIllegalBags()));
		        fields[5].setText((Statistics.getInstance().getIllegalRate()));
		        fields[6].setText(Double.toString(Statistics.getInstance().getAverageTime()));
		    }
		};

		// Execute the stats update timer
		new javax.swing.Timer(1000, updateStats).start();
		
		/* ***************************************
		 * GRAPH VIEWER
		 * ***************************************/
		
		// Graph viewer
		this.graph = new MultiGraph("Airport");
		View view = initGraph(graph);
		
		// Set up the sprite manager and the CSS for styling
		sManager = new SpriteManager(graph);
		graph.addAttribute("ui.stylesheet", "url('src/model/graph.css')");

		
		/* *****************************************
		 * ADD CONTENT
		 * *****************************************/
		
		// Control Panel content
		controlPanel.add(simPanel);
		controlPanel.add(labelPanel);
		controlPanel.add(disablePanel);
		controlPanel.add(statsPanel);
		
		// Main frame content
		Container contentPane = frame.getContentPane();
		contentPane.add(scrollControlView);
		contentPane.add(view);
	}
	
	
	/**
	 * Initializes the graph viewer by "installing" our model into GraphStream's
	 * representation.
	 * 
	 * @param graph The graph object to install to.
	 * @return A view of the GraphStream graph.
	 */
	private View initGraph(Graph graph) {
		
		// Add all of the nodes to the graph
		for (Node node : Overlord.getInstance().getAllNodes()) {
			// Add node to graph and set its xy based on the data from the node properties CSV
			MultiNode curNode = graph.addNode(node.getID());
			curNode.setAttribute("xy", node.getPos().width, node.getPos().height);
			
			// Assign the node a ui.class based on its class
			curNode.setAttribute("ui.class", node.getClass().getSimpleName());
			
			// Add a label
			curNode.addAttribute("ui.label", node.getID());
		}
		
		// Add all edges to the graph
		for (Edge edge : Overlord.getInstance().getAllEdges()) {
			
			org.graphstream.graph.Edge graphEdge = graph.addEdge(edge.getID(), edge.getInputNode().getID(), edge.getOutputNode().getID(), true);
			
			// Don't display this edge if it is between a collection point and a scanner
			if (edge.getInputNode().getClass() == CollectionPoint.class) {
				graphEdge.addAttribute("ui.class", "invisible");
			}
			// Don't display the arrows if these are attached to the collection area
			else if (edge.getInputNode().getClass() == Collection.class ||
					  edge.getOutputNode().getClass() == Collection.class) {
				graphEdge.addAttribute("ui.class", "noArrow");
			}
			
			// Add a label
			graphEdge.addAttribute("ui.label", edge.getID());
		}
		
		// Create a graph viewer pane
		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.disableAutoLayout();
		View view = viewer.addDefaultView(false);   // false indicates "no JFrame".
		
		// Remove the only mouse listener on the graph
		view.removeMouseListener(view.getMouseListeners()[0]);
		
		return view;
	}
	
	
	/**
	 * Updates the rendering of the graph to reflect newly disabled or enabled elements.
	 * @param element The element to change
	 */
	public void updateModel(Object element) {
		
		// EDGE
		if (element.getClass() == Edge.class) {
			Edge edge = (Edge)element;
			
			// "Enable" the edge
			if (edge.getState()) {
				graph.getEdge(edge.getID()).addAttribute("ui.style", "shadow-mode: none;");
			}
			// "Disable" the edge
			else {
				graph.getEdge(edge.getID()).addAttribute("ui.style", "shadow-mode: plain;");
			}
			
		// NODE
		} else if (element instanceof Node) {
			Node node = (Node)element;

			// "Enable" the node
			if (node.isEnabled()) {
				graph.getNode(node.getID()).addAttribute("ui.style", "fill-color: black;");
			}
			// "Disable" the edge
			else {
				graph.getNode(node.getID()).addAttribute("ui.style", "fill-color: red;");
			}
		}
	}
	
	
	/**
	 * Loops through every bag in the system and either updates the position of
	 * the bag or creates a new sprite for new bags.
	 */
	public void updateBags() {
		
		Sprite sprite;
		Random rand = new Random();
		
		// Retrieve a set backed by the hash map
		for (Map.Entry<String, Bag> entry : Overlord.getInstance().getAllBags().entrySet()) {
			Bag bag = entry.getValue();
			
			// Check if bag is new (isn't in the sprite manager yet)
			if (!sManager.hasSprite(Integer.toString(bag.getID()))) {
				sprite = addBagSprite(Integer.toString(bag.getID()), rand, bag);
				sprite.addAttribute("ui.label", Integer.toString(bag.getID()));
			} else {
				sprite = sManager.getSprite(Integer.toString(bag.getID()));
			}
			
			// Get the bag's current location
			Object bagContainer = bag.getPathTaken().peekLast();
			
			// Edge
			if (bagContainer.getClass() == Edge.class) {
				Edge edge = (Edge)bagContainer;
				
				int index = (edge.getBags().size() - 1) - edge.getBags().indexOf(bag); // index is backwards
				org.graphstream.graph.Edge graphEdge = graph.getEdge(edge.getID());
				
				sprite.attachToEdge(graphEdge.getId());
				sprite.setPosition( (double)(index + 1) / (edge.getCapacity() + 1) );
			}
			// Node
			else if (bagContainer instanceof Node) {
				Node node = (Node)bagContainer;
				
				org.graphstream.graph.Node graphNode = graph.getNode(node.getID());
				sprite.attachToNode(graphNode.getId());
				sprite.setPosition(0D, 0D, 0D);
			}
		}
		
		// Remove bags from the sprite list if they are out of the system
		for (Map.Entry<String, Bag> entry : Overlord.getInstance().getDisposedBags().entrySet()) {
			sManager.removeSprite(entry.getKey());
		}
	}
	
	
	/**
	 * Creates a new sprite for a bag and randomly assigns it a color.
	 * @param id The ID of the new sprite
	 * @param rand Random for color choosing
	 * @return A new sprite for the bag
	 */
	private Sprite addBagSprite(String id, Random rand, Bag bag) {
		
		Sprite sprite = sManager.addSprite(id);
		
		// If the bag is in the main network, give it a pretty color
		if (bag.getClass() == Bag.class) {
			switch (rand.nextInt(5)) {

			case 0:
				sprite.addAttribute("ui.class", "red");
				break;
			case 1:
				sprite.addAttribute("ui.class", "blue");
				break;
			case 2:
				sprite.addAttribute("ui.class", "yellow");
				break;
			case 3:
				sprite.addAttribute("ui.class", "green");
				break;
			default:
			case 4:
				sprite.addAttribute("ui.class", "purple");
				break;
			}
		}
		// Otherwise give the color a lame grayscale color
		else {
			switch (rand.nextInt(5)) {
			case 0:
				sprite.addAttribute("ui.class", "gray1");
				break;
			case 1:
				sprite.addAttribute("ui.class", "gray2");
				break;
			case 2:
				sprite.addAttribute("ui.class", "gray3");
				break;
			case 3:
				sprite.addAttribute("ui.class", "gray4");
				break;
			default:
			case 4:
				sprite.addAttribute("ui.class", "gray5");
				break;
			}

		}

		return sprite;
	}

	
	/**
	 * Gets the Swing frame.
	 * @return the main application Swing frame.
	 */
	public JFrame getFrame() {
		return frame;
	}

}
