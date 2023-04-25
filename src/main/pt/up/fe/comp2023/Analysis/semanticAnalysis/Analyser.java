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
    public Analyser(MySymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    protected void buildVisitor() {

        addVisit("Start", this::start);
        addVisit("MethodDecl", this::visitMethodDecl);
        addVisit("IdExpr", this::visitIdentifier);
        addVisit("AddSuAbExpr", this::visitBinaryOp);
        addVisit("MultDivExpr", this::visitBinaryOp);
        addVisit("RelExpr", this::visitBinaryOp);
        addVisit("AndOrExpr", this::visitBinaryOp);
        addVisit("IfElseStmt", this::visitCondition);
        addVisit("WhileStmt", this::visitCondition);
        addVisit("MethodHeader", this::visitMethodHeader);
        addVisit("AssignmentExpr",this::visitAssignmentExpr);
        addVisit("ExprStmt",this::visitMethodDecl);
        addVisit("MethodCallExpr", this::visitMethodCall);
        addVisit("ReturnExpression", this::visitReturnStmt);
        this.setDefaultVisit(this::start);

    }

    private String visitAssignmentExpr(JmmNode jmmNode, List<Report> reports) {
        String nodeLhs = jmmNode.getJmmChild(0).getKind();
        String nodeRhs = jmmNode.getJmmChild(1).getKind();
        if(nodeLhs.equals("MethodCallExpr") && (nodeRhs.equals("MethodCallExpr") || nodeRhs.equals("IntExpr") || nodeRhs.equals("BoolExpr"))){

            var varName = getTypeSafe(jmmNode.getJmmChild(1));
            var varName1 = getTypeSafe(jmmNode.getJmmChild(0));
            var varNameType = symbolTable.getSymbolByName(varName).getName();
            var varNameType1 = symbolTable.getSymbolByName(varName1).getName();
            if(!varNameType.equals(varNameType1)){
                if(Arrays.asList("int", "void", "boolean").contains(varNameType) && Arrays.asList("int", "void", "boolean").contains(varNameType1)){
                    addSemanticErrorReport(reports, jmmNode, "Variables types are different");
                    return "<Invalid>";
                }
                if(Arrays.asList("int", "void", "boolean").contains(varNameType) || Arrays.asList("int", "void", "boolean").contains(varNameType1)) {return "";}

                    if(symbolTable.getImports().contains(varNameType) && symbolTable.getImports().contains(varNameType)){return "";}
                if(symbolTable.getSuper() == null || !(symbolTable.getSuper().equals(varNameType) || symbolTable.getSuper().equals(varNameType1)))
                {
                    addSemanticErrorReport(reports, jmmNode, "Class " +varNameType1+ " dont extend " + varNameType);
                    return "<Invalid>";
                }
            }


        }

        return "";
    }

    private String visitCondition(JmmNode jmmNode, List<Report> reports) {
        for (JmmNode child : jmmNode.getChildren()) {
            String type = visit(child, reports);
            if (jmmNode.getKind().equals("WhileStmt") && ((child.getKind().equals("RelExpr")) || child.getKind().equals("BlockStmt")) ){
                    continue;
            }
            else if (child.hasAttribute("value") ? !symbolTable.getSymbolByName(child.get("value")).equals("boolean") : true) {
                addSemanticErrorReport(reports, jmmNode, "Condition is not of type 'boolean'");
                return "<Invalid>";
            }
        }
        return "";

    }

    private String visitMethodCall(JmmNode jmmNode, List<Report> reports) {
        String identifier;
        if ( jmmNode.hasAttribute("value")){
            identifier = jmmNode.get("value");}
            else if(jmmNode.hasAttribute("method")){
                identifier = jmmNode.get("method");
                String variable = jmmNode.getJmmChild(0).get("value");
                Type variableType = symbolTable.getSymbolByName(variable);
                if ( variableType.getName().equals(symbolTable.getClassName())){
                    if(!(symbolTable.getSuper() == null)){
                        return "";
                    }
                    if ( symbolTable.getMethods().contains(identifier)){
                        return  "";
                    }
                    else {
                        addSemanticErrorReport(reports, jmmNode, "Variable '" + variable + "of Type: " +variableType.getName()+" don't have method "+ identifier);
                        return "<Invalid>";
                    }
                }
            }


            else {
            return "";}

        Type identifierType = symbolTable.getSymbolByName(identifier);

        if (hasArrayAccess(jmmNode) && !identifierType.isArray()) {
            addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "' cannot be accessed .");
            return "<Invalid>";
        }
        if(hasArrayAccess(jmmNode) && hasArrayAccess(jmmNode.getJmmParent().getJmmChild(1)))
        {
            String type =symbolTable.getSymbolByName(jmmNode.getJmmParent().getJmmChild(1).get("value")).getName();

            if(type.equals("boolean") || type.equals("void"))
            {
                addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "' cannot be accessed by an "+ type );
                return "<Invalid>";
            }
        }
        return "";
    }

    private String visitMethodDecl(JmmNode jmmNode, List<Report> reports) {
        JmmNode child = jmmNode.getJmmChild(0);
        //String a= child.get("value");
        for (JmmNode childs : jmmNode.getChildren()) {
             visit(childs, reports);
        }
        return "";

    }

    public  Type getType(JmmNode jmmNode) {
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
            case "MultDivExpr":
            case "AddSuAbExpr":
            case "RelExpr" :
                JmmNode leftOperand = jmmNode.getJmmChild(0);
                JmmNode rightOperand = jmmNode.getJmmChild(1);
                Type leftType = symbolTable.getSymbolByName(leftOperand.get("value"));
                Type rightType;
                if (!(jmmNode.getJmmChild(1).getKind().equals("IntExpr"))){
                    rightType = symbolTable.getSymbolByName(jmmNode.getJmmChild(1).get("value"));

                }
                else{

                    rightType = symbolTable.getSymbolByName(rightOperand.get("value")) != null ?
                            symbolTable.getSymbolByName(rightOperand.get("value")) : new Type("int",false);

                }
                // Determine the resulting type based on the actual operation type
                if (leftType.getName().equals("int") && rightType.getName().equals("int")) {
                    type = new Type("int", false); // Change isArray to false
                } else if (leftType.getName().equals("boolean") && rightType.getName().equals("boolean")) {
                    type = new Type("boolean", false); // Change isArray to false
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
                return jmmNode.hasAttribute("value") ?  new Type(jmmNode.get("value"), isArray) : new Type("UNKNOWN",isArray);
        }
    }

    private String getTypeSafe(JmmNode jmmNode) {
        if ( jmmNode.hasAttribute("value")){
            return jmmNode.get("value");
        }
        if ( jmmNode.getKind().equals("ArrayAccessExpr")){
            return jmmNode.getJmmChild(0).get("value");
        }
        else return "UNKNOWN";
    }


    private Type getTypee(JmmNode node) {

        if (!(node.getKind().equals("IntExpr"))){
            return symbolTable.getSymbolByName(getTypeSafe(node));
        }
        else{
            return symbolTable.getSymbolByName(getTypeSafe(node)) != null ?
                    symbolTable.getSymbolByName(getTypeSafe(node)) : new Type("int",false);
    }
    }

    private Type getTypeFromSymbolTable(JmmNode node, Type type) {
        if (!type.getName().equals("#UNKNOWN")) {
            return type;
        } else if (isVariableDeclared(node.get("value"))) {
            return symbolTable.getSymbolByName(node.get("value"));
        } else {
            return type;
        }
    }

    private boolean isInvalidCondition(JmmNode node, Type type) {
        return node.getJmmParent() != null && (node.getJmmParent().getKind().equals("IfStmt") || node.getJmmParent().getKind().equals("WhileStmt")) && !type.getName().equals("boolean") && !List.of("<").contains(node.get("op"));
    }

    private boolean isInvalidOperator(JmmNode node, Type lhsType, Type rhsType) {
        if (Arrays.asList("+", "-", "*", "/").contains(node.get("op")) || List.of("&&").contains(node.get("op")) || List.of("<").contains(node.get("op"))) {
            if (lhsType.isArray()) {
                return true;
            }

            if (Arrays.asList("+", "-", "*", "/").contains(node.get("op")) || List.of("<").contains(node.get("op"))) {
                if (!lhsType.getName().equals("int")) {
                    return true;
                }
            }

            if (List.of("&&").contains(node.get("op"))) {
                if (!lhsType.getName().equals("boolean")) {
                    return true;
                }
            }

            if (!Arrays.asList("int", "void", "boolean").contains(lhsType.getName())) {
                return true;
            }
            if ("<".contains(node.get("op"))) {
                node.put("type",  new Type("boolean", false).getName());
                node.put("isArray", String.valueOf( new Type("boolean", false).isArray()));
            } else {
                node.put("type", lhsType.getName());
                node.put("isArray", String.valueOf(lhsType.isArray()));
            }

            if (!(lhsType.getName()).equals(rhsType.getName())) {
                return true;
            }
        }

        return false;
    }

    private boolean isBinaryOp(JmmNode node) {
        return node.getKind().equals("MultDivExpr") || node.getKind().equals("AddSuAbExpr") || node.getKind().equals("RelExpr");
    }
    private String visitBinaryOp(JmmNode jmmNode, List<Report> reports) {

        Stack<JmmNode> stack = new Stack<>();
        stack.push(jmmNode);

        while(!stack.isEmpty()){
            jmmNode = stack.pop();


            JmmNode lhsNode = jmmNode.getJmmChild(0);
            JmmNode rhsNode = jmmNode.getJmmChild(1);

            Type lhsType = getTypee(lhsNode);
            Type rhsType = getTypee(rhsNode);
            if(lhsNode.getKind().equals("ArrayAccessExpr")){
                lhsNode = jmmNode.getJmmChild(0).getJmmChild(0);
                if(lhsNode.getKind().equals("Identifier")){
                   visit(lhsNode, reports);

                }
            }

            if(rhsNode.getKind().equals("ArrayAccessExpr")){
                JmmNode rhsParentNode = jmmNode.getJmmChild(0);
                if(!rhsParentNode.getChildren().isEmpty()){
                    rhsNode = rhsParentNode.getJmmChild(0);
                }
                else {
                    rhsNode = lhsNode;
                }
                if(rhsNode.getKind().equals("Identifier")){
                    visit(lhsNode, reports);
                }
            }


            if (lhsType.getName().equals("#UNKNOWN") || rhsType.getName().equals("#UNKNOWN")) {
                lhsType = symbolTable.getSymbolByName(jmmNode.getJmmChild(0).get( "value"));
                rhsType = getTypeFromSymbolTable(jmmNode.getJmmChild(1), rhsType);
            }


            if (isInvalidCondition(jmmNode, lhsType)) {
                addSemanticErrorReport(reports, jmmNode.getJmmParent(), "Invalid condition type : " + rhsType.print());
                return "<INVALID>";
            }


            // If the current child is a BinaryOp, add it to the stack
            if (isBinaryOp( lhsNode)) {
                stack.push(lhsNode);
            } else {
                if (isInvalidOperator(jmmNode, lhsType, rhsType)) {
                    addSemanticErrorReport(reports, jmmNode, "Invalid operator " + jmmNode.get("op") + " applied to " + lhsType.print() + " and " + rhsType.print());
                    return "<INVALID>";
                }
            }
        }

        return "";
    }

    public  boolean isVariableDeclaredWithMethod(String varName,String parent) {
        // Check if the variable exists in the fields
       if ( isVariableDeclared(varName)) return  true;

         for ( Symbol symbol : symbolTable.getArgsByMethod(parent)){
            if (symbol.getName().equals(varName)){return true;}
        }
        //for (){}
        return false;
    }

        public  boolean isVariableDeclared(String varName) {
        // Check if the variable exists in the fields
        for (Symbol field : symbolTable.getFields()) {
            if (field.getName().equals(varName)) {
                return true;
            }
        }


        for (var method : symbolTable.getMethods()){
            for (Symbol localVar : symbolTable.getLocalVariables(method)) {
                if (localVar.getName().equals(varName)) {
                    return true;
                }
            }

        }
        if ( symbolTable.hasImport(varName)) return true;


        return false;
    }
    private boolean hasArrayAccess(JmmNode jmmNode) {
        JmmNode parent = jmmNode.getJmmParent();
        if (parent == null) {
            return false;
        }
        if(parent.getChildren().size() > 1 && jmmNode.getJmmParent().getJmmChild(1).equals(jmmNode))
        {
            if(symbolTable.getSymbolByName(jmmNode.get("value")).getName().equals("int"))
            {return false;}
        }
        if ( (parent.getKind().equals("ArrayAccessExpr")) || (jmmNode.getKind().equals("IdExpr") && !jmmNode.getChildren().isEmpty()) || parent.getJmmParent().getKind().equals("ArrayAccessExpr")) {
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
        if (!isVariableDeclared(identifier)) {
            addSemanticErrorReport(reports, jmmNode, "Variable '" + identifier + "' cannot be Found.");
            return "<Invalid>";
        }
        JmmNode parent = jmmNode.getJmmParent();

        Type identifierType = symbolTable.getSymbolByName(identifier);

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

    private String visitReturnStmt(JmmNode jmmNode, List<Report> reports) {

        String methodName = "#UNKNOWN";

        // Get the method return type
        Type methodReturnType = symbolTable.getReturnType(methodName);

        // Get the return expression type
        JmmNode returnExpression = jmmNode.getChildren().get(0);
        if (isBinaryOp(returnExpression)) {
             visit(returnExpression, reports);
        }

        Type expressionType = getType(returnExpression);

        if (symbolTable.hasMethod(expressionType.getName())) {
            expressionType = symbolTable.getReturnType(expressionType.getName());
        }

        if (returnExpression.getKind().equals("MethodCallExpr")) {
            visit(returnExpression, reports);
        }
        // Check if the return type is compatible
        if (!expressionType.equals(methodReturnType)) {
            // If the expression is a method call on an imported class, assume it's valid
            if (returnExpression.getKind().equals("MethodCallExpr")) {
                JmmNode methodCallTarget;
                String variableName;
                String parentName;
                if(returnExpression.getChildren().isEmpty()){
                    variableName = returnExpression.get("value");
                    parentName = returnExpression.getJmmParent().getJmmParent().hasAttribute("name") ? returnExpression.getJmmParent().getJmmParent().get("name") : "";

                }
                else{
                     methodCallTarget = returnExpression.getChildren().get(0);
                     variableName = methodCallTarget.get("value");
                     parentName = returnExpression.getJmmParent().getJmmParent().hasAttribute("name") ? returnExpression.getJmmParent().getJmmParent().get("name") : "";
                }

                Type variableType = null;
                String className;

                if(isVariableDeclaredWithMethod(variableName,parentName)){
                    return "";
                }
                variableType = symbolTable.getSymbolByName(variableName) !=null ? symbolTable.getSymbolByName(variableName) : new Type("UNKNOWN",false) ;
                className = variableType.getName();

                if (symbolTable.hasImport(className)) {
                    // Do not generate a report for imported class method calls
                    return "";
                }

                else {
                    addSemanticErrorReport(reports, jmmNode, "Variable '" + variableName + "' cannot be Found.");
                    return "";
                }
            }
            else if(returnExpression.getKind().equals("IdExpr") || returnExpression.getKind().equals("ArrayAccessExpr")){
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
