package com.mbax.population;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mbax.model.TimeRestriction;

public class Population {

	private static final Random RANDOM = new Random();

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Date dayMinimumDate = null;
	private Date dayMaximumDate = null;

	public Population() {

		try {
			this.dayMinimumDate = this.sdf.parse("2018-09-07 16:00:00");
			this.dayMaximumDate = this.sdf.parse("2018-09-07 20:00:00");
		} catch (ParseException e) {

			throw new IllegalStateException("Erro durante criacao de datas limite", e);

		}

	}

	public static int[][] createCostMatrix(int n) {

		int cost[][] = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				cost[i][j] = (1 + Math.abs(RANDOM.nextInt()) % 180) * 60 * 1000;
			}
		}

		return cost;

	}

	public List<TimeRestriction> createTimeRestrictionArray(int n) {

		List<TimeRestriction> timeRestrictions = new ArrayList<TimeRestriction>();

		boolean valid = false;
		for (int i = 0; i < n - 1; i++) {

			valid = false;
			long dateDepart = 0l;
			long dateArrive = 0l;

			dateDepart = this.dayMinimumDate.getTime() + RANDOM.nextInt((int) (this.dayMaximumDate.getTime() - this.dayMinimumDate.getTime()) + 1) + this.dayMinimumDate.getTime();			
			dateArrive = this.dayMinimumDate.getTime() + RANDOM.nextInt((int) (this.dayMaximumDate.getTime() - dateDepart) + 1) + dateDepart;

			timeRestrictions.add(new TimeRestriction(dateDepart, dateArrive));
		}
		
		timeRestrictions.add(timeRestrictions.get(0));
		

		return timeRestrictions;

	}

	public static void print(int cost[][], int n) {

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print((cost[i][j] / 60 / 1000) + "\t");
			}

			System.out.println();
		}

		System.out.println();
	}

}
