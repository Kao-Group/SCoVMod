/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model;

import scovmod.model.output.IOutputModule;
//import scovmod.model.output.modules.community.DailyResult;
import scovmod.model.output.modules.councilarea.CouncilAreaSummary;
import scovmod.model.output.modules.councilarea.CouncilAreaSummaryResult;
import scovmod.model.output.modules.healthboard.DailyResult;
import scovmod.model.output.modules.healthboard.HealthBoardSummaryResult;
import scovmod.model.untested.Factory;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.InputData;
import scovmod.model.input.config.Parameters;
import scovmod.model.output.IResult;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class Main {

    public static void main(String[] args) {
        Path jsonConfigPath = Paths.get("scovmod.json");
        String jsonConfig = "";

        try {
            jsonConfig = FileUtils.readFileToString(jsonConfigPath.toFile(), Charset.defaultCharset());
        } catch (Exception e) {
            throw new RuntimeException("I can't find the file: "+jsonConfigPath);
        }

        InputData inputData = InputData.fromJSON(jsonConfig);
        ConfigParameters configParams = ConfigParameters.fromJSON(jsonConfig);
        Parameters modelParams = Parameters.fromJSON(jsonConfig);

        // Pull properly from json (in scala batch script)- only for test purposes
        String testModule = "ca";

        Path rootDir = Paths.get("/home/thomdoherty/tbmi/results/");
        Path resultsDir = rootDir.resolve("test-run-20200327");

        IOutputModule out;
        switch (testModule) {
            case "oa":
                out = new scovmod.model.output.modules.community.CommunitySummary();
                break;
            case "ca":
                out = new scovmod.model.output.modules.councilarea.CouncilAreaSummary();
                break;
            case "fitting":
                out = new scovmod.model.output.modules.healthboard.HealthBoardSummary();
                break;
//            case "nationalTest":
//                out = new scovmod.model.output.modules.national.NationalTestCSV(resultsDir);
//                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }

//        IResult result = Factory.buildModel(inputData, configParams, modelParams, out).run();
//        CommunitySummaryResult csr = (CommunitySummaryResult) result;
//        List<String> lines = new ArrayList<>();
//        for (Map.Entry<DailyResult, Integer> kvp : csr.getSimulatedDailyResultPerCommunity().entrySet()) {
//            lines.add(kvp.getKey().getDate() + "," + kvp.getKey().getCommunityId()  + "," + kvp.getKey().getType()  + "," +kvp.getValue());
//        }
//        writeData(Paths.get("/home/thomdoherty/_CODE/SCoVMod-model/test.csv"),"date,area,measurement,value",lines);

//        IResult result = Factory.buildModel(inputData, configParams, modelParams, out).run();
//        HealthBoardSummaryResult csr = (HealthBoardSummaryResult) result;
//        List<String> lines = new ArrayList<>();
//        for (Map.Entry<DailyResult, Integer> kvp : csr.getSimulatedDailyResultPerHealthBoard().entrySet()) {
//            lines.add(kvp.getKey().getDate() + "," + kvp.getKey().getHealthBoardID()  + "," + kvp.getKey().getType()  + "," +kvp.getValue());
//        }

        IResult result = Factory.buildModel(inputData, configParams, modelParams, out).run();
        CouncilAreaSummaryResult csr = (CouncilAreaSummaryResult) result;
        List<String> lines = new ArrayList<>();
        for (Map.Entry<scovmod.model.output.modules.councilarea.DailyResult, Integer> kvp : csr.getSimulatedDailyResultPerCouncilArea().entrySet()) {
            lines.add(kvp.getKey().getDate() + "," + kvp.getKey().getCouncilAreaID() + "," + kvp.getKey().getType()  + "," +kvp.getValue());
        }
        writeData(Paths.get("/home/thomdoherty/_CODE/SCoVMod-model/test.csv"),"date,area,measurement,value",lines);
    }

        private static void writeData(Path filePath, String header, List<String> lines){
        try {
            Files.deleteIfExists(filePath);
            //Path parentDir = filePath.getParent();
            //if(!Files.exists(parentDir)) Files.createDirectory(parentDir);

            BufferedWriter writer = Files.newBufferedWriter(filePath, Charset.defaultCharset());
            writer.write(header);
            for(String line : lines){
                writer.newLine();
                writer.write(line);
            }
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
