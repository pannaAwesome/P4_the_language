package src.classes.types;
import java.util.ArrayList;
import java.util.List;

import src.classes.scanner.*;
import src.classes.exceptions.*;
import src.classes.visitors.*;
import src.test.unitTests.typeCheck.integerType.and.ConstrainExceptionAndTest;

public class IntegerType extends BaseType {
    public Integer minValue = null;
    public boolean withGivenMinValue = false;
    public Integer maxValue = null;
    public boolean withGivenMaxValue = false;
    public List<Integer> equalValue = new ArrayList<Integer>();

    public IntegerType() {
        super(3);
    }

    private void SetMinValue(int newMinValue, boolean withValue) {
        minValue = newMinValue;
        withGivenMinValue = withValue;
    }

    private void SetMaxValue(int newMaxValue, boolean withValue) {
        maxValue = newMaxValue;
        withGivenMaxValue = withValue;
    }

    private void SetEqualValue(int newEqualValue) {
        equalValue.add(newEqualValue);
    }

    public void SetValue(String operator, int value){
        switch (operator) {
            case ">=":
                SetMinValue(value, true);
                break;
            case ">":
                SetMinValue(value, false);
                break;
            case "<=":
                SetMaxValue(value, true);
                break;
            case "<":
                SetMaxValue(value, false);
                break;
            default:
                SetEqualValue(value);
                break;
        }
    }

    public String toString() {
        return "Integer";
    }

    public String toString(String minOrMaxOrEqual){
        switch (minOrMaxOrEqual){
            case "min": 
                return minValue.toString();
            case "max": 
                return maxValue.toString();
            case "equal": 
                return equalValue.toString();
            default: 
                return "";
        }
    }
    
    @Override
    public boolean compareTypesAnd(String id, BaseType type, SimpleNode parentNode) throws Exception {
        IntegerType t = (IntegerType) type;
        if (ISINTEGER(t)){
            return true;
            //TypeCheckVisitor.warning++;
            //throw new DuplicationException(id, parentNode);
        } else {
            compareMinValue(id, t, parentNode);
            compareMaxValue(id, t, parentNode);
            compareEqualValue(id, t, parentNode);
        }
        return true;
    }

    private void compareMinValue(String id, IntegerType t, SimpleNode parentNode) throws Exception {
        if (this.minValue != null && this.withGivenMinValue) { // >= for den nuværende
            if (t.maxValue != null) { // < for den nye
                if (this.minValue == t.maxValue || this.minValue == t.maxValue-1){ // min og max værdierne er lig hinanden
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue);
                } else if (this.minValue >= t.maxValue){
                    TypeCheckVisitor.warning++;
                    throw new ConstraintException(id, parentNode, t.maxValue, this.minValue);
                }
            } else if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "bigger than or equal to", minValue);
            }else if (t.minValue != null) { // den nye indeholder en minimumsværdi
                if (this.minValue.equals(t.minValue) && t.withGivenMinValue) { // minimum værdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "bigger than or equal to ", this.minValue);
                } else if (t.withGivenMinValue) { // minimum værdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "bigger than or equal to", t.minValue, "bigger than or equal to", minValue);
                } else { // minimum værdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "bigger than", t.minValue, "bigger than or equal to", minValue);
                } 
            }
        } else if (this.minValue != null && !this.withGivenMinValue) { // > for den nuværende
            if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                if (this.minValue == t.equalValue.get(0)){
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "equal to ", t.equalValue.get(0), "bigger than "+this.minValue);
                } else if (t.equalValue.get(0)>this.minValue) {
                    TypeCheckVisitor.error++;
                    throw new RedundantSyntaxException(id, parentNode, t.equalValue.get(0));
                }
            }else if (t.minValue != null) { // den nye indeholder en minimumværdi
                if (this.minValue.equals(t.minValue) && !t.withGivenMinValue) { // minimumværdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "bigger than ", this.minValue);
                } else if (t.withGivenMinValue) { // minimum værdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "bigger than or equal to", t.minValue, "bigger than", minValue);
                } else { // minimum værdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "bigger than", t.minValue, "bigger than or equal to", minValue);
                }
            } else if (t.maxValue != null){ // < den nye indeholder maksværdi
                if (this.minValue >= t.maxValue){
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, t.maxValue, this.minValue);
                } else if (t.maxValue != null && this.minValue == t.maxValue-1) {
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, t.maxValue, this.minValue, t);
                } else if (this.minValue+1 == t.maxValue-1){
                    TypeCheckVisitor.error++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue+1);
                }
            }
        } else if (t.minValue != null) { // alt er okay og derfor kan minimumværdien sættes
            this.SetMinValue(t.minValue, t.withGivenMinValue);
        }
    }

    private void compareMaxValue (String id, IntegerType t, SimpleNode parentNode) throws Exception {
        if (this.maxValue != null && this.withGivenMaxValue) { // <= for den nuværende
            if (t.minValue != null && t.withGivenMinValue) { // >= for den nye
                if (this.maxValue == t.minValue){ // min og max værdierne er lig hinanden
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue);
                } 
            } else if (t.minValue != null){ // > den nye indeholder minværdi
                System.out.println("IntegerType.compareMaxValue()");
                if (this.maxValue <= t.minValue){
                    TypeCheckVisitor.warning++;
                    throw new ConstraintException(id, parentNode, this.maxValue, t.minValue);
                } else {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue);
                }
            } else if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                if (t.equalValue.get(0)<this.maxValue){
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "less than or equal to", this.maxValue);
                } else {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue);
                }
            }else if (t.maxValue != null) { // den nye indeholder en maksimumsværdi
                if (this.maxValue.equals(t.maxValue) && t.withGivenMaxValue) { // maksimumsværdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "less than or equal to ", this.maxValue);
                } else if (t.withGivenMaxValue) { // makismum værdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "less than or equal to", t.maxValue, "less than or equal to", maxValue);
                } else { // maksimumværdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "less than", t.maxValue, "less than or equal to", maxValue);
                }
            }
        } else if (this.maxValue != null && !this.withGivenMaxValue) { // < for den nuværende
            if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "less than", maxValue);
            }else if (t.maxValue != null) { // den nye indeholder en maksimumværdi
                if (this.maxValue.equals(t.maxValue) && !t.withGivenMaxValue) { // maksimumværdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "less than ", this.maxValue);
                } else if (t.withGivenMaxValue) { // maksimumværdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "less than or equal to", t.maxValue, "less than", maxValue);
                }else { // makismumværdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "less than", t.maxValue, "less than or equal to", maxValue);
                }
            }else if (t.minValue != null) { // <
                if (this.maxValue <= t.minValue){
                    TypeCheckVisitor.warning++;
                    throw new ConstraintException(id, parentNode, this.maxValue, t.minValue);
                } else if (this.maxValue-1 == t.minValue) {
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, this.maxValue, t.minValue, t);
                } 
            } 
        } else if (t.maxValue != null) { // alt er okay og derfor kan maksimumværdien sættes
            this.SetMaxValue(t.maxValue, t.withGivenMaxValue);
        }
    }

    private void compareEqualValue (String id, IntegerType t, SimpleNode parentNode) throws Exception {
        if (this.equalValue.size() > 0 && t.equalValue.size() != 0) { // den nuværende og den nye har begge lig med værdier
            if (this.equalValue.contains(t.equalValue.get(0))) { // lig med værdierne er de samme
                TypeCheckVisitor.warning++;
                throw new DuplicationException(id, parentNode, t.equalValue.get(0));
            }else { // lig med værdierne er ikke de samme
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, t.equalValue.get(0), equalValuesToString());
            }    
        } else if (this.equalValue.size() > 0 && t.minValue != null) { // den nye har en minimumværdi
            if (t.withGivenMinValue) { // >=
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "bigger than or equal to ", t.minValue, equalValuesToString());
            } else { // >
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "bigger than ", t.minValue, equalValuesToString());
            }
        } else if (this.equalValue.size() > 0 && t.maxValue != null) { // den nye har en maximumværdi
            if (t.withGivenMaxValue) { // <=
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "less than or equal to ", t.maxValue, equalValuesToString());
            } else { // <
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "less than ", t.maxValue, equalValuesToString());
            }
        } else if (t.equalValue.size() != 0) { // alt er okay og derfor kan lig med værdien sættes
            this.equalValue.add(t.equalValue.get(0));
        }
    }
    
    private String equalValuesToString() {
        String equalValues = "";
        for (int i = 0; i < equalValue.size(); i++) {
            if (i == 0) {
                equalValues += equalValue.get(i).toString();
            } else {
                equalValues += " or " + equalValue.get(i).toString();
            }
        }
        return equalValues;
    }

    @Override
    public boolean compareTypesOr(String id, BaseType type, SimpleNode parentNode) throws DuplicationException, RedundantSyntaxException {
        IntegerType t = (IntegerType) type;
        compareValues(id, t, parentNode);
        return true;
    }

    private void compareValues(String id, IntegerType t, SimpleNode parentNode) throws DuplicationException, RedundantSyntaxException { 
        if (ISINTEGER(this) && ISINTEGER(t)){
            TypeCheckVisitor.warning++;
            throw new DuplicationException(id, parentNode);
        } else if (ISINTEGER(this)){
            String secondArg = "";
            if (t.minValue != null){
                secondArg = t.withGivenMinValue ? "bigger than or equal to "+t.minValue : "bigger than "+t.minValue;
            } else if (t.maxValue != null){
                secondArg = t.withGivenMaxValue ? "less than or equal to "+t.maxValue : "less than "+t.maxValue;
            } else if (t.equalValue.size() != 0){
                secondArg = "equal to "+t.equalValue.get(0);
            }
            TypeCheckVisitor.warning++;
            throw new RedundantSyntaxException(id, parentNode, "Integer", secondArg, t);
        }
        
        if (this.minValue != null && this.withGivenMinValue){ // >= for nuværende
            if (ISINTEGER(t)){
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, "bigger than or equal to "+this.minValue+"", "Integer", t);
            } else if (t.minValue != null && t.withGivenMinValue){ // >= for den nye
                if (this.minValue == t.minValue){
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "bigger than or equal to ", this.minValue);
                } else {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.minValue, "bigger than or equal to ", "or");
                }
            } else if (t.minValue != null){ // > for den nye
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.minValue, "bigger than ", "or");
            } else if (t.maxValue != null && t.withGivenMaxValue){ // <= for nye
                if (this.minValue == t.maxValue){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.maxValue, "less than or equal to ", "or");
                }
            } else if (t.maxValue != null){ // < for den nye
                if (this.minValue == t.maxValue-1){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.maxValue, "less than ", "or");
                }
            } else if (t.equalValue.size() != 0){ // = for den nye
                if (this.minValue == t.equalValue.get(0)){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.equalValue.get(0), "equal to ", "or");
                }
            }
        } else if (this.minValue != null) { // > for nuværende
            if (ISINTEGER(t)){
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, "bigger than "+this.minValue+"", "Integer", t);
            } else if (t.minValue != null){ // > Den nye har minværdi 
                String firstConstrain = this.withGivenMinValue ? "bigger than or equal to " : "bigger than ";
                String secondConstrain = t.withGivenMinValue ? "bigger than or equal to " : "bigger than ";
                if (this.withGivenMinValue == t.withGivenMinValue && this.minValue.equals(t.minValue)){ // minimumværdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, firstConstrain, this.minValue);
                } else { // minimumværdierne er forskellige
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, firstConstrain, t.minValue, secondConstrain, "or");
                }
            } else if (t.maxValue != null && t.withGivenMaxValue) { // <= for den nye
                if (this.minValue==t.maxValue || this.minValue == t.maxValue-1){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than ", t.maxValue, "less than or equal to ", "or");
                }
            } else if(t.maxValue != null){ // < for den nye
                if (this.minValue==t.maxValue || this.minValue==t.maxValue-1){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than ", t.maxValue, "less than ", "or");
                }
            } else if (t.equalValue.size() != 0){ // = for den nye
                int tEqualVal = t.equalValue.get(0);
                if (this.minValue == tEqualVal || this.minValue < tEqualVal){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than ", tEqualVal, "equal to ", "or");
                } 
            }
        } if (this.maxValue != null && this.withGivenMaxValue){ // <= for den nuværende
            if (ISINTEGER(t)){
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, "less than or equal to "+this.maxValue+"", "Integer", t);
            } else if (t.maxValue != null && t.withGivenMaxValue){ // <= for den nye
                if (this.maxValue == t.maxValue){
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "less than or equal to ", this.maxValue);
                } else {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.maxValue, "less than or equal to ", "or");
                }
            } else if (t.maxValue != null) { // < for den nye
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.maxValue, "less than ", "or");
            } else if (t.minValue != null && t.withGivenMinValue){ // >= for den nye
                if (this.maxValue == t.minValue){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.minValue, "bigger than or equal to ", "or");
                }
            } else if (t.minValue != null){ // > for den nye
                if (this.maxValue-1==t.minValue){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.minValue, "bigger than ", "or");
                }
            } else if (t.equalValue.size()!=0){ // = for den nye
                if (this.maxValue == t.equalValue.get(0)){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.equalValue.get(0), "equal to ", "or");
                }
            }
        } else if (this.maxValue != null){ // < for den nuværende
            if (ISINTEGER(t)){
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, "less than "+this.maxValue+"", "Integer", t);
            } else if (t.minValue != null && t.withGivenMinValue){ // >= for den nye
                if (this.maxValue-1==t.minValue){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than ", t.minValue, "bigger than or equal to ", "or");
                }
            } else if (t.minValue != null){ // > for den nye
                if (this.maxValue-1==t.minValue){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than ", t.minValue, "bigger than ", "or");
                }
            } else if (t.maxValue != null){ // < for den nye
                String firstConstrain = this.withGivenMaxValue ? "less than or equal to " : "less than ";
                String secondConstrain = t.withGivenMaxValue ? "less than or equal to " : "less than ";
                if (this.withGivenMaxValue == t.withGivenMaxValue && this.maxValue.equals(t.maxValue)){ // maksimumværdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, firstConstrain, this.maxValue);
                } else { // maksimumværdierne er forskellige
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, firstConstrain, t.maxValue, secondConstrain, "or");
                }
            } else if (t.equalValue.size() != 0){
                if (this.maxValue-1 == t.equalValue.get(0)){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than ", t.equalValue.get(0), "equal to ", "or");
                }
            }
            
        } else if (this.equalValue.size() != 0){ // = for nuværende
            int thisEqVal = this.equalValue.get(this.equalValue.size()-1); 
            if (ISINTEGER(t)){
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, this.equalValuesToString(), "Integer", t);
            } else if (t.maxValue != null && t.withGivenMaxValue){ // <= for den nye
                if (thisEqVal == t.maxValue || thisEqVal-1==t.maxValue){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, thisEqVal, "", t.maxValue, "less than or equal to ", "or");
                }
            } else if (t.maxValue != null){ // < for den nye
                if (thisEqVal == t.maxValue || thisEqVal < t.maxValue){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, thisEqVal, "", t.maxValue, "less than ", "or");
                }
            } else if (t.minValue != null && t.withGivenMinValue){ // >= for den nye
                if (thisEqVal == t.minValue || thisEqVal+1 == t.minValue){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, thisEqVal, "", t.minValue, "bigger than or equal to ", "or");
                }
            } else if (t.minValue != null){ // > for den nye
                if (thisEqVal == t.minValue || thisEqVal > t.minValue){
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, thisEqVal, "", t.minValue, "bigger than ", "or");
                }
            }
            if (t.equalValue.size()!=0){
                Integer tEqual = t.equalValue.get(0);
                if (this.equalValue.size()!=0){
                    for (Integer i : this.equalValue) {
                        if (i == tEqual){
                            TypeCheckVisitor.warning++;
                            throw new DuplicationException(id, parentNode, tEqual);
                        }
                    }
                } 
            }
        }
        System.out.println("IntegerType.compareValues()");

        // alt er ok, og vædier kan sættes, dog check de ikke er null
        if (t.equalValue.size() != 0) {
            this.equalValue.add(t.equalValue.get(0));
        }
        if (t.minValue != null){
            this.minValue = t.minValue;
            this.withGivenMinValue = t.withGivenMinValue;
        }
        if (t.maxValue != null){
            this.maxValue = t.maxValue;
            this.withGivenMaxValue = t.withGivenMaxValue;
        }
    }

    private boolean ISINTEGER(IntegerType t){
        return t.minValue == null && 
        t.withGivenMinValue == false &&
        t.maxValue == null &&
        t.withGivenMaxValue == false &&
        t.equalValue.size() == 0;
    }
}
