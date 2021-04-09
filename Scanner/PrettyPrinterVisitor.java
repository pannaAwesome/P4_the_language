public class PrettyPrinterVisitor implements ScannerVisitor {
    public String print = "";
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
        print +="IMPORT ";
        node.jjtGetChild(0).jjtAccept(this, data);
        print +=" IN ";
        node.jjtGetChild(1).jjtAccept(this, data);
        
        int numOfChild = node.jjtGetNumChildren();
        if (numOfChild == 2) {
            print += "\n";
            return node;
        }

        print +=" BEGIN\n";
        node.jjtGetChild(2).jjtAccept(this, data);
        print +="END\n";
        return node;
    }

    @Override
    public SimpleNode visit(IMPOPTIONS node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 0; i < numOfChild; i++) {
            if (node.jjtGetChild(i).toString().equals("IDEN") || node.jjtGetChild(i).toString().equals("INTEGER")) {
                print +="ID ";
                node.jjtGetChild(i).jjtAccept(this, data);
            }else {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            print +="\n";
        }
        return node;
    }

    @Override
    public SimpleNode visit(FLNM node, SimpleNode data) {
        print += node.value.toString();
        return node;
    }

    @Override
    public SimpleNode visit(NOHEADERS node, SimpleNode data) {
        print += "NOHEADERS ";
        return node;
    }

    @Override
    public SimpleNode visit(MODEL node, SimpleNode data) {
        print += "MODEL ";
        node.jjtGetChild(0).jjtAccept(this, data);
        print += " BEGIN\n";

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }

        print +="END\n";
        return node;
    }

    @Override
    public SimpleNode visit(COLRULE node, SimpleNode data) {
        print += "COL ";
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        if (node.jjtGetChild(1).toString().equals("COLPARTRULE")) {
            print +=" BEGIN\n";
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            print +="END\n";
        } else {
            print +=": ";
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            print +="\n";
        }       
        
        return node;
    }

    @Override
    public SimpleNode visit(COLPARTRULE node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        print +=": ";
        node.jjtGetChild(1).jjtAccept(this, data);
        print += "\n";
        return node;
    }

    @Override
    public SimpleNode visit(OR node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            print += " OR ";
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(AND node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            print += " AND ";
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(COLVALEXPR node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        print += " " + node.value.toString() + " ";
        node.jjtGetChild(1).jjtAccept(this, data);
        return node;
    }

    @Override
    public SimpleNode visit(WHERE node, SimpleNode data) {
        print += " WHERE ";
        node.jjtGetChild(0).jjtAccept(this, data);
        return node;
    }

    @Override
    public SimpleNode visit(SACD node, SimpleNode data) {
        print += node.value.toString() + "(";
        node.jjtGetChild(0).jjtAccept(this, data);
        print += ")";
        return node;
    }

    @Override
    public SimpleNode visit(ADD node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            print += " " + node.value.toString() + " ";
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(MULT node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            print += " " + node.value.toString() + " ";
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(RULE node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        if (node.jjtGetChild(1).toString().equals("PARTRULE")) {
            print += " BEGIN\n";
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            print += "END\n";
        } else {
            print += ": ";
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
            print += "\n";
        }       
        
        return node;
    }

    @Override
    public SimpleNode visit(PARTRULE node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        print +=": ";
        node.jjtGetChild(1).jjtAccept(this, data);
        print += "\n";
        return node;
    }

    @Override
    public SimpleNode visit(VALEXPR node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        print += " " + node.value.toString() + " ";
        node.jjtGetChild(1).jjtAccept(this, data);
        return node;
    }

    @Override
    public SimpleNode visit(CONSTRAINTS node, SimpleNode data) {
        if (node.value.toString().equals("NOTEMPTY")) {
            print += "NOT EMPTY";
        }else {
            print += node.value.toString();
        }
        return node;
    }

    @Override
    public SimpleNode visit(STRING node, SimpleNode data) {
        print += node.value.toString();
        return node;
    }

    @Override
    public SimpleNode visit(INTEGER node, SimpleNode data) {
        print += node.value.toString();
        return node;
    }

    @Override
    public SimpleNode visit(FLOATY node, SimpleNode data) {
        print += node.value.toString();
        return node;
    }

    @Override
    public SimpleNode visit(ANALYZE node, SimpleNode data) {
        print += "ANALYZE ";
        node.jjtGetChild(0).jjtAccept(this, data);
        print += " WITH ";
        node.jjtGetChild(1).jjtAccept(this, data);
        
        int numOfChild = node.jjtGetNumChildren();
        if (numOfChild == 2) {
            print += "\n";
            return node;
        }

        print += " BEGIN\n";
        node.jjtGetChild(2).jjtAccept(this, data);
        print += "END\n";
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
            print += "\n";
        }
        return node;
    }

    @Override
    public SimpleNode visit(ROWS node, SimpleNode data) {
        print += "ROW ";
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            print += ", ";
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        print+="\n";
        return node;
    }

    @Override
    public SimpleNode visit(COLS node, SimpleNode data) {
        print +="COL ";
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            print +=", ";
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        print += "\n";
        return node;
    }

    @Override
    public SimpleNode visit(RANGE node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        print += " TO ";
        node.jjtGetChild(1).jjtAccept(this, data);
        return node;
    }

    @Override
    public SimpleNode visit(IDEN node, SimpleNode data) {
        print += node.value.toString();
        return node;
    }
    
}