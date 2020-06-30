//package scovmod.model.input.seeding;
//
//import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
//import it.unimi.dsi.fastutil.ints.IntSet;
//import scovmod.model.input.StartLocationsAndAgeClasses;
//import scovmod.model.seeding.NationalSampler;
//import scovmod.model.seeding.NationalSamplerFactory;
//import scovmod.model.state.infection.InfectionState;
//
//import static scovmod.model.state.infection.InfectionState.*;
//
//public class SeedManager {
//
//    private StartLocationsAndAgeClasses startLocations;
//    //private StatisticsCollector stats;
//    //private IntSet seeds;
//    private Int2ObjectMap<AgeClass> peopleAgeClasses;
//    private NationalSamplerFactory nsf;
//
//    public SeedManager(
//            StartLocationsAndAgeClasses asl,
//            /*InfectedSeeds infectedSeeds, */
//            NationalSamplerFactory nsf) {
//        startLocations = asl;
//        //seeds = infectedSeeds.getInfectedSeeds();
//        peopleAgeClasses = asl.getPeopleAgeClasses();
//        this.nsf = nsf;
//    }
//
//    public void seedPeopleInfections(
//            Int2ObjectMap<InfectionState> personStates,
//            Int2ObjectMap<IntSet> peopleByLocation) {
//        // Make all people susceptible to start - apart from seeds read in.
//        IntSet personKeys = startLocations.getLocationsByPeopleId().keySet();
//        // Make all people susceptible to start
//        for (int personID : personKeys) {
//            personStates.put(personID, InfectionState.SUSCEPTIBLE);
//        }
//        NationalSampler ns = nsf.build(peopleByLocation);
//        for (int personID : ns.getSampledPeople()) {
//            AgeClass ageClass = peopleAgeClasses.get(personID);
//            //Get what age group the person is in
//            switch (ageClass) {
//                case YOUNG:
//                    personStates.put(personID, EXPOSED_YOUNG);
//                    break;
//                case ADULT:
//                    personStates.put(personID, EXPOSED_ADULT);
//                    break;
//                case ELDERLY:
//                    personStates.put(personID, EXPOSED_ELDERLY);
//                    break;
//                default:
//                    throw new UnsupportedOperationException("Age class not known- for recovered event");
//            }
//        }
//    }
//}
