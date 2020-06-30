package scovmod.model.state.delta;

import static org.junit.Assert.*;

import org.junit.Test;

import static scovmod.model.state.infection.InfectionState.*;

public class CompartmentSetDeltaTest {
	private final int PERSON_1 = 1;
	private final int PERSON_2 = 2;

	@Test
	public void isValueObject(){
		CompartmentSetDelta instance1a = new CompartmentSetDelta(false, PERSON_1, SUSCEPTIBLE);
		CompartmentSetDelta instance1b = new CompartmentSetDelta(false, PERSON_1, SUSCEPTIBLE);
		CompartmentSetDelta instance2 = new CompartmentSetDelta(true, PERSON_1, SUSCEPTIBLE);
		CompartmentSetDelta instance3 = new CompartmentSetDelta(false, PERSON_2, SUSCEPTIBLE);
		//CompartmentSetDelta instance4 = new CompartmentSetDelta(false, PERSON_1, DEAD);

		assertEquals(instance1a, instance1b);
		assertFalse(instance1a.equals(instance2));
		assertFalse(instance1a.equals(instance3));
		//assertFalse(instance1a.equals(instance4));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());
		assertFalse(instance1a.hashCode() == instance3.hashCode());
		//assertFalse(instance1a.hashCode() == instance4.hashCode());
	}

	@Test
	public void interfaceMethod(){
		CompartmentDelta instance1 = new CompartmentSetDelta(true, 0, null);
		assertTrue(instance1.wasEdgeCase());

		CompartmentDelta instance2 = new CompartmentSetDelta(false, 0, null);
		assertFalse(instance2.wasEdgeCase());
	}

	@Test
	public void states(){
		CompartmentSetDelta instance1 = new CompartmentSetDelta(false, PERSON_1, SUSCEPTIBLE);
		assertEquals(SUSCEPTIBLE, instance1.getSetInfectionState());
		assertEquals(PERSON_1, instance1.getPersonId());

		CompartmentSetDelta instance2 = new CompartmentSetDelta(false, PERSON_2, EXPOSED_ADULT);
		assertEquals(EXPOSED_ADULT, instance2.getSetInfectionState());
		assertEquals(PERSON_2, instance2.getPersonId());
	}

	@Test
	public void augmentingWithLocation(){
		CompartmentSetDelta osd = new CompartmentSetDelta(true, PERSON_1, EXPOSED_ADULT);
		SetDelta instance1 = osd.addLocation(100);

		assertTrue(((CompartmentDelta)instance1).wasEdgeCase());
		assertEquals(EXPOSED_ADULT, instance1.getInfectionState());
		assertEquals(100, instance1.getLocationId());
		assertEquals(PERSON_1, instance1.getPersonId());

		SetDelta instance2 = osd.addLocation(200);

		assertTrue(((CompartmentDelta)instance2).wasEdgeCase());
		assertEquals(EXPOSED_ADULT, instance2.getInfectionState());
		assertEquals(200, instance2.getLocationId());
		assertEquals(PERSON_1, instance1.getPersonId());

		SetDelta instance3 = new CompartmentSetDelta(false, PERSON_2, SUSCEPTIBLE).addLocation(300);

		assertFalse(((CompartmentDelta)instance3).wasEdgeCase());
		assertEquals(SUSCEPTIBLE, instance3.getInfectionState());
		assertEquals(300, instance3.getLocationId());
		assertEquals(PERSON_2, instance3.getPersonId());
	}
}
