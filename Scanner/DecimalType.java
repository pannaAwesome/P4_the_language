import java.util.ArrayList;
import java.util.List;

public class DecimalType extends BaseType {
    private Double minValue = null;
    private boolean withGivenMinValue = false;
    private Double maxValue = null;
    private boolean withGivenMaxValue = false;
    private List<Double> equalValue = new ArrayList<Double>();

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
        if (this.minValue > t.maxValue) {
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
                throw new RedundantSyntaxException(id, parentNode, this.minValue);
            }else if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "less than or equal", minValue);
            }else if (t.minValue != null) { // den nye indeholder en minimumsværdi
                if (this.minValue.equals(t.minValue) && t.withGivenMinValue) { // minimum værdierne er helt ens
                    throw new DuplicationException(id, parentNode, "less than", this.minValue);
                } else if (t.withGivenMinValue) { // minimum værdierne er ikke helt ens
                    throw new ConstraintException(id, parentNode, "less than or equal", t.minValue, "less than or equal", minValue);
                } else { // minimum værdierne er ikke helt ens
                    throw new ConstraintException(id, parentNode, "less than", t.minValue, "less than or equal", minValue);
                }
            }
        } else if (this.minValue != null && !this.withGivenMinValue) { // > for den nuværende
            if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "less than", minValue);
            }else if (t.minValue != null) { // den nye indeholder en minimumværdi
                if (this.minValue.equals(t.minValue) && !t.withGivenMinValue) {
                    throw new DuplicationException(id, parentNode, "less than", this.minValue);
                } else if (t.withGivenMinValue) { // minimum værdierne er ikke helt ens
                    throw new ConstraintException(id, parentNode, "less than or equal", t.minValue, "less than", minValue);
                } else { // minimum værdierne er ikke helt ens
                    throw new ConstraintException(id, parentNode, "less than", t.minValue, "less than", minValue);
                }
            }
        } else { // alt er okay og derfor kan minimumværdien sættes
            this.SetMinValue(t.minValue, t.withGivenMinValue);
        }
    }

    private void compareMaxValue (String id, DecimalType t, SimpleNode parentNode) throws Exception {
        if (this.maxValue != null && this.withGivenMaxValue) { // <= for den nuværende
            if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "bigger than or equal", maxValue);
            }else if (t.maxValue != null) { // den nye indeholder en maksimumsværdi
                if (this.maxValue.equals(t.maxValue) && t.withGivenMaxValue) { // maksimumsværdierne er helt ens
                    throw new DuplicationException(id, parentNode, "bigger than or equal", this.maxValue);
                } else if (t.withGivenMaxValue) { // makismum værdierne er ikke helt ens
                    throw new ConstraintException(id, parentNode, "bigger than or equal", t.maxValue, "bigger than or equal", maxValue);
                } else { // maksimumværdierne er ikke helt ens
                    throw new ConstraintException(id, parentNode, "bigger than", t.maxValue, "bigger than or equal", maxValue);
                }
            }
        } else if (this.maxValue != null && !this.withGivenMaxValue) { // < for den nuværende
            if (t.equalValue.size() != 0) { // den nye indeholder en lig med værdi
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "bigger than or equal", maxValue);
            }else if (t.maxValue != null) { // den nye indeholder en maksimumværdi
                if (this.maxValue.equals(t.maxValue) && !t.withGivenMaxValue) { // 
                    throw new DuplicationException(id, parentNode, "bigger than", this.maxValue);
                } else if (t.withGivenMaxValue) { // makismumværdierne er ikke helt ens
                    throw new ConstraintException(id, parentNode, "bigger than or equal", t.maxValue, "bigger than or equal", maxValue);
                } else { // makismumværdierne er ikke helt ens
                    throw new ConstraintException(id, parentNode, "bigger than", t.maxValue, "bigger than", maxValue);
                }
            }
        } else { // alt er okay og derfor kan maksimumværdien sættes
            this.SetMaxValue(t.maxValue, t.withGivenMaxValue);
        }
    }

    private void compareEqualValue (String id, DecimalType t, SimpleNode parentNode) throws Exception {
        if (this.equalValue.size() > 0 && t.equalValue.size() != 0) { // den nuværende og den nye har begge lig med værdier
            if (this.equalValue.contains(t.equalValue.get(0))) { // lig med værdierne er de samme
                throw new DuplicationException(id, parentNode, t.equalValue.get(0));
            }else { // lig med værdierne er ikke de samme
                String equalValues = "";
                for (int i = 0; i < equalValue.size(); i++) {
                    if (i == 0) {
                        equalValues += equalValue.get(i).toString();
                    } else {
                        equalValues += " or " + equalValue.get(i).toString();
                    }
                }
                throw new ConstraintException(id, parentNode, t.equalValue.get(0), equalValues);
            }    
        }else if (t.equalValue.size() != 0) { // alt er okay og derfor kan lig med værdien sættes
            this.equalValue.add(t.equalValue.get(0));
        }
    }

    @Override
    public boolean compareTypesOr(String id, BaseType type, SimpleNode parentNode) throws Exception {
        DecimalType t = (DecimalType) type;
        compareValues(id, t, parentNode);
        return true;
    }

    private void compareValues(String id, DecimalType t, SimpleNode parentNode) throws Exception {
        if (this.minValue != null && t.minValue != null) { // begge har en minimum værdi
            String firstConstrain = this.withGivenMinValue ? "less than or equal" : "less than";
            String secondConstrain = t.withGivenMinValue ? "less than or equal" : "less than";
            if (this.withGivenMinValue == t.withGivenMinValue && this.minValue.equals(t.minValue)){ // minimumværdierne er helt ens
                throw new DuplicationException(id, parentNode, firstConstrain, this.minValue);
            } else { // minimumværdierne er forskellige
                throw new RedundantSyntaxException(id, parentNode, this.minValue, firstConstrain, t.minValue, secondConstrain);
            }
        } else if (this.maxValue != null && t.maxValue != null){ // begge har en maksimum værdi
            String firstConstrain = this.withGivenMaxValue ? "bigger than or equal" : "bigger than";
            String secondConstrain = t.withGivenMaxValue ? "bigger than or equal" : "bigger than";
            if (this.withGivenMaxValue == t.withGivenMaxValue && this.maxValue.equals(t.maxValue)){ // maksimumværdierne er helt ens
                throw new DuplicationException(id, parentNode, firstConstrain, this.maxValue);
            } else { // maksimumværdierne er forskellige
                throw new RedundantSyntaxException(id, parentNode, this.maxValue, firstConstrain, t.maxValue, secondConstrain);
            }
        }
        if (t.equalValue.size() != 0) {
            this.equalValue.add(t.equalValue.get(0));
        }
        this.minValue = t.minValue;
        this.maxValue = t.maxValue;
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