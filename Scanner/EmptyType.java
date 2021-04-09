public class EmptyType extends BaseType {
    public boolean notFlag = true;

    public EmptyType() {
        super(5);
    }

    @Override
    public String toString() {
        return (notFlag ? "Not Empty" : "Empty");
    }

    @Override
    public boolean equals(Object obj) {
        BaseType baseT = (BaseType) obj;
        if (this.type == baseT.type) {
            EmptyType t = (EmptyType) obj;
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
