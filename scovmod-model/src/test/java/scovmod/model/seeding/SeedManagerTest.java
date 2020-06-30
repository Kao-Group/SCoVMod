package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.util.math.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.listOf;

public class SeedManagerTest {

    @Mock
    StartLocationsAndAgeClasses sl;

    @Mock
    NationalSamplerFactory asf;

    @Mock
    NationalSampler as;

    @Mock
    Random rand;

    @Mock
    StatisticsCollector stats;

    @Mock
    Int2ObjectOpenHashMap<IntSet> peopleByLocationMap;

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int LOCATION_3 = 300;
    private final int LOCATION_4 = 400;
    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final int PERSON_3 = 3;
    private final int PERSON_4 = 4;
    private final int PERSON_5 = 5;
    private final int PERSON_6 = 6;
    private final int PERSON_7 = 7;
    private final int PERSON_8 = 8;

    private SeedManager instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Int2IntMap startLocationMap = new Int2IntOpenHashMap();
        startLocationMap.put(PERSON_1, LOCATION_1);
        startLocationMap.put(PERSON_2, LOCATION_1);
        startLocationMap.put(PERSON_3, LOCATION_2);
        startLocationMap.put(PERSON_4, LOCATION_2);
        startLocationMap.put(PERSON_5, LOCATION_3);
        startLocationMap.put(PERSON_6, LOCATION_3);
        startLocationMap.put(PERSON_7, LOCATION_4);
        startLocationMap.put(PERSON_8, LOCATION_4);

        when(sl.getLocationsByPeopleId()).thenReturn(startLocationMap);
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_2, AgeClass.ADULT);
        peopleAgeClasses.put(PERSON_3, AgeClass.ELDERLY);
        peopleAgeClasses.put(PERSON_4, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_5, AgeClass.ADULT);
        peopleAgeClasses.put(PERSON_6, AgeClass.ELDERLY);
        peopleAgeClasses.put(PERSON_7, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_8, AgeClass.ELDERLY);

        when(sl.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);


        instance = new SeedManager(sl, asf, rand);

    }

    @Test
    public void seedAnimalInfections() {

        Int2ObjectMap<IntSet> peopleByLocation = peopleByLocationMap;

        when(asf.build(peopleByLocationMap)).thenReturn(as);

        when(as.getSampledPeople()).thenReturn(intSetOf(PERSON_1,PERSON_2,PERSON_3));

        Int2ObjectMap<InfectionState> resultAnimalStates = new Int2ObjectOpenHashMap<>();

        instance.seedPeopleInfections(resultAnimalStates, peopleByLocation);

        assertEquals(InfectionState.EXPOSED_YOUNG, resultAnimalStates.get(PERSON_1));
        assertEquals(InfectionState.EXPOSED_ADULT, resultAnimalStates.get(PERSON_2));
        assertEquals(InfectionState.EXPOSED_ELDERLY, resultAnimalStates.get(PERSON_3));
        assertEquals(InfectionState.SUSCEPTIBLE, resultAnimalStates.get(PERSON_4));
        assertEquals(InfectionState.SUSCEPTIBLE, resultAnimalStates.get(PERSON_5));
        assertEquals(InfectionState.SUSCEPTIBLE, resultAnimalStates.get(PERSON_6));
        assertEquals(InfectionState.SUSCEPTIBLE, resultAnimalStates.get(PERSON_7));
        assertEquals(InfectionState.SUSCEPTIBLE, resultAnimalStates.get(PERSON_8));
        assertEquals(8, resultAnimalStates.size());
    }

}
