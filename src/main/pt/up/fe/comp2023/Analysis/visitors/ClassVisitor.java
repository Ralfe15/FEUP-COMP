package pt.up.fe.comp2023.Analysis.visitors;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2023.Analysis.types.ClassInfo;

import java.util.Optional;

public class ClassVisitor extends AJmmVisitor<ClassInfo, Boolean> {
    @Override
    protected void buildVisitor() {
        addVisit("Start", this::start);
        addVisit("ClassDeclaration", this::visitClassDeclaration);
        addVisit("VarDeclaration", this::visitVarDeclaration);
        setDefaultVisit((node, classSignature) -> true);
    }

    public Boolean start(JmmNode startNode, ClassInfo classSignature) {
        for (JmmNode child : startNode.getChildren()) {
            visit(child, classSignature);
        }
        return true;
    }

    private Boolean visitClassDeclaration(JmmNode classNode, ClassInfo classSignature) {
        classSignature.setName(classNode.get("name"));
        Optional<String> superName = classNode.getOptional("extension");
        superName.ifPresent(classSignature::setSuperName);

        for (JmmNode child : classNode.getChildren()) {
            visit(child, classSignature);
        }
        return true;
    }

    private Boolean visitVarDeclaration(JmmNode varDeclarationNode, ClassInfo classSignature) {
        JmmNode typeNode = varDeclarationNode.getJmmChild(0);
        String name = varDeclarationNode.get("varName");
        Type fieldType = new Type(typeNode.get("rawType"), typeNode.getKind().equals("IntArrayType"));
        classSignature.getFields().add(new Symbol(fieldType, name));
        return true;
    }

}
