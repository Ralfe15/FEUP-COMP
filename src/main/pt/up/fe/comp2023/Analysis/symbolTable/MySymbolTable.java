package pt.up.fe.comp2023.Analysis.symbolTable;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp2023.Analysis.visitors.ImportVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySymbolTable implements SymbolTable {
    // Class
    String className;
    String extendsSuper;
    List<Symbol> fields;

    // Imports
    List<String> imports = new ArrayList<>();

    // Methods (keys are method name -> identifier)
    HashMap<String, List<Symbol>> methodParameters;
    HashMap<String, List<Symbol>> methodVariables;
    HashMap<String, Type> methodTypes;


    public MySymbolTable(JmmParserResult jmmParserResult){
        new ImportVisitor().start(jmmParserResult.getRootNode(), imports);
        fields = new ArrayList<>();
        methodParameters = new HashMap<>();
        methodVariables = new HashMap<>();
        methodTypes = new HashMap<>();
    }

    @Override
    public List<String> getImports() {
        return imports;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getSuper() {
        return extendsSuper;
    }

    @Override
    public List<Symbol> getFields() {
        return fields;
    }

    @Override
    public List<String> getMethods() {
        return new ArrayList<>(methodTypes.keySet());
    }

    @Override
    public Type getReturnType(String s) {
        return methodTypes.get(s);
    }

    @Override
    public List<Symbol> getParameters(String s) {
        return methodParameters.get(s);
    }

    @Override
    public List<Symbol> getLocalVariables(String s) {
        return methodVariables.get(s);
    }
}