package scovmod.model.transition.susceptible;

import java.util.Set;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.state.StateQuery;
import scovmod.model.transition.BetaRates;

public class VisitorPressure {

    private StateQuery sq;
    private BetaRates br;

    public VisitorPressure(StateQuery sq, BetaRates br) {
        this.sq = sq;
        this.br = br;
    }

    public void addVisitorPresssure(Set<LocationIncomingPersons> timeStepIncomingPersons, Int2ObjectMap<InfectionPressure> pressureMap) {

//        for (LocationIncomingPersons incomingAni : timeStepIncomingPersons) { //Note: No trans movement for now - so comment out
//            int locationId = incomingAni.getLocationId();
//            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locationId);
//            int numberOfDead = localPop.getAllInState(DEAD_YOUNG).size() + localPop.getAllInState(DEAD_ADULT).size() + localPop.getAllInState(DEAD_ELDERLY).size();
//            int numberInHospital = localPop.getAllInState(HOSPITALISED_YOUNG).size() + localPop.getAllInState(HOSPITALISED_ADULT).size() + localPop.getAllInState(HOSPITALISED_ELDERLY).size();
//            int numberRecovered= localPop.getAllInState(RECOVERED_YOUNG).size() + localPop.getAllInState(RECOVERED_ADULT).size() + localPop.getAllInState(RECOVERED_ELDERLY).size();
//            int numberToRemove = numberOfDead + numberInHospital + numberRecovered;
//            double locationSize = (double) sq.getLocationSize(locationId) - numberToRemove; //Is this necessary?
//            //double amount = beta / (double) sq.getLocationSize(locationId); //Remember remove dead/hospitalised/recovered?
//            for (int visitorId : incomingAni.getTransitoryVisitors()) {
//                double amount = br.getBeta(visitorId) /locationSize;
//                InfectionState visitorState = sq.getPersonInfectionStatus(visitorId);
//                if ( visitorState == MILD_INFECTIOUS_YOUNG
//                || visitorState == MILD_INFECTIOUS_ADULT
//                || visitorState == MILD_INFECTIOUS_ELDERLY
//                || visitorState == SEVERE_INFECTIOUS_YOUNG
//                || visitorState == SEVERE_INFECTIOUS_ADULT
//                 || visitorState == SEVERE_INFECTIOUS_ELDERLY ) {
//                    if (pressureMap.containsKey(locationId))
//                        pressureMap.put(locationId, pressureMap.get(locationId).augmentPeoplePressure(amount));
//                    else
//                        pressureMap.put(locationId, new InfectionPressure(0, amount));
//                }
//            }
//        }
    }

}
