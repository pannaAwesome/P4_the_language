public class StringType extends BaseType {
    private String value = null;
    private boolean onlyContains = false;

    public StringType() {
        super(1);
    }

    public void setStringValues(String operator, String newValue) {
        if (operator.equals("CONTAINS")) {
            onlyContains = true;
        } else {
            onlyContains = false;
        }
        value = newValue;
    }

    @Override
    public boolean compareTypes(BaseType type) {
        StringType t = (StringType) type;
        if (this.onlyContains == false && t.onlyContains == false) {
            if (this.value.equals(t.value)) {
                return true;
            } else {
                return false;
            }
        } else if (this.onlyContains && t.onlyContains) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public String toString() {

        return "String, where string " + (onlyContains ? "contains" : "is") + " " + value;
    }
}
