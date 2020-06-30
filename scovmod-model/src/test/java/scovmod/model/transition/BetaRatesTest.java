package scovmod.model.transition;

import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BetaRatesTest {

    private static final double TOL = 1e-10;
    //private final long CURRENT_EPOCH_TIMESTEP = 1l;

    @Mock
    Parameters params;
    @Mock
    ConfigParameters config;
    @Mock
    StateQuery sq;
    @Mock
    TimeManager tm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //when(tm.getTimeStep()).thenReturn(CURRENT_EPOCH_TIMESTEP);
        when(params.getBetaFactorForLockdown()).thenReturn(0.5);
        when(params.getsToE_Mild_YoungRate_Night()).thenReturn(0.555);
        when(params.getsToE_Mild_AdultRate_Night()).thenReturn(0.555);
        when(params.getsToE_Mild_ElderlyRate_Night()).thenReturn(0.555);
        when(params.getsToE_Severe_YoungRate_Night()).thenReturn(1.2);
        when(params.getsToE_Severe_AdultRate_Night()).thenReturn(1.2);
        when(params.getsToE_Severe_ElderlyRate_Night()).thenReturn(1.2);
        when(params.getsToE_Mild_YoungRate_Day()).thenReturn(0.55);
        when(params.getsToE_Mild_AdultRate_Day()).thenReturn(0.55);
        when(params.getsToE_Mild_ElderlyRate_Day()).thenReturn(0.55);
        when(params.getsToE_Severe_YoungRate_Day()).thenReturn(1.0);
        when(params.getsToE_Severe_AdultRate_Day()).thenReturn(1.0);
        when(params.getsToE_Severe_ElderlyRate_Day()).thenReturn(1.0);

        when(params.getBetaYoungMild_night_lockdown()).thenReturn(0.55);
        when(params.getBetaAdultMild_night_lockdown()).thenReturn(0.55);
        when(params.getBetaElderlyMild_night_lockdown()).thenReturn(0.55);
        when(params.getBetaYoungSevere_night_lockdown()).thenReturn(1.0);
        when(params.getBetaAdultSevere_night_lockdown()).thenReturn(1.0);
        when(params.getBetaElderlySevere_night_lockdown()).thenReturn(1.0);
        when(params.getBetaYoungMild_day_lockdown()).thenReturn(0.55);
        when(params.getBetaAdultMild_day_lockdown()).thenReturn(0.55);
        when(params.getBetaElderlyMild_day_lockdown()).thenReturn(0.55);
        when(params.getBetaYoungSevere_day_lockdown()).thenReturn(0.5);
        when(params.getBetaAdultSevere_day_lockdown()).thenReturn(0.5);
        when(params.getBetaElderlySevere_day_lockdown()).thenReturn(0.5);
    }


    @Test
    public void checkBetaRatesNotInLockdown_atNight() {
        BetaRates instance = new BetaRates(params, config, sq, tm);
        when(tm.getTimeStep()).thenReturn(5l);
        when(config.getOverrideParametersTimeStep()).thenReturn(10l);
        assertEquals(0.666, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG), TOL);
        assertEquals(1.2, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY), TOL);
    }

    @Test
    public void checkBetaRatesNotInLockdown_duringDay() {
        BetaRates instance = new BetaRates(params, config, sq, tm);
        when(tm.getTimeStep()).thenReturn(6l);
        when(config.getOverrideParametersTimeStep()).thenReturn(10l);
        assertEquals(0.55, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG), TOL);
        assertEquals(1.0, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY), TOL);
    }

    @Test
    public void checkBetaRatesInLockdown_atNight() {
        BetaRates instance = new BetaRates(params, config, sq, tm);
        when(tm.getTimeStep()).thenReturn(5l);
        when(config.getOverrideParametersTimeStep()).thenReturn(4l);
        assertEquals(0.55, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG), TOL);
        assertEquals(1.0, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY), TOL);

    }

    @Test
    public void checkBetaRatesInLockdown_duringDay() {
        BetaRates instance = new BetaRates(params, config, sq, tm);
        when(tm.getTimeStep()).thenReturn(6l);
        when(config.getOverrideParametersTimeStep()).thenReturn(4l);
        assertEquals(0.275, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG), TOL);
        assertEquals(0.5, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY), TOL);

    }

}
