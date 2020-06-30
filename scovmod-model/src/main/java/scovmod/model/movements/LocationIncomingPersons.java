package scovmod.model.movements;

import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Objects;

public class LocationIncomingPersons {

    private int location;
    private IntSet personIDs;
    private IntSet visitorPersonIds;

    public LocationIncomingPersons(int location, IntSet personIds, IntSet visitorPersonIds) {
        this.location = location;
        this.personIDs = personIds;
        this.visitorPersonIds = visitorPersonIds;
    }

    public int getLocationId() {
        return this.location;
    }

    public IntSet getNewPersonIds() {
        return this.personIDs;
    }

    public IntSet getTransitoryVisitors() {
        return this.visitorPersonIds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.location;
        hash = 41 * hash + Objects.hashCode(this.personIDs);
        hash = 41 * hash + Objects.hashCode(this.visitorPersonIds);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LocationIncomingPersons other = (LocationIncomingPersons) obj;
        if (this.location != other.location) {
            return false;
        }
        if (!Objects.equals(this.personIDs, other.personIDs)) {
            return false;
        }
        if (!Objects.equals(this.visitorPersonIds, other.visitorPersonIds)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IncomingPerson{" + "location=" + location + ", personIDs=" + personIDs + ", visitorPersonIds=" + visitorPersonIds + '}';
    }
}
