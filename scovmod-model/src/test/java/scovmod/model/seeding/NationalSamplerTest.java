package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.intSetOf;

public class NationalSamplerTest {


    @Mock
    SpatialGroupMultinomial sgs;

    @Mock
    WithinGroupSampler asbg;


    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int SEED_GROUP_1 = 15;
    private final int SEED_GROUP_2 = 16;
    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final int PERSON_3 = 3;
    private final int PERSON_4 = 4;

    private Int2ObjectMap<IntSet> peopleByLocation;

    private NationalSampler instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        peopleByLocation = new Int2ObjectOpenHashMap<>();
        peopleByLocation.put(LOCATION_1,intSetOf(PERSON_1,PERSON_2));
        peopleByLocation.put(LOCATION_2,intSetOf(PERSON_3,PERSON_4));
    }


    @Test
    public void sampleAnimals(){
        Map<Integer,Integer> multinomialTrialResult = new HashMap<>();
        multinomialTrialResult.put(SEED_GROUP_1, 2);
        multinomialTrialResult.put(SEED_GROUP_2, 1);
        when(sgs.getNumberSeedsPerGroupMap()).thenReturn(multinomialTrialResult);

        when(asbg.samplePeopleFromGroup(2, SEED_GROUP_1, peopleByLocation))
                .thenReturn(intSetOf(PERSON_1,PERSON_2));

        when(asbg.samplePeopleFromGroup(1, SEED_GROUP_2, peopleByLocation))
                .thenReturn(intSetOf(PERSON_4));

        instance = new NationalSampler(sgs, asbg, peopleByLocation);

        assertEquals(
                intSetOf(PERSON_1, PERSON_2, PERSON_4),
                instance.getSampledPeople()
        );
    }
}
