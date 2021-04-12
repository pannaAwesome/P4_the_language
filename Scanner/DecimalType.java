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

    /**
     * @throws ConstraintException
     * @throws RedundantSyntaxException
     * @throws DuplicationException
     */
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
        if (this.minValue != null && this.withGivenMinValue) {
            if (this.minValue.equals(t.maxValue) && t.withGivenMaxValue) {
                throw new RedundantSyntaxException(id, parentNode, this.minValue);
            }else if (t.equalValue.size() != 0) {
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "less than or equal", minValue);
            }else if (t.minValue != null) {
                if (this.minValue.equals(t.minValue) && t.withGivenMinValue) {
                    throw new DuplicationException(id, parentNode, "less than", this.minValue);
                } else {
                    throw new ConstraintException(id, parentNode, "less than or equal", t.minValue, "less than or equal", minValue);
                }
            }
        } else if (this.minValue != null && !this.withGivenMinValue) {
            if (t.equalValue.size() != 0) {
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "less than", minValue);
            }else if (t.minValue != null) {
                if (this.minValue.equals(t.minValue) && !t.withGivenMinValue) {
                    throw new DuplicationException(id, parentNode, "less than", this.minValue);
                } else {
                    throw new ConstraintException(id, parentNode, "less than", t.minValue, "less than", minValue);
                }
            }
        } else {
            this.SetMinValue(t.minValue, t.withGivenMinValue);
        }
    }

    private void compareMaxValue (String id, DecimalType t, SimpleNode parentNode) throws Exception {
        if (this.maxValue != null && this.withGivenMaxValue) {
            if (t.equalValue.size() != 0) {
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "bigger than or equal", maxValue);
            }else if (t.maxValue != null) {
                if (this.maxValue.equals(t.maxValue) && t.withGivenMaxValue) {
                    throw new DuplicationException(id, parentNode, "bigger than or equal", this.maxValue);
                } else {
                    throw new ConstraintException(id, parentNode, "bigger than or equal", t.maxValue, "bigger than or equal", maxValue);
                }
            }
        } else if (this.maxValue != null && !this.withGivenMaxValue) {
            if (t.equalValue.size() != 0) {
                throw new ConstraintException(id, parentNode, "", t.equalValue.get(0), "bigger than or equal", maxValue);
            }else if (t.maxValue != null) {
                if (this.maxValue.equals(t.maxValue) && !t.withGivenMaxValue) {
                    throw new DuplicationException(id, parentNode, "bigger than", this.maxValue);
                } else {
                    throw new ConstraintException(id, parentNode, "bigger than", t.maxValue, "bigger than", maxValue);
                }
            }
        } else {
            this.SetMaxValue(t.maxValue, t.withGivenMaxValue);
        }
    }

    private void compareEqualValue (String id, DecimalType t, SimpleNode parentNode) throws Exception {
        if (this.equalValue.size() > 0 && t.equalValue.size() != 0) {
            if (this.equalValue.contains(t.equalValue.get(0))) {
                throw new DuplicationException(id, parentNode, t.equalValue.get(0));
            }else {
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
        } else  if (t.equalValue.size() != 0) {
            this.equalValue.add(t.equalValue.get(0));
        }
    }

    @Override
    public boolean compareTypesOr(BaseType type) {
        DecimalType t = (DecimalType) type;
        return true;
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