package scovmod.model.movement;

import scovmod.model.movements.ResolvedMovement;
import scovmod.model.movements.Resolver;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.state.StateQuery;
import static scovmod.model.state.infection.InfectionState.*;

import scovmod.model.util.TestUtils;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.intSetOf;

import org.mockito.MockitoAnnotations;

public class ResolverTest {

    @Mock
    private StateQuery sq;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private Resolver instance;
    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int LOCATION_3 = 300;
    private final int PERSON_1 = 1001;
    private final int PERSON_2 = 1002;
    private final int PERSON_3 = 1003;

    @Test
    public void resolveToSetOfMovements() {
        when(sq.getPersonLocation(PERSON_1)).thenReturn(LOCATION_1);
        when(sq.getPersonLocation(PERSON_2)).thenReturn(LOCATION_1);
        when(sq.getPersonLocation(PERSON_3)).thenReturn(LOCATION_3);
        when(sq.getPersonInfectionStatus(PERSON_1)).thenReturn(MILD_INFECTIOUS_ADULT);
        when(sq.getPersonInfectionStatus(PERSON_2)).thenReturn(SUSCEPTIBLE);
        when(sq.getPersonInfectionStatus(PERSON_3)).thenReturn(DEAD_ELDERLY);
        when(sq.getAllExposedLocations()).thenReturn(intSetOf());
        when(sq.getAllMildInfectiousLocations()).thenReturn(intSetOf(LOCATION_2));
        when(sq.getAllSevereInfectiousLocations()).thenReturn(intSetOf());
        instance = new Resolver(sq);

        LocationIncomingPersons locInMovs = new LocationIncomingPersons(
                LOCATION_2,
                intSetOf(PERSON_1, PERSON_2, PERSON_3),
                null);

        ResolvedMovement Person_1_Movement = new ResolvedMovement(
                PERSON_1,
                LOCATION_1,
                LOCATION_2,
                MILD_INFECTIOUS_ADULT
        );
        ResolvedMovement Person_2_Movement = new ResolvedMovement(
                PERSON_2,
                LOCATION_1,
                LOCATION_2,
                SUSCEPTIBLE
        );
        ResolvedMovement Person_3_Movement = new ResolvedMovement(
                PERSON_3,
                LOCATION_3,
                LOCATION_2,
                DEAD_ELDERLY
        );
        Set<ResolvedMovement> expectedResolvedMovements = TestUtils.setOf(
                Person_1_Movement,
                Person_2_Movement
                /*Person_3_Movement*/);

        assertEquals(expectedResolvedMovements, instance.apply(locInMovs,intSetOf(),intSetOf(LOCATION_2),intSetOf()));
    }

    @Test
    public void doNotProcessDeadPerson() {
        when(sq.getPersonLocation(PERSON_1)).thenReturn(LOCATION_1);
        when(sq.getPersonLocation(PERSON_2)).thenReturn(LOCATION_1);
        when(sq.getPersonLocation(PERSON_3)).thenReturn(LOCATION_3);
        when(sq.getPersonInfectionStatus(PERSON_1)).thenReturn(MILD_INFECTIOUS_ADULT);
        when(sq.getPersonInfectionStatus(PERSON_2)).thenReturn(SUSCEPTIBLE);
        when(sq.getPersonInfectionStatus(PERSON_3)).thenReturn(DEAD_ELDERLY);
        when(sq.getAllExposedLocations()).thenReturn(intSetOf());
        when(sq.getAllMildInfectiousLocations()).thenReturn(intSetOf());
        when(sq.getAllSevereInfectiousLocations()).thenReturn(intSetOf());

        instance = new Resolver(sq);

        LocationIncomingPersons locInMovs = new LocationIncomingPersons(
                LOCATION_3,
                intSetOf(PERSON_1, PERSON_2, PERSON_3),
                null);

        assertEquals(1, instance.apply(locInMovs,intSetOf(),intSetOf(),intSetOf()).size());
    }
}
