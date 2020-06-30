package scovmod.model.transition.susceptible;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.state.StateQuery;
import scovmod.model.transition.BetaRates;
import scovmod.model.util.TestUtils;

import java.util.Set;

import static scovmod.model.state.infection.InfectionState.*;

public class VisitorPressureTest {

    private static final double RATE = 0.2;
    private static final double TOLERANCE = Double.MIN_VALUE;

    private final int INFECTIOUS_VISITOR_1 = 11;
    private final int INFECTIOUS_VISITOR_2 = 12;
    private final int INFECTIOUS_VISITOR_3 = 13;
    private final int NON_INFECTIOUS_VISITOR_1 = 14;
    private final int NON_INFECTIOUS_VISITOR_2 = 15;

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int LOCATION_3 = 300;

    @Mock
    BetaRates br;

    @Test
    public void mutateVisitorPressureMap() {
        StateQuery sq = Mockito.mock(StateQuery.class);
        Mockito.when(sq.getLocationSize(LOCATION_1)).thenReturn(100);
        Mockito.when(sq.getLocationSize(LOCATION_2)).thenReturn(200);

        VisitorPressure instance = new VisitorPressure(sq, br);

        Mockito.when(sq.getPersonInfectionStatus(INFECTIOUS_VISITOR_1)).thenReturn(MILD_INFECTIOUS_ADULT);
        Mockito.when(sq.getPersonInfectionStatus(INFECTIOUS_VISITOR_2)).thenReturn(MILD_INFECTIOUS_YOUNG);
        Mockito.when(sq.getPersonInfectionStatus(INFECTIOUS_VISITOR_3)).thenReturn(SEVERE_INFECTIOUS_ELDERLY);
        Mockito.when(sq.getPersonInfectionStatus(NON_INFECTIOUS_VISITOR_1)).thenReturn(SUSCEPTIBLE);
        Mockito.when(sq.getPersonInfectionStatus(NON_INFECTIOUS_VISITOR_2)).thenReturn(EXPOSED_ADULT);

        Set<LocationIncomingPersons> timeStepIncomingPersons = TestUtils.setOf(
                new LocationIncomingPersons(LOCATION_1, null, TestUtils.intSetOf(INFECTIOUS_VISITOR_1, INFECTIOUS_VISITOR_2)),
                new LocationIncomingPersons(LOCATION_2, null, TestUtils.intSetOf(INFECTIOUS_VISITOR_3, NON_INFECTIOUS_VISITOR_1)),
                new LocationIncomingPersons(LOCATION_3, null, TestUtils.intSetOf(NON_INFECTIOUS_VISITOR_2))
        );

        Int2ObjectMap<InfectionPressure> pressureMap = new Int2ObjectOpenHashMap<>();
        instance.addVisitorPresssure(timeStepIncomingPersons, pressureMap);

        //fail("Update test to not use Matchers");
//
//        assertThat(
//            pressureMap,
//            allOf(hasSize(2),hasEntry(LOCATION_1, new InfectionPressure (2*RATE / 100,InfectionPressureType.FROM_CATTLE)),hasEntry(LOCATION_2, new InfectionPressure (RATE / 200,InfectionPressureType.FROM_CATTLE))));
    }

//    public static Matcher<Int2ObjectMap> hasSize(final int size) {
//        return new TypeSafeMatcher<Int2ObjectMap>() {
//            @Override
//            public boolean matchesSafely(Int2ObjectMap kvMap) {
//                return kvMap.size() == size;
//            }
//
//            @Override
//            public void describeTo(Description description) {
//                description.appendText(" has ").appendValue(size).appendText(" key/value pairs");
//            }
//        };
//    }
//
//    public static Matcher<Int2ObjectMap> hasEntry(final int key, final InfectionPressure value) {
//        return new TypeSafeMatcher<Int2ObjectMap>() {
//            @Override
//            public boolean matchesSafely(Int2ObjectMap kvMap) {
//                return kvMap.containsKey(key) && Math.abs((int) kvMap.get(key) - value.getPressure()) < TOLERANCE;
//            }
//
//            @Override
//            public void describeTo(Description description) {
//                description.appendText(" has key-value pair: ").appendValue(key).appendText("-").appendValue(value);
//            }
//        };
//    }
}
