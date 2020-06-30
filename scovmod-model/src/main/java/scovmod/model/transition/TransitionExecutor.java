package scovmod.model.transition;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.config.Parameters;
import scovmod.model.state.StateManagementFactory;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.transition.infected.*;
import scovmod.model.transition.susceptible.InfectionPressure;
import scovmod.model.transition.susceptible.SusceptiblePersonTransitionExecutor;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.*;

public class TransitionExecutor {

    private StateQuery sq;
    private Random rnd;
    private StateModifier sm;

	private SusceptiblePersonTransitionExecutor sate;
    private ExposedToMildInfectiousTransitionExecutor eate;
    private ExposedToRecoveredTransitionExecutor eare;
    private MildInfectiousToSevereTransitionExecutor mitste;
    private MildInfectiousToRecoveredTransitionExecutor mitre;
    private SevereInfectiousToRecoveredTransitionExecutor sitrte;
    private SevereInfectiousToHospitalisedTransitionExecutor sithte;
    private SevereInfectiousToDeadTransitionExecutor sitdte;
    private HospitalisedToRecoveredTransitionExecutor htrte;
    private HospitalisedToDeadTransitionExecutor htdte;

    public TransitionExecutor(StateManagementFactory smf,
                              Random rnd,
                              Parameters params,
                              SusceptiblePersonTransitionExecutor sate,
                              ExposedToMildInfectiousTransitionExecutor eate,
                              ExposedToRecoveredTransitionExecutor eare,
                              MildInfectiousToSevereTransitionExecutor mitste,
                              MildInfectiousToRecoveredTransitionExecutor mitre,
                              SevereInfectiousToRecoveredTransitionExecutor sitrte,
                              SevereInfectiousToHospitalisedTransitionExecutor sithte,
                              SevereInfectiousToDeadTransitionExecutor sitdte,
                              HospitalisedToRecoveredTransitionExecutor htrte,
                              HospitalisedToDeadTransitionExecutor htdte) {
		this.sate = sate;
        this.sq = smf.getStateQuery();
        this.sm = smf.getStateModifier();
        this.rnd = rnd;
        this.eare = eare;
        this.mitste = mitste;
        this.mitre = mitre;
        this.sitrte = sitrte;
        this.sithte = sithte;
        this.sitdte = sitdte;
        this.htrte = htrte;
        this.htdte = htdte;
        this.eate = eate;
    }

    public void doSusceptibleTransitions(Int2ObjectMap<InfectionPressure> locationPressures) {
        for (int locationId : locationPressures.keySet()) {
            IntSet localSusceptibles = sq.getCopyOfLocalPopulation(locationId).getAllInState(SUSCEPTIBLE);
            if (localSusceptibles.isEmpty()) {
                continue;
            }
            InfectionPressure communityPressure = locationPressures.get(locationId);
            if (communityPressure.isNonZeroPressure()) {
                for (int personId : localSusceptibles) {
                    sate.apply(personId, communityPressure);
                }
            }
        }
    }

    public void doExposedToMildTransitions() {
        for (int locId : sq.getExposedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_YOUNG)) {
                eate.apply(personId);
            }
        }
        for (int locId : sq.getExposedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ADULT)) {
                eate.apply(personId);
            }
        }
        for (int locId : sq.getExposedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ELDERLY)) {
                eate.apply(personId);
            }
        }
    }

    public void doExposedToRecoveredTransitions() {
        for (int locId : sq.getExposedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_YOUNG)) {
                eare.apply(personId);
            }
        }
        for (int locId : sq.getExposedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ADULT)) {
                eare.apply(personId);
            }
        }
        for (int locId : sq.getExposedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ELDERLY)) {
                eare.apply(personId);
            }
        }
    }

    public void doMildToRecoveredTransitions() {
        for (int locId : sq.getMildInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_YOUNG)) {
                mitre.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ADULT)) {
                mitre.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ELDERLY)) {
                mitre.apply(personId);
            }
        }
    }

    public void doMildToSevereTransitions() {
        for (int locId : sq.getMildInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_YOUNG)) {
                mitste.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ADULT)) {
                mitste.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ELDERLY)) {
                mitste.apply(personId);
            }
        }
    }

    public void doSevereToRecoveredTransitions() {
        for (int locId : sq.getSevereInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG)) {
                sitrte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT)) {
                sitrte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY)) {
                sitrte.apply(personId);
            }
        }
    }

    public void doSevereToHospitalisedTransitions() {
        for (int locId : sq.getSevereInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG)) {
                sithte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT)) {
                sithte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY)) {
                sithte.apply(personId);
            }
        }
    }

    public void doSevereToDeadTransitions() {
        for (int locId : sq.getSevereInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG)) {
                sitdte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT)) {
                sitdte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY)) {
                sitdte.apply(personId);
            }
        }
    }

    public void doHospitalisedToRecoveredTransitions() {
        for (int locId : sq.getHospitalisedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_YOUNG)) {
                htrte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ADULT)) {
                htrte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ELDERLY)) {
                htrte.apply(personId);
            }
        }
    }

    public void doHospitalisedToDeadTransitions() {
        for (int locId : sq.getHospitalisedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_YOUNG)) {
                htdte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ADULT)) {
                htdte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ELDERLY)) {
                htdte.apply(personId);
            }
        }
    }
}
