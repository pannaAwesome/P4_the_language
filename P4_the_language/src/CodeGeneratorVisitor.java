package src;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CodeGeneratorVisitor implements ScannerVisitor {

    private String output = "";

    public void getPyFile(){
        try {
            PrintWriter out = new PrintWriter("output.py");
            out.println(output);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SimpleNode visit(SimpleNode node, SimpleNode data) {
        return null;
    }

    @Override
    public SimpleNode visit(PROG node, SimpleNode data) {

        output += "import pandas as pd\n";
        output += "import matplotlib.pyplot as mlp\n";
        output += "import matplotlib.ticker as mlt\n";
        output += "import numpy as np\n\n";
        
        output += "ruleNames = []\n";
        output += "columnRuleNames = []\n";
        output += "resultFromColumnRules = []\n\n";

        output += "def isfloat(value):\n";
        output += "\ttry:\n";
        output += "\t\tfloat(value)\n";
        output += "\t\treturn True\n";
        output += "\texcept Exception:\n";   
        output += "\t\treturn False\n\n";  

        output += "def isint(value):\n";
        output += "\ttry:\n";
        output += "\t\tint(value)\n";
        output += "\t\treturn True\n";
        output += "\texcept Exception:\n";   
        output += "\t\treturn False\n\n";


        for(int i = 0; i < node.jjtGetNumChildren(); i++){
            node.jjtGetChild(i).jjtAccept(this, null);
        }

        return null;
    }

    @Override
    public SimpleNode visit(IMPORT node, SimpleNode data) {
        String path = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "path = \""+path+"\"\n";
        // Der skal være mulighed for at angive om ens csv fil er komma er semilocolon separeret
        if (node.jjtGetNumChildren()>2){
            node.jjtGetChild(2).jjtAccept(this, null);
        } else {
            output += "df = pd.read_csv(path, dtype=str)\n";
            output += "idColSet = False\n";
        }
        return null;
    }

    @Override
    public SimpleNode visit(IMPOPTIONS node, SimpleNode data) {
        output += "df = pd.read_csv(path, dtype=str)\n";
        for (int i = 0; i < node.jjtGetNumChildren(); i++){
            SimpleNode n = node.jjtGetChild(i).jjtAccept(this, null);
            if (n instanceof IDEN || n instanceof INTEGER){ // id parameter 
                output += "idColSet = \""+n.toString("")+"\"\n";
                output += "\n";
                return null;
            }  
        }
        return null;
    }

    @Override
    public SimpleNode visit(FLNM node, SimpleNode data) {
        return node;
    }

    @Override
    public SimpleNode visit(NOHEADERS node, SimpleNode data) {
        output += "def noHeaders():\n";
        output += "\tnewHeaders = []\n";
        output += "\tcolNum = len(df.columns)\n";
        output += "\tfor i in range(colNum):\n";
        output += "\t\tnewHeaders.append(f\"column{i+1}\")\n";
        output += "\tdf.columns = newHeaders\n";
        output += "noHeaders()\n\n";
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
            String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "_"+ruleName+"Where = []\n"; 
            output += "_"+ruleName+" = []\n";
            for (int i = 0; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }  
            output += "def "+ruleName+"():\n";
            output += "\ttry:\n";
            output += "\t\ttempDf = pd.DataFrame(columns=df.columns)\n";
            output += "\t\ttempBool = False\n";
            output += "\t\ttempRes = False\n";
            output += "\t\tfor index, row in df.iterrows():\n";
            output += "\t\t\tfor func in _nameRuleWhere:\n";
            output += "\t\t\t\ttempBool = tempBool or func(row)\n";
            output += "\t\t\tif tempBool:\n";
            output += "\t\t\t\ttempDf = tempDf.append(row)\n\n";
		
            output += "\t\t\tfor func in _"+ruleName+":\n";
            output += "\t\t\t\ttempRes = tempRes or func(row)\n";
            output += "\t\t\tif tempRes:\n";
            output += "\t\t\t\treturn ['x', ' ']\n";
            output += "\t\t\telse:\n";
            output += "\t\t\t\treturn [' ', 'x']\n";
            output += "\texcept Exception:\n";
            output += "\t\treturn [' ', 'x']\n";
            output += "columnRuleNames.append(\""+ruleName+"\")\n";
            output += "resultFromColumnRules.append("+ruleName+"())\n\n";

            return null;
        }

        String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "def "+ruleName+"():\n";
        int lastChild = node.jjtGetNumChildren()-1;
        String mabyeWhereClause = node.jjtGetChild(lastChild).toString();
        if (mabyeWhereClause.equals("WHERE")){ // if where clause exists, it has to be run first
            output += "\ttempDf = pd.DataFrame(columns=df.columns)\n";
            output += "\tfor index, row in df.iterrows():\n";
            output += "\t\tif ";
            node.jjtGetChild(lastChild).jjtAccept(this, null);
            output += ":\n";
            output += "\t\t\ttempDf = tempDf.append(row)\n";
            output += "\tif ";
            for (int i = 1; i < node.jjtGetNumChildren()-1; i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }
        } else {
            output += "\tif ";
            for (int i = 1; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }
        }
        output += ":\n";
        output += "\t\treturn ['x', ' ']\n";
        output += "\telse:\n";
        output += "\t\treturn [' ', 'x']\n";
        output += "columnRuleNames.append(\""+ruleName+"\")\n";
        output += "resultFromColumnRules.append("+ruleName+"())\n";
        output += "\n";
        

        return null;
    }

    @Override
    public SimpleNode visit(COLPARTRULE node, SimpleNode data) {
        String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "def "+ruleName+"Where(row):\n";
        int lastChild = node.jjtGetNumChildren()-1;
        SimpleNode whereClause = (SimpleNode)node.jjtGetChild(lastChild);

        output += "\treturn ";
        if (whereClause instanceof WHERE){
            for (int i = 0; i < whereClause.jjtGetNumChildren(); i++){
                whereClause.jjtGetChild(i).jjtAccept(this, null);
            } 
            output += "\n";
            output += "_"+getParentRuleFromPartRule(node)+"Where.append("+ruleName+"Where)\n\n";

            output += "def "+ruleName+"():\n";
            output += "\treturn ";
            for (int i = 0; i < node.jjtGetNumChildren()-1; i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }
            output += "\n";
            output += "_"+getParentRuleFromPartRule(node)+".append("+ruleName+")\n";
            output += "\n";
        } else {
            for (int i = 1; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }
        }


        return null;
    }

    @Override
    public SimpleNode visit(COLOR node, SimpleNode data) {
        output += "(";
        SimpleNode left = node.jjtGetChild(0).jjtAccept(this, data);
        if (left instanceof IDEN){
            output += left.toString("");
        }
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " or ";
            SimpleNode right = node.jjtGetChild(i).jjtAccept(this, data);
            if (right instanceof IDEN){
                output += left.toString("");
            }
        }
        output += ")";
        return null;
    }

    @Override
    public SimpleNode visit(COLAND node, SimpleNode data) {
        output += "(";
        SimpleNode left = node.jjtGetChild(0).jjtAccept(this, data);
        if (left instanceof IDEN){
            output += left.toString("");
        }
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " and ";
            SimpleNode right = node.jjtGetChild(i).jjtAccept(this, data);
            if (right instanceof IDEN){
                output += left.toString("");
            }
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
            node.jjtGetChild(0).jjtAccept(this, null);
            output += " == ";
            node.jjtGetChild(1).jjtAccept(this, null);
        } else if (expr.equals("<=") | expr.equals(">=") | expr.equals("<") | expr.equals(">")){
            node.jjtGetChild(0).jjtAccept(this, null);
            output += node.toString("");
            node.jjtGetChild(1).jjtAccept(this, null);
        } else {
            node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += node.toString("");
            node.jjtGetChild(1).jjtAccept(this, null);
        }
        return null;
    }

    @Override
    public SimpleNode visit(WHERE node, SimpleNode data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode n = node.jjtGetChild(i).jjtAccept(this, data);
            if (n instanceof IDEN) {

            }
        }
        return node;
    }

    @Override
    public SimpleNode visit(SACD node, SimpleNode data) {
        IDEN childNode = (IDEN)node.jjtGetChild(0);
        String childType = getType(childNode);
        String childName = childNode.toString("");
        String val = node.toString("");
        switch (val) {
            case "DISTINCT":
                output += "len(df[\""+childName+"\"].unique())";
                break;
            case "COUNT":
                output += "df[\""+childName+"\"].count()";
                break;
            case "SUM":
                output += "pd.to_numeric(df[\""+childName+"\"], downcast='"+childType+"').sum()";
                break;
            case "AVG":
                output += "pd.to_numeric(df[\""+childName+"\"], downcast='"+childType+"').mean()";
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
            String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString(""); 
            output += "_"+ruleName+" = []\n";
            for (int i = 0; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }  
            output += "def "+ruleName+"():\n";
            output += "\tresult = []\n";
            output += "\ttempRes = False\n";
            output += "\tfor index, row in df.iterrows():\n";
            output += "\t\ttry:\n";
            output += "\t\t\tfor func in _"+ruleName+":\n";
            output += "\t\t\t\ttempRes = tempRes or func(row)\n";
            output += "\t\t\tif tempRes:\n";
            output += "\t\t\t\tresult.append(\"True\")\n";
            output += "\t\t\telse:\n";
            output += "\t\t\t\tresult.append(\"False\")\n";
            output += "\t\t\ttempRes = False\n";
            output += "\t\texcept Exception:\n";
            output += "\t\t\tresult.append(\"False\")\n";
            output += "\treturn pd.Series(result)\n";
            output += "ruleNames.append(\""+ruleName+"\")\n";
            output += "df[\""+ruleName+"\"] = "+ruleName+"()\n\n";

            return null;
        }

        String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "def "+ruleName+"():\n";
        output += "\t"+"result = []\n";
        output += "\tfor index, row in df.iterrows():\n";
        output += "\t\ttry:\n";
        output += "\t\t\tif ";
        for (int i = 1; i < node.jjtGetNumChildren(); i++){
            node.jjtGetChild(i).jjtAccept(this, null);
        }        
        output += ":\n";
        output += "\t\t\t\tresult.append(\"True\")\n"; 
        output += "\t\t\telse:\n";
        output += "\t\t\t\tresult.append(\"False\")\n"; 
        output += "\t\texcept Exception:\n";
        output += "\t\t\tresult.append(\"False\")\n";     
        output += "\treturn pd.Series(result)\n";
        output += "ruleNames.append(\""+ruleName+"\")\n";
        output += "df[\""+ruleName+"\"] = "+ruleName+"()\n";
        output += "\n";
        return null;
    }

    @Override
    public SimpleNode visit(PARTRULE node, SimpleNode data) {
        String ruleName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
        output += "def "+ruleName+"(row):\n";
        /*int lastChild = node.jjtGetNumChildren()-1;
        String mabyeWhereClause = node.jjtGetChild(lastChild).toString();
        if (mabyeWhereClause.equals("WHERE")){ // if where clause exists, it has to be run first
            output += "\tif ";
            node.jjtGetChild(lastChild).jjtAccept(this, null);
            output += "\t\treturn ";
            for (int i = 1; i < node.jjtGetNumChildren()-1; i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }
            output += "\telse:";
            output += "return \"\"";
        } else {*/
            output += "\t return ";
            for (int i = 1; i < node.jjtGetNumChildren(); i++){
                node.jjtGetChild(i).jjtAccept(this, null);
            }        
            output += "\n";
        //}
        output += "_"+getParentRuleFromPartRule(node)+".append("+ruleName+")\n";
        output += "\n";
        return null;
    }

    private String getParentRuleFromPartRule(Node n){
        while (!(n instanceof RULE) 
            && !(n instanceof COLRULE)) {
            n = n.jjtGetParent();
        }

        SimpleNode node = (SimpleNode)n.jjtGetChild(0);
        
        return node.toString("");
    }

    @Override
    public SimpleNode visit(OR node, SimpleNode data) {
        output += "(";
        SimpleNode left = node.jjtGetChild(0).jjtAccept(this, data);
        if (left instanceof IDEN){
            output += left.toString("");
        }
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " or ";
            SimpleNode right = node.jjtGetChild(i).jjtAccept(this, data);
            if (right instanceof IDEN){
                output += left.toString("");
            }
        }
        output += ")";
        return null;
    }

    @Override
    public SimpleNode visit(AND node, SimpleNode data) {
        output += "(";
        SimpleNode left = node.jjtGetChild(0).jjtAccept(this, data);
        if (left instanceof IDEN){
            output += left.toString("");
        }
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            output += " and ";
            SimpleNode right = node.jjtGetChild(i).jjtAccept(this, data);
            if (right instanceof IDEN){
                output += left.toString("");
            }
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
            String id = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "row[\""+id+"\"]";
            output += " == ";
            output += node.jjtGetChild(1).jjtAccept(this, null).toString("");
        } else if (expr.equals("CONTAINS")){
            node.jjtGetChild(1).jjtAccept(this, null).toString("");
            output += " in ";
            String colName = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += "row[\""+colName+"\"]";
        } else if (expr.equals("<=") | expr.equals(">=") | expr.equals("<") | expr.equals(">")){
            String id = node.jjtGetChild(0).jjtAccept(this, null).toString("");
            output += getType((IDEN)node.jjtGetChild(0))+"(row[\""+id+"\"])";
            output += node.toString("");
            SimpleNode rightNode = node.jjtGetChild(1).jjtAccept(this, null);
            if (rightNode instanceof IDEN) {
                output += getType((IDEN)rightNode)+"row[\""+rightNode.toString("")+"\"]";
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
        output += node.toString("");
        return node;
    }

    @Override
    public SimpleNode visit(INTEGER node, SimpleNode data) {
        output += node.toString("");
        return node;
    }

    @Override
    public SimpleNode visit(FLOATY node, SimpleNode data) {
        output += node.toString("");
        return null;
    }

    @Override
    public SimpleNode visit(ANALYZE node, SimpleNode data) {

        if (node.jjtGetNumChildren()>2) {
            node.jjtGetChild(2).jjtAccept(this, null);
            return null;
        }

        output += "def pretty_print(analyzeRuleTable, columnTable):\n";
        output += "\tfig, (overviewAxis, normalAxis, columnAxis) = mlp.subplots(3, 1)\n";
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
        output += "\toverviewAxis.legend(['Passed', 'Failed'], loc=[1, 0.5])\n\n";

        output += "\tanalyzeRuleTable[\"Total\"] = analyzeRuleTable[\"True\"] + analyzeRuleTable[\"False\"] \n";
        output += "\tanalyzeRuleTable[\"%\"] = round((analyzeRuleTable[\"True\"] / analyzeRuleTable[\"Total\"]) * 100, 2)\n\n";

        output += "\tcolumn_labels = ['No. of passed rows', 'No. of failed rows', 'no. of total rows', '% of passed rows']\n";
        output += "\tnormalAxis.axis('off')\n";
        output += "\tnorPlot = normalAxis.table(\n";
        output += "\t\tcellText=analyzeRuleTable.values, \n";
        output += "\t\tcolLabels=column_labels, \n";
        output += "\t\trowLabels=ruleNames,\n";
        output += "\t\tbbox=[0, 0.2, 1, 0.65],\n";
        output += "\t\tloc='center'\n";
        output += "\t\t)\n";
        output += "\tnorPlot.auto_set_font_size(False)\n";
        output += "\tnorPlot.set_fontsize(12)\n\n";
    
        output += "\tcolumn_labels = ['Passed', 'Failed', 'Error message']\n";
        output += "\tcolumnAxis.axis('off')\n";
        output += "\tcolPlot = columnAxis.table(\n";
        output += "\t\tcellText=columnTable.values, \n";
        output += "\t\tcolLabels=column_labels, \n";
        output += "\t\trowLabels=columnRuleNames,\n";
        output += "\t\t#bbox=[0, 0, 1, 0.65],\n";
        output += "\t\tloc='center'\n";
        output += "\t)\n";
        output += "\tcolPlot.set_fontsize(12)\n\n";

        output += "\tmlp.tight_layout()\n";
        output += "\tmlp.show()\n\n";

        output += "def ANALYZE():\n";
        output += "\tcolumnTable = pd.DataFrame.from_records(resultFromColumnRules)\n";

        output += "\ttotalFailure = 0\n";
        output += "\tfor row in df.iterrows():\n";
        output += "\t\tfor rule in ruleNames:\n";
        output += "\t\t\tif (row[1][rule] == \"False\"):\n";
        output += "\t\t\t\ttotalFailure += 1\n";
        output += "\t\t\t\tbreak\n";

        output += "\ttotalRows = len(df.index)\n";
        output += "\ttotalFailure = round((totalFailure / totalRows) * 100, 2)\n";
        output += "\toverrall = pd.DataFrame([[totalFailure, 100-totalFailure]], columns=[\"False\", \"True\"]).rename(index={0: \"Overrall correctness\"})\n";

        
        output += "\tcols = df[ruleNames].apply(pd.value_counts).fillna(0).transpose()\n";
        output += "\tcols = cols.append(overrall)\n";
        output += "\truleNames.append(\"Overral correctness\")\n";
        output += "\tanalyzeRuleTable = pd.DataFrame(cols[\"True\"])\n";
        output += "\tanalyzeRuleTable[\"False\"] = cols[\"False\"]\n";
        output += "\tpretty_print(analyzeRuleTable, columnTable)\n";

        output += "ANALYZE()";
        return null;
    }

    @Override
    public SimpleNode visit(ANLZOPTIONS node, SimpleNode data) {
        output += "def ANALYZE():\n";
        output += "\trows = df.drop(labels = ruleNames, axis = 1)\n";
        
        for (int i = 0; i < node.jjtGetNumChildren(); i++){
            node.jjtGetChild(i).jjtAccept(this, null);
        }
        
        output += "\tprint(rows.to_string())\n";
        output += "ANALYZE()\n";
        return null;
    }

    @Override
    public SimpleNode visit(RULEOPT node, SimpleNode data) {
        String ruleName = "";

        for (int i = 0; i < node.jjtGetNumChildren(); i++){
            ruleName = node.jjtGetChild(i).jjtAccept(this, null).toString("");
            output += "\truleNames.remove(\""+ruleName+"\")\n";
        }

        output += "\trows = df.loc[df['"+ruleName+"'] == \"False\"]\n";
        return null;
    }

    @Override
    public SimpleNode visit(ROWS node, SimpleNode data) {

        for (int i = 0; i < node.jjtGetNumChildren(); i++){
            SimpleNode range = node.jjtGetChild(i).jjtAccept(this, null);
            
            output += "\tif (idColSet != False):\n";
            output += "\t\trows[idColSet] = pd.to_numeric(rows[idColSet], downcast='signed')\n";
            output += "\t\trows = rows.loc[(rows[idColSet] >=";
            range.jjtGetChild(0).jjtAccept(this, null);
            output += ") & (rows[idColSet] <= ";
            range.jjtGetChild(1).jjtAccept(this, null);
            output += ")]\n";
            output += "\telse:\n";
            output += "\t\trows = rows.loc[";
            range.jjtGetChild(0).jjtAccept(this, null);
            output += ":";
            range.jjtGetChild(1).jjtAccept(this, null);
            output += "]\n";
        }
        return null;
    }

    @Override
    public SimpleNode visit(COLS node, SimpleNode data) {
        SimpleNode mabyeIden = node.jjtGetChild(0).jjtAccept(this, null);
        if (mabyeIden instanceof IDEN){
            for (int i = 0; i < node.jjtGetNumChildren(); i++){
                String id = node.jjtGetChild(i).jjtAccept(this, null).toString("");
                output += "\trows = rows[\""+id+"\"]\n";
            }
        } else { // if range, i replace headers with ints, and index them 
            output += "\trows.columns = list(range(1, len(rows.columns)+1))\n";
            for (int i = 0; i < node.jjtGetNumChildren(); i++){
                String id = node.jjtGetChild(i).jjtAccept(this, null).toString("");
                output += "\trows = rows["+id+"]\n";
            }
        }
        return null;
    }

    @Override
    public SimpleNode visit(RANGE node, SimpleNode data) {
        return node;
    }

    @Override
    public SimpleNode visit(IDEN node, SimpleNode data) {
        return node;
    }

    private String getType(IDEN n){
        BaseType t = n.type;
        if (t == null){
            return "float";
        }
        if (t.toString().equals("Integer")){
            return "int";
        } else {
            return "float";
        }
    }
}