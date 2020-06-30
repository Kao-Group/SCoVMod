package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;

public class NationalSamplerFactory {

    private SpatialGroupMultinomial sgs;
    private WithinGroupSampler asbg;

    public NationalSamplerFactory(
            SpatialGroupMultinomial sgs,
            WithinGroupSampler asbg
    ){
        this.sgs = sgs;
        this.asbg = asbg;
    }

    public NationalSampler build(Int2ObjectMap<IntSet> peopleByLocation) {
        return new NationalSampler(
                sgs,
                asbg,
                peopleByLocation
        );
    }
}
