package scovmod.model.transition;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.DeprivationIndexPerHealthBoard;
import scovmod.model.input.config.Parameters;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.StateQuery;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DeathRatesTest {

    private static final int OA_AREA_1 = 20;
    private static final int LA_AYRSHIRE = 15;
    private static final int PERSON_1 = 1;
    private static final double FITTED_HEALTH_MODIFIER = 0.5;
    private static final double AVERAGE_HEALTH_INDEX = 17;
    private static final double TOL = 1e-10;

    @Mock
    Parameters params;
    @Mock
    StateQuery sq;
    @Mock
    DeprivationIndexPerHealthBoard diph;
    @Mock
    HealthBoardLookup hbl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Int2DoubleMap deprivationIndex = new Int2DoubleOpenHashMap();
        deprivationIndex.put(15,28.28449377);
        deprivationIndex.put(16,15.72185475);
        deprivationIndex.put(17,16.80101126);
        deprivationIndex.put(19,17.70649213);
        deprivationIndex.put(20,10.84380362);
        deprivationIndex.put(22,14.53387911);
        deprivationIndex.put(24,16.29550089);
        deprivationIndex.put(25,10.59653685);
        deprivationIndex.put(26,10.86340133);
        deprivationIndex.put(28,13.72908302);
        deprivationIndex.put(29,18.76111476);
        deprivationIndex.put(30,18.28639984);
        deprivationIndex.put(31,31.76163256);
        deprivationIndex.put(32,26.56907092);
        when(diph.getDeprivationIndexPerHB()).thenReturn(deprivationIndex);
        when(params.getAverageHealthIndexPerCouncilArea()).thenReturn(AVERAGE_HEALTH_INDEX);
        when(hbl.getCouncilAreaFromOA(OA_AREA_1)).thenReturn(LA_AYRSHIRE);
        //when(hbl.getHealthBoardFromOA(OA_AREA_1)).thenReturn(LA_AYRSHIRE);
        when(sq.getPersonLocation(PERSON_1)).thenReturn(OA_AREA_1);
        when(params.getHealthModifier()).thenReturn(FITTED_HEALTH_MODIFIER);
    }

    @Test
    public void getModificationToDeathRateInAyrshireAndArran() {
        DeathRates instance = new DeathRates(params, diph, sq, hbl);
        double expectedValue = (1+FITTED_HEALTH_MODIFIER*(28.28449377-AVERAGE_HEALTH_INDEX));
        assertEquals(expectedValue, instance.getDeathRateModifierBasedOnHealthIndex(PERSON_1), TOL);
    }
}
