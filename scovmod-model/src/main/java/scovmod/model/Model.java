package scovmod.model;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.MovementEventManager;
import scovmod.model.movements.MovementStepper;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.output.IResult;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.setup.Initialiser;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.time.TaskTimeManager;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;
import scovmod.model.transition.TransitionManager;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Model {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String modelId;
    private final Initialiser initialiser;
    private final ConfigParameters config;
    private final MovementStepper movements;
    private final TimeManager time;
    private final StateQuery stateQuery;
    private final MovementEventManager mover;
    private final TransitionManager transitionManager;
    private final StatisticsCollector stats;
    private final TimeConversions tcv;
    private final TaskTimeManager ttm;
    private HealthBoardLookup hbl;
    private Int2IntMap laLookupMap;

    public Model(
            String modelId,
            Initialiser initialiser,
            ConfigParameters config,
            MovementStepper movements,
            TimeManager time,
            StateQuery stateQuery,
            MovementEventManager mover,
            TransitionManager transitionManager,
            StatisticsCollector stats,
            TimeConversions tcv,
            TaskTimeManager ttm,
            HealthBoardLookup hbl) {
        this.modelId = modelId;
        this.initialiser = initialiser;
        this.config = config;
        this.movements = movements;
        this.time = time;
        this.stateQuery = stateQuery;
        this.mover = mover;
        this.transitionManager = transitionManager;
        this.stats = stats;
        this.tcv = tcv;
        this.ttm = ttm;
        laLookupMap = hbl.getLALookupMap();
        this.hbl = hbl;
    }

    public IResult run() {

        //Initialisation
        initialiser.seedPeople(config, movements.getTimeStepSize());
        // initialiser.setupRoutineSkinTests();
        final long endTimeInit = System.currentTimeMillis();

        while (time.getTimeStep() < config.getLastTimeStep()) {
            final long startTimeStep = System.currentTimeMillis();

            if (log.isDebugEnabled()) {
                log.debug("There are currently " + stateQuery.getAllInfectiousLocations().size()
                        + " mild or severe infectious communities in time step " + time.getTimeStep());
                log.debug("There are currently " + stateQuery.getAllHospitalisedLocations().size()
                        + " communities with hospitalised in time step " + time.getTimeStep());
            }

            //Get movements for this timestep
            Set<LocationIncomingPersons> personsMoving = movements.getNextTimeStepMovements().getMovements();
//            int personmovingCount = 0;
//            for(LocationIncomingPersons lip:personsMoving){
//                personmovingCount += lip.getNewPersonIds().size();
//            }
//            System.out.println("There are : "+personmovingCount+" people moving in this timestep");

            //Process movements for this timestep
            final long startTimeMoves = System.currentTimeMillis();
            IntSet allExposedLocations = stateQuery.getAllExposedLocations();
            IntSet allMildInfectiousLocations = stateQuery.getAllMildInfectiousLocations();
            IntSet allSevereInfectiousLocations = stateQuery.getAllSevereInfectiousLocations();

            mover.doMovements(personsMoving,allExposedLocations,allMildInfectiousLocations,allSevereInfectiousLocations);
            final long endTimeMoves = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("Total execution time for executing movements: " + (double) (endTimeMoves - startTimeMoves) / 1000);
            }

            //Process disease transition movements, including pressure from 'visiting' people
            final long startTimePeopleTransitions = System.currentTimeMillis();
            transitionManager.doTransitions(personsMoving);
            final long endTimePeopleTransitions = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("Total execution time for processing people state transitions: " + (double) (endTimePeopleTransitions - startTimePeopleTransitions) / 1000);
            }

            final long endTimeStep = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("******* Total execution time for time step: " + (double) (endTimeStep - startTimeStep) / 1000);
            }

            if ( (time.getTimeStep() - config.getFirstTimeStep()) % 14 == 0 ) totalStatesReporting(); //NOTE - Very Important: Only actually call every 14th time step to increment daily total.

            time.advanceOneStep();
            //stats.newTimeStep();
        }
        return stats.buildResult();
    }

    private void totalStatesReporting() {
        int totalInfectious = 0;
        int totalRecovered = 0;
        int totalDead = 0;
        int totalPopulation = 0;
        int totalExposed = 0;
        int totalHospitalised = 0;
        for (int location : stateQuery.getAllActiveLocationIds()) {
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
            int noSus = 0;
            LocalPopulation localPop = stateQuery.getCopyOfLocalPopulation(location);
            noSus = localPop.getAllInState(InfectionState.SUSCEPTIBLE).size();
            noE_Y = localPop.getAllInState(InfectionState.EXPOSED_YOUNG).size();
            if(noE_Y>0) {
                stats.currentExposedYoung(noE_Y ,location);
                stats.currentExposedYoungHB(noE_Y ,location);
            }
            noE_A = localPop.getAllInState(InfectionState.EXPOSED_ADULT).size();
            if(noE_A>0) {
                stats.currentExposedAdult(noE_A ,location);
                stats.currentExposedAdultHB(noE_A ,location);
            }
            noE_E = localPop.getAllInState(InfectionState.EXPOSED_ELDERLY).size();
            if(noE_E>0) {
                stats.currentExposedElderly(noE_E ,location);
                stats.currentExposedElderlyHB(noE_E ,location);
            }
            if(noE_Y + noE_A + noE_E >0) {
                stats.currentExposed(noE_Y + noE_A + noE_E ,location);
                stats.currentExposedHB(noE_Y + noE_A + noE_E ,location);
            }
            noMI_Y = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG).size();
            if(noMI_Y>0) {
                stats.currentMildInfectiousYoung(noMI_Y,location);
                stats.currentMildInfectiousYoungHB(noMI_Y,location);
            }
            noMI_A = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT).size();
            if(noMI_A>0) {
                stats.currentMildInfectiousAdult(noMI_A,location);
                stats.currentMildInfectiousAdultHB(noMI_A,location);
            }
            noMI_E = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY).size();
            if(noMI_E>0) {
                stats.currentMildInfectiousElderly(noMI_E,location);
                stats.currentMildInfectiousElderlyHB(noMI_E,location);
            }
            if(noMI_Y+noMI_A+noMI_E >0)  {
                stats.currentMildInfectious(noMI_Y+noMI_A+noMI_E,location);
                stats.currentMildInfectiousHB(noMI_Y+noMI_A+noMI_E,location);
            }
            noSI_Y = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_YOUNG).size();
            if(noSI_Y>0) {
                stats.currentSevereInfectiousYoung(noSI_Y,location);
                stats.currentSevereInfectiousYoungHB(noSI_Y,location);
            }
            noSI_A = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ADULT).size();
            if(noSI_A>0) {
                stats.currentSevereInfectiousAdult(noSI_A,location);
                stats.currentSevereInfectiousAdultHB(noSI_A,location);
            }
            noSI_E = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ELDERLY).size();
            if(noSI_E>0) {
                stats.currentSevereInfectiousElderly(noSI_E,location);
                stats.currentSevereInfectiousElderlyHB(noSI_E,location);
            }
            if(noSI_Y+noSI_A+noSI_E>0) {
                stats.currentSevereInfectious(noSI_Y+noSI_A+noSI_E,location);
                stats.currentSevereInfectiousHB(noSI_Y+noSI_A+noSI_E,location);
            }
            if(noMI_Y+noMI_A+noMI_E+noSI_Y+noSI_A+noSI_E>0) {
                stats.currentInfectious(noMI_Y+noMI_A+noMI_E+noSI_Y+noSI_A+noSI_E,location);
                stats.currentInfectiousHB(noMI_Y+noMI_A+noMI_E+noSI_Y+noSI_A+noSI_E,location);
            }
            noH_Y = localPop.getAllInState(InfectionState.HOSPITALISED_YOUNG).size();
            if(noH_Y>0) {
                stats.currentHospitalisedYoung(noH_Y,location);
                stats.currentHospitalisedYoungHB(noH_Y,location);
            }
            noH_A = localPop.getAllInState(InfectionState.HOSPITALISED_ADULT).size();
            if(noH_A>0) {
                stats.currentHospitalisedAdult(noH_A,location);
                stats.currentHospitalisedAdultHB(noH_A,location);
            }
            noH_E = localPop.getAllInState(InfectionState.HOSPITALISED_ELDERLY).size();
            if(noH_E>0) {
                stats.currentHospitalisedElderly(noH_E,location);
                stats.currentHospitalisedElderlyHB(noH_E,location);
            }
            if(noH_Y+noH_A+noH_E>0) {
                stats.currentHospitalised(noH_Y+noH_A+noH_E,location);
                stats.currentHospitalisedHB(noH_Y+noH_A+noH_E,location);
            }
            noR_Y = localPop.getAllInState(InfectionState.RECOVERED_YOUNG).size();
            if(noR_Y>0) {
                stats.currentRecoveredYoung(noR_Y,location);
                stats.currentRecoveredYoungHB(noR_Y,location);
            }
            noR_A = localPop.getAllInState(InfectionState.RECOVERED_ADULT).size();
            if(noR_A>0) {
                stats.currentRecoveredAdult(noR_A,location);
                stats.currentRecoveredAdultHB(noR_A,location);
            }
            noR_E = localPop.getAllInState(InfectionState.RECOVERED_ELDERLY).size();
            if(noR_E>0) {
                stats.currentRecoveredElderly(noR_E,location);
                stats.currentRecoveredElderlyHB(noR_E,location);
            }
            if(noR_Y+noR_A+noR_E>0)  {
                stats.currentRecovered(noR_Y+noR_A+noR_E,location);
                stats.currentRecoveredHB(noR_Y+noR_A+noR_E,location);
            }
            noD_Y = localPop.getAllInState(InfectionState.DEAD_YOUNG).size();
            if(noD_Y>0) {
                stats.currentDeadYoung(noD_Y,location);
                stats.currentDeadYoungHB(noD_Y,location);
            }
            noD_A = localPop.getAllInState(InfectionState.DEAD_ADULT).size();
            if(noD_A>0) {
                stats.currentDeadAdult(noD_A,location);
                stats.currentDeadAdultHB(noD_A,location);
            }
            noD_E = localPop.getAllInState(InfectionState.DEAD_ELDERLY).size();
            if(noD_E>0) {
                stats.currentDeadElderlyHB(noD_E,location);
            }
           // if(noD_Y+noD_A+noD_E>0) { // Actually want zeroes - otherwise mucks up metric in fit with missing days
            stats.currentDead(noD_Y+noD_A+noD_E,location);
            stats.currentDeadHB(noD_Y+noD_A+noD_E,location);
            stats.currentDeadCA(noD_Y+noD_A+noD_E,location);
           // }
            totalInfectious +=  noMI_Y + noMI_A + noMI_E + noSI_Y + noSI_A + noSI_E;// + noH_Y + noH_A + noH_E;
            totalRecovered += noR_Y + noR_A + noR_E;
            totalDead += noD_Y + noD_A + noD_E;
            totalExposed += noE_Y + noE_A + noE_E;
            totalHospitalised += noH_Y + noH_A + noH_E;

            //This part for infected LA's per HB
            if(noD_Y > 0 || noD_A > 0 || noD_E > 0) {
                int dz = hbl.getDZAreaFromOA(location);
                stats.currentNumberDZAreasWithDeadPerHealthBoard(dz,location);
            }
        }


       // totalPopulation = noSus + totalExposed + totalInfectious + totalRecovered - totalDead;

      //  log.debug("Number susceptible: " + noSus);
        log.debug("Number exposed: " + totalExposed);
        /*log.debug("Number number exposed young : " + noE_Y);
        log.debug("Number number exposed adult : " + noE_A);
        log.debug("Number number exposed elderly : " + noE_E);*/
        log.debug("Number infectious: " + totalInfectious);
       /* log.debug("Number number mild young : " + noMI_Y);
        log.debug("Number number mild adult : " + noMI_A);
        log.debug("Number number mild elderly : " + noMI_E);
        log.debug("Number number severe young : " + noSI_Y);
        log.debug("Number number severe adult : " + noSI_A);
        log.debug("Number number severe elderly : " + noSI_E);*/
        log.debug("Number recovered: " + totalRecovered);
        /*log.debug("Number number recovered young : " + noR_Y);
        log.debug("Number number recovered adult : " + noR_A);
        log.debug("Number number recovered elderly : " + noR_E);*/
        log.debug("Number hospitalised: " + totalHospitalised);
        /*log.debug("Number number hospitalised young : " + noH_Y);
        log.debug("Number number hospitalised adult : " + noH_A);
        log.debug("Number number hospitalised elderly : " + noH_E);*/
        log.debug("Number Dead: " + totalDead);
       /* log.debug("Number number dead young : " + noD_Y);
        log.debug("Number number dead adult : " + noD_A);
        log.debug("Number number dead elderly : " + noD_E);*/
        log.debug("Total population: " + totalPopulation);
    }

    public String getModelId() {
        return modelId;
    }
}
