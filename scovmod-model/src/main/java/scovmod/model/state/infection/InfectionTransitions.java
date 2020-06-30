package scovmod.model.state.infection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static scovmod.model.state.infection.InfectionState.*;

public class InfectionTransitions {

	private Map<InfectionState, Set<InfectionState>> permittedTransitions = new HashMap<>();
	
	public InfectionTransitions(){
		permittedTransitions.put(UNDEFINED, setOf(SUSCEPTIBLE));
		permittedTransitions.put(SUSCEPTIBLE, setOf(EXPOSED_YOUNG, EXPOSED_ADULT, EXPOSED_ELDERLY));

		permittedTransitions.put(EXPOSED_YOUNG, setOf(RECOVERED_YOUNG,MILD_INFECTIOUS_YOUNG));
		permittedTransitions.put(EXPOSED_ADULT, setOf(RECOVERED_ADULT,MILD_INFECTIOUS_ADULT));
		permittedTransitions.put(EXPOSED_ELDERLY, setOf(RECOVERED_ELDERLY,MILD_INFECTIOUS_ELDERLY));

		permittedTransitions.put(MILD_INFECTIOUS_YOUNG, setOf(RECOVERED_YOUNG,SEVERE_INFECTIOUS_YOUNG));
		permittedTransitions.put(MILD_INFECTIOUS_ADULT, setOf(RECOVERED_ADULT,SEVERE_INFECTIOUS_ADULT));
		permittedTransitions.put(MILD_INFECTIOUS_ELDERLY, setOf(RECOVERED_ELDERLY,SEVERE_INFECTIOUS_ELDERLY));

		permittedTransitions.put(SEVERE_INFECTIOUS_YOUNG, setOf(RECOVERED_YOUNG,HOSPITALISED_YOUNG, DEAD_YOUNG));
		permittedTransitions.put(SEVERE_INFECTIOUS_ADULT, setOf(RECOVERED_ADULT,HOSPITALISED_ADULT, DEAD_ADULT));
		permittedTransitions.put(SEVERE_INFECTIOUS_ELDERLY, setOf(RECOVERED_ELDERLY,HOSPITALISED_ELDERLY,DEAD_ELDERLY));

		permittedTransitions.put(HOSPITALISED_YOUNG, setOf(RECOVERED_YOUNG, DEAD_YOUNG));
		permittedTransitions.put(HOSPITALISED_ADULT, setOf(RECOVERED_ADULT, DEAD_ADULT));
		permittedTransitions.put(HOSPITALISED_ELDERLY, setOf(RECOVERED_ELDERLY, DEAD_ELDERLY));

		permittedTransitions.put(RECOVERED_YOUNG, setOf());
		permittedTransitions.put(RECOVERED_ADULT, setOf());
		permittedTransitions.put(RECOVERED_ELDERLY, setOf());

		permittedTransitions.put(DEAD_YOUNG, setOf());
		permittedTransitions.put(DEAD_ADULT, setOf());
		permittedTransitions.put(DEAD_ELDERLY, setOf());
	}
	
	public boolean permittedTransition(InfectionState from, InfectionState to) {
		return permittedTransitions.get(from).contains(to);
	}
	
	private Set<InfectionState> setOf(InfectionState... states){
		return new HashSet<InfectionState>(Arrays.asList(states));
	}

}
