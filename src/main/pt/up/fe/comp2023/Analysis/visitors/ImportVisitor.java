package pt.up.fe.comp2023.Analysis.visitors;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;

import java.util.List;
import java.util.stream.Collectors;

public class ImportVisitor extends AJmmVisitor<List<String>, Boolean> {
    @Override
    protected void buildVisitor() {
        addVisit("Start", this::start);
        addVisit("ImportDeclaration", this::visitImportDeclaration);
    }

    public Boolean start(JmmNode start, List<String> imports) {
        for (JmmNode child : start.getChildren()) {
            if (child.getKind().equals("ImportDeclaration")) {
                visit(child, imports);
            }
        }
        return true;
    }

    private Boolean visitImportDeclaration(JmmNode importDeclaration, List<String> imports) {
        String path = importDeclaration.get("value");
        path = path.replaceAll("^\\[|]$", "").replaceAll(", ", ".");
        imports.add(path);
        return true;
    }
}