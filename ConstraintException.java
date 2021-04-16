public class ConstraintException extends Exception {

    private static final long serialVersionUID = 1L;

    //###############################################################################################
    /**
     * StringType Exceptions
     */
    public ConstraintException(String id, String currentVal, String newVal, SimpleNode node){
        super(stringConstrain(id, currentVal, newVal, node));
    }

    public ConstraintException(String id, String firstConstrain, String firstValues, String secondConstrain, String secondValues, SimpleNode node){
        super(stringRedefine(id, firstConstrain, firstValues, secondConstrain, secondValues, node));
    }

    private static String stringConstrain(String id, String currentVal, String newVal, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: \"" + id +"\" is already \"" + currentVal + "\", and can therefore not be \"" + newVal + "\"\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String stringRedefine(String id, String firstConstrain, String firstValues, String secondConstrain, String secondValues, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: \"" + id +"\" is already declared " + firstConstrain + " \"" + firstValues + "\". Cannot change \"" + id + "\" " + secondConstrain + " \"" + secondValues + "\"\n";
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

    public ConstraintException(String id, SimpleNode node, String wrongConstrain, double wrongValue, String value){
        super(equalOtherConstrainDecimal(id, node, wrongConstrain, wrongValue, value));
    }

    public ConstraintException(String id, double max, double min){
        super(minBiggerThanMaxConstrainDecimal(id, max, min));
    }

    private static String constrainDecimal(String id, SimpleNode node, String firstConstrain, double wrongValue, String secondConstrain, double value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: Cannot redefine \"" + id + "\" to be " + firstConstrain + " " + wrongValue + " because it is already defined as " + secondConstrain + " " + value + "\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String equalConstrainDecimal(String id, SimpleNode node, double wrongValue, String value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: Cannot redefine \"" + id + "\" to be " + wrongValue + " because it is already defined as " + value + "\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String equalOtherConstrainDecimal(String id, SimpleNode node, String wrongConstrain, double wrongValue, String value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: Cannot redefine \"" + id + "\" to be " + wrongConstrain + wrongValue + " because it is already defined as " + value + "\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String minBiggerThanMaxConstrainDecimal(String id, double max, double min) {
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: \"" + id + "\" cannot have a max value of " + max + ", which is smaller than the min value of " + min+ "\n";
        newMessage += "\n";
        return newMessage;
    }

    //###############################################################################################
    /**
     * IntegerType Exceptions
     */
    public ConstraintException(String id, SimpleNode node, String firstConstrain, int wrongValue, String secondConstrain, int value){
        super(constrainInt(id, node, firstConstrain, wrongValue, secondConstrain, value));
    }

    public ConstraintException(String id, SimpleNode node, int wrongValue, String value){
        super(equalConstrainInt(id, node, wrongValue, value));
    }

    public ConstraintException(String id, SimpleNode node, String wrongConstrain, int wrongValue, String value){
        super(equalOtherConstrainInt(id, node, wrongConstrain, wrongValue, value));
    }

    public ConstraintException(String id, int max, int min){
        super(minBiggerThanMaxConstrainInt(id, max, min));
    }
    
    private static String constrainInt(String id, SimpleNode node, String firstConstrain, int wrongValue, String secondConstrain, int value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: Cannot redefine \"" + id + "\" to be " + firstConstrain + " " + wrongValue + " because it is already defined as " + secondConstrain + " " + value + "\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String equalConstrainInt(String id, SimpleNode node, int wrongValue, String value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: Cannot redefine \"" + id + "\" to be " + wrongValue + " because it is already defined as " + value + "\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String equalOtherConstrainInt(String id, SimpleNode node, String wrongConstrain, int wrongValue, String value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: Cannot redefine \"" + id + "\" to be " + wrongConstrain + wrongValue + " because it is already defined as " + value + "\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String minBiggerThanMaxConstrainInt(String id, double max, int min) {
        String newMessage = "ERROR:\n";
        newMessage += "CONSTRAINT ERROR: \"" + id + "\" cannot have a max value of " + max + ", which is smaller than the min value of " + min+ "\n";
        newMessage += "\n";
        return newMessage;
    }
}
