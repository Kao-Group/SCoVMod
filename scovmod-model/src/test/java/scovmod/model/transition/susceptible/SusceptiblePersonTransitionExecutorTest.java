package scovmod.model.transition.susceptible;

import static org.mockito.Mockito.*;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateModifier;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.util.math.Random;

public class SusceptiblePersonTransitionExecutorTest {

    private int timeStep = 1;
    private final int PERSON_1 = 11;
    private final int PERSON_2 = 22;

    @Mock
    StateModifier sm;
    @Mock
    Random rnd;
    @Mock
    StatisticsCollector stats;
    @Mock
    InfectionPressure infPressure;
    @Mock
    StartLocationsAndAgeClasses slaac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doSusceptibleTransitionYoung(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac);

        when(infPressure.evaluate(rnd, 1)).thenReturn(
                TransmissionEventType.FROM_PERSON,
                TransmissionEventType.NULL
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verify(sm).updateInfectionState(PERSON_1, InfectionState.EXPOSED_YOUNG);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void doSusceptibleTransitionAdult(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ADULT);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac);

        when(infPressure.evaluate(rnd, 1)).thenReturn(
                TransmissionEventType.FROM_PERSON,
                TransmissionEventType.NULL
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verify(sm).updateInfectionState(PERSON_1, InfectionState.EXPOSED_ADULT);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void doSusceptibleTransitionElderly(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac);

        when(infPressure.evaluate(rnd, 1)).thenReturn(
                TransmissionEventType.FROM_PERSON,
                TransmissionEventType.NULL
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verify(sm).updateInfectionState(PERSON_1, InfectionState.EXPOSED_ELDERLY);
        verifyNoMoreInteractions(sm);
    }
}
