/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.setup;

//import scovmod.model.input.seeding.SeedManager;

import scovmod.model.seeding.SeedManager;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.state.StateManagementFactory;
import scovmod.model.state.StateQuery;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.util.math.Random;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Initialiser {

    private StateQuery sq;
    private InitialLocations initialLocations;
    private Random random;
    private TimeManager tm;
    private TimeConversions tc;
    private InitialSeeder is;
    private SeedManager sm;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Initialiser(
            StateManagementFactory smf,
            InitialLocations initLocations,
            Random random,
            TimeManager tm,
            TimeConversions tc,
            InitialSeeder is,
            SeedManager sm) {
        this.initialLocations = initLocations;
        this.random = random;
        this.tm = tm;
        this.tc = tc;
        this.is = is;
        this.sq = smf.getStateQuery();
        this.sm = sm;
    }

    public void seedPeople(ConfigParameters params, int stochasticIncrement) {
        Int2ObjectMap<IntSet> peopleByLocation = new Int2ObjectOpenHashMap<>();
        Int2ObjectMap<InfectionState> peopleStates = new Int2ObjectOpenHashMap<>(5400000);

        initialLocations.setup(peopleByLocation);
        sm.seedPeopleInfections(peopleStates, peopleByLocation);
        is.commit(peopleStates);
        if (log.isDebugEnabled()) {
            int noE_Y = 0;
            int noE_A = 0;
            int noE_E = 0;
            int noMI_Y = 0;
            int noMI_A = 0;
            int noMI_E = 0;
            int noSI_Y = 0;
            int noSI_A = 0;
            int noSI_E = 0;
            int noH_Y = 0;
            int noH_A = 0;
            int noH_E = 0;
            int noR_Y = 0;
            int noR_A = 0;
            int noR_E = 0;
            int noD_Y = 0;
            int noD_A = 0;
            int noD_E = 0;

            int totalInfected = 0;
            int totalExposed = 0;
            int totalSusceptible = 0;
            int totalRecovered = 0;
            int totalDead = 0;
            int totalPopulation = 0;

            for (int location : sq.getAllActiveLocationIds()) {
                LocalPopulation localPop = sq.getCopyOfLocalPopulation(location);
                totalSusceptible += localPop.getAllInState(InfectionState.SUSCEPTIBLE).size();
                noE_Y += localPop.getAllInState(InfectionState.EXPOSED_YOUNG).size();
                noE_A += localPop.getAllInState(InfectionState.EXPOSED_ADULT).size();
                noE_E += localPop.getAllInState(InfectionState.EXPOSED_ELDERLY).size();
                noMI_Y += localPop.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG).size();
                noMI_A += localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT).size();
                noMI_E += localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY).size();
                noSI_Y += localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_YOUNG).size();
                noSI_A += localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ADULT).size();
                noSI_E += localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ELDERLY).size();
                noH_Y += localPop.getAllInState(InfectionState.HOSPITALISED_YOUNG).size();
                noH_A += localPop.getAllInState(InfectionState.HOSPITALISED_ADULT).size();
                noH_E += localPop.getAllInState(InfectionState.HOSPITALISED_ELDERLY).size();
                noR_Y += localPop.getAllInState(InfectionState.RECOVERED_YOUNG).size();
                noR_A += localPop.getAllInState(InfectionState.RECOVERED_ADULT).size();
                noR_E += localPop.getAllInState(InfectionState.RECOVERED_ELDERLY).size();
                noD_Y += localPop.getAllInState(InfectionState.DEAD_YOUNG).size();
                noD_A += localPop.getAllInState(InfectionState.DEAD_ADULT).size();
                noD_E += localPop.getAllInState(InfectionState.DEAD_ELDERLY).size();
                totalInfected = noE_Y + noE_A + noE_E + noMI_Y + noMI_A + noMI_E + noSI_Y + noSI_A + noSI_E + noH_Y + noH_A + noH_E;
                totalExposed = noE_Y + noE_A + noE_E;
                totalRecovered = noR_Y + noR_A + noR_E;
                totalDead = noD_Y + noD_A + noD_E;
            }
            log.debug("Seeding people Susceptibles:{}, Exposed:{}, Infectious:{}, Recovered:{}, Dead:{}", totalSusceptible, totalExposed, totalInfected, totalRecovered, totalDead);
//            log.debug("Number exposed: " + totalExposed);
//            log.debug("Number number exposed young : " + noE_Y);
//            log.debug("Number number exposed adult : " + noE_A);
//            log.debug("Number number exposed elderly : " + noE_E);
//            log.debug("Number infectious: " + totalInfected);
//            log.debug("Number number mild young : " + noMI_Y);
//            log.debug("Number number mild adult : " + noMI_A);
//            log.debug("Number number mild elderly : " + noMI_E);
//            log.debug("Number number severe young : " + noSI_Y);
//            log.debug("Number number severe adult : " + noSI_A);
//            log.debug("Number number severe elderly : " + noSI_E);
//            log.debug("Number recovered: " + totalRecovered);
//            log.debug("Number number recovered young : " + noR_Y);
//            log.debug("Number number recovered adult : " + noR_A);
//            log.debug("Number number recovered elderly : " + noR_E);
//            log.debug("Number number hospitalised young : " + noH_Y);
//            log.debug("Number number hospitalised adult : " + noH_A);
//            log.debug("Number number hospitalised elderly : " + noH_E);
//            log.debug("Number Dead: " + totalDead);
//            log.debug("Number number dead young : " + noD_Y);
//            log.debug("Number number dead adult : " + noD_A);
//            log.debug("Number number dead elderly : " + noD_E);
        }
    }

//    public void setupRoutineSkinTests() {
//        Set<ScheduledTest> scheduledTests = new HashSet<>();
//        for (int location : pl.getAllFarms()) {
//            double ptiInterval = 4;
//            PTIInterval ptiValue = ptiLookup.getPTI(location);
//            switch (ptiValue) {
//                case SIX_MONTHLY:
//                    ptiInterval = 0.5;
//                    break;
//                case ANNUAL:
//                    ptiInterval = 1;
//                    break;
//                case TWO_YEARLY:
//                    ptiInterval = 2;
//                    break;
//                case FOUR_YEARLY:
//                    ptiInterval = 4;
//                    break;
//                default:
//                    throw new ModelException("Unexpected pti Interval");
//            }
//            int dayRange = (int) (ptiInterval * 365);
//            int randomNumberDays = random.nextUniformInteger(0, dayRange);
//            if (randomNumberDays < 15) {
//                randomNumberDays = 15;
//            }
//            long currentTimeStep = tm.getTimeStep();
//            long scheduledTimeStep = tc.addChronoUnitsToStep(currentTimeStep, randomNumberDays, ChronoUnit.DAYS);
//
//            switch (ptiValue) {
//                case SIX_MONTHLY:
//                    scheduledTests.add(new ScheduledLocationTest(location, scheduledTimeStep, ScheduledTestType.ROUTINE_SICCT_PTI_SIX_MONTHLY));
//                    break;
//                case ANNUAL:
//                    scheduledTests.add(new ScheduledLocationTest(location, scheduledTimeStep, ScheduledTestType.ROUTINE_SICCT_PTI1));
//                    break;
//                case TWO_YEARLY:
//                    scheduledTests.add(new ScheduledLocationTest(location, scheduledTimeStep, ScheduledTestType.ROUTINE_SICCT_PTI2));
//                    break;
//                case FOUR_YEARLY:
//                    scheduledTests.add(new ScheduledLocationTest(location, scheduledTimeStep, ScheduledTestType.ROUTINE_SICCT_PTI4));
//                    break;
//                default:
//                    throw new ModelException("Unexpected pti Interval");
//            }
//        }
//        tl.scheduleTests(scheduledTests);
//    }
}
