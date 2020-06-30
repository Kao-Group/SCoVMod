package scovmod.model.state.population;

import it.unimi.dsi.fastutil.ints.IntSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.state.delta.CompartmentRemoveDelta;
import scovmod.model.state.delta.CompartmentSetDelta;
import scovmod.model.state.delta.RemoveDelta;
import scovmod.model.state.delta.SetDelta;
import scovmod.model.state.infection.InfectionState;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scovmod.model.state.infection.InfectionState.*;
import static scovmod.model.util.TestUtils.setOf;

public class LocalPopulationIndexTest {

    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final int PERSON_3 = 3;
    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;

    private LocalPopulationIndex instance;
    @Mock
    private LocalPopulationBank bank;
    @Mock
    private LocalPopulation population_1, population_2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        instance = new LocalPopulationIndex(bank);
    }

    @Test
    public void getPersonLocation() {
        instance = new LocalPopulationIndex(new LocalPopulationBank());

        instance.set(PERSON_1, MILD_INFECTIOUS_ADULT, LOCATION_1);

        assertEquals(LOCATION_1, instance.getPersonLocationId(PERSON_1));
    }

    @Test
    public void getPersonInfectionState() {
        instance = new LocalPopulationIndex(new LocalPopulationBank());

        instance.set(PERSON_1, MILD_INFECTIOUS_ADULT, LOCATION_1);

        assertEquals(MILD_INFECTIOUS_ADULT, instance.getPersonInfectionStatus(PERSON_1));
    }

    @Test
    public void settingAndRemoving() {
        //Setting
        CompartmentSetDelta onDelta2 = new CompartmentSetDelta(true, PERSON_2, SEVERE_INFECTIOUS_ADULT);
        CompartmentSetDelta onDelta3 = new CompartmentSetDelta(false, PERSON_3, SUSCEPTIBLE);

        when(bank.getPopulationAtLocation(LOCATION_1)).thenReturn(population_1);
        when(bank.getPopulationAtLocation(LOCATION_2)).thenReturn(population_2);

        when(population_1.setStateByPersonId(PERSON_2, SEVERE_INFECTIOUS_ADULT)).thenReturn(onDelta2);
        when(population_2.setStateByPersonId(PERSON_3, SUSCEPTIBLE)).thenReturn(onDelta3);

        assertEquals(new SetDelta(LOCATION_1, onDelta2), instance.set(PERSON_2, SEVERE_INFECTIOUS_ADULT, LOCATION_1));
        assertEquals(new SetDelta(LOCATION_2, onDelta3), instance.set(PERSON_3, SUSCEPTIBLE, LOCATION_2));

        //Removing
        CompartmentRemoveDelta offDelta1 = new CompartmentRemoveDelta(false, PERSON_1, EXPOSED_YOUNG);
        CompartmentRemoveDelta offDelta3 = new CompartmentRemoveDelta(true, PERSON_2, SUSCEPTIBLE);

        when(population_1.remove(PERSON_1)).thenReturn(offDelta1);
        when(population_2.remove(PERSON_3)).thenReturn(offDelta3);

        assertEquals(new RemoveDelta(LOCATION_2, offDelta3), instance.remove(PERSON_3));
    }

    @Test
    public void defensiveCopyingOfAllLocationIds(){
        LocalPopulation population = mock(LocalPopulation.class);
        setupMockSetDeltaFor(population);

        instance.set(PERSON_1, SUSCEPTIBLE, LOCATION_1);
        instance.set(PERSON_2, SUSCEPTIBLE, LOCATION_2);
        instance.set(PERSON_3, SUSCEPTIBLE, LOCATION_2);

        IntSet allLocationIds = instance.getAllLocationIds();
        allLocationIds.remove(LOCATION_2);
        allLocationIds.remove(LOCATION_1);

        assertSameElements(setOf(LOCATION_1, LOCATION_2), instance.getAllLocationIds());
    }

    @Test
    public void getAllLocationIds() {
        LocalPopulation population = mock(LocalPopulation.class);
        setupMockSetDeltaFor(population);

        instance.set(PERSON_2, SUSCEPTIBLE, LOCATION_2);
        assertSameElements(setOf(LOCATION_2), instance.getAllLocationIds());

        instance.set(PERSON_3, MILD_INFECTIOUS_YOUNG, LOCATION_2);
        assertSameElements(setOf(LOCATION_2), instance.getAllLocationIds());

        instance.remove(PERSON_2);
        assertSameElements(setOf(LOCATION_2), instance.getAllLocationIds());

        instance.remove(PERSON_1);
        assertSameElements(setOf(LOCATION_2), instance.getAllLocationIds());
    }

    @Test
    public void getStateOfPersonNeverSeedBefore() {
        assertEquals(UNDEFINED, instance.getPersonInfectionStatus(PERSON_3));
    }

    @Test
    public void getLocalPopulation() {
        LocalPopulation population = mock(LocalPopulation.class);
        when(bank.getPopulationAtLocation(LOCATION_1)).thenReturn(population);

        assertEquals(population, instance.getLocalPopulation(LOCATION_1));
    }

    private void assertSameElements(Set<Integer> expected, Collection<Integer> items) {
        assertEquals(expected, new HashSet<Integer>(items));
    }

    private void setupMockSetDeltaFor(LocalPopulation population) {
        CompartmentSetDelta sDelta = new CompartmentSetDelta(false, 0, null);
        CompartmentRemoveDelta rDelta = new CompartmentRemoveDelta(false, 0, null);
        when(bank.getPopulationAtLocation(anyInt())).thenReturn(population);
        when(population.setStateByPersonId(anyInt(), any(InfectionState.class))).thenReturn(sDelta);
        when(population.remove(anyInt())).thenReturn(rDelta);
    }
}
