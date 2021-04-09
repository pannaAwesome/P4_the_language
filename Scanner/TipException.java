public class TipException extends Exception {

    private static final long serialVersionUID = 1L;
    public TipException(){
        super();
    }
    public String ReadabilityTip(SimpleNode node, String id, String ruleName){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "TIP:";
        newMessage += "READABILITY ERROR: Long OR-chains can be rewritten as part rules in \"" + ruleName + "\"";
        newMessage += "At line: ";
        newMessage += "For Example: ...";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
    
}
