/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;

public class MovementTimeStepReader {

    public static int getStochasticIncrement(Path basePath) {
        Path filePath = basePath.resolve("epoch_timestep_config.json");
        String json = "";
        try {
            json = FileUtils.readFileToString(filePath.toFile(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonElement element = parser.parse(json);
        JsonObject increment = element.getAsJsonObject();

        return increment.get("time-step-days").getAsInt();
    }

}
