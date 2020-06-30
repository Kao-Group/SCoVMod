/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.community;


import scovmod.model.output.IResult;
import java.util.Arrays;
import java.util.Map;

public class CommunitySummaryResult implements IResult{

    private final Map<DailyResult,Integer>  simulatedDailyCommunityResult;

    public CommunitySummaryResult(Map<DailyResult,Integer>  simulatedDailyCommunityResult) {
        this.simulatedDailyCommunityResult= simulatedDailyCommunityResult;
    }

    public Map<DailyResult,Integer>getSimulatedDailyResultPerCommunity() {
        return this.simulatedDailyCommunityResult;
    }

    @Override
    public String toString() {
        return "CommunitySummaryResult{" +
                "simulatedDailyCommunityResult=" + Arrays.toString(simulatedDailyCommunityResult.entrySet().toArray()) +
                '}';
    }
}
