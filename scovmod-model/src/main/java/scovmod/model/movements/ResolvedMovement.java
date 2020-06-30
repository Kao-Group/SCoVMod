package scovmod.model.movements;

import scovmod.model.state.infection.InfectionState;
import java.util.Objects;

public class ResolvedMovement {

    private final int personID;
    private final int source;
    private final int destination;
    private final InfectionState state;

    public ResolvedMovement(
            int personID,
            int source,
            int destination,
            InfectionState state) {
        this.personID = personID;
        this.source = source;
        this.destination = destination;
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResolvedMovement that = (ResolvedMovement) o;
        return personID == that.personID &&
                source == that.source &&
                destination == that.destination &&
                state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, source, destination, state);
    }

    public int getPersonID() {
        return personID;
    }

    public int getDestination() {
        return destination;
    }

    public int getSource() {
        return source;
    }   

    public InfectionState getState() {
        return state;
    }
    
}
