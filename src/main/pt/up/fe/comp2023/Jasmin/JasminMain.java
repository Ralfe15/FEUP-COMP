package pt.up.fe.comp2023.Jasmin;

import org.specs.comp.ollir.*;
import pt.up.fe.comp.jmm.jasmin.JasminBackend;
import pt.up.fe.comp.jmm.jasmin.JasminResult;
import pt.up.fe.comp2023.Jasmin.JasminMethodBuilder;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp.jmm.report.Report;

import java.util.ArrayList;
import java.util.List;

public class JasminMain implements JasminBackend {
    List<Report> reports = new ArrayList<>();

    @Override
    public JasminResult toJasmin(OllirResult ollirResult) {
        StringBuilder jasminCode = new StringBuilder();

        ClassUnit ollirClass = ollirResult.getOllirClass();
        jasminCode.append(getJasminClass(ollirClass)).append("\n");
        jasminCode.append(getJasminSuper(ollirClass)).append("\n");
        jasminCode.append("\n");

        jasminCode.append(getJasminFields(ollirClass)).append("\n");

        jasminCode.append(getJasminMethods(ollirClass));

        System.out.println(jasminCode);

        return new JasminResult(ollirResult, jasminCode.toString(), this.reports);
    }

    private String getJasminClass(ClassUnit ollirClass) {
        StringBuilder classDirective = new StringBuilder(".class ");

        if (ollirClass.isFinalClass()) {
            classDirective.append("final ");
        }

        if (ollirClass.isStaticClass()) {
            classDirective.append("static ");
        }

        if (ollirClass.getClassAccessModifier().toString().equals("DEFAULT")) {
            classDirective.append("public ");
        } else {
            classDirective.append(ollirClass.getClassAccessModifier().toString()).append(" ");
        }

        String ollirPackage = ollirClass.getPackage();
        if (ollirPackage != null) {
            classDirective.append(ollirPackage).append("/");
        }

        classDirective.append(ollirClass.getClassName());

        return classDirective.toString();
    }

    private String getJasminSuper(ClassUnit ollirClass) {
        if (ollirClass.getSuperClass() == null) {
            ollirClass.setSuperClass("java/lang/Object");
        }
        return ".super " + ollirClass.getSuperClass();
    }

    private String getJasminFields(ClassUnit ollirClass) {
        StringBuilder jasminFields = new StringBuilder();

        for (Field field: ollirClass.getFields()) {
            jasminFields.append(".field ");

            if (field.getFieldAccessModifier().toString().equals("DEFAULT")) {
                jasminFields.append("private ");
            } else {
                jasminFields.append(field.getFieldAccessModifier().toString().toLowerCase()).append(" ");
            }

            if (field.isFinalField()) {
                jasminFields.append("final ");
            }

            if (field.isStaticField()) {
                jasminFields.append("static ");
            }

            jasminFields.append(field.getFieldName()).append(" ");
            jasminFields.append(JasminUtils.translateType(ollirClass, field.getFieldType()));

            jasminFields.append("\n");
        }

        return jasminFields.toString();
    }

    private String getJasminMethods(ClassUnit ollirClass) {
        StringBuilder jasminMethods = new StringBuilder();
        JasminMethodBuilder methodBuilder = new JasminMethodBuilder(this.reports);

        for(Method method: ollirClass.getMethods()) {
            methodBuilder.setMethod(method);

            jasminMethods.append(methodBuilder.getJasminMethod());

            jasminMethods.append("\n");
        }

        if (this.reports != methodBuilder.getReports()) {
            this.reports = methodBuilder.getReports();
        }

        return jasminMethods.toString();
    }
}
