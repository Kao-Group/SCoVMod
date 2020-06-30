package scovmod.model.transition;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;

import static scovmod.model.state.infection.InfectionState.*;

public class BetaRates {

    private final Parameters params;
    private final ConfigParameters config;
    private final double betaYoungMild_night;
    private final double betaAdultMild_night;
    private final double betaElderlyMild_night;
    private final double betaYoungSevere_night;
    private final double betaAdultSevere_night;
    private final double betaElderlySevere_night;
    private final double betaYoungMild_day;
    private final double betaAdultMild_day;
    private final double betaElderlyMild_day;
    private final double betaYoungSevere_day;
    private final double betaAdultSevere_day;
    private final double betaElderlySevere_day;

    private final double betaYoungMild_night_lockdown;
    private final double betaAdultMild_night_lockdown;
    private final double betaElderlyMild_night_lockdown;
    private final double betaYoungSevere_night_lockdown;
    private final double betaAdultSevere_night_lockdown;
    private final double betaElderlySevere_night_lockdown;
    private final double betaYoungMild_day_lockdown;
    private final double betaAdultMild_day_lockdown;
    private final double betaElderlyMild_day_lockdown;
    private final double betaYoungSevere_day_lockdown;
    private final double betaAdultSevere_day_lockdown;
    private final double betaElderlySevere_day_lockdown;
    private final StateQuery sq;
    private TimeManager tm;


    public BetaRates(Parameters params, ConfigParameters config, StateQuery sq, TimeManager tm) {
        this.params = params;
        this.betaYoungMild_night = params.getsToE_Mild_YoungRate_Night()*params.getsToE_Severe_YoungRate_Night(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaAdultMild_night = params.getsToE_Mild_AdultRate_Night()*params.getsToE_Severe_AdultRate_Night(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaElderlyMild_night = params.getsToE_Mild_ElderlyRate_Night()*params.getsToE_Severe_ElderlyRate_Night(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaYoungSevere_night = params.getsToE_Severe_YoungRate_Night();
        this.betaAdultSevere_night = params.getsToE_Severe_AdultRate_Night();
        this.betaElderlySevere_night = params.getsToE_Severe_ElderlyRate_Night();
        this.betaYoungMild_day = params.getsToE_Mild_YoungRate_Day()*params.getsToE_Severe_YoungRate_Day(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaAdultMild_day = params.getsToE_Mild_AdultRate_Day()*params.getsToE_Severe_AdultRate_Day(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaElderlyMild_day = params.getsToE_Mild_ElderlyRate_Day()*params.getsToE_Severe_ElderlyRate_Day(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaYoungSevere_day = params.getsToE_Severe_YoungRate_Day();
        this.betaAdultSevere_day = params.getsToE_Severe_AdultRate_Day();
        this.betaElderlySevere_day = params.getsToE_Severe_ElderlyRate_Day();
        this.config = config;
        this.betaYoungMild_night_lockdown = params.getBetaYoungMild_night_lockdown()*params.getBetaYoungSevere_night_lockdown(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaAdultMild_night_lockdown = params.getBetaAdultMild_night_lockdown()*params.getBetaAdultSevere_night_lockdown(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaElderlyMild_night_lockdown = params.getBetaElderlyMild_night_lockdown()*params.getBetaElderlySevere_night_lockdown(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaYoungSevere_night_lockdown = params.getBetaYoungSevere_night_lockdown();
        this.betaAdultSevere_night_lockdown = params.getBetaAdultSevere_night_lockdown();
        this.betaElderlySevere_night_lockdown = params.getBetaElderlySevere_night_lockdown();
        this.betaYoungMild_day_lockdown = params.getBetaYoungMild_day_lockdown()*params.getBetaYoungSevere_day_lockdown(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaAdultMild_day_lockdown = params.getBetaAdultMild_day_lockdown()*params.getBetaAdultSevere_day_lockdown(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaElderlyMild_day_lockdown = params.getBetaElderlyMild_day_lockdown()*params.getBetaElderlySevere_day_lockdown(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaYoungSevere_day_lockdown = params.getBetaYoungSevere_day_lockdown();
        this.betaAdultSevere_day_lockdown = params.getBetaYoungSevere_day_lockdown();
        this.betaElderlySevere_day_lockdown = params.getBetaElderlySevere_day_lockdown();
        this.sq = sq;
        this.tm = tm;
    }

    public boolean isDay(){
        if ( tm.getTimeStep() % 2 == 0 ) return true;
        else return false;
    }

    public boolean isInLockdown(){
        if ( tm.getTimeStep() >=config.getOverrideParametersTimeStep() ) return true;
        else return false;
    }

    public double getBeta(InfectionState state) {
        if (!isInLockdown()) {
            if (state == MILD_INFECTIOUS_YOUNG) {
                if (isDay()) return betaYoungMild_day;
                else return betaYoungMild_night;
            } else if (state == SEVERE_INFECTIOUS_YOUNG) {
                if (isDay()) return betaYoungSevere_day;
                else return betaYoungSevere_night;
            } else if (state == MILD_INFECTIOUS_ADULT) {
                if (isDay()) return betaAdultMild_day;
                else return betaAdultMild_night;
            } else if (state == SEVERE_INFECTIOUS_ADULT) {
                if (isDay()) return betaAdultSevere_day;
                else return betaAdultSevere_night;
            } else if (state == MILD_INFECTIOUS_ELDERLY) {
                if (isDay()) return betaElderlyMild_day;
                else return betaElderlyMild_night;
            } else {
                if (isDay()) return betaElderlySevere_day;
                else return betaElderlySevere_night;
            }
        } else {
            if (state == MILD_INFECTIOUS_YOUNG) {
                if (isDay()) return betaYoungMild_day_lockdown;
                else return betaYoungMild_night_lockdown;
            } else if (state == SEVERE_INFECTIOUS_YOUNG) {
                if (isDay()) return betaYoungSevere_day_lockdown;
                else return betaYoungSevere_night_lockdown;
            } else if (state == MILD_INFECTIOUS_ADULT) {
                if (isDay()) return betaAdultMild_day_lockdown;
                else return betaAdultMild_night_lockdown;
            } else if (state == SEVERE_INFECTIOUS_ADULT) {
                if (isDay()) return betaAdultSevere_day_lockdown;
                else return betaAdultSevere_night_lockdown;
            } else if (state == MILD_INFECTIOUS_ELDERLY) {
                if (isDay()) return betaElderlyMild_day_lockdown;
                else return betaElderlyMild_night_lockdown;
            } else {
                if (isDay()) return betaElderlySevere_day_lockdown;
                else return betaElderlySevere_night_lockdown;
            }
        }
    }

//    public double getBeta(InfectionState state){ //Experiment with lockdown
//        if (state == MILD_INFECTIOUS_YOUNG) {
//            if (isInLockdown()) return 0.154;
//            else return betaYoungMild_day;
//        } else if (state == SEVERE_INFECTIOUS_YOUNG) {
//            if (isInLockdown()) return 0.28;
//            else return betaYoungSevere_day;
//        } else if (state == MILD_INFECTIOUS_ADULT) {
//            if (isInLockdown()) return 0.154;
//            else return betaAdultMild_day;
//        } else if (state == SEVERE_INFECTIOUS_ADULT) {
//            if (isInLockdown()) return 0.28;
//            else return betaAdultSevere_night;
//        } else if (state == MILD_INFECTIOUS_ELDERLY) {
//            if (isInLockdown()) return 0.154;
//            else return betaElderlyMild_night;
//        } else {
//            if (isInLockdown()) return 0.28;
//            else return betaElderlySevere_night;
//        }
//    }
}
