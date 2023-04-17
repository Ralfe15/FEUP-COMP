package pt.up.fe.comp2023.Analysis.symbolTable;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp2023.Analysis.types.ClassInfo;
import pt.up.fe.comp2023.Analysis.types.MethodInfo;
import pt.up.fe.comp2023.Analysis.visitors.ClassVisitor;
import pt.up.fe.comp2023.Analysis.visitors.ImportVisitor;
import pt.up.fe.comp2023.Analysis.visitors.MethodVisitor;

import java.util.*;

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
        if(methods.containsKey(s)) {
            return methods.get(s).getRetType();
        }
        return null;
    }

    @Override
    public List<Symbol> getParameters(String s) {
        if(methods.containsKey(s)) {
            return methods.get(s).getArgs();
        }
        return null;
    }

    @Override
    public List<Symbol> getLocalVariables(String s) {
        return methods.get(s).getLocalVariables();
    }

    public static Optional<JmmNode> getClosestMethod(JmmNode node) {
        var method = node.getAncestor("MethodDecl");
        if (method.isPresent()) {
            return method;
        }
        method = node.getAncestor("MainMethodDecl");
        return method;
    }
    public Optional<Symbol> getClosestSymbol(JmmNode node, String name) {
        var method = getClosestMethod(node);
        if (method.isPresent()) {
            String methodName = method.get().getKind().equals("MethodDecl") ?
                    method.get().get("name") : "main";
            for (Symbol symbol : getLocalVariables(methodName)) {
                if (symbol.getName().equals(name)) {
                    return Optional.of(symbol);
                }
            }
            for (Symbol symbol : getParameters(methodName)) {
                if (symbol.getName().equals(name)) {
                    return Optional.of(symbol);
                }
            }
        }
        for (Symbol symbol : getFields()) {
            if (symbol.getName().equals(name)) {
                return Optional.of(symbol);
            }
        }
        return Optional.empty();
    }

    public boolean isLocalVariable(JmmNode node, String name) {
        var closestSymbol = getClosestSymbol(node, name);
        if (closestSymbol.isEmpty()) {
            return !this.getImports().contains(name) && !name.equals("this");
        }
        var closestMethod = getClosestMethod(node);
        if (closestMethod.isEmpty()){
            return false;
        }
        return this.getLocalVariables(getMethodName(closestMethod.get())).stream().anyMatch(s -> s.getName().equals(name));
    }

    public static String getMethodName(JmmNode method) {
        return method.getKind().equals("MethodDecl") ?
                method.getChildren().get(1).get("name") : "main";
    }
}