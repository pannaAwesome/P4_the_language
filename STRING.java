/* Generated By:JJTree: Do not edit this line. STRING.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class STRING extends SimpleNode {
  public STRING(int id) {
    super(id);
  }

  public STRING(Scanner p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public SimpleNode jjtAccept(ScannerVisitor visitor, SimpleNode data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=e5cda56b250ec5c58ca5ed31e6f85a61 (do not edit this line) */
