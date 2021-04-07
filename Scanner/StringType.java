public class StringType extends BaseType {
    private String value = null;
    private boolean onlyContains = false;

    public StringType(String currentValue) {
        super(1);
        value = currentValue;
    }

    public void setOnlyContains(boolean currOnlyContains) {
        onlyContains = currOnlyContains;
    }

    @Override
    public boolean equals(Object obj) {
        StringType t = (StringType) obj;
        if (this.type == t.type) {
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
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "String, where string is " + value;
    }
}
