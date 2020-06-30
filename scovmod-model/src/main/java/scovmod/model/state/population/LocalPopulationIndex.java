package scovmod.model.state.population;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.delta.CompartmentSetDelta;
import scovmod.model.state.delta.RemoveDelta;
import scovmod.model.state.delta.SetDelta;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

public class LocalPopulationIndex {

    private LocalPopulationBank lpb;
    private Int2IntMap locationByPerson = new Int2IntOpenHashMap(5400000);//TODO find programatic way of setting this

    public LocalPopulationIndex(LocalPopulationBank localPopulationsBank) {
        this.lpb = localPopulationsBank;
    }

    public SetDelta set(int personId, InfectionState state, int locationId) {
        locationByPerson.put(personId, locationId);
        CompartmentSetDelta cds = lpb.getPopulationAtLocation(locationId).setStateByPersonId(personId, state);
        return cds.addLocation(locationId);
    }

    public RemoveDelta remove(int personId) {
        int locationId = locationByPerson.remove(personId);
        return lpb.getPopulationAtLocation(locationId).remove(personId).tagWithLocation(locationId);
    }

    public int getPersonLocationId(int personId) {
        if(!locationByPerson.containsKey(personId)){
           // throw new ModelException("Person does not exist!");
        } 
        return locationByPerson.get(personId);
    }

    public InfectionState getPersonInfectionStatus(int personId) {
        if (locationByPerson.containsKey(personId)) {
            int locationId = locationByPerson.get(personId);
            return lpb.getPopulationAtLocation(locationId).getPersonInfectionState(personId);
        } else {
            return InfectionState.UNDEFINED;
        }
    }

    public IntSet getAllLocationIds() {
        IntSet uniqueLocations = new IntOpenHashSet();
        uniqueLocations.addAll(locationByPerson.values());
        return uniqueLocations;
    }

    public LocalPopulation getLocalPopulation(int locationId) {
        return lpb.getPopulationAtLocation(locationId);
    }

}
