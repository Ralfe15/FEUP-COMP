package pt.up.fe.comp2023.Analysis.semanticAnalysis;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.ReportType;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.analysis.table.Type;

import java.util.List;
import java.util.*;

public class Analyser extends AJmmVisitor<List<Report>, String> {
    private final MySymbolTable symbolTable;

    public Analyser(MySymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    protected void buildVisitor() {

        addVisit("Start", this::start);
        addVisit("MethodDecl", this::visitMethodDecl);
        addVisit("IdExpr", this::visitIdentifier);
        addVisit("AddSubExpr", this::visitBinaryOp);
        addVisit("MultDivExpr", this::visitBinaryOp);
        addVisit("RelExpr", this::visitBinaryOp);
        addVisit("AndOrExpr", this::visitBinaryOp);
        addVisit("IfElseStmt", this::visitCondition);
        addVisit("ArrayAccessExpr", this::visitArray);
        addVisit("WhileStmt", this::visitCondition);
        addVisit("MethodHeader", this::visitMethodHeader);
        addVisit("AssignmentExpr", this::visitAssignmentExpr);
        addVisit("ExprStmt", this::visitMethodDecl);
        addVisit("ThisExpr", this::visitThis);
        addVisit("MainMethodDecl", this::visitMethodDecl);
        addVisit("MethodCallExpr", this::visitMethodCall);
        addVisit("ReturnExpression", this::visitReturnStmt);
        addVisit("Params", this::visitParams);
        this.setDefaultVisit(this::start);

    }

    private String visitParams(JmmNode jmmNode, List<Report> reports) {
        // check Params size
        for (var child : jmmNode.getChildren()) {
            if (isFieldInStatic(child)) {
                addSemanticErrorReport(reports, child, "Main class cannot have static variables ");
                return "<INVALID>";
            }
        }


        String methodName = jmmNode.getJmmParent().get("method");
        int index = 0;
        String callerName = getTypeSafe(jmmNode.getJmmParent().getJmmChild(0));
        Type callerType = symbolTable.getSymbolByNameWithParent(callerName, getParentMethodName(jmmNode.getJmmParent().getJmmChild(0)));
        if (symbolTable.getImports().contains(callerType.getName()) || !symbolTable.getMethods().contains(methodName)) {
            return "";
        }
        int callerSize = jmmNode.getChildren().size();
        int methodSize = symbolTable.getMethod(methodName).getArgs().size();
        if (callerSize != methodSize) {
            addSemanticErrorReport(reports, jmmNode, methodName + " expects " + methodSize + " Arguments");
            return "<Invalid>";
        }
        for (var child : jmmNode.getChildren()) {
            if (!getType(child).equals(symbolTable.getMethod(methodName).getArgs().get(index).getType())) {
                addSemanticErrorReport(reports, jmmNode, "Wrong param type");
                return "<Invalid>";
            }
            index++;
        }
        return "";
    }

    private String visitThis(JmmNode jmmNode, List<Report> reports) {

        if (jmmNode.getJmmParent().getKind().equals("MethodCallExpr")) {
            if (!symbolTable.getMethods().contains(jmmNode.getJmmParent().get("method"))) {
                if (!(symbolTable.getSuper() == null)) {
                    if (symbolTable.getImports().contains(symbolTable.getSuper())) {
                        return "";

                    } else {
                        addSemanticErrorReport(reports, jmmNode, "The class " + jmmNode.getJmmParent().get("method") + " was not imported");
                        return "<Invalid>";
                    }
                }

                addSemanticErrorReport(reports, jmmNode, "The method " + jmmNode.getJmmParent().get("method") + " dont exist");
                return "<Invalid>";
            }
        }
        return "";
    }

    private String visitArray(JmmNode jmmNode, List<Report> reports) {
        for (JmmNode child : jmmNode.getChildren()) {
            visit(child, reports);
        }
        var varName = getTypeSafe(jmmNode.getJmmChild(0));
        if (isPrimitive(varName)) {
            addSemanticErrorReport(reports, jmmNode, "Cannot access primitive variable");
            return "<Invalid>";
        }
        String identifier;
        if (jmmNode.hasAttribute("value")) {
            identifier = jmmNode.get("value");
        } else if (jmmNode.hasAttribute("method")) {
            identifier = jmmNode.get("method");
        } else {
            identifier = "UNKNOWN";
        }

        if (!jmmNode.getChildren().isEmpty() && !jmmNode.getJmmChild(0).getChildren().isEmpty()) {
            addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "' cannot be accessed .");
            return "<Invalid>";
        }
        return "";

    }


    private String visitAssignmentExpr(JmmNode jmmNode, List<Report> reports) {
        for (JmmNode child : jmmNode.getChildren()) {
            visit(child, reports);
        }
        String nodeLhs = jmmNode.getJmmChild(0).getKind();
        String nodeRhs = jmmNode.getJmmChild(1).getKind();
        if (isThisInStatic(jmmNode.getJmmChild(0)) || isThisInStatic(jmmNode.getJmmChild(1))) {
            addSemanticErrorReport(reports, jmmNode.getJmmParent(), "Main class cannot have this ");
            return "<INVALID>";
        }
        if (isFieldInStatic(jmmNode.getJmmChild(0)) || isFieldInStatic(jmmNode.getJmmChild(1))) {
            addSemanticErrorReport(reports, jmmNode.getJmmParent(), "Main class cannot have fields ");
            return "<INVALID>";
        }

        if ((nodeLhs.equals("MethodCallExpr") || nodeLhs.equals("IdExpr")) && (nodeRhs.equals("MethodCallExpr") || (nodeLhs.equals("IdExpr") && nodeRhs.equals("IdExpr")) || nodeRhs.equals("IntExpr") || nodeRhs.equals("BoolExpr"))) {
            var varName = getTypeSafe(jmmNode.getJmmChild(1));
            var varName1 = getTypeSafe(jmmNode.getJmmChild(0));
            var varNameType = symbolTable.getSymbolByNameWithParent(varName, getParentMethodName(jmmNode.getJmmChild(1))).getName();
            var varNameType1 = symbolTable.getSymbolByNameWithParent(varName1, getParentMethodName(jmmNode.getJmmChild(0))).getName();
            if (!varNameType.equals(varNameType1)) {
                if (Arrays.asList("int", "void", "boolean").contains(varNameType) && Arrays.asList("int", "void", "boolean").contains(varNameType1)) {
                    addSemanticErrorReport(reports, jmmNode, "Variables types are different");
                    return "<Invalid>";
                }
                if (Arrays.asList("int", "void", "boolean").contains(varNameType) || Arrays.asList("int", "void", "boolean").contains(varNameType1)) {
                    return "";
                }

                if (symbolTable.getImports().contains(varNameType) && symbolTable.getImports().contains(varNameType)) {
                    return "";
                }
                if (symbolTable.getSuper() == null || !(symbolTable.getSuper().equals(varNameType) || symbolTable.getSuper().equals(varNameType1))) {
                    addSemanticErrorReport(reports, jmmNode, "Class " + varNameType1 + " dont extend " + varNameType);
                    return "<Invalid>";
                } else {
                    if (symbolTable.getSuper() != null && !symbolTable.getImports().contains(symbolTable.getSuper())) {
                        addSemanticErrorReport(reports, jmmNode, "Import issue");
                        return "<Invalid>";
                    }

                }
            }


        }

        return "";
    }

    private boolean isFieldInStatic(JmmNode node) {
        if (node.hasAttribute("value")) {
            var variable = node.get("value");
            var parent = node.getJmmParent();
            while (true) {
                if (parent.getKind().equals("MainMethodDecl")) {
                    for (var field : symbolTable.getFields()) {
                        return ((symbolTable.getSymbolByNameWithMethodName(variable, "main")).getName().equals("Field") && field.getName().equals(variable));
                    }
                    return false;
                }
                if ((parent.getKind().equals("ClassDeclaration"))) {
                    return false;
                } else {
                    parent = parent.getJmmParent();
                }

            }


        }
        return false;
    }

    private boolean isOperator(String operator) {
        return Arrays.asList("AndOrExpr", "RelExpr", "AddSubExpr", "MultDivExpr").contains(operator);

    }

    public boolean checkConditions(JmmNode node, String condition) {
        return Arrays.asList("WhileStmt", "IfElseStmt").contains(node.getKind()) && Arrays.asList("WhileStmt", "IfElseStmt").contains(node.getJmmParent().getJmmParent().getKind());
    }

    private String visitCondition(JmmNode jmmNode, List<Report> reports) {
        for (JmmNode child : jmmNode.getChildren()) {
            String type = visit(child, reports);
            if ((jmmNode.getKind().equals("WhileStmt") || jmmNode.getKind().equals("IfElseStmt")) && ( child.getKind().equals("BlockStmt"))) {
                continue;
            } else if (true) {
                if (checkConditions(jmmNode, "WhileStmt") || checkConditions(jmmNode, "IfElseStmt")) {
                    if (isOperator(child.getKind())) {
                        visitBinaryOp(child, reports);
                        if (isBool(child.getJmmChild(0)) && isBool(child.getJmmChild(1))) {
                            continue;
                        }
                    }
                    if ((child.hasAttribute("value")) && isBool(child)) {
                        continue;
                    }

                }
                if (jmmNode.getKind().equals("WhileStmt") || jmmNode.getKind().equals("IfElseStmt")) {
                    if (isBool(child)) {
                        continue;
                    } else if (child.hasAttribute("type") && child.get("type").equals("boolean") || (child.hasAttribute("value") && symbolTable.getSymbolByName(child.get("value")).getName().equals("boolean"))) {
                        continue;
                    }
                    else if ( child.getChildren().size() >1 && (child.hasAttribute("type") && child.get("type").equals("int") || (child.hasAttribute("value") && symbolTable.getSymbolByName(child.get("value")).getName().equals("int")))) {
                        continue;
                    }
                    else if (isInt(child) && child.getChildren().size() >1) {
                        continue;
                    }
                }
                addSemanticErrorReport(reports, jmmNode, "Condition is not of type 'boolean'");
                return "<Invalid>";
            }
        }
        return "";


    }

    public String getParentMethodName(JmmNode node) {
        var condition = true;
        var parent = node.getJmmParent();
        while (condition) {
            if (parent.getKind().equals("MainMethodDecl")) {
                return "main";

            }
            if ((parent.getKind().equals("MethodDecl"))) {
                return parent.get("name");

            } else {
                parent = parent.getJmmParent();
            }

        }
        return "INVALID";
    }

    public boolean isBool(JmmNode node) {
        String parentName = getParentMethodName(node);
        if (node.hasAttribute("value")) {

            return symbolTable.getSymbolByNameWithParent(getTypeSafe(node), parentName).getName().equals("boolean");
        } else {
            if (node.hasAttribute("bool") && isPrimitive(node.get("bool"))) {
                return true;
            }
        }

        if (node.hasAttribute("method")) {
            String methodName = node.get("method");
            if (symbolTable.getMethod(methodName) != null) {
                return (symbolTable.getMethod(methodName).getRetType().getName().equals("boolean"));
            }
        }
        return false;
    }
    public boolean isInt(JmmNode node) {
        String parentName = getParentMethodName(node);
        if (node.hasAttribute("value")) {

            return symbolTable.getSymbolByNameWithParent(getTypeSafe(node), parentName).getName().equals("int");
        } else {
            if (node.hasAttribute("value") && isPrimitive(node.get("value"))) {
                return true;
            }
        }

        if (node.hasAttribute("method")) {
            String methodName = node.get("method");
            if (symbolTable.getMethod(methodName) != null) {
                return (symbolTable.getMethod(methodName).getRetType().getName().equals("int"));
            }
        }
        return false;
    }
    private String visitMethodCall(JmmNode jmmNode, List<Report> reports) {
        for (JmmNode childs : jmmNode.getChildren()) {
            visit(childs, reports);
        }
        String identifier;
        if (isThisInStatic(jmmNode)) {
            addSemanticErrorReport(reports, jmmNode.getJmmParent(), "Main class cannot have this ");
            return "<INVALID>";
        }

        if (jmmNode.hasAttribute("value")) {
            identifier = jmmNode.get("value");
        } else if (jmmNode.hasAttribute("method")) {
            identifier = jmmNode.get("method");
            String variable = getTypeSafe(jmmNode.getJmmChild(0));
            Type variableType = symbolTable.getSymbolByNameWithParent(variable, getParentMethodName(jmmNode.getJmmChild(0)));
            if (variableType.getName().equals(symbolTable.getClassName())) {
                if (!(symbolTable.getSuper() == null)) {
                    return "";
                }
                if (symbolTable.getSuper() != null && !symbolTable.getImports().contains(symbolTable.getSuper())) {
                    addSemanticErrorReport(reports, jmmNode, "Import issue");
                    return "<Invalid>";
                }
                if (symbolTable.getMethods().contains(identifier)) {
                    return "";
                } else {
                    addSemanticErrorReport(reports, jmmNode, "Variable '" + variable + "of Type: " + variableType.getName() + " don't have method " + identifier);
                    return "<Invalid>";
                }
            }
        } else {
            return "";
        }

        Type identifierType = symbolTable.getSymbolByName(identifier);


        return "";
    }

    private String visitMethodDecl(JmmNode jmmNode, List<Report> reports) {
        for (JmmNode childs : jmmNode.getChildren()) {
            visit(childs, reports);
        }
        return "";

    }

    public Type getType(JmmNode jmmNode) {
        boolean isArray = jmmNode.getAttributes().contains("isArray") && jmmNode.get("isArray").equals("true");
        String nodeKind = jmmNode.getKind();
        Type type;

        switch (nodeKind) {
            case "BoolExpr":
                type = new Type("boolean", isArray);
                return type;
            case "IdExpr":
                if (!jmmNode.getChildren().isEmpty()) {
                    return new Type(symbolTable.getSymbolByNameWithParent(getTypeSafe(jmmNode), getParentMethodName(jmmNode)).getName(), false);
                }
                return symbolTable.getSymbolByNameWithParent(getTypeSafe(jmmNode), getParentMethodName(jmmNode));
            case "IntExpr":
                type = new Type("int", isArray);
                return type;
            case "NewArray":
                JmmNode arrayTypeNode = jmmNode.getJmmChild(0);
                type = getType(arrayTypeNode);
                return new Type(type.getName(), true);
            case "ArrayAccessExpr":
                JmmNode arrayNode = jmmNode.getJmmChild(0);
                type = getType(arrayNode);
                return new Type(type.getName(), false); // Array element type
            case "MultDivExpr":
            case "AddSubExpr":
            case "RelExpr":
            case "AndOrExpr":
                JmmNode leftOperand = jmmNode.getJmmChild(0);
                JmmNode rightOperand = jmmNode.getJmmChild(1);
                Type leftType = symbolTable.getSymbolByNameWithParent(getTypeSafe(leftOperand), getParentMethodName(leftOperand));
                Type rightType;
                if (!(jmmNode.getJmmChild(1).getKind().equals("IntExpr"))) {
                    rightType = symbolTable.getSymbolByNameWithParent(getTypeSafe(jmmNode.getJmmChild(1)), getParentMethodName(rightOperand));

                } else {

                    rightType = symbolTable.getSymbolByName(getTypeSafe(rightOperand)) != null ?
                            symbolTable.getSymbolByNameWithParent(getTypeSafe(rightOperand), getParentMethodName(rightOperand)) : new Type("int", false);

                }
                // Determine the resulting type based on the actual operation type
                if (leftType.getName().equals("int") && rightType.getName().equals("int")) {
                    type = new Type("int", false); // Change isArray to false
                } else if (leftType.getName().equals("boolean") && rightType.getName().equals("boolean")) {
                    type = new Type("boolean", false); // Change isArray to false
                } else {
                    // Handle other cases if necessary
                    type = new Type("UNKNOWN", isArray);
                }
                return type;
            default:
                if (jmmNode.getOptional("value").isPresent()) {
                    type = new Type(getTypeSafe(jmmNode), isArray);
                    return type;
                } else if (jmmNode.getOptional("type").isPresent()) {
                    type = new Type(jmmNode.get("type"), isArray);
                    return type;
                }
                return jmmNode.hasAttribute("value") ? new Type(getTypeSafe(jmmNode), isArray) : new Type("UNKNOWN", isArray);
        }
    }

    private String getTypeSafe(JmmNode jmmNode) {

        if (jmmNode.hasAttribute("value")) {
            return jmmNode.get("value");
        }
        if (jmmNode.getKind().equals("ArrayAccessExpr")) {
            return getTypeSafe(jmmNode.getJmmChild(0));
        } else return "#UNKNOWN";
    }


    private Type getTypee(JmmNode node) {
        if (node.getKind().equals("MethodCallExpr")) {
            if (symbolTable.getMethod(node.get("method")) != null) {
                var methodInfo = symbolTable.getMethod(node.get("method")).getRetType().getName();
                return new Type(methodInfo, false);

            }
        }
        if (!(node.getKind().equals("IntExpr"))) {
            return symbolTable.getSymbolByNameWithParent(getTypeSafe(node), getParentMethodName(node));
        } else {
            return symbolTable.getSymbolByName(getTypeSafe(node)) != null ?
                    symbolTable.getSymbolByNameWithParent(getTypeSafe(node), getParentMethodName(node)) : new Type("int", false);
        }
    }

    private Type getTypeFromSymbolTable(JmmNode node, Type type) {
        if (!type.getName().equals("UNKNOWN")) {
            return type;
        } else if (node.hasAttribute("bool") || (node.getJmmParent().hasAttribute("name") && isVariableDeclaredWithMethod(getTypeSafe(node), node.getJmmParent().get("name")))) {
            if (node.hasAttribute("bool")) {
                return new Type("boolean", false);
            } else {
                return symbolTable.getSymbolByNameWithParent(node.get("value"), getParentMethodName(node));

            }
        } else {
            if (node.hasAttribute("value")) {
                if (!isVariableDeclared(node.get("value"))) {
                    return new Type("ERROR", false);
                }
            }
            return type;
        }

    }

    private boolean isInvalidCondition(JmmNode node, Type type) {
        return node.getJmmParent() != null && (node.getJmmParent().getKind().equals("IfStmt") || node.getJmmParent().getKind().equals("WhileStmt")) && (!type.getName().equals("boolean") && !(type.getName().equals("int") && node.getKind().equals("RelExpr"))) ;
    }

    private boolean isInvalidOperator(JmmNode node, Type lhsType, Type rhsType) {
        if (Arrays.asList("+", "-", "*", "/").contains(node.get("op")) || Objects.equals("&&", node.get("op")) || Objects.equals("<", node.get("op")) || Objects.equals(">", node.get("op"))) {
            if (lhsType.isArray()) {
                if (node.getJmmChild(0).getChildren().isEmpty() && node.getJmmChild(1).getChildren().isEmpty()) {
                    return true;
                }

                if (lhsType.isArray() && rhsType.isArray()) {
                    return false;
                }
                return (!node.getJmmChild(0).getJmmChild(0).getChildren().isEmpty()) && (!rhsType.isArray() || !node.getJmmChild(1).getJmmChild(0).getChildren().isEmpty());
            }

            if (Arrays.asList("+", "-", "*", "/").contains(node.get("op")) || Objects.equals("<", node.get("op"))) {
                if (!lhsType.getName().equals("int")) {
                    return true;
                }
            }

            if (Objects.equals("&&", node.get("op"))) {
                if (!lhsType.getName().equals("boolean")) {
                    return true;
                }
            }

            if (!Arrays.asList("int", "void", "boolean").contains(lhsType.getName())) {
                return true;
            }
            if (Arrays.asList("<",">").contains(node.get("op"))) {
                node.put("type", new Type("int", false).getName());
                node.put("isArray", String.valueOf(new Type("int", false).isArray()));
            } else {
                node.put("type", "INVALID");
                node.put("isArray", String.valueOf(lhsType.isArray()));
            }

            return !(lhsType.getName()).equals(rhsType.getName());
        }

        return false;
    }

    public boolean isThisInStatic(JmmNode node) {
        if (!node.getChildren().isEmpty()) {

            if (node.getJmmChild(0).getKind().equals("ThisExpr")) {
                var condition = true;
                var parent = node.getJmmParent();
                while (condition) {
                    if (parent.getKind().equals("MainMethodDecl")) {
                        return true;
                    }
                    if ((parent.getKind().equals("ClassDeclaration"))) {
                        return false;
                    } else {
                        parent = parent.getJmmParent();
                    }

                }

            }
        }
        return false;
    }

    private boolean isBinaryOp(JmmNode node) {
        return node.getKind().equals("MultDivExpr") || node.getKind().equals("AddSubExpr") || node.getKind().equals("RelExpr");
    }

    private String visitBinaryOp(JmmNode jmmNode, List<Report> reports) {

        Stack<JmmNode> stack = new Stack<>();
        stack.push(jmmNode);

        while (!stack.isEmpty()) {
            jmmNode = stack.pop();


            JmmNode lhsNode = jmmNode.getJmmChild(0);
            JmmNode rhsNode = jmmNode.getJmmChild(1);

            Type lhsType = getTypee(lhsNode);
            Type rhsType = getTypee(rhsNode);
            if (lhsNode.getKind().equals("ArrayAccessExpr")) {
                lhsNode = jmmNode.getJmmChild(0).getJmmChild(0);
                if (lhsNode.getKind().equals("Identifier")) {
                    visit(lhsNode, reports);

                }
            }

            if (rhsNode.getKind().equals("ArrayAccessExpr")) {
                JmmNode rhsParentNode = jmmNode.getJmmChild(0);
                if (!rhsParentNode.getChildren().isEmpty()) {
                    rhsNode = rhsParentNode.getJmmChild(0);
                } else {
                    rhsNode = lhsNode;
                }
                if (rhsNode.getKind().equals("Identifier")) {
                    visit(lhsNode, reports);
                }
            }


            if (lhsType.getName().equals("UNKNOWN") || rhsType.getName().equals("UNKNOWN")) {
                if (jmmNode.getJmmChild(0).hasAttribute("bool")) {
                    lhsType = new Type("boolean", false);
                } else {
                    lhsType = jmmNode.getJmmChild(0).hasAttribute("value") ? symbolTable.getSymbolByNameWithParent(jmmNode.getJmmChild(0).get("value"), getParentMethodName(jmmNode.getJmmChild(0))) : new Type("UNKNOWN", false);

                }
                rhsType = getTypeFromSymbolTable(jmmNode.getJmmChild(1), rhsType);
                if (rhsType.getName().equals("ERROR")) {
                    addSemanticErrorReport(reports, jmmNode.getJmmParent(), "Variable " + jmmNode.getJmmChild(1).get("value") + " not declared");
                    return "<INVALID>";
                }
            }
            if (isThisInStatic(lhsNode) || isThisInStatic(rhsNode)) {
                addSemanticErrorReport(reports, jmmNode.getJmmParent(), "Main class cannot have this ");
                return "<INVALID>";
            }
            if (isFieldInStatic(lhsNode) || isFieldInStatic(rhsNode)) {
                addSemanticErrorReport(reports, jmmNode, "Main class cannot have static variables ");
                return "<INVALID>";
            }
            if (isInvalidCondition(jmmNode, lhsType)) {
                addSemanticErrorReport(reports, jmmNode.getJmmParent(), "Invalid condition type : " + rhsType.print());
                return "<INVALID>";
            }


            // If the current child is a BinaryOp, add it to the stack
            if (isBinaryOp(lhsNode)) {
                stack.push(lhsNode);
            } else {

                if (lhsType.isArray() && rhsType.isArray()) {
                    for (JmmNode child : jmmNode.getChildren()) {
                        visit(child, reports);
                    }
                }
                if (isInvalidOperator(jmmNode, lhsType, rhsType)) {
                    addSemanticErrorReport(reports, jmmNode, "Invalid operator " + jmmNode.get("op") + " applied to " + lhsType.print() + " and " + rhsType.print());
                    return "<INVALID>";
                }
            }
        }

        return "";
    }

    public boolean isVariableDeclaredWithMethod(String varName, String parent) {
        if (isVariableDeclared(varName)) return true;
        for (Symbol symbol : symbolTable.getArgsByMethod(parent)) {
            if (symbol.getName().equals(varName)) {
                return true;
            }
        }
        return varName.equals("this");
    }

    public boolean isVariableDeclared(String varName) {
        // Check if the variable exists in the fields
        for (Symbol field : symbolTable.getFields()) {
            if (field.getName().equals(varName)) {
                return true;
            }
        }


        for (var method : symbolTable.getMethods()) {
            for (Symbol localVar : symbolTable.getLocalVariables(method)) {
                if (localVar.getName().equals(varName)) {
                    return true;
                }
            }

        }
        return symbolTable.hasImport(varName);
    }

    private boolean hasArrayAccess(JmmNode jmmNode) {
        JmmNode parent = jmmNode.getJmmParent();
        if (parent == null) {
            return false;
        }

        if (parent.getChildren().size() > 1 && jmmNode.getJmmParent().getJmmChild(1).equals(jmmNode)) {
            if (jmmNode.hasAttribute("value")) {
                if (symbolTable.getSymbolByNameWithParent(jmmNode.get("value"), getParentMethodName(jmmNode)).getName().equals("int")) {
                    return false;
                }

            }
        }
        if ((parent.getKind().equals("ArrayAccessExpr")) || jmmNode.getJmmParent().getKind().equals("IdExpr") && jmmNode.getChildren().isEmpty() || ((jmmNode.getKind().equals("IdExpr")) && !jmmNode.getChildren().isEmpty()) || parent.getJmmParent().getKind().equals("ArrayAccessExpr")) {
            return true;
        }
        return symbolTable.getSymbolByNameWithParent(getTypeSafe(jmmNode), getParentMethodName(jmmNode)).isArray();
    }

    private String visitIdentifier(JmmNode jmmNode, List<Report> reports) {
        String identifier;

        if (jmmNode.hasAttribute("value")) {
            identifier = jmmNode.get("value");
        } else if (jmmNode.hasAttribute("method")) {
            identifier = jmmNode.get("method");
            String variable = getTypeSafe(jmmNode.getJmmChild(0));
            Type variableType = symbolTable.getSymbolByNameWithParent(variable, getParentMethodName(jmmNode.getJmmChild(0)));
            if (variableType.getName().equals(symbolTable.getClassName())) {
                if (!(symbolTable.getSuper() == null)) {
                    return "";
                }
                if (symbolTable.getSuper() != null && !symbolTable.getImports().contains(symbolTable.getSuper())) {
                    addSemanticErrorReport(reports, jmmNode, "Import issue");
                    return "<Invalid>";
                }
                if (symbolTable.getMethods().contains(identifier)) {
                    return "";
                } else {
                    addSemanticErrorReport(reports, jmmNode, "Variable '" + variable + "of Type: " + variableType.getName() + " don't have method " + identifier);
                    return "<Invalid>";
                }
            }
        } else {
            return "";
        }

        Type identifierType = symbolTable.getSymbolByNameWithParent(identifier, getParentMethodName(jmmNode));

        if (hasArrayAccess(jmmNode) && !identifierType.isArray() && !jmmNode.getJmmParent().getKind().equals("IdExpr")) {
            if (!symbolTable.getSymbolByNameWithParent(identifier, getParentMethodName(jmmNode)).isArray()) {
                addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "' cannot be accessed .");
                return "<Invalid>";
            }
        }
        if (!jmmNode.getChildren().isEmpty()) {
            if (hasArrayAccess(jmmNode) && hasArrayAccess(jmmNode.getJmmChild(0))) {
                String type;
                if (!jmmNode.getJmmChild(0).hasAttribute("value")) {
                    type = getType(jmmNode.getJmmChild(0)).getName();
                } else {
                    Type typeT = symbolTable.getSymbolByNameWithParent(getTypeSafe(jmmNode.getJmmChild(0)), getParentMethodName(jmmNode.getJmmChild(0)));
                    if (!typeT.equals(identifierType) && jmmNode.getJmmParent().getKind().equals("ArrayAccessExpr")) {
                        if (!jmmNode.getChildren().isEmpty() && !isPrimitive(getTypeSafe(jmmNode.getJmmChild(0)))) {
                            addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "'indexes are int");
                            return "<Invalid>";
                        }
                    }
                    if (jmmNode.getJmmParent().getKind().equals("ArrayAccessExpr") && jmmNode.getKind().equals("IdExpr") && !jmmNode.getChildren().isEmpty())
                        visit(jmmNode.getJmmChild(0), reports);
                    type = typeT.getName();
                }

                if (type.equals("boolean") || type.equals("void")) {
                    addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "' cannot be accessed by an " + type);
                    return "<Invalid>";
                }
            }
        }


        if (symbolTable.hasImport(identifier)) {
            // Do not generate a report for imported class method calls
            return "";
        }
        var parent = jmmNode.getJmmParent();
        while (true) {
            if (parent.getKind().equals("MethodDecl") || (parent.getKind().equals("ClassDeclaration"))) {
                break;
            } else {
                parent = parent.getJmmParent();
            }
        }
        if (!isVariableDeclaredWithMethod(identifier, parent.get("name"))) {
            addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "' cannot be Found.");
            return "<Invalid>";
        }
        if (!isVariableTypeValid(identifier, identifierType)) {
            addSemanticErrorReport(reports, jmmNode, "Variable Type " + identifierType.getName() + " dont exist.");
            return "<Invalid>";
        }
        identifierType = symbolTable.getSymbolByNameWithParent(identifier, getParentMethodName(jmmNode));

        if (hasArrayAccess(jmmNode) && !identifierType.isArray()) {
            return "<Invalid>";
        }
        if (!jmmNode.getChildren().isEmpty()) visit(jmmNode.getJmmChild(0), reports);
        return "";
    }

    private boolean isVariableTypeValid(String identifier, Type identifierType) {
        if (Arrays.asList("int", "boolean", "void", "UNKNOWN").contains(identifierType.getName())) {
            return true;
        }
        if (!symbolTable.getImports().contains(identifierType.getName()) && !symbolTable.getClassName().equals(identifierType.getName())) {
            if (symbolTable.getSuper() != null) {
                return symbolTable.getSuper().equals(identifierType.getName());
            } else {
                return false;
            }
        }
        return true;
    }

    private String visitMethodHeader(JmmNode jmmNode, List<Report> reports) {
        JmmNode identifier = jmmNode.getChildren().get(0);
        JmmNode method = jmmNode.getChildren().get(1);
        visit(identifier, reports);
        return visit(method, reports);
    }

    public String start(JmmNode start, List<Report> reports) {
        for (JmmNode child : start.getChildren()) {
            visit(child, reports);
        }
        return "";
    }

    /**
     * Visits a return statement node and checks if the return type is compatible with the method's return type.
     * If not, generates a semantic error report.
     *
     * @param jmmNode The return statement node to be visited.
     * @param reports The list of semantic error reports.
     * @return An empty string.
     */
    private String visitReturnStmt(JmmNode jmmNode, List<Report> reports) {

        String methodName = jmmNode.getJmmParent().get("name");
        Type methodReturnType = symbolTable.getReturnType(methodName);
        JmmNode returnExpression = jmmNode.getChildren().get(0);

        // Check if the return expression is a binary operation
        if (isBinaryOp(returnExpression)) {
            visit(returnExpression, reports);
        }

        Type expressionType = symbolTable.getSymbolByNameWithParent(getTypeSafe(returnExpression), getParentMethodName(jmmNode.getChildren().get(0)));

        // Check if the return expression is a method call
        if (symbolTable.hasMethod(expressionType.getName())) {
            expressionType = symbolTable.getReturnType(expressionType.getName());
        }


        // Check if the return type is compatible
        if (!expressionType.getName().equals(methodReturnType.getName())) {

            // If the expression is a method call on an imported class, assume it's valid
            if (returnExpression.getKind().equals("MethodCallExpr") || returnExpression.getKind().equals("IdExpr")) {
                JmmNode methodCallTarget;
                String variableName;
                String parentName;

                // Get the variable name and parent name of the method call target
                if (returnExpression.getChildren().isEmpty()) {
                    variableName = getTypeSafe(returnExpression);
                    parentName = returnExpression.getJmmParent().getJmmParent().hasAttribute("name") ? returnExpression.getJmmParent().getJmmParent().get("name") : "";
                } else {
                    methodCallTarget = returnExpression.getChildren().get(0);
                    variableName = getTypeSafe(methodCallTarget);
                    parentName = returnExpression.getJmmParent().getJmmParent().hasAttribute("name") ? returnExpression.getJmmParent().getJmmParent().get("name") : "";
                }

                Type variableType = null;
                String className;

                // Check if the variable is declared with a method
                if (isVariableDeclaredWithMethod(variableName, parentName)) {
                    visit(returnExpression, reports);
                    return "";
                }

                // Get the variable type and class name
                variableType = symbolTable.getSymbolByName(variableName) != null ? symbolTable.getSymbolByName(variableName) : new Type("UNKNOWN", false);
                className = variableType.getName();

                // If the class is imported, assume the method call is valid
                if (symbolTable.hasImport(className)) {
                    return "";
                }


                // Otherwise, generate a semantic error report
                else {

                    // only got here if we didn't return false
                    if (isPrimitive(variableName)) return "";
                    addSemanticErrorReport(reports, jmmNode, "Variable '" + variableName + "' cannot be Found.");
                    return "";
                }
            }

            // If the expression is an identifier or an array access expression, visit it
            else if (returnExpression.getKind().equals("IdExpr") || returnExpression.getKind().equals("ArrayAccessExpr")) {
                visit(returnExpression, reports);
            }
        }

        if (!methodReturnType.getName().equals(expressionType.getName())) {
            if (returnExpression.getKind().equals("BoolExpr") && methodReturnType.getName().equals(getType(returnExpression).getName())) {

            } else {
                addSemanticErrorReport(reports, jmmNode, "Return is not correct");
                return "";
            }
        }


        visit(returnExpression, reports);
        return "";
    }

    private boolean isPrimitive(String varName) {
        {
            if (Arrays.asList("false", "true").contains(varName)) return true;
            try {
                Integer.parseInt(varName);
            } catch (NumberFormatException e) {
                return false;
            } catch (NullPointerException e) {
                return false;
            }
            return true;
        }
    }

    private void addSemanticErrorReport(List<Report> reports, JmmNode node, String message) {
        reports.add(new Report(
                ReportType.ERROR,
                Stage.SEMANTIC,
                Integer.parseInt(node.get("lineStart")),
                Integer.parseInt(node.get("colStart")),
                message
        ));
    }
}
