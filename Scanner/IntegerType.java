public class IntegerType extends BaseType {
    private Integer minValue = null;
    private boolean withGivenMinValue = false;
    private Integer maxValue = null;
    private boolean withGivenMaxValue = false;

    public IntegerType() {
        super(3);
    }

    public void SetMinValue(int newMinValue, boolean withValue) {
        minValue = newMinValue;
        withGivenMinValue = withValue;
    }

    public void SetMaxValue(int newMaxValue, boolean withValue) {
        maxValue = newMaxValue;
        withGivenMaxValue = withValue;
    }

    @Override
    public boolean equals(Object obj) {
        IntegerType t = (IntegerType) obj;
        if (this.type == t.type) {
            if (t.minValue == null && t.maxValue == null) {
                return true;
            } else if (this.minValue.equals(t.minValue) && this.withGivenMinValue == t.withGivenMinValue
                    && this.maxValue.equals(t.maxValue) && this.withGivenMaxValue == t.withGivenMaxValue) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String values = "";
        values += (minValue != null) ? ", where minimum value is " + minValue.toString() : "";
        values += (maxValue != null && values.equals("")) ? ", where maximum value is " + maxValue.toString()
                : (maxValue != null) ? " and maximum value is " + maxValue.toString() : "";
        return "Integer" + values;
    }
}
