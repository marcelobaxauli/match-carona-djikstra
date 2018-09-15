package com.mbax.djikstra;

import java.util.Date;

import com.mbax.model.TimeRestriction;

public class Node implements Comparable<Node> {

	private Integer numberOfOutputVertexes;

	private Node previousNode;

	private int nodeNumber;

	private int currentMinimumPathCost;

	private int currentNumberOfPassengers;

	private TimeRestriction timeRestriction;

	private Graph graph;

	public Node(int number, TimeRestriction timeRestriction, Graph graph) {

		this.numberOfOutputVertexes = graph.getNumberOfNodes();

		this.graph = graph;
		this.nodeNumber = number;
		this.currentMinimumPathCost = 0;

		this.timeRestriction = timeRestriction;

		this.currentNumberOfPassengers = 0;

		if (number == 0) {
			this.currentMinimumPathCost = 0;
		} else {
			this.currentMinimumPathCost = Integer.MAX_VALUE;
		}
	}

	public Node getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(Node previousNode) {
		this.previousNode = previousNode;
	}

	public int getNumber() {
		return nodeNumber;
	}

	public void setNumber(int number) {
		this.nodeNumber = number;
	}

	public int getCurrentMinimumPathCost() {
		return currentMinimumPathCost;
	}

	public void setCurrentMinimumPathCost(int currentMinimumPathCost) {
		this.currentMinimumPathCost = currentMinimumPathCost;
	}

	public boolean isInTimeRestriction(Date visitTime, Date maximumTime) {

		if (this.nodeNumber == this.graph.getLastNode().getNumber()) {
			return true;
		}

		return this.timeRestriction.isInTimeRestriction(visitTime, maximumTime);
	}

	public int getCurrentNumberOfPassengers() {
		return currentNumberOfPassengers;
	}

	public void setCurrentNumberOfPassengers(int currentNumberOfPassengers) {
		this.currentNumberOfPassengers = currentNumberOfPassengers;
	}

	// span costs to forward adjacent vertices
	public void spanCosts() {

		for (int adjacentNodeIndex = 1; adjacentNodeIndex < this.numberOfOutputVertexes; adjacentNodeIndex++) {

			if (this.nodeNumber != adjacentNodeIndex && !this.graph.isNodeVisisted(adjacentNodeIndex)) {
				int minimumPathCost = this.currentMinimumPathCost
						+ this.graph.getCost(this.nodeNumber, adjacentNodeIndex);

				Node adjacentNode = this.graph.getNode(adjacentNodeIndex);

				if (adjacentNode.isInTimeRestriction(
						new Date(this.graph.getRideDepartTime().getTime() + minimumPathCost),
						this.graph.getRideDepartTime())
						&& (adjacentNode.getNumber() == this.graph.getLastNode().getNumber()
								|| (this.currentNumberOfPassengers + 1 <= this.graph.getMaximumNumberPassagenders()))
						&& minimumPathCost - ((this.currentNumberOfPassengers) * 16) < adjacentNode
								.getCurrentMinimumPathCost()) {

					this.graph.setCurrentMinimumPathCost(minimumPathCost, this.nodeNumber, adjacentNodeIndex);
					adjacentNode.setPreviousNode(this);
					adjacentNode.setCurrentNumberOfPassengers(this.currentNumberOfPassengers + 1);
//					this.graph.addVisitedNode(this);
				}
			}

		}

	}

	@Override
	public int compareTo(Node o) {
		return this.getCurrentMinimumPathCost() - o.getCurrentMinimumPathCost();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentMinimumPathCost;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (currentMinimumPathCost != other.currentMinimumPathCost)
			return false;
		return true;
	}

}
