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
import scovmod.model.transition.DeathRates;
import scovmod.model.util.math.Random;

import static org.mockito.Mockito.*;

public class SevereInfectiousToDeadTransitionExecutorTest {

    private static int PERSON_1 = 100;
    private static int TIMESTEP = 1;
    private static double SI_TO_D_RATE = 0.3;
    private static double DEATH_RATE_MODIFICATION = 2.0;

    @Mock
    Random rnd;
    @Mock
    StateModifier sm;
    @Mock
    Parameters params;
    @Mock
    StartLocationsAndAgeClasses slaac;
    @Mock
    DeathRates dr;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void youngPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getSiToD_YoungRate()).thenReturn(SI_TO_D_RATE);
        when(dr.getDeathRateModifierBasedOnHealthIndex(PERSON_1)).thenReturn(DEATH_RATE_MODIFICATION);

        SevereInfectiousToDeadTransitionExecutor instance = new SevereInfectiousToDeadTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, dr);

        when(rnd.nextPoissonReturnsOneOrMore(SI_TO_D_RATE * DEATH_RATE_MODIFICATION * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.DEAD_YOUNG);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void adultPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ADULT);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getSiToD_AdultRate()).thenReturn(SI_TO_D_RATE);
        when(dr.getDeathRateModifierBasedOnHealthIndex(PERSON_1)).thenReturn(DEATH_RATE_MODIFICATION);

        SevereInfectiousToDeadTransitionExecutor instance = new SevereInfectiousToDeadTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, dr);

        when(rnd.nextPoissonReturnsOneOrMore(SI_TO_D_RATE  * DEATH_RATE_MODIFICATION * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.DEAD_ADULT);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void elderlyPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getSiToD_ElderlyRate()).thenReturn(SI_TO_D_RATE);
        when(dr.getDeathRateModifierBasedOnHealthIndex(PERSON_1)).thenReturn(DEATH_RATE_MODIFICATION);

        SevereInfectiousToDeadTransitionExecutor instance = new SevereInfectiousToDeadTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, dr);

        when(rnd.nextPoissonReturnsOneOrMore(SI_TO_D_RATE  * DEATH_RATE_MODIFICATION * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.DEAD_ELDERLY);
        verifyNoMoreInteractions(sm);
    }

}
