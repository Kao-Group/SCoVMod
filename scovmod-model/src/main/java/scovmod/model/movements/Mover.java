package scovmod.model.movements;

import scovmod.model.output.StatisticsCollector;

import java.util.Set;

public class Mover {

    private final StatisticsCollector stats;
    private MovementHandler mh;

    public Mover(
            MovementHandler mh,
            StatisticsCollector stats) {
        this.mh = mh;
        this.stats = stats;
    }

    public void apply(Set<ResolvedMovement> movements) {
        for (ResolvedMovement movement : movements) {
            mh.apply(movement);
        }
    }
}
