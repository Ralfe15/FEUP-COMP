package pt.up.fe.comp2023.jasmin;

import org.specs.comp.ollir.*;

public class JasminUtils {
    public static String translateType(Type type, ClassUnit ollirClass) {
        ElementType elementType = type.getTypeOfElement();

        return switch(elementType) {
            case INT32 -> "I";
            case BOOLEAN -> "Z";
            case STRING -> "Ljava/lang/String;";
            case THIS -> "this";
            case VOID -> "V";
            case ARRAYREF -> "[" + translateType(((ArrayType) type).getElementType(), ollirClass);
            case OBJECTREF, CLASS -> "L" + getFullClassName(ollirClass, ((ClassType) type).getName()) + ";";
        };
    }

    public static String getFullClassName(ClassUnit ollirClass, String className) {
        if (ollirClass.isImportedClass(className)) {
            for (String fullImport : ollirClass.getImports()) {
                int lastSeparatorIndex = className.lastIndexOf(".");

                if (lastSeparatorIndex < 0 && fullImport.equals(className)) {
                    return className;
                } else if (fullImport.substring(lastSeparatorIndex + 1).equals(className)) {
                    return fullImport;
                }
            }
        }

        return className;
    }

    public static String removeQuotes(String literal) {
        if (literal.charAt(0) != '"') {
            return literal;
        }
        return literal.length() == 1 ? literal : literal.substring(1, literal.length() - 1);
    }
}