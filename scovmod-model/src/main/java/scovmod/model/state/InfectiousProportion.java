/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.state;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;

//import static apha.tbmi.state.infection.InfectionState.DEAD;
import scovmod.model.state.population.LocalPopulation;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

import static scovmod.model.state.infection.InfectionState.*;

public class InfectiousProportion {

    private StateQuery sq;

    private Int2IntMap numInfectious, communitySize;
    private final TimeManager tm;
    private long currentDay;

    public InfectiousProportion(StateQuery sq, TimeManager tm) {
        this.sq = sq;
        numInfectious = new Int2IntOpenHashMap();
        communitySize = new Int2IntOpenHashMap();
        this.tm = tm;
        currentDay = -1l;
    }

    public double inCommunity(int locationID, InfectionState state) {
        ensureCacheUpdated(locationID,state);
        return (double) numInfectious.get(locationID) / (double) communitySize.get(locationID);
    }

    private void ensureCacheUpdated(int locationID,InfectionState state) {
//        if (tm.getTimeStep() != currentDay) {
//            currentDay = tm.getTimeStep();
//            numInfectious.clear();
//            communitySize.clear();
//        }

        numInfectious.clear();
        communitySize.clear();

        if (!numInfectious.containsKey(locationID) || !communitySize.containsKey(locationID)) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locationID);
            int infectiousPressure = localPop.getAllInState(state).size();
            int numberOfDead = localPop.getAllInState(DEAD_YOUNG).size() + localPop.getAllInState(DEAD_ADULT).size() + localPop.getAllInState(DEAD_ELDERLY).size();
            int numberInHospital = localPop.getAllInState(HOSPITALISED_YOUNG).size() + localPop.getAllInState(HOSPITALISED_ADULT).size() + localPop.getAllInState(HOSPITALISED_ELDERLY).size();
            int numberRecovered= localPop.getAllInState(RECOVERED_YOUNG).size() + localPop.getAllInState(RECOVERED_ADULT).size() + localPop.getAllInState(RECOVERED_ELDERLY).size();
            int numberToRemove = numberOfDead + numberInHospital + numberRecovered;
            int numberOfPeople = localPop.getAllPersonIds().size() - numberToRemove;
            numInfectious.put(locationID, infectiousPressure);
            communitySize.put(locationID, numberOfPeople);
        }
    }

}
