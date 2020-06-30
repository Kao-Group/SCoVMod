/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.healthboard;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import scovmod.model.output.IResult;
import scovmod.model.output.modules.OutputModuleAdapter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HealthBoardSummary extends OutputModuleAdapter {

    // This contains the data which will be reported in the IResult
    private final Map<DailyResult, Integer> dailyHealthBoardResult = new HashMap<>();

    private Int2ObjectMap<IntOpenHashSet> dzsPerHealthBoardsWithDead = new Int2ObjectOpenHashMap<>();

    @Override
    public IResult buildResult() {
        return new HealthBoardSummaryResult(dailyHealthBoardResult);
    }

    @Override
    public void currentTotalDeadHB(LocalDate date, int value, int healthBaord) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD, healthBaord);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalSevereInfectiousHB(LocalDate date, int value, int healthBaord) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS, healthBaord);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalHospitalisedHB(LocalDate date, int value, int healthBaord) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED, healthBaord);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalExposedHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentExposedYoungHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentExposedAdultHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentExposedElderlyHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalInfectiousHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.INFECTIOUS, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousYoungHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousAdultHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousElderlyHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalMildInfectiousHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousYoungHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousAdultHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousElderlyHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS_ELDERLY, community);
        incrementDailyResult(ar, value);
    }


    @Override
    public void currentRecoveredYoungHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredAdultHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredElderlyHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalRecoveredHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentDeadYoungHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentDeadAdultHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentDeadElderlyHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedYoungHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedAdultHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedElderlyHB(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED_ELDERLY, community);
        incrementDailyResult(ar, value);
    }


    @Override
    public void currentNumberDZAreasWithDeadPerHealthBoard(LocalDate date, int laAreaWithDead, int healthBoard) {
        if(dzsPerHealthBoardsWithDead.containsKey(healthBoard)){
            IntOpenHashSet lasWithDead = dzsPerHealthBoardsWithDead.get(healthBoard);
            if(!lasWithDead.contains(laAreaWithDead)) { //Doesn't contain it already then increment
                lasWithDead.add(laAreaWithDead);
                dzsPerHealthBoardsWithDead.put(healthBoard, lasWithDead);
                DailyResult ar = new DailyResult(date, HealthBoardValueType.NUMBER_DZ_AREAS_WITH_DEAD, healthBoard);
                //incrementDailyResult(ar, izsWithDead.size()); //new IZ area with dead  so increment for this healthboard
                dailyHealthBoardResult.put(ar, lasWithDead.size());
            } else { // Still want to report even if no change
                DailyResult ar = new DailyResult(date, HealthBoardValueType.NUMBER_DZ_AREAS_WITH_DEAD, healthBoard);
                dailyHealthBoardResult.put(ar, lasWithDead.size());
            }
        } else { // No entries for this healthboard yet so create new empty set and add - then increment stat
            IntOpenHashSet lasWithDead = new IntOpenHashSet();
            lasWithDead.add(laAreaWithDead);
            dzsPerHealthBoardsWithDead.put(healthBoard, lasWithDead);
            DailyResult ar = new DailyResult(date, HealthBoardValueType.NUMBER_DZ_AREAS_WITH_DEAD, healthBoard);
            incrementDailyResult(ar, 1); //new IZ area with dead  so increment for this healthboard
        }
    }

    private void incrementDailyResult(DailyResult ar, int value) {
        if (dailyHealthBoardResult.containsKey(ar)) {
            int currentCount = dailyHealthBoardResult.get(ar);
            //System.out.println("Incrementing: "+ar+" with count "+currentCount);
            dailyHealthBoardResult.put(ar, currentCount + value);
        } else {
            //System.out.println("Entering new: "+ar+" with count "+value);
            dailyHealthBoardResult.put(ar, value);
        }
    }

}
