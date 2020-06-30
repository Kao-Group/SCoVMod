package scovmod.model.state.cache;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.state.population.LocalPopulationIndex;

public class CacheManager {

    private Cache[] caches;
    private LocalPopulationIndex lpi;

    public CacheManager(LocalPopulationIndex lpi, Cache... caches) {
        this.lpi = lpi;
        this.caches = caches;
    }

    public void notifyEdgeCaseDetectedAt(int locationId) {
        LocalPopulation partition = lpi.getLocalPopulation(locationId);
        for (Cache c : caches) {
            c.wholeLocationUpdate(locationId, partition);
        }
    }

    public void notifyNonEdgeCaseMovement(int personId, boolean isOnMovement, int locationId, InfectionState state) {
        for (Cache c : caches) {
            c.notifyNonEdgeCaseMovement(personId, isOnMovement, locationId, state);
        }
    }

    public void notifyNonEdgeCaseStateChange(int personId, InfectionState oldState, InfectionState newState, int locationId) {
        for (Cache c : caches) {
            c.notifyNonEdgeCaseStateChange(personId, oldState, newState, locationId);
        }
    }

}
