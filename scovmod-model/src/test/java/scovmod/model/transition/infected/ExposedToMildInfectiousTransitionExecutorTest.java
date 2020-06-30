package scovmod.model.transition.infected;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.util.math.Random;

import static org.mockito.Mockito.*;

public class ExposedToMildInfectiousTransitionExecutorTest {

    private static int PERSON_1 = 100;
    private static int TIMESTEP = 1;
    private static double E_TO_MI_RATE = 0.3;

    @Mock
    Random rnd;
    @Mock
    StateModifier sm;
    @Mock
    Parameters params;
    @Mock
    StartLocationsAndAgeClasses slaac;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void youngPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.geteToMI_YoungRate()).thenReturn(E_TO_MI_RATE);

        ExposedToMildInfectiousTransitionExecutor instance = new ExposedToMildInfectiousTransitionExecutor(sm, rnd, TIMESTEP, params,slaac);

        when(rnd.nextPoissonReturnsOneOrMore(E_TO_MI_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.MILD_INFECTIOUS_YOUNG);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void adultPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ADULT);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.geteToMI_AdultRate()).thenReturn(E_TO_MI_RATE);

        ExposedToMildInfectiousTransitionExecutor instance = new ExposedToMildInfectiousTransitionExecutor(sm, rnd, TIMESTEP, params,slaac);

        when(rnd.nextPoissonReturnsOneOrMore(E_TO_MI_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.MILD_INFECTIOUS_ADULT);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void elderlyPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.geteToMI_ElderlyRate()).thenReturn(E_TO_MI_RATE);

        ExposedToMildInfectiousTransitionExecutor instance = new ExposedToMildInfectiousTransitionExecutor(sm, rnd, TIMESTEP, params,slaac);

        when(rnd.nextPoissonReturnsOneOrMore(E_TO_MI_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.MILD_INFECTIOUS_ELDERLY);
        verifyNoMoreInteractions(sm);
    }

}
