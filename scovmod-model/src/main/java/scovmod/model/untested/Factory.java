/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.untested;

import scovmod.model.Model;
import scovmod.model.input.*;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.InputData;
import scovmod.model.input.config.Parameters;
//import scovmod.model.input.seeding.SeedManager;
import scovmod.model.movements.MovementEventManager;
import scovmod.model.movements.Mover;
import scovmod.model.movements.Resolver;
import scovmod.model.movements.MovementStepper;
import scovmod.model.movements.MovementHandler;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.output.IOutputModule;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.seeding.*;
import scovmod.model.setup.InitialLocations;
import scovmod.model.setup.InitialSeeder;
import scovmod.model.setup.Initialiser;
import scovmod.model.state.InfectiousProportion;
import scovmod.model.state.StateManagementFactory;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.time.TaskTimeManager;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;
import scovmod.model.transition.*;
import scovmod.model.transition.infected.*;
import scovmod.model.transition.susceptible.SusceptiblePersonTransitionExecutor;
import scovmod.model.util.math.Random;

public class Factory {

    public static Model buildModel(
            InputData data, 
            ConfigParameters config, 
            Parameters params,
            IOutputModule out) {
        Random rnd = Random.buildRandom();

        String modelId = randomName(rnd);

        StateManagementFactory smf = new StateManagementFactory(rnd);
        StateModifier sm = smf.getStateModifier();
        StateQuery sq = smf.getStateQuery();

        int timeStepInDays = MovementTimeStepReader.getStochasticIncrement(data.getMovementDir());
        TimeConversions tcv = new TimeConversions(timeStepInDays);

        double tauLeapTimeStep = config.getTauLeapStep();

        //config.initTime(tcv);

        long startTimeStep = config.getFirstTimeStep();
        TimeManager timeMgr = new TimeManager(startTimeStep);

        MovementStepper movementStepper = MovementStepper.build(config, data);
        HealthBoardLookup hbl = new HealthBoardLookup(data.getAreaToHBLookupFile());
        StatisticsCollector stats = new StatisticsCollector(out, timeMgr,  tcv,  sq, hbl);
        TaskTimeManager ttm = new TaskTimeManager( config, timeMgr);

        MovementHandler mh = new MovementHandler(sm, stats, sq);
        Mover mover = new Mover(mh,stats);
        Resolver resolver = new Resolver(sq);
        MovementEventManager moverMgr = new MovementEventManager(resolver,mover);

        StartLocationsAndAgeClasses asl = new StartLocationsAndAgeClasses(data.getPeopleStartLocationsFile());
        SpatialSeedingGroupAttributes ssga = new SpatialSeedingGroupAttributes(data.getSpatialSeedingGroupFileName());

        SpatialGroupMultinomial sgs = new SpatialGroupMultinomial(params,rnd,ssga);
        LocationShuffler fs = new LocationShuffler(hbl, rnd);
        WithinLocationSampler as = new WithinLocationSampler(ssga, rnd, params);
        WithinGroupSampler asbg = new WithinGroupSampler(fs,as);
        NationalSamplerFactory asf = new NationalSamplerFactory(sgs,asbg);
        SeedManager smr = new SeedManager(asl, asf, rnd);

        //InfectedSeeds ifs = new InfectedSeeds(data.getInfectedSeedsFile());
        InfectiousProportion ip = new InfectiousProportion(sq,timeMgr);
        SusceptiblePersonTransitionExecutor sate = new SusceptiblePersonTransitionExecutor(sm,rnd,tauLeapTimeStep,stats,asl);
        BetaRates br = new BetaRates(params, config, sq, timeMgr);
        DeprivationIndexPerHealthBoard diph = new DeprivationIndexPerHealthBoard(data.getHealthIndexFile());
        DeathRates dr = new DeathRates(params,diph,sq,hbl);
        ExposedToMildInfectiousTransitionExecutor eate= new ExposedToMildInfectiousTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        ExposedToRecoveredTransitionExecutor eare = new ExposedToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        MildInfectiousToSevereTransitionExecutor mitste = new MildInfectiousToSevereTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        MildInfectiousToRecoveredTransitionExecutor mitre = new MildInfectiousToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        SevereInfectiousToRecoveredTransitionExecutor sitrte = new SevereInfectiousToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        SevereInfectiousToHospitalisedTransitionExecutor sithte = new SevereInfectiousToHospitalisedTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        SevereInfectiousToDeadTransitionExecutor sitdte = new SevereInfectiousToDeadTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl, dr);
        HospitalisedToRecoveredTransitionExecutor htrte = new HospitalisedToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        HospitalisedToDeadTransitionExecutor htdte = new HospitalisedToDeadTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl, dr);

        TransitionExecutor te = new TransitionExecutor(smf, rnd,params, sate,eate,eare,mitste,mitre,sitrte,sithte,sitdte,htrte,htdte);
        TransitionManager tm = TransitionManager.build(sq, params, te, ip,br);
        InitialLocations il = new InitialLocations(asl);
        InitialSeeder is = new InitialSeeder(asl, smf);
        //SeedManager smr = new SeedManager(asl,ifs); //Old version

        Initialiser initialiser = new Initialiser(smf, il, rnd, timeMgr, tcv,  is, smr);

        return new Model(
                modelId,
                initialiser,
                config,
                movementStepper,
                timeMgr,
                sq,
                moverMgr,
                tm,
                stats,
                tcv,
                ttm,
                hbl);
    }

    public static String randomName(Random random) {
        char[] word = new char[random.nextUniformInteger(3,6)];
        for(int j = 0; j < word.length; j++)
        {
            word[j] = (char)('a' + random.nextUniformInteger(0, 25));
        }
        return new String(word);
    }
}
