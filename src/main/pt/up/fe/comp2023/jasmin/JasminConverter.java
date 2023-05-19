package pt.up.fe.comp2023.jasmin;

import org.specs.comp.ollir.*;
import pt.up.fe.comp.jmm.jasmin.JasminBackend;
import pt.up.fe.comp.jmm.jasmin.JasminResult;
import pt.up.fe.comp.jmm.ollir.OllirResult;

import java.util.Collections;

public class JasminConverter implements JasminBackend {
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

        return new JasminResult(ollirResult, jasminCode.toString(), Collections.emptyList());
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

        if (ollirClass.getPackage() != null) {
            classDirective.append(ollirClass.getPackage()).append("/");
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
        StringBuilder fieldDefinitions = new StringBuilder();

        for (Field field: ollirClass.getFields()) {
            fieldDefinitions.append(".field ");

            if (field.getFieldAccessModifier().toString().equals("DEFAULT")) {
                fieldDefinitions.append("private ");
            } else {
                fieldDefinitions.append(field.getFieldAccessModifier().toString().toLowerCase()).append(" ");
            }

            if (field.isFinalField()) {
                fieldDefinitions.append("final ");
            }

            if (field.isStaticField()) {
                fieldDefinitions.append("static ");
            }

            fieldDefinitions.append(field.getFieldName()).append(" ");
            fieldDefinitions.append(JasminUtils.translateType(ollirClass, field.getFieldType()));

            fieldDefinitions.append("\n");
        }

        return fieldDefinitions.toString();
    }

    private String getJasminMethods(ClassUnit ollirClass) {
        StringBuilder methodDefinitions = new StringBuilder();
        JasminMethodBuilder methodBuilder = new JasminMethodBuilder();

        for(Method method: ollirClass.getMethods()) {
            methodBuilder.setMethod(method);

            methodDefinitions.append(methodBuilder.getMethodDefinition());

            methodDefinitions.append("\n");
        }

        return methodDefinitions.toString();
    }
}