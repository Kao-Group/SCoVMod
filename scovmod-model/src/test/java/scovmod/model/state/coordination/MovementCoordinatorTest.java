package scovmod.model.state.coordination;

import static scovmod.model.state.infection.InfectionState.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import scovmod.model.state.cache.CacheManager;
import scovmod.model.state.delta.RemoveDelta;
import scovmod.model.state.delta.SetDelta;
import scovmod.model.state.population.LocalPopulationIndex;

public class MovementCoordinatorTest {

    private final int PERSON_1 = 1;

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;

    private final boolean ON = true;
    private final boolean OFF = false;

    @Mock
    private LocalPopulationIndex lpi;
    @Mock
    private MovementCoordinator instance;
    @Mock
    private CacheManager cm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        instance = new MovementCoordinator(lpi, cm);
    }
    

    @Test
    public void movingPersonFromOneLocationToAnotherPreservesState() {
        RemoveDelta rd = mock(RemoveDelta.class);
        when(rd.wasEdgeCase()).thenReturn(false);
        when(rd.getInfectionState()).thenReturn(MILD_INFECTIOUS_ADULT);
        when(rd.getLocationId()).thenReturn(LOCATION_1);

        SetDelta sd = mock(SetDelta.class);
        when(sd.wasEdgeCase()).thenReturn(false);

        when(lpi.remove(PERSON_1)).thenReturn(rd);
        when(lpi.set(PERSON_1, MILD_INFECTIOUS_ADULT, LOCATION_2)).thenReturn(sd);

        instance.movePerson(PERSON_1, LOCATION_2);

        verify(lpi).set(PERSON_1, MILD_INFECTIOUS_ADULT, LOCATION_2);
    }

    @Test
    public void offMovementEdgeCases() {
        RemoveDelta rd = mock(RemoveDelta.class);
        when(rd.wasEdgeCase()).thenReturn(true);
        when(rd.getInfectionState()).thenReturn(SUSCEPTIBLE);
        when(rd.getLocationId()).thenReturn(LOCATION_1);

        SetDelta sd = mock(SetDelta.class);
        when(sd.wasEdgeCase()).thenReturn(false);

        when(lpi.remove(PERSON_1)).thenReturn(rd);
        when(lpi.set(PERSON_1, SUSCEPTIBLE, LOCATION_2)).thenReturn(sd);

        instance.movePerson(PERSON_1, LOCATION_2);

        verify(cm).notifyEdgeCaseDetectedAt(LOCATION_1);
        verify(cm).notifyNonEdgeCaseMovement(PERSON_1, ON, LOCATION_2, SUSCEPTIBLE);
        verifyNoMoreInteractions(cm);
    }

    @Test
    public void onMovementEdgeCases() {
        RemoveDelta rd = mock(RemoveDelta.class);
        when(rd.wasEdgeCase()).thenReturn(false);
        when(rd.getInfectionState()).thenReturn(EXPOSED_ELDERLY);
        when(rd.getLocationId()).thenReturn(LOCATION_1);

        SetDelta sd = mock(SetDelta.class);
        when(sd.wasEdgeCase()).thenReturn(true);

        when(lpi.remove(PERSON_1)).thenReturn(rd);
        when(lpi.set(PERSON_1, EXPOSED_ELDERLY, LOCATION_2)).thenReturn(sd);

        instance.movePerson(PERSON_1, LOCATION_2);

        verify(cm).notifyEdgeCaseDetectedAt(LOCATION_2);
        verify(cm).notifyNonEdgeCaseMovement(PERSON_1, OFF, LOCATION_1, EXPOSED_ELDERLY);
        verifyNoMoreInteractions(cm);
    }

    @Test
    public void simultaneousEdgeCases() {
        RemoveDelta rd = mock(RemoveDelta.class);
        when(rd.wasEdgeCase()).thenReturn(true);
        when(rd.getInfectionState()).thenReturn(EXPOSED_ELDERLY);
        when(rd.getLocationId()).thenReturn(LOCATION_1);

        SetDelta sd = mock(SetDelta.class);
        when(sd.wasEdgeCase()).thenReturn(true);

        when(lpi.remove(PERSON_1)).thenReturn(rd);
        when(lpi.set(PERSON_1, EXPOSED_ELDERLY, LOCATION_2)).thenReturn(sd);

        instance.movePerson(PERSON_1, LOCATION_2);

        verify(cm).notifyEdgeCaseDetectedAt(LOCATION_1);
        verify(cm).notifyEdgeCaseDetectedAt(LOCATION_2);
        verifyNoMoreInteractions(cm);
    }

    @Test
    public void neitherOnNorOffEdgeCase() {
        RemoveDelta rd = mock(RemoveDelta.class);
        when(rd.wasEdgeCase()).thenReturn(false);
        when(rd.getInfectionState()).thenReturn(EXPOSED_YOUNG);
        when(rd.getLocationId()).thenReturn(LOCATION_1);

        SetDelta sd = mock(SetDelta.class);
        when(sd.wasEdgeCase()).thenReturn(false);

        when(lpi.remove(PERSON_1)).thenReturn(rd);
        when(lpi.set(PERSON_1, EXPOSED_YOUNG, LOCATION_2)).thenReturn(sd);

        instance.movePerson(PERSON_1, LOCATION_2);

        verify(cm).notifyNonEdgeCaseMovement(PERSON_1, OFF, LOCATION_1, EXPOSED_YOUNG);
        verify(cm).notifyNonEdgeCaseMovement(PERSON_1, ON, LOCATION_2, EXPOSED_YOUNG);
        verifyNoMoreInteractions(cm);
    }
}
