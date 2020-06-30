/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.time;

import scovmod.model.ModelException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TimeConversions {

    private int timeStepInDays;

    public TimeConversions(int timestepDays) {
        this.timeStepInDays = timestepDays;
    }

  //  public int getTimeStepInDays(){
  //      return timeStepInDays;
  //  }

   // public long toEpochTimeStep(LocalDate date) {
   //     return (date.toEpochDay() / this.timeStepInDays);
    //}

    //public long toEpochDays(long timeStep) {
   //     return timeStep * timeStepInDays;
   // }

    public LocalDate toTimeStepStartDate(long timeStep) {
        return LocalDate.ofEpochDay(timeStep/2 * this.timeStepInDays);
    }

//    public long addChronoUnitsToStep(long timeStep, int unitsToAdd, ChronoUnit unit) {
//        LocalDate d = toTimeStepStartDate(timeStep);
//        LocalDate dNew = d.plus(unitsToAdd, unit);
//        return toEpochTimeStep(dNew);
//    }

    public int toDayOfYear(long timeStep){
        return toTimeStepStartDate(timeStep).getDayOfYear();
    }

    public long elapsedDaysSince(int dayOfYear, long currentTimeStep){
        int currentDayOfYear = toDayOfYear(currentTimeStep);
        if(currentDayOfYear<dayOfYear){
            return 365-(dayOfYear-currentDayOfYear);//return elasped days since last year
        } else {
            return currentDayOfYear - dayOfYear; //no days elapsed this year
        }

    }

//	public boolean isFirstWholeTimeStepOfTheYear(long timeStep) {
//		LocalDate date = toTimeStepStartDate(timeStep);
//		int dayOfMonth = date.getDayOfMonth();
//		return (date.getMonthValue() == 1)
//				&& (dayOfMonth >= 1)
//				&& (dayOfMonth <= timeStepInDays);
//	}

//    public boolean isLastWholeTimeStepOfTheYear(long timeStep) {
//        LocalDate date = toTimeStepStartDate(timeStep);
//        int tsStartDay = date.getDayOfMonth();
//        int lastDayOfThisTS = tsStartDay + timeStepInDays - 1;
//        int lastDayOfNextTS = lastDayOfThisTS + timeStepInDays;
//        return (date.getMonthValue() == 12)
//                && (lastDayOfThisTS <= 31) // Last day of this step must be THIS year
//                && (lastDayOfNextTS > 31); // Next time step must end within NEXT year
//
//    }

//	public boolean isFirstWholeTimeStepOfTheMonth(long timeStep) {
//		LocalDate date = toTimeStepStartDate(timeStep);
//		int dayOfMonth = date.getDayOfMonth();
//		return (dayOfMonth >= 1) && (dayOfMonth < timeStepInDays);
//	}
//
//    public long firstWholeTimeStepOfYear(int year) {
//        long timeStep = this.toEpochTimeStep(LocalDate.ofYearDay(year,1));
//        if(isFirstWholeTimeStepOfTheYear(timeStep)){
//            return timeStep;
//        } else if(isFirstWholeTimeStepOfTheYear(timeStep + 1)){
//            return timeStep +1;
//        } else {
//            throw new ModelException("Problem finding first time step of year");
//        }
//    }

//    public long lastWholeTimeStepOfYear(int year) {
//        long timeStep = this.toEpochTimeStep(LocalDate.ofYearDay(year,365));
//        if(isLastWholeTimeStepOfTheYear(timeStep)){
//            return timeStep;
//        } else if(isLastWholeTimeStepOfTheYear(timeStep - 1)){
//            return timeStep - 1;
//        } else {
//            throw new ModelException("Problem finding last time step of year");
//        }
//    }

    public int toYear(long timeStep) {
        return this.toTimeStepStartDate(timeStep).getYear();
    }
}
