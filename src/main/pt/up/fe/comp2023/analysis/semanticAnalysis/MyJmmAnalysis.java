package pt.up.fe.comp2023.analysis.semanticAnalysis;

import pt.up.fe.comp.jmm.analysis.JmmAnalysis;
import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp2023.analysis.symbolTable.MySymbolTable;

import java.util.ArrayList;
import java.util.List;

public class MyJmmAnalysis implements JmmAnalysis {
    @Override
    public JmmSemanticsResult semanticAnalysis(JmmParserResult jmmParserResult) {
        MySymbolTable symbolTable = new MySymbolTable(jmmParserResult);
        List<Report> reports = new ArrayList<>();
        Analyser analyser = new Analyser(symbolTable);
        analyser.visit(jmmParserResult.getRootNode(),reports);
        reports.addAll(symbolTable.getReports());
        return new JmmSemanticsResult(jmmParserResult, symbolTable,reports);
    }
}
