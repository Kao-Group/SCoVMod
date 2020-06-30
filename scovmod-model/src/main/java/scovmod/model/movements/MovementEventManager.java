package scovmod.model.movements;

import it.unimi.dsi.fastutil.ints.IntSet;

import java.util.Set;

public class MovementEventManager {

    private final Resolver resolver;
    private final Mover mover;

    public MovementEventManager(Resolver resolve, Mover mover) {
        this.resolver = resolve;
        this.mover = mover;
    }

    public void doMovements(Set<LocationIncomingPersons> locationIncomingPeople,
                            IntSet allExposedLocations,
                            IntSet allMildInfectiousLocations,
                            IntSet allSevereInfectiousLocations) {
        for(LocationIncomingPersons incoming :locationIncomingPeople) {
            mover.apply((resolver.apply(incoming,allExposedLocations,allMildInfectiousLocations,allSevereInfectiousLocations)));
        }
    }
}
