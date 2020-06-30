package scovmod.model.seeding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.SpatialSeedingGroupAttributes;
import scovmod.model.input.config.Parameters;
import scovmod.model.util.math.Random;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SpatialGroupMultinomialTest {

    @Mock
    SpatialSeedingGroupAttributes seedingGroupWeightsReader;

    @Mock
    Parameters params;

    @Mock
    Random rand;

    private final int SEED_GROUP_1 = 15;
    private final int SEED_GROUP_2 = 16;

    private SpatialGroupMultinomial instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void sampleSeedsPerGroup(){
        Map<Integer,Double> seedingGroupWeight = new HashMap<>();
        seedingGroupWeight.put(SEED_GROUP_1, 0.1);
        seedingGroupWeight.put(SEED_GROUP_2, 0.2);
        when(seedingGroupWeightsReader.getGroupWeightsMap()).thenReturn(seedingGroupWeight);

        when(params.getNumSeeds()).thenReturn(3.0);
        Map<Integer,Integer> multinomialTrialResult = new HashMap<>();
        multinomialTrialResult.put(SEED_GROUP_1, 1);
        multinomialTrialResult.put(SEED_GROUP_2, 2);
        when(rand.nextMultinomialTrials(seedingGroupWeight,3)).thenReturn(multinomialTrialResult);

        instance = new SpatialGroupMultinomial(params, rand, seedingGroupWeightsReader);

        assertEquals(
                multinomialTrialResult,
                instance.getNumberSeedsPerGroupMap());
    }
}
