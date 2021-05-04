package src;
import java.util.ArrayList;
import java.util.List;

public class TypeCheckVisitor implements ScannerVisitor {
    public static int error = 0;
    public static int warning = 0;
    public static int tip = 0;

    public static void printWrongs() {
        String message = "";
        message += (error == 1 ? "Found " + error + " error" : (error > 1 ? "Found " + error + " errors" : ""));
        message += (warning == 1 && message != "" ? ", " + warning + " warning" : (message == "" ? "Found " + warning + " warning" : ""));
        message += (warning > 1 && message != "" ? ", " + warning + " warnings" : (message == "" ? "Found " + warning + " warnings" : ""));
        
        message += (tip == 1 && message != "" ? ", " + tip + " tip" : (message == "" ? "Found " + tip + " tip" : ""));
        message += (tip > 1 && message != "" ? ", " + tip + " tips" : (message == "" ? "Found " + tip + " tips" : ""));

        message += (message != "" ? " in code." : "");
        
        System.out.println(message);
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
        
        return data;
    }

    @Override
    public SimpleNode visit(COLPARTRULE node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 1; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, node);
        }
        return data;
    }

    @Override
    public SimpleNode visit(COLVALEXPR node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 0; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, node);
        }
        return data;
    }
    
    @Override
    public SimpleNode visit(COLOR node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 0; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, node);
        }
        return data;
    }

    @Override
    public SimpleNode visit(COLAND node, SimpleNode data) {
        int numOfChild = node.jjtGetNumChildren();
        for (int i = 0; i < numOfChild; i++) {
            node.jjtGetChild(i).jjtAccept(this, node);
        }
        return data;
    }

    @Override
    public SimpleNode visit(WHERE node, SimpleNode data) {
        if (node.jjtGetChild(0).toString().equals("OR") || node.jjtGetChild(0).toString().equals("AND")) {
            node.jjtGetChild(0).jjtAccept(this, data);
        } else {
            SimpleNode idNode = node.jjtGetChild(0).jjtAccept(this, data);
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
            if (node.jjtGetChild(1).toString().equals("OR") || node.jjtGetChild(1).toString().equals("AND")) {
                node.jjtGetChild(1).jjtAccept(this, data);
            } else {
                SimpleNode idNode = node.jjtGetChild(1).jjtAccept(this, data);
                checkAndColNode(idNode);
            }
        }
        
        return data;
    }

    @Override
    public SimpleNode visit(PARTRULE node, SimpleNode data) {
        if (node.jjtGetChild(1).toString().equals("OR") || node.jjtGetChild(1).toString().equals("AND")) {
            node.jjtGetChild(1).jjtAccept(this, data);
        } else {
            SimpleNode idNode = node.jjtGetChild(1).jjtAccept(this, data);
            checkOrColNode(idNode);
        }
        return data;
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
        int numOfChild = node.jjtGetNumChildren();
        try {
            for (int i = 0; i < numOfChild; i++) {
                if (node.jjtGetChild(i).toString().equals("AND")) {
                    node.jjtGetChild(i).jjtAccept(this, data);
                } else {
                    SimpleNode idNode = node.jjtGetChild(i).jjtAccept(this, data);
                    checkOrColNode(idNode);
                }              
            }

            if (numOfChild > 2) {
                SimpleNode parentRule = getRule(node);
                String ruleName = parentRule.jjtGetChild(0).jjtAccept(this, data).value.toString();
                if (parentRule instanceof PARTRULE) {
                    TypeCheckVisitor.tip++;
                    throw new TipException(parentRule, "partrule", ruleName, true);
                } else {
                    TypeCheckVisitor.tip++;
                    throw new TipException(parentRule, "rule", ruleName, false);
                }            
            }
        } catch (TipException e) {
            System.out.println(e.getMessage());            
        }
        
        return data;
    }

    @Override
    public SimpleNode visit(AND node, SimpleNode data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            if (node.jjtGetChild(i).toString().equals("OR")) {
                node.jjtGetChild(i).jjtAccept(this, data);
            } else {
                SimpleNode idNode = node.jjtGetChild(i).jjtAccept(this, data);
                checkAndColNode(idNode);
            }
        }
        return data;
    }

    @Override
    public SimpleNode visit(SACD node, SimpleNode data) {
        if (node.value.toString().equals("SUM") || node.value.toString().equals("AVG")) {
            SimpleNode idNode = node.jjtGetChild(0).jjtAccept(this, data);
            checkCalcExprNode(idNode, node, data);
            node.type = new DecimalType();
        } else {
            node.type = new IntegerType();
        }
        return node;
    }

    @Override
    public SimpleNode visit(ADD node, SimpleNode data) {
        for (int i = node.jjtGetNumChildren(); i > 0; i--) {
            SimpleNode currNode = node.jjtGetChild(i-1).jjtAccept(this, node);
            checkCalcExprNode(currNode, node, node);
        }
        node.type = new DecimalType();
        return node;
    }

    @Override
    public SimpleNode visit(MULT node, SimpleNode data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode currNode = node.jjtGetChild(i).jjtAccept(this, node);
            checkCalcExprNode(currNode, node, node);
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
                EmptyType empty = new EmptyType(false);
                node.type = empty;                
                break;
            case "NOTEMPTY":
                EmptyType notEmpty = new EmptyType(true);
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

        try {
            if(!SymbolTableVisitor.ST.get(tableName).type.contains(new TableType())) {
                STVal types = SymbolTableVisitor.ST.get(tableName);
                TypeCheckVisitor.error++;
                throw new TypeException(tableName, types, new TableType(), node);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            if (!SymbolTableVisitor.ST.get(modelName).type.contains(new ModelType())) {
                STVal types = SymbolTableVisitor.ST.get(modelName);
                TypeCheckVisitor.error++;
                throw new TypeException(modelName, types, new ModelType(), node);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int numOfChild = node.jjtGetNumChildren();
        if (numOfChild == 3) {
            node.jjtGetChild(2).jjtAccept(this, node);
        }
        return data;
    }

    @Override
    public SimpleNode visit(ANLZOPTIONS node, SimpleNode data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        return data;
    }

    @Override
    public SimpleNode visit(RULEOPT node, SimpleNode data) {
        try {
            for (int i = 0; i < node.jjtGetNumChildren(); i++){
                SimpleNode idNode = node.jjtGetChild(i).jjtAccept(this, data);
                String idName = idNode.value.toString();
                STVal types = SymbolTableVisitor.ST.get(idName);
                if (!types.type.contains(new RuleType())) {
                    TypeCheckVisitor.error++;
                    throw new TypeException(idName, types, new RuleType(), data);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
                SimpleNode parentNode = getRule(idNode);
                if(parentNode instanceof RULE || parentNode instanceof PARTRULE) {
                    idTypes.type.get(index).compareTypesAnd(idName,type, parentNode);
                }
            } else {
                SimpleNode parentNode = getRule(idNode);
                TypeCheckVisitor.error++;
                throw new TypeException(idName, idTypes, type, parentNode);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkOrColNode(SimpleNode idNode) {
        try {
            BaseType type = idNode.type;
            String idName = idNode.value.toString();
            STVal idTypes = SymbolTableVisitor.ST.get(idName);
            if (idTypes.type.contains(type)) {
                int index = idTypes.type.indexOf(type);
                SimpleNode parentNode = getRule(idNode);
                if(parentNode instanceof RULE || parentNode instanceof PARTRULE) {
                    idTypes.type.get(index).compareTypesOr(idName,type, parentNode);
                }
            } else {
                SimpleNode parentNode = getRule(idNode);
                TypeCheckVisitor.error++;
                throw new TypeException(idName, idTypes, type, parentNode);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void checkCalcExprNode(SimpleNode idNode, SimpleNode currNode, SimpleNode parentNode) {
        try {
            String idName = idNode.value.toString();
            if (SymbolTableVisitor.ST.containsKey(idName)) {
                STVal idTypes = SymbolTableVisitor.ST.get(idName);
                
                if (!idTypes.type.contains(new DecimalType()) && !idTypes.type.contains(new IntegerType()) && !idTypes.type.contains(new EmptyType(true)) && !idTypes.type.contains(new EmptyType(false))) {
                    SimpleNode ruleNode = getRule(parentNode);
                    if (currNode instanceof ADD || currNode instanceof MULT) {
                        TypeCheckVisitor.error++;
                        throw new TypeException(idName, ruleNode, "num");
                    } else {
                        TypeCheckVisitor.error++;
                        throw new TypeException(idName, ruleNode, currNode.value.toString().toLowerCase());
                    }
                    
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private SimpleNode getRule(Node parentRule){
        while (!(parentRule instanceof RULE) 
            && !(parentRule instanceof COLRULE) 
            && !(parentRule instanceof PARTRULE) 
            && !(parentRule instanceof COLPARTRULE)) {
            parentRule = parentRule.jjtGetParent();
        }

        SimpleNode parentNode = (SimpleNode) parentRule;
        
        return parentNode;
    }
}
