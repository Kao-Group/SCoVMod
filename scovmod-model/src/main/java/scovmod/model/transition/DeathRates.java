package scovmod.model.transition;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import scovmod.model.input.DeprivationIndexPerHealthBoard;
import scovmod.model.input.config.Parameters;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.StateQuery;

public class DeathRates {

    private final Parameters params;
    private final Int2DoubleMap deprivationIndexPerLA;
    private final StateQuery sq;
    private final HealthBoardLookup hbl;

    public DeathRates(Parameters params,
                      DeprivationIndexPerHealthBoard diph,
                      StateQuery sq,
                      HealthBoardLookup hbl) {
        this.params = params;
        this.deprivationIndexPerLA = diph.getDeprivationIndexPerHB();
        this.sq = sq;
        this.hbl = hbl;
    }

    public double getDeathRateModifierBasedOnHealthIndex(int personID) {
        int location = sq.getPersonLocation(personID);
        int councilArea = hbl.getCouncilAreaFromOA(location);
        //int healthBoard = hbl.getHealthBoardFromOA(location);
        double H_ca = deprivationIndexPerLA.get(councilArea);
        //System.out.println("H_ca: "+H_ca);
        if(H_ca==0) return 1;
        double health_modifier = params.getHealthModifier();
        double H_av = params.getAverageHealthIndexPerCouncilArea(); //NB This is currently HB average
        //System.out.println("H_hb-H_av: "+(H_hb-H_av));
        //System.out.println("health_modifier*(H_hb-H_av): "+(health_modifier*(H_hb-H_av)));
        return (1+(health_modifier*(H_ca-H_av)));
    }
}
