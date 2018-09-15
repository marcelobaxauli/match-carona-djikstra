package com.mbax.djikstra;

public class Vertex {

	private Node targetNode;

	private int cost;

	public Node getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(Node targetNode) {
		this.targetNode = targetNode;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
