package com.mbax.algorithm;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mbax.djikstra.Graph;
import com.mbax.djikstra.Node;
import com.mbax.model.TimeRestriction;

public class DjikstraAlgorithm implements Algorithm {

	private Graph graph;

	private List<TimeRestriction> timeRestrictions;

	private int cost[][];

	public DjikstraAlgorithm(Graph graph, int cost[][], List<TimeRestriction> timeRestrictions) {
		this.graph = graph;
		this.timeRestrictions = timeRestrictions;
		this.cost = cost;
	}

	@Override
	public void run() {

		Date startTime = new Date();
		System.out.println(startTime + ": starting...");

		Set<Integer> visitedNodes = new HashSet<Integer>();

		Node lastNode = this.graph.getLastNode();
		Node firstNode = this.graph.getFirstNode();

		Node nextMinimumCostNode = null;
		do {

			nextMinimumCostNode = this.graph.getMinimumCostNode();

			if (nextMinimumCostNode != null) {
				nextMinimumCostNode.spanCosts(visitedNodes);
			}

			visitedNodes.add(nextMinimumCostNode.getNumber());
			
		} while (visitedNodes.size() != this.graph.getCurrentSize());

		Date finishTime = new Date();
		System.out.println(startTime + ": finished.");

		System.out.println("elapsed: " + ((finishTime.getTime() - startTime.getTime())) + " mili seconds");

		this.printSequence(firstNode);
		System.out.println("\ncost: " + firstNode.getCurrentMinimumPathCost() / 60 / 1000);
		System.out.println("direct travel cost: " + this.cost[0][this.cost.length - 1] / 60 / 1000);

		System.out.println("Option pool:");
		this.printPossiblePassengers();
	}

	public void printSequence(Node node) {

		if (node != null) {
			this.printSequence(node.getNextNode());
			TimeRestriction timeRestriction = this.timeRestrictions.get(node.getNumber());
			Date depart = new Date(timeRestriction.getDepartTime());
			Date arrive = new Date(timeRestriction.getArriveTime());
			System.out.print(node.getNumber() + "(" + depart + ", " + arrive + ")" + "\t");
		}

	}

	public void printPossiblePassengers() {

		TimeRestriction sourceTimeRestriction = this.timeRestrictions.get(0);

		for (int j = 1; j < this.timeRestrictions.size() - 1; j++) {

			TimeRestriction currentTimeRestriction = this.timeRestrictions.get(j);

			if (currentTimeRestriction.getDepartTime() <= sourceTimeRestriction.getDepartTime()
					&& currentTimeRestriction.getArriveTime() >= sourceTimeRestriction.getArriveTime()
					&& sourceTimeRestriction.getArriveTime() > currentTimeRestriction.getDepartTime()) {
				// System.out.println(j + ": " + currentTimeRestriction + " dist(" +
				// this.cost[0][j] / 60 / 1000 + ")");
			}
		}
	}

}
