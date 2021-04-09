public class RedundantSyntaxException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public RedundantSyntaxException(String id, STVal types, BaseType newType, SimpleNode node, int value) {
        super(RedundantSyntaxExceptionString(id, types, newType, node, value));
    }
    public RedundantSyntaxException(String id, STVal types, BaseType newType, SimpleNode node, double value) {
        super(RedundantSyntaxExceptionString(id, types, newType, node, value));
    }

    private static <T> String RedundantSyntaxExceptionString(String id, STVal types, BaseType newType, SimpleNode node, <T> value) {
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
