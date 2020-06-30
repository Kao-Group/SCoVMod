package scovmod.model.state.delta;

import scovmod.model.state.infection.InfectionState;

public class RemoveDelta implements CompartmentDelta{

	private int locationId;
	private CompartmentRemoveDelta compartmentRemovalDelta;
	
	public RemoveDelta(int locationId, CompartmentRemoveDelta compartmentRemovalDelta) {
		this.locationId = locationId;
		this.compartmentRemovalDelta = compartmentRemovalDelta;
	}

	public int getLocationId() {
		return locationId;
	}

	public InfectionState getInfectionState() {
		return compartmentRemovalDelta.getInfectionState();
	}
	
	@Override
	public boolean wasEdgeCase() {
		return compartmentRemovalDelta.wasEdgeCase();
	}

	public int getPersonId() {
		return compartmentRemovalDelta.getPersonId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((compartmentRemovalDelta == null) ? 0
						: compartmentRemovalDelta.hashCode());
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
		RemoveDelta other = (RemoveDelta) obj;
		if (compartmentRemovalDelta == null) {
			if (other.compartmentRemovalDelta != null)
				return false;
		} else if (!compartmentRemovalDelta
				.equals(other.compartmentRemovalDelta))
			return false;
		if (locationId != other.locationId)
			return false;
		return true;
	}

	
	
}
