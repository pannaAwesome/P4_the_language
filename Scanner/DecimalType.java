public class DecimalType extends BaseType {
    private Double minValue = null;
    private boolean withGivenMinValue = false;
    private Double maxValue = null;
    private boolean withGivenMaxValue = false;
    private Double equalValue = null;

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
        equalValue = newEqualValue;
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
    public boolean equals(Object obj) {
        BaseType baseT = (BaseType) obj;
        if (this.type == baseT.type) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean compareTypes(BaseType type) {
        DecimalType t = (DecimalType) type;
            if (t.minValue == null && t.maxValue == null && t.equalValue == null) {
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
        
        return "Decimal" + values;
    }
}