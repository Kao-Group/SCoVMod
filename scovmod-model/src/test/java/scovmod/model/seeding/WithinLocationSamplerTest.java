package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.listOf;

public class WithinLocationSamplerTest {

    private static final Double INF_SE = 0.6;
    private static final Double GROUP_1_LOAD_FACTOR = 0.9;
    private static final Double GROUP_2_LOAD_FACTOR = 0.3;
    private static final int LOCATION_1 = 100;
    private static final int LOCATION_2 = 200;
    private static final int PERSON_1 = 1;
    private static final int PERSON_2 = 2;
    private static final int PERSON_3 = 3;
    private static final int PERSON_4 = 4;
    private static final int PERSON_5 = 5;
    private static final int SEED_GROUP_1 = 15;
    private static final int SEED_GROUP_2 = 16;

    private Int2ObjectMap<IntSet> animalsByLocation;

    @Mock
    SpatialSeedingGroupAttributes seedingGroupWeightsReader;

    @Mock
    Random rand;

    private WithinLocationSampler instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Map<Integer,Double> seedingGroupLoadFactorMap = new HashMap<>();
        seedingGroupLoadFactorMap.put(SEED_GROUP_1, GROUP_1_LOAD_FACTOR);
        seedingGroupLoadFactorMap.put(SEED_GROUP_2, GROUP_2_LOAD_FACTOR);
        when(seedingGroupWeightsReader.getGroupLoadFactor()).thenReturn(seedingGroupLoadFactorMap);

        Parameters params = mock(Parameters.class);
       // when(params.getInfectiousSensitivity()).thenReturn(INF_SE);

        animalsByLocation = new Int2ObjectOpenHashMap<>();
        animalsByLocation.put(LOCATION_1,intSetOf(PERSON_1,PERSON_2));
        animalsByLocation.put(LOCATION_2,intSetOf(PERSON_3,PERSON_4, PERSON_5));

        instance = new WithinLocationSampler(seedingGroupWeightsReader, rand, params);
    }

//    @Test
//    public void attemptToSampleMoreAniThanAtLocation(){
//        int expectedNumAniFarm1 = 2;//(int)(animalsByLocation.get(LOCATION_1).size() * GROUP_1_LOAD_FACTOR / INF_SE);
//
//        when(rand.sampleWithoutReplacement(
//                expectedNumAniFarm1,
//                intSetOf(PERSON_1,PERSON_2))
//        ).thenReturn(listOf(PERSON_1,PERSON_2));
//
//        assertEquals(
//                listOf(PERSON_1,PERSON_2),
//                instance.samplePeopleAtLocation(LOCATION_1, SEED_GROUP_1, animalsByLocation));
//    }

    @Test
    public void sampleFarmInGroup2(){
        int expectedNumAniFarm2 = 1; //(int)(animalsByLocation.get(LOCATION_2).size() * GROUP_2_LOAD_FACTOR / INF_SE);

        when(rand.sampleWithoutReplacement(
                expectedNumAniFarm2,
                intSetOf(PERSON_3, PERSON_4, PERSON_5))
        ).thenReturn(listOf(PERSON_4));

        assertEquals(
                listOf(PERSON_4),
                instance.samplePeopleAtLocation(LOCATION_2, SEED_GROUP_2, animalsByLocation));

    }
}
