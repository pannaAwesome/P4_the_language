package src.classes.exceptions;

import src.classes.scanner.*;
import src.classes.visitors.*;

public class RedundantSyntaxException extends Exception {

    private static final long serialVersionUID = 1L;
    
    //###############################################################################################
    /**
     * StringType Exceptions
     */
    public RedundantSyntaxException(String id,  SimpleNode node, String containValue, String exactValue) {
        super(ContainsSameAsEqualString(id,  node, containValue, exactValue));
    }

    private static String ContainsSameAsEqualString(String id, SimpleNode node, String containValue, String exactValue) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" has been given the exact value \"" + exactValue + "\" and it should contain the value \"" + containValue + "\", this is redundant. Therefore one of the operations can be omitted.\n";
        newMessage += "At line: "; // ikke sikker på om det bliver contains linjen som bliver udskrevet 
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
    
    //###############################################################################################
    /**
     * DecimalType Exceptions
     */
    public RedundantSyntaxException(String id,  SimpleNode node, double value) {
        super(RedundantSyntaxExceptionDecimal(id,  node, value));
    }
    
    public RedundantSyntaxException(String id, SimpleNode node, double firstValue, String firstConstrain, double secondValue, String secondConstrain) {
        super(multipleValuesDefinedDecimal(id, node, firstValue, firstConstrain, secondValue, secondConstrain));
    }

    private static String RedundantSyntaxExceptionDecimal(String id, SimpleNode node, double value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" will always be equal to " + value + " and can be rewritten as \"" + id + " = " + value + "\"\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String multipleValuesDefinedDecimal(String id, SimpleNode node, double firstValue, String firstConstrain, double secondValue, String secondConstrain) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" has already been defined as " + firstConstrain + firstValue + " and " + secondConstrain + secondValue + ". This can be simplified\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
    
    //###############################################################################################
    /**
     * IntegerType Exceptions
     */
    public RedundantSyntaxException(String id,  SimpleNode node, int value) {
        super(RedundantSyntaxExceptionDecimal(id,  node, value));
    }
    
    public RedundantSyntaxException(String id, SimpleNode node, int firstValue, String firstConstrain, int secondValue, String secondConstrain) {
        super(multipleValuesDefinedDecimal(id, node, firstValue, firstConstrain, secondValue, secondConstrain));
    }

    private static String RedundantSyntaxExceptionDecimal(String id, SimpleNode node, int value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" will always be equal to " + value + " and can be rewritten as \"" + id + " = " + value + "\"\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String multipleValuesDefinedDecimal(String id, SimpleNode node, int firstValue, String firstConstrain, int secondValue, String secondConstrain) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" has already been defined as " + firstConstrain + firstValue + " and " + secondConstrain + secondValue + ". This can be simplified\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
}
