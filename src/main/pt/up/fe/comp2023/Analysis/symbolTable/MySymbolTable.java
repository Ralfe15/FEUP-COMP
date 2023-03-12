package pt.up.fe.comp2023.Analysis.symbolTable;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp2023.Analysis.types.ClassInfo;
import pt.up.fe.comp2023.Analysis.types.MethodInfo;
import pt.up.fe.comp2023.Analysis.visitors.ClassVisitor;
import pt.up.fe.comp2023.Analysis.visitors.ImportVisitor;
import pt.up.fe.comp2023.Analysis.visitors.MethodVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySymbolTable implements SymbolTable {
    // Class
    ClassInfo classInfo = new ClassInfo();

    // Imports
    List<String> imports = new ArrayList<>();

    // Methods
    Map<String, MethodInfo> methods = new HashMap<>();


    public MySymbolTable(JmmParserResult jmmParserResult){
        new ImportVisitor().start(jmmParserResult.getRootNode(), imports);
        new ClassVisitor().start(jmmParserResult.getRootNode(), classInfo);
        MethodVisitor methodVisitor = new MethodVisitor();
        methodVisitor.visitStart(jmmParserResult.getRootNode(), methods);

    }

    @Override
    public List<String> getImports() {
        return imports;
    }

    @Override
    public String getClassName() {
        return classInfo.getName();
    }

    @Override
    public String getSuper() {
        return classInfo.getSuperName();
    }

    @Override
    public List<Symbol> getFields() {
        return classInfo.getFields();
    }

    @Override
    public List<String> getMethods() {
        return new ArrayList<>(methods.keySet());
    }

    @Override
    public Type getReturnType(String s) {
        return methods.get(s).getRetType();
    }

    @Override
    public List<Symbol> getParameters(String s) {
        return methods.get(s).getArgs();
    }

    @Override
    public List<Symbol> getLocalVariables(String s) {
        return methods.get(s).getLocalVariables();
    }
}