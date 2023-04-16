package pt.up.fe.comp2023.Optimization;

import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.ollir.JmmOptimization;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;

import java.util.Collections;

public class MyJmmOptimizer implements JmmOptimization {

    @Override
    public OllirResult toOllir(JmmSemanticsResult jmmSemanticsResult) {
        final StringBuilder code = new StringBuilder();
        OllirGenerator ollirGenerator = new OllirGenerator(code, (MySymbolTable) jmmSemanticsResult.getSymbolTable(), 4);
        ollirGenerator.visit(jmmSemanticsResult.getRootNode());
        return new OllirResult(jmmSemanticsResult, code.toString(), Collections.emptyList());
    }
}
