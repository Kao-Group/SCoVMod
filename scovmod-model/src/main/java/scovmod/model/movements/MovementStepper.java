package scovmod.model.movements;

import scovmod.model.ModelException;
import scovmod.model.input.*;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.InputData;
import scovmod.model.util.BufferedIterator;
import java.util.Iterator;

public class MovementStepper {

    public static MovementStepper build(ConfigParameters param, InputData data) {

        return new MovementStepper(
                BufferedIterator.createWithSharedExecutor(
                        new TimeStepMovementIterator(
                                new JsonMovementReader(data.getMovementDir()),
                                param.getFirstTimeStep()),
                        param.getChunkSize()
                ),
                param.getFirstTimeStep(),
                MovementTimeStepReader.getStochasticIncrement(data.getMovementDir())
        );
    }

    private Iterator<TimeStepMovements> iterator;
    private long currentTimeStep;
    private final int timeStepSize;

    public MovementStepper(Iterator<TimeStepMovements> movementIt, long firstTimeStep, int timeStepSize) {
        iterator = movementIt;
        currentTimeStep = firstTimeStep - 1; //Start one step back, so first 'next' gets first timestep
        this.timeStepSize = timeStepSize;
    }

    public TimeStepMovements getNextTimeStepMovements() {
        TimeStepMovements next = iterator.next();

        currentTimeStep++;
        long cachedTimeStep = next.getTimeStep();
        if (currentTimeStep == cachedTimeStep) {
            return next;
        } else {
            throw new ModelException("Missing a timestep of data - please check movement data");
        }
    }

    public int getTimeStepSize() {
        return timeStepSize;
    }
}
