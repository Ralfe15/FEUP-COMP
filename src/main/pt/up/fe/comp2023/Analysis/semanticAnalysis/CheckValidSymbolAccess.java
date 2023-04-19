package pt.up.fe.comp2023.Analysis.semanticAnalysis;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.ReportType;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;
import pt.up.fe.comp2023.Analysis.types.ClassInfo;

import java.util.List;

public class CheckValidSymbolAccess extends TopDownScopeAnalyser {
    public CheckValidSymbolAccess(MySymbolTable symbolTable, List<Report> reports) {
        super(symbolTable, reports);
        addVisit("Start", this::start);
        addVisit("VarName", this::visitVarName);
        addVisit("ArrAccess", this::visitArrayAccess);
    }
    @Override
    protected void buildVisitor() {
        addVisit("VarName", this::visitVarName);
        addVisit("ArrAccess", this::visitArrayAccess);
    }

    public Boolean start(JmmNode startNode, String scope) {
        for (JmmNode child : startNode.getChildren()) {
            visit(child, scope);
        }
        return true;
    }

    private Boolean visitArrayAccess(JmmNode node, String scope) {
        //Node is at the table
        if (visit(node.getJmmChild(0), scope)) {

            List<Symbol> symbolList = symbolTable.getLocalVariables("A");
            for (Symbol symbol : symbolList){
                if (symbol != null && !symbol.getType().isArray()) {
                    reports.add(new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(node.get("line")),
                            Integer.parseInt(node.get("column")),
                            String.format("Tried to access '%s' as an array but it's type is '%s'", node.getJmmChild(0).get("image"), symbol.getType().toString())
                    ));
                }
            }

        }
        return false;
    }

    private Boolean visitVarName(JmmNode node, String scope) {

        if (!symbolTable.hasSymbol(scope, node.get("int"))) {
            reports.add(new Report(
                    ReportType.ERROR,
                    Stage.SEMANTIC,
                    Integer.parseInt(node.get("line")),
                    Integer.parseInt(node.get("column")),
                    "Variable " + node.get("int") + " wasn't found in this scope"
            ));
            return false;
        }
        return true;
    }

    @Override
    public List<Report> getReports() {
        return reports;
    }




}