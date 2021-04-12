public class StringType extends BaseType {
    private String value = null;
    private boolean onlyContains = false;

    public StringType() {
        super(1);
    }

    public void setStringValues(String operator, String newValue) {
        onlyContains = operator.equals("CONTAINS");
        value = newValue;
    }

    public boolean compareTypes(BaseType type) {
        StringType t = (StringType) type;
        if (this.onlyContains == false && t.onlyContains == false) {
            return this.value.equals(t.value);
        } else {
            return this.onlyContains && t.onlyContains;
        }
    }
    
    public String toString() {
        return "String";
    }

    public String toString(String withValue) {
        return value;
    }

    @Override
    public boolean compareTypesAnd(String id, BaseType type, SimpleNode parentNode){
        if (this.onlyContains && type.onlyContains){
            if (this.value.equals(type.value)) throw new DuplicationException(id, parentNode, "contain "+type.value);          
        } else if (!this.onlyContains && !type.onlyContains){
            this.value.equals(type.value) ? return throw DuplicationException(id, parentNode, "be defined as "+"\""+this.value+"\"") : throw new ConstraintException(id, this.value, type.value, parentNode); 
        } else if (this.onlyContains && !type.onlyContains){
            if (type.value.contains(this.value)) throw new RedundantSyntaxException(id, type.value);
        } else if (!this.onlyContains && type.onlyContains){
            if ((type.value.contains(this.value)) throw new RedundantSyntaxException(id, this.value);
        }
        return true; 
    }

    public boolean compareTypesOr(BaseType type){
        if (this.onlyContains && type.onlyContains){
            if (this.value.equals(type.value)) throw new DuplicationException(id, parentNode, "contain "+type.value); 
        } else if (!this.onlyContains && !type.onlyContains){
            if (this.value.equals(type.value)) {
                throw DuplicationException(id, parentNode, "be defined as "+"\""+this.value+"\""); 
            }
        }
        return true;
    }
}
