/* Generated By:JJTree: Do not edit this line. ASTMODEL.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package SyntaxAnalysis.TreeFiles;

public class ASTMODEL extends SimpleNode {
  public ASTMODEL(int id) {
    super(id);
  }

  public ASTMODEL(Scanner p, int id) {
    super(p, id);
  }

  /** Accept the visitor. **/
  public Object jjtAccept(ScannerVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/*
 * JavaCC - OriginalChecksum=6e0289d0a0d57a5c57a8da84dd9093c5 (do not edit this
 * line)
 */
