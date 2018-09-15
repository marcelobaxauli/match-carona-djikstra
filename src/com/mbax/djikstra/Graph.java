package com.mbax.djikstra;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.mbax.model.TimeRestriction;

public class Graph {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	private List<Node> minimumCostList;
	private Node firstNode;
	private Node lastNode;

	public Graph(int cost[][], int n, List<TimeRestriction> timeRestriction, int lastNode, int maxNumberOfPassengers) {

		this.minimumCostList = new ArrayList<Node>();

		List<Node> nodes = new ArrayList<Node>();

		for (int i = 0; i < n; i++) {
			Node newNode = new Node(i, timeRestriction.get(i), new Date(timeRestriction.get(0).getDepartTime()),
					new Date(timeRestriction.get(0).getArriveTime()), lastNode, maxNumberOfPassengers);

			if (i == 0) {
				this.firstNode = newNode;
			}

			if (i == n - 1) {
				this.lastNode = newNode;
			}

			nodes.add(newNode);
			this.minimumCostList.add(newNode);

		}

		for (int i = 0; i < n; i++) {
			for (int j = 1; j < n; j++) {

				if (i != j) {
					Node sourceNode = nodes.get(i);
					Node targetNode = nodes.get(j);

					Vertex newVertex = new Vertex();
					newVertex.setCost(cost[i][j]);
					newVertex.setTargetNode(targetNode);

					sourceNode.addOutputVertex(newVertex);
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

}
