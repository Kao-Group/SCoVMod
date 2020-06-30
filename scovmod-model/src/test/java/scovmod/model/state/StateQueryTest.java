package scovmod.model.state;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.state.cache.dead.DeadAdultLocations;
import scovmod.model.state.cache.dead.DeadElderlyLocations;
import scovmod.model.state.cache.dead.DeadYoungLocations;
import scovmod.model.state.cache.exposed.ExposedAdultLocations;
import scovmod.model.state.cache.exposed.ExposedElderlyLocations;
import scovmod.model.state.cache.exposed.ExposedYoungLocations;
import scovmod.model.state.cache.hospitalised.HospitalisedAdultLocations;
import scovmod.model.state.cache.hospitalised.HospitalisedElderlyLocations;
import scovmod.model.state.cache.hospitalised.HospitalisedYoungLocations;
import scovmod.model.state.cache.mildInfectious.MildInfectiousAdultLocations;
import scovmod.model.state.cache.mildInfectious.MildInfectiousElderlyLocations;
import scovmod.model.state.cache.mildInfectious.MildInfectiousYoungLocations;
import scovmod.model.state.cache.recovered.RecoveredAdultLocations;
import scovmod.model.state.cache.recovered.RecoveredElderlyLocations;
import scovmod.model.state.cache.recovered.RecoveredYoungLocations;
import scovmod.model.state.cache.severeInfectious.SevereInfectiousAdultLocations;
import scovmod.model.state.cache.severeInfectious.SevereInfectiousElderlyLocations;
import scovmod.model.state.cache.severeInfectious.SevereInfectiousYoungLocations;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.state.population.LocalPopulationIndex;
import scovmod.model.util.math.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scovmod.model.state.infection.InfectionState.MILD_INFECTIOUS_ADULT;
import static scovmod.model.state.infection.InfectionState.SUSCEPTIBLE;

public class StateQueryTest {

    private final int LOCATION_1 = 100;

    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    @Mock
    private LocalPopulationIndex lpi;
    @Mock
    private ExposedYoungLocations eyl;
    @Mock
    private ExposedAdultLocations eal;
    @Mock
    private ExposedElderlyLocations eel;
    @Mock
    private DeadYoungLocations dyl;
    @Mock
    private DeadAdultLocations dal;
    @Mock
    private DeadElderlyLocations del;
    @Mock
    private HospitalisedYoungLocations hyl;
    @Mock
    private HospitalisedAdultLocations hal;
    @Mock
    private HospitalisedElderlyLocations hel;
    @Mock
    private RecoveredYoungLocations ryl;
    @Mock
    private RecoveredAdultLocations ral;
    @Mock
    private RecoveredElderlyLocations rel;
    @Mock
    private MildInfectiousYoungLocations miyl;
    @Mock
    private MildInfectiousAdultLocations mial;
    @Mock
    private MildInfectiousElderlyLocations miel;
    @Mock
    private SevereInfectiousYoungLocations siyl;
    @Mock
    private SevereInfectiousAdultLocations sial;
    @Mock
    private SevereInfectiousElderlyLocations siel;
    @Mock
    private Random rnd;
    private StateQuery instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        instance = new StateQuery(lpi,eyl,eal,eel,dyl, dal,del,hyl,hal,hel,ryl,ral,rel,miyl,mial,miel,siyl,sial,siel, rnd);
    }

    @Test
    public void getPersonLocation() {
        int expected = LOCATION_1;
        when(lpi.getPersonLocationId(PERSON_1)).thenReturn(LOCATION_1);
        assertSame(expected, instance.getPersonLocation(PERSON_1));
    }

    @Test
    public void getAllLocationIds() {
        IntSet expected = new IntOpenHashSet();
        when(lpi.getAllLocationIds()).thenReturn(expected);

        assertEquals(expected, instance.getAllActiveLocationIds());
    }

    @Test
    public void getRandomPersonAtLocation() {
        LocalPopulation partition = mock(LocalPopulation.class);
        when(lpi.getLocalPopulation(LOCATION_1)).thenReturn(partition);

        IntSet potentials = new IntOpenHashSet();
        when(partition.getAllInState(SUSCEPTIBLE)).thenReturn(potentials);

        when(rnd.sampleOne(same(potentials))).thenReturn(PERSON_1);

        assertEquals(PERSON_1, instance.getRandomPersonAtLocation(LOCATION_1, SUSCEPTIBLE));
    }

    @Test
    public void getExposedYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(eyl.getExposedYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getExposedYoungLocations());
    }

    @Test
    public void getExposedAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(eal.getExposedAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getExposedAdultLocations());
    }

    @Test
    public void getExposedElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(eel.getExposedElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getExposedElderlyLocations());
    }

    @Test
    public void getDeadYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(dyl.getDeadYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getDeadYoungLocations());
    }

    @Test
    public void getDeadAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(dal.getDeadAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getDeadAdultLocations());
    }

    @Test
    public void getDeadElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(del.getDeadElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getDeadElderlyLocations());
    }

    @Test
    public void getHospitalisedYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(hyl.getHospitalisedYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getHospitalisedYoungLocations());
    }

    @Test
    public void getHospitalisedAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(hal.getHospitaliseAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getHospitalisedAdultLocations());
    }

    @Test
    public void getHospitalisedElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(hel.getHospitalisedElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getHospitalisedElderlyLocations());
    }

    @Test
    public void getRecoveredYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(ryl.getRecoveredYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getRecoveredYoungLocations());
    }

    @Test
    public void getRecoveredAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(ral.getRecoveredAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getRecoveredAdultLocations());
    }

    @Test
    public void getRecoveredElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(rel.getRecoveredElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getRecoveredElderlyLocations());
    }

    @Test
    public void getMildInfectiousYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(miyl.getMildInfectiousYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getMildInfectiousYoungLocations());
    }

    @Test
    public void getMildInfectiousAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(mial.getMildInfectiousAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getMildInfectiousAdultLocations());
    }

    @Test
    public void getMildInfectiousElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(miel.getMildInfectiousElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getMildInfectiousElderlyLocations());
    }

    @Test
    public void getSevereInfectiousYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(siyl.getSevereInfectiousYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getSevereInfectiousYoungLocations());
    }

    @Test
    public void getSevereInfectiousAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(sial.getSevereInfectiousAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getSevereInfectiousAdultLocations());
    }

    @Test
    public void getSevereInfectiousElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(siel.getSevereInfectiousElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getSevereInfectiousElderlyLocations());
    }

    @Test
    public void getCopyOfLocalPopulation() {
        LocalPopulation localPop = mock(LocalPopulation.class);
        when(lpi.getLocalPopulation(LOCATION_1)).thenReturn(localPop);

        assertSame(localPop, instance.getCopyOfLocalPopulation(LOCATION_1));
    }

    @Test
    public void getPersonInfectionStatus() {
        when(lpi.getPersonInfectionStatus(PERSON_1)).thenReturn(SUSCEPTIBLE);
        when(lpi.getPersonInfectionStatus(PERSON_2)).thenReturn(MILD_INFECTIOUS_ADULT);

        assertEquals(SUSCEPTIBLE, instance.getPersonInfectionStatus(PERSON_1));
        assertEquals(MILD_INFECTIOUS_ADULT, instance.getPersonInfectionStatus(PERSON_2));
    }

    @Test
    public void getLocationSize() {
        LocalPopulation localPop = mock(LocalPopulation.class);
        when(localPop.getSize()).thenReturn(200);
        when(lpi.getLocalPopulation(LOCATION_1)).thenReturn(localPop);

        assertEquals(200, instance.getLocationSize(LOCATION_1));
    }
}
