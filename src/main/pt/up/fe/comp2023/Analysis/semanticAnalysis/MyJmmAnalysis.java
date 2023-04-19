package pt.up.fe.comp2023.Analysis.semanticAnalysis;

import pt.up.fe.comp.jmm.analysis.JmmAnalysis;
import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;
import pt.up.fe.comp2023.Analysis.semanticAnalysis.Analyser;
import pt.up.fe.comp2023.Analysis.visitors.ClassVisitor;

import java.util.ArrayList;
import java.util.List;

public class MyJmmAnalysis implements JmmAnalysis {
    @Override
    public JmmSemanticsResult semanticAnalysis(JmmParserResult jmmParserResult) {
        MySymbolTable symbolTable = new MySymbolTable(jmmParserResult);
        new Analyser().start(jmmParserResult.getRootNode(),symbolTable);
       // new CheckValidSymbolAccess(symbolTable, reports).start(jmmParserResult.getRootNode(), String.valueOf(0));
        return new JmmSemanticsResult(jmmParserResult, symbolTable, symbolTable.getReports());
    }
}
