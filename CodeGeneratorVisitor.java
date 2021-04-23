
public class CodeGeneratorVisitor implements ScannerVisitor {

    private String output = "";

    @Override
    public SimpleNode visit(MINUS node, SimpleNode data) {
        SimpleNode n = node.jjtGetChild(0).jjtAccept(this, null);
        if (n instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
        output += " - ";
        SimpleNode rightChild = node.jjtGetChild(1).jjtAccept(this, null);
        if (rightChild instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
        return null;
    }

    @Override
    public SimpleNode visit(DIVIDE node, SimpleNode data) {
        SimpleNode n = node.jjtGetChild(0).jjtAccept(this, null);
        if (n instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
        output += " / ";
        SimpleNode rightChild = node.jjtGetChild(1).jjtAccept(this, null);
        if (rightChild instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
        return null;
    }

    @Override
    public SimpleNode visit(MODULUS node, SimpleNode data) {
        SimpleNode n = node.jjtGetChild(0).jjtAccept(this, null);
        if (n instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
        output += " % ";
        SimpleNode rightChild = node.jjtGetChild(1).jjtAccept(this, null);
        if (rightChild instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
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
        String tableName = "df"; /*node.jjtGetChild(0).jjtAccept(this, null).toString("");*/
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
        String mabyePartRule = node.jjtGetChild(1).toString();
        if (mabyePartRule.equals("COLPARTRULE")){
            for (int i = 0; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }  
            return null;
        }

        String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "def "+ruleName+"():\n";
        int lastChild = node.jjtGetNumChildren()-1;
        String mabyeWhereClause = node.jjtGetChild(lastChild).toString();
        if (mabyeWhereClause.equals("WHERE")){ // if where clause exists, it has to be run first
            output += "\ttempDf = pd.DataFrame(columns=df.columns)\n";
            output += "\tfor index, row in df.iterrows():\n";
            output += "\t\tif: ";
            node.jjtGetChild(lastChild).jjtAccept(this, null);
            output += "\n";
            output += "\t\t\ttempDf = tempDf.append(row)\n";
            output += "\trow = tempDf\n";
        } 
        output += "\tif ";
        for (int i = 1; i < node.jjtGetNumChildren()-1; i++){
            node.jjtGetChild(i).jjtAccept(this, null);
        }
        output += "\t\treturn ['x', ' ']\n";
        output += "\telse:\n";
        output += "\t\treturn [' ', 'x']\n";
        output += "\n";
        return null;
    }

    @Override
    public SimpleNode visit(COLPARTRULE node, SimpleNode data) {
        String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "def "+ruleName+"():\n";
        int lastChild = node.jjtGetNumChildren()-1;
        String mabyeWhereClause = node.jjtGetChild(lastChild).toString();
        if (mabyeWhereClause.equals("WHERE")){ // if where clause exists, it has to be run first
            output += "\ttempDf = pd.DataFrame(columns=df.columns)\n";
            output += "\tfor index, row in df.iterrows():\n";
            output += "\t\tif: ";
            node.jjtGetChild(lastChild).jjtAccept(this, null);
            output += "\n";
            output += "\t\t\ttempDf = tempDf.append(row)\n";
            output += "\trow = tempDf\n";
        } 
        output += "\tif ";
        for (int i = 1; i < node.jjtGetNumChildren()-1; i++){
            node.jjtGetChild(i).jjtAccept(this, null);
        }
        output += "\t\treturn ['x', ' ']\n";
        output += "\telse:\n";
        output += "\t\treturn [' ', 'x']\n";
        output += "\n";
        return null;
    }

    @Override
    public SimpleNode visit(COLOR node, SimpleNode data) {
        output += "(";
        output += node.jjtGetChild(0).jjtAccept(this, data).toString("");
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " or ";
            output += node.jjtGetChild(i).jjtAccept(this, data).toString("");
        }
        output += ")";
        return null;
    }

    @Override
    public SimpleNode visit(COLAND node, SimpleNode data) {
        output += "(";
        output += node.jjtGetChild(0).jjtAccept(this, data).toString("");
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " and ";
            output += node.jjtGetChild(i).jjtAccept(this, data).toString("");
        }
        output += ")";
        return null;
    }

    @Override
    public SimpleNode visit(COLVALEXPR node, SimpleNode data) {
        String expr = node.toString("");
        if (expr.equals("IS")){
            processIS(node);
        } else if (expr.equals("=")) {
            output += node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += " == ";
            output += node.jjtGetChild(1).jjtAccept(this, null).toString("");
        } else if (expr.equals("CONTAINS")){
            String str = node.jjtGetChild(1).jjtAccept(this, null).toString("");
            output += str+" in ";
            String colName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "("+getType(node)+")row[\""+colName+"\"]";
        } else if (expr.equals("<=") | expr.equals(">=") | expr.equals("<") | expr.equals(">")){
            String id = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "row[\""+id+"\"]";
            output += node.toString("");
            SimpleNode rightNode = node.jjtGetChild(1).jjtAccept(this, null);
            if (rightNode instanceof IDEN) {
                output += "row[\""+rightNode.toString("")+"\"]";
            } else {
                node.jjtGetChild(1).jjtAccept(this, null);
            }          
        } else {
            String id = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "row[\""+id+"\"]";
            node.jjtGetChild(1).jjtAccept(this, null);
        }
        return null;
    }

    @Override
    public SimpleNode visit(WHERE node, SimpleNode data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return node;
    }

    @Override
    public SimpleNode visit(SACD node, SimpleNode data) {
        SimpleNode childNode = (SimpleNode)node.jjtGetChild(0);
        String childType = getType(childNode);
        String val = node.toString("");
        switch (val) {
            case "DISTINCT":
                output += "len(df["+childNode+"].unique())";
                break;
            case "COUNT":
                output += "pd.to_numeric(df[\""+childNode+"\"], downcast='"+childType+"').count()";
                break;
            case "SUM":
                output += "pd.to_numeric(df[\""+childNode+"\"], downcast='"+childType+"').sum()";
                break;
            case "AVG":
                output += "pd.to_numeric(df[\""+childNode+"\"], downcast='"+childType+"').mean()";
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public SimpleNode visit(ADD node, SimpleNode data) {
        SimpleNode n = node.jjtGetChild(0).jjtAccept(this, null);
        if (n instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
        output += " + ";
        SimpleNode rightChild = node.jjtGetChild(1).jjtAccept(this, null);
        if (rightChild instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
        return null;
    }

    @Override
    public SimpleNode visit(MULT node, SimpleNode data) {
        SimpleNode n = node.jjtGetChild(0).jjtAccept(this, null);
        if (n instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
        output += " * ";
        SimpleNode rightChild = node.jjtGetChild(1).jjtAccept(this, null);
        if (rightChild instanceof IDEN){
            output += "row[\""+n+"\"]";
        } 
        return null;
    }

    @Override
    public SimpleNode visit(RULE node, SimpleNode data) {
        String mabyePartRule = node.jjtGetChild(1).toString();
        if (mabyePartRule.equals("PARTRULE")){
            for (int i = 0; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }  
            return null;
        }

        String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "def "+ruleName+"():\n";
        output += "\t"+"result = []\n";
        output += "\tfor index, row in people.iterrows():\n";
        output += "\t\ttry:\n";
        int lastChild = node.jjtGetNumChildren()-1;
        String mabyeWhereClause = node.jjtGetChild(lastChild).toString();
        if (mabyeWhereClause.equals("WHERE")){ // if where clause exists, it has to be run first
            output += "\t\t\tif ";
            node.jjtGetChild(lastChild).jjtAccept(this, null);
            output += "\t\t\t\tif ";
            for (int i = 1; i < node.jjtGetNumChildren()-1; i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }
            output += ":\n";
            output += "\t\t\t\t\tresult.append(\"Right\")\n"; 
            output += "\t\t\t\telse:\n";
            output += "\t\t\t\t\tresult.append(\"Wrong\")\n"; 
            output += "\t\t\telse:\n";
            output += "result.append(\"\")\n";
        } else {
            output += "\t\t\tif ";
            for (int i = 1; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }        
            output += ":\n";
            output += "\t\t\t\tresult.append(\"Right\")\n"; 
            output += "\t\t\telse:\n";
            output += "\t\t\t\tresult.append(\"Wrong\")\n"; 
        }
        output += "\t\texcept Exception:\n";
        output += "\t\t\tresult.append(\"Wrong\")\n";     
        output += "\treturn pd.Series(result)\n";
        output += "df.append(\""+ruleName+"\")\n";
        output += "resultFromRules[\""+ruleName+"\"] = "+ruleName+"()\n";
        return null;
    }

    @Override
    public SimpleNode visit(PARTRULE node, SimpleNode data) {
        String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "def "+ruleName+"():\n";
        output += "\t"+"result = []\n";
        output += "\tfor index, row in people.iterrows():\n";
        output += "\t\ttry:\n";
        int lastChild = node.jjtGetNumChildren()-1;
        String mabyeWhereClause = node.jjtGetChild(lastChild).toString();
        if (mabyeWhereClause.equals("WHERE")){ // if where clause exists, it has to be run first
            output += "\t\t\tif ";
            node.jjtGetChild(lastChild).jjtAccept(this, null);
            output += "\t\t\t\tif ";
            for (int i = 1; i < node.jjtGetNumChildren()-1; i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }
            output += ":\n";
            output += "\t\t\t\t\tresult.append(\"Right\")\n"; 
            output += "\t\t\t\telse:\n";
            output += "\t\t\t\t\tresult.append(\"Wrong\")"; 
            output += "\t\t\telse:";
            output += "result.append(\"\")";
        } else {
            output += "\t\t\tif ";
            for (int i = 1; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }        
            output += ":\n";
            output += "\t\t\t\tresult.append(\"Right\")\n"; 
            output += "\t\t\telse:\n";
            output += "\t\t\t\tresult.append(\"Wrong\")"; 
        }
        output += "\t\texcept Exception:\n";
        output += "\t\t\tresult.append(\"Wrong\")\n";     
        output += "\treturn pd.Series(result)\n";
        output += "df.append(\""+ruleName+"\")\n";
        output += "resultFromRules[\""+ruleName+"\"] = "+ruleName+"()";
        return null;
    }

    @Override
    public SimpleNode visit(OR node, SimpleNode data) {
        output += "(";
        output += node.jjtGetChild(0).jjtAccept(this, data).toString("");
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " or ";
            output += node.jjtGetChild(i).jjtAccept(this, data).toString("");
        }
        output += ")";
        return null;
    }

    @Override
    public SimpleNode visit(AND node, SimpleNode data) {
        output += "(";
        output += node.jjtGetChild(0).jjtAccept(this, data).toString("");
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " and ";
            output += node.jjtGetChild(i).jjtAccept(this, data).toString("");
        }
        output += ")";
        return null;
    }

    @Override
    public SimpleNode visit(VALEXPR node, SimpleNode data) {
        String expr = node.toString("");
        if (expr.equals("IS")){
            processIS(node);
        } else if (expr.equals("=")) {
            output += node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += " == ";
            output += node.jjtGetChild(1).jjtAccept(this, null).toString("");
        } else if (expr.equals("CONTAINS")){
            String str = node.jjtGetChild(1).jjtAccept(this, null).toString("");
            output += str+" in ";
            String colName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "("+getType(node)+")row[\""+colName+"\"]";
        } else if (expr.equals("<=") | expr.equals(">=") | expr.equals("<") | expr.equals(">")){
            String id = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "row[\""+id+"\"]";
            output += node.toString("");
            SimpleNode rightNode = node.jjtGetChild(1).jjtAccept(this, null);
            if (rightNode instanceof IDEN) {
                output += "row[\""+rightNode.toString("")+"\"]";
            } else {
                node.jjtGetChild(1).jjtAccept(this, null);
            }          
        } else {
            String id = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "row[\""+id+"\"]";
            node.jjtGetChild(1).jjtAccept(this, null);
        }
        return null;
    }

    private void processIS(SimpleNode n){
        String id = "row[\""+n.jjtGetChild(0).jjtAccept(this, null).toString("")+"\"]";
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

        if (node.jjtGetChild(2)!=null) {
            analyzeWithArguments(node);
            return null;
        }

        output += "def pretty_print(analyzeRuleTable, overviewAxis, normalAxis, columnAxis, columnTable):\n";
        output += "\tcolors_list = ['#5cb85c', '#d9534f']\n";
        output += "\toverviewPlot = (analyzeRuleTable.div(analyzeRuleTable.sum(1), axis=0)).plot(\n";
        output += "\tkind='barh', \n";
        output += "\tstacked=True, \n";
        output += "\tcolor=colors_list, \n";
        output += "\tedgecolor='white', \n";
        output += "\tax=overviewAxis\n";
        output += "\t)\n";
        output += "\trightSide = overviewPlot.spines['right']\n";
        output += "\trightSide.set_visible(False)\n";
        output += "\ttopSide = overviewPlot.spines['top']\n";
        output += "\ttopSide.set_visible(False)\n";
        output += "\toverviewAxis.set_title('Overview of failures in rules:', fontsize='large')\n";
        output += "\toverviewAxis.xaxis.set_major_formatter(mlt.PercentFormatter(1))\n";
        output += "\toverviewAxis.legend(['Passed', 'Failed'], loc=[1, 0.5])\n";

        output += "\tcolumn_labels = ['No. of passed rows', 'No. of failed rows']\n";
        output += "\tnormalAxis.axis('off')\n";
        output += "\tnormalAxis.table(\n";
        output += "\t\tcellText=analyzeRuleTable.values, \n";
        output += "\t\tcolLabels=column_labels, \n";
        output += "\t\trowLabels=ruleNames,\n";
        output += "\t\tbbox=[0, 0.2, 1, 0.65],\n";
        output += "\t\tloc='center'\n";
        output += "\t\t)\n";

        output += "\tcolumn_labels = ['Passed', 'Failed', 'Error message']\n";
        output += "\tcolumnAxis.axis('off')\n";
        output += "\tcolPlot = columnAxis.table(\n";
        output += "\t\tcellText=columnTable.values, \n";
        output += "\t\tcolLabels=column_labels, \n";
        output += "\t\trowLabels=columnRuleNames,\n";
        output += "\t\t#bbox=[0, 0, 1, 0.65],\n";
        output += "\t\tloc='center'\n";
        output += "\t)\n";
        output += "\tcolPlot.set_fontsize(15)\n";

        output += "\tmlp.tight_layout()\n";
        output += "\tmlp.show()\n";

        output += "def ANALYZE():\n";
        output += "\tcolumnTable = pd.DataFrame.from_records(columnList)\n";
        output += "\tcols = df[ruleNames].apply(pd.value_counts).fillna(0).transpose()\n";
        output += "\tanalyzeRuleTable = pd.DataFrame(cols[\"Right\"])\n";
        output += "\tanalyzeRuleTable[\"Wrong\"] = cols[\"Wrong\"]\n";
        output += "\tfig, (overviewAxis, normalAxis, columnAxis) = mlp.subplots(3, 1)\n";
        output += "\tpretty_print(analyzeRuleTable, overviewAxis, normalAxis, columnAxis, columnTable)\n";

        output += "ANALYZE()";
        return null;
    }

    private void analyzeWithArguments(ANALYZE node){

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