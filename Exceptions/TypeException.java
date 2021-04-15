package Exceptions;
import Types.*;
import Exceptions.*;
import Visitors.*;
import jjtNode.*;

public class TypeException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TypeException(String id, STVal types, BaseType newType, SimpleNode node) {
        super(typeExceptionRedeclaration(id, types, newType, node));
    }

    public TypeException(String id, SimpleNode node, String choice) {
        super(typeExceptionNumOrSA(id, node, choice));
    }

    public TypeException(String id, String type, SimpleNode node) {
        super(simpleRedeclaration(id, type, node));
    }

    private static String typeExceptionRedeclaration(String id, STVal types, BaseType newType, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "TYPE ERROR: \"" + id + "\" has already been declared to be of ";
        newMessage += types.toString("all");
        newMessage += ", cannot redeclare identifier as " + newType.toString()+"\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String simpleRedeclaration(String id, String type, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "TYPE ERROR: \"" + id + "\" is not of type " + type +"\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String typeExceptionNumOrSA(String id, SimpleNode node, String choice){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        if (choice.equals("num")){
            newMessage += "TYPE ERROR: Cannot use numeric operators +, -, *, / with non-numeric coloumn "+id+"\n";
            newMessage += "At line: ";
            node.jjtAccept(ppv, null);
            newMessage += ppv.print;
            newMessage += "\n";
            return newMessage;
        } else if (choice.equals("sum") || choice.equals("avg")) {
            newMessage += "TYPE ERROR: Can only use " + choice + " coloumns of numeric or empty values\n";
            newMessage += "At line: ";
            node.jjtAccept(ppv, null);
            newMessage += ppv.print;
            newMessage += "\n";
            return newMessage;
        } else {
            throw new IllegalArgumentException("TypeException was not given \"sum\" or \"num\"");
        }
    }
}
