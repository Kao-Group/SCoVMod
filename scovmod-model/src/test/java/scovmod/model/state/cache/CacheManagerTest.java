package scovmod.model.state.cache;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.state.population.LocalPopulationIndex;

public class CacheManagerTest {

	private final boolean ON = true;
	private final boolean OFF = false;

	private final int PERSON = 1;

	private final int LOCATION_1 = 100;
	private final int LOCATION_2 = 100;

	@Test
	public void notifyingMultipleCaches(){
		Cache cache1 = mock(Cache.class);
		Cache cache2 = mock(Cache.class);
		Cache cache3 = mock(Cache.class);

		LocalPopulationIndex lpi = mock(LocalPopulationIndex.class);
		LocalPopulation partition_1 = mock(LocalPopulation.class);
		LocalPopulation partition_2 = mock(LocalPopulation.class);
		when(lpi.getLocalPopulation(LOCATION_1)).thenReturn(partition_1, partition_2);

		CacheManager instance = new CacheManager(lpi, cache1, cache2, cache3);

		instance.notifyEdgeCaseDetectedAt(LOCATION_1);
		verify(lpi, times(1)).getLocalPopulation(LOCATION_1);
		verify(cache1, times(1)).wholeLocationUpdate(LOCATION_1, partition_1);
		verify(cache2, times(1)).wholeLocationUpdate(LOCATION_1, partition_1);
		verify(cache3, times(1)).wholeLocationUpdate(LOCATION_1, partition_1);
		verifyNoMoreInteractions(lpi, cache1, cache2, cache3);

		instance.notifyNonEdgeCaseStateChange(PERSON, InfectionState.SUSCEPTIBLE, InfectionState.EXPOSED_YOUNG, LOCATION_2);
		verify(cache1, times(1)).notifyNonEdgeCaseStateChange(PERSON, InfectionState.SUSCEPTIBLE, InfectionState.EXPOSED_YOUNG, LOCATION_2);
		verify(cache2, times(1)).notifyNonEdgeCaseStateChange(PERSON, InfectionState.SUSCEPTIBLE, InfectionState.EXPOSED_YOUNG, LOCATION_2);
		verify(cache3, times(1)).notifyNonEdgeCaseStateChange(PERSON, InfectionState.SUSCEPTIBLE, InfectionState.EXPOSED_YOUNG, LOCATION_2);
		verifyNoMoreInteractions(lpi, cache1, cache2, cache3);

		instance.notifyNonEdgeCaseMovement(PERSON, OFF, LOCATION_1, InfectionState.SUSCEPTIBLE);
		verify(cache1, times(1)).notifyNonEdgeCaseMovement(PERSON, OFF, LOCATION_1, InfectionState.SUSCEPTIBLE);
		verify(cache2, times(1)).notifyNonEdgeCaseMovement(PERSON, OFF, LOCATION_1, InfectionState.SUSCEPTIBLE);
		verify(cache3, times(1)).notifyNonEdgeCaseMovement(PERSON, OFF, LOCATION_1, InfectionState.SUSCEPTIBLE);
		verifyNoMoreInteractions(lpi, cache1, cache2, cache3);
	}
}
