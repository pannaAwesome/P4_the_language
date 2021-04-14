import java.util.ArrayList;
import java.util.List;

public class StringType extends BaseType {
    public List<String> exactValue = new ArrayList<String>();
    public List<String> containValue = new ArrayList<String>();
    public boolean onlyContains = false;

    public StringType() {
        super(1);
    }

    public void setStringValues(String operator, String newValue) {
        onlyContains = operator.equals("CONTAINS");
        if (onlyContains) {
            containValue.add(newValue);
        } else {
            exactValue.add(newValue);
        }
    }
    
    public String toString() {
        return "String";
    }

    @Override
    public boolean compareTypesAnd(String id, BaseType type, SimpleNode parentNode) throws Exception{
        StringType t = (StringType) type;
        String tContainedVal = t.onlyContains ? t.containValue.get(0) : null;
        String tExactVal = !t.onlyContains ? t.exactValue.get(0) : null;

        if (this.containValue.size()!=0 && t.onlyContains){ // if both are contained values
            if (this.containValue.contains(tContainedVal)){ // and the contained values are the same
                throw new DuplicationException(id, parentNode, "contain "+ tContainedVal);
            } else {
                this.containValue.add(t.containValue.get(0));
            }
        } else if (this.exactValue.size()!=0 && !t.onlyContains){ // else, if both values are exact
            if (this.exactValue.contains(tExactVal)){ // and they are equal
                throw new DuplicationException(id, parentNode, "be defined as "+"\""+this.exactValue.get(0)+"\""); // also redunant
            } else {
                throw new ConstraintException(id, this.exactValue.get(0), t.exactValue.get(0), parentNode); // else, you're trying to redeclare the exact value which is constraint error
            }
        } else if (this.containValue.size()!=0 && !t.onlyContains){ // if this has contains values, and the other is an exact
            if (t.containValue.contains(t.exactValue.get(0))){ // and they are the same 
                throw new RedundantSyntaxException(id, parentNode); // redudants error
            }  else {
                throw new ConstraintException(id, t.exactValue.get(0), parentNode);
            }
        } else if (this.exactValue.size()==0 && t.onlyContains){ // same as above
            if (t.exactValue.contains(t.containValue.get(0))){ // and they are the same 
                throw new RedundantSyntaxException(id, parentNode); // redudants error
            }  else {
                throw new ConstraintException(id, t.exactValue.get(0), parentNode);
            }
        }

        return true; 
    }

    public boolean compareTypesOr(String id, BaseType type, SimpleNode parentNode) throws Exception{
        StringType t = (StringType) type;
        if (this.containValue.size()!=0 && t.onlyContains){ // if both are contains
            if (this.containValue.contains(t.containValue.get(0))){ // and values are the same
                throw new DuplicationException(id, parentNode, "contain "+t.containValue.get(0)); // redundant
            } else {
                this.containValue.add(t.containValue.get(0));
            }
        } else if (this.exactValue.size()!=0 && !t.onlyContains){ // if both are exact
            if (this.exactValue.contains(t.exactValue.get(0))) { // if they are the same 
                throw new DuplicationException(id, parentNode, "be defined as "+"\"" + t.exactValue.get(0) +"\""); 
            } else {
                this.exactValue.add(t.exactValue.get(0));
            }
        }
        return true;
    }
}
