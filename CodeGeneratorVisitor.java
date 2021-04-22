import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CodeGeneratorVisitor implements ScannerVisitor {

    public String output = "";

    /*public void getPyFile(){
        try {
            PrintWriter out = new PrintWriter("filename.txt");
            out.println(output);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    @Override
    public SimpleNode visit(MINUS node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(DIVIDE node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(MODULUS node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(SimpleNode node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(PROG node, SimpleNode data) {

        output += "import pandas as pd\n";
        output += "import matplotlib.pyplot as mlp\n";
        output += "import matplotlib.ticker as mlt\n";
        output += "import numpy as np\n";
        
        output += "ruleNames = []\n";
        output += "columnList = []\n";
        output += "columnRuleNames = []\n";

        output += "def isfloat(value):\n";
        output += "\ttry:\n";
        output += "\t\tfloat(value)\n";
        output += "\t\treturn True\n";
        output += "\texcept Exception:\n";   
        output += "\t\treturn False\n";  

        output += "def isint(value):\n";
        output += "\ttry:\n";
        output += "\t\tint(value)\n";
        output += "\t\treturn True\n";
        output += "\texcept Exception:\n";   
        output += "\t\treturn False\n";


        for(int i = 0; i < node.jjtGetNumChildren(); i++){
            node.jjtGetChild(i).jjtAccept(this, null);
        }

        return null;
    }

    @Override
    public SimpleNode visit(IMPORT node, SimpleNode data) {
        String tableName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        String path = node.jjtGetChild(1).jjtAccept(this, null).toString("");
        output += "path = \""+path+"\n\"";
        // Der skal være mulighed for at angive om ens csv fil er komma er semilocolon separeret
        if (node.jjtGetNumChildren()>2){
            // Sørg for import options
        } else {
            output += tableName+" = pd.read_csv(peoplecsv, dtype=str)\n";
        }
        return null;
    }

    @Override
    public SimpleNode visit(IMPOPTIONS node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(FLNM node, SimpleNode data) {
        return node;
    }

    @Override
    public SimpleNode visit(NOHEADERS node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(MODEL node, SimpleNode data) {
        for(int i = 1; i < node.jjtGetNumChildren(); i++){
            node.jjtGetChild(i).jjtAccept(this, null);
        }
        return null;
    }

    @Override
    public SimpleNode visit(COLRULE node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(COLPARTRULE node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(COLOR node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(COLAND node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(COLVALEXPR node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(WHERE node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(SACD node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(ADD node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " and ";
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(MULT node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(RULE node, SimpleNode data) {
        String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "def "+ruleName+"():\n";
        output += "\t"+"result = []\n";
        output += "\tfor index, row in people.iterrows():\n";
        output += "\t\ttry:\n";
        output += "\t\t\tif ";
        node.jjtGetChild(1).jjtAccept(this, null).toString("");
        output += ":\n";
        output += "\t\t\t\tresult.append(\"Right\")\n"; 
        output += "\t\t\telse:\n";
        output += "\t\t\t\tresult.append(\"Wrong\")"; 
        output += "\t\texcept Exception:\n";
        output += "\t\tresult.append(\"Wrong\")\n";     
        output += "\treturn pd.Series(result)\n";
        output += "ruleNames.append(\""+ruleName+"\")\n";
        output += "resultFromRules[\""+ruleName+"\"] = "+ruleName+"()";
        return null;
    }

    @Override
    public SimpleNode visit(PARTRULE node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(OR node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " or ";
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(AND node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);

        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " and ";
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(VALEXPR node, SimpleNode data) {
        String expr = node.toString("");
        if (expr.equals("IS")){
            processIS(node);
        } else if (expr.equals("=")) {
            node.jjtGetChild(0).jjtAccept(this, null);
            output += " == ";
            node.jjtGetChild(1).jjtAccept(this, null);
        } else if (expr.equals("CONTAINS")){
            node.jjtGetChild(1).jjtAccept(this, null);
            output += " in ";
            String colName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "("+getType(node)+")row[\""+colName+"\"]";
        } else {
            node.jjtGetChild(0).jjtAccept(this, null);
            output += node.toString("");
            SimpleNode n = node.jjtGetChild(0).jjtAccept(this, null);
            if (n instanceof IDEN) {
                output += "row[\""+n.toString("")+"\"]";
            } else {
                n.jjtGetChild(1).jjtAccept(this, null);
            }
        }
        return null;
    }

    private void processIS(SimpleNode n){
        String id = n.jjtGetChild(0).jjtAccept(this, null).toString("");
        String constraint = n.jjtGetChild(1).jjtAccept(this, null).toString("");
        switch (constraint) {
            case "LETTERS":
                output += id+".isalpha()";
                break;
            case "DECIMAL":
                output += "isfloat("+id+")";
                break;
            case "INTEGER":
                output += "isint("+id+")";
                break;
            case "EMPTY":
                output += "pd.isna(row[\""+id+"\"])";
                break;
            case "NOT":
                output += "not pd.isna(row[\""+id+"\"])";
                break;
        }
    }

    @Override
    public SimpleNode visit(CONSTRAINTS node, SimpleNode data) {
        return node;
    }

    @Override
    public SimpleNode visit(STRING node, SimpleNode data) {
        return node;
    }

    @Override
    public SimpleNode visit(INTEGER node, SimpleNode data) {
        return node;
    }

    @Override
    public SimpleNode visit(FLOATY node, SimpleNode data) {
        return node;
    }

    @Override
    public SimpleNode visit(ANALYZE node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(ANLZOPTIONS node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(RULEOPT node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(ROWS node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(COLS node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(RANGE node, SimpleNode data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SimpleNode visit(IDEN node, SimpleNode data) {
        return node;
    }

    private String getType(SimpleNode n){
        String t = n.type.toString();
        if (t.equals("Integer")){
            return "int";
        } else {
            return "float";
        }
    }
    
}