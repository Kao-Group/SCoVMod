package scovmod.model.output;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import scovmod.model.BadInputDataException;
import scovmod.model.ModelException;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HealthBoardLookup {

    private final String[] expectedHeader = new String[]{"output_area", "intermediate_zone", "super_layer", "workplace_zone", "local_authority_name", "local_authority", "health_board_name", "health_board", "easting", "northing", "HH_population", "WF_population", "young_population", "working_age_population", "old_population"};

    private Int2IntMap izToHealthBoardLookup = new Int2IntOpenHashMap();
    private Int2IntMap oaToHealthBoardLookup = new Int2IntOpenHashMap();
    private Int2IntMap dzToHealthBoardLookup = new Int2IntOpenHashMap();
    private Int2IntMap laToHealthBoardLookup = new Int2IntOpenHashMap();
    private Int2IntMap oaToCouncilAreaLookup = new Int2IntOpenHashMap();

    private Int2IntMap oaToIZLookup = new Int2IntOpenHashMap();
    private Int2IntMap oaToLALookup = new Int2IntOpenHashMap();
    private Int2IntMap oaToDZLookup = new Int2IntOpenHashMap();

    private Int2ObjectMap<List<Integer>> oasByHB = new Int2ObjectOpenHashMap<>();
    private Int2ObjectMap<List<Integer>> oasByLA = new Int2ObjectOpenHashMap<>();
    //private IntSet healthBoardSet;

    public HealthBoardLookup(Path filePath) {
        // healthBoardSet = new IntOpenHashSet();
        try (BufferedReader br = Files.newBufferedReader(filePath, Charset.defaultCharset())) {
            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
            if (!Arrays.equals(header, expectedHeader)) {
                throw new BadInputDataException("Bad file header");
            }
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] toks = line.replaceAll("\\s+", "").split(","); //don't really need split here - only cph given.
                String oaString = toks[0];
                String izString = toks[1];
                String dzString = toks[2];
                String laString = toks[5];
                String hbString = toks[7];
                int izLength = izString.length();
                int iz = Integer.parseInt(izString.substring(3, izLength));
                int dzLength = dzString.length();
                int dz = Integer.parseInt(dzString.substring(3, dzLength));
                int hbLength = hbString.length();
                int hb = Integer.parseInt(hbString.substring(3, hbLength));
                int oaLength = oaString.length();
                int oa = Integer.parseInt(oaString.substring(3, oaLength));
                int laLength = laString.length();
                int la = Integer.parseInt(laString.substring(3, laLength));
                izToHealthBoardLookup.put(iz, hb);
                oaToHealthBoardLookup.put(oa, hb);
                dzToHealthBoardLookup.put(dz,hb);
                laToHealthBoardLookup.put(la, hb);
                oaToCouncilAreaLookup.put(oa, la);
                oaToIZLookup.put(oa, iz);
                oaToDZLookup.put(oa,dz);
                oaToLALookup.put(oa, la);
                if (oasByHB.containsKey(hb)) {
                    List<Integer> oas = oasByHB.get(hb);
                    oas.add(oa);
                    oasByHB.put(hb,oas);
                } else {
                    List<Integer> oas = new ArrayList<>();
                    oas.add(oa);
                    oasByHB.put(hb,oas);
                }
                if (oasByLA.containsKey(la)) {
                    List<Integer> oas = oasByLA.get(la);
                    oas.add(oa);
                    oasByLA.put(la,oas);
                } else {
                    List<Integer> oas = new ArrayList<>();
                    oas.add(oa);
                    oasByLA.put(la,oas);
                }

            }
        } catch (IOException ex) {
            throw new ModelException("Exception reading file for HealthBoard Lookup", ex);
        }
    }

    public int getHealthBoardFromIZ(int izArea) {
        return izToHealthBoardLookup.get(izArea);
    }
    public int getHealthBoardFromOA(int oaArea) {
        return oaToHealthBoardLookup.get(oaArea);
    }
    public int getHealthBoardFromDZ(int dzArea) {
        return dzToHealthBoardLookup.get(dzArea);
    }
    public int getCouncilAreaFromOA(int oaArea) {
        return oaToCouncilAreaLookup.get(oaArea);
    }
    public int getHealthBoardFromLA(int laArea) {
        return laToHealthBoardLookup.get(laArea);
    }
    public int getIZAreaFromOA(int oaArea) {
        return oaToIZLookup.get(oaArea);
    }
    public int getDZAreaFromOA(int oaArea) {
        return oaToDZLookup.get(oaArea);
    }
    public Int2IntMap getHealthBoardFromIZLookupMap() {
        return izToHealthBoardLookup;
    }
    public Int2IntMap getHealthBoardFromOALookupMap() {
        return oaToHealthBoardLookup;
    }
    public Int2IntMap getHealthBoardFromLALookupMap() {
        return laToHealthBoardLookup;
    }

    public Int2IntMap getOaToCouncilAreaLookup() {
        return oaToCouncilAreaLookup;
    }

    public Int2IntMap getIZLookupMap() {
        return oaToIZLookup;
    }
    public Int2IntMap getLALookupMap() {
        return oaToLALookup;
    }

    public Int2ObjectMap<List<Integer>> getOasByHB() {
        return oasByHB;
    }

    public Int2ObjectMap<List<Integer>> getOasByLA() {
        return oasByLA;
    }
}
