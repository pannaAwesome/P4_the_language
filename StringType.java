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
    public boolean compareTypesAnd(String id, BaseType type, SimpleNode parentNode) throws DuplicationException, ConstraintException, RedundantSyntaxException{
        StringType t = (StringType) type;
        String tContainedVal = (t.onlyContains ? t.containValue.get(0) : null);
        String tExactVal = (!t.onlyContains ? t.exactValue.get(0) : null);

        if (this.containValue.size() != 0 && t.onlyContains){ // if both are contained values
            if (this.containValue.contains(tContainedVal)){ // and the contained values are the same
                throw new DuplicationException(id, parentNode, "contain "+ tContainedVal);
            } else {
                this.containValue.add(tContainedVal);
            }
        } else if (this.exactValue.size() != 0 && !t.onlyContains){ // else, if both values are exact
            if (this.exactValue.contains(tExactVal)){ // and they are equal
                throw new DuplicationException(id, parentNode, "be defined as "+"\""+this.exactValue.get(0)+"\""); // also redunant
            } else {
                throw new ConstraintException(id, this.exactValue.get(0), tExactVal, parentNode); // else, you're trying to redeclare the exact value which is constraint error
            }
        } else if (this.containValue.size()!=0 && !t.onlyContains){ // if this has contains values, and the other is an exact
            String tExact = t.exactValue.get(0);
            for (String contained : containValue) {
                if (tExact.contains(contained)) {
                    throw new RedundantSyntaxException(id, parentNode); // den eksakte værdi er delvist ens med contain værdierne
                }
            }
            throw new ConstraintException(id, t.exactValue.get(0), parentNode); // den eksakte værdi er ikke delvist ens med contain værdierne
        } else if (this.exactValue.size()==0 && t.onlyContains){ // if this has equal values, and the other is an contains
            String tContained = t.containValue.get(0);
            for (String exact : exactValue) {
                if (exact.contains(tContained)) {
                    throw new RedundantSyntaxException(id, parentNode); // de eksakte værdier er delvist ens med contain værdien
                }
            }
            throw new ConstraintException(id, t.exactValue.get(0), parentNode); // de eksakte værdier er ikke delvist ens med contain værdien
        }

        return true; 
    }

    public boolean compareTypesOr(String id, BaseType type, SimpleNode parentNode) throws DuplicationException{
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
