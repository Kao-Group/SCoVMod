package scovmod.model.setup;

import scovmod.model.input.StartLocationsAndAgeClasses;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class InitialLocationsTest {

    private final int ANI_1 = 1;
    private final int FARM_1 = 100;
    private final int ANI_2 = 2;
    private final int FARM_2 = 200;
    private final int ANI_3 = 3;
    private final int FARM_3 = 300;

    @Mock
    private StartLocationsAndAgeClasses startLocations;

    private InitialLocations instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        instance = new InitialLocations(startLocations);
    }

    @Test
    public void setupInitialLocations() {
        Int2ObjectMap<IntSet> personsByLocation = new Int2ObjectOpenHashMap<>();
        Int2IntMap startLocationMap = new Int2IntOpenHashMap();
        startLocationMap.put(ANI_1, FARM_1);
        startLocationMap.put(ANI_2, FARM_2);
        startLocationMap.put(ANI_3, FARM_3);
        
        when(startLocations.getLocationsByPeopleId()).thenReturn(startLocationMap);
        instance.setup(personsByLocation);
        assertEquals(3,personsByLocation.size());
    }
    
    @Test
    public void exceptionIfMapPassedInIsNotEmpty(){
        Int2ObjectMap<IntSet> personsByLocation = new Int2ObjectOpenHashMap<>();
        personsByLocation.put(ANI_1, null);
        
        try{
            instance.setup(personsByLocation);
            fail("Expected exception");
        } catch(UnsupportedOperationException e){}
    }
}
