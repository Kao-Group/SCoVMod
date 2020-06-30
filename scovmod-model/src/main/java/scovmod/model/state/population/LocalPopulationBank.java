package scovmod.model.state.population;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class LocalPopulationBank {

    private Int2ObjectMap<LocalPopulation> localPopulations = new Int2ObjectOpenHashMap<>();

    public LocalPopulationBank() {
    }

    public LocalPopulation getPopulationAtLocation(int locationId) {
        if (localPopulations.containsKey(locationId)) {
            return localPopulations.get(locationId);
        } else {
            LocalPopulation lp = new LocalPopulation();
            localPopulations.put(locationId, lp);
            return lp;
        }
    }

}
