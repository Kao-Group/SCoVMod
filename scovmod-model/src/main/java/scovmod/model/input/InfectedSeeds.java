/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import it.unimi.dsi.fastutil.ints.*;
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

public class InfectedSeeds {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private IntSet infectedSeeds = new IntOpenHashSet();

    private final String[] expectedHeader = new String[]{"PersonID"};

    public InfectedSeeds(Path path) {
        final long startTimeLoadInfectedSeeds = System.currentTimeMillis();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
            if (!Arrays.equals(header, expectedHeader)) {
                throw new BadInputDataException("Bad file header");
            }

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] toks = line.replaceAll("\\s+", "").split(",");
                int personID = Integer.parseInt(toks[0]);
                if (infectedSeeds.contains(personID)) {
                    throw new BadInputDataException("This person appears more than once in infected seeds");
                } else {
                    infectedSeeds.add(personID);
                }
            }
        } catch (IOException e) {
            throw new ModelException("Exception reading infected seed data", e);
        }
        final long endTimeLoadInfectedSeeds = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Total execution time for loading start locations: " + (double) (endTimeLoadInfectedSeeds - startTimeLoadInfectedSeeds) / 1000);
        }
    }
    public IntSet getInfectedSeeds() { return infectedSeeds; }
}
