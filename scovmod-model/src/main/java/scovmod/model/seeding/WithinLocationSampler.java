package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.SpatialSeedingGroupAttributes;
import scovmod.model.input.config.Parameters;
import scovmod.model.util.math.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WithinLocationSampler {
    private final SpatialSeedingGroupAttributes seedingGroupWeightsReader;
    private final Random rand;
    private Map<Integer, Double> seedingGroupLoadFactorMap;
    private Parameters params;


    public WithinLocationSampler(
            SpatialSeedingGroupAttributes seedingGroupWeightsReader,
            Random rand,
            Parameters params) {
        this.seedingGroupWeightsReader = seedingGroupWeightsReader;
        this.rand = rand;
        seedingGroupLoadFactorMap = seedingGroupWeightsReader.getGroupLoadFactor();
        this.params = params;
    }

    private  int getNumPeopleToSeed(int locationID, int groupId, Int2ObjectMap<IntSet> peopleByLocation){
//        IntSet locationPeople = peopleByLocation.get(locationID);
//        int numAniToSeed = 0;
//        // Possible that farmAnimals is null since
//        // the locationID has come from a listing of all locations across a whole year (spatial group), whereas
//        // the animalsByLocation is just the animals on farms _today_.
//        if (locationPeople != null) {
//            int farmSize = locationPeople.size();
//            double inflationFactor = 0; //params.getInfectiousSensitivity(); // TODO Where to configure this?
//            numAniToSeed = (int) ((seedingGroupLoadFactorMap.get(groupId) / inflationFactor) * farmSize);
//            if (numAniToSeed > farmSize) numAniToSeed = farmSize;
//        }
        return 1;
    }


    public List<Integer> samplePeopleAtLocation(
            int locID,
            int groupId,
            Int2ObjectMap<IntSet> peopleByLocation) {

        IntSet locationPeople = peopleByLocation.get(locID);
        //System.out.println("locationPeople: "+locationPeople);
        int numPeopleToSeed = getNumPeopleToSeed(locID,groupId,peopleByLocation);
        List<Integer> animalIDs = new ArrayList<>();
        if(numPeopleToSeed>0 && locationPeople!=null)
        animalIDs = rand.sampleWithoutReplacement(numPeopleToSeed, locationPeople);

        return animalIDs;
    }
}
