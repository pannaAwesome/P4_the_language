import java.util.ArrayList;
import java.util.Arrays;
import ContextualAnalysis.*;

public class TypeIdentifierVisitor implements ScannerVisitor {

    public SymbolTableBuilder stb = new SymbolTableBuilder(null);

    public Object visit(ASTIDEN node, Object data) {
        // Figure out type of node and add it to node.type...
        // cases:
        // 1. iden is model
        // 2. iden is a rule
        // 3. iden is a coloumn name
        // regex, composite data type, number, letter ... or?
        // regex = "IS" and no OR at the end expr
        // numbers = only comparative operators, only x IS NUMBER,
        // LETTERS = x = "peter", only x IS LETTERS
        // NumbersAndLetters = If rules for numbers and letters are combined (eg.
        // comparative operator and compare with string)
        // 4. iden is a table

        // If the parent of an identifier is ANALYZE, then the identifier is being refrenced, not declared
        // Insert into symbol table, if declaration
        // made symbol table static  

        String parentName = node.jjtGetParent().toString();
        // System.out.println("parent name is " + parentName);

        if (parentName.equals("MODEL")) {
            node.type = SimpleNode.MODEL;
            //System.out.println("Parent name is " + parentName + " and node type is: " + node.type);
            // System.out.println("node type is: " + node.type);
            // update in symbol table
            stb.insertNode(node.id, node.type);
        } else if (parentName.equals("IMPORT")) {
            node.type = SimpleNode.TABLE;
            //System.out.println("Parent name is " + parentName + " and node type is: " + node.type);
            // System.out.println("node type is: " + node.type);
            // update in symbol table
        } else if (parentName.equals("RULE")) {
            node.type = SimpleNode.RULE;
            //System.out.println("Parent name is " + parentName + " and node type is: " + node.type);
            // System.out.println("node type is: " + node.type);
            // update in symbol table
        } else {processColIden(node);} 

        return null;
    }

    private void processColIden(ASTIDEN n) {

        // find øverste parent hvilket er rule og tag dets barn
        // virker pt ikke, fordi refs kommer herned
        Node parent = n;
        while(!parent.jjtGetParent().toString("").equals("RULE")){
            parent = parent.jjtGetParent();
            System.out.println(parent.toString(""));
        }

        String parentName = n.jjtGetParent().toString("");
        String parentRightNodeName = parent.jjtGetChild(1).toString("");
        //ArrayList<String> CompOpr = new ArrayList<String>(Arrays.asList("<", ">", "<=", ">="));
        Node tempNodeL = parent.jjtGetChild(0);
        Node tempNodeR = parent.jjtGetChild(1);
        
        //System.out.println("parentName is: "+parentName);

        if (parentName.equals("<") || parentName.equals(">") || parentName.equals(">=") || parentName.equals("<=")) {
            n.type = SimpleNode.NUMBER;
            //System.out.println("CompOpr: Parent is " + parentName + " and node type is: " + n.type);
            
        } else if (parentName.equals("IS")) { // IS kig på højre barn
            n.type = parentRightNodeName.equals("NUMBERS") ? SimpleNode.NUMBER : SimpleNode.LETTERS;
            //System.out.println("IS: Parent is " + parentName +" parentRightChild is " +parentRightNodeName+ " and node type is: " + n.type);
        } else if (parentName.equals("=")) { // hvis = højest kig på højre barn
            n.type = parentRightNodeName.charAt(0) == '"' ? SimpleNode.LETTERS : SimpleNode.NUMBER;
            //System.out.println("=:Parent is " + parentName + " and node type is: " + n.type);
        } else if (parentName.equals("OR")) { // hvis OR kig videre...
            while (tempNodeR != null || tempNodeR != null) {
                tempNodeL = tempNodeL.jjtGetChild(0);
                tempNodeR = tempNodeL.jjtGetChild(1);
                if (tempNodeL instanceof ASTIDEN)
                    processColIden((ASTIDEN) tempNodeL);
                if (tempNodeR instanceof ASTIDEN)
                    processColIden((ASTIDEN) tempNodeR);
            }
        }
    }

    @Override
    public Object visit(SimpleNode node, Object data) {
        System.out.println("simple node is called");
        return null;
    }

    @Override
    public Object visit(ASTPROG node, Object data) {
        System.out.println("this is prog visit");
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTIMPORT node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTFLNM node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTMODEL node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTRULE node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTOR node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTAND node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTVALEXPR node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTCONSTRAINTS node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTTYPES node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTINTEGER node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTFLOATY node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }

    @Override
    public Object visit(ASTANALYZE node, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return null;
    }
}