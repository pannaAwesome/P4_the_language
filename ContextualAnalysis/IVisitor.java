package ContextualAnalysis;

import SyntaxAnalysis.TreeFiles.*;

public interface IVisitor {
    public void visit(ASTIDEN node);

    public void visit(ASTANALYZE node);

    public void visit(ASTAND node);

    public void visit(ASTCONSTRAINTS node);

    public void visit(ASTFLNM node);

    public void visit(ASTFLOATY node);

    public void visit(ASTIMPORT node);

    public void visit(ASTINTEGER node);

    public void visit(ASTMODEL node);

    public void visit(ASTOR node);

    public void visit(ASTPROG node);

    public void visit(ASTRULE node);

    public void visit(ASTTYPES node);

    public void visit(ASTVALEXPR node);
}
