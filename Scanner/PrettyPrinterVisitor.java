public class PrettyPrinterVisitor implements ScannerVisitor {
    @Override
    public SimpleNode visit(SimpleNode node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(PROG node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 0; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(IMPORT node, SimpleNode data) {
        System.out.print("IMPORT");
        System.out.print(" " + node.jjtGetChild(0).jjtAccept(this, data) + " ");
        System.out.print("AS");
        System.out.println(" " + node.jjtGetChild(1).jjtAccept(this, data));
    }

    @Override
    public SimpleNode visit(ANLZOPTIONS node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(IMOPTIONS node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(FLNM node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(NOHEADERS node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(MODEL node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(COLRULE node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(PARTRULE node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(OR node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(AND node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(COLVALEXPR node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(WHERE node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(SACD node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(ADD node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(MULT node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(RULE node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(VALEXPR node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(CONSTRAINTS node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(STRING node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(INTEGER node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(FLOATY node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(ANALYZE node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(RULEOPT node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(ROWS node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(COLS node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(RANGE node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(IDEN node, SimpleNode data) {
        return null;
    }
}