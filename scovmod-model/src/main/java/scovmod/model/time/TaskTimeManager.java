/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.time;

import scovmod.model.input.config.ConfigParameters;

public class TaskTimeManager {

    private final TimeManager tm;
    private final long firstTimeStep;

    public TaskTimeManager(ConfigParameters modelConfig, TimeManager tm) {
        this.tm = tm;
        firstTimeStep = modelConfig.getFirstTimeStep();
    }

    public boolean isFirstTimeStep() {
        return tm.getTimeStep() == firstTimeStep;
    }

}
