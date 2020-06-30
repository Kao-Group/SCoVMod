package scovmod.model.transition.susceptible;

//import org.apache.commons.lang3.builder.ToStringExclude;
//import org.junit.Test;
//
//import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
//
//public class LocalPersonPressureTest {
//    @Test
//    public void todo() {
//        fail("Write new tests, without bonkers custom matchers, or delete");
//    }
//}


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import scovmod.model.input.config.Parameters;
import scovmod.model.state.InfectiousProportion;
import scovmod.model.state.StateQuery;

import static junit.framework.TestCase.assertEquals;
import static scovmod.model.util.TestUtils.intSetOf;
import org.junit.Test;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.transition.BetaRates;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocalPersonPressureTest {

    private static final double MI_RATE = 0.2;
    private static final double SI_RATE = 0.5;

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;

    private final double IP_MI_LOCATION_1 = 0.3;
    private final double IP_MI_LOCATION_2 = 0.4;

    private final double IP_SI_LOCATION_1 = 0.4;
    private final double IP_SI_LOCATION_2 = 0.5;

    @Test
    public void mutatePersonPressureMap() {
        StateQuery sq = mock(StateQuery.class);
        InfectiousProportion ip = mock(InfectiousProportion.class);
        BetaRates br = mock(BetaRates.class);
        Parameters params = mock(Parameters.class);
        when(br.getBeta(InfectionState.MILD_INFECTIOUS_ADULT)).thenReturn(MI_RATE);
        when(br.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY)).thenReturn(SI_RATE);
        when(ip.inCommunity(LOCATION_1, InfectionState.MILD_INFECTIOUS_ADULT)).thenReturn(IP_MI_LOCATION_1);
        when(ip.inCommunity(LOCATION_2,InfectionState.MILD_INFECTIOUS_ADULT)).thenReturn(IP_MI_LOCATION_2);
        when(ip.inCommunity(LOCATION_1, InfectionState.SEVERE_INFECTIOUS_ELDERLY)).thenReturn(IP_SI_LOCATION_1);
        when(ip.inCommunity(LOCATION_2,InfectionState.SEVERE_INFECTIOUS_ELDERLY)).thenReturn(IP_SI_LOCATION_2);

        LocalPersonPressure instance = new LocalPersonPressure(sq, params, ip, br);

        when(sq.getMildInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousAdultLocations()).thenReturn(intSetOf(LOCATION_1, LOCATION_2));
        when(sq.getMildInfectiousElderlyLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousElderlyLocations()).thenReturn(intSetOf(LOCATION_1, LOCATION_2));

        Int2ObjectMap pressureMap = new Int2ObjectOpenHashMap();
        instance.addLocalPressure(pressureMap);

        Int2ObjectMap expectedMap = new Int2ObjectOpenHashMap();

        expectedMap.put(LOCATION_1,new InfectionPressure(0,MI_RATE*IP_MI_LOCATION_1+SI_RATE*IP_SI_LOCATION_1));
        expectedMap.put(LOCATION_2, new InfectionPressure(0,MI_RATE*IP_MI_LOCATION_2+SI_RATE*IP_SI_LOCATION_2));
        assertEquals(pressureMap,expectedMap);
        InfectionPressure loc1PersonPressure = (InfectionPressure) expectedMap.get(LOCATION_1);
        assertEquals(MI_RATE*IP_MI_LOCATION_1+SI_RATE*IP_SI_LOCATION_1,loc1PersonPressure.getPersonPressure());
        InfectionPressure loc2PersonPressure = (InfectionPressure) expectedMap.get(LOCATION_2);
        assertEquals(MI_RATE*IP_MI_LOCATION_2+SI_RATE*IP_SI_LOCATION_2,loc2PersonPressure.getPersonPressure());
    }
}
