package scovmod.model.state.infection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static scovmod.model.state.infection.InfectionState.*;

public class InfectionTransitionsTest {

	private InfectionTransitions instance;

	@Before
	public void setup(){
		instance = new InfectionTransitions();
	}

	/*
	 * UNDEFINED => SUSCEPTIBLE => EXPOSED => GAMMA-TEST-SENSITIVE => INFECTIOUS
	 */

	@Test
	public void permittedTransitions(){
		assertTrue(instance.permittedTransition(UNDEFINED, SUSCEPTIBLE));

        assertTrue(instance.permittedTransition(SUSCEPTIBLE, EXPOSED_YOUNG));
        assertTrue(instance.permittedTransition(SUSCEPTIBLE, EXPOSED_ADULT));
        assertTrue(instance.permittedTransition(SUSCEPTIBLE, EXPOSED_ELDERLY));

        assertTrue(instance.permittedTransition(EXPOSED_YOUNG, MILD_INFECTIOUS_YOUNG));
        assertTrue(instance.permittedTransition(EXPOSED_ADULT, MILD_INFECTIOUS_ADULT));
        assertTrue(instance.permittedTransition(EXPOSED_ELDERLY, MILD_INFECTIOUS_ELDERLY));

        assertTrue(instance.permittedTransition(EXPOSED_YOUNG, RECOVERED_YOUNG));
        assertTrue(instance.permittedTransition(EXPOSED_ADULT, RECOVERED_ADULT));
        assertTrue(instance.permittedTransition(EXPOSED_ELDERLY, RECOVERED_ELDERLY));

        assertTrue(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, SEVERE_INFECTIOUS_YOUNG));
        assertTrue(instance.permittedTransition(MILD_INFECTIOUS_ADULT, SEVERE_INFECTIOUS_ADULT));
        assertTrue(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, SEVERE_INFECTIOUS_ELDERLY));

        assertTrue(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, RECOVERED_YOUNG));
        assertTrue(instance.permittedTransition(MILD_INFECTIOUS_ADULT, RECOVERED_ADULT));
        assertTrue(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, RECOVERED_ELDERLY));

        assertTrue(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, DEAD_YOUNG));
        assertTrue(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, DEAD_ADULT));
        assertTrue(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, DEAD_ELDERLY));

        assertTrue(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, RECOVERED_YOUNG));
        assertTrue(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, RECOVERED_ADULT));
        assertTrue(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, RECOVERED_ELDERLY));

        assertTrue(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, HOSPITALISED_YOUNG));
        assertTrue(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, HOSPITALISED_ADULT));
        assertTrue(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, HOSPITALISED_ELDERLY));

        assertTrue(instance.permittedTransition(HOSPITALISED_YOUNG, RECOVERED_YOUNG));
        assertTrue(instance.permittedTransition(HOSPITALISED_ADULT, RECOVERED_ADULT));
        assertTrue(instance.permittedTransition(HOSPITALISED_ELDERLY, RECOVERED_ELDERLY));

        assertTrue(instance.permittedTransition(HOSPITALISED_YOUNG, DEAD_YOUNG));
        assertTrue(instance.permittedTransition(HOSPITALISED_ADULT, DEAD_ADULT));
        assertTrue(instance.permittedTransition(HOSPITALISED_ELDERLY, DEAD_ELDERLY));
	}

	@Test
	public void premittedTransitions(){
		assertFalse(instance.permittedTransition(UNDEFINED, UNDEFINED));
        assertFalse(instance.permittedTransition(UNDEFINED, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(UNDEFINED, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(UNDEFINED, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(UNDEFINED, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(UNDEFINED, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(UNDEFINED, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(UNDEFINED, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(UNDEFINED, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(UNDEFINED, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(UNDEFINED, RECOVERED_YOUNG));
        assertFalse(instance.permittedTransition(UNDEFINED, RECOVERED_ADULT));
        assertFalse(instance.permittedTransition(UNDEFINED, RECOVERED_ELDERLY));
        assertFalse(instance.permittedTransition(UNDEFINED, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(UNDEFINED, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(UNDEFINED, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(UNDEFINED, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(UNDEFINED, DEAD_ADULT));
        assertFalse(instance.permittedTransition(UNDEFINED, DEAD_ELDERLY));

		assertFalse(instance.permittedTransition(SUSCEPTIBLE, UNDEFINED));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, RECOVERED_YOUNG));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, RECOVERED_ADULT));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, RECOVERED_ELDERLY));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, DEAD_ADULT));
        assertFalse(instance.permittedTransition(SUSCEPTIBLE, DEAD_ELDERLY));

        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, UNDEFINED));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, UNDEFINED));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, UNDEFINED));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, DEAD_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_YOUNG, DEAD_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, DEAD_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_ADULT, DEAD_ELDERLY));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, DEAD_ADULT));
        assertFalse(instance.permittedTransition(EXPOSED_ELDERLY, DEAD_ELDERLY));

        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, UNDEFINED));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, UNDEFINED));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, UNDEFINED));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, DEAD_ADULT));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_YOUNG, DEAD_ELDERLY));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, DEAD_ADULT));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ADULT, DEAD_ELDERLY));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, DEAD_ADULT));
        assertFalse(instance.permittedTransition(MILD_INFECTIOUS_ELDERLY, DEAD_ELDERLY));

        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, UNDEFINED));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, UNDEFINED));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, UNDEFINED));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_YOUNG, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ADULT, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(SEVERE_INFECTIOUS_ELDERLY, MILD_INFECTIOUS_ELDERLY));

        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, UNDEFINED));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, UNDEFINED));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, UNDEFINED));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, DEAD_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_YOUNG, DEAD_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, DEAD_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ADULT, DEAD_ELDERLY));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, DEAD_ADULT));
        assertFalse(instance.permittedTransition(RECOVERED_ELDERLY, DEAD_ELDERLY));

        assertFalse(instance.permittedTransition(DEAD_YOUNG, UNDEFINED));
        assertFalse(instance.permittedTransition(DEAD_ADULT, UNDEFINED));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, UNDEFINED));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(DEAD_ADULT, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ADULT, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ADULT, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ADULT, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ADULT, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ADULT, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ADULT, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ADULT, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ADULT, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ADULT, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ADULT, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ADULT, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ADULT, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, DEAD_ADULT));
        assertFalse(instance.permittedTransition(DEAD_YOUNG, DEAD_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ADULT, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ADULT, DEAD_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ADULT, DEAD_ELDERLY));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, DEAD_YOUNG));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, DEAD_ADULT));
        assertFalse(instance.permittedTransition(DEAD_ELDERLY, DEAD_ELDERLY));

        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, UNDEFINED));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, UNDEFINED));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, UNDEFINED));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, SUSCEPTIBLE));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, EXPOSED_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, EXPOSED_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, EXPOSED_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, MILD_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, MILD_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, MILD_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, SEVERE_INFECTIOUS_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, SEVERE_INFECTIOUS_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, SEVERE_INFECTIOUS_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_YOUNG, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_ADULT, HOSPITALISED_ELDERLY));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, HOSPITALISED_YOUNG));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, HOSPITALISED_ADULT));
        assertFalse(instance.permittedTransition(HOSPITALISED_ELDERLY, HOSPITALISED_ELDERLY));

	}
}
