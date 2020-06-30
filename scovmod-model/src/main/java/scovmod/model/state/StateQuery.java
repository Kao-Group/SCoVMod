package scovmod.model.state;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.cache.exposed.*;
import scovmod.model.state.cache.dead.*;
import scovmod.model.state.cache.hospitalised.*;
import scovmod.model.state.cache.recovered.*;
import scovmod.model.state.cache.mildInfectious.*;
import scovmod.model.state.cache.severeInfectious.*;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.state.population.LocalPopulationIndex;
import scovmod.model.util.math.Random;
import it.unimi.dsi.fastutil.ints.IntSet;

public class StateQuery {

    private LocalPopulationIndex lpi;
    private Random rnd;
    private ExposedYoungLocations eyl;
    private ExposedAdultLocations eal;
    private ExposedElderlyLocations eel;

    private DeadYoungLocations dyl;
    private DeadAdultLocations dal;
    private DeadElderlyLocations del;

    private HospitalisedYoungLocations hyl;
    private HospitalisedAdultLocations hal;
    private HospitalisedElderlyLocations hel;

    private RecoveredYoungLocations ryl;
    private RecoveredAdultLocations ral;
    private RecoveredElderlyLocations rel;

    private MildInfectiousYoungLocations miyl;
    private MildInfectiousAdultLocations mial;
    private MildInfectiousElderlyLocations miel;

    private SevereInfectiousYoungLocations siyl;
    private SevereInfectiousAdultLocations sial;
    private SevereInfectiousElderlyLocations siel;

    public StateQuery(
            LocalPopulationIndex lpi,
            ExposedYoungLocations eyl,
            ExposedAdultLocations eal,
            ExposedElderlyLocations eel,
            DeadYoungLocations dyl,
            DeadAdultLocations dal,
            DeadElderlyLocations del,
            HospitalisedYoungLocations hyl,
            HospitalisedAdultLocations hal,
            HospitalisedElderlyLocations hel,
            RecoveredYoungLocations ryl,
            RecoveredAdultLocations ral,
            RecoveredElderlyLocations rel,
            MildInfectiousYoungLocations miyl,
            MildInfectiousAdultLocations mial,
            MildInfectiousElderlyLocations miel,
            SevereInfectiousYoungLocations siyl,
            SevereInfectiousAdultLocations sial,
            SevereInfectiousElderlyLocations siel,
            Random rnd) {
        this.lpi = lpi;
        this.rnd = rnd;
        this.eyl = eyl;
        this.eal = eal;
        this.eel = eel;
        this.dyl = dyl;
        this.dal = dal;
        this.del = del;
        this.hyl = hyl;
        this.hal = hal;
        this.hel = hel;
        this.ryl = ryl;
        this.ral = ral;
        this.rel = rel;
        this.miyl = miyl;
        this.mial = mial;
        this.miel = miel;
        this.siyl = siyl;
        this.sial = sial;
        this.siel = siel;
    }

    public IntSet getAllActiveLocationIds() {
        return lpi.getAllLocationIds();
    }

    public int getRandomPersonAtLocation(int locationId, InfectionState state) {
        return rnd.sampleOne(lpi.getLocalPopulation(locationId).getAllInState(state));
    }

    //Temporary
    public IntSet combine(IntSet... sets)
    {
        IntSet collection = new IntOpenHashSet();
        for (IntSet e: sets)
            collection.addAll(e);
        return collection;
    }

    public IntSet getAllInfectiousLocations(){
        return combine(getMildInfectiousYoungLocations(),getMildInfectiousAdultLocations(),getMildInfectiousElderlyLocations(),
                getSevereInfectiousYoungLocations(),getSevereInfectiousAdultLocations(),getSevereInfectiousElderlyLocations());
    }

    public IntSet getAllHospitalisedLocations(){
        return combine(getHospitalisedYoungLocations(),getHospitalisedAdultLocations(),getHospitalisedElderlyLocations());
    }

    public IntSet getAllExposedLocations(){
        return combine(getExposedYoungLocations(),getExposedAdultLocations(),getExposedElderlyLocations());
    }

    public IntSet getAllMildInfectiousLocations(){
        return combine(getMildInfectiousYoungLocations(),getMildInfectiousAdultLocations(),getMildInfectiousElderlyLocations());
    }

    public IntSet getAllSevereInfectiousLocations(){
        return combine(getSevereInfectiousYoungLocations(),getSevereInfectiousAdultLocations(),getSevereInfectiousElderlyLocations());
    }

    public IntSet getExposedYoungLocations() {
        return eyl.getExposedYoungLocations();
    }

    public IntSet getExposedAdultLocations() {
        return eal.getExposedAdultLocations();
    }

    public IntSet getExposedElderlyLocations() {
        return eel.getExposedElderlyLocations();
    }

    public IntSet getDeadYoungLocations() {
        return dyl.getDeadYoungLocations();
    }

    public IntSet getDeadAdultLocations() {
        return dal.getDeadAdultLocations();
    }

    public IntSet getDeadElderlyLocations() {
        return del.getDeadElderlyLocations();
    }

    public IntSet getHospitalisedYoungLocations() {
        return hyl.getHospitalisedYoungLocations();
    }

    public IntSet getHospitalisedAdultLocations() {
        return hal.getHospitaliseAdultLocations();
    }

    public IntSet getHospitalisedElderlyLocations() {
        return hel.getHospitalisedElderlyLocations();
    }

    public IntSet getRecoveredYoungLocations() {
        return ryl.getRecoveredYoungLocations();
    }

    public IntSet getRecoveredAdultLocations() {
        return ral.getRecoveredAdultLocations();
    }

    public IntSet getRecoveredElderlyLocations() {
        return rel.getRecoveredElderlyLocations();
    }

    public IntSet getMildInfectiousYoungLocations() {
        return miyl.getMildInfectiousYoungLocations();
    }

    public IntSet getMildInfectiousAdultLocations() {
        return mial.getMildInfectiousAdultLocations();
    }

    public IntSet getMildInfectiousElderlyLocations() {
        return miel.getMildInfectiousElderlyLocations();
    }

    public IntSet getSevereInfectiousYoungLocations() {
        return siyl.getSevereInfectiousYoungLocations();
    }

    public IntSet getSevereInfectiousAdultLocations() {
        return sial.getSevereInfectiousAdultLocations();
    }

    public IntSet getSevereInfectiousElderlyLocations() {
        return siel.getSevereInfectiousElderlyLocations();
    }

    public LocalPopulation getCopyOfLocalPopulation(int locationId) {
        return lpi.getLocalPopulation(locationId);
    }

    public InfectionState getPersonInfectionStatus(int personId) {
        return lpi.getPersonInfectionStatus(personId);
    }

    public  boolean isShowingSevereSymptoms(int personId) {
        InfectionState state = lpi.getPersonInfectionStatus(personId);
        if(state==InfectionState.SEVERE_INFECTIOUS_YOUNG
                || state==InfectionState.SEVERE_INFECTIOUS_ADULT
                || state==InfectionState.SEVERE_INFECTIOUS_ELDERLY
                || state==InfectionState.HOSPITALISED_YOUNG
                || state==InfectionState.HOSPITALISED_ADULT
                || state==InfectionState.HOSPITALISED_ELDERLY
                || state==InfectionState.DEAD_YOUNG
                || state==InfectionState.DEAD_ADULT
                || state==InfectionState.DEAD_ELDERLY){ return true;}
        else {return false;}
    }

    public int getPersonLocation(int personId) {
        return lpi.getPersonLocationId(personId);
    }

    public int getLocationSize(int locationId) {
        return getCopyOfLocalPopulation(locationId).getSize();
    }
}
