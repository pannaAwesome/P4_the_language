public class RedundantSyntaxException extends Exception {

    private static final long serialVersionUID = 1L;
    
    //###############################################################################################
    /**
     * StringType Exceptions
     */
    public RedundantSyntaxException(String id,  SimpleNode node) {
        super(ContainsSameAsEqualString(id,  node));
    }

    private static String ContainsSameAsEqualString(String id, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:";
        newMessage += "REDUNDANT SYNTAX WARNING: CONTAINS and EQUALS operations with \"" + id + "\" has been given the same value with and is unnecessary. Contains operation can be omitted";
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
        String newMessage = "WARNING:";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" will always be equal to \"" + value + "\" and is unnecessary and can be rewritten as \"" + id + "\" equal \"" + value + "\"";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String multipleValuesDefinedDecimal(String id, SimpleNode node, double firstValue, String firstConstrain, double secondValue, String secondConstrain) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" has already been defined as " + firstConstrain + firstValue + " and " + secondConstrain + secondValue + ". This can be simplified";
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
    public RedundantSyntaxException(String id, SimpleNode node, int value) {
        super(RedundantSyntaxExceptionInt(id, node, value));
    }

    private static String RedundantSyntaxExceptionInt(String id, SimpleNode node, int value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:";
        newMessage += "REDUNDANT SYNTAX WARNING: \"" + id + "\" will always be equal to \"" + value + "\" and is unnecessary and can be rewritten as \"" + id + "\" equal \"" + value + "\"";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
}
