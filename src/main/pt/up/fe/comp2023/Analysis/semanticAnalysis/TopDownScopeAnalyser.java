package pt.up.fe.comp2023.Analysis.semanticAnalysis;

import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;

import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;
import java.util.List;
import pt.up.fe.comp.jmm.analysis.JmmAnalysis;
import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp2023.Analysis.semanticAnalysis.Analyser;
import pt.up.fe.comp2023.Analysis.visitors.ClassVisitor;

public abstract class TopDownScopeAnalyser extends AJmmVisitor<String, Boolean> implements SemanticAnalyser {
    protected MySymbolTable symbolTable;
    protected List<Report> reports;
    @Override
    protected void buildVisitor() {
        addVisit("Start", this::visitStart);
        addVisit("ClassDeclaration", this::visitClassDeclaration);
        addVisit("MainMethodDeclaration", this::visitMainMethodDecl);
        addVisit("MethodDeclaration", this::visitMethodDecl);
        addVisit("Variable", (node, method) -> false);
        setDefaultVisit(this::defaultVisitor);
    }


    public TopDownScopeAnalyser(MySymbolTable symbolTable, List<Report> reports) {
        this.symbolTable = symbolTable;
        this.reports = reports;
    }

    private Boolean defaultVisitor(JmmNode node, String scope) {
        if (scope == null) {
            return false;
        }
        for (var child: node.getChildren()) {
            visit(child, scope);
        }
        return true;
    }

    private Boolean visitStart(JmmNode node, String dummy) {
        for (var child: node.getChildren()) {
            visit(child);
        }
        return true;
    }

    private Boolean visitClassDeclaration(JmmNode node, String dummy) {
        for (var child: node.getChildren()) {
            visit(child);
        }
        return true;
    }

    private Boolean visitMainMethodDecl(JmmNode node, String dummy) {
        visit(node.getJmmChild(1), "main");
        return true;
    }

    private Boolean visitMethodDecl(JmmNode node, String dummy) {
        visit(node.getJmmChild(3), node.getJmmChild(1).get("image"));
        return true;
    }

    @Override
    public List<Report> getReports() {
        return reports;
    }
}