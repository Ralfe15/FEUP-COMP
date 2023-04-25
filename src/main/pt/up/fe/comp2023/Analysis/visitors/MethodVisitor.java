package pt.up.fe.comp2023.Analysis.visitors;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2023.Analysis.types.MethodInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodVisitor extends AJmmVisitor<Map<String, MethodInfo>, Boolean> {
    @Override
    protected void buildVisitor() {
        addVisit("program", this::visitStart);
        addVisit("MethodDecl", this::visitMethodDeclaration);
        addVisit("MainMethodDecl", this::visitMainMethodDeclaration);
        setDefaultVisit((node, methods) -> true);
    }

    public boolean visitStart(JmmNode node, Map<String, MethodInfo> methods) {
        for (JmmNode child : node.getChildren()) {
            if (child.getKind().equals("ClassDeclaration")) {
                for (JmmNode classChild : child.getChildren()) {
                    visit(classChild, methods);
                }
            } else {
                visit(child, methods);
            }
        }
        return true;
    }

    private Boolean visitMethodDeclaration(JmmNode methodDeclaration, Map<String, MethodInfo> methods) {
        String name = methodDeclaration.get("name");
        Type type = new Type(methodDeclaration.getChildren().get(0).get("rawType"), methodDeclaration.getChildren().get(0).getKind().contains("Array"));
        List<Symbol> args = new ArrayList<>();
        List<Symbol> localVariables = new ArrayList<>();
        for (JmmNode child : methodDeclaration.getChildren()) {
            if (child.getKind().equals("Args")) {
                for (JmmNode argNode : child.getChildren()) {
                    String argName = argNode.get("argName");
                    String argType = argNode.getChildren().get(0).get("rawType");
                    boolean isArray = argType.equals("ArrayType");
                    args.add(new Symbol(new Type(argType, isArray), argName));
                }
            } else if (child.getKind().equals("VarDeclaration")) {
                String varName = child.get("varName");
                String varType = child.getChildren().get(0).get("rawType");
                boolean isArray = child.getChildren().get(0).getKind().contains("Array");
                localVariables.add(new Symbol(new Type(varType, isArray), varName));
            } else if (child.getKind().equals("ReturnExpression")) {

                // Handle return expression and check if matches function signature
            }

            else{
//                String retType = child.get("rawType");
//                type = new Type(retType, child.getKind().equals("IntArrayType"));
            }
        }
        methods.put(name, new MethodInfo(type, args, localVariables));
        return true;
    }

    // Update to fit main method (copy of function above)
    private Boolean visitMainMethodDeclaration(JmmNode methodDeclaration, Map<String, MethodInfo> methods) {
        String name = "main";
        Type type = new Type("void", false);
        List<Symbol> args = new ArrayList<>();
        args.add(new Symbol(new Type("String", true), "args"));
        List<Symbol> localVariables = new ArrayList<>();
        System.out.println(methodDeclaration.getChildren());
        for (JmmNode child : methodDeclaration.getChildren()) {
            if (child.getKind().equals("VarDeclaration")) {
                String varName = child.get("varName");
                String varType = child.getChildren().get(0).get("rawType");
                boolean isArray = child.getChildren().get(0).getKind().contains("Array");
                localVariables.add(new Symbol(new Type(varType, isArray), varName));
            }
        }
        methods.put(name, new MethodInfo(type, args, localVariables));
        return true;
    }

}
