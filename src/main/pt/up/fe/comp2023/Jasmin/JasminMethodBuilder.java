package pt.up.fe.comp2023.Jasmin;

import org.specs.comp.ollir.*;
import pt.up.fe.comp.jmm.report.Report;

import java.util.List;
import java.util.Map;

public class JasminMethodBuilder {
    private Method method;
    public List<Report> reports;

    public JasminMethodBuilder(List<Report> reports) {
        this.reports = reports;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getJasminMethod() {
        StringBuilder methodDefinition = new StringBuilder();
        boolean hasReturn = false;

        if (method.isConstructMethod()) {
            method.setMethodName("<init>");
        }

        methodDefinition.append(getHeader()).append("\n");

        methodDefinition.append("\t.limit stack 99").append("\n"); // TODO: CP3 - set limits
        methodDefinition.append("\t.limit locals 99").append("\n"); // TODO: CP3 - set limits

        StringBuilder methodInstructions = new StringBuilder();

        this.method.buildVarTable();
        InstructionTranslator instructionTranslator = new InstructionTranslator(this.reports);

        for (Instruction instruction: method.getInstructions()) {
            if (!hasReturn && instruction.getInstType() == InstructionType.RETURN) {
                hasReturn = true;
            }

            for (Map.Entry<String, Instruction> entry: method.getLabels().entrySet()) {
                if (entry.getValue().equals(instruction)) {
                    methodInstructions.append(entry.getKey()).append(":").append("\n");
                }
            }
            methodInstructions.append(instructionTranslator.translateInstruction(instruction, method)).append("\n");

            if (instruction.getInstType() == InstructionType.CALL) {
                if (((CallInstruction) instruction).getReturnType().getTypeOfElement() != ElementType.VOID)
                    methodInstructions.append("\tpop\n");
            }
        }

        if (this.reports != instructionTranslator.getReports()) {
            this.reports = instructionTranslator.getReports();
        }

        methodDefinition.append(methodInstructions);

        if (!hasReturn) {
            methodDefinition.append("\t").append("return").append("\n");
        }

        methodDefinition.append(".end method\n");

        return methodDefinition.toString();
    }

    private String getHeader() {
        StringBuilder methodHeader = new StringBuilder(".method ");

        if (method.getMethodAccessModifier().toString().equals("DEFAULT")) {
            methodHeader.append("public ");
        } else {
            methodHeader.append(method.getMethodAccessModifier().toString().toLowerCase()).append(" ");
        }

        if (method.isFinalMethod()) {
            methodHeader.append("final ");
        }

        if (method.isStaticMethod()) {
            methodHeader.append("static ");
        }

        methodHeader.append(method.getMethodName()).append(getDescriptor());

        return methodHeader.toString();
    }

    private String getDescriptor() {
        StringBuilder descriptor = new StringBuilder("(");

        for (Element parameter: method.getParams()) {
            descriptor.append(JasminUtils.translateType(method.getOllirClass(), parameter.getType()));
        }

        descriptor.append(")").append(JasminUtils.translateType(method.getOllirClass(), method.getReturnType()));

        return descriptor.toString();
    }

    public List<Report> getReports() {
        return this.reports;
    }
}
