public class IntegerType extends BaseType {
    private Integer minValue = null;
    private boolean withGivenMinValue = false;
    private Integer maxValue = null;
    private boolean withGivenMaxValue = false;
    private Integer equalValue = null;

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
        equalValue = newEqualValue;
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

    @Override
    public boolean compareTypes(BaseType type) {
        IntegerType t = (IntegerType) type;
        if (t.minValue == null && t.maxValue == null && t.equalValue == null
            && this.minValue == null && this.maxValue == null && this.equalValue == null) {
            return true;
        } else if (this.equalValue.equals(t.equalValue) 
                && this.minValue == null && this.maxValue == null
                && t.minValue == null && t.maxValue == null) {
            return true;
        } else if (this.minValue.equals(t.minValue) && this.withGivenMinValue == t.withGivenMinValue
                && this.maxValue.equals(t.maxValue) && this.withGivenMaxValue == t.withGivenMaxValue) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String values = "";
        if (equalValue != null) {
            values = ", where value is " + equalValue.toString();
        } else {
            values += (minValue != null) ? ", where minimum value is " + minValue.toString() : "";
            values += (maxValue != null && values.equals("")) ? ", where maximum value is " + maxValue.toString()
                    : (maxValue != null) ? " and maximum value is " + maxValue.toString() : "";
        }
        return "Integer" + values;
    }
}
