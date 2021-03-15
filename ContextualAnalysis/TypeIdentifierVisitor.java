package ContextualAnalysis;
import java.util.ArrayList;
import java.util.Arrays;

import SyntaxAnalysis.TreeFiles.*;

public class TypeIdentifierVisitor implements ScannerVisitor {

    public Object visit(ASTIDEN node, Object data) {
        // Figure out type of node and add it to node.type...

        // cases:
        // 1. iden is model
        // 2. iden is a rule
        // 3. iden is a coloumn name
                                                                                                                                                    //      regex, composite data type, number, letter ... or?
                                                                                                                                                    //      regex = "IS" and no OR at the end expr
        //      numbers = only comparative operators, only x IS NUMBER, 
        //      LETTERS = x = "peter", only x IS LETTERS 
        //      NumbersAndLetters = If rules for numbers and letters are combined (eg. comparative operator and compare with string)
        // 4. iden is a table

        String parentName = node.jjtGetParent().toString(); 

        if (parentName == "MODEL"){
            node.type = SimpleNode.MODEL; 
            System.out.println(node.type);
            // update in symbol table
        } else if (parentName == "IMPORT"){
            node.type = SimpleNode.TABLE; 
            System.out.println(node.type);
            // update in symbol table
        } else if (parentName == "RULE") {
            node.type = SimpleNode.RULE;
            System.out.println(node.type);
            // update in symbol table
        } else processColIden(node);

        return null;
    }

    private void processColIden(ASTIDEN n){
        Node parent = n.jjtGetParent();
        String parentName = n.jjtGetParent().toString(); 
        String parentRightNode = n.jjtGetParent().toString();
        ArrayList<String> CompOpr = new ArrayList<String>(Arrays.asList("<", ">", "<=", ">="));
        Node tempNodeL = parent.jjtGetChild(0);
        Node tempNodeR = parent.jjtGetChild(1);
        
        if (CompOpr.contains(parentName)) {
            n.type = SimpleNode.NUMBER; 
            System.out.println(n.type);
        } else if (parentName == "IS"){ // IS kig på højre barn
            n.type = parentRightNode == "NUMBERS" ? SimpleNode.NUMBER : SimpleNode.LETTERS;
            System.out.println(n.type);
        } else if (parentName == "="){ // hvis = højest kig på højre barn
            n.type = parentRightNode.charAt(0) == '"' ?  SimpleNode.LETTERS : SimpleNode.NUMBER;
            System.out.println(n.type);
        } else if (parentName == "OR"){ // hvis OR kig videre...
            while(tempNodeR != null || tempNodeR != null) {
                tempNodeL = tempNodeL.jjtGetChild(0);
                tempNodeR = tempNodeL.jjtGetChild(1);
                if (tempNodeL instanceof ASTIDEN) processColIden((ASTIDEN)tempNodeL);
                if (tempNodeR instanceof ASTIDEN) processColIden((ASTIDEN)tempNodeR);
            }
        }
    }

    @Override
    public Object visit(SimpleNode node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTPROG node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTIMPORT node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTFLNM node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTMODEL node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTRULE node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTOR node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTAND node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTVALEXPR node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTCONSTRAINTS node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTTYPES node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTINTEGER node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTFLOATY node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object visit(ASTANALYZE node, Object data) {
        // TODO Auto-generated method stub
        return null;
    }
}