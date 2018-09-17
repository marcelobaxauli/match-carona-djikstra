package com.mbax.djikstra;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mbax.model.TimeRestriction;

public class Graph {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	private Map<Integer, Node> nodes;

	private List<Node> minimumCostList;
	private Node firstNode;
	private Node lastNode;

	private Date rideDepartTime;
	private Date rideArriveTime;

	private int currentSize;

	private int carCapacity;

	public Graph(int maxNodes) {

		this.minimumCostList = new ArrayList<Node>();

		this.nodes = new HashMap<Integer, Node>(maxNodes, 1);

		for (int i = 0; i < maxNodes; i++) {
			Node newNode = new Node(i, this);

			nodes.put(i, newNode);
			this.minimumCostList.add(newNode);

		}

		System.out.println("creating vertex:");
		for (int i = 0; i < maxNodes; i++) {
			for (int j = 1; j < maxNodes; j++) {

				if (i != j) {
					Node sourceNode = nodes.get(i);
					Node targetNode = nodes.get(j);

					Vertex newVertex = new Vertex();
					newVertex.setTargetNode(targetNode);

					sourceNode.addOutputVertex(newVertex);
				}

			}

			if (i % 1000 == 0) {
				System.out.println(sdf.format(new Date()) + ": i = " + i);
			}
		}

	}

	public void configure(int cost[][], int n, int carCapacity, List<TimeRestriction> timeRestrictionList) {

		this.currentSize = n;
		this.carCapacity = carCapacity;
		
		this.firstNode = this.nodes.get(0);
		this.lastNode = this.nodes.get(n);

		for (int i = 0; i < n; i++) {

			Node sourceNode = this.nodes.get(i);
			sourceNode.setTimeRestriction(timeRestrictionList.get(i));

			for (int j = 1; j < n; j++) {

				if (i != j) {
					Vertex vertex = sourceNode.getOutputVertexes().get(j);
					vertex.setCost(cost[i][j]);
				}

			}

		}

		this.rideDepartTime = new Date(this.nodes.get(0).getTimeRestriction().getDepartTime());
		this.rideArriveTime = new Date(this.nodes.get(0).getTimeRestriction().getArriveTime());

	}

	public Node getMinimumCostNode() {

		this.minimumCostList.sort(new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				return o1.getCurrentMinimumPathCost() - o2.getCurrentMinimumPathCost();
			}

		});
		return this.minimumCostList.remove(0);
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

}
