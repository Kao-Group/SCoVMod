/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input.config;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputData {

    private String rootDirectory;
    private String movementJSONDirectoryName;
    private String seedingDirectoryName;
    private String summaryStatDirectoryName;
    private String areaLookupFileName;
    private String peopleStartLocationsFileName;
    private String infectedSeedsFileName;
    private String healthDirectoryName;
    private String healthIndexFileName;
    private String spatialSeedingGroupWeightsFileName;

    public static InputData fromJSON(String jsonContent) {

        ReadContext ctx = JsonPath.parse(jsonContent);
        String rootDirectory = ctx.read("$.data.dir");
        String movementDirectory = ctx.read("$.data.movement.dir");
        String movementJSONDirectoryName = ctx.read("$.data.movement.dir");
        String seedingDirectoryName = ctx.read("$.data.seeding.dir");
        String areaLookupFileName = ctx.read("$.data.summarystat.area-hb-lookup");
        String summaryStatDirectoryName = ctx.read("$.data.summarystat.dir");
        String peopleStartLocationsFileName = ctx.read("$.data.seeding.people-start-locations");
        String spatialSeedingGroupWeightsFileName = ctx.read("$.data.seeding.spatial-seeding-weights-file");
        String infectedSeedsFileName = ctx.read("$.data.seeding.infectedSeeds");
        String healthDirectoryName = ctx.read("$.data.health.dir");
        String healthIndexFileName = ctx.read("$.data.health.healthIndex");



        return new InputData(
                rootDirectory,
                movementDirectory,
                movementJSONDirectoryName,
                seedingDirectoryName,
                summaryStatDirectoryName,
                areaLookupFileName,
                peopleStartLocationsFileName,
                infectedSeedsFileName,
                healthDirectoryName,
                healthIndexFileName,
                spatialSeedingGroupWeightsFileName
        );
    }

    public InputData(
            String rootDirectory,
            String movementDirectory,
            String movementJSONDirectoryName,
            String seedingDirectoryName,
            String summaryStatDirectoryName,
            String areaLookupFileName,
            String peopleStartLocationsFileName,
            String infectedSeedFileName,
            String healthDirectoryName,
            String healthIndexFileName,
            String spatialSeedingGroupWeightsFileName) {
        this.rootDirectory = rootDirectory;
        this.movementJSONDirectoryName = movementJSONDirectoryName;
        this.seedingDirectoryName = seedingDirectoryName;
        this.summaryStatDirectoryName = summaryStatDirectoryName;
        this.areaLookupFileName = areaLookupFileName;
        this.peopleStartLocationsFileName = peopleStartLocationsFileName;
        this.infectedSeedsFileName = infectedSeedFileName;
        this.healthDirectoryName = healthDirectoryName;
        this.healthIndexFileName = healthIndexFileName;
        this.spatialSeedingGroupWeightsFileName = spatialSeedingGroupWeightsFileName;
    }

    public Path getRootDir() {
        return Paths.get(this.rootDirectory);
    }

    public Path getSeedingDir() {
        return this.getRootDir().resolve(Paths.get(this.seedingDirectoryName));
    }

    public Path getMovementDir() {
        return this.getRootDir().resolve(Paths.get(movementJSONDirectoryName));
    }

    public Path getSummaryStatDir() {
        return this.getRootDir().resolve(Paths.get(this.summaryStatDirectoryName));
    }

    public Path getHealthDir() {
        return this.getRootDir().resolve(Paths.get(this.healthDirectoryName));
    }

    public Path getAreaToHBLookupFile() { return this.getSummaryStatDir().resolve(Paths.get(this.areaLookupFileName)); }

    public Path getPeopleStartLocationsFile() { return this.getSeedingDir().resolve(Paths.get(this.peopleStartLocationsFileName)); }

    public Path getInfectedSeedsFile() {
        return this.getSeedingDir().resolve(Paths.get(this.infectedSeedsFileName));
    }

    public Path getHealthIndexFile() {
        return this.getHealthDir().resolve(Paths.get(this.healthIndexFileName));
    }

    public Path getSpatialSeedingGroupFileName() { return getSeedingDir().resolve(spatialSeedingGroupWeightsFileName); }

}
