package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

public class WithinGroupSampler {

    private final LocationShuffler ls;
    private final WithinLocationSampler wls;

    public WithinGroupSampler(
            LocationShuffler ls,
            WithinLocationSampler wls) {
        this.ls = ls;
        this.wls = wls;
    }

    public IntSet samplePeopleFromGroup(
            int desiredNumGroupSeeds,
            int hb,
            Int2ObjectMap<IntSet> peopleByLocation) {
        IntSet hbPeopleSampled = new IntOpenHashSet();

        for (int oa : ls.shuffleLocationsInGroup(hb)) { // The list of OAs is shuffled each time
            hbPeopleSampled.addAll(wls.samplePeopleAtLocation(oa, hb, peopleByLocation));
            if (hbPeopleSampled.size() >= desiredNumGroupSeeds) {
                break;
            }
        }
        return hbPeopleSampled;
    }
}