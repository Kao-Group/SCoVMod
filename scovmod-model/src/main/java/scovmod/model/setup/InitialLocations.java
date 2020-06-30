/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.setup;

import scovmod.model.input.StartLocationsAndAgeClasses;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitialLocations {

    private final StartLocationsAndAgeClasses startLocations;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public InitialLocations(StartLocationsAndAgeClasses startLocations) {
        this.startLocations = startLocations;
    }

    public void setup(Int2ObjectMap<IntSet> peopleByLocation) {
    	/*
    	 * Note this mutates the (blank) input map
    	 */
        if (peopleByLocation.size() > 0) {
            throw new UnsupportedOperationException("'peopleByLocation' must be empty");
        }

        IntSet personKeys = startLocations.getLocationsByPeopleId().keySet();
        if (log.isDebugEnabled()) {
            log.debug("About to initialise " + startLocations.getLocationsByPeopleId().size() + " people");
        }
        for (int personID : personKeys) {
            int location = startLocations.getLocationsByPeopleId().get(personID);
            if (!peopleByLocation.containsKey(location)) {
                peopleByLocation.put(location, new IntOpenHashSet());
            }
            peopleByLocation.get(location).add(personID);
        }
    }    
}
