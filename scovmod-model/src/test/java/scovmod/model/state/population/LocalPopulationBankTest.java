package scovmod.model.state.population;

import static org.junit.Assert.*;
import org.junit.Before;

import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class LocalPopulationBankTest {

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void alwaysReturnsTheSamePartitionForGivenFarm() {
        LocalPopulationBank instance = new LocalPopulationBank();
        
        LocalPopulation firstCallLoc1 = instance.getPopulationAtLocation(LOCATION_1);
        LocalPopulation firstCallLoc2 = instance.getPopulationAtLocation(LOCATION_2);

        assertFalse(firstCallLoc1 == firstCallLoc2);

        assertTrue(firstCallLoc1 == instance.getPopulationAtLocation(LOCATION_1));
        assertTrue(firstCallLoc2 == instance.getPopulationAtLocation(LOCATION_2));
    }

    @Test
    public void verifyLocationIsKnownIfCreatingALocalPopulation() {
        LocalPopulationBank instance = new LocalPopulationBank();
        instance.getPopulationAtLocation(LOCATION_1);

        instance = new LocalPopulationBank();
        instance.getPopulationAtLocation(LOCATION_1);

        instance = new LocalPopulationBank();
        try{
            instance.getPopulationAtLocation(LOCATION_1);
            fail("Expected exception");
        }catch(AssertionError e){};
    }
    
    @Test
    public void dontVerifyLocationKnownIfAlreadyCreated(){
        LocalPopulationBank instance = new LocalPopulationBank();

        instance.getPopulationAtLocation(LOCATION_1);

        instance.getPopulationAtLocation(LOCATION_1);
    }

}
