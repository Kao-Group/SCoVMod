package scovmod.model.input.config;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class ConfigParameters {

    private long firstTimeStep = -1;
    private long lastTimeStep = -1;
    private long overrideParametersTimeStep = -1;
    private double tauLeapStep = 0;
    private int chunkSize;

    public static ConfigParameters fromJSON(String jsonContent) {

        ReadContext ctx = JsonPath.parse(jsonContent);

        int startTimeStep = ctx.read("$.configParameters.time.start-timestep");
        int lastTimeStepInclusive = ctx.read("$.configParameters.time.end-timestep-inclusive");
        int overrideParametersTimeStep = ctx.read("$.configParameters.time.override-parameters-timestep");
        double tauLeapStep = ctx.read("$.configParameters.time.tau-leap-step");
        int chunkSize = ctx.read("$.configParameters.time.chunkSize");

        return new ConfigParameters(
                startTimeStep,
                lastTimeStepInclusive,
                overrideParametersTimeStep,
                tauLeapStep,
                chunkSize);
    }

    public ConfigParameters(
            int startTimeStep,
            int lastTimeStepInclusive,
            int overrideParametersTimeStep,
            double tauLeapStep,
            int chunkSize) {
        this.firstTimeStep = startTimeStep;
        this.lastTimeStep = lastTimeStepInclusive;
        this.overrideParametersTimeStep = overrideParametersTimeStep;
        this.chunkSize = chunkSize;
        this.tauLeapStep = tauLeapStep;
    }

    public long getFirstTimeStep() {
        return firstTimeStep;
    }

    public long getLastTimeStep() {
        return lastTimeStep;
    }

    public long getOverrideParametersTimeStep() { return overrideParametersTimeStep; }

    public int getChunkSize() {
        return chunkSize;
    }

    public double getTauLeapStep() { return tauLeapStep; }
}
