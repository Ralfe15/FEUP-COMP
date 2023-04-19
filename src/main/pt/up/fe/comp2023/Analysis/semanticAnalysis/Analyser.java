package pt.up.fe.comp2023.Analysis.semanticAnalysis;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.ReportType;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2023.Analysis.symbolTable.MySymbolTable;
import pt.up.fe.comp2023.Analysis.types.ClassInfo;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.analysis.table.Type;
import java.util.ArrayList;
import java.util.List;

public class Analyser extends AJmmVisitor<MySymbolTable, Boolean> {

    @Override
    protected void buildVisitor() {
        addVisit("Start", this::start);
        addVisit("ClassDeclaration", this::visitSymbolTable);
        addVisit("Array", this::visitArray);
        setDefaultVisit((node, classSignature) -> true);
    }


    public Boolean start(JmmNode start, MySymbolTable obj) {
        for (JmmNode child : start.getChildren()) {
            visit(child, obj);
        }
        return true;
    }
    public Boolean visitSymbolTable(JmmNode jmmNode,MySymbolTable symbolTable) {
        if (visit(jmmNode.getJmmChild(0))) {
            for (String method : symbolTable.getMethods()){
                List<Symbol> s = symbolTable.getLocalVariables(method);
                for (Symbol symbol: s){
                    if (symbol != null && !symbol.getType().isArray()) {
                        symbolTable.getReports().add(new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,0,0,
                                String.format("Tried to access a as an array but it's type is ")
                        ));
                    }

                }

            }

        }
        return false;
    }

    public Boolean visitArray(JmmNode jmmNode,MySymbolTable table) {
        if (!table.hasSymbol(jmmNode.getKind(), jmmNode.get("int"))) {
            table.getReports().add(new Report(
                    ReportType.ERROR,
                    Stage.SEMANTIC,
                    Integer.parseInt(jmmNode.get("line")),
                    Integer.parseInt(jmmNode.get("column")),
                    "Variable " + jmmNode.get("int") + " wasn't found in this scope"
            ));
            return false;
        }
        return true;
    }

}
