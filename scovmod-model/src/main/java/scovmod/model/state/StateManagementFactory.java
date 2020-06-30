package scovmod.model.state;

import scovmod.model.state.infection.InfectionTransitions;
import scovmod.model.state.cache.CacheManager;
import scovmod.model.state.cache.exposed.*;
import scovmod.model.state.cache.dead.*;
import scovmod.model.state.cache.hospitalised.*;
import scovmod.model.state.cache.recovered.*;
import scovmod.model.state.cache.mildInfectious.*;
import scovmod.model.state.cache.severeInfectious.*;
import scovmod.model.state.coordination.InfectionStateCoordinator;
import scovmod.model.state.coordination.MovementCoordinator;
import scovmod.model.state.population.LocalPopulationBank;
import scovmod.model.state.population.LocalPopulationIndex;
import scovmod.model.util.math.Random;

public class StateManagementFactory {

    private StateModifier sm = null;
    private StateQuery sq = null;

    public StateManagementFactory(Random rnd) {
        LocalPopulationBank lpb = new LocalPopulationBank();
        LocalPopulationIndex lpi = new LocalPopulationIndex(lpb);

        ExposedYoungLocations eyl = new ExposedYoungLocations();
        ExposedAdultLocations eal = new ExposedAdultLocations();
        ExposedElderlyLocations eel = new ExposedElderlyLocations();

        DeadYoungLocations dyl = new DeadYoungLocations();
        DeadAdultLocations dal = new DeadAdultLocations();
        DeadElderlyLocations del = new DeadElderlyLocations();

        HospitalisedYoungLocations hyl = new HospitalisedYoungLocations();
        HospitalisedAdultLocations hal = new HospitalisedAdultLocations();
        HospitalisedElderlyLocations hel = new HospitalisedElderlyLocations();

        RecoveredYoungLocations ryl = new RecoveredYoungLocations();
        RecoveredAdultLocations ral = new RecoveredAdultLocations();
        RecoveredElderlyLocations rel = new RecoveredElderlyLocations();

        MildInfectiousYoungLocations miyl = new MildInfectiousYoungLocations();
        MildInfectiousAdultLocations mial = new MildInfectiousAdultLocations();
        MildInfectiousElderlyLocations miel = new MildInfectiousElderlyLocations();

        SevereInfectiousYoungLocations siyl = new SevereInfectiousYoungLocations();
        SevereInfectiousAdultLocations sial = new SevereInfectiousAdultLocations();
        SevereInfectiousElderlyLocations siel = new SevereInfectiousElderlyLocations();

        CacheManager cm = new CacheManager(lpi, eyl, eal, eel, miyl, mial, miel, siyl, sial, siel, hyl, hal, hel, ryl, ral, rel, dyl, dal,del);
        MovementCoordinator mc = new MovementCoordinator(lpi, cm);
        InfectionTransitions it = new InfectionTransitions();
        InfectionStateCoordinator isc = new InfectionStateCoordinator(lpi, it, cm);

        sm = new StateModifier(mc, isc);
        sq = new StateQuery(lpi, eyl, eal, eel, dyl, dal,del, hyl, hal, hel, ryl, ral, rel, miyl, mial, miel, siyl, sial, siel, rnd);
    }

    public StateModifier getStateModifier() {
        return sm;
    }

    public StateQuery getStateQuery() {
        return sq;
    }
}
