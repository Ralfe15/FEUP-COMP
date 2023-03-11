package pt.up.fe.comp2023.Analysis.types;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;

import java.util.ArrayList;
import java.util.List;
public class MethodInfo {
    final Type retType;
    final List<Symbol> args;

    public MethodInfo(Type type, List<Symbol> args) {
        this.retType = type;
        this.args = new ArrayList<>(args);
    }

    public Type getRetType() {
        return retType;
    }

    public List<Symbol> getArgs() {
        return args;
    }
}
