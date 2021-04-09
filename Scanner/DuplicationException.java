public class DuplicationException extends Exception{
    public DuplicationException(){
        super(duplicationException)
    }

    private static duplicationException(String id, SimpleNode node, String definedAs){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "WARNING:";
        newMessage += "DUPLICATE WARNING: \"" + id + "\" has already been defined to be "+definedAs;
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
}
