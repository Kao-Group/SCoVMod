package scovmod.model.input;

import static scovmod.model.util.TestUtils.setOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.TimeStepMovements;
import static scovmod.model.util.TestUtils.intSetOf;

import org.mockito.MockitoAnnotations;

public class JsonMovementReaderTest {

    static final Path basePath = Paths.get("src", "test", "resources", "inputData", "movements");

    JsonMovementReader instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        instance = new JsonMovementReader(basePath);
    }

    @Test
    public void loadTimeSteps() {
        TimeStepMovements expected1 = new TimeStepMovements(1001, setOf(
                new LocationIncomingPersons(100, intSetOf(10001, 20001, 30001), intSetOf(50001, 60001)),
                new LocationIncomingPersons(200, intSetOf(10002, 20002, 30002), intSetOf()),
                new LocationIncomingPersons(300, intSetOf(10003, 20003, 30003), intSetOf(50003, 60003, 70003)))
        );

        assertTrue(instance.stepExists(1001));
        assertEquals(
                expected1,
                instance.loadTimeStep(1001));

        TimeStepMovements expected2 = new TimeStepMovements(1002, setOf(
                new LocationIncomingPersons(100, intSetOf(10002, 20002, 30003), intSetOf(50003, 60002)),
                new LocationIncomingPersons(200, intSetOf(10003), intSetOf()),
                new LocationIncomingPersons(300, intSetOf(10001), intSetOf(50002, 60001, 70001)))
        );

        assertTrue(instance.stepExists(1002));
        assertEquals(
                expected2,
                instance.loadTimeStep(1002));
    }

    
    @Test
    public void stepWithNoMovements() {
        assertTrue(instance.stepExists(1003));
        assertEquals(
                new TimeStepMovements(1003, setOf()),
                instance.loadTimeStep(1003));
    }

    @Test
    public void fileWhichDoesntExist() {
        assertFalse(instance.stepExists(1004));
    }

    @Test
    public void wrongJsonStructureFile() {
        try {
            instance.loadTimeStep(2000);
            fail("Expected exception");
        } catch (RuntimeException e) {
        }
    }
}
