/* Generated By:JJTree: Do not edit this line. FLNM.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package src.classes.scanner;

public
class FLNM extends SimpleNode {
  public FLNM(int id) {
    super(id);
  }

  public FLNM(Scanner p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public SimpleNode jjtAccept(ScannerVisitor visitor, SimpleNode data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b0bb072a4234044c4258e9cecaebec1e (do not edit this line) */