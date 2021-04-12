import java.util.ArrayList;
import java.util.List;

public class TypeCheckVisitor implements ScannerVisitor {

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
        // TODO Auto-generated method stub
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
        node.jjtGetChild(0).jjtAccept(this, data);

        for (int i = 1; i < node.jjtGetNumChildren(); i++){
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return data;
    }

    @Override
    public SimpleNode visit(COLRULE node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, node);
        }
        
        return node;
    }

    @Override
    public SimpleNode visit(COLPARTRULE node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, node);
        }
        return node;
    }

    @Override
    public SimpleNode visit(COLVALEXPR node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, node);
        }
        return node;
    }
    
    @Override
    public SimpleNode visit(COLOR node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, node);
        }
        return null;
    }

    @Override
    public SimpleNode visit(COLAND node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, node);
        }
        return null;
    }

    @Override
    public SimpleNode visit(WHERE node, SimpleNode data) {
        if (!node.jjtGetChild(1).toString().equals("OR") || !node.jjtGetChild(1).toString().equals("AND")) {
            node.jjtGetChild(1).jjtAccept(this, data);
        } else {
            SimpleNode idNode = node.jjtGetChild(1).jjtAccept(this, data);
            checkAndColNode(idNode);
        }
        return data;
    }


    @Override
    public SimpleNode visit(RULE node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        if (node.jjtGetChild(1).toString().equals("PARTRULE")) {
            for (int i = 1; i < numOfChild; i++) {
                node.jjtGetChild(i).jjtAccept(this, data);
            }
        }else {
            if (!node.jjtGetChild(1).toString().equals("OR") || !node.jjtGetChild(1).toString().equals("AND")) {
                node.jjtGetChild(1).jjtAccept(this, data);
            } else {
                SimpleNode idNode = node.jjtGetChild(1).jjtAccept(this, data);
                checkAndColNode(idNode);
            }
        }
        
        return node;
    }

    @Override
    public SimpleNode visit(PARTRULE node, SimpleNode data) {
        if (!node.jjtGetChild(1).toString().equals("OR") || !node.jjtGetChild(1).toString().equals("AND")) {
            node.jjtGetChild(1).jjtAccept(this, data);
        } else {
            SimpleNode idNode = node.jjtGetChild(1).jjtAccept(this, data);
            checkOrColNode(idNode);
        }
        return node;
    }

    @Override
    public SimpleNode visit(VALEXPR node, SimpleNode data) {
        SimpleNode idNode = node.jjtGetChild(0).jjtAccept(this, data);
        SimpleNode typeNode = node.jjtGetChild(1).jjtAccept(this, node);
        idNode.type = typeNode.type;

        return idNode;
    } 

    @Override
    public SimpleNode visit(OR node, SimpleNode data) {
        try {
            int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            SimpleNode idNode = node.jjtGetChild(i).jjtAccept(this, data);
            checkOrColNode(idNode);
        }

        if (numOfChild > 2) {
            SimpleNode parentRule = getRule(node, data);
            String ruleName = parentRule.jjtGetChild(0).jjtAccept(this, data).value.toString();
            if (parentRule instanceof PARTRULE) {
                throw new TipException(parentRule, "partrule", ruleName);
            } else {
                throw new TipException(parentRule, "rule", ruleName);
            }            
        }
        } catch (TipException e) {
            System.err.println(e.getMessage());
        }
        
        return data;
    }

    @Override
    public SimpleNode visit(AND node, SimpleNode data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode currNode = node.jjtGetChild(i).jjtAccept(this, data);
            checkAndColNode(currNode);
        }
        return data;
    }

    @Override
    public SimpleNode visit(SACD node, SimpleNode data) {
        if (node.value.toString().equals("SUM") || node.value.toString().equals("AVG")) {
            SimpleNode idNode = node.jjtGetChild(0).jjtAccept(this, data);
            checkCalcExprNode(idNode, node);
            node.type = new DecimalType();
        } else {
            node.type = new IntegerType();
        }
        return node;
    }

    @Override
    public SimpleNode visit(ADD node, SimpleNode data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode currNode = node.jjtGetChild(i).jjtAccept(this, data);
            checkCalcExprNode(currNode, node);
        }
        node.type = new DecimalType();
        return node;
    }

    @Override
    public SimpleNode visit(MULT node, SimpleNode data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode currNode = node.jjtGetChild(i).jjtAccept(this, data);
            checkCalcExprNode(currNode, node);
        }
        node.type = new DecimalType();
        return node;
    }
    
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
        StringType type = new StringType();
        String operator = data.value.toString();
        type.setStringValues(operator, node.value.toString());
        node.type = type;
        return node;
    }

    @Override
    public SimpleNode visit(INTEGER node, SimpleNode data) {
        IntegerType type = new IntegerType();
        int number = Integer.parseInt(node.value.toString());
        String operator = data.value.toString();
        type.SetValue(operator, number);
        node.type = type;
        return node;
    }

    @Override
    public SimpleNode visit(FLOATY node, SimpleNode data) {
        DecimalType type = new DecimalType();
        double number = Double.parseDouble(node.value.toString());
        String operator = data.value.toString();
        type.SetValue(operator, number);
        node.type = type;
        return node;
    }

    @Override
    public SimpleNode visit(ANALYZE node, SimpleNode data) {
        SimpleNode tableNode = node.jjtGetChild(0).jjtAccept(this, data);
        String tableName = tableNode.toString("");

        SimpleNode modelNode = node.jjtGetChild(1).jjtAccept(this, data);
        String modelName = modelNode.toString("");

        if(!SymbolTableVisitor.ST.containsKey(tableName)) {
            //error("An id with the name \"" + tableName + "\" has not been declared");
        }else if (!SymbolTableVisitor.ST.containsKey(modelName)) {
            //error("An id with the name \"" + modelName + "\" has not been declared");
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
                //error("An id with the name \"" + str + "\" has not been declared");
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

    private void checkAndColNode(SimpleNode idNode){
        try {
            BaseType type = idNode.type;
            String idName = idNode.value.toString();
            STVal idTypes = SymbolTableVisitor.ST.get(idName);
            if (idTypes.type.contains(type)) {
                int index = idTypes.type.indexOf(type);
                idTypes.type.get(index).compareTypesAnd(type);
            } else {
                SimpleNode parentNode = getRule(idNode, null);
                throw new TypeException(idName, idTypes, type, idNode);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void checkOrColNode(SimpleNode idNode) {
        try {
            BaseType type = idNode.type;
            String idName = idNode.value.toString();
            STVal idTypes = SymbolTableVisitor.ST.get(idName);
            if (idTypes.type.contains(type)) {
                int index = idTypes.type.indexOf(type);
                idTypes.type.get(index).compareTypesOr(type);
            } else {
                SimpleNode parentNode = getRule(idNode, null);
                throw new TypeException(idName, idTypes, type, parentNode);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void checkCalcExprNode(SimpleNode idNode, SimpleNode currNode) {
        try {
            String idName = idNode.value.toString();
            if (SymbolTableVisitor.ST.containsKey(idName)) {
                STVal idTypes = SymbolTableVisitor.ST.get(idName);

                if (idTypes.type.contains(new LetterType()) || idTypes.type.contains(new StringType()) || idTypes.type.contains(new EmptyType())) {
                    SimpleNode parentNode = getRule(currNode, null);
                    throw new TypeException(idName, parentNode, currNode.value.toString().toLowerCase());
                }
            }
            
        } catch (TypeException e) {
            System.err.println(e.getMessage());
        }
    }

    private SimpleNode getRule(Node parentRule, SimpleNode data){
        while (!(parentRule instanceof RULE) 
            && !(parentRule instanceof COLRULE) 
            && !(parentRule instanceof PARTRULE) 
            && !(parentRule instanceof COLPARTRULE)) {
            parentRule = parentRule.jjtGetParent();
        }

        SimpleNode parentNode = parentRule.jjtAccept(this, data);
        
        return parentNode;
    }
}
