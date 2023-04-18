package pt.up.fe.comp2023.Optimization;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.ast.PreorderJmmVisitor;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable.getClosestMethod;
import static pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable.getMethodName;
import static pt.up.fe.comp2023.Optimization.OllirUtils.*;


public class OllirGenerator extends AJmmVisitor<TempVar, Boolean> {

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
        addVisit("Program", this::visitStart);
        addVisit("ClassDeclaration", this::visitClassDeclaration);
        addVisit("VarDeclaration", this::visitFieldDeclaration);
        addVisit("MethodDecl", this::visitMethod);
        addVisit("MainMethodDecl", this::visitMainMethod);
        addVisit("IntExpr", this::visitIntLiteral);
        addVisit("BoolExpr", this::visitBooleanLiteral);
        addVisit("IdExpr", this::visitIdExpr);
        addVisit("ExprStmt", this::visitExpression);
        addVisit("ReturnExpression", this::visitReturn);
//        addVisit("MethodCallExpr", this::visitMethodCall);

        addVisit("MultDivExpr", this::visitBinOp);
        addVisit("AddSubExpr", this::visitBinOp);
        addVisit("RelExpr", this::visitBinOp);
        addVisit("AndOrExpr", this::visitBinOp);
        addVisit("AssignmentExpr", this::visitBinOp);


        setDefaultVisit((node, dummy) -> true);
    }

    private TempVar createTemporaryVariable(JmmNode closestNode) {
        tempVarCounter += 1;
        String name = "t" + tempVarCounter;
        while (symbolTable.getClosestSymbol(closestNode, name).isPresent()) {
            name = "_" + name;
        }
        return new TempVar(name);
    }

    private String createTemporaryAssign(TempVar substituteVariable, String value) {
        String ollirType = getOllirType(substituteVariable.getVariableType());
        if (ollirType.equals("V")) {
            return value + ";";
        }
        return substituteVariable.getVariableName() + "." + ollirType + " :=." + ollirType + " " + value + ";";
    }

    private void startNewLine() {
        ollirCode.append("\n").append(" ".repeat(numberOfSpaces * indentationLevel));
    }

    private void handleImports() {
        for (var imp : symbolTable.getImports()) {
            ollirCode.append("import ").append(imp).append(";").append("\n");
        }
    }

    private Boolean visitStart(JmmNode jmmNode, TempVar tempVar) {
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
        System.out.println("afafeaf");
        String varName = node.get("varName");
        String varType = node.getChildren().get(0).get("rawType");
        boolean isArray = node.getChildren().get(0).getKind().contains("Array");
        Type type = new Type(varType, isArray);
        ollirCode.append(varName).append(".").append(getOllirType(type));
        ollirCode.append(";");
        return true;
    }

    private Boolean visitExpression(JmmNode node, TempVar dummy) {
        visit(node.getJmmChild(0));
        return true;
    }

    private Boolean visitMethod(JmmNode node, TempVar dummy) {
        String methodName = node.get("name");
        var returnType = symbolTable.getReturnType(methodName);
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
            if (child.getKind().equals("ReturnExpression")) { // TODO: Add visit return expression
                visit(child);
                returned = true;
            } else { // TODO: Add visit to method body stuff
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

    private Boolean visitMainMethod(JmmNode node, TempVar dummy) {
        String methodName = "main";
        var returnType = symbolTable.getReturnType(methodName);
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
            if (child.getKind().equals("ReturnExpression")) { // TODO: Add visit return expression
                visit(child);
                returned = true;
            } else { // TODO: Add visit to method body stuff
                System.out.println(child.getKind());
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

    private Boolean visitReturn(JmmNode node, TempVar dummy) {
        TempVar temp = createTemporaryVariable(node);
        if (getClosestMethod(node).isPresent()) {
            String methodName = getClosestMethod(node).get().getKind().equals("MethodDecl") ?
                    getClosestMethod(node).get().get("name") : "main";
            temp.setVariableType(symbolTable.getReturnType(methodName));
        }
        visit(node.getJmmChild(0), temp);
        startNewLine();
        ollirCode.append("ret.").append(getOllirType(temp.getVariableType())).append(" ")
                .append(temp.getSubstituteWithType()).append(";");
        return true;
    }

    private Boolean visitBinOp(JmmNode node, TempVar substituteVariable) {
        var t1 = createTemporaryVariable(node);
        var t2 = createTemporaryVariable(node);
        visit(node.getJmmChild(0), t1);
        t2.setAssignType(t1.getVariableType());
        visit(node.getJmmChild(1), t2);
        String operation = node.get("op");
        if (node.getKind().equals("AssignmentExpr")) {
            String ollirType = getOllirType(t1.getVariableType());
            var closestMethod = getClosestMethod(node);
            boolean isClassField = symbolTable.getFields().stream().anyMatch(s -> s.getName().equals(t1.getVariableName())) && closestMethod.isPresent()
                    && symbolTable.getLocalVariables(getMethodName(closestMethod.get())).stream().noneMatch(s -> s.getName().equals(t1.getVariableName()));
            startNewLine();
            if (isClassField) {
                ollirCode.append(OllirUtils.putField("this", t1.getSubstituteWithType(), t2.getSubstituteWithType())).append(";");
            } else {
                ollirCode.append(t1.getSubstituteWithType()).append(" :=.").append(ollirType)
                        .append(" ").append(t2.getSubstituteWithType()).append(";");
            }
            return true;
        }
        else  {
            Type operationType = !operation.equals("<") ? t1.getVariableType() : new Type("boolean", false);
            String tempType = getOllirType(operationType);
            String code = t1.getSubstituteWithType() + " " + operation + "." + tempType + " " + t2.getSubstituteWithType();
            String tempPrefix = substituteVariable.getVariableName() + "." + tempType + " :=." + tempType + " ";
            substituteVariable.setVariableType(operationType);
            startNewLine();
            ollirCode.append(tempPrefix).append(code).append(";");
            return true;
        }
    }




    private Boolean visitIntLiteral(JmmNode node, TempVar substituteVariable) {
        substituteVariable.setValue(node.get("value"));
        substituteVariable.setVariableType(new Type("int", false));
        return true;
    }

    private Boolean visitBooleanLiteral(JmmNode node, TempVar substituteVariable) {
        substituteVariable.setValue(node.get("bool").equals("true") ? "1" : "0");
        substituteVariable.setVariableType(new Type("boolean", false));
        return true;
    }

    private Boolean visitIdExpr(JmmNode node, TempVar substituteVariable) {
        substituteVariable.setValue(node.get("value"));
        boolean isArray = node.getChildren().size()!=0;
        if (substituteVariable.getVariableType() != null) {isArray|=substituteVariable.getVariableType().isArray();}
        substituteVariable.setVariableType(new Type("int", isArray));
        return true;
    }


}

