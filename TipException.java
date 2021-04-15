public class TipException extends Exception {

    private static final long serialVersionUID = 1L;

    public TipException(SimpleNode node, String id, String ruleName, boolean isColRule, boolean isPartRule){
        super(ReadabilityTip(node, id, ruleName, isColRule, isPartRule));
    }

    private static String ReadabilityTip(SimpleNode node, String id, String ruleName, boolean isColRule, boolean isPartRule){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "TIP:\n";
        newMessage += "READABILITY ERROR: Long OR-chains can be rewritten as part rules in \"" + ruleName + "\"\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\nFor Example: \n";
        newMessage += (isPartRule ? PartRuleSplit(node) : RuleSplit(node, isColRule)) +"\n";
        return newMessage;
    }
    
    private static String RuleSplit(SimpleNode Rulenode, boolean IsColRule){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        Rulenode.jjtAccept(ppv, null);
        String str[] = ppv.print.split("OR");
        String example = (IsColRule ? "COL " : "") + Rulenode.jjtGetChild(0).toString();
        example += " BEGIN\n";
        for(int i = 0; i < str.length; i++){
            example += "partrule"+i+": "+str[i]+"\n";
        }
        example += "END\n";
        return example;
    }

    private static String PartRuleSplit(SimpleNode Rulenode){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        Rulenode.jjtAccept(ppv, null);
        String str[] = ppv.print.split("OR");
        String example = Rulenode.jjtGetChild(0).toString();
        for(int i = 0; i < str.length; i++){
            example += "partrule"+i+": "+str[i]+"\n";
        }
        return example;
    }
}
