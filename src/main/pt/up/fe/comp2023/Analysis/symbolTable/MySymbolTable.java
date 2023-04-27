package pt.up.fe.comp2023.Analysis.symbolTable;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp2023.Analysis.types.ClassInfo;
import pt.up.fe.comp2023.Analysis.types.MethodInfo;
import pt.up.fe.comp2023.Analysis.visitors.ClassVisitor;
import pt.up.fe.comp2023.Analysis.visitors.ImportVisitor;
import pt.up.fe.comp2023.Analysis.visitors.MethodVisitor;

import java.util.ArrayList;
import java.util.*;

public class MySymbolTable implements SymbolTable {
    // Class
    ClassInfo classInfo = new ClassInfo();

    // Imports
    List<String> imports = new ArrayList<>();

    // Methods
    Map<String, MethodInfo> methods = new HashMap<>();
    List<Symbol> fields = new ArrayList<>();
    List<Report> reports = new ArrayList<>();
    public MySymbolTable(JmmParserResult jmmParserResult){
        new ImportVisitor().visit(jmmParserResult.getRootNode(), imports);
        new ClassVisitor().visit(jmmParserResult.getRootNode(), classInfo);
        MethodVisitor methodVisitor = new MethodVisitor();
        methodVisitor.visitStart(jmmParserResult.getRootNode(), methods);

    }



    @Override
    public List<String> getImports() {
        return imports;
    }
    public List<Report> getReports() {
        return reports;
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
    public Map<String, MethodInfo> getMethodsDict()
    {
        return methods;
    }

    public Symbol getFieldByName(String fieldName) {
        for (Symbol field : getFields()) {
            if (field.getName().equals(fieldName))
                return field;
        }
        return null;
    }

    public MethodInfo getMethod(String methodName){
        for(String method : getMethodsDict().keySet())
        {
            if(method.equals(methodName)){
                return  getMethodsDict().get(method);
            }
        }
     return null;
    }
    public List<Symbol> getArgsByMethod(String methodName){
    //get args by method
        MethodInfo methodInfo = getMethod(methodName);
        if ( methodInfo == null) return new ArrayList<>();
        if (!methodInfo.getArgs().isEmpty()) {
            return methodInfo.getArgs();
        }
        return new ArrayList<>();
    }

    public boolean hasImport(String identifier) {
        for (String _import : getImports()){
            if(_import.equals(identifier)) return true;
        }
        return false;
    }

    public boolean hasMethod(String identifier) {
        for (String _method : getMethods()){
            if(_method.equals(identifier)) return true;
        }
        return false;
    }
    public Type getSymbolByNameWithParent(String varName,String methodOrClassname){
         MethodInfo method = getMethod(methodOrClassname);
        for(var symbol : getFields()){
            if(symbol.getName().equals(varName))
            {
                return symbol.getType();

            }
        }


        int count = 0;
            if (method == null) return new Type("UNKNOWN",false);
            for (var ex : method.getLocalVariables()){
                var name = ex.getName();
                if(name.equals(varName))
                {
                    return method.getLocalVariables().get(count).getType();

                }
                count++;
            }
            for( var a : getArgsByMethod(methodOrClassname)){
                if ( a.getName().equals(varName)){
                    return a.getType();
                }
            }

        try {
            Integer.parseInt(varName);
        } catch(NumberFormatException e) {
            return new Type("UNKNOWN",false);
        } catch(NullPointerException e) {
            return new Type("UNKNOWN",false);
        }
        // only got here if we didn't return false
        return new Type ( "int",false);

    }
    public Type getSymbolByName(String varName) {
        // Check if the variable exists in the fields
        for (String method : getMethods()) {
            var a = methods.get(method);
            var d = a.getLocalVariables();
            int count = 0;
            for (var ex : a.getLocalVariables()){
                var name = ex.getName();
                if(name.equals(varName))
                {
                    return methods.get(method).getLocalVariables().get(count).getType();

                }
            count++;
            }
        }
        for (String method : getMethods()){
            for( var a : getArgsByMethod(method)){
                if ( a.getName().equals(varName)){
                    return a.getType();
                }
            }
        }
            for(var symbol : getFields()){
                if(symbol.getName().equals(varName))
                {
                    return symbol.getType();

                }
            }
            try {
                Integer.parseInt(varName);
            } catch(NumberFormatException e) {
                return new Type("UNKNOWN",false);
            } catch(NullPointerException e) {
                return new Type("UNKNOWN",false);
            }
            // only got here if we didn't return false
            return new Type ( "int",false);

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
        return closestMethod.filter(jmmNode -> this.getLocalVariables(getMethodName(jmmNode)).stream().anyMatch(s -> s.getName().equals(name))).isPresent();
    }

    public static String getMethodName(JmmNode method) {
        return method.getKind().equals("MethodDecl") ?
                method.get("name") : "main";
    }
}
