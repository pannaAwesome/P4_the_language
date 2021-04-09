public class ConstraintException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConstrainException(String id, SimpleNode node){ 
        super(constrainException(id, node));
    }

    public ConstrainException(String id, SimpleNode node, String typeValue, int value){
        super(constrainException(id, node, typeValue, value));
    }

    public ConstrainException(String id, SimpleNode node, String typeValue, double value){
        super(constrainException(id, node, typeValue, value));
    }

    public ConstrainException(String id, SimpleNode node, String typeValue, SimpleNode value){
        super(constrainException(id, node, typeValue, value));
    }

    private static String constrainException(String id, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "CONSTRAINT ERROR: \"" + id +"\" cannot have a max value, which is smaller than the min value";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += "\n"
        return newMessage;
    }
    private static String constrainException(String id, SimpleNode node, String typeValue, int value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage += "ERROR:";
        newMessage += "CONSTRAINT ERROR: Cannot redefine " + typeValue + " value for \"" + id +"\" as it is already defined as " + value;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += "\n";
        return newMessage;
    }
    private static String constrainException(String id, SimpleNode node, String typeValue, double value) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage += "ERROR:";
        newMessage += "CONSTRAINT ERROR: Cannot redefine " + typeValue + " value for \"" + id +"\" as it is already defined as " + value;
        newMessage += "At line: ";
        newMessage += ppv, null;
        newMessage += "\n";
        return newMessage;
    }
    private static String constrainException(String id, StringType newType, StringType type, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage += "ERROR:";
        newMessage += "CONSTRAINT ERROR: \"" + id +"\" is already \"" + type.toString() + "\", and cannot be \"" + newType.toString() + "\"";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += "\n";
        return newMessage;
    }
}
