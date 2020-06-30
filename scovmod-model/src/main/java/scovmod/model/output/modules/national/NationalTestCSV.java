///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package scovmod.model.output.modules.national;
//
//import scovmod.model.output.IResult;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.time.LocalDate;
//import java.util.*;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import scovmod.model.output.modules.OutputModuleAdapter;
//
//public class NationalTestCSV extends OutputModuleAdapter {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    private final Map<LocalDate, Integer> totalSusceptiblesByTimeStep = new TreeMap<>();
//    private final Map<LocalDate, Integer> totalExposedByTimeStep = new TreeMap<>();
//    private final Map<LocalDate, Integer> totalInfectiousByTimeStep = new TreeMap<>();
//    private final Map<LocalDate, Integer> totalRecoveredByTimeStep = new TreeMap<>();
//    private final Map<LocalDate, Integer> totalDeadByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalExposedYoungByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalExposedAdultByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalExposedElderlyByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalMildInfectiousYoungByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalMildInfectiousAdultByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalMildInfectiousElderlyByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalSevereInfectiousYoungByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalSevereInfectiousAdultByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalSevereInfectiousElderlyByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalHospitalisedYoungByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalHospitalisedAdultByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalHospitalisedElderlyByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalRecoveredYoungByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalRecoveredAdultByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalRecoveredElderlyByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalDeadYoungByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalDeadAdultByTimeStep = new TreeMap<>();
////    private final Map<LocalDate, Integer> totalDeadElderlyByTimeStep = new TreeMap<>();
//    private final Path outputDir;
//
//    public NationalTestCSV(Path outputDir) {
//        this.outputDir = outputDir;
//    }
//
//    public int getTotalSusceptible(LocalDate date) {
//        return totalSusceptiblesByTimeStep.get(date);
//    }
//
//    public int getTotalExposed(LocalDate date) { return totalExposedByTimeStep.get(date); }
//
//    public int getTotalInfectious(LocalDate date) {
//        return totalInfectiousByTimeStep.get(date);
//    }
//
//    public int getTotalRecovered(LocalDate date) { return totalRecoveredByTimeStep.get(date); }
//
//    public int getTotalDead(LocalDate date) { return totalDeadByTimeStep.get(date); }
//
//    @Override
//    public void currentTotalSusceptible(LocalDate date, int totalSusceptible) { totalSusceptiblesByTimeStep.put(date, totalSusceptible); }
//
//    private void writeData(Path filePath, String header, List<String> lines){
//        try {
//            Files.deleteIfExists(filePath);
//            Path parentDir = filePath.getParent();
//            if(!Files.exists(parentDir)) Files.createDirectory(parentDir);
//
//            BufferedWriter writer = Files.newBufferedWriter(filePath, Charset.defaultCharset());
//            writer.write(header);
//            for(String line : lines){
//                writer.newLine();
//                writer.write(line);
//            }
//            writer.close();
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    @Override
//    public IResult buildResult() {
//
//        List<String> linesSusceptibles = new ArrayList<>();
//        for (Map.Entry<LocalDate, Integer> kvp : totalSusceptiblesByTimeStep.entrySet()) {
//            linesSusceptibles.add(kvp.getKey().toString() + "," + kvp.getValue());
//        }
//        writeData(
//                outputDir.resolve("totalSusceptiblePerTimeStep.csv"),
//                "Date, totalSusceptibles",
//                linesSusceptibles
//        );
//
//        List<String> linesExposed = new ArrayList<>();
//        for (Map.Entry<LocalDate, Integer> kvp : totalExposedByTimeStep.entrySet()) {
//            linesExposed.add(kvp.getKey().toString() + "," + kvp.getValue());
//        }
//        writeData(
//                outputDir.resolve("totalExposedPerTimeStep.csv"),
//                "Date, totalExposed",
//                linesExposed
//        );
//
//        List<String> linesInfectious = new ArrayList<>();
//        for (Map.Entry<LocalDate, Integer> kvp : totalInfectiousByTimeStep.entrySet()) {
//            linesInfectious.add(kvp.getKey().toString() + "," + kvp.getValue());
//        }
//        writeData(
//                outputDir.resolve("totalInfectiousPerTimeStep.csv"),
//                "Date, totalInfectious",
//                linesInfectious
//        );
//
//        return null;
//    }
//
//}
