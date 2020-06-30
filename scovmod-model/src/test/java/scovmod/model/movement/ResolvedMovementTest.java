package scovmod.model.movement;

import scovmod.model.movements.ResolvedMovement;
import scovmod.model.state.infection.InfectionState;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResolvedMovementTest {

    private ResolvedMovement instance;

    private final int PERSON_1 = 100;
    private final int PERSON_2 = 200;
    private final int SOURCE_LOCATION_1 = 10001;
    private final int SOURCE_LOCATION_2 = 20001;
    private final int DESTINATION_LOCATION_1 = 20001;
    private final int DESTINATION_LOCATION_2 = 20002;
    private final InfectionState INFECTION_STATE_PERSON_1 =InfectionState.MILD_INFECTIOUS_ADULT;
    private final InfectionState INFECTION_STATE_PERSON_2 =InfectionState.SUSCEPTIBLE;

    @Test
    public void valueObject() {
        ResolvedMovement instance1a = new ResolvedMovement(
                PERSON_1,
                SOURCE_LOCATION_1,
                DESTINATION_LOCATION_1,
                INFECTION_STATE_PERSON_1);

        ResolvedMovement instance1b = new ResolvedMovement(
                PERSON_1,
                SOURCE_LOCATION_1,
                DESTINATION_LOCATION_1,
                INFECTION_STATE_PERSON_1);

        ResolvedMovement instance2 = new ResolvedMovement(
                PERSON_2,
                SOURCE_LOCATION_1,
                DESTINATION_LOCATION_1,
                INFECTION_STATE_PERSON_1);

        ResolvedMovement instance3 = new ResolvedMovement(
                PERSON_1,
                SOURCE_LOCATION_2,
                DESTINATION_LOCATION_1,
                INFECTION_STATE_PERSON_1);

        ResolvedMovement instance4 = new ResolvedMovement(
                PERSON_1,
                SOURCE_LOCATION_1,
                DESTINATION_LOCATION_2,
                INFECTION_STATE_PERSON_2);

        ResolvedMovement instance5 = new ResolvedMovement(
                PERSON_1,
                SOURCE_LOCATION_1,
                DESTINATION_LOCATION_1,
                INFECTION_STATE_PERSON_1);

        ResolvedMovement instance6 = new ResolvedMovement(
                PERSON_1,
                SOURCE_LOCATION_1,
                DESTINATION_LOCATION_1,
                INFECTION_STATE_PERSON_1);

        ResolvedMovement instance7 = new ResolvedMovement(
                PERSON_1,
                SOURCE_LOCATION_1,
                DESTINATION_LOCATION_1,
                INFECTION_STATE_PERSON_2);


        assertTrue(instance1a.equals(instance1b));
        assertFalse(instance1a.equals(instance2));
        assertFalse(instance1a.equals(instance3));
        assertFalse(instance1a.equals(instance4));

        assertEquals(instance1a.hashCode(), instance1b.hashCode());
        assertFalse(instance1a.hashCode() == instance2.hashCode());
        assertFalse(instance1a.hashCode() == instance3.hashCode());
        assertFalse(instance1a.hashCode() == instance4.hashCode());
    }
}

