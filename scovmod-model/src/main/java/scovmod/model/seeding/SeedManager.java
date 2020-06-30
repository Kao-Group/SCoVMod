package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.util.math.Random;

import java.util.ArrayList;
import java.util.List;

import static scovmod.model.state.infection.InfectionState.*;

public class SeedManager {

    private StartLocationsAndAgeClasses startLocations;
    private StatisticsCollector stats;
    private NationalSamplerFactory nsf;
    private Random rand;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;

    public SeedManager(
            StartLocationsAndAgeClasses sl,
            NationalSamplerFactory nsf,
            Random rand) {
        startLocations = sl;
        this.nsf = nsf;
        this.rand = rand;
        peopleAgeClasses = sl.getPeopleAgeClasses();
    }

    public void seedPeopleInfections(
            Int2ObjectMap<InfectionState> personStates,
            Int2ObjectMap<IntSet> peopleByLocation
    ) {
        // Make all people susceptible to start
        IntSet animalKeys = startLocations.getLocationsByPeopleId().keySet();
        for (int animalID : animalKeys) {
            personStates.put(animalID, InfectionState.SUSCEPTIBLE);
        }
        NationalSampler ns = nsf.build(peopleByLocation);
        IntSet sampledSeeds = ns.getSampledPeople();
        for (int personID : sampledSeeds) {
            AgeClass ageClass = peopleAgeClasses.get(personID);
            //Get what age group the person is in
            switch (ageClass) {
                case YOUNG:
                    personStates.put(personID, EXPOSED_YOUNG);
                    break;
                case ADULT:
                    personStates.put(personID, EXPOSED_ADULT);
                    break;
                case ELDERLY:
                    personStates.put(personID, EXPOSED_ELDERLY);
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for recovered event");
            }
        }
    }
}
