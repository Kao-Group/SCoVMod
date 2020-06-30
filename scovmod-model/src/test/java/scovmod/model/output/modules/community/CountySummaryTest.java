///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package tbmi.model.output.modules.county;
//
//import org.junit.Assert;
//import tbmi.model.output.IOutputModule;
//import it.unimi.dsi.fastutil.ints.IntSet;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import org.mockito.MockitoAnnotations;
//import tbmi.model.transition.susceptible.TransmissionEventType;
//import tbmi.model.util.TestUtils;
//
//import static tbmi.model.attic.test.enums.ScheduledTestType.*;
//
//public class CountySummaryTest {
//
//    IOutputModule instance;
//    private final int COUNTY_1 = 1;
//    private final int COUNTY_12 = 12;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        instance = new CountySummary();
//    }
//
//    @Test
//    public void getSimulatedAnnualResultPerCounty() {
//        IntSet tenReactors = TestUtils.intSetOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        IntSet fiveReactors = TestUtils.intSetOf(11, 12, 13, 14, 15);
//        IntSet threeReactors = TestUtils.intSetOf(16,17, 18);
//        IntSet sevenReactors = TestUtils.intSetOf(19, 20, 21, 22, 23, 24, 25);
//        //County 1
//        instance.newInfection(1, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(2, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(3, 2013, TransmissionEventType.FROM_WILDLIFE);
//        instance.newInfection(4, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(5, 2013, TransmissionEventType.FROM_WILDLIFE);
//        instance.newInfection(6, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(7, 2013, TransmissionEventType.FROM_WILDLIFE);
//        instance.newInfection(8, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(9, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(10, 2013, TransmissionEventType.FROM_WILDLIFE);
//        instance.newInfection(11, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(12, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(13, 2013, TransmissionEventType.FROM_WILDLIFE);
//        instance.newInfection(14, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(15, 2013, TransmissionEventType.FROM_WILDLIFE);
//        //County 12
//        instance.newInfection(16, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(17, 2013, TransmissionEventType.FROM_WILDLIFE);
//        instance.newInfection(18, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(19, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(20, 2013, TransmissionEventType.FROM_WILDLIFE);
//        instance.newInfection(21, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(22, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(23, 2013, TransmissionEventType.FROM_WILDLIFE);
//        instance.newInfection(24, 2013, TransmissionEventType.FROM_PERSON);
//        instance.newInfection(25, 2013, TransmissionEventType.FROM_WILDLIFE);
//
//
//        instance.newReactors(tenReactors, 2013, COUNTY_1);
//        instance.newReactors(fiveReactors, 2013, COUNTY_1);
//        instance.newReactors(threeReactors, 2013, COUNTY_12);
//        instance.newReactors(sevenReactors, 2013, COUNTY_12);
//
//        instance.newFailedTest(2013, COUNTY_1, ROUTINE_SICCT_PTI1);
//        instance.newFailedTest(2013, COUNTY_1, SICCT_OTFS_SIT_1);
//        instance.newFailedTest(2013, COUNTY_12, FOLLOW_UP_6M_SICCT);
//
//        instance.newBreakdown(2013, COUNTY_1);
//        instance.newBreakdown(2013, COUNTY_1);
//        instance.newBreakdown(2013, COUNTY_12);
//
//        int expectedCount_County_1 = 15;
//        int expectedCount_County_12 = 10;
//
//        AnnualResult county1Reactors = new AnnualResult(2013, CountyValueType.REACTORS, COUNTY_1);
//        AnnualResult county12Reactors = new AnnualResult(2013, CountyValueType.REACTORS, COUNTY_12);
//        AnnualResult county1FailedTests = new AnnualResult(2013, CountyValueType.FAILED_TESTS, COUNTY_1);
//        AnnualResult county12FailedTests = new AnnualResult(2013, CountyValueType.FAILED_TESTS, COUNTY_12);
//        AnnualResult county1Breakdowns = new AnnualResult(2013, CountyValueType.BREAKDOWNS, COUNTY_1);
//        AnnualResult county12Breakdowns = new AnnualResult(2013, CountyValueType.BREAKDOWNS, COUNTY_12);
//        AnnualResult county1OTFW = new AnnualResult(2013, CountyValueType.OTFW, COUNTY_1);
//        AnnualResult county12OTFS = new AnnualResult(2013, CountyValueType.OTFS, COUNTY_12);
//        AnnualResult county1FailedRoutine = new AnnualResult(2013, CountyValueType.FAILED_ROUTINE, COUNTY_1);
//        AnnualResult county1FailedSIT = new AnnualResult(2013, CountyValueType.FAILED_SIT, COUNTY_1);
//        AnnualResult county1FailedFollowUP = new AnnualResult(2013, CountyValueType.FAILED_FOLLOWUP, COUNTY_12);
//        AnnualResult county1ReactorPerson = new AnnualResult(2013, CountyValueType.REACTOR_ANIMAL, COUNTY_1);
//        AnnualResult county1ReactorEnvironment = new AnnualResult(2013, CountyValueType.REACTOR_ENVIRONMENT, COUNTY_1);
//        AnnualResult county12ReactorPerson = new AnnualResult(2013, CountyValueType.REACTOR_ANIMAL, COUNTY_12);
//        AnnualResult county12ReactorEnvironment = new AnnualResult(2013, CountyValueType.REACTOR_ENVIRONMENT, COUNTY_12);
//
//
//        Map<AnnualResult, Integer> expectedAnnualCountyResult = new HashMap<>();
//
//        expectedAnnualCountyResult.put(county1Reactors, expectedCount_County_1);
//        expectedAnnualCountyResult.put(county12Reactors, expectedCount_County_12);
//        expectedAnnualCountyResult.put(county1FailedTests, 2);
//        expectedAnnualCountyResult.put(county12FailedTests, 1);
//        expectedAnnualCountyResult.put(county1Breakdowns, 2);
//        expectedAnnualCountyResult.put(county12Breakdowns, 1);
//        expectedAnnualCountyResult.put(county1OTFW, 1);
//        expectedAnnualCountyResult.put(county12OTFS, 1);
//        expectedAnnualCountyResult.put(county1FailedRoutine, 1);
//        expectedAnnualCountyResult.put(county1FailedSIT, 1);
//        expectedAnnualCountyResult.put(county1FailedFollowUP, 1);
//        expectedAnnualCountyResult.put(county1ReactorPerson, 9);
//        expectedAnnualCountyResult.put(county1ReactorEnvironment, 6);
//        expectedAnnualCountyResult.put(county12ReactorPerson, 6);
//        expectedAnnualCountyResult.put(county12ReactorEnvironment, 4);
//
//        CountySummaryResult buildResult = (CountySummaryResult) instance.buildResult();
//        Map<AnnualResult, Integer> simulatedAnnualCountyResult = buildResult.getSimulatedAnnualResultPerCounty();
//        Assert.assertEquals(expectedAnnualCountyResult, simulatedAnnualCountyResult);
//    }
//}
