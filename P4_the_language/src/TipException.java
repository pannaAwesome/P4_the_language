package src;
public class TipException extends Exception {

    private static final long serialVersionUID = 1L;

    public TipException(SimpleNode node, String id, String ruleName, boolean isPartRule){
        super(ReadabilityTip(node, id, ruleName, isPartRule));
    }

    private static String ReadabilityTip(SimpleNode node, String id, String ruleName, boolean isPartRule){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "TIP:\n";
        newMessage += "READABILITY TIP: Long OR-chains can be rewritten as part rules in \"" + ruleName + "\"\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\nFor Example: \n";
        newMessage += (isPartRule ? PartRuleSplit(node, ruleName) : RuleSplit(node, ruleName)) +"\n";
        return newMessage;
    }
    
    private static String RuleSplit(SimpleNode Rulenode, String ruleName){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        Rulenode.jjtAccept(ppv, null);
        String[] str = ppv.print.split(":");
        str = str[1].split("OR");
        String example = ruleName;
        example += " BEGIN\n";
        for(int i = 0; i < str.length; i++){
            example += "partrule"+i+": "+str[i]+"\n";
        }
        example += "END\n";
        return example;
    }

    private static String PartRuleSplit(SimpleNode Rulenode, String ruleName){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        Rulenode.jjtAccept(ppv, null);
        String str[] = ppv.print.split("OR");
        String example = str[0] + "\n";
        for(int i = 1; i < str.length; i++){
            example += "partrule" + i + ": " + str[i] + "\n";
        }
        return example;
    }
}
