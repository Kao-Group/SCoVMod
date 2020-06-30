//package tbmi.model.output.modules.national;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.MockitoAnnotations;
//
//import tbmi.model.output.IOutputModule;
//import it.unimi.dsi.fastutil.ints.IntSet;
//import tbmi.model.util.TestUtils;
//
//import java.time.LocalDate;
//import java.time.Month;
//
//public class NationalTestCSVTest {
//
//    IOutputModule instance;
//    private static int FARM_1 = 12345678;
//    private static int FARM_2 = 13453453;
//    private LocalDate DATE_1 = LocalDate.of(2008, Month.JANUARY, 1);
//
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        instance = new NationalTestCSV(null);
//    }
//
//    @Test
//    public void newReactors() {
//
//        IntSet tenReactors = TestUtils.intSetOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        IntSet fiveReactors = TestUtils.intSetOf(1, 2, 3, 4, 5);
//
//        instance.newReactors(tenReactors, 2013,1);
//        instance.newReactors(fiveReactors, 2013,1);
//
//        int expectedCount = 15;
//
//        Assert.assertEquals(expectedCount, ((NationalTestCSV) instance).getTotalReactors(2013));
//    }
//
//        @Test
//    public void newFailedTests() {
//
//        instance.newFailedTest(2013,1, null);
//        instance.newFailedTest(2013,1, null);
//
//        int expectedCount = 2;
//
//        Assert.assertEquals(expectedCount, ((NationalTestCSV) instance).getTotalFailedTests(2013));
//    }
//
//
//
////    @Test
////    public void newDeathMovement() {
////
////        instance.newDeathMovement(2013);
////        instance.newDeathMovement(2013);
////
////        int expectedCount = 2;
////
////        Assert.assertEquals(expectedCount, ((IntegrationTestCSV) instance).getTotalDeathMovs(2013));
////    }
////    @Test
////    public void newStandardMovement() {
////
////        instance.newStandardMovement(2013);
////        instance.newStandardMovement(2013);
////
////        int expectedCount = 2;
////
////        Assert.assertEquals(expectedCount, ((IntegrationTestCSV) instance).getTotalStandardMovs(2013));
////    }
//
//
//    @Test
//    public void currentTotalSusceptible() {
//
//        instance.currentTotalSusceptible(DATE_1, 1234);
//
//        int expectedCount = 1234;
//
//        Assert.assertEquals(expectedCount, ((NationalTestCSV) instance).getTotalSusceptible(DATE_1));
//    }
//
//    @Test
//    public void currentTotalTestSensitiveGamma() {
//
//        instance.currentTotalGammaTestSensitive(DATE_1, 1234);
//
//        int expectedCount = 1234;
//
//        Assert.assertEquals(expectedCount, ((NationalTestCSV) instance).getTotalTestSensitiveGamma(DATE_1));
//    }
//
//    @Test
//    public void currentTotalExposed() {
//
//        instance.currentTotalExposed(DATE_1, 1234);
//
//        int expectedCount = 1234;
//
//        Assert.assertEquals(expectedCount, ((NationalTestCSV) instance).getTotalExposed(DATE_1));
//    }
//
//    @Test
//    public void currentTotalInfectious() {
//
//        instance.currentTotalInfectious(DATE_1, 1234);
//
//        int expectedCount = 1234;
//
//        Assert.assertEquals(expectedCount, ((NationalTestCSV) instance).getTotalInfectious(DATE_1));
//    }
//
////    @Test
////    public void currentTotalPopulation() {
////
////        instance.currentOverallPopulation(DATE_1, 1234);
////
////        int expectedCount = 1234;
////
////        Assert.assertEquals(expectedCount, ((IntegrationTestCSV) instance).getTotalPopulation(DATE_1));
////    }
//
//}
