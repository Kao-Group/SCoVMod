/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.movement;

import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.MovementStepper;
import scovmod.model.movements.TimeStepMovements;
import scovmod.model.time.TimeManager;
import scovmod.model.ModelException;
import static scovmod.model.util.TestUtils.iteratorOf;
import static scovmod.model.util.TestUtils.setOf;

import java.util.Iterator;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class MovementStepperTest {

    private static final int TIMESTEP_1 = 10001;
    private static final int TIMESTEP_4 = 10004;

    private static final int TIME_STEP_SIZE = 1;

    @Mock
    private TimeManager tm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fieldForTimeStepSize(){
        MovementStepper instance = new MovementStepper(null, TIMESTEP_1, TIME_STEP_SIZE);
        assertEquals(TIME_STEP_SIZE, instance.getTimeStepSize());
    }

    @Test
    public void exceptionIfNoMovments() {
        LocationIncomingPersons anis1 = mock(LocationIncomingPersons.class);
        LocationIncomingPersons anis2 = mock(LocationIncomingPersons.class);
        LocationIncomingPersons anis3 = mock(LocationIncomingPersons.class);

        Set<LocationIncomingPersons> incomingAnis1 = setOf(anis1, anis2);
        Set<LocationIncomingPersons> incomingAnis2 = setOf(anis3);

        Iterator<TimeStepMovements> movementIt = iteratorOf(
                new TimeStepMovements(TIMESTEP_1, incomingAnis1),
                new TimeStepMovements(TIMESTEP_4, incomingAnis2)
        );

        MovementStepper instance = new MovementStepper(movementIt, TIMESTEP_1, TIME_STEP_SIZE);
        assertEquals(new TimeStepMovements(TIMESTEP_1, incomingAnis1), instance.getNextTimeStepMovements());
        try {
            instance.getNextTimeStepMovements();
            fail("expected exception");
        } catch (ModelException e) {}
    }
}
