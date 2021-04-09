public class TypeException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TypeException(String id, STVal types, BaseType newType, SimpleNode node) {
        super(typeExceptionRedeclaration(id, types, newType, node));
    }

    private static String typeExceptionRedeclaration(String id, STVal types, BaseType newType, SimpleNode node) {
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "TYPE ERROR: \"" + id + "\" has already been declared to be of ";
        newMessage += types.toString("all");
        newMessage += ", cannot redeclare identifier as " + newType.toString()+"\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String typeExceptionNumOper(String id, SimpleNode node){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "TYPE ERROR: Cannot use numeric operators +, -, *, / with non-numeric coloumn "+id+"\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }

    private static String typeExceptionSumAverage(String id, SimpleNode node){
        PrettyPrinterVisitor ppv = new PrettyPrinterVisitor();
        String newMessage = "ERROR:";
        newMessage += "TYPE ERROR: Can only use AVERAGE and SUM coloumns of numeric or empty values\n";
        newMessage += "At line: ";
        node.jjtAccept(ppv, null);
        newMessage += ppv.print;
        newMessage += "\n";
        return newMessage;
    }
}
