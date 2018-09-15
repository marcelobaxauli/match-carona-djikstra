package com.mbax.djikstra;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mbax.model.TimeRestriction;

public class Graph {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	private int cost[][];
	private int accumulatedCost[][];

	private List<Node> nodesByNumber = new LinkedList<Node>();
	private List<Node> minimumCostList = new LinkedList<Node>();
	private Set<Integer> visitedNodeNumbers;

	private List<TimeRestriction> timeRestrictionList;

	private int maximumNumberPassagenders;

	private int numberOfNodes;

	public Graph(int cost[][], int n, List<TimeRestriction> timeRestriction, int lastNode, int maxNumberOfPassengers) {

		this.cost = cost;
		this.accumulatedCost = new int[n][n];

		this.numberOfNodes = n;

		this.timeRestrictionList = timeRestriction;

		this.maximumNumberPassagenders = maxNumberOfPassengers;

		this.visitedNodeNumbers = new HashSet<Integer>(n);

		for (int i = 0; i < n; i++) {
			Node newNode = new Node(i, this.timeRestrictionList.get(i), this);

			this.nodesByNumber.add(newNode);
			this.minimumCostList.add(newNode);

		}

		for (int i = 0; i < n; i++) {
			for (int j = 1; j < n; j++) {

				if (i != j) {
					this.accumulatedCost[i][j] = Integer.MAX_VALUE;
				}

			}

			if (i % 1000 == 0) {
				System.out.println(sdf.format(new Date()) + ": i = " + i);
			}
		}

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

	public int getCost(int i, int j) {
		return this.cost[i][j];
	}

	public Node getNode(int i) {
		return this.nodesByNumber.get(i);
	}

	public Node getLastNode() {
		return this.nodesByNumber.get(this.nodesByNumber.size() - 1);
	}

	public Node getFirstNode() {
		return this.nodesByNumber.get(0);
	}

	public Date getRideDepartTime() {
		return new Date(this.timeRestrictionList.get(0).getDepartTime());
	}

	public Date getRideArriveTime() {
		return new Date(this.timeRestrictionList.get(0).getArriveTime());
	}

	public int getMaximumNumberPassagenders() {
		return maximumNumberPassagenders;
	}

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public void addVisitedNode(Node node) {
		this.visitedNodeNumbers.add(node.getNumber());
	}

	public boolean isNodeVisisted(int nodeIndex) {
		return this.visitedNodeNumbers.contains(nodeIndex);
	}

	public void setCurrentMinimumPathCost(int minimumPathCost, int i, int j) {

		// to quebrando um pouco da orientação a objetos aqui pra ganhar em performance
		// (que é essencial pro algoritmo funcionar)
		this.accumulatedCost[i][j] = minimumPathCost;
		this.nodesByNumber.get(j).setCurrentMinimumPathCost(minimumPathCost);

	}

}
