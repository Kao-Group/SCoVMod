/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition.susceptible;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.util.math.Random;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class InfectionPressureTest {

	private static final double TOLERANCE = 1e-10;

@Mock
Random rnd;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void isValueObject() {
		//Test hashcode and equals in here
		InfectionPressure instance1a = new InfectionPressure(0.2, 0.3);
		InfectionPressure instance1b = new InfectionPressure(0.2, 0.3);
		InfectionPressure instance2 = new InfectionPressure(0.4, 0.3);
		InfectionPressure instance4 = new InfectionPressure( 0.2, 0.4);

		assertEquals(instance1a, instance1b);
		assertFalse(instance1a.equals(instance2));
		assertFalse(instance1a.equals(instance4));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());
		assertFalse(instance1a.hashCode() == instance4.hashCode());
	}

	@Test
	public void testForEvaluate_NoTransmission() {
		int timeStepInDays = 15;
		double wildlifePressure = 0.00012;
		double personPressure = 0.015;
		InfectionPressure instance = new InfectionPressure(wildlifePressure, personPressure);
		double probNoTransmission =
				Math.exp(-(wildlifePressure * timeStepInDays)) *
				Math.exp(-(personPressure * timeStepInDays));
		when(rnd.nextBoolean(1-probNoTransmission)).thenReturn(false);

 		Assert.assertEquals(TransmissionEventType.NULL,instance.evaluate(rnd,timeStepInDays));
	}

//	@Test
//	public void testForEvaluate_EnvTransmission() {
//		int timeStepInDays = 15;
//		double wildlifePressure = 0.00012;
//		double personPressure = 0.015;
//		InfectionPressure instance = new InfectionPressure(wildlifePressure, personPressure);
//		double probNoTransmission =
//				Math.exp(-(wildlifePressure * timeStepInDays)) *
//						Math.exp(-(personPressure * timeStepInDays));
//		when(rnd.nextBoolean(1 - probNoTransmission)).thenReturn(true);
//		when(rnd.nextBoolean(getConditionalProbability(wildlifePressure,personPressure))).thenReturn(true);
//
//		assertEquals(TransmissionEventType.FROM_WILDLIFE,instance.evaluate(rnd,timeStepInDays));
//	}

	@Test
	public void testForEvaluate_PersonTransmission() {
		int timeStepInDays = 15;
		double wildlifePressure = 0.00012;
		double personPressure = 0.015;
		InfectionPressure instance = new InfectionPressure(wildlifePressure, personPressure);
		double probNoTransmission =
				Math.exp(-(wildlifePressure * timeStepInDays)) *
						Math.exp(-(personPressure * timeStepInDays));
		when(rnd.nextBoolean(1 - probNoTransmission)).thenReturn(true);
		when(rnd.nextBoolean(getConditionalProbability(wildlifePressure,personPressure))).thenReturn(false);
		assertEquals(TransmissionEventType.FROM_PERSON,instance.evaluate(rnd,timeStepInDays));
	}


	@Test
	public void augmentingCattlePressure() {
		double wildlifePressure = 0.00012;
		double personPressure = 0.015;
		InfectionPressure instance = new InfectionPressure(wildlifePressure, personPressure);
		InfectionPressure result = instance.augmentPeoplePressure(0.1);

		assertEquals(personPressure + 0.1, result.getPersonPressure(), TOLERANCE);
		assertEquals(wildlifePressure, result.getWildlifePressure(), TOLERANCE);
	}

	@Test
	public void settingWildlifePressure() {
		double personPressure = 0.012;
		InfectionPressure instance = new InfectionPressure(0, personPressure);
		InfectionPressure result = instance.setWildlifePressure(0.1);

		assertEquals(personPressure, result.getPersonPressure(), TOLERANCE);
		assertEquals(0.1, result.getWildlifePressure(), TOLERANCE);
	}

	@Test
	public void exceptionIfOverwriteWildlifePressure() {
		double wildlifePressure = 0.1;
		double personPressure = 0;
		InfectionPressure instance = new InfectionPressure(wildlifePressure, personPressure);

		try {
			instance.setWildlifePressure(0.2);
			fail("expected exception");
		} catch (AssertionError e){}
	}

	private double getConditionalProbability(double wildlifePressure, double personPressure){
		return wildlifePressure / (wildlifePressure + personPressure);
	}

}
