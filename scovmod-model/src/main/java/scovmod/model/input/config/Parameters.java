package scovmod.model.input.config;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.Objects;

public class Parameters {

    private double sToE_Mild_YoungRate_Day;
    private double sToE_Mild_AdultRate_Day;
    private double sToE_Mild_ElderlyRate_Day;
    private double sToE_Severe_YoungRate_Day;
    private double sToE_Severe_AdultRate_Day;
    private double sToE_Severe_ElderlyRate_Day;
    private double sToE_Mild_YoungRate_Night;
    private double sToE_Mild_AdultRate_Night;
    private double sToE_Mild_ElderlyRate_Night;
    private double sToE_Severe_YoungRate_Night;
    private double sToE_Severe_AdultRate_Night;
    private double sToE_Severe_ElderlyRate_Night;

    //Temporary until we have more info on beta params in lockdown
    private double betaFactorForLockdown;

    private double eToMI_YoungRate;
    private double eToMI_AdultRate;
    private double eToMI_ElderlyRate;
    private double eToR_YoungRate;
    private double eToR_AdultRate;
    private double eToR_ElderlyRate;
    private double miToR_YoungRate;
    private double miToR_AdultRate;
    private double miToR_ElderlyRate;
    private double miToSI_YoungRate;
    private double miToSI_AdultRate;
    private double miToSI_ElderlyRate;
    private double siToR_YoungRate;
    private double siToR_AdultRate;
    private double siToR_ElderlyRate;
    private double siToH_YoungRate;
    private double siToH_AdultRate;
    private double siToH_ElderlyRate;
    private double siToD_YoungRate;
    private double siToD_AdultRate;
    private double siToD_ElderlyRate;
    private double hToD_YoungRate;
    private double hToD_AdultRate;
    private double hToD_ElderlyRate;
    private double hToR_YoungRate;
    private double hToR_AdultRate;
    private double hToR_ElderlyRate;

    private double betaYoungMild_night_lockdown;
    private double betaAdultMild_night_lockdown;
    private double betaElderlyMild_night_lockdown;
    private double betaYoungSevere_night_lockdown;
    private double betaAdultSevere_night_lockdown;
    private double betaElderlySevere_night_lockdown;
    private double betaYoungMild_day_lockdown;
    private double betaAdultMild_day_lockdown;
    private double betaElderlyMild_day_lockdown;
    private double betaYoungSevere_day_lockdown;
    private double betaAdultSevere_day_lockdown;
    private double betaElderlySevere_day_lockdown;

    private double healthModifier;
    private double averageHealthIndexPerCouncilArea;

    private double numSeeds;


    private static final String newLine = System.getProperty("line.separator");
    private static final String commaNewLine = "," + newLine;

    public static Parameters fromJSON(String jsonContent) {

        ReadContext ctx = JsonPath.parse(jsonContent);
        double sToE_Mild_YoungRate_Day = ctx.read("$.parameters.rates.sToE_Mild_YoungRate_Day");
        double sToE_Mild_AdultRate_Day = ctx.read("$.parameters.rates.sToE_Mild_AdultRate_Day");
        double sToE_Mild_ElderlyRate_Day = ctx.read("$.parameters.rates.sToE_Mild_ElderlyRate_Day");
        double sToE_Severe_YoungRate_Day = ctx.read("$.parameters.rates.sToE_Severe_YoungRate_Day");
        double sToE_Severe_AdultRate_Day = ctx.read("$.parameters.rates.sToE_Severe_AdultRate_Day");
        double sToE_Severe_ElderlyRate_Day = ctx.read("$.parameters.rates.sToE_Severe_ElderlyRate_Day");
        double sToE_Mild_YoungRate_Night = ctx.read("$.parameters.rates.sToE_Mild_YoungRate_Night");
        double sToE_Mild_AdultRate_Night = ctx.read("$.parameters.rates.sToE_Mild_AdultRate_Night");
        double sToE_Mild_ElderlyRate_Night = ctx.read("$.parameters.rates.sToE_Mild_ElderlyRate_Night");
        double sToE_Severe_YoungRate_Night = ctx.read("$.parameters.rates.sToE_Severe_YoungRate_Night");
        double sToE_Severe_AdultRate_Night = ctx.read("$.parameters.rates.sToE_Severe_AdultRate_Night");
        double sToE_Severe_ElderlyRate_Night = ctx.read("$.parameters.rates.sToE_Severe_ElderlyRate_Night");
        double betaFactorForLockdown = ctx.read("$.parameters.rates.betaFactorForLockdown");
        double eToMI_YoungRate = ctx.read("$.parameters.rates.eToMI_YoungRate");
        double eToMI_AdultRate = ctx.read("$.parameters.rates.eToMI_AdultRate");
        double eToMI_ElderlyRate = ctx.read("$.parameters.rates.eToMI_ElderlyRate");
        double eToR_YoungRate = ctx.read("$.parameters.rates.eToR_YoungRate");
        double eToR_AdultRate = ctx.read("$.parameters.rates.eToR_AdultRate");
        double eToR_ElderlyRate = ctx.read("$.parameters.rates.eToR_ElderlyRate");
        double miToR_YoungRate = ctx.read("$.parameters.rates.miToR_YoungRate");
        double miToR_AdultRate = ctx.read("$.parameters.rates.miToR_AdultRate");
        double miToR_ElderlyRate = ctx.read("$.parameters.rates.miToR_ElderlyRate");
        double miToSI_YoungRate = ctx.read("$.parameters.rates.miToSI_YoungRate");
        double miToSI_AdultRate = ctx.read("$.parameters.rates.miToSI_AdultRate");
        double miToSI_ElderlyRate = ctx.read("$.parameters.rates.miToSI_ElderlyRate");
        double siToR_YoungRate = ctx.read("$.parameters.rates.siToR_YoungRate");
        double siToR_AdultRate = ctx.read("$.parameters.rates.siToR_AdultRate");
        double siToR_ElderlyRate = ctx.read("$.parameters.rates.siToR_ElderlyRate");
        double siToH_YoungRate = ctx.read("$.parameters.rates.siToH_YoungRate");
        double siToH_AdultRate = ctx.read("$.parameters.rates.siToH_AdultRate");
        double siToH_ElderlyRate = ctx.read("$.parameters.rates.siToH_ElderlyRate");
        double siToD_YoungRate = ctx.read("$.parameters.rates.siToD_YoungRate");
        double siToD_AdultRate = ctx.read("$.parameters.rates.siToD_AdultRate");
        double siToD_ElderlyRate = ctx.read("$.parameters.rates.siToD_ElderlyRate");
        double hToD_YoungRate = ctx.read("$.parameters.rates.hToD_YoungRate");
        double hToD_AdultRate = ctx.read("$.parameters.rates.hToD_AdultRate");
        double hToD_ElderlyRate = ctx.read("$.parameters.rates.hToD_ElderlyRate");
        double hToR_YoungRate = ctx.read("$.parameters.rates.hToR_YoungRate");
        double hToR_AdultRate = ctx.read("$.parameters.rates.hToR_AdultRate");
        double hToR_ElderlyRate = ctx.read("$.parameters.rates.hToR_ElderlyRate");

        double betaYoungMild_night_lockdown = ctx.read("$.parameters.rates.betaYoungMild_night_lockdown");
        double betaAdultMild_night_lockdown = ctx.read("$.parameters.rates.betaAdultMild_night_lockdown");
        double betaElderlyMild_night_lockdown = ctx.read("$.parameters.rates.betaElderlyMild_night_lockdown");
        double betaYoungSevere_night_lockdown = ctx.read("$.parameters.rates.betaYoungSevere_night_lockdown");
        double betaAdultSevere_night_lockdown = ctx.read("$.parameters.rates.betaAdultSevere_night_lockdown");
        double betaElderlySevere_night_lockdown = ctx.read("$.parameters.rates.betaElderlySevere_night_lockdown");
        double betaYoungMild_day_lockdown = ctx.read("$.parameters.rates.betaYoungMild_day_lockdown");
        double betaAdultMild_day_lockdown = ctx.read("$.parameters.rates.betaAdultMild_day_lockdown");
        double betaElderlyMild_day_lockdown = ctx.read("$.parameters.rates.betaElderlyMild_day_lockdown");
        double betaYoungSevere_day_lockdown = ctx.read("$.parameters.rates.betaYoungSevere_day_lockdown");
        double betaAdultSevere_day_lockdown = ctx.read("$.parameters.rates.betaAdultSevere_day_lockdown");
        double betaElderlySevere_day_lockdown = ctx.read("$.parameters.rates.betaElderlySevere_day_lockdown");

        double healthModifier = ctx.read("$.parameters.health.deprivationIndex.healthModifier");
        double averageHealthIndexPerCouncilArea = ctx.read("$.parameters.health.deprivationIndex.averageHealthIndexPerCouncilArea");

        double numSeeds = ctx.read("$.parameters.numSeeds");

        return new Parameters(
                sToE_Mild_YoungRate_Day,
                sToE_Mild_AdultRate_Day,
                sToE_Mild_ElderlyRate_Day,
                sToE_Severe_YoungRate_Day,
                sToE_Severe_AdultRate_Day,
                sToE_Severe_ElderlyRate_Day,
                sToE_Mild_YoungRate_Night,
                sToE_Mild_AdultRate_Night,
                sToE_Mild_ElderlyRate_Night,
                sToE_Severe_YoungRate_Night,
                sToE_Severe_AdultRate_Night,
                sToE_Severe_ElderlyRate_Night,
                betaFactorForLockdown,
                eToMI_YoungRate,
                eToMI_AdultRate,
                eToMI_ElderlyRate,
                eToR_YoungRate,
                eToR_AdultRate,
                eToR_ElderlyRate,
                miToR_YoungRate,
                miToR_AdultRate,
                miToR_ElderlyRate,
                miToSI_YoungRate,
                miToSI_AdultRate,
                miToSI_ElderlyRate,
                siToR_YoungRate,
                siToR_AdultRate,
                siToR_ElderlyRate,
                siToH_YoungRate,
                siToH_AdultRate,
                siToH_ElderlyRate,
                siToD_YoungRate,
                siToD_AdultRate,
                siToD_ElderlyRate,
                hToD_YoungRate,
                hToD_AdultRate,
                hToD_ElderlyRate,
                hToR_YoungRate,
                hToR_AdultRate,
                hToR_ElderlyRate,
                betaYoungMild_night_lockdown,
                betaAdultMild_night_lockdown,
                betaElderlyMild_night_lockdown,
                betaYoungSevere_night_lockdown,
                betaAdultSevere_night_lockdown,
                betaElderlySevere_night_lockdown,
                betaYoungMild_day_lockdown,
                betaAdultMild_day_lockdown,
                betaElderlyMild_day_lockdown,
                betaYoungSevere_day_lockdown,
                betaAdultSevere_day_lockdown,
                betaElderlySevere_day_lockdown,
                healthModifier,
                averageHealthIndexPerCouncilArea,
                numSeeds);
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "sToE_Mild_YoungRate_Day=" + sToE_Mild_YoungRate_Day +
                ", sToE_Mild_AdultRate_Day=" + sToE_Mild_AdultRate_Day +
                ", sToE_Mild_ElderlyRate_Day=" + sToE_Mild_ElderlyRate_Day +
                ", sToE_Severe_YoungRate_Day=" + sToE_Severe_YoungRate_Day +
                ", sToE_Severe_AdultRate_Day=" + sToE_Severe_AdultRate_Day +
                ", sToE_Severe_ElderlyRate_Day=" + sToE_Severe_ElderlyRate_Day +
                ", sToE_Mild_YoungRate_Night=" + sToE_Mild_YoungRate_Night +
                ", sToE_Mild_AdultRate_Night=" + sToE_Mild_AdultRate_Night +
                ", sToE_Mild_ElderlyRate_Night=" + sToE_Mild_ElderlyRate_Night +
                ", sToE_Severe_YoungRate_Night=" + sToE_Severe_YoungRate_Night +
                ", sToE_Severe_AdultRate_Night=" + sToE_Severe_AdultRate_Night +
                ", sToE_Severe_ElderlyRate_Night=" + sToE_Severe_ElderlyRate_Night +
                ", betaFactorForLockdown=" + betaFactorForLockdown +
                ", eToMI_YoungRate=" + eToMI_YoungRate +
                ", eToMI_AdultRate=" + eToMI_AdultRate +
                ", eToMI_ElderlyRate=" + eToMI_ElderlyRate +
                ", eToR_YoungRate=" + eToR_YoungRate +
                ", eToR_AdultRate=" + eToR_AdultRate +
                ", eToR_ElderlyRate=" + eToR_ElderlyRate +
                ", miToR_YoungRate=" + miToR_YoungRate +
                ", miToR_AdultRate=" + miToR_AdultRate +
                ", miToR_ElderlyRate=" + miToR_ElderlyRate +
                ", miToSI_YoungRate=" + miToSI_YoungRate +
                ", miToSI_AdultRate=" + miToSI_AdultRate +
                ", miToSI_ElderlyRate=" + miToSI_ElderlyRate +
                ", siToR_YoungRate=" + siToR_YoungRate +
                ", siToR_AdultRate=" + siToR_AdultRate +
                ", siToR_ElderlyRate=" + siToR_ElderlyRate +
                ", siToH_YoungRate=" + siToH_YoungRate +
                ", siToH_AdultRate=" + siToH_AdultRate +
                ", siToH_ElderlyRate=" + siToH_ElderlyRate +
                ", siToD_YoungRate=" + siToD_YoungRate +
                ", siToD_AdultRate=" + siToD_AdultRate +
                ", siToD_ElderlyRate=" + siToD_ElderlyRate +
                ", hToD_YoungRate=" + hToD_YoungRate +
                ", hToD_AdultRate=" + hToD_AdultRate +
                ", hToD_ElderlyRate=" + hToD_ElderlyRate +
                ", hToR_YoungRate=" + hToR_YoungRate +
                ", hToR_AdultRate=" + hToR_AdultRate +
                ", hToR_ElderlyRate=" + hToR_ElderlyRate +
                ", betaYoungMild_night_lockdown=" + betaYoungMild_night_lockdown +
                ", betaAdultMild_night_lockdown=" + betaAdultMild_night_lockdown +
                ", betaElderlyMild_night_lockdown=" + betaElderlyMild_night_lockdown +
                ", betaYoungSevere_night_lockdown=" + betaYoungSevere_night_lockdown +
                ", betaAdultSevere_night_lockdown=" + betaAdultSevere_night_lockdown +
                ", betaElderlySevere_night_lockdown=" + betaElderlySevere_night_lockdown +
                ", betaYoungMild_day_lockdown=" + betaYoungMild_day_lockdown +
                ", betaAdultMild_day_lockdown=" + betaAdultMild_day_lockdown +
                ", betaElderlyMild_day_lockdown=" + betaElderlyMild_day_lockdown +
                ", betaYoungSevere_day_lockdown=" + betaYoungSevere_day_lockdown +
                ", betaAdultSevere_day_lockdown=" + betaAdultSevere_day_lockdown +
                ", betaElderlySevere_day_lockdown=" + betaElderlySevere_day_lockdown +
                ", healthModifier=" + healthModifier +
                ", averageHealthIndexPerCouncilArea=" + averageHealthIndexPerCouncilArea +
                ", numSeeds=" + numSeeds +
                '}';
    }

    public Parameters(
            double sToE_Mild_youngRate_Day,
            double sToE_Mild_adultRate_Day,
            double sToE_Mild_elderlyRate_Day,
            double sToE_Severe_youngRate_Day,
            double sToE_Severe_adultRate_Day,
            double sToE_Severe_elderlyRate_Day,
            double sToE_Mild_youngRate_Night,
            double sToE_Mild_adultRate_Night,
            double sToE_Mild_elderlyRate_Night,
            double sToE_Severe_youngRate_Night,
            double sToE_Severe_adultRate_Night,
            double sToE_Severe_elderlyRate_Night,
            double betaFactorForLockdown,
            double eToMI_youngRate,
            double eToMI_adultRate,
            double eToMI_elderlyRate,
            double eToR_youngRate,
            double eToR_adultRate,
            double eToR_elderlyRate,
            double miToR_youngRate,
            double miToR_adultRate,
            double miToR_elderlyRate,
            double miToSI_youngRate,
            double miToSI_adultRate,
            double miToSI_elderlyRate,
            double siToR_youngRate,
            double siToR_adultRate,
            double siToR_elderlyRate,
            double siToH_youngRate,
            double siToH_adultRate,
            double siToH_elderlyRate,
            double siToD_youngRate,
            double siToD_adultRate,
            double siToD_elderlyRate,
            double hToD_youngRate,
            double hToD_adultRate,
            double hToD_elderlyRate,
            double hToR_youngRate,
            double hToR_adultRate,
            double hToR_elderlyRate,
            double betaYoungMild_night_lockdown,
            double betaAdultMild_night_lockdown,
            double betaElderlyMild_night_lockdown,
            double betaYoungSevere_night_lockdown,
            double betaAdultSevere_night_lockdown,
            double betaElderlySevere_night_lockdown,
            double betaYoungMild_day_lockdown,
            double betaAdultMild_day_lockdown,
            double betaElderlyMild_day_lockdown,
            double betaYoungSevere_day_lockdown,
            double betaAdultSevere_day_lockdown,
            double betaElderlySevere_day_lockdown,
            double healthModifier,
            double averageHealthIndexPerCouncilArea,
            double numSeeds) {
        sToE_Mild_YoungRate_Day = sToE_Mild_youngRate_Day;
        sToE_Mild_AdultRate_Day = sToE_Mild_adultRate_Day;
        sToE_Mild_ElderlyRate_Day = sToE_Mild_elderlyRate_Day;
        sToE_Severe_YoungRate_Day = sToE_Severe_youngRate_Day;
        sToE_Severe_AdultRate_Day = sToE_Severe_adultRate_Day;
        sToE_Severe_ElderlyRate_Day = sToE_Severe_elderlyRate_Day;
        sToE_Mild_YoungRate_Night = sToE_Mild_youngRate_Night;
        sToE_Mild_AdultRate_Night = sToE_Mild_adultRate_Night;
        sToE_Mild_ElderlyRate_Night = sToE_Mild_elderlyRate_Night;
        sToE_Severe_YoungRate_Night = sToE_Severe_youngRate_Night;
        sToE_Severe_AdultRate_Night = sToE_Severe_adultRate_Night;
        sToE_Severe_ElderlyRate_Night = sToE_Severe_elderlyRate_Night;
        this.betaFactorForLockdown = betaFactorForLockdown;
        eToMI_YoungRate = eToMI_youngRate;
        eToMI_AdultRate = eToMI_adultRate;
        eToMI_ElderlyRate = eToMI_elderlyRate;
        eToR_YoungRate = eToR_youngRate;
        eToR_AdultRate = eToR_adultRate;
        eToR_ElderlyRate = eToR_elderlyRate;
        miToR_YoungRate = miToR_youngRate;
        miToR_AdultRate = miToR_adultRate;
        miToR_ElderlyRate = miToR_elderlyRate;
        miToSI_YoungRate = miToSI_youngRate;
        miToSI_AdultRate = miToSI_adultRate;
        miToSI_ElderlyRate = miToSI_elderlyRate;
        siToR_YoungRate = siToR_youngRate;
        siToR_AdultRate = siToR_adultRate;
        siToR_ElderlyRate = siToR_elderlyRate;
        siToH_YoungRate = siToH_youngRate;
        siToH_AdultRate = siToH_adultRate;
        siToH_ElderlyRate = siToH_elderlyRate;
        siToD_YoungRate = siToD_youngRate;
        siToD_AdultRate = siToD_adultRate;
        siToD_ElderlyRate = siToD_elderlyRate;
        hToD_YoungRate = hToD_youngRate;
        hToD_AdultRate = hToD_adultRate;
        hToD_ElderlyRate = hToD_elderlyRate;
        hToR_YoungRate = hToR_youngRate;
        hToR_AdultRate = hToR_adultRate;
        hToR_ElderlyRate = hToR_elderlyRate;
        this.betaYoungMild_night_lockdown = betaYoungMild_night_lockdown;
        this.betaAdultMild_night_lockdown = betaAdultMild_night_lockdown;
        this.betaElderlyMild_night_lockdown = betaElderlyMild_night_lockdown;
        this.betaYoungSevere_night_lockdown = betaYoungSevere_night_lockdown;
        this.betaAdultSevere_night_lockdown = betaAdultSevere_night_lockdown;
        this.betaElderlySevere_night_lockdown = betaElderlySevere_night_lockdown;
        this.betaYoungMild_day_lockdown = betaYoungMild_day_lockdown;
        this.betaAdultMild_day_lockdown = betaAdultMild_day_lockdown;
        this.betaElderlyMild_day_lockdown = betaElderlyMild_day_lockdown;
        this.betaYoungSevere_day_lockdown = betaYoungSevere_day_lockdown;
        this.betaAdultSevere_day_lockdown = betaAdultSevere_day_lockdown;
        this.betaElderlySevere_day_lockdown = betaElderlySevere_day_lockdown;
        this.healthModifier = healthModifier;
        this.averageHealthIndexPerCouncilArea = averageHealthIndexPerCouncilArea;
        this.numSeeds = numSeeds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameters that = (Parameters) o;
        return Double.compare(that.sToE_Mild_YoungRate_Day, sToE_Mild_YoungRate_Day) == 0 &&
                Double.compare(that.sToE_Mild_AdultRate_Day, sToE_Mild_AdultRate_Day) == 0 &&
                Double.compare(that.sToE_Mild_ElderlyRate_Day, sToE_Mild_ElderlyRate_Day) == 0 &&
                Double.compare(that.sToE_Severe_YoungRate_Day, sToE_Severe_YoungRate_Day) == 0 &&
                Double.compare(that.sToE_Severe_AdultRate_Day, sToE_Severe_AdultRate_Day) == 0 &&
                Double.compare(that.sToE_Severe_ElderlyRate_Day, sToE_Severe_ElderlyRate_Day) == 0 &&
                Double.compare(that.sToE_Mild_YoungRate_Night, sToE_Mild_YoungRate_Night) == 0 &&
                Double.compare(that.sToE_Mild_AdultRate_Night, sToE_Mild_AdultRate_Night) == 0 &&
                Double.compare(that.sToE_Mild_ElderlyRate_Night, sToE_Mild_ElderlyRate_Night) == 0 &&
                Double.compare(that.sToE_Severe_YoungRate_Night, sToE_Severe_YoungRate_Night) == 0 &&
                Double.compare(that.sToE_Severe_AdultRate_Night, sToE_Severe_AdultRate_Night) == 0 &&
                Double.compare(that.sToE_Severe_ElderlyRate_Night, sToE_Severe_ElderlyRate_Night) == 0 &&
                Double.compare(that.betaFactorForLockdown, betaFactorForLockdown) == 0 &&
                Double.compare(that.eToMI_YoungRate, eToMI_YoungRate) == 0 &&
                Double.compare(that.eToMI_AdultRate, eToMI_AdultRate) == 0 &&
                Double.compare(that.eToMI_ElderlyRate, eToMI_ElderlyRate) == 0 &&
                Double.compare(that.eToR_YoungRate, eToR_YoungRate) == 0 &&
                Double.compare(that.eToR_AdultRate, eToR_AdultRate) == 0 &&
                Double.compare(that.eToR_ElderlyRate, eToR_ElderlyRate) == 0 &&
                Double.compare(that.miToR_YoungRate, miToR_YoungRate) == 0 &&
                Double.compare(that.miToR_AdultRate, miToR_AdultRate) == 0 &&
                Double.compare(that.miToR_ElderlyRate, miToR_ElderlyRate) == 0 &&
                Double.compare(that.miToSI_YoungRate, miToSI_YoungRate) == 0 &&
                Double.compare(that.miToSI_AdultRate, miToSI_AdultRate) == 0 &&
                Double.compare(that.miToSI_ElderlyRate, miToSI_ElderlyRate) == 0 &&
                Double.compare(that.siToR_YoungRate, siToR_YoungRate) == 0 &&
                Double.compare(that.siToR_AdultRate, siToR_AdultRate) == 0 &&
                Double.compare(that.siToR_ElderlyRate, siToR_ElderlyRate) == 0 &&
                Double.compare(that.siToH_YoungRate, siToH_YoungRate) == 0 &&
                Double.compare(that.siToH_AdultRate, siToH_AdultRate) == 0 &&
                Double.compare(that.siToH_ElderlyRate, siToH_ElderlyRate) == 0 &&
                Double.compare(that.siToD_YoungRate, siToD_YoungRate) == 0 &&
                Double.compare(that.siToD_AdultRate, siToD_AdultRate) == 0 &&
                Double.compare(that.siToD_ElderlyRate, siToD_ElderlyRate) == 0 &&
                Double.compare(that.hToD_YoungRate, hToD_YoungRate) == 0 &&
                Double.compare(that.hToD_AdultRate, hToD_AdultRate) == 0 &&
                Double.compare(that.hToD_ElderlyRate, hToD_ElderlyRate) == 0 &&
                Double.compare(that.hToR_YoungRate, hToR_YoungRate) == 0 &&
                Double.compare(that.hToR_AdultRate, hToR_AdultRate) == 0 &&
                Double.compare(that.hToR_ElderlyRate, hToR_ElderlyRate) == 0 &&
                Double.compare(that.betaYoungMild_night_lockdown, betaYoungMild_night_lockdown) == 0 &&
                Double.compare(that.betaAdultMild_night_lockdown, betaAdultMild_night_lockdown) == 0 &&
                Double.compare(that.betaElderlyMild_night_lockdown, betaElderlyMild_night_lockdown) == 0 &&
                Double.compare(that.betaYoungSevere_night_lockdown, betaYoungSevere_night_lockdown) == 0 &&
                Double.compare(that.betaAdultSevere_night_lockdown, betaAdultSevere_night_lockdown) == 0 &&
                Double.compare(that.betaElderlySevere_night_lockdown, betaElderlySevere_night_lockdown) == 0 &&
                Double.compare(that.betaYoungMild_day_lockdown, betaYoungMild_day_lockdown) == 0 &&
                Double.compare(that.betaAdultMild_day_lockdown, betaAdultMild_day_lockdown) == 0 &&
                Double.compare(that.betaElderlyMild_day_lockdown, betaElderlyMild_day_lockdown) == 0 &&
                Double.compare(that.betaYoungSevere_day_lockdown, betaYoungSevere_day_lockdown) == 0 &&
                Double.compare(that.betaAdultSevere_day_lockdown, betaAdultSevere_day_lockdown) == 0 &&
                Double.compare(that.betaElderlySevere_day_lockdown, betaElderlySevere_day_lockdown) == 0 &&
                Double.compare(that.healthModifier, healthModifier) == 0 &&
                Double.compare(that.averageHealthIndexPerCouncilArea, averageHealthIndexPerCouncilArea) == 0 &&
                Double.compare(that.numSeeds, numSeeds) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sToE_Mild_YoungRate_Day, sToE_Mild_AdultRate_Day, sToE_Mild_ElderlyRate_Day, sToE_Severe_YoungRate_Day, sToE_Severe_AdultRate_Day, sToE_Severe_ElderlyRate_Day, sToE_Mild_YoungRate_Night, sToE_Mild_AdultRate_Night, sToE_Mild_ElderlyRate_Night, sToE_Severe_YoungRate_Night, sToE_Severe_AdultRate_Night, sToE_Severe_ElderlyRate_Night, betaFactorForLockdown, eToMI_YoungRate, eToMI_AdultRate, eToMI_ElderlyRate, eToR_YoungRate, eToR_AdultRate, eToR_ElderlyRate, miToR_YoungRate, miToR_AdultRate, miToR_ElderlyRate, miToSI_YoungRate, miToSI_AdultRate, miToSI_ElderlyRate, siToR_YoungRate, siToR_AdultRate, siToR_ElderlyRate, siToH_YoungRate, siToH_AdultRate, siToH_ElderlyRate, siToD_YoungRate, siToD_AdultRate, siToD_ElderlyRate, hToD_YoungRate, hToD_AdultRate, hToD_ElderlyRate, hToR_YoungRate, hToR_AdultRate, hToR_ElderlyRate, betaYoungMild_night_lockdown, betaAdultMild_night_lockdown, betaElderlyMild_night_lockdown, betaYoungSevere_night_lockdown, betaAdultSevere_night_lockdown, betaElderlySevere_night_lockdown, betaYoungMild_day_lockdown, betaAdultMild_day_lockdown, betaElderlyMild_day_lockdown, betaYoungSevere_day_lockdown, betaAdultSevere_day_lockdown, betaElderlySevere_day_lockdown, healthModifier, averageHealthIndexPerCouncilArea, numSeeds);
    }

    public double getsToE_Mild_YoungRate_Day() {
        return sToE_Mild_YoungRate_Day;
    }

    public double getsToE_Mild_AdultRate_Day() {
        return sToE_Mild_AdultRate_Day;
    }

    public double getsToE_Mild_ElderlyRate_Day() {
        return sToE_Mild_ElderlyRate_Day;
    }

    public double getsToE_Severe_YoungRate_Day() {
        return sToE_Severe_YoungRate_Day;
    }

    public double getsToE_Severe_AdultRate_Day() {
        return sToE_Severe_AdultRate_Day;
    }

    public double getsToE_Severe_ElderlyRate_Day() {
        return sToE_Severe_ElderlyRate_Day;
    }

    public double getsToE_Mild_YoungRate_Night() {
        return sToE_Mild_YoungRate_Night;
    }

    public double getsToE_Mild_AdultRate_Night() {
        return sToE_Mild_AdultRate_Night;
    }

    public double getsToE_Mild_ElderlyRate_Night() {
        return sToE_Mild_ElderlyRate_Night;
    }

    public double getsToE_Severe_YoungRate_Night() {
        return sToE_Severe_YoungRate_Night;
    }

    public double getsToE_Severe_AdultRate_Night() {
        return sToE_Severe_AdultRate_Night;
    }

    public double getsToE_Severe_ElderlyRate_Night() {
        return sToE_Severe_ElderlyRate_Night;
    }

    public double geteToMI_YoungRate() {
        return eToMI_YoungRate;
    }

    public double geteToMI_AdultRate() {
        return eToMI_AdultRate;
    }

    public double geteToMI_ElderlyRate() {
        return eToMI_ElderlyRate;
    }

    public double geteToR_YoungRate() {
        return eToR_YoungRate;
    }

    public double geteToR_AdultRate() {
        return eToR_AdultRate;
    }

    public double geteToR_ElderlyRate() {
        return eToR_ElderlyRate;
    }

    public double getMiToR_YoungRate() {
        return miToR_YoungRate;
    }

    public double getMiToR_AdultRate() {
        return miToR_AdultRate;
    }

    public double getMiToR_ElderlyRate() {
        return miToR_ElderlyRate;
    }

    public double getMiToSI_YoungRate() {
        return miToSI_YoungRate;
    }

    public double getMiToSI_AdultRate() {
        return miToSI_AdultRate;
    }

    public double getMiToSI_ElderlyRate() {
        return miToSI_ElderlyRate;
    }

    public double getSiToR_YoungRate() {
        return siToR_YoungRate;
    }

    public double getSiToR_AdultRate() {
        return siToR_AdultRate;
    }

    public double getSiToR_ElderlyRate() {
        return siToR_ElderlyRate;
    }

    public double getSiToH_YoungRate() {
        return siToH_YoungRate;
    }

    public double getSiToH_AdultRate() {
        return siToH_AdultRate;
    }

    public double getSiToH_ElderlyRate() {
        return siToH_ElderlyRate;
    }

    public double getSiToD_YoungRate() {
        return siToD_YoungRate;
    }

    public double getSiToD_AdultRate() {
        return siToD_AdultRate;
    }

    public double getSiToD_ElderlyRate() {
        return siToD_ElderlyRate;
    }

    public double gethToD_YoungRate() {
        return hToD_YoungRate;
    }

    public double gethToD_AdultRate() {
        return hToD_AdultRate;
    }

    public double gethToD_ElderlyRate() {
        return hToD_ElderlyRate;
    }

    public double gethToR_YoungRate() {
        return hToR_YoungRate;
    }

    public double gethToR_AdultRate() {
        return hToR_AdultRate;
    }

    public double gethToR_ElderlyRate() {
        return hToR_ElderlyRate;
    }

    public double getBetaYoungMild_night_lockdown() {
        return betaYoungMild_night_lockdown;
    }

    public double getBetaAdultMild_night_lockdown() {
        return betaAdultMild_night_lockdown;
    }

    public double getBetaElderlyMild_night_lockdown() {
        return betaElderlyMild_night_lockdown;
    }

    public double getBetaYoungSevere_night_lockdown() {
        return betaYoungSevere_night_lockdown;
    }

    public double getBetaAdultSevere_night_lockdown() {
        return betaAdultSevere_night_lockdown;
    }

    public double getBetaElderlySevere_night_lockdown() { return betaElderlySevere_night_lockdown; }

    public double getBetaYoungMild_day_lockdown() { return betaYoungMild_day_lockdown; }

    public double getBetaAdultMild_day_lockdown() {
        return betaAdultMild_day_lockdown;
    }

    public double getBetaElderlyMild_day_lockdown() {
        return betaElderlyMild_day_lockdown;
    }

    public double getBetaYoungSevere_day_lockdown() {
        return betaYoungSevere_day_lockdown;
    }

    public double getBetaAdultSevere_day_lockdown() {
        return betaAdultSevere_day_lockdown;
    }

    public double getBetaElderlySevere_day_lockdown() {
        return betaElderlySevere_day_lockdown;
    }

    public double getBetaFactorForLockdown() { return betaFactorForLockdown; }

    public double getHealthModifier() { return healthModifier; }

    public double getAverageHealthIndexPerCouncilArea() { return averageHealthIndexPerCouncilArea; }

    public double getNumSeeds() { return numSeeds; }
}


