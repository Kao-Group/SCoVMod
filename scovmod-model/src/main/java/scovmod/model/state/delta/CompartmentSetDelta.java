package scovmod.model.state.delta;

import scovmod.model.state.infection.InfectionState;

public class CompartmentSetDelta implements CompartmentDelta {

	private boolean wasEdgeCase;
	private InfectionState stateThatWasSet;
	private int personId;

	public CompartmentSetDelta(boolean wasEdgeCase, int personId, InfectionState stateThatWasSet) {
		this.wasEdgeCase = wasEdgeCase;
		this.personId = personId;
		this.stateThatWasSet = stateThatWasSet;
	}

	@Override
	public boolean wasEdgeCase() {
		return wasEdgeCase;
	}

	public InfectionState getSetInfectionState() {
		return stateThatWasSet;
	}
	
	public SetDelta addLocation(int locationId){
		return new SetDelta(locationId, this);
	}

	public int getPersonId() {
		return personId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + personId;
		result = prime * result
				+ ((stateThatWasSet == null) ? 0 : stateThatWasSet.hashCode());
		result = prime * result + (wasEdgeCase ? 1231 : 1237);
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
		CompartmentSetDelta other = (CompartmentSetDelta) obj;
		if (personId != other.personId)
			return false;
		if (stateThatWasSet != other.stateThatWasSet)
			return false;
		if (wasEdgeCase != other.wasEdgeCase)
			return false;
		return true;
	}
}
