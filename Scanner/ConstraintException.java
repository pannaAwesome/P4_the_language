public class ConstraintException extends Exception {

    private static final long serialVersionUID = 1L;

    //###############################################################################################
    /**
     * StringType Exceptions
     */
    public ConstraintException(String id, String currentVal, String newVal, SimpleNode node){
        super(stringConstrain(id, currentVal, newVal, node));
    }

    public ConstraintException(String id, String exactString, SimpleNode node){
        super(stringContainsRedefinedToExact(id, exactString, node));
    }

    private static String stringConstrain(String id, String currentVal, String newVal, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "CONSTRAINT ERROR: \"" + id +"\" is already \"" + currentVal.toString() + "\", and cannot be \"" + newVal.toString() + "\"";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String stringContainsRedefinedToExact(String id, String exactString, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "CONSTRAINT ERROR: \"" + id +"\" is already defined to contains different values. Cannot change \"" + id + "\""+ "to be exact \"" + exactString + "\"";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    //###############################################################################################
    /**
     * DecimalType Exceptions
     */
    public ConstraintException(String id, SimpleNode node, String firstConstrain, double wrongValue, String secondConstrain, double value){
        super(constrainDecimal(id, node, firstConstrain, wrongValue, secondConstrain, value));
    }

    public ConstraintException(String id, SimpleNode node, double wrongValue, String value){
        super(equalConstrainDecimal(id, node, wrongValue, value));
    }

    public ConstraintException(String id, double max, double min){
        super(minBiggerThanMaxConstrainDecimal(id, max, min));
    }

    private static String constrainDecimal(String id, SimpleNode node, String firstConstrain, double wrongValue, String secondConstrain, double value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "CONSTRAINT ERROR: Cannot redefine \"" + id + "\" to be " + firstConstrain + " " + wrongValue + "because it is already defined as " + secondConstrain + " " + value;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String equalConstrainDecimal(String id, SimpleNode node, double wrongValue, String value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "CONSTRAINT ERROR: Cannot redefine \"" + id + "\" to be " + + wrongValue + "because it is already defined as " + value;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String minBiggerThanMaxConstrainDecimal(String id, double max, double min) {
        String newMessage = "ERROR:";
        newMessage += "CONSTRAINT ERROR: \"" + id + "\" cannot have a max value of " + max + ", which is smaller than the min value of " + min;
        newMessage += "\n";
        return newMessage;
    }

    //###############################################################################################
    /**
     * IntegerType Exceptions
     */
    private static String constraintExceptionInt(String id, SimpleNode node, String typeValue, int value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "CONSTRAINT ERROR: Cannot redefine " + typeValue + " value for \"" + id +"\" as it is already defined as " + value;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
}
