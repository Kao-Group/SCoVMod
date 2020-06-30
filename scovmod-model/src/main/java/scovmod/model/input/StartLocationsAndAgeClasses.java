/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import scovmod.model.BadInputDataException;
import scovmod.model.ModelException;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.input.seeding.AgeClass;

public class StartLocationsAndAgeClasses {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Int2IntMap peopleStartLocations = new Int2IntOpenHashMap();
    private Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();

    private final String[] expectedHeader = new String[]{"PersonID","Area","Age"};

    public StartLocationsAndAgeClasses(Path path) {
        final long startTimeLoadStartLocations = System.currentTimeMillis();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
            if (!Arrays.equals(header, expectedHeader)) {
                throw new BadInputDataException("Bad file header");
            }

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] toks = line.replaceAll("\\s+", "").split(",");
                int personID = Integer.parseInt(toks[0]);
                String locationString = toks[1];
                int locationLength = locationString.length();
                int location = Integer.parseInt(locationString.substring(3,locationLength));
                String ageClass = toks[2];
                if (peopleStartLocations.containsKey(personID)) {
                    throw new BadInputDataException("This person appears more than once in start locations");
                } else {
                    peopleStartLocations.put(personID, location);
                }
                if (peopleAgeClasses.containsKey(personID)) {
                    throw new BadInputDataException("This person appears more than once in age classes");
                } else {

                    peopleAgeClasses.put(personID, getAgeClass(ageClass));
                }
            }
        } catch (IOException e) {
            throw new ModelException("Exception reading person location data", e);
        }
        final long endTimeLoadStartLocations = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Total execution time for loading start locations: " + (double) (endTimeLoadStartLocations - startTimeLoadStartLocations) / 1000);
        }
    }

    private AgeClass getAgeClass(String ageClass) {
        if(ageClass.equals("Young")) return AgeClass.YOUNG;
        else if(ageClass.equals("Adult")) return AgeClass.ADULT;
        else return AgeClass.ELDERLY;
    }

    public Int2IntMap getLocationsByPeopleId() {
        return peopleStartLocations;
    }
    public Int2ObjectMap<AgeClass> getPeopleAgeClasses() { return peopleAgeClasses; }
}
