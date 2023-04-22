package pt.up.fe.comp2023.Analysis.visitors;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2023.Analysis.types.ClassInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImportVisitor extends AJmmVisitor<List<String>, Boolean> {
    @Override
    protected void buildVisitor() {
        addVisit("Start", this::start);
        addVisit("Program", this::visitProgram);
        addVisit("ImportDeclaration", this::visitImportDeclaration);
        this.setDefaultVisit(this::start);

    }

    public Boolean start(JmmNode start, List<String> imports) {
        for (JmmNode child : start.getChildren()) {
            if (child.getKind().equals("ImportDeclaration")) {
                visit(child, imports);
            }
        }
        return true;
    }
    private Boolean visitProgram(JmmNode jmmNode, List<String> unused) {
        visitAllChildren(jmmNode, unused);
        return true;
    }

    private Boolean visitImportDeclaration(JmmNode importDeclaration, List<String> imports) {
        List<String> path = importDeclaration.getObjectAsList("value", String.class);
        String result = String.join(".", path);
        imports.add(result);
        return true;
    }
}
