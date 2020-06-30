package scovmod.model.input.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputDataTest {

    @Test
    public void testJSONParsing() {
        Path jsonPath = Paths.get("src", "test", "resources", "inputData", "parameters", "scovmod-data.json");

        String json = "";
        try {
            json = FileUtils.readFileToString(new File(jsonPath.toString()), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputData data = InputData.fromJSON(json);
        assertEquals(Paths.get("/mnt/TBFT5168"), data.getRootDir());
        assertEquals(Paths.get("/mnt/TBFT5168/JSON_MOVEMENTS"), data.getMovementDir());
        assertEquals(Paths.get("/mnt/TBFT5168/SEEDING/startlocations.csv"), data.getPeopleStartLocationsFile());
        assertEquals(Paths.get("/mnt/TBFT5168/SEEDING/infectedSeeds.csv"), data.getInfectedSeedsFile());
        assertEquals(Paths.get("/mnt/TBFT5168/SUMMARYSTAT/OA_info_for_movement_model.csv"), data.getAreaToHBLookupFile());
        assertEquals(Paths.get("/mnt/TBFT5168/HEALTH/deprivationIndex.csv"), data.getHealthIndexFile());
        assertEquals(Paths.get("/mnt/TBFT5168/SEEDING/spatialSeedingGroupAttributes.csv"), data.getSpatialSeedingGroupFileName());

    }
}
