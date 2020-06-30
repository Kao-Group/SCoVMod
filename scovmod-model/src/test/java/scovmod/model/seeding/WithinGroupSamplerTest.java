package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.output.HealthBoardLookup;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.listOf;

public class WithinGroupSamplerTest {

    private static final int SAMPLE_2_PERSON = 2;
    private static final int SAMPLE_4_PERSON = 4;
    private static final int SAMPLE_1_PERSON = 1;
    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int LOCATION_3 = 300;
    private final int SEED_GROUP_1 = 15;
    private final int SEED_GROUP_2 = 16;
    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final int PERSON_3 = 3;
    private final int PERSON_4 = 4;
    private final int PERSON_5 = 5;


    private Int2ObjectMap<IntSet> animalsByLocation;

    @Mock
    LocationShuffler fs;

    @Mock
    WithinLocationSampler as;


    private WithinGroupSampler instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        instance = new WithinGroupSampler(fs,as);

        when(fs.shuffleLocationsInGroup(SEED_GROUP_1)).thenReturn(listOf(LOCATION_1));
        when(fs.shuffleLocationsInGroup(SEED_GROUP_2)).thenReturn(listOf(LOCATION_2, LOCATION_3));

        animalsByLocation = new Int2ObjectOpenHashMap<>();
        animalsByLocation.put(LOCATION_1,intSetOf(PERSON_1,PERSON_2));
        animalsByLocation.put(LOCATION_2,intSetOf(PERSON_3,PERSON_4));
        animalsByLocation.put(LOCATION_3,intSetOf(PERSON_5));

        when(as.samplePeopleAtLocation(LOCATION_1,SEED_GROUP_1,animalsByLocation))
                .thenReturn(listOf(PERSON_1,PERSON_2));

        when(as.samplePeopleAtLocation(LOCATION_2,SEED_GROUP_2,animalsByLocation))
                .thenReturn(listOf(PERSON_4));

        when(as.samplePeopleAtLocation(LOCATION_3,SEED_GROUP_2,animalsByLocation))
                .thenReturn(listOf(PERSON_5));

    }

    @Test
    public void sampleAnimalsFromGroup_targetReached(){
        assertEquals(intSetOf(PERSON_1,PERSON_2),instance.samplePeopleFromGroup(SAMPLE_2_PERSON, SEED_GROUP_1, animalsByLocation));
        assertEquals(intSetOf(PERSON_4),instance.samplePeopleFromGroup(SAMPLE_1_PERSON, SEED_GROUP_2, animalsByLocation));
        assertEquals(intSetOf(PERSON_4, PERSON_5),instance.samplePeopleFromGroup(SAMPLE_2_PERSON, SEED_GROUP_2, animalsByLocation));
    }

    @Test
    public void sampleAnimalsFromGroup_targetNotReached(){
        assertEquals(intSetOf(PERSON_1,PERSON_2),instance.samplePeopleFromGroup(SAMPLE_4_PERSON, SEED_GROUP_1, animalsByLocation));
    }
}
