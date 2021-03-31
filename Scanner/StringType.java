public class StringType extends BaseType {
    private String value = null;

    public StringType(String currentValue) {
        super(1);
        value = currentValue;
    }

    @Override
    public boolean equals(Object obj) {
        StringType t = (StringType) obj;
        if (this.type == t.type) {
            if (this.value.equals(t.value)) {
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
        return "String, where string is " + value;
    }
}
