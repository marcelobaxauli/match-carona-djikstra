package com.mbax.djikstra;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mbax.model.ObjectiveFunction;
import com.mbax.model.TimeRestriction;

public class Graph {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	private Map<Integer, Node> nodes;

	private List<Node> minimumCostList;
	private List<Node> instanceMinimumCostList;

	private Node firstNode;
	private Node lastNode;

	private Date rideDepartTime;
	private Date rideArriveTime;

	private int currentSize;

	private int carCapacity;
	
	private ObjectiveFunction objectiveFunction;

	public Graph(int maxNodes) {

		this.minimumCostList = new ArrayList<Node>();

		this.nodes = new HashMap<Integer, Node>(maxNodes, 1);

		this.objectiveFunction = new ObjectiveFunction(3, 4 * 60, 30);
		
		for (int i = 0; i < maxNodes; i++) {
			Node newNode = new Node(i, this);

			nodes.put(i, newNode);
			this.minimumCostList.add(newNode);

		}

		System.out.println("creating vertex:");
		for (int i = maxNodes - 1; i >= 0; i--) {
			for (int j = 0; j < maxNodes - 1; j++) {

				if (i != j) {
					Node sourceNode = nodes.get(i);
					Node targetNode = nodes.get(j);

					Vertex newVertex = new Vertex();
					newVertex.setTargetNode(targetNode);
					newVertex.setI(i);
					newVertex.setJ(j);
					
					sourceNode.addOutputVertex(newVertex);
				}

			}

			if (i % 1000 == 0) {
				System.out.println(sdf.format(new Date()) + ": i = " + i);
			}
		}

	}

	public void configure(int cost[][], int n, int carCapacity, List<TimeRestriction> timeRestrictionList) {

		this.instanceMinimumCostList = new LinkedList<Node>();

		for (int i = 0; i < n; i++) {
			Node node = this.nodes.get(i);						
			
			if (i == n - 1) {
				node.setCurrentMinimumPathCost(0);
			} else {
				node.setCurrentMinimumPathCost(Integer.MAX_VALUE);
			}
			node.setTimeRestriction(timeRestrictionList.get(i));			
			this.instanceMinimumCostList.add(node);
		}

		this.currentSize = n;
		this.carCapacity = carCapacity;

		this.firstNode = this.nodes.get(0);
		this.lastNode = this.nodes.get(n - 1);

		for (int i = 0; i < n; i++) {

			Node sourceNode = this.nodes.get(i);

			for (Vertex vertex : sourceNode.getOutputVertexes()) {

				if (vertex.getI() >= n || vertex.getJ() >= n) {
					break;
				}

				vertex.setTimeCost(cost[vertex.getI()][vertex.getJ()]);

			}

		}

		// último nó não possui vertices de saída
		Node sourceNode = this.nodes.get(n - 1);
		sourceNode.setTimeRestriction(timeRestrictionList.get(n - 1));

		this.rideDepartTime = new Date(this.nodes.get(0).getTimeRestriction().getDepartTime());
		this.rideArriveTime = new Date(this.nodes.get(0).getTimeRestriction().getArriveTime());

	}

	public Node getMinimumCostNode() {

		this.instanceMinimumCostList.sort(new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				return o1.getCurrentMinimumPathCost() - o2.getCurrentMinimumPathCost();
			}

		});
		return this.instanceMinimumCostList.remove(0);
	}

	public Node getFirstNode() {
		return firstNode;
	}

	public void setFirstNode(Node firstNode) {
		this.firstNode = firstNode;
	}

	public Node getLastNode() {
		return lastNode;
	}

	public void setLastNode(Node lastNode) {
		this.lastNode = lastNode;
	}

	public Date getRideDepart() {
		return this.rideDepartTime;
	}

	public Date getRideArriveTime() {
		return this.rideArriveTime;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public int getCarCapacity() {
		return carCapacity;
	}

	public void setCarCapacity(int carCapacity) {
		this.carCapacity = carCapacity;
	}
	
	public int getObjectiveValue(int numberOfPassengers, int timeCost) {
		return this.objectiveFunction.getObjectiveFunctionValue(numberOfPassengers, timeCost);
	}

}
