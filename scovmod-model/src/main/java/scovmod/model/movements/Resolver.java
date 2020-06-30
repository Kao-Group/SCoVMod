package scovmod.model.movements;

import scovmod.model.state.StateQuery;

import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.state.infection.InfectionState;

import java.util.HashSet;
import java.util.Set;

public class Resolver {

    private final StateQuery sq;
    private static int count = 0;

    public Resolver(StateQuery sq) {
        this.sq = sq;
    }

    public Set<ResolvedMovement> apply(LocationIncomingPersons incomingLocMovs,
                                       IntSet allExposedLocations,
                                       IntSet allMildInfectiousLocations,
                                       IntSet allSevereInfectiousLocations) {
        int destinationID = incomingLocMovs.getLocationId();
        boolean destinationInfected = false;
        if(allExposedLocations.contains(destinationID)
                || allMildInfectiousLocations.contains(destinationID)
                || allSevereInfectiousLocations.contains(destinationID)) destinationInfected = true;

        IntSet incomingPersons = incomingLocMovs.getNewPersonIds();
        Set<ResolvedMovement> resolvedMovements = new HashSet<>();
        for (int personID : incomingPersons) {
            int sourceID = sq.getPersonLocation(personID);
            if(sourceID==destinationID) continue; //Have missed move to work so ignore move to self?
            InfectionState state = sq.getPersonInfectionStatus(personID);
            if ((state == InfectionState.DEAD_ADULT)
                    || (state == InfectionState.DEAD_YOUNG)
                    || (state == InfectionState.DEAD_ELDERLY)
                    || (state == InfectionState.HOSPITALISED_YOUNG)
                    || (state == InfectionState.HOSPITALISED_ELDERLY)
                    || (state == InfectionState.HOSPITALISED_ADULT)
                    || (state == InfectionState.SEVERE_INFECTIOUS_YOUNG)
                    || (state == InfectionState.SEVERE_INFECTIOUS_ADULT)
                    || (state == InfectionState.SEVERE_INFECTIOUS_ELDERLY)) {
                continue; //No matter what ignore moves for these people - in real life they obviously would not commute
            } else {
                //This is an optimisation to speed up processing of movements - only move those who could spread covid
                if(destinationInfected) {
                    resolvedMovements.add(
                            new ResolvedMovement(
                                    personID,
                                    sourceID,
                                    destinationID,
                                    state
                            ));
                } else if ((state == InfectionState.EXPOSED_YOUNG)
                        || (state == InfectionState.EXPOSED_ADULT)
                        || (state == InfectionState.EXPOSED_ELDERLY)
                        || (state == InfectionState.MILD_INFECTIOUS_YOUNG)
                        || (state == InfectionState.MILD_INFECTIOUS_ADULT)
                        || (state == InfectionState.MILD_INFECTIOUS_ELDERLY)){
                        resolvedMovements.add(
                                new ResolvedMovement(
                                        personID,
                                        sourceID,
                                        destinationID,
                                        state
                                ));
                } else {
                    continue;
                }
            }
        }
        return resolvedMovements;

    }
}
