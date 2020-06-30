/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition.susceptible;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.config.Parameters;
import scovmod.model.state.InfectiousProportion;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.transition.BetaRates;

public class LocalPersonPressure {

    private StateQuery sq;
    private Parameters params;
    private InfectiousProportion ip;
    private BetaRates br;

    public LocalPersonPressure(StateQuery sq, Parameters params, InfectiousProportion ip, BetaRates br) {
        this.sq = sq;
        this.params = params;
        this.ip = ip;
        this.br = br;
    }

    public void addLocalPressure(Int2ObjectMap<InfectionPressure> pressureMap) {
        for (int infLocId : sq.getMildInfectiousYoungLocations()) {
            double localPressure = br.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG) * ip.inCommunity(infLocId, InfectionState.MILD_INFECTIOUS_YOUNG);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentPeoplePressure(localPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(0, localPressure));
        }
        for (int infLocId : sq.getMildInfectiousAdultLocations()) {
            double localPressure = br.getBeta(InfectionState.MILD_INFECTIOUS_ADULT) * ip.inCommunity(infLocId, InfectionState.MILD_INFECTIOUS_ADULT);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentPeoplePressure(localPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(0, localPressure));
        }
        for (int infLocId : sq.getMildInfectiousElderlyLocations()) {
            double localPressure = br.getBeta(InfectionState.MILD_INFECTIOUS_ELDERLY) * ip.inCommunity(infLocId, InfectionState.MILD_INFECTIOUS_ELDERLY);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentPeoplePressure(localPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(0, localPressure));
        }
        for (int infLocId : sq.getSevereInfectiousYoungLocations()) {
            double localPressure = br.getBeta(InfectionState.SEVERE_INFECTIOUS_YOUNG) * ip.inCommunity(infLocId, InfectionState.SEVERE_INFECTIOUS_YOUNG);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentPeoplePressure(localPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(0, localPressure));
        }
        for (int infLocId : sq.getSevereInfectiousAdultLocations()) {
            double localPressure = br.getBeta(InfectionState.SEVERE_INFECTIOUS_ADULT) * ip.inCommunity(infLocId, InfectionState.SEVERE_INFECTIOUS_ADULT);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentPeoplePressure(localPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(0, localPressure));
        }
        for (int infLocId : sq.getSevereInfectiousElderlyLocations()) {
            double localPressure = br.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY) * ip.inCommunity(infLocId, InfectionState.SEVERE_INFECTIOUS_ELDERLY);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentPeoplePressure(localPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(0, localPressure));
        }
    }

}
