package pt.up.fe.comp2023.Optimization;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.ast.PreorderJmmVisitor;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static pt.up.fe.comp2023.Optimization.OllirUtils.*;


public class OllirGenerator extends PreorderJmmVisitor<TempVar, Boolean> {

    private final StringBuilder ollirCode;
    private final MySymbolTable symbolTable;
    private final int numberOfSpaces;
    private int indentationLevel = 0;
    private int tempVarCounter = -1;


    public OllirGenerator(StringBuilder code, MySymbolTable symbolTable, int i) {
        this.ollirCode = code;
        this.symbolTable = symbolTable;
        this.numberOfSpaces = i;
    }

    @Override
    protected void buildVisitor() {
        addVisit("Start", this::visitStart);
        addVisit("ClassDeclaration", this::visitClassDeclaration);
        addVisit("VarDeclaration", this::visitFieldDeclaration);
        addVisit("MethodDecl", this::visitMethod);

        setDefaultVisit((node, dummy) -> true);
    }

    private void startNewLine() {
        ollirCode.append("\n").append(" ".repeat(numberOfSpaces * indentationLevel));
    }

    private void handleImports() {
        for (var imp : symbolTable.getImports()) {
            ollirCode.append("import ").append(imp).append(";");
            startNewLine();
        }
    }

    private Boolean visitStart(JmmNode jmmNode, TempVar tempVar) {
        System.out.println("testando");
        handleImports();
        visitAllChildren(jmmNode, tempVar);
        return true;
    }

    private Boolean visitClassDeclaration(JmmNode node, TempVar dummy) {
        String className = node.get("name");
        ollirCode.append(className).append(" ");
        if (symbolTable.getSuper() != null) {
            ollirCode.append("extends ").append(symbolTable.getSuper()).append(" ");
        }
        ollirCode.append("{");
        indentationLevel++;
        for (var child : node.getChildren().stream().filter((n) -> n.getKind().equals("VarDeclaration")).toList()) {
            startNewLine();
            ollirCode.append(".field private ");
            visit(child);
        }
        addVisit("VarDeclaration", (n, d) -> null);
        startNewLine();

        ollirCode.append(".construct ").append(className).append("().V {");
        indentationLevel++;
        startNewLine();
        ollirCode.append(invoke("invokespecial", "this", "<init>", new ArrayList<>(), "V")).append(";");
        indentationLevel--;
        startNewLine();
        ollirCode.append("}");

        for (var child : node.getChildren().stream().filter((n) -> n.getKind().equals("MethodDecl")).toList()) {
            startNewLine();
            ollirCode.append(".method public ");
            visit(child);
        }
        for (var child : node.getChildren().stream().filter((n) -> n.getKind().equals("MainMethodDecl")).toList()) {
            startNewLine();
            ollirCode.append(".method public static ");
            visit(child);
        }

        indentationLevel--;
        startNewLine();
        ollirCode.append("}");
        return true;
    }

    private Boolean visitFieldDeclaration(JmmNode node, TempVar dummy) {
        String varName = node.get("varName");
        String varType = node.getChildren().get(0).get("rawType");
        boolean isArray = node.getKind().equals("IntArrayType");
        Type type = new Type(varType, isArray);
        ollirCode.append(varName).append(".").append(getOllirType(type));
        ollirCode.append(";");
        return true;
    }

    private Boolean visitMethod(JmmNode node, TempVar dummy) {
        String methodName = node.get("name");
        var returnType = symbolTable.getReturnType(methodName);
        System.out.println(symbolTable.getReturnType(methodName));
        ollirCode.append(methodName).append("(");
        var parameters = symbolTable.getParameters(methodName);
        if (!parameters.isEmpty()) {
            for (var parameter : symbolTable.getParameters(methodName)) {
                ollirCode.append(parameter.getName()).append(".").append(getOllirType(parameter)).append(", ");
            }
            ollirCode.delete(ollirCode.lastIndexOf(","), ollirCode.length());
        }
        ollirCode.append(").").append(getOllirType(returnType)).append(" {");
        indentationLevel++;
        boolean returned = false;
        int currentTemporaryVariableCounter = tempVarCounter;
        for (var child : node.getChildren()) {
            if (child.getKind().equals("Return")) {
                visit(child);
                returned = true;
            } else if (child.getKind().equals("MethodBody")) {
                visit(child);
            }
        }
        tempVarCounter = currentTemporaryVariableCounter;
        if (!returned) {
            startNewLine();
            ollirCode.append("ret.").append(getOllirType(returnType)).append(";");
        }
        indentationLevel--;
        startNewLine();
        ollirCode.append("}");
        return true;
    }


}
