/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.movement;

import static scovmod.model.util.TestUtils.intSetOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import scovmod.model.movements.LocationIncomingPersons;

public class LocationIncomingPersonsTest {

	private final int LOCATION_1 = 100;
	private final int LOCATION_2 = 100;
    private final int PERSON_1 = 100;
	private final int PERSON_2 = 200;
	private final int PERSON_3 = 300;
	private final int VISITOR_PERSON_1 = 50001;
	private final int VISITOR_PERSON_2 = 60001;

	@Test
	public void getAndSet(){
		LocationIncomingPersons instance = new LocationIncomingPersons(LOCATION_1,intSetOf(PERSON_1,PERSON_2,PERSON_3),intSetOf(VISITOR_PERSON_1,VISITOR_PERSON_2));
		assertEquals(LOCATION_1, instance.getLocationId());
		assertEquals(intSetOf(PERSON_1,PERSON_2,PERSON_3), instance.getNewPersonIds());
		assertEquals(intSetOf(VISITOR_PERSON_1,VISITOR_PERSON_2), instance.getTransitoryVisitors());
	}

	@Test
	public void valueObject(){
		LocationIncomingPersons instance1a = new LocationIncomingPersons(LOCATION_1,intSetOf(PERSON_1,PERSON_2),intSetOf(VISITOR_PERSON_1));
		LocationIncomingPersons instance1b = new LocationIncomingPersons(LOCATION_1,intSetOf(PERSON_1,PERSON_2),intSetOf(VISITOR_PERSON_1));
		LocationIncomingPersons instance2 = new LocationIncomingPersons(LOCATION_2,intSetOf(PERSON_3),intSetOf(VISITOR_PERSON_2));

		assertTrue(instance1a.equals(instance1b));
		assertFalse(instance1a.equals(instance2));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());

	}
}
