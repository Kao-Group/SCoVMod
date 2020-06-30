/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.state;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;
import scovmod.test.FakeLocalPopulation;

import static scovmod.model.state.infection.InfectionState.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;

public class InfectiousProportionTest {

    private static final double TOL = 1e-10;

    private static final int LOCATION_1 = 100;
    private static final int LOCATION_2 = 200;

    private static final int PERSON_1_1 = 1001;
    private static final int PERSON_1_2 = 1002;
    private static final int PERSON_1_3 = 1003;

    private static final int PERSON_2_1 = 2001;
    private static final int PERSON_2_2 = 2002;

    private static final long DAY_1 = 1;
    private static final long DAY_2 = 2;

    @Mock TimeManager tm;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        when(tm.getTimeStep()).thenReturn(DAY_1);
    }

    @Test
    public void emptyCacheOfPreviousLocationProportions(){

        FakeLocalPopulation pop = new FakeLocalPopulation();
        pop.testSet(PERSON_1_2, MILD_INFECTIOUS_ELDERLY);

        FakeLocalPopulation updatedPop = new FakeLocalPopulation();
        updatedPop.testSet(PERSON_1_1, InfectionState.EXPOSED_ADULT);
        updatedPop.testSet(PERSON_1_2, MILD_INFECTIOUS_ELDERLY);
        updatedPop.testSet(PERSON_1_3, SEVERE_INFECTIOUS_ELDERLY);

        StateQuery sq = mock(StateQuery.class);
        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop, updatedPop);

        InfectiousProportion instance = new InfectiousProportion(sq, tm);

        instance.inCommunity(LOCATION_1,MILD_INFECTIOUS_ELDERLY);
        instance.inCommunity(LOCATION_1,SEVERE_INFECTIOUS_ELDERLY);

        when(tm.getTimeStep()).thenReturn(DAY_2);
        assertEquals(1.0 / 3.0, instance.inCommunity(LOCATION_1,MILD_INFECTIOUS_ELDERLY), TOL);
        assertEquals(1.0 / 3.0, instance.inCommunity(LOCATION_1,SEVERE_INFECTIOUS_ELDERLY), TOL);
    }


    @Test
    public void useNormalisedNumInfectiousInLocation() {

        FakeLocalPopulation pop1 = new FakeLocalPopulation();
        pop1.testSet(PERSON_1_1, EXPOSED_ADULT);
        pop1.testSet(PERSON_1_2, MILD_INFECTIOUS_ELDERLY);
        pop1.testSet(PERSON_1_3, EXPOSED_YOUNG);

        StateQuery sq = mock(StateQuery.class);
        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);

        InfectiousProportion instance = new InfectiousProportion(sq, tm);

        assertEquals(1.0 / 3.0, instance.inCommunity(LOCATION_1,MILD_INFECTIOUS_ELDERLY), TOL);
    }

//    @Test
//    public void aggregateProportionInfected() {
//        StateQuery sq = mock(StateQuery.class);
//
//        // Location 1
//        FakeLocalPopulation pop1 = new FakeLocalPopulation();
//        pop1.testSet(PERSON_1_1, EXPOSED_YOUNG);
//        pop1.testSet(PERSON_1_2, MILD_INFECTIOUS_ADULT);
//        pop1.testSet(PERSON_1_3, EXPOSED_ADULT);
//        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
//
//        // Location 2
//        FakeLocalPopulation pop2 = new FakeLocalPopulation();
//        pop1.testSet(PERSON_2_1, EXPOSED_ELDERLY);
//        pop1.testSet(PERSON_2_2, MILD_INFECTIOUS_ADULT);
//        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
//
//        InfectiousProportion instance = new InfectiousProportion( sq, tm);
//        assertEquals(2.0 / 5.0,  instance.inCommunity(intSetOf(LOCATION_1, LOCATION_2),MILD_INFECTIOUS_ADULT), TOL);
//    }
}
