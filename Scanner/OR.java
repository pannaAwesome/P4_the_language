/* Generated By:JJTree: Do not edit this line. OR.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class OR extends SimpleNode {
  public OR(int id) {
    super(id);
  }

  public OR(Scanner p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public SimpleNode jjtAccept(ScannerVisitor visitor, SimpleNode data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9090317c19c62657c904932d9f0ef37d (do not edit this line) */
