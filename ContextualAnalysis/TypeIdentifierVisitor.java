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

        String parentName = node.jjtGetParent().toString();

        if (parentName.equals("ANALYZE")) {
            try {
                SymbolTableBuilder.getNodeType(node.jjtGetValue().toString());
            } catch (Exception e) {
                // ikke smid error, bare return, angiv error i typechecking 
                System.out.println(e);
            }
            return null;
        }

        if (parentName.equals("MODEL")) {
            node.type = SimpleNode.MODEL;
        } else if (parentName.equals("IMPORT")) {
            node.type = SimpleNode.TABLE;
        } else if (parentName.equals("RULE")) {
            node.type = SimpleNode.RULE;
        } else {
            node.type = processColIden(node);
        }

        if (!SymbolTableBuilder.hasNodeBeenInserted(node.jjtGetValue().toString())) {
            //System.out.println("Name: " + node.jjtGetValue().toString() + " Type: " + node.type);
            SymbolTableBuilder.insertNode(node.jjtGetValue().toString(), node.type);
        }
        return null;
    }

    private int processColIden(ASTIDEN n) {
        Node topParent = findTopParentInRule(n);

        if (topParent.toString("").equals("OR")) { // hvis OR kig videre...
            n.type = processOrStatement(n, topParent);
        } else {
            n.type = processCol(n);
        }

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
        } else if (parentName.equals("IS")) { // IS kig på højre barn
            n.type = nParentRightChildName.equals("NUMBERS") ? SimpleNode.NUMBER : SimpleNode.LETTERS;
        } else if (parentName.equals("=")) { // hvis = højest kig på højre barn
            n.type = nParentRightChildName.charAt(0) == '"' ? SimpleNode.String : SimpleNode.NUMBER;
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
        int result = -1;
        findORIdens(topParent);
        String nToComp = ((ASTIDEN)n).jjtGetValue().toString();

        for (ASTIDEN node : astIdenList) {
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
        }
        
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