package pt.up.fe.comp2023.optimization;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2023.analysis.symbolTable.MySymbolTable;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static pt.up.fe.comp2023.analysis.symbolTable.MySymbolTable.getClosestMethod;
import static pt.up.fe.comp2023.analysis.symbolTable.MySymbolTable.getMethodName;
import static pt.up.fe.comp2023.optimization.OllirUtils.*;


public class OllirGenerator extends AJmmVisitor<TempVar, Boolean> {

    private final StringBuilder ollirCode;
    private final MySymbolTable symbolTable;
    private final int numberOfSpaces;
    private int indentationLevel = 0;
    private int tempVarCounter = -1;
    private int ifCounter = -1;
    private int loopCounter = -1;


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
        addVisit("NewObjectExpr", this::visitNewObject);
        addVisit("MethodCallExpr", this::visitMethodCall);
        addVisit("ArrayLengthExpr", this::visitMethodCall);
        addVisit("ThisExpr", this::visitThis);
        addVisit("MultDivExpr", this::visitBinOp);
        addVisit("AddSubExpr", this::visitBinOp);
        addVisit("RelExpr", this::visitBinOp);
        addVisit("AndOrExpr", this::visitBinOp);
        addVisit("AssignmentExpr", this::visitBinOp);
        addVisit("NotExpr", this::visitNotExpr);

        // Checkpoint final
        addVisit("IfElseStmt", this::visitIfElse);
        addVisit("WhileStmt", this::visitWhile);
        addVisit("BlockStmt", this::visitBlock);
        addVisit("NewIntArrayExpr", this::visitNewArray);
        addVisit("ArrayAccessExpr", this::visitArrayIndex);


        setDefaultVisit((node, dummy) -> true);
    }

    private Boolean visitNotExpr(JmmNode node, TempVar tempVar) {
        TempVar childHolder = createTemporaryVariable(node);
        visit(node.getJmmChild(0), childHolder);
        tempVar.setVariableType(childHolder.getVariableType());
        startNewLine();
        ollirCode.append(createTemporaryAssign(tempVar, "!.bool " + childHolder.getSubstituteWithType()));
        tempVar.setVariableType(childHolder.getVariableType());
        return true;
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


    private Boolean visitArrayElement(JmmNode node, TempVar substituteVariable) {
        String variableName;
        if (node.getChildren().size() > 1) {
            TempVar t = new TempVar("");
            visit(node.getJmmChild(0), t);
            variableName = t.getVariableName();
        } else {
            variableName = node.getJmmChild(0).get("value");
        }
        Symbol symbol;
        if (symbolTable.getClosestSymbol(node.getJmmChild(0), variableName).isPresent()) {
            symbol = symbolTable.getClosestSymbol(node.getJmmChild(0), variableName).get();
        } else {
            symbol = new Symbol(new Type("void", false), variableName);
        }

        // Visit indexation
        TempVar positionChild = createTemporaryVariable(node);
        visit(node.getJmmChild(1), positionChild);

        // Get element type
        substituteVariable.setVariableName(variableName);
        Type elementType = new Type(symbol.getType().getName(), false);

        // Hold indexation in variable
        startNewLine();
        TempVar positionTemporaryVariable = createTemporaryVariable(node);
        positionTemporaryVariable.setVariableType(new Type("int", false));
        ollirCode.append(createTemporaryAssign(positionTemporaryVariable, positionChild.getSubstituteWithType()));
        var closestMethod = getClosestMethod(node);
        boolean isClassField = symbolTable.getFields().stream().anyMatch(s -> s.getName().equals(variableName) && closestMethod.isPresent())
                && symbolTable.getLocalVariables(getMethodName(closestMethod.get())).stream().noneMatch(s -> s.getName().equals(variableName));
        if (isClassField) {
            TempVar referenceHolder = createTemporaryVariable(node);
            referenceHolder.setVariableType(symbol.getType());
            startNewLine();
            ollirCode.append(createTemporaryAssign(referenceHolder, getField("this", variableName, getOllirType(symbol.getType()))));
            substituteVariable.setVariableName(referenceHolder.getVariableName() + "[" + positionTemporaryVariable.getSubstituteWithType() + "]");
        } else {
            substituteVariable.setVariableName(variableName + "[" + positionTemporaryVariable.getSubstituteWithType() + "]");
        }
        substituteVariable.setVariableType(elementType);

        return true;
    }

    private Boolean visitNewArray(JmmNode node, TempVar substituteVariable) {
        Type arrayType = new Type("int", true);
        Type elementType = new Type("int", false);

        // Visit indexation
        TempVar positionChild = createTemporaryVariable(node);
        visit(node.getJmmChild(0), positionChild);

        // Hold indexation child in variable
        startNewLine();
        TempVar sizeHolder = createTemporaryVariable(node);
        sizeHolder.setVariableType(elementType);
        ollirCode.append(createTemporaryAssign(sizeHolder, positionChild.getSubstituteWithType()));

        // Create new array
        startNewLine();
        substituteVariable.setVariableType(arrayType);
        ollirCode.append(createTemporaryAssign(substituteVariable,
                "new(array, " + sizeHolder.getSubstituteWithType() + ")." + getOllirType(arrayType)));
        substituteVariable.setVariableType(arrayType);

        return true;
    }

    private Boolean visitMethodCall(JmmNode node, TempVar accessedVariable) {

        // Visit attribute
        TempVar attributeChild = createTemporaryVariable(node);
        boolean isParen = node.getJmmChild(0).getKind().equals("ParenExpr");
        if (!isParen) {
            visit(node.getJmmChild(0), attributeChild);
        }
        else {
            visit(node.getJmmChild(0).getJmmChild(0), attributeChild);
        }
        Type elementType = attributeChild.getVariableType();
        String variableName = attributeChild.getVariableName();

        if (accessedVariable == null) {
            accessedVariable = createTemporaryVariable(node);
            accessedVariable.setVariableType(elementType);
            accessedVariable.setVariableName(variableName);
        }
        accessedVariable.setVariableName(variableName);

        // Handle the length case
        if (node.getKind().equals("ArrayLengthExpr")) {
            TempVar lengthHolder = createTemporaryVariable(node);
            startNewLine();
            visit(node.getJmmChild(0), accessedVariable);
            lengthHolder.setVariableType(new Type("int", false));
            ollirCode.append(createTemporaryAssign(lengthHolder, arrayLength(accessedVariable.getValue(), "i32")));
            accessedVariable.setValue(lengthHolder.getVariableName());
            accessedVariable.setVariableType(new Type("int", false));
            return true;
        }

        // Visit arguments
        var arguments = new ArrayList<TempVar>();
        if (node.getChildren().size() > 1) {
            for (var arg : node.getJmmChild(1).getChildren()) {
                TempVar methodArgument = createTemporaryVariable(node);
                visit(arg, methodArgument);
                if (arg.getKind().equals("ArrayAccessExpr")){
                    System.out.println(node.getKind());
                    TempVar idxHolder = createTemporaryVariable(node);
                    idxHolder.setVariableType(new Type("int", false));
                    startNewLine();
                    ollirCode.append(createTemporaryAssign(idxHolder, methodArgument.getSubstituteWithType()));
                    methodArgument = idxHolder;
                }

                arguments.add(methodArgument);
            }

        }

        // Write method call
        startNewLine();
        String methodName = node.get("method");
        if (node.getAncestor("ReturnExpression").isPresent() && node.getAncestor("MethodDecl").isPresent()) {
            methodName = node.getAncestor("MethodDecl").get().get("name");
        }
        Type methodType = symbolTable.getReturnType(methodName);

        if (methodType == null) {
            methodType = accessedVariable.getAssignType();
        }

        String ollirMethodType = getOllirType(methodType);
        TempVar methodCallHolder = createTemporaryVariable(node);
        methodCallHolder.setVariableType(methodType);

        boolean isVirtualCall = attributeChild.getValue().equals("this") || symbolTable.isLocalVariable(node, attributeChild.getSubstitute());


        TempVar targetHolder = isVirtualCall ? createTemporaryVariable(node) : accessedVariable;

        if (isVirtualCall) {
            targetHolder.setVariableType(accessedVariable.getVariableType());
//            System.out.println(accessedVariable.getVariableType());
//            ollirCode.append(createTemporaryAssign(targetHolder, accessedVariable.getSubstituteWithType()));
//            targetHolder.setVariableType(accessedVariable.getVariableType());
//            startNewLine();
        }

        methodCallHolder.setVariableType(methodType);

        ollirCode.append(createTemporaryAssign(methodCallHolder,
                invoke(isVirtualCall ? "invokevirtual" : "invokestatic", attributeChild.getInvokeString(node, symbolTable),
                        node.get("method"),
                        arguments.stream().map(TempVar::getSubstituteWithType).collect(Collectors.toList()), ollirMethodType)));

        accessedVariable.setValue(methodCallHolder.getVariableName());
        accessedVariable.setVariableType(methodType);
        return true;

    }

    private Boolean visitFieldDeclaration(JmmNode node, TempVar dummy) {
        String varName = node.get("varName");
        String varType = node.getChildren().get(0).get("rawType");
        boolean isArray = node.getChildren().get(0).getKind().contains("Array");
        Type type = new Type(varType, isArray);
        ollirCode.append(varName).append(".").append(getOllirType(type));
        ollirCode.append(";");
        return true;
    }

    private Boolean visitExpression(JmmNode node, TempVar dummy) {
        visit(node.getJmmChild(0), dummy);
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
            if (child.getKind().equals("ReturnExpression")) {
                visit(child);
                returned = true;
            } else {
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
            if (child.getKind().equals("ReturnExpression")) {
                visit(child);
                returned = true;
            } else {
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
        t2.setVariableType(t1.getVariableType());
        t2.setAssignType(t1.getVariableType());
        visit(node.getJmmChild(1), t2);
        String operation = node.get("op");
        if (node.getKind().equals("AssignmentExpr")) {
            String ollirType = getOllirType(t1.getVariableType());
            var closestMethod = getClosestMethod(node);
            boolean isClassField = symbolTable.getFields().stream().anyMatch(s -> s.getName().equals(t1.getVariableName())) && closestMethod.isPresent()
                    && symbolTable.getLocalVariables(getMethodName(closestMethod.get())).stream().noneMatch(s -> s.getName().equals(t1.getVariableName())) &&
                    symbolTable.getParameters(getMethodName(closestMethod.get())).stream().noneMatch(s -> s.getName().equals(t1.getVariableName()));
            startNewLine();
            if (isClassField) {
                ollirCode.append(OllirUtils.putField("this", t1.getSubstituteWithType(), t2.getSubstituteWithType())).append(";");
            } else {
                ollirCode.append(t1.getSubstituteWithType()).append(" :=.").append(ollirType)
                        .append(" ").append(t2.getSubstituteWithType()).append(";");
            }
            return true;
        } else {
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

    private Boolean visitNewObject(JmmNode node, TempVar substituteVariable) {
        if (substituteVariable == null) {
            substituteVariable = createTemporaryVariable(node);

        }
        startNewLine();
        String className = node.get("object");
        substituteVariable.setVariableType(new Type(className, false));
        ollirCode.append(createTemporaryAssign(substituteVariable,
                "new(" + className + ")." + className));

        startNewLine();
        ollirCode.append(invoke("invokespecial", substituteVariable.getSubstituteWithType(),
                "<init>", new ArrayList<>(), "V")).append(";");

        substituteVariable.setVariableType(new Type(className, false));
        return true;
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

    private Boolean visitThis(JmmNode node, TempVar substituteVariable) {
        Type classType = new Type(symbolTable.getClassName(), false);
        if (node.getAncestor("MethodCallExpr").isEmpty() && node.getAncestor("NewObjectExpr").isEmpty()) {
            substituteVariable.setValue("$0.this");
        } else {
            substituteVariable.setValue("this");
        }
        substituteVariable.setVariableType(classType);
        return true;
    }

    private Boolean visitIdExpr(JmmNode node, TempVar substituteVariable) {
        String variableName = node.get("value");
        Symbol symbol;
        if (symbolTable.getClosestSymbol(node, variableName).isPresent()) {
            symbol = symbolTable.getClosestSymbol(node, variableName).get();
            substituteVariable.setVariableType(symbol.getType());
            substituteVariable.setVariableName(symbol.getName());
        } else {
            symbol = new Symbol(new Type("void", false), variableName);
            substituteVariable.setVariableType(symbol.getType());
        }
        var closestMethod = getClosestMethod(node);
        if (closestMethod.isEmpty()) {
            return false;
        }

        var methodName = getMethodName(closestMethod.get());

        boolean isParameter = false;
        int parameterNumber = methodName.equals("main") ? 0 : 1;
        for (var param : symbolTable.getParameters(methodName)) {
            if (param.getName().equals(variableName)) {
                isParameter = true;
                break;
            }
            parameterNumber += 1;
        }

        boolean isClassField = symbolTable.getFields().stream().anyMatch(s -> s.getName().equals(variableName))
                && symbolTable.getLocalVariables(methodName).stream().noneMatch(s -> s.getName().equals(variableName)) &&
                symbolTable.getParameters(methodName).stream().noneMatch(s -> s.getName().equals(variableName));
        ;
        boolean isAssign = node.getAncestor("AssignmentExpr").isPresent()
                && node.getAncestor("AssignmentExpr").get().get("op").equals("=")
                && node.getAncestor("AssignmentExpr").get().getJmmChild(0).equals(node);
        if (isClassField && !isAssign) {
            startNewLine();
            TempVar tempHolder = createTemporaryVariable(node);
            tempHolder.setVariableType(symbol.getType());
            ollirCode.append(createTemporaryAssign(tempHolder,
                    getField("this", variableName, getOllirType(symbol.getType()))));
            substituteVariable.setVariableName(tempHolder.getVariableName());
            substituteVariable.setValue(getSafeVariableName(tempHolder.getVariableName()));
        } else {
            substituteVariable.setVariableName(variableName);
            if (isParameter && symbolTable.getLocalVariables(methodName).stream().noneMatch(s -> s.getName().equals(variableName))) {
                substituteVariable.setValue("$" + parameterNumber + "." + getSafeVariableName(variableName));

            } else {
                substituteVariable.setValue(getSafeVariableName(variableName));
            }
        }
        return true;
    }

    private Boolean visitBlock(JmmNode node, TempVar tempVar) {
        for (var child : node.getChildren()) {
            visit(child, tempVar);
        }
        return true;
    }

    private Boolean visitIfElse(JmmNode node, TempVar tempVar) {
        ifCounter++;
        int localCounter = ifCounter;

        TempVar conditionHolder = createTemporaryVariable(node);
        visit(node.getJmmChild(0), conditionHolder);
        startNewLine();
        ollirCode.append("if (!.bool ").append(conditionHolder.getSubstituteWithType())
                .append(") goto Else").append(localCounter).append(";");
        startNewLine();
        ollirCode.append("Then").append(localCounter).append(":");
        indentationLevel++;
        for (var thenChild : node.getJmmChild(1).getChildren()) {
            visit(thenChild);
        }
        startNewLine();
        ollirCode.append("goto EndIf").append(localCounter).append(";");
        indentationLevel--;
        startNewLine();
        ollirCode.append("Else").append(localCounter).append(":");
        indentationLevel++;
        for (var elseChild : node.getJmmChild(2).getChildren()) {
            visit(elseChild);
        }
        indentationLevel--;
        startNewLine();
        ollirCode.append("EndIf").append(localCounter).append(":");
        return true;
    }

    private Boolean visitWhile(JmmNode node, TempVar tempVar) {
        loopCounter++;
        int localCounter = loopCounter;

        startNewLine();
        ollirCode.append("Loop").append(localCounter).append(":");
        indentationLevel++;
        TempVar conditionHolder = createTemporaryVariable(node);
        visit(node.getJmmChild(0), conditionHolder);
        startNewLine();
        ollirCode.append("if (!.bool ").append(conditionHolder.getSubstituteWithType())
                .append(") goto EndLoop").append(localCounter).append(";");
        for (var bodyChild : node.getJmmChild(1).getChildren()) {
            visit(bodyChild);
        }
        startNewLine();
        ollirCode.append("goto Loop").append(localCounter).append(";");
        indentationLevel--;
        startNewLine();
        ollirCode.append("EndLoop").append(localCounter).append(":");
        return true;
    }

    private Boolean visitArrayIndex(JmmNode node, TempVar tempVar) {
        String variableName = node.getJmmChild(0).get("value");
        Symbol symbol;
        if (symbolTable.getClosestSymbol(node.getJmmChild(0), variableName).isPresent()) {
            symbol = symbolTable.getClosestSymbol(node.getJmmChild(0), variableName).get();
        } else {
            symbol = new Symbol(new Type("void", false), variableName);
        }

        // Visit indexation
        TempVar positionChild = createTemporaryVariable(node);
        visit(node.getJmmChild(1), positionChild);

        // Get element type
        tempVar.setVariableName(variableName);
        Type elementType = new Type(symbol.getType().getName(), false);

        // Hold indexation in variable
        startNewLine();
        TempVar positionTemporaryVariable = createTemporaryVariable(node);
        positionTemporaryVariable.setVariableType(new Type("int", false));
        ollirCode.append(createTemporaryAssign(positionTemporaryVariable, positionChild.getSubstituteWithType()));
        var closestMethod = getClosestMethod(node);
        boolean isClassField = symbolTable.getFields().stream().anyMatch(s -> s.getName().equals(variableName) && closestMethod.isPresent())
                && symbolTable.getLocalVariables(getMethodName(closestMethod.get())).stream().noneMatch(s -> s.getName().equals(variableName));
        if (isClassField) {
            TempVar referenceHolder = createTemporaryVariable(node);
            referenceHolder.setVariableType(symbol.getType());
            startNewLine();
            ollirCode.append(createTemporaryAssign(referenceHolder, getField("this", variableName, getOllirType(symbol.getType()))));
            tempVar.setVariableName(referenceHolder.getVariableName() + "[" + positionTemporaryVariable.getSubstituteWithType() + "]");
        } else {
            tempVar.setVariableName(variableName + "[" + positionTemporaryVariable.getSubstituteWithType() + "]");
        }
        tempVar.setVariableType(elementType);

        return true;
    }


}

