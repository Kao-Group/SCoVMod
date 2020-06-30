package scovmod.model.input.config;

import java.io.Serializable;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FittingParameters implements Serializable {
    private static final long serialVersionUID = 1;

    private final double sToE_MI_Factor;
    private final double sToE_SI_day;
    private final double sToE_SI_night;
    private final double eToMI;
    private final double miToR;
    private final double miToSI;
    private final double health_mod;
    private final double numSeeds;

    private static final Logger log = LoggerFactory.getLogger(FittingParameters.class.getClass());

    public FittingParameters(double sToE_mi_factor, double sToE_SI_day, double sToE_SI_night, double eToMI, double miToR, double miToSI, double health_mod, double numSeeds) {
        this.sToE_MI_Factor = sToE_mi_factor;
        this.sToE_SI_day = sToE_SI_day;
        this.sToE_SI_night = sToE_SI_night;
        this.eToMI = eToMI;
        this.miToR = miToR;
        this. miToSI = miToSI;
        this.health_mod = health_mod;
        this.numSeeds = numSeeds;
    }


    public Parameters makeFullParameterSet(Parameters params) {
        return makeFullParameterSet(
                params.geteToR_YoungRate(),
                params.geteToR_AdultRate(),
                params.geteToR_ElderlyRate(),
                params.getSiToR_YoungRate(),
                params.getSiToR_AdultRate(),
                params.getSiToR_ElderlyRate(),
                params.getSiToH_YoungRate(),
                params.getSiToH_AdultRate(),
                params.getSiToH_ElderlyRate(),
                params.getSiToD_YoungRate(),
                params.getSiToD_AdultRate(),
                params.getSiToD_ElderlyRate(),
                params.gethToD_YoungRate(),
                params.gethToD_AdultRate(),
                params.gethToD_ElderlyRate(),
                params.gethToR_YoungRate(),
                params.gethToR_AdultRate(),
                params.gethToR_ElderlyRate(),
                params.getBetaFactorForLockdown(),
//                params.getBetaYoungMild_night_lockdown(),
//                params.getBetaAdultMild_night_lockdown(),
//                params.getBetaElderlyMild_night_lockdown(),
//                params.getBetaYoungSevere_night_lockdown(),
//                params.getBetaAdultSevere_night_lockdown(),
//                params.getBetaElderlySevere_night_lockdown(),
//                params.getBetaYoungMild_day_lockdown(),
//                params.getBetaAdultMild_day_lockdown(),
//                params.getBetaElderlyMild_day_lockdown(),
//                params.getBetaYoungSevere_day_lockdown(),
//                params.getBetaAdultSevere_day_lockdown(),
//                params.getBetaElderlySevere_day_lockdown());
             params.getAverageHealthIndexPerCouncilArea());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FittingParameters that = (FittingParameters) o;
        return Double.compare(that.sToE_MI_Factor, sToE_MI_Factor) == 0 &&
                Double.compare(that.sToE_SI_day, sToE_SI_day) == 0 &&
                Double.compare(that.sToE_SI_night, sToE_SI_night) == 0 &&
                Double.compare(that.eToMI, eToMI) == 0 &&
                Double.compare(that.miToR, miToR) == 0 &&
                Double.compare(that.miToSI, miToSI) == 0 &&
                Double.compare(that.health_mod, health_mod) == 0 &&
                Double.compare(that.numSeeds, numSeeds) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sToE_MI_Factor, sToE_SI_day, sToE_SI_night, eToMI, miToR, miToSI, health_mod, numSeeds);
    }

    @Override
    public String toString() {
        return "FittingParameters{" +
                "sToE_MI_Factor=" + sToE_MI_Factor +
                ", sToE_SI_day=" + sToE_SI_day +
                ", sToE_SI_night=" + sToE_SI_night +
                ", eToMI=" + eToMI +
                ", miToR=" + miToR +
                ", miToSI=" + miToSI +
                ", health_mod=" + health_mod +
                ", numSeeds=" + numSeeds +
                '}';
    }

    public Parameters makeFullParameterSet(
            double eToR_youngRate,
            double eToR_adultRate,
            double eToR_elderlyRate,
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
            double betaFactorForLockdown,
//            double betaAdultMild_night_lockdown,
//            double betaElderlyMild_night_lockdown,
//            double betaYoungSevere_night_lockdown,
//            double betaAdultSevere_night_lockdown,
//            double betaElderlySevere_night_lockdown,
//            double betaYoungMild_day_lockdown,
//            double betaAdultMild_day_lockdown,
//            double betaElderlyMild_day_lockdown,
//            double betaYoungSevere_day_lockdown,
//            double betaAdultSevere_day_lockdown,
//            double betaElderlySevere_day_lockdown
            double averageHealthIndexPerHealthBoard
            ) {

        return new Parameters(
                sToE_MI_Factor,
                sToE_MI_Factor,
                sToE_MI_Factor,
                sToE_SI_day,
                sToE_SI_day,
                sToE_SI_day,
                sToE_MI_Factor,
                sToE_MI_Factor,
                sToE_MI_Factor,
                sToE_SI_night,
                sToE_SI_night,
                sToE_SI_night,
                betaFactorForLockdown,
                eToMI,
                eToMI,
                eToMI,
                eToR_youngRate,
                eToR_adultRate,
                eToR_elderlyRate,
                miToR,
                miToR,
                miToR,
                miToSI,
                miToSI,
                miToSI,
                siToR_youngRate,
                siToR_adultRate,
                siToR_elderlyRate,
                siToH_youngRate,
                siToH_adultRate,
                siToH_elderlyRate,
                siToD_youngRate,
                siToD_adultRate,
                siToD_elderlyRate,
                hToD_youngRate,
                hToD_adultRate,
                hToD_elderlyRate,
                hToR_youngRate,
                hToR_adultRate,
                hToR_elderlyRate,
                sToE_MI_Factor/**betaFactorForLockdown*/, //Note not multiplied by lockdown factor as we know severe rate will be
                sToE_MI_Factor/**betaFactorForLockdown*/, //Note not multiplied by lockdown factor as we know severe rate will be
                sToE_MI_Factor/**betaFactorForLockdown*/, //Note not multiplied by lockdown factor as we know severe rate will be
                sToE_SI_night*betaFactorForLockdown,
                sToE_SI_night*betaFactorForLockdown,
                sToE_SI_night*betaFactorForLockdown,
                sToE_MI_Factor/**betaFactorForLockdown*/, //Note not multiplied by lockdown factor as we know severe rate will be
                sToE_MI_Factor/**betaFactorForLockdown*/, //Note not multiplied by lockdown factor as we know severe rate will be
                sToE_MI_Factor/**betaFactorForLockdown*/, //Note not multiplied by lockdown factor as we know severe rate will be
                sToE_SI_day*betaFactorForLockdown,
                sToE_SI_day*betaFactorForLockdown,
                sToE_SI_day*betaFactorForLockdown,
                health_mod,
                averageHealthIndexPerHealthBoard,
                numSeeds);
    }

    public Logger getLog() {
        return log;
    }

    public double getsToE_MI_Factor() {
        return sToE_MI_Factor;
    }

    public double getsToE_SI_day() {
        return sToE_SI_day;
    }

    public double getsToE_SI_night() {
        return sToE_SI_night;
    }

    public double geteToMI() {
        return eToMI;
    }

    public double getMiToR() {
        return miToR;
    }

    public double getMiToSI() {
        return miToSI;
    }

    public double getHealth_mod() { return health_mod; }

    public double getNumSeeds() { return numSeeds; }
}
