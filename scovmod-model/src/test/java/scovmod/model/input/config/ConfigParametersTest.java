package scovmod.model.input.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import scovmod.model.time.TimeConversions;
import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ConfigParametersTest {

    private static final double TOL = 1e-10;

    @Mock
    TimeConversions tcv;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private String getTestConfig(String fileName){
        Path jsonPath = Paths.get("src", "test", "resources", "inputData", "parameters", fileName);

        String json = "";
        try {
            json = FileUtils.readFileToString(new File(jsonPath.toString()), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return json;
    }

    @Test
    public void testJSONParsing() {
        String json = getTestConfig("scovmod-config.json");

        ConfigParameters params = ConfigParameters.fromJSON(json);

        assertEquals(0, params.getFirstTimeStep());
        assertEquals(56, params.getLastTimeStep());
        assertEquals(0.5, params.getTauLeapStep(),TOL);
        assertEquals(20, params.getChunkSize());
        assertEquals(123, params.getOverrideParametersTimeStep());
    }

    @Test
    public void anotherTestWithDifferentConfigValues(){
        String json = getTestConfig("scovmod-config-1.json");

        ConfigParameters params = ConfigParameters.fromJSON(json);

        assertEquals(0, params.getFirstTimeStep());
        assertEquals(28, params.getLastTimeStep());
        assertEquals(0.6, params.getTauLeapStep(),TOL);
        assertEquals(20, params.getChunkSize());
        assertEquals(321, params.getOverrideParametersTimeStep());
    }

//    @Test
//    public void testTimeStepNotInitialisedProperly() {
//        String json = getTestConfig("scovmod-config.json");
//
//        ConfigParameters params = ConfigParameters.fromJSON(json);
//
//        try {
//            params.getFirstTimeStep();
//        } catch (ModelException e) {
//        }
//
//        try {
//            params.getLastTimeStep();
//        } catch (ModelException e) {
//        }
//    }

}
