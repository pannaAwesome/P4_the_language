package src.classes.types;
import java.util.ArrayList;
import java.util.List;

import src.classes.scanner.*;
import src.classes.exceptions.*;
import src.classes.visitors.*;

public class DecimalType extends BaseType {
    public Double minValue = null;
    public boolean withGivenMinValue = false;
    public Double maxValue = null;
    public boolean withGivenMaxValue = false;
    public List<Double> equalValue = new ArrayList<Double>();

    public DecimalType() {
        super(2);
    }

    private void SetMinValue(double newMinValue, boolean withValue) {
        minValue = newMinValue;
        withGivenMinValue = withValue;
    }

    private void SetMaxValue(double newMaxValue, boolean withValue) {
        maxValue = newMaxValue;
        withGivenMaxValue = withValue;
    }

    private void SetEqualValue(double newEqualValue) {
        equalValue.add(newEqualValue);
    }

    public void SetValue(String operator, double value) {
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

    @Override
    public boolean compareTypesAnd(String id, BaseType type, SimpleNode parentNode) throws Exception {
        DecimalType t = (DecimalType) type;
        if (this.minValue != null && t.maxValue != null && this.minValue > t.maxValue) {
            TypeCheckVisitor.error++;
            throw new ConstraintException(id, this.minValue, t.maxValue);
        } else {
            compareMinValue(id, t, parentNode);
            compareMaxValue(id, t, parentNode);
            compareEqualValue(id, t, parentNode);
        }
        return true;
    }

    private void compareMinValue(String id, DecimalType t, SimpleNode parentNode) throws Exception {
        if (this.minValue != null && this.withGivenMinValue) { // >= for den nuværende
            if (t.maxValue != null && this.minValue.equals(t.maxValue) && t.withGivenMaxValue) { // min og max værdierne er lig hinanden
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, this.minValue);
            }else if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "bigger than or equal to", minValue);
            }else if (t.minValue != null) { // den nye indeholder en minimumsværdi
                if (this.minValue.equals(t.minValue) && t.withGivenMinValue) { // minimum værdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "bigger than", this.minValue);
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
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "bigger than", minValue);
            }else if (t.minValue != null) { // den nye indeholder en minimumværdi
                if (this.minValue.equals(t.minValue) && !t.withGivenMinValue) { // minimumværdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "bigger than", this.minValue);
                } else if (t.withGivenMinValue) { // minimum værdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "bigger than or equal to", t.minValue, "bigger than", minValue);
                } else { // minimum værdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "bigger than", t.minValue, "bigger than", minValue);
                }
            }
        } else if (t.minValue != null) { // alt er okay og derfor kan minimumværdien sættes
            this.SetMinValue(t.minValue, t.withGivenMinValue);
        }
    }

    private void compareMaxValue (String id, DecimalType t, SimpleNode parentNode) throws Exception {
        if (this.maxValue != null && this.withGivenMaxValue) { // <= for den nuværende
            if (t.minValue != null && this.maxValue.equals(t.minValue) && t.withGivenMinValue) { // min og max værdierne er lig hinanden
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, this.minValue);
            }else if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "less than or equal to", maxValue);
            }else if (t.maxValue != null) { // den nye indeholder en maksimumsværdi
                if (this.maxValue.equals(t.maxValue) && t.withGivenMaxValue) { // maksimumsværdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "less than or equal to", this.maxValue);
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
                    throw new DuplicationException(id, parentNode, "less than", this.maxValue);
                } else if (t.withGivenMaxValue) { // maksimumværdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "less than or equal to", t.maxValue, "less than", maxValue);
                } else { // maksimumværdierne er ikke helt ens
                    TypeCheckVisitor.error++;
                    throw new ConstraintException(id, parentNode, "less than", t.maxValue, "less than", maxValue);
                }
            }
        } else if (t.maxValue != null)  { // alt er okay og derfor kan maksimumværdien sættes
            this.SetMaxValue(t.maxValue, t.withGivenMaxValue);
        }
    }

    private void compareEqualValue (String id, DecimalType t, SimpleNode parentNode) throws Exception {
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
                throw new ConstraintException(id, parentNode, "bigger than or equal to", t.minValue, equalValuesToString());
            } else { // >
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "bigger than", t.minValue, equalValuesToString());
            }
        } else if (this.equalValue.size() > 0 && t.maxValue != null) { // den nye har en maximumværdi
            if (t.withGivenMaxValue) { // <=
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "less than or equal to", t.maxValue, equalValuesToString());
            } else { // <
                TypeCheckVisitor.error++;
                throw new ConstraintException(id, parentNode, "less than", t.maxValue, equalValuesToString());
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
        DecimalType t = (DecimalType) type;
        compareValues(id, t, parentNode);
        return true;
    }

    private void compareValues(String id, DecimalType t, SimpleNode parentNode) throws DuplicationException, RedundantSyntaxException {
        if (ISDECIMAL(this) && ISDECIMAL(t)){
            TypeCheckVisitor.warning++;
            throw new DuplicationException(id, parentNode);
        } else if (ISDECIMAL(this)){
            String secondArg = "";
            if (t.minValue != null){
                secondArg = t.withGivenMinValue ? "bigger than or equal to "+t.minValue : "bigger than "+t.minValue;
            } else if (t.maxValue != null){
                secondArg = t.withGivenMaxValue ? "less than or equal to "+t.maxValue : "less than "+t.maxValue;
            } else if (t.equalValue.size() != 0){
                secondArg = "equal to "+t.equalValue.get(0);
            }
            TypeCheckVisitor.warning++;
            throw new RedundantSyntaxException(id, parentNode, "Decimal", secondArg, t);
        }
        
        if (this.minValue != null && this.withGivenMinValue) { // >= for den nuværende
            if (t.maxValue != null && t.withGivenMaxValue){ // <= for den nye
                if (Double.compare(this.minValue, t.maxValue) <= 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.maxValue, "less than or equal to ", "or");
                }
            } else if (t.maxValue != null){ // < for den nye
                if (Double.compare(this.minValue, t.maxValue) < 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.maxValue, "less than ", "or");
                }
            } else if (t.minValue != null && t.withGivenMinValue) { // >= for den nye
                if (areEqual(this.minValue, t.minValue)){ // minimumværdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "bigger than or equal to", this.minValue);
                } else { 
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.minValue, "bigger than or equal to ", "or");
                }
            } else if (t.minValue != null){ // > for den nye
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.minValue, "bigger than ", "or");
            } else if (t.equalValue.size() != 0) {
                if (Double.compare(t.equalValue.get(0), this.minValue) >= 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than or equal to ", t.equalValue.get(0), "equal to ", "or");
                }
            } else if (ISDECIMAL(t)) {
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, "bigger than or equal to "+this.minValue, "Decimal", "or", t);
            }
        } else if (this.minValue != null) { // > for den nuværende
            if (t.minValue != null && t.withGivenMinValue) { // >= for den nye
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than ", t.minValue, "bigger than or equal to ", "or");
            } else if (t.minValue != null) { // > for den nye
                if (areEqual(this.minValue, t.minValue)){ // minimumværdierne er helt ens
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "bigger than", this.minValue);
                } else { // minimumværdierne er forskellige
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than ", t.minValue, "bigger than ", "or");
                }
            } else if (t.maxValue != null && t.withGivenMaxValue) { // <= for den nye
                if (Double.compare(this.minValue, t.maxValue) <= 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than ", t.maxValue, "less than or equal to ", "or");
                }
            } else if (t.maxValue != null) { // < for den nye
                if (Double.compare(this.minValue, t.maxValue) < 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than ", t.maxValue, "less than ", "or");
                }
            } else if (t.equalValue.size() != 0) {
                if (Double.compare(t.equalValue.get(0), this.minValue) > 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.minValue, "bigger than ", t.equalValue.get(0), "equal to ", "or");
                }
            } else if (ISDECIMAL(t)) {
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, "bigger than "+this.minValue, "Decimal", "or", t);
            }
        } else if (this.maxValue != null && this.withGivenMaxValue) { // <= for den nuværende
            if (t.maxValue != null && t.withGivenMaxValue){ // <= for den nye
                if (areEqual(this.maxValue, t.maxValue)) {
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "less than or equal to", this.maxValue);
                } else {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.maxValue, "less than or equal to ", "or");
                }
            } else if (t.maxValue != null){ // < for den nye
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.maxValue, "less than ", "or");
            } else if (t.minValue != null && t.withGivenMinValue) { // >= for den nye
                if (Double.compare(t.minValue, this.maxValue) <= 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.minValue, "bigger than or equal to ", "or");
                }
            } else if (t.minValue != null){ // > for den nye
                if (Double.compare(t.minValue, this.maxValue)<0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.minValue, "bigger than ", "or");
                }
            } else if (t.equalValue.size() != 0) { // = for den nye
                if (Double.compare(t.equalValue.get(0), this.maxValue) <= 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than or equal to ", t.equalValue.get(0), "equal to ", "or");
                }
            } else if (ISDECIMAL(t)) {
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, "less than or equal to "+this.maxValue, "Decimal", "or", t);
            }
        } else if (this.maxValue != null) { // < for den nuværende
            if (t.minValue != null && t.withGivenMinValue) { // >= for den nye
                if (Double.compare(t.minValue, this.maxValue) <= 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than ", t.minValue, "bigger than or equal to ", "or");
                }
            } else if (t.minValue != null) { // > for den nye
                if (Double.compare(t.minValue, this.maxValue)<0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than ", t.minValue, "bigger than ", "or");
                }
            } else if (t.maxValue != null && t.withGivenMaxValue){ // <= for den nye
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than ", t.maxValue, "less than or equal to ", "or");
            } else if (t.maxValue != null){ // < for den nye
                if (areEqual(this.maxValue, t.maxValue)) {
                    TypeCheckVisitor.warning++;
                    throw new DuplicationException(id, parentNode, "less than", this.maxValue);
                } else {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than ", t.maxValue, "less than ", "or");
                }
            } else if (t.equalValue.size() != 0) { // = for den nye
                if (Double.compare(t.equalValue.get(0), this.maxValue) < 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, this.maxValue, "less than ", t.equalValue.get(0), "equal to ", "or");
                }
            } else if (ISDECIMAL(t)) {
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, "less than "+this.maxValue, "Decimal", "or", t);
            }
        } else if (this.equalValue.size() != 0){ // = for den nuværende
            double thisEqVal = this.equalValue.get(this.equalValue.size()-1);
            if (ISDECIMAL(t)) { // IS DECIMAL for den nye
                TypeCheckVisitor.warning++;
                throw new RedundantSyntaxException(id, parentNode, ""+this.equalValue.get(this.equalValue.size()-1), "Decimal", "or", t);
            } else if (t.equalValue.size() != 0){
                double d2 = t.equalValue.get(0);
                for (Double d : this.equalValue) {
                    if (areEqual(d, d2)) {
                        TypeCheckVisitor.warning++;
                        throw new DuplicationException(id, parentNode, d2);
                    }
                }
            } else if (t.maxValue != null && t.withGivenMaxValue){ // <= for den nye
                if (Double.compare(thisEqVal, t.maxValue) <= 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, thisEqVal, "", t.maxValue, "less than or equal to ", "or");
                }
            } else if (t.maxValue != null) { // < for den nye
                if (Double.compare(thisEqVal, t.maxValue) < 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, thisEqVal, "", t.maxValue, "less than ", "or");
                }
            } else if (t.minValue != null && t.withGivenMinValue) { // >= for den nye
                if (Double.compare(thisEqVal, t.minValue) >= 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, thisEqVal, "", t.minValue, "bigger than or equal to ", "or");
                }
            } else if (t.minValue != null) { // > for den nye
                if (Double.compare(thisEqVal, t.minValue) > 0) {
                    TypeCheckVisitor.warning++;
                    throw new RedundantSyntaxException(id, parentNode, thisEqVal, "", t.minValue, "bigger than ", "or");
                }
            }
        }
        
        if (t.equalValue.size() != 0) {
            this.equalValue.add(t.equalValue.get(0));
        }
        if (t.minValue != null) {
            this.minValue = t.minValue;
            this.withGivenMinValue = t.withGivenMinValue;
        }
        if (t.maxValue != null) {
            this.maxValue = t.maxValue;
            this.withGivenMaxValue = t.withGivenMaxValue;
        }
    }
    
    private boolean ISDECIMAL(DecimalType t) {
        return t.minValue == null &&
               t.withGivenMinValue == false &&
               t.maxValue == null &&
               t.withGivenMaxValue == false &&
               t.equalValue.size() == 0;
    }

    // Used to compare doubles
    private boolean areEqual(Double d1, Double d2){
        return Double.compare(d1, d2) == 0;
    }

    public String toString() {
        return "Decimal";
    }

    public String toString(String minOrMaxOrEqual){
        switch (minOrMaxOrEqual) {
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
}