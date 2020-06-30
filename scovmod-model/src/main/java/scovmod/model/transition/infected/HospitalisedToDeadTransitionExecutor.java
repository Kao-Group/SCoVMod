package scovmod.model.transition.infected;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.transition.DeathRates;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.*;

public class HospitalisedToDeadTransitionExecutor {

    private Random rnd;
    private double timeStep;
    private final Parameters params;
    private StateModifier sm;
    private final Int2ObjectMap<AgeClass> peopleAgeClasses;
    private final DeathRates dr;


    public HospitalisedToDeadTransitionExecutor(
            StateModifier sm,
            Random rnd,
            double timeStep,
            Parameters params,
            StartLocationsAndAgeClasses slaac,
            DeathRates dr) {
        this.rnd = rnd;
        this.sm = sm;
        this.timeStep = timeStep;
        this.params = params;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
        this.dr = dr;
    }

    public void apply(int personID) {
        AgeClass ageClass = peopleAgeClasses.get(personID);
        double deathRateModPerHB = dr.getDeathRateModifierBasedOnHealthIndex(personID);

        //Get what age group the person is in
            switch(ageClass){
                case YOUNG:
                    if (rnd.nextPoissonReturnsOneOrMore(params.gethToD_YoungRate() * deathRateModPerHB * timeStep)) {
                        sm.updateInfectionState(personID, DEAD_YOUNG);
                    }
                    break;
                case ADULT:
                    if (rnd.nextPoissonReturnsOneOrMore(params.gethToD_AdultRate() * deathRateModPerHB * timeStep)) {
                        sm.updateInfectionState(personID, DEAD_ADULT);
                    }
                    break;
                case ELDERLY:
                    if (rnd.nextPoissonReturnsOneOrMore(params.gethToD_ElderlyRate() * deathRateModPerHB * timeStep)) {
                        sm.updateInfectionState(personID, DEAD_ELDERLY);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for death event");
            }
    }
}