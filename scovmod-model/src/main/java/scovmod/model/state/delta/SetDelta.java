package scovmod.model.state.delta;

import scovmod.model.state.infection.InfectionState;

public class SetDelta implements CompartmentDelta {

	private int locationId;
	private CompartmentSetDelta innerDelta;

	public SetDelta(int locationId, CompartmentSetDelta innerDelta) {
		this.locationId = locationId;
		this.innerDelta = innerDelta;
	}

	@Override
	public boolean wasEdgeCase() {
		return innerDelta.wasEdgeCase();
	}

	public InfectionState getInfectionState() {
		return innerDelta.getSetInfectionState();
	}

	public int getLocationId() {
		return locationId;
	}

	public int getPersonId() {
		return innerDelta.getPersonId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((innerDelta == null) ? 0 : innerDelta.hashCode());
		result = prime * result + locationId;
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
		SetDelta other = (SetDelta) obj;
		if (innerDelta == null) {
			if (other.innerDelta != null)
				return false;
		} else if (!innerDelta.equals(other.innerDelta))
			return false;
		if (locationId != other.locationId)
			return false;
		return true;
	}
}
