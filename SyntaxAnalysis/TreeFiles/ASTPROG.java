/* Generated By:JJTree: Do not edit this line. ASTPROG.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTPROG extends SimpleNode {
  public ASTPROG(int id) {
    super(id);
  }

  public ASTPROG(Scanner p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ScannerVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5b0afc824faccd122eb4a494aa6f783c (do not edit this line) */
