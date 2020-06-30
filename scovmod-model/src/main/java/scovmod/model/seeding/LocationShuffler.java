package scovmod.model.seeding;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.util.math.Random;

import java.util.List;

public class LocationShuffler {
    private Int2ObjectMap<List<Integer>> oasByLA;
    private Random rand;

    public LocationShuffler(
            HealthBoardLookup hbl,
            Random rand) {
        this.oasByLA = hbl.getOasByLA();
        this.rand = rand;
    }

    public List<Integer> shuffleLocationsInGroup(int la) {
        return rand.shuffleList(oasByLA.get(la));
    }
}
