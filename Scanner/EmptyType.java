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
    public boolean equals(EmptyType obj) {
        if (this.type == obj.type) {
            if (this.notFlag == obj.notFlag) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
