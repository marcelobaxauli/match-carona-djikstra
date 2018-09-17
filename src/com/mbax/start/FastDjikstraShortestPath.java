package com.mbax.start;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mbax.algorithm.Algorithm;
import com.mbax.algorithm.DjikstraAlgorithm;
import com.mbax.djikstra.Graph;
import com.mbax.model.TimeRestriction;
import com.mbax.population.Population;

public class FastDjikstraShortestPath {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	private static final int N = 10000;

	private static final int MAX_NUMBER_OF_PASSENGERS = 3;

	public static void main(String[] args) {

		Population population = new Population();

		int[][] cost = Population.createCostMatrix(N);
//		Population.print(cost, N);

		System.out.println(sdf.format(new Date()) + ": Creating time restrictions...");
		List<TimeRestriction> timeRestrictions = population.createTimeRestrictionArray(N);
		int i = 0;
		for (TimeRestriction timeRestriction : timeRestrictions) {
//			System.out.println(i++ + ": " + timeRestriction);
		}
		System.out.println(sdf.format(new Date()) + ": Time restrictions created...");

		System.out.println(sdf.format(new Date()) + ": Creating graph...");
		Graph graph = new Graph(cost, N, timeRestrictions, N - 1, MAX_NUMBER_OF_PASSENGERS);
		System.out.println(sdf.format(new Date()) + ": Graph created.");

		System.out.println(sdf.format(new Date()) + ": Running dijkstra...");
		Algorithm algorithm = new DjikstraAlgorithm(graph, cost, timeRestrictions);
		algorithm.run();
		System.out.println(sdf.format(new Date()) + ": Dijkstra runned.");

	}

}
