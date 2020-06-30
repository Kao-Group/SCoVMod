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
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class InitialSeederTest {

    private final int PERSON_1 = 1;
    private final int LOCATION_1 = 100;
    private final int PERSON_2 = 2;
    private final int LOCATION_2 = 200;
    private final int PERSON_3 = 3;
    private final int LOCATION_3 = 300;

    @Mock
    private StartLocationsAndAgeClasses startLocations;
    @Mock
    private StateModifier sm;
    @Mock
    StateManagementFactory smf;

    private InitialSeeder instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(smf.getStateModifier()).thenReturn(sm);
        instance = new InitialSeeder(startLocations,smf);
    }

    @Test
    public void commitSeeds() {
        Int2IntMap startLocationMap = new Int2IntOpenHashMap();
        startLocationMap.put(PERSON_1, LOCATION_1);
        startLocationMap.put(PERSON_2, LOCATION_2);
        startLocationMap.put(PERSON_3, LOCATION_3);

        when(startLocations.getLocationsByPeopleId()).thenReturn(startLocationMap);
        Int2ObjectMap<InfectionState> personStates = new Int2ObjectOpenHashMap<>();
        personStates.put(PERSON_1, InfectionState.EXPOSED_YOUNG);
        personStates.put(PERSON_2, InfectionState.MILD_INFECTIOUS_ADULT);
        personStates.put(PERSON_3, InfectionState.SUSCEPTIBLE);

        instance.commit(personStates);

        verify(sm).seedState(PERSON_1, InfectionState.EXPOSED_YOUNG, LOCATION_1);
        verify(sm).seedState(PERSON_2, InfectionState.MILD_INFECTIOUS_ADULT, LOCATION_2);
        verify(sm).seedState(PERSON_3, InfectionState.SUSCEPTIBLE, LOCATION_3);

    }

}
