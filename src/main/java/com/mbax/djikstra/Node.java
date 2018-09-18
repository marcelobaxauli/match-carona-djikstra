package com.mbax.djikstra;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mbax.model.TimeRestriction;

public class Node {

	private List<Vertex> outputVertexes = new LinkedList<Vertex>();

	private Node nextNode;

	private int number;

	private int currentMinimumPathCost;

	private TimeRestriction timeRestriction;

	private int currentNumberOfPassengers;

	private Graph graph;

	public Node(int number, Graph graph) {

		this.number = number;
		this.graph = graph;

	}

	public Node getNextNode() {
		return nextNode;
	}

	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
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

		if (this.number == this.graph.getCurrentSize()) {
			return true;
		}

		return this.timeRestriction.isInTimeRestriction(visitTime, maximumTime);
	}

	public TimeRestriction getTimeRestriction() {
		return this.timeRestriction;
	}

	public void setTimeRestriction(TimeRestriction timeRestriction) {
		this.timeRestriction = timeRestriction;
	}

	public int getCurrentNumberOfPassengers() {
		return currentNumberOfPassengers;
	}

	public void setCurrentNumberOfPassengers(int currentNumberOfPassengers) {
		this.currentNumberOfPassengers = currentNumberOfPassengers;
	}

	// span costs to forward adjacent vertices
	public void spanCosts(Set<Integer> visitedNodes) {

		for (Vertex outputVertex : this.outputVertexes) {

			if (outputVertex.getI() >= this.graph.getCurrentSize()
					|| outputVertex.getJ() >= this.graph.getCurrentSize()) {
				break;
			}

			int minimumPathCost = this.currentMinimumPathCost + outputVertex.getCost();

			if (	!visitedNodes.contains(outputVertex.getTargetNode())
					&& outputVertex.getTargetNode().isInTimeRestriction(
					new Date(this.graph.getRideDepart().getTime() + minimumPathCost), this.graph.getRideArriveTime())
					&& (outputVertex.getTargetNode().getNumber() == this.graph.getCurrentSize() - 1
							|| (this.currentNumberOfPassengers + 1 <= this.graph.getCarCapacity()))
					&& minimumPathCost - ((this.currentNumberOfPassengers) * 16) < outputVertex.getTargetNode()
							.getCurrentMinimumPathCost()) {
								
				outputVertex.getTargetNode().setCurrentMinimumPathCost(minimumPathCost);
				outputVertex.getTargetNode().setNextNode(this);
				outputVertex.getTargetNode().setCurrentNumberOfPassengers(this.currentNumberOfPassengers + 1);
			}

		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		if (number != other.number)
			return false;
		return true;
	}

}
