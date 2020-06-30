package scovmod.model.transition;

import scovmod.model.movements.LocationIncomingPersons;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import scovmod.model.transition.susceptible.FromSusceptible;

public class TransitionManagerTest {

    @Mock
    Set<LocationIncomingPersons> incomingPersons;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void delegatesToSubComponents() {
        FromSusceptible fromSus = mock(FromSusceptible.class);
        TransitionExecutor te = mock(TransitionExecutor.class);

        TransitionManager instance = new TransitionManager(fromSus, te);

        instance.doTransitions(incomingPersons);

        verify(fromSus).doTransitions(incomingPersons);
        verify(te).doExposedToMildTransitions();
        verify(te).doExposedToRecoveredTransitions();
        verify(te).doMildToSevereTransitions();
        verify(te).doMildToRecoveredTransitions();
        verify(te).doSevereToDeadTransitions();
        verify(te).doSevereToHospitalisedTransitions();
        verify(te).doSevereToRecoveredTransitions();
        verify(te).doHospitalisedToDeadTransitions();
        verify(te).doHospitalisedToRecoveredTransitions();
    }
}
