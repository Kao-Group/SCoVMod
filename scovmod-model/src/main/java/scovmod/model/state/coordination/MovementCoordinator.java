package scovmod.model.state.coordination;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.cache.CacheManager;
import scovmod.model.state.delta.RemoveDelta;
import scovmod.model.state.delta.SetDelta;
import scovmod.model.state.population.LocalPopulationIndex;

import static scovmod.model.state.infection.InfectionState.UNDEFINED;

public class MovementCoordinator {

    private final LocalPopulationIndex lpi;
    private final CacheManager cm;
    private final int DEATH_MOVEMENT = -1;

    public MovementCoordinator(LocalPopulationIndex lpi, CacheManager cm) {
        this.lpi = lpi;
        this.cm = cm;
    }

    public void movePerson(int personId, int newLocationId) {
        if (lpi.getPersonInfectionStatus(personId) == UNDEFINED) {
           // doBirthMovement(personId, newLocationId); //Ignore births
        } else if (newLocationId == DEATH_MOVEMENT) {
            //doDeathMovement(personId); //Ignore deaths
        } else {
            doMovement(personId, newLocationId);
        }
    }

    private void doDeathMovement(int personId) {
        RemoveDelta rDelta = lpi.remove(personId);

        boolean offEdgeCase = rDelta.wasEdgeCase();
        InfectionState state = rDelta.getInfectionState();

        if (offEdgeCase) {
            cm.notifyEdgeCaseDetectedAt(rDelta.getLocationId());
        } else {
            cm.notifyNonEdgeCaseMovement(personId, false, rDelta.getLocationId(), state);
        }

    }


    private void doMovement(int personId, int newLocationId) {
        RemoveDelta rDelta = lpi.remove(personId);
        SetDelta sDelta = lpi.set(personId, rDelta.getInfectionState(), newLocationId);

        boolean offEdgeCase = rDelta.wasEdgeCase();
        boolean onEdgeCase = sDelta.wasEdgeCase();
        InfectionState state = rDelta.getInfectionState();

        if (onEdgeCase) {
            cm.notifyEdgeCaseDetectedAt(newLocationId);
        } else {
            cm.notifyNonEdgeCaseMovement(personId, true, newLocationId, state);
        }

        if (offEdgeCase) {
            cm.notifyEdgeCaseDetectedAt(rDelta.getLocationId());
        } else {
            cm.notifyNonEdgeCaseMovement(personId, false, rDelta.getLocationId(), state);
        }
    }
}
