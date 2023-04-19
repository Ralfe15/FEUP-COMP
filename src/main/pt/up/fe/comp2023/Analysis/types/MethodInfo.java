package pt.up.fe.comp2023.Analysis.types;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodInfo {
    final Type retType;
    final List<Symbol> args;
    final List<Symbol> localVariables;

    public MethodInfo(Type type, List<Symbol> args, List<Symbol> localVariables) {
        this.retType = type;
        this.args = new ArrayList<>(args);
        this.localVariables = new ArrayList<>(localVariables);
    }

    public Type getRetType() {
        return retType;
    }

    public List<Symbol> getArgs() {
        return args;
    }

    public List<Symbol> getLocalVariables() {
        return localVariables;
    }


}
