package scovmod.model.movement;


import scovmod.model.movements.ResolvedMovement;
import scovmod.model.movements.MovementHandler;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class MovementHandlerTest {

    private final int PERSON_1 = 100;
    private final int PERSON_2 = 200;
    private final int SOURCE_LOCATION_1 = 10001;
    private final int DESTINATION_LOCATION_1 = 20001;
    private final InfectionState ALIVE_STATE = InfectionState.SEVERE_INFECTIOUS_ELDERLY;

    @Mock
    StateModifier sm;
    @Mock
    StatisticsCollector stats;
    @Mock
    StateQuery sq;

    private MovementHandler instance;

    ResolvedMovement  PersonMovement1,PersonMovement2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        instance = new MovementHandler(sm,stats, sq);

        PersonMovement1 = new ResolvedMovement(
                PERSON_1,
                SOURCE_LOCATION_1,
                DESTINATION_LOCATION_1,
                ALIVE_STATE);

        PersonMovement2 = new ResolvedMovement(
                PERSON_2,
                SOURCE_LOCATION_1,
                DESTINATION_LOCATION_1,
                ALIVE_STATE);
    }

    @Test
    public void movementOfPerson() {
        when(sq.isShowingSevereSymptoms(PERSON_1)).thenReturn(false);
        instance.apply(PersonMovement1);
        verify(sm).movePerson(PERSON_1,DESTINATION_LOCATION_1);
        verifyNoMoreInteractions( sm);
    }

    @Test
    public void nonMovementOfPersonWithSymptoms() {
        when(sq.isShowingSevereSymptoms(PERSON_2)).thenReturn(true);
        instance.apply(PersonMovement2);
        verifyNoMoreInteractions( sm);
    }

}
