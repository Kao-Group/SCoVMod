package scovmod.model.state.delta;

import static org.junit.Assert.*;

import org.junit.Test;

import scovmod.model.state.infection.InfectionState;

public class RemoveDeltaTest {

	private final int LOCATION_1 = 100;
	private final int LOCATION_2 = 200;

	private final int PERSON_1 = 1;
	private final int PERSON_2 = 2;

	@Test
	public void isValueObject(){
		CompartmentRemoveDelta crd1 = new CompartmentRemoveDelta(true, PERSON_1, InfectionState.MILD_INFECTIOUS_ELDERLY);
		CompartmentRemoveDelta crd2 = new CompartmentRemoveDelta(false, PERSON_1, InfectionState.MILD_INFECTIOUS_ELDERLY);

		RemoveDelta instance1a = new RemoveDelta(LOCATION_1, crd1);
		RemoveDelta instance1b = new RemoveDelta(LOCATION_1, crd1);
		RemoveDelta instance2 = new RemoveDelta(LOCATION_2, crd1);
		RemoveDelta instance3 = new RemoveDelta(LOCATION_1, crd2);

		assertEquals(instance1a, instance1b);
		assertFalse(instance1a.equals(instance2));
		assertFalse(instance1a.equals(instance3));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());
		assertFalse(instance1a.hashCode() == instance3.hashCode());
	}

	@Test
	public void interfaceMethod(){
		CompartmentDelta instance1 = new RemoveDelta(0, new CompartmentRemoveDelta(true, PERSON_1, InfectionState.EXPOSED_ELDERLY));
		assertTrue(instance1.wasEdgeCase());

		CompartmentDelta instance2 = new RemoveDelta(0, new CompartmentRemoveDelta(false, PERSON_2, InfectionState.EXPOSED_ELDERLY));
		assertFalse(instance2.wasEdgeCase());
	}

	@Test
	public void personAndLocationState(){
		RemoveDelta instance1 = new RemoveDelta(LOCATION_1, new CompartmentRemoveDelta(true, PERSON_1, InfectionState.EXPOSED_ELDERLY));
		assertEquals(InfectionState.EXPOSED_ELDERLY, instance1.getInfectionState());
		assertEquals(PERSON_1, instance1.getPersonId());
		assertEquals(LOCATION_1, instance1.getLocationId());

		RemoveDelta instance2 = new RemoveDelta(LOCATION_2, new CompartmentRemoveDelta(false, PERSON_2, InfectionState.SUSCEPTIBLE));
		assertEquals(InfectionState.SUSCEPTIBLE, instance2.getInfectionState());
		assertEquals(PERSON_2, instance2.getPersonId());
		assertEquals(LOCATION_2, instance2.getLocationId());
	}


}
