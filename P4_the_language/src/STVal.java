package src;
import java.util.ArrayList;

public class STVal {
    public ArrayList<BaseType> type = new ArrayList<BaseType>();
    public String parentRule;

    public STVal(BaseType t){
        type.add(t);
    }

    public String toString() {
        return type.toString();
    }

    public String toString(String all) {
        String types = "";
        if (type.size() == 1) {
            types += "type " + type.get(0);
        } else {
            types += " types: ";
            for (BaseType type : type) {
                types += type.toString();
            }
        }
        return types;
    }
}