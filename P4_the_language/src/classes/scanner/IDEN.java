/* Generated By:JJTree: Do not edit this line. IDEN.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package src.classes.scanner;

public
class IDEN extends SimpleNode {
  public IDEN(int id) {
    super(id);
  }

  public IDEN(Scanner p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public SimpleNode jjtAccept(ScannerVisitor visitor, SimpleNode data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7041d056febadd3e9f249beebf6c9725 (do not edit this line) */