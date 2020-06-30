/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.setup;

import scovmod.model.seeding.SeedManager;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.state.StateManagementFactory;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.util.math.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;

public class InitialiserTest {

    private final int STOCHASTIC_INCREMENT = 15;

    @Mock
    private StateModifier sm;
    @Mock
    private StateQuery sq;
    @Mock
    Random random;
    @Mock
    TimeManager tm;
    @Mock
    TimeConversions tc;
    @Mock
    StateManagementFactory smf;
    @Mock
    InitialLocations ils;
    @Mock
    SeedManager smr;
    @Mock
    InitialSeeder is;
    @Mock
    private ConfigParameters configParams;

    private Initialiser instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);
        instance = new Initialiser(smf, ils, random,
                tm, tc,  is, smr);
    }

    @Test
    public void setupSeedPersonsUsingStateModifier() {
        Int2ObjectMap<IntSet> personsByLocation = new Int2ObjectOpenHashMap<>();
        Int2ObjectMap<IntSet> personsByLocation2 = new Int2ObjectOpenHashMap<>();
        Int2ObjectMap<InfectionState> personStates = new Int2ObjectOpenHashMap<>();

        instance.seedPeople(configParams, STOCHASTIC_INCREMENT);

        InOrder inOrder = org.mockito.Mockito.inOrder(ils, smr, is);

        inOrder.verify(ils).setup(personsByLocation2);
        inOrder.verify(smr).seedPeopleInfections(personStates,personsByLocation2);
        inOrder.verify(is).commit(personStates);
        inOrder.verifyNoMoreInteractions();
    }
}
