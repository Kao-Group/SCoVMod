/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

import java.time.temporal.ChronoUnit;

public class TimeConversionsTest {

    private static final int TIMESTEP_DAYS_1 = 1;
    private static final int TIMESTEP_DAYS_15 = 15;
    private static final int TIMESTEP = 10;

//    @Test
//    public void timeStepToYear(){
//        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_14);
//
//        assertEquals(2008, instance.toYear(timeStepFrom(2008, Month.JANUARY, 14)));
//        assertEquals(2008, instance.toYear(timeStepFrom(2008, Month.DECEMBER, 31)));
//        assertEquals(2008, instance.toYear(timeStepFrom(2008, Month.APRIL, 8)));
//        assertEquals(2012, instance.toYear(timeStepFrom(2012, Month.FEBRUARY, 20)));
//        assertEquals(2016, instance.toYear(timeStepFrom(2016, Month.AUGUST, 21)));
//    }

//    private long timeStepFrom(int year, Month month, int day){
//        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_14);
//        return instance.toEpochTimeStep(LocalDate.of(year, month, day));
//    }

//    @Test
//    public void dateToTimeStep() {
//        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_14);
//
//        assertEquals(1043, instance.toEpochTimeStep(LocalDate.of(2010, Month.JANUARY, 6)));
//        assertEquals(1044, instance.toEpochTimeStep(LocalDate.of(2010, Month.JANUARY, 7)));
//        assertEquals(1044, instance.toEpochTimeStep(LocalDate.of(2010, Month.JANUARY, 8)));
//        assertEquals(1044, instance.toEpochTimeStep(LocalDate.of(2010, Month.JANUARY, 20)));
//        assertEquals(1045, instance.toEpochTimeStep(LocalDate.of(2010, Month.JANUARY, 21)));
//    }

    @Test
    public void timeStepToDate() {
        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_1);

        assertEquals(LocalDate.of(1971, Month.JUNE, 7), instance.toTimeStepStartDate(1044));
        assertEquals(LocalDate.of(1971, Month.JUNE, 7), instance.toTimeStepStartDate(1045));
    }

//    @Test
//    public void addChronoUnitsToStep() {
//        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_14);
//
//        //Note: timestep of 14 days leads to the addition of 30 days being
//        // rounded down to 2 time steps, i.e. 28 days
//        assertEquals(1046, instance.addChronoUnitsToStep(1044, 30, ChronoUnit.DAYS));
//        assertEquals(1048, instance.addChronoUnitsToStep(1044, 60, ChronoUnit.DAYS));
//
//        assertEquals(1070, instance.addChronoUnitsToStep(1044, 1, ChronoUnit.YEARS));
//        assertEquals(1148, instance.addChronoUnitsToStep(1044, 4, ChronoUnit.YEARS));
//    }

//    @Test
//    public void epochTimeStepToEpochDays() {
//        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_14);
//
//        assertEquals(1044 * TIMESTEP_DAYS_14, instance.toEpochDays(1044));
//    }

    @Test
    public void convertTimeStepToDayofYear() {
        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_1);

        assertEquals(6, instance.toDayOfYear(TIMESTEP));
    }

    @Test
    public void getTimeElapsedBetweenDaysOfYear() {
        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_1);

        assertEquals(221, instance.elapsedDaysSince(150, TIMESTEP));
    }
    
//    @Test
//    public void isFirstTimeStepOfTheMonth(){
//    	TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_14);
//
//    	assertFalse(instance.isFirstWholeTimeStepOfTheMonth(1043l));
//    	assertTrue(instance.isFirstWholeTimeStepOfTheMonth(1044l));
//    	assertFalse(instance.isFirstWholeTimeStepOfTheMonth(1045l));
//    	assertTrue(instance.isFirstWholeTimeStepOfTheMonth(1046l));
//    	assertFalse(instance.isFirstWholeTimeStepOfTheMonth(1047l));
//    	assertTrue(instance.isFirstWholeTimeStepOfTheMonth(1048l));
//    }

//    @Test
//    public void getFirstWholeTimeStepOfTheYear() {
//        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_15);
//        assertEquals(926,instance.firstWholeTimeStepOfYear(2008));
//        assertEquals(950,instance.firstWholeTimeStepOfYear(2009));
//        assertEquals(1023,instance.firstWholeTimeStepOfYear(2012));

/*
        // Helper to work out time steps
        LocalDate d = LocalDate.parse("2015-12-15");
        for(int i = 0; i<37; i++){
            LocalDate e = d.plus(i, ChronoUnit.DAYS);
            System.out.println(e+" ---> "+instance.toEpochTimeStep(e));
        }
 */
//        assertEquals(1121,instance.firstWholeTimeStepOfYear(2016));
//    }

//    @Test
//    public void getLastWholeTimeStepOfTheYear() {
//        TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_15);
//
////        LocalDate d1 = LocalDate.parse("2013-12-01");
////        for(int i = 0; i<37; i++){
////            LocalDate e = d1.plus(i, ChronoUnit.DAYS);
////            System.out.println(e+" ---> "+instance.toEpochTimeStep(e));
////        }
//
//        assertEquals(1070,instance.lastWholeTimeStepOfYear(2013));
//        assertEquals(1046,instance.lastWholeTimeStepOfYear(2012));
//        assertEquals(1119,instance.lastWholeTimeStepOfYear(2015));
//        assertEquals(1143,instance.lastWholeTimeStepOfYear(2016));
//    }

//        @Test
//    public void isFirstTimeStepOfTheYear(){
//    	TimeConversions instance = new TimeConversions(TIMESTEP_DAYS_14);
//
//    	assertFalse(instance.isFirstWholeTimeStepOfTheYear(1043l));
//    	assertTrue(instance.isFirstWholeTimeStepOfTheYear(1044l));
//    	assertFalse(instance.isFirstWholeTimeStepOfTheYear(1045l));
//    }
}
