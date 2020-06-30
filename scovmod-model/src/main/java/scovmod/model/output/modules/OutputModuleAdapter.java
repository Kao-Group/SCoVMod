package scovmod.model.output.modules;

import scovmod.model.output.IOutputModule;
import scovmod.model.output.IResult;
import java.time.LocalDate;

public class OutputModuleAdapter implements IOutputModule {

    @Override
    public IResult buildResult() {
        return null;
    }

    @Override
    public void currentTotalSusceptible(LocalDate date, int totalSusceptible, int location) { }

    @Override
    public void currentTotalExposed(LocalDate date, int totalExposed, int location) { }

    @Override
    public void currentTotalInfectious(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentTotalMildInfectious(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentTotalSevereInfectious(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentTotalSevereInfectiousHB(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentTotalHospitalised(LocalDate date, int totalHospitalised, int location) { }

    @Override
    public void currentTotalHospitalisedHB(LocalDate date, int totalHospitalised, int location) { }

    @Override
    public void currentTotalRecoveredHB(LocalDate date, int totalRecovered, int location) {

    }

    @Override
    public void currentTotalRecovered(LocalDate date, int totalRecovered, int location) { }

    @Override
    public void currentTotalDead(LocalDate toTimeStepStartDate, int totalDead, int location) { }

    @Override
    public void currentTotalDeadHB(LocalDate toTimeStepStartDate, int totalDead, int location) { }

    @Override
    public void currentSusceptibleYoungHB(LocalDate date, int totalSusceptible, int location) {

    }

    @Override
    public void currentExposedYoungHB(LocalDate date, int totalExposed, int location) {

    }

    @Override
    public void currentMildInfectiousYoungHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentSevereInfectiousYoungHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentHospitalisedYoungHB(LocalDate date, int totalHospitalised, int location) {

    }

    @Override
    public void currentRecoveredYoungHB(LocalDate date, int totalRecovered, int location) {

    }

    @Override
    public void currentDeadYoungHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentSusceptibleAdultHB(LocalDate date, int totalSusceptible, int location) {

    }

    @Override
    public void currentExposedAdultHB(LocalDate date, int totalExposed, int location) {

    }

    @Override
    public void currentMildInfectiousAdultHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentSevereInfectiousAdultHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentHospitalisedAdultHB(LocalDate date, int totalHospitalised, int location) {

    }

    @Override
    public void currentRecoveredAdultHB(LocalDate date, int totalRecovered, int location) {

    }

    @Override
    public void currentDeadAdultHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentSusceptibleElderlyHB(LocalDate date, int totalSusceptible, int location) {

    }

    @Override
    public void currentExposedElderlyHB(LocalDate date, int totalExposed, int location) {

    }

    @Override
    public void currentMildInfectiousElderlyHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentSevereInfectiousElderlyHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentHospitalisedElderlyHB(LocalDate date, int totalHospitalised, int location) {

    }

    @Override
    public void currentRecoveredElderlyHB(LocalDate date, int totalRecovered, int location) {

    }

    @Override
    public void currentDeadElderlyHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentPopulationHB(LocalDate date, int totalPopulation, int location) {

    }

    @Override
    public void currentTotalDeadCA(LocalDate toTimeStepStartDate, int totalDead, int location) { }

    @Override
    public void currentSusceptibleYoung(LocalDate date, int totalSusceptible, int location) { }

    @Override
    public void currentExposedYoung(LocalDate date, int totalExposed, int location) { }

    @Override
    public void currentMildInfectiousYoung(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentSevereInfectiousYoung(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentHospitalisedYoung(LocalDate date, int totalHospitalised, int location) { }

    @Override
    public void currentRecoveredYoung(LocalDate date, int totalRecovered, int location) { }

    @Override
    public void currentDeadYoung(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentSusceptibleAdult(LocalDate date, int totalSusceptible, int location) { }

    @Override
    public void currentExposedAdult(LocalDate date, int totalExposed, int location) { }

    @Override
    public void currentMildInfectiousAdult(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentSevereInfectiousAdult(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentHospitalisedAdult(LocalDate date, int totalHospitalised, int location) { }

    @Override
    public void currentRecoveredAdult(LocalDate date, int totalRecovered, int location) { }

    @Override
    public void currentDeadAdult(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentSusceptibleElderly(LocalDate date, int totalSusceptible, int location) { }

    @Override
    public void currentExposedElderly(LocalDate date, int totalExposed, int location) { }

    @Override
    public void currentMildInfectiousElderly(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentSevereInfectiousElderly(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentHospitalisedElderly(LocalDate date, int totalHospitalised, int location) { }

    @Override
    public void currentRecoveredElderly(LocalDate date, int totalRecovered, int location) { }

    @Override
    public void currentDeadElderly(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentPopulation(LocalDate date, int totalPopulation, int location) { }

    @Override
    public void currentTotalExposedHB(LocalDate date, int totalExposed, int location) {

    }

    @Override
    public void currentTotalInfectiousHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentTotalMildInfectiousHB(LocalDate date, int totalInfectious, int location) {

    }

    @Override
    public void currentNumberDZAreasWithDeadPerHealthBoard(LocalDate date, int totalPopulation, int location) { }


}
