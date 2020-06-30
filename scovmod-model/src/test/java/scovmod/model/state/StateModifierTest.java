package scovmod.model.state;

import org.junit.Before;
import org.junit.Test;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.coordination.InfectionStateCoordinator;
import scovmod.model.state.coordination.MovementCoordinator;
import static org.mockito.Mockito.*;

public class StateModifierTest {

	private final int PERSON = 1;
	private final int NEW_LOCATION = 100;

	private MovementCoordinator mc;
	private InfectionStateCoordinator isc;

	private StateModifier instance;

	@Before
	public void setup(){
		mc = mock(MovementCoordinator.class);
		isc = mock(InfectionStateCoordinator.class);
		instance = new StateModifier(mc, isc);
	}

	@Test
	public void seedInfection(){
		instance.seedState(PERSON, InfectionState.SEVERE_INFECTIOUS_ELDERLY, NEW_LOCATION);

		verifyNoMoreInteractions(mc);
		verify(isc).seedState(PERSON, InfectionState.SEVERE_INFECTIOUS_ELDERLY, NEW_LOCATION);
	}

	@Test
	public void updateInfectionState(){
		instance.updateInfectionState(PERSON, InfectionState.SUSCEPTIBLE);

		verifyNoMoreInteractions(mc);
		verify(isc).updateInfectionState(PERSON, InfectionState.SUSCEPTIBLE);
	}

	@Test
	public void movePerson(){
		instance.movePerson(PERSON, NEW_LOCATION);

		verifyNoMoreInteractions(isc);
		verify(mc).movePerson(PERSON, NEW_LOCATION);
	}
}
