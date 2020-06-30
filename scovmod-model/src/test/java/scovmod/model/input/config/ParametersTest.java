package scovmod.model.input.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParametersTest {

    private static final double TOL = 1e-10;

    @Test
    public void testJSONParsing() {
        Path jsonPath = Paths.get("src", "test", "resources", "inputData", "parameters", "scovmod-parameters.json");

        System.out.println(jsonPath.toString());

        String json = "";
        try {
            json = FileUtils.readFileToString(new File(jsonPath.toString()), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parameters params = Parameters.fromJSON(json);

        //assertEquals(0.308, params.getInfectiousSensitivity(), TOL);

        assertEquals(0.00419749473795, params.getsToE_Mild_YoungRate_Day(), TOL);
        assertEquals(0.0419749473795, params.getsToE_Mild_AdultRate_Day(), TOL);
        assertEquals(0.0419749473795, params.getsToE_Mild_ElderlyRate_Day(), TOL);
        assertEquals(0.0419749473795, params.getsToE_Severe_YoungRate_Day() , TOL);
        assertEquals(0.00419749473795, params.getsToE_Severe_AdultRate_Day(), TOL);
        assertEquals(0.00419749473795, params.getsToE_Severe_ElderlyRate_Day() , TOL);
        assertEquals(0.00419749473795, params.getsToE_Mild_YoungRate_Night(), TOL);
        assertEquals(0.00419749473795, params.getsToE_Mild_AdultRate_Night(), TOL);
        assertEquals(0.00419749473795, params.getsToE_Mild_ElderlyRate_Night(), TOL);
        assertEquals(0.0419749473795, params.getsToE_Severe_YoungRate_Night(), TOL);
        assertEquals(0.00419749473795, params.getsToE_Severe_AdultRate_Night() , TOL);
        assertEquals(0.0419749473795, params.getsToE_Severe_ElderlyRate_Night(), TOL);

        assertEquals(0.5, params.getBetaFactorForLockdown(), TOL);
        assertEquals(0.55, params.getBetaYoungMild_night_lockdown(), TOL);
        assertEquals(0.55, params.getBetaAdultMild_night_lockdown(), TOL);
        assertEquals(0.55, params.getBetaElderlyMild_night_lockdown(), TOL);
        assertEquals(1.0, params.getBetaYoungSevere_night_lockdown(), TOL);
        assertEquals(1.0, params.getBetaAdultSevere_night_lockdown(), TOL);
        assertEquals(1.0, params.getBetaElderlySevere_night_lockdown(), TOL);
        assertEquals(0.55, params.getBetaYoungMild_day_lockdown(), TOL);
        assertEquals(0.55, params.getBetaAdultMild_day_lockdown(), TOL);
        assertEquals(0.55, params.getBetaElderlyMild_day_lockdown(), TOL);
        assertEquals(1.0, params.getBetaYoungSevere_day_lockdown(), TOL);
        assertEquals(1.0, params.getBetaAdultSevere_day_lockdown(), TOL);
        assertEquals(1.0, params.getBetaElderlySevere_day_lockdown(), TOL);

        assertEquals(0.00859118850425, params.geteToMI_YoungRate(), TOL);
        assertEquals(0.00859118850425, params.geteToMI_AdultRate(), TOL);
        assertEquals(0.00859118850425, params.geteToMI_ElderlyRate() , TOL);
        assertEquals(0.00859118850425, params.geteToR_YoungRate() , TOL);
        assertEquals(0.00859118850425, params.geteToR_AdultRate(), TOL);
        assertEquals(0.00859118850425, params.geteToR_ElderlyRate() , TOL);
        assertEquals(0.00859118850425, params.getMiToR_YoungRate() , TOL);
        assertEquals(0.00859118850425, params.getMiToR_AdultRate(), TOL);
        assertEquals(0.00859118850425, params.getMiToR_ElderlyRate() , TOL);
        assertEquals(0.0859118850425, params.getMiToSI_YoungRate() , TOL);
        assertEquals(0.0859118850425, params.getMiToSI_AdultRate(), TOL);
        assertEquals(0.0859118850425, params.getMiToSI_ElderlyRate() , TOL);
        assertEquals(0.00859118850425, params.getSiToR_YoungRate(), TOL);
        assertEquals(0.00859118850425, params.getSiToR_AdultRate(), TOL);
        assertEquals(0.00859118850425, params.getSiToR_ElderlyRate() , TOL);
        assertEquals(0.0859118850425, params.getSiToH_YoungRate(), TOL);
        assertEquals(0.0859118850425, params.getSiToH_AdultRate() , TOL);
        assertEquals(0.0859118850425, params.getSiToH_ElderlyRate(), TOL);
        assertEquals(0.000859118850425, params.getSiToD_YoungRate() , TOL);
        assertEquals(0.00859118850425, params.getSiToD_AdultRate(), TOL);
        assertEquals(0.0859118850425, params.getSiToD_ElderlyRate(), TOL);
        assertEquals(0.000859118850425, params.gethToD_YoungRate(), TOL);
        assertEquals(0.00859118850425, params.gethToD_AdultRate() , TOL);
        assertEquals(0.0859118850425, params.gethToD_ElderlyRate(), TOL);
        assertEquals(0.000859118850425, params.gethToR_YoungRate(), TOL);
        assertEquals(0.000859118850425, params.gethToR_AdultRate(), TOL);
        assertEquals(0.0859118850425, params.gethToR_ElderlyRate() , TOL);

        assertEquals(0.5, params.getHealthModifier(), TOL);
        assertEquals(0.4, params.getAverageHealthIndexPerCouncilArea() , TOL);

        assertEquals(100.0, params.getNumSeeds() , TOL);
    }

}
