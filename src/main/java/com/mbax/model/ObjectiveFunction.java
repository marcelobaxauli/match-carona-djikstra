package com.mbax.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ObjectiveFunction {

	private Map<NumberPassengerTimeTuple, Integer> objectiveTable = new HashMap<NumberPassengerTimeTuple, Integer>();

	private int numberPassengers;
	private int timeHorizon;

	private int detourTime;

	public ObjectiveFunction(int numberPassengers, int timeHorizon, int detourTime) {
		this.numberPassengers = numberPassengers;
		this.timeHorizon = timeHorizon;
		this.detourTime = detourTime;

		this.buildObjectiveTable();
	}

	private void buildObjectiveTable() {

		int score = 1;
		int timeIndex = 1;

		for (int passengerIndex = this.numberPassengers; passengerIndex >= 0; passengerIndex--) {

			for (; timeIndex <= detourTime; timeIndex++) {

				NumberPassengerTimeTuple tuple = new NumberPassengerTimeTuple();
				tuple.setNumberPassagenders(passengerIndex);
				tuple.setTime(timeIndex);

				this.objectiveTable.put(tuple, score++);

			}

		}

		for (; timeIndex <= this.timeHorizon; timeIndex++) {
			for (int passengerIndex = this.numberPassengers; passengerIndex >= 0; passengerIndex--) {

				NumberPassengerTimeTuple tuple = new NumberPassengerTimeTuple();
				tuple.setNumberPassagenders(passengerIndex);
				tuple.setTime(timeIndex);

				this.objectiveTable.put(tuple, score++);

			}
		}

	}

	public int getObjectiveFunctionValue(int numberOfPassengers, int time) {

		NumberPassengerTimeTuple tuple = new NumberPassengerTimeTuple(numberOfPassengers, time);

		Integer objective = this.objectiveTable.get(tuple);
		
		return (objective == null) ? -1 : objective;
	}

	public void printObjectiveTable() {

		Set<Entry<NumberPassengerTimeTuple, Integer>> entrySet = this.objectiveTable.entrySet();

		for (Entry<NumberPassengerTimeTuple, Integer> entry : entrySet) {
			NumberPassengerTimeTuple tuple = entry.getKey();
			Integer objetiveValue = entry.getValue();

			System.out.println("number_passengers = " + tuple.getNumberPassagenders() + " time = " + tuple.getTime()
					+ " function = " + objetiveValue);

		}

	}

	class NumberPassengerTimeTuple {

		private int numberPassagenders;
		private int time; // in minutes

		public NumberPassengerTimeTuple() {
			this.numberPassagenders = 0;
			this.time = 0;
		}

		public NumberPassengerTimeTuple(int numberPassagenders, int time) {
			this.numberPassagenders = numberPassagenders;
			this.time = time;
		}

		public int getNumberPassagenders() {
			return numberPassagenders;
		}

		public void setNumberPassagenders(int numberPassagenders) {
			this.numberPassagenders = numberPassagenders;
		}

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + numberPassagenders;
			result = prime * result + time;
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
			NumberPassengerTimeTuple other = (NumberPassengerTimeTuple) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (numberPassagenders != other.numberPassagenders)
				return false;
			if (time != other.time)
				return false;
			return true;
		}

		private ObjectiveFunction getOuterType() {
			return ObjectiveFunction.this;
		}

	}

}
