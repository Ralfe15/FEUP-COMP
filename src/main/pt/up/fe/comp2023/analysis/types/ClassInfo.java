package pt.up.fe.comp2023.analysis.types;

import pt.up.fe.comp.jmm.analysis.table.Symbol;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
    String name;
    String superName;
    private final List<Symbol> fields = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getSuperName() {
        return superName;
    }

    public List<Symbol> getFields() {
        return fields;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }
}
