package ContextualAnalysis;

import SyntaxAnalysis.TreeFiles.ASTIDEN;

public class TypeCheckVisitor implements IVisitor {

    public void visit(ASTIDEN node) {
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
    }
}
