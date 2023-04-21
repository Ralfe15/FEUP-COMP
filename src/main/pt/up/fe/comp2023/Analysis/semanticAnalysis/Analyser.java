package pt.up.fe.comp2023.Analysis.semanticAnalysis;

import org.specs.comp.ollir.NodeType;
import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.ReportType;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;
import pt.up.fe.comp2023.Analysis.types.ClassInfo;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.analysis.table.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Analyser extends AJmmVisitor<List<Report>, String> {
    private MySymbolTable symbolTable;
    List<String> variables;

    public Analyser(MySymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    protected void buildVisitor() {
        setDefaultVisit(this::start);

        addVisit("Start", this::start);
        addVisit("MethodDecl", this::visitMethodDecl);

        addVisit("IdExpr", this::visitIdentifier);
        addVisit("MultDivExpr", this::visitBinaryOp);
        addVisit("AddSubExpr", this::visitBinaryOp);
        addVisit("RelExpr", this::visitBinaryOp);
        addVisit("AndOrExpr", this::visitBinaryOp);
        addVisit("MethodHeader", this::visitMethodHeader);
        addVisit("ClassDeclaration", this::visitArrayExpression);
        //addVisit("Array", this::visitArray);
        addVisit("ReturnExpression", this::visitReturnStmt);
        setDefaultVisit((node, reports) -> "");
    }

    private String visitMethodDecl(JmmNode jmmNode, List<Report> reports) {
        JmmNode child = jmmNode.getJmmChild(0);
        String a= child.get("value");
        for (JmmNode childs : jmmNode.getChildren()) {
             visit(childs, reports);
        }
        return "";

    }

    public static Type getType(JmmNode jmmNode) {
        boolean isArray = jmmNode.getAttributes().contains("isArray") && jmmNode.get("isArray").equals("true");
        String nodeKind = jmmNode.getKind();
        Type type;

        switch (nodeKind) {
            case "Boolean":
                type = new Type("boolean", isArray);
                return type;
            case "Identifier":
                type = new Type("#UNKNOWN", isArray);
                return type;
            case "Integer":
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
            case "BinaryOp":
                JmmNode leftOperand = jmmNode.getJmmChild(0);
                JmmNode rightOperand = jmmNode.getJmmChild(1);
                Type leftType = getType(leftOperand);
                Type rightType = getType(rightOperand);

                // Determine the resulting type based on the actual operation type
                if (leftType.getName().equals("int") && rightType.getName().equals("int")) {
                    type = new Type("int", false); // Change isArray to false
                } else if (leftType.getName().equals("boolean") && rightType.getName().equals("boolean")) {
                    type = new Type("boolean", false); // Change isArray to false
                } else if(leftOperand.getKind().equals("BinaryOp")) {
                    type = new Type(rightType.getName(), false); // Change isArray to false
                }
                else {
                    // Handle other cases if necessary
                    type = new Type("#UNKNOWN", isArray);
                }
                return type;
            default:
                if (jmmNode.getOptional("value").isPresent()) {
                    type = new Type(jmmNode.get("value"), isArray);
                    return type;
                } else if (jmmNode.getOptional("type").isPresent()) {
                    type = new Type(jmmNode.get("type"), isArray);
                    return type;
                }

                return new Type(jmmNode.get("value"), isArray);
        }
    }


    private String visitBinaryOp(JmmNode jmmNode, List<Report> reports) {

        Stack<JmmNode> stack = new Stack<>();
        stack.push(jmmNode);

        while(!stack.isEmpty()){
            jmmNode = stack.pop();

            Type lhsType = getType(jmmNode.getJmmChild(0));
            Type rhsType = getType(jmmNode.getJmmChild(1));

            JmmNode lhsNode = jmmNode.getJmmChild(0);
            JmmNode rhsNode = jmmNode.getJmmChild(1);


            if(lhsNode.getKind().equals("ArrayAccessExpr")){
                lhsNode = jmmNode.getJmmChild(0).getJmmChild(0);
                if(lhsNode.getKind().equals("Identifier")){
                   visit(lhsNode, reports);

                }
            }

            if(rhsNode.getKind().equals("ArrayAccessExpr")){
                rhsNode = jmmNode.getJmmChild(0).getJmmChild(0);
                if(rhsNode.getKind().equals("Identifier")){
                    visit(lhsNode, reports);
                }
            }

            if (lhsType.getName().equals("#UNKNOWN") || rhsType.getName().equals("#UNKNOWN")) {
                // Get the type names from the symbol table if possible
                if (!lhsNode.getKind().equals("BinaryOp")) {
                    lhsType = lhsType.getName().equals("#UNKNOWN") && isVariableDeclared(lhsNode.get("value"), symbolTable)
                            ? symbolTable.getSymbolByName(lhsNode.get("value")).getType()
                            : lhsType;
                }

                if (!rhsNode.getKind().equals("BinaryOp")) {
                    rhsType = rhsType.getName().equals("#UNKNOWN") && isVariableDeclared(rhsNode.get("value"), symbolTable)
                            ? symbolTable.getSymbolByName(rhsNode.get("value")).getType()
                            : rhsType;
                }
            }



            if (jmmNode.getJmmParent() != null && (jmmNode.getJmmParent().getKind().equals("IfStmt") || jmmNode.getJmmParent().getKind().equals("WhileStmt"))) {
                if (!lhsType.getName().equals("boolean") && !List.of("<").contains(jmmNode.get("op"))){
                    jmmNode.put("type", "#UNKNOWN");;
                    addSemanticErrorReport(reports, jmmNode.getJmmParent(), "Invalid condition type : " + rhsType.print() );
                    return "<Invalid>";
                }
            }


            // If the current child is a BinaryOp, add it to the stack
            if (lhsNode.getKind().equals("BinaryOp")) {
                stack.push(lhsNode);
            } else {
                if (Arrays.asList("+", "-", "*", "/").contains(jmmNode.get("op")) || List.of("&&").contains(jmmNode.get("op")) || List.of("<").contains(jmmNode.get("op"))) {
                    if (lhsType.isArray()) {
                        addSemanticErrorReport(reports, jmmNode, "Invalid operator" + jmmNode.get("op") + "applied to" + lhsType.print() + " and " + rhsType.print() );
                        return "<Invalid>";
                    }
                }


                if (Arrays.asList("+", "-", "*", "/").contains(jmmNode.get("op")) || List.of("<").contains(jmmNode.get("op"))) {
                    if (!lhsType.getName().equals("int")) {
                        addSemanticErrorReport(reports, jmmNode, "Invalid operator" + jmmNode.get("op") + "applied to" + lhsType.print() + " and " + rhsType.print() );
                        return "<Invalid>";

                    }
                }

                if (List.of("&&").contains(jmmNode.get("op"))) {
                    if (!lhsType.getName().equals("boolean")) {
                        addSemanticErrorReport(reports, jmmNode, "Invalid operator" + jmmNode.get("op") + "applied to" + lhsType.print() + " and " + rhsType.print() );
                        return "<Invalid>";
                    }
                }

                if (!Arrays.asList("int", "void", "boolean").contains(lhsType.getName())) {
                    addSemanticErrorReport(reports, jmmNode, "Invalid operator" + jmmNode.get("op") + "applied to" + lhsType.print() + " and " + rhsType.print() );
                    return "<Invalid>";
                }

                if ("<".contains(jmmNode.get("op"))) {
                    jmmNode.put("type",  new Type("boolean", false).getName());
                    jmmNode.put("isArray", String.valueOf( new Type("boolean", false).isArray()));
                } else {
                    jmmNode.put("type", lhsType.getName());
                    jmmNode.put("isArray", String.valueOf(lhsType.isArray()));
                }

                if (!lhsType.equals(rhsType)) {
                    addSemanticErrorReport(reports, jmmNode, "Invalid operator" + jmmNode.get("op") + "applied to" + lhsType.print() + " and " + rhsType.print() );
                    return "<Invalid>";
                }
            }
        }

        return "";
    }
    public static boolean isVariableDeclared(String varName, MySymbolTable symbolTable) {
        // Check if the variable exists in the fields
        for (Symbol field : symbolTable.getFields()) {
            if (field.getName().equals(varName)) {
                return true;
            }
        }

        // Check if the variable exists in the parameters or local variables of any method
        String methodName = "#UNKNOWN";
        var methodParameters = symbolTable.getParameters(methodName);
        var methodLocalVars = symbolTable.getLocalVariables(methodName);

        for (Symbol parameter : methodParameters) {
            if (parameter.getName().equals(varName)) {
                return true;
            }
        }

        for (Symbol localVar : methodLocalVars) {
            if (localVar.getName().equals(varName)) {
                return true;
            }
        }

        return false;
    }
    private boolean hasArrayAccess(JmmNode jmmNode) {
        JmmNode parent = jmmNode.getJmmParent();
        if (parent == null) {
            return false;
        }

        if (parent.getKind().equals("ArrayAccessExpr") || parent.getJmmParent().getKind().equals("ArrayAccessExpr")) {
            return true;
        }

        return false;
    }

    private String visitIdentifier(JmmNode jmmNode,List<Report> reports) {
        String identifier = jmmNode.get("value");

        if (symbolTable.hasImport(identifier)) {
            // Do not generate a report for imported class method calls
            return "";
        }
        if (!isVariableDeclared(identifier, symbolTable)) {
            addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "' cannot be Found.");
            return "<Invalid>";
        }

        Type identifierType = symbolTable.getSymbolByName(jmmNode.get("value")).getType();

        if (hasArrayAccess(jmmNode) && !identifierType.isArray()) {
            addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "' cannot be accessed .");
            return "<Invalid>";
        }

        return "";
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


    public String visitSymbolTable(JmmNode jmmNode,List<Report> reports) {
        if (visit(jmmNode.getJmmChild(0)) == "") {
            for (String method : symbolTable.getMethods()){
                List<Symbol> s = symbolTable.getLocalVariables(method);
                for (Symbol symbol: s){
                    if (symbol != null && !symbol.getType().isArray()) {
                        symbolTable.getReports().add(new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,0,0,
                                String.format("Tried to access a as an array but it's type is ")
                        ));
                    }

                }

            }

        }
        return "";
    }

    public String visitArray(JmmNode jmmNode,List<Report> reports) {
        if (!symbolTable.hasSymbol(jmmNode.getKind(), jmmNode.get("int"))) {
            symbolTable.getReports().add(new Report(
                    ReportType.ERROR,
                    Stage.SEMANTIC,
                    Integer.parseInt(jmmNode.get("line")),
                    Integer.parseInt(jmmNode.get("column")),
                    "Variable " + jmmNode.get("int") + " wasn't found in this scope"
            ));
            return "";
        }
        return "";
    }


    private String visitCondition(JmmNode node, List<Report> reports) {

        for (JmmNode child : node.getChildren()) {
            String type = visit(child, reports);
            if (!type.equals("boolean")) {
                addSemanticErrorReport(reports, node, "Condition is not of type 'boolean'");
                return "<Invalid>";
            }
        }
        return "";
    }
    /**
     * Expression Visitors
     */
    private String visitArrayExpression(JmmNode node, List<Report> reports){
        //JmmMethod method = symbolTable.getParentMethodName(node);

        JmmNode nodeToVisit;
        String name;
        Type type;
        if (node.getJmmParent().getKind().equals("ArrayAssignStmt")) {
            name = node.getJmmParent().getJmmChild(0).get("name");
            nodeToVisit = node.getJmmParent().getJmmChild(0);
            String assignedType = visit(node.getJmmParent().getJmmChild(2), reports);

            List<Symbol> s = symbolTable.getLocalVariables(name);
            if (s!= null) {
            //    if (!symbolTable.getLocalVariables( name).getType().getName().equals(assignedType)) {
            //        addSemanticErrorReport(reports, node, "Array can't be assigned '" + assignedType +
             //               "' variables when its type is '" + symbolTable.getLocalVar(method.toString(), name).getType().getName() + "'.");
            //        return "<Invalid>";
              //  }
            }

        } else nodeToVisit = node.getJmmChild(0);

        type = getType(nodeToVisit);

        if (type != null) {
            if (!type.isArray()) {
                addSemanticErrorReport(reports, nodeToVisit, "Variable '" + type.getName() + "' cannot be indexed.");
                return "<Invalid>";
            }
        }
        for (JmmNode child : node.getChildren()) {
            if (child.getKind().equals("Literal")) {
                String idxType = visit(child, reports);

                if (!idxType.equals("int")) {
                    addSemanticErrorReport(reports, node, "Array indexes must be of type int.");
                    return "<Invalid>";
                }
            } else if (!child.equals(node.getJmmChild(0))) {
                String idx = "int";
                if (!idx.equals("int")){
                    addSemanticErrorReport(reports, node,
                            "Array indexes must be of type ints.");
                    return "<Invalid>";
                }

            } else {
                Type childType = getType(child);
                String idxType;
                if (childType == null) {
                    idxType = visit(child, reports);
                } else idxType = childType.getName();
                if (!idxType.equals("int")) {
                    addSemanticErrorReport(reports, node, "Array indexes must be of type int.");
                    return "<Invalid>";
                }
            }
        }
        if (type != null)
            return type.getName();
        return "";
    }
/**
    private Type getType(JmmNode node) {
        Type type = null;

        if (node.getAncestor("MethodDeclaration").isPresent()) {
            if (node.getAncestor("MethodDeclaration").get().getJmmParent().getKind().equals("MainMethod")) {
                List<String> a = symbolTable.getMethods();

            }

            //return getMethodByName(methodBody.get().getJmmParent().getJmmChild(0).getJmmChild(1).get("name"));
        }

        List<String> method = symbolTable.getMethods();
        if (node.getAttributes().contains("name")) {
            String varName = node.get("name");
            if (symbolTable.getLocalVar(method.toString(), varName) != null)
                type = symbolTable.getLocalVar(method.toString(), varName).getType();
            else if (symbolTable.getFieldByName(varName) != null)
                type = symbolTable.getFieldByName(varName).getType();
        }

        return type;
    }
**/
    private String visitReturnStmt(JmmNode jmmNode, List<Report> reports) {

        String methodName = "#UNKNOWN";

        // Get the method return type
        Type methodReturnType = symbolTable.getReturnType(methodName);

        // Get the return expression type
        JmmNode returnExpression = jmmNode.getChildren().get(0);
        if (returnExpression.getKind().equals("BinaryOp")) {
             visit(returnExpression, reports);
            //reports.addAll(childReports);
        }

        Type expressionType = getType(returnExpression);

        if (symbolTable.hasMethod(expressionType.getName())) {
            expressionType = symbolTable.getReturnType(expressionType.getName());
        }

        if (returnExpression.getKind().equals("MethodCallExpr")) {
            visit(returnExpression, reports);
            //reports.addAll(childReports);
        }
        // Check if the return type is compatible
        if (!expressionType.equals(methodReturnType)) {
            // If the expression is a method call on an imported class, assume it's valid
            if (returnExpression.getKind().equals("MethodCallExpr")) {
                JmmNode methodCallTarget = returnExpression.getChildren().get(0);

                String variableName = methodCallTarget.get("value");
                Type variableType = null;
                String className;

                if (isVariableDeclared(variableName, symbolTable)) {
                    variableType = symbolTable.getSymbolByName(variableName).getType();
                    className = variableType.getName();
                } else {
                    className = methodCallTarget.get("value");
                }

                if (symbolTable.hasImport(className)) {
                    // Do not generate a report for imported class method calls
                    return "";
                } else {
                    addSemanticErrorReport(reports, jmmNode, "Variable '" + className + "' cannot be Found.");
                }
            }
            else if(returnExpression.getKind().equals("Identifier") || returnExpression.getKind().equals("ArrayAccessExpr")){
                visit(returnExpression, reports);
            }
        }


        return "";
    }
    private void addSemanticErrorReport(List<Report>  reports, JmmNode node, String message){
        reports.add(new Report(
                ReportType.ERROR,
                Stage.SEMANTIC,
                Integer.parseInt(node.get("lineStart")),
                Integer.parseInt(node.get("colStart")),
                message
        ));
    }
}
