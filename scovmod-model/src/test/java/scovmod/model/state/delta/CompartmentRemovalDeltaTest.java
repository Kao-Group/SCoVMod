package scovmod.model.state.delta;

import static org.junit.Assert.*;

import org.junit.Test;

import static scovmod.model.state.infection.InfectionState.*;

public class CompartmentRemovalDeltaTest {

	private final int PERSON_1 = 1;
	private final int PERSON_2 = 2;


	@Test
	public void isValueObject(){
		CompartmentDelta instance1a = new CompartmentRemoveDelta(true, PERSON_1, EXPOSED_YOUNG);
		CompartmentDelta instance1b = new CompartmentRemoveDelta(true, PERSON_1, EXPOSED_YOUNG);
		CompartmentDelta instance2 = new CompartmentRemoveDelta(false, PERSON_1, EXPOSED_YOUNG);
		CompartmentDelta instance3 = new CompartmentRemoveDelta(true, PERSON_2, EXPOSED_YOUNG);
		//CompartmentDelta instance4 = new CompartmentRemoveDelta(true, PERSON_2, DEAD);

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
		CompartmentDelta instance1 = new CompartmentRemoveDelta(true, PERSON_1, null);
		assertTrue(instance1.wasEdgeCase());

		CompartmentDelta instance2 = new CompartmentRemoveDelta(false, PERSON_1, null);
		assertFalse(instance2.wasEdgeCase());
	}

	@Test
	public void personState(){
		CompartmentRemoveDelta instance1 = new CompartmentRemoveDelta(true, PERSON_1,  EXPOSED_YOUNG);
		assertEquals(EXPOSED_YOUNG, instance1.getInfectionState());
		assertEquals(PERSON_1, instance1.getPersonId());

		CompartmentRemoveDelta instance2 = new CompartmentRemoveDelta(false, PERSON_2, SUSCEPTIBLE);
		assertEquals(SUSCEPTIBLE, instance2.getInfectionState());
		assertEquals(PERSON_2, instance2.getPersonId());
	}

	@Test
	public void augmentingWithLocation(){
		CompartmentRemoveDelta osd = new CompartmentRemoveDelta(true, PERSON_1, EXPOSED_YOUNG);
		RemoveDelta instance1 = osd.tagWithLocation(100);

		assertTrue(((CompartmentDelta)instance1).wasEdgeCase());
		assertEquals(EXPOSED_YOUNG, instance1.getInfectionState());
		assertEquals(100, instance1.getLocationId());
		assertEquals(PERSON_1, instance1.getPersonId());

		RemoveDelta instance2 = osd.tagWithLocation(200);

		assertTrue(((CompartmentDelta)instance2).wasEdgeCase());
		assertEquals(EXPOSED_YOUNG, instance2.getInfectionState());
		assertEquals(200, instance2.getLocationId());
		assertEquals(PERSON_1, instance1.getPersonId());

		RemoveDelta instance3 = new CompartmentRemoveDelta(false, PERSON_2, SUSCEPTIBLE).tagWithLocation(300);

		assertFalse(((CompartmentDelta)instance3).wasEdgeCase());
		assertEquals(SUSCEPTIBLE, instance3.getInfectionState());
		assertEquals(300, instance3.getLocationId());
		assertEquals(PERSON_2, instance3.getPersonId());
	}
}
