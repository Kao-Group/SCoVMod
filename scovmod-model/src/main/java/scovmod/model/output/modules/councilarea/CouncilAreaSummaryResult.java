/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.councilarea;


import scovmod.model.output.IResult;

import java.util.Arrays;
import java.util.Map;

public class CouncilAreaSummaryResult implements IResult{

    private final Map<DailyResult,Integer>  simulatedDailyCouncilAreaResult;

    public CouncilAreaSummaryResult(Map<DailyResult,Integer>  simulatedDailyCouncilAreaResult) {
        this.simulatedDailyCouncilAreaResult= simulatedDailyCouncilAreaResult;
    }

    public Map<DailyResult,Integer>getSimulatedDailyResultPerCouncilArea() {
        return this.simulatedDailyCouncilAreaResult;
    }

    @Override
    public String toString() {
        return "CouncilAreaSummaryResult{" +
                "simulatedDailyCouncilAreaResult=" + Arrays.toString(simulatedDailyCouncilAreaResult.entrySet().toArray()) +
                '}';
    }
}
