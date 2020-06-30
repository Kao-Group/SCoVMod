/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.healthboard;


import scovmod.model.output.IResult;

import java.util.Arrays;
import java.util.Map;

public class HealthBoardSummaryResult implements IResult{

    private final Map<DailyResult,Integer>  simulatedDailyHealthBoardResult;

    public HealthBoardSummaryResult(Map<DailyResult,Integer>  simulatedDailyHealthBoardResult) {
        this.simulatedDailyHealthBoardResult= simulatedDailyHealthBoardResult;
    }

    public Map<DailyResult,Integer>getSimulatedDailyResultPerHealthBoard() {
        return this.simulatedDailyHealthBoardResult;
    }

    @Override
    public String toString() {
        return "HealthBoardSummaryResult{" +
                "simulatedDailyHealthBoardResult=" + Arrays.toString(simulatedDailyHealthBoardResult.entrySet().toArray()) +
                '}';
    }
}
