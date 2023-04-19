package pt.up.fe.comp2023.Analysis.semanticAnalysis;

import pt.up.fe.comp.jmm.analysis.JmmAnalysis;
import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyJmmSemanticsResult extends JmmSemanticsResult {
    public MyJmmSemanticsResult(JmmNode rootNode, SymbolTable symbolTable, List<Report> reports, Map<String, String> config) {
        super(rootNode, symbolTable, reports, config);
    }
}
