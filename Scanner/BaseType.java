public abstract class BaseType {
    protected final int type;

    public BaseType(int currentType) {
        type = currentType;
    }

    @Override
    public boolean equals(Object obj) {
        BaseType t = (BaseType) obj;
        return this.type == t.type;
    }

    @Override
    public String toString() {
        return "No type was given";
    }
}
