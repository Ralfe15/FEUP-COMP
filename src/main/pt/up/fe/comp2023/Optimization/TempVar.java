package pt.up.fe.comp2023.Optimization;

import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;

import static pt.up.fe.comp2023.Optimization.OllirUtils.getOllirType;


public class TempVar {
    private Type variableType;
    private String variableName;
    private String variableValue;
    private Type assignType = new Type("void", false);

    public TempVar(String variableName) {
        this.variableName = variableName;
    }

    public Type getVariableType() {
        return variableType;
    }

    public void setVariableType(Type variableType) {
        this.variableType = variableType;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getValue() {
        return variableValue != null ? variableValue : variableName;
    }

    public void setValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public String getSubstituteWithType() {
        String ollirType = variableType != null ? getOllirType(variableType) : "i32";
        return getSubstitute() + "." + ollirType;
    }

    public String getSubstitute() {
        return getValue() != null ? getValue() : getVariableName();
    }

    public void setAssignType(Type assignType) {
        this.assignType = assignType;
    }

    public Type getAssignType() {
        return assignType;
    }
}