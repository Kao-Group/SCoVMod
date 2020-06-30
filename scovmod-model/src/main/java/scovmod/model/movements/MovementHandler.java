/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.movements;

import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;

public class MovementHandler {

    private final StateModifier sm;
    private final StatisticsCollector stat;
    private final StateQuery sq;

    public MovementHandler(
            StateModifier sm,
            StatisticsCollector stat, StateQuery sq) {
        this.sm = sm;
        this.stat = stat;
        this.sq = sq;
    }

    public void apply(ResolvedMovement movement) {
            if(!sq.isShowingSevereSymptoms(movement.getPersonID())) sm.movePerson(movement.getPersonID(), movement.getDestination());
    }
}
