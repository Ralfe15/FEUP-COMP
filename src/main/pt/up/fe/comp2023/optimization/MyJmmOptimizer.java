package pt.up.fe.comp2023.optimization;

import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.ollir.JmmOptimization;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp2023.analysis.symbolTable.MySymbolTable;

import java.util.Collections;

public class MyJmmOptimizer implements JmmOptimization {

    @Override
    public OllirResult toOllir(JmmSemanticsResult jmmSemanticsResult) {
        final StringBuilder code = new StringBuilder();
        OllirGenerator ollirGenerator = new OllirGenerator(code, (MySymbolTable) jmmSemanticsResult.getSymbolTable(), 4);
        System.out.println(jmmSemanticsResult.getRootNode().toTree());
        ollirGenerator.visit(jmmSemanticsResult.getRootNode());
        System.out.println(code);
        return new OllirResult(jmmSemanticsResult, code.toString(), Collections.emptyList());
    }
}
