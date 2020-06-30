/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.setup;

import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.state.StateManagementFactory;
import scovmod.model.state.StateModifier;
import scovmod.model.state.infection.InfectionState;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;

public class InitialSeeder {

    private final StartLocationsAndAgeClasses startLocations;
    private final StateModifier sm;

    public InitialSeeder(StartLocationsAndAgeClasses startLocations, StateManagementFactory smf) {
        this.startLocations = startLocations;
        this.sm = smf.getStateModifier();
    }

    public void commit(Int2ObjectMap<InfectionState> personStates) {
        IntSet personKeys = startLocations.getLocationsByPeopleId().keySet();
        // Commit person seeding
        for (int personID : personKeys) {
            sm.seedState(
                    personID, personStates.get(personID),
                    startLocations.getLocationsByPeopleId().get(personID)
            );
        }
    }
}
