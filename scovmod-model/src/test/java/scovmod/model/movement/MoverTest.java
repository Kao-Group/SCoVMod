package scovmod.model.movement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.movements.MovementHandler;
import scovmod.model.movements.Mover;
import scovmod.model.movements.ResolvedMovement;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.util.TestUtils;

import static org.mockito.Mockito.*;

public class MoverTest {

    private final int PERSON_1 = 500;
    private final int SOURCE_FARM_1 = 10001;
    private final int DESTINATION_LOCATION_1 = 20001;
    private final InfectionState ALIVE_STATE = InfectionState.MILD_INFECTIOUS_ADULT;

    @Mock
    StatisticsCollector stats;
    @Mock
    MovementHandler mh;

    private Mover instance;

    ResolvedMovement personMovement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        instance = new Mover(mh,stats);

        personMovement = new ResolvedMovement(
                PERSON_1,
                SOURCE_FARM_1,
                DESTINATION_LOCATION_1,
                ALIVE_STATE);
    }


    @Test
    public void applyPersonMovements() {
        instance.apply(TestUtils.setOf(personMovement));
        verify(mh).apply(personMovement);
        verifyNoMoreInteractions(mh, stats);
    }

}
