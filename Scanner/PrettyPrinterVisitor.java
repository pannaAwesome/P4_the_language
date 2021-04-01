public class PrettyPrinterVisitor implements ScannerVisitor {

    @Override
    public SimpleNode visit(SimpleNode node, SimpleNode data) {
        throw new RuntimeException("Visit SimpleNode");
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
        System.out.print("IMPORT ");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" IN ");
        node.jjtGetChild(1).jjtAccept(this, data);
        
        int numOfChild = node.jjtGetNumChildren();
        if (numOfChild == 2) {
            System.out.println();
            return node;
        }

        System.out.println(" BEGIN");
        node.jjtGetChild(2).jjtAccept(this, data);
        System.out.println("END");
        return node;
    }

    @Override
    public SimpleNode visit(IMPOPTIONS node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 0; i < numOfChild; i++) {
            if (node.jjtGetChild(i).toString().equals("IDEN") || node.jjtGetChild(i).toString().equals("INTEGER")) {
                System.out.print("ID ");
                node.jjtGetChild(i).jjtAccept(this, data);
            }else {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            System.out.println();
        }
        return node;
    }

    @Override
    public SimpleNode visit(FLNM node, SimpleNode data) {
        System.out.print(node.value.toString());
        return node;
    }

    @Override
    public SimpleNode visit(NOHEADERS node, SimpleNode data) {
        System.out.print("NOHEADERS");
        return node;
    }

    @Override
    public SimpleNode visit(MODEL node, SimpleNode data) {
        System.out.print("MODEL ");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.println(" BEGIN");

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }

        System.out.println("END");
        return node;
    }

    @Override
    public SimpleNode visit(COLRULE node, SimpleNode data) {
        System.out.print("COL ");
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        if (node.jjtGetChild(1).toString().equals("COLPARTRULE")) {
            System.out.println(" BEGIN");
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            System.out.println("END");
        } else {
            System.out.print(": ");
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            System.out.println();
        }       
        
        return node;
    }

    @Override
    public SimpleNode visit(COLPARTRULE node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(": ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.println("");
        return node;
    }

    @Override
    public SimpleNode visit(OR node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            System.out.print(" OR ");
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(AND node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            System.out.print(" AND ");
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(COLVALEXPR node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" " + node.value.toString() + " ");
        node.jjtGetChild(1).jjtAccept(this, data);
        return node;
    }

    @Override
    public SimpleNode visit(WHERE node, SimpleNode data) {
        System.out.print(" WHERE ");
        node.jjtGetChild(0).jjtAccept(this, data);
        return node;
    }

    @Override
    public SimpleNode visit(SACD node, SimpleNode data) {
        System.out.print(node.value.toString() + "(");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(")");
        return node;
    }

    @Override
    public SimpleNode visit(ADD node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            System.out.print(" " + node.value.toString() + " ");
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(MULT node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            System.out.print(" " + node.value.toString() + " ");
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(RULE node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        if (node.jjtGetChild(1).toString().equals("PARTRULE")) {
            System.out.println(" BEGIN");
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            System.out.println("END");
        } else {
            System.out.print(": ");
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            System.out.println();
        }       
        
        return node;
    }

    @Override
    public SimpleNode visit(PARTRULE node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(": ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.println("");
        return node;
    }

    @Override
    public SimpleNode visit(VALEXPR node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" " + node.value.toString() + " ");
        node.jjtGetChild(1).jjtAccept(this, data);
        return node;
    }

    @Override
    public SimpleNode visit(CONSTRAINTS node, SimpleNode data) {
        if (node.value.toString().equals("NOTEMPTY")) {
            System.out.print("NOT EMPTY");
        }else {
            System.out.print(node.value.toString());
        }
        return node;
    }

    @Override
    public SimpleNode visit(STRING node, SimpleNode data) {
        System.out.print(node.value.toString());
        return node;
    }

    @Override
    public SimpleNode visit(INTEGER node, SimpleNode data) {
        System.out.print(node.value.toString());
        return node;
    }

    @Override
    public SimpleNode visit(FLOATY node, SimpleNode data) {
        System.out.print(node.value.toString());
        return node;
    }

    @Override
    public SimpleNode visit(ANALYZE node, SimpleNode data) {
        System.out.print("ANALYZE ");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" WITH ");
        node.jjtGetChild(1).jjtAccept(this, data);
        
        int numOfChild = node.jjtGetNumChildren();
        if (numOfChild == 2) {
            System.out.println();
            return node;
        }

        System.out.println(" BEGIN");
        node.jjtGetChild(2).jjtAccept(this, data);
        System.out.println("END");
        return node;
    }

    @Override
    public SimpleNode visit(ANLZOPTIONS node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 0; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(RULEOPT node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 0; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
            System.out.println();
        }
        return node;
    }

    @Override
    public SimpleNode visit(ROWS node, SimpleNode data) {
        System.out.print("ROW ");
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            System.out.print(", ");
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        System.out.println();
        return node;
    }

    @Override
    public SimpleNode visit(COLS node, SimpleNode data) {
        System.out.print("COL ");
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            System.out.print(", ");
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        System.out.println();
        return node;
    }

    @Override
    public SimpleNode visit(RANGE node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" TO ");
        node.jjtGetChild(1).jjtAccept(this, data);
        return node;
    }

    @Override
    public SimpleNode visit(IDEN node, SimpleNode data) {
        System.out.print(node.value.toString());
        return node;
    }
    
}