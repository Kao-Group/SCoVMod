package scovmod.model.transition;

import static scovmod.model.state.infection.InfectionState.*;
import static scovmod.model.util.TestUtils.intSetOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import scovmod.model.input.config.Parameters;
import scovmod.model.state.StateManagementFactory;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.transition.infected.*;
import scovmod.model.transition.susceptible.InfectionPressure;
import scovmod.model.transition.susceptible.SusceptiblePersonTransitionExecutor;
import scovmod.test.FakeLocalPopulation;
import scovmod.model.util.math.Random;

public class TransitionExecutorTest {

    private int timeStep = 15;

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int LOCATION_3 = 300;

    private final int PERSON_1 = 11;
    private final int PERSON_2 = 12;
    private final int PERSON_3 = 13;
    private final int PERSON_3a = 131;
    private final int PERSON_4 = 14;
    private final int PERSON_5 = 15;
    private final int PERSON_5a = 151;
    private final int PERSON_6 = 16;
    private final int PERSON_7 = 17;

    @Mock
    StateQuery sq;
    @Mock
    StateModifier sm;
    @Mock
    Random rnd;
    @Mock
    Parameters params;
    @Mock
    StateManagementFactory smf;
    @Mock
    SusceptiblePersonTransitionExecutor sate;
    @Mock
    ExposedToMildInfectiousTransitionExecutor eate;
    @Mock
    ExposedToRecoveredTransitionExecutor eare;
    @Mock
    MildInfectiousToSevereTransitionExecutor mitste;
    @Mock
    MildInfectiousToRecoveredTransitionExecutor mitre;
    @Mock
    SevereInfectiousToRecoveredTransitionExecutor sitrte;
    @Mock
    SevereInfectiousToHospitalisedTransitionExecutor sithte;
    @Mock
    SevereInfectiousToDeadTransitionExecutor sitdte;
    @Mock
    HospitalisedToRecoveredTransitionExecutor htrte;
    @Mock
    HospitalisedToDeadTransitionExecutor htdte;


    FakeLocalPopulation pop1, pop2, pop3;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        pop1 = new FakeLocalPopulation();
        pop1.testSet(PERSON_1, SUSCEPTIBLE);
        pop1.testSet(PERSON_2, EXPOSED_YOUNG);
        pop1.testSet(PERSON_3, MILD_INFECTIOUS_ADULT);
        pop1.testSet(PERSON_3a, HOSPITALISED_ADULT);

        pop2 = new FakeLocalPopulation();
        pop2.testSet(PERSON_4, SUSCEPTIBLE);
        pop2.testSet(PERSON_5, EXPOSED_ADULT);
        pop2.testSet(PERSON_5a, SEVERE_INFECTIOUS_ELDERLY);

        pop3 = new FakeLocalPopulation();
        pop3.testSet(PERSON_6, SUSCEPTIBLE);
        pop3.testSet(PERSON_7, SUSCEPTIBLE);
    }


    @Test
    public void doLocationBasedSusceptibleTransitions() {
        InfectionPressure farm1CattlePressure = new InfectionPressure(0, 0.1);
        InfectionPressure farm2CattlePressure = new InfectionPressure(0, 0.15);
        InfectionPressure farm3CattlePressure = new InfectionPressure(0, 0.45);

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        Int2ObjectMap<InfectionPressure> pressureMap= new Int2ObjectOpenHashMap<>();
        pressureMap.put(LOCATION_1, farm1CattlePressure);
        pressureMap.put(LOCATION_2, farm2CattlePressure);
        pressureMap.put(LOCATION_3, farm3CattlePressure);

        instance.doSusceptibleTransitions(pressureMap);

        verify(sate).apply(PERSON_1, farm1CattlePressure);
        verify(sate).apply(PERSON_4, farm2CattlePressure);
        verify(sate).apply(PERSON_6, farm3CattlePressure);
        verify(sate).apply(PERSON_7, farm3CattlePressure);
        Mockito.verifyNoMoreInteractions(sate);
    }

    @Test
    public void doLocationBasedExposedToMildTransitions() {
        when(sq.getExposedYoungLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getExposedAdultLocations()).thenReturn(intSetOf(LOCATION_2));
        when(sq.getExposedElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        instance.doExposedToMildTransitions();

        verify(eate).apply(PERSON_2);
        verify(eate).apply(PERSON_5);
        Mockito.verifyNoMoreInteractions(eate);
    }

    @Test
    public void doLocationBasedExposedToRecoveredTransitions() {
        when(sq.getExposedYoungLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getExposedAdultLocations()).thenReturn(intSetOf(LOCATION_2));
        when(sq.getExposedElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        instance.doExposedToRecoveredTransitions();

        verify(eare).apply(PERSON_2);
        verify(eare).apply(PERSON_5);
        Mockito.verifyNoMoreInteractions(eare);
    }

    @Test
    public void doLocationBasedMildToRecoveredTransitions() {
        when(sq.getMildInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getMildInfectiousElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        instance.doMildToRecoveredTransitions();

        verify(mitre).apply(PERSON_3);
        Mockito.verifyNoMoreInteractions(mitre);
    }

    @Test
    public void doLocationBasedMildToSevereTransitions() {
        when(sq.getMildInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getMildInfectiousElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        instance.doMildToSevereTransitions();

        verify(mitste).apply(PERSON_3);
        Mockito.verifyNoMoreInteractions(mitste);
    }

    @Test
    public void doLocationBasedSevereToRecoveredTransitions() {
        when(sq.getSevereInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        instance.doSevereToRecoveredTransitions();

        verify(sitrte).apply(PERSON_5a);
        Mockito.verifyNoMoreInteractions(sitrte);
    }

    @Test
    public void doLocationBasedSevereToHospitalisedTransitions() {
        when(sq.getSevereInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        instance.doSevereToHospitalisedTransitions();

        verify(sithte).apply(PERSON_5a);
        Mockito.verifyNoMoreInteractions(sithte);
    }

    @Test
    public void doLocationBasedSevereToDeadTransitions() {
        when(sq.getSevereInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        instance.doSevereToDeadTransitions();

        verify(sitdte).apply(PERSON_5a);
        Mockito.verifyNoMoreInteractions(sitdte);
    }

    @Test
    public void doLocationBasedHospitalisedToRecoveredTransitions() {
        when(sq.getHospitalisedYoungLocations()).thenReturn(intSetOf());
        when(sq.getHospitalisedAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getHospitalisedElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        instance.doHospitalisedToRecoveredTransitions();

        verify(htrte).apply(PERSON_3a);
        Mockito.verifyNoMoreInteractions(htrte);
    }

    @Test
    public void doLocationBasedHospitalisedToDeadTransitions() {
        when(sq.getHospitalisedYoungLocations()).thenReturn(intSetOf());
        when(sq.getHospitalisedAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getHospitalisedElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, mitste, mitre, sitrte, sithte, sitdte, htrte, htdte);

        instance.doHospitalisedToDeadTransitions();

        verify(htdte).apply(PERSON_3a);
        Mockito.verifyNoMoreInteractions(htdte);
    }

}
