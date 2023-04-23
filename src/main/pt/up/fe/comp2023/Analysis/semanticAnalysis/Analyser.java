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
        addVisit("ReturnExpression", this::visitReturnStmt);
        addVisit("MethodCallExpr", this::visitMethodCall);

        this.setDefaultVisit(this::start);

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
        if (! jmmNode.hasAttribute("value")){return "";}
        String identifier = jmmNode.get("value");

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
        else return "UNKNOWN";
    }
    private String visitBinaryOp(JmmNode jmmNode, List<Report> reports) {

        Stack<JmmNode> stack = new Stack<>();
        stack.push(jmmNode);

        while(!stack.isEmpty()){
            jmmNode = stack.pop();

            Type lhsType;
            Type rhsType;
            if (!(jmmNode.getJmmChild(0).getKind().equals("IntExpr"))){
                lhsType = symbolTable.getSymbolByName(getTypeSafe(jmmNode.getJmmChild(0)));

            }
            else{

                lhsType = symbolTable.getSymbolByName(getTypeSafe(jmmNode.getJmmChild(0))) != null ?
                        symbolTable.getSymbolByName(getTypeSafe(jmmNode.getJmmChild(0))) : new Type("int",false);

            }

            if (!(jmmNode.getJmmChild(1).getKind().equals("IntExpr"))){
                 rhsType = symbolTable.getSymbolByName(getTypeSafe(jmmNode.getJmmChild(1)));

            }
            else{

                 rhsType = symbolTable.getSymbolByName(getTypeSafe(jmmNode.getJmmChild(1))) != null ?
                         symbolTable.getSymbolByName(getTypeSafe(jmmNode.getJmmChild(1))) : new Type("int",false);

            }
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
                if (!lhsNode.getKind().equals("MultDivExpr") || !lhsNode.getKind().equals("AddSuAbExpr") || !lhsNode.getKind().equals("RelExpr")) {
                    lhsType = lhsType.getName().equals("#UNKNOWN") && isVariableDeclared(lhsNode.get("value"))
                            ? symbolTable.getSymbolByName(lhsNode.get("value"))
                            : lhsType;
                }

                if (!rhsNode.getKind().equals("MultDivExpr") || !rhsNode.getKind().equals("AddSuAbExpr") || !rhsNode.getKind().equals("RelExpr")) {
                    rhsType = rhsType.getName().equals("#UNKNOWN") && isVariableDeclared(rhsNode.get("value"))
                            ? symbolTable.getSymbolByName(rhsNode.get("value"))
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
            if (lhsNode.getKind().equals("MultDivExpr") || lhsNode.getKind().equals("AddSuAbExpr") || lhsNode.getKind().equals("RelExpr")) {
                stack.push(lhsNode);
            } else {
                if (Arrays.asList("+", "-", "*", "/").contains(jmmNode.get("op")) || List.of("&&").contains(jmmNode.get("op")) || List.of("<").contains(jmmNode.get("op"))) {
                    if (lhsType.isArray()) {
                        addSemanticErrorReport(reports, jmmNode, "Invalid operator" + jmmNode.get("op") + "applied to " + lhsType.print() + " and " + rhsType.print() );
                        return "<Invalid>";
                    }
                }


                if (Arrays.asList("+", "-", "*", "/").contains(jmmNode.get("op")) || List.of("<").contains(jmmNode.get("op"))) {
                    if (!lhsType.getName().equals("int")) {
                        addSemanticErrorReport(reports, jmmNode, "Invalid operator" + jmmNode.get("op") + "applied to " + lhsType.print() + " and " + rhsType.print() );
                        return "<Invalid>";

                    }
                }

                if (List.of("&&").contains(jmmNode.get("op"))) {
                    if (!lhsType.getName().equals("boolean")) {
                        addSemanticErrorReport(reports, jmmNode, "Invalid operator" + jmmNode.get("op") + "applied to " + lhsType.print() + " and " + rhsType.print() );
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

                if (!(lhsType.getName()).equals(rhsType.getName())) {
                    addSemanticErrorReport(reports, jmmNode, "Invalid operator" + jmmNode.get("op") + "applied to" + lhsType.print() + " and " + rhsType.print() );
                    return "<Invalid>";
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
        if (returnExpression.getKind().equals("MultDivExpr") || returnExpression.getKind().equals("AddSuAbExpr") || returnExpression.getKind().equals("RelExpr") ) {
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
                //} else {
                    //className = methodCallTarget.get("value");
               // }

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
