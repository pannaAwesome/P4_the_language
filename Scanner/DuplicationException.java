public class DuplicationException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DuplicationException(String id, SimpleNode node, String definedAs){
        super(duplicationException(id, node, definedAs));
    }

    public DuplicationException(String id, SimpleNode node, String constrain, double value){
        super(constrainDuplicationDecimal(id, node, constrain, value));
    }

    public DuplicationException(String id, SimpleNode node, double value){
        super(equalDuplicationDecimal(id, node, value));
    }
    public DuplicationException(String id, SimpleNode node, String constrain, int value){
        super(constrainDuplicationInteger(id, node, constrain, value));
    }

    public DuplicationException(String id, SimpleNode node, int value){
        super(equalDuplicationInteger(id, node, value));
    }


    private static String duplicationException(String id, SimpleNode node, String definedAs){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:";
        newMessage += "DUPLICATE WARNING: \"" + id + "\" has already been defined to "+definedAs;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String constrainDuplicationDecimal(String id, SimpleNode node, String constrain, double value){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:";
        newMessage += "DUPLICATE WARNING: \"" + id + "\" has already been defined to " + constrain + " " + value;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String equalDuplicationDecimal(String id, SimpleNode node, double value){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:";
        newMessage += "DUPLICATE WARNING: \"" + id + "\" has already been defined as " + value;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
    private static String constrainDuplicationInteger(String id, SimpleNode node, String constrain, int value){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:";
        newMessage += "DUPLICATE WARNING: \"" + id + "\" has already been defined to " + constrain + " " + value;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String equalDuplicationInteger(String id, SimpleNode node, int value){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:";
        newMessage += "DUPLICATE WARNING: \"" + id + "\" has already been defined as " + value;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
}
