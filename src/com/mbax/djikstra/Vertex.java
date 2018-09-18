package com.mbax.djikstra;

public class Vertex {

	private Node targetNode;

	private int cost;

	private int i;
	private int j;

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

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

}
