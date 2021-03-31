public class DecimalType extends BaseType {
    private Double minValue = null;
    private boolean withGivenMinValue = false;
    private Double maxValue = null;
    private boolean withGivenMaxValue = false;

    public DecimalType() {
        super(2);
    }

    public SetMinValue(double newMinValue, boolean withValue) {
        minValue = newMinValue;
        withGivenMinValue = withValue;
    }

    public SetMaxValue(double newMaxValue, boolean withValue) {
        maxValue = newMaxValue;
        withGivenMaxValue = withValue;
    }

    @Override
    public boolean equals(DecimalType obj) {
        if (this.type == obj.type) {
            if (obj.minValue == null && obj.maxValue == null) {
                return true;
            } else if (this.minValue.equals(obj.minValue) && this.withGivenMinValue == obj.withGivenMinValue
                    && this.maxValue.equals(obj.maxValue) && this.withGivenMaxValue == obj.withGivenMaxValue) {
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
        return "Decimal" + values;
    }
}