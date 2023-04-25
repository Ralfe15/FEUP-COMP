package pt.up.fe.comp2023;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pt.up.fe.comp.TestUtils;
import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.jasmin.JasminResult;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp2023.Analysis.semanticAnalysis.MyJmmAnalysis;
import pt.up.fe.comp2023.Jasmin.JasminConverter;
import pt.up.fe.comp2023.Optimization.MyJmmOptimizer;
import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.SpecsSystem;

public class Launcher {

    public static void main(String[] args) {
        // Setups console logging and other things
        SpecsSystem.programStandardInit();

        // Parse arguments as a map with predefined options
        var config = parseArgs(args);

        // Get input file
        File inputFile = new File(config.get("inputFile"));

        // Check if file exists
        if (!inputFile.isFile()) {
            throw new RuntimeException("Expected a path to an existing input file, got '" + inputFile + "'.");
        }

        // Read contents of input file
        String code = SpecsIo.read(inputFile);


        // Instantiate JmmParser
        SimpleParser parser = new SimpleParser();

        // Parse stage
        JmmParserResult parserResult = parser.parse(code, config);

        // Check if there are parsing errors
        TestUtils.noErrors(parserResult.getReports());

        // Instantiate JmmAnalysis
        MyJmmAnalysis analyser = new MyJmmAnalysis();

        // Analysis stage
        JmmSemanticsResult analysisResult = analyser.semanticAnalysis(parserResult);
        // Check if there are parsing errors
        TestUtils.noErrors(analysisResult.getReports());

        System.out.println(analysisResult.getSymbolTable().print());

        // Optimization stage
        //MyJmmOptimizer optimizer = new MyJmmOptimizer();
        //OllirResult ollirResult = optimizer.toOllir(analysisResult);


        // ---------- OLLIR -> JASMIN -------------
        OllirResult ollirResult = new OllirResult(code, Collections.emptyMap());

        JasminConverter jasminConverter = new JasminConverter();
        JasminResult jasminCode = jasminConverter.toJasmin(ollirResult);

        System.out.println(jasminCode);
    }

    private static Map<String, String> parseArgs(String[] args) {
        SpecsLogs.info("Executing with args: " + Arrays.toString(args));

        // Check if there is at least one argument
        if (args.length != 1) {
            throw new RuntimeException("Expected a single argument, a path to an existing input file.");
        }

        // Create config
        Map<String, String> config = new HashMap<>();
        config.put("inputFile", args[0]);
        config.put("optimize", "false");
        config.put("registerAllocation", "-1");
        config.put("debug", "false");

        return config;
    }

}
