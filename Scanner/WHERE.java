/* Generated By:JJTree: Do not edit this line. WHERE.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class WHERE extends SimpleNode {
  public WHERE(int id) {
    super(id);
  }

  public WHERE(Scanner p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public SimpleNode jjtAccept(ScannerVisitor visitor, SimpleNode data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b671da735b23a3f3d26ca4429bdcf42c (do not edit this line) */
