/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.movement;

import static scovmod.model.util.TestUtils.intSetOf;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.TimeStepMovements;

public class TimeStepMovementsTest {
	
	private final int LOCATION_1 = 100;
	private final int LOCATION_2 = 200;	
	private final int LOCATION_3 = 300;		
    private final int PERSON_1 = 100;
	private final int PERSON_2 = 200;
	private final int PERSON_3 = 300;
	private final int VISITOR_PERSON_1 = 50001;
	private final int VISITOR_PERSON_2 = 60001;

	@Mock
    Set<LocationIncomingPersons> setIncomingPersons;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getAndSet(){
		
		Set<LocationIncomingPersons> incomingPersonsSet = new HashSet<>();
	    LocationIncomingPersons farm1 = new LocationIncomingPersons(LOCATION_1,intSetOf(PERSON_1,PERSON_2),intSetOf(VISITOR_PERSON_1));
		LocationIncomingPersons farm2 = new LocationIncomingPersons(LOCATION_2,intSetOf(PERSON_1,PERSON_2),intSetOf(VISITOR_PERSON_1));
		LocationIncomingPersons farm3 = new LocationIncomingPersons(LOCATION_3,intSetOf(PERSON_3),intSetOf(VISITOR_PERSON_2));
		incomingPersonsSet.add(farm1);
		incomingPersonsSet.add(farm2);
		incomingPersonsSet.add(farm3);
		
		TimeStepMovements instance = new TimeStepMovements(200401, incomingPersonsSet);
		
		assertEquals(200401, instance.getTimeStep());
		assertEquals(incomingPersonsSet, instance.getMovements());
		
		Set<LocationIncomingPersons> moreIncomingPersons = setIncomingPersons;
		TimeStepMovements instance2 = new TimeStepMovements(201001, moreIncomingPersons);	
		assertEquals(201001, instance2.getTimeStep());
		assertSame(moreIncomingPersons, instance2.getMovements());
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
