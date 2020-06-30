package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.util.Map;

public class NationalSampler {
    private SpatialGroupMultinomial sgm;
    private WithinGroupSampler wgs;

    Int2ObjectMap<IntSet> peopleByLocation;

    public NationalSampler(
            SpatialGroupMultinomial sgm,
            WithinGroupSampler wgs,
            Int2ObjectMap<IntSet> peopleByLocation
            ) {
        this.sgm = sgm;
        this.wgs = wgs;
        this.peopleByLocation = peopleByLocation;
    }

    public IntSet getSampledPeople() {
        IntSet sampledPeople = new IntOpenHashSet();
        Map<Integer,Integer> seedsPerGroup = sgm.getNumberSeedsPerGroupMap();

        for (Map.Entry<Integer, Integer> entry : seedsPerGroup.entrySet()) {
            int groupId = entry.getKey();
            int numPeopleForGroup = entry.getValue(); //Number of people to sample for this particular group
            sampledPeople.addAll(
                    wgs.samplePeopleFromGroup(
                            numPeopleForGroup,
                            groupId,
                            peopleByLocation
                    )
            );
        }
        return sampledPeople;
    }
}
