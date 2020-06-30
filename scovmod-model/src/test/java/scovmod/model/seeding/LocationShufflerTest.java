package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.util.math.Random;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.listOf;

public class LocationShufflerTest {

    private static final Integer LOCATION_1 = 100;
    private static final Integer LOCATION_2 = 200;
    private static final Integer LOCATION_3 = 300;

    @Mock
    HealthBoardLookup hbl;

    @Mock
    Random rand;

    private final int SEED_GROUP_1 = 15;
    private final int SEED_GROUP_2 = 16;

    private LocationShuffler instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void sampleSeedsPerGroup(){
        Int2ObjectMap<List<Integer>> oasPerGroup = new Int2ObjectOpenHashMap<>();
        oasPerGroup.put(SEED_GROUP_1, listOf(LOCATION_1));
        oasPerGroup.put(SEED_GROUP_2, listOf(LOCATION_2, LOCATION_3));
        when(hbl.getOasByLA()).thenReturn(oasPerGroup);
        //when(hbl.getOasByHB()).thenReturn(oasPerGroup);

        instance = new LocationShuffler(hbl, rand);

        when(rand.shuffleList(listOf(LOCATION_1))).thenReturn(listOf(LOCATION_1));
        assertEquals(
                listOf(LOCATION_1),
                instance.shuffleLocationsInGroup(SEED_GROUP_1));

        when(rand.shuffleList(listOf(LOCATION_2, LOCATION_3))).thenReturn(listOf(LOCATION_3, LOCATION_2));
        assertEquals(
                listOf(LOCATION_3, LOCATION_2),
                instance.shuffleLocationsInGroup(SEED_GROUP_2));
    }
}
