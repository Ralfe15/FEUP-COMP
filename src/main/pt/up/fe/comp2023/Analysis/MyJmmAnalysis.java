package pt.up.fe.comp2023.Analysis;

import pt.up.fe.comp.jmm.analysis.JmmAnalysis;
import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;

import java.util.ArrayList;
import java.util.List;

public class MyJmmAnalysis implements JmmAnalysis {
    @Override
    public JmmSemanticsResult semanticAnalysis(JmmParserResult jmmParserResult) {
        MySymbolTable symbolTable = new MySymbolTable(jmmParserResult);
        List<Report> reports = new ArrayList<>();
        return new JmmSemanticsResult(jmmParserResult, symbolTable, reports);
    }
}
