package scovmod.model.state.coordination;

import static scovmod.model.state.infection.InfectionState.*;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.infection.InfectionTransitions;
import scovmod.model.state.StateException;
import scovmod.model.state.cache.CacheManager;
import scovmod.model.state.delta.RemoveDelta;
import scovmod.model.state.delta.SetDelta;
import scovmod.model.state.population.LocalPopulationIndex;

public class InfectionStateCoordinator {

    private LocalPopulationIndex lpi;
    private CacheManager cm;
    private InfectionTransitions it;

    public InfectionStateCoordinator(LocalPopulationIndex lpi, InfectionTransitions it, CacheManager cm) {
        this.lpi = lpi;
        this.it = it;
        this.cm = cm;
    }

    public void seedState(int personId, InfectionState state, int locationId) {
        if (lpi.getPersonInfectionStatus(personId) != UNDEFINED) {
            throw new StateException("Cannot seed person infection once registered to a location: personId " + personId + " @ LocationId " + locationId);
        }

        SetDelta delta = lpi.set(personId, state, locationId);

        if (delta.wasEdgeCase()) {
            cm.notifyEdgeCaseDetectedAt(locationId);
        } else {
            cm.notifyNonEdgeCaseStateChange(personId, UNDEFINED, state, locationId);
        }
    }

    public void updateInfectionState(int personId, InfectionState newState) {
        InfectionState prevState = lpi.getPersonInfectionStatus(personId);
        if (prevState == UNDEFINED) {
            throw new StateException("Person " + personId + " is not born yet.  Either seed or move onto a location.");
        }
        if (!it.permittedTransition(prevState, newState)) {
            throw new StateException("Person " + personId + " attempted illegal state transition: " + prevState + " -> " + newState);
        }
        RemoveDelta deltaExiting = lpi.remove(personId);
        int locationId = deltaExiting.getLocationId();
        SetDelta deltaEntering = lpi.set(personId, newState, locationId);

        if (deltaEntering.wasEdgeCase() || deltaExiting.wasEdgeCase()) {
            cm.notifyEdgeCaseDetectedAt(locationId);
        } else {
            cm.notifyNonEdgeCaseStateChange(personId, prevState, newState, locationId);
        }
    }

}
