package scovmod.model.state.population;

import java.util.List;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.StateException;
import scovmod.model.state.delta.CompartmentRemoveDelta;
import scovmod.model.state.delta.CompartmentSetDelta;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;

public class LocalPopulation {

    private Object2ObjectMap<InfectionState, IntSet> personsByState;
     private Int2ObjectMap<InfectionState> stateByPerson;

    public LocalPopulation() {
        personsByState = new Object2ObjectOpenHashMap<>();
        stateByPerson = new Int2ObjectOpenHashMap<>();
    }

    public LocalPopulation(LocalPopulation instance) {
        personsByState = new Object2ObjectOpenHashMap<>();
        for (InfectionState state : instance.personsByState.keySet()) {
            IntSet value = new IntOpenHashSet(instance.personsByState.get(state));
            value.addAll(instance.personsByState.get(state));
            personsByState.put(state, value);
        }
        stateByPerson = new Int2ObjectOpenHashMap<>(instance.stateByPerson);
    }

    protected CompartmentSetDelta setStateByPersonId(int personId, InfectionState state) {
        if (state == InfectionState.UNDEFINED) {
            throw new StateException("Cannot set person state to " + InfectionState.UNDEFINED);
        }
        if (stateByPerson.containsKey(personId)) {
            throw new StateException("Person " + personId + " is already in compartment " + state + ". Remove it before calling set.");
        }

        IntSet compartment = getCompartment(state);
        CompartmentSetDelta delta = new CompartmentSetDelta(compartment.isEmpty(), personId, state);

        compartment.add(personId);
        stateByPerson.put(personId, state);

        return delta;
    }

    public IntSet getAllInState(InfectionState state) {
        IntSet result =  new IntOpenHashSet();
        result.addAll(getCompartment(state));
        return result;
    }

    protected CompartmentRemoveDelta remove(int personId) {
        if (!stateByPerson.containsKey(personId)) {
            throw new StateException("Cannot remove person which isn't present: " + personId);
        }
        InfectionState state = stateByPerson.get(personId);

        IntSet compartment = getCompartment(state);

        compartment.remove(personId);
        stateByPerson.remove(personId);

        return new CompartmentRemoveDelta(compartment.isEmpty(), personId, state);
    }

    private IntSet getCompartment(InfectionState state) {
        IntSet compartment;
        if(state == null) throw new NullPointerException("State cannot be null.");
        if (personsByState.containsKey(state)) {
            compartment = personsByState.get(state);
        } else {
            compartment = new IntOpenHashSet();
            personsByState.put(state, compartment);
        }
        return compartment;
    }

    public InfectionState getPersonInfectionState(int aniId) {
        if (!stateByPerson.containsKey(aniId)) {
            throw new StateException("Person " + aniId + " not known at this location");
        } else {
            return stateByPerson.get(aniId);
        }
    }

    public IntSet getAllPersonIds() {
        IntSet result = new IntOpenHashSet();
        result.addAll(stateByPerson.keySet());
        return result;        
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((personsByState == null) ? 0 : personsByState.hashCode());
        result = prime * result
                + ((personsByState == null) ? 0 : personsByState.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LocalPopulation other = (LocalPopulation) obj;
        if (personsByState == null) {
            if (other.personsByState != null) {
                return false;
            }
        } else if (!personsByState.equals(other.personsByState)) {
            return false;
        }
        if (stateByPerson == null) {
            if (other.stateByPerson != null) {
                return false;
            }
        } else if (!stateByPerson.equals(other.stateByPerson)) {
            return false;
        }
        return true;
    }

    public List<InfectionState> getStateList() {
        List<InfectionState> states = new ArrayList<>();
        IntSet people = this.getAllPersonIds();
        for (int person : people) {
            states.add(stateByPerson.get(person));
        }
        return states;
    }

    public int getSize() {
        return stateByPerson.size();
    }

}
