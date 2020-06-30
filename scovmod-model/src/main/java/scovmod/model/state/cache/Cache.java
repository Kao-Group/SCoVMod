package scovmod.model.state.cache;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;

public interface Cache {

    public void wholeLocationUpdate(int locationId, LocalPopulation partition);

    public void notifyNonEdgeCaseStateChange(
            int personId,
            InfectionState oldState,
            InfectionState newState,
            int locationId
    );

    public void notifyNonEdgeCaseMovement(
            int personId,
            boolean isOnMovement,
            int locationId,
            InfectionState state
    );
}
