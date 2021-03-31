public abstract class BaseType {
    private final int type;

    public BaseType(int currentType) {
        type = currentType;
    }

    @Override
    public boolean equals(BaseType obj) {
        return this.type == obj.type;
    }

    @Override
    public String toString() {
        return "No type was given";
    }
}
