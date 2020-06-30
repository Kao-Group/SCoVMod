/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.movements;

import java.util.Objects;
import java.util.Set;

public class TimeStepMovements {

	private int timeStep;
	private Set<LocationIncomingPersons> incomingPersonsSet;

	public TimeStepMovements(int timeStep, Set<LocationIncomingPersons> incomingPersonsSet) {
		this.timeStep = timeStep;
		this.incomingPersonsSet = incomingPersonsSet;
	}

	public int getTimeStep() {
		return timeStep;
	}

	public Set<LocationIncomingPersons> getMovements() {
		return incomingPersonsSet;
	}

	@Override
	public String toString() {
		return "TimeStepMovements{" + "timeStep=" + this.timeStep + ", incomingPersonsSet=" + this.incomingPersonsSet + '}';
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 61 * hash + this.timeStep;
		hash = 61 * hash + Objects.hashCode(this.incomingPersonsSet);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TimeStepMovements other = (TimeStepMovements) obj;
		if (this.timeStep != other.timeStep) {
			return false;
		}
		if (!Objects.equals(this.incomingPersonsSet, other.incomingPersonsSet)) {
			return false;
		}
		return true;
	}

}
