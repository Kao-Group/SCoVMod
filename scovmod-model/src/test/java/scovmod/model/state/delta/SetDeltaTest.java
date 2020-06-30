package scovmod.model.state.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import scovmod.model.state.infection.InfectionState;

public class SetDeltaTest {
	private final int LOCATION_1 = 100;
	private final int LOCATION_2 = 200;

	private final int PERSON_1 = 1;
	private final int PERSON_2 = 2;

	@Test
	public void isValueObject(){
		CompartmentSetDelta csd1 = new CompartmentSetDelta(true, PERSON_1, InfectionState.SEVERE_INFECTIOUS_ADULT);
		CompartmentSetDelta csd2 = new CompartmentSetDelta(false, PERSON_1, InfectionState.SEVERE_INFECTIOUS_ADULT);

		SetDelta instance1a = new SetDelta(LOCATION_1, csd1);
		SetDelta instance1b = new SetDelta(LOCATION_1, csd1);
		SetDelta instance2 = new SetDelta(LOCATION_2, csd1);
		SetDelta instance3 = new SetDelta(LOCATION_1, csd2);

		assertEquals(instance1a, instance1b);
		assertFalse(instance1a.equals(instance2));
		assertFalse(instance1a.equals(instance3));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());
		assertFalse(instance1a.hashCode() == instance3.hashCode());
	}

	@Test
	public void interfaceMethod(){
		CompartmentDelta instance1 = new SetDelta(0, new CompartmentSetDelta(true, PERSON_1, InfectionState.EXPOSED_ADULT));
		assertTrue(instance1.wasEdgeCase());

		CompartmentDelta instance2 = new SetDelta(0, new CompartmentSetDelta(false, PERSON_2, InfectionState.EXPOSED_ADULT));
		assertFalse(instance2.wasEdgeCase());
	}

	@Test
	public void getPersonAndLocationState(){
		SetDelta instance1 = new SetDelta(LOCATION_1, new CompartmentSetDelta(true, PERSON_1, InfectionState.SEVERE_INFECTIOUS_ADULT));
		assertEquals(InfectionState.SEVERE_INFECTIOUS_ADULT, instance1.getInfectionState());
		assertEquals(PERSON_1, instance1.getPersonId());
		assertEquals(LOCATION_1, instance1.getLocationId());

		SetDelta instance2 = new SetDelta(LOCATION_2, new CompartmentSetDelta(false, PERSON_2, InfectionState.EXPOSED_ADULT));
		assertEquals(InfectionState.EXPOSED_ADULT, instance2.getInfectionState());
		assertEquals(PERSON_2, instance2.getPersonId());
		assertEquals(LOCATION_2, instance2.getLocationId());
	}
}
