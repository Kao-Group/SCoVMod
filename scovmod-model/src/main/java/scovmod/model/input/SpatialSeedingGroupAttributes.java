package scovmod.model.input;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.BadInputDataException;
import scovmod.model.ModelException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpatialSeedingGroupAttributes {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Map<Integer, Double> groupWeights = new HashMap<>();
    private Map<Integer, Double> groupLoadFactor = new HashMap<>();
    private final String[] expectedHeader = new String[]{"group","weight"};

    public SpatialSeedingGroupAttributes(Path path) {
        final long startTimeLoadStartLocations = System.currentTimeMillis();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
            if (!Arrays.equals(header, expectedHeader)) {
                throw new BadInputDataException("Bad file header");
            }

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] toks = line.replaceAll("\\s+", "").split(",");
                double weight = Double.parseDouble(toks[1]);
               // double loadFactor = Double.parseDouble(toks[1]);
                int group = Integer.parseInt(toks[0]);
                if (groupWeights.containsKey(group)) {
                    throw new BadInputDataException("This group appears more than once");
                } else {
                    groupWeights.put(group, weight);
                }
//                if (groupLoadFactor.containsKey(group)) {
//                    throw new BadInputDataException("This group appears more than once");
//                } else {
//                    groupLoadFactor.put(group, loadFactor);
//                }
            }
        } catch (IOException e) {
            throw new ModelException("Exception reading quintile group weight data", e);
        }
        final long endTimeLoadStartLocations = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Total execution time for loading quintile group weight data: " + (double) (endTimeLoadStartLocations - startTimeLoadStartLocations) / 1000);
        }
    }

    public Map<Integer, Double> getGroupWeightsMap() {
        return groupWeights;
    }

    public Map<Integer, Double> getGroupLoadFactor() {
        return groupLoadFactor;
    }
}
