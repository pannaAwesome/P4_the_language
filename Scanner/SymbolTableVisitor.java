import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SymbolTableVisitor implements ScannerVisitor {

    public static HashMap<String, STVal> ST = new HashMap<String, STVal>();

    private void error(String message) {
        throw new Error(message);
    }

    private void insertNode(String name, BaseType type) {
        if (!SymbolTableVisitor.ST.containsKey(name)) {
            SymbolTableVisitor.ST.put(name, new STVal(type));
            //SymbolTableVisitor.ST.get(name).parentRule = type;
        } else {
            error("An id with the name \"" + name + "\" has already been declared");
        }
    }

    private void insertColNode(String name, BaseType type, String parentName) {
        if (!SymbolTableVisitor.ST.containsKey(name)) {
            SymbolTableVisitor.ST.put(name, new STVal(type));
            SymbolTableVisitor.ST.get(name).parentRule = parentName;
        } else {
            if (SymbolTableVisitor.ST.get(name).parentRule.equals(parentName)
                    && !SymbolTableVisitor.ST.get(name).type.contains(type)) {
                SymbolTableVisitor.ST.get(name).type.add(type);
            }
        }
    }

    private String getRuleName(Node parentRule, SimpleNode data){
        while (!(parentRule instanceof RULE) && !(parentRule instanceof COLRULE)) {
            parentRule = parentRule.jjtGetParent();
        }
        SimpleNode ruleNode = parentRule.jjtGetChild(0).jjtAccept(this, data);
        String parentName = ruleNode.value.toString();

        return parentName;
    }

    @Override
    public SimpleNode visit(SimpleNode node, SimpleNode data) {
        // TODO Auto-generated method stub
        return data;
    }

    @Override
    public SimpleNode visit(PROG node, SimpleNode data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++){
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return data;
    }

    @Override
    public SimpleNode visit(IMPORT node, SimpleNode data) {
        SimpleNode tableNode = node.jjtGetChild(1).jjtAccept(this, data);
        String tableName = tableNode.value.toString();
        insertNode(tableName, new TableType());
        return data;
    }

    @Override
    public SimpleNode visit(IMPOPTIONS node, SimpleNode data) {
        // TODO Auto-generated method stub
        return data;
    }

    @Override
    public SimpleNode visit(FLNM node, SimpleNode data) {
        // TODO Auto-generated method stub
        return data;
    }

    @Override
    public SimpleNode visit(NOHEADERS node, SimpleNode data) {
        // TODO Auto-generated method stub
        return data;
    }

    @Override
    public SimpleNode visit(MODEL node, SimpleNode data) {
        SimpleNode modelNode = node.jjtGetChild(0).jjtAccept(this, data);
        String modelName = modelNode.value.toString();
        insertNode(modelName, new ModelType());

        for (int i = 1; i < node.jjtGetNumChildren(); i++){
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return data;
    }

    @Override
    public SimpleNode visit(COLRULE node, SimpleNode data) {
        SimpleNode idNode = node.jjtGetChild(0).jjtAccept(this, data);
        String idName = idNode.value.toString();
        insertNode(idName, new ColRuleType());

        int numOfChild = node.jjtGetNumChildren();
        if (node.jjtGetChild(1).toString().equals("COLPARTRULE")) {
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
        }
        
        return data;
    }

    @Override
    public SimpleNode visit(COLPARTRULE node, SimpleNode data) {
        SimpleNode idNode = node.jjtGetChild(0).jjtAccept(this, data);
        String idName = idNode.value.toString();
        insertNode(idName, new ColPartRuleType());
        return data;
    }

    @Override
    public SimpleNode visit(PARTRULE node, SimpleNode data) {
        SimpleNode idNode = node.jjtGetChild(0).jjtAccept(this, data);
        String idName = idNode.value.toString();
        insertNode(idName, new PartRuleType());

        String parentName = getRuleName(node, data);
        SimpleNode idExprNode = node.jjtGetChild(1).jjtAccept(this, data);
        String idExprName = idExprNode.value.toString();
        insertColNode(idExprName, idExprNode.type, parentName);
        return data;
    }

    @Override
    public SimpleNode visit(OR node, SimpleNode data) {
        String parentName = getRuleName(node, data);
        SimpleNode firstIdNode = node.jjtGetChild(0).jjtAccept(this, data);

        for (int i = 1; i < node.jjtGetNumChildren(); i++) {
            SimpleNode idNode = node.jjtGetChild(i).jjtAccept(this, data);
            String idName = idNode.value.toString();
            insertColNode(idName, idNode.type, parentName);
        }
        return firstIdNode;
    }

    @Override
    public SimpleNode visit(AND node, SimpleNode data) {
        List<String> idNames = new ArrayList<String>();
        int numChild = node.jjtGetNumChildren();
        String parentName = getRuleName(node, data);
        SimpleNode firstIdNode = node.jjtGetChild(0).jjtAccept(this, data);
        idNames.add(firstIdNode.value.toString());

        for (int i = 1; i < numChild; i++) {
            SimpleNode currNode = node.jjtGetChild(i).jjtAccept(this, data);
            String idName = currNode.value.toString();

            if(!idNames.contains(idName)){
                insertColNode(idName, currNode.type, parentName);
            }

            idNames.add(idName);
        }
        return firstIdNode;
    }

    @Override
    public SimpleNode visit(COLVALEXPR node, SimpleNode data) {
        return node;
    }
    
    @Override
    public SimpleNode visit(WHERE node, SimpleNode data) {
        String parentName = getRuleName(node, data);
        SimpleNode idExprNode = node.jjtGetChild(1).jjtAccept(this, data);
        String idExprName = idExprNode.value.toString();
        insertColNode(idExprName, idExprNode.type, parentName);
        return data;
    }

    @Override
    public SimpleNode visit(SACD node, SimpleNode data) {
        if (node.value.toString().equals("SUM") || node.value.toString().equals("AVG")) {
            node.type = new DecimalType();
        } else {
            node.type = new IntegerType();
        }
        return node;
    }

    @Override
    public SimpleNode visit(ADD node, SimpleNode data) {
        node.type = new DecimalType();
        return node;
    }

    @Override
    public SimpleNode visit(MULT node, SimpleNode data) {
        node.type = new DecimalType();
        return node;
    }

    @Override
    public SimpleNode visit(RULE node, SimpleNode data) {
        SimpleNode idNode = node.jjtGetChild(0).jjtAccept(this, data);
        String idName = idNode.value.toString();
        insertNode(idName, new RuleType());

        String parentName = getRuleName(node, data);
        int numOfChild = node.jjtGetNumChildren();
        if (node.jjtGetChild(1).toString().equals("PARTRULE")) {
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
        } else {
            SimpleNode idExprNode = node.jjtGetChild(1).jjtAccept(this, data);
            String idExprName = idExprNode.value.toString();
            insertColNode(idExprName, idExprNode.type, parentName);
        }
        
        return data;
    }

    @Override
    public SimpleNode visit(VALEXPR node, SimpleNode data) {
        SimpleNode idNode = node.jjtGetChild(0).jjtAccept(this, data);
        SimpleNode typeNode = node.jjtGetChild(1).jjtAccept(this, data);
        idNode.type = typeNode.type;

        if (node.value.toString().equals("CONTAINS")) {
            StringType type = (StringType) typeNode.type;
            type.setOnlyContains(true);
            idNode.type = type;
        } else {
            if (typeNode.type instanceof IntegerType) {
                IntegerType type = (IntegerType) typeNode.type;
                switch (node.value.toString()) {
                    case "<=":
                        type.SetMaxValue(type.equalValue, true);
                        idNode.type = type;
                        break;
                    case ">=":
                        type.SetMinValue(type.equalValue, true);
                        idNode.type = type;
                        break;
                    case "<":
                        type.SetMaxValue(type.equalValue, false);
                        idNode.type = type;
                        break;
                    case ">":
                        type.SetMinValue(type.equalValue, false);
                        idNode.type = type;
                        break;
                    default:
                        break;
                }
            } else if (typeNode.type instanceof DecimalType) {
                IntegerType type = (IntegerType) typeNode.type;
                switch (node.value.toString()) {
                    case "<=":
                        type.SetMaxValue(type.equalValue, true);
                        idNode.type = type;
                        break;
                    case ">=":
                        type.SetMinValue(type.equalValue, true);
                        idNode.type = type;
                        break;
                    case "<":
                        type.SetMaxValue(type.equalValue, false);
                        idNode.type = type;
                        break;
                    case ">":
                        type.SetMinValue(type.equalValue, false);
                        idNode.type = type;
                        break;
                    default:
                        break;
                }
            }
        }

        return idNode;
    }
    //regelnavn: hejsa >= 0
    //           ----------- 
    @Override
    public SimpleNode visit(CONSTRAINTS node, SimpleNode data) {
        switch (node.value.toString()) {
            case "LETTERS":
                node.type = new LetterType();
                break;
            case "INTEGER":
                node.type = new IntegerType();
                break;
            case "DECIMAL":
                node.type = new DecimalType();
                break;
            case "EMPTY":
                EmptyType empty = new EmptyType();
                empty.notFlag = false;
                node.type = empty;                
                break;
            case "NOTEMPTY":
                EmptyType notEmpty = new EmptyType();
                notEmpty.notFlag = true;
                node.type = notEmpty;
                break;
            default:
                break;
        }
        return node;
    }

    @Override
    public SimpleNode visit(STRING node, SimpleNode data) {
        node.type = new StringType(node.jjtGetValue().toString());
        return node;
    }

    @Override
    public SimpleNode visit(INTEGER node, SimpleNode data) {
        node.type = new IntegerType();
        return node;
    }

    @Override
    public SimpleNode visit(FLOATY node, SimpleNode data) {
        node.type = new DecimalType();
        return node;
    }

    @Override
    public SimpleNode visit(ANALYZE node, SimpleNode data) {
        SimpleNode tableNode = node.jjtGetChild(0).jjtAccept(this, data);
        String tableName = tableNode.toString("");

        SimpleNode modelNode = node.jjtGetChild(1).jjtAccept(this, data);
        String modelName = modelNode.toString("");

        if(!SymbolTableVisitor.ST.containsKey(tableName)) {
            error("An id with the name \"" + tableName + "\" has not been declared");
        }else if (!SymbolTableVisitor.ST.containsKey(modelName)) {
            error("An id with the name \"" + modelName + "\" has not been declared");
        }

        node.jjtGetChild(2).jjtAccept(this, data);
        return data;
    }

    @Override
    public SimpleNode visit(ANLZOPTIONS node, SimpleNode data) {
        node.jjtGetChild(1).jjtAccept(this, data);
        return data;
    }

    @Override
    public SimpleNode visit(RULEOPT node, SimpleNode data) {
        ArrayList<String> rules = new ArrayList<String>();

        for (int i = 0; i < node.jjtGetNumChildren(); i++){
            SimpleNode n = node.jjtGetChild(i).jjtAccept(this, data);
            String ruleName = n.toString("");
            rules.add(ruleName);
        }

        for (String str : rules) {
            if(!SymbolTableVisitor.ST.containsKey(str)) {
                error("An id with the name \"" + str + "\" has not been declared");
            }
        }
        return data;
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

}