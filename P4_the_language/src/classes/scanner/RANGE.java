/* Generated By:JJTree: Do not edit this line. RANGE.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package src.classes.scanner;

public
class RANGE extends SimpleNode {
  public RANGE(int id) {
    super(id);
  }

  public RANGE(Scanner p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public SimpleNode jjtAccept(ScannerVisitor visitor, SimpleNode data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=375bfb2969b4845915a342ea04147f90 (do not edit this line) */