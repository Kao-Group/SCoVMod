/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output;

import scovmod.model.state.StateQuery;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;

public class StatisticsCollector {

    private final TimeManager tm;
    private final TimeConversions tcv;
    private StateQuery sq;
    private IOutputModule output;
    private HealthBoardLookup hbl;

    public StatisticsCollector(
            IOutputModule output,
            TimeManager tm,
            TimeConversions tcv,
            StateQuery sq,
            HealthBoardLookup hbl) {
        this.tm = tm;
        this.tcv = tcv;
        this.sq = sq;
        this.output = output;
        this.hbl = hbl;
    }

    public void currentExposed(int totalExposed, int location) {
        output.currentTotalExposed(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, location);
    }

    public void currentExposedHB(int totalExposed, int location) {
        output.currentTotalExposedHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getHealthBoardFromOA(location));
    }

    public void currentInfectious(int totalInfectious, int location) {
        output.currentTotalInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
    }

    public void currentInfectiousHB(int totalInfectious, int location) {
        output.currentTotalInfectiousHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,hbl.getHealthBoardFromOA(location));
    }

    public void currentMildInfectious(int totalInfectious, int location) {
        output.currentTotalMildInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
    }

    public void currentMildInfectiousHB(int totalInfectious, int location) {
        output.currentTotalMildInfectiousHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,hbl.getHealthBoardFromOA(location));
    }

    public void currentSevereInfectious(int totalInfectious, int location) {
        output.currentTotalSevereInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
    }

    public void currentSevereInfectiousHB(int totalInfectious, int location) {
        output.currentTotalSevereInfectiousHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getHealthBoardFromOA(location));
    }

    public void currentRecovered(int totalRecovered, int location) {
        output.currentTotalRecovered(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
    }

    public void currentRecoveredHB(int totalRecovered, int location) {
        output.currentTotalRecoveredHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,hbl.getHealthBoardFromOA(location));
    }

    public void currentDead(int totalDead, int location) {
        output.currentTotalDead(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
    }

    public void currentDeadHB(int totalDead, int location) {
        output.currentTotalDeadHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getHealthBoardFromOA(location));
    }

    public void currentDeadCA(int totalDead, int location) {
        output.currentTotalDeadCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
    }

    public void currentPopulation(int totalPopulation, int location) {
        output.currentPopulation(tcv.toTimeStepStartDate(tm.getTimeStep()), totalPopulation,location);
    }

    public void currentHospitalised(int totalHospitalised, int location) {
        output.currentTotalHospitalised(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
    }

    public void currentHospitalisedHB(int totalHospitalised, int location) {
        output.currentTotalHospitalisedHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getHealthBoardFromOA(location));
    }

    public void currentSusceptibleYoung(int totalSusceptible, int location) {
        output.currentSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,location);
    }

    public void currentExposedYoung(int totalExposed, int location) {
        output.currentExposedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
    }

    public void currentExposedYoungHB(int totalExposed, int location) {
        output.currentExposedYoungHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,hbl.getHealthBoardFromOA(location));
    }

    public void currentMildInfectiousYoung(int totalInfectious, int location) {
        output.currentMildInfectiousYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
    }

    public void currentMildInfectiousYoungHB(int totalInfectious, int location) {
        output.currentMildInfectiousYoungHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,hbl.getHealthBoardFromOA(location));
    }

    public void currentSevereInfectiousYoung(int totalInfectious, int location) {
        output.currentSevereInfectiousYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
    }

    public void currentSevereInfectiousYoungHB(int totalInfectious, int location) {
        output.currentSevereInfectiousYoungHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,hbl.getHealthBoardFromOA(location));
    }

    public void currentRecoveredYoung(int totalRecovered, int location) {
        output.currentRecoveredYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
    }

    public void currentRecoveredYoungHB(int totalRecovered, int location) {
        output.currentRecoveredYoungHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,hbl.getHealthBoardFromOA(location));
    }

    public void currentDeadYoung(int totalDead, int location) {
        output.currentDeadYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
    }

    public void currentDeadYoungHB(int totalDead, int location) {
        output.currentDeadYoungHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,hbl.getHealthBoardFromOA(location));
    }

    public void currentHospitalisedYoung(int totalHospitalised, int location) {
        output.currentHospitalisedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
    }

    public void currentHospitalisedYoungHB(int totalHospitalised, int location) {
        output.currentHospitalisedYoungHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,hbl.getHealthBoardFromOA(location));
    }

    public void currentSusceptibleAdult(int totalSusceptible, int location) {
        output.currentSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,location);
    }

    public void currentExposedAdult(int totalExposed, int location) {
        output.currentExposedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
    }

    public void currentExposedAdultHB(int totalExposed, int location) {
        output.currentExposedAdultHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,hbl.getHealthBoardFromOA(location));
    }

    public void currentMildInfectiousAdult(int totalInfectious, int location) {
        output.currentMildInfectiousAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
    }

    public void currentMildInfectiousAdultHB(int totalInfectious, int location) {
        output.currentMildInfectiousAdultHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,hbl.getHealthBoardFromOA(location));
    }

    public void currentSevereInfectiousAdult(int totalInfectious, int location) {
        output.currentSevereInfectiousAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
    }

    public void currentSevereInfectiousAdultHB(int totalInfectious, int location) {
        output.currentSevereInfectiousAdultHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,hbl.getHealthBoardFromOA(location));
    }

    public void currentRecoveredAdult(int totalRecovered, int location) {
        output.currentRecoveredAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
    }

    public void currentRecoveredAdultHB(int totalRecovered, int location) {
        output.currentRecoveredAdultHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,hbl.getHealthBoardFromOA(location));
    }

    public void currentDeadAdult(int totalDead, int location) {
        output.currentDeadAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
    }

    public void currentDeadAdultHB(int totalDead, int location) {
        output.currentDeadAdultHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,hbl.getHealthBoardFromOA(location));
    }

    public void currentHospitalisedAdult(int totalHospitalised, int location) {
        output.currentHospitalisedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
    }

    public void currentHospitalisedAdultHB(int totalHospitalised, int location) {
        output.currentHospitalisedAdultHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,hbl.getHealthBoardFromOA(location));
    }

    public void currentSusceptibleElderly(int totalSusceptible, int location) {
        output.currentSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,location);
    }

    public void currentExposedElderly(int totalExposed, int location) {
        output.currentExposedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
    }

    public void currentExposedElderlyHB(int totalExposed, int location) {
        output.currentExposedElderlyHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,hbl.getHealthBoardFromOA(location));
    }

    public void currentMildInfectiousElderly(int totalInfectious, int location) {
        output.currentMildInfectiousElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
    }

    public void currentMildInfectiousElderlyHB(int totalInfectious, int location) {
        output.currentMildInfectiousElderlyHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,hbl.getHealthBoardFromOA(location));
    }

    public void currentSevereInfectiousElderly(int totalInfectious, int location) {
        output.currentSevereInfectiousElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
    }

    public void currentSevereInfectiousElderlyHB(int totalInfectious, int location) {
        output.currentSevereInfectiousElderlyHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,hbl.getHealthBoardFromOA(location));
    }

    public void currentRecoveredElderly(int totalRecovered, int location) {
        output.currentRecoveredElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
    }

    public void currentRecoveredElderlyHB(int totalRecovered, int location) {
        output.currentRecoveredElderlyHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,hbl.getHealthBoardFromOA(location));
    }

    public void currentDeadElderly(int totalDead, int location) {
        output.currentDeadElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
    }

    public void currentDeadElderlyHB(int totalDead, int location) {
        output.currentDeadElderlyHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,hbl.getHealthBoardFromOA(location));
    }

    public void currentHospitalisedElderly(int totalHospitalised, int location) {
        output.currentHospitalisedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
    }

    public void currentHospitalisedElderlyHB(int totalHospitalised, int location) {
        output.currentHospitalisedElderlyHB(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,hbl.getHealthBoardFromOA(location));
    }

    public void currentNumberDZAreasWithDeadPerHealthBoard(int dz, int oa) {
        int healthBoard = hbl.getHealthBoardFromOA(oa);
        output.currentNumberDZAreasWithDeadPerHealthBoard(tcv.toTimeStepStartDate(tm.getTimeStep()), dz,healthBoard);
    }

    public IResult buildResult() {
        return output.buildResult();
    }
}
