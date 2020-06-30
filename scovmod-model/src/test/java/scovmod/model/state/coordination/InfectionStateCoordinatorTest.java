package scovmod.model.state.coordination;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.infection.InfectionTransitions;
import scovmod.model.state.StateException;
import scovmod.model.state.cache.CacheManager;
import scovmod.model.state.delta.CompartmentRemoveDelta;
import scovmod.model.state.delta.CompartmentSetDelta;
import scovmod.model.state.delta.RemoveDelta;
import scovmod.model.state.delta.SetDelta;
import scovmod.model.state.population.LocalPopulationBank;
import scovmod.model.state.population.LocalPopulationIndex;
import static scovmod.model.state.infection.InfectionState.*;

public class InfectionStateCoordinatorTest {

    private static final boolean EDGE_CASE = true;
    private static final boolean NOT_EDGE_CASE = false;

    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;

    private final Integer UNALLOCATED = null;
    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;

    @Mock
    private LocalPopulationIndex lpi;
    @Mock
    private CacheManager cm;
    private InfectionStateCoordinator instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        instance = new InfectionStateCoordinator(
                lpi,
                new InfectionTransitions(),
                cm
        );
    }

    @Test
    public void seedingInfection_NoEdgeCase() {
        when(lpi.set(PERSON_1, MILD_INFECTIOUS_ADULT, LOCATION_1)).thenReturn(new SetDelta(LOCATION_1, new CompartmentSetDelta(NOT_EDGE_CASE, PERSON_1, MILD_INFECTIOUS_ADULT)));
        when(lpi.getPersonInfectionStatus(PERSON_1)).thenReturn(UNDEFINED);

        instance.seedState(PERSON_1, MILD_INFECTIOUS_ADULT, LOCATION_1);

        verify(lpi).set(PERSON_1, MILD_INFECTIOUS_ADULT, LOCATION_1);
        verify(cm).notifyNonEdgeCaseStateChange(PERSON_1, UNDEFINED, MILD_INFECTIOUS_ADULT, LOCATION_1);
        verifyNoMoreInteractions(cm);
    }

    @Test
    public void seedingInfection_EdgeCase() {
        when(lpi.getPersonInfectionStatus(PERSON_2)).thenReturn(UNDEFINED);
        when(lpi.set(PERSON_2, EXPOSED_YOUNG, LOCATION_2)).thenReturn(new SetDelta(LOCATION_2, new CompartmentSetDelta(EDGE_CASE, PERSON_2, SUSCEPTIBLE)));

        instance.seedState(PERSON_2, EXPOSED_YOUNG, LOCATION_2);

        verify(lpi).set(PERSON_2, EXPOSED_YOUNG, LOCATION_2);
        verify(cm).notifyEdgeCaseDetectedAt(LOCATION_2);
        verifyNoMoreInteractions(cm);
    }

    @Test
    public void exceptionIfTryToSeedAnPersonWhichExistsElsewhere() {
        when(lpi.getPersonLocationId(PERSON_1)).thenReturn(LOCATION_1);

        try {
            instance.seedState(PERSON_1, SUSCEPTIBLE, LOCATION_1);
            fail("Expected exception");
        } catch (StateException e) {
        }
    }

    @Test
    public void updating_NoEdgeCase() {
		// SUSCEPTIBLE -> EXPOSED

        when(lpi.getPersonLocationId(PERSON_1)).thenReturn(LOCATION_1);
        when(lpi.getPersonInfectionStatus(PERSON_1)).thenReturn(SUSCEPTIBLE);
        when(lpi.remove(PERSON_1)).thenReturn(new RemoveDelta(LOCATION_1, new CompartmentRemoveDelta(NOT_EDGE_CASE, PERSON_1, SUSCEPTIBLE)));
        when(lpi.set(PERSON_1, EXPOSED_ADULT, LOCATION_1)).thenReturn(new SetDelta(LOCATION_1, new CompartmentSetDelta(NOT_EDGE_CASE, PERSON_1, EXPOSED_ADULT)));

        instance.updateInfectionState(PERSON_1, EXPOSED_ADULT);

        verify(lpi).set(PERSON_1, EXPOSED_ADULT, LOCATION_1);
        verify(cm).notifyNonEdgeCaseStateChange(PERSON_1, SUSCEPTIBLE, EXPOSED_ADULT, LOCATION_1);
        verifyNoMoreInteractions(cm);
    }

    @Test
    public void updating_EdgeCaseFromEnteringCompartment() {
		//EXPOSED -> MILD_INFECTIOUS

        when(lpi.getPersonLocationId(PERSON_2)).thenReturn(LOCATION_2);
        when(lpi.getPersonInfectionStatus(PERSON_2)).thenReturn(EXPOSED_ELDERLY);

        when(lpi.remove(PERSON_2)).thenReturn(new RemoveDelta(LOCATION_2, new CompartmentRemoveDelta(NOT_EDGE_CASE, PERSON_2, EXPOSED_ELDERLY)));
        when(lpi.set(PERSON_2, MILD_INFECTIOUS_ELDERLY, LOCATION_2)).thenReturn(new SetDelta(LOCATION_2, new CompartmentSetDelta(EDGE_CASE, PERSON_2, MILD_INFECTIOUS_ELDERLY)));

        instance.updateInfectionState(PERSON_2, MILD_INFECTIOUS_ELDERLY);

        verify(cm).notifyEdgeCaseDetectedAt(LOCATION_2);
        verifyNoMoreInteractions(cm);
    }

    @Test
    public void updating_EdgeCaseFromExitingCompartment() {
		//EXPOSED -> MILD_INFECTIOUS

        when(lpi.getPersonLocationId(PERSON_2)).thenReturn(LOCATION_2);
        when(lpi.getPersonInfectionStatus(PERSON_2)).thenReturn(EXPOSED_YOUNG);
        when(lpi.remove(PERSON_2)).thenReturn(new RemoveDelta(LOCATION_2, new CompartmentRemoveDelta(EDGE_CASE, PERSON_2, EXPOSED_YOUNG)));
        when(lpi.set(PERSON_2, MILD_INFECTIOUS_YOUNG, LOCATION_2)).thenReturn(new SetDelta(LOCATION_2, new CompartmentSetDelta(NOT_EDGE_CASE, PERSON_2, MILD_INFECTIOUS_YOUNG)));

        instance.updateInfectionState(PERSON_2, MILD_INFECTIOUS_YOUNG);

        verify(cm).notifyEdgeCaseDetectedAt(LOCATION_2);
        verifyNoMoreInteractions(cm);
    }

    @Test
    public void exceptionIfTryToUpdatePersonWhichDoesntExistYet() {
        when(lpi.getPersonInfectionStatus(PERSON_1)).thenReturn(UNDEFINED);
        try {
            instance.updateInfectionState(PERSON_1, SUSCEPTIBLE);
            fail("Expected exception");
        } catch (StateException e) {
        }

        /*try{
         instance.updateInfectionState(PERSON_1, DEAD);
         fail("Expected exception");
         }catch(StateException e){}*/
    }

    @Test
    public void allowedTransitions() {
        lpi = spy(new LocalPopulationIndex(new LocalPopulationBank()));
        instance = new InfectionStateCoordinator(
                lpi,
                new InfectionTransitions(),
                cm
        );

        instance.seedState(PERSON_1, SUSCEPTIBLE, LOCATION_1);

        assertTransitionException(PERSON_1, UNDEFINED);
        assertTransitionException(PERSON_1, SUSCEPTIBLE);
        assertTransitionException(PERSON_1, MILD_INFECTIOUS_ADULT);
      //  assertTransitionOK(PERSON_1, EXPOSED);
        assertTransitionOK(PERSON_1, EXPOSED_ADULT);
        assertTransitionException(PERSON_1, SUSCEPTIBLE);
    }

    private void assertTransitionOK(int personId, InfectionState state) {
        instance.updateInfectionState(personId, state);
        verify(lpi).set(personId, state, LOCATION_1);
    }

    private void assertTransitionException(int personId, InfectionState to) {
        try {
            instance.updateInfectionState(personId, to);
            fail("Expected exception");
        } catch (StateException e) {}
    }
}
