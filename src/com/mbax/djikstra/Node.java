package com.mbax.djikstra;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.mbax.model.TimeRestriction;

public class Node implements Comparable<Node> {

	private List<Vertex> outputVertexes = new LinkedList<Vertex>();

	private Node previousNode;

	private int number;

	private int currentMinimumPathCost;

	private TimeRestriction timeRestriction;

	private Date rideDepartTime;

	private Date rideArrivalTime;

	private int currentNumberOfPassengers;

	private int maxNumberOfPassengers;

	private int lastNode;

	public Node(int number, TimeRestriction timeRestriction, Date rideDepartTime, Date rideArrivalTime, int lastNode,
			int maxNumberOfPassengers) {
		this.number = number;

		this.timeRestriction = timeRestriction;

		this.currentMinimumPathCost = 0;

		this.lastNode = lastNode;

		this.maxNumberOfPassengers = maxNumberOfPassengers;

		this.rideDepartTime = rideDepartTime;

		this.rideArrivalTime = rideArrivalTime;

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

	public List<Vertex> getOutputVertexes() {
		return outputVertexes;
	}

	public void setOutputVertexes(List<Vertex> outputVertexes) {
		this.outputVertexes = outputVertexes;
	}

	public void addOutputVertex(Vertex vertex) {
		this.outputVertexes.add(vertex);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCurrentMinimumPathCost() {
		return currentMinimumPathCost;
	}

	public void setCurrentMinimumPathCost(int currentMinimumPathCost) {
		this.currentMinimumPathCost = currentMinimumPathCost;
	}

	public boolean isInTimeRestriction(Date visitTime, Date maximumTime) {

		if (this.number == this.lastNode) {
			return true;
		}

		return this.timeRestriction.isInTimeRestriction(visitTime, maximumTime);
	}

	public long getRideDepartTime() {
		return rideDepartTime.getTime();
	}

	public void setRideDepartTime(long rideDepartTime) {
		this.rideDepartTime = new Date(rideDepartTime);
	}

	public int getCurrentNumberOfPassengers() {
		return currentNumberOfPassengers;
	}

	public void setCurrentNumberOfPassengers(int currentNumberOfPassengers) {
		this.currentNumberOfPassengers = currentNumberOfPassengers;
	}

	// span costs to forward adjacent vertices
	public void spanCosts() {

		for (Vertex outputVertex : this.outputVertexes) {

			int minimumPathCost = this.currentMinimumPathCost + outputVertex.getCost();

			if (outputVertex.getTargetNode().isInTimeRestriction(
					new Date(this.rideDepartTime.getTime() + minimumPathCost), this.rideArrivalTime)
					&& (outputVertex.getTargetNode().getNumber() == this.lastNode
							|| (this.currentNumberOfPassengers + 1 <= this.maxNumberOfPassengers))
					&& minimumPathCost - ((this.currentNumberOfPassengers) * 16) < outputVertex.getTargetNode()
							.getCurrentMinimumPathCost()) {
				outputVertex.getTargetNode().setCurrentMinimumPathCost(minimumPathCost);
				outputVertex.getTargetNode().setPreviousNode(this);
				outputVertex.getTargetNode().setCurrentNumberOfPassengers(this.currentNumberOfPassengers + 1);
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
