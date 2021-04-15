package Types;
import Types.*;
import Exceptions.*;
import Visitors.*;
import jjtNode.*;
import Scanner.*;

public class RuleType extends BaseType {
    public RuleType() {
        super(8);
    }

    @Override
    public String toString(){
        return "Rule";
    }
}
