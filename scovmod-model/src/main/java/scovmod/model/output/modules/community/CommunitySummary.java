/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.community;

import scovmod.model.output.IResult;
import scovmod.model.output.modules.OutputModuleAdapter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CommunitySummary extends OutputModuleAdapter {

    // This contains the data which will be reported in the IResult
    private final Map<DailyResult, Integer> dailyCommunityResult = new HashMap<>();

    @Override
    public IResult buildResult() {
        return new CommunitySummaryResult(dailyCommunityResult);
    }

    @Override
    public void currentTotalExposed(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentExposedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentExposedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentExposedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalInfectious(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.INFECTIOUS, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalMildInfectious(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalSevereInfectious(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalRecovered(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentDeadYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentDeadAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentDeadElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalDead(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalHospitalised(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED, community);
        updateForDailyResult(ar, value);
    }
    private void updateForDailyResult(DailyResult ar, int value) { //Want to combine two half day timesteps
        dailyCommunityResult.put(ar,  value);
    }

}
