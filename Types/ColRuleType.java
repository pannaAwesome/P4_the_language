package Types;
import Types.*;
import Exceptions.*;
import Visitors.*;
import jjtNode.*;
import Scanner.*;

public class ColRuleType extends BaseType{
    public ColRuleType() {
        super(9);
    }

    @Override
    public String toString(){
        return "Column Rule";
    }
}
