/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.TimeStepMovements;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public class JsonMovementReader {

    private final Path basePath;

    public JsonMovementReader(Path basePath) {
        this.basePath = basePath;
    }

    public TimeStepMovements loadTimeStep(long timeStepNumber) {
        Path filePath = basePath.resolve("moveSim_step_" + timeStepNumber + ".json");
        String json = "";
        try {
            json = FileUtils.readFileToString(filePath.toFile(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonObject stepData = parser.parse(json).getAsJsonObject();
        TimeStepMovements tsm = null;

        try {
            Set<LocationIncomingPersons> ias = new HashSet<>(); //incoming persons set for this step
            JsonArray locs = stepData.getAsJsonArray("locs");
            for (int j = 0; j < locs.size(); j++) {
                JsonObject loc = locs.get(j).getAsJsonObject();
                IntSet persons = new IntOpenHashSet(); //persons per location
                IntSet visitors = new IntOpenHashSet(); //vistors per location
                JsonArray ani = loc.getAsJsonArray("ani");
                for (int k = 0; k < ani.size(); k++) {
                    persons.add(ani.get(k).getAsInt());
                }
                JsonArray trans = loc.getAsJsonArray("trans");
                if (trans != null) {
                    for (int l = 0; l < trans.size(); l++) {
                        visitors.add(trans.get(l).getAsInt());
                    }
                }
                String location = loc.get("loc").getAsString();
                int locationLength = location.length();
                int locationInt = Integer.parseInt(location.substring(3,locationLength));
                //ias.add(new LocationIncomingPersons(loc.get("loc").getAsInt(), persons, visitors));
                ias.add(new LocationIncomingPersons(locationInt, persons, visitors));
            }

            tsm = new TimeStepMovements(resolveJsonFileStep(stepData, timeStepNumber), ias);

        } catch (Exception e) {
            throw new RuntimeException("Bad json structure.  Nested exception: " + e.getMessage(), e);
        }
        return tsm;
    }

    
        private int resolveJsonFileStep(JsonObject stepData, long timeStepNumber){
        return (int) stepData.get("step").getAsLong();
    }

    public boolean stepExists(long stepNumber) {
        Path filePath = basePath.resolve("moveSim_step_" + stepNumber + ".json");
        return Files.exists(filePath);
    }
}
