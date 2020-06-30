///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package tbmi.model.output.modules.county;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import tbmi.model.output.modules.county.AnnualResult;
//import tbmi.model.output.modules.county.CountyValueType;
//
//public class AnnualResultTest {
//
//    private final int Year_2011 = 2011;
//    private final int Year_2012 = 2012;
//    private final CountyValueType REACTOR = CountyValueType.REACTORS;
//    private final CountyValueType FAILEDTEST = CountyValueType.FAILED_TESTS;
//    private final int countyId = 1;
//
//    @Test
//    public void valueObject() {
//        AnnualResult instance1a = new AnnualResult(Year_2011, REACTOR, countyId);
//        AnnualResult instance1b = new AnnualResult(Year_2011, REACTOR, countyId);
//        AnnualResult instance2 = new AnnualResult(Year_2012, FAILEDTEST, countyId);
//        Assert.assertTrue(instance1a.equals(instance1b));
//        Assert.assertFalse(instance1a.equals(instance2));
//
//        Assert.assertEquals(instance1a.hashCode(), instance1b.hashCode());
//        Assert.assertFalse(instance1a.hashCode() == instance2.hashCode());
//
//    }
//}
