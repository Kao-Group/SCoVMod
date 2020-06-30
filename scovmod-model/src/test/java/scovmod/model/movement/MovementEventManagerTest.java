package scovmod.model.movement;

import scovmod.model.movements.*;
import scovmod.model.util.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.mockito.Mockito.*;
import static scovmod.model.util.TestUtils.intSetOf;

public class MovementEventManagerTest {

    @Mock
    LocationIncomingPersons incoming1, incoming2;

    @Mock
    Set<ResolvedMovement> movements1, movements2;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void resolveMovementsThenDelegateTsoMover(){
        Mover mover = mock(Mover.class);

        Resolver resolver = mock(Resolver.class);
        when(resolver.apply(incoming1,intSetOf(),intSetOf(),intSetOf())).thenReturn(movements1);
        when(resolver.apply(incoming2,intSetOf(),intSetOf(),intSetOf())).thenReturn(movements2);

        MovementEventManager instance = new MovementEventManager(resolver, mover);
        instance.doMovements(TestUtils.setOf(incoming1, incoming2),intSetOf(),intSetOf(),intSetOf());

        verify(mover).apply(movements1);
        verify(mover).apply(movements2);
        verifyNoMoreInteractions(mover);
    }
}
