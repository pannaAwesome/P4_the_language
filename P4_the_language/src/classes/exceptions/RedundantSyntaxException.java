package src.classes.exceptions;

import src.classes.scanner.*;
import src.classes.visitors.*;

public class RedundantSyntaxException extends Exception {

    private static final long serialVersionUID = 1L;
    
    //###############################################################################################
    /**
     * StringType Exceptions
     */
    public RedundantSyntaxException(String id, SimpleNode node, String containValue, String exactValue) {
        super(ContainsSameAsEqualString(id, node, containValue, exactValue));
    }

    public RedundantSyntaxException(String id, SimpleNode node, String containValue, String exactValue, String containsContains) {
        super(ContainsIsContainedInContainsString(id, node, containValue, exactValue, containsContains));
    }

    public RedundantSyntaxException(String id, SimpleNode node, String containValue, String exactValue, int notUsed) {
        super(exactIsContainedInContains(id, node, containValue, exactValue, notUsed));
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
    
    private static String ContainsIsContainedInContainsString(String id, SimpleNode node, String firstContain, String secondConstrain, String containsContains) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \""+id+"\" should contain both \""+firstContain+"\" and \""+secondConstrain+"\", this is redundant. Therefore one of the operations can be omitted.\n";
        newMessage += "At line: "; // ikke sikker på om det bliver contains linjen som bliver udskrevet 
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String exactIsContainedInContains(String id, SimpleNode node, String exactVal, String containsVal, int notUsed) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \""+id+"\" has been given the exact value \""+exactVal+"\" and it should contain the value \""+containsVal+"\", this is redundant. Therefore one of the operations can be omitted.\n";
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
    
    public RedundantSyntaxException(String id, SimpleNode node, double firstValue, String firstConstrain, double secondValue, String secondConstrain, String orAnd) {
        super(multipleValuesDefinedDecimal(id, node, firstValue, firstConstrain, secondValue, secondConstrain, orAnd));
    }

    public RedundantSyntaxException(String id, SimpleNode node, String firstConstrain, String secondConstrain, String orAnd, Object param){
        super(isDecimalRedundant(id, node, firstConstrain, secondConstrain, orAnd));
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

    private static String multipleValuesDefinedDecimal(String id, SimpleNode node, double firstValue, String firstConstrain, double secondValue, String secondConstrain, String orAnd) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" has been defined as " + firstConstrain + firstValue + " "+orAnd+" " + secondConstrain + secondValue + ". This can be simplified\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String isDecimalRedundant(String id, SimpleNode node, String firstConstrain, String secondConstrain, String orAnd) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" has been defined as " + firstConstrain + " "+orAnd+" " + secondConstrain + ". This can be simplified\n";
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
        super(multipleValuesDefinedInteger(id, node, firstValue, firstConstrain, secondValue, secondConstrain));
    }

    public RedundantSyntaxException(String id, SimpleNode node, int firstValue, String firstConstrain, int secondValue, String secondConstrain, String or) {
        super(multipleValuesDefinedInteger(id, node, firstValue, firstConstrain, secondValue, secondConstrain, or));
    }

    public RedundantSyntaxException(String id, SimpleNode node, String firstConstrain, String secondConstrain, Object param) {
        super(OrIsInteger(id, node, firstConstrain, secondConstrain, param));
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

    private static String multipleValuesDefinedInteger(String id, SimpleNode node, int firstValue, String firstConstrain, int secondValue, String secondConstrain) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" has been defined as " + firstConstrain + firstValue + " and " + secondConstrain + secondValue + ". This can be simplified\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String multipleValuesDefinedInteger(String id, SimpleNode node, int firstValue, String firstConstrain, int secondValue, String secondConstrain, String or) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" has been defined as " + firstConstrain + firstValue + " or " + secondConstrain + secondValue + ". This can be simplified\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String OrIsInteger(String id, SimpleNode node, String firstConstrain, String secondConstrain, Object Param) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:\n";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" has been defined as " + firstConstrain+ " or "+secondConstrain+". This can be simplified\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
}
