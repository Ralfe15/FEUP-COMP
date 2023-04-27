package pt.up.fe.comp2023.Jasmin;

import org.specs.comp.ollir.*;

import java.util.ArrayList;

public class InstructionGenerator {
    private int indentationLevel = 1;
    private int labelCounter = 0;

    public String translateInstruction(Instruction instruction, Method ancestorMethod) {
        InstructionType instructionType = instruction.getInstType();
        StringBuilder translatedInstruction = new StringBuilder();

        switch (instructionType) {
            case CALL:
                translatedInstruction.append(translateInstruction((CallInstruction) instruction, ancestorMethod));
                break;
            case RETURN:
                translatedInstruction.append(translateInstruction((ReturnInstruction) instruction, ancestorMethod));
                break;
            case PUTFIELD:
                translatedInstruction.append(translateInstruction((PutFieldInstruction) instruction, ancestorMethod));
                break;
            case GETFIELD:
                translatedInstruction.append(translateInstruction((GetFieldInstruction) instruction, ancestorMethod));
                break;
            case ASSIGN:
                translatedInstruction.append(translateInstruction((AssignInstruction) instruction, ancestorMethod));
                break;
            case BINARYOPER:
                translatedInstruction.append(translateInstruction((BinaryOpInstruction) instruction, ancestorMethod));
                break;
            case UNARYOPER:
                translatedInstruction.append(translateInstruction((UnaryOpInstruction) instruction, ancestorMethod));
                break;
            case NOPER:
                translatedInstruction.append(translateInstruction((SingleOpInstruction) instruction, ancestorMethod));
                break;
            case GOTO:
                translatedInstruction.append(translateInstruction((GotoInstruction) instruction));
                break;
            default:
                break;
        }

        return translatedInstruction.toString();
    }

    public String translateInstruction(GotoInstruction instruction) {
        return getIndentation() + "goto " + instruction.getLabel();
    }

    public String translateInstruction(UnaryOpInstruction instruction, Method ancestorMethod) {
        Operation operation = instruction.getOperation();
        OperationType operationType = operation.getOpType();
        Element first = instruction.getOperand();

        if (operationType == OperationType.NOT || operationType == OperationType.NOTB) {
            return getCorrespondingLoad(first, ancestorMethod) + "\n";
        }

        return "";
    }

    public String translateInstruction(SingleOpInstruction instruction, Method ancestorMethod) {
        return getCorrespondingLoad(instruction.getSingleOperand(), ancestorMethod);
    }

    public String translateInstruction(GetFieldInstruction instruction, Method ancestorMethod) {
        Element destinationObject = instruction.getFirstOperand();
        Element destinationField = instruction.getSecondOperand();

        if (destinationObject.isLiteral() || destinationField.isLiteral()) {
            return "";
        }

        StringBuilder jasminInstruction = new StringBuilder();

        if (destinationObject.getType().getTypeOfElement() == ElementType.OBJECTREF || destinationObject.getType().getTypeOfElement() == ElementType.THIS) {
            jasminInstruction.append(getCorrespondingLoad(destinationObject, ancestorMethod)).append("\n");
            jasminInstruction.append(getIndentation()).append("getfield ");
        } else if (destinationObject.getType().getTypeOfElement() == ElementType.CLASS) {
            jasminInstruction.append(getIndentation()).append("getstatic ");
        }

        ClassType classType = (ClassType) destinationObject.getType();

        jasminInstruction.append(classType.getName()).append("/").append(((Operand) destinationField).getName());
        jasminInstruction.append(" ").append(JasminUtils.translateType(ancestorMethod.getOllirClass(), destinationField.getType()));

        return jasminInstruction.toString();
    }

    public String translateInstruction(PutFieldInstruction instruction, Method ancestorMethod) {
        Element destinationObject = instruction.getFirstOperand();
        Element destinationField = instruction.getSecondOperand();

        if (destinationObject.isLiteral() || destinationField.isLiteral()) {
            return "";
        }

        StringBuilder jasminInstruction = new StringBuilder();
        Element newFieldValue = instruction.getThirdOperand();

        if (destinationObject.getType().getTypeOfElement() == ElementType.OBJECTREF || destinationObject.getType().getTypeOfElement() == ElementType.THIS) {
            jasminInstruction.append(getCorrespondingLoad(destinationObject, ancestorMethod)).append("\n");
            jasminInstruction.append(getCorrespondingLoad(newFieldValue, ancestorMethod)).append("\n");
            jasminInstruction.append(getIndentation()).append("putfield ");
        } else {
            jasminInstruction.append(getIndentation()).append("putstatic ");
        }
        ClassType classType = (ClassType) destinationObject.getType();

        jasminInstruction.append(classType.getName()).append("/").append(((Operand) destinationField).getName());
        jasminInstruction.append(" ").append(JasminUtils.translateType(ancestorMethod.getOllirClass(), destinationField.getType()));

        return jasminInstruction.toString();
    }

    public String translateInstruction(AssignInstruction instruction, Method ancestorMethod) {
        Element destination = instruction.getDest();

        if (destination.isLiteral()) {
            return "";
        }

        Instruction rhs = instruction.getRhs();

        if (rhs.getInstType() == InstructionType.CALL) {
            CallInstruction callInstruction = (CallInstruction) rhs;
            if (callInstruction.getInvocationType() == CallType.NEW) {
                ElementType elementType = callInstruction.getFirstArg().getType().getTypeOfElement();
                if (elementType != ElementType.ARRAYREF) {
                    return translateInstruction(rhs, ancestorMethod);
                }
            }
        }

        return translateInstruction(rhs, ancestorMethod) + "\n" + getCorrespondingStore(destination, ancestorMethod);
    }

    public String translateInstruction(CallInstruction instruction, Method ancestorMethod) {
        StringBuilder jasminInstruction = new StringBuilder();
        StringBuilder parametersDescriptor = new StringBuilder();
        Operand caller = (Operand) instruction.getFirstArg();
        LiteralElement methodName = (LiteralElement) instruction.getSecondArg();

        CallType callType = instruction.getInvocationType();

        switch (callType) {
            case invokestatic:
            case invokevirtual:
                if (callType == CallType.invokevirtual) {
                    jasminInstruction.append(getCorrespondingLoad(caller, ancestorMethod)).append("\n");
                }

                for (Element element : instruction.getListOfOperands()) {
                    jasminInstruction.append(getCorrespondingLoad(element, ancestorMethod)).append("\n");
                    parametersDescriptor.append(JasminUtils.translateType(ancestorMethod.getOllirClass(), element.getType()));
                }

                jasminInstruction.append(getIndentation());

                if (callType == CallType.invokestatic) {
                    jasminInstruction.append("invokestatic ").append(caller.getName());
                } else {
                    jasminInstruction.append("invokevirtual ");

                    ClassType classType = (ClassType) instruction.getFirstArg().getType();
                    jasminInstruction.append(JasminUtils.getFullClassName(ancestorMethod.getOllirClass(), classType.getName()));
                }


                jasminInstruction.append(".").append(JasminUtils.removeQuotes(methodName.getLiteral()));
                jasminInstruction.append("(").append(parametersDescriptor);


                jasminInstruction.append(")").append(JasminUtils.translateType(ancestorMethod.getOllirClass(), instruction.getReturnType()));
                break;
            case invokespecial:
                if (ancestorMethod.isConstructMethod()) {
                    if (caller.getName().equals("this")) {
                        jasminInstruction.append(getCorrespondingLoad(caller, ancestorMethod)).append("\n");
                    }
                }

                for (Element element : instruction.getListOfOperands()) {
                    jasminInstruction.append(getCorrespondingLoad(element, ancestorMethod)).append("\n");
                    parametersDescriptor.append(JasminUtils.translateType(ancestorMethod.getOllirClass(), element.getType()));
                }

                jasminInstruction.append(getIndentation());

                jasminInstruction.append("invokespecial ");

                if (ancestorMethod.isConstructMethod()) {
                    if (caller.getName().equals("this")) {
                        jasminInstruction.append(ancestorMethod.getOllirClass().getSuperClass());
                    }
                } else {
                    ClassType classType = (ClassType) instruction.getFirstArg().getType();
                    jasminInstruction.append(JasminUtils.getFullClassName(ancestorMethod.getOllirClass(), classType.getName()));
                }


                jasminInstruction.append(".").append(JasminUtils.removeQuotes(methodName.getLiteral()));
                jasminInstruction.append("(").append(parametersDescriptor);


                jasminInstruction.append(")").append(JasminUtils.translateType(ancestorMethod.getOllirClass(), instruction.getReturnType()));

                if (!ancestorMethod.isConstructMethod()) {
                    jasminInstruction.append("\n").append(getCorrespondingStore(instruction.getFirstArg(), ancestorMethod));
                }
                break;
            case NEW:
                ElementType elementType = caller.getType().getTypeOfElement();
                if (elementType == ElementType.OBJECTREF || elementType == ElementType.CLASS) {
                    jasminInstruction.append(getIndentation()).append("new ").append(caller.getName()).append("\n");
                    jasminInstruction.append(getIndentation()).append("dup");
                }
                break;
        }
        return jasminInstruction.toString();
    }

    public String translateInstruction(BinaryOpInstruction instruction, Method ancestorMethod) {
        Element first = instruction.getLeftOperand();
        Element second = instruction.getRightOperand();
        Operation operation = instruction.getOperation();
        OperationType operationType = operation.getOpType();
        StringBuilder jasminInstruction = new StringBuilder();

        switch (operationType) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case LTH:
            case AND:
            case ANDB:
            case OR:
            case ORB:
            case EQ:
                String operationString;
                String loads = getCorrespondingLoad(first, ancestorMethod) + "\n"
                        + getCorrespondingLoad(second, ancestorMethod) + "\n";

                if (operationType == OperationType.ADD) {
                    if (!first.isLiteral() && second.isLiteral()) {
                        return getIinc(ancestorMethod, (LiteralElement) second, (Operand) first, jasminInstruction);
                    } else if (first.isLiteral() && !second.isLiteral()) {
                        return getIinc(ancestorMethod, (LiteralElement) first, (Operand) second, jasminInstruction);
                    } else {
                        operationString = "iadd";
                    }
                } else if (operationType == OperationType.SUB) {
                    operationString = "isub";
                } else if (operationType == OperationType.MUL) {
                    operationString = "imul";
                } else if (operationType == OperationType.DIV) {
                    operationString = "idiv";
                } else if (operationType == OperationType.LTH) {
                    try {
                        if (second.isLiteral()) {
                            LiteralElement literalElement = (LiteralElement) second;

                            int literal = Integer.parseInt(JasminUtils.removeQuotes(literalElement.getLiteral()));
                            if (literal == 0) {
                                return getCorrespondingLoad(first, ancestorMethod) + "\n";
                            } else {
                                throw new Exception("");
                            }
                        } else {
                            throw new Exception("");
                        }
                    } catch (Exception e) {
                        operationString = "";
                    }
                } else if (operationType == OperationType.AND || operationType == OperationType.ANDB) {
                    operationString = "iand";
                } else if (operationType == OperationType.OR || operationType == OperationType.ORB) {
                    operationString = "ior";
                } else {
                    operationString = "";
                }

                return loads + getIndentation() + operationString;
        }
        return "";
    }

    private String getIinc(Method ancestorMethod, LiteralElement literalElement, Operand operand, StringBuilder jasminInstruction) {
        jasminInstruction.append("iinc ").append(this.getVirtualReg(operand, ancestorMethod));
        jasminInstruction.append(" ");

        jasminInstruction.append(JasminUtils.removeQuotes(literalElement.getLiteral()));
        return getIndentation() + jasminInstruction + "\n" + getCorrespondingLoad(operand, ancestorMethod);
    }

    public String translateInstruction(ReturnInstruction instruction, Method ancestorMethod) {
        StringBuilder jasminInstruction = new StringBuilder();
        ElementType returnType = instruction.getReturnType().getTypeOfElement();

        switch (returnType) {
            case BOOLEAN:
            case INT32:
            case OBJECTREF:
            case CLASS:
            case STRING:
            case ARRAYREF:
                jasminInstruction.append(getCorrespondingLoad(instruction.getOperand(), ancestorMethod)).append("\n");

                jasminInstruction.append(getIndentation());
                if (returnType == ElementType.BOOLEAN || returnType == ElementType.INT32) {
                    jasminInstruction.append("ireturn");
                } else {
                    jasminInstruction.append("areturn");
                }

                break;
            case VOID:
                jasminInstruction.append(getIndentation()).append("return");
                break;
        }

        return jasminInstruction.toString();
    }

    private String getCorrespondingLoad(Element element, Method ancestorMethod) {
        if (element.isLiteral()) {
            LiteralElement literalElement = (LiteralElement) element;

            switch (literalElement.getType().getTypeOfElement()) {
                case INT32:
                case BOOLEAN:
                    StringBuilder jasminInstruction = new StringBuilder(getIndentation());
                    String literal = JasminUtils.removeQuotes(literalElement.getLiteral());

                    try {
                        int literalInt = Integer.parseInt(literal);

                        if (literalInt <= 5) {
                            jasminInstruction.append("iconst_").append(literal);
                        } else if (literalInt < Math.pow(2, 7)) {
                            jasminInstruction.append("bipush ").append(literal);
                        } else if (literalInt < Math.pow(2, 15)) {
                            jasminInstruction.append("sipush ").append(literal);
                        } else {
                            throw new Exception("");
                        }
                    } catch (Exception e) {
                        jasminInstruction.append("ldc ").append(literal);
                    }
                    return jasminInstruction.toString();
                default:
                    return "";
            }
        } else {
            Operand operand = (Operand) element;

            Descriptor operandDescriptor = ancestorMethod.getVarTable().get(operand.getName());
            if (operandDescriptor.getVirtualReg() < 0) {
                return "";
            }

            String spacer = operandDescriptor.getVirtualReg() < 4 ? "_" : " ";

            switch (operandDescriptor.getVarType().getTypeOfElement()) {
                case INT32:
                case BOOLEAN:
                    return getIndentation() + "iload" + spacer + operandDescriptor.getVirtualReg();
                case ARRAYREF:
                    StringBuilder jasminInstruction = new StringBuilder();

                    jasminInstruction.append(getIndentation()).append("aload").append(spacer).append(operandDescriptor.getVirtualReg());

                    return jasminInstruction.toString();
                case CLASS:
                case OBJECTREF:
                case THIS:
                case STRING:
                    return getIndentation() + "aload" + spacer + operandDescriptor.getVirtualReg();
                default:
                    return "";
            }
        }
    }

    private String getCorrespondingStore(Element element, Method ancestorMethod) {
        if (element.isLiteral()) {
            return "";
        } else {
            Operand operand = (Operand) element;

            Descriptor operandDescriptor = ancestorMethod.getVarTable().get(operand.getName());

            String spacer = operandDescriptor.getVirtualReg() < 4 ? "_" : " ";

            switch (operand.getType().getTypeOfElement()) {
                case INT32:
                case BOOLEAN:
                    return getIndentation() + "istore" + spacer + operandDescriptor.getVirtualReg();
                case CLASS:
                case OBJECTREF:
                case THIS:
                case STRING:
                    return getIndentation() + "astore" + spacer + operandDescriptor.getVirtualReg();
                case ARRAYREF:
                    StringBuilder jasminInstruction = new StringBuilder();

                    return jasminInstruction.toString();
                default:
                    return "";
            }
        }
    }

    private String getVirtualReg(Operand operand, Method ancestorMethod) {
        Descriptor operandDescriptor = ancestorMethod.getVarTable().get(operand.getName());

        return Integer.toString(operandDescriptor.getVirtualReg());
    }

    private String getIndentation() {
        return "\t".repeat(this.indentationLevel);
    }
}
