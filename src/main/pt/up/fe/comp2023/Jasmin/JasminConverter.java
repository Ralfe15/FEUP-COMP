package pt.up.fe.comp2023.Jasmin;

import org.specs.comp.ollir.*;
import pt.up.fe.comp.jmm.jasmin.JasminBackend;
import pt.up.fe.comp.jmm.jasmin.JasminResult;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JasminConverter implements JasminBackend {
    int conditionals;
    int comparisons;
    int stacklimit = 99; // for CP2, don't care about stack
    int localLimit = 99; // for CP2, don't care about stack
    int stack;
    String className;
    String superClass;
    ArrayList<String> imports;

    @Override
    public JasminResult toJasmin(OllirResult ollirResult) {
        ClassUnit ollirClass = ollirResult.getOllirClass();
        System.out.println(ollirClass);
        // correr ollirclass, pega root node e meter no jasminCode e se tiver erros mandar para o report

        try {
            // Example of what you can do with the OLLIR class
            // System.out.println("-------------- checkmethodlabels --------------\n");
            ollirClass.checkMethodLabels(); // check the use of labels in the OLLIR loaded
            // System.out.println("-------------- buildcfgs --------------\n");
            ollirClass.buildCFGs(); // build the CFG of each method
            ollirClass.outputCFGs(); // output to .dot files the CFGs, one per method
            // System.out.println("-------------- buildvartables --------------\n");
            ollirClass.buildVarTables(); // build the table of variables for each method
            // System.out.println(ollirClass.getClassName());
            /*
            System.out.println("-------------- show --------------\n");
            ollirClass.show(); // print to console main information about the input OLLIR
            */

            // Convert the OLLIR to a String containing the equivalent Jasmin code
            String jasminCode = generateCode(ollirClass);
            System.out.println("\n\n---------\n" + jasminCode + "\n---------\n\n");
            /*
            System.out.println("\nclassName : " + className);
            System.out.println("\nsuperClass : " + superClass);
            System.out.println("\nimports : " + imports);
             */

            // More reports from this stage
            List<Report> reports = new ArrayList<>();

            return new JasminResult(ollirResult, "jasminCode", reports);
        } catch (OllirErrorException e){
            return new JasminResult(ollirClass.getClassName(), null, Arrays.asList(Report.newError(Stage.GENERATION, -1, -1, "Exception during Jasmin generation", e)));
        }
    }

    private String generateCode(ClassUnit classUnit) {
        className = classUnit.getClassName();
        imports = classUnit.getImports();
        if (classUnit.getSuperClass() == null)
            superClass = "java/lang/Object";
        else
            superClass = classUnit.getSuperClass();

        StringBuilder jasminCode = new StringBuilder(classHeader(classUnit));

        for (Field field : classUnit.getFields()) {
            jasminCode.append("\n").append(generateField(field));
        }
        for (Method method : classUnit.getMethods()) {
            jasminCode.append("\n").append(generateMethod(method));
        }

        return jasminCode.toString();
    }

    private String classHeader(ClassUnit classUnit) {
        StringBuilder jasminCode = new StringBuilder(".class");

        if (classUnit.getClassAccessModifier() != AccessModifiers.DEFAULT) {
            jasminCode.append(" ").append(classUnit.getClassAccessModifier().toString().toLowerCase());
        }

        jasminCode.append(" ").append(classUnit.getClassName()).append("\n.super ").append(superClass).append("\n");
        return jasminCode.toString();
    }

    private String generateField(Field field) {
        StringBuilder jasminCode = new StringBuilder(".field");

        AccessModifiers accessModifier = field.getFieldAccessModifier();
        if (accessModifier != AccessModifiers.DEFAULT) {
            jasminCode.append(" ").append(field.getFieldAccessModifier().toString().toLowerCase());
        }
        if (field.isStaticField()) {
            jasminCode.append(" static");
        }
        if (field.isFinalField()) {
            jasminCode.append(" final");
        }
        jasminCode.append(" ").append(field.getFieldName()).append(" ").append(getDescriptor(field.getFieldType(), true));

        if (field.isInitialized()) {
            jasminCode.append(" = ").append(field.getInitialValue());
        }
        jasminCode.append("\n");

        return jasminCode.toString();
    }

    private String translateType(ElementType elementType) {
        return switch (elementType) {
            case INT32 -> "I";
            case VOID -> "V";
            case STRING -> "Ljava/lang/String;";
            case BOOLEAN -> "Z";
            default -> "[" + elementType + "] descriptor not implemented";
        };
    }

    private String getDescriptor(Type type, boolean first) {
        ElementType elementType = type.getTypeOfElement();
        String jasminCode = "";
        if (!first)
            jasminCode = "; ";

        if (elementType == ElementType.ARRAYREF) {
            elementType = ((ArrayType) type).getArrayType();
            return jasminCode + "[" + translateType(elementType);
        }

        if (elementType == ElementType.OBJECTREF) {
            String className = ((ClassType) type).getName();
            for (String imported : imports) {
                if (imported.endsWith("." + className))
                    return jasminCode + "L" + imported.replace('.', '/') + ";";
            }
            return jasminCode + "L" + className + ";";
        }

        return jasminCode + translateType(elementType);
    }

    private String generateMethod(Method method) {
        StringBuilder jasminCode = new StringBuilder(methodHeader(method));
        jasminCode.append("\t.limit stack ").append(stacklimit).append("\n");
        jasminCode.append("\t.limit locals ").append(localLimit).append("\n");

        /*

        HashMap<String, Descriptor> varTable = method.getVarTable();
        StringBuilder instructions = new StringBuilder("");

        HashMap<String, Instruction> labels = method.getLabels();
        for (int i = 0; i < method.getInstructions().size(); i++) {
            Instruction instruction = method.getInstr(i);
            for (String s : labels.keySet()) {
                if (labels.get(s) == instruction) {
                    instructions.append(s).append(":\n");
                }
            }

            instructions.append(generateInstruction(instruction, varTable));
            if (instruction.getInstType() == InstructionType.CALL) {
                if (((CallInstruction) instruction).getReturnType().getTypeOfElement() != ElementType.VOID) {
                    instructions.append("\tpop\n");
                }
            }
        }

        ArrayList<Integer> locals = new ArrayList<>();
        for (Descriptor descriptor : varTable.values()) {
            if (!locals.contains(descriptor.getVirtualReg())) {
                locals.add(descriptor.getVirtualReg());
            }
        }
        if (!locals.contains(0) && !method.isConstructMethod()) {
            locals.add(0);
        }

         */

        // jasminCode.append("\t.limit locals ").append(locals.size()).append("\n\n").append(instructions);

        if (method.isConstructMethod()) {
            jasminCode.append("\treturn\n");
        }

        jasminCode.append(".end method\n");

        return jasminCode.toString();
    }

    private String methodHeader(Method method) {
        StringBuilder jasminCode = new StringBuilder(".method");

        if (method.getMethodAccessModifier() != AccessModifiers.DEFAULT)
            jasminCode.append(" ").append(method.getMethodAccessModifier().toString().toLowerCase());

        if (method.isStaticMethod())
            jasminCode.append(" static");
        if (method.isFinalMethod())
            jasminCode.append(" final");

        if (method.isConstructMethod())
            jasminCode.append(" <init>");
        else
            jasminCode.append(" ").append(method.getMethodName());

        jasminCode.append("(");
        for (int i = 0; i < method.getParams().size(); i++) {
            if (i == 0)
                jasminCode.append(getDescriptor(method.getParam(i).getType(), true));
            else
                jasminCode.append(getDescriptor(method.getParam(i).getType(), false));
        }
        jasminCode.append(")").append(getDescriptor(method.getReturnType(), true)).append("\n");

        return jasminCode.toString();
    }

    /*
    private String generateInstruction(Instruction instruction, HashMap<String, Descriptor> varTable) {
        switch (instruction.getInstType()) {
            case ASSIGN:
                return assignInstruction((AssignInstruction) instruction, varTable);
            case BRANCH:
                return branchInstruction((CondBranchInstruction) instruction, varTable);
            case GOTO:
                return goToInstruction((GotoInstruction) instruction);
            case RETURN:
                return returnInstruction((ReturnInstruction) instruction, varTable);
            case NOPER:
                return singleOpInstruction((SingleOpInstruction) instruction, varTable);
            case CALL:
                return callInstruction((CallInstruction) instruction, varTable);
            case BINARYOPER:
                return binaryOpInstruction((BinaryOpInstruction) instruction, varTable);
            case PUTFIELD:
                return putFieldInstruction((PutFieldInstruction) instruction, varTable);
            case GETFIELD:
                return getFieldInstruction((GetFieldInstruction) instruction, varTable);
            default:
                return "[ERROR] Instruction not found\n";
        }
    }

     */

    /*
    private String assignInstruction(AssignInstruction instruction, HashMap<String, Descriptor> varTable) {
        StringBuilder jasminCode = new StringBuilder();

        Operand o = (Operand) instruction.getDest();
        int reg = varTable.get(o.getName()).getVirtualReg();

        // case i = i + 1 => iinc i
        if (instruction.getRhs().getInstType() == InstructionType.BINARYOPER) {
            BinaryOpInstruction op = (BinaryOpInstruction) instruction.getRhs();
            if (op.getUnaryOperation().getOpType() == OperationType.ADD) {
                if (!op.getLeftOperand().isLiteral() && op.getRightOperand().isLiteral()) {
                    if (((Operand) op.getLeftOperand()).getName().equals(o.getName())
                            && Integer.parseInt(((LiteralElement) op.getRightOperand()).getLiteral()) == 1) {
                        return "\tiinc " + reg + " 1\n";
                    }
                } else if (op.getLeftOperand().isLiteral() && !op.getRightOperand().isLiteral()) {
                    if (((Operand) op.getRightOperand()).getName().equals(o.getName())
                            && Integer.parseInt(((LiteralElement) op.getLeftOperand()).getLiteral()) == 1) {
                        return "\tiinc " + reg + " 1\n";
                    }
                }
            }
        }

        if (varTable.get(o.getName()).getVarType().getTypeOfElement() == ElementType.ARRAYREF
                && o.getType().getTypeOfElement() != ElementType.ARRAYREF) {
            ArrayOperand arrayOp = (ArrayOperand) o;
            Element index = arrayOp.getIndexOperands().get(0);

            jasminCode.append(loadDescriptor(varTable.get(o.getName())))
                    .append(loadElement(index, varTable));
        }

        jasminCode.append(generateInstruction(instruction.getRhs(), varTable));


        if (o.getType().getTypeOfElement() == ElementType.INT32 || o.getType().getTypeOfElement() == ElementType.BOOLEAN)
            if (varTable.get(o.getName()).getVarType().getTypeOfElement() == ElementType.ARRAYREF) {
                jasminCode.append("\tiastore\n");

                limitStack(stack);
                stack = 0;
                return jasminCode.toString();
            } else
                jasminCode.append("\tistore");
        else {
            jasminCode.append("\tastore");
        }

        jasminCode.append((reg <= 3) ? "_" : " ").append(reg).append("\n");


        limitStack(stack);
        stack = 0;

        return jasminCode.toString();
    }

    private String branchInstruction(CondBranchInstruction instruction, HashMap<String, Descriptor> varTable) {
        StringBuilder jasminCode = new StringBuilder("");

        if (instruction.getCondition().getInstType() == InstructionType.GOTO) {
            /*
            return loadElement(instruction.getLeftOperand(), varTable) +
                    "\tifeq False" + comparisons + "\n" +
                    loadElement(instruction.getRightOperand(), varTable) +
                    "\tifeq False" + comparisons + "\n" +
                    "\tgoto " + instruction.getLabel() + "\n" +
                    "False" + comparisons + ":\n";
             */
    /*
            jasminCode.append("goto ").append(instruction.getLabel());
            return jasminCode.toString();
        }
        /*
        Element l = instruction.getLeftOperand();
        Element r = instruction.getRightOperand();

        jasminCode = loadElement(l, varTable) +
                loadElement(r, varTable) +
                "\t" + getComparison(instruction.getCondOperation()) + " " + instruction.getLabel() + "\n";

        limitStack(stack);
        stack = 0;
         */

        /*
        return jasminCode.toString();
    }

    private String goToInstruction(GotoInstruction instruction) {
        return "\tgoto " + instruction.getLabel() + "\n";
    }

    private String returnInstruction(ReturnInstruction instruction, HashMap<String, Descriptor> varTable) {
        if (!instruction.hasReturnValue())
            return "\treturn\n";

        ElementType returnType = instruction.getOperand().getType().getTypeOfElement();

        String jasminCode = loadElement(instruction.getOperand(), varTable)
                + "\t" + ((returnType == ElementType.INT32 || returnType == ElementType.BOOLEAN) ? "i" : "a") + "return\n";

        limitStack(stack);
        stack = 0;
        return jasminCode;
    }

    private String singleOpInstruction(SingleOpInstruction instruction, HashMap<String, Descriptor> varTable) {
        return loadElement(instruction.getSingleOperand(), varTable);
    }

    private String callInstruction(CallInstruction instruction, HashMap<String, Descriptor> varTable) {
        StringBuilder jasminCode = new StringBuilder();

        switch (instruction.getInvocationType()) {
            case NEW:
                if (instruction.getReturnType().getTypeOfElement() == ElementType.OBJECTREF) {
                    for (Element e : instruction.getListOfOperands()) {
                        jasminCode.append(loadElement(e, varTable));
                    }

                    jasminCode.append("\tnew ")
                            .append(((Operand) instruction.getFirstArg()).getName()).append("\n")
                            .append("\tdup\n");
                } else if (instruction.getReturnType().getTypeOfElement() == ElementType.ARRAYREF) {
                    for (Element e : instruction.getListOfOperands()) {
                        jasminCode.append(loadElement(e, varTable));
                    }

                    jasminCode.append("\tnewarray ");
                    if (instruction.getListOfOperands().get(0).getType().getTypeOfElement() == ElementType.INT32)
                        jasminCode.append("int\n");
                    else
                        jasminCode.append("array type not implemented\n");
                } else
                    jasminCode.append("\tnew (not implemented)\n");

                limitStack(stack);
                stack = 1;

                return jasminCode.toString();
            case invokespecial:
                jasminCode.append(loadElement(instruction.getFirstArg(), varTable));
                limitStack(stack);

                jasminCode.append("\tinvokespecial ")
                        .append((instruction.getFirstArg().getType().getTypeOfElement() == ElementType.THIS) ? superClass : className)
                        .append(".<init>(");

                for (Element e : instruction.getListOfOperands())
                    jasminCode.append(getDescriptor(e.getType()));

                jasminCode.append(")").append(getDescriptor(instruction.getReturnType())).append("\n");
                stack = 0;
                return jasminCode.toString();
            case invokevirtual:
                jasminCode.append(loadElement(instruction.getFirstArg(), varTable));

                for (Element e : instruction.getListOfOperands())
                    jasminCode.append(loadElement(e, varTable));

                limitStack(stack + 1);
                stack = (instruction.getReturnType().getTypeOfElement() == ElementType.VOID) ? 0 : 1;

                jasminCode.append("\tinvokevirtual ")
                        .append(getObjectName(((ClassType) instruction.getFirstArg().getType()).getName()))
                        .append(".").append(((LiteralElement) instruction.getSecondArg()).getLiteral().replace("\"", ""))
                        .append("(");

                for (Element e : instruction.getListOfOperands())
                    jasminCode.append(getDescriptor(e.getType()));

                jasminCode.append(")").append(getDescriptor(instruction.getReturnType())).append("\n");

                return jasminCode.toString();
            case invokestatic:
                for (Element e : instruction.getListOfOperands())
                    jasminCode.append(loadElement(e, varTable));

                limitStack(stack);
                jasminCode.append("\tinvokestatic ")
                        .append(getObjectName(((Operand) instruction.getFirstArg()).getName()))
                        .append(".").append(((LiteralElement) instruction.getSecondArg()).getLiteral().replace("\"", ""))
                        .append("(");

                for (Element e : instruction.getListOfOperands())
                    jasminCode.append(getDescriptor(e.getType()));

                jasminCode.append(")").append(getDescriptor(instruction.getReturnType())).append("\n");

                stack = (instruction.getReturnType().getTypeOfElement() == ElementType.VOID) ? 0 : 1;

                return jasminCode.toString();
            case ldc:
                return loadElement(instruction.getFirstArg(), varTable);
            case arraylength:
                return loadElement(instruction.getFirstArg(), varTable) + "\tarraylength\n";
            default:
                return "\tcall func not implemented\n";
        }
    }

    private String binaryOpInstruction(BinaryOpInstruction instruction, HashMap<String, Descriptor> varTable) {
        if (instruction.getUnaryOperation().getOpType() == OperationType.ANDB) {
            conditionals++;
            limitStack(1);
            stack = 0;

            return loadElement(instruction.getLeftOperand(), varTable) +
                    "\tifeq False" + conditionals + "\n" +
                    loadElement(instruction.getRightOperand(), varTable) +
                    "\tifeq False" + conditionals + "\n" +
                    "\ticonst_1\n" +
                    "\tgoto Store" + conditionals + "\n" +
                    "False" + conditionals + ":\n" +
                    "\ticonst_0\n" +
                    "Store" + conditionals + ":\n";
        }

        if (instruction.getUnaryOperation().getOpType() == OperationType.NOTB) {
            conditionals++;
            limitStack(1);
            stack = 0;

            String jasminCode = loadElement(instruction.getLeftOperand(), varTable);
            if (((Operand) instruction.getRightOperand()).getName().equals(
                    ((Operand) instruction.getLeftOperand()).getName())) {
                jasminCode += "\tifeq";

            } else {
                jasminCode += loadElement(instruction.getRightOperand(), varTable) +
                        "\t" + getComparison(instruction.getUnaryOperation());
            }

            return jasminCode + " True" + conditionals + "\n" +
                    "\ticonst_0\n" +
                    "\tgoto Store" + conditionals + "\n" +
                    "True" + conditionals + ":\n" +
                    "\ticonst_1\n" +
                    "Store" + conditionals + ":\n";
        }

        if (instruction.getUnaryOperation().getOpType() == OperationType.LTH) {
            conditionals++;
            String jasminCode = loadElement(instruction.getLeftOperand(), varTable) +
                    loadElement(instruction.getRightOperand(), varTable) +
                    "\t" + getComparison(instruction.getUnaryOperation()) + " True" + conditionals + "\n" +
                    "\ticonst_0\n" +
                    "\tgoto Store" + conditionals + "\n" +
                    "True" + conditionals + ":\n" +
                    "\ticonst_1\n" +
                    "Store" + conditionals + ":\n";

            stack = 1;
            return jasminCode;
        }

        return loadElement(instruction.getLeftOperand(), varTable) +
                unaryOpInstruction(instruction, varTable);
    }

    private String unaryOpInstruction(UnaryOpInstruction instruction, HashMap<String, Descriptor> varTable) {
        String jasminCode = loadElement(instruction.getRightOperand(), varTable) +
                "\t" + getOperation(instruction.getUnaryOperation()) + "\n";

        limitStack(stack);
        stack -= 1;
        return jasminCode;
    }

    private String putFieldInstruction(PutFieldInstruction instruction, HashMap<String, Descriptor> varTable) {
        String jasminCode = loadElement(instruction.getFirstOperand(), varTable) +
                loadElement(instruction.getThirdOperand(), varTable) +
                "\tputfield " +
                getObjectName(((Operand) instruction.getFirstOperand()).getName()) +
                "/" + ((Operand) instruction.getSecondOperand()).getName() +
                " " + getDescriptor(instruction.getSecondOperand().getType()) + "\n";

        limitStack(stack);
        stack = 0;
        return jasminCode;
    }

    private String getFieldInstruction(GetFieldInstruction instruction, HashMap<String, Descriptor> varTable) {
        String jasminCode = loadElement(instruction.getFirstOperand(), varTable) +
                "\tgetfield " +
                getObjectName(((Operand) instruction.getFirstOperand()).getName()) +
                "/" + ((Operand) instruction.getSecondOperand()).getName() +
                " " + getDescriptor(OllirAccesser.getFieldType(instruction)) + "\n";

        limitStack(stack);
        stack = 0;
        return jasminCode;
    }
    */
}
