import java.util.ArrayList;

public class STVal {
    public ArrayList<BaseType> type = new ArrayList<BaseType>();
    public String parentRule;

    public STVal(BaseType t){
        type.add(t);
    }
}