package scovmod.model.state;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.coordination.InfectionStateCoordinator;
import scovmod.model.state.coordination.MovementCoordinator;

public class StateModifier {

    private MovementCoordinator mc;
    private InfectionStateCoordinator isc;

    public StateModifier(MovementCoordinator mc, InfectionStateCoordinator isc) {
        this.mc = mc;
        this.isc = isc;
    }

    public void seedState(int personId, InfectionState state, int locationId) {
        isc.seedState(personId, state, locationId);
    }

    public void updateInfectionState(int personId, InfectionState state) {
        isc.updateInfectionState(personId, state);
    }

    public void movePerson(int personId, int newLocationId) {
        mc.movePerson(personId, newLocationId);
    }

}
