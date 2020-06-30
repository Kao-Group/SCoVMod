package scovmod.model.transition.susceptible;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateModifier;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.*;

public class SusceptiblePersonTransitionExecutor {
    private final StateModifier sm;
    private final Random rnd;
    private double timeStepInDays;
    private final StatisticsCollector stats;
    private final Int2ObjectMap<AgeClass> peopleAgeClasses;

    public SusceptiblePersonTransitionExecutor(StateModifier sm,
                                               Random rnd,
                                               double timeStepInDays,
                                               StatisticsCollector stats,
                                               StartLocationsAndAgeClasses slaac) {
        this.sm = sm;
        this.rnd = rnd;
        this.timeStepInDays = timeStepInDays;
        this.stats = stats;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
    }

    public void apply(int personID, InfectionPressure communityPressure) {
        TransmissionEventType outcome = communityPressure.evaluate(rnd, timeStepInDays);
        AgeClass ageClass = peopleAgeClasses.get(personID);
        if(outcome!= TransmissionEventType.NULL) {
            //Get what age group the person is in
            switch(ageClass){
                case YOUNG:
                    sm.updateInfectionState(personID, EXPOSED_YOUNG);
                    break;
                case ADULT:
                    sm.updateInfectionState(personID, EXPOSED_ADULT);
                    break;
                case ELDERLY:
                    sm.updateInfectionState(personID, EXPOSED_ELDERLY);
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for exposed event");
            }
        }
    }
}
