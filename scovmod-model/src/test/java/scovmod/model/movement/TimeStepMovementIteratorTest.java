/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.movement;

import scovmod.model.input.JsonMovementReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.TimeStepMovementIterator;
import scovmod.model.movements.TimeStepMovements;

import static scovmod.model.util.TestUtils.*;

public class TimeStepMovementIteratorTest {

    private final long STEP_EXISTS = 1001;
    private final long STEP_DOESNT_EXIST = 1003;

    @Mock
    private JsonMovementReader reader;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void hasNext_true() {
        TimeStepMovementIterator instance = new TimeStepMovementIterator(reader, STEP_EXISTS);
        when(reader.stepExists(STEP_EXISTS)).thenReturn(true);
        assertTrue(instance.hasNext());
    }

    @Test
    public void hasNext_false() {
        TimeStepMovementIterator instance = new TimeStepMovementIterator(reader, STEP_DOESNT_EXIST);
        when(reader.stepExists(STEP_DOESNT_EXIST)).thenReturn(false);
        assertFalse(instance.hasNext());
    }

    @Test
    public void next() {
        TimeStepMovements tsm = new TimeStepMovements(200401, setOf(
                new LocationIncomingPersons(100, intSetOf(10001, 20001, 30001), intSetOf(50001, 60001)),
                new LocationIncomingPersons(200, intSetOf(10002, 20002, 30002), intSetOf()),
                new LocationIncomingPersons(300, intSetOf(10003, 20003, 30003), intSetOf(50003, 60003, 70003))));

        TimeStepMovementIterator instance = new TimeStepMovementIterator(reader, STEP_EXISTS);

        when(reader.stepExists(STEP_EXISTS)).thenReturn(true);
        when(reader.loadTimeStep(STEP_EXISTS)).thenReturn(tsm);

        assertTrue(instance.hasNext());
        assertEquals(tsm, instance.next());
    }
}
