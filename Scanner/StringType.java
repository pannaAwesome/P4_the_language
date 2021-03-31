public class StringType extends BaseType {
    private String value = null;

    public StringType(String currentValue) {
        super(1);
        value = currentValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.type == obj.type) {
            if (this.value.equals(obj.value)) {
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
