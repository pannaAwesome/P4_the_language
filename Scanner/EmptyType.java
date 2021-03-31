public class EmptyType extends BaseType {
    private boolean notFlag = false;

    public EmptyType() {
        super(5);
    }

    @Override
    public String toString() {
        return "Empty";
    }

    @Override
    public boolean equals(Object obj) {
        EmptyType t = (EmptyType) obj;
        if (this.type == t.type) {
            if (this.notFlag == t.notFlag) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
