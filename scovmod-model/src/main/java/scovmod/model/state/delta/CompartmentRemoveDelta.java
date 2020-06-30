package scovmod.model.state.delta;

import scovmod.model.state.infection.InfectionState;

public class CompartmentRemoveDelta implements CompartmentDelta {
	private boolean wasEdgeCase;
	private InfectionState state;
	private int personId;

	public CompartmentRemoveDelta(boolean wasEdgeCase, int personId, InfectionState state) {
		this.wasEdgeCase = wasEdgeCase;
		this.personId = personId;
		this.state = state;
	}

	@Override
	public boolean wasEdgeCase() {
		return wasEdgeCase;
	}

	public InfectionState getInfectionState() {
		return state;
	}

	public RemoveDelta tagWithLocation(int locId) {
		return new RemoveDelta(locId, this);
	}
	
	public int getPersonId() {
		return personId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + personId;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		CompartmentRemoveDelta other = (CompartmentRemoveDelta) obj;
		if (personId != other.personId)
			return false;
		if (state != other.state)
			return false;
		if (wasEdgeCase != other.wasEdgeCase)
			return false;
		return true;
	}
	
	
}
