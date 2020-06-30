package scovmod.model.movements;

import scovmod.model.input.JsonMovementReader;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeStepMovementIterator implements Iterator<TimeStepMovements> {

    private long timeStep;
    private JsonMovementReader reader;
    private final Logger log = LoggerFactory.getLogger(this.getClass());    

    public TimeStepMovementIterator(JsonMovementReader reader, long startStep) {
        this.timeStep = startStep;
        this.reader = reader;
    }

    @Override
    public boolean hasNext() {
        if (log.isDebugEnabled()) {
            log.debug("In TimeStepMovementIterator hasNext() checking file for " + timeStep + " exists");
        }
        return reader.stepExists(timeStep);
    }
   
    @Override
    public TimeStepMovements next() {
        //log.debug("In TimeStepMovementIterator next()");
        TimeStepMovements tsm = reader.loadTimeStep(timeStep);
        timeStep++;
        return tsm;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
