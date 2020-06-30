package scovmod.model.state.population;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.StateException;
import scovmod.model.state.delta.CompartmentRemoveDelta;
import scovmod.model.state.delta.CompartmentSetDelta;
import static scovmod.model.state.infection.InfectionState.*;

public class LocalPopulationTest {

    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final int PERSON_3 = 3;
    private final int PERSON_4 = 4;
    private int personIdCounter;

    private LocalPopulation instance;

    @Before
    public void setup() {
        instance = new LocalPopulation();

        personIdCounter = 500;
    }

    @Test
    public void testDefensiveCopyOfGetAllPersons() {
        instance.setStateByPersonId(PERSON_1, SUSCEPTIBLE);
        instance.setStateByPersonId(PERSON_3, EXPOSED_ELDERLY);

        LocalPopulation result = new LocalPopulation(instance);

        //result.removeTestsDueOn(PERSON_4);
        //Note that dead persons are included in the return
       // assertEquals(setOf(PERSON_1, PERSON_2, PERSON_3, PERSON_4), instance.getAllPersonIds());
        assertEquals(setOf(PERSON_1, PERSON_3), instance.getAllPersonIds());

        assertEquals(2, instance.getSize());
    }

    @Test
    public void copyConstructor() {
        //instance.setStateByPersonId(PERSON_1, EXPOSED);
        instance.setStateByPersonId(PERSON_2, SUSCEPTIBLE);
        instance.setStateByPersonId(PERSON_3, EXPOSED_ADULT);
        instance.setStateByPersonId(PERSON_4, EXPOSED_ADULT);

        LocalPopulation result = new LocalPopulation(instance);

        //Modifying original should not modify copy
        //instance.removeTestsDueOn(PERSON_1);
        instance.remove(PERSON_2);
        instance.setStateByPersonId(PERSON_2, MILD_INFECTIOUS_ADULT);

        assertEquals(setOf(PERSON_2), result.getAllInState(SUSCEPTIBLE));
       // assertEquals(setOf(PERSON_1), result.getAllInState(EXPOSED));
        assertEquals(setOf(PERSON_3, PERSON_4), result.getAllInState(EXPOSED_ADULT));
        assertEquals(setOf(), result.getAllInState(MILD_INFECTIOUS_ADULT));
       // assertEquals(setOf(), result.getAllInState(DEAD));
    }

    @Test
    public void equalityAndHashCode() {
        instance.setStateByPersonId(PERSON_1, SUSCEPTIBLE);
        instance.setStateByPersonId(PERSON_2, SEVERE_INFECTIOUS_ELDERLY);
       // instance.setStateByPersonId(PERSON_3, DEAD);

        LocalPopulation copy = new LocalPopulation(instance);

        assertEquals(copy, instance);
        assertEquals(copy.hashCode(), instance.hashCode());

        copy.remove(PERSON_1);
        assertFalse(copy.equals(instance));
        assertFalse(copy.hashCode() == instance.hashCode());
    }

    @Test
    public void setAndGetAllInState() {
        instance.setStateByPersonId(PERSON_1, SUSCEPTIBLE);
        instance.setStateByPersonId(PERSON_2, DEAD_ADULT);
        instance.setStateByPersonId(PERSON_3, EXPOSED_YOUNG);
        instance.setStateByPersonId(PERSON_4, DEAD_ADULT);

        assertEquals(setOf(PERSON_1), instance.getAllInState(SUSCEPTIBLE));
        assertEquals(setOf(PERSON_3), instance.getAllInState(EXPOSED_YOUNG));
        assertEquals(setOf(), instance.getAllInState(MILD_INFECTIOUS_ADULT));
        assertEquals(setOf(PERSON_2, PERSON_4), instance.getAllInState(DEAD_ADULT));
    }

    @Test
    public void getAllPersons() {
        instance.setStateByPersonId(PERSON_1, SUSCEPTIBLE);
        instance.setStateByPersonId(PERSON_2, DEAD_ELDERLY);
        instance.setStateByPersonId(PERSON_3, EXPOSED_YOUNG);
        instance.setStateByPersonId(PERSON_4, DEAD_ELDERLY);

        //Note that dead persons are included in the return
        assertEquals(setOf(PERSON_1, PERSON_2, PERSON_3,PERSON_4), instance.getAllPersonIds());
        //assertEquals(setOf(PERSON_1, PERSON_3), instance.getAllPersonIds());

        assertEquals(4, instance.getSize());
    }

    @Test
    public void setAndGetPersonInfectionState() {
        instance.setStateByPersonId(PERSON_1, EXPOSED_YOUNG);
        instance.setStateByPersonId(PERSON_2, HOSPITALISED_ADULT);

        assertEquals(EXPOSED_YOUNG, instance.getPersonInfectionState(PERSON_1));
        assertEquals(HOSPITALISED_ADULT, instance.getPersonInfectionState(PERSON_2));
    }

    @Test(expected = StateException.class)
    public void exceptionIfAskForStateOfPersonNotSeenBefore() {
        instance.getPersonInfectionState(PERSON_3);
    }

    @Test
    public void remove() {
        instance.setStateByPersonId(PERSON_1, InfectionState.SUSCEPTIBLE);
        instance.setStateByPersonId(PERSON_2, DEAD_ELDERLY);
        instance.setStateByPersonId(PERSON_3, EXPOSED_YOUNG);
        instance.setStateByPersonId(PERSON_4, DEAD_ELDERLY);

        instance.remove(PERSON_3);

        assertEquals(setOf(PERSON_1), instance.getAllInState(InfectionState.SUSCEPTIBLE));
        assertEquals(setOf(), instance.getAllInState(EXPOSED_YOUNG));
        assertEquals(setOf(), instance.getAllInState(MILD_INFECTIOUS_ADULT));
        assertEquals(setOf(PERSON_2, PERSON_4), instance.getAllInState(DEAD_ELDERLY));
    }

    @Test
    public void exceptionIfOverwritingExistingState() {
        instance.setStateByPersonId(PERSON_1, InfectionState.SUSCEPTIBLE);

        try {
            instance.setStateByPersonId(PERSON_1, EXPOSED_YOUNG);
            fail("Expected exception");
        } catch (StateException e) {
        }
    }

    @Test
    public void noExceptionIfRemovePersonBeforeAddingStateAgain() {
        instance.setStateByPersonId(PERSON_1, InfectionState.SUSCEPTIBLE);
        instance.remove(PERSON_1);
        instance.setStateByPersonId(PERSON_1, InfectionState.SUSCEPTIBLE);
    }

    @Test
    public void exceptionIfRemovePersonTwice() {
        instance.setStateByPersonId(PERSON_1, InfectionState.SUSCEPTIBLE);
        instance.remove(PERSON_1);

        try {
            instance.remove(PERSON_1);
            fail("Expected exception");
        } catch (StateException e) {
        }
    }

    @Test(expected = StateException.class)
    public void exceptionIfTryToSetUNDEFINED() {
        instance.setStateByPersonId(PERSON_1, InfectionState.UNDEFINED);
    }

    @Test
    public void onDeltasTriggeredByEdgeCase() {
        assertEquals(new CompartmentSetDelta(true, PERSON_1, SUSCEPTIBLE), instance.setStateByPersonId(PERSON_1, SUSCEPTIBLE));
    }

    @Test
    public void onDeltasNonEdgeCase() {
        instance.setStateByPersonId(PERSON_1, EXPOSED_YOUNG);

        assertEquals(new CompartmentSetDelta(false, PERSON_2, EXPOSED_YOUNG), instance.setStateByPersonId(PERSON_2, EXPOSED_YOUNG));
    }

    @Test
    public void onDeltasDiseaseCompartmentsDoNotInteract() {
        instance.setStateByPersonId(PERSON_1, MILD_INFECTIOUS_ADULT);

        assertEquals(new CompartmentSetDelta(true, PERSON_2, SUSCEPTIBLE), instance.setStateByPersonId(PERSON_2, SUSCEPTIBLE));
    }

    @Test
    public void onDeltaTriggeredAfterRemovalOfLastPersonsInState() {
        instance.setStateByPersonId(PERSON_1, EXPOSED_ADULT);
        instance.setStateByPersonId(PERSON_2, EXPOSED_ADULT);

        instance.remove(PERSON_1);
        instance.remove(PERSON_2);

        int personId = nextPersonId();
        assertEquals(new CompartmentSetDelta(true, personId, EXPOSED_ADULT), instance.setStateByPersonId(personId, EXPOSED_ADULT));
    }

    @Test
    public void offDeltas() {
        instance.setStateByPersonId(PERSON_1, InfectionState.SUSCEPTIBLE);
        assertEquals(new CompartmentRemoveDelta(true, PERSON_1, SUSCEPTIBLE), instance.remove(PERSON_1));

        instance.setStateByPersonId(PERSON_2, InfectionState.SUSCEPTIBLE);
        instance.setStateByPersonId(PERSON_3, InfectionState.SUSCEPTIBLE);
        assertEquals(new CompartmentRemoveDelta(false, PERSON_2, SUSCEPTIBLE), instance.remove(PERSON_2));

        instance.setStateByPersonId(PERSON_1, MILD_INFECTIOUS_ADULT);
        instance.setStateByPersonId(PERSON_2, EXPOSED_ELDERLY);
        instance.setStateByPersonId(PERSON_4, InfectionState.SUSCEPTIBLE);
        assertEquals(new CompartmentRemoveDelta(true, PERSON_2, EXPOSED_ELDERLY), instance.remove(PERSON_2));
        assertEquals(new CompartmentRemoveDelta(false, PERSON_3, SUSCEPTIBLE), instance.remove(PERSON_3));
    }

    @Test
    public void getStateList() {
        int PERSON_5 = 5;
        int PERSON_6 = 6;
        instance.setStateByPersonId(PERSON_1, InfectionState.SUSCEPTIBLE);
        instance.setStateByPersonId(PERSON_2, SEVERE_INFECTIOUS_ELDERLY);
      //  instance.setStateByPersonId(PERSON_3, InfectionState.EXPOSED);
        instance.setStateByPersonId(PERSON_4, EXPOSED_ADULT);
        instance.setStateByPersonId(PERSON_5, EXPOSED_ELDERLY);
        instance.setStateByPersonId(PERSON_6, InfectionState.SUSCEPTIBLE);

        List<InfectionState> states = instance.getStateList();
        assertEquals(5, states.size());
        assertNumberOfEntries(2, InfectionState.SUSCEPTIBLE, states);
       // assertNumberOfEntries(1, InfectionState.EXPOSED, states);
        assertNumberOfEntries(1, EXPOSED_ADULT, states);
        assertNumberOfEntries(1, SEVERE_INFECTIOUS_ELDERLY, states);
    }

    @Test
    public void exceptionIfTryToUseNullState(){
        try{
            instance.getAllInState(null);
            fail("Expected exception");
        } catch(NullPointerException e){}

        try{
            instance.setStateByPersonId(PERSON_1, null);
            fail("Expected exception");
        } catch(NullPointerException e){}
    }

    private void assertNumberOfEntries(int i, InfectionState infectionState,
            List<InfectionState> states) {
        int counter = 0;
        for (InfectionState state : states) {
            if (state.equals(infectionState)) {
                counter++;
            }
        }
        assertEquals("Wrong number of " + infectionState + ".  Expected " + i + ", got " + counter, i, counter);
    }

    private Set<Integer> setOf(Integer... personsIds) {
        return new HashSet<Integer>(Arrays.asList(personsIds));
    }

    private int nextPersonId() {
        return personIdCounter++;
    }
}
