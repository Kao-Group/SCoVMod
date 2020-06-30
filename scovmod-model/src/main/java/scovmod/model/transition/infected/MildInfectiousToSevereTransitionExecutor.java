package scovmod.model.transition.infected;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.*;

public class MildInfectiousToSevereTransitionExecutor {

    private Random rnd;
    private double timeStep;
    private final Parameters params;
    private StateModifier sm;
    private final Int2ObjectMap<AgeClass> peopleAgeClasses;

    public MildInfectiousToSevereTransitionExecutor(
            StateModifier sm,
            Random rnd,
            double timeStep,
            Parameters params,
            StartLocationsAndAgeClasses slaac
    ) {
        this.rnd = rnd;
        this.sm = sm;
        this.timeStep = timeStep;
        this.params = params;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
    }

    public void apply(int personID) {
        AgeClass ageClass = peopleAgeClasses.getOrDefault(personID,AgeClass.YOUNG);
            //Get what age group the person is in
            switch(ageClass){
                case YOUNG:
                    if (rnd.nextPoissonReturnsOneOrMore(params.getMiToSI_YoungRate() * timeStep)) {
                        sm.updateInfectionState(personID, SEVERE_INFECTIOUS_YOUNG);
                    }
                    break;
                case ADULT:
                    if (rnd.nextPoissonReturnsOneOrMore(params.getMiToSI_AdultRate() * timeStep)) {
                        sm.updateInfectionState(personID, SEVERE_INFECTIOUS_ADULT);
                    }
                    break;
                case ELDERLY:
                    if (rnd.nextPoissonReturnsOneOrMore(params.getMiToSI_ElderlyRate() * timeStep)) {
                        sm.updateInfectionState(personID, SEVERE_INFECTIOUS_ELDERLY);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for severe infectious event");
            }
    }
}