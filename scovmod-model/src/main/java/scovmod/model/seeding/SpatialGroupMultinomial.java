package scovmod.model.seeding;

import scovmod.model.input.SpatialSeedingGroupAttributes;
import scovmod.model.input.config.Parameters;
import scovmod.model.util.math.Random;

import java.util.Map;


public class SpatialGroupMultinomial {
    private Random rand;
    private SpatialSeedingGroupAttributes seedingGroupWeightsReader;
    private int numberToSeed;
    private Map<Integer,Double> seedingGroupWeightsMap;


    public SpatialGroupMultinomial(
            Parameters params,
            Random rand,
            SpatialSeedingGroupAttributes seedingGroupWeightsReader) {

        this.rand = rand;
        this.seedingGroupWeightsReader = seedingGroupWeightsReader;
        this.numberToSeed = (int) params.getNumSeeds();
        seedingGroupWeightsMap = seedingGroupWeightsReader.getGroupWeightsMap();

    }

    public Map<Integer,Integer> getNumberSeedsPerGroupMap() {
        return rand.nextMultinomialTrials(seedingGroupWeightsMap,numberToSeed);
    }
}
