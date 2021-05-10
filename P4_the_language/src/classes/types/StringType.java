package src.classes.types;
import java.util.ArrayList;
import java.util.List;

import src.classes.scanner.*;
import src.classes.exceptions.*;
import src.classes.visitors.*;

public class StringType extends BaseType {
    public List<String> exactValue = new ArrayList<String>();
    public List<String> containValue = new ArrayList<String>();
    public boolean onlyContains = false;

    public StringType() {
        super(1);
    }

    public void setStringValues(String operator, String newValue) {
        onlyContains = operator.equals("CONTAINS");
        newValue = newValue.replace("\"", "");
        if (onlyContains) {
            containValue.add(newValue);
        } else {
            exactValue.add(newValue);
        }
    }
    
    public String toString() {
        return "String";
    }

    private String printAllValues(String type) {
        List<String> values = (type.equals("exact") ? exactValue : containValue);
        String valueNames = "";

        for (int i = 0; i < values.size(); i++) {
            if (i == 0) {
                valueNames += values.get(i);
            } else {
                valueNames += "\" and \"" + values.get(i);
            }
        }

        return valueNames;
    }

    @Override
    public boolean compareTypesAnd(String id, BaseType type, SimpleNode parentNode) throws DuplicationException, ConstraintException, RedundantSyntaxException{
        StringType t = (StringType) type;
        String tContainedVal = (t.onlyContains ? t.containValue.get(0) : null);
        String tExactVal = (!t.onlyContains ? t.exactValue.get(0) : null);

        if (this.containValue.size() != 0 && t.onlyContains){ // if both are contained values
            if (this.containValue.contains(tContainedVal)){ // and the contained values are the same
                TypeCheckVisitor.warning++;
                throw new DuplicationException(id, parentNode, "to contain", tContainedVal);
            } 
            for (String string : this.containValue) {
                if (string.contains(tContainedVal)){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, string, tContainedVal, "ContainsContained");
                } else if (tContainedVal.contains(string)){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, string, tContainedVal, "ContainsContained");
                }
            }   
            this.containValue.add(tContainedVal);
            return true;
            
        } else if (this.exactValue.size() != 0 && !t.onlyContains){ // else, if both values are exact
            if (this.exactValue.contains(tExactVal)){ // and they are equal
                TypeCheckVisitor.warning++;
                throw new DuplicationException(id, parentNode, "as", tExactVal); // also redunant
            } else {
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, printAllValues("exact"), tExactVal, parentNode); // else, you're trying to redeclare the exact value which is constraint error
            }
        } else if (this.containValue.size() != 0 && !t.onlyContains){ // if this has contains values, and the other is an exact
            for (String contained : containValue) {
                if (tExactVal.contains(contained)) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, contained, tExactVal); // den eksakte værdi er delvist ens med contain værdierne
                }
            }
            throw new ConstraintException(id, "to contain", printAllValues("contain"), "to be", tExactVal, parentNode); // den eksakte værdi er ikke delvist ens med contain værdierne
        } else if (this.exactValue.size() != 0 && t.onlyContains){ // if this has equal values, and the other is an contains
            for (String exact : exactValue) {
                if (exact.contains(tContainedVal)) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, tContainedVal, exact); // de eksakte værdier er delvist ens med contain værdien
                }
            }
            TypeCheckVisitor.error++;
            throw new ConstraintException(id, "as", printAllValues("exact"), "to contain", tContainedVal, parentNode); // de eksakte værdier er ikke delvist ens med contain værdien
        }

        if (t.containValue.size() != 0) {
            this.containValue.add(tContainedVal);
        } else {
            this.exactValue.add(tExactVal);
        }

        return true; 
    }

    public boolean compareTypesOr(String id, BaseType type, SimpleNode parentNode) throws DuplicationException, RedundantSyntaxException{
        StringType t = (StringType) type;
        if (this.containValue.size()!=0 && t.onlyContains){ // if both are contains
            if (this.containValue.contains(t.containValue.get(0))){ // and values are the same
                TypeCheckVisitor.warning++;
                throw new DuplicationException(id, parentNode, "to contain", t.containValue.get(0)); // redundant
            } 
            String tContainedVal = t.containValue.get(0);
            for (String string : this.containValue) {
                if (string.contains(tContainedVal)){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, string, tContainedVal, "ContainsContained");
                } else if (tContainedVal.contains(string)){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, string, tContainedVal, "ContainsContained");
                }
            } 
            this.containValue.add(t.containValue.get(0));
            
        } else if (this.exactValue.size()!=0 && !t.onlyContains){ // if both are exact
            if (this.exactValue.contains(t.exactValue.get(0))) { // if they are the same 
                TypeCheckVisitor.warning++;
                throw new DuplicationException(id, parentNode, "as", t.exactValue.get(0)); 
            } else {
                this.exactValue.add(t.exactValue.get(0));
            }
        } else if (this.containValue.size()!=0 && !t.onlyContains){ // if this contains, and other exact
            String tExactVal = t.exactValue.get(0);
            for (String string : this.containValue) {
                if (tExactVal.contains(string)){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, tExactVal, string, 1);
                }
            } 
            this.exactValue.add(t.exactValue.get(0));
        } else if (this.exactValue.size()!=0 && t.onlyContains){ // if this has exact, and other contains
            String tContainVal = t.containValue.get(0);
            for (String string : this.exactValue) {
                if (string.contains(tContainVal)){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, string, tContainVal, 1);
                }
            }
            this.containValue.add(t.containValue.get(0));
        }
        return true;
    }
}
