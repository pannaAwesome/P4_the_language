package ContextualAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import SyntaxAnalysis.TreeFiles.*;

public class TypeIdentifierVisitor implements ScannerVisitor {

    private List<ASTIDEN> astIdenList;

    public TypeIdentifierVisitor() {
        astIdenList = new ArrayList<ASTIDEN>();
    }

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

        // If the parent of an identifier is ANALYZE, then the identifier is being
        // refrenced, not declared
        // Insert into symbol table, if declaration
        // made symbol table static

        String parentName = node.jjtGetParent().toString();

        if (parentName.equals("ANALYZE")) {
            try {
                SymbolTableBuilder.getNodeType(node.jjtGetValue().toString());
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }
        //System.out.println("parent name is " + parentName);

        if (parentName.equals("MODEL")) {
            node.type = SimpleNode.MODEL;
            // System.out.println("Parent name is " + parentName + " and node type is: " +
            // node.type);
            // System.out.println("node type is: " + node.type);
            // update in symbol table
        } else if (parentName.equals("IMPORT")) {
            node.type = SimpleNode.TABLE;
            // System.out.println("Parent name is " + parentName + " and node type is: " +
            // node.type);
            // System.out.println("node type is: " + node.type);
            // update in symbol table
        } else if (parentName.equals("RULE")) {
            node.type = SimpleNode.RULE;
            // System.out.println("Parent name is " + parentName + " and node type is: " +
            // node.type);
            // System.out.println("node type is: " + node.type);
            // update in symbol table
        } else {
            node.type = processColIden(node);
        }

        if (!SymbolTableBuilder.hasNodeBeenInserted(node.jjtGetValue().toString())) {
            System.out.println("Name: " + node.jjtGetValue().toString() + " Type: " + node.type);
            SymbolTableBuilder.insertNode(node.jjtGetValue().toString(), node.type);
        }
        return null;
    }

    private int processColIden(ASTIDEN n) {

        // find øverste parent hvilket er rule og tag dets barn
        // virker pt ikke, fordi refs kommer herned
        // Node topParent = n;
        Node topParent = findTopParentInRule(n);

        // ArrayList<String> CompOpr = new ArrayList<String>(Arrays.asList("<", ">",
        // "<=", ">="));

        // System.out.println("parentName is: "+parentName);
            // bliver ved med at kigge på top parent, skal den ikke, når den bliver kaldt med OR's børn
        // Mangler håndtering af AND
        if (topParent.toString("").equals("OR")) { // hvis OR kig videre...
            //System.out.println("OR is being proccessed");
            n.type = processOrStatement(n, topParent);
        } else {
            n.type = processCol(n);
        }
        // while (tempNodeR != null || tempNodeR != null) {
        // tempNodeL = tempNodeL.jjtGetChild(0);
        // tempNodeR = tempNodeL.jjtGetChild(1);
        // if (tempNodeL instanceof ASTIDEN)
        // processColIden((ASTIDEN) tempNodeL);
        // if (tempNodeR instanceof ASTIDEN)
        // processColIden((ASTIDEN) tempNodeR);
        // }

        return n.type;
    }

    private int processCol(ASTIDEN n){
        Node nParent = n.jjtGetParent();
        String parentName = nParent.toString("");
        Node nParentRightChild = nParent.jjtGetChild(1);
        String nParentRightChildName = nParentRightChild.toString("");

        if (parentName.equals("<") || parentName.equals(">") || parentName.equals(">=")
                || parentName.equals("<=")) {
            n.type = SimpleNode.NUMBER;
            // System.out.println("Parent is " + parentName + " and node type is: " +
            // n.type);

        } else if (parentName.equals("IS")) { // IS kig på højre barn
            n.type = nParentRightChildName.equals("NUMBERS") ? SimpleNode.NUMBER : SimpleNode.LETTERS;
            // System.out.println("Parent is " + parentName +" parentRightChild is "
            // +nParentRightChildName+ " and node type is: " + n.type);
        } else if (parentName.equals("=")) { // hvis = højest kig på højre barn
            n.type = nParentRightChildName.charAt(0) == '"' ? SimpleNode.String : SimpleNode.NUMBER;
            // System.out.println("Parent is " + parentName + " parentRightChild is "
            // +nParentRightChildName+ " and node type is: " + n.type);
        }

        return n.type;
    }

    private Node findTopParentInRule(Node n) {
        while (!n.jjtGetParent().toString("").equals("RULE")) {
            n = n.jjtGetParent();
        }
        return n;
    }

    private int processOrStatement(Node n, Node topParent) {
        //System.out.println("OR is being proccessed still...");
        // Node topParentL = topParent.jjtGetChild(0);
        // Node topParentR = topParent.jjtGetChild(1);
        int result = -1;
        findORIdens(topParent);
        String nToComp = ((ASTIDEN)n).jjtGetValue().toString();
        //System.out.println("First name is: "+firstElemName);

        // alder, height, alder
        // alder
        // alder IS LETTERS == LETTERS
        // alder IS LETTERS == LETTERS

        for (ASTIDEN node : astIdenList) {
            //System.out.println("firstElem: "+firstElemName+" nodeName: "+node.jjtGetValue().toString());
            //System.out.println(node.jjtGetValue().toString().equals(firstElemName));
            if (node.jjtGetValue().toString().equals(nToComp)){
                int type = processCol(node);
                if (result == -1)
                    result = type;
                else if (type == 6)
                    result = 6;
                else if ((result == 3 && type == 4) || (result == 4 && type == 3))
                    result = 5;
            }
        }

        astIdenList.clear();
        return result;
    }

    private void findORIdens(Node n) {
        if (n == null)
            return;
        if (n instanceof ASTIDEN) {
            astIdenList.add((ASTIDEN) n);
            //System.out.println("Found something!");
            //System.out.println("Found iden: " + ((ASTIDEN) n).toString(""));
        }
        //System.out.println("Number is children is: " + n.jjtGetNumChildren());
        
        if (n.jjtGetNumChildren() != 0){
            for (int i = 0; i < n.jjtGetNumChildren(); i++){
                findORIdens(n.jjtGetChild(i));
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