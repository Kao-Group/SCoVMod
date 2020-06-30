/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.councilarea;

import it.unimi.dsi.fastutil.ints.*;
import scovmod.model.output.IResult;
import scovmod.model.output.modules.OutputModuleAdapter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CouncilAreaSummary extends OutputModuleAdapter {

    // This contains the data which will be reported in the IResult
    private final Map<DailyResult, Integer> dailyCouncilAreaResult = new TreeMap<>();
    private final Map<DailyResult, Integer> nonCumulativeCouncilAreaResult = new HashMap<>();

    private Int2ObjectMap<IntOpenHashSet> lasPerCouncilAreaWithDead = new Int2ObjectOpenHashMap<>();

    @Override
    public IResult buildResult() {
        //Convert cumulative to just incidence per week
        Map<Integer,Integer> previousValuePerArea = new Int2IntOpenHashMap();
        for (Map.Entry<DailyResult, Integer> kvp : dailyCouncilAreaResult.entrySet()) {
            int newValue = kvp.getValue();
            DailyResult dailyResult = kvp.getKey();
            int area = dailyResult.getCouncilAreaID();
            int previousValue = 0;
            if(previousValuePerArea.containsKey(area)) previousValue = previousValuePerArea.get(area);
            int diff = newValue - previousValue;
            nonCumulativeCouncilAreaResult.put(dailyResult,diff);
            previousValuePerArea.put(area,newValue);
        }
        return new CouncilAreaSummaryResult(nonCumulativeCouncilAreaResult);
       // return new CouncilAreaSummaryResult(dailyCouncilAreaResult);
    }

    @Override
    public void currentTotalDeadCA(LocalDate date, int value, int ca) {
        DailyResult ar = new DailyResult(date, CouncilAreaValueType.DEAD, ca);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalSevereInfectiousHB(LocalDate date, int value, int healthBaord) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS, healthBaord);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalHospitalisedHB(LocalDate date, int value, int healthBaord) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED, healthBaord);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalExposed(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentExposedYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentExposedAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentExposedElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalInfectious(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.INFECTIOUS, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalMildInfectious(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalSevereInfectious(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalRecovered(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentDeadYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentDeadAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentDeadElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalDead(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalHospitalised(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED, community);
//        incrementDailyResult(ar, value);
    }

//    @Override
//    public void currentNumberLAAreasWithDeadPerHealthBoard(LocalDate date, int laAreaWithDead, int healthBoard) {
//        if(lasPerCouncilAreaWithDead.containsKey(healthBoard)){
//            IntOpenHashSet lasWithDead = lasPerHealthBoardsWithDead.get(healthBoard);
//            if(!lasWithDead.contains(laAreaWithDead)) { //Doesn't contain it already then increment
//                lasWithDead.add(laAreaWithDead);
//                lasPerHealthBoardsWithDead.put(healthBoard, lasWithDead);
//                DailyResult ar = new DailyResult(date, HealthBoardValueType.NUMBER_LA_AREAS_WITH_DEAD, healthBoard);
//                //incrementDailyResult(ar, izsWithDead.size()); //new IZ area with dead  so increment for this healthboard
//                dailyHealthBoardResult.put(ar, lasWithDead.size());
//            } else { // Still want to report even if no change
//                DailyResult ar = new DailyResult(date, HealthBoardValueType.NUMBER_LA_AREAS_WITH_DEAD, healthBoard);
//                dailyHealthBoardResult.put(ar, lasWithDead.size());
//            }
//        } else { // No entries for this healthboard yet so create new empty set and add - then increment stat
//            IntOpenHashSet lasWithDead = new IntOpenHashSet();
//            lasWithDead.add(laAreaWithDead);
//            lasPerHealthBoardsWithDead.put(healthBoard, lasWithDead);
//            DailyResult ar = new DailyResult(date, HealthBoardValueType.NUMBER_LA_AREAS_WITH_DEAD, healthBoard);
//            incrementDailyResult(ar, 1); //new IZ area with dead  so increment for this healthboard
//        }
//    }

    private void incrementDailyResult(DailyResult ar, int value) {
        if (dailyCouncilAreaResult.containsKey(ar)) {
            int currentCount = dailyCouncilAreaResult.get(ar);
            dailyCouncilAreaResult.put(ar, currentCount + value);
        } else {
            dailyCouncilAreaResult.put(ar, value);
        }
    }

}
